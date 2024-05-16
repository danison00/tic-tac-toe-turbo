package com.dandev.tictactoeturbo.socket.service.implementations;

import com.dandev.tictactoeturbo.service.UserService;
import com.dandev.tictactoeturbo.socket.dtos.Move;
import com.dandev.tictactoeturbo.socket.enums.GamePlayStatus;
import com.dandev.tictactoeturbo.util.JsonConverter;
import com.dandev.tictactoeturbo.socket.dtos.GameDto;
import com.dandev.tictactoeturbo.socket.dtos.PlayerDto;
import com.dandev.tictactoeturbo.socket.service.GamePlayerSessionManager;
import com.dandev.tictactoeturbo.socket.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GamePlayerSessionManager gameSessionManager;
    @Autowired
    private UserService userService;
    @Autowired
    private JsonConverter jsonConverter;
    private final Map<KeyGame, GameDto> gamesByPlayers = new ConcurrentHashMap<>();
    private final Map<UUID, GameDto> gamesById = new ConcurrentHashMap<>();

    @Override
    public GameDto newGame(UUID player1Id, UUID player2Id) {

        GameDto game = gamesByPlayers.get(new KeyGame(player1Id, player2Id));
        if (game == null) {
            game = gamesByPlayers.get(new KeyGame(player2Id, player1Id));
        }
        if (game == null) {
            String iconPlayer1 = "";
            var player1Opt = userService.findById(player1Id);
            if (player1Opt.isEmpty()) return null;
            iconPlayer1 = new Random().nextBoolean() ? "X" : "O";
            PlayerDto player1 = new PlayerDto(player1Id, player1Opt.get().getName(), iconPlayer1, new ConcurrentHashMap<>());

            var player2opt = userService.findById(player2Id);
            if (player2opt.isEmpty()) return null;
            String iconPlayer2 = iconPlayer1.equals("X") ? "O" : "X";
            PlayerDto player2 = new PlayerDto(player2Id, player2opt.get().getName(), iconPlayer2, new ConcurrentHashMap<>());
            var playerCurrent = new Random().nextBoolean() ? player1 : player2;

            String[][] board = {{"", "", ""}, {"", "", ""}, {"", "", ""}};
            UUID gameId = UUID.randomUUID();
            game = new GameDto(gameId, player1, player2, playerCurrent, null, GamePlayStatus.NO_TOUCH, jsonConverter.serialize(board));
            gamesByPlayers.put(new KeyGame(player1Id, player2Id), game);
            gamesById.put(game.id(), game);
        }
        return game;
    }


    @Override
    public Optional<GameDto> getByPlayers(UUID player1Id, UUID player2Id) {
        GameDto gameDto = gamesByPlayers.get(new KeyGame(player1Id, player2Id));
        if (gameDto != null)
            return Optional.of(gameDto);

        gameDto = gamesByPlayers.get(new KeyGame(player2Id, player1Id));
        if (gameDto != null)
            return Optional.of(gameDto);

        return Optional.empty();
    }


    @Override
    public Optional<GameDto> getById(UUID gameId) {
        GameDto gameDto = gamesById.get(gameId);
        if (gameDto == null) return Optional.empty();
        return Optional.of(gameDto);
    }

    @Override
    public GameDto makeMove(Move move) {


        GameDto gameDto = gamesById.get(move.idGame());



        if (gameDto.status().equals(GamePlayStatus.PLAYING) || gameDto.status().equals(GamePlayStatus.NO_TOUCH)) {
            String[][] board = jsonConverter.deserialize(gameDto.board(), String[][].class);
            if (gameDto.playerCurrent().id().equals(move.player().id())) {
                board[move.line()][move.column()] = move.player().iconPlayer();
                String boardStr = jsonConverter.serialize(board);
                gameDto = gameDto.setBoard(boardStr).setStatus(GamePlayStatus.PLAYING).tooglePlayerCurrent();
                if (isNoPlayerWin(board)) {
                    gameDto = gameDto.setStatus(GamePlayStatus.NO_WINS);
                    gamesById.remove(gameDto.id());
                    gamesByPlayers.remove(KeyGame.of(gameDto.player1().id(), gameDto.player2().id()));
                    gamesByPlayers.remove(KeyGame.of(gameDto.player2().id(), gameDto.player1().id()));
                    return gameDto;
                }
                Optional<PlayerDto> playerDtoOpt = checkWinner(board, gameDto.player1(), gameDto.player2());
                if (playerDtoOpt.isPresent()) {
                    gameDto = gameDto.setPlayerWins(playerDtoOpt.get()).setStatus(GamePlayStatus.WIN);
                    gamesById.remove(gameDto.id());
                    gamesByPlayers.remove(KeyGame.of(gameDto.player1().id(), gameDto.player2().id()));
                    gamesByPlayers.remove(KeyGame.of(gameDto.player2().id(), gameDto.player1().id()));
                    return gameDto;
                }


            }

            gamesById.put(gameDto.id(), gameDto);
            gamesByPlayers.put(new KeyGame(gameDto.player1().id(), gameDto.player2().id()), gameDto);
        }
        return gameDto;
    }

    private Optional<PlayerDto> checkWinner(String[][] board, PlayerDto player1, PlayerDto player2) {

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

    private boolean isNoPlayerWin(String[][] board) {
        var b = new ArrayList<ArrayList<String>>();
        b.add(new ArrayList<>(List.of(board[0])));
        b.add(new ArrayList<>(List.of(board[1])));
        b.add(new ArrayList<>(List.of(board[2])));

        return !b.get(0).contains("") && !b.get(1).contains("") && !b.get(2).contains("");
    }

    record KeyGame(
            UUID player1,
            UUID player2
    ) {
        public static KeyGame of(UUID key1, UUID key2){
            return new KeyGame(key1, key2);
        }
    }
}

