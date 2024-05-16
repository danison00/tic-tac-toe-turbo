import { PlayerInterface } from './player.interface';

export interface Move {
    idGame: string
    player: PlayerInterface;
    line: number;
    column: number;
    board?: string[][]
  }
