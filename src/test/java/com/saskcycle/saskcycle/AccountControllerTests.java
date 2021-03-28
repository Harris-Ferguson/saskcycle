package com.saskcycle.saskcycle;

import com.saskcycle.DAO.UserDAOInterface;
import com.saskcycle.controller.AccountController;
import com.saskcycle.repo.UserAccountRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = SaskCycleApplication.class)
public class AccountControllerTests {
    @Autowired
    AccountController accountController;

    @Autowired
    UserAccountRepo userAccountRepo;

    @Autowired
    UserDAOInterface accountDataAccessObject;

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
        accountController.registerOrg("RobinTheEpicOrgUserExists!", "testorgemailexists@gmail.com", "password");
        Assertions.assertThrows(IllegalStateException.class, () -> accountController.registerOrg("RobinTheEpicOrgUserExists!", "testorgemailexists@gmail.com", "password"));
        userAccountRepo.deleteAccountByUsername("RobinTheEpicOrgUser");
    }

}





