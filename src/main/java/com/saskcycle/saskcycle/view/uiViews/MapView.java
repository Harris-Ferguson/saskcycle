package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.*;

import java.time.Duration;

@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    String postalCode;

    boolean refresh = true;

    TextField data;

    public MapView() {
        this.setHeight("100%");
        this.setWidth("100%");

        // Search bar, transportation selection, and corresponding submit button
        HorizontalLayout startingAddress = new HorizontalLayout();

        Label startLabel = new Label("Starting Address: ");

        TextField text = new TextField();
//        getParameterMap().get("param1");



        text.setPlaceholder("Input your starting address (EG 123 4th street East)");
        text.setWidth("500px");
        text.setId("text");

        Button submitStart = new Button("Get Route");
        submitStart.setId("submitStart");
        submitStart.setWidth("150px");
        startingAddress.add(startLabel, text, submitStart);
        startingAddress.setAlignItems(Alignment.CENTER);

        // Selection for method of transportation (default walking)
        HorizontalLayout transSelectLayout = new HorizontalLayout();
        Label transLabel = new Label("Method of Transportation: ");


        Select<String> transSelect = new Select<>("Walking", "Cycling", "Transit", "Driving");
        transSelect.setValue("Walking");
        transSelect.setLabel("Method of Transportation:");
        transSelect.setId("trans");


//        todo: Implement departure time

        HorizontalLayout timeLayout = new HorizontalLayout();

        RadioButtonGroup<String> timeButtons = new RadioButtonGroup<>();
        timeButtons.setId("timeButton");
        timeButtons.setItems("Now", "Custom Time");
        timeButtons.setValue("Now");
        timeButtons.setLabel("Departure Time:");

        TimePicker tp = new TimePicker();
        tp.setId("timePick");
        tp.setStep(Duration.ofMinutes(15));
        tp.setReadOnly(true);

        timeButtons.addValueChangeListener(event ->{
            if (timeButtons.getValue().equals("Now")) {
                tp.setReadOnly(true);
                tp.clear();
            }
            else tp.setReadOnly(false);
        });

        timeLayout.add(timeButtons);


        transSelectLayout.add(transSelect,  timeButtons, tp);
        transSelectLayout.setAlignItems(Alignment.CENTER);



        // Map view
        MapComponent map = new MapComponent(52.118, -106.643, "Label");

        // Information for bottom target address Hbox
        HorizontalLayout targetAddress = new HorizontalLayout();


        Label dataLabel = new Label("Approximate post address: ");

        data = new TextField();
        data.setId("sCoords");
        data.setReadOnly(true);


        targetAddress.add(dataLabel, data);

//        System.out.println(map.getId());
        // Add components to view
        add(startingAddress, transSelectLayout, map, targetAddress);


    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String pCode) {
        postalCode = pCode;
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        data.setValue(postalCode/*"918 11th street east saskatoon"*/);

    }

//    @Override
//    public void afterNavigation(AfterNavigationEvent afterNavigationEvent) {
////        DataProvider.
//    }
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
//}
