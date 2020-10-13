package com.michelle.user.api;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michelle.user.dto.Address;
import com.michelle.user.dto.UserDetails;
import com.michelle.user.model.User;
import java.nio.charset.Charset;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Testcontainers
public class IntegrationTest {

  @Container private static MySQLContainer database = new MySQLContainer();

  @Autowired private TestRestTemplate testRestTemplate;

  @Autowired ObjectMapper objectMapper;

  @DynamicPropertySource
  static void databaseProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", database::getJdbcUrl);
    registry.add("spring.datasource.username", database::getUsername);
    registry.add("spring.datasource.password", database::getPassword);
  }

  protected Address address = new Address("Francis Road", "NSW", "Sydney", 2000);
  protected UserDetails userDetails = new UserDetails(14, "Mrs", "Lea", "Go", "Female", address);
  protected User user =
      new User(
          1, 14, "Mrs", "Lea", "Go", "Female", "Francis Road", "NSW", "Sydney", 2000, "ADMIN", "");

  @Test
  void addUpdateTest() throws Exception {

    HttpHeaders headers = new HttpHeaders();
    String auth = "222" + ":" + "222";
    byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
    String authHeader = "Basic " + new String(encodedAuth);
    headers.set("Authorization", authHeader);

    ResponseEntity<UserDetails> exchange =
        this.testRestTemplate.exchange(
            "/user/add", HttpMethod.PUT, new HttpEntity<>(userDetails, headers), UserDetails.class);

    assertThat(exchange.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(exchange.getBody().getTitle().compareTo(userDetails.getTitle())).isZero();
  }

  @Test
  void addUpdateFailTest() throws Exception {

    HttpHeaders headers = new HttpHeaders();
    String auth = "222" + ":" + "222";
    byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
    String authHeader = "Basic " + new String(encodedAuth);
    headers.set("Authorization", authHeader);

    ResponseEntity<UserDetails> exchange =
        this.testRestTemplate.exchange(
            "/user/add", HttpMethod.PUT, new HttpEntity<>(userDetails, headers), UserDetails.class);

    assertThat(exchange.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(exchange.getBody().getTitle().compareTo(userDetails.getTitle())).isZero();

    UserDetails userDetailsError =
        new UserDetails(
            14,
            "Extend to more than 5 to fail database constraints",
            "Lea",
            "Go",
            "Female",
            address);

    exchange =
        this.testRestTemplate.exchange(
            "/user/add",
            HttpMethod.PUT,
            new HttpEntity<>(userDetailsError, headers),
            UserDetails.class);

    assertThat(exchange.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(exchange.getBody()).isEqualTo(null);
  }

  @Test
  void getTest() throws Exception {

    HttpHeaders headers = new HttpHeaders();
    String auth = "333" + ":" + "333";
    byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
    String authHeader = "Basic " + new String(encodedAuth);
    headers.set("Authorization", authHeader);

    ResponseEntity<UserDetails> exchange =
        this.testRestTemplate.exchange(
            "/user/333", HttpMethod.GET, new HttpEntity<>(userDetails, headers), UserDetails.class);

    assertThat(exchange.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(exchange.getBody().getTitle().compareTo(userDetails.getTitle())).isZero();
  }
}
