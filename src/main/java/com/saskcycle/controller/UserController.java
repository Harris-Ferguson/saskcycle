package com.saskcycle.controller;

import com.saskcycle.repo.UserAccountRepo;
import com.saskcycle.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

public class UserController {

    @Autowired
    private UserAccountRepo UAR;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public Account add (@RequestBody Account account){
        return UAR.save(account);
    }

}

