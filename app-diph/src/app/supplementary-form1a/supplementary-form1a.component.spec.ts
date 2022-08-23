import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplementaryForm1aComponent } from './supplementary-form1a.component';

describe('SupplementaryForm1aComponent', () => {
  let component: SupplementaryForm1aComponent;
  let fixture: ComponentFixture<SupplementaryForm1aComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplementaryForm1aComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplementaryForm1aComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
