package com.sb.mybank.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler
{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handlemethodArgumentNotValid(MethodArgumentNotValidException e) {
        log.debug("GlobalExceptionHandler: MethodArgumentNotValidException - " + e.getMessage());
        log.info("GlobalExceptionHandler: MethodArgumentNotValidException - " + e.getMessage());
        return "Method Argument Not Valid  - " + e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolation(ConstraintViolationException e) {
        log.debug("GlobalExceptionHandler: ConstraintViolationException - " + e.getMessage());
        log.info("GlobalExceptionHandler: ConstraintViolationException - " + e.getMessage());
        return "Constraint Violated - " + e.getMessage();
    }
}