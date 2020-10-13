package com.michelle.user.dto;

import com.michelle.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {

  int userId;
  String title;
  String firstn;
  String lastname;
  String gender;
  Address address;

  public User toUser() {
    User user = new User();
    user.setUserId(this.getUserId());
    user.setFirstn(this.getFirstn());
    user.setLastname(this.getLastname());
    user.setTitle(this.getTitle());
    user.setGender(this.getGender());
    user.setAddressStreet(this.getAddress().getStreet());
    user.setAddressPostcode(this.getAddress().getPostcode());
    user.setAddressCity(this.getAddress().getCity());
    user.setAddressState(this.getAddress().getState());
    return user;
  }
}
