package com.example.externalconf;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component @Slf4j @RequiredArgsConstructor
public class EnvironmentCheck {

    private final Environment environment;

    @PostConstruct
    public void init() {
        String url = environment.getProperty("url");
        String username  = environment.getProperty("username");
        String password = environment.getProperty("password");
        log.info("env url : {}", url);
        log.info("env username : {}", username);
        log.info("env password : {}", password);
    }
}
