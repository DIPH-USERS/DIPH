import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form3DefineViewComponent } from './form3-define-view.component';

describe('Form3DefineViewComponent', () => {
  let component: Form3DefineViewComponent;
  let fixture: ComponentFixture<Form3DefineViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form3DefineViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form3DefineViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
