package com.saskcycle.saskcycle.view.components;

import com.saskcycle.model.Event;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.Scroller;
import org.vaadin.stefan.fullcalendar.EntryClickedEvent;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.format.DateTimeFormatter;

public class EventInfoComponent extends Dialog {

    /**
     * Constructs a dialog box to display info about an event in the calendar
     * @param clickEvent click event
     * @param timezone timezone associated with the calendar
     * @param saskcycleEvent the event that was clicked in the calendar
     */
    public EventInfoComponent(EntryClickedEvent clickEvent, Timezone timezone, Event saskcycleEvent) {

        // Style dialog window
        setHeight("400px");
        setWidth("400px");

        H3 eventTitle = new H3(clickEvent.getEntry().getTitle());

        H5 startTime = new H5(clickEvent.getEntry().getStart(timezone).format(DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy hh:mm a")));

        // Display description of the event
        Paragraph desc = new Paragraph(clickEvent.getEntry().getDescription());
        Scroller scroller = new Scroller();
        scroller.setContent(desc);
        scroller.setHeight("100px");
        scroller.setWidth("350px");
        scroller.getStyle().set("border", "1px solid #eeeeee");

        H5 location = new H5(saskcycleEvent.location);
        H5 organizer = new H5(saskcycleEvent.organizer);

        String[] tags = saskcycleEvent.tags;

        // Display other event info with indication icons
        HorizontalLayout time = new HorizontalLayout(VaadinIcon.CLOCK.create(), startTime);
        time.setAlignItems(FlexComponent.Alignment.BASELINE);

        HorizontalLayout loc = new HorizontalLayout(VaadinIcon.MAP_MARKER.create(), location);
        loc.setAlignItems(FlexComponent.Alignment.BASELINE);

        HorizontalLayout org = new HorizontalLayout(VaadinIcon.USER.create(), organizer);
        org.setAlignItems(FlexComponent.Alignment.BASELINE);

        // Exit button which allows user to close the event info window
        HorizontalLayout exit = new HorizontalLayout(VaadinIcon.CLOSE.create());
        exit.addClassName("exit");
        exit.setAlignItems(FlexComponent.Alignment.END);
        exit.addClickListener(e -> this.close());

        add(exit, eventTitle, scroller, formatTags(tags), time, loc, org);
    }

    /**
     * Formats the post's tags into a visual representation
     * @param tags array containing the tags that are associated with the event
     * @return a horizontal layout of the tags which are displayed in boxes
     */
    private HorizontalLayout formatTags(String[] tags) {

        HorizontalLayout tagGroup = new HorizontalLayout();

        for (String t : tags) {
            Button component = new Button(t);
            tagGroup.add(component);
            component.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            component.addClassName("reset-button");
        }
        return tagGroup;
    }
}
