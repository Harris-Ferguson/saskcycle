package com.saskcycle.DAO;
// This class handles the interactions between the database and the system, helping add some
// abstraction to
// our design. This implementation was inspired by the following tutorial:
/*
https://medium.com/@ahmodadeola/creating-restful-apis-with-spring-boot-2-and-mongodb-3bba937cd438
 */

import com.saskcycle.model.Account;
import com.saskcycle.model.Post;
import com.saskcycle.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountDAO implements UserDAOInterface {

    /* --------- Attributes ------------ */

    // This is the actual connection with the Account repo - the rest of the class will use this to
    // implement methods.
    @Autowired
    private final UserAccountRepo UAR;


    /* ----------- Methods ------------- */

    @Autowired
    public AccountDAO(UserAccountRepo repo) {
        UAR = repo;
    }

    @Override
    public List<Account> AllAccounts() {
        return UAR.findAll();
    }

    @Override
    public Optional<Account> searchByID(String id) {
        return UAR.findById(id);
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
  public ArrayList<String> getPosts(Account account) {
    return account.getPostIds();
  }

  @Override
  public ArrayList<String> getWishlist(Account account) {
    return account.getWishlist();
  }

    @Override
    public void removePost(Post post, Account account) {
        account.getWishlist().remove(post);
        UAR.save(account);
    }

  @Override
  public void addPost(Post post, Account account) {
    account.getWishlist().add(post.id);
    UAR.save(account);
  }

    @Override
    public boolean accountExists(Account account) {
        return UAR.existsByEmail(account.getEmail()) || UAR.existsByUsername(account.getUsername());
    }

}
