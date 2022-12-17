package com.sb.mybank.service;

import com.sb.mybank.model.AccountTransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class AccountTransactionDAO
{
    //For JDBC operations
    private final JdbcTemplate jdbcTemplate;
    private String bankSlogan = "";

    public AccountTransactionDAO(JdbcTemplate jdbcTemplate, @Value("${bank.slogan}") String bankSlogan)
    {
        this.bankSlogan = bankSlogan;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<AccountTransactionDTO> findAll()
    {
        log.debug("AccountTransactionDAO: findAll() - ALERT: This is a DB operation");
        log.debug("AccountTransactionDAO: findAll() - Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        List<AccountTransactionDTO> transactionsDTO = new CopyOnWriteArrayList<>();

        return (List<AccountTransactionDTO>) jdbcTemplate.query("select tx.id, tx.reference, tx.user_id, tx.amount, tx.datetime from t_transaction tx", (resultSet, rowNum) -> {
            AccountTransactionDTO transaction = new AccountTransactionDTO();
            transaction.setId(resultSet.getObject("id").toString());
            transaction.setReference(resultSet.getString("reference"));
            transaction.setUserId(resultSet.getString("user_id"));
            transaction.setAmount(resultSet.getBigDecimal("amount"));
            transaction.setTimestamp(resultSet.getTimestamp("datetime").toLocalDateTime().atZone(ZoneId.systemDefault()));
            return transaction;
        });
    }

    @Transactional
    public AccountTransactionDTO createInDB(AccountTransactionDTO inputDTO)
    {
        log.debug("AccountTransactionDAO: createInDB() - ALERT: This is a DB operation");
        log.debug("AccountTransactionDAO: createInDB() - Is a database transaction open? = " + TransactionSynchronizationManager.isActualTransactionActive());

        //The values above are coming from a DTO only, so no need for any conversions
        ZonedDateTime timestamp = ZonedDateTime.now();
        String usableUserId;
        Optional<String> checkUserId = Optional.ofNullable(inputDTO.getUserId());
        if(checkUserId.isPresent())
        {
            usableUserId = inputDTO.getUserId();
        }
        else
        {
            //Although we should never have this situation happening
            usableUserId = "anomymous";
        }

        KeyHolder keyHolder = new GeneratedKeyHolder();

         /*
            JDBC Code to create a new account transaction - below (can be replaced by JPA easily)
         */
        log.debug("AccountTransactionDAO: createInDB() -Creating transaction for user " + inputDTO.getUserId() + "and amount " + inputDTO.getAmount() + "and date time as " + inputDTO.getTimestamp());
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement("insert into t_transaction (reference, user_id, amount, datetime) values (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, inputDTO.getReference());
            ps.setString(2, usableUserId);
            ps.setBigDecimal(3, inputDTO.getAmount());
            ps.setTimestamp(4, Timestamp.valueOf(inputDTO.getTimestamp().toLocalDateTime()));
            return ps;
        }, keyHolder);

        log.debug("AccountTransactionDAO: createInDB() -Transaction created for user " + inputDTO.getUserId() + "and amount " + inputDTO.getAmount());

        String uuid = !keyHolder.getKeys().isEmpty() ? ((UUID) keyHolder.getKeys().values().iterator().next()).toString(): null;
        log.debug("AccountTransactionDAO: createInDB() -UUID of new transaction created is " + uuid);

        AccountTransactionDTO transactionDTO = new AccountTransactionDTO();
        transactionDTO.setId(uuid);
        transactionDTO.setReference(inputDTO.getReference());
        transactionDTO.setUserId(inputDTO.getUserId());
        transactionDTO.setAmount(inputDTO.getAmount());
        transactionDTO.setTimestamp(inputDTO.getTimestamp());
        log.info("AccountTransactionDAO: createInDB() - New Transaction successfully created");

        return transactionDTO;
    }
}