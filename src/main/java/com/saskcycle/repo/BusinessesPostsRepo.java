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

    /**
     * Find all business that take in items based on inputted tag
     * @param tags predefined string respresenting a searchable tag
     * @return A list of business objects from DB that have the inputted tag
     */
     List<Business> findAllByTags(String tags);

     Business findByTitle(String title);

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
