package com.saskcycle.services;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class FilterService implements Serializable {

    private SearchController SC;

    private List<Post> posts;

    public FilterService(SearchController sc){

        this.SC = sc;
        posts = SC.getAllListings();
    }

    /**
     * Gets the lists of all posts in post document
     * @return list of posts
     */
    public List<Post> getPosts() {
        return posts;
    }



    /**
     * Gets the either the give or get posts depending what's chosen by the user
     * @param value "get" or "give"
     * @return list of get or give posts
     */
    public List<Post> sortByFunction(String value) {

        posts = SC.getSpecifiedPosts(value, posts);

        return posts;

    }

    /**
     * Sorts posts by the given specification
     * @param value the characteristic by which the code is sorted
     * @return list of sorted posts
     */
    public List<Post> sortPosts(String value) {

        SC.getSortedPosts(value, posts);

        return posts;
    }

    /**
     * Hides the posts that are tagged with the specified tag(s)
     * @param value tag values(s) associated with posts
     * @return list of posts that are not associated with spec. tag(s)
     */
    public List<Post> excludePosts(Set<String> value, String poster) {

            for (String t : value) {
                posts = SC.ExcludeListingsByTag(t, posts);
            }
            return posts;


    }

    /**
     * Gets all posts in the database; removes any filtering/excluding/sorting
     * @return list of all posts
     */
    public List<Post> resetPosts() {
        posts.clear();
        posts = SC.getAllListings();

        return posts;
    }

    /**
     * Shows all and only the posts that are associated with specified tag(s)
     * @param value tag values(s) associated with posts
     * @return lit of posts that contain the relevant tag(s)
     */
    public List<Post> filterPosts(Set<String> value, String poster) {
        if (poster.equals("Users"))
        {
            List<Post> newPosts = new ArrayList<>();

            for (String t : value) {
                newPosts.addAll(SC.getAllPostsByTag(t));
            }

            List<Post> anotherPosts = new ArrayList<>();
            for (Post p : newPosts) {
//                for (Post a : anotherPosts) {
//                    if (a.title.compareTo(p.title) == 0)
//                    {
//                        break;
//                    }
//                }
//                anotherPosts.add(p);
                if (!anotherPosts.contains(p)) {
                    System.out.println(p.title);
                    anotherPosts.add(p);
                }

            }

            return anotherPosts;
        }
        else if (poster.equals("Organizations"))
        {
            List<Post> newPosts = new ArrayList<>();

            for (String t : value) {
                newPosts.addAll(SC.getAllBusinessesByTag(t));
            }

            List<Post> anotherPosts = new ArrayList<>();
            for (Post p : newPosts) {
                if (!anotherPosts.contains(p)) {
                    anotherPosts.add(p);
                }

            }

            return anotherPosts;
        }
        else
        {
            List<Post> newPosts = new ArrayList<>();

            for (String t : value) {
                newPosts.addAll(SC.getAllListingsByTag(t));
            }

            List<Post> anotherPosts = new ArrayList<>();
            for (Post p : newPosts) {
                if (!anotherPosts.contains(p)) {
                    anotherPosts.add(p);
                }

            }

            return anotherPosts;
        }

    }

    /**
     * Checks what features are being applied to the posts
     * @param includedTags tags for posts the user wants to see
     * @param excludedTags tags for posts user doesn't want to see
     * @param sortChoice sorting order of the posts
     * @return list of posts with appropriate filters applied
     */
    public List<Post> checkOtherFilters(Set<String> includedTags, Set<String> excludedTags, String poster, String sortChoice) {


        if (poster.equals("Users") || poster.equals("Organizations")) {
            posts = showByPoster(poster);
        }
        else {
            posts = SC.getAllListings();
        }

        if (!includedTags.isEmpty()) {
            posts = filterPosts(includedTags,poster);
        }
        if (!excludedTags.isEmpty()) {
            posts = excludePosts(excludedTags,poster);
        }
        /* Use case is not fully implemented
        if (useChoice.equals("Get") || useChoice.equals("Give")) {
            posts = sortByFunction(useChoice);
        }
        */
        if (sortChoice.equals("Alphabetically (A-Z)") || sortChoice.equals("Closest to me")) {
            posts = sortPosts(sortChoice);
        }
        return posts;
    }

    public List<Post> showByPoster(String value) {

        posts.clear();
        if (value.equals("Users")) {
            posts = SC.getAllPosts();
        }
        else if (value.equals("Organizations")){
           posts = SC.getAllBusinesses();
        }

    return posts;
  }
}
