package com.saskcycle.controller;

import com.saskcycle.DAO.EventDAOInterface;
import com.saskcycle.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class EventController {

    @Autowired
    private EventDAOInterface Eaccess;

    public List<Event> getAllEvents() {
        return Eaccess.allEvents();
    }

    public Event getEventByTitle(String title) {
        return Eaccess.findEventByTitle(title);
    }

    public void addEvent(Event newEvent) {
        Eaccess.addEvent(newEvent);
    }

    public void deleteEvent(Event saskcycleEvent) {
        Eaccess.deleteEvent(saskcycleEvent);
    }

    public Event getEventByID(String id) {
        return Eaccess.searchById(id);
    }
}
