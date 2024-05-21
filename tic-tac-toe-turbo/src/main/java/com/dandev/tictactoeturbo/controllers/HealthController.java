package com.dandev.tictactoeturbo.controllers;

import com.dandev.tictactoeturbo.socket.infra.reflection.annotations.Get;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/health")
public class HealthController {

    @GetMapping
    public ResponseEntity<?> health(){
        return ResponseEntity.ok("application alive");
    }
}
