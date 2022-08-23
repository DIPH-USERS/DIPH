import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form5FollowUpViewComponent } from './form5-follow-up-view.component';

describe('Form5FollowUpViewComponent', () => {
  let component: Form5FollowUpViewComponent;
  let fixture: ComponentFixture<Form5FollowUpViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form5FollowUpViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form5FollowUpViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
