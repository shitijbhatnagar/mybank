package com.sb.mybank.repository;

import com.sb.mybank.MybankApplication;
import com.sb.mybank.config.ContainersEnv;
import com.sb.mybank.model.Referral;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

/*
 * Pre-requisites:
 * 1) Ensure Docker Service is running on your machine (if Windows, in Services)
 * 2) Ensure Docker Desktop is running (so you can see visually how a new container gets created and destroyed)
 * 3) Ensure you have a data-dev.sql script with some data inserted for the table/repository under test
 * 4) Modify the data in data-dev.sql to ensure that a single record is inserted and matched in assertEquals
 */

@ActiveProfiles("test")
@Profile("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MybankApplication.class)
public class ReferralRepositoryTCTest extends ContainersEnv
{
    @Autowired
    private ReferralRepository referralRepository;

    @Test
    public void tc_repo_get_referrals(){
        List<Referral> referrals = referralRepository.findAll();

        //data-dev.sql would be run and at least 1 record will be available in the database
        assertEquals(1, referrals.size());
    }
}