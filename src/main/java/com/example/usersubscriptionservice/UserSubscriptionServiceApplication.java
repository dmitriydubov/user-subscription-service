package com.example.usersubscriptionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class UserSubscriptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserSubscriptionServiceApplication.class, args);
    }

}
