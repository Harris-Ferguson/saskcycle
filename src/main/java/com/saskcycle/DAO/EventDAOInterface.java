package com.saskcycle.DAO;

import com.saskcycle.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventDAOInterface {

    List<Event> allEvents();

    Event searchById(String id);

    void addEvent(Event event);

    void deleteEvent(Event saskcycleEvent);

    void updateEvent(Event e);

    public Event findEventByTitle(String title);

    Event findEventByDetails(String title, int[] start, int[] end);
}
