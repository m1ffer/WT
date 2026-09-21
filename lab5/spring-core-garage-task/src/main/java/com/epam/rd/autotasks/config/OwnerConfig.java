package com.epam.rd.autotasks.config;

import com.epam.rd.autotasks.Owner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OwnerConfig {
    public static final String name = "John Doe";
    public static final String taxNumber = "19671223-0000";
    @Bean
    public Owner owner(){
        return new Owner(name, taxNumber);
    }
}
