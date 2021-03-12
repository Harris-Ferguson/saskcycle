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


    public BusinessDAO(BusinessesPostsRepo repo){
        BR = repo;
    }


    public List<Business> AllPosts() {
        return BR.findAll();
    }



    public Business searchByID(String id) {
        return null;
    }

    @Override
    public ArrayList<Business> searchByKeywordFiltered(String keyword, String tag) {
        return null;
    }

    @Override
    public ArrayList<Business> searchByLocation(String location) {
        return null;
    }

    @Override
    public ArrayList<Business> searchByLocationFiltered(String location) {
        return null;
    }

    @Override
    public ArrayList<Business> searchByRecentFiltered(Date date, String Tag) {
        return null;
    }

    @Override
    public ArrayList<Business> searchByRecent(Date date) {
        return null;
    }

    @Override
    public Business addBusiness(Business business) {
        return BR.insert(business);
    }



    public void deleteBusiness(Business business){
        BR.delete(business);
    }

    /**
     * Find all business that take in items based on inputted tag
     * @param tag predefined string respresenting a searchable tag
     * @return A list of business objects from DB that have the inputted tag
     */
    @Override
    public List<Business> getAllBusinessesByTag(String tag) {
        return BR.findAllByTags(tag);
    }


    public Business findByTitle(String name) {
        return BR.findByTitle(name);
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
}
