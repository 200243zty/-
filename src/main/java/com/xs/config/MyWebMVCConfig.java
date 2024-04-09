package com.xs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMVCConfig implements WebMvcConfigurer {

//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/avatar/**").addResourceLocations("file:D:/upload/avatar/");
//        registry.addResourceHandler("/voice/**").addResourceLocations("file:D:/upload/voice/");
//        registry.addResourceHandler("/userAvatar/**").addResourceLocations("file:D:/upload/userAvatar/");
//    }

}
