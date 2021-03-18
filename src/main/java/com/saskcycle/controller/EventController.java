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
}
