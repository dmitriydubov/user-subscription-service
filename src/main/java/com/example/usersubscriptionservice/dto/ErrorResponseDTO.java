package com.example.usersubscriptionservice.dto;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public record ErrorResponseDTO(
    int status,
    Map<String, List<String>> errors,
    Instant timestamp
) {
}
