package com.example.usersubscriptionservice;

import org.springframework.boot.SpringApplication;

public class TestUserSubscriptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.from(UserSubscriptionServiceApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
