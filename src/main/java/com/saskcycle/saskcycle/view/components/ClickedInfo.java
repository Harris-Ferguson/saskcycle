package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Event;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public abstract class ClickedInfo extends Dialog {

    protected Timezone saskCycleTimezone;

    /**
     * Show the post info in full when it's clicked
     * @param event
     */
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
        String eventTime = formatTime(startTime, endTime);

        // Display description of the event
        Paragraph desc = new Paragraph(saskcycleEvent.description);
        Scroller scroller = new Scroller();
        scroller.setContent(desc);
        scroller.setHeight("100px");
        scroller.setWidth("600px");
        scroller.getStyle().set("border", "1px solid #eeeeee");

        Details org = new Details("Organizer", new Text(saskcycleEvent.owner.getUsername() + " (" + saskcycleEvent.owner.getEmail() + ")"));
        Details loc = new Details("Location", displayAddress(saskcycleEvent.elocation));
        Details time = new Details("Time", new Text(eventTime));

        String[] tags = saskcycleEvent.tags.toArray(new String[0]);

        // Exit button which allows user to close the event info window
        HorizontalLayout exit = new HorizontalLayout(VaadinIcon.CLOSE.create());
        exit.addClassName("exit");
        exit.setAlignItems(FlexComponent.Alignment.END);
        exit.getStyle().set("cursor", "pointer");
        exit.addClickListener(e -> this.close());

        add(exit, eventTitle, scroller, formatTags(tags), loc, time, org);

    }

    /**
     * Build a component to display the address info of the event
     * @param elocation location of the event
     * @return array of event information
     */
    private VerticalLayout displayAddress(ArrayList<String> elocation) {

        VerticalLayout info =  new VerticalLayout();

        for (String line : elocation) {
            Span s = new Span(new Text(line));
            info.add(s);
        }

        return info;
    }
}
