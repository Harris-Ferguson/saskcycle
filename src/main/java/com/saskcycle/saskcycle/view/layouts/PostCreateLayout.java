package com.saskcycle.saskcycle.view.layouts;

import com.saskcycle.saskcycle.view.components.SaskCycleHeader;


public class PostCreateLayout extends SaskCycleLayout {

  /**
   * Constructs the layout of the post creation page which allows the user to make and add a post to the system
   */
  public PostCreateLayout() {
    super();
    addToNavbar(new SaskCycleHeader());
  }
}
