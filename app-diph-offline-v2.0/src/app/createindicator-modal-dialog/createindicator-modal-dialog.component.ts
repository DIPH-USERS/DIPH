import { Component, OnInit, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

@Component({
  selector: 'app-createindicator-modal-dialog',
  templateUrl: './createindicator-modal-dialog.component.html',
  styleUrls: ['./createindicator-modal-dialog.component.css']
})
export class CreateindicatorModalDialogComponent implements OnInit {

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

  adminloggedIN:string="0";

  constructor(public dialogRef: MatDialogRef<CreateindicatorModalDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

    //Removing duplicate names of area anonymously and saving in this.area_indicator_obj.all_area_id_names
    let param = 'area_id';
    let xyz = data.arr.filter(function (item, pos, array) {
      return array.map(function (mapItem) { return mapItem[param]; }).indexOf(item[param]) === pos;
    });

    let admin_p = sessionStorage.getItem('userStatus');

    if(admin_p == "1"){
      let temparr = [{"area_id":"-1","area_name":"Create New Area"}];

    this.area_indicator_obj.all_area_id_names = temparr.concat(xyz);
    }
    else{
      this.area_indicator_obj.all_area_id_names = xyz;
    }

    

    // this.area_indicator_obj.all_indicator_names = data.arr.filter(t => t.area_id == '1');
    this.area_indicator_obj.all_indicator_names = data.arr;
  }

  ngOnInit() {
    this.adminloggedIN =  sessionStorage.getItem('userStatus');
  }

  newAreaToggle(){
    let x = this.IndicatorBean.new_indicator_area_id;

    if(x == -1){
      this.addNewArea();
    }
    else{
      this.area_arr.splice(0,1);
    }
  }

  area_arr=[]; 

  addNewArea(){
    if(this.area_arr.length == 0){
      this.area_arr.push({"area_name":""});
    }
  }

  @Input() IndicatorBean: any =
    {
      "new_indicator_area_id": "0",
      "new_indicator_name": "",
      "new_indicator_desc": "",
      "new_indicator_numerator": "",
      "new_indicator_denominator": "",
      "new_indicator_source": "",
      "new_indicator_frequency": "0",
      "new_indicator_type": "ACTION",
    };

  cancel() {
    let obj = { "status": "0" };
    this.dialogRef.close(obj);
  }

  save_indicator() {

    this.submitted = true;
    let finalresult = true;

    if (this.IndicatorBean.new_indicator_name == '') {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_area_id == '' || 
     this.IndicatorBean.new_indicator_area_id == "0") {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_desc == '') {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_numerator == '') {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_denominator == '') {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_source == '') {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_frequency == '') {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_type == '') {
      finalresult = false;
    }else if(this.IndicatorBean.new_indicator_area_id == "-1"){
      if(this.area_arr.length !=0){
        if (this.area_arr[0]["area_name"] == '') { 
          finalresult = false;
        }
        else if(this.area_arr[0]["area_name"] != '')
        {
          if(this.validateSpecialCharsAndNum(this.area_arr[0]["area_name"])){
            finalresult = false;
          }
        }
      }
    }
    

    if(this.area_arr.length !=0){
      this.IndicatorBean.new_area_name = (""+this.area_arr[0]["area_name"]).toUpperCase();;
    }
    
    
    this.IndicatorBean.new_indicator_name =  this.IndicatorBean.new_indicator_name[0].toUpperCase() + this.IndicatorBean.new_indicator_name.slice(1);
    
    this.IndicatorBean.new_indicator_desc =  this.IndicatorBean.new_indicator_desc[0].toUpperCase() + this.IndicatorBean.new_indicator_desc.slice(1);

    this.IndicatorBean.new_indicator_numerator =  this.IndicatorBean.new_indicator_numerator[0].toUpperCase() + this.IndicatorBean.new_indicator_numerator.slice(1);

    this.IndicatorBean.new_indicator_denominator =  this.IndicatorBean.new_indicator_denominator[0].toUpperCase() + this.IndicatorBean.new_indicator_denominator.slice(1);

    this.IndicatorBean.new_indicator_source =  this.IndicatorBean.new_indicator_source[0].toUpperCase() + this.IndicatorBean.new_indicator_source.slice(1);

    this.IndicatorBean.new_indicator_frequency =  this.IndicatorBean.new_indicator_frequency[0].toUpperCase() + this.IndicatorBean.new_indicator_frequency.slice(1);


    //alert(JSON.stringify(this.IndicatorBean,null,1000));

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




validateSpecialCharsAndNum(val: string) {
  //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
  return (!!(val).replace(/[A-Za-z\s]/gi, '').length);
}

}
