package com.saskcycle.model;

/** Class representing a users notifications settings */
public class UserNotificationSettings {

  private boolean wantsEmail = false;

  private boolean wantsText = false;

  public UserNotificationSettings(boolean wantsEmail, boolean wantsText) {
    this.wantsEmail = wantsEmail;
    this.wantsText = wantsText;
  }

  public boolean wantsEmail() {
    return wantsEmail;
  }

  public void setWantsEmail(boolean wantsEmail) {
    this.wantsEmail = wantsEmail;
  }

  public boolean wantsText() {
    return wantsText;
  }

  public void setWantsText(boolean wantsText) {
    this.wantsText = wantsText;
  }
}
