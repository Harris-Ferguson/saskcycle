package com.saskcycle.model;

import com.vaadin.flow.component.polymertemplate.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.LocalDateTime;


@Document(collection = "Events")
public class Event {

    /* --------- Attributes ------------ */

    @Id
    public String id;
    public String title;
    public int[] startTime;
    public int[] endTime;
    public String organizer;
    public String[] tags;
    public String desc;
    public String location;

    public Event(String id, int[] startTime, int[] endTime, String title, String organizer, String[] tags, String desc, String location) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.organizer = organizer;
        this.tags = tags;
        this.desc = desc;
        this.location = location;
    }
}
