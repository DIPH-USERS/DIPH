import { Component, OnInit, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

@Component({
  selector: 'app-delete-confirmation-dialog',
  templateUrl: './delete-confirmation-dialog.component.html',
  styleUrls: ['./delete-confirmation-dialog.component.css']
})
export class DeleteConfirmationDialogComponent implements OnInit {

  constructor(public dialogRef: MatDialogRef<DeleteConfirmationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

     
     }

  ngOnInit() {
  }

  save() {
    let obj = { "status": "0" };
    this.dialogRef.close(obj);
  }

  cancel() {
    let obj = { "status": "0" }; 
    this.dialogRef.close(obj);
  }

}
