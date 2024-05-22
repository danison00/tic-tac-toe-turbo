import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-mark',
  templateUrl: './mark.component.html',
  styleUrls: ['./mark.component.scss'],
})
export class MarkComponent {

  @Input()
  mark: string = 'Q';
  @Input()
  size: 'small' | 'big' = 'small';


}
