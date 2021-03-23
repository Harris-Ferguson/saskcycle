package com.saskcycle.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "Businesses")
public class Business extends Post {
  // Methods

  public Business(
      String title,
      String description,
      String id,
      String location,
      ArrayList<String> tags,
      boolean give,
      double longitude,
      double latitude) {
  }

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
