import { Injectable } from '@angular/core';
import { Subject, takeUntil } from 'rxjs';
import { Event } from '../model/interfaces/Event.interface';
import { environment } from 'src/environments/environment.dev';
import { CookieServiceService } from '../utils/cookie-service.service';

@Injectable({
  providedIn: 'root',
})
export class SocketService {
  private connection!: WebSocket;

  private event = new Subject<Event>();

  constructor(private cookieService: CookieServiceService) {}

  public connect($unsubscribeTrigger: Subject<void>): void {
    if (this.connection)
      if (this.connection.readyState == this.connection.OPEN) return;

    const userId = this.cookieService.getValue('user_id');
    if (userId == undefined) return;

    this.connection = new WebSocket(
      'ws://' + environment.domain + '/gamePlayer?id=' + userId
    );
    this.connection.onmessage = (e) => this.handleEvent(e);
    this.connection.onopen = () => {
      console.info('Connection wesocket estabilish!');
    };
    this.connection.onclose = (error) => {
      console.log(error);
    };
    $unsubscribeTrigger.subscribe(() => this.connection.close());
  }
  private handleEvent(e: MessageEvent) {
    const event: Event = JSON.parse(e.data);
    console.log(event);
    
    this.event.next(event);
  }
  public sendEvent(event: Event) {
    if (this.connection) {
      if (this.connection.readyState == this.connection.OPEN) {
        console.log(event);

        this.connection.send(JSON.stringify(event));
        return;
      }
    }
    const interval = setInterval(() => {
      if (this.connection.readyState == this.connection.OPEN) {
        this.sendEvent(event);
        clearInterval(interval);
      }
    }, 100);
  }
  listenEvent() {
    return this.event;
  }
}
