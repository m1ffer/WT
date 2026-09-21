package com.epam.rd.autotasks.config;

import com.epam.rd.autotasks.Car;
import com.epam.rd.autotasks.Owner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(OwnerConfig.class)
public class CarConfig {
    public static final String MODEL = "Tesla Model X";
    public static final String YEAR = "2022";
    @Bean
    public Car car(Owner owner){
        return new Car(MODEL, YEAR, owner);
    }
}
