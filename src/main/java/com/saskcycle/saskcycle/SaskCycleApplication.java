package com.saskcycle.saskcycle;

import com.saskcycle.repo.UserAccountRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import java.util.List;
import java.util.Collection;
import com.saskcycle.model.*;
import com.saskcycle.controller.*;
import org.springframework.boot.CommandLineRunner;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.*;

@ComponentScan(basePackages = {"com.saskcycle"})
@EntityScan("com.saskcycle.model")
@EnableMongoRepositories(basePackageClasses = UserAccountRepo.class)
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class SaskCycleApplication extends SpringBootServletInitializer /*implements CommandLineRunner*/ {
//
//	@Autowired
//	private com.saskcycle.repo.PostsRepo PR;
//
//	@Autowired
//	private SearchController SC;
//
//	@Autowired
//	private com.saskcycle.repo.BusinessesPostsRepo BR;


	public static void main(String[] args) {
		SpringApplication.run(SaskCycleApplication.class, args);
	}


//	@Override
//	public void run(String... args) throws Exception {
//		System.out.println("hi");
////		System.out.println(count());
////		System.out.println(count2());
//////
//		List<Post> newList = SC.getAllListings();
////
//		newList.clear();
//		for (Post p : newList) System.out.println(p.description);
//		System.out.println(newList);
//
//		newList = SC.getAllPosts();
//
//		for(Post p : newList) System.out.println(p.description);

//		newList = SC.getAllListingsByTag("Art");
//		for(Post p : newList) System.out.println(p.tags);


//		newList = SC.getAllPostsByKeyword("x");
//				for(Post p : newList) System.out.println(p.title);
//
//	}
//




		// FOR ADDING POSTS TO DATABASE WITH HASTE
//		ArrayList<String> tags = new ArrayList<>();
//		tags.add("Toys (plastic)");
//		tags.add("Toys (electric)");
//		tags.add("Electronics");
//		tags.add("Cloths");
//		tags.add("Furniture");
//		tags.add("Appliances");
//		tags.add("Art");
//
//
//
//
//		Business SuperStore = new Business("SuperStore", "A store", "1", "1.2KM", tags);
//		Post p1 = new Post("Clothing available", "Get some", "1", null, "1.2KM", tags);
//		tags.remove(0);
//		Business SuperStore2 = new Business("SuperStore2", "A store2", "2", "1.4KM", tags);
//		Post p2 = new Post("No plastic", "Get some no plast", "2", null, "1.3KM", tags);
//		Business SuperStore3 = new Business("SuperStorn", "A storn", "3", "1.2KM", tags);
//		Post p3 = new Post("Clothing available", "Get some no toy", "3", null, "1.2KM", tags);
//		tags.remove(0);
//		Business SuperStore4 = new Business("SuperStorm", "A storm", "4", "1.4KM", tags);
//		Post p4 = new Post("No plastic", "Get some no elect", "4", null, "1.3KM", tags);
//
//
//		PR.insert(p1);
//		PR.insert(p2);
//		PR.insert(p3);
//		PR.insert(p4);
//
//		BR.insert(SuperStore);
//		BR.insert(SuperStore2);
//		BR.insert(SuperStore3);
//		BR.insert(SuperStore4);
//
//		System.out.println(BR.findAll());
//
//		System.out.println(PR.findAll());

//
//
//	}
//
//	public int count(){
//		return (int) PR.count();
//	}
//
//	public int count2(){
//		return (int) BR.count();
//	}
}