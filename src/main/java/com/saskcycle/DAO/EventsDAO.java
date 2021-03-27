package com.saskcycle.DAO;

import com.saskcycle.model.Event;
import com.saskcycle.repo.EventRepo;
import com.saskcycle.repo.PostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventsDAO implements EventDAOInterface {

    @Autowired
    private final EventRepo ER;

    public EventsDAO(EventRepo er) {
        ER = er;
    }

    @Override
    public List<Event> allEvents() {
        return ER.findAll();
    }

    @Override
    public Event searchById(String id) {
        Optional<Event> event = ER.findById(id);
        return event.get();
    }

    @Override
    public void addEvent(Event event) {
        ER.insert(event);

    }

    @Override
    public void deleteEvent(Event saskcycleEvent) {

        ER.delete(saskcycleEvent);

    }

    @Override
    public void updateEvent(Event e) {
        ER.save(e);
    }

}
