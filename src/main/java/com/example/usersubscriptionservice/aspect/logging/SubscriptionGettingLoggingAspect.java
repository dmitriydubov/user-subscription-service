package com.example.usersubscriptionservice.aspect.logging;

import com.example.usersubscriptionservice.error.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SubscriptionGettingLoggingAspect {
    @Before(
        "execution(* com.example.usersubscriptionservice.service.SubscriptionService.getSubscriptions(..)) && " +
        "args(id)"
    )
    public void beforeUserGetting(Long id) {
        log.info("Попытка получения подписок пользователя с id: {}", id);
    }

    @AfterReturning(
        pointcut = "execution(* com.example.usersubscriptionservice.service.SubscriptionService.getSubscriptions(..))"
    )
    public void afterSubscriptionGetting() {
        log.info("Подписки успешно получены");
    }

    @AfterThrowing(
        pointcut = "execution(* com.example..SubscriptionService.getSubscriptions(..))",
        throwing = "ex"
    )
    public void logFailedSubscriptionGetting(UserNotFoundException ex) {
        log.warn("Попытка получения пользователя: {}", ex.getMessage());
    }
}
