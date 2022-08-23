import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form1AEditComponent } from './form1-a-edit.component';

describe('Form1AEditComponent', () => {
  let component: Form1AEditComponent;
  let fixture: ComponentFixture<Form1AEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form1AEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form1AEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
