import { GamePlayerStatus } from '../enums/game-player-status.enum';
import { Move } from '../interfaces/Move.entity';
import { PlayerInterface } from '../interfaces/player.interface';
import { Message } from '../message.interface';
import { BoardMain } from './board-main.entity';
export class GamePlayer {
  public constructor(
    private _id: string,
    private _player1: PlayerInterface,
    private _player2: PlayerInterface,
    private _playerCurrent: PlayerInterface,
    private _playerWins: PlayerInterface,
    private _status: GamePlayerStatus,
    private _boardMain: BoardMain,
    private _gameEnd: boolean,
    private _lastMove: Move,
    private _messages: Message[]

  ) {}

  public tooglePlayerCurrent() {
    this._playerCurrent =
      this._playerCurrent == this._player1 ? this._player2 : this._player1;
  }

  public makeMove(move: Move) {
    if (
      this._status == GamePlayerStatus.NO_WINS ||
      this._status == GamePlayerStatus.WIN
    )
      return false;

    if (this._playerCurrent.id != move.player.id) return false;

    return this._boardMain.makeMove(move);
  }

  public get playerWins() {
    return this._playerWins;
  }
  public getBoardSecondary(line: number, column: number) {
    return this._boardMain.getBoardSecondary(line, column);
  }
  public get id() {
    return this._id;
  }
  public get player2() {
    return this._player2;
  }
  public get player1() {
    return this._player1;
  }
  public get playerCurrent() {
    return this._playerCurrent;
  }
  public get boardMain() {
    return this._boardMain;
  }
  public get boardCurrent() {
    return this.boardMain.boardCurrent;
  }
  public get lastMove() {
    return this._lastMove;
  }
  public get messages(){
    return this._messages;
  }
}
