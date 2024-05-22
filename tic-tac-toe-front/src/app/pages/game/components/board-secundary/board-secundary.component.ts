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
  boardEmphasis = false;

  constructor(
    private elementRef: ElementRef,
    protected gameService: TicTacToeService
  ) {}
  ngOnInit(): void {
    this.gameService.gameChangesEvent
      .pipe(takeUntil(this.$trigger))
      .subscribe(() => {
        this.checkBoardCurrent();
        this.expandBoard = false;
      });
    this.checkBoardCurrent();
    
  }

  private checkBoardCurrent() {
    console.log();
    

    if(this.gameService.game.boardCurrent == null || this.boardEmphasis == undefined){
      this.boardEmphasis = true;
      return;
    }


    if (
      this.gameService.game.boardCurrent?.yourLine ==
        this.boardSecondary.yourLine &&
      this.gameService.game.boardCurrent.yourColumn ==
        this.boardSecondary.yourColumn
    ) {
    
        this.boardEmphasis = true;
      
    } else {
      this.boardEmphasis = false;
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

    if (gamePlayer.boardCurrent == null) return;

    if (
      gamePlayer.boardCurrent.yourLine == this.boardSecondary.yourLine &&
      gamePlayer.boardCurrent.yourColumn == this.boardSecondary.yourColumn
    ) {
      this.boardEmphasis = true;
    } else {
      this.boardEmphasis = false;
    }
  }

  @HostListener('document:click', ['$event'])
  _contractBoardEventClickOutsideListener(event: MouseEvent) {
    if (!this.elementRef.nativeElement.contains(event.target)) {
      this.expandBoard = false;
    }
  }
  isLastMove(line: number, column: number): any {
    const lastMove = this.gameService.game.lastMove;
    if(!lastMove) return false;
    return (
      this.boardSecondary.yourColumn == lastMove.columnBoardSecondary &&
      this.boardSecondary.yourLine == lastMove.lineBoardSecondary &&
      lastMove.line == line &&
      lastMove.column == column
    );
  }
}
