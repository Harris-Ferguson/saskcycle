package com.saskcycle.model;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "Businesses")
public class Business extends Post {

    /* --------- Attributes ------------ */

    @PersistenceConstructor
    public Business(
            String title,
            String description,
            String id,
            String postalCode,
            ArrayList<String> tags,
            boolean give,
            double longitude,
            double latitude)
    {
        super(give,title,description,id,new Date(),null,postalCode,tags,true,"Check our Website out",longitude,latitude);
    }

    /* ----------- Methods ------------- */

    public ArrayList<String> getTags() {
        return this.tags;
    }

    public String getName() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return this.title + "\n" + this.description + "\n";
    }
}
