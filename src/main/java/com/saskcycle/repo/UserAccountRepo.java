package com.saskcycle.repo;

import com.saskcycle.model.Account;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
// This gives us all the default MongoDB actions!
@Repository
public interface UserAccountRepo extends MongoRepository<Account, String> {
    Account findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}


