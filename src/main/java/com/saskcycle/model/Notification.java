package com.saskcycle.model;

import org.springframework.data.annotation.Id;

import java.util.ArrayList;

public class Notification {


    /* --------- Attributes ------------ */

    public boolean readStatus;

    public String description;

    private ArrayList<Account> subscribers;

    public String tag;

    // The link to the webpage containing the post related to the notification
    public String link;

    /* ----------- Methods ------------- */
}
