package com.sb.mybank.service;

import com.sb.mybank.model.objects.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.ZonedDateTime;

@Slf4j
@Service
@Profile("dev") //this bean will only be created when the profile being used is "dev"
public class SeedTransactionDataLoader {
    private final TransactionService transactionService;

    public SeedTransactionDataLoader(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostConstruct
    public void setup()
    {
        log.debug("SeedTransactionDataLoader: @setup() Creating dev transactions... Started");
        TransactionDTO transactionDTO1 = new TransactionDTO(), transactionDTO2 = new TransactionDTO(), transactionDTO3 = new TransactionDTO();
        transactionDTO1.setUserId("someUserId4");
        transactionDTO1.setTimestamp(ZonedDateTime.now());
        transactionDTO1.setReference("sample 4");
        transactionDTO1.setAmount(BigDecimal.valueOf(25));
        transactionService.createInDB(transactionDTO1);

        transactionDTO2.setUserId("someUserId5");
        transactionDTO2.setTimestamp(ZonedDateTime.now());
        transactionDTO2.setReference("sample 5");
        transactionDTO2.setAmount(BigDecimal.valueOf(26));
        transactionService.createInDB(transactionDTO2);

        transactionDTO3.setUserId("someUserId6");
        transactionDTO3.setTimestamp(ZonedDateTime.now());
        transactionDTO3.setReference("sample 6");
        transactionDTO3.setAmount(BigDecimal.valueOf(27));
        transactionService.createInDB(transactionDTO3);

        log.debug("SeedTransactionDataLoader: @setup Creating dev transactions... Completed");
    }
}