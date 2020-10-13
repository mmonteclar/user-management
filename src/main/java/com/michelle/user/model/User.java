package com.michelle.user.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "User")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  int id;

  @Column(unique = true)
  int userId;

  @Column(length = 5)
  String title;

  String firstn;
  String lastname;
  String gender;
  String addressStreet;
  String addressCity;
  String addressState;
  int addressPostcode;
  String role;
  String password;
}
