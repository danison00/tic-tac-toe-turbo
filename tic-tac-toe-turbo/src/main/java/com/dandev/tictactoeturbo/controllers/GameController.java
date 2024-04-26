package com.dandev.tictactoeturbo.controllers;


import com.dandev.tictactoeturbo.model.entity.Game;
import com.dandev.tictactoeturbo.webSocketConnection.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;
    @GetMapping
    public ResponseEntity<Game> getGame(@RequestParam() UUID gameId){
        if (gameId == null) return ResponseEntity.ok().build();
        return ResponseEntity.ok(gameService.getGame(gameId));
    }

}
