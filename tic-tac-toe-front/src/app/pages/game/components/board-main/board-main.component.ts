import {
  Component,
  HostListener,
  Input,
  OnInit
} from '@angular/core';
import { GamePlayer } from 'src/app/model/entities/game-player.entity';

@Component({
  selector: 'board-main',
  templateUrl: './board-main.component.html',
  styleUrls: ['./board-main.component.scss'],
})
export class BoardMainComponent {
  

  @Input() gamePlayer!: GamePlayer;

  @HostListener('document:click', ['$event'])
  _contractBoardEventClickOutsideListener(event: MouseEvent) {}
}
