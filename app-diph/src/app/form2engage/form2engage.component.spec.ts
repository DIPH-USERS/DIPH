import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form2engageComponent } from './form2engage.component';

describe('Form2engageComponent', () => {
  let component: Form2engageComponent;
  let fixture: ComponentFixture<Form2engageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form2engageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form2engageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
