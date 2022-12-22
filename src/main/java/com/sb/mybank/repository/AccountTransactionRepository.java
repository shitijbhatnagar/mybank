package com.sb.mybank.repository;

import com.sb.mybank.model.AccountTransaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface AccountTransactionRepository extends CrudRepository<AccountTransaction, String>
{
    //Standard implementation available in CrudRepository interface
}
