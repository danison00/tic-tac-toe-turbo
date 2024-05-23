import { Component, Input } from '@angular/core';
import { Message } from 'src/app/model/message.interface';

@Component({
  selector: 'message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {

  @Input()
  type: "sent" | "received" = "sent";
  @Input()
  message!: Message;
  public getDate(){
    return this.message.dateHour as Date;
  }
}
