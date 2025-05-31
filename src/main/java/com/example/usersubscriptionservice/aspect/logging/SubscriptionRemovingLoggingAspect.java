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
public class SubscriptionRemovingLoggingAspect {
    @Before(
        value = "execution(* com.example.usersubscriptionservice.service.SubscriptionService.deleteSubscription(..)) && " +
                "args(userId, subscriptionId)",
        argNames = "userId,subscriptionId")
    public void beforeSubscriptionRemoving(Long userId, Long subscriptionId) {
        log.info("Попытка удаления подписки id: {} пользователя id: {}", subscriptionId, userId);
    }

    @AfterReturning(
        pointcut = "execution(* com.example.usersubscriptionservice.service.SubscriptionService.deleteSubscription(..))"
    )
    public void afterSubscriptionRemoving() {
        log.info("Подписка успешно удалена");
    }

    @AfterThrowing(
        pointcut = "execution(* com.example..SubscriptionService.deleteSubscription(..))",
        throwing = "ex"
    )
    public void logFailedSubscriptionRemoving(Exception ex) {
        log.warn("Попытка удаления подписки: {}", ex.getMessage());
    }
}
