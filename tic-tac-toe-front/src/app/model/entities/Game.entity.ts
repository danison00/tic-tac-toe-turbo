import { EMPTY, Observable, map, of, switchMap } from 'rxjs';
import { Board } from './Board';
import { Player } from './Player.entity';

export class Game {
  private _id: string;
  private _player1: Player;
  private _player2: Player;
  private _board: Board;
  private _playerCurrent: Player;
  private _playerInit: Player;

  constructor(
    id: string,
    player1: Player,
    player2: Player,
    playerCurrent: Player,
    playerInit: Player,
    board: Board
  ) {
    this._id = id;
    this._player1 = player1;
    this._board = board;
    this._player2 = player2;
    this._playerCurrent = playerCurrent;
    this._playerInit = playerInit;
  }

  public async makeMove(line: number, column: number, player: Player) {
    if (this._board.getCell(line, column) !== '') return false;
    if (!this.playerEquals(this._playerCurrent, player)) return false;

    this._board.makeMove(line, column, player.iconPlayer);
    this.tooglePlayer();
    return true;
  }

  public hasWinner(): Observable<Player> {
    return this._board.winner.pipe(
      switchMap((icon: string) =>{
        if(icon == this._player1.iconPlayer ) return of(this._player1);
        if(icon == this._player2.iconPlayer ) return of(this._player2);

        return EMPTY;
      }
      )
    );
  }
  public tooglePlayer() {
    this._playerCurrent = this.playerEquals(this._playerCurrent, this._player1)
      ? this._player2
      : this._player1;
  }
  public get player1(): Player {
    return this._player1;
  }

  public get player2(): Player {
    return this._player2;
  }
  public get playerCurrent() {
    return this._playerCurrent;
  }
  public get id() {
    return this._id;
  }
  public getCellOfBoard(line: number, column: number) {
    return this._board.getCell(line, column);
  }
  private playerEquals(player1: Player, player2: Player) {
    if (
      player1.name === player2.name &&
      player1.id === player2.id &&
      player1.iconPlayer === player2.iconPlayer
    )
      return true;

    return false;
  }
}
