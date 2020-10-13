package com.michelle.user.repository;

import com.michelle.user.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

  @Query("select u from User u where u.userId = ?1")
  Optional<User> findByUserId(int userId);
}
