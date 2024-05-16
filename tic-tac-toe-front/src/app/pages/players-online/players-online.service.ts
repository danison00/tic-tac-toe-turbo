import { Injectable } from '@angular/core';
import { Observable, filter, map, merge, of, switchMap, timer } from 'rxjs';
import { StatusCode } from 'src/app/model/enums/status-code.enum';
import { User } from 'src/app/model/interfaces/user.interface';
import { Response } from 'src/app/model/response.interface';
import { RequestSenderService } from 'src/app/service/socket/requestSender.service';
import { ResponseListenerService } from 'src/app/service/socket/responseListener.service';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';

@Injectable({
  providedIn: 'root',
})
export class PlayersOnlineService {
 
  private _userId: string | undefined;

  constructor(
    private cookieServive: CookieServiceService,
    private responseListener: ResponseListenerService,
    private requestSender: RequestSenderService
  ) {
    this._userId = this.cookieServive.getValue('user_id');
  }

  public getUsersOnline(): Observable<User[]> {
    const $getUsersRequest = timer(0, 10000).pipe(
      switchMap((number: number) => {
        this.requestSender.get('/user?userId=' + this._userId);
        return of(number);
      })
    );

    const $getUsersResponse = this.responseListener
      .listen(StatusCode.USERS_ONLINE)
      .pipe(map((response: Response<any>) => response.body as User[]));

    return merge($getUsersRequest, $getUsersResponse).pipe(filter((value: any| number)=> typeof(value) !== 'number'))
  }
  public challengePlayer(id: string) {
    this.requestSender.post(`/challenge?idPlayerSender=${this._userId}&idPlayerReceiver=${id}`);
  }
}
