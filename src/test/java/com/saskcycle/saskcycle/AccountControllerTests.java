package com.saskcycle.saskcycle;

import com.saskcycle.DAO.UserDAOInterface;
import com.saskcycle.controller.AccountController;
import com.saskcycle.repo.UserAccountRepo;
import com.saskcycle.model.Account;
import com.saskcycle.saskcycle.security.SecurityUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest(classes = SaskCycleApplication.class)
public class AccountControllerTests {
    @Autowired
    AccountController accountController;

    @Autowired
    UserAccountRepo userAccountRepo;

    @Autowired
    UserDAOInterface accountDataAccessObject;

    @Autowired
    private PasswordEncoder encoder;

    private Account testAccount;

    private Account testOrgAccount;

    @BeforeAll
    private void setAccounts(){
        // make the user account
        String encodedPass = encoder.encode("password");
        UserDetails userDetails = User.withUsername("Robin").password(encodedPass).roles("USER").build();
        testAccount = Account.makeAccountFromUser(userDetails, "test@gmail.com");
        // make the org account
        String encodedOrgPass = encoder.encode("password");
        UserDetails orgUserDetails = User.withUsername("Sarcan").password(encodedOrgPass).roles("USER", "ORG").build();
        testOrgAccount = Account.makeAccountFromUser(orgUserDetails, "testOrg@gmail.com");
    }

    @Test
    public void testRegister(){
        accountController.register("RobinTheEpicUser", "testemail@gmail.com", "password");
        Assertions.assertTrue(userAccountRepo.existsByEmail("test@gmail.com"));
        userAccountRepo.deleteAccountByUsername("RobinTheEpicUser");
    }

    @Test
    public void testRegisterOrg(){
        accountController.registerOrg("RobinTheEpicOrgUser", "testorgemail@gmail.com", "password");
        Assertions.assertTrue(userAccountRepo.existsByEmail("test@gmail.com"));
        userAccountRepo.deleteAccountByUsername("RobinTheEpicOrgUser");
    }

    @Test
    public void testAccountRegisterExistingAccountFails(){

    }
}





