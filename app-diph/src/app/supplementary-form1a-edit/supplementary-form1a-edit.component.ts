import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { form1bEdit } from '../model/form1bEdit';
import { PlatformLocation,DatePipe  } from '@angular/common';
import { MatDialog } from '@angular/material';
import { Router } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';

@Component({
  selector: 'app-supplementary-form1a-edit',
  templateUrl: './supplementary-form1a-edit.component.html',
  styleUrls: ['./supplementary-form1a-edit.component.css']
})
export class SupplementaryForm1aEditComponent implements OnInit {

  completeClicked : boolean = false;
  
  @Input() customer: any;
  public jsonresponse: any;
  loading = true;
  resp: any;
  all_areas_indicators_arr = [];
  submitted = false;
  minDate =new Date("12,01,2019");

  total_coverage_indi = [];
  supplementryCompleted = false;


  user: Observable<any>;

  stakeholder_arr = [];
  primary_stakeholder_arr = [
    { id: '1', name: 'order 1' },
    { id: '2', name: 'order 2' },
    { id: '3', name: 'order 3' },
    { id: '4', name: 'order 4' }
  ];
  secondary_stakeholder_arr = [];

  action_required:any ={};
  
  constructor(location: PlatformLocation, public dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
    history.pushState(null, null, location.href);
    location.onPopState(() => {
    history.pushState(null, null, location.href);
    return false;
  });
   }

  tempvar: string;

