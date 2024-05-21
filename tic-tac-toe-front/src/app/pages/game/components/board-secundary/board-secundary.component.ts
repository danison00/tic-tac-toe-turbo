import {
  Component,
  ElementRef,
  HostListener,
  Input,
  OnDestroy,
  OnInit,
} from '@angular/core';
import { Board } from 'src/app/model/entities/Board';
import { BoardSecondary } from 'src/app/model/entities/board-secondary.entity';
import { GamePlayerStatus } from 'src/app/model/enums/game-player-status.enum';
import { TicTacToeService } from '../../game.service';
import { Subject, takeUntil } from 'rxjs';
import { GamePlayer } from 'src/app/model/entities/game-player.entity';

@Component({
  selector: 'board-secundary',
  templateUrl: './board-secundary.component.html',
  styleUrls: ['./board-secundary.component.scss'],
})
export class BoardSecundaryComponent implements OnDestroy, OnInit {
  @Input() boardSecondary!: BoardSecondary;
  private $trigger = new Subject<void>();

  expandBoard = false;
  isBoardCurrent = false;

  constructor(
    private elementRef: ElementRef,
    private gameService: TicTacToeService
  ) {}
  ngOnInit(): void {
    this.gameService.gameChangesEvent
      .pipe(takeUntil(this.$trigger))
      .subscribe((gamePlayer) => {
        this.checkBoardCurrent();
      });
    this.checkBoardCurrent();
  }

  private checkBoardCurrent() {
    if (
      this.gameService.game.boardCurrent?.yourLine ==
        this.boardSecondary.yourLine &&
      this.gameService.game.boardCurrent.yourColumn ==
        this.boardSecondary.yourColumn
    ) {
      if (
        this.gameService.game.playerCurrent.id ==
        this.gameService.game.player1.id
      ) {
        this.isBoardCurrent = true;
        this.expandBoard = true;
      }
    }else{
      this.isBoardCurrent = false;
      this.expandBoard = false
    }
  }

  ngOnDestroy(): void {
    this.$trigger.next();
    this.$trigger.complete();
  }

  onMakeMove(line: number, column: number) {
    if (!this.expandBoard) return;

    this.gameService.makeMove(
      line,
      column,
      this.boardSecondary.yourLine,
      this.boardSecondary.yourColumn
    );
  }
  handleChangeGame(gamePlayer: GamePlayer) {
    console.log('is board current', this.isBoardCurrent);

    if (gamePlayer.boardCurrent == null) return;

    if (
      gamePlayer.boardCurrent.yourLine == this.boardSecondary.yourLine &&
      gamePlayer.boardCurrent.yourColumn == this.boardSecondary.yourColumn
    ) {
      this.isBoardCurrent = true;
    } else {
      this.isBoardCurrent = false;
    }
  }

  @HostListener('document:click', ['$event'])
  _contractBoardEventClickOutsideListener(event: MouseEvent) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.expandBoard = false;
    }
  }
}
