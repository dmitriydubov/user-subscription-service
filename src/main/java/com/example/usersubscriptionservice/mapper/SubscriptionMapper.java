package com.example.usersubscriptionservice.mapper;

import com.example.usersubscriptionservice.dto.SubscriptionDTO;
import com.example.usersubscriptionservice.model.Subscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SubscriptionMapper {
    SubscriptionDTO toDto(Subscription subscription);
    Subscription toEntity(SubscriptionDTO subscriptionDTO);
}
