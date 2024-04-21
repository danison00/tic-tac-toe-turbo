package com.dandev.tictactoeturbo.repository;

import com.dandev.tictactoeturbo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.UUID;

public interface UserRespository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<UserDetails> findByUsername(String username);

    boolean existsByUsername(String username);
}
