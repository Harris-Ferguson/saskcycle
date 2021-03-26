package com.saskcycle.saskcycle.security;

import com.saskcycle.model.Account;
import com.saskcycle.repo.UserAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SaskCycleUserDetailsService implements UserDetailsService {

  @Autowired private UserAccountRepo UAR;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account user = UAR.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(
          "Username " + username + " not found in User Account Repository");
    }
    return User.withUsername(user.getUsername())
        .password(user.getPassword())
        .authorities(user.getAuthorities())
        .build();
  }
}
