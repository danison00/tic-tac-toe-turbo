import { EventEmitter, Injectable } from '@angular/core';
import {
  Subject,
  Subscription,
  first,
  of,
  switchMap,
  take,
  takeUntil,
} from 'rxjs';
import { Board } from 'src/app/model/entities/Board';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { Game } from 'src/app/model/entities/Game.entity';
import { Player } from 'src/app/model/entities/Player.entity';
import { GameDto } from 'src/app/model/interfaces/Game.interface';
import { SocketService } from 'src/app/service/socket.service';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';
import { UtilService } from 'src/app/utils/utils.service';
import { GameEventSenderService } from './game-event-sender.service';
import { Move } from 'src/app/model/interfaces/Move.entity';

@Injectable({
  providedIn: 'root',
})
export class GameService {
  private _game!: Game;
  private _player!: Player;
  private _id!: string;
  $hasWinner = new EventEmitter<Player>();
  $getGameEvent = new EventEmitter<void>();
  private _$unsubscribeTrigger!: Subject<void>;
  private reset!: () => void;
  private hasWinner!: (player: Player) => void;

  constructor(
    private senderEvent: GameEventSenderService,
    private cookieService: CookieServiceService,
    private util: UtilService
  ) {}

  public init(
    $unsubscribeTrigger: Subject<void>,
    reset: () => void,
    hasWinner: (player: Player) => void
  ) {
    this.reset = reset;
    this.hasWinner = hasWinner;
    this._$unsubscribeTrigger = $unsubscribeTrigger;
    this._id = this.cookieService.getValue('user_id') ?? '';
  }

  public movePlayer1(line: number, column: number) {
    this._game.makeMove(line, column, this._player).then((resp) => {
      if (resp) {
        const move: Move = {
          line: line,
          column: column,
          idGame: this._game.id,
          player: this._player,
        };
        this.senderEvent.move(move, this.game.player2.id);
      }
    });
  }

  movePlayer2(move: Move) {
    this._game.makeMove(move.line, move.column, move.player);
  }

  public newChallenge() {
    this.senderEvent.challenge(
      this.id,
      this.game.player2.id,
      this._player.name
    );
  }


  public handleGetGame(game: GameDto) {
    this.reset();
    let player1: Player =
      game.player1.id === this.id ? game.player1 : game.player2;
    let player2: Player =
      player1.id == game.player1.id ? game.player2 : game.player1;

    this._player = player1.id == this.id ? player1 : player2;
    this._game = new Game(
      game.id,
      player1,
      player2,
      game.playerCurrent,
      game.playerInit,
      new Board(JSON.parse(game.board))
    );
    this._game
      .hasWinner()
      .pipe(takeUntil(this._$unsubscribeTrigger))
      .subscribe((player: Player) => {
        this.hasWinner(player);
      });
  }
  get game() {
    return this._game;
  }
  get id() {
    return this._id;
  }
}
