import { Board } from '../entities/Board';
import { Player } from '../entities/Player.entity';
import { GamePlayerStatus } from '../enums/game-player-status.enum';
import { Move } from './Move.entity';

export interface GameDto {
  id: string;
  player1: Player;
  player2: Player;
  playerWins: Player;
  playerCurrent: Player;
  status: GamePlayerStatus;
  board: string[][];
}
