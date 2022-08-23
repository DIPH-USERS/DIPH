import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSelectedIndicatorComponent } from './edit-selected-indicator.component';

describe('EditSelectedIndicatorComponent', () => {
  let component: EditSelectedIndicatorComponent;
  let fixture: ComponentFixture<EditSelectedIndicatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditSelectedIndicatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSelectedIndicatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
