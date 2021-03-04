package com.saskcycle.saskcycle;


import com.mongodb.ClientSessionOptions;
import com.mongodb.client.*;
import com.mongodb.connection.ClusterDescription;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@SpringBootApplication
public class SaskCycleApplication {

//	private MongoRepository repository;

	public static void main(String[] args) {

		SpringApplication.run(SaskCycleApplication.class, args);

//		Mongo

	}

//	public void run(String...args) throws Exception{
//		repository.
//	}
}
