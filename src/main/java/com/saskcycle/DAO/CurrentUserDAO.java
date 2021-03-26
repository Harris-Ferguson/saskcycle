package com.saskcycle.DAO;

import com.saskcycle.model.Account;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;
import com.saskcycle.model.UserNotificationSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDAO implements CurrentUserDAOInterface {

  @Autowired private UserDAOInterface userDAO;

  @Override
  public Account updateSettings(boolean wantsEmail, boolean wantsText) {
    UserNotificationSettings settings = new UserNotificationSettings(wantsEmail, wantsText);
    Account account = getCurrentAccount();
    account.setNotificationSettings(settings);
    return userDAO.updateAccount(account);
  }

  @Override
  public boolean getEmailSetting() {
    Account account = getCurrentAccount();
    return account.getNotificationSettings().wantsEmail();
  }

  @Override
  public String getEmail() {
    Account account = getCurrentAccount();
    return account.getEmail();
  }

  @Override
  public boolean getTextSetting() {
    Account account = getCurrentAccount();
    return account.getNotificationSettings().wantsText();
  }

  /**
   * Gets the current user, I.E the user who is currently making a given request
   *
   * @return the current user as an Account object
   */
  public Account getCurrentAccount() {
    UserDetails user =
        (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return userDAO.searchByName(user.getUsername());
  }

  /**
   * Adds an ID to a logged in user's wishlist
   * @param id a string representation the ID of a post in the database
   */
  public void updateWishlist(String id)
  {
    Account account = this.getCurrentAccount();
    account.getWishlist().add(id);
    userDAO.updateAccount(account);
  }

  public void updatePosts(Post p) {
      Account account = this.getCurrentAccount();
      account.getPostIds().add(p.id);
      userDAO.updateAccount(account);

  }

  @Override
  public void deleteEvent(Event saskcycleEvent) {
    Account account = this.getCurrentAccount();
    account.getPostIds().remove(saskcycleEvent.getId());
    userDAO.updateAccount(account);

  }

  public void updateCreatedPostList(String id){
    Account account = this.getCurrentAccount();
    account.getPostIds().add(id);
    userDAO.updateAccount(account);
  }
}
