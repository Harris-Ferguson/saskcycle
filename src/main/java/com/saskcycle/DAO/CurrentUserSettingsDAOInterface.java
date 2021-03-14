package com.saskcycle.DAO;

import com.saskcycle.model.Account;

/** An interface for operations on the currently logged in user */
public interface CurrentUserSettingsDAOInterface {
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
}
