package com.fuar.config.log;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    /*
    @Override
    public void addInterceptors (InterceptorRegistry registry) {
        registry.addInterceptor(new LoggingInterceptor());
        super.addInterceptors(registry);
    }
  */
    @Bean
    public WebMvcConfigurerAdapter adapter() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(getMyInterceptor());
            }
        };
    }

    @Bean
    public HandlerInterceptor getMyInterceptor(){
        return new LoggingInterceptor();
    }

}