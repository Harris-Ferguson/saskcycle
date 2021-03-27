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

  private ArrayList<String> wishlist;

  private ArrayList<String> postIds;

  private ArrayList<String> eventIds;

  private UserNotificationSettings notificationSettings =
      new UserNotificationSettings(false, false);

  /* --------- Methods -------------*/

  public Account(
      String username,
      String password,
      Collection<? extends GrantedAuthority> authorities,
      String id,
      String email,
      ArrayList<String> wishlist,
      ArrayList<String> postIds,
      ArrayList<String> eventIds) {
    super(username, password, authorities);
    this.id = id;
    this.email = email;
    this.wishlist = wishlist;
    this.postIds = postIds;
    this.eventIds = eventIds;
  }

  @PersistenceConstructor
  /**
   * Constructor for Account which is Used by the UserAccountRepo interface to instance objects when
   * it is adding and removing from the Data Repository
   */
  public Account(
      String username,
      String password,
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
    this.email = email;
    this.wishlist = new ArrayList<>();
    this.postIds = new ArrayList<>();
    this.eventIds = new ArrayList<>();
  }

    public static Account makeAccountFromUser(UserDetails user, String email) {
        return new Account(
                user.getUsername(),
                user.getPassword(),
                user.getAuthorities(),
                Integer.toString(user.hashCode()),
                email,
                new ArrayList<String>(),
                new ArrayList<String>(),
                new ArrayList<String>()
        );
    }

  /* --------- Getters and Setters -------------*/

  public ArrayList<String> getPostIds() {
    return postIds;
  }

  public ArrayList<String> getEventIds()
  {
    return eventIds;
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
