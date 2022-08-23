import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SupplementaryGuideComponent } from './supplementary-guide.component';

describe('SupplementaryGuideComponent', () => {
  let component: SupplementaryGuideComponent;
  let fixture: ComponentFixture<SupplementaryGuideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SupplementaryGuideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SupplementaryGuideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
