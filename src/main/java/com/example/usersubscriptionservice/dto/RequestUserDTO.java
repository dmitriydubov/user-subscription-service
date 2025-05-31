package com.example.usersubscriptionservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для запроса создания/обновления пользователя")
public record RequestUserDTO(

        @Schema(description = "Имя пользователя", example = "Иван")
        @NotBlank(message = "Имя обязательно")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String firstName,

        @Schema(description = "Фамилия пользователя", example = "Иванов")
        @NotBlank(message = "Фамилия обязательна")
        @Size(max = 50)
        String lastName,

        @Schema(description = "Email пользователя", example = "user@example.com")
        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный формат email")
        String email
) {
}
