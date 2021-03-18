package com.saskcycle.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.LocalDateTime;


@Document(collection = "Events")
public class Event {

    public String title;
    public int[] startTime;
    public int[] endTime;
    //private Timezone timezone;

    public Event(int[] startTime, int[] endTime, String title) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
//        this.timezone = tz;
    }


}
