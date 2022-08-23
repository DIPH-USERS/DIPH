import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplementaryForm1aEditComponent } from './supplementary-form1a-edit.component';

describe('SupplementaryForm1aEditComponent', () => {
  let component: SupplementaryForm1aEditComponent;
  let fixture: ComponentFixture<SupplementaryForm1aEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplementaryForm1aEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplementaryForm1aEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
