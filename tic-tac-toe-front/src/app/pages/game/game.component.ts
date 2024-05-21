import { GamePlayerStatus } from './../../model/enums/game-player-status.enum';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Component, HostListener, OnDestroy, OnInit } from '@angular/core';
import { EMPTY, Subject, switchMap, takeUntil } from 'rxjs';
import { GameDto } from 'src/app/model/interfaces/game-dto.interface';
import { TicTacToeService } from './game.service';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';


@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.scss'],
})
export class GameComponent implements OnInit, OnDestroy {
  protected modalWins = false;
  protected modalLost = false;
  protected modalNoWins = false;
  private $unsubscribeTrigger = new Subject<void>();

  constructor(
    private activatedRoute: ActivatedRoute,
    private service: TicTacToeService,
    private cookieService: CookieServiceService
  ) {}

  ngOnDestroy(): void {
    this.$unsubscribeTrigger.next();
    this.$unsubscribeTrigger.complete();
  }
  ngOnInit(): void {
    this._getGameById();
  }
  

  protected get userId() {
     return this.service.player.id;
  }


  reset = () => {
    this.modalWins = false;
    this.modalLost = false;
    this.modalNoWins = false;
  };
  protected onNewChallenge() {
    this.service.newChallenge();
  }
  private _getGameById() {
    return this.activatedRoute.queryParamMap
      .pipe(
        switchMap((params: ParamMap) => {
          const gameId = params.get('gameId');
          if (gameId) return this.service.getGame(gameId);
          return EMPTY;
        })
      )
      .pipe(takeUntil(this.$unsubscribeTrigger))
      .subscribe((game) => {
          this.checkStatus(game);
      });
  }

  private checkStatus(game: GameDto) {

    switch (game.status) {

      case GamePlayerStatus.WIN: {
        const userId = this.cookieService.getValue("user_id");  
        if(game.playerWins.id == userId) this.modalWins = true;
        else this.modalLost = true;
        break;
      }
      case GamePlayerStatus.NO_WINS: {
        this.modalNoWins = true;
        break;
      }
      case GamePlayerStatus.NO_TOUCH :{
        this.reset()
      }
    }
  }
  protected get game(){
    return this.service.game;
  }
}
