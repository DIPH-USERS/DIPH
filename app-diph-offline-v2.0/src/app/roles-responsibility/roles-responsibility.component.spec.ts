import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RolesResponsibilityComponent } from './roles-responsibility.component';

describe('RolesResponsibilityComponent', () => {
  let component: RolesResponsibilityComponent;
  let fixture: ComponentFixture<RolesResponsibilityComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RolesResponsibilityComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RolesResponsibilityComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
