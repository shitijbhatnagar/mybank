package com.sb.mybank.web;

import com.sb.mybank.constants.APIEndPointsAndConstants;
import com.sb.mybank.dto.AccountTransactionDTO;
import com.sb.mybank.service.AccountTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
public class AccountTransactionController
{
    AccountTransactionService accountTransactionService;

    public AccountTransactionController(AccountTransactionService accountTransactionService)
    {
        this.accountTransactionService = accountTransactionService;
    }

    @GetMapping(APIEndPointsAndConstants.api_getCreateTransactions)
    public List<AccountTransactionDTO> getTransactions()
    {
        log.info("AccountTransactionController: Controller end point to GET TRANSACTIONS");
        return accountTransactionService.findAll();
    }

    @PostMapping(APIEndPointsAndConstants.api_createTransaction)
     public AccountTransactionDTO createTransaction(@RequestBody @Valid AccountTransactionDTO dto)
     {
         //Pass on amount and reference
         log.info("AccountTransactionController: Controller end point to CREATE TRANSACTION");
         return accountTransactionService.createInDB(dto);
     }
}
