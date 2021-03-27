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
    private final PostsRepo postsRepository;


    /* ---------   Methods  ---------- */
    public PostsDAO(PostsRepo repo) {
        this.postsRepository = repo;
    }

    @Override
    public List<Post> AllPosts() {
        return postsRepository.findAll();
    }

    @Override
    public Post searchByID(String id) {
        return postsRepository.searchById(id);
    }

    @Override
    public void addPost(Post post) {
        postsRepository.insert(post);
    }

    public void updatePost(Post post) {
        postsRepository.save(post);
    }

    @Override
    public void deletePost(Post post) {
        postsRepository.delete(post);
    }

    @Override
    public List<Post> findByIds(List<String> ids) {
        return postsRepository.findByIdIn(ids);
    }
}
