package com.flexsolution.chyzhov.kmb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.validation.Validator;

public class WebConfig implements WebMvcConfigurer {

    @Bean
    public Validator validator() {
        return new LocalValidatorFactoryBean();
    }

}
