import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-modal-account-register-successful',
  templateUrl: './modal-account-register-successful.component.html',
  styleUrls: ['./modal-account-register-successful.component.scss']
})
export class ModalAccountRegisterSuccessfulComponent {

  @Input()
  view: boolean = false;
}
