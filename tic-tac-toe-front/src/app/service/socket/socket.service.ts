import { CookieServiceService } from './../../utils/cookie-service.service';
import { Subject } from 'rxjs';
import { environment } from 'src/environments/environment.dev';
import { Injectable } from '@angular/core';
import { Response } from 'src/app/model/response.interface';
import { Request } from 'src/app/model/request.interface';
@Injectable({
  providedIn: 'root',
})
export class SocketService {
  private connection!: WebSocket;
  private $event = new Subject<Response<any>>();
  private closeConnectionEvent = new Subject<void>();

  private constructor(private cookieService: CookieServiceService) {}
  public connect() {
    const userId = this.cookieService.getValue('user_id');
    if (this.connection)
      if (
        this.connection.readyState == this.connection.OPEN ||
        this.connection.readyState == this.connection.CONNECTING
      )
        return this.closeConnectionEvent;

    this.connection = new WebSocket(environment.urlSocket);

    this.connection.onmessage = (e) => {
      this.handlerResponse(e);
    };
    this.connection.onopen = () => {
      console.info('Connection websocket estabilish!');
    };
    this.connection.onclose = (error) => {
      console.log(error);
      if (!error.wasClean) {
        const interval = setInterval(() => {
          if (
            this.connection.OPEN == this.connection.readyState ||
            this.connection.CONNECTING == this.connection.readyState
          ) {
            clearInterval(interval);
          } else {
            this.connect();
          }
        }, 4000);
      }
    };

    this.closeConnectionEvent.subscribe(() => {
      this.connection.close();
    });

    return this.closeConnectionEvent;
  }

  private handlerResponse(e: MessageEvent) {
    const event: Response<any> = JSON.parse(e.data);
    this.$event.next(event);
  }
  public send<T>(req: Request<T>) {
    if (this.connection) {
      if (this.connection.readyState == this.connection.OPEN) {
        this.connection.send(JSON.stringify(req));
        return;
      }
    }
    const interval = setInterval(() => {
      if (this.connection.readyState == this.connection.OPEN) {
        this.send(req);
        clearInterval(interval);
      }
    }, 100);
  }
  listenEvent() {
    return this.$event;
  }
}
