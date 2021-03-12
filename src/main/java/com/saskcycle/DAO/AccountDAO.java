package com.saskcycle.DAO;
// This class handles the interactions between the database and the system, helping add some abstraction to
// our design. This implementation was inspired by the following tutorial:
/*
https://medium.com/@ahmodadeola/creating-restful-apis-with-spring-boot-2-and-mongodb-3bba937cd438
 */


import com.saskcycle.model.*;
import com.saskcycle.repo.UserAccountRepo;
import com.saskcycle.saskcycle.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.lang.IllegalArgumentException;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.*;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.caseSensitive;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class AccountDAO implements UserDAOInterface {


    /* --------- Attributes ------------ */

    // This is the actual connection with the Account repo - the rest of the class will use this to implement methods.
    @Autowired
    private UserAccountRepo UAR;

    @Autowired
    private PasswordEncoder encoder;


    /* ----------- Methods ------------- */

    @Autowired
    public AccountDAO(UserAccountRepo repo){
        UAR = repo;
    }




    @Override
    public List<Account> AllAccounts() {
        return UAR.findAll();
    }



    @Override
    public Optional<Account> searchByID(String id) {
        return  UAR.findById(id);
    }



    @Override
    public Account searchByName(String name) {
        return UAR.findByUsername(name);
    }

    @Override
    public Account searchByEmail(String email) {
        return null;
    }

    @Override
    public Account updateAccount(Account account) {
        return UAR.save(account);
    }

    @Override
    public Account addAccount(Account account) {
        return UAR.insert(account);
    }

    @Override
    public void deleteAccount(Account account) {
        UAR.delete(account);
    }

    @Override
    public Feed getPosts(Account account) {
        return account.getPosts();
    }

    @Override
    public Feed getWishlist(Account account) {
        return account.getWishlist();
    }

    @Override
    public void removePost(Post post, Account account) {
        account.getWishlist().remove(post);
        UAR.save(account);
    }

    @Override
    public void addPost(Post post, Account account) {
        account.getWishlist().add(post);
        UAR.save(account);
    }

    @Override
    public boolean accountExists(Account account) {
        return UAR.existsByEmail(account.getEmail()) || UAR.existsByUsername(account.getUsername());
    }

    @Override
    public Account register(String username, String email, String password) {
        // this is janky but we're building a user details user then using its authorities to build an Account
        // we could probably find another way to do this, OR abstract this to a method in Account
        UserDetails newUser = User.withUsername(username).password(password).roles("USER").build();

        Account account = new Account(
                username,
                encoder.encode(password),
                newUser.getAuthorities(),
                Long.toString(UAR.count()),
                email,
                "USER",
                new Feed(),
                new Feed(),
                0.0,
                new ArrayList<Notification>()
                );
        if(accountExists(account)){
            throw new IllegalArgumentException("Account already exists");
        }
        return addAccount(account);
    }
}
