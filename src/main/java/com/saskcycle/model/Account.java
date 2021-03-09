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

    private String role;

    private Feed wishlish;

    private Feed posts;

    private double userRating;

    private ArrayList<Notification> notifications;

    /* --------- Methods -------------*/


    public Account(String username,
                   String password,
                   String role,
                   boolean enabled,
                   boolean accountNonExpired,
                   boolean credentialsNonExpired,
                   boolean accountNonLocked,
                   Collection<? extends GrantedAuthority> authorities,
                   String id,
                   String email){

        // Calls super to initialize other values
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
        this.role = role;
        this.email = email;
        this.userRating = 0;
        this.notifications = new ArrayList<>();
        this.wishlish = new Feed();
        this.posts = new Feed();

    }

    /* --------- Getters and Setters -------------*/


    public Feed getPosts() {
        return posts;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getRole() {
        return role;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public Feed getWishlish() {
        return wishlish;
    }

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


