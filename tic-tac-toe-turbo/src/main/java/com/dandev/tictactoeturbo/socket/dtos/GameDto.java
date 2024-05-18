package com.dandev.tictactoeturbo.socket.dtos;

import com.dandev.tictactoeturbo.socket.enums.GamePlayStatus;
import com.dandev.tictactoeturbo.socket.service.implementations.GameServiceImpl;

import java.util.*;


public record GameDto(
        UUID id,
        PlayerDto player1,
        PlayerDto player2,
        PlayerDto playerCurrent,
        PlayerDto playerWins,
        GamePlayStatus status,
        String[][] board
) {


    public static GameDto newGame(UUID idPlayer1, String player1name, UUID idPlayer2, String player2name){
        String markerPlayer1 = new Random().nextBoolean() ? "X" : "O";
        String markerPlayer2 = markerPlayer1.equals("X") ? "O" : "X";

        PlayerDto player1 = new PlayerDto(idPlayer1, player1name, markerPlayer1);
        PlayerDto player2 = new PlayerDto(idPlayer2, player2name, markerPlayer2);
        PlayerDto playerCurrent = new Random().nextBoolean() ? player1 : player2;

        String[][] board = {{"", "", ""}, {"", "", ""}, {"", "", ""}};
        UUID gameId = UUID.randomUUID();
       return new GameDto(gameId, player1, player2, playerCurrent, null, GamePlayStatus.NO_TOUCH, board);

    }

    public GameDto tooglePlayerCurrent() {
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent.equals(player1) ? player2 : player1,
                playerWins,
                status,
                board
        );
    }

    public GameDto setBoard(String[][] board) {
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent,
                playerWins,
                status,
                board
        );
    }
    public GameDto setStatus(GamePlayStatus status){
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent,
                playerWins,
                status,
                board
        );
    }
    public GameDto setPlayerWins(PlayerDto playerWins){
        return new GameDto(
                id,
                player1,
                player2,
                playerCurrent,
                playerWins,
                status,
                board
        );
    }

    public GameDto makeMove(Move move){

        GameDto gameDto = null;



        if ((status().equals(GamePlayStatus.PLAYING) || status().equals(GamePlayStatus.NO_TOUCH)) &&   playerCurrent().id().equals(move.player().id())) {

            gameDto = setStatus(GamePlayStatus.PLAYING);

            gameDto.board[move.line()][move.column()] = move.player().iconPlayer();

            gameDto = setStatus(GamePlayStatus.PLAYING).tooglePlayerCurrent();
            if (_isNoPlayerWin()) {
                return gameDto.setStatus(GamePlayStatus.NO_WINS);
            }
            Optional<PlayerDto> playerDtoOpt = checkWinner();
            if (playerDtoOpt.isPresent()) {
                return gameDto.setPlayerWins(playerDtoOpt.get()).setStatus(GamePlayStatus.WIN);

            }

        }
        return gameDto;
    }

    private boolean _isNoPlayerWin() {
        var b = new ArrayList<ArrayList<String>>();
        b.add(new ArrayList<>(List.of(board[0])));
        b.add(new ArrayList<>(List.of(board[1])));
        b.add(new ArrayList<>(List.of(board[2])));

        return !b.get(0).contains("") && !b.get(1).contains("") && !b.get(2).contains("");
    }

    private Optional<PlayerDto> checkWinner() {

        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() &&
                    board[i][0].equals(board[i][1]) &&
                    board[i][1].equals(board[i][2])) {
                if (board[i][0].equals(player1.iconPlayer())) return Optional.of(player1);
                return Optional.of(player2);
            }
        }

        for (int i = 0; i < 3; i++) {
            if (!board[0][i].isEmpty() &&
                    board[0][i].equals(board[1][i]) &&
                    board[1][i].equals(board[2][i])
            ) {
                if (board[0][i].equals(player1.iconPlayer())) return Optional.of(player1);
                return Optional.of(player2);
            }
        }

        if (!board[0][0].isEmpty() &&
                board[0][0].equals(board[1][1]) &&
                board[1][1].equals(board[2][2])
        ) {

            if (board[0][0].equals(player1.iconPlayer())) return Optional.of(player1);
            return Optional.of(player2);
        }

        if (!board[0][2].isEmpty() &&
                board[0][2].equals(board[1][1]) &&
                board[1][1].equals(board[2][0])
        ) {

            if (board[0][2].equals(player1.iconPlayer())) return Optional.of(player1);
            return Optional.of(player2);
        }

        return Optional.empty();
    }


}
