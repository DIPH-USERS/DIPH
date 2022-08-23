import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EdituserviaadminComponent } from './edituserviaadmin.component';

describe('EdituserviaadminComponent', () => {
  let component: EdituserviaadminComponent;
  let fixture: ComponentFixture<EdituserviaadminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EdituserviaadminComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EdituserviaadminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
