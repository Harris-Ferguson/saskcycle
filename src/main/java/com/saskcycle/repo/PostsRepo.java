package com.saskcycle.repo;

import com.saskcycle.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PostsRepo extends MongoRepository<Post, String> {
    Post searchById(String id);

    List<Post> findByIdIn(List<String> ids);
}
