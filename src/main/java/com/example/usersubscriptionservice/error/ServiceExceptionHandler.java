package com.example.usersubscriptionservice.error;

import com.example.usersubscriptionservice.dto.ErrorResponseDTO;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(Exception exception) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> messages = new ArrayList<>(){{add(exception.getMessage());}};
        errors.put("messages", messages);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(createErrorResponse(HttpStatus.CONFLICT, errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, List<String>> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.groupingBy(
                FieldError::getField,
                Collectors.mapping(
                    FieldError::getDefaultMessage,
                    Collectors.toList()
                )
            ));
        log.warn("Ошибка валидации: {}",
            ex.getBindingResult().getFieldErrors().stream()
            .map(FieldError::getDefaultMessage)
            .collect(Collectors.joining(", "))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse(HttpStatus.BAD_REQUEST, errors));
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleException(UserNotFoundException exception) {
        Map<String, List<String>> errors = new HashMap<>();
        List<String> messages = new ArrayList<>(){{add(exception.getMessage());}};
        errors.put("messages", messages);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createErrorResponse(HttpStatus.NOT_FOUND, errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, List<String>> errors = ex.getConstraintViolations().stream()
            .collect(Collectors.groupingBy(
                violation -> "messages",
                Collectors.mapping(
                    ConstraintViolation::getMessage,
                    Collectors.toList()
                )
            ));

        log.warn("Ошибка валидации параметров: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse(HttpStatus.BAD_REQUEST, errors));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponseDTO> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, List<String>> errors = new HashMap<>();
        String message = String.format("Некорректный тип параметра '%s': требуется %s",
            "messages",
            ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "неизвестный тип");

        errors.put(ex.getName(), List.of(message));

        log.warn("Ошибка типа параметра: {}", message);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(createErrorResponse(HttpStatus.BAD_REQUEST, errors));
    }

    private ErrorResponseDTO createErrorResponse(HttpStatus status, Map<String, List<String>> errors) {
        return new ErrorResponseDTO(
                status.value(),
                errors,
                Instant.now()
        );
    }
}
