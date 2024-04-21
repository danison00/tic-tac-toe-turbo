import { PlayerInterface } from './Player.interface';

export interface Move {
    idGame: string
    player: PlayerInterface;
    line: number;
    column: number;
    board?: string[][]
  }
