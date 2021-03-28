package com.saskcycle.controller;

import com.saskcycle.DAO.CurrentUserDAOInterface;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.model.Post;
import com.saskcycle.services.GeocodeService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class PostController implements Serializable {

    /* --------- Attributes --------- */

    private Post currentPost;

    @Autowired
    private PostsDAOInterface postsDataAccess;

    @Autowired
    private GeocodeService geocodeService;

    @Autowired
    private CurrentUserDAOInterface currentUserDataAccess;

    /* ---------  Methods  --------- */

    public PostController() {
    }

    public void setCurrentInspectedPost(Post post) {
        this.currentPost = post;
    }

    public void setPostType(String type) {
        currentPost.setPostType(type.equals("giving away"));
    }

    public String getPostType() {
        if (currentPost.getPostType()) {
            return "give";
        } else {
            return "take";
        }
    }

    public Post getCurrentPost() {
        return currentPost;
    }

    public void setPostTitle(String title) {
        currentPost.setTitle(title);
    }

    public String getPostTitle() {
        return currentPost.getTitle();
    }

    public void setPostDescription(String description) {
        currentPost.setDescription(description);
    }

    public String getPostDescription() {
        return currentPost.getDescription();
    }

    public void setPostPrivacy(String privacy) {
        currentPost.setPublic(privacy.equals("Public"));
    }

    public String getPostPrivacy() {
        if (currentPost.isPublic()) {
            return "Public";
        } else {
            return "Accounts";
        }
    }

    public String getPostalCode() {
        return currentPost.getPostalCode();
    }

    public void setPostPostalCode(String postal) {
        try {
            geocodeService.geolocationFromPostalCode(postal);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        currentPost.setLatitude(geocodeService.getLat());
        currentPost.setLongitude(geocodeService.getLon());
        currentPost.setPostalCode(postal);
    }

    public void setPostTags(ArrayList<String> tagList) {
        currentPost.setTags(tagList);
    }

    public ArrayList<String> getPostTags() {
        return currentPost.getTags();
    }

    public void setPostContactEmail(String contactEmail) {
        currentPost.setContactEmail(contactEmail);
    }

    public void setPostID() {
        currentPost.setId(Integer.toString(currentPost.hashCode()));
    }

    public String getPostID() {
        return currentPost.getId();
    }

    public void removePost() {
        postsDataAccess.deletePost(currentPost);
    }

    public Boolean verifyAndPublish() {
        if (currentPost.isComplete()) {
            currentPost.setDatePosted(new Date());
            postsDataAccess.addPost(currentPost);
            return true;
        } else {
            System.err.println("Error Verifying post creation");
            return false;
        }
    }

    public Boolean verifyAndUpdatePost() {
        if (currentPost.isComplete()) {
            postsDataAccess.updatePost(currentPost);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Obtain the users created posts
     *
     * @return lists of users created posts
     */
    public List<Post> getUserCreatedPosts() {
        List<String> usersPosts = currentUserDataAccess.getCurrentAccount().getPostIds();
        return postsDataAccess.findByIds(usersPosts);
    }
}
