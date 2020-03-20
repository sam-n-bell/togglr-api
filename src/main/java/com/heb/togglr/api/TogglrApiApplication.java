package com.heb.togglr.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.heb.togglr"})
public class TogglrApiApplication {

    public static void main(String[] args) {
        System.out.println(System.getenv("SSO_URL"));
        SpringApplication.run(TogglrApiApplication.class, args);
    }
}
