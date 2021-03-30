package com.saskcycle.DAO;

import com.saskcycle.model.Event;

import java.time.LocalDateTime;
import java.util.List;

public interface EventDAOInterface {

    /**
     * Retrieves all events from the database
     * @return list of event objects
     */
    List<Event> allEvents();

    /**
     * Finds an event with the corresponding id
     * @param id id number of the event
     * @return the event if it exists;l null otherwise
     */
    Event searchById(String id);

    /**
     * Adds an event to the database
     * @param event event to be added
     */
    void addEvent(Event event);

    /**
     * Deletes an event from the database
     * @param saskcycleEvent event to be deleted
     */
    void deleteEvent(Event saskcycleEvent);

    void updateEvent(Event e);

    public Event findEventByTitle(String title);

    /**
     * Retrieves an event from the database using its title, start and end times
     * Method is used to work with the entries in Vaadin's calendar component since it's not possible to use the event id
     * @param title title of the event
     * @param start start time of the event
     * @param end end time of the event
     * @return the event if it exists; null otherwise
     */
    Event findEventByDetails(String title, int[] start, int[] end);
}
