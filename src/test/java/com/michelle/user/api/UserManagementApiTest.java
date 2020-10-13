package com.michelle.user.api;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.michelle.user.dto.Address;
import com.michelle.user.dto.UserDetails;
import com.michelle.user.model.User;
import com.michelle.user.repository.UserRepository;
import com.michelle.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserManagementApi.class)
class UserManagementApiTest {

  @Autowired private MockMvc mockMvc;

  @Autowired WebApplicationContext webApplicationContext;

  @MockBean private UserService userService;

  @MockBean private UserManagementApi userApplication;

  @MockBean private UserDetailsService userDetailsService;

  @MockBean private UserRepository userRepository;

  @Autowired ObjectMapper objectMapper;

  protected Address address = new Address("Francis Road", "NSW", "Sydney", 2000);
  protected UserDetails userDetails = new UserDetails(14, "Mrs", "Lea", "Go", "Female", address);
  protected User user =
      new User(
          1, 14, "Mrs", "Lea", "Go", "Female", "Francis Road", "NSW", "Sydney", 2000, "ADMIN", "");

  @BeforeEach
  public void setUp() {
    this.mockMvc = webAppContextSetup(webApplicationContext).build();
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void getUserTestInvalidUserId_thenThrowsException() throws Exception {

    mockMvc.perform(get("/user/aaa")).andExpect(status().isBadRequest());
  }

  @Test
  void getUserTestValidUserId() throws Exception {

    Mockito.when(userService.updateUser(user)).thenReturn(user);
    Mockito.when(userApplication.userToUserDetails(user)).thenReturn(userDetails);
    mockMvc
        .perform(
            put("/user/add")
                .content(objectMapper.writeValueAsString(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

    mockMvc.perform(get("/user/14")).andExpect(status().isOk());
  }

  @Test
  void addUserTestValidUserId() throws Exception {

    Mockito.when(userService.updateUser(user)).thenReturn(user);
    Mockito.when(userApplication.userToUserDetails(user)).thenReturn(userDetails);
    mockMvc
        .perform(
            put("/user/add")
                .content(objectMapper.writeValueAsString(userDetails))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
