import { Component, OnInit, Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-supplementary-form1a',
  templateUrl: './supplementary-form1a.component.html',
  styleUrls: ['./supplementary-form1a.component.css']
})
export class SupplementaryForm1aComponent implements OnInit {

  completeClicked : boolean = false;

  submitted = false;
  nextbtn = false;
  savedform = false;
  public serverjsonresponse: any;
  stakeholder_arr = [];
  primary_stakeholder_arr = [
    { id: '1', name: 'order 1' },
    { id: '2', name: 'order 2' },
    { id: '3', name: 'order 3' },
    { id: '4', name: 'order 4' }
  ];
  secondary_stakeholder_arr = [];
  action_required: any = {};


  dynamicForm: FormGroup;
  date = new FormControl(new Date());
  date_max = new Date();
  date_max2 = new Date();
  date_2 = new FormControl(new Date());
  minDate =new Date("12,01,2019");

  constructor(location: PlatformLocation, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
    history.pushState(null, null, location.href);
    location.onPopState(() => {
    history.pushState(null, null, location.href);
    return false;
  });

    let user = sessionStorage.getItem('username');
    if (user == null || user.length == 0) {
      this.router.navigate(['login']);
    }

    this.submitted = false;

  }

  ngOnInit() {


    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        dataFollowup => {
                  
          if ( (dataFollowup.date_of_meeting == null || dataFollowup.venue_of_meeting == '') || 
               (dataFollowup.completed == "0") ) {
            alert("Please complete Follow Up form to access this form");
            this.router.navigate(['dashboard/secondaryforms']);
            return;
          }
        });

    let jsonresponse: any;
    let prim_text: string;
    let sec_text: string;
    let prim_id: string;
    let sec_id: string;




    this.dynamicForm = this.formBuilder.group({

      parta_document_title: ['',  [Validators.required, Validators.maxLength(10000) ]],
      parta_date_of_release: ['1/1/2019', Validators.required],
      parta_primary_theme: ['',  [Validators.required, Validators.maxLength(10000) ]],
      parta_goal: ['',  [Validators.required, Validators.maxLength(10000) ]],
      parta_part: ['PART-A'],
      parta_da_action_point: ['',  [Validators.required, Validators.maxLength(10000) ]],
      partb_document_title: ['',  [Validators.required, Validators.maxLength(10000) ]],
      partb_date_of_release: ['1/1/2019', Validators.required],
      partb_primary_theme: ['',  [Validators.required, Validators.maxLength(10000) ]],
      partb_goal: ['',  [Validators.required, Validators.maxLength(10000) ]],
      partb_part: ['PART-B'],
      partb_da_action_point: ['',  [Validators.required, Validators.maxLength(10000) ]],

      numberOfTickets: ['1'],
      parta_da_action_point_array: new FormArray([]),


      numberOfpartbaction_arr: ['1'],
      partb_da_action_point_array: new FormArray([])
    });

