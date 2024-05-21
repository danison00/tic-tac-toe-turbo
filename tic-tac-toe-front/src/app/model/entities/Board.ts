import { Subject } from 'rxjs';

export class Board {
  private board: string[][] = [
    ['', '', ''],
    ['', '', ''],
    ['', '', ''],
  ];

  private hasWinner = new Subject<string>();
  constructor(board: string[][]) {
    this.board = board;
  }

  public makeMove(line: number, column: number, iconPlayer: string) {
    this.board[line][column] = iconPlayer;
    this.checkWinner(iconPlayer);
  }

  public isMarked(line: number, column: number): boolean {
    return this.board[line][column] !== '';
  }

  private checkWinner(iconPlayed: string) {
    for (let i = 0; i < 3; i++) {
      if (
        this.board[i][0] !== '' &&
        this.board[i][0] === this.board[i][1] &&
        this.board[i][1] === this.board[i][2]
      ) {
        this.hasWinner.next(iconPlayed);
        return;
      }
    }

    for (let i = 0; i < 3; i++) {
      if (
        this.board[0][i] !== '' &&
        this.board[0][i] === this.board[1][i] &&
        this.board[1][i] === this.board[2][i]
      ) {
        this.hasWinner.next(iconPlayed);
        return;
      }
    }

    if (
      this.board[0][0] !== '' &&
      this.board[0][0] === this.board[1][1] &&
      this.board[1][1] === this.board[2][2]
    ) {
      this.hasWinner.next(iconPlayed);

      return;
    }

    if (
      this.board[0][2] !== '' &&
      this.board[0][2] === this.board[1][1] &&
      this.board[1][1] === this.board[2][0]
    ) {
      this.hasWinner.next(iconPlayed);
      return;
    }

     let wins = false;

    
     if(!this.board[0].includes('') && !this.board[1].includes('') && !this.board[2].includes(''))
      this.hasWinner.next('');

  }
  get winner() {
    return this.hasWinner.asObservable();
  }
  getCell(line: number, column: number) {
    return this.board[line][column];
  }
  public getBoardAsString() {
    return JSON.stringify(this.board);
  }
}
