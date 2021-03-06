package com.saskcycle.saskcycle.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("events")
public class EventView extends VerticalLayout {

    public EventView() {
        add(new H1("Event view"));
    }
}
