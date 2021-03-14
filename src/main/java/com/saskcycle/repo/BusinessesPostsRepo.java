package com.saskcycle.repo;

import com.saskcycle.model.Business;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessesPostsRepo extends MongoRepository<Business, String> {

  /**
   * Find all business that take in items based on inputted tag
   *
   * @param tags predefined string respresenting a searchable tag
   * @return A list of business objects from DB that have the inputted tag
   */
  List<Business> findAllByTags(String tags);

  /**
   * Find a single business object by its title This method will be primarily used for testing
   * purposes
   *
   * @param title a possible string title of a business object found in the DB
   * @return the business object with the given title if it exists, null otherwise(?)
   */
  Business findByTitle(String title);
}
