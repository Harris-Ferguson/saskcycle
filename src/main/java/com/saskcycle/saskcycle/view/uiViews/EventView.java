package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.layouts.EventLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

// @Route("events")
@Route(value = "events", layout = EventLayout.class)
public class EventView extends VerticalLayout {

  public EventView() {

    add(new H1("Event view"));
  }
}
