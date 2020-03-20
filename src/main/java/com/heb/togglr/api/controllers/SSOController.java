package com.heb.togglr.api.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SSOController {

    @Value("${app.oauth2.sso.url}")
    private String ssoUrl;
    @RequestMapping(method = RequestMethod.GET, value = "/ssologin")
    @ResponseBody
    public String ssologin() {
        System.out.println("From docker-compose: " + ssoUrl);
        return ("${heb.security.oauth2.client.registration.github.redirectUri}");
    }
}
