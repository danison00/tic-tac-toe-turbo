import { BoardMainDto } from '../board-main.dto.interface';
import { Board } from '../entities/Board';
import { Player } from '../entities/Player.entity';
import { BoardMain } from '../entities/board-main.entity';
import { GamePlayerStatus } from '../enums/game-player-status.enum';
import { Move } from './Move.entity';
import { PlayerInterface } from './player.interface';

export interface GameDto {
  id: string;
  player1: PlayerInterface;
  player2: PlayerInterface;
  playerWins: PlayerInterface;
  playerCurrent: PlayerInterface;
  status: GamePlayerStatus;
  boardMain: BoardMainDto;
  gameEnd: boolean;
}
