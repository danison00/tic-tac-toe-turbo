import { User } from './model/interfaces/User.interface';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { SocketService } from './service/socket.service';
import { Router } from '@angular/router';
import { Subject, takeUntil } from 'rxjs';
import { Event } from './model/interfaces/Event.interface';
import { EventType } from './model/entities/EventType.enum';
import { UtilService } from './utils/utils.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'tic-tac-toe-turbo';
  
}
