import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DistrictCycleComponent } from './district-cycle.component';

describe('DistrictCycleComponent', () => {
  let component: DistrictCycleComponent;
  let fixture: ComponentFixture<DistrictCycleComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DistrictCycleComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DistrictCycleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
