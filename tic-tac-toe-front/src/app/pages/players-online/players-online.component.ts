import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { PlayersOnlineService } from './players-online.service';
import { User } from 'src/app/model/interfaces/user.interface';

@Component({
  selector: 'app-players-online',
  templateUrl: './players-online.component.html',
  styleUrls: ['./players-online.component.scss'],
})
export class PlayersOnlineComponent implements OnInit, OnDestroy {
  protected users: User[] = [];
  private $unsubscribeTrigger = new Subject<void>();
  private idUser!: string;

  constructor(
    private playersOnlineService: PlayersOnlineService,
  ) {}
  ngOnInit(): void {
    this.getUsersOnline();
  }
  ngOnDestroy(): void {
    this.$unsubscribeTrigger.next();
    this.$unsubscribeTrigger.complete();
  }
  protected onChallenge(btn: any) {
      this.playersOnlineService.challengePlayer(btn.id as string);
  }

  public getUsersOnline() {
    this.playersOnlineService
      .getUsersOnline()
      .pipe(takeUntil(this.$unsubscribeTrigger))
      .subscribe((users: User[]) => (this.users = users));
  }
}
