package com.heb.togglr.api.security.cloud;


import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.heb.togglr.api.security.jwt.service.JwtService;

@Component
@Profile({"local","clouddev"})
public class RestAuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${heb.togglr.jwt.tokenheader}")
    private String tokenHeader;

    @Value("${heb.togglr.app-domain}")
    private String cookieDomain;

    private JwtService jwtService;


    public RestAuthSuccessHandler(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        User userDetails = new User(authentication.getName(), "",  true, true, true, true, new ArrayList<>());

        String jwt = this.jwtService.generateToken(userDetails);

        Cookie cookie = new Cookie(this.tokenHeader, jwt);
        cookie.setDomain(this.cookieDomain);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
