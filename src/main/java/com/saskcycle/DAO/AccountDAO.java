package com.saskcycle.DAO;
// This class handles the interactions between the database and the system, helping add some abstraction to
// our design. This implementation was inspired by the following tutorial:
/*
https://medium.com/@ahmodadeola/creating-restful-apis-with-spring-boot-2-and-mongodb-3bba937cd438
 */


import com.saskcycle.model.*;
import com.saskcycle.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountDAO implements UserDAOInterface {


    /* --------- Attributes ------------ */

    // This is the actual connection with the Account repo - the rest of the class will use this to implement methods.
    @Autowired
    private UserAccountRepo UAR;


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
    public boolean checkPassword(String attempt, String email) {
        // search by email address
        Account searchingAccount = searchByEmail(email);
        return attempt.equals(searchingAccount.getPassword());
    }



    @Override
    public Account addAccount(Account account) {
        UAR.insert(account);
        return account;
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
        return account.getWishlish();
    }

    @Override
    public void removePost(Post post, Account account) {
        account.getWishlish().remove(post);
        UAR.save(account);
    }

    @Override
    public void addPost(Post post, Account account) {
        account.getWishlish().add(post);
        UAR.save(account);
    }
}
