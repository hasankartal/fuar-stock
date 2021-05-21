package com.fuar;

import com.fuar.config.LoggingInterceptor;
import com.fuar.service.log.LogRestIncomingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.*;

import java.util.List;

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