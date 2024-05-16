import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
} from '@angular/core';

import { UserView } from 'src/app/model/user-view.interface';

@Component({
  selector: 'app-modal-new-game-request',
  templateUrl: './modal-new-game-request.component.html',
  styleUrls: ['./modal-new-game-request.component.scss'],
})
export class ModalNewGameRequestComponent implements OnInit {
  @Output()
  challengeAccept = new EventEmitter<void>();
  @Output()
  challengeReject = new EventEmitter<void>();

  protected firstName = '';
  protected lastName = '';

  @Input()
  userChallenger!: UserView;
  constructor() {}

  ngOnInit(): void {
    if (this.userChallenger) {
      try {
        this.firstName = this.userChallenger.name.split(' ')[0];
        this.lastName =
          this.userChallenger.name.split(' ')[this.userChallenger.name.split(' ').length - 1];
          if(this.firstName == this.lastName) this.lastName = ''
      } catch (error) {
        console.log(error);
        this.firstName = this.userChallenger.name;
      }
    }
  }
}
