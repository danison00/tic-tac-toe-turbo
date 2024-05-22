package com.dandev.tictactoeturbo.socket.dtos;

import com.dandev.tictactoeturbo.socket.enums.GamePlayStatus;
import com.dandev.tictactoeturbo.socket.infra.exceptions.GamePlayEnd;
import com.dandev.tictactoeturbo.socket.infra.exceptions.MoveNotAllowed;
import lombok.Getter;
import lombok.ToString;

import static com.dandev.tictactoeturbo.socket.dtos.BoardMain.MoveBoardMain;

import java.util.*;

@Getter
@ToString
public class Game {

    private final UUID id;
    private final PlayerDto player1;
    private final PlayerDto player2;
    private PlayerDto playerCurrent;
    private PlayerDto playerWins;
    private GamePlayStatus status;
    private final BoardMain boardMain;
    private Move lastMove;
    private boolean gameEnd = false;


    public Game(UUID idPlayer1, String player1name, UUID idPlayer2, String player2name) {
        this.id = UUID.randomUUID();
        String markerPlayer1 = new Random().nextBoolean() ? "X" : "O";
        String markerPlayer2 = markerPlayer1.equals("X") ? "O" : "X";
        this.player1 = new PlayerDto(idPlayer1, player1name, markerPlayer1);
        this.player2 = new PlayerDto(idPlayer2, player2name, markerPlayer2);
        this.playerCurrent = new Random().nextBoolean() ? player1 : player2;
        this.status = GamePlayStatus.NO_TOUCH;
        this.boardMain = new BoardMain();
    }

    public void tooglePlayerCurrent() {
        this.playerCurrent = this.playerCurrent == player1 ? player2 : player1;
    }

    public void makeMove(Move move) {

        if (status.equals(GamePlayStatus.NO_WINS) || status.equals(GamePlayStatus.WIN)) throw new GamePlayEnd();

        if (!playerCurrent.id().equals(move.player().id())) throw new MoveNotAllowed();

        if (status.equals(GamePlayStatus.NO_TOUCH)) {
            status = GamePlayStatus.PLAYING;
        }

        boardMain.makeMove(new MoveBoardMain(move.lineBoardSecondary(), move.columnBoardSecondary(), move.line(), move.column(), move.player().marker()));
        tooglePlayerCurrent();
        lastMove = move;

        if (!boardMain.getWinner().isEmpty()) {
            gameEnd = true;
            String winner = boardMain.getWinner();
            playerWins = winner.equals(player1.marker()) ? player1 : winner.equals(player2.marker()) ? player2 : null;
            status = GamePlayStatus.WIN;
        }
        if (boardMain.isNoWins()) {
            gameEnd = true;
            String winner = "Q";
            playerWins = null;
            status = GamePlayStatus.WIN;
        }

    }

//    private boolean _isNoPlayerWin() {
//        var b = new ArrayList<ArrayList<String>>();
//        b.add(new ArrayList<>(List.of(board[0])));
//        b.add(new ArrayList<>(List.of(board[1])));
//        b.add(new ArrayList<>(List.of(board[2])));
//
//        return !b.get(0).contains("") && !b.get(1).contains("") && !b.get(2).contains("");
//    }
//
//    private Optional<PlayerDto> checkWinner() {
//
//        for (int i = 0; i < 3; i++) {
//            if (!board[i][0].isEmpty() &&
//                    board[i][0].equals(board[i][1]) &&
//                    board[i][1].equals(board[i][2])) {
//                if (board[i][0].equals(player1.iconPlayer())) return Optional.of(player1);
//                return Optional.of(player2);
//            }
//        }
//
//        for (int i = 0; i < 3; i++) {
//            if (!board[0][i].isEmpty() &&
//                    board[0][i].equals(board[1][i]) &&
//                    board[1][i].equals(board[2][i])
//            ) {
//                if (board[0][i].equals(player1.iconPlayer())) return Optional.of(player1);
//                return Optional.of(player2);
//            }
//        }
//
//        if (!board[0][0].isEmpty() &&
//                board[0][0].equals(board[1][1]) &&
//                board[1][1].equals(board[2][2])
//        ) {
//
//            if (board[0][0].equals(player1.iconPlayer())) return Optional.of(player1);
//            return Optional.of(player2);
//        }
//
//        if (!board[0][2].isEmpty() &&
//                board[0][2].equals(board[1][1]) &&
//                board[1][1].equals(board[2][0])
//        ) {
//
//            if (board[0][2].equals(player1.iconPlayer())) return Optional.of(player1);
//            return Optional.of(player2);
//        }
//
//        return Optional.empty();
//    }


}
