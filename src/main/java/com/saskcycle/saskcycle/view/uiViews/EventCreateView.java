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
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
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
    private PostalCodeComponent postalCodeField;
    private TextField title;
    private TextArea description;
    private Button returnButton;
    private ArrayList<String> tagList;
    DateTimePicker startTime;
    MultiSelectListBox<String> tags;

    /**
     * Allows organizational account to create an event
     */
    public EventCreateView() {

        createReturnButton();
        titleField();
        descriptionField();
        VerticalLayout address = addressFields();

        startTime = new DateTimePicker();
        startTime.setLabel("Start time");
        startTime.setValue(LocalDateTime.now());


        DateTimePicker endTime = new DateTimePicker();
        endTime.setLabel("End time");
        endTime.setValue(LocalDateTime.now());

        createTags();

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

    /**
     * Publishes the post to the database
     * @param eventStart start time of event
     * @param eventEnd end time of event
     * @param title title of event
     * @param description description of event
     * @param addressInfo location of event
     * @param tags tags of event
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
        }
        else if (eventEnd.isBefore(eventStart)) {
            Notification.show("Event's end time is before its start time");
        }
        else if (eventStart.equals(eventEnd)) {
            Notification.show("Event's start time is the same as its end time");
        }
        else if (eventStart.isBefore(LocalDateTime.now())) {
            Notification.show("Event's start time is before the current date");
        }
        else {
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
            returnButton.addClassName("reset-button");
            returnButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            returnButton.addClickListener(
                    e -> {
                        returnButton.getUI().ifPresent(ui -> ui.navigate("delete-event"));
                        confirmPosted.close();
                    });
            VerticalLayout vbox = new VerticalLayout(new H2("Successful Post!"), returnButton);
            vbox.setAlignItems(Alignment.CENTER);
            confirmPosted.add(vbox);
            confirmPosted.setOpened(true);
        }
    }

    /**
     * Constructs the button that allows the user to navigate back to the events page
     */
    private void createReturnButton(){
        returnButton = new Button("Return", new Icon(VaadinIcon.ANGLE_LEFT));
        returnButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        returnButton.addClassName("reset-button");
        returnButton.addClickListener(e -> returnButton.getUI().ifPresent(ui -> ui.navigate("delete-event")));
    }

    /**
     * Constructs the description field for the user to enter event details
     */
    private void descriptionField() {
        description = new TextArea();
        description.setLabel("Description");
        description.setPlaceholder("Type here ...");
        description.setMinWidth("600px");
        description.setMinHeight("200px");
        description.setRequiredIndicatorVisible(true);
    }

    /**
     * Constructs text field to take in the title of the event
     */
    private void titleField() {
        title = new TextField();
        title.setLabel("Event Title");
        title.setPlaceholder("Type here ...");
        title.setMinWidth("600px");
        title.setRequiredIndicatorVisible(true);
    }

    /**
     * Collects the tags selected by the user
     */
    private void createTags() {
        // Tags list
        tags = new MultiSelectListBox<>();
        tags.setItems(Tags.getTagNames());
        tagList = new ArrayList<>();
        tags.addValueChangeListener(
                e -> {
                    tagList.clear();
                    tagList.addAll(e.getValue());
                });
    }

    /**
     * Construct the address form needed to get the location information about the event
     * @return formatted address form
     */
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

    /**
     * Retrieves the address info from the location section in the event create form
     * @return
     */
    private ArrayList<String> formatAddressInfo() {
        ArrayList<String> addressInfo = new ArrayList<>();
        addressInfo.add(line1.getValue());
        addressInfo.add(line2.getValue());
        addressInfo.add(city.getValue() + ", " + province.getValue());
        addressInfo.add(postalCodeField.getTextField().getValue());

        return addressInfo;
    }

}
