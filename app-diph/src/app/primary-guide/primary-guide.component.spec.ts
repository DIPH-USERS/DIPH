import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PrimaryGuideComponent } from './primary-guide.component';

describe('PrimaryGuideComponent', () => {
  let component: PrimaryGuideComponent;
  let fixture: ComponentFixture<PrimaryGuideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PrimaryGuideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PrimaryGuideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
