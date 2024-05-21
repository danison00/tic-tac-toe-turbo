package com.dandev.tictactoeturbo.socket.dtos;

import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter()
@ToString
public abstract class ABoard<MoveType> {

    String[][] board = {{"", "", ""}, {"", "", ""}, {"", "", ""}};

    abstract void makeMove(MoveType move);

    public boolean isNoWins() {
        var b = new ArrayList<ArrayList<String>>();
        b.add(new ArrayList<>(List.of(board[0])));
        b.add(new ArrayList<>(List.of(board[1])));
        b.add(new ArrayList<>(List.of(board[2])));

        return !b.get(0).contains("") && !b.get(1).contains("") && !b.get(2).contains("");
    }



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

    public String[][] getBoard(){
        return board;
    }

    ;

}
