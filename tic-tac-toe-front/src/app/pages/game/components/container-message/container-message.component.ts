import {
  AfterViewInit,
  Component,
  ElementRef,
  OnChanges,
  OnInit,
  QueryList,
  SimpleChanges,
  ViewChild,
  ViewChildren,
} from '@angular/core';
import { MessageComponent } from './message/message.component';

@Component({
  selector: 'container-message',
  templateUrl: './container-message.component.html',
  styleUrls: ['./container-message.component.scss'],
})
export class ContainerMessageComponent implements AfterViewInit {
  @ViewChild('messageContainer') private messageContainer!: ElementRef;
  @ViewChild('endMessage') private messageElements!: ElementRef;

  protected hiddenMessageContainer = true;
  resizeTextArea(inputMessage: HTMLTextAreaElement) {
    inputMessage.style.height = 'auto';
    inputMessage.style.height =
      Math.min(
        inputMessage.scrollHeight,
        parseFloat(getComputedStyle(inputMessage).maxHeight)
      ) + 'px';
  }

  ngAfterViewInit(): void {
    const container = this.messageContainer.nativeElement as HTMLElement;
    const lastMessage = this.messageElements.nativeElement as HTMLElement;
    container.scrollTop = lastMessage.offsetTop;
  }
  toogleMessageContainer() {
    this.hiddenMessageContainer = !this.hiddenMessageContainer;
    const container = this.messageContainer.nativeElement as HTMLElement;
    const lastMessage = this.messageElements.nativeElement as HTMLElement;
    container.scrollTop = lastMessage.offsetTop;
  }
}
