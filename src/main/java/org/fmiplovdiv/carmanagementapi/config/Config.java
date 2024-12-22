package org.fmiplovdiv.carmanagementapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class Config implements WebMvcConfigurer {


    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**") // Позволява всички ендпойнти
                .allowedOrigins("http://localhost:3000") // Разрешава заявките от frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Разрешава изброените методи
                .allowedHeaders("*") // Позволява всички хедъри
                .allowCredentials(true); // Позволява изпращането на креденшъли (cookies)
    }



}