    $(document).ready(function () {


    });
  }

  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get parta_da_action_point_array() { return this.f.parta_da_action_point_array as FormArray; }
  get partb_da_action_point_array() { return this.f.partb_da_action_point_array as FormArray; }

  onClickAddPartAActionArrayRow(val: number) {
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) + val);
    const numberOfTickets = this.f.numberOfTickets.value - 1 || 0;
    if (this.parta_da_action_point_array.length < numberOfTickets) {
      for (let i = this.parta_da_action_point_array.length; i < numberOfTickets; i++) {
        this.parta_da_action_point_array.push(this.formBuilder.group({
          document_name: ['',  [Validators.required, Validators.maxLength(10000) ]]
        }));
      }
    }
  }
  onClickRemovePartAActionArrayRow(e: number) {
    this.parta_da_action_point_array.removeAt(e);
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) - 1);
  }




  onClickAddPartBActionArrayRow(val: number) {
    this.f.numberOfpartbaction_arr.setValue(parseInt(this.f.numberOfpartbaction_arr.value) + val);
    const numberOfpartbaction_arr = this.f.numberOfpartbaction_arr.value - 1 || 0;
    if (this.partb_da_action_point_array.length < numberOfpartbaction_arr) {
      for (let i = this.partb_da_action_point_array.length; i < numberOfpartbaction_arr; i++) {
        this.partb_da_action_point_array.push(this.formBuilder.group({
          document_name: ['',  [Validators.required, Validators.maxLength(10000) ]]
        }));
      }
    }
  }
  onClickRemovePartBActionArrayRow(e: number) {
    this.partb_da_action_point_array.removeAt(e);
    this.f.numberOfpartbaction_arr.setValue(parseInt(this.f.numberOfpartbaction_arr.value) - 1);
  }





  onClickAddRow() {

  }

  onClickRemove() {

  }


  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }


  onSubmit() {
    // Year should be in this format
    // 2016-06-15

    this.submitted = true;

    //alert("Form submitted New");
    // stop here if form is invalid
    if (this.dynamicForm.invalid) {
      alert("Form invalid");
      return;
    }

   
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');
    let login_userid = sessionStorage.getItem('userid');


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    let x: any = this.dynamicForm.value;
    // parta_date_of_release

    x.parta_date_of_release = new_year2 + "-" + new_month2 + "-" + new_date2;


    //alert(x.parta_date_of_release);

    var date3 = new Date("" + this.date_2.value);

    var new_date3 = date3.getDate();
    var new_month3 = (date3.getMonth() + 1);
    var new_year3 = date3.getFullYear();

    // partb_date_of_release


    x.partb_date_of_release = new_year3 + "-" + new_month3 + "-" + new_date3;

    //alert(JSON.stringify(x,null,4));

    //console.log(JSON.stringify(x, null, 4));

    let sstr1 = x.parta_da_action_point;
    let sstr2 = x.partb_da_action_point;

    let arr1 = [];

    arr1[0] = { "document_name": sstr1 };

    let index1 = 1;
    for (let i = 0; i < x.parta_da_action_point_array.length; i++) {
      let obj1 = x.parta_da_action_point_array[i];
      arr1[index1] = obj1;
      index1++;
    }
    x.parta_da_action_point_array = arr1;
    let arr2 = [];

    arr2[0] = { "document_name": sstr2 };

    let index2 = 1;
    for (let j = 0; j < x.partb_da_action_point_array.length; j++) {
      let obj2 = x.partb_da_action_point_array[j];
      arr2[index2] = obj2;
      index2++;
    }
    x.partb_da_action_point_array = arr2;


    //alert(JSON.stringify(x,null,4));

    //console.log(JSON.stringify(x,null,4));

    this.completeClicked = true;

    //alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
    this._diphHttpClientService.savesupplemmentaryform1aDetails(x, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          this.completeClicked = false;
          this.savedform = true;
          //this.router.navigate(['dashboard']);
          this.router.navigate(['dashboard/supplementaryform1aview']);
        },
        error => { this.completeClicked = false; alert("Error= " + error); });
  }


  partialSave(){

    let x: any = this.dynamicForm.value;

    if(x.parta_document_title == null || x.parta_document_title == ''){
      alert("Document title of Part A must be filled");
      return;
    }else{
      if(this.validate100charactersallowed(x.parta_document_title)){
        alert("Document title of Part A can not exceed 100 characters");
        return;
      }
      
    }
    if(x.partb_document_title != null && x.partb_document_title != '' && this.validate100charactersallowed(x.partb_document_title)){
      alert("Document title of Part B can not exceed 100 characters");
      return;
    }

    if(x.parta_goal != null && x.parta_goal != '' && this.validate100charactersallowed(x.parta_goal)){
      alert("Document goal of Part A can not exceed 100 characters");
      return;
    }

    if(x.partb_goal != null && x.partb_goal != '' && this.validate100charactersallowed(x.partb_goal)){
      alert("Document goal of Part B can not exceed 100 characters");
      return;
    }

    if(x.parta_primary_theme != null && x.parta_primary_theme != '' && this.validate100charactersallowed(x.parta_primary_theme)){
      alert("Primary Theme of Part A can not exceed 100 characters");
      return;
    }

    if(x.partb_primary_theme != null && x.partb_primary_theme != '' && this.validate100charactersallowed(x.partb_primary_theme)){
      alert("Secondary Theme of Part B can not exceed 100 characters");
      return;
    }
    
    for(var i=0; i<x.parta_da_action_point_array.length; i++){
      
      if(x.parta_da_action_point_array[i]["document_name"] == null || x.parta_da_action_point_array[i]["document_name"] == ''){
        alert("'Action points specified by the document' in Part A is expanded but not filled");
        return;
      }
    }

    for(var i=0; i<x.partb_da_action_point_array.length; i++){
      
      if(x.partb_da_action_point_array[i]["document_name"] == null || x.partb_da_action_point_array[i]["document_name"] == ''){
        alert("'Action points specified by the document' in Part B is expanded but not filled");
        return;
      }
    }


    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');
    let login_userid = sessionStorage.getItem('userid');


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();
   

    x.parta_date_of_release = new_year2 + "-" + new_month2 + "-" + new_date2;



    var date3 = new Date("" + this.date_2.value);

    var new_date3 = date3.getDate();
    var new_month3 = (date3.getMonth() + 1);
    var new_year3 = date3.getFullYear();

  
    x.partb_date_of_release = new_year3 + "-" + new_month3 + "-" + new_date3;

    
    let sstr1 = x.parta_da_action_point;
    let sstr2 = x.partb_da_action_point;

    let arr1 = [];

    arr1[0] = { "document_name": sstr1 };

    let index1 = 1;
    for (let i = 0; i < x.parta_da_action_point_array.length; i++) {
      let obj1 = x.parta_da_action_point_array[i];
      arr1[index1] = obj1;
      index1++;
    }
    x.parta_da_action_point_array = arr1;
    let arr2 = [];

    arr2[0] = { "document_name": sstr2 };

    let index2 = 1;
    for (let j = 0; j < x.partb_da_action_point_array.length; j++) {
      let obj2 = x.partb_da_action_point_array[j];
      arr2[index2] = obj2;
      index2++;
    }
    x.partb_da_action_point_array = arr2;

    this.completeClicked = true;
    
    this._diphHttpClientService.savesupplemmentaryform1aDetails(x, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {    
          this.completeClicked = false;      
          this.savedform = true;          
          this.router.navigate(['dashboard/supplementaryform1aview']);
        },
        error => { this.completeClicked = false; alert("Error= " + error); });


  }

  validate100charactersallowed(val:string){

    if(val != null && val.length <=10000){      
      return false;
    }
    else if(val != null && val.length > 10000){
      return true;
    }
    else if(val == null || (val!= null && val.trim() == "")){
      return true; 
    }

  } 

}
