package com.sb.mybank.repository;

import com.sb.mybank.config.ContainersEnv;
import com.sb.mybank.model.Referral;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

/*
 * Purpose: The purpose of this test case is to demonstrate the use of Test containers (as an alternative to on-prem)
 * The table t_referral is fairly simple (02 columns), schema can be found in schema-test.sql file under 'resources'
 * Pre-requisites:
 * 1) Ensure Docker Service & WSL Service, both, are running on your machine (if Windows, in Services)
 * 2) Ensure Docker Desktop is running (so you can see visually how a new container gets created and destroyed)
 * 3) Ensure you have a data-test.sql (+ any other) script with some data inserted for the repository under test
 * 4) Ensure right data in data-test.sql (+ any other) to ensure that inserted data gets matched in assertEquals
 * Note: This test case spans up a PostgreSQL test container, it does not use mocking
 */

@Slf4j
@ActiveProfiles("test")
@Profile("test")
@DataJpaTest
public class ReferralRepositoryTCTest extends ContainersEnv
{
    @Autowired
    private ReferralRepository referralRepository;

    @Test
    @SqlGroup({@Sql(value = "classpath:data-test-additional.sql", executionPhase = BEFORE_TEST_METHOD)})
    public void int_tc_repo_get_referrals(){
        List<Referral> referrals = referralRepository.findAll();

        //data-test.sql & data-test-additional.sql would be run, so 2 records will be available in the database
        assertEquals(2, referrals.size());
        log.debug("ReferralRepositoryTCTest: @int_tc_repo_get_referrals - Referral Repository test successfully executed");
        log.info("ReferralRepositoryTCTest: @int_tc_repo_get_referrals - Referral Repository test successfully executed");
    }
}