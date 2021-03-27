package com.saskcycle.DAO;

import com.saskcycle.model.Post;

import java.util.List;

public interface PostsDAOInterface {

    /**
     * Gives the user every Post in the MongoDB Post database
     *
     * @return a list of posts from the database
     */
    List<Post> AllPosts();

    /**
     * Returns a post corresponding to a specific ID
     *
     * @param id The id of the sought-after post
     * @return a post with the id specified
     */
    Post searchByID(String id);

    /**
     * Adds a specified post to the database
     *
     * @param post: The desired added post
     * @return the post which was added
     */
    void addPost(Post post);

    /**
     * Update the database entry for a given post, matched by the post ID
     *
     * @param post post to update
     */
    void updatePost(Post post);

    /**
     * Removes the specified post from the database
     *
     * @param post a post to be removed
     */
    void deletePost(Post post);

    /**
     * Find all the posts matching all the given IDs
     *
     * @param ids A list of ids to check for
     * @return a list of posts for each id found in the database
     */
    List<Post> findByIds(List<String> ids);
}


