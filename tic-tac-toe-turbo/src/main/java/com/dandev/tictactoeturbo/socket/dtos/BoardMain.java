package com.dandev.tictactoeturbo.socket.dtos;

import lombok.Getter;
import lombok.ToString;

import static com.dandev.tictactoeturbo.socket.dtos.BoardMain.MoveBoardMain;

import java.util.Arrays;
import java.util.List;

import static com.dandev.tictactoeturbo.socket.dtos.BoardSecondary.MoveBoardSecondary;

@Getter
@ToString(callSuper = true)
public class BoardMain extends ABoard<MoveBoardMain> {

    private List<List<BoardSecondary>> boardsSecondary;
    private BoardSecondary boardCurrent;

    public BoardMain() {
        boardsSecondary = List.of(
                List.of(new BoardSecondary(0, 0), new BoardSecondary(0, 1), new BoardSecondary(0, 2)),
                List.of(new BoardSecondary(1, 0), new BoardSecondary(1, 1), new BoardSecondary(1, 2)),
                List.of(new BoardSecondary(2, 0), new BoardSecondary(2, 1), new BoardSecondary(2, 2))
        );
    }

    @Override
    public void makeMove(MoveBoardMain move) {
        if (boardCurrent == null) {
            boardCurrent = boardsSecondary.get(move.lineBoardSecondary()).get(move.columnBoardSecondary());
        }

        boardCurrent.makeMove(MoveBoardSecondary
                .of(move.line, move.column, move.marker));

        _checkStates(boardCurrent, move.line, move.column);

        if (!boardsSecondary.get(move.line()).get(move.column()).getWinner().isEmpty()) {
            boardCurrent = null;
        } else {
            boardCurrent = boardsSecondary.get(move.line()).get(move.column());
        }
    }

    @Override
    public String getWinner() {
        String winner = super.getWinner();
        if (!winner.isEmpty()) return winner;

        for (String[] line : board) {
            if (List.of(line).contains("Q") &&
                    !List.of(line).contains("") &&
                    (line[0].equals(line[1]) || line[1].equals(line[2]) || line[0].equals(line[2]))
            ) {
                return !line[0].equals("Q") ? line[0] : !line[1].equals("Q") ? line[1] : !line[2].equals("Q") ? line[2] : "";
            }
        }

        for (int i = 0; i < 3; i++) {

            var column = List.of(board[0][i], board[1][i], board[2][i]);
            if (column.contains("Q") && !column.contains("") &&
                    (column.get(0).equals(column.get(1)) || column.get(1).equals(column.get(2)) || column.get(0).equals(column.get(2)))) {

                return !column.get(0).equals("Q") ? column.get(0) : column.get(1).equals("Q") ? column.get(1) : !column.get(2).equals("Q") ? column.get(2) : "";

            }
        }
        return "";
    }

    private void _checkStates(BoardSecondary board, int line, int column) {
        if (!board.getWinner().isEmpty()) {
            super.board[board.getYourLine()][board.getYourColumn()] = board.getWinner();
        }

        if (board.isNoWins()) {
            super.board[board.getYourLine()][board.getYourColumn()] = "Q";
        }
    }


    public record MoveBoardMain(int lineBoardSecondary, int columnBoardSecondary, int line, int column, String marker) {
    }


}
