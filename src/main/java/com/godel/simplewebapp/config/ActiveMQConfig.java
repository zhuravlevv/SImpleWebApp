package com.godel.simplewebapp.config;

import com.godel.simplewebapp.dto.Employee;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;

import java.util.Collections;

@EnableJms
@Configuration
public class ActiveMQConfig {

    @Bean
    public MappingJackson2MessageConverter messageConverter(){
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTypeIdPropertyName("content-type");
        converter.setTypeIdMappings(Collections.singletonMap("employee", Employee.class));

        return converter;
    }
}
