import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form1AViewComponent } from './form1-a-view.component';

describe('Form1AViewComponent', () => {
  let component: Form1AViewComponent;
  let fixture: ComponentFixture<Form1AViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form1AViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form1AViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
