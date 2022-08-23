import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form1bComponent } from './form1b.component';

describe('Form1bComponent', () => {
  let component: Form1bComponent;
  let fixture: ComponentFixture<Form1bComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form1bComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form1bComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
