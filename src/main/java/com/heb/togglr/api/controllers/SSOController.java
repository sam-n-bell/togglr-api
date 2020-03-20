package com.heb.togglr.api.controllers;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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
