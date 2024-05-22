import { StatusCode } from '../../model/enums/status-code.enum';
import { Injectable } from '@angular/core';
import { SocketService } from './socket.service';
import { EMPTY, filter, of, switchMap } from 'rxjs';
import { Response } from 'src/app/model/response.interface';

@Injectable({
  providedIn: 'root',
})
export class ResponseListenerService {
  constructor(private socket: SocketService) {}

  public connect() {
    return this.socket.connect();
  }

  listen(statusCode: StatusCode) {
    return this.socket
      .listenEvent()
      .pipe(filter((response) => response.status == statusCode));
  }
}
