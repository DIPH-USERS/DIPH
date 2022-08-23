import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form2BComponent } from './form2-b.component';

describe('Form2BComponent', () => {
  let component: Form2BComponent;
  let fixture: ComponentFixture<Form2BComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form2BComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form2BComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
