import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form1bViewComponent } from './form1b-view.component';

describe('Form1bViewComponent', () => {
  let component: Form1bViewComponent;
  let fixture: ComponentFixture<Form1bViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form1bViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form1bViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
