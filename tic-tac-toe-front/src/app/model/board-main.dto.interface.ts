import { BoardSecondaryDto } from './board-secondary.dato.interface';
import { BoardSecondary } from './entities/board-secondary.entity';
export interface BoardMainDto{

    boardsSecondary: BoardSecondaryDto[][],
    board: string[][],
    boardCurrent: BoardSecondary

}