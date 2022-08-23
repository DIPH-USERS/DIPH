import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GeneralGuideComponent } from './general-guide.component';

describe('GeneralGuideComponent', () => {
  let component: GeneralGuideComponent;
  let fixture: ComponentFixture<GeneralGuideComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GeneralGuideComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GeneralGuideComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
