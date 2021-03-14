package com.saskcycle.repo;

import com.saskcycle.model.Business;
import com.saskcycle.model.Post;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BusinessesPostsRepo extends MongoRepository<Business, String> {

//    List<Post> findByTagRegex(String tag);
//
////    List<Post> findAll(Sort date);
//
//    Post findByLocationRegex(String location, String tag);
//
//    Post findByLocationAndTag(String location, String tag);
//
//    Post findByDateAndTag(Date date, String tag);
//
//    Post findByDateRegex(Date date);


}
