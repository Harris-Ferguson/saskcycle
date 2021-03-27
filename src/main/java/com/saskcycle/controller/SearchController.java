package com.saskcycle.controller;

import com.saskcycle.DAO.BusinessDAOInterface;
import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.model.Business;
import com.saskcycle.model.Post;
import com.saskcycle.model.PostDistancePair;
import com.saskcycle.services.GeocodeService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.*;

@Controller
public class SearchController implements Serializable {

    /* --------- Attributes --------- */

    @Autowired
    private PostsDAOInterface postDataAccess;

    @Autowired
    private BusinessDAOInterface businessDataAccess;

    @Autowired
    private CurrentUserDAOInterface currentUserDataAccess;

    private List<Post> currentPosts;

    private final int ITEMS_PER_PAGE = 5;

    public SearchController() {

    }

    /* ---------  Methods  --------- */


    /**
     * Method used to populate currentPosts with all listings in the database
     */
    public void resetPosts() {
        currentPosts = this.getAllListings();
    }

    /**
     * Method to determine which "page" of posts to display to the user. Pages contain up to five posts at a time,
     * and which posts are displayed is determined by what the user selects in the view
     *
     * @param pageSelect a double representing the page number of posts to return (using a double because that is what
     *                   Vaadin uses)
     * @return a list of up to ITEMS_PER_PAGE posts from the corresponding "page" of all currentPosts
     */
    public List<Post> getPageOfPosts(double pageSelect) {
        //goTo will determine the last post to look for per page
        int goTo = (int) pageSelect * ITEMS_PER_PAGE;
        List<Post> postsPage = new ArrayList<>();
        //start at an iteration of 5, and go five items forward in list
        for (int i = ((int) pageSelect - 1) * ITEMS_PER_PAGE; i < goTo; i++) {
            //ensure no array out of bounds errors
            if (i < currentPosts.size()) {
                postsPage.add(currentPosts.get(i));
            }
        }
        return postsPage;
    }

    /**
     * Gets every post - giving away, getting, and static business posts - in one query
     *
     * @return A lLst of Post and Business items (which can be displayed the same way).
     */
    public List<Post> getAllListings() {
        List<Post> allPosts = new ArrayList<>();
        allPosts.addAll(postDataAccess.AllPosts());
        allPosts.addAll(businessDataAccess.AllPosts());
        return allPosts;
    }

    /**
     * Gets all giving away and getting posts.
     *
     * @return a List of Posts
     */
    public List<Post> getAllPosts() {
        return new ArrayList<>(postDataAccess.AllPosts());
    }

    /**
     * Gets all Listings that contain a certain tag
     *
     * @param tag : a tag of a search item type
     * @return a list of every post in the database containing that tag
     */
    public List<Post> getAllListingsByTag(String tag) {
        List<Post> postsByTag = new ArrayList<>();
        List<Post> allPosts = getAllListings();
        for (Post p : allPosts) {
            if (p.tags.contains(tag)) postsByTag.add(p);
        }
        return postsByTag;
    }

    /**
     * Gets all Posts that contain a certain tag
     *
     * @param tag: the tag of a search item type
     * @return a list of every post in the database containing that tag
     */
    public List<Post> getAllPostsByTag(String tag) {
        List<Post> postsByTag = new ArrayList<>();
        List<Post> allPosts = getAllPosts();
        for (Post p : allPosts) {
            if (p.tags.contains(tag)) postsByTag.add(p);
        }
        return postsByTag;
    }

    /**
     * Returns post that has ID marker ID
     *
     * @param id: a String containing the identification marker of a post
     * @return a post's ID
     */
    public Post getPostByID(String id) {
        return postDataAccess.searchByID(id);
    }

    /**
     * Removes all posts that are associated with specified tag(s)
     *
     * @param tag   the tag(s) to be excluded
     * @param posts the posts being filtered
     * @return list of posts not associated with the specified tag(s)
     * @postcond modifies the contents of posts
     */
    public List<Post> ExcludeListingsByTag(String tag, List<Post> posts) {
        // If no tag, no posts are excluded
        if (tag.isEmpty()) return posts;
        // Otherwise, exclude other posts
        List<Post> postsWithoutTag = new ArrayList<>();

        for (Post p : posts) {
            if (!p.tags.contains(tag)) postsWithoutTag.add(p);
        }
        return postsWithoutTag;
    }

