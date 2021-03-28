package com.saskcycle.saskcycle;

import com.saskcycle.DAO.BusinessDAOInterface;
import com.saskcycle.DAO.PostsDAO;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import com.saskcycle.model.Tags;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class SaskCycleApplicationTests {
  @Autowired private com.saskcycle.repo.BusinessesPostsRepo BR;

  @Autowired private BusinessDAOInterface Baccess;

  @Autowired private SearchController SC;

  @Autowired PostsDAO postDAD;

  @Test
  @DisplayName("Tests adding and removing post addition and removal")
  void businessTests(){
    ArrayList<String> tags = new ArrayList<String>(Arrays.asList(Tags.getTagNames()));
    Post post = new Post();
    post.setContactEmail("tester1@mail");
    post.setDatePosted(new Date());
    post.setId("37");
    post.setDescription("Test 1");
    post.setPostType(true);
    post.setPostalCode("test1");
    post.setLatitude(0);
    post.setLongitude(0);
    post.setOwner(null);
    post.setPublic(true);
    post.setTags(tags);
    post.setTitle("Test 1 title");

    Post post2 = new Post();
    post2.setContactEmail("tester2@mail");
    post2.setDatePosted(new Date());
    post2.setId("38");
    post2.setDescription("Test 2");
    post2.setPostType(true);
    post2.setPostalCode("test2");
    post2.setLatitude(0);
    post2.setLongitude(0);
    post2.setOwner(null);
    post2.setPublic(true);
    post2.setTags(tags);
    post2.setTitle("Test 2 title");

    Post post3 = new Post();
    post3.setContactEmail("tester3@mail");
    post3.setDatePosted(new Date());
    post3.setId("39");
    post3.setDescription("Test 3");
    post3.setPostType(true);
    post3.setPostalCode("test3");
    post3.setLatitude(0);
    post3.setLongitude(0);
    post3.setOwner(null);
    post3.setPublic(true);
    post3.setTags(tags);
    post3.setTitle("Test 3 title");

    Post post4 = new Post();
    post4.setContactEmail("tester4@mail");
    post4.setDatePosted(new Date());
    post4.setId("40");
    post4.setDescription("Test 4");
    post4.setPostType(true);
    post4.setPostalCode("test4");
    post4.setLatitude(0);
    post4.setLongitude(0);
    post4.setOwner(null);
    post4.setPublic(true);
    post4.setTags(tags);
    post4.setTitle("Test 4 title");

    Post post5 = new Post();
    post5.setContactEmail("tester5@mail");
    post5.setDatePosted(new Date());
    post5.setId("41");
    post5.setDescription("Test 5");
    post5.setPostType(true);
    post5.setPostalCode("test5");
    post5.setLatitude(0);
    post5.setLongitude(0);
    post5.setOwner(null);
    post5.setPublic(true);
    post5.setTags(tags);
    post5.setTitle("Test 5 title");
    postDAD.addPost(post);
    postDAD.addPost(post2);
    postDAD.addPost(post3);
    postDAD.addPost(post4);
    postDAD.addPost(post5);
    Assertions.assertTrue(true);
    SC.resetPosts();
    Assertions.assertEquals(SC.getPageOfPosts(1).size(), 5);
    Assertions.assertTrue(SC.getAllListings().size() >= 5);
    Assertions.assertTrue(SC.showByPoster("Users").size() >= 5);
    Assertions.assertTrue(SC.getAllPosts().size() >=5);
    Assertions.assertTrue(SC.amountOfPages() >=1);
    Set<String> set = new HashSet<>();
    set.add("Metal");
    Assertions.assertTrue(SC.excludePosts(set).size() <= SC.getAllListings().size() -5);
    postDAD.deletePost(post);
    postDAD.deletePost(post2);
    postDAD.deletePost(post3);
    postDAD.deletePost(post4);
    postDAD.deletePost(post5);

  }

  //todo
  // Write tests for model objects
  // Write tests for adding and removing businesses/posts from database (as opposed to hardcoding results, checkmembership?
  // Test Geocode service
  // Test Controller methods (searching, etc)
}