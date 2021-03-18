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
import java.util.Set;

@Controller
public class SearchController {

  /* --------- Attributes --------- */

  @Autowired private PostsDAOInterface Paccess;

  @Autowired private BusinessDAOInterface Baccess;

  List<Post> currentPosts;

  public SearchController() {
//    currentPosts = this.getAllListings();
  }

  /* ---------  Methods  --------- */

  /***
   * Gets every post - giving away, getting, and static business posts - in one query
   * @return A lLst of Post and Business items (which can be displayed the same way).
   */
  public List<Post> getAllListings() {
    List<Post> allPosts = new ArrayList<>();
    allPosts.addAll(Paccess.AllPosts());
    allPosts.addAll(Baccess.AllPosts());

    return allPosts;
  }

  /***
   * Gets all giving away and getting posts.
   * @return a List of Posts
   */
  public List<Post> getAllPosts() {
    return new ArrayList<>(Paccess.AllPosts());
  }

  /***
   * Adds a post to the SaskCycle system
   * @param post: A post object
   */
  public void addPost(Post post) {
    if (post != null) Paccess.addPost(post);
  }

  /***
   * Gets all Listings that contain a certain tag
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

  /***
   * Gets all Posts that contain a certain tag
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

  /***
   * Returns post that has ID marker ID
   * @param id: a String containing the identification marker of a post
   * @return a post's ID
   */
  public Post getPostByID(String id) {
    return Paccess.searchByID(id);
  }

  /***
   * Returns business that has ID marker ID
   * @param id: a String containing the identification marker of a business
   * @return a business's ID
   */
  public Business getBusinessByID(String id) {
    return Baccess.searchByID(id);
  }

  /***
   * Gets all posts containing a keyword in the description or title
   * @param keyword: A string the user wishes to search by
   * @return a list of posts containing the keyphrase specified by the searcher
   */
  public List<Post> getAllPostsByKeyword(String keyword) {
    List<Post> filteredPosts = new ArrayList<>();
    for (Post p : getAllPosts()) {
      // Checks if case insensitive keyword is in title or description
      if (p.title.toLowerCase().contains(keyword.toLowerCase())
          || p.description.toLowerCase().contains(keyword.toLowerCase())) filteredPosts.add(p);
    }
    return filteredPosts;
  }

  /**
   * Removes all posts that are associated with specified tag(s)
   *
   * @param tag the tag(s) to be excluded
   * @param posts the posts being filtered
   * @postcond modifies the contents of posts
   * @return list of posts not associated with the specified tag(s)
   */
  public List<Post> ExcludeListingsByTag(String tag, List<Post> posts) {
    // If no tag, no posts are excluded
    if (tag.isEmpty()) return posts;
    // Otherwise, exclude other posts
    List<Post> postsWithoutTag = new ArrayList<>();
    // List<Post> allPosts = getAllPosts();
    for (Post p : posts) {
      if (!p.tags.contains(tag)) postsWithoutTag.add(p);
    }
    return postsWithoutTag;
  }

  /**
   * Filters posts based on what action is associated with each post
   *
   * @param value give/get
   * @param posts the posts being filtered
   * @return list of posts that are associated with the specific action (give/get)
   */
  public List<Post> getSpecifiedPosts(String value, List<Post> posts) {

    List<Post> specPosts = new ArrayList<>();

    // If incorrect string parameter given, return empty list
    if (!value.equals("Get") && !value.equals("Give")) return specPosts;

    // List<Post> allPosts = getAllPosts();
    for (Post p : posts) {
      if (value.equals("Get") && !p.give) {
        specPosts.add(p);
      }
      if (value.equals("Give") && p.give) {
        specPosts.add(p);
      }
    }
    return specPosts;
  }

  /**
   * Either sorts posts alphabetically or from closest to farthest away
   *
   * @param value the filter value
   * @param posts the posts being filtered
   * @postcond modifies the order of posts
   * @return List of sorted posts
   */
  public List<Post> getSortedPosts(String value, List<Post> posts) {

    if (value.equals("Alphabetically (A-Z)")) {
      posts.sort(Comparator.comparing(a -> a.title));
    }
    if (value.equals("Closest to me")) {
      posts.sort(
          Comparator.comparing(
              a -> Float.parseFloat(a.location.substring(0, a.location.length() - 2))));
    }

    return posts;
  }

  /***
   * Gets all Business posts that contain a certain tag
   * @param tag: a tag of a search item type
   * @return a list of every post in the database containing that tag
   */
  public List<Business> getAllBusinessesByTag(String tag) {

    return Baccess.getAllBusinessesByTags(tag);
  }

  /***
   * Gets all Business posts containing a keyword in the description or title
   * @param keyword: A string the user wishes to search by
   * @return a list of posts containing the keyphrase specified by the searcher
   */
  public List<Business> getAllBusinessesByKeyword(String keyword) {

    return Baccess.getAllBusinessesByKeyword(keyword);
  }

  /**
   * Find a single business object by its title This method will be primarily used for testing
   * purposes
   *
   * @param title a possible string title of a business object found in the DB
   * @return the business object with the given title if it exists, null otherwise(?)
   */
  public Business findBusinessByTitle(String title) {
    return Baccess.findBusinessByTitle(title);
  }

  /**
   * Method to get all business posts from the database
   *
   * @return List of type business that cotains all buisness objects currently in DB
   */
  public List<Post> getAllBusinesses() {
    return new ArrayList<>(Baccess.getAllBusinesses());
  }

  public void filterService(Set<String> includedTags, Set<String> excludedTags, String poster, String sortChoice)
  {

  }



}
