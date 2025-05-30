package com.example.usersubscriptionservice.aspect.logging;

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
public class UserGettingLoggingAspect {
    @Before(
            "execution(* com.example.usersubscriptionservice.service.UserService.getUserInfo(..)) && " +
            "args(id)"
    )
    public void beforeUserGetting(Long id) {
        log.info("Попытка получения пользователя по id: {}", id);
    }

    @AfterReturning(pointcut = "execution(* com.example.usersubscriptionservice.service.UserService.getUserInfo(..))")
    public void afterUserGetting() {
        log.info("Данные пользователя успешно получены");
    }

    @AfterThrowing(
            pointcut = "execution(* com.example..UserSubService.getUserInfo(..))",
            throwing = "ex"
    )
    public void logFailedUserGetting(UserNotFoundException ex) {
        log.warn("Попытка получения пользователя: {}", ex.getMessage());
    }
}
