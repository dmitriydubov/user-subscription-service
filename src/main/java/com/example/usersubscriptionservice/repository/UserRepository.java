package com.example.usersubscriptionservice.repository;

import com.example.usersubscriptionservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    void deleteUserById(Long id);
}
