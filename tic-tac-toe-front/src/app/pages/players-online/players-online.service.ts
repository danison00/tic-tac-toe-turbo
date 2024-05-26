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
    this.requestSender.get('/topic/users-online');

    return this.responseListener.listen(StatusCode.USERS_ONLINE).pipe(
      map((response) => {
        return response.body as User[];
      })
    );
  }

  complete() {}
  public challengePlayer(id: string) {
    this.requestSender.post(`/challenge?idPlayerReceiver=${id}`);
  }
}
