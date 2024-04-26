import { ActivatedRoute, ParamMap } from '@angular/router';
import { Player } from './../../model/entities/Player.entity';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { EMPTY, Subject, first, of, switchMap, takeUntil } from 'rxjs';
import { GameService } from './game.service';
import { GameEventListenerService } from './game-event-listener.service';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment.dev';
import { GameDto } from 'src/app/model/interfaces/Game.interface';

@Component({
  selector: 'app-tic-tac-toe',
  templateUrl: './tic-tac-toe.component.html',
  styleUrls: ['./tic-tac-toe.component.scss'],
})
export class TicTacToeComponent implements OnInit, OnDestroy {
  protected modalWins = false;
  protected modalLost = false;
  protected modalNoWins = false;
  private $unsubscribeTrigger = new Subject<void>();

  constructor(
    private gameService: GameService,
    private eventListener: GameEventListenerService,
    private activatedRoute: ActivatedRoute,
    private http: HttpClient
  ) {}

  ngOnDestroy(): void {
    this.$unsubscribeTrigger.next();
    this.$unsubscribeTrigger.complete();
  }
  ngOnInit(): void {
    this.getGameById().subscribe({
      next: (game: GameDto)=>{
        this.gameService.handleGetGame(game);
      }
    })

    this.gameService.init(this.$unsubscribeTrigger, this.reset, this.hasWinner);
    this.eventListener.init(this.$unsubscribeTrigger);
  }
  public hasWinner = (win: Player) => {
    if (typeof win === 'string') {
      this.modalNoWins = true;
      return;
    }
    const playerWins = win as Player;
    const userId = this.gameService.id;
    if (playerWins.id === userId) {
      this.modalWins = true;
      return;
    }

    if (playerWins.id !== userId) {
      this.modalLost = true;
    }
  };

  protected get game() {
    return this.gameService.game;
  }
  protected get userId() {
    return this.gameService.id;
  }

  onMakeMove(line: number, column: number) {
    this.gameService.movePlayer1(line, column);
  }

  reset = () => {
    this.modalWins = false;
    this.modalLost = false;
    this.modalNoWins = false;
  };
  protected onNewChallenge() {
    this.gameService.newChallenge();
  }
  private getGameById() {
    return this.activatedRoute.queryParamMap
      .pipe(
        switchMap((params: ParamMap) => {
          const gameId = params.get('gameId');
          if (gameId) {
            return of(gameId);
          }
          return EMPTY;
        })
      )
      .pipe(
        switchMap((gameId: string) => {
          return this.http.get<GameDto>(environment.baseUrl + '/game', {
            withCredentials: true,
            params: { gameId: gameId },
          });
        })
      );
  }
}
