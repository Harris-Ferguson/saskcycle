package com.saskcycle.repo;

import com.saskcycle.model.Account;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepo extends MongoRepository<Account, String> {
    // This gives us all the default MongoDB actions!
}
