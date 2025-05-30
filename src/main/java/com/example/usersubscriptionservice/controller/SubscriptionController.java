package com.example.usersubscriptionservice.controller;

import com.example.usersubscriptionservice.dto.RequestSubscriptionDTO;
import com.example.usersubscriptionservice.dto.SubscriptionDTO;
import com.example.usersubscriptionservice.dto.TopSubscriptionResponse;
import com.example.usersubscriptionservice.service.SubscriptionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Validated
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping("/users/{id}/subscriptions")
    public ResponseEntity<SubscriptionDTO> subscribe(
            @PathVariable("id")
            @Min(value = 1, message = "id должен быть положительным числом")
            Long id,
            @Valid
            @RequestBody RequestSubscriptionDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.subscribe(id, request));
    }

    @GetMapping("/users/{id}/subscriptions")
    public ResponseEntity<List<SubscriptionDTO>> getUserSubscriptions(
            @Min(value = 1, message = "id должен быть положительным числом")
            @PathVariable
            Long id
    ) {
        return ResponseEntity.ok().body(subscriptionService.getSubscriptions(id));
    }

    @DeleteMapping("/users/{id}/subscriptions/{sub_id}")
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable("id")
            @Min(value = 1, message = "id должен быть положительным числом")
            Long userId,
            @PathVariable("sub_id")
            @Min(value = 1, message = "id должен быть положительным числом")
            Long subscriptionId
    ) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<TopSubscriptionResponse>> getTopSubscriptions() {
        return ResponseEntity.ok().body(subscriptionService.getTopSubscriptions());
    }
}
