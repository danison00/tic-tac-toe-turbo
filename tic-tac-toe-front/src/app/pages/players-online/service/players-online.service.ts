import { Injectable } from '@angular/core';
import {
  EMPTY,
  Subject,
  of,
  switchMap,
  takeUntil,
  timer,
} from 'rxjs';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { Event } from 'src/app/model/interfaces/Event.interface';
import { User } from 'src/app/model/interfaces/User.interface';
import { SocketService } from 'src/app/service/socket.service';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';

@Injectable({
  providedIn: 'root',
})
export class PlayersOnlineService {
  
  private _idUser!: string;

  constructor(
    private socketService: SocketService,
    private cookieService: CookieServiceService
  ) {}

  public init($unsubscribeTrigger: Subject<void>): void {
    const userId = this.cookieService.getValue('user_id');
    if (userId == undefined) return;

    this._idUser = userId;
    timer(0, 5000)
      .pipe(takeUntil($unsubscribeTrigger))
      .subscribe(() => {        
        this.socketService.sendEvent({
          idSender: this._idUser,
          idReceiver: '',
          type: EventType.GET_USERS_ONLINE,
        });
      });
  }

  public challenge(id: string) {
    this.socketService.sendEvent({
      idSender: this._idUser,
      idReceiver: id,
      type: EventType.NEW_GAME_REQUEST,
    });
  }

  public handlerUsersOnline() {
    return this.socketService.listenEvent().pipe(
      switchMap((ev: Event) => {
        if (ev.type == EventType.GET_USERS_ONLINE) {
          return of(ev.payload as User[]);
        }
        return EMPTY;
      })
    );
  }
}
