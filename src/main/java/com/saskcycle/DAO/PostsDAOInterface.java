package com.saskcycle.DAO;

import com.saskcycle.model.Post;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface PostsDAOInterface {

  /***
   * Gives the user every Post in the MongoDB Post database
   * @return a list of posts from the database
   */
  List<Post> AllPosts();

  /***
   * Returns a post corresponding to a specific ID
   * @param id The id of the sought-after post
   * @return a post with the id specified
   */
  Post searchByID(String id);

  /**
   * Returns all posts containing a specific keyword in the title or description from the database
   * @param keyword a word or series of words being searched for
   * @return all posts with that keyword in the title or description
   */
  ArrayList<Post> searchByKeyword(String keyword);

  /**
   * Searches the database for posts containing a keyword and a tag
   * @param keyword a word or series of words being searched for
   * @param tag a descriptive tag specifying item type in String form
   * @return all posts containing both the keyword and tag
   */
  ArrayList<Post> searchByKeywordFiltered(String keyword, String tag);

  /**
   * Gets all database posts from closest to fatherest from the input location
   * @param location: The starting location
   * @return a list of posts sorted from closest to farthest from the starting location
   */
  ArrayList<Post> searchByLocation(String location);


  /**
   * Gets all database posts from closest to fatherest from the input location by a specific tag
   * @param location: The starting location
   * @return a list of posts sorted from closest to farthest from the starting location
   */
  ArrayList<Post> searchByLocationFiltered(String location);

  /**
   * Gets all posts from recently made to least recently made with a specific tag
   * @param date The current date
   * @param Tag a filtered tag specifying which posts to include
   * @return a list of posts sorted by date
   */
  ArrayList<Post> searchByRecentFiltered(Date date, String Tag);


  /**
   * Gets all posts from recently made to least recently made
   * @param date The current date
   * @return a list of posts sorted by date
   */
  ArrayList<Post> searchByRecent(Date date);

  /**
   * Adds a specified post to the database
   * @param post: The desired added post
   * @return the post which was added
   */
  Post addPost(Post post);

  void updatePost(Post post);

  /**
   * Removes the specified post from the database
   * @param post a post to be removed
   */
  void deletePost(Post post);

  List<Post> findByIds(List<String> ids);
}


