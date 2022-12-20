package com.sb.mybank.service;

import com.sb.mybank.dto.AccountTransactionDTO;

import java.util.List;

public interface AccountTransactionService
{
    public List<AccountTransactionDTO> findAll();

    public AccountTransactionDTO createInDB(AccountTransactionDTO inputDTO);
}
