import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditSelectedOptionalIndicatorComponent } from './edit-selected-optional-indicator.component';

describe('EditSelectedOptionalIndicatorComponent', () => {
  let component: EditSelectedOptionalIndicatorComponent;
  let fixture: ComponentFixture<EditSelectedOptionalIndicatorComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditSelectedOptionalIndicatorComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditSelectedOptionalIndicatorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
