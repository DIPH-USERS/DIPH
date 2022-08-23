import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IndicatorsListDashboardComponent } from './indicators-list-dashboard.component';

describe('IndicatorsListDashboardComponent', () => {
  let component: IndicatorsListDashboardComponent;
  let fixture: ComponentFixture<IndicatorsListDashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IndicatorsListDashboardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IndicatorsListDashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
