package com.sb.mybank.repository;

import com.sb.mybank.model.Referral;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface ReferralRepository extends JpaRepository<Referral, String>
{
    //Standard implementation available in JpaRepository interface
}