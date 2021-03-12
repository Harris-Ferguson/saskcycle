package com.saskcycle.controller;

import com.saskcycle.DAO.BusinessDAOInterface;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.model.Business;
import com.saskcycle.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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


    public List<Post> ExcludeListingsByTag(String tag) {
        List<Post> postsWithoutTag = new ArrayList<>();
        List<Post> allPosts = getAllPosts();
        for (Post p : allPosts) {
            if (!p.tags.contains(tag)) postsWithoutTag.add(p);
        }
        return postsWithoutTag;
    }

    public List<Post> getSpecifiedPosts(String value) {

        List<Post> specPosts = new ArrayList<>();
        List<Post> allPosts = getAllPosts();
        for (Post p : allPosts) {
            if (value.equals("Get") && !p.give) {
                specPosts.add(p);
            }
            if (value.equals("Give") && p.give) {
                specPosts.add(p);
            }
        }
        return specPosts;
    }

    public List<Post> getSortedPosts(String value) {

        List<Post> allPosts = getAllPosts();
        if (value.equals("Alphabetically (A-Z)")) {
                allPosts.sort(Comparator.comparing(a -> a.title));
        }
        if (value.equals("Closest to me")) {
            allPosts.sort(Comparator.comparing(a -> a.location));
        }

        return allPosts;
    }
}


