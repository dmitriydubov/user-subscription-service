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
public class UserRemovingLoggingAspect {
    @Before(
        "execution(* com.example.usersubscriptionservice.service.UserService.deleteUser(..)) && " +
        "args(id)"
    )
    public void beforeUserRemoving(Long id) {
        log.info("Попытка удаления пользователя по id: {}", id);
    }

    @AfterReturning(
        pointcut = "execution(* com.example.usersubscriptionservice.service.UserService.deleteUser(..))"
    )
    public void afterUserRemoving() {
        log.info("Пользователь успешно удален");
    }

    @AfterThrowing(
        pointcut = "execution(* com.example..UserService.deleteUser(..))",
        throwing = "ex"
    )
    public void logFailedUserRemoving(UserNotFoundException ex) {
        log.warn("Попытка удаления пользователя: {}", ex.getMessage());
    }
}
