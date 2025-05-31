package com.example.usersubscriptionservice.repository;

import com.example.usersubscriptionservice.model.Subscription;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findAllByUserId(Long userId);

    void deleteSubscriptionByUserIdAndId(Long userId, Long id);

    @Query("SELECT s.name AS serviceName, COUNT(s) AS subscriptionCount " +
            "FROM Subscription s " +
            "GROUP BY s.name " +
            "ORDER BY COUNT(s) DESC")
    List<Object[]> findTop3Subscription(Pageable pageable);

    Optional<Subscription> findByUserIdAndName(Long userId, String name);

    boolean existsByIdAndUserId(Long id, Long userId);
}
