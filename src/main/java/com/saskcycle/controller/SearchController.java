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
import java.util.Locale;

@Controller
public class SearchController {

    /* --------- Attributes --------- */


    @Autowired
    private PostsDAOInterface Paccess;

    @Autowired
    private BusinessDAOInterface Baccess;


    public SearchController(){}


    /* ---------  Methods  --------- */


    /***
     * Gets every post - giving away, getting, and static business posts - in one query
     * @return A lLst of Post and Business items (which can be displayed the same way).
     */
    public List<Post> getAllListings(){
        List<Post> allPosts = new ArrayList<>();
        allPosts.addAll(Paccess.AllPosts());
        allPosts.addAll(Baccess.AllPosts());

        return allPosts;
        }



    /***
     * Gets all giving away and getting posts.
     * @return a List of Posts
     */
    public List<Post> getAllPosts(){
        return new ArrayList<>(Paccess.AllPosts());
    }


    /***
     * Adds a post to the SaskCycle system
     * @param post: A post object
     */
    public void addPost(Post post){
        Paccess.addPost(post);
    }





    /***
     * Gets all Listings that contain a certain tag
     * @param tag: a tag of a search item type
     * @return a list of every post in the database containing that tag
     */
    public  List<Post> getAllListingsByTag(String tag) {
        List<Post> postsByTag = new ArrayList<>();
        List<Post> allPosts = getAllListings();
        for (Post p : allPosts) {
            if (p.tags.contains(tag)) postsByTag.add(p);
        }
        return postsByTag;
    }

    /***
     * Gets all Posts that contain a certain tag
     * @param tag: the tag of a search item type
     * @return a list of every post in the database containing that tag
     */
    public  List<Post> getAllPostsByTag(String tag) {
        List<Post> postsByTag = new ArrayList<>();
        List<Post> allPosts = getAllPosts();
        for (Post p : allPosts) {
            if (p.tags.contains(tag)) postsByTag.add(p);
        }
        return postsByTag;
    }

    /***
     * Returns post that has ID marker ID
     * @param id: a String containing the identification marker of a post
     * @return a post's ID
     */
    public Post getPostByID(String id){
        return Paccess.searchByID(id);
    }

    /***
     * Returns business that has ID marker ID
     * @param id: a String containing the identification marker of a business
     * @return a business's ID
     */
    public Business getBusinessByID(String id){
        return Baccess.searchByID(id);
    }

    /***
     * Gets all posts containing a keyword in the description or title
     * @param keyword: A string the user wishes to search by
     * @return a list of posts containing the keyphrase specified by the searcher
     */
    public List<Post> getAllPostsByKeyword(String keyword){
        List<Post> filteredPosts = new ArrayList<>();
        for (Post p : getAllPosts()){
            // Checks if case insensitive keyword is in title or description
            if (p.title.toLowerCase().contains(keyword.toLowerCase()) ||
                    p.description.toLowerCase().contains(keyword.toLowerCase()))
                filteredPosts.add(p);
        }
        return filteredPosts;
    }


    /***
     * Gets all Business posts that contain a certain tag
     * @param tag: a tag of a search item type
     * @return a list of every post in the database containing that tag
     */
    public  List<Business> getAllBusinessesByTag(String tag) {

        return Baccess.getAllBusinessesByTags(tag);
    }

    /***
     * Gets all Business posts containing a keyword in the description or title
     * @param keyword: A string the user wishes to search by
     * @return a list of posts containing the keyphrase specified by the searcher
     */
    public List<Business> getAllBusinessesByKeyword(String keyword){

        return Baccess.getAllBusinessesByKeyword(keyword);
    }

    public Business findBusinessByTitle(String title)
    {
        return Baccess.findBusinessByTitle(title);
    }


}


