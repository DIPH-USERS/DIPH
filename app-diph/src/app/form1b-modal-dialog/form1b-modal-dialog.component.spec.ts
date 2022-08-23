import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form1bModalDialogComponent } from './form1b-modal-dialog.component';

describe('Form1bModalDialogComponent', () => {
  let component: Form1bModalDialogComponent;
  let fixture: ComponentFixture<Form1bModalDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form1bModalDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form1bModalDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
