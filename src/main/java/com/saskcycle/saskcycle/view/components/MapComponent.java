package com.saskcycle.saskcycle.view.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@JavaScript("./src/js/map-script.js")
public class MapComponent extends Div {

    public MapComponent() {
        this.setHeight("100%");
        this.setWidth("100%");
        this.setId("map");
    }
}
