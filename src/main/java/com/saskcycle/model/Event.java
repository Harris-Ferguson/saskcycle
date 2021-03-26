package com.saskcycle.model;



import org.springframework.data.mongodb.core.mapping.Document;
import org.vaadin.stefan.fullcalendar.Timezone;

import java.time.LocalDateTime;
import java.util.ArrayList;


@Document(collection = "Events")
public class Event extends Post {
    /* --------- Attributes ------------ */
    public String etitle;


    public int[] startTime;
    public int[] endTime;
    public String organizer;
    public ArrayList<String> etags;
    public String desc;
    public ArrayList<String> elocation;
    public String calendarEntryID;
    //private Timezone timezone;

    public Event(int[] startTime, int[] endTime, String etitle, Account organizer, ArrayList<String> etags, String desc, ArrayList<String> elocation) {
        super();
        super.setTitle(etitle);
        super.setTags(etags);
        super.setDescription(desc);
        super.setOwner(organizer);
//        this.title = etitle;
        this.elocation = elocation;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public void setCalendarEntryID(String id) {
        this.calendarEntryID = id;
    }


}
