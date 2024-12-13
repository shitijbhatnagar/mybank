package com.sb.mybank.web;

import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.TransactionDTO;
import com.sb.mybank.exception.TransactionNotFoundException;
import com.sb.mybank.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class TransactionController
{
    TransactionService accountTransactionService;

    public TransactionController(TransactionService accountTransactionService)
    {
        this.accountTransactionService = accountTransactionService;
    }

    @GetMapping(APIEndPointsAndConstants.api_getCreateTransactions)
    @ResponseStatus(code = HttpStatus.OK)
    public List<TransactionDTO> getTransactions()
    {
        log.info("TransactionController: Controller end point to GET TRANSACTIONS");
        return accountTransactionService.findAll();
    }

    @GetMapping(APIEndPointsAndConstants.api_getCreateTransactions + "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public TransactionDTO getTransaction(@NotNull @Valid @PathVariable String id)
    {
        log.info("TransactionController: Controller end point to GET TRANSACTION for id " + id);
        TransactionDTO dto = accountTransactionService.findTransactionById(id);
        if(Optional.ofNullable(dto).isPresent())
            return dto;
        else {
            throw new TransactionNotFoundException("Transaction could not be located for requested id " + id);
        }

    }

    @PostMapping(APIEndPointsAndConstants.api_createTransaction)
    @ResponseStatus(code = HttpStatus.CREATED)
     public TransactionDTO createTransaction(@RequestBody @Valid TransactionDTO dto)
     {
         //Pass on amount and reference
         log.info("TransactionController: Controller end point to CREATE TRANSACTION");
         return accountTransactionService.createInDB(dto);
     }
}
