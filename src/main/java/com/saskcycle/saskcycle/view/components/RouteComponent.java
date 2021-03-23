package com.saskcycle.saskcycle.view.components;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;

@JsModule("./src/js/route-script.js")
public class RouteComponent extends Div {

    private double lat;
    private double lon;
    private String name;

    /**
     * Instance a blank map
     */
    public RouteComponent() {
        initializeMap();
    }

    /**
     * Instance a map with a marker at a given position
     * @param lat Double Latitude
     * @param lon Double Longitude
     * @param name Label
     */
    public RouteComponent(double lat, double lon, String name) {
        initializeMap();
        this.lat = lat;
        this.lon = lon;
        this.name = name;
    }

    private void initializeMap(){
        this.setHeight("100%");
        this.setWidth("100%");
        this.setId("map");
    }

    /**
     * Add a marker to a map
     * @param lati latitude
     * @param longi longitude
     * @param markerName name for the marker
     */
    public void addMarker(double lati, double longi, String markerName){
        this.lat = lati;
        this.lon = longi;
        this.name = markerName;
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        getElement().executeJs("window.addMarker($0, $1, $2)", lat, lon, name);
    }
}
