package com.saskcycle.saskcycle;

import com.saskcycle.controller.AccountController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

@SpringBootTest(classes = SaskCycleApplication.class)
public class AccountControllerTests {
    @Autowired
    AccountController accountController;

    @Test
    public void testAccount(){
        Assertions.assertEquals(1, 1);
    }
}





