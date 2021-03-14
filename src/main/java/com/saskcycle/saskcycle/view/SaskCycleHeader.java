package com.saskcycle.saskcycle.view;

import com.saskcycle.saskcycle.security.SecurityUtils;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class SaskCycleHeader extends HorizontalLayout {

  /** å Builds the header component at the top of each page in SaskCycle app */
  public SaskCycleHeader() {

    // Sets up the SaskCycle logo on the header and calls it 'logo' so it can be styled by CSS
    H1 logo = new H1("SaskCycle");
    logo.addClassName("logo");

    // Creates a search bar located in the header
    TextField searchBar = new TextField();
    searchBar.setPlaceholder("Search for anything");
    searchBar.addClassName("searchBar");

    // Creates a hamburger menu that can store the account options
    DrawerToggle drawerToggle = new DrawerToggle();
    drawerToggle.addClassName("drawerToggle");


    // Create the signup / signin buttons
    HorizontalLayout signinButtons = createSignInButtons();

    setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    setWidth("100%");

    addClassName("header");
    add(drawerToggle, logo, searchBar, signinButtons);
  }


  /** Constructs the drawer feature, denoted by the hamburger menu */
  private VerticalLayout createDrawer() {
    RouterLink accountLink = new RouterLink("Main", MainView.class);
    RouterLink postLink = new RouterLink("Posts", PostView.class);
    RouterLink settingsLink = new RouterLink("Settings", SettingsView.class);
    accountLink.setHighlightCondition(HighlightConditions.sameLocation());

    return new VerticalLayout(accountLink, postLink, settingsLink);
  }

  /**
   * Creates a log in and register button
   *
   * @return HorizontalLayout containing two buttons
   */
  private HorizontalLayout createSignInButtons() {
    Anchor loginButton = new Anchor();
    Anchor signonButton = new Anchor("/register");
    signonButton.setText("Sign Up");
    loginButton.setClassName("header-button");
    signonButton.setClassName("header-button");
    if (SecurityUtils.isUserLoggedIn()) {
      loginButton.setHref("/logout");
      loginButton.setText("Logout");
      signonButton.setVisible(false);
    } else {
      loginButton.setHref("/login");
      loginButton.setText("Log In");
      signonButton.setVisible(true);
    }
    return new HorizontalLayout(loginButton, signonButton);
  }
}
