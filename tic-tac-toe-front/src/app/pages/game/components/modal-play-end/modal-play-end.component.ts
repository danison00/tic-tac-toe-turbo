import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AppComponent } from 'src/app/app.component';
import { PageHostComponent } from 'src/app/pages/host/page-host/page-host.component';

@Component({
  selector: 'app-modal-play-end',
  templateUrl: './modal-play-end.component.html',
  styleUrls: ['./modal-play-end.component.scss'],
})
export class ModalPlayEndComponent {
  @Input()
  wins!: boolean;
  @Input()
  lost!: boolean;
  @Input()
  noWins!: boolean;
  @Output()
  eventChalleng = new EventEmitter<void>();
  constructor(private host: PageHostComponent, private route: Router) {}

  routeHome() {
    this.route.navigate(['tic-tac-toc','home']);
  }
  onChallenge() {
    this.eventChalleng.emit();
  }
}
