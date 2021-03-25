package com.saskcycle.DAO;

// Created with guidance from the following tutorial:
// https://medium.com/@ahmodadeola/creating-restful-apis-with-spring-boot-2-and-mongodb-3bba937cd438

import com.saskcycle.model.Account;
import com.saskcycle.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface UserDAOInterface {

  List<Account> AllAccounts();

  Optional<Account> searchByID(String id);

  Account searchByName(String name);

  Account searchByEmail(String email);

  Account updateAccount(Account account);

  /**
   * Inserts an account into the database
   *
   * @param account Account object to insert into the database
   * @return The account inserted into the database
   */
  Account addAccount(Account account);

  void deleteAccount(Account account);

  ArrayList<Post> getPosts(Account account);

 ArrayList<String> getWishlist(Account account);

  void removePost(Post post, Account account);

  void addPost(Post post, Account account);

  /**
   * Check if a given account exists
   *
   * @param account Account object
   * @return true if the Account already exists, false if not
   */
  boolean accountExists(Account account);

  /**
   * Adds a new user to the system based on username, email, and password
   *
   * @param username username for the new user
   * @param email email for the new user
   * @param password password for the new user
   */
  Account register(String username, String email, String password);

  /**
   * Adds a new user with Organization permissions
   *
   * @param username
   * @param email
   * @param password
   * @return
   */
  Account registerOrg(String username, String email, String password);

  void addToUserPosts(Post post, Account account);
}
