package com.saskcycle.model;

import com.vaadin.flow.component.polymertemplate.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "Posts")
public class Post {

  /* --------- Attributes ------------ */
  public boolean give;

  public String title;

  public String description;

  @Id public String id;

  public int IDnum;

  // ToDo
  // public Arraylist<pictures> photos;

  public Date datePosted;

  public Account owner;

  public String location;

  public ArrayList<String> tags;

  public boolean privacy;

  public String contactEmail;

  // Latitude of post location
  public double lat;
  // longitude of post location
  public double lon;

  String postalCode;

  /* ----------- Methods ------------- */
  public Post(
      String title,
      String description,
      String id,
      Account owner,
      String location,
      ArrayList<String> tags,
      boolean give,
      double longitude,
      double latitude,
      String postalCode
      ) {
    this.title = title;
    this.description = description;
    this.id = id;
    this.owner = null;
    this.location = location;
    this.tags = tags;
    this.give = give;
    this.lat = longitude;
    this.lon = latitude;
    this.postalCode = postalCode;
  }


  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getLocation() {
    return location;
  }

  public void setTitle(String title) {
    this.title = title;
  }


  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Post)) {
      return false;
    }
    return (((Post) obj).id.equals(this.id)) && (((Post) obj).title.equals(this.title));
  }
}
