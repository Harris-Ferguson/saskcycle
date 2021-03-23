package com.saskcycle.DAO;

import com.saskcycle.model.Post;
import com.saskcycle.repo.PostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PostsDAO implements PostsDAOInterface {

  /* --------- Attributes --------- */

  @Autowired private final PostsRepo PR;


  /* ---------   Methods  ---------- */
  public PostsDAO(PostsRepo repo) {
    this.PR = repo;
  }

  @Override
  public List<Post> AllPosts() {
    // Returns all posts from the Posts MongoDB database
    return PR.findAll();
  }

  @Override
  public Post searchByID(String id) {
    return null;
  }

  @Override
  public ArrayList<Post> searchByKeyword(String keyword) {
    return null;
  }

  @Override
  public ArrayList<Post> searchByKeywordFiltered(String keyword, String tag) {
    return null;
  }

  @Override
  public ArrayList<Post> searchByLocation(String location) {
    return null;
  }

  @Override
  public ArrayList<Post> searchByLocationFiltered(String location) {
    return null;
  }

  @Override
  public ArrayList<Post> searchByRecentFiltered(Date date, String Tag) {
    return null;
  }

  @Override
  public ArrayList<Post> searchByRecent(Date date) {
    return null;
  }

  @Override
  public Post addPost(Post post) {
  // Inserts a post to the Posts MongoDB Database
  return PR.insert(post);
  }

  @Override
  public void deletePost(Post post) {
    // Deletes a post from the Posts MongoDB Database
    PR.delete(post);
  }

  public ArrayList<Post> getFilteredPosts() {

    return null;
  }
}
