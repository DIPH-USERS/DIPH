import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form4PlanEditComponent } from './form4-plan-edit.component';

describe('Form4PlanEditComponent', () => {
  let component: Form4PlanEditComponent;
  let fixture: ComponentFixture<Form4PlanEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form4PlanEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form4PlanEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
