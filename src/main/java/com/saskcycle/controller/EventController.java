package com.saskcycle.controller;

import com.saskcycle.DAO.EventDAOInterface;
import com.saskcycle.DAO.PostsDAOInterface;
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

        List<Event> events = getAllEvents();

        for (Event e : events) {
            if (e.title.equals(title)) {
                return e;
            }
        }
        return null;
    }

    public void addEvent(Event newEvent) {
        Eaccess.addEvent(newEvent);
    }

    public void deleteEvent(Event saskcycleEvent) {
        Eaccess.deleteEvent(saskcycleEvent);
    }

    public Event getEventByID(String id) {

        List<Event> events = getAllEvents();

        for (Event e : events) {
            if (e.id.equals(id)) {
                return e;
            }
        }
        return null;
    }
}
