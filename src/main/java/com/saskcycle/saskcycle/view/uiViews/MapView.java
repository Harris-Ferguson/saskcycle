package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.*;

import java.time.Duration;


@Route(value = "map", layout = MainLayout.class)
public class MapView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    /* --------- Attributes ------------ */

    // Constant postal code from previous view
    String postalCode;
    // Textfield containing postal code
    TextField pCode;

    /* ----------- Methods ------------- */

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

        // Time selection widget
        HorizontalLayout timeLayout = new HorizontalLayout();

        RadioButtonGroup<String> timeButtons = new RadioButtonGroup<>();
        timeButtons.setId("timeButton");
        timeButtons.setItems("Now", "Custom Time");
        timeButtons.setValue("Now");
        timeButtons.setLabel("Departure Time:");

        DateTimePicker dtp = new DateTimePicker();
        dtp.setId("timePick");
        dtp.setDatePlaceholder("Date");
        dtp.setTimePlaceholder("Time");
        dtp.setStep(Duration.ofMinutes(15));
        dtp.setReadOnly(true);

        // Radio button selection for time Picker clears and disallows custom departure entry when set to "Now"
        timeButtons.addValueChangeListener(event -> {
            if (timeButtons.getValue().equals("Now")) {
                dtp.setReadOnly(true);
                dtp.clear();
            } else dtp.setReadOnly(false);
        });

        timeLayout.add(timeButtons);

        transSelectLayout.add(transSelect, timeButtons, dtp);
        transSelectLayout.setAlignItems(Alignment.CENTER);

        // Map view
        MapComponent map = new MapComponent();

        // Information for bottom target postal cod
        HorizontalLayout targetAddress = new HorizontalLayout();

        Label dataLabel = new Label("Approximate Post Address*");

        pCode = new TextField();
        pCode.setId("sCoords");
        pCode.setReadOnly(true);

        Text warning = new Text("  *Destination addresses are approximate to protect user confidentiality." +
                "\nFor full destination address, contact the user.");

        targetAddress.add(dataLabel, pCode, warning);

        add(startingAddress, transSelectLayout, map, targetAddress);
    }

    @Override
    public void setParameter(BeforeEvent beforeEvent, String pCode) {
        postalCode = pCode;
    }


    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        pCode.setValue(postalCode);
    }
}
