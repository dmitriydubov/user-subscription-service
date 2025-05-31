package com.example.usersubscriptionservice.controller;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
import com.example.usersubscriptionservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создание пользователя")
    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody RequestUserDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @Operation(summary = "Удаление пользователя")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "ID пользователя", required = true)
        @PathVariable
        @Min(value = 1, message = "id должен быть положительным числом")
        Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получение информации о пользователе")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(
        @Parameter(description = "ID пользователя", required = true)
        @PathVariable
        @Min(value = 1, message = "id должен быть положительным числом")
        Long id
    ) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @Operation(summary = "Обновление данных пользователя")
    @PutMapping("users/{id}")
    public ResponseEntity<UserDTO> update(
        @Parameter(description = "ID пользователя", required = true)
        @PathVariable
        @Min(value = 1, message = "id должен быть положительным числом")
        Long id,
        @Valid
        @RequestBody
        RequestUserDTO request
    ) {
        return ResponseEntity.ok(userService.updateUser(id, request));
    }
}
