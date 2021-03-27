package com.saskcycle.saskcycle.view.layouts;

import com.saskcycle.saskcycle.security.SecurityUtils;
import com.saskcycle.saskcycle.view.uiViews.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;

public abstract class SaskCycleLayout extends AppLayout {

  public SaskCycleLayout() {
    createDrawer();
  }

  /** Constructs the drawer feature, denoted by the hamburger menu */
  private void createDrawer() {
    //RouterLink accountLink = new RouterLink("Main", MainView.class);
    RouterLink postLink = new RouterLink("Posts", PostView.class);
    RouterLink settingsLink = new RouterLink("Settings", SettingsView.class);
    RouterLink eventCreateLink = new RouterLink();
    if(SecurityUtils.isOrgUser()){
      eventCreateLink = new RouterLink("Events", EventDeleteView.class);
    }
    RouterLink wishListLink = new RouterLink("Wishlist", WishlistView.class);
    //accountLink.setHighlightCondition(HighlightConditions.sameLocation());

    postLink.addClassName("nav-link");
    settingsLink.addClassName("nav-link");
    eventCreateLink.addClassName("nav-link");
    wishListLink.addClassName("nav-link");



    addToDrawer(new VerticalLayout(postLink, settingsLink,wishListLink,eventCreateLink));
    setDrawerOpened(false);
  }
}
