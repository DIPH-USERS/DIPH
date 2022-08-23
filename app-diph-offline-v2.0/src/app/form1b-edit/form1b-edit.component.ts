import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Observable } from 'rxjs';
import { form1bEdit } from '../model/form1bEdit';
import * as $ from 'jquery';
import { MatDialog } from '@angular/material';
import { Form1bModalDialogComponent } from '../form1b-modal-dialog/form1b-modal-dialog.component';

@Component({
  selector: 'app-form1b-edit',
  templateUrl: './form1b-edit.component.html',
  styleUrls: ['./form1b-edit.component.css']
})
export class Form1bEditComponent implements OnInit {

  completeClicked : boolean = false;

  date = new FormControl('2019-08-22T23:00:00');
  minDate = new Date("12,01,2019");

  @Input() customer: any;
  public jsonresponse: any;
  loading = true;
  resp: any;
  all_areas_indicators_arr = [];
  form1a_all_doc_arr: any = [];
  total_coverage_indi = [];
  public verified_by_name_from_Db = [];

  selected_indi: any;
  submitted = false;
  form_engage_not_filled = false;


  dialog_response: any = null;


  user: Observable<any>;
  saved_form_indicators = [];

  sanctioned_year: string = new Date().getFullYear() + "";
  populationDesity = new Number(0.0);
  numberOfWomen = new Number(0.0);
  numberOfChildren = new Number(0.0);
  numberOfWomenConversionFactor = 1;
  numberOfChildrenConversionFactor = 1;
  completed = false;


  constructor(location: PlatformLocation, public dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
    history.pushState(null, null, location.href);
    location.onPopState(() => {
      history.pushState(null, null, location.href);
      return false;
    });

   }

  tempvar: string;
  date_max = new Date();


  openDialog(): void {
    const dialogRef = this.dialog.open(Form1bModalDialogComponent, {
      data: {
        myvar: "My variable value",
        arr: this.all_areas_indicators_arr,
        dialog_result: this.dialog_response,
        selected_indi: this.selected_indi
      },
      height: '90%',
      width: '70%'
      // maxHeight:'100%',
      // maxWidth: '100%',
      // width:'1000px',
      // height:'1000px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log("Json string passsed on save will be printed below in result variable");
      //console.log(JSON.stringify(result));


      let final_obj = [];

      let count = 0;
      for (var i in result) {
        if (i == "status") {
          count++;
        }
      }


      if (result == null) {

      }
      else if (count != 0) {
        let indicatorbean_obj: any;
        for (var i in result) {
          if (i == "bean") {
            indicatorbean_obj = result[i];
            // alert(JSON.stringify(indicatorbean_obj, null, 4));
            this._diphHttpClientService.createNewIndicator(indicatorbean_obj)
              .subscribe(
                data => {
                  alert("Successfully submitted!!");
                  console.log(data);
                  this.router.routeReuseStrategy.shouldReuseRoute = () => false;
                  this.router.onSameUrlNavigation = 'reload';
                  this.router.navigate(['/dashboard/form1b']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error); });
          }
        }
      }
      else {
        this.selected_indi = result;
        //this.dialog_response = result;

        //alert("Not printed yet.");

        //alert(JSON.stringify(result,null,100));

        console.log("result = ");
        console.log(JSON.stringify(result,null,100));

        this.customer.total_coverage_indi = result;
      }



    });
  }


