package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout /*implements HasUrlParameter<String>, AfterNavigationObserver*/{

    public MapView() {
        this.setHeight("100%");
        this.setWidth("100%");

        // Search bar, transportation selection, and corresponding submit button
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

        // Selection for method of transportation (default walking)
        HorizontalLayout transSelectLayout = new HorizontalLayout();
        Label transLabel = new Label("Method of Transportation: ");


        Select<String> transSelect = new Select<>("Walking", "Cycling", "Transit", "Driving");
        transSelect.setValue("Walking");
        transSelect.setId("trans");

        transSelectLayout.add(transLabel, transSelect);
        transSelectLayout.setAlignItems(Alignment.CENTER);

        //todo: Implement departure time
//        TimePicker tp = new TimePicker();
//        tp.
        //todo: Find way to display route back information

        // Map view
        MapComponent map = new MapComponent(52.118, -106.643, "Label");

        // Information for bottom target address Hbox
        HorizontalLayout targetAddress = new HorizontalLayout();

        Label dataLabel = new Label("Approximate post address: ");

        TextField data = new TextField();
        data.setId("sCoords");
        data.setValue("918 11th street east saskatoon");
        data.setReadOnly(true);

        targetAddress.add(dataLabel, data);

        // Add components to view
        add(startingAddress, transSelectLayout, map, targetAddress);

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
