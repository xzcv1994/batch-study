package com.batch.batchstudy.config;


import com.batch.batchstudy.filter.CommonFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.util.List;

public class WebMvcConfiguration implements WebApplicationInitializer {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new CommonFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.setName("CommonFilter");
        filterRegistrationBean.setOrder(1);

        return filterRegistrationBean;
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext servletAppContext = new AnnotationConfigWebApplicationContext();
        servletAppContext.register(ServletAppContext.class);

        AnnotationConfigWebApplicationContext rootAppContext = new AnnotationConfigWebApplicationContext();
        servletAppContext.register(RootAppContext.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(servletAppContext);
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");

        ContextLoaderListener listener = new ContextLoaderListener(rootAppContext);
        container.addListener(listener);
    }
}
