import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalPlayEndComponent } from './modal-play-end.component';

describe('ModalPlayEndComponent', () => {
  let component: ModalPlayEndComponent;
  let fixture: ComponentFixture<ModalPlayEndComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ModalPlayEndComponent]
    });
    fixture = TestBed.createComponent(ModalPlayEndComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
