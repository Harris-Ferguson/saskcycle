package com.saskcycle.saskcycle;

import com.saskcycle.repo.UserAccountRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@ComponentScan(basePackages = {"com.saskcycle"})
@EntityScan("com.saskcycle.model")
@EnableMongoRepositories(basePackageClasses = UserAccountRepo.class)
@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class SaskCycleApplication extends SpringBootServletInitializer {
  public static void main(String[] args) {
    SpringApplication.run(SaskCycleApplication.class, args);
  }
}
