import { Component, OnInit, Inject, Input } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { IDropdownSettings } from 'ng-multiselect-dropdown';

@Component({
  selector: 'app-form1b-modal-dialog',
  templateUrl: './form1b-modal-dialog.component.html',
  styleUrls: ['./form1b-modal-dialog.component.css']
})
export class Form1bModalDialogComponent implements OnInit {

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

  constructor(public dialogRef: MatDialogRef<Form1bModalDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: any) {

      //alert(JSON.stringify(data.selected_indi,null,100));

    this.indicatorsUpdatedArray = data.selected_indi;
    //console.log("Its updated array : "+JSON.stringify(this.indicatorsUpdatedArray));

    //alert(JSON.stringify(data.arr,null,100)); 

    //this is corect
    //Removing duplicate names of area anonymously and saving in this.area_indicator_obj.all_area_id_names
    let param = 'area_id';
    this.area_indicator_obj.all_area_id_names = data.arr.filter(function (item, pos, array) {
      return array.map(function (mapItem) { return mapItem[param]; }).indexOf(item[param]) === pos;
    });

    //alert(JSON.stringify(this.area_indicator_obj.all_area_id_names,null,1000)); 

    // this.area_indicator_obj.all_indicator_names = data.arr.filter(t => t.area_id == '1');
    this.area_indicator_obj.all_indicator_names = data.arr;

    // if(data.dialog_result == null){

    // }
    // else{
    //   console.log(data.dialog_result);
    //   this.dialog_result = data.dialog_result;
    //   this.currentselectedindi=this.dialog_result;
    // }

    if (data.selected_indi == null || data.selected_indi == 'undefined') {

    }
    else {

      let custom_arr = [];

      //Find those area ID's which have been already selected and their ids will be stored in 
      //this array.
      let visit_obj_arr = [];
      for (let x = 0; x < data.selected_indi.length; x++) {

        if (!visit_obj_arr.includes(data.selected_indi[x].area_id/*area_id=1*/)) {
          visit_obj_arr.push(data.selected_indi[x].area_id);

          let obj = {};//id{1:"123"}
          obj[data.selected_indi[x].area_id] = [data.selected_indi[x].indicator_val];
          custom_arr.push(obj);
        }
        else {

          //custom_arr  =[{1:["Indi 1","Indi 101"]},{4:["Indi 147"]}];

          for (var key in custom_arr) {
            for (var key2 in custom_arr[key]) {
              if (key2 == this.indicatorsUpdatedArray[x].area_id) {
                let temp_arr: any = custom_arr[key][key2];
                temp_arr.push("" + this.indicatorsUpdatedArray[x].indicator_val);
              }
            }
          }

        }
      }



      this.currentselectedindi = custom_arr;
    }


  }




  ngOnInit() {

    this.all_indicator_name_per_area = this.all_areas_arr;


    this.dropdownSettings = {
      singleSelection: false,
      idField: 'indicator_val',
      textField: 'indicator_val',
      // selectAllText: 'Select All',
      // unSelectAllText: 'UnSelect All',
      itemsShowLimit: 1000,
      allowSearchFilter: true
    };
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
    };

