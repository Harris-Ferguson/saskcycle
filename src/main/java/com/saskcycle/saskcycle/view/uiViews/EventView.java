package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.DAO.EventsDAO;
import com.saskcycle.controller.EventController;
import com.saskcycle.model.Event;
import com.saskcycle.saskcycle.view.layouts.EventLayout;
import com.vaadin.flow.component.charts.model.VerticalAlign;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import elemental.json.JsonString;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.stefan.fullcalendar.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;


import org.apache.catalina.loader.ResourceEntry;


import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

// @Route("events")
@Route(value = "events", layout = EventLayout.class)
public class EventView extends VerticalLayout {

  FullCalendar calendar;

  private Button buttonDatePicker;

  private List<Event> events;

  @Autowired
  private EventController EC;

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

    calLayout.add(calendar);
    calLayout.setFlexGrow(1, calendar);

    calendar.addEntryClickedListener(event -> {
      H3 eventTitle = new H3(event.getEntry().getTitle());
      Dialog eventInfo =  new Dialog(eventTitle);

      H5 startTime = new H5(String.valueOf(event.getEntry().getStart(calendar.getTimezone())));
      H5 endTime = new H5(String.valueOf(event.getEntry().getEnd(calendar.getTimezone())));





      eventInfo.add(startTime, endTime);



      eventInfo.setHeight("400px");
      eventInfo.setWidth("400px");

      eventInfo.open();
    });

    addEvents();


    return calLayout;
  }

  /**
   * Sets up the tool bar which allows the user to toggle between months and return to their current day
   * @return vertical layout containing current month, toggle and home buttons
   */
  private VerticalLayout createToolBar() {

    HorizontalLayout toolbar = new HorizontalLayout();

    H1 month = new H1(LocalDate.now().getMonth()+ " " + LocalDate.now().getYear());

    Button buttonToday = new Button("Today", VaadinIcon.HOME.create(), e -> {
      calendar.today();
      calendar.getElement().executeJs("return this.getCalendar().view.title")
              .then(x -> month.setText(x instanceof JsonString ? x.asString(): "--"));
    });
    Button buttonPrevious = new Button("Previous", VaadinIcon.ANGLE_LEFT.create(), e -> {
      calendar.previous();
      calendar.getElement().executeJs("return this.getCalendar().view.title")
              .then(x -> month.setText(x instanceof JsonString ? x.asString(): "--"));

    });

    Button buttonNext = new Button("Next", VaadinIcon.ANGLE_RIGHT.create(), e -> {
      calendar.next();
      calendar.getElement().executeJs("return this.getCalendar().view.title")
              .then(x -> month.setText(x instanceof JsonString ? x.asString(): "--"));
    });
    buttonNext.setIconAfterText(true);

    DatePicker gotoDate = new DatePicker();
    gotoDate.addValueChangeListener(event1 -> {
      calendar.gotoDate(event1.getValue());
      calendar.getElement().executeJs("return this.getCalendar().view.title")
              .then(x -> month.setText(x instanceof JsonString ? x.asString(): "--"));

    });

    gotoDate.getElement().getStyle().set("visibility", "hidden");
    gotoDate.getElement().getStyle().set("position", "fixed");
    gotoDate.setWidth("0px");
    gotoDate.setHeight("0px");
    gotoDate.setWeekNumbersVisible(true);
    buttonDatePicker = new Button(VaadinIcon.CALENDAR.create());
    buttonDatePicker.getElement().appendChild(gotoDate.getElement());
    buttonDatePicker.addClickListener(event -> gotoDate.open());



    toolbar.add(buttonToday, buttonPrevious, buttonDatePicker, buttonNext);
    return new VerticalLayout(month, toolbar);

  }

  /**
   * Adds the events in the database to the calendar
   */
  private void addEvents() {

    for (Event e : events) {

      Entry entry = new Entry();
      entry.setTitle(e.title);
      // start info array format [month, day, start hour, start minute]
      entry.setStart(LocalDate.now().withMonth(e.startTime[0]).withDayOfMonth(e.startTime[1]).atTime(e.startTime[2], e.startTime[3]), calendar.getTimezone());
      // end info array format [month, day, end hour, end minute]
      entry.setEnd(LocalDate.now().withMonth(e.endTime[0]).withDayOfMonth(e.endTime[1]).atTime(e.endTime[2], e.endTime[3]), calendar.getTimezone());

      entry.setColor("#04584a");

      calendar.addEntry(entry);
    }

  }
}