  ngOnInit() {

    setTimeout(() => {
      console.log("Hello from setTimeout");


      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.getSavedSupplementaryForm1ADetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {

            if(data.completed == '1')
                this.supplementryCompleted = true;
            else if(data.completed == '0')
                 this.supplementryCompleted = false;

            if (data.parta_document_title == null && data.parta_date_of_release == null && data.parta_goal == null) {
              alert("No data set for chosen District, Cycle and Year");
              console.log("No data set for chosen District, Cycle and Year, Redirecting to dashboard");
              this.router.navigate(['dashboard']);
            }
            else {

              // this.customer = new form1bEdit();
              // this.customer.dist_demogra_dtl_id = data.dist_demogra_dtl_id;

              this.jsonresponse = data;
              //console.log(this.jsonresponse);

              this.customer ={};
              this.customer = data;
            // this.customer.filled_by="Abhishk";
            // this.customer.parta_document_title=data.parta_document_title;
            // this.customer.parta_date_of_release=data.parta_date_of_release;
            // this.customer.parta_primary_theme=data.parta_primary_theme;
            // this.customer.parta_goal=data.parta_goal;
            // this.customer.parta_part=data.parta_part;
            // this.customer.parta_da_action_point=data.parta_da_action_point;
            // this.customer.partb_document_title=data.partb_document_title;
            // this.customer.partb_date_of_release=data.partb_date_of_release;
            // this.customer.partb_primary_theme=data.partb_primary_theme;
            // this.customer.partb_goal=data.partb_goal;
            // this.customer.partb_part=data.partb_part;
            // this.customer.partb_da_action_point=data.partb_da_action_point;
            this.date.setValue(""+this.customer.parta_date_of_release);
            this.date_2.setValue(""+this.customer.partb_date_of_release);

            }

          },
          error => {
            console.log(error); alert("Error in fetching data from Server= " + error);
          });


    }, 2000);




  }


  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }


  onClickAddRow() {

  }

  onClickRemove() {

  }

  onClickAddPartAActionArrayRow(){
    this.customer.parta_da_action_point_array.push({"document_name":"","insert":"1"});
  }

  onClickRemovePartAActionArrayRow(i:number){
    this.customer.parta_da_action_point_array.splice(i,1);
  }


  onClickAddPartBActionArrayRow(){
    this.customer.partb_da_action_point_array.push({"document_name":"","insert":"1"});
  }

  onClickRemovePartBActionArrayRow(i:number){
    this.customer.partb_da_action_point_array.splice(i,1);
  }

  date = new FormControl('2019-08-22T23:00:00');
  date_2 = new FormControl('2019-08-22T23:00:00');


  validateSpecialCharsAndNum(val:string){    
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
  return  (!!(val).replace(/[A-Za-z\s]/gi, '').length);
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

  onSubmit() {
    // Year should be in this format
    // 2016-06-15

    let finalresult = true;

    this.submitted = true;


    if(this.customer.parta_document_title==''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.parta_document_title)  ){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.parta_document_title) ){
      finalresult = false;
    }
    else if(this.customer.parta_goal==''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.parta_goal)){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.parta_goal)){
      finalresult = false;
    }
    else if(this.customer.parta_primary_theme==''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.parta_primary_theme)){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.parta_primary_theme)){
      finalresult = false;
    }
    

    else if(this.customer.partb_document_title==''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.partb_document_title)){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.partb_document_title)){
      finalresult = false;
    }
    else if(this.customer.partb_goal==''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.partb_goal)){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.partb_goal)){
      finalresult = false;
    }
    else if(this.customer.partb_primary_theme==''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.partb_primary_theme)){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.partb_primary_theme)){
      finalresult = false;
    }


    for(let j=0;j<this.customer.partb_da_action_point_array.length;j++){

      let tempobj = this.customer.partb_da_action_point_array[j];

      if(tempobj['document_name'] == '' ){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_name'])    ){
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed(tempobj['document_name'])  ){
        finalresult = false;
      }
    }

    for(let j=0;j<this.customer.parta_da_action_point_array.length;j++){

      let tempobj = this.customer.parta_da_action_point_array[j];

      if(tempobj['document_name'] == '' ){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_name'])    ){
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed(tempobj['document_name'])  ){
        finalresult = false;
      }
    }

    
    // stop here if form is invalid
    if (!finalresult) {
      alert("Form invalid");
      return;
    }

    //alert(this.date.value);
    var date2 = new Date("" + this.date.value);
    var date3 = new Date("" + this.date_2.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    var new_date3 = date3.getDate();
    var new_month3 = (date3.getMonth() + 1);
    var new_year3 = date3.getFullYear();

    this.customer.parta_date_of_release = new_year2 + "-" + new_month2 + "-" + new_date2;
    this.customer.partb_date_of_release = new_year3 + "-" + new_month3 + "-" + new_date3;

    //this.datePipe.transform(this.date.value, 'yyyy-dd-MM');
    //alert(this.customer.date_of_verification);

    alert("Form submitted");
    this.loading = true;
    //console.log(this.customer);
    // display form values on success
    // alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    this.completeClicked = true;

    //alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
    this._diphHttpClientService.editUpdateSupplementaryForm1ADetails(this.customer, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          this.completeClicked = false;
          
        //   this.savedform = true; 
        //  this.router.navigate(['dashboard']);
          this.router.navigate(['dashboard/supplementaryform1aview']);
        },
        error => { 
	this.completeClicked = false;
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
         });
  }


  partialSave(){

    

    if(this.customer.parta_document_title==''){
      alert("Document title of Part A must be filled");
      return;
    }
    else if(this.validate100charactersallowed(this.customer.parta_document_title) ){
      alert("Document title of Part A can not exceed 10000 characters");
      return;
    }
	
	if(this.customer.partb_document_title != null && this.customer.partb_document_title != '' && this.validate100charactersallowed(this.customer.partb_document_title)){
      alert("Document title of Part B can not exceed 10000 characters");
      return;
    }

    if(this.customer.parta_goal != null && this.customer.parta_goal != '' && this.validate100charactersallowed(this.customer.parta_goal)){
      alert("Document goal of Part A can not exceed 10000 characters");
      return;
    }

    if(this.customer.partb_goal != null && this.customer.partb_goal != '' && this.validate100charactersallowed(this.customer.partb_goal)){
      alert("Document goal of Part B can not exceed 10000 characters");
      return;
    }

    if(this.customer.parta_primary_theme != null && this.customer.parta_primary_theme != '' && this.validate100charactersallowed(this.customer.parta_primary_theme)){
      alert("Primary Theme of Part A can not exceed 10000 characters");
      return;
    }

    if(this.customer.partb_primary_theme != null && this.customer.partb_primary_theme != '' && this.validate100charactersallowed(this.customer.partb_primary_theme)){
      alert("Secondary Theme of Part B can not exceed 10000 characters");
      return;
    }
    
   for(var i=1; i<this.customer.parta_da_action_point_array.length; i++){
      
      if(this.customer.parta_da_action_point_array[i]["document_name"] == null || this.customer.parta_da_action_point_array[i]["document_name"] == ''){
        alert("'Action points specified by the document' in Part A is expanded but not filled");
        return;
      }
    }

    for(var i=1; i<this.customer.partb_da_action_point_array.length; i++){
      
      if(this.customer.partb_da_action_point_array[i]["document_name"] == null || this.customer.partb_da_action_point_array[i]["document_name"] == ''){
        alert("'Action points specified by the document' in Part B is expanded but not filled");
        return;
      }
    }
	
	
    var date2 = new Date("" + this.date.value);
    var date3 = new Date("" + this.date_2.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    var new_date3 = date3.getDate();
    var new_month3 = (date3.getMonth() + 1);
    var new_year3 = date3.getFullYear();

    this.customer.parta_date_of_release = new_year2 + "-" + new_month2 + "-" + new_date2;
    this.customer.partb_date_of_release = new_year3 + "-" + new_month3 + "-" + new_date3;

    this.loading = true;
    
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');
    let login_userid = sessionStorage.getItem('userid');

    this.completeClicked = true;
   
    this._diphHttpClientService.editUpdateSupplementaryForm1ADetails(this.customer, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {
          this.completeClicked = false;
          this.router.navigate(['dashboard/supplementaryform1aview']);
        },
        error => { 
	this.completeClicked = false;
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
         });

  }

}