  save_indicator() {

    this.submitted = true;
    let finalresult = true;

    if (this.IndicatorBean.new_indicator_name == '') {
      finalresult = false;
    }
    else if (this.IndicatorBean.new_indicator_area_id == '') {
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

  //After saving an indicator
  save() {

    console.log("Inside save() method to save indicator");
    // this.indicatorsUpdatedSelectedArray
    // this.indicatorsUpdatedRemovedArray


    // this.indicatorsUpdatedArray 

    // console.log("this.indicatorsUpdatedSelectedArray = ");
    // console.log(JSON.stringify(this.indicatorsUpdatedSelectedArray, null, 4));

    console.log("this.indicatorsUpdatedArray = ");

    console.log(JSON.stringify(this.indicatorsUpdatedArray, null, 4));

    //alert("indicatorsUpdatedArray = "+JSON.stringify(this.indicatorsUpdatedArray,null,4));

    //alert("indicatorsUpdatedSelectedArray = "+JSON.stringify(this.indicatorsUpdatedSelectedArray,null,4));


    //Removing same indicators in this loop and then reassigning them alltogether.
    for (let z = 0; z < this.indicatorsUpdatedSelectedArray.length; z++) {

      let current_obj = this.indicatorsUpdatedSelectedArray[z];

      for (let s = 0; s < this.indicatorsUpdatedArray.length; s++) {
        let current_obj2 = this.indicatorsUpdatedArray[s];

        if (current_obj.indicator_val == current_obj2.indicator_val) {
          this.indicatorsUpdatedArray.splice(s, 1);
        }
      }

    }

    console.log("Before Concat this.indicatorsUpdatedArray = ");

    console.log(this.indicatorsUpdatedArray );

    this.indicatorsUpdatedArray = this.indicatorsUpdatedArray.concat(this.indicatorsUpdatedSelectedArray);

    

    console.log("this.indicatorsUpdatedArray = ");
    console.log(JSON.stringify(this.indicatorsUpdatedArray, null, 4));

    console.log("this.indicatorsUpdatedRemovedArray.length = " + this.indicatorsUpdatedRemovedArray.length);

    for (let x = 0; x < this.indicatorsUpdatedRemovedArray.length; x++) {

      let tempobj = this.indicatorsUpdatedRemovedArray[x];

      let indi_id_prop = tempobj.indicator_id;

      console.log("Going to remove data with id " + indi_id_prop);

      for (let y = 0; y < this.indicatorsUpdatedArray.length; y++) {
        let tempobj2 = this.indicatorsUpdatedArray[y];

        if (tempobj2.indicator_id == indi_id_prop) {
          this.indicatorsUpdatedArray.splice(y, 1);
          console.log("Matched");
        }
        else {
          console.log(" Not Matched");
        }

      }

      console.log("this.indicatorsUpdatedArray = ");
      console.log(JSON.stringify(this.indicatorsUpdatedArray, null, 4));
    }




    //this.currentselectedindi


    let custom_arr = [];
    let visit_obj_arr = [];


    for (let x = 0; x < this.indicatorsUpdatedArray.length; x++) {

      if (!visit_obj_arr.includes(this.indicatorsUpdatedArray[x].area_id)) {
        visit_obj_arr.push(this.indicatorsUpdatedArray[x].area_id);

        let obj = {};
        obj[this.indicatorsUpdatedArray[x].area_id] = [this.indicatorsUpdatedArray[x].indicator_val];
        custom_arr.push(obj);
      }
/*
indicatorsUpdatedArray = [{areaid:1,indi_id:123,indi_val:abc,def:"",num:""},{areaid:1,indi_id:124,indi_val:def},
  {areaid:1,indi_id:126,indi_val:geh},{areaid:34,indi_id:678,indi_val:niutri}];


currentselectedindi = [{"1":[abc,def,geh]},{"34":[niutri]}];
*/


      else {

        for (var key in custom_arr) {

          for (var key2 in custom_arr[key]) {
            if (key2 == this.indicatorsUpdatedArray[x].area_id) {

              let temp_arr: any = custom_arr[key][key2];
              temp_arr.push("" + this.indicatorsUpdatedArray[x].indicator_val);

            }
            else {

            }
          }


        }
      }

    }

    this.currentselectedindi = custom_arr;


    // alert("custom_arr = "+JSON.stringify(custom_arr,null,4));
    // alert("this.currentselectedindi = "+JSON.stringify(custom_arr,null,4));

    // console.log("custom_arr = "+JSON.stringify(custom_arr,null,4));
    // console.log("this.currentselectedindi = "+JSON.stringify(custom_arr,null,4));



    console.log("this.indicatorsUpdatedArray = ");
    console.log(JSON.stringify(this.indicatorsUpdatedArray,null,4));

    this.dialogRef.close(this.indicatorsUpdatedArray);
    console.log("Exiting save() method to save indicator");

    
//End of saving an indicator
  }

  cancel() {
    let obj = { "status": "0" };
    this.dialogRef.close(obj);
  }

  visited_area_id = [];

  latest_sel_area_id = '';


  onchangerefreshindicators(val: string) {

    console.log(JSON.stringify(val));
    if (val != "0") {


      // alert("Area id = "+this.current_area_id+", indicators= "+JSON.stringify(this.currentselectedindi, null, 4));

      this.visited_area_id.push(val);
      this.latest_sel_area_id = val;

      //latest_sel_area_id

      //filter object names for selected area-id
      this.dropdownList = this.area_indicator_obj.all_indicator_names.filter(t => t.area_id == this.latest_sel_area_id);
      this.selectedItems = [];

      console.log("After refresh the values are :: ");
      console.log(this.currentselectedindi);

      //this.currentselectedindi

      let get_arr: any;

      let flag = 0;

      if (this.currentselectedindi.length != 0) {

        //This loop gets the object related to currently selected Area id
        for (let i = 0; i < this.currentselectedindi.length; i++) {
          let tempobj = this.currentselectedindi[i];
          let tempcount = 0;
          for (let key in tempobj) {
            if (key == this.latest_sel_area_id) {
              flag++;
              get_arr = tempobj[key];
            }
          }
        }

        //
        console.log("Saved array");
        console.log(get_arr);



        if (flag != 0) {
          for (let i = 0; i < get_arr.length; i++) {
            let str: string = get_arr[i];
            str.replace("\n", "");
            get_arr[i] = str;
          }

          console.log("Transformed array");
          console.log(get_arr);

          var sel_obj = [];
          for (let i = 0; i < this.dropdownList.length; i++) {
            var tempobj = this.dropdownList[i];
            if (get_arr.includes(tempobj.indicator_val)) {
              sel_obj.push(tempobj);
            }

          }

          console.log("Final sel_obj");
          console.log(sel_obj);
          this.selectedItems = sel_obj;
        }


      }

      // alert("this.selectedItems = "+JSON.stringify(this.selectedItems,null,4));
      // alert("this.currentselectedindi = "+JSON.stringify(this.currentselectedindi,null,4));





      //this.all_area_names_arr = this.all_area_names_arr.filter(t => t.area_id == '1');

      // this.dropdownList = this.all_indicator_name_per_area;
    }
    else {
      //alert("0 selected");
    }

    //alert("this.selectedItems  = "+JSON.stringify(this.selectedItems,null,4));

    // alert(JSON.stringify(this.selectedItems,null,100));

    
    // for(let pos=0;pos< this.indicatorsUpdatedArray.length;pos++){

    //   let tempobj  = this.indicatorsUpdatedArray[pos];

    //   for(let index=0;index<this.selectedItems.length;index++){

    //     if(this.selectedItems[index].indicator_val == tempobj.indicator_val){
    //       if(tempobj.form5_filled_flag == "1"){
    //         alert("Cant delete this one");
    //       }
    //     }

    //   }      

    // }


    console.log("Loook this");
console.log(this.selectedItems);

   this.testobj = JSON.stringify( this.selectedItems);
  }

  testobj:any;

  currentselectedindi = [];

  onItemSelect(item: any) {

    //alert(JSON.stringify(this.selectedItems,null,100));

    let temparr = [];

    for (let i = 0; i < this.selectedItems.length; i++) {
      let str = this.selectedItems[i];

      for (let i = 0; i < this.area_indicator_obj.all_indicator_names.length; i++) {
        let tempObj = this.area_indicator_obj.all_indicator_names[i];

        if (tempObj.indicator_val == str) {
          tempObj.data = '';
          tempObj.gap = '';
          tempObj.expected = '';
          tempObj.source = '';
          temparr.push(tempObj);
          break;
        }
      }
    }

    //alert("temparr="+JSON.stringify(temparr,null,10000));

    //alert("indicatorsUpdatedSelectedArray="+JSON.stringify(this.indicatorsUpdatedSelectedArray,null,100));


    //alert("Before Splice :: \nindicatorsUpdatedSelectedArray="+JSON.stringify(this.indicatorsUpdatedSelectedArray,null,1000));

    //Currently temparr contains all selected data
    for (let i = 0; i < temparr.length; i++) {
      let tempobj = temparr[i];

      tempobj.indicator_val;

      for (let j = 0; j < this.indicatorsUpdatedSelectedArray.length; j++) {
        let obj2 = this.indicatorsUpdatedSelectedArray[j];

        if (obj2.indicator_val == tempobj.indicator_val) {
          this.indicatorsUpdatedSelectedArray.splice(j, 1);
        }
      }


    }

    //alert("indicatorsUpdatedSelectedArray="+JSON.stringify(this.indicatorsUpdatedSelectedArray,null,100));

    for (let index = 0; index < temparr.length; index++) {
      let tempobj2 = temparr[index];

      this.indicatorsUpdatedSelectedArray.push(tempobj2);
    }

    //alert("After Splice :: \nindicatorsUpdatedSelectedArray="+JSON.stringify(this.indicatorsUpdatedSelectedArray,null,1000));


    //Adding old code from here

   // alert("currentselectedindi="+JSON.stringify(this.currentselectedindi,null,100));

    //Removing old object for selected area ID
    for (let i = 0; i < this.currentselectedindi.length; i++) {
      let tempobj = this.currentselectedindi[i];

      for (let key in tempobj) {
        if (key == this.latest_sel_area_id) {
          this.currentselectedindi.splice(i, 1);
        }
      }
    }

    let param = this.latest_sel_area_id;

    this.currentselectedindi.filter(function (item, pos, array) {
      return array.map(function (mapItem) { return mapItem[param]; }).indexOf(item[param]) === pos;
    });


    //Now entering the fresh object for samed area id
    let obj = {};
    obj[this.latest_sel_area_id] = this.selectedItems;
    this.currentselectedindi.push(obj);

    //alert(JSON.stringify(this.currentselectedindi,null,1000));

   
  }

  onItemDeSelect(item: any,myobj) {

    //this.selectedItems =  JSON.parse(this.testobj);
    
    // alert(myobj);
    // console.log(myobj);
    // alert(JSON.stringify(this.selectedItems,null,100));
  

    /******************************************************/
    //Here "this.indicatorsUpdatedArray" is the object carrying all data send from form1b
    for(let pos=0;pos< this.indicatorsUpdatedArray.length;pos++){
      let tempobj  = this.indicatorsUpdatedArray[pos];
      if(item == tempobj.indicator_val){

        
        try {
          if(tempobj.form5_filled_flag == "1"){
            alert("Sorry, cannot delete. This indicator is already in use.\n Please Select/Deselect items again");

            $('#area_name').prop('selectedIndex',0);
            this.selectedItems = []; 

          //  let arr_re_assign = this.selectedItems;

          //  for(let index=0;index<this.dropdownList.length;index++){
          //   if(this.dropdownList[index].indicator_val == item){
          //     arr_re_assign.push(this.dropdownList[index]);
          //   }
          //  }

          //  alert(JSON.stringify(arr_re_assign,null,100));
          //  this.selectedItems = arr_re_assign;

          //  console.log(JSON.stringify(arr_re_assign) == this.testobj);

          //  console.log("Obj1 = "+JSON.stringify(arr_re_assign));

          //  console.log("Obj2 = "+this.testobj);

            return false;;
          }  
        } catch (error) {
          console.log("Error "+error);          
        }        
      }
    }

    /******************************************************/

    let temparr = [];

    console.log("Removed Object = " + item);

    console.log("this.selectedItems = " + this.selectedItems);

    for (let i = 0; i < this.area_indicator_obj.all_indicator_names.length; i++) {
      let tempObj = this.area_indicator_obj.all_indicator_names[i];

      if (tempObj.indicator_val == item) {
        tempObj.data = '0';
        tempObj.gap = '0';
        tempObj.expected = '0';
        tempObj.source = '0';
        this.indicatorsUpdatedRemovedArray.push(tempObj);
        break;
      }
    }


    // console.log("Final remove array = "+JSON.stringify(this.indicatorsUpdatedRemovedArray,null,4));

    //Adding old code from here

    //Removing old object for selected area ID
    for (let i = 0; i < this.currentselectedindi.length; i++) {
      let tempobj = this.currentselectedindi[i];

      for (let key in tempobj) {
        if (key == this.latest_sel_area_id) {
          this.currentselectedindi.splice(i, 1);
        }
      }
    }

    let param = this.latest_sel_area_id;

    this.currentselectedindi.filter(function (item, pos, array) {
      return array.map(function (mapItem) { return mapItem[param]; }).indexOf(item[param]) === pos;
    });


    //Now entering the fresh object for samed area id
    let obj = {};
    obj[this.latest_sel_area_id] = this.selectedItems;
    this.currentselectedindi.push(obj);

  }

  onSelectAll(items: any) {
    console.log(items);

    //Removing old object for selected area ID
    for (let i = 0; i < this.currentselectedindi.length; i++) {
      let tempobj = this.currentselectedindi[i];

      for (let key in tempobj) {
        if (key == this.latest_sel_area_id) {
          this.currentselectedindi.splice(i, 1);
        }
        // alert(key); // alerts key
        //alert(tempobj[key]); //alerts key's value
      }
    }//End of Removing old object for selected area ID

    let param = this.latest_sel_area_id;

    this.currentselectedindi.filter(function (item, pos, array) {
      return array.map(function (mapItem) { return mapItem[param]; }).indexOf(item[param]) === pos;
    });

    //Now entering the fresh object for samed area id
    let obj = {};
    obj[this.latest_sel_area_id] = this.selectedItems;
    this.currentselectedindi.push(obj);
    //End of Now entering the fresh object for samed area id
  }

  onDeSelectAll(items: any) {
    console.log(items);

    //Removing old object for selected area ID
    for (let i = 0; i < this.currentselectedindi.length; i++) {
      let tempobj = this.currentselectedindi[i];

      for (let key in tempobj) {
        if (key == this.latest_sel_area_id) {
          this.currentselectedindi.splice(i, 1);
        }
        // alert(key); // alerts key
        //alert(tempobj[key]); //alerts key's value
      }
    }//End of Removing old object for selected area ID

    let param = this.latest_sel_area_id;

    this.currentselectedindi.filter(function (item, pos, array) {
      return array.map(function (mapItem) { return mapItem[param]; }).indexOf(item[param]) === pos;
    });

    //Now entering the fresh object for samed area id
    let obj = {};
    obj[this.latest_sel_area_id] = this.selectedItems;
    this.currentselectedindi.push(obj);
    //End of Now entering the fresh object for samed area id
  }

}
