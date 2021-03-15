package com.saskcycle.saskcycle.view.uiViews;

import com.saskcycle.saskcycle.view.layouts.EventLayout;
import com.vaadin.flow.component.charts.model.VerticalAlign;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.fullcalendar.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;


import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

// @Route("events")
@Route(value = "events", layout = EventLayout.class)
public class EventView extends VerticalLayout {

  FullCalendar calendar;

  private Button buttonDatePicker;

  public EventView() {


    add(createToolBar(), createCalendar());
  }

  public VerticalLayout createCalendar() {

    VerticalLayout calLayout = new VerticalLayout();

    calendar = FullCalendarBuilder.create().build();
    calendar.setWeekNumbersVisible(false);

    calLayout.add(calendar);
    calLayout.setFlexGrow(1, calendar);

    Entry entry =  new Entry();
    entry.setTitle("Test");
    entry.setStart(LocalDate.now().withDayOfMonth(3).atTime(10, 0), calendar.getTimezone());
    entry.setEnd(entry.getStart().plusHours(2), calendar.getTimezone());
    entry.setColor("#ff3333");

    calendar.addEntry(entry);

    return calLayout;
  }

  private VerticalLayout createToolBar() {

    HorizontalLayout toolbar = new HorizontalLayout();

    H1 month = new H1(String.valueOf(LocalDate.now().getMonth()) + " " + LocalDate.now().getYear());

    Button buttonToday = new Button("Today", VaadinIcon.HOME.create(), e -> calendar.today());
    Button buttonPrevious = new Button("Previous", VaadinIcon.ANGLE_LEFT.create(), e -> {
      calendar.previous();
    });
    Button buttonNext = new Button("Next", VaadinIcon.ANGLE_RIGHT.create(), e -> calendar.next());
    buttonNext.setIconAfterText(true);

    DatePicker gotoDate = new DatePicker();
    gotoDate.addValueChangeListener(event1 -> {
      calendar.gotoDate(event1.getValue());
      month.setText(event1.getValue().getMonth().toString() + " " + event1.getValue().getYear());
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
}