    /**
     * Filters posts based on what action is associated with each post
     *
     * @param value give/get
     * @return list of posts that are associated with the specific action (give/get)
     */
    public List<Post> getSpecifiedPosts(String value) {

        List<Post> specPosts = new ArrayList<>();

        // If incorrect string parameter given, return empty list
        if (!value.equals("Looking for") && !value.equals("Giving away")) return specPosts;

        for (Post p : currentPosts) {
            if (value.equals("Looking for") && p.give) {
                specPosts.add(p);
            }
            if (value.equals("Giving away") && !p.give) {
                specPosts.add(p);
            }
        }

        return specPosts;
    }

    /**
     * Sorts posts by the users selected option
     *
     * @param value the filter value
     * @param posts the posts being filtered
     * @return List of sorted posts
     * @postcond modifies the order of posts
     */
    public List<Post> getSortedPosts(String value, List<Post> posts, String userLocation) {
        if (value.equals("Alphabetically (A-Z)")) {
            return sortAlphabetically(posts);
        } else {
            return sortByDistance(posts, userLocation);
        }
    }

    private List<Post> sortAlphabetically(List<Post> posts) {
        posts.sort(Comparator.comparing(a -> a.title));
        return posts;
    }

    private List<Post> sortByDistance(List<Post> posts, String userLocation) {
        List<PostDistancePair> postDistancePairs = getPostDistancePairs(posts, userLocation);
        Collections.sort(postDistancePairs);
        List<Post> sortedPosts = new ArrayList<>();
        for (PostDistancePair postDistancePair : postDistancePairs) {
            sortedPosts.add(postDistancePair.post);
        }
        return sortedPosts;
    }

