package com.example.usersubscriptionservice.aspect.logging;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
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
public class UserUpdatingLoggingAspect {
    @Before(
            value = "execution(* com.example.usersubscriptionservice.service.UserSubService.updateUser(..)) && " +
                    "args(id, requestUserDTO)",
            argNames = "id,requestUserDTO")
    public void beforeUserUpdating(Long id, RequestUserDTO requestUserDTO) {
        log.info("Попытка обновления пользователя id: {}, e-mail: {}", id, requestUserDTO.email());
    }

    @AfterReturning(
            pointcut = "execution(* com.example.usersubscriptionservice.service.UserSubService.updateUser(..))",
            returning = "result")
    public void afterUserUpdated(UserDTO result) {
        log.info("Данные пользователя id: {}, email: {} успешно обновлены", result.id(), result.email());
    }

    @AfterThrowing(
            pointcut = "execution(* com.example..UserSubService.updateUser(..))",
            throwing = "ex"
    )
    public void logFailedUpdating(UserNotFoundException ex) {
        log.warn("Попытка обновления пользователя: {}", ex.getMessage());
    }
}
