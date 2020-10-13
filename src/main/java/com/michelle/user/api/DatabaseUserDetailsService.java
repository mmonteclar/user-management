package com.michelle.user.api;

import com.michelle.user.model.User;
import com.michelle.user.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** The Class DatabaseUserDetailsService. */
public class DatabaseUserDetailsService implements UserDetailsService {

  /** The user repository. */
  @Autowired private UserRepository userRepository;

  /**
   * Load user by username.
   *
   * @param username the username
   * @return the user details
   */
  public UserDetails loadUserByUsername(String username) {

    Optional<User> user = userRepository.findByUserId(Integer.valueOf(username));

    if (user.isPresent()) {

      List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
      grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.get().getRole()));

      return new org.springframework.security.core.userdetails.User(
          String.valueOf(user.get().getUserId()),
          "{noop}" + user.get().getPassword(),
          grantedAuthorities);

    } else {
      throw new UsernameNotFoundException(username);
    }
  }
}
