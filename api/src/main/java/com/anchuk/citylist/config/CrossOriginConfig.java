package com.anchuk.citylist.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

@Configuration
@Slf4j
public class CrossOriginConfig {

    @Autowired
    private Environment env;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String frontendUrl = defaultIfBlank(env.getProperty("FRONTEND_URL"), "http://localhost:8080");
                log.warn("FRONTEND_URL=" + frontendUrl);
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins(frontendUrl);
            }
        };
    }

}
