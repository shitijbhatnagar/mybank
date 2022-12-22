package com.sb.mybank.service;

import com.sb.mybank.dto.AccountTransactionDTO;
import com.sb.mybank.model.AccountTransaction;
import com.sb.mybank.repository.AccountTransactionRepository;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service //Same purpose as @Component but just specific that its a service
public class AccountTransactionServiceImpl implements AccountTransactionService {
    @Autowired
    private final AccountTransactionRepository accountTransactionRepository;

    public AccountTransactionServiceImpl(AccountTransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public List<AccountTransactionDTO> findAll() {
        log.debug("AccountTransactionServiceImpl: findAll() ALERT: This is a DB operation");
        log.debug("AccountTransactionServiceImpl: findAll() Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        List<AccountTransactionDTO> transactionsDTOList = new CopyOnWriteArrayList<>();
        for (AccountTransaction accountTransaction : accountTransactionRepository.findAll()) {
            transactionsDTOList.add(MockDataProvider.convertEntityToDTO(accountTransaction));
        }
        log.debug("AccountTransactionServiceImpl: findAll() Transactions retrieved : " + transactionsDTOList.size());
        log.info("AccountTransactionServiceImpl: findAll() Transactions retrieved : " + transactionsDTOList.size());
        return transactionsDTOList;
    }

    @Transactional
    public AccountTransactionDTO createInDB(AccountTransactionDTO inputDTO) {
        log.debug("AccountTransactionServiceImpl: createInDB() - ALERT: This is a DB operation");
        log.debug("AccountTransactionServiceImpl: createInDB() - Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        //The values above are coming from a DTO only, so no need for any conversions
        log.debug("AccountTransactionDAO: createInDB() -Transaction created for user " + inputDTO.getUserId() + "and amount " + inputDTO.getAmount());

        AccountTransaction transaction = MockDataProvider.convertDTOToEntity(inputDTO);
        accountTransactionRepository.save(transaction);
        log.debug("AccountTransactionServiceImpl: createInDB() - New Transaction successfully created");

        //Assign the Id value from Entity into the DTO object (as that is missing at this point - unless set)
        inputDTO.setId(transaction.getId());
        log.info("AccountTransactionDAO: createInDB() - New Transaction successfully created");

        return inputDTO;
    }
}