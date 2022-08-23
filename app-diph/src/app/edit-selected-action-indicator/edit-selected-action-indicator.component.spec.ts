import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSelectedActionIndicatorComponent } from './edit-selected-action-indicator.component';

describe('EditSelectedActionIndicatorComponent', () => {
  let component: EditSelectedActionIndicatorComponent;
  let fixture: ComponentFixture<EditSelectedActionIndicatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditSelectedActionIndicatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSelectedActionIndicatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
