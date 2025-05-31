package com.example.usersubscriptionservice.dto;

public record TopSubscriptionResponse(
    String serviceName,
    Long count
) {
}
