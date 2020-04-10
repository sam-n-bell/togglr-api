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

    private RestTemplate restTemplate = new RestTemplate();;

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
        // Shikha - the code below is able to get the access token back from the temp code

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

        // status code of the response of POST. Good is 200
        System.out.println(response.getStatusCodeValue());

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
        // 2. tell UI to redirect the user inside of Togglr?

        return ResponseEntity.ok(code.toString());
    }
}
