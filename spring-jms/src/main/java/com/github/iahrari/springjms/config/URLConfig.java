package com.github.iahrari.springjms.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URLConfig {
    
    @Bean
    public String rootControllerPathPrefix(){
        return "/api/v1";
    }

    @Bean
    public String routerControllerPathPrefix(){
        return "/router";
    }

    @Bean
    public String secondControllerPathPrefix(){
        return "/second";
    }
}
