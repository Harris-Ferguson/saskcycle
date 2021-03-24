package com.saskcycle.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Document(collection = "User Accounts")
public class Account extends User {

  /* --------- Attributes -------------*/

  @Id private String id;

  private String email;

  private String role;

  private ArrayList<String> wishlist;

  private ArrayList<String> posts;

  private double userRating;

  private ArrayList<Notification> notifications;

  private UserNotificationSettings notificationSettings =
      new UserNotificationSettings(false, false);

  /* --------- Methods -------------*/

  public Account(
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      String id,
      String email,
      String role,
      ArrayList<String> wishlist,
      ArrayList<String> posts,
      double userRating,
      ArrayList<Notification> notifications) {
    super(username, password, authorities);
    this.id = id;
    this.email = email;
    this.role = role;
    this.wishlist = wishlist;
    this.posts = posts;
    this.userRating = userRating;
    this.notifications = notifications;
  }

  @PersistenceConstructor
  /**
   * Constructor for Account which is Used by the UserAccountRepo interface to instance objects when
   * it is adding and removing from the Data Repository
   */
  public Account(
      String username,
      String password,
      String role,
      boolean enabled,
      boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities,
      String id,
      String email) {

    // Calls super to initialize other values
    super(
        username,
        password,
        enabled,
        accountNonExpired,
        credentialsNonExpired,
        accountNonLocked,
        authorities);
    this.id = id;
    this.role = role;
    this.email = email;
    this.userRating = 0;
    this.notifications = new ArrayList<>();
    this.wishlist = new ArrayList<>();
    this.posts = new ArrayList<>();
  }

    public static Account makeAccountFromUser(UserDetails user, String email) {
        return new Account(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities(),
                Integer.toString(user.hashCode()),
                email,
                "USER",
                new ArrayList<>(),
                new ArrayList<>(),
                0.0,
                new ArrayList<Notification>()
        );
    }

  /* --------- Getters and Setters -------------*/

  public ArrayList<String> getPosts() {
    return posts;
  }

  public double getUserRating() {
    return userRating;
  }

  public void setUserRating(double userRating) {
    this.userRating = userRating;
  }

  public String getRole() {
    return role;
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

  public void setId(String id) {
    this.id = id;
  }

  public ArrayList<Notification> getNotifications() {
    return notifications;
  }

  public ArrayList<String> getWishlist() {
    return wishlist;
  }

  public UserNotificationSettings getNotificationSettings() {
    return notificationSettings;
  }

  public void setNotificationSettings(UserNotificationSettings notificationSettings) {
    this.notificationSettings = notificationSettings;
  }

}
