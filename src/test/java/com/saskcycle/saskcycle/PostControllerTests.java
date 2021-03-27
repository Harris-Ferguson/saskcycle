package com.saskcycle.saskcycle;

import com.saskcycle.controller.PostController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

@SpringBootTest(classes = SaskCycleApplication.class)
public class PostControllerTests {

    @Autowired
    PostController PC;

    @Test
    void Test(){
    }


}
