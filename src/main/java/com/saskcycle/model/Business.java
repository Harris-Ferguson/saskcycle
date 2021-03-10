package com.saskcycle.model;

import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;

@Document(collection = "Businesses")
public class Business extends  Post {
    //Methods
    public ArrayList<String> get_tags()
    {
        return this.tags;
    }
    public String get_name()
    {
        return this.title;
    }
    public String get_description()
    {
        return this.description;
    }
    public String get_location()
    {
        return this.location;
    }
}
