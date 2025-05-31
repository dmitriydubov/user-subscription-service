package com.example.usersubscriptionservice;

import com.example.usersubscriptionservice.dao.SubscriptionRepositoryService;
import com.example.usersubscriptionservice.dao.UserRepositoryService;
import com.example.usersubscriptionservice.model.Subscription;
import com.example.usersubscriptionservice.model.User;
import com.example.usersubscriptionservice.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
class SubscriptionServiceIntegrationTest {

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private SubscriptionRepositoryService subscriptionRepositoryService;

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:17-alpine"));
    }

    @Test
    void testSubscriptionSavingAndGetting() {
        var user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.ru");
        User savedUser = userRepositoryService.save(user);

        assertTrue(userRepositoryService.existsById(user.getId()));

        var subscription = new Subscription();
        subscription.setUser(savedUser);
        subscription.setName("test subscription");
        subscription.setServiceUrl("http://localhost:8080");
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusDays(1));
        subscriptionRepositoryService.save(subscription);

        assertFalse(subscriptionRepositoryService.getAllByUserId(savedUser.getId()).isEmpty());
    }
}
