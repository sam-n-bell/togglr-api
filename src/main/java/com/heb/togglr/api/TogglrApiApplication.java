package com.heb.togglr.api;

import com.heb.togglr.api.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


//@EnableResourceServer
@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
@ComponentScan(basePackages = {"com.heb.togglr"})
public class TogglrApiApplication {

    public static void main(String[] args) {
        System.out.println(System.getenv("SSO_URL"));
        SpringApplication.run(TogglrApiApplication.class, args);
    }
}
