package com.saskcycle.services;

import com.saskcycle.controller.SearchController;
import com.saskcycle.model.Post;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Service
public class FilterService implements Serializable {

    private SearchController SC;

    private List<Post> posts;

    public FilterService(SearchController sc){

        this.SC = sc;
        posts = SC.getAllPosts();
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
    public List<Post> excludePosts(Set<String> value) {

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
        posts = SC.getAllPosts();

        return posts;
    }

    /**
     * Shows all and only the posts that are associated with specified tag(s)
     * @param value tag values(s) associated with posts
     * @return lit of posts that contain the relevant tag(s)
     */
    public List<Post> filterPosts(Set<String> value) {

        for (String t : value) {
            posts = SC.getAllListingsByTag(t, posts);
        }

        return posts;
    }
}
