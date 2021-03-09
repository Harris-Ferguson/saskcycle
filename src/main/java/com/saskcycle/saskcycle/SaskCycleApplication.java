package com.saskcycle.saskcycle;


import com.saskcycle.model.Account;
import com.saskcycle.model.authorities.AccountAuthority;
import com.saskcycle.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

@SpringBootApplication
@ComponentScan(basePackages = {"com.saskcycle"})
@EntityScan("com.saskcycle.model")
@EnableMongoRepositories(basePackageClasses = UserAccountRepo.class)
public class SaskCycleApplication implements CommandLineRunner {


//	private MongoRepository repository;

	@Autowired
	private UserAccountRepo UAR;



	public static void main(String[] args) {
		SpringApplication.run(SaskCycleApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("hi");
		System.out.println(count());

		ArrayList<GrantedAuthority> authorities = new ArrayList<>();

		AccountAuthority auth = new AccountAuthority();

		authorities.add(auth);


		Account newAccount = new Account("Harris add", "The Big Password",
				authorities,"101", "FakeEmail@123.com");
		UAR.insert(newAccount);
//		System.out.println(UAR.toString());

	}

	public int count(){
		return (int) UAR.count();
	}
}
