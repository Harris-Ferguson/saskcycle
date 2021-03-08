package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class SaskCycleHeader extends HorizontalLayout {

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

        setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        setWidth("100%");

        addClassName("header");
        add(drawerToggle, logo, searchBar);


    }


}
