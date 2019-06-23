package com.oauth2.oauth2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MyUserDetailsService implements UserDetailsService {

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    log.info("Username: " + userName);

    // hard coding here but will change to findByUserName and getPassword()
    String password = passwordEncoder.encode("123456");

    return new User(userName, password, true, true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
  }

}
