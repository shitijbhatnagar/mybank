package com.sb.mybank.remote.service;


import com.sb.mybank.model.objects.TransactionDTO;
import java.util.List;

public interface RemoteTransactionAppService {
    public List<TransactionDTO> retrieveTransactions() throws Exception;

    public TransactionDTO retrieveTransaction(String id) throws Exception;

    public TransactionDTO createTransaction(TransactionDTO data) throws Exception;
}