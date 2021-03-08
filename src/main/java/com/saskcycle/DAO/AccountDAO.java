package com.saskcycle.DAO;
// This class handles the interactions between the database and the system, helping add some abstraction to
// our design. This implementation was inspired by the following tutorial:
/*
https://medium.com/@ahmodadeola/creating-restful-apis-with-spring-boot-2-and-mongodb-3bba937cd438
 */


import com.saskcycle.model.*;
import com.saskcycle.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class AccountDAO implements UserDAOInterface {


    /* --------- Attributes ------------ */

    // This is the actual connection with the Account repo - the rest of the class will use this to implement methods.
    @Autowired
    private UserAccountRepo UAR;


    /* ----------- Methods ------------- */

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

    // TODO: implement search by name
    @Override
    public Account searchByName(String name) {
        return null;
    }

    // TODO: Implement search by Email
    @Override
    public Account searchByEmail(String email) {
        return null;
    }

    @Override
    public boolean checkPassword(String attempt, String email) {
        // search by email address
        Account searchingAccount = searchByEmail(email);
        return attempt.equals(searchingAccount.getPassword());
    }

    // TODO: Change to boolean?
    @Override
    public Account addAccount(Account account) {
        UAR.insert(account);
        return account;
    }

    // TODO: Change to boolean?
    @Override
    public void deleteAccount(Account account) {
        UAR.delete(account);
    }

    // TODO: IMPLEMENT
    @Override
    public List<Post> getFeed() {
        return null;
    }

    // TODO: IMPLEMENT
    @Override
    public void removePost(Post post) {

    }

    // TODO: IMPLEMENT
    @Override
    public Post addPost(Post post) {
        return null;
    }
}
