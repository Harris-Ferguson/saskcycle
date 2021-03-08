package com.saskcycle.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "User Accounts")
public class Account extends User{

    /* --------- Attributes -------------*/

    private String userName;

    @Id
    private String id;

    private String email;

    private Feed wishlish;

    private String password;

    private double userRating;

    private ArrayList<Notification> notifications;

    /* --------- Methods -------------*/

    public Account(String name, String id, String email, String password){
        this.userName = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.userRating = 0;
        this.notifications = new ArrayList<>();
        this.wishlish = new Feed();
    }

    /* --------- Getters -------------*/


    public double getUserRating() {
        return userRating;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    /* --------- Setters -------------*/

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}
