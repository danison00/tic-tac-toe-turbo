package com.dandev.tictactoeturbo.socket.dtos;

import static com.dandev.tictactoeturbo.socket.dtos.BoardMain.MoveBoardMain;

import java.util.List;

import static com.dandev.tictactoeturbo.socket.dtos.BoardSecondary.MoveBoardSecondary;

public class BoardMain extends ABoard<MoveBoardMain> {

    List<List<ABoard<MoveBoardSecondary>>> boardsSecondary;

    public BoardMain() {
        boardsSecondary = List.of(
                List.of(new BoardSecondary(), new BoardSecondary(), new BoardSecondary()),
                List.of(new BoardSecondary(), new BoardSecondary(), new BoardSecondary()),
                List.of(new BoardSecondary(), new BoardSecondary(), new BoardSecondary())
        );
    }

    @Override
    public void makeMove(MoveBoardMain move) {
        ABoard<MoveBoardSecondary> boardSecondary = boardsSecondary.get(move.lineBoardSecondary).get(move.columnBoardSecondary());

        boardSecondary.makeMove(MoveBoardSecondary
                .of(move.line, move.column, move.marker));

        _checkStates(boardSecondary, move.lineBoardSecondary, move.columnBoardSecondary);
    }

    private void _checkStates(ABoard<?> boardSecondary, int line, int column) {
        if (boardSecondary.isFinish()) {
            super.board[line][column] = boardSecondary.getWinner();
        }
    }



    public record MoveBoardMain(int lineBoardSecondary, int columnBoardSecondary, int line, int column, String marker) {
    }
}
