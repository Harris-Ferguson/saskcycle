package com.saskcycle.DAO;

import com.saskcycle.model.Business;
import com.saskcycle.model.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface BusinessDAOInterface {

    List<Business> AllPosts();

    Business searchByID(String id);

    List<Business> getAllBusinessesByKeyword(String keyword);

    ArrayList<Business> searchByKeywordFiltered(String keyword, String tag);

    ArrayList<Business> searchByLocation(String location);

    ArrayList<Business> searchByLocationFiltered(String location);

    ArrayList<Business> searchByRecentFiltered(Date date, String Tag);

    ArrayList<Business> searchByRecent(Date date);

    Post addBusiness(Business business);

    void deleteBusiness(Business business);

    List<Business> getAllBusinessesByTag(String tag);
    Business findBusinessByTitle(String title);
}
