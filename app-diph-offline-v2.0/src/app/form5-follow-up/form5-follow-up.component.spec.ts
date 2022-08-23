import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form5FollowUpComponent } from './form5-follow-up.component';

describe('Form5FollowUpComponent', () => {
  let component: Form5FollowUpComponent;
  let fixture: ComponentFixture<Form5FollowUpComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form5FollowUpComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form5FollowUpComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
