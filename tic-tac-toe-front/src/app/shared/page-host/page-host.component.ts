import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { EventType } from 'src/app/model/entities/EventType.enum';
import { Event } from 'src/app/model/interfaces/Event.interface';
import { User } from 'src/app/model/interfaces/User.interface';
import { SocketService } from 'src/app/service/socket.service';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';
import { VarsGlobalService } from 'src/app/utils/vars-global.service';

@Component({
  selector: 'app-page-host',
  templateUrl: './page-host.component.html',
  styleUrls: ['./page-host.component.scss'],
})
export class PageHostComponent {
  user!: User;
  private unsubscribeTrigger$ = new Subject<void>();
  private newGameEvent!: Event;
  protected gameRequestUser!: User;
  protected viewModal = false;

  constructor(
    private global: VarsGlobalService,
    private cookieService: CookieServiceService,
    private socketService: SocketService,
    private router: Router,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.listenEvent();
    this.socketService.connect();
    this.global.getIdUser();
  }
  ngOnDestroy(): void {
    this.unsubscribeTrigger$.next();
    this.unsubscribeTrigger$.complete();
  }
  protected listenEvent = () => {
    this.socketService
      .listenEvent()
      .pipe(takeUntil(this.unsubscribeTrigger$))
      .subscribe((event: Event) => {
        switch (event.type) {
          case EventType.USER_DATA: {
            this.user = event.payload as User;
            break;
          }
          case EventType.NEW_GAME_ACCEPT: {
            this.newGameEvent = event;
            this.handlleNewGame();
            break;
          }
          case EventType.NEW_GAME_REQUEST: {
            this.newGameEvent = event;
            this.gameRequestUser = event.payload as User;
            this.viewModal = true;
          }
        }
      });
  };

  onGameAccept() {
    this.socketService.sendEvent({
      idSender: this.getId(),
      idReceiver: this.newGameEvent.idSender,
      type: EventType.NEW_GAME_ACCEPT,
    });
  }
  handlleNewGame() {
    this.viewModal = false;
    this.router.navigate(['tic-tac-toe', 'game'], {
      queryParams: {
        id: this.getId(),
        idGame: this.newGameEvent.payload as string,
      },
    });
  }

  getId(): string {
    return this.cookieService.getValue('user_id');
  }

  onLogout() {
    this.http
      .post(
        this.global.baseUrl + '/authentication/logouter',
        {},
        { withCredentials: true }
      )
      .subscribe((v) => console.log(v));
  }
}
