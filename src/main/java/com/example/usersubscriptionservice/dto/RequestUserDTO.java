package com.example.usersubscriptionservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestUserDTO(
        @NotBlank(message = "Имя обязательно")
        @Size(min = 2, max = 50, message = "Имя должно быть от 2 до 50 символов")
        String firstName,

        @NotBlank(message = "Фамилия обязательна")
        @Size(max = 50)
        String lastName,

        @NotBlank(message = "Email не может быть пустым")
        @Email(message = "Некорректный формат email")
        String email
) {
}
