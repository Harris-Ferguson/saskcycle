package com.saskcycle.saskcycle.view.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
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
        displayMarker(-56.1, -106.6, "MapComponentCaller");
    }

    public void displayMarker(double lat, double lon, String name){
        getElement().executeJs("addMarker($0, $1, $2)", lat, lon, name);
    }
}
