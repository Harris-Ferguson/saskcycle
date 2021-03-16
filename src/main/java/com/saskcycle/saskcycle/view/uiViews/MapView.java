package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.JavaScript;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout {

    public MapView() {
        this.setHeight("100%");
        this.setWidth("100%");
        add(new MapComponent());
    }

    public Div createMap(){
        Div map = new Div();
        map.setId("map");
        map.setHeight("100%");
        return map;
    }
}
