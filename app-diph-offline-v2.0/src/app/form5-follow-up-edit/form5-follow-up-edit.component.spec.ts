import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form5FollowUpEditComponent } from './form5-follow-up-edit.component';

describe('Form5FollowUpEditComponent', () => {
  let component: Form5FollowUpEditComponent;
  let fixture: ComponentFixture<Form5FollowUpEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form5FollowUpEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form5FollowUpEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
