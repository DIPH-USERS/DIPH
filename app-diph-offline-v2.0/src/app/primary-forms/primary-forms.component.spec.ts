import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrimaryFormsComponent } from './primary-forms.component';

describe('PrimaryFormsComponent', () => {
  let component: PrimaryFormsComponent;
  let fixture: ComponentFixture<PrimaryFormsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrimaryFormsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrimaryFormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
