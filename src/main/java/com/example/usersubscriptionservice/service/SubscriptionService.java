package com.example.usersubscriptionservice.service;

import com.example.usersubscriptionservice.dto.RequestSubscriptionDTO;
import com.example.usersubscriptionservice.dto.SubscriptionDTO;
import com.example.usersubscriptionservice.dto.TopSubscriptionResponse;

import java.util.List;

public interface SubscriptionService {

    SubscriptionDTO subscribe(Long id, RequestSubscriptionDTO request);

    List<SubscriptionDTO> getSubscriptions(Long id);

    void deleteSubscription(Long userId, Long subscriptionId);

    List<TopSubscriptionResponse> getTopSubscriptions();
}
