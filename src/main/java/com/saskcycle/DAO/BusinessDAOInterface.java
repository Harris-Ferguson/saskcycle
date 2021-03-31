package com.saskcycle.DAO;

import com.saskcycle.model.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessDAOInterface {

    /**
     * Add a business to the database
     *
     * @param business business to add
     * @return the added business
     */
    Business addBusiness(Business business);

    List<Business> AllPosts();

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

    Business findByid(String id);
}
