import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form4PlanComponent } from './form4-plan.component';

describe('Form4PlanComponent', () => {
  let component: Form4PlanComponent;
  let fixture: ComponentFixture<Form4PlanComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form4PlanComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form4PlanComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
