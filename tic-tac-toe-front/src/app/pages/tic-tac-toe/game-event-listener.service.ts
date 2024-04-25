import { Injectable } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { Event } from 'src/app/model/interfaces/Event.interface';
import { GameDto } from 'src/app/model/interfaces/Game.interface';
import { Move } from 'src/app/model/interfaces/Move.entity';
import { SocketService } from 'src/app/service/socket.service';
import { GameService } from './game.service';

@Injectable({
  providedIn: 'root',
})
export class GameEventListenerService {
  constructor(
    private socketService: SocketService,
    private gameService: GameService
  ) {}

  public init($unsubscribeTrigger: Subject<void>) {
    this.socketService
      .listenEvent()
      .pipe(takeUntil($unsubscribeTrigger))
      .subscribe({
        next: (event: Event) => {
          switch (event.type) {
            case EventType.GET_GAME: {
              this.handleGetGame(event.payload as GameDto);

              break;
            }

            case EventType.MOVE: {
              this.moveHandle(event.payload as Move);
              console.log('move');

            }
          }
        },
      });
      return this;
  }
  moveHandle(move: Move) {
   this.gameService.movePlayer2(move)
  }
  handleGetGame(gameDto: GameDto) {
    // TODO: handle get Game
    this.gameService.handleGetGame(gameDto);
    console.log(gameDto);
  }
}
