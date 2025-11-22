package com.example.banquewsrest.config;

import com.example.banquewsrest.web.CompteJAXRSAPI;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    // Commenté pour tester Spring MVC
    @Bean
    public ResourceConfig resourceConfig() {
        ResourceConfig jerseyServlet = new ResourceConfig();
        jerseyServlet.register(CompteJAXRSAPI.class);
        return jerseyServlet;
    }
}