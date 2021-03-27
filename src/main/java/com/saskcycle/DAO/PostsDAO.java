package com.saskcycle.DAO;

import com.saskcycle.model.Post;
import com.saskcycle.repo.PostsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostsDAO implements PostsDAOInterface {

    /* --------- Attributes --------- */

    @Autowired
    private final PostsRepo PR;


    /* ---------   Methods  ---------- */
    public PostsDAO(PostsRepo repo) {
        this.PR = repo;
    }

    @Override
    public List<Post> AllPosts() {
        return PR.findAll();
    }

    @Override
    public Post searchByID(String id) {
        return PR.searchById(id);
    }

    @Override
    public void addPost(Post post) {
        PR.insert(post);
    }

    public void updatePost(Post post) {
        deletePost(searchByID(post.id));
        addPost(post);
    }

    @Override
    public void deletePost(Post post) {
        PR.delete(post);
    }

    @Override
    public List<Post> findByIds(List<String> ids) {
        return PR.findByIdIn(ids);
    }
}
