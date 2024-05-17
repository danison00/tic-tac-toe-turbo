import { Game } from 'src/app/model/entities/Game.entity';
import { Response } from './../../model/response.interface';
import { CookieServiceService } from './../../utils/cookie-service.service';
import { ResponseListenerService } from './../../service/socket/responseListener.service';
import { RequestSenderService } from 'src/app/service/socket/requestSender.service';
import { Injectable } from '@angular/core';
import { Observable, map, switchMap, tap } from 'rxjs';
import { GameDto } from 'src/app/model/interfaces/game-dto.interface';
import { StatusCode } from 'src/app/model/enums/status-code.enum';
import { Player } from 'src/app/model/entities/Player.entity';
import { Board } from 'src/app/model/entities/Board';
import { Move } from 'src/app/model/interfaces/Move.entity';

@Injectable({
  providedIn: 'root',
})
export class TicTacToeService {
  private _game!: Game;
  private _player!: Player;

  constructor(
    private requestSender: RequestSenderService,
    private responseListener: ResponseListenerService,
    private cookieServiceService: CookieServiceService
  ) {}

  public getGame(gameId: string): Observable<GameDto> {
    const userId = this.cookieServiceService.getValue('user_id');

    this.requestSender.get(`/game/single?userId=${userId}&gameId=${gameId}`);
    return this.responseListener.listen(StatusCode.GET_GAME).pipe(
      map((response: Response<any>) => response.body as GameDto),
      tap((gameDto: GameDto) => {
        this._handleGetGame(gameDto);
      })
    );
  }
  public newChallenge() {
const userId = this.cookieServiceService.getValue("user_id");
    this.requestSender.post(`/challenge?idPlayerSender=${userId}&idPlayerReceiver=${this.game.player2.id}`)

  }
  public makeMove(line: number, column: number) {
    const move: Move = {
      idGame: this.game.id,
      line: line,
      column: column,
      player: this.player,
    };
    const isMaked = this.game.makeMove(move);
    if (isMaked) {
      this.requestSender.post(`/game/move?userId=${this.player.id}`, move);
    }
  }

  private _handleGetGame(game: GameDto) {
    const userId = this.cookieServiceService.getValue('user_id');

    const player1: Player =
      game.player1.id == userId ? game.player1 : game.player2;
    const player2: Player =
      player1.id == game.player1.id ? game.player2 : game.player1;
    this._player = player1.id == userId ? player1 : player2;

    this._game = new Game(
      game.id,
      player1,
      player2,
      game.playerCurrent,
      game.status,
      new Board(game.board)
    );

  }
  get game() {
    return this._game;
  }
  get player() {
    return this._player;
  }
}
