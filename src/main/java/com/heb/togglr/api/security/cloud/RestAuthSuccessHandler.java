package com.heb.togglr.api.security.cloud;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heb.togglr.api.entities.SuperAdminsEntity;
import com.heb.togglr.api.repositories.SuperAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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

    @Autowired
    private SuperAdminRepository superAdminRepository;

    public RestAuthSuccessHandler(JwtService jwtService){
        this.jwtService = jwtService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        final String ROLE_PREFIX = "ROLE_";

        Optional<SuperAdminsEntity> superAdmin = superAdminRepository.findById(authentication.getName());
        List<GrantedAuthority> userRoles = new ArrayList<>();
        if (superAdmin.isPresent()) {
            userRoles.add(new SimpleGrantedAuthority(ROLE_PREFIX + "SUPERADMIN"));
        }

        for (GrantedAuthority grantedAuthority: userRoles) {
            System.out.println(grantedAuthority.getAuthority());
        }

        User userDetails = new User(authentication.getName(), "",  true, true, true, true, userRoles);

        String jwt = this.jwtService.generateToken(userDetails);

        Cookie cookie = new Cookie(this.tokenHeader, jwt);
        cookie.setDomain(this.cookieDomain);
        cookie.setPath("/");
        response.addCookie(cookie);

        response.setStatus(HttpServletResponse.SC_OK);
    }
}
