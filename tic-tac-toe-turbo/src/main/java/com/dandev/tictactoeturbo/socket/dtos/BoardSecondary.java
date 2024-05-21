package com.dandev.tictactoeturbo.socket.dtos;

import com.dandev.tictactoeturbo.socket.infra.exceptions.MoveNotAllowed;
import lombok.Getter;
import lombok.ToString;

import static com.dandev.tictactoeturbo.socket.dtos.BoardSecondary.MoveBoardSecondary;

@Getter
@ToString(callSuper = true)
public class BoardSecondary extends ABoard<MoveBoardSecondary> {
    private int yourLine;
    private int yourColumn;

    public BoardSecondary(int yourLine, int yourColumn) {
        this.yourLine = yourLine;
        this.yourColumn = yourColumn;
    }

    @Override
    public void makeMove(MoveBoardSecondary move) {
        if(!board[move.line()][move.column()].isEmpty()) throw new MoveNotAllowed();
        board[move.line()][move.column()] = move.marker();

    }



    @Override
    public String getWinner() {
        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() &&
                    board[i][0].equals(board[i][1]) &&
                    board[i][1].equals(board[i][2])) {

                return board[i][0];
            }
        }

        for (int i = 0; i < 3; i++) {
            if (!board[0][i].isEmpty() &&
                    board[0][i].equals(board[1][i]) &&
                    board[1][i].equals(board[2][i])
            ) {
                return board[0][i];
            }
        }

        if (!board[0][0].isEmpty() &&
                board[0][0].equals(board[1][1]) &&
                board[1][1].equals(board[2][2])
        ) {

            return board[0][0];
        }

        if (!board[0][2].isEmpty() &&
                board[0][2].equals(board[1][1]) &&
                board[1][1].equals(board[2][0])
        ) {

            return board[0][2];
        }

        return "";
    }

    public record MoveBoardSecondary(int line, int column, String marker) {

        public static MoveBoardSecondary of(int line, int column, String marker) {
            return new MoveBoardSecondary(line, column, marker);
        }

    }
}
