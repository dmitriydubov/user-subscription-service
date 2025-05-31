package com.example.usersubscriptionservice.aspect.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class SubscriptionGetTop3LoggingAspect {

    @Before(
        value = "execution(* com.example.usersubscriptionservice.service.SubscriptionService.getTopSubscriptions(..))"
    )
    public void beforeSubscriptionTop3Getting() {
        log.info("Попытка получения топ 3 подписок");
    }

    @AfterReturning(
        pointcut = "execution(* com.example.usersubscriptionservice.service.SubscriptionService.getTopSubscriptions(..))"
    )
    public void afterSubscriptionTop3Getting() {
        log.info("Топ 3 подписки успешно получены");
    }

    @AfterThrowing(
        pointcut = "execution(* com.example..SubscriptionService.deleteSubscription(..))",
        throwing = "ex"
    )
    public void logFailedSubscriptionTop3Getting(Exception ex) {
        log.warn("Попытка получения топ 3 подписок: {}", ex.getMessage());
    }
}
