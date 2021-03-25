package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.DAO.EventsDAO;
import com.saskcycle.DAO.PostsDAO;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;
import com.saskcycle.saskcycle.view.layouts.PostCreateLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Route(value = "create-event", layout = PostCreateLayout.class)
@PageTitle("SaskCycle | Event Create")
@Secured("ROLE_ORG")
public class EventCreateView extends VerticalLayout {

    @Autowired
    private EventController EC;

    @Autowired
    private CurrentUserDAOInterface currentAccount;

    public EventCreateView() {

        // cancel button
        Button returnButton = new Button("Return", new Icon(VaadinIcon.ARROW_BACKWARD));
        returnButton.addClickListener(e -> returnButton.getUI().ifPresent(ui -> ui.navigate("delete-event")));

        // Title Field
        TextField title = new TextField();
        title.setLabel("Post Title");
        title.setPlaceholder("Type here ...");
        title.setMinWidth("600px");
        title.setRequiredIndicatorVisible(true);

        // Description Field
        TextArea description = new TextArea();
        description.setLabel("Description");
        description.setPlaceholder("Type here ...");
        description.setMinWidth("600px");
        description.setMinHeight("200px");
        description.setRequiredIndicatorVisible(true);

        // Location Field
        TextField location = new TextField();
        location.setLabel("Location");
        location.setPlaceholder("Type your location ...");
        location.setMinWidth("600px");
        location.setRequiredIndicatorVisible(true);

        DateTimePicker startTime = new DateTimePicker();
        startTime.setLabel("Start time");

        DateTimePicker endTime = new DateTimePicker();
        endTime.setLabel("End time");


        // Tags list
        MultiSelectListBox<String> tags = new MultiSelectListBox<>();
        tags.setItems("Appliances", "Clothing", "Electronics", "Furniture", "Art");
        ArrayList<String> tagList = new ArrayList<>();
        tags.addValueChangeListener(
                e -> {
                    tagList.clear();
                    tagList.addAll(e.getValue());
                });

        // Left side of post creation
        VerticalLayout LeftInfoPanel = new VerticalLayout(title, description, location, startTime, endTime);

        // Right side of post creation
        VerticalLayout RightInfoPanel = new VerticalLayout(new H1("Tags:"), tags);

        // Body of post creation
        HorizontalLayout InfoPanel = new HorizontalLayout(LeftInfoPanel, RightInfoPanel);

        // Header of post creation
        HorizontalLayout Header = new HorizontalLayout(returnButton, new H1("Create Event Post"));
        Header.setAlignItems(Alignment.CENTER);

        // create post button (calls publish post when clicked)
        Button createPostButton = new Button("Create Post!", new Icon(VaadinIcon.THUMBS_UP));
        createPostButton.addClickListener(
                e -> publishEvent(
                        startTime.getValue(),
                        endTime.getValue(),
                        title.getValue(),
                        description.getValue(),
                        location.getValue(),
                        tagList));

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
            String location,
            ArrayList<String> tags){

        if (title.trim().isEmpty()) {
            Notification.show("Enter a Title");
        } else if (description.trim().isEmpty()) {
            Notification.show("Enter a Description");
        } else if (location.trim().isEmpty()) {
            Notification.show("Enter a Location");
        } else if (tags.isEmpty()) {
            Notification.show("Please add some tags");
        }
        else if (eventEnd.isBefore(eventStart)) {
            Notification.show("Event's end time is before its start time");
        }
        else {
            int[] startTimeDetails = new int[]{eventStart.getMonth().getValue(), eventStart.getDayOfMonth(), eventStart.getHour(), eventStart.getMinute(), eventStart.getYear()};
            int[] endTimeDetails = new int[]{eventEnd.getMonth().getValue(), eventEnd.getDayOfMonth(), eventEnd.getHour(), eventEnd.getMinute(), eventEnd.getYear()};
            Event newEvent = new Event(startTimeDetails, endTimeDetails, title, currentAccount.getCurrentAccount().getUsername(),
                    tags, description, location);

            //postRepo.addPost(newPost);
            EC.addEvent(newEvent);
            currentAccount.updatePosts(newEvent);

            // Confirmation Dialog Box
            Dialog confirmPosted = new Dialog();
            confirmPosted.setModal(false);
            Button returnButton = new Button("Return Home", new Icon(VaadinIcon.HOME));
            returnButton.addClickListener(
                    e -> {
                        returnButton.getUI().ifPresent(ui -> ui.navigate(""));
                        confirmPosted.close();
                    });
            confirmPosted.add(new H1("Successful Post!"), returnButton);
            confirmPosted.setOpened(true);
        }
    }
}
