import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalAccountRegisterSuccessfulComponent } from './modal-account-register-successful.component';

describe('ModalAccountRegisterSuccessfulComponent', () => {
  let component: ModalAccountRegisterSuccessfulComponent;
  let fixture: ComponentFixture<ModalAccountRegisterSuccessfulComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalAccountRegisterSuccessfulComponent]
    });
    fixture = TestBed.createComponent(ModalAccountRegisterSuccessfulComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
