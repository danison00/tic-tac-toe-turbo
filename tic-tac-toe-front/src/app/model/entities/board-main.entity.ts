import { Move } from '../interfaces/Move.entity';
import { BoardSecondary } from './board-secondary.entity';

export class BoardMain {
  public constructor(
    private _board: string[][],
    private _boardsSecondary: BoardSecondary[][],
    private _boardCurrent: BoardSecondary | null
  ) {}

  public makeMove(move: Move): boolean {
    if (this.boardCurrent == null) {
      this._boardCurrent =
        this._boardsSecondary[move.lineBoardSecondary][
          move.columnBoardSecondary
        ];
    }
    if (this._boardCurrent != null) {
      if (
        this._boardCurrent.yourColumn != move.columnBoardSecondary ||
        this._boardCurrent.yourLine != move.lineBoardSecondary
      ) {
        return false;
      }

      return this._boardCurrent.makeMove(
        move.line,
        move.column,
        move.player.marker
      );
    }
    return false;
  }

  public getBoardSecondary(line: number, column: number) {
    return this._boardsSecondary[line][column];
  }
  public get boardsSecondary() {
    return this._boardsSecondary;
  }
  public get board() {
    return this._board;
  }
  public get boardCurrent() {
    return this._boardCurrent;
  }
}
