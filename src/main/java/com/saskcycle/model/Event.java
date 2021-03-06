package com.saskcycle.model;


import org.springframework.data.mongodb.core.mapping.Document;

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


    /**
     * Creates an event to store information about recycle/reuse events in Saskatoon
     * @param startTime start time of the event
     * @param endTime end time of the event
     * @param etitle title of the event
     * @param organizer organizer of the event
     * @param etags tag(s) associated with the event
     * @param desc description of the event
     * @param elocation location info about the event
     */
    public Event(int[] startTime, int[] endTime, String etitle, Account organizer, ArrayList<String> etags, String desc, ArrayList<String> elocation) {
        super();
        super.setTitle(etitle);
        super.setTags(etags);
        super.setDescription(desc);
        super.setOwner(organizer);
        this.elocation = elocation;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Sets the calendarEntry field for the object using the entry id generated by Vaadin's calandar component
     * @param id Vaadin calendar component entry id number
     */

    public void setCalendarEntryID(String id) {
        this.calendarEntryID = id;
    }


}
