package com.example.usersubscriptionservice.aspect.logging;

import com.example.usersubscriptionservice.dto.RequestUserDTO;
import com.example.usersubscriptionservice.dto.UserDTO;
import com.example.usersubscriptionservice.error.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class UserCreationLoggingAspect {

    @Before(
        "execution(* com.example.usersubscriptionservice.service.UserService.createUser(..)) && " +
        "args(requestUserDTO)"
    )
    public void beforeUserCreation(RequestUserDTO requestUserDTO) {
        log.info("Попытка создания нового пользователя по e-mail: {}", requestUserDTO.email());
    }

    @AfterReturning(
        pointcut = "execution(* com.example.usersubscriptionservice.service.UserService.createUser(..))",
        returning = "result")
    public void afterUserCreated(UserDTO result) {
        log.info("Новый пользователь успешно создан id: {}, email: {}", result.id(), result.email());
    }

    @AfterThrowing(
        pointcut = "execution(* com.example..UserService.createUser(..))",
        throwing = "ex"
    )
    public void logFailedCreation(UserAlreadyExistsException ex) {
        log.warn("Попытка создания пользователя с существующим email: {}", ex.getMessage());
    }
}
