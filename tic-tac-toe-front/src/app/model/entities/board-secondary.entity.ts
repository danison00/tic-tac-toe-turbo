export class BoardSecondary {


  public constructor(private _yourLine: number, private _yourColumn: number, private _board: string[][]) {}

  public makeMove(line: number, column: number, iconPlayer: string): boolean {
    if (this.board[line][column] != '') return false;

    this.board[line][column] = iconPlayer;
    return true;
  }
  public getWinner(): string {
    for (let i = 0; i < 3; i++) {
      if (
        this.board[i][0] !== '' &&
        this.board[i][0] === this.board[i][1] &&
        this.board[i][1] === this.board[i][2]
      ) {
        return this.board[i][0];
      }
    }

    for (let i = 0; i < 3; i++) {
      if (
        this.board[0][i] !== '' &&
        this.board[0][i] === this.board[1][i] &&
        this.board[1][i] === this.board[2][i]
      ) {
        return this.board[0][i];
      }
    }

    if (
      this.board[0][0] !== '' &&
      this.board[0][0] === this.board[1][1] &&
      this.board[1][1] === this.board[2][2]
    ) {
      return this.board[0][0];
    }

    if (
      this.board[0][2] !== '' &&
      this.board[0][2] === this.board[1][1] &&
      this.board[1][1] === this.board[2][0]
    ) {
      return this.board[0][2];
    }
    if(!this.board[0].includes('') && !this.board[1].includes('') && !this.board[2].includes('')) return "Q"

    return '';
  }

  public get board(){
    return this._board;
  }
  public get yourLine(){
    return this._yourLine;
  }
  public get yourColumn(){
    return this._yourColumn;
  }
}
