import { Component, Input } from '@angular/core';

@Component({
  selector: 'message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.scss']
})
export class MessageComponent {

  @Input()
  type: "sent" | "received" = "sent";
  @Input()
  date: Date = new Date();
  @Input()
  payload: string = "Message content works!";
}
