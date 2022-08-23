import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { Form4ModalDialogComponent } from './form4-modal-dialog.component';

describe('Form4ModalDialogComponent', () => {
  let component: Form4ModalDialogComponent;
  let fixture: ComponentFixture<Form4ModalDialogComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ Form4ModalDialogComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(Form4ModalDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
