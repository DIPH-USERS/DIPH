import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form3DefineComponent } from './form3-define.component';

describe('Form3DefineComponent', () => {
  let component: Form3DefineComponent;
  let fixture: ComponentFixture<Form3DefineComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form3DefineComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form3DefineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
