import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SecondaryFormsComponent } from './secondary-forms.component';

describe('SecondaryFormsComponent', () => {
  let component: SecondaryFormsComponent;
  let fixture: ComponentFixture<SecondaryFormsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SecondaryFormsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SecondaryFormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
