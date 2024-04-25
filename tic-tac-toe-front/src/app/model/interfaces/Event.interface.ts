import { EventType } from '../entities/EventType.enum';
import { Game } from '../entities/Game.entity';
import { NewGameRequest } from '../entities/NewGameRequest.entity';
import { GameDto } from './Game.interface';
import { Move } from './Move.entity';
import { User } from './User.interface';

export interface  Event {
  idSender?: string,
  idReceiver: string,
  payload?: Move | NewGameRequest | string | User[] | User | GameDto,
  type: EventType

}
