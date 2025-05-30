package com.example.usersubscriptionservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record UserSubscriptionDTO(
        Long id,
        String name,
        String serviceUrl,
        LocalDate startDate,
        LocalDate endDate,
        LocalDateTime createdAt
) {
}
