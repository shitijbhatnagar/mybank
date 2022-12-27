package com.sb.mybank.web;

import com.sb.mybank.dto.ErrorDTO;
import com.sb.mybank.exception.TransactionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    //Exception handling defined only for few HTTP 400 & 404 scenarios, handling for other conditions to be
    //handled by Spring Boot (default) mechanism

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorDTO handlemethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.debug("GlobalExceptionHandler: MethodArgumentNotValidException - " + e.getMessage());
        log.info("GlobalExceptionHandler: MethodArgumentNotValidException - " + e.getMessage());

        List<String> invalidFields = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getField)
                .collect(Collectors.toList());
        return new ErrorDTO("Invalid or missing data for input field(s)", invalidFields);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErrorDTO handleConstraintViolation(ConstraintViolationException e) {
        log.debug("GlobalExceptionHandler: ConstraintViolationException - " + e.getMessage());
        log.info("GlobalExceptionHandler: ConstraintViolationException - " + e.getMessage());

        List<String> invalidFields = e.getConstraintViolations()
                .stream()
                .map(cv -> cv.getPropertyPath().toString())
                .collect(Collectors.toList());
        return new ErrorDTO("Constraint violated for input field(s)", invalidFields);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TransactionNotFoundException.class)
    public ErrorDTO handleNoTransactionFound(TransactionNotFoundException e) {
        log.debug("GlobalExceptionHandler: TransactionNotFoundException - " + e.getMessage());
        log.info("GlobalExceptionHandler: TransactionNotFoundException - " + e.getMessage());

        List<String> invalidFields = Arrays.asList("Transaction Id");
        return new ErrorDTO(e.getMessage(), invalidFields);
    }
}