package com.example.usersubscriptionservice.aspect.logging;

import com.example.usersubscriptionservice.dto.RequestSubscriptionDTO;
import com.example.usersubscriptionservice.dto.SubscriptionDTO;
import com.example.usersubscriptionservice.error.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SubscriptionCreationLoggingAspect {
    @Before(
            value = "execution(* com.example..SubscriptionService.subscribe(..)) && " +
                    "args(userId, request)",
            argNames = "userId,request")
    public void beforeSubscriptionCreation(Long userId, RequestSubscriptionDTO request) {
        log.info("Попытка создания новой подписки: {} для пользователя id: {}", request.name(), userId);
    }

    @AfterReturning(
            pointcut = "execution(* com.example.usersubscriptionservice.service.SubscriptionService.subscribe(..))",
            returning = "result")
    public void afterSubscriptionCreation(SubscriptionDTO result) {
        log.info("Подписка id {} на {} успешно оформлена", result.id(), result.name());
    }

    @AfterThrowing(
            pointcut = "execution(* com.example..SubscriptionService.subscribe(..))",
            throwing = "ex"
    )
    public void logFailedCreation(UserNotFoundException ex) {
        log.warn("Не удалось оформить подписку: {}", ex.getMessage());
    }
}
