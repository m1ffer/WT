package com.epam.rd.autotasks.config;

import com.epam.rd.autotasks.Employee;
import com.epam.rd.autotasks.Task;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private final static String DESCRIPTION = "New feature",
            ASSIGNEE_NAME = "John Doe",
            ASSIGNEE_POSITION = "Junior Software Engineer",
            REVIEWER_NAME = "Emily Brown",
            REVIEWER_POSITION = "Senior Software Engineer";

    @Bean
    public Task task(@Qualifier("assignee") Employee assignee,
                     @Qualifier("reviewer") Employee reviewer){
        return new Task(DESCRIPTION, assignee, reviewer);
    }

    @Bean("assignee")
    public Employee assignee(){
        return new Employee(ASSIGNEE_NAME, ASSIGNEE_POSITION);
    }

    @Bean("reviewer")
    public Employee reviewer(){
        return new Employee(REVIEWER_NAME, REVIEWER_POSITION);
    }
}
