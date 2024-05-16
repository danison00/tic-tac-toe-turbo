import { EMPTY, Observable, map, of, switchMap } from 'rxjs';
import { Board } from './Board';
import { Player } from './Player.entity';
import { Move } from '../interfaces/Move.entity';
import { GamePlayerStatus } from '../enums/game-player-status.enum';

export class Game {


  constructor(
    private _id: string,
    private _player1: Player,
    private _player2: Player,
    private _playerCurrent: Player,
    private _status: GamePlayerStatus,
    private _board: Board
  ) {

  }

  public makeMove(move: Move) {
    if(this._status == GamePlayerStatus.PLAYING || this._status == GamePlayerStatus.NO_TOUCH){
      if (this._board.getCell(move.line, move.column) !== '' || !this.playerEquals(this._playerCurrent, move.player)) return false;

      this._board.makeMove(move.line, move.column, move.player.iconPlayer);
      this.tooglePlayer();
      return true;
    } 
    return false;
    
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
