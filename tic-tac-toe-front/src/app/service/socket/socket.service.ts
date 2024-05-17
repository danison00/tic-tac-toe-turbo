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

  private constructor(private cookieService: CookieServiceService) {}
  public connect(): void {
    const userId = this.cookieService.getValue('user_id');
    if (!userId) return;

    if (this.connection)
      if (this.connection.readyState == this.connection.OPEN || this.connection.readyState == this.connection.CONNECTING) return;

    this.connection = new WebSocket(
      'ws://' +
        environment.domain +
        '/socket/connect'
    );

    this.connection.onmessage = (e) => {

      this.handlerResponse(e);
    }
    this.connection.onopen = () => {
      console.info('Connection wesocket estabilish!');
    };
    this.connection.onclose = (error) => {
      console.log(error);
    };
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