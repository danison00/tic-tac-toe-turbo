import { Injectable } from '@angular/core';
import { SocketService } from './socket.service';
import { Request } from 'src/app/model/request.interface';
import { WebSocketVerb } from 'src/app/model/enums/webSocketVerb.enum';

@Injectable({
  providedIn: 'root',
})
export class RequestSenderService {
  constructor(private socket: SocketService) {
    socket.connect();
  }

  public post<T>(uri: string, body?: T) {
    const req: Request<any> = {
      uri: uri,
      verb: WebSocketVerb.POST,
      body: body,
    };
    console.log(req);
    
    this.socket.send<any>(req);
  }

  public get(uri: string) {
    const req: Request<null> = {
      uri: uri,
      verb: WebSocketVerb.GET
    };
    this.socket.send<null>(req);
  }
  
}
