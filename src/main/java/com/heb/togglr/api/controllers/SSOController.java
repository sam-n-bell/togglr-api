package com.heb.togglr.api.controllers;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SSOController {
    @Value("${spring.security.oauth2.client.userAuthorizationUri}")
    private String ssourl;

    @RequestMapping(method = RequestMethod.GET, value = "/ssologin")
    @ResponseBody
    public ResponseEntity<?> ssologin() {
//        System.out.println(System.getenv("SSO_URL"));
        System.out.println("Debugging sso url");
//        System.out.println(ssourl);
        return ResponseEntity.ok(ssourl);
//        return null;
    }
}
