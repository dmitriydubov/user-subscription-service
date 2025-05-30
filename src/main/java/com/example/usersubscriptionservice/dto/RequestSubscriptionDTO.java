package com.example.usersubscriptionservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record RequestSubscriptionDTO(
        @NotBlank(message = "Имя сервиса обязательно")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String name,

        @NotBlank(message = "Ссылка на сервис обязательно")
        @Size(max = 50)
        String url,

        @NotNull(message = "Дата начала подписки обязательно")
        LocalDate startDate,

        @NotNull(message = "Дата завершения подписки обязательно")
        LocalDate endDate
) {
}
