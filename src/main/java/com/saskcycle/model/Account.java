package com.saskcycle.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;

@Document(collection = "User Accounts")
public class Account extends User {

    /* --------- Attributes -------------*/


    @Id
    private String id;

    private String email;

    private Feed wishlish;

    private double userRating;

    private ArrayList<Notification> notifications;

    /* --------- Methods -------------*/


    public Account(String username, String password, Collection<? extends GrantedAuthority> authorities, String id,
                   String email){

        // Calls super to initialize other values
        super(username, password, authorities);
        this.id = id;
        this.email = email;
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


    /* --------- Setters -------------*/

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserRating(double userRating) {
        this.userRating = userRating;
    }
}
