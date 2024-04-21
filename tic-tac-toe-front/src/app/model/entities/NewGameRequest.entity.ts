import { Player } from './Player.entity';

export interface NewGameRequest {
  id: string,
  player: Player;
  adversary: Player;
  playerInit: Player;
}
