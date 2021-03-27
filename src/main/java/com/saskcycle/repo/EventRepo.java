package com.saskcycle.repo;

import com.saskcycle.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends MongoRepository<Event, String> {
    Event findEventByTitle(String title);
}
