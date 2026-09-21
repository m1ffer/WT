package com.epam.rd.autotasks.config;

import com.epam.rd.autotasks.Generator;
import com.epam.rd.autotasks.Singer;
import com.epam.rd.autotasks.Song;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.epam.rd.autotasks")

@RequiredArgsConstructor
public class AppConfig {
    private final Generator generator;

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public Singer singer(){
        return new Singer("Elton John");
    }

    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Song song(){
        String s = generator.generateString();
        return new Song(s);
    }
}
