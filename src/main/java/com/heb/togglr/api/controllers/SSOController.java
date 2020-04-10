package com.heb.togglr.api.controllers;

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

import java.util.Collections;
import java.util.Optional;

@RestController
public class SSOController {
    @Value("${spring.security.oauth2.client.userAuthorizationUri}")
    private String ssourl;

    @Value("${spring.security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.clientId}")
    private String clientId;


    @Value("${spring.security.oauth2.client.accessTokenUri}")
    private String accessTokenUri;

    private RestTemplate restTemplate;


    @RequestMapping(method = RequestMethod.GET, value = "/ssologin")
    @ResponseBody
    public ResponseEntity<?> ssologin() {
//        System.out.println(System.getenv("SSO_URL"));
        System.out.println("Debugging sso url");
//        System.out.println(ssourl);
        return ResponseEntity.ok(ssourl);
//        return null;
    }

    @RequestMapping(method = RequestMethod.GET, value="/oauth/signin/callback")
    @ResponseBody
    public ResponseEntity<?> handleCode(@RequestParam(defaultValue = "None") String code) {
        System.out.println("code ========== " + code);

//        WebhookResponse webhookResponse = this.restTemplate.postForObject(webhookUrl, null, WebhookResponse.class);
        if (!code.equalsIgnoreCase("None")) {
            StringBuilder accessTokenRequestUri = new StringBuilder();
            accessTokenRequestUri.append(this.accessTokenUri)
                    .append("?client_id=").append(this.clientId)
                    .append("&client_secret=").append(this.clientSecret)
                    .append("&code=").append(code);

            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>("body", headers);
            System.out.println(accessTokenRequestUri.toString());
//            ResponseEntity<String> response = restTemplate.postForEntity(accessTokenRequestUri.toString(), null, String.class);
//            System.out.println(response.getStatusCodeValue());
//            System.out.println(response.getBody());
        }

        return ResponseEntity.ok(code.toString());
    }
}
