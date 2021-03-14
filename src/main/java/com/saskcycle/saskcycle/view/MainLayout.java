package com.saskcycle.saskcycle.view;

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

  /** Constructs the header at the top of the web page */
  private void createHeader() {

    saskCycleHeader = new SaskCycleHeader();
    addToNavbar(new SaskCycleHeader());
  }

}
