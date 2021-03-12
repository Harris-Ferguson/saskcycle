package com.saskcycle.saskcycle.view.auth;

import com.saskcycle.DAO.UserDAOInterface;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@Route("register")
@PageTitle("Sask Cycle | Register")
public class RegisterView extends Composite {

  @Autowired private UserDAOInterface userDao;

  @Override
  protected Component initContent() {
    TextField username = new TextField("Username");
    TextField email = new TextField("Email");
    PasswordField password1 = new PasswordField("Password");
    PasswordField password2 = new PasswordField("Confirm Password");
    return new VerticalLayout(
        new H2("Register"),
        username,
        email,
        password1,
        password2,
        new Button(
            "Register",
            event ->
                register(
                    username.getValue(),
                    email.getValue(),
                    password1.getValue(),
                    password2.getValue())));
  }

  /**
   * Passes a registration to the Account Service
   *
   * @param username username of the new account
   * @param email email of a new account
   * @param password1 password of the new account
   * @param password2 confirm password field of the new account
   */
  private void register(String username, String email, String password1, String password2) {
    if (credentialsAreValid(username, password1, password2)) {
      try {
        userDao.register(username, email, password1);
        Notification.show("Registered!");
        UI.getCurrent().navigate("login");
      } catch (IllegalArgumentException e) {
        Notification.show("Account already exists");
        UI.getCurrent().navigate("login");
      }
    }
  }

  /**
   * Check if the entered fields are valid
   *
   * @param username username field value
   * @param password1 password field
   * @param password2 confirm password field
   * @return true / false if the credentials are valid
   */
  private boolean credentialsAreValid(String username, String password1, String password2) {
    if (username.trim().isEmpty()) {
      Notification.show("Enter a username");
      return false;
    } else if (password1.trim().isEmpty()) {
      Notification.show("Enter a password");
      return false;
    } else if (!password1.equals(password2)) {
      Notification.show("Passwords do not match");
      return false;
    }
    return true;
  }
}
