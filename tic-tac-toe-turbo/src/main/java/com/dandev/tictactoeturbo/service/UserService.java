package com.dandev.tictactoeturbo.service;

import com.dandev.tictactoeturbo.model.entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    User save(User user);

    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);

   UserDetails loadUserByUsername(String username);

    boolean usernameAlreadyExists(String username);

}
