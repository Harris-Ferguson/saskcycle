package com.saskcycle.saskcycle.view.layouts;

import com.saskcycle.saskcycle.view.components.SaskCycleHeader;

public class SearchResultsLayout extends SaskCycleLayout {

    /**
     * Constructs the layout of the page which contains the listings in the DB and the visual options for filtering listings
     */
    public SearchResultsLayout() {
        super();
        addToNavbar(new SaskCycleHeader());
    }
}
