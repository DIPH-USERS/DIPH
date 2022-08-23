import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form1AComponent } from './form1-a.component';

describe('Form1AComponent', () => {
  let component: Form1AComponent;
  let fixture: ComponentFixture<Form1AComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form1AComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form1AComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
