package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout /*implements HasUrlParameter<String>, AfterNavigationObserver*/{

    public MapView() {
        this.setHeight("100%");
        this.setWidth("100%");

        // Search bar and corresponding submit button
        HorizontalLayout startingAddress = new HorizontalLayout();


        Label startLabel = new Label("Starting Address: ");

        TextField text = new TextField();
        text.setPlaceholder("Input your starting address (EG 123 4th street East)");
        text.setWidth("500px");
        text.setId("text");

        Button submitStart = new Button("Submit Address");
        submitStart.setId("submitStart");
        submitStart.setWidth("150px");
        startingAddress.add(startLabel, text, submitStart);
        startingAddress.setAlignItems(Alignment.CENTER);

        // Information for bottom target address Hbox
        HorizontalLayout targetAddress = new HorizontalLayout();

        Label dataLabel = new Label("Approximate post address: ");

        TextField data = new TextField();
        data.setId("sCoords");
        data.setValue("s7n0p8");
        data.setReadOnly(true);

        targetAddress.add(dataLabel, data);


        add(startingAddress);

        // Map view
        MapComponent map = new MapComponent(52.118, -106.643, "Label");


        add(map);
        add(targetAddress);
    }


    // Reimplement tomorrow

//    @Override
//    public void setParameter(BeforeEvent beforeEvent, String s) {
//        id = s;
//    }
//
//    @Override
//    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
//        Post post = SC.getPostByID(id);
//        lon = post.getLongitude();
//        lat = post.getLatitude();
//    }
//}
}
