package com.example.usersubscriptionservice.validator;

import com.example.usersubscriptionservice.annotation.ValidDateRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, Object> {

    @Override
    public void initialize(ValidDateRange constraintAnnotation) {}

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        try {
            LocalDate start = (LocalDate) value.getClass().getMethod("startDate").invoke(value);
            LocalDate end = (LocalDate) value.getClass().getMethod("endDate").invoke(value);

            if (start == null && end == null) {
                return true;
            }
            if (start == null || end == null) {
                return false;
            }

            boolean isValid = !start.isAfter(end);

            if (!isValid) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                        .addPropertyNode("endDate")
                        .addConstraintViolation();
            }

            return isValid;
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalArgumentException("Объект должен иметь методы startDate() и endDate()", e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Ошибка при вызове startDate()/endDate()", e);
        }
    }
}
