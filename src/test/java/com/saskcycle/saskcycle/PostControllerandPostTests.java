package com.saskcycle.saskcycle;

import com.saskcycle.DAO.PostsDAO;
import com.saskcycle.controller.PostController;
import com.saskcycle.model.Post;
import com.saskcycle.model.Tags;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@SpringBootTest(classes = SaskCycleApplication.class)
public class PostControllerandPostTests {

    @Autowired
    PostController PC;

    @Autowired
    PostsDAO paccess;

    @Test
    @DisplayName("Testing isComplete (posts)")
    void testIsComplete() {
        ArrayList<String> tags = new ArrayList();
        tags.addAll(Arrays.asList(Tags.getTagNames()));
        Post post1 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);

        Assertions.assertTrue(post1.isComplete());

        post1.setTitle("");
        Assertions.assertFalse(post1.isComplete());
        post1.setTitle("Hey it's a test");
        post1.setDescription("");
        Assertions.assertFalse(post1.isComplete());
        post1.setDescription("Test Description");
        post1.setId("");
        Assertions.assertFalse(post1.isComplete());
        post1.setId("TESTMIX2000");
        post1.setTags(new ArrayList<>());
        Assertions.assertFalse(post1.isComplete());
    }

    @Test
    @DisplayName(("Testing posts.equal"))
    void testPostEquals(){
        ArrayList<String> tags = new ArrayList();
        tags.addAll(Arrays.asList(Tags.getTagNames()));        tags.addAll(Arrays.asList(Tags.getTagNames()));        Post post1 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);

        Post post2 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);

        Post post3 = new Post(true,
                "Hey its a false test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);

        Post post4 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2FALSE000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);

        Post post5 = new Post(true,
                "Hey it's  a fake test",
                "Hey it's a test",
                "TESTMIX2FALSE000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);


        Assertions.assertEquals(post1, post2);
        Assertions.assertNotEquals(post1,post3);
        Assertions.assertNotEquals(post1,post4);
        Assertions.assertNotEquals(post1,post5);
    }

    @Test
    @DisplayName("Testing setting postal code")
    void testGetPostalCode(){
        ArrayList<String> tags = new ArrayList();
        tags.addAll(Arrays.asList(Tags.getTagNames()));        Post post1 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                tags,
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
    @DisplayName("Testing verifying of posts and deletion of posts")
    void testVerifyPublishDelete(){
        ArrayList<String> tags = new ArrayList();
        tags.addAll(Arrays.asList(Tags.getTagNames()));        Post post1 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);

        PC.setCurrentInspectedPost(post1);
        PC.verifyAndPublish();
        ArrayList<Post> posts = new ArrayList<>(paccess.AllPosts());
        Assertions.assertTrue(posts.contains(post1));

        PC.removePost();
        posts.clear();
        posts.addAll(paccess.AllPosts());
        Assertions.assertFalse(posts.contains(post1));

    }

    @Test
    @DisplayName("Test updating of posts")
    void testVerifyAndUpdatePost() {
        ArrayList tags = new ArrayList(Arrays.asList(Tags.getTagNames()));
        Post post1 = new Post(true,
                "Hey it's test",
                "Hey it's a test",
                "TESTMIX2000",
                new Date(),
                null,
                "s7n0p8",
                tags,
                true,
                "hey@email.com",
                50,
                53);

        PC.setCurrentInspectedPost(post1);
        PC.verifyAndPublish();
        ArrayList<Post> posts = new ArrayList<>(paccess.AllPosts());
        Assertions.assertTrue(posts.contains(post1));

        PC.setPostTitle("Still a test, you know it!");
        PC.verifyAndUpdatePost();
        posts.clear();
        posts.addAll(paccess.AllPosts());
        Assertions.assertTrue(posts.contains(post1));

        PC.removePost();
        posts.clear();
        posts.addAll(paccess.AllPosts());
        Assertions.assertFalse(posts.contains(post1));
    }





}
