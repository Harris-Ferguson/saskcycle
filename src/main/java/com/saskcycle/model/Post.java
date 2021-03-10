package com.saskcycle.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "Posts")
public class Post {

    /* --------- Attributes ------------ */

    public String title;

    public String description;

    public int Id;

    //public Arraylist<pictures> photos;

    public Date datePosted;

    public Account owner;

    public String location;

    public ArrayList<String> tags;

    /* ----------- Methods ------------- */



}
