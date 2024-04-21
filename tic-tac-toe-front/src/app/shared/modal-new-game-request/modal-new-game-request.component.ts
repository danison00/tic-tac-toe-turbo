import { AppComponent } from './../../app.component';
import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';

import { User } from 'src/app/model/interfaces/User.interface';

@Component({
  selector: 'app-modal-new-game-request',
  templateUrl: './modal-new-game-request.component.html',
  styleUrls: ['./modal-new-game-request.component.scss'],
})
export class ModalNewGameRequestComponent implements OnInit {
  @Output()
  gameAccept = new EventEmitter<void>();
  @Output()
  gameReject = new EventEmitter<void>();

  protected firstName = '';
  protected lastName = '';

  @Input()
  user!: User;
  constructor() {}

  ngOnInit(): void {
    if (this.user) {
      try {
        this.firstName = this.user.name.split(' ')[0];
        this.lastName =
          this.user.name.split(' ')[this.user.name.split(' ').length - 1];
      } catch (error) {
        console.log(error);
        this.firstName = this.user.name;
      }
    }
  }
}
