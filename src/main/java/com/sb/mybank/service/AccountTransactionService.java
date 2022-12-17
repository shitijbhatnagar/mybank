package com.sb.mybank.service;

import com.sb.mybank.model.AccountTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service //Same purpose as @Component but just specific that its a service
public class AccountTransactionService
{
    @Autowired
    AccountTransactionDAO accountTransactionDAO;

    public List<AccountTransactionDTO> findAll()
    {
        log.debug("AccountTransactionService: findAll() invoked");
        return accountTransactionDAO.findAll();
    }

    @Transactional
    public AccountTransactionDTO createInDB(AccountTransactionDTO inputDTO)
    {
        log.debug("AccountTransactionService: createInDB() invoked");
        return accountTransactionDAO.createInDB(inputDTO);
    }







// CODE COMMENTED BELOW FOR FUTURE USE

//    public AccountTransactionDTO create(AccountTransactionDTO inputDTO)
//    {
//        //The values above are coming from a DTO only, so no need for any conversions
//        ZonedDateTime timestamp = ZonedDateTime.now();
//        String usableUserId = "";
//        Optional<String> checkUserId = Optional.ofNullable(inputDTO.getUserId());
//        if(checkUserId.isPresent())
//        {
//            usableUserId = inputDTO.getUserId();
//        }
//        AccountTransaction transaction = new AccountTransaction(inputDTO.getId(), inputDTO.getAmount(), timestamp, bankSlogan + " - " + inputDTO.getReference(),usableUserId);
//        transactions.add(transaction);  //Similar to a DB save operation
//
//        System.out.println("New Transaction saved");
//
//        //Prepare the DTO to be returned (as few data values have changed e.g. reference)
//        AccountTransactionDTO outputDTO = new AccountTransactionDTO();
//        outputDTO.setId(transaction.getId());
//        outputDTO.setAmount(transaction.getAmount());
//        outputDTO.setTimestamp(transaction.getTimestamp());
//        outputDTO.setReference(transaction.getReference());
//        outputDTO.setUserId(transaction.getUserId());
//
//        return outputDTO;
//    }

//    //Convert the list of transactions to DTOs
//    private List<AccountTransactionDTO> findAllDTOs()
//    {
//        List<AccountTransactionDTO> transactionsDTO = new CopyOnWriteArrayList<>();
//
//        System.out.println("findAllDTOs: Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
//        log.info("findAllDTOs: Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
//
//        return (List<AccountTransactionDTO>) jdbcTemplate.query("select tx.id, tx.reference, tx.user_id, tx.amount, tx.datetime from t_transaction tx", (resultSet, rowNum) -> {
//            AccountTransactionDTO transaction = new AccountTransactionDTO();
//            transaction.setId(resultSet.getObject("id").toString());
//            transaction.setReference(resultSet.getString("reference"));
//            transaction.setUserId(resultSet.getString("user_id"));
//            transaction.setAmount(resultSet.getBigDecimal("amount"));
//            transaction.setTimestamp(resultSet.getTimestamp("datetime").toLocalDateTime().atZone(ZoneId.systemDefault()));
//            return transaction;
//        });
//
//
////        //Transpose Entity data into DTO
////        for(AccountTransaction transaction:transactions)
////        {
////            AccountTransactionDTO dto = new AccountTransactionDTO();
////            dto.setId(transaction.getId());
////            dto.setAmount(transaction.getAmount());
////            dto.setTimestamp(transaction.getTimestamp());
////            dto.setReference(transaction.getReference());
////            transactionsDTO.add(dto);
////        }
////        return transactionsDTO;
//    }

    //Find transactions for a particular user id only
//    private List<AccountTransactionDTO> getUserTransactions(String userId) throws Exception
//    {
//        //check User Id is present or not
//        Optional<String> checkUserId = Optional.ofNullable(userId);
//        if(!checkUserId.isPresent())
//        {
//            System.out.println("AccountTransactionService: findAllDTOsForUser -> userId is not present");
//            throw new Exception("User Id not provided to retrieve transactions");
//        }
//
//        List<AccountTransactionDTO> transactionsDTO = new CopyOnWriteArrayList<>();
//        List<AccountTransactionDTO> filteredTransactionsDTO = new CopyOnWriteArrayList<>();
//
//        //Transpose Entity data into DTO
//        for(AccountTransaction transaction:transactions)
//        {
//            AccountTransactionDTO dto = new AccountTransactionDTO();
//            dto.setId(transaction.getId());
//            dto.setAmount(transaction.getAmount());
//            dto.setTimestamp(transaction.getTimestamp());
//            dto.setReference(transaction.getReference());
//            dto.setUserId(transaction.getUserId()); //this line added late
//            transactionsDTO.add(dto);
//        }
//        System.out.println("Total Transactions transformed: " + transactionsDTO.size());
//
//        filteredTransactionsDTO = transactionsDTO.stream().filter(t->t.getUserId().equalsIgnoreCase(userId)).collect(Collectors.toList());
//
//        System.out.println("Total Transactions found for user: " + filteredTransactionsDTO.size());
//        return filteredTransactionsDTO;
//    }

//    @PostConstruct
//    public void init()
//    {
//        System.out.println("PostConstruct: Setting up seed data");
//        this.create(new BigDecimal(100.20),"Test Transaction 01");
//        this.create(new BigDecimal(120.50),"Test Transaction 02");
//    }
//
//    @PreDestroy
//    public void end()
//    {
//        System.out.println("PreDestroy: Removing data");
//    }

}

//    private List<AccountTransactionDTO> getUserTransactionsFromDB(String userId)
//    {
//        System.out.println("getUserTransactionsFromDB: Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
//        log.info("getUserTransactionsFromDB: Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());
//
//        return (List<AccountTransactionDTO>) jdbcTemplate.query("select tx.id, tx.reference, tx.user_id, tx.amount, tx.datetime from t_transaction tx where tx.user_id = '" + userId + "'", (resultSet, rowNum) -> {
//            AccountTransactionDTO transaction = new AccountTransactionDTO();
//            transaction.setId(resultSet.getObject("id").toString());
//            transaction.setReference(resultSet.getString("reference"));
//            transaction.setUserId(resultSet.getString("user_id"));
//            transaction.setAmount(resultSet.getBigDecimal("amount"));
//            transaction.setTimestamp(resultSet.getTimestamp("datetime").toLocalDateTime().atZone(ZoneId.systemDefault()));
//            return transaction;
//        });
//    }

//    public List<AccountTransactionDTO> findAllForUserFromDB(String userId) throws Exception {
//        return getUserTransactionsFromDB(userId);
//    }
//
//    //Filter method based on user id
//    public List<AccountTransactionDTO> findAllForUser(String userId) throws Exception {
//        return getUserTransactions(userId);
//    }