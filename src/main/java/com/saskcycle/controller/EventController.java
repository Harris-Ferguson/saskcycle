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

    public List<Event> getAllEvents() {
        return eventDataAccess.allEvents();
    }

    public Event getEventByDetails(String title) {
        return eventDataAccess.findEventByTitle(title);
    }

    public void addEvent(Event newEvent) {
        eventDataAccess.addEvent(newEvent);
    }

    public void deleteEvent(Event saskcycleEvent) {
        eventDataAccess.deleteEvent(saskcycleEvent);
    }

    public Event getEventByID(String id) {
        return eventDataAccess.searchById(id);
    }

    public Event getEventByDetails(String title, int[] start, int[] end) {
        return eventDataAccess.findEventByDetails(title, start, end);
    }
}
