import { Injectable } from '@angular/core';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { Event } from 'src/app/model/interfaces/Event.interface';
import { Move } from 'src/app/model/interfaces/Move.entity';
import { SocketService } from 'src/app/service/socket.service';

@Injectable({
  providedIn: 'root',
  
})
export class GameEventSenderService {
  constructor(private socketService: SocketService) {}

  challenge(idSender: string, idReceiver: string, name: string) {
    this.socketService.sendEvent({
      idSender: idSender,
      idReceiver: idReceiver,
      payload: {
        name: name,
        id: idReceiver,
      },
      type: EventType.NEW_GAME_REQUEST,
    });
  }
  move(move: Move, idPlayer2: string) {
    const event: Event = {
      idSender: move.player.id,
      idReceiver: idPlayer2,
      payload: move,
      type: EventType.MOVE,
    };

    this.socketService.sendEvent(event);
  }
  getGame(userId: string, gameId: string){

    this.socketService.sendEvent({
      idSender: userId,
      idReceiver: userId,
      type: EventType.GET_GAME,
      payload: gameId,
    });
  }
}
