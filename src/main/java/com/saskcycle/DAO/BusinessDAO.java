package com.saskcycle.DAO;

import com.saskcycle.model.Business;

import com.saskcycle.model.Post;
import com.saskcycle.repo.BusinessesPostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BusinessDAO implements BusinessDAOInterface{


    // This is the actual connection with the Account repo - the rest of the class will use this to implement methods.
    @Autowired
    private BusinessesPostsRepo BR;


    //attribute connected ot our repo
    public BusinessDAO(BusinessesPostsRepo repo){
        BR = repo;
    }

    /**
     * Method to grab all Business objects from the database
     * @return a List of all business in the database
     */
    public List<Business> AllPosts() {
        return BR.findAll();
    }



    //ToDo
    public Business searchByID(String id) {
        return null;
    }
    //ToDo
    @Override
    public ArrayList<Business> searchByKeywordFiltered(String keyword, String tag) {
        return null;
    }

    //ToDo
    @Override
    public ArrayList<Business> searchByLocation(String location) {
        return null;
    }

    //ToDo
    @Override
    public ArrayList<Business> searchByLocationFiltered(String location) {
        return null;
    }

    //ToDo
    @Override
    public ArrayList<Business> searchByRecentFiltered(Date date, String Tag) {
        return null;
    }

    //ToDo
    @Override
    public ArrayList<Business> searchByRecent(Date date) {
        return null;
    }

    /**
     * Adds a business object into the Database
     * @param business A fully constucted business object
     * @return the object that is inserted into the database
     */
    @Override
    public Business addBusiness(Business business) {
        return BR.insert(business);
    }


    /**
     * Removes a business object from the database
     * @param business the object that you want removed from thje database
     */
    public void deleteBusiness(Business business){
        BR.delete(business);
    }

    /**
     * Find all business that take in items based on inputted tag
     * @param tag predefined string respresenting a searchable tag
     * @return A list of business objects from DB that have the inputted tag
     */
    public List<Business> getAllBusinessesByTags(String tag) {
        return BR.findAllByTags(tag);
    }


    /**
     * Find a single business object by its title
     * This method will be primarily used for testing purposes
     * @param title a possible string title of a business object found in the DB
     * @return the business object with the given title if it exists, null otherwise(?)
     */
    public Business findBusinessByTitle(String title) {
        return BR.findByTitle(title);
    }


    /***
     * Gets all Business posts containing a keyword in the description or title
     * @param keyword: A string the user wishes to search by
     * @return a list of posts containing the keyphrase specified by the searcher
     */
    public List<Business> getAllBusinessesByKeyword(String keyword){
        List<Business> filteredBusinesses = new ArrayList<>();
        for (Business b : BR.findAll()){
            // Checks if case insensitive keyword is in title or description
            if (b.title.toLowerCase().contains(keyword.toLowerCase()) ||
                    b.description.toLowerCase().contains(keyword.toLowerCase()))
                filteredBusinesses.add(b);
        }
        return filteredBusinesses;
    }

    /**
     * Method to get all business posts from the database
     * @return List of type business that cotains all buisness objects currently in DB
     */
    public List<Business> getAllBusinesses() { return BR.findAll(); }
}
