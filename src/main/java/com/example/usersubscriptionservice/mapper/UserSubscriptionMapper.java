package com.example.usersubscriptionservice.mapper;

import com.example.usersubscriptionservice.dto.UserSubscriptionDTO;
import com.example.usersubscriptionservice.model.UserSubscription;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSubscriptionMapper {
    UserSubscriptionDTO toDto(UserSubscription userSubscription);
    UserSubscription toEntity(UserSubscriptionDTO userSubscriptionDTO);
}
