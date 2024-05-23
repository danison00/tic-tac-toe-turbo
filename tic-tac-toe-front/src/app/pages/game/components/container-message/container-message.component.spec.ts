import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContainerMessageComponent } from './container-message.component';

describe('ContainerMessageComponent', () => {
  let component: ContainerMessageComponent;
  let fixture: ComponentFixture<ContainerMessageComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ContainerMessageComponent]
    });
    fixture = TestBed.createComponent(ContainerMessageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
