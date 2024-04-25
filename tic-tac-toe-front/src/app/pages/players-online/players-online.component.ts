import { SocketService } from 'src/app/service/socket.service';
import { User } from './../../model/interfaces/User.interface';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, Subscription, takeUntil } from 'rxjs';
import { PageHostComponent } from 'src/app/pages/host/page-host/page-host.component';
import { PlayersOnlineService } from './service/players-online.service';

@Component({
  selector: 'app-players-online',
  templateUrl: './players-online.component.html',
  styleUrls: ['./players-online.component.scss'],
})
export class PlayersOnlineComponent implements OnInit, OnDestroy {
  protected players: User[] = [];
  private $unsubscribeTrigger = new Subject<void>();
  private u!: Subscription;
  private idUser!: string;

  constructor(private playersService: PlayersOnlineService) {}
  ngOnInit(): void {
    this.playersService.init(this.$unsubscribeTrigger);
    this.getPlayers();
  }
  ngOnDestroy(): void {
    this.$unsubscribeTrigger.next();
    this.$unsubscribeTrigger.complete();
  }
  protected onChallenge(btn: any) {
    this.playersService.challenge(btn.id as string);
  }

  public getPlayers(): void {
    this.u = this.playersService
      .handlerUsersOnline()
      .pipe(takeUntil(this.$unsubscribeTrigger))
      .subscribe({
        next: (players: User[]) => {
          this.players = players;
        },
      });
  }
}
