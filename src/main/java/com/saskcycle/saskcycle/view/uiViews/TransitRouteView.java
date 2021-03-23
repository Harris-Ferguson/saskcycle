package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.RouteComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route(value = "route", layout = MainLayout.class)
public class TransitRouteView extends VerticalLayout {

    public TransitRouteView() {
        this.setHeight("100%");
        this.setWidth("100%");

        // Search bar and corresponding submit button
        HorizontalLayout StartingAddress = new HorizontalLayout();

        TextField text = new TextField();
        text.setPlaceholder("Input your starting address (EG 123 4th street East)");
        text.setWidth("500px");
        text.setId("text");

        Button submitStart = new Button("Submit Address");
        submitStart.setId("submitStart");
        submitStart.setWidth("150px");
        StartingAddress.add(text, submitStart);

        StartingAddress.setAlignItems(Alignment.CENTER);
        add(StartingAddress);

        // Corresponding target postal code
//        String end = "saskatoon, sk";

//        submitStart.addClickListener(event -> {start = startText.getValue()});

        // Map view
        RouteComponent route = new RouteComponent(52.118, -106.643, "Label");
        add(route);



    }


}
