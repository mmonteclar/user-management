package com.michelle.user.service;

import com.michelle.user.model.User;
import com.michelle.user.repository.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired private UserRepository userRepository;

  /**
   * Search user by UserId
   *
   * @param userId int
   * @return
   */
  public Optional<User> findUserByUserId(int userId) {
    return userRepository.findByUserId(userId);
  }

  /**
   * Add or Update a user
   *
   * @param user User
   */
  public User updateUser(User user) {
    Optional<User> userDetail = findUserByUserId(user.getUserId());
    if (userDetail.isPresent()) {
      user.setId(userDetail.get().getId());
    }

    return userRepository.save(user);
  }

  /**
   * Add a user
   *
   * @param user User
   */
  public User addUser(User user) {
    return userRepository.save(user);
  }
}
