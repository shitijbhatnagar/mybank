package com.sb.mybank.service;

import com.sb.mybank.dto.TransactionDTO;
import com.sb.mybank.model.Transaction;
import com.sb.mybank.repository.TransactionRepository;
import com.sb.mybank.util.MockDataProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service //Same purpose as @Component but just specific that its a service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private final TransactionRepository accountTransactionRepository;

    public TransactionServiceImpl(TransactionRepository accountTransactionRepository) {
        this.accountTransactionRepository = accountTransactionRepository;
    }

    public List<TransactionDTO> findAll() {
        log.debug("TransactionServiceImpl: findAll() ALERT: This is a DB operation");
        log.debug("TransactionServiceImpl: findAll() Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        List<TransactionDTO> transactionsDTOList = new CopyOnWriteArrayList<>();
        for (Transaction accountTransaction : accountTransactionRepository.findAll()) {
            transactionsDTOList.add(MockDataProvider.convertEntityToDTO(accountTransaction));
        }
        log.debug("TransactionServiceImpl: findAll() Transactions retrieved : " + transactionsDTOList.size());
        log.info("TransactionServiceImpl: findAll() Transactions retrieved : " + transactionsDTOList.size());
        return transactionsDTOList;
    }

    //Locate the transaction in the system using Id
    public TransactionDTO findTransactionById(String id){
        List<TransactionDTO> transactionsDTOList = new CopyOnWriteArrayList<>();
        Optional<Transaction> optionalTransaction = accountTransactionRepository.findById(id);

        if(optionalTransaction.isPresent())
        {
            log.debug("TransactionServiceImpl: findTransactionById() Transactions retrieved for id " + id);
            log.info("TransactionServiceImpl: findTransactionById() Transactions retrieved for id " + id);
            return MockDataProvider.convertEntityToDTO(optionalTransaction.get());
        }

        log.debug("Unable to locate requested transaction with id " + id);
        log.info("Unable to locate requested transaction with id " + id);
        return null;
    }

    @Transactional
    public TransactionDTO createInDB(TransactionDTO inputDTO) {
        log.debug("TransactionServiceImpl: createInDB() - ALERT: This is a DB operation");
        log.debug("TransactionServiceImpl: createInDB() - Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        //The values above are coming from a DTO only, so no need for any conversions
        log.debug("AccountTransactionDAO: createInDB() -Transaction created for user " + inputDTO.getUserId() + "and amount " + inputDTO.getAmount());

        Transaction transaction = MockDataProvider.convertDTOToEntity(inputDTO);
        accountTransactionRepository.save(transaction);
        log.debug("TransactionServiceImpl: createInDB() - New Transaction successfully created");

        //Assign the Id & timestamp value from Entity into the DTO object (as that is/might be missing at this point - unless set)
        inputDTO.setId(transaction.getId());
        inputDTO.setTimestamp(transaction.getTimestamp());
        log.info("AccountTransactionDAO: createInDB() - New Transaction successfully created");

        return inputDTO;
    }
}