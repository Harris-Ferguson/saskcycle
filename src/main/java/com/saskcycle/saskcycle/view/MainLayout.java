package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouterLink;


@CssImport("./styles/shared-styles.css")
@PageTitle("SaskCycle")
public class MainLayout extends AppLayout {

    private TextField searchBar;

    public MainLayout() {
        createHeader();
        createDrawer();
    }

    /**
     * Constructs the header at the top of the web page
     */
    private void createHeader() {

        // Sets up the SaskCycle logo on the header and calls it 'logo' so it can be styled by CSS
        H1 logo = new H1("SaskCycle");
        logo.addClassName("logo");

        // Creates a search bar located in the header
        searchBar = new TextField();
        searchBar.setPlaceholder("Search for anything");
        searchBar.addClassName("searchBar");

        //Creates a hamburger menu that can store the account options
        DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("drawerToggle");
        HorizontalLayout header = new HorizontalLayout(drawerToggle, logo, searchBar);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.setWidth("100%");
        header.addClassName("header");

        addToNavbar(header);

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

