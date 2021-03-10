package com.saskcycle.controller;


import com.saskcycle.model.Account;
import com.saskcycle.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "http://localhost:8080")
//@RestController
//@RequestMapping("/api")
//public class AccountController {
//
//    @Autowired
//    UserAccountRepo UARepo;
//
//    @GetMapping("/glen")
//    public void addAcount(){
////        Account glen = new Account("Glne","Hey","er","heyy");
////        UARepo.save(glen);
//    }
//
//}
