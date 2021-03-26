package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Event;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class ClickedInfo extends Dialog {

    protected Timezone saskCycleTimezone;

    public ClickedInfo(Event event) {

        saskCycleTimezone = new Timezone(ZoneId.of("Canada/Saskatchewan"));

        setHeight("400px");
        setWidth("650px");

        displayEventInfo(event);

    }

    /**
     * Styles the time string depending on if the event is on the same day
     * @param start start time
     * @param end end time
     * @return string representation of the LocalDateTime duration of the event
     */
    protected String formatTime(LocalDateTime start, LocalDateTime end) {

        String eventTime;

        LocalDate startDate = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();

        // Event is on the same day
        if (startDate.equals(endDate)) {
            eventTime = start.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy, hh:mm a"));
            eventTime += " to " + end.format(DateTimeFormatter.ofPattern("hh:mm a"));
        }
        // Even spans more than one day
        else {
            eventTime = start.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hh:mm a"));
            eventTime += " to " + end.format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hh:mm a"));
        }

        return eventTime;
    }

    /**
     * Formats the post's tags into a visual representation
     * @param tags array containing the tags that are associated with the event
     * @return a horizontal layout of the tags which are displayed in boxes
     */
    protected HorizontalLayout formatTags(String[] tags) {

        HorizontalLayout tagGroup = new HorizontalLayout();

        for (String t : tags) {
            Button component = new Button(t);
            tagGroup.add(component);
            component.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            component.addClassName("reset-button");
        }
        return tagGroup;
    }

    /**
     * Displays the information for the event
     * @param saskcycleEvent the event whose info needs to be displayed
     */
    protected void displayEventInfo(Event saskcycleEvent) {

        H3 eventTitle = new H3(saskcycleEvent.title);

        LocalDateTime startTime = LocalDate.now().withMonth(saskcycleEvent.startTime[0]).withDayOfMonth(saskcycleEvent.startTime[1]).atTime(saskcycleEvent.startTime[2], saskcycleEvent.startTime[3]);
        LocalDateTime endTime = LocalDate.now().withMonth(saskcycleEvent.endTime[0]).withDayOfMonth(saskcycleEvent.endTime[1]).atTime(saskcycleEvent.endTime[2], saskcycleEvent.endTime[3]);
        H5 eventTime = new H5(formatTime(startTime, endTime));

        // Display description of the event
        Paragraph desc = new Paragraph(saskcycleEvent.description);
        Scroller scroller = new Scroller();
        scroller.setContent(desc);
        scroller.setHeight("100px");
        scroller.setWidth("600px");
        scroller.getStyle().set("border", "1px solid #eeeeee");

        H5 location = new H5(saskcycleEvent.location);
        H5 organizer = new H5(saskcycleEvent.organizer);

        String[] tags = saskcycleEvent.tags.toArray(new String[0]);

        // Display other event info with indication icons
        HorizontalLayout time = new HorizontalLayout(VaadinIcon.CLOCK.create(), eventTime);
        time.setAlignItems(FlexComponent.Alignment.BASELINE);

        Icon map = VaadinIcon.MAP_MARKER.create();
        HorizontalLayout loc = new HorizontalLayout(map, location);
        loc.setAlignItems(FlexComponent.Alignment.CENTER);

        Icon user = VaadinIcon.USER.create();
        HorizontalLayout org = new HorizontalLayout(user, organizer);
        org.setAlignItems(FlexComponent.Alignment.CENTER);


        // Exit button which allows user to close the event info window
        HorizontalLayout exit = new HorizontalLayout(VaadinIcon.CLOSE.create());
        exit.addClassName("exit");
        exit.setAlignItems(FlexComponent.Alignment.END);
        exit.getStyle().set("cursor", "pointer");
        exit.addClickListener(e -> this.close());

        add(exit, eventTitle, scroller, formatTags(tags), loc, time, org);

    }
}
