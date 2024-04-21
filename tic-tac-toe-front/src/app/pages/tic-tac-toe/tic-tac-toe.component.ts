import { Player } from './../../model/entities/Player.entity';
import { Game } from './../../model/entities/Game.entity';
import { Board } from './../../model/entities/Board';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, combineLatest, takeUntil } from 'rxjs';
import { SocketService } from 'src/app/service/socket.service';
import { Event } from 'src/app/model/interfaces/Event.interface';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { Move } from 'src/app/model/interfaces/Move.entity';
import { UtilService } from 'src/app/utils/utils.service';
import { GameInterface } from 'src/app/model/interfaces/Game.interface';
import { AppComponent } from 'src/app/app.component';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';

@Component({
  selector: 'app-tic-tac-toe',
  templateUrl: './tic-tac-toe.component.html',
  styleUrls: ['./tic-tac-toe.component.scss'],
})
export class TicTacToeComponent implements OnInit, OnDestroy {
  protected game!: Game;
  protected id!: string;
  protected modalWins = false;
  protected modalLost = false;
  protected modalNoWins = false;
  private unsubscribeTrigger = new Subject<void>();

  constructor(
    private socketService: SocketService,
    private cookieService: CookieServiceService,
    private util: UtilService,
    private app: AppComponent
  ) {}

  ngOnDestroy(): void {
    this.unsubscribeTrigger.next();
    this.unsubscribeTrigger.complete();
  }

  ngOnInit(): void {
    this.listenEvent();
    this.getGameById();
  }

  protected handleMove = (move: Move) => {
    this.game.makeMove(move.line, move.column, {
      id: move.player.id,
      name: move.player.name,
      iconPlayer: move.player.iconPlayer,
    });
  };
  public getGameById = () => {
    combineLatest([this.util.getIdByUrl(), this.util.getIdGameByUrl()])
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe(([id, idGame]) => {
        this.id = id;
        this.socketService.sendEvent({
          idReceiver: '',
          idSender: id,
          type: EventType.GET_GAME,
          payload: idGame as string,
        });
      });
  };

  protected listenEvent = () => {
    this.socketService
      .listenEvent()
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe({
        next: (event: Event) => {
          if (event.type == EventType.MOVE) {
            this.handleMove(event.payload as Move);
          }
          if (event.type == EventType.GET_GAME) {
            this.handleGetGame(event.payload as GameInterface);
          }
        },
      });
  };
  handleGetGame(game: GameInterface) {
    this.reset();
    let player1: Player =
      game.player1.id === this.id ? game.player1 : game.player2;
    let player2: Player =
      player1.id == game.player1.id ? game.player2 : game.player1;

    this.game = new Game(
      game.id,
      player1,
      player2,
      game.playerCurrent,
      game.playerInit,
      new Board(JSON.parse(game.board))
    );
    this.game
      .hasWinner()
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe((win: Player | string) => {

        console.log(win);
        

        if (typeof win === 'string') {
          this.modalNoWins = true;
          console.log(win);
          
          return;
        }

        const playerWins = win as Player;
        if (playerWins.id === this.cookieService.getValue('user_id')) {
          this.modalWins = true;
          console.log(this.modalWins);
          return;
        }

        if (playerWins.id !== this.cookieService.getValue('user_id')){

          this.modalLost = true;
          console.log(this.modalLost);
        }

      });
  }

  onMakeMove(line: number, column: number) {
    this.game.makeMove(line, column, this.game.player1).then((resp) => {
      if (resp) {
        this.sendMoveEvent(line, column, this.game.player1);
      }
    });
  }

  protected sendMoveEvent(line: number, column: number, player: Player) {
    const event: Event = {
      idSender: this.game.player1.id,
      idReceiver: this.game.player2.id,
      payload: {
        idGame: this.game.id,
        player: {
          id: player.id,
          name: player.name,
          iconPlayer: player.iconPlayer,
        },
        column: column,
        line: line,
      },
      type: EventType.MOVE,
    };
    this.socketService.sendEvent(event);
  }
  reset() {
    this.modalWins = false;
    this.modalLost = false;
    this.modalNoWins = false;
  }
  protected onChallenge() {
    this.socketService.sendEvent({
      idSender: this.id,
      idReceiver: this.game.player2.id,
      payload: {
        name: this.game.player2.name,
        id: this.game.player2.id,
      },
      type: EventType.NEW_GAME_REQUEST,
    });
  }

  // async onNewGameRequest() {
  //   const idReceiver =
  //     this.id === '84a491f0-5369-4c53-845f-6afc0aa23c49'
  //       ? 'df99effb-d267-499e-93b3-fc3858b7b5c7'
  //       : '84a491f0-5369-4c53-845f-6afc0aa23c49';
  //   const event: Event = {
  //     idSender: this.id,
  //     idReceiver: idReceiver,
  //     payload: '',
  //     type: EventType.NEW_GAME,
  //   };
  //   this.socketService.sendEvent(event);
  // }
}
