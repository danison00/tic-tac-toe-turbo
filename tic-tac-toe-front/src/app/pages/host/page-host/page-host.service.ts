import { Response } from './../../../model/response.interface';
import { RequestSenderService } from 'src/app/service/socket/requestSender.service';
import { Injectable } from '@angular/core';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';
import { ResponseListenerService } from 'src/app/service/socket/responseListener.service';
import { StatusCode } from 'src/app/model/enums/status-code.enum';
import { Observable, map, of, switchMap } from 'rxjs';
import { UserView } from 'src/app/model/user-view.interface';

@Injectable({
  providedIn: 'root',
})
export class PageHostService {
  private userId!: string;

  constructor(
    private cookieService: CookieServiceService,
    private requestSender: RequestSenderService,
    private responseListener: ResponseListenerService
  ) {
    const id = cookieService.getValue('user_id');
    if (id) this.userId = id;
  }

  public getNewChallenge(): Observable<UserView> {
    return this.responseListener
      .listen(StatusCode.NEW_CHALLENGE)
      .pipe(map((response: Response<any>) => response.body as UserView));
  }
  public newGame(challengerId: string) {
    this.requestSender.post(
      `/game?player1Id=${this.userId}&player2Id=${challengerId}`
    );
  }
  public listenNewGame(): Observable<string>{
    return this.responseListener.listen(StatusCode.NEW_GAME).pipe(
      map((response: Response<any>) => response.body as string),
    );
  }
}
