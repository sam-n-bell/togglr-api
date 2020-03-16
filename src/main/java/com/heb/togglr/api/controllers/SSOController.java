package com.heb.togglr.api.controllers;

import javassist.tools.web.BadHttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class SSOController {

    @RequestMapping(method = RequestMethod.GET, value = "/ssologin")
    @ResponseBody
    public String ssologin() {
return ("${heb.security.oauth2.client.registration.github.redirectUri}");
    }
}
