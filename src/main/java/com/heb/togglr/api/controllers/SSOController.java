package com.heb.togglr.api.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.heb.togglr.api.models.responses.WebhookResponse;
import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.Collections;
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

    private RestTemplate restTemplate = new RestTemplate();;

    @RequestMapping(method = RequestMethod.GET, value = "/ssologin")
    @ResponseBody
    public ResponseEntity<?> ssologin() {
        StringBuilder ssoUrl = new StringBuilder();

        ssoUrl.append(this.userAuthorizationUri)
                .append("client_id=").append(this.clientId)
                .append("&redirect_uri=").append(this.redirectUri);

        return ResponseEntity.ok(ssoUrl.toString());
    }

    @RequestMapping(method = RequestMethod.GET, value="/oauth/signin/callback")
    @ResponseBody
    public ResponseEntity<?> handleCode(@RequestParam(defaultValue = "None") String code, Principal principal) {
        // Shikha - the code below is able to get the access token back from the temp code

        try{
            System.out.println(principal.getName());
        } catch (Exception e) {
            System.out.println("error in fist try");
            System.out.println(e.getMessage());
        }
        // checking the temp code from the url params
        System.out.println("code ========== " + code);

        // preparing to make a POST request to github to get access token
        StringBuilder accessTokenRequestUri = new StringBuilder();
        accessTokenRequestUri.append(this.accessTokenUri)
                .append("?client_id=").append(this.clientId)
                .append("&client_secret=").append(this.clientSecret)
                .append("&code=").append(code);

        // setting the 'Accept': 'Application/Json' header so github returns data in JSON format
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // checking the url to make sure it's valid
        System.out.println(accessTokenRequestUri.toString());

        // making the POST
        ResponseEntity<String> response = this.restTemplate.postForEntity(
                accessTokenRequestUri.toString(),
                entity,
                String.class);


        // Now lets prepare to parse the json data that came back
        // one way to do this would be to create a new model class, but not sure of if providers send back different data
        // so handling this more dynamically right now
        ObjectMapper objectMapper = new ObjectMapper();
        String token;

        try {
            // parse the response into a jsonnode (something i saw from Googling)
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            // try to get the access_token if it's present
            token = jsonNode.get("access_token").asText(null);
        } catch (Exception e) {
            token = null;
        }

        System.out.println("token = " + token);

        // next steps:
        // 1. return the token from above to the UI
        // 2. tell UI to redirect the user inside of Togglr
        // 3. How to get user object back to UI

        // plan b:
        // get user obj by doing a GET to userInfoUri (api.github.com/user)
        // create a custom model class that contains the access token (String) and a User object from Spring
        // fill the Spring User object with the data from userInfoUri (github)
        // create a jwt out of the custom model
        // send that model class back in a redirect

        // current
        // Spring gets the User details
        // Stores that user object as a JWT, returns it to the UI
        // UI decodes it to get the user object out


        return ResponseEntity.ok(token);
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
