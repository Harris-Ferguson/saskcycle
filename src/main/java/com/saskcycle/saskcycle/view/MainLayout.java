package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;


@CssImport("./styles/shared-styles.css")
@PageTitle("SaskCycle")
public class MainLayout extends AppLayout {

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    /**
     * Constructs the header at the top of the web page
     */
    private void createHeader() {

        addToNavbar(new SaskCycleHeader());
    }

    /**
     * Constructs the drawer feature, denoted by the hamburger menu
     */
    private void createDrawer() {
        RouterLink accountLink = new RouterLink("Main", MainView.class);
        RouterLink postLink = new RouterLink("Posts", PostView.class);
        RouterLink settingsLink = new RouterLink("Settings", SettingsView.class);
        accountLink.setHighlightCondition(HighlightConditions.sameLocation());

        addToDrawer(new VerticalLayout(accountLink, postLink, settingsLink));
    }
}

