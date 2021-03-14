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
      boolean give) {
    super(title, description, id, null, location, tags, give);
  }

  public ArrayList<String> get_tags() {
    return this.tags;
  }

  public String get_name() {
    return this.title;
  }

  public String get_description() {
    return this.description;
  }

  public String get_location() {
    return this.location;
  }

  public String toString() {
    return this.title + "\n" + this.description + "\n" + this.location + "\n";
  }
}
