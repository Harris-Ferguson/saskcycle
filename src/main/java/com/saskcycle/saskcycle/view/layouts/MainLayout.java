package com.saskcycle.saskcycle.view.layouts;

import com.saskcycle.saskcycle.view.components.SaskCycleHeader;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.PageTitle;

@CssImport("./styles/shared-styles.css")
@PageTitle("SaskCycle")
public class MainLayout extends SaskCycleLayout {

    SaskCycleHeader saskCycleHeader;

    /**
     * Sets up the layout of the main page
     */
    public MainLayout() {

        super();
        createHeader();
    }

    /**
     * Constructs the header at the top of the web page
     */
    private void createHeader() {

        saskCycleHeader = new SaskCycleHeader();
        addToNavbar(new SaskCycleHeader());
    }
}