    private List<PostDistancePair> getPostDistancePairs(List<Post> posts, String userLocation) {
        List<PostDistancePair> sorted = new ArrayList<>();
        GeocodeService geocodeService = new GeocodeService();
        try {
            geocodeService.geolocationFromPostalCode(userLocation);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (Post post : posts) {
            double distance = geocodeService.distance(post.getLatitude(), post.getLongitude());
            sorted.add(new PostDistancePair(post, distance));
        }
        return sorted;
    }

    /**
     * Gets all Business posts that contain a certain tag
     *
     * @param tag: a tag of a search item type
     * @return a list of every post in the database containing that tag
     */
    public List<Business> getAllBusinessesByTag(String tag) {
        return businessDataAccess.getAllBusinessesByTags(tag);
    }

    /**
     * Method to get all business posts from the database
     *
     * @return List of type business that cotains all buisness objects currently in DB
     */
    public List<Post> getAllBusinesses() {
        return new ArrayList<>(businessDataAccess.getAllBusinesses());
    }

    /**
     * Method that takes in filters user currently has selected, and determines which posts to put into list currentPosts
     *
     * @param includedTags a set of strings representing currently selected tags
     * @param excludedTags a set of strings that represent tags to exclude
     * @param poster       string representing whether the user is looking for other users or businesses/organizations
     * @param useChoice    string representing if user is looking for people that are taking things or giving things
     * @param sortChoice   string representing how the user wants the posts to be sorted
     */
    public void filterService(Set<String> includedTags, Set<String> excludedTags, String poster, String useChoice, String sortChoice, String location) {
        // Determine what posts from users/organizations should have filters applied
        if (poster.equals("Users") || poster.equals("Organizations")) {
            this.currentPosts = showByPoster(poster);
        }
        // If not selected, then get all listings in the DB
        else {
            this.currentPosts = this.getAllListings();
        }
        // Find all posts with the specified associated tags
        if (!includedTags.isEmpty()) {
            this.currentPosts = filterPosts(includedTags, poster);
        }
        // Do not get the posts that have the specified tag(s)
        if (!excludedTags.isEmpty()) {
            this.currentPosts = excludePosts(excludedTags);
        }
        if (useChoice.equals("Looking for") || useChoice.equals("Giving away")) {
            currentPosts = getSpecifiedPosts(useChoice);
        }
        // If specified, sort posts
        if (sortChoice.equals("Alphabetically (A-Z)") || sortChoice.equals("Closest to me")) {
            this.currentPosts = this.getSortedPosts(sortChoice, currentPosts, location);
        }
    }

    /**
     * Shows all posts from either all businesses or all users
     *
     * @param value a string containing either Users or Organizations. If it is neither an empty list is returned.
     * @return a list of all User or Organizational posts
     */
    public List<Post> showByPoster(String value) {
        this.currentPosts.clear();
        if (value.equals("Users")) {
            this.currentPosts = this.getAllPosts();
        } else if (value.equals("Organizations")) {
            this.currentPosts = this.getAllBusinesses();
        }

        return currentPosts;
    }

    /**
     * Shows all and only the posts that are associated with specified tag(s)
     *
     * @param value tag values(s) associated with posts
     * @return lit of posts that contain the relevant tag(s)
     */
    public List<Post> filterPosts(Set<String> value, String poster) {
        List<Post> newPosts = new ArrayList<>();
        // Only apply filters to user posts
        if (poster.equals("Users")) {
            // Get the posts associated with the tags
            for (String t : value) {
                newPosts.addAll(this.getAllPostsByTag(t));
            }
            return removeDuplicates(newPosts);
        }
        // Only apply filters to organization posts
        else if (poster.equals("Organizations")) {
            // Get the posts associated with the tags
            for (String t : value) {
                newPosts.addAll(this.getAllBusinessesByTag(t));
            }
            return removeDuplicates(newPosts);
        }
        // Otherwise no user/organization was chosen
        else {
            // Get the posts associated with the tags
            for (String t : value) {
                newPosts.addAll(this.getAllListingsByTag(t));
            }
            return removeDuplicates(newPosts);
        }
    }

    private List<Post> removeDuplicates(List<Post> newPosts) {
        List<Post> anotherPosts = new ArrayList<>();
        for (Post p : newPosts) {
            if (!anotherPosts.contains(p)) {
                anotherPosts.add(p);
            }
        }

        return anotherPosts;
    }

    /**
     * Hides the posts that are tagged with the specified tag(s)
     *
     * @param value tag values(s) associated with posts
     * @return list of posts that are not associated with spec. tag(s)
     */
    public List<Post> excludePosts(Set<String> value) {
        for (String t : value) {
            this.currentPosts = this.ExcludeListingsByTag(t, currentPosts);
        }
        return currentPosts;
    }

    /**
     * helper method for the view to determine how many pages of posts there will be based on size of currentPosts
     *
     * @return an integer representing the amount of pages there will be in the view
     */
    public int amountOfPages() {
        int numberOfPages;
        if (this.currentPosts.size() % ITEMS_PER_PAGE == 0) {
            numberOfPages = this.currentPosts.size() / ITEMS_PER_PAGE;
        } else {
            numberOfPages = this.currentPosts.size() / ITEMS_PER_PAGE + 1;
        }
        if (numberOfPages == 0) {
            return 1;
        } else {
            return numberOfPages;
        }
    }

    /**
     * Finds a user's list of saved posts (IDs) and returns a list of Post objects to be displayed in the view
     *
     * @return a list of Post objects found by their IDs
     */
    public List<Post> getSavedPosts() {
        //Get list of strings from User's accound class, intialize a list of posts to return,
        // and a list of strings that represent invalid (deleted) IDs to be removed
        List<String> wishlist = currentUserDataAccess.getCurrentAccount().getWishlist();
        List<Post> wishListPosts = new ArrayList<>();
        List<String> postsToRemove = new ArrayList<>();
        for (String s : wishlist) {
            if (postDataAccess.searchByID(s) == null) {
                postsToRemove.add(s);
            } else {
                wishListPosts.add(postDataAccess.searchByID(s));
            }

        }
        for (String s : postsToRemove) {
            currentUserDataAccess.getCurrentAccount().getWishlist().remove(s);
        }
        return wishListPosts;
    }

    public void removeSavedPost(String id) {
        currentUserDataAccess.removeFromWishlist(id);
    }
}
