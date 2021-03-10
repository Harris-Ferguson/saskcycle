package com.saskcycle.repo;

import com.saskcycle.model.Account;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountRepo extends MongoRepository<Account, String> {
    // This gives us all the default MongoDB actions!

    Account findByName(String name);

    Account findByEmail(String email);

    Account findByEmailAndName(String name, String email);


}