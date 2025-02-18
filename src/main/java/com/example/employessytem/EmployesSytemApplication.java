package com.example.employessytem;

import com.example.employessytem.service.impl.IUserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class EmployesSytemApplication {

  public static void main(String[] args) {
    SpringApplication.run(EmployesSytemApplication.class, args);
  }

  @Bean
  public CommandLineRunner run(IUserService userService) {
    return args -> {
      if (!userService.adminExists()) {
        var admin = userService.registerAdmin();
        System.out.println("Admin: " + admin);
      } else {
        System.out.println("Admin already exists.");
      }
    };
  }
}
