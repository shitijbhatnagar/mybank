package com.sb.mybank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class TransactionNotFoundException extends RuntimeException
{
    public TransactionNotFoundException()
    {
        super();
    }
    public TransactionNotFoundException(String message)
    {
        super(message);
    }
}