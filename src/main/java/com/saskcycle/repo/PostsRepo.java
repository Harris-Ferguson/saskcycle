package com.saskcycle.repo;

import com.saskcycle.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface PostsRepo extends MongoRepository<Post, String> {

    /* These methods may be used to improve the efficiency of database interactions,
    * but currently are not operational. Possibly usable for a future version of SaskCycle*/

      Post findByTag(String tag);

      Post findByLocation(String location, String tag);

      Post findByLocationAndTag(String location, String tag);

      Post findByDateAndTag(Date date, String tag);

      Post findByDate(Date date);

}
