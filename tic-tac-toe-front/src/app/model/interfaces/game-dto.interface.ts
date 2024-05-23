import { BoardMainDto } from "../board-main.dto.interface";
import { GamePlayerStatus } from "../enums/game-player-status.enum";
import { Message } from "../message.interface";
import { Move } from "./Move.entity";
import { PlayerInterface } from "./player.interface";


export interface GameDto {
  id: string;
  player1: PlayerInterface;
  player2: PlayerInterface;
  playerWins: PlayerInterface;
  playerCurrent: PlayerInterface;
  status: GamePlayerStatus;
  boardMain: BoardMainDto;
  gameEnd: boolean;
  lastMove: Move,
  messages: Message[]
}
