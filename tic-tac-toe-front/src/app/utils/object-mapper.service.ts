import { NewGameRequest } from 'src/app/model/entities/NewGameRequest.entity';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { Event } from 'src/app/model/interfaces/Event.interface';
import { Move } from '../model/interfaces/Move.entity';
import { Injectable } from '@angular/core';
import { Player } from '../model/entities/Player.entity';
import { User } from '../model/interfaces/User.interface';
@Injectable({
  providedIn: 'root',
})
export class ObjectMapperService {
  // getSimpleMessage = () =>{};
 

  // private treats: any = {
  //   MOVE: this.getMove,
  //   NEW_GAME: this.getNewGameRequest,
  //   SIMPLE_MESSAGE: this.getSimpleMessage,
  //   GET_USERS_ONLINE: this.getUsers,
  //   USER_NOT_ONLINE: this.getUsers,
  //   NEW_GAME_REQUEST: 
  // };
  // constructor() {}

  // // TODO: mapper payload to New Game;

  // public getNewGameRequest(newgameReq: NewGameRequest) : NewGameRequest {
    
  //   return new NewGameRequest(
  //     new Player(
  //       newgameReq.player.id,
  //       newgameReq.player.name,
  //       newgameReq.player.iconPlayer
  //     ),
  //     new Player(
  //       newgameReq.adversary.id,
  //       newgameReq.adversary.name,
  //       newgameReq.adversary.iconPlayer
  //     ),
  //     new Player(
  //       newgameReq.playerInit.id,
  //       newgameReq.playerInit.name,
  //       newgameReq.playerInit.iconPlayer
  //     )
  //   );

  // }

  // getUsers(users: User[]): User[]{
  //   return users;
    
  // }
  // public getMove(move: Move) : Move{
  //   const moveEntity: Move = {
  //     player: new Player(
  //      move.player.id,
  //       move.player.name,
  //       move.player.iconPlayer
  //     ),
  //     line: move.line,
  //     column: move.column,
  //   };

  //   return moveEntity;
  // }
  // public getEvent(event: Event): Event {
 
  
  //   const treat = this.treats[event.type];
  //   const payload = treat(event.payload);
  //   return {
  //     idSender: event.idSender,
  //     idReceiver: event.idReceiver,
  //     payload: payload,
  //     type: event.type,
  //   };

  // }
}
