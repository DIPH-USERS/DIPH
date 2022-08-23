import { Component, OnInit, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

@Component({
  selector: 'app-edit-selected-optional-indicator',
  templateUrl: './edit-selected-optional-indicator.component.html',
  styleUrls: ['./edit-selected-optional-indicator.component.css']
})
export class EditSelectedOptionalIndicatorComponent implements OnInit {

  adminloggedIN:string="0";

  all_areas_indicators_arr: [];
  all_areas_arr: [];
  all_indicator_name_per_area: [];

  dropdownList = [];
  selectedItems = [];
  dropdownSettings = {};

  submitted = false;

  area_indicator_obj = {
    all_area_id_names: [],
    all_indicator_names: [],
    selected_indicator_names: []
  };

  dialog_result: any = [];
  indicatorsUpdatedArray = [];
  indicatorsUpdatedSelectedArray = [];
  indicatorsUpdatedRemovedArray = [];

  currentObj:any=null;

  constructor(public dialogRef: MatDialogRef<EditSelectedOptionalIndicatorComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) { 

       //Removing duplicate names of area anonymously and saving in this.area_indicator_obj.all_area_id_names
    let param = 'area_id';
    this.area_indicator_obj.all_area_id_names = data.arr.filter(function (item, pos, array) {
      return array.map(function (mapItem) { return mapItem[param]; }).indexOf(item[param]) === pos;
    });

    // this.area_indicator_obj.all_indicator_names = data.arr.filter(t => t.area_id == '1');
    this.area_indicator_obj.all_indicator_names = data.arr;

    this.currentObj = data.currentObj;
    }

  ngOnInit() {

    this.adminloggedIN =  sessionStorage.getItem('userStatus');

  //   {
  //     "indicator_id": "2",
  //     "indicator_val": "Pregnant Women Forum report on mothers input, pregnant women waiting room, youth reproductive health, maternal and new born care performance and quality improvement standard",
  //     "definition": null,
  //     "numerator": null,
  //     "denominator": null,
  //     "source": null,
  //     "theme": "-Focused ANC    -Skilled Birth attendance",
  //     "current_available": "Yes",
  //     "frequency": "Monthly",
  //     "area_id": "0",
  //     "category": "OPTIONAL",
  //     "area_name": null
  // }



  this.IndicatorBean.edit_indicator_area_id = this.currentObj.area_id;
   this.IndicatorBean.edit_indicator_name = this.currentObj.indicator_val;
   this.IndicatorBean.edit_indicator_desc = this.currentObj.definition;
   this.IndicatorBean.edit_indicator_numerator = this.currentObj.numerator;
   this.IndicatorBean.edit_indicator_denominator = this.currentObj.denominator;
   this.IndicatorBean.edit_indicator_source = this.currentObj.source;
   this.IndicatorBean.edit_indicator_frequency = this.currentObj.frequency;
   this.IndicatorBean.edit_indicator_category = this.currentObj.category;

   this.IndicatorBean.theme = this.currentObj.theme;
   this.IndicatorBean.current_available = this.currentObj.current_available;

   this.IndicatorBean.indicator_id = this.currentObj.indicator_id;
  }


  @Input() IndicatorBean: any =
  {
    "edit_indicator_area_id": "0",
    "edit_indicator_name": "",
    "edit_indicator_desc": "",
    "edit_indicator_numerator": "",
    "edit_indicator_denominator": "",
    "edit_indicator_source": "",
    "edit_indicator_frequency": "0",
    "edit_indicator_category":"ESSENTIAL",//depends on what is clicked
    "indicator_id":"0",
    "theme":"",
    "current_available":""
  };

cancel() {
  let obj = { "status": "0" };
  this.dialogRef.close(obj);
}

save_indicator() {

  this.submitted = true;
  let finalresult = true;  

  if (this.IndicatorBean.edit_indicator_name == '') {
    finalresult = false;
  }
  // else if (this.IndicatorBean.edit_indicator_area_id == '') {
  //   finalresult = false;
  // }
  // else if (this.IndicatorBean.edit_indicator_desc == '') {
  //   finalresult = false;
  // }
  // else if (this.IndicatorBean.edit_indicator_numerator == '') {
  //   finalresult = false;
  // }
  // else if (this.IndicatorBean.edit_indicator_denominator == '') {
  //   finalresult = false;
  // }
  // else if (this.IndicatorBean.edit_indicator_source == '') {
  //   finalresult = false;
  // }
  else   if (this.IndicatorBean.edit_indicator_frequency == '') {
    finalresult = false;
  }


  if (finalresult) {

    let ans: boolean = confirm("Current page will reload. \n Changes not saved will be lost.\nDo you want to continue?");

    if (ans) {
      let obj = { "status": "0", "bean": this.IndicatorBean };
      this.dialogRef.close(obj);
    }
    else {

    }

  }
  else 
  {
    alert("Form invalid");
    return;
  }


}

}
