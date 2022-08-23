import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form3DefineEditComponent } from './form3-define-edit.component';

describe('Form3DefineEditComponent', () => {
  let component: Form3DefineEditComponent;
  let fixture: ComponentFixture<Form3DefineEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form3DefineEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form3DefineEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
