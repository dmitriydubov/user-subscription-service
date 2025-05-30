package com.example.usersubscriptionservice;

import com.example.usersubscriptionservice.dao.UserRepositoryService;
import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.error.UserNotFoundException;
import com.example.usersubscriptionservice.model.User;
import com.example.usersubscriptionservice.service.UserSubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
class UserSubscriptionServiceIntegrationTest {

    @Autowired
    private UserSubService userSubService;

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:17-alpine"));
    }

    @Test
    void testUserSavingAndDeletion() {
        var user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.ru");
        User savedUser = userRepositoryService.save(user);

        assertTrue(userRepositoryService.existsById(user.getId()));

        userSubService.deleteUser(savedUser.getId());

        assertFalse(userRepositoryService.existsById(savedUser.getId()));
    }

    @Test
    void testNonExistentUserDeletion() {
        Long nonExistingId = 999L;

        assertThrows(UserNotFoundException.class, () -> {
            userSubService.deleteUser(nonExistingId);
        }, "UserNotFoundException");
    }

    @Test
    void testUserUpdating() {
        var user = new User();
        user.setFirstName("Test");
        user.setLastName("User");
        user.setEmail("test@test.ru");
        User savedUser = userRepositoryService.save(user);

        assertTrue(userRepositoryService.existsById(savedUser.getId()));

        RequestUserDTO updateRequest = new RequestUserDTO(
                "Test2",
                "User2",
                "test2@test.ru"
        );

        userSubService.updateUser(savedUser.getId(), updateRequest);

        assertTrue(userRepositoryService.existsById(savedUser.getId()));

        User updatedUser = userRepositoryService.getUser(savedUser.getId())
                .orElseThrow(() -> new AssertionError("Пользователь не найден после обновления"));

        assertEquals(updateRequest.firstName(), updatedUser.getFirstName());
        assertEquals(updateRequest.lastName(), updatedUser.getLastName());
        assertEquals(updateRequest.email().toLowerCase().trim(), updatedUser.getEmail());
    }
}
