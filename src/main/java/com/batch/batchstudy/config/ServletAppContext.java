package com.batch.batchstudy.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@ComponentScan("com.batch.batchstudy")
@Configuration
@EnableWebMvc
public class ServletAppContext implements WebMvcConfigurer {
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry){
        WebMvcConfigurer.super.configureViewResolvers(registry);
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        WebMvcConfigurer.super.addResourceHandlers(registry);
        registry.addResourceHandler("**").addResourceLocations("/resources");
    }
}
