package com.dandev.tictactoeturbo.service.implementations;

import com.dandev.tictactoeturbo.infra.exceptions.UsernameUnavailable;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.repository.UserRespository;
import com.dandev.tictactoeturbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceimpl implements UserService {


    @Autowired
    private PasswordEncoder passwordEncoder;

    private final UserRespository userRespository;

    public UserServiceimpl(UserRespository userRespository) {
        this.userRespository = userRespository;
    }

    @Override
    public User save(User user) {

        if (user.getUsername() != null)
            if (this.loadUserByUsername(user.getUsername()) != null)
                throw new UsernameUnavailable("username indiponível");

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return this.userRespository.save(user);


    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.userRespository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return this.userRespository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return this.userRespository.findByUsername(username).orElse(null);
    }

    @Override
    public boolean usernameAlreadyExists(String username) {
        return this.userRespository.existsByUsername(username);
    }


}
