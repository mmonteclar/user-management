package com.michelle.user.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.michelle.user.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class IntegrationTest {

  @Container private static MySQLContainer database = new MySQLContainer();

  @Autowired UserService userService;

  @DynamicPropertySource
  static void databaseProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", database::getJdbcUrl);
    registry.add("spring.datasource.username", database::getUsername);
    registry.add("spring.datasource.password", database::getPassword);
  }

  @Test
  public void saveUserTest() {

    User user01 = new User();
    user01.setUserId(1234);
    user01.setTitle("Mr");

    User user02 = new User();
    user02.setUserId(1234);

    user01 = this.userService.updateUser(user01);

    assertThat(
        this.userService
                .findUserByUserId(user01.getUserId())
                .get()
                .getTitle()
                .compareTo(user01.getTitle())
            == 0);
  }
}
