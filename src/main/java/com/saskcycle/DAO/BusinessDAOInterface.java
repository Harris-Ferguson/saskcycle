package com.saskcycle.DAO;

import com.saskcycle.model.Business;
import com.saskcycle.model.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface BusinessDAOInterface {

  List<Business> AllPosts();

  Business searchByID(String id);

  /***
   * Gets all Business posts containing a keyword in the description or title
   * @param keyword: A string the user wishes to search by
   * @return a list of posts containing the keyphrase specified by the searcher
   */
  List<Business> getAllBusinessesByKeyword(String keyword);

  ArrayList<Business> searchByKeywordFiltered(String keyword, String tag);

  ArrayList<Business> searchByLocation(String location);

  ArrayList<Business> searchByLocationFiltered(String location);

  ArrayList<Business> searchByRecentFiltered(Date date, String Tag);

  ArrayList<Business> searchByRecent(Date date);

  Post addBusiness(Business business);

  void deleteBusiness(Business business);

  /**
   * Find all business that take in items based on inputted tag
   *
   * @param tag predefined string respresenting a searchable tag
   * @return A list of business objects from DB that have the inputted tag
   */
  List<Business> getAllBusinessesByTags(String tag);

  /**
   * Find a single business object by its title This method will be primarily used for testing
   * purposes
   *
   * @param title a possible string title of a business object found in the DB
   * @return the business object with the given title if it exists, null otherwise(?)
   */
  Business findBusinessByTitle(String title);

  /**
   * Method to get all business posts from the database
   *
   * @return List of type business that cotains all buisness objects currently in DB
   */
  List<Business> getAllBusinesses();
}
