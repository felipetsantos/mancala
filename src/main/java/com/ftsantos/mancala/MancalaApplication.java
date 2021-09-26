package com.ftsantos.mancala;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class MancalaApplication {
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(MancalaApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String originsProperty = env.getProperty("mancala.cors.allowed-origins");
                String[] origins = originsProperty != null ? originsProperty.split(",") : new String[]{};
                CorsRegistration cors = registry.addMapping("/api/**")
                        .allowedMethods("GET", "POST", "PUT", "DELETE");
                Arrays.stream(origins)
                        .filter( origin -> origin != null)
                        .filter(origin -> !origin.trim().isEmpty())
                        .forEach( origin -> {
                    cors.allowedOrigins(origin);
                });
            }
        };
    }

}
