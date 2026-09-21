package edu.epam.fop.spring.injection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SubConfig.class)){
            Subscription sub = context.getBean(Subscription.class);
            System.out.println(sub.getPayment());
            System.out.println(sub.getPeriod());
            System.out.println(sub.getUser());
        }
    }
}
