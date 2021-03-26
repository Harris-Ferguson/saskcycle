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

  public Date datePosted;

  public Account owner;

  public String postalCode;

  public ArrayList<String> tags;

  public boolean privacy;

  public String contactEmail;

  // Latitude of post location
  public double latitude;
  // longitude of post location
  public double longitude;

  /* ----------- Methods ------------- */

  public Post(){
  }

  public Boolean getPostType() { return give; }

  public void setPostType(Boolean postType){ give = postType; }

  public void setGive(boolean give) {
    this.give = give;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Date getDatePosted() {
    return datePosted;
  }

  public void setDatePosted(Date datePosted) {
    this.datePosted = datePosted;
  }

  public Account getOwner() {
    return owner;
  }

  public void setOwner(Account owner) {
    this.owner = owner;
  }

  public ArrayList<String> getTags() {
    return tags;
  }

  public void setTags(ArrayList<String> tags) {
    this.tags = tags;
  }

  public boolean isPublic() {
    return privacy;
  }

  public void setPublic(boolean privacy) {
    this.privacy = privacy;
  }

  public String getContactEmail() {
    return contactEmail;
  }

  public void setContactEmail(String contactEmail) {
    this.contactEmail = contactEmail;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof Post)) {
      return false;
    }
    return (((Post) obj).id.equals(this.id)) && (((Post) obj).title.equals(this.title));
  }

  /**
   * Checks to insure that each post field has been filled
   * @return True if each field has been filled, false otherwise
   */
  public boolean isComplete(){
    return !title.isEmpty() && !description.isEmpty() && !this.id.isEmpty() && !this.tags.isEmpty();
  }
}
