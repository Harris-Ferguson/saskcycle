package com.saskcycle.DAO;

import com.saskcycle.model.Account;
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
}
