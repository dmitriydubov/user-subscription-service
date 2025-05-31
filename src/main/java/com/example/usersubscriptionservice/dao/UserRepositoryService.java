package com.example.usersubscriptionservice.dao;

import com.example.usersubscriptionservice.model.User;

import java.util.Optional;

public interface UserRepositoryService {

    User save(User user);

    boolean existsByEmail(String email);

    void deleteUser(Long id);

    boolean existsById(Long id);

    Optional<User> getUser(Long id);
}
