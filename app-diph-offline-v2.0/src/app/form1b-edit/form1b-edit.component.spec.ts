import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form1bEditComponent } from './form1b-edit.component';

describe('Form1bEditComponent', () => {
  let component: Form1bEditComponent;
  let fixture: ComponentFixture<Form1bEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form1bEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form1bEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
