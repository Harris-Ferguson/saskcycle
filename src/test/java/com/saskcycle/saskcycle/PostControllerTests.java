package com.saskcycle.saskcycle;

import com.saskcycle.DAO.PostsDAO;
import com.saskcycle.controller.PostController;
import com.saskcycle.model.Account;
import com.saskcycle.model.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.util.Date;

@SpringBootTest(classes = SaskCycleApplication.class)
public class PostControllerTests {

    @Autowired
    PostController PC;

//    @Autowired
//    PostsDAO paccess;

    @Test
    @DisplayName("Testing setting postal code")
    void testGetPostalCode(){
        Post post1 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                null,
                true,
                "hey@email.com",
                50,
                53);

        PC.setCurrentInspectedPost(post1);
        PC.setPostPostalCode("s7n0p8");

        Assertions.assertEquals(PC.getPostalCode().toLowerCase(), "s7n0p8".toLowerCase());
        Assertions.assertEquals(PC.getCurrentPost().getLatitude(), -106.596794);
        Assertions.assertEquals(PC.getCurrentPost().getLongitude(), 52.136318);
    }

    @Test
    @DisplayName("Testing verifying of posts")
    void testVerifyAndPublic(){
        Post post1 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                null,
                true,
                "hey@email.com",
                50,
                53);


    }






}
