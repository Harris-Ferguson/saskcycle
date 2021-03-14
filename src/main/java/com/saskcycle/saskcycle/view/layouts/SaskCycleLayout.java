package com.saskcycle.saskcycle.view.layouts;


import com.saskcycle.saskcycle.view.uiViews.MainView;
import com.saskcycle.saskcycle.view.uiViews.PostView;
import com.saskcycle.saskcycle.view.uiViews.SettingsView;
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
    RouterLink accountLink = new RouterLink("Main", MainView.class);
    RouterLink postLink = new RouterLink("Posts", PostView.class);
    RouterLink settingsLink = new RouterLink("Settings", SettingsView.class);
    accountLink.setHighlightCondition(HighlightConditions.sameLocation());

    addToDrawer(new VerticalLayout(accountLink, postLink, settingsLink));
  }
}
