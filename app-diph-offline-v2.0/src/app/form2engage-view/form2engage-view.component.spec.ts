import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form2engageViewComponent } from './form2engage-view.component';

describe('Form2engageViewComponent', () => {
  let component: Form2engageViewComponent;
  let fixture: ComponentFixture<Form2engageViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form2engageViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form2engageViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
