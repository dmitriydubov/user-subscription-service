package com.example.usersubscriptionservice.dto;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(Long id,
        String firstName,
        String lastName,
        String email,
        LocalDateTime createdAt,
        List<SubscriptionDTO> subscriptions
) {
}
