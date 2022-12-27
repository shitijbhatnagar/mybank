package com.sb.mybank.web;

import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.TransactionDTO;
import com.sb.mybank.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.List;

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
    public List<TransactionDTO> getTransactions()
    {
        log.info("TransactionController: Controller end point to GET TRANSACTIONS");
        return accountTransactionService.findAll();
    }

    @PostMapping(APIEndPointsAndConstants.api_createTransaction)
     public TransactionDTO createTransaction(@RequestBody @Valid TransactionDTO dto)
     {
         //Pass on amount and reference
         log.info("TransactionController: Controller end point to CREATE TRANSACTION");
         return accountTransactionService.createInDB(dto);
     }
}
