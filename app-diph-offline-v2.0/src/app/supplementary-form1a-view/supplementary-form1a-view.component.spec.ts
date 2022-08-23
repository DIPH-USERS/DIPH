import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplementaryForm1aViewComponent } from './supplementary-form1a-view.component';

describe('SupplementaryForm1aViewComponent', () => {
  let component: SupplementaryForm1aViewComponent;
  let fixture: ComponentFixture<SupplementaryForm1aViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplementaryForm1aViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplementaryForm1aViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
