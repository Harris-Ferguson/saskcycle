package com.saskcycle.saskcycle.view.components;

import com.saskcycle.saskcycle.view.MainView;
import com.saskcycle.saskcycle.view.PostView;
import com.saskcycle.saskcycle.view.SettingsView;
import com.vaadin.flow.component.applayout.DrawerToggle;
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

    /**
     * Builds the header component at the top of each page in SaskCycle app
     */
    public SaskCycleHeader() {

        // Sets up the SaskCycle logo on the header and calls it 'logo' so it can be styled by CSS
        H1 logo = new H1("SaskCycle");
        logo.addClassName("logo");

        // Creates a search bar located in the header
        TextField searchBar = new TextField();
        searchBar.setPlaceholder("Search for anything");
        searchBar.addClassName("searchBar");

        //Creates a hamburger menu that can store the account options
        DrawerToggle drawerToggle = new DrawerToggle();
        drawerToggle.addClassName("drawerToggle");

        NativeButton searchButton = new NativeButton("Search");
        searchButton.addClickListener(e -> searchButton.getUI().ifPresent(ui -> ui.navigate("results")));

        setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        setWidth("100%");

        addClassName("header");
        add(drawerToggle, logo, searchBar, searchButton);


    }

    /**
     * Constructs the drawer feature, denoted by the hamburger menu
     */
    private VerticalLayout createDrawer() {
        RouterLink accountLink = new RouterLink("Main", MainView.class);
        RouterLink postLink = new RouterLink("Posts", PostView.class);
        RouterLink settingsLink = new RouterLink("Settings", SettingsView.class);
        accountLink.setHighlightCondition(HighlightConditions.sameLocation());

        return new VerticalLayout(accountLink, postLink, settingsLink);
    }


}
