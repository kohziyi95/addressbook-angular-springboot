package com.vttp.addressbookserver.configurations;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration 
public class AppConfig {

    private Logger logger = Logger.getLogger(AppConfig.class.getName());
    
    @Value("${cors.pathMapping}")
    String pathMapping;

    @Value("${cors.origins}")
    String origins;


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        logger.info("CORS.Mapping: " + pathMapping + ". CORS.Origins: " + origins);
        return new CorsConfig(pathMapping, origins);
    }
}