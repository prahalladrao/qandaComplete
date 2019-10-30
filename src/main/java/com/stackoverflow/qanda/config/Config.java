package com.stackoverflow.qanda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.client.RestTemplate;


import java.time.LocalDateTime;
import java.util.Date;

@Configuration
public class Config {
    @Bean
    @Scope("prototype")
    public Date date()
    {
        return new Date(System.currentTimeMillis());
    }
    @Bean
    @Scope("prototype")
    public LocalDateTime localDateTime()
    {
        return LocalDateTime.now();
    }
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
