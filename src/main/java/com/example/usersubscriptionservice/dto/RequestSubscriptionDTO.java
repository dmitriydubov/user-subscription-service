package com.example.usersubscriptionservice.dto;

import com.example.usersubscriptionservice.annotation.ValidDateRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Schema(description = "DTO для запроса создания/обновления подписок пользователя")
@ValidDateRange
public record RequestSubscriptionDTO(

        @Schema(description = "Названия сервиса", example = "Netflix")
        @NotBlank(message = "Имя сервиса обязательно")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String name,

        @Schema(description = "Адрес сервиса", example = "https://netflix.com")
        @NotBlank(message = "Ссылка на сервис обязательно")
        @Size(max = 50)
        String url,

        @Schema(description = "Дата начала подписки", example = "2025-05-31")
        @NotNull(message = "Дата начала подписки обязательно")
        LocalDate startDate,

        @Schema(description = "Дата начала подписки", example = "2025-06-30")
        @NotNull(message = "Дата завершения подписки обязательно")
        LocalDate endDate
) {
}
