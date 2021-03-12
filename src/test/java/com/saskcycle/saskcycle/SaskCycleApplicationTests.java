package com.saskcycle.saskcycle;

import com.saskcycle.model.Business;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class SaskCycleApplicationTests {
	@Autowired
	private com.saskcycle.repo.BusinessesPostsRepo BR;


	@Test
	void contextLoads() {
//		Assertions.assertEquals(1,2);

		// FOR ADDING POSTS TO DATABASE WITH HASTE
		ArrayList<String> tags = new ArrayList<>();
		tags.add("Computers");
		tags.add("Electronics");
		tags.add("Decorations");
		tags.add("Cookware");
		tags.add("Clothing");
		tags.add("Appliances");
		tags.add("Art");
//
//
//
//
		Business store = new Business("Test Store", "I am not real I am a test", "6", "10.1KM", tags);
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
		BR.insert(store);
//		BR.insert(SuperStore2);
//		BR.insert(SuperStore3);
//		BR.insert(SuperStore4);
//
		System.out.println(BR.count());
//
//		System.out.println(PR.findAll());
	}

}
