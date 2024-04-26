package com.dandev.tictactoeturbo.webSocketConnection.service.impl;

import com.dandev.tictactoeturbo.infra.exceptions.GameNotFound;
import com.dandev.tictactoeturbo.webSocketConnection.dtos.Event;
import com.dandev.tictactoeturbo.webSocketConnection.dtos.EventType;
import com.dandev.tictactoeturbo.webSocketConnection.dtos.Move;
import com.dandev.tictactoeturbo.model.entity.Game;
import com.dandev.tictactoeturbo.model.entity.Player;
import com.dandev.tictactoeturbo.webSocketConnection.infra.exceptions.UserNotOnline;
import com.dandev.tictactoeturbo.model.entity.User;
import com.dandev.tictactoeturbo.webSocketConnection.service.GameService;
import com.dandev.tictactoeturbo.webSocketConnection.service.UserOnlineService;
import com.dandev.tictactoeturbo.util.Mapper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameServiceImpl implements GameService {

    private final UserOnlineService userOnlineService;
    private final Map<UUID, Game> games = new ConcurrentHashMap<>();
    private final Mapper mapper;

    public GameServiceImpl(UserOnlineService userOnlineService, Mapper mapper) {
        this.userOnlineService = userOnlineService;
        this.mapper = mapper;
    }

    @Override
    public void newGameRequest(UUID id, Event event) {
        this.userOnlineService.sendEvent(id, event);
    }

    @Override
    public Game getGame(UUID gameId) {
        if (!this.games.containsKey(gameId)){

            System.out.println("Game não encontrado(GameController:42)");
            throw new GameNotFound();
        }
        System.out.println("Game encontrado(GameController:45)");
        return games.get(gameId);

    }

    @Override
    public void newGame(Event messageSender) {
        UUID idPlayer1 = messageSender.idSender();
        UUID idPlayer2 = messageSender.idReceiver();

        try {


            User userPlayer1 = userOnlineService.get(idPlayer1);
            User userPlayer2 = userOnlineService.get(idPlayer2);


            String iconPlayer = new Random().nextBoolean() ? "X" : "O";
            String iconPlayerAdversary = iconPlayer.equals("X") ? "O" : "X";


            com.dandev.tictactoeturbo.model.entity.Player player1 = new Player(idPlayer1, userPlayer1.getName(), iconPlayer);
            Player player2 = new Player(idPlayer2, userPlayer2.getName(), iconPlayerAdversary);
            Player playerCurrent = new Random().nextBoolean() ? player1 : player2;
            String[][] b = {{"", "", ""}, {"", "", ""}, {"", "", ""}};
            String board = mapper.serialize(b);

            UUID idGame = UUID.randomUUID();
            Game game = new Game(
                    idGame,
                    player1,
                    player2,
                    playerCurrent,
                    board
            );

            this.games.put(idGame, game);


            userOnlineService.sendEvent(idPlayer1, new Event(
                    null,
                    idPlayer1,
                    idGame.toString(),
                    EventType.NEW_GAME_ACCEPT));

            userOnlineService.sendEvent(idPlayer2, new Event(
                    null,
                    idPlayer2,
                    idGame.toString(),
                    EventType.NEW_GAME_ACCEPT));
        } catch (UserNotOnline e) {
            throw new UserNotOnline(e.getMessage(), idPlayer1);
        }

    }

    public void getGameById(Event event) {

    }

    @Override
    public void makeMove(UUID id, Event event) {
        Move move = (Move) event.payload();
        System.out.println("move->  " + move.toString());

        Game game = this.games.get(move.idGame());
        if (game == null) throw new RuntimeException("Jogo não encontrado");
        if (!game.getPlayerCurrent().getId().equals(move.player().getId())) {
            throw new RuntimeException("Não é a sua vez porraaa");
        }

        String[][] board = mapper.deserialize(game.getBoard(), String[][].class);
        if (!board[move.line()][move.column()].isEmpty())
            System.out.println("casa já marcada(GameServiceImpl.java:110)");
        ;
        var n =
                board[move.line()][move.column()] = move.player().getIconPlayer();
        game.setBoard(mapper.serialize(board));
        game.setPlayerCurrent(game.getPlayerCurrent().equals(game.getPlayer1()) ? game.getPlayer2() : game.getPlayer1());
        System.out.println(game.getBoard());
        move = move.setBoard(board);
        event = event.setPaylooad(move);
        System.out.println(move.toString());


        userOnlineService.sendEvent(id, event);
    }


}
