import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { Event } from '../model/interfaces/Event.interface';
import { UtilService } from '../utils/utils.service';
import { environment } from 'src/environments/environment.dev';

@Injectable({
  providedIn: 'root',
})
export class SocketService {
  private connection!: WebSocket;

  private event = new Subject<Event>();

  constructor(private util: UtilService) {}

  public connect(): void {
    if (this.connection)
      if (this.connection.readyState == this.connection.OPEN) return;

    const userId = this.util.getCookie('user_id');
    if (userId == null) return;

    this.connection = new WebSocket('ws://'+environment.domain+'/game?id=' + userId);
    this.connection.onmessage = (e) => this._listenEvent(e);
    this.connection.onopen = () => {
      console.info('Connection wesocket estabilish!');
    };
    this.connection.onclose = (error) => {
      console.log(error);
    };
  }
  private _listenEvent(e: MessageEvent) {
    const event: Event = JSON.parse(e.data);
    this.event.next(event);
  }
  public sendEvent(event: Event) {
    if (this.connection)
      if (this.connection.readyState == this.connection.OPEN) {
        this.connection.send(JSON.stringify(event));
      } else {
        const interval = setInterval(() => {
          if (this.connection.readyState == this.connection.OPEN) {
            this.sendEvent(event);
            clearInterval(interval);
          }
        }, 50);
      }
  }
  listenEvent() {
    return this.event;
  }
}