  public getform1aDetails(district_id: string, cycle_id: string, year: string, user_id: string) {
    this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {


          let arr1 = [];
          for (let i = 0; i < data.state_policy_array.length; i++) {
            let tempobj = data.state_policy_array[i];

            arr1.push(tempobj);
          }

          let arr2 = [];
          for (let i = 0; i < data.district_policy_array.length; i++) {
            let tempobj = data.district_policy_array[i];

            arr2.push(tempobj);
          }

          let arr3 = [];
          for (let i = 0; i < data.health_dept_array.length; i++) {
            let tempobj = data.health_dept_array[i];

            arr3.push(tempobj);
          }

          let arr4 = [];
          for (let i = 0; i < data.non_health_dept_array.length; i++) {
            let tempobj = data.non_health_dept_array[i];

            arr4.push(tempobj);
          }

          let arr5 = [];
          for (let i = 0; i < data.private_sec_array.length; i++) {
            let tempobj = data.private_sec_array[i];

            arr5.push(tempobj);
          }

          let arr6 = [];
          for (let i = 0; i < data.large_scale_district_array.length; i++) {
            let tempobj = data.large_scale_district_array[i];

            arr6.push(tempobj);
          }

          arr1 = arr1.concat(arr2);

          arr1 = arr1.concat(arr3);

          arr1 = arr1.concat(arr4);

          arr1 = arr1.concat(arr5);

          arr1 = arr1.concat(arr6);

          this.form1a_all_doc_arr = this.form1a_all_doc_arr.concat(arr1);

          console.log("All objects");
          console.log(JSON.stringify(this.form1a_all_doc_arr, null, 4));
        },
        error => {
          console.log(error); alert("Error= " + error);
        });
  }



  ngOnInit() {


    this._diphHttpClientService.getVerified_by_names()
      .subscribe(
        data => {

          if (data['status'] == "1") {
            this.verified_by_name_from_Db = data['verified_by_name'];
          }
          else {
            alert("Error in retrieving \n'Chairperson names from Server'");
          }

        },
        error => {
          console.log(error); alert("Error= " + error);
        });

    setTimeout(() => {
      //console.log("Hello from setTimeout");


      let region_id:string, district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');
      region_id = "" + sessionStorage.getItem('regionId');

      this.sanctioned_year = year + "-" + (parseInt(year) + 1);

      this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          if (data == null || data.completed == null || data.userid == null)

            this.form_engage_not_filled = true;
          else 
            this.form_engage_not_filled = false;
          
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });

      //Ajax calling to get Form1A Details...
      this.getform1aDetails(district_id, cycle_id, year, user_id);

      this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {

            console.log("Data : "+JSON.stringify(data));

                if(data.completed == '1')
                  this.completed = true;
                else if(data.completed == '0')
                  this.completed = false;
            
            this.selected_indi = data.total_coverage_indi;
            this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
            .subscribe(
              response => {
      
               // console.log(response);    
               
      
                if (response.date_of_meeting == null && response.venue_of_meeting == null && response.chairperson_of_meeting == null) {
                  
                  //alert("Form5 not not not filled");

                  for(let pos=0;pos<this.selected_indi.length;pos++){
                    let tempobj = this.selected_indi[pos];
                    tempobj["form5_filled_flag"]  = "0";  
                  }
      
                } else { 
                  //alert("Form5 filled");

                  for(let pos=0;pos<this.selected_indi.length;pos++){
                    let tempobj = this.selected_indi[pos];
                    tempobj["form5_filled_flag"]  = "1";
                  }

                  

                }
      
               
              },
              error => {
                console.log(error); alert("Error= " + error);
              });




            
            let myarr: any = data.total_coverage_indi;


            if (myarr) {


              let max = 0;
              for (let index = 0; index < myarr.length; index++) {
                let temp_obj = myarr[index];

                let area_id = temp_obj["area_id"];
                if (area_id > max) {
                  max = area_id;
                }
              }

              let outer_array = [];

              for (let i = 1; i <= max; i++) {
                //  alert( myarr.filter(t => t.area_id== i));
                //alert(  JSON.stringify(myarr.filter(t => t.area_id== i), null, 4) );


                let temp_arr = myarr.filter(t => t.area_id == i);

                let inner_array = [];
                for (let j = 0; j < temp_arr.length; j++) {
                  let t_obj = temp_arr[j];
                  inner_array.push(t_obj["indicator_val"]);
                }
                let obj = {};
                obj[i] = inner_array;
                outer_array.push(obj);
              }

              this.dialog_response = outer_array;

            }

            //alert(  JSON.stringify(outer_array, null, 4) );

            //this.all_area_names_arr = this.all_area_names_arr.filter(t => t.area_id == '1');

            // for (var pos = 0; pos < data.areas_Id_Indicators_map_list.length; pos++) {
            //   var tempobj = data.areas_Id_Indicators_map_list[pos];

            // }



            // let area_name:string;
            // let indicator_name:string;
            // let table:string= '<div style="overflow: auto; max-height: 200px;"><table  class="table  bg-white table-hover  table-bordered table-sm"><thead   style="background-color: #007b5e;color: #FFFFFF"><th>Area</th><th>Indicator</th></thead><tbody>';

            // for(var pos=0;pos<data.areas_Id_Indicators_map_list.length;pos++){
            //   var tempobj = data.areas_Id_Indicators_map_list[pos];
            //   table = table +"<tr (click)='getcurrentObj($event.target)'><td>"+tempobj["area_name"]+"</td><td>"+tempobj["indicator_val"]+"</td></tr>";              
            // }
            // table = table +"</tbody></table></div>";           

            // $(document).ready(function () {              
            //   $("#myModal #modal-body").html(""+table).promise().done(function(){

            //     $("#myModal #modal-body table tr").click(function(){
            //       if($("#myModal #modal-dynamic-table").html() == ''){                    
            //       $("#myModal #modal-dynamic-table").html('<table  id="myTable" class="table  bg-white table-hover  table-bordered table-sm"><thead   style="background-color: #007b5e;color: #FFFFFF"><th>Area</th><th>Indicator</th></thead><tbody><tr>'+$(this).html()+'</tr></tbody></table>');
            //       }
            //       else{                    
            //         $('#myModal #modal-dynamic-table #myTable tr:last').after('<tr>'+$(this).html()+'</tr>');
            //       }
            //     });

            //   });

            // });



            if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {
              alert("No data set for chosen District, Cycle and Year");
              console.log("No data set for chosen District, Cycle and Year, Redirecting to dashboard");
              this.router.navigate(['dashboard']);
            }
            else {

              this.saved_form_indicators = data.total_coverage_indi;

              this.customer = data;

              this.customer.infra_array = data.infra_array;
              this.customer.fina_array = data.fina_array;
              this.customer.supp_array = data.supp_array;
              this.customer.tech_array = data.tech_array;
              this.customer.hr_array = data.hr_array;

              //console.log("data XYZ = "+JSON.stringify(data));
              //console.log(data);

              //console.log(data.areas_Id_Indicators_map_list);
              //console.log(data.areas_Id_Indicators_map_list[0]);

              this.all_areas_indicators_arr = data.areas_Id_Indicators_map_list;
              let indi = data.areas_Id_Indicators_map_list;
              //alert("indi = "+JSON.stringify(indi,null,1000));


              // Setting values to show in form
              var date = new Date("" + this.customer.date_of_verification);



              var new_date = date.getDate();
              var new_month = (date.getMonth() + 1);
              var new_year = date.getFullYear();

              let x: any;

              if (new_date < 10) {
                x = "0" + new_date;
              }
              else {
                x = new_date;
              }

              let y: any;

              if (new_month < 10) {
                y = "0" + new_month;
              }
              else {
                y = new_month;
              }

              this.date.setValue("" + this.customer.date_of_verification);

              this.customer.date_of_verification = x + '/' + y + '/' + new_year;
              // this.customer.date_of_verification = y + '/' + 3 + '/' + new_year;
              this.date = new FormControl(new_year + '-' + y + '-' + x + 'T23:00:00');
              //Setting values completed
              //Now show fetched values from Server
              

              console.log("this.customer after changing the value = ");
              console.log(this.customer);


              //This code is used in Indicators List (Fetches only ESSENTIAL and ACTION Indicators)
              this._diphHttpClientService.getAllIndicators()
              .subscribe(
                data => {

                  for(let i=0;i<data.areas_Id_Indicators_map_list.length;i++){
                    let temp_o = data.areas_Id_Indicators_map_list[i];

                    for(let j=0;j<this.customer.total_coverage_indi.length;j++){
                      let mytempobj = this.customer.total_coverage_indi[j];

                      if(mytempobj["indicator_id"] == temp_o["indicator_id"]){
                       let obj:any = {};
                       obj = mytempobj;
                       obj.category = ""+temp_o["category"];
                       obj.frequency = ""+temp_o["frequency"];
                        this.customer.total_coverage_indi[j] = obj;
                      }
                    }
                  }
                },
                error => {
                  console.log(error); alert("Error= " + error);
                });

                this._diphHttpClientService.getCurrentRegionDetails(region_id)
                    .subscribe(
                      dataRegion => {
                                      
                        if(dataRegion.reproductive_age_women_cfactor != null || dataRegion.reproductive_age_women_cfactor != '')
                            this.numberOfWomenConversionFactor = parseFloat(dataRegion.reproductive_age_women_cfactor);
                        if(dataRegion.under_five_children_cfactor != null || dataRegion.under_five_children_cfactor != '')
                            this.numberOfChildrenConversionFactor = parseFloat(dataRegion.under_five_children_cfactor);
                      },
                      error => {
                        console.log(error); alert("Error to get current region = " + error);
                      });
            }

          },
          error => {
            console.log(error); alert("Error in fetching data from Server= " + error);
          });

          this.loading = false;
    }, 2000);


    $(document).ready(function () {
      $(window).on('load', function () {
        $("#coverScreen").hide();
        //$('[data-toggle="tooltip"]').tooltip();
      });

      // $("#ucNoteGrid_grdViewNotes_ctl01_btnPrint").click(function () {
      //   $("#coverScreen").show();
      //   });

    });

  }

  

  dynamic_infra_array = [];
  dynamic_fina_array = [];
  dynamic_supp_array = [];
  dynamic_tech_array = [];
  dynamic_hr_array = [];
  dynamic_key_ngo_array = [];
  dynamic_key_stakeholder_array = [];

  onClickAddRowngoInfo(val: number) {

    this.customer.key_ngo_info_array.push({
      "ngo_name": "",
      "ngo_details": "",
      "primary_key":"0"
    });

  }

  onClickAddRowngoSrc(val: number) {

    this.customer.key_ngo_source_array.push({
      "stakeholder_name": "",
      "contact_details": "",
      "primary_key":"0"
    });

  }



  onClickAddInfraRow(val: number) {

    this.dynamic_infra_array.push({
      "document_details": "",
      "document_sanctioned": "",
      "document_available_functional": "",
      "document_gap": "",
      "dist_demogra_dtl_id": "0",
      "primary_key": "0"
    });

  }

  onClickAddFinaRow(val: number) {

    this.dynamic_fina_array.push({
      "document_details": "",
      "document_sanctioned": "",
      "document_available_functional": "",
      "document_gap": "",
      "dist_demogra_dtl_id": "0",
      "primary_key": "0"
    });

  }

  onClickAddSuppRow(val: number) {

    this.dynamic_supp_array.push({
      "document_details": "",
      "document_sanctioned": "",
      "document_available_functional": "",
      "document_gap": "",
      "dist_demogra_dtl_id": "0",
      "primary_key": "0"
    });

  }

  onClickAddTechRow(val: number) {

    this.dynamic_tech_array.push({
      "document_details": "",
      "document_sanctioned": "",
      "document_available_functional": "",
      "document_gap": "",
      "dist_demogra_dtl_id": "0",
      "primary_key": "0"
    });

  }


  onClickAddHrRow(val: number) {

    this.dynamic_hr_array.push({
      "document_details": "",
      "document_sanctioned": "",
      "document_available_functional": "",
      "document_gap": "",
      "dist_demogra_dtl_id": "0",
      "primary_key": "0"
    });

  }

  onClickDeleteInfraRow(val: number) {
    this.dynamic_infra_array.splice(val, 1);
  }

  onClickDeleteFinaRow(val: number) {
    this.dynamic_fina_array.splice(val, 1);
  }

  onClickDeleteSuppRow(val: number) {
    this.dynamic_supp_array.splice(val, 1);
  }

  onClickDeleteTechRow(val: number) {
    this.dynamic_tech_array.splice(val, 1);
  }

  onClickDeleteHrRow(val: number) {
    this.dynamic_hr_array.splice(val, 1);
  }

  onClickRemoveRowngoSrc(val: number) {
    this.customer.key_ngo_source_array.splice(val, 1);
  }

  onClickRemoveRowngoInfo(val: number) {
    this.customer.key_ngo_info_array.splice(val, 1);
  }

  getcurrentObj(Obj: any) {
    console.log("Selected Row");
    console.log(Obj);
  }


  onPrint() {
    window.print();
  }

  isGoodDate(dt){
    var reGoodDate = /^((0?[1-9]|1[012])[- /.](0?[1-9]|[12][0-9]|3[01])[- /.](19|20)?[0-9]{2})*$/;
    return reGoodDate.test(dt);
}

  allowDateFormat(val: string){
    return (!!(val).replace(/[A-Za-z\s]/gi, '').length);
  }


  validateSpecialCharsAndNum(val: string) {
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
    return (!!(val).replace(/[A-Za-z\s]/gi, '').length);
  }

  validate100charactersallowed(val:string){

    if(val != null && val.length <=5000){      
      return false;
    }
    else if(val != null && val.length > 5000){
      return true;
    }
    else if(val == null || (val!= null && val.trim() == "")){
      return true; 
    }

  } 

  allowAlphabetsOnly(event) {
    // alert(event.which);
    if ((event.which > 64 && event.which < 91) || (event.which > 96 && event.which < 123) || (event.which == 32) || (event.which == 0))
      return true;
    else
      return false;
  }

  allowNumbersOnly(event) {
    // alert(event.which);
    if ((event.which > 64 && event.which < 91) || (event.which > 96 && event.which < 123) || (event.which == 32) || (event.which == 0))
      return false;
    else
      return true;
  }

  findvalue(event) {
    // alert(event.which); > 31 <48    ||  > 31 && >57
    if (event.which > 31 && (event.which < 48 || event.which > 57)) {
      event.stopPropagation();
      return false;
    }
    return true;
  }

  allownum(val) {
    if (isNaN(val)) {
      return false;
    }
    // if(isNaN(String.fromCharCode($event.keyCode))){
    //     $event.preventDefault();
    // }
  }

  allowpositiveInt(str:string){
    let regexp = /^[1-9][0-9]*$/g;
    let result = regexp.test(str);
    return !result; 
  
  }
  allowdespositiveInt(str:string){
    let regexp = /^[1-9][0-9]|[1-9.][0-9]+$/g;
    let result = regexp.test(str);
    return !result; 
  
  }

  allowDecimalsIntegersOnly(str:string){
    //let str = "1,2"
    let regexp = /^(([0-9]+)|([0-9]+[.][0-9]+))$/g;
    let result = regexp.test(str);
    return !result;
  }

  allowIntegersUpto100(val:string){
    if( (val != null && parseFloat(val) <= 100)  && (val != null && parseFloat(val) >=0) ){      
      return false; 
    }
    else {
      return true;
    }
  }

  cancelpage(){
    let ans=confirm("Unsaved data will not be saved, Are you sure you want to close.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
  }

  calculatePopulationDensity(population, area){
    
    this.populationDesity = 0.0;
    var temp = 0.0;
    if(population !=null && population != '' && population != 0){
      if(area !=null && area != '' && area != 0){
        temp = population/area;
        this.populationDesity = parseFloat(temp.toFixed(2));        
      }
    }
    this.customer.pop_density = this.populationDesity;
  }

  calculateNumberOfWomen(population){
    
    this.numberOfWomen = 0.0;
    var temp = 0.0;
    if(population !=null && population != '' && population != 0){       
        temp = (population*this.numberOfWomenConversionFactor)/100;
        this.numberOfWomen = parseInt(temp.toFixed(0));       
    }
    this.customer.num_women_in_reproductive_age_15_49_y = this.numberOfWomen;
  }
  calculateNumberOfChildren(population){

    this.numberOfChildren = 0.0;
    var temp = 0.0;
    if(population !=null && population != '' && population != 0){      
        temp = (population*this.numberOfChildrenConversionFactor)/100;
        this.numberOfChildren = parseInt(temp.toFixed(0));        
    }
    this.customer.num_child_under_5_y = this.numberOfChildren;
  }

  onsubmit() {

    
    this.submitted = true;
    let finalresult = true;

  /*
    if(! this.form_engage_not_filled){
      alert("Form Engage data is used in next form. Data can not be edited.");
      window.location.reload();
      return;
  }
  */

    if (this.customer.filled_by == '') {
      finalresult = false;
    }/* else if (this.validateSpecialCharsAndNum(this.customer.filled_by)) {
      finalresult = false;
    }*/
    else if (this.validate100charactersallowed(this.customer.filled_by)) {
      finalresult = false;
    }
    if (this.customer.verified_by_name == '') {
      finalresult = false;
    }

   // alert("this.customer.verified_by_name = "+this.customer.verified_by_name);
   // alert("this.customer.verified_by_other_actual_name = "+this.customer.verified_by_other_actual_name);

    if (this.customer.verified_by_name == '15' && this.customer.verified_by_other_actual_name == '') {
      finalresult = false;
    }

    if (this.customer.verified_by_name == '15' && this.customer.verified_by_other_actual_name == 'null') {
      finalresult = false;
    }

    if (this.customer.verified_by_name == '15' && this.customer.verified_by_other_actual_name == null) {
      finalresult = false;
    }

    if (this.customer.total_area == '') {
      finalresult = false;
    }
    else if (this.allowdespositiveInt(this.customer.total_area)) {
      finalresult = false;
    }
    else if (this.customer.total_area_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.total_area_others == '') {
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.total_area_others)) {
    //   finalresult = false;
    // }

    if (this.customer.total_pop == '') {
      finalresult = false;
    }
    else if (this.allowpositiveInt(this.customer.total_pop)) {
      finalresult = false;
    }
    else if (this.customer.total_pop_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.total_pop_others == '') {
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.total_pop_others)) {
    //   finalresult = false;
    // }

    if (this.customer.num_women_in_reproductive_age_15_49_y == '') {
      finalresult = false;
    }
    else if (this.allowpositiveInt(this.customer.num_women_in_reproductive_age_15_49_y)) {
      finalresult = false;
    }
    else if (this.customer.num_women_in_reproductive_age_15_49_y_source == '') {
      finalresult = false;
    }
    else if (this.customer.num_women_in_reproductive_others == '') {
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.num_women_in_reproductive_others)) {
    //   finalresult = false;
    // }

    if (this.customer.num_child_under_5_y == '') {
      finalresult = false;
    }
    else if (this.allowpositiveInt(this.customer.num_child_under_5_y)) {
      finalresult = false;
    }
    else if (this.customer.num_child_under_5_y_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.no_of_child_under5_others == '') {
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.no_of_child_under5_others)) {
    //   finalresult = false;
    // }

    if (this.customer.rural_pop == '') {
      finalresult = false;
    }
    else if (this.customer.rural_pop_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.rural_pop_others == '') {
      finalresult = false;
    }
    else if(this.allowDecimalsIntegersOnly(this.customer.rural_pop)){
      finalresult = false;
    } 
    else if(this.allowIntegersUpto100(this.customer.rural_pop)){
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.rural_pop_others)) {
    //   finalresult = false;
    // }
    /*
    if (this.customer.sc_pop == '') {
      finalresult = false;
    }
    else if (this.customer.sc_pop_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.sc_pop_others == '') {
      finalresult = false;
    }
    else if(this.allowDecimalsIntegersOnly(this.customer.sc_pop)){
      finalresult = false;
    } 
    else if(this.allowIntegersUpto100(this.customer.sc_pop)){
      finalresult = false;
    }*/
    // else if (this.validateSpecialCharsAndNum(this.customer.sc_pop_others)) {
    //   finalresult = false;
    // }

    /*
    if (this.customer.st_pop == '') {
      finalresult = false;
    }
    else if (this.customer.st_pop_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.st_pop_others == '') {
      finalresult = false;
    }
    else if(this.allowDecimalsIntegersOnly(this.customer.st_pop)){
      finalresult = false;
    } 
    else if(this.allowIntegersUpto100(this.customer.st_pop)){
      finalresult = false;
    }*/
    // else if (this.validateSpecialCharsAndNum(this.customer.st_pop_others)) {
    //   finalresult = false;
    // }


    if (this.customer.pop_density == '') {
      finalresult = false;
    }   
    else if (this.customer.pop_density_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.pop_dens_others == '') {
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.pop_dens_others)) {
    //   finalresult = false;
    // }


    if (this.customer.total_literacy == '') {
      finalresult = false;
    }
    else if (this.customer.total_literacy_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.tot_lit_others == '') {
      finalresult = false;
    }
    else if(this.allowDecimalsIntegersOnly(this.customer.total_literacy)){
      finalresult = false;
    } 
    else if(this.allowIntegersUpto100(this.customer.total_literacy)){
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.tot_lit_others)) {
    //   finalresult = false;
    // }


    if (this.customer.fem_literacy == '') {
      finalresult = false;
    }
    else if (this.customer.fem_literacy_demogra_id == '') {
      finalresult = false;
    }
    else if (this.customer.female_lit_others == '') {
      finalresult = false;
    }
    else if(this.allowDecimalsIntegersOnly(this.customer.fem_literacy)){
      finalresult = false;
    } 
    else if(this.allowIntegersUpto100(this.customer.fem_literacy)){
      finalresult = false;
    }
    // else if (this.validateSpecialCharsAndNum(this.customer.female_lit_others)) {
    //   finalresult = false;
    // }

    if (this.customer.iphs_theme_name == '') {
      finalresult = false;
    }
    /*
    else if (this.validateSpecialCharsAndNum(this.customer.iphs_theme_name)) {
      finalresult = false;
    }*/
    else if (this.validate100charactersallowed(this.customer.iphs_theme_name)) {
      finalresult = false;
    }

    if(this.customer.total_coverage_indi.length == 0){
      alert("Atleast one indicator should be selected in 'WHO Health System Blocks' section");
      return;
    }

    for (let z = 0; z < this.customer.total_coverage_indi.length; z++) {
      if (this.customer.total_coverage_indi[z]['source'] == '') {
        finalresult = false;
      }
      else if (this.customer.total_coverage_indi[z]['data'] == '') {
        finalresult = false;
      }
      else if (this.customer.total_coverage_indi[z]['expected'] == '') {
        finalresult = false;
      }
    }  

    for (let j = 0; j < this.customer.hr_array.length; j++) {
      if (this.customer.hr_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.customer.hr_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.customer.hr_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.customer.hr_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.customer.hr_array[j]['document_details']) ) {
        finalresult = false;
      } 
    }


    for (let j = 0; j < this.dynamic_hr_array.length; j++) {
      if (this.dynamic_hr_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_hr_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_hr_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.dynamic_hr_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.dynamic_hr_array[j]['document_details'])) {
        finalresult = false;
      }
    }


    for (let j = 0; j < this.customer.tech_array.length; j++) {
      if (this.customer.tech_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.customer.tech_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.customer.tech_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.customer.tech_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.customer.tech_array[j]['document_details']) ) {
        finalresult = false;
      }
    }


    for (let j = 0; j < this.dynamic_tech_array.length; j++) {
      if (this.dynamic_tech_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_tech_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_tech_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.dynamic_tech_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.dynamic_tech_array[j]['document_details'])) {
        finalresult = false;
      }
    }


    for (let j = 0; j < this.customer.supp_array.length; j++) {
      if (this.customer.supp_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.customer.supp_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.customer.supp_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.customer.supp_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.customer.supp_array[j]['document_details']) ) {
        finalresult = false;
      }
    }


    for (let j = 0; j < this.dynamic_supp_array.length; j++) {
      if (this.dynamic_supp_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_supp_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_supp_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.dynamic_supp_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.dynamic_supp_array[j]['document_details'])) {
        finalresult = false;
      }
    }



    for (let j = 0; j < this.customer.fina_array.length; j++) {
      if (this.customer.fina_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.customer.fina_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.customer.fina_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.customer.fina_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.customer.fina_array[j]['document_details']) ) {
        finalresult = false;
      } 
    }


    for (let j = 0; j < this.dynamic_fina_array.length; j++) {
      if (this.dynamic_fina_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_fina_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_fina_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.dynamic_fina_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.dynamic_fina_array[j]['document_details'])) {
        finalresult = false;
      }
    }

    for (let j = 0; j < this.customer.infra_array.length; j++) {
      if (this.customer.infra_array[j]['document_details'] == '') {
        finalresult = false;
      }      
      else if (this.customer.infra_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.customer.infra_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.customer.infra_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.customer.infra_array[j]['document_details']) ) {
        finalresult = false;
      } 
    }


    for (let j = 0; j < this.dynamic_infra_array.length; j++) {
      if (this.dynamic_infra_array[j]['document_details'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_infra_array[j]['document_sanctioned'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_infra_array[j]['document_available_functional'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.dynamic_infra_array[j]['document_details'])) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.dynamic_infra_array[j]['document_details'])) {
        finalresult = false;
      }
    }

    //key_ngo_source_array

    for(let j = 0; j < this.customer.key_ngo_info_array.length; j++){
      if (this.customer.key_ngo_info_array[j]['ngo_name'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.customer.key_ngo_info_array[j]['ngo_name']) ) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.customer.key_ngo_info_array[j]['ngo_name']) ) {
        finalresult = false;
      }
      if (this.customer.key_ngo_info_array[j]['ngo_details'] == '') {
        finalresult = false;
      }
    }

    //key_ngo_info_array

    for(let j = 0; j < this.customer.key_ngo_source_array.length; j++){
      if (this.customer.key_ngo_source_array[j]['stakeholder_name'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum(this.customer.key_ngo_source_array[j]['stakeholder_name'])   ) {
        finalresult = false;
      }*/
      else if (this.validate100charactersallowed(this.customer.key_ngo_source_array[j]['stakeholder_name'])  ) {
        finalresult = false;
      }
      if (this.customer.key_ngo_source_array[j]['contact_details'] == '') {
        finalresult = false;
      } 
    }



    if (!finalresult) {
      alert("Form invalid");
      return;
    }



    //alert("Saved Day = '"+new_date2+"', Month = '"+new_month2+"', Year = '"+new_year2+"'");


    //alert("Form submitted");
   // this.loading = true;
    //console.log(this.customer);

    //alert("'"+this.customer.date_of_verification+"'");
    // alert(this.customer.date_of_verification);
    // var date = new Date("" + this.customer.date_of_verification);

    // var new_date = date.getDate();
    // var new_month = (date.getMonth() + 1);
    // var new_year = date.getFullYear();
    // //9
    // //10
    // //2009
    // console.log(new_date); console.log(new_month); console.log(new_year);

    // this.customer.date_of_verification = new_year + "-" + new_month + "-" + new_date;

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    // Array.prototype.push.apply(arr1,arr2);



    //this.customer.total_coverage_indi = this.total_coverage_indi;
    Array.prototype.push.apply(this.customer.infra_array, this.dynamic_infra_array);

    Array.prototype.push.apply(this.customer.fina_array, this.dynamic_fina_array);

    Array.prototype.push.apply(this.customer.supp_array, this.dynamic_supp_array);

    Array.prototype.push.apply(this.customer.tech_array, this.dynamic_tech_array);

    Array.prototype.push.apply(this.customer.hr_array, this.dynamic_hr_array);


    // dynamic_infra_array=[];
    // dynamic_fina_array=[];
    // dynamic_supp_array=[];
    // dynamic_tech_array=[];
    // dynamic_hr_array=[];

    this.completeClicked = true; 
    
    if(this.form_engage_not_filled){      

      this._diphHttpClientService.editUpdateForm1BDetails(this.customer, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          alert("Successfully submitted!!");
          this.completeClicked = false;
          this.router.navigate(['dashboard/form1bview']);
        },
        error => { 
	this.completeClicked = false;
          alert("Error= " + error);
          this.router.navigate(['dashboard']);
       });

    }else{
      
      this._diphHttpClientService.editUpdateForm1BWhenForm2Saved(this.customer, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          alert("Successfully submitted!!");
          this.completeClicked = false;
          this.router.navigate(['dashboard/form1bview']);
        },
        error => { 
	this.completeClicked = false;
          alert("Error= " + error);
          this.router.navigate(['dashboard']);
       });

    }
   
  }

  partialSave(){

    if (this.customer.filled_by == '') {
      alert("Venue of the meeting is compulsary");
      return;
    }
    else if (this.validate100charactersallowed(this.customer.filled_by)) {
      alert("Venue of the meeting must be less than 5000 characters");
      return;
    }
    if (this.customer.verified_by_name == '') {
      alert("Chairperson of the meeting is compulsary");
      return;
    }
   
    if (this.customer.verified_by_name == '15' && this.customer.verified_by_other_actual_name == '') {
      alert("Chairperson of the meeting is compulsary");
      return;
    }

    if (this.customer.verified_by_name == '15' && this.customer.verified_by_other_actual_name == 'null') {
      alert("Chairperson of the meeting is compulsary");
      return;
    }

    if (this.customer.verified_by_name == '15' && this.customer.verified_by_other_actual_name == null) {
      alert("Chairperson of the meeting is compulsary");
      return;
    }

    if (this.customer.total_area !=null && this.customer.total_area != '' && this.allowdespositiveInt(this.customer.total_area)) {
      alert("Total area(Sq.km) must be positive number");
      return;
    }

	if (this.customer.total_pop !=null && this.customer.total_pop != '' && this.allowpositiveInt(this.customer.total_pop)) {
        alert("Total population must be positive number");
        return;
    }
    
	if (this.customer.num_women_in_reproductive_age_15_49_y !=null && this.customer.num_women_in_reproductive_age_15_49_y != '' && this.allowpositiveInt(this.customer.num_women_in_reproductive_age_15_49_y)) {
        alert("Number of women in the reproductive age group must be positive number");
        return;
    }
   
   if (this.customer.num_child_under_5_y !=null && this.customer.num_child_under_5_y != '' && this.allowpositiveInt(this.customer.num_child_under_5_y)) {
        alert("Number of children under 5 years must be positive number");
        return;
    }
    
	if(this.customer.rural_pop !=null && this.customer.rural_pop != '' && (this.allowIntegersUpto100(this.customer.rural_pop))){
		alert("Rural Population must be positive number less or equal to 100");
        return;
    }
    
	if(this.customer.total_literacy !=null && this.customer.total_literacy != '' && (this.allowIntegersUpto100(this.customer.total_literacy))){
        alert("Total literacy must be positive number less or equal to 100");
        return;
    }
	

	if(this.customer.fem_literacy !=null && this.customer.fem_literacy != '' && (this.allowIntegersUpto100(this.customer.fem_literacy))){    
        alert("Female literacy must be positive number less or equal to 100");
        return;
    }


    for (let j = 0; j < this.customer.hr_array.length; j++) {
      if (this.customer.hr_array[j]['document_details'] != '' && this.validate100charactersallowed(this.customer.hr_array[j]['document_details']) ) {
        alert("Human resources details can not be greater than 5000 characters");
        return;
      } 
    }


    for (let j = 0; j < this.dynamic_hr_array.length; j++) {
      if (this.dynamic_hr_array[j]['document_details'] == '') {        
		alert("Human resources details is expanded but not filled");
        return;
      }      
      else if (this.validate100charactersallowed(this.dynamic_hr_array[j]['document_details'])) {
        alert("Human resources details can not be greater than 5000 characters");
        return;
      }
    }


    for (let j = 0; j < this.customer.tech_array.length; j++) {
      if (this.customer.tech_array[j]['document_details'] != '' && this.validate100charactersallowed(this.customer.tech_array[j]['document_details']) ) {
         alert("Technology details can not be greater than 5000 characters");
        return;
      }
    }


    for (let j = 0; j < this.dynamic_tech_array.length; j++) {
      if (this.dynamic_tech_array[j]['document_details'] == '') {
        alert("Technology details is expanded but not filled");
        return;
      }
      else if (this.validate100charactersallowed(this.dynamic_tech_array[j]['document_details'])) {
        alert("Technology details can not be greater than 5000 characters");
        return;
      }
    }


    for (let j = 0; j < this.customer.supp_array.length; j++) {
      if (this.customer.supp_array[j]['document_details'] != '' && this.validate100charactersallowed(this.customer.supp_array[j]['document_details']) ) {
        alert("Supply details can not be greater than 5000 characters");
        return;
      }
    }


    for (let j = 0; j < this.dynamic_supp_array.length; j++) {
      if (this.dynamic_supp_array[j]['document_details'] == '') {
        alert("Supply details is expanded but not filled");
        return;
      }
      else if (this.validate100charactersallowed(this.dynamic_supp_array[j]['document_details'])) {
        alert("Supply details can not be greater than 5000 characters");
        return;
      }
    }



    for (let j = 0; j < this.customer.fina_array.length; j++) {
      if (this.customer.fina_array[j]['document_details'] != '' && this.validate100charactersallowed(this.customer.fina_array[j]['document_details']) ) {
        alert("Finance details can not be greater than 5000 characters");
        return;
      } 
    }


    for (let j = 0; j < this.dynamic_fina_array.length; j++) {
      if (this.dynamic_fina_array[j]['document_details'] == '') {
        alert("Finance details is expanded but not filled");
        return;
      }
      else if (this.validate100charactersallowed(this.dynamic_fina_array[j]['document_details'])) {
        alert("Finance details can not be greater than 5000 characters");
        return;
      }
    }

    for (let j = 0; j < this.customer.infra_array.length; j++) {
     if (this.customer.infra_array[j]['document_details'] != '' && this.validate100charactersallowed(this.customer.infra_array[j]['document_details']) ) {
        alert("Infrastruture details can not be greater than 5000 characters");
        return;
      } 
    }


    for (let j = 0; j < this.dynamic_infra_array.length; j++) {
      if (this.dynamic_infra_array[j]['document_details'] == '') {
        alert("Infrastruture details is expanded but not filled");
        return;
      }
      else if (this.validate100charactersallowed(this.dynamic_infra_array[j]['document_details'])) {
        alert("Infrastruture details can not be greater than 5000 characters");
        return;
      }
    }

    //key_ngo_source_array

    for(let j = 0; j < this.customer.key_ngo_info_array.length; j++){
      if (this.customer.key_ngo_info_array[j]['ngo_name'] != '' && this.validate100charactersallowed(this.customer.key_ngo_info_array[j]['ngo_name']) ) {
        alert("NGO name can not be greater than 5000 characters");
        return;
      }
    }

    //key_ngo_info_array

    for(let j = 0; j < this.customer.key_ngo_source_array.length; j++){
      if (this.customer.key_ngo_source_array[j]['stakeholder_name'] != '' && this.validate100charactersallowed(this.customer.key_ngo_source_array[j]['stakeholder_name'])  ) {
        alert("Private Stakeholder name can not be greater than 5000 characters");
        return;
      }
    }


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    Array.prototype.push.apply(this.customer.infra_array, this.dynamic_infra_array);

    Array.prototype.push.apply(this.customer.fina_array, this.dynamic_fina_array);

    Array.prototype.push.apply(this.customer.supp_array, this.dynamic_supp_array);

    Array.prototype.push.apply(this.customer.tech_array, this.dynamic_tech_array);

    Array.prototype.push.apply(this.customer.hr_array, this.dynamic_hr_array);

    this.completeClicked = true;

    this._diphHttpClientService.editUpdateForm1BDetails(this.customer, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {
          alert("Successfully submitted!!");
          this.completeClicked = false;
          this.router.navigate(['dashboard/form1bview']);
        },
        error => { this.completeClicked = false; alert("Error= " + error); });
  }


  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }
}
