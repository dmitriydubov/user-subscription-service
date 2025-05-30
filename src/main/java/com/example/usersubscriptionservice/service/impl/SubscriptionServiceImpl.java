package com.example.usersubscriptionservice.service.impl;

import com.example.usersubscriptionservice.dao.SubscriptionRepositoryService;
import com.example.usersubscriptionservice.dao.UserRepositoryService;
import com.example.usersubscriptionservice.dto.RequestSubscriptionDTO;
import com.example.usersubscriptionservice.dto.SubscriptionDTO;
import com.example.usersubscriptionservice.dto.TopSubscriptionResponse;
import com.example.usersubscriptionservice.error.SubscribeNotExistException;
import com.example.usersubscriptionservice.error.UserNotFoundException;
import com.example.usersubscriptionservice.mapper.SubscriptionMapper;
import com.example.usersubscriptionservice.model.Subscription;
import com.example.usersubscriptionservice.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepositoryService repositoryService;
    private final UserRepositoryService userRepositoryService;
    private final SubscriptionMapper mapper;
    private final SubscriptionRepositoryService subscriptionRepositoryService;

    @Override
    @Transactional
    public SubscriptionDTO subscribe(Long userId, RequestSubscriptionDTO request) {
        var subscription = subscriptionRepositoryService.getByUserIdAndServiceName(userId, request.name())
                .orElse(new Subscription());
        var user = userRepositoryService.getUser(userId).orElseThrow(() ->
                new UserNotFoundException("Пользователь с id " + userId + " не зарегистрирован")
        );
        subscription.setName(request.name());
        subscription.setServiceUrl(request.url());
        subscription.setStartDate(request.startDate());
        subscription.setEndDate(request.endDate());
        subscription.setUser(user);

        return mapper.toDto(repositoryService.save(subscription));
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionDTO> getSubscriptions(Long id) {
        var user = userRepositoryService.getUser(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("Пользователь с id " + id + " не зарегистрирован");
        }
        return repositoryService.getAllByUserId(id).stream().map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public void deleteSubscription(Long userId, Long subscriptionId) {
        userRepositoryService.getUser(userId).orElseThrow(() ->
            new UserNotFoundException("Пользователь с id " + userId + " не зарегистрирован")
        );
        boolean isExistingSubscribe =
                subscriptionRepositoryService.existBySubscriptionIdAndUser(subscriptionId, userId);
        if (!isExistingSubscribe) {
            throw new SubscribeNotExistException(
                    "Подписка id " + subscriptionId + " для пользователя id " + userId + " не существует"
            );
        }
        repositoryService.deleteByUserId(userId, subscriptionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TopSubscriptionResponse> getTopSubscriptions() {
        Pageable top3 = PageRequest.of(0, 3);
        List<Object[]> results = subscriptionRepositoryService.findTop3(top3);
        return results.stream()
            .map(result -> new TopSubscriptionResponse(
                (String) result[0],
                (long) result[1]
            ))
            .collect(Collectors.toList());
    }
}
