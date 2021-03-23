package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "route", layout = MainLayout.class)
public class TransitRouteView extends VerticalLayout {

    public TransitRouteView() {
        this.setHeight("100%");
        this.setWidth("100%");
        MapComponent map = new MapComponent(52.118, -106.643, "Label");
        add(map);
    }
}
