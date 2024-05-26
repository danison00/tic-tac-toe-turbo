import {
  AfterViewInit,
  Component,
  ElementRef,
  OnChanges,
  OnDestroy,
  OnInit,
  QueryList,
  SimpleChanges,
  ViewChild,
  ViewChildren,
} from '@angular/core';
import { MessageComponent } from './message/message.component';
import { MessageService } from '../../message.service';
import { Subject, take, takeUntil } from 'rxjs';
import { Message } from 'src/app/model/message.interface';
import { TicTacToeService } from '../../game.service';

@Component({
  selector: 'container-message',
  templateUrl: './container-message.component.html',
  styleUrls: ['./container-message.component.scss'],
})
export class ContainerMessageComponent
  implements AfterViewInit, OnInit, OnDestroy
{
  @ViewChild('messageContainer') private messageContainer!: ElementRef;
  @ViewChild('endMessage') private messageElements!: ElementRef;

  protected messages!: Message[];

  protected hiddenMessageContainer = true;
  protected payloadMessage: string = '';
  protected alertNewMessage = false;
  protected isUserOnline = false;
  private $unsubscribeTrigger = new Subject<void>();
  constructor(
    private messageService: MessageService,
    protected gameService: TicTacToeService
  ) {}

  sendMessage() {
    if (this.payloadMessage.trim() == '') return;
    const message = this.messageService.sendMessage(this.payloadMessage);
    this.messages.push(message);
    this.scrollMessage();
    this.payloadMessage = '';
  }
  onKeyDown($event: KeyboardEvent) {
    if ($event.key == 'Enter') {
      this.sendMessage();
    }
  }

  ngOnDestroy(): void {
    this.$unsubscribeTrigger.next();
    this.$unsubscribeTrigger.complete();
  }
  ngOnInit(): void {
    this.messages = this.messageService.getAll();
    this.messageService
      .listenMessages()
      .pipe(takeUntil(this.$unsubscribeTrigger))
      .subscribe((message) => {
        this.messages.push(message);
        if (this.hiddenMessageContainer) {
          this.alertNewMessage = true;
        }
        this.scrollMessage();
      });

    this.messageService
      .isUserOnline()
      .pipe(takeUntil(this.$unsubscribeTrigger))
      .subscribe((is) => {
        this.isUserOnline = is;
      });
  }

  resizeTextArea(inputMessage: HTMLTextAreaElement) {
    inputMessage.style.height = 'auto';
    inputMessage.style.height =
      Math.min(
        inputMessage.scrollHeight,
        parseFloat(getComputedStyle(inputMessage).maxHeight)
      ) + 'px';
  }

  ngAfterViewInit(): void {}
  toogleMessageContainer() {
    this.alertNewMessage = false;
    this.hiddenMessageContainer = !this.hiddenMessageContainer;
    this.scrollMessage();
  }
  public scrollMessage() {
    setTimeout(() => {
      const container = this.messageContainer.nativeElement as HTMLElement;
      const lastMessage = this.messageElements.nativeElement as HTMLElement;
      container.scroll({ behavior: 'auto' });
      container.scrollTop = lastMessage.offsetTop;
    }, 1);
  }
}
