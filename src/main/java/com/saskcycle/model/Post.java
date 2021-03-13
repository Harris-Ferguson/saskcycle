package com.saskcycle.model;

import com.vaadin.flow.component.polymertemplate.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "Posts")
public class Post {

    /* --------- Attributes ------------ */

    public String title;

    public String description;

    @Id
    public String id;

    //public Arraylist<pictures> photos;

    public Date datePosted;

    public Account owner;

    public String location;

    public ArrayList<String> tags;

    /* ----------- Methods ------------- */
    public Post(String title, String description, String id, Account owner, String location, ArrayList<String> tags){
        this.title = title;
        this.description = description;
        this.id = id;
        this.owner = null;
        this.location = location;
        this.tags = tags;
    }




}
