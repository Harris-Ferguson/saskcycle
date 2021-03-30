package com.saskcycle.controller;

import com.saskcycle.DAO.EventDAOInterface;
import com.saskcycle.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventDAOInterface eventDataAccess;

    /**
     * Retrieves all the evnets stored in the database
     * @return list of event objects
     */
    public List<Event> getAllEvents() {
        return eventDataAccess.allEvents();
    }

    /**
     * Adds an event to the database
     * @param newEvent the event to be added
     */
    public void addEvent(Event newEvent) {
        eventDataAccess.addEvent(newEvent);
    }

    /**
     * Deletes the specified event from the database
     * @param saskcycleEvent the event to be deleted
     */
    public void deleteEvent(Event saskcycleEvent) {
        eventDataAccess.deleteEvent(saskcycleEvent);
    }

    /**
     * Retrieves the event that matches the specified id (if it exists) from the database
     * @param id the id number of the searched for event
     * @return the event with the specified id number; null if no such event is stored in the database
     */
    public Event getEventByID(String id) {
        return eventDataAccess.searchById(id);
    }

    /**
     * Retrieves an event from the database using its title, start and end times
     * Method is used to work with the entries in Vaadin's calendar component since it's not possible to use the event id
     * @param title title of the event
     * @param start start time of the event
     * @param end end time of the event
     * @return the event if it exists; null otherwise
     */
    public Event getEventByDetails(String title, int[] start, int[] end) {
        return eventDataAccess.findEventByDetails(title, start, end);
    }

}
