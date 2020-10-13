package com.michelle.user;

import com.michelle.user.model.User;
import com.michelle.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class UserManagementApplication {

  public static void main(String[] args) {
    SpringApplication.run(UserManagementApplication.class, args);
  }

  @Bean
  CommandLineRunner initDatabase(UserRepository userRepository) {

    return args -> {
      userRepository.save(
          new User(
              1,
              111,
              "Mrs",
              "Lea",
              "Go",
              "Female",
              "Francis Road",
              "NSW",
              "Sydney",
              2000,
              "ADMIN",
              "111"));

      userRepository.save(
          new User(
              2,
              222,
              "Mrs",
              "Lea",
              "Go",
              "Female",
              "Francis Road",
              "NSW",
              "Sydney",
              2000,
              "ADMIN",
              "222"));

      userRepository.save(
          new User(
              3,
              333,
              "Mrs",
              "Lea",
              "Go",
              "Female",
              "Francis Road",
              "NSW",
              "Sydney",
              2000,
              "USER",
              "333"));

      userRepository.save(
          new User(
              4,
              444,
              "Mrs",
              "Lea",
              "Go",
              "Female",
              "Francis Road",
              "NSW",
              "Sydney",
              2000,
              "USER",
              "444"));

      userRepository.save(
          new User(
              5,
              555,
              "Mrs",
              "Lea",
              "Go",
              "Female",
              "Francis Road",
              "NSW",
              "Sydney",
              2000,
              "NOT_ALLOWED",
              "555"));
    };
  }
}
