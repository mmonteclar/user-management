package com.michelle.user.api;

import com.google.gson.Gson;
import com.michelle.user.dto.Address;
import com.michelle.user.dto.UserDetails;
import com.michelle.user.model.User;
import com.michelle.user.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
public class UserManagementApi extends RequestManagement {

  @Autowired private CircuitBreakerFactory circuitBreakerFactory;
  @Autowired UserService userService;

  private Gson gson = new Gson();

  /**
   * Get User by UserId
   *
   * @param userId int
   * @return Response ResponseEntity<Object>
   */
  @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
  public ResponseEntity<Object> getUser(@PathVariable("userId") @Valid int userId) {

    this.logPayload("Incoming Request :", userId);

    Optional<User> user = userService.findUserByUserId(userId);
    UserDetails userDetails = new UserDetails();
    if (user.isPresent()) {
      userDetails = this.userToUserDetails(user.get());
    }

    this.logPayload("Outgoing response :", userDetails);

    return new ResponseEntity<>(
        user.isPresent() ? userDetails : "User Id not found", HttpStatus.OK);
  }

  /**
   * Add / Update UserDetails
   *
   * @param userDetails
   * @return User
   */
  @RequestMapping(value = "/user/add", method = RequestMethod.PUT)
  public ResponseEntity<UserDetails> addUser(@Valid @RequestBody UserDetails userDetails) {

    this.logPayload("Incoming Request :", userDetails);

    User user = userDetails.toUser();

    List<User> users = List.of(user);

    user =
        this.circuitBreakerFactory
            .create("updateUser")
            .run(
                () -> userService.updateUser(users.get(0)),
                throwable -> (User) this.errorManagement(throwable));

    UserDetails response = null;
    if (Optional.ofNullable(user).isPresent()) {
      response = this.userToUserDetails(user);
    }

    this.logPayload("Outgoing response :", userDetails);
    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  public UserDetails userToUserDetails(User user) {
    UserDetails userDetails = new UserDetails();
    userDetails.setUserId(user.getUserId());
    userDetails.setTitle(user.getTitle());
    userDetails.setFirstn(user.getFirstn());
    userDetails.setLastname(user.getLastname());
    userDetails.setGender(user.getGender());

    Address address = new Address();
    address.setCity(user.getAddressCity());
    address.setPostcode(user.getAddressPostcode());
    address.setState(user.getAddressState());
    address.setStreet(user.getAddressStreet());
    userDetails.setAddress(address);
    return userDetails;
  }
}
