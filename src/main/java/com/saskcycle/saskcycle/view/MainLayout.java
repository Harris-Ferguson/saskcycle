package com.saskcycle.saskcycle.view;

import com.saskcycle.saskcycle.view.components.SaskCycleHeader;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.PageTitle;


@CssImport("./styles/shared-styles.css")
@PageTitle("SaskCycle")
public class MainLayout extends SaskCycleLayout {

    SaskCycleHeader saskCycleHeader;

    public MainLayout() {

        super();
        createHeader();
//        createDrawer();
    }

    /**
     * Constructs the header at the top of the web page
     */
    private void createHeader() {

        saskCycleHeader = new SaskCycleHeader();
        addToNavbar(new SaskCycleHeader());
    }

//    /**
//     * Constructs the drawer feature, denoted by the hamburger menu
//     */
//    private void createDrawer() {
//        RouterLink accountLink = new RouterLink("Main", MainView.class);
//        RouterLink postLink = new RouterLink("Posts", PostView.class);
//        RouterLink settingsLink = new RouterLink("Settings", SettingsView.class);
//        accountLink.setHighlightCondition(HighlightConditions.sameLocation());
//
//        addToDrawer(new VerticalLayout(accountLink, postLink, settingsLink));
//    }
}

