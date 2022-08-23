import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form4PlanViewComponent } from './form4-plan-view.component';

describe('Form4PlanViewComponent', () => {
  let component: Form4PlanViewComponent;
  let fixture: ComponentFixture<Form4PlanViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form4PlanViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form4PlanViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
