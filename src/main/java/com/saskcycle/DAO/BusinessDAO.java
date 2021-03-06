package com.saskcycle.DAO;

import com.saskcycle.model.Business;
import com.saskcycle.repo.BusinessesPostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BusinessDAO implements BusinessDAOInterface {

    // This is the actual connection with the Account repo - the rest of the class will use this to
    // implement methods.
    @Autowired
    private final BusinessesPostsRepo BR;

    // attribute connected ot our repo
    public BusinessDAO(BusinessesPostsRepo repo) {
        BR = repo;
    }

    /**
     * Method to grab all Business objects from the database
     *
     * @return a List of all business in the database
     */
    public List<Business> AllPosts() {
        return BR.findAll();
    }

    /**
     * Adds a business object into the Database
     *
     * @param business A fully constucted business object
     * @return the object that is inserted into the database
     */
    @Override
    public Business addBusiness(Business business) {
        return BR.insert(business);
    }

    /**
     * Removes a business object from the database
     *
     * @param business the object that you want removed from thje database
     */
    public void deleteBusiness(Business business) {
        BR.delete(business);
    }

    /**
     * Find all business that take in items based on inputted tag
     *
     * @param tag predefined string respresenting a searchable tag
     * @return A list of business objects from DB that have the inputted tag
     */
    public List<Business> getAllBusinessesByTags(String tag) {
        return BR.findAllByTags(tag);
    }

    /**
     * Find a single business object by its title This method will be primarily used for testing
     * purposes
     *
     * @param title a possible string title of a business object found in the DB
     * @return the business object with the given title if it exists, null otherwise(?)
     */
    public Business findBusinessByTitle(String title) {
        return BR.findByTitle(title);
    }

    /**
     * Method to get all business posts from the database
     *
     * @return List of type business that cotains all buisness objects currently in DB
     */
    public List<Business> getAllBusinesses() {
        return BR.findAll();
    }

    public Business findByid(String id){
        return BR.findByid(id);
    }
}
