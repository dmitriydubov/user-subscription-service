package com.example.usersubscriptionservice.controller;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
import com.example.usersubscriptionservice.service.UserService;
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

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody RequestUserDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(request));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            @Min(value = 1, message = "id должен быть положительным числом")
            Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable
            @Min(value = 1, message = "id должен быть положительным числом")
            Long id
    ) {
        return ResponseEntity.ok(userService.getUserInfo(id));
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDTO> update(
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
