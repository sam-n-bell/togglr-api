package com.heb.togglr.api.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heb.togglr.api.entities.SuperAdminsEntity;
import com.heb.togglr.api.handlers.UpdateEventHandlers;
import com.heb.togglr.api.models.responses.WebhookResponse;
import com.heb.togglr.api.repositories.SuperAdminRepository;
import com.heb.togglr.api.security.jwt.service.JwtService;
import javassist.tools.web.BadHttpRequest;
import org.apache.catalina.filters.ExpiresFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class SSOController {
    @Value("${spring.security.oauth2.client.userAuthorizationUri}")
    private String userAuthorizationUri;

    @Value("${spring.security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.clientId}")
    private String clientId;

    @Value("${spring.security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    @Value("${spring.security.oauth2.client.redirectUri}")
    private String redirectUri;

    @Value("${heb.togglr.app-domain}")
    private String cookieDomain;

    @Value("${heb.togglr.oauth.tokenheader}")
    private String oauthTokenHeader;

    @Value("${heb.togglr.jwt.tokenheader}")
    private String tokenHeader;

    @Value("${heb.togglr.oauth.userIdentification}")
    private String ssoUserIdentification;

    @Value("${spring.security.oauth2.resource.userInfoUri}")
    private String userInfoUri;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private SuperAdminRepository superAdminRepository;

    private static Logger logger = LoggerFactory.getLogger(SSOController.class);

    private RestTemplate restTemplate = new RestTemplate();;

    @RequestMapping(method = RequestMethod.GET, value = "/ssologin")
    @ResponseBody
    public ResponseEntity<?> ssologin() {
        StringBuilder ssoUrl = new StringBuilder();

        ssoUrl.append(this.userAuthorizationUri)
                .append("client_id=").append(this.clientId);

        return ResponseEntity.ok(ssoUrl.toString());
    }

    @RequestMapping(method = RequestMethod.GET, value="/oauth/signin/callback")
    @ResponseBody
    public void handleCode(@RequestParam(defaultValue = "None") String code, HttpServletResponse servletResponse, Principal principal) throws IOException {

        // preparing  POST request uri to provider to get access token
        StringBuilder accessTokenRequestUri = new StringBuilder();
        accessTokenRequestUri.append(this.accessTokenUri)
                .append("?client_id=").append(this.clientId)
                .append("&client_secret=").append(this.clientSecret)
                .append("&code=").append(code);

        // setting the 'Accept': 'Application/Json' header so provider returns data in JSON format
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // making POST to get access token
        ResponseEntity<String> response = this.restTemplate.postForEntity(
                accessTokenRequestUri.toString(),
                entity,
                String.class);

        try {
            // Now lets prepare to parse the json data that came back
            ObjectMapper objectMapper = new ObjectMapper();
            String token;
            // parse the response into a jsonnode
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            // try to get the access_token if it's present
            token = jsonNode.get("access_token").asText(null);

            if (token != null) {
                // GET user info from oauth provider
                System.out.println("About get usesr data from provider");
                headers.setBearerAuth(token);
                entity = new HttpEntity<>(null, headers);
                ResponseEntity<String> userResponse = this.restTemplate.exchange(
                        this.userInfoUri,
                        HttpMethod.GET,
                        entity,
                        String.class
                );
                String userIdentifier;
                jsonNode = objectMapper.readTree(userResponse.getBody());
                System.out.println("Reading the response to check property: " + this.ssoUserIdentification);
                userIdentifier = jsonNode.get(this.ssoUserIdentification).asText();
                System.out.println(userIdentifier + " is logged in");

                SuperAdminsEntity superAdmin = this.superAdminRepository.findById(userIdentifier).orElse(null);
                List<GrantedAuthority> userRoles = new ArrayList<>();
                if (superAdmin != null) {
                    userRoles.add(new SimpleGrantedAuthority("ROLE_SUPERADMIN"));
                }

                User userDetails = new User(userIdentifier, "",  true, true, true, true, userRoles);

                String jwt = this.jwtService.generateToken(userDetails);

                Cookie oauthToken = new Cookie(this.oauthTokenHeader, token);
                oauthToken.setDomain(this.cookieDomain);
                oauthToken.setPath("/");
                Cookie togglrToken = new Cookie(this.tokenHeader, jwt);
                togglrToken.setDomain(this.cookieDomain);
                togglrToken.setPath("/");
                servletResponse.addCookie(togglrToken);
                servletResponse.addCookie(oauthToken);
            }
        } catch (Exception e) {
            logger.error("Issue dealing with oauth provider: " + e.getMessage());
            System.out.println("Issue dealing with oauth provider: " + e.getMessage());
        }

        servletResponse.sendRedirect(this.redirectUri);

    }

    @RequestMapping(method = RequestMethod.GET, value="/oauth/test")
    @ResponseBody
    public void testAuthenticatedRoute(Principal principal) {
        try{
            System.out.println(principal.getName());
        } catch (Exception e) {
            System.out.println("error in this new try");
            System.out.println(e.getMessage());
        }
    }
}
