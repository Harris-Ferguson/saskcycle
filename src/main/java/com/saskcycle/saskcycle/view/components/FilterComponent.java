package com.saskcycle.saskcycle.view.components;

import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;

public class FilterComponent extends VerticalLayout {

  private H1 heading;

  private Select<String> sortSelect;
  private Select<String> useSelect;
  private Select<String> postChoice;

  private CheckboxGroup<String> includeGroup;
  private CheckboxGroup<String> excludeGroup;

  /** Constructs the panel which contains all visual options for filtering posts */
  private void FilterComponent() {}
}
