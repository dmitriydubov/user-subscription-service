package com.example.usersubscriptionservice.controller;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
import com.example.usersubscriptionservice.service.UserSubService;
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
public class UserSubController {

    private final UserSubService userSubService;

    @PostMapping("/users")
    public ResponseEntity<UserDTO> create(@Valid @RequestBody RequestUserDTO requestUserDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userSubService.createUser(requestUserDTO));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable
            @Min(value = 1, message = "id должен быть положительным числом")
            Long id
    ) {
        userSubService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable
            @Min(value = 1, message = "id должен быть положительным числом")
            Long id
    ) {
        return ResponseEntity.ok(userSubService.getUserInfo(id));
    }

    @PutMapping("users/{id}")
    public ResponseEntity<UserDTO> update(
            @PathVariable
            @Min(value = 1, message = "id должен быть положительным числом")
            Long id,
            @Valid @RequestBody RequestUserDTO requestUserDTO
    ) {
        return ResponseEntity.ok(userSubService.updateUser(id, requestUserDTO));
    }
}
