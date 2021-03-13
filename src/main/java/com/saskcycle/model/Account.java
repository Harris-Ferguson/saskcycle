package com.saskcycle.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

@Document(collection = "User Accounts")
public class Account extends User {

  /* --------- Attributes -------------*/

  @Id private String id;

  private String email;

  private final Feed wishlish;

  private final Feed posts;

  private double userRating;

  private final ArrayList<Notification> notifications;

  /* --------- Methods -------------*/

  public Account(
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      String id,
      String email) {

    // Calls super to initialize other values
    super(username, password, authorities);
    this.id = id;
    this.email = email;
    this.userRating = 0;
    this.notifications = new ArrayList<>();
    this.wishlish = new Feed();
    this.posts = new Feed();
  }

  /* --------- Getters -------------*/

  public Feed getPosts() {
    return posts;
  }

  public double getUserRating() {
    return userRating;
  }

  public void setUserRating(double userRating) {
    this.userRating = userRating;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getId() {
    return id;
  }

  /* --------- Setters -------------*/

  public void setId(String id) {
    this.id = id;
  }

  public ArrayList<Notification> getNotifications() {
    return notifications;
  }

  public Feed getWishlish() {
    return wishlish;
  }
}
