import { Response } from './../../model/response.interface';
import { CookieServiceService } from './../../utils/cookie-service.service';
import { ResponseListenerService } from './../../service/socket/responseListener.service';
import { RequestSenderService } from 'src/app/service/socket/requestSender.service';
import { EventEmitter, Injectable } from '@angular/core';
import { Observable, map, switchMap, tap } from 'rxjs';
import { GameDto } from 'src/app/model/interfaces/game-dto.interface';
import { StatusCode } from 'src/app/model/enums/status-code.enum';
import { Move } from 'src/app/model/interfaces/Move.entity';
import { GamePlayer } from 'src/app/model/entities/game-player.entity';
import { PlayerInterface } from 'src/app/model/interfaces/player.interface';
import { BoardMain } from 'src/app/model/entities/board-main.entity';
import { BoardSecondary } from 'src/app/model/entities/board-secondary.entity';

@Injectable({
  providedIn: 'root',
})
export class TicTacToeService {
  private _game!: GamePlayer;
  private _player!: PlayerInterface;

  gameChangesEvent = new EventEmitter<GamePlayer>();

  
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
       const gamePlayer = this._handleGetGame(gameDto);
        this.gameChangesEvent.emit(gamePlayer);
      })
    );
  }
  public newChallenge() {
    const userId = this.cookieServiceService.getValue('user_id');
    this.requestSender.post(
      `/challenge?idPlayerSender=${userId}&idPlayerReceiver=${this.game.player2.id}`
    );
  }
  public makeMove(
    line: number,
    column: number,
    lineBoardSecondary: number,
    columnBoardSecondary: number
  ) {
    const move: Move = {
      idGame: this.game.id,
      line: line,
      column: column,
      lineBoardSecondary: lineBoardSecondary,
      columnBoardSecondary: columnBoardSecondary,
      player: this.player,
    };

     const isMaked = this.game.makeMove(move);
    if (isMaked) {
      this.requestSender.post(`/game/move?userId=${this.player.id}`, move);
    }
    
  }

  private _handleGetGame(game: GameDto): GamePlayer {
    const userId = this.cookieServiceService.getValue('user_id');

    const player1: PlayerInterface =
      game.player1.id == userId ? game.player1 : game.player2;
    const player2: PlayerInterface =
      player1.id == game.player1.id ? game.player2 : game.player1;
    this._player = player1.id == userId ? player1 : player2;

    const boardsSecondary = [
      [
        new BoardSecondary(
          game.boardMain.boardsSecondary[0][0].yourLine,
          game.boardMain.boardsSecondary[0][0].yourColumn,
          game.boardMain.boardsSecondary[0][0].board
        ),
        new BoardSecondary(
          game.boardMain.boardsSecondary[0][1].yourLine,
          game.boardMain.boardsSecondary[0][1].yourColumn,
          game.boardMain.boardsSecondary[0][1].board
        ),
        new BoardSecondary(
          game.boardMain.boardsSecondary[0][2].yourLine,
          game.boardMain.boardsSecondary[0][2].yourColumn,
          game.boardMain.boardsSecondary[0][2].board
        ),
      ],
      [
        new BoardSecondary(
          game.boardMain.boardsSecondary[1][0].yourLine,
          game.boardMain.boardsSecondary[1][0].yourColumn,
          game.boardMain.boardsSecondary[1][0].board
        ),
        new BoardSecondary(
          game.boardMain.boardsSecondary[1][1].yourLine,
          game.boardMain.boardsSecondary[1][1].yourColumn,
          game.boardMain.boardsSecondary[1][1].board
        ),
        new BoardSecondary(
          game.boardMain.boardsSecondary[1][2].yourLine,
          game.boardMain.boardsSecondary[1][2].yourColumn,
          game.boardMain.boardsSecondary[1][2].board
        ),
      ],
      [
        new BoardSecondary(
          game.boardMain.boardsSecondary[2][0].yourLine,
          game.boardMain.boardsSecondary[2][0].yourColumn,
          game.boardMain.boardsSecondary[2][0].board
        ),
        new BoardSecondary(
          game.boardMain.boardsSecondary[2][1].yourLine,
          game.boardMain.boardsSecondary[2][1].yourColumn,
          game.boardMain.boardsSecondary[2][1].board
        ),
        new BoardSecondary(
          game.boardMain.boardsSecondary[2][2].yourLine,
          game.boardMain.boardsSecondary[2][2].yourColumn,
          game.boardMain.boardsSecondary[2][2].board
        ),
      ],
    ];

    let boardCurrent = null;
    if(game.boardMain.boardCurrent){
      boardCurrent = new BoardSecondary(game.boardMain.boardCurrent.yourLine, game.boardMain.boardCurrent.yourColumn, game.boardMain.boardCurrent.board);
    }

    const boardMain = new BoardMain(game.boardMain.board, boardsSecondary, boardCurrent);

    this._game = new GamePlayer(
      game.id,
      player1,
      player2,
      game.playerCurrent,
      game.playerWins,
      game.status,
      boardMain,
      game.gameEnd,
      game.lastMove,
      game.messages
    );  
    
    return this._game;
  }

  get game() {
    return this._game;
  }
  public get player() {
    return this._player;
  }

}
