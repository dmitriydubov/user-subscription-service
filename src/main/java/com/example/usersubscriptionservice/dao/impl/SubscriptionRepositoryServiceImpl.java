package com.example.usersubscriptionservice.dao.impl;

import com.example.usersubscriptionservice.dao.SubscriptionRepositoryService;
import com.example.usersubscriptionservice.model.Subscription;
import com.example.usersubscriptionservice.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SubscriptionRepositoryServiceImpl implements SubscriptionRepositoryService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public Subscription save(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    @Override
    public List<Subscription> getAllByUserId(Long id) {
        return subscriptionRepository.findAllByUserId(id);
    }

    @Override
    public void deleteByUserId(Long userId, Long id) {
        subscriptionRepository.deleteSubscriptionByUserIdAndId(userId, id);
    }

    @Override
    public List<Object[]> findTop3(Pageable pageable) {
        return subscriptionRepository.findTop3Subscription(pageable);
    }

    @Override
    public Optional<Subscription> getByUserIdAndServiceName(Long userId, String serviceName) {
        return subscriptionRepository.findByUserIdAndName(userId, serviceName);
    }

    @Override
    public boolean existBySubscriptionIdAndUser(Long subscriptionId, Long userId) {
        return subscriptionRepository.existsByIdAndUserId(subscriptionId, userId);
    }
}
