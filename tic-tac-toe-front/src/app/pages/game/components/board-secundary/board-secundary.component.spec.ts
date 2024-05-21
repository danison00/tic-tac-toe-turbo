import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BoardSecundaryComponent } from './board-secundary.component';

describe('BoardSecundaryComponent', () => {
  let component: BoardSecundaryComponent;
  let fixture: ComponentFixture<BoardSecundaryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [BoardSecundaryComponent]
    });
    fixture = TestBed.createComponent(BoardSecundaryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
