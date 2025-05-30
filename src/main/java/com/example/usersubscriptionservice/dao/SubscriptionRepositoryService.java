package com.example.usersubscriptionservice.dao;

import com.example.usersubscriptionservice.model.Subscription;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SubscriptionRepositoryService {
    Subscription save(Subscription subscription);

    List<Subscription> getAllByUserId(Long id);

    void deleteByUserId(Long userId, Long id);

    List<Object[]> findTop3(Pageable pageable);

    Optional<Subscription> getByUserIdAndServiceName(Long userId, String name);

    boolean existBySubscriptionIdAndUser(Long subscriptionId, Long userId);
}
