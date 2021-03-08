package com.saskcycle.model;

// Ex
public class User /*extends that anon user class*/ {
    /* --------- Attributes ------------ */

    // public cookies cookies

    public Feed savedPosts;

    public Route route;

    /* ----------- Methods ------------- */


    public User(){
        savedPosts = new Feed();
        this.route = new Route();
    }


}
