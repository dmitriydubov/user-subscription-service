package com.example.usersubscriptionservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionDTO(
        Long id,
        String name,
        String serviceUrl,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt
) {
}
