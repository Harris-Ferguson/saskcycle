package com.saskcycle.DAO;

import com.saskcycle.model.Account;
import com.saskcycle.model.Event;
import com.saskcycle.model.Post;

import java.util.ArrayList;

/**
 * An interface for operations on the currently logged in user
 */
public interface CurrentUserDAOInterface {
    /**
     * Update a users Notification Settings
     *
     * @param wantsEmail does the user want emails?
     * @param wantsText  does the user want texts?
     * @return the updated account
     */
    Account updateSettings(boolean wantsEmail, boolean wantsText);

    /**
     * Get the current users email setting
     *
     * @return true / false if the user wants emails
     */
    boolean getEmailSetting();

    /**
     * Get the current users email
     *
     * @return email string
     */
    String getEmail();

    /**
     * Gets the current user, I.E the user who is currently making a given request
     * @return the current user as an Account object
     */
    Account getCurrentAccount();

    /**
     * Get the current users text setting
     *
     * @return true / false if the user wants texts
     */
    boolean getTextSetting();

    /**
     * Adds an ID to a logged in user's wishlist
     * @param id a string representation the ID of a post in the database
     */
    public void updateWishlist(String id);

    /**
     * Adds the post to the current account's list of created posts
     * @param post
     */
    public void updatePosts(Post post);

    /**
     * Delete specified event from the database
     * @param saskcycleEvent deletes the event from the database
     */
    void deleteEvent(Event saskcycleEvent);

    /**
     * Adds the just-created post to the user's list of created posts
     * @param id the id of the new post
     */
    public void updateCreatedPostList(String id);

    /**
     * Removes a saved post from the wishlist of the current user
     * @param id id of the saved post
     */
    public void removeFromWishlist(String id);

    /**
     * Removes a specifed post from the current user's list of posts
     * @param id id of the post
     */
    public void removePost(String id);

    /**
     * Gets all the event ids created by the current user
     * @return list of event object ids
     */
    ArrayList<String> getEventIds();

    /**
     * Adds an event object id to the current user's list of created events
     * @param id id of the event object
     */
    void updateEvents(String id);
}
