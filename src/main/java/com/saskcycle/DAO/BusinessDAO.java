package com.saskcycle.DAO;

import com.saskcycle.model.Business;

import com.saskcycle.model.Post;
import com.saskcycle.repo.BusinessesPostsRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public ArrayList<Business> searchByKeyword(String keyword) {
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




    public Business searchByName(String name) {
        return null;
    }

    public List<Business> searchByTag(String tag) {
        return null;
    }
}
