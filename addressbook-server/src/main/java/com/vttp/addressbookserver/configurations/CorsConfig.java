package com.vttp.addressbookserver.configurations;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {

    private String path;
    private String origins;

    public CorsConfig(String p, String o) {
        this.path = p;
        this.origins = o;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping(path)
                .allowedOrigins(origins);
    }

}