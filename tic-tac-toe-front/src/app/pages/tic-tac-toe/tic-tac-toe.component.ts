import { Player } from './../../model/entities/Player.entity';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { GameService } from './game.service';
import { GameEventListenerService } from './game-event-listener.service';

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
    private eventListener: GameEventListenerService
  ) {}

  ngOnDestroy(): void {
    this.$unsubscribeTrigger.next();
    this.$unsubscribeTrigger.complete();
  }
  ngOnInit(): void {
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
}
