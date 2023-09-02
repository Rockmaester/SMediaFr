package com.lrb.smediafr.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@ComponentScan("com.lrb.smediafr")
public class SMConfig {
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
