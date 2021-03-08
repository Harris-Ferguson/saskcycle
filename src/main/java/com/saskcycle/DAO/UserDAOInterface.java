package com.saskcycle.DAO;

// Created with guidance from the following tutorial:
// https://medium.com/@ahmodadeola/creating-restful-apis-with-spring-boot-2-and-mongodb-3bba937cd438


import com.saskcycle.model.Account;
import com.saskcycle.model.Post;

import java.util.ArrayList;

public interface UserDAOInterface {

    ArrayList<Account> AllAccounts();

    Account searchByID(String id);

    Account searchByName(String name);

    Account searchByEmail(String email);

    boolean checkPassword();

    Account addAccount(Account account);

    void deleteAccount(Account account);

    ArrayList<Post> getPostList();

    void removePost(Post post);

    Post addPost(Post post);




}
