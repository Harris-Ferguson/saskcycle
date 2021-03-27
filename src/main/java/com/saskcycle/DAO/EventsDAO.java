package com.saskcycle.DAO;

import com.saskcycle.model.Event;
import com.saskcycle.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventsDAO implements EventDAOInterface {

    @Autowired
    private final EventRepo eventRepository;

    public EventsDAO(EventRepo er) {
        eventRepository = er;
    }

    @Override
    public List<Event> allEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event searchById(String id) {
        Optional<Event> event = eventRepository.findById(id);
        return event.get();
    }

    @Override
    public void addEvent(Event event) {
        eventRepository.insert(event);
    }

    @Override
    public void deleteEvent(Event saskcycleEvent) {
        eventRepository.delete(saskcycleEvent);
    }

    @Override
    public void updateEvent(Event e) {
        eventRepository.save(e);
    }

    @Override
    public Event findEventByTitle(String title) {
        return eventRepository.findEventByTitle(title);
    }

}
