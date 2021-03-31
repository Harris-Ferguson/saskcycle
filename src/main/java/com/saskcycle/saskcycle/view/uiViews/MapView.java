package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.components.MapComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
@PageTitle("SaskCycle | Plan a route")
public class MapView extends VerticalLayout implements HasUrlParameter<String>, BeforeEnterObserver {

    /* --------- Attributes ------------ */

    // Constant postal code from previous view
    String postalCode;
    // Textfield containing postal code
    TextField pCode;

    /* ----------- Methods ------------- */

    /**
     * Constructs the "plan your route" view which allows users to visualize their travel plan
     */
    public MapView() {
        this.setHeight("100%");
        this.setWidth("100%");

        // Search bar, transportation selection, and corresponding submit button
        HorizontalLayout startingAddress = new HorizontalLayout();
        Label startLabel = new Label("Starting Address: ");
        TextField text = createStartingPlaceholderText();
        Button submitStart = createSubmitStart(startingAddress, startLabel, text);

        // Selection for method of transportation (default walking)
        HorizontalLayout transSelectLayout = new HorizontalLayout();
        Label transLabel = new Label("Method of Transportation: ");

        Select<String> transSelect = createTransportationSelector();

        // Time selection widget
        HorizontalLayout timeLayout = new HorizontalLayout();

        RadioButtonGroup<String> timeButtons = createTimeSelectorButton();

        DateTimePicker dateTimePicker = createDateTimePicker();
        // Radio button selection for time Picker clears and disallows custom departure entry when set to "Now"
        timeButtons.addValueChangeListener(event -> {
            if (timeButtons.getValue().equals("Now")) {
                dateTimePicker.setReadOnly(true);
                dateTimePicker.clear();
            } else dateTimePicker.setReadOnly(false);
        });

        timeLayout.add(timeButtons);

        transSelectLayout.add(transSelect, timeButtons, dateTimePicker, submitStart);
        transSelectLayout.setAlignItems(Alignment.BASELINE);

        // Map view
        MapComponent map = new MapComponent();

        // Information for bottom target postal cod
        HorizontalLayout targetAddress = new HorizontalLayout();

        Label dataLabel = new Label("Post Address*");

        pCode = new TextField();
        pCode.setId("sCoords");
        pCode.setReadOnly(true);

        Text warning = new Text("  *Destination addresses are approximate to protect user confidentiality." +
                "\nFor full destination address, contact the user.");

        targetAddress.add(dataLabel, pCode);
        targetAddress.setAlignItems(Alignment.BASELINE);

        add(startingAddress, transSelectLayout, map, targetAddress, warning);
    }

    /**
     * Allows user to select departure time
     * @return a date time picker component to collect user's departure time
     */
    private DateTimePicker createDateTimePicker() {
        DateTimePicker dtp = new DateTimePicker();
        dtp.setId("timePick");
        dtp.setDatePlaceholder("Date");
        dtp.setTimePlaceholder("Time");
        dtp.setStep(Duration.ofMinutes(15));
        dtp.setReadOnly(true);
        return dtp;
    }

    /**
     * Allows user to decide if they want to plan their route using the current time or a time in the future
     * @return checkbox component
     */
    private RadioButtonGroup<String> createTimeSelectorButton() {
        RadioButtonGroup<String> timeButtons = new RadioButtonGroup<>();
        timeButtons.setId("timeButton");
        timeButtons.setItems("Now", "Custom Time");
        timeButtons.setValue("Now");
        timeButtons.setLabel("Departure Time:");
        return timeButtons;
    }

    /**
     * Allows user to select method of transportation
     * @return drop down menu component that gets the user's transportation choice
     */
    private Select<String> createTransportationSelector() {
        Select<String> transSelect = new Select<>("Walking", "Cycling", "Transit", "Driving");
        transSelect.setValue("Walking");
        transSelect.setLabel("Method of Transportation:");
        transSelect.setId("trans");
        return transSelect;
    }

    /**
     * Instructs the user in the proper formatting of their starting location and gets their starting location
     * @return textfield component to get the user's staring location
     */
    private TextField createStartingPlaceholderText() {
        TextField text = new TextField();

        text.setPlaceholder("Input your starting address (EG 123 4th street East)");
        text.setWidth("500px");
        text.setId("text");
        return text;
    }

    /**
     * Constructs button for user to to submit their location information
     * @param startingAddress hbox to contain + format the location info
     * @param startLabel user's starting time
     * @param text user's starting address
     * @return
     */
    private Button createSubmitStart(HorizontalLayout startingAddress, Label startLabel, TextField text) {
        Button submitStart = new Button("Get Route");
        submitStart.setId("submitStart");
        submitStart.setWidth("150px");
        startingAddress.add(startLabel, text);
        startingAddress.setAlignItems(Alignment.CENTER);
        submitStart.addClassName("reset-button");
        submitStart.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        return submitStart;
    }

    /**
     * Sets the user's postal code
     * @param beforeEvent info needed before the event occurs
     * @param pCode user's postal code
     */
    @Override
    public void setParameter(BeforeEvent beforeEvent, String pCode) {
        postalCode = pCode;
    }


    /**
     * Sets the postal code
     * @param beforeEnterEvent
     */
    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        pCode.setValue(postalCode);
    }
}
