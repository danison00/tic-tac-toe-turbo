package com.dandev.tictactoeturbo.controllers;


import com.dandev.tictactoeturbo.controllers.dtos.NewUserRequest;
import com.dandev.tictactoeturbo.controllers.dtos.UsernameExistsDto;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("register")
    public ResponseEntity<HttpStatus> register(@RequestBody() NewUserRequest user) {


        System.out.println(user.toString());

        this.userService.save(new User(null, user.name(), null, user.username(), user.password()));


        return ResponseEntity.ok().build();
    }

    @GetMapping("username-exists")
    public ResponseEntity<UsernameExistsDto> usernameExists(@RequestParam("username") String username) {

        return ResponseEntity.ok(new UsernameExistsDto(this.userService.usernameAlreadyExists(username)));
    }
}
