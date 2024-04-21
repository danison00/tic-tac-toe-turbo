package com.dandev.tictactoeturbo.service;

import com.dandev.tictactoeturbo.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    User save(User user);

    Optional<User> findByEmail(String email);

   UserDetails loadUserByUsername(String username);

    boolean usernameAlreadyExists(String username);

}
