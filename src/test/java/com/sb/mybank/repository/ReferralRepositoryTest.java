package com.sb.mybank.repository;

import com.sb.mybank.MybankApplication;
import com.sb.mybank.config.ContainersEnv;
import com.sb.mybank.model.Referral;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

//@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = MybankApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReferralRepositoryTest extends ContainersEnv
{
    @Autowired
    private ReferralRepository referralRepository;

    @Test
    public void tc_repo_get_referrals(){
        List<Referral> referrals = referralRepository.findAll();
        assertEquals(0, referrals.size());
    }
}