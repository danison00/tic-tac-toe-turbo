import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalNewGameRequestComponent } from './modal-new-game-request.component';

describe('ModalNewGameRequestComponent', () => {
  let component: ModalNewGameRequestComponent;
  let fixture: ComponentFixture<ModalNewGameRequestComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalNewGameRequestComponent]
    });
    fixture = TestBed.createComponent(ModalNewGameRequestComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
