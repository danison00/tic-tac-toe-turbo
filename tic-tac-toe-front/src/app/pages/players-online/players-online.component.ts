import { SocketService } from 'src/app/service/socket.service';
import { User } from './../../model/interfaces/User.interface';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subject, switchMap, takeUntil, timer } from 'rxjs';
import { Event } from 'src/app/model/interfaces/Event.interface';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { AppComponent } from 'src/app/app.component';
import { UtilService } from 'src/app/utils/utils.service';
import { PageHostComponent } from 'src/app/shared/page-host/page-host.component';

@Component({
  selector: 'app-players-online',
  templateUrl: './players-online.component.html',
  styleUrls: ['./players-online.component.scss'],
})
export class PlayersOnlineComponent implements OnInit, OnDestroy {
  protected players: User[] = [];
  private unsubscribeTrigger = new Subject<void>();
  private idUser!: string;

  constructor(
    private socketService: SocketService,
    private util: UtilService,
    private host: PageHostComponent
  ) {}
  ngOnDestroy(): void {
    this.unsubscribeTrigger.next();
    this.unsubscribeTrigger.complete();
  }
  protected onChallenge(btn: any) {
    this.socketService.sendEvent({
      idSender: this.idUser,
      idReceiver: btn.id,
      payload: this.host.user,
      type: EventType.NEW_GAME_REQUEST,
    });
  }
  ngOnInit(): void {
    
    const userId = this.util.getCookie("user_id");
    if(userId==null) return;

    this.idUser = userId;
    this.listenEvent();

    timer(0, 5000)
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe(() => {
        this.socketService.sendEvent({
          idSender: this.idUser,
          idReceiver: '',
          payload: '',
          type: EventType.GET_USERS_ONLINE,
        });
      });
  }

  protected listenEvent = () => {
    this.socketService
      .listenEvent()
      .pipe(takeUntil(this.unsubscribeTrigger))
      .subscribe((ev: Event) => {
        if (ev.type == EventType.GET_USERS_ONLINE)
          this.players = ev.payload as User[];
      });
  };
}
