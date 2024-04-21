import { Board } from "../entities/Board";
import { Player } from "../entities/Player.entity";

export interface GameInterface{
    id: string;
    firstMove: boolean,
    player1: Player;
    player2: Player;
    playerInit: Player;
    playerCurrent: Player;
    board: string;
}
