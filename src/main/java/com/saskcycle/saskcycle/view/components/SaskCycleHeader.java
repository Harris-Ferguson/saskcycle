package com.saskcycle.saskcycle.view.components;

import com.saskcycle.saskcycle.security.SecurityUtils;
import com.saskcycle.saskcycle.view.uiViews.EventCreateView;
import com.saskcycle.saskcycle.view.uiViews.MainView;
import com.saskcycle.saskcycle.view.uiViews.PostView;
import com.saskcycle.saskcycle.view.uiViews.SettingsView;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public class SaskCycleHeader extends HorizontalLayout {

  /** Builds the header component at the top of each page in SaskCycle app */
  public SaskCycleHeader() {

    // Sets up the SaskCycle logo on the header and calls it 'logo' so it can be styled by CSS
    H1 logo = new H1("SaskCycle");
    logo.addClassName("logo");

    // Creates a hamburger menu that can store the account options
    DrawerToggle drawerToggle = new DrawerToggle();
    drawerToggle.addClassName("drawerToggle");

    Button searchButton = new Button("Search all listings");
    searchButton.addClassName("header-button");
    searchButton.addClickListener(
        e -> searchButton.getUI().ifPresent(ui -> ui.navigate("results")));
    searchButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

    setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
    setWidth("100%");

    addClassName("header");
    add(drawerToggle, logo, searchButton, createSignInButtons());
  }

  /**
   * Creates a log in and register buttons
   *
   * @return HorizontalLayout containing the two buttons
   */
  private HorizontalLayout createSignInButtons() {
    Anchor loginButton = new Anchor();
    Anchor signonButton = new Anchor("/register");
    signonButton.setText("Sign Up");
    loginButton.setClassName("register-button");
    signonButton.setClassName("register-button");
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
