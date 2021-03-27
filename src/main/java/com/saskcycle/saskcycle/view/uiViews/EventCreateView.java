package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.AccountController;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.model.Tags;
import com.saskcycle.saskcycle.view.components.PostalCodeComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Route(value = "create-event", layout = MainLayout.class)
@PageTitle("SaskCycle | Event Create")
@Secured("ROLE_ORG")
public class EventCreateView extends VerticalLayout {

    @Autowired
    private EventController EC;

    @Autowired
    private AccountController currentAccount;

    private TextField line1, line2, city, province;

    PostalCodeComponent postalCodeField;

    public EventCreateView() {
        // cancel button
        Button returnButton = new Button("Return", new Icon(VaadinIcon.ANGLE_LEFT));
        returnButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        returnButton.addClassName("reset-button");
        returnButton.addClickListener(e -> returnButton.getUI().ifPresent(ui -> ui.navigate("delete-event")));

        // Title Field
        TextField title = new TextField();
        title.setLabel("Event Title");
        title.setPlaceholder("Type here ...");
        title.setMinWidth("600px");
        title.setRequiredIndicatorVisible(true);

        VerticalLayout address = addressFields();

        // Description Field
        TextArea description = new TextArea();
        description.setLabel("Description");
        description.setPlaceholder("Type here ...");
        description.setMinWidth("600px");
        description.setMinHeight("200px");
        description.setRequiredIndicatorVisible(true);

        DateTimePicker startTime = new DateTimePicker();
        startTime.setLabel("Start time");

        DateTimePicker endTime = new DateTimePicker();
        endTime.setLabel("End time");

        // Tags list
        MultiSelectListBox<String> tags = new MultiSelectListBox<>();
        tags.setItems(Tags.getTagNames());
        ArrayList<String> tagList = new ArrayList<>();
        tags.addValueChangeListener(
                e -> {
                    tagList.clear();
                    tagList.addAll(e.getValue());
                });

        // Left side of post creation
        VerticalLayout LeftInfoPanel = new VerticalLayout(title, description, address, startTime, endTime);

        // Right side of post creation
        VerticalLayout RightInfoPanel = new VerticalLayout(new H1("Tags"), tags);

        // Body of post creation
        HorizontalLayout InfoPanel = new HorizontalLayout(LeftInfoPanel, RightInfoPanel);

        // Header of post creation
        HorizontalLayout Header = new HorizontalLayout(returnButton, new H1("Create Event"));
        Header.setAlignItems(Alignment.CENTER);

        // create post button (calls publish post when clicked)
        Button createPostButton = new Button("Create Event!");
        createPostButton.addClassName("reset-button");
        createPostButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createPostButton.addClickListener(
                e -> {
                    publishEvent(
                            startTime.getValue(),
                            endTime.getValue(),
                            title.getValue(),
                            description.getValue(),
                            formatAddressInfo(),
                            tagList);
                });

        add(Header, InfoPanel, createPostButton);
    }

    /* publish post
     * method verifies all the required fields are filled out
     * if so, then a new post is made using all the provided info from user
     */
    private void publishEvent(
            LocalDateTime eventStart,
            LocalDateTime eventEnd,
            String title,
            String description,
            ArrayList<String> addressInfo,
            ArrayList<String> tags) {

        if (line1.getValue().trim().isEmpty()) {
            Notification.show("Enter Address Line 1");
        } else if (!postalCodeField.postalCodeIsValid()) {
            Notification.show("Enter a valid postal code");
        } else if (title.trim().isEmpty()) {
            Notification.show("Enter a Title");
        } else if (description.trim().isEmpty()) {
            Notification.show("Enter a Description");
        } else if (tags.isEmpty()) {
            Notification.show("Please add some tags");
        } else if (eventEnd.isBefore(eventStart)) {
            Notification.show("Event's end time is before its start time");
        } else {
            int[] startTimeDetails = new int[]{eventStart.getMonth().getValue(), eventStart.getDayOfMonth(), eventStart.getHour(), eventStart.getMinute(), eventStart.getYear()};
            int[] endTimeDetails = new int[]{eventEnd.getMonth().getValue(), eventEnd.getDayOfMonth(), eventEnd.getHour(), eventEnd.getMinute(), eventEnd.getYear()};
            Event newEvent = new Event(startTimeDetails, endTimeDetails, title, currentAccount.getCurrentAccount(),
                    tags, description, addressInfo);

            EC.addEvent(newEvent);
            currentAccount.updateEvents(newEvent.id);


            // Confirmation Dialog Box
            Dialog confirmPosted = new Dialog();
            confirmPosted.setModal(false);
            Button returnButton = new Button("Go to your events", new Icon(VaadinIcon.HOME));
            returnButton.addClickListener(
                    e -> {
                        returnButton.getUI().ifPresent(ui -> ui.navigate("delete-event"));
                        confirmPosted.close();
                    });
            confirmPosted.add(new H1("Successfully created your event!"), returnButton);
            confirmPosted.setOpened(true);
        }
    }

    private VerticalLayout addressFields() {

        line1 = new TextField();
        line1.setLabel("Address Line 1");
        line1.setRequiredIndicatorVisible(true);

        line2 = new TextField();
        line2.setLabel("Address Line 2");

        city = new TextField("City");
        city.setValue("Saskatoon");
        city.setReadOnly(true);

        province = new TextField("Province");
        province.setValue("Saskatchewan");
        province.setReadOnly(true);

        postalCodeField = new PostalCodeComponent();

        VerticalLayout address = new VerticalLayout(line1, line2, new HorizontalLayout(city, province), postalCodeField);
        address.getStyle().set("border", "1px solid #eeeeee");
        return new VerticalLayout(new H5("Location"), address);
    }

    private ArrayList<String> formatAddressInfo() {
        ArrayList<String> addressInfo = new ArrayList<>();
        addressInfo.add(line1.getValue());
        addressInfo.add(line2.getValue());
        addressInfo.add(city.getValue() + ", " + province.getValue());
        addressInfo.add(postalCodeField.getTextField().getValue());

        return addressInfo;
    }

}
