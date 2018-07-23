package com.example.es.config;

import com.example.es.ioc.Group;
import com.example.es.ioc.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Mario on 2018/7/16.
 */
@Configuration
public class IOCconfig {

    @Bean
    public User initUser(){
        return new User();
    }
    @Bean
    public Group initGroup(){
        return new Group();
    }

}
