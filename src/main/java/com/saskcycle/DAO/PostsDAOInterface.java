package com.saskcycle.DAO;

import com.saskcycle.model.Post;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public interface PostsDAOInterface {

  List<Post> AllPosts();

  Post searchByID(String id);

  ArrayList<Post> searchByKeyword(String keyword);

  ArrayList<Post> searchByKeywordFiltered(String keyword, String tag);

  ArrayList<Post> searchByLocation(String location);

  ArrayList<Post> searchByLocationFiltered(String location);

  ArrayList<Post> searchByRecentFiltered(Date date, String Tag);

  ArrayList<Post> searchByRecent(Date date);

  Post addPost(Post post);

  void deletePost(Post post);
}
