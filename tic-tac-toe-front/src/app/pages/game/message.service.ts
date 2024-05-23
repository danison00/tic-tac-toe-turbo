import { map } from 'rxjs';
import { Message } from './../../model/message.interface';
import { Injectable } from '@angular/core';
import { StatusCode } from 'src/app/model/enums/status-code.enum';
import { RequestSenderService } from 'src/app/service/socket/requestSender.service';
import { ResponseListenerService } from 'src/app/service/socket/responseListener.service';
import { TicTacToeService } from './game.service';
import { CookieServiceService } from 'src/app/utils/cookie-service.service';

@Injectable({
  providedIn: 'root',
})
export class MessageService {

  constructor(
    private listener: ResponseListenerService,
    private request: RequestSenderService,
    private gameService: TicTacToeService,
    private cookieService: CookieServiceService
  ) {}

  public sendMessage(payload: string) {
    const idSender = this.gameService.game.player1.id;
    const idReceiver = this.gameService.game.player2.id;
    const date = new Date();
    const message: Message = {
      idSender: idSender,
      idReceiver: idReceiver,
      dateHour: date.toISOString(),
      payload: payload,
    };

    this.request.post<Message>('/message?gameId='+this.gameService.game.id, message);
    message.type = 'sent';
    message.dateHour = date;
    return message;
  }
  public listenMessages() {
    return this.listener.listen(StatusCode.NEW_MESSAGE).pipe(
      map((response) => {
        const message = response.body as Message;
        message.type = 'received';
        message.dateHour = new Date(message.dateHour as string);
        return message;
      })
    );
  }
  public getAll() {
   const messages = this.gameService.game.messages;
   const userId = this.cookieService.getValue("user_id");
   messages.forEach((message)=>{
    message.dateHour = new Date(message.dateHour);
    if(message.idSender == userId){
      message.type = "sent";
    }else{
      message.type = "received";
    }
   })
    return messages; 
  }
}
