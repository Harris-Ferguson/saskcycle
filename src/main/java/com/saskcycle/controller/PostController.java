package com.saskcycle.controller;

import com.saskcycle.DAO.PostsDAO;
import com.saskcycle.DAO.PostsDAOInterface;
import com.saskcycle.model.Post;
import com.saskcycle.repo.PostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;


import java.util.ArrayList;
import java.util.Date;

@Controller
public class PostController {

    /* --------- Attributes --------- */

    private Post currentPost;

    @Autowired private PostsDAOInterface postDAD;

    /* ---------  Methods  --------- */

    public PostController(){
        this.currentPost = new Post();
    }

    public void setPostType(String type){
        if(type.equals("giving away")){
            currentPost.setPostType(true);
        }
        else {
            currentPost.setPostType(false);
        }
    }

    public String getPostType(){
        if(currentPost.getPostType()){
            return "give";
        }
        else {
            return "take";
        }
    }

    public void setPostTitle(String title){
        currentPost.setTitle(title);
    }

    public String getPostTitle(){
        return currentPost.getTitle();
    }

    public void setPostDescription(String description){
        currentPost.setDescription(description);
    }

    public String getPostDescription(){
        return currentPost.getDescription();
    }

    public void setPostPrivacy(String privacy){
        if(privacy.equals("Public")){
            currentPost.setPublic(true);
        }
        else {
            currentPost.setPublic(false);
        }
    }

    public String getPostPrivacy(){
        if(currentPost.isPublic()){
            return "Public";
        }
        else {
            return "Accounts";
        }
    }

    public void setPostDistance(Double dist){
        currentPost.setLocation(dist.toString()+"KM");
    }

    public Double getPostDistance(){
        // converts string distance from post to double and removes KM from end
        return Double.parseDouble(currentPost.getLocation().substring(0,currentPost.getLocation().length() - 2));
    }

    public void setPostPostalCode(String postal){
        currentPost.setLocation(postal);
    }

    public String getPostalCode(){
        return currentPost.getLocation();
    }

    public void setPostTags(ArrayList<String> tagList){
        currentPost.setTags(tagList);
    }

    public ArrayList<String> getPostTags(){
        return currentPost.getTags();
    }

    public void setPostContactEmail(String contactEmail){
        currentPost.setContactEmail(contactEmail);
    }

    public String getPostContactEmail(){
        return currentPost.getContactEmail();
    }

    public void setPostID(){
        Integer postDataBaseSize = currentPost.hashCode();
        currentPost.setId(postDataBaseSize.toString());
    }

    public Integer getPostID(){
        return Integer.parseInt(currentPost.getId());
    }

    public Boolean verifyAndPublish(){
        if(currentPost.isComplete()){
            currentPost.setDatePosted(new Date());
            postDAD.addPost(currentPost);
            return true;
        }
        else{
            System.err.println("Error Verifying post creation");
            return false;
        }
    }

}
