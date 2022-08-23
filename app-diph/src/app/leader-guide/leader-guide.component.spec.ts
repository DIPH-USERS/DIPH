import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LeaderGuideComponent } from './leader-guide.component';

describe('LeaderGuideComponent', () => {
  let component: LeaderGuideComponent;
  let fixture: ComponentFixture<LeaderGuideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LeaderGuideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LeaderGuideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
