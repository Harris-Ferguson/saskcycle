package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.saskcycle.view.components.EventInfoComponent;
import com.saskcycle.saskcycle.view.layouts.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import elemental.json.JsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;
import org.vaadin.stefan.fullcalendar.FullCalendarBuilder;
import org.vaadin.stefan.fullcalendar.Timezone;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

// @Route("events")
@Route(value = "events", layout = MainLayout.class)
@PageTitle("SaskCycle | Events")
public class EventView extends VerticalLayout {

    private FullCalendar calendar;

    private Button buttonDatePicker;

    private H1 month;

    private List<Event> events;

    @Autowired
    private EventController EC;

    /**
     * Shows the calendar which displays all reuse events
     */
    @PostConstruct
    public void EventView() {
        events = EC.getAllEvents();
        add(createToolBar(), createCalendar());
    }

    /**
     * Creates and styles calendar component and adds the events
     * @return vertical layour containing a full calendar component
     */
    private VerticalLayout createCalendar() {

        VerticalLayout calLayout = new VerticalLayout();

        calendar = FullCalendarBuilder.create().build();
        calendar.setWeekNumbersVisible(false);
        calendar.setTimezone(new Timezone(ZoneId.of("Canada/Saskatchewan")));

        calLayout.add(calendar);
        calLayout.setFlexGrow(1, calendar);

        calendar.addEntryClickedListener(event -> {
            Event e = EC.getEventByDetails(event.getEntry().getTitle(),
                    makeStartTimeArray(event.getEntry().getStart()),
                    makeEndTimeArray(event.getEntry().getEnd()));
            Dialog eventInfo = new EventInfoComponent(e);
            eventInfo.open();
        });

        addEvents();
        return calLayout;
    }

    /**
     * Sets up the tool bar which allows the user to toggle between months and return to their current day
     * Toolbar code based on this demo:
     * https://github.com/stefanuebe/vaadin_fullcalendar/blob/master/demo/src/main/java/org/vaadin/stefan/Demo.java
     * @return vertical layout containing current month, toggle and home buttons
     */
    private VerticalLayout createToolBar() {

        HorizontalLayout toolbar = new HorizontalLayout();

        month = new H1(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM yyyy")));

        // Allows user to return to current calendar month + date
        Button buttonToday = new Button("Today", VaadinIcon.HOME.create(), e -> {
            calendar.today();
            updateMonth();
        });
        buttonToday.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonToday.addClassName("reset-button");

        // Allows user to visit previous months in the calendar
        Button buttonPrevious = new Button("Previous", VaadinIcon.ANGLE_LEFT.create(), e -> {
            calendar.previous();
            updateMonth();
        });
        buttonPrevious.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonPrevious.addClassName("reset-button");

        // Allows user to visit future months in the calendar
        Button buttonNext = new Button("Next", VaadinIcon.ANGLE_RIGHT.create(), e -> {
            calendar.next();
            updateMonth();
        });
        buttonNext.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonNext.addClassName("reset-button");
        buttonNext.setIconAfterText(true);

        // Allows user to pick a month/day/year to immediately visit in the calendar
        DatePicker gotoDate = new DatePicker();
        gotoDate.addValueChangeListener(event1 -> {
            calendar.gotoDate(event1.getValue());
            updateMonth();
        });

        gotoDate.getElement().getStyle().set("visibility", "hidden");
        gotoDate.getElement().getStyle().set("position", "fixed");
        gotoDate.setWidth("0px");
        gotoDate.setHeight("0px");
        gotoDate.setWeekNumbersVisible(true);

        buttonDatePicker = new Button(VaadinIcon.CALENDAR.create());
        buttonDatePicker.getElement().appendChild(gotoDate.getElement());
        buttonDatePicker.addClickListener(event -> gotoDate.open());
        buttonDatePicker.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttonDatePicker.addClassName("reset-button");

        toolbar.add(buttonToday, buttonPrevious, buttonDatePicker, buttonNext);
        return new VerticalLayout(month, toolbar);

    }

    /**
     * Adds the events in the database to the calendar
     */
    private void addEvents() {

        for (Event e : events) {

            Entry entry = new Entry();

            e.setCalendarEntryID(entry.getId());
            //EC.updateCalendarEntryId(e, entry.getId());


            entry.setTitle(e.title);
            // start info array format [month, day, start hour, start minute]
            entry.setStart(LocalDate.now().withMonth(e.startTime[0]).withDayOfMonth(e.startTime[1]).atTime(e.startTime[2], e.startTime[3]), calendar.getTimezone());
            // end info array format [month, day, end hour, end minute]
            entry.setEnd(LocalDate.now().withMonth(e.endTime[0]).withDayOfMonth(e.endTime[1]).atTime(e.endTime[2], e.endTime[3]), calendar.getTimezone());

            entry.setDescription(e.desc);

            entry.setColor("#04584a");

            calendar.addEntry(entry);
        }
    }


    /**
     * Updates the month label for the calendar
     */
    private void updateMonth() {

        calendar.getElement().executeJs("return this.getCalendar().view.title")
                .then(x -> month.setText(x instanceof JsonString ? x.asString() : "--"));
    }

    /**
     * Formats the start time into an array
     * @param start event start time
     * @return LocalDateTime as an array
     */
    private int[] makeStartTimeArray(LocalDateTime start) {
        return new int[]{start.getMonthValue(), start.getDayOfMonth(), start.getHour(), start.getMinute(), start.getYear()};
    }

    /**
     * Format the end time into an array
     * @param end event end time
     * @return LocalDateTime as an array
     */
    private int[] makeEndTimeArray(LocalDateTime end) {
        return new int[]{end.getMonthValue(),end.getDayOfMonth(), end.getHour(), end.getMinute(), end.getYear()};
    }

}
