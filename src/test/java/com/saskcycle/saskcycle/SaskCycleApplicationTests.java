package com.saskcycle.saskcycle;

import com.saskcycle.DAO.BusinessDAOInterface;
import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Business;
import com.saskcycle.model.Post;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class SaskCycleApplicationTests {
  @Autowired private com.saskcycle.repo.BusinessesPostsRepo BR;

  @Autowired private BusinessDAOInterface Baccess;

  @Autowired private SearchController SC;

  @Test
  @DisplayName("Searching by tag tests")
  void searchByTagTests() {

    List<Business> bL = SC.getAllBusinessesByTag("Clothing");
    for (Business b : bL) {
      System.out.println(b.toString());
    }
    // three stores with clothing tags in DB
    Assertions.assertEquals(3, bL.size());
    bL.clear();

    bL = SC.getAllBusinessesByTag("Metal");
    for (Business b : bL) {
      System.out.println(b.toString());
    }
    // One business with metal tags in DB
    Assertions.assertEquals(1, bL.size());
    bL.clear();

    // serach for tag that does not exist
    bL = SC.getAllBusinessesByTag("clothes");
    Assertions.assertEquals(0, bL.size());
  }

  @Test
  @DisplayName("Search for All businesses in DB")
  void getAllBusinesses() {
    List<Post> bL = SC.getAllBusinesses();
    for (Post b : bL) {
      System.out.println(b.toString());
    }
    Assertions.assertEquals(5, bL.size());
  }

  //todo
  // Write tests for model objects
  // Write tests for adding and removing businesses/posts from database (as opposed to hardcoding results, checkmembership?
  // Test Geocode service
  // Test Controller methods (searching, etc)
}
