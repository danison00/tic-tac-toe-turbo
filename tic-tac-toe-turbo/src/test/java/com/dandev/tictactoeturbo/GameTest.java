package com.dandev.tictactoeturbo;

import com.dandev.tictactoeturbo.socket.dtos.*;
import com.dandev.tictactoeturbo.socket.enums.GamePlayStatus;
import com.dandev.tictactoeturbo.socket.infra.exceptions.MoveNotAllowed;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class GameTest {


    public void initGame() {

//        Assertions.assertNull(game.getPlayerWins());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.NO_TOUCH);
    }


    public void step1() {
//
//        game.makeMove(new Move(idGame, dan, 1, 1, 1, 1));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[1][1], dan.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, joao, 2, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[2][2], joao.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, dan, 0, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2).getBoard()[0][2], dan.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, joao, 1, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2).getBoard()[1][1], joao.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, dan, 2, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[2][0], dan.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, joao, 1, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0).getBoard()[1][0], joao.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 1, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(0).getBoard()[1][1], dan.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, joao, 0, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[0][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, dan, 2, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2).getBoard()[2][2], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, joao, 1, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2).getBoard()[1][1], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 1, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[1][2], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, joao, 1, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2).getBoard()[1][1], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 1, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[1][0], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//        // board 1x1 finish
//        Assertions.assertEquals("X", game.getBoardMain().getBoardsSecondary().get(1).get(1).getWinner());
//        Assertions.assertEquals("X", game.getBoardMain().getBoard()[1][1]);
//
//        game.makeMove(new Move(idGame, joao, 2, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(0).getBoard()[2][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 2, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2).getBoard()[2][0], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//        game.makeMove(new Move(idGame, joao, 2, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0).getBoard()[2][0], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 1, 1, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0).getBoard()[1][1], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//        //obrigado a jogar em um jogo finalizado. é possivel escolher
//        game.makeMove(new Move(idGame, joao, 0, 0, 2, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0).getBoard()[0][0], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        //board 2x0 finalizado
//        Assertions.assertEquals("O", game.getBoardMain().getBoardsSecondary().get(2).get(0).getWinner());
//        Assertions.assertEquals("O", game.getBoardMain().getBoard()[2][0]);
//
//        game.makeMove(new Move(idGame, dan, 1, 1, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(dan.marker(), game.getBoardMain().getBoardsSecondary().get(0).get(0).getBoard()[1][1]);
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//        // obrigado a jogar em um jogo já finalizado 1x1, pode escolher qual jogar
//        game.makeMove(new Move(idGame, joao, 0, 2, 1, 2));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2).getBoard()[0][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 0, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2).getBoard()[0][2], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//        game.makeMove(new Move(idGame, joao, 1, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2).getBoard()[1][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, dan, 2, 0, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2).getBoard()[2][0], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        // obrigado a jogar em um jogo já finalizado 1x1, pode escolher qual jogar
//        // deve ganhar o jogo da board
//        game.makeMove(new Move(idGame, joao, 1, 0, 0, 2));
//        Assertions.assertEquals("O", game.getBoardMain().getBoardsSecondary().get(0).get(2).getWinner());
//        Assertions.assertEquals("O", game.getBoardMain().getBoard()[0][2]);
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(0).getBoard()[1][0], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 2, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(0).getBoard()[2][1], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//        game.makeMove(new Move(idGame, joao, 1, 1, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1).getBoard()[1][1], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        // obrigado a jogar em um jogo já finalizado 1x1, pode escolher qual jogar
//        // deve ganhar o jogo da board
//        game.makeMove(new Move(idGame, dan, 0, 1, 1, 0));
//        Assertions.assertEquals(dan.marker(), game.getBoardMain().getBoardsSecondary().get(1).get(0).getWinner());
//        Assertions.assertEquals(dan.marker(), game.getBoardMain().getBoard()[1][0]);
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(dan.marker(), game.getBoardMain().getBoardsSecondary().get(1).get(0).getBoard()[0][1]);
//        Assertions.assertEquals(game.getPlayerCurrent(), joao);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, joao, 1, 0, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1).getBoard()[1][0], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        // obrigado a jogar em um jogo já finalizado 1x0, pode escolher qual jogar
//        game.makeMove(new Move(idGame, dan, 2, 2, 2, 2));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2).getBoard()[2][2], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        game.makeMove(new Move(idGame, joao, 2, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2).getBoard()[2][1], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 0, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1).getBoard()[0][0], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        game.makeMove(new Move(idGame, joao, 0, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0).getBoard()[0][1], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 2, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1).getBoard()[2][1], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        game.makeMove(new Move(idGame, joao, 0, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1).getBoard()[0][1], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 0, 1, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1).getBoard()[0][1], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        game.makeMove(new Move(idGame, joao, 1, 1, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1).getBoard()[1][1], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        // obrigado a jogar em um jogo já finalizado 1x1, deve escolher qual jogar
//        // venceu o jogo na board jogada
//        game.makeMove(new Move(idGame, dan, 1, 2, 2, 2));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2).getBoard()[1][2], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertEquals(dan.marker(), game.getBoardMain().getBoardsSecondary().get(2).get(2).getWinner());
//        Assertions.assertEquals(dan.marker(), game.getBoardMain().getBoard()[2][2]);
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        game.makeMove(new Move(idGame, joao, 2, 2, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2).getBoard()[2][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        // obrigado a jogar em um jogo já finalizado 1x0, pode escolher qual jogar
//        game.makeMove(new Move(idGame, dan, 0, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0).getBoard()[0][0], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        game.makeMove(new Move(idGame, joao, 2, 2, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[2][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        // obrigado a jogar em um jogo já finalizado 1x0, pode escolher qual jogar
//        game.makeMove(new Move(idGame, dan, 2, 1, 2, 1));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1).getBoard()[2][1], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        game.makeMove(new Move(idGame, joao, 1, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(1).getBoard()[1][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        game.makeMove(new Move(idGame, dan, 1, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2).getBoard()[1][2], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        // vence a board marcada
//        game.makeMove(new Move(idGame, joao, 0, 0, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(2).getBoard()[0][0], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//        Assertions.assertEquals(joao.marker(), game.getBoardMain().getBoardsSecondary().get(1).get(2).getWinner());
//        Assertions.assertEquals(joao.marker(), game.getBoardMain().getBoard()[1][2]);
//        Assertions.assertFalse(game.getBoardMain().isNoWins());
//
//
//        game.makeMove(new Move(idGame, dan, 2, 0, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0).getBoard()[2][0], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//
//
//        // obrigado a escolher nova board
//        // vence a board marcada
//        game.makeMove(new Move(idGame, joao, 1, 2, 0, 1));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(1).getBoard()[1][2], joao.marker());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//        Assertions.assertEquals(joao.marker(), game.getBoardMain().getBoardsSecondary().get(0).get(1).getWinner());
//        Assertions.assertEquals(joao.marker(), game.getBoardMain().getBoard()[0][1]);
//        Assertions.assertFalse(game.getBoardMain().isNoWins());
//
//
//        // obrigado a jogar em um jogo já finalizado 1x0, pode escolher qual jogar
//        game.makeMove(new Move(idGame, dan, 1, 0, 0, 0));
//        Assertions.assertNull(game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(0).getBoard()[1][0], dan.marker());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertTrue(game.isGameEnd());
//
//        //dan vence o jogo
//        Assertions.assertEquals(game.getPlayerWins(), dan);
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.WIN);


    }


    public void test2() {

//        game.makeMove(new Move(idGame, dan, 1, 1, 1, 1));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[1][1], dan.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, joao, 2, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(1).get(1).getBoard()[2][2], joao.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(game.getPlayerCurrent(), dan);
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//        game.makeMove(new Move(idGame, dan, 0, 2, 0, 0));
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(2).get(2).getBoard()[0][2], dan.marker());
//        Assertions.assertEquals(game.getBoardMain().getBoardsSecondary().get(0).get(2), game.getBoardMain().getBoardCurrent());
//        Assertions.assertEquals(joao, game.getPlayerCurrent());
//        Assertions.assertFalse(game.isGameEnd());
//        Assertions.assertEquals(game.getStatus(), GamePlayStatus.PLAYING);
//
//
//        Assertions.assertThrows(MoveNotAllowed.class, () ->
//                game.makeMove(new Move(idGame, dan, 1, 1, 0, 0)));
//

    }


}
