package com.saskcycle.controller;

import com.saskcycle.DAO.BusinessDAO;
import com.saskcycle.DAO.BusinessDAOInterface;
import com.saskcycle.DAO.PostsDAO;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.model.Business;
import com.saskcycle.model.Post;
import com.saskcycle.repo.BusinessesPostsRepo;
import com.saskcycle.repo.PostsRepo;
import com.saskcycle.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SearchController {

    /* --------- Attributes --------- */

//    @Autowired
//    private PostsRepo PR;
//
//    @Autowired
//    private BusinessesPostsRepo BR;


    @Autowired
    private PostsDAOInterface Paccess;

    @Autowired
    private BusinessDAOInterface Baccess;


    public SearchController(){
    }


    /* ---------  Methods  --------- */

//    public BusinessDAO getBaccess() {
//        return Baccess;
//    }
//
//    public PostsDAO getPaccess() {
//        return Paccess;
//    }

    public List<Post> getAllGivingAway(){
        List<Post> allPosts = new ArrayList<>();
        allPosts.addAll(Paccess.AllPosts());
        allPosts.addAll(Baccess.AllPosts());

        return allPosts;
        }

//
//    public static void main(String[] args) {
//        SearchController controller = new SearchController();
//        List<Post> allPost = controller.getAllGivingAway();
//
//        for (Post p : allPost){
//            System.out.println(p.description);
//        }
//    }






}
