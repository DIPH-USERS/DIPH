import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateindicatorModalDialogComponent } from './createindicator-modal-dialog.component';

describe('CreateindicatorModalDialogComponent', () => {
  let component: CreateindicatorModalDialogComponent;
  let fixture: ComponentFixture<CreateindicatorModalDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CreateindicatorModalDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateindicatorModalDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
