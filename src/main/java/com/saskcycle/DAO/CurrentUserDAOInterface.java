package com.saskcycle.DAO;

import com.saskcycle.model.Account;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;

/** An interface for operations on the currently logged in user */
public interface CurrentUserDAOInterface {
  /**
   * Update a users Notification Settings
   *
   * @param wantsEmail does the user want emails?
   * @param wantsText does the user want texts?
   * @return the updated account
   */
  Account updateSettings(boolean wantsEmail, boolean wantsText);

  /**
   * Get the current users email setting
   *
   * @return true / false if the user wants emails
   */
  boolean getEmailSetting();

  /**
   * Get the current users email
   *
   * @return email string
   */
  String getEmail();

  Account getCurrentAccount();

  /**
   * Get the current users text setting
   *
   * @return true / false if the user wants texts
   */
  boolean getTextSetting();

  public void updateWishlist(String id);

  public void updatePosts(Post post);

  void deleteEvent(Event saskcycleEvent);

  public void updateCreatedPostList(String id);

  public void removeFromWishlist(String id);

  public void removePost(String id);
}
