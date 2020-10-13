package com.michelle.user.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/** The Class SecurityConfigurer. */
@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Bean
  public UserDetailsService userDetailsService() {
    return new DatabaseUserDetailsService();
  }

  /**
   * Secure the endpoint with HTTP Basic authentication.
   *
   * @param http the http
   * @throws Exception the exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http
        // HTTP Basic authentication
        .httpBasic()
        .and()
        .authorizeRequests()
        .antMatchers(HttpMethod.PUT, "/user/add/**")
        .hasRole("ADMIN")
        .antMatchers(HttpMethod.GET, "/user/**")
        .permitAll()
        .and()
        .csrf()
        .disable()
        .formLogin()
        .disable();
  }
}
