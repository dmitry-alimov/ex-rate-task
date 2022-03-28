package com.gd.alimov.client.config;

import com.gd.alimov.client.ConsumerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomerClientConfiguration {

    @Bean
    Logger logger() {
        return LoggerFactory.getLogger(ConsumerClient.class);
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    ConsumerClient client(RestTemplate restTemplate) {
        return new ConsumerClient(logger(), restTemplate);
    }
}
