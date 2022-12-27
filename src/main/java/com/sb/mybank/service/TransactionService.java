package com.sb.mybank.service;

import com.sb.mybank.dto.TransactionDTO;
import java.util.List;

public interface TransactionService
{
    public List<TransactionDTO> findAll();

    public TransactionDTO findTransactionById(String id);

    public TransactionDTO createInDB(TransactionDTO inputDTO);

}
