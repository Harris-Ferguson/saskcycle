package com.saskcycle.repo;

import com.saskcycle.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepo extends MongoRepository<Post, String> {
  //
  //    Post findByTag(String tag);
  //
  //    Post findByLocation(String location, String tag);
  //
  //    Post findByLocationAndTag(String location, String tag);
  //
  //    Post findByDateAndTag(Date date, String tag);
  //
  //    Post findByDate(Date date);

}
