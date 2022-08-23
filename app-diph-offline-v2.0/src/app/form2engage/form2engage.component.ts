import { Component, OnInit, Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

import { MatDialogModule } from '@angular/material/dialog';
import {
  trigger,
  state,
  style,
  animate,
  transition
} from '@angular/animations';
import 'hammerjs';
import {
  MatButtonModule,
  MatMenuModule,
  MatToolbarModule,
  MatIconModule,
  MatCardModule,
  MatFormFieldModule,
  MatInputModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatRadioModule,
  MatSelectModule,
  MatOptionModule,
  MatSlideToggleModule,
  MatSliderModule
} from '@angular/material';

@Component({
  selector: 'app-form2engage',
  templateUrl: './form2engage.component.html',
  styleUrls: ['./form2engage.component.css']
})
export class Form2engageComponent implements OnInit {

  completeClicked : boolean = false;

  date = new FormControl(new Date()); 
  date_max= new Date(); 
  minDate =new Date("12,01,2019");
  submitted = false;
  nextbtn = false;
  savedform = false;
  show_second_half_form = false;
  stakeholder_arr = [];
  primary_stakeholder_arr = [
    { id: '1', name: 'order 1' },
    { id: '2', name: 'order 2' },
    { id: '3', name: 'order 3' },
    { id: '4', name: 'order 4' }
  ];
  secondary_stakeholder_arr = [];
  public serverjsonresponse: any;
  public verified_by_name_from_Db= [];

  //form2EngageModel
  dynamicForm: FormGroup;

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
      
    this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {
       
        if ( (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) || 
              (data.date_of_verification != null && data.filled_by != null && data.verified_by_name != null && data.completed == '0') ) {
            alert("Please Fill Form1A to Fill Form1B");
            this.router.navigate(['/dashboard']);
            return false;
          }
      });

    this._diphHttpClientService.getVerified_by_names()
      .subscribe(
        data => {

        if(data['status'] == "1"){
          this.verified_by_name_from_Db = data['verified_by_name'];
        }
        else{
          alert("Error in retrieving \n'Chairperson names from Server'");
        }
        
        },
        error => {
          console.log(error); alert("Error= " + error);
        });


    this.dynamicForm = this.formBuilder.group({

      form_2_date_of_meeting: ['2019-10-03', Validators.required],
      form_2_venue: ['', [Validators.required, Validators.maxLength(5000) ]],
      form_2_filled: ['', Validators.required],
      form_2_filled_others:new FormControl(),
      // primary_stake_label: new FormArray([]),
      primary_stake_label: ['Primary Stakeholder'],
      primary_stakeholder_text: ['', [Validators.required, Validators.maxLength(5000) ]],
      // secondary_stake_label: new FormArray([]),
      secondary_stake_label: ['Secondary Stakeholder'],
      secondary_stakeholder_text: ['', [Validators.required, Validators.maxLength(5000) ]],
      defining_primary_role_section_select: ['', Validators.required],
      defining_primary_role_section_text: ['', [Validators.required]],
      current_effort_to_address_the_issue_section_select: ['', Validators.required],
      current_effort_to_address_the_issue_section_text:['', [Validators.required]],
      how_to_enhance_engagement_and_efficiency_section_select: ['', Validators.required],
      how_to_enhance_engagement_and_efficiency_section_text: ['', [Validators.required]],
      theme_lead_by_department_section_select: ['', Validators.required],
      theme_lead_by_department_section_text: ['', [Validators.required ]], 

      numberOfTickets: ['1'],
      primary_stake_array: new FormArray([]),

      numberOfsecondary_arr: ['1'],
      secondary_stake_array: new FormArray([]),

      numberOfdefiningprimaryrole_arr: ['1'],
      definingprimaryrole_array: new FormArray([]),

      numberOfefforttoaddressissue_arr: ['1'],
      efforttoaddressissue_array: new FormArray([]),

      numberOfenhanceefficiencyrole_arr: ['1'],
      enhanceefficiency_array: new FormArray([]),


      numberOfthemeleadbydptrole_arr: ['1'],
      themeleadbydpt_array: new FormArray([])
    });

    $(document).ready(function () {


    });
  }

  movetonexttab() {

  }


  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get dprs() { return this.f.defining_primary_role_section_select as FormArray; }
  get p_stake_arr() { return this.f.primary_stake_array as FormArray; }
  get s_stake_arr() { return this.f.secondary_stake_array as FormArray; }
  get definingprimaryrole_array() { return this.f.definingprimaryrole_array as FormArray; }
  get efforttoaddressissue_array() { return this.f.efforttoaddressissue_array as FormArray; }
  get enhanceefficiency_array() { return this.f.enhanceefficiency_array as FormArray; }
  get themeleadbydpt_array() { return this.f.themeleadbydpt_array as FormArray; }




  onClickAddRow() {

  }

  onClickRemove() {

  }

  onClickRemovePrimaryRow(e) {
    this.p_stake_arr.removeAt(e);
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) - 1);
  }

  onClickRemoveSecondaryRow(e) {
    this.s_stake_arr.removeAt(e);
    this.f.numberOfsecondary_arr.setValue(parseInt(this.f.numberOfsecondary_arr.value) - 1);
  }

  onClickRemoveDefiningPrimaryRow(e) {
    this.definingprimaryrole_array.removeAt(e);
    this.f.numberOfdefiningprimaryrole_arr.setValue(parseInt(this.f.numberOfdefiningprimaryrole_arr.value) - 1);
  }

  // numberOfefforttoaddressissue_arr:['1'], 
  //     efforttoaddressissue_array:new  FormArray([]),

  //     numberOfenhanceefficiencyrole_arr:['1'],
  //     enhanceefficiency_array:new  FormArray([]),


  //     numberOfthemeleadbydptrole_arr:['1'],
  //     themeleadbydpt_array:new  FormArray([])

  onClickRemoveEfforttoAddressIssueRow(e) {
    this.efforttoaddressissue_array.removeAt(e);
    this.f.numberOfefforttoaddressissue_arr.setValue(parseInt(this.f.numberOfefforttoaddressissue_arr.value) - 1);
  }

  onClickRemoveEnhanceEfficiencyRow(e) {
    this.enhanceefficiency_array.removeAt(e);
    this.f.numberOfenhanceefficiencyrole_arr.setValue(parseInt(this.f.numberOfenhanceefficiencyrole_arr.value) - 1);
  }

  onClickRemoveThemeLeadbyDptRow(e) {
    this.themeleadbydpt_array.removeAt(e);
    this.f.numberOfthemeleadbydptrole_arr.setValue(parseInt(this.f.numberOfthemeleadbydptrole_arr.value) - 1);
  }

  onClickAddPrimaryRow(val) {
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) + val);
    const numberOfTickets = this.f.numberOfTickets.value - 1 || 0;
    if (this.p_stake_arr.length < numberOfTickets) {
      for (let i = this.p_stake_arr.length; i < numberOfTickets; i++) {
        this.p_stake_arr.push(this.formBuilder.group({
          document_name: ['', [Validators.required, Validators.maxLength(5000) ]],
        }));
      }
    }
  }




  onClickAddEnhanceEfficiencyRow(val) {
    this.f.numberOfenhanceefficiencyrole_arr.setValue(parseInt(this.f.numberOfenhanceefficiencyrole_arr.value) + val);
    const numberOfenhanceefficiencyrole_arr = this.f.numberOfenhanceefficiencyrole_arr.value - 1 || 0;
    if (this.enhanceefficiency_array.length < numberOfenhanceefficiencyrole_arr) {
      for (let i = this.enhanceefficiency_array.length; i < numberOfenhanceefficiencyrole_arr; i++) {
        this.enhanceefficiency_array.push(this.formBuilder.group({
          document_select_stakeholder: ['', Validators.required],
          document_desc: ['', [Validators.required ]]
        })); 
      }
    }
  } 



  onClickAddThemeLeadbyDptRow(val) {
    this.f.numberOfthemeleadbydptrole_arr.setValue(parseInt(this.f.numberOfthemeleadbydptrole_arr.value) + val);
    const numberOfthemeleadbydptrole_arr = this.f.numberOfthemeleadbydptrole_arr.value - 1 || 0;
    if (this.themeleadbydpt_array.length < numberOfthemeleadbydptrole_arr) {
      for (let i = this.themeleadbydpt_array.length; i < numberOfthemeleadbydptrole_arr; i++) {
        this.themeleadbydpt_array.push(this.formBuilder.group({
          document_select_stakeholder: ['', Validators.required],
          document_desc: ['', [Validators.required ]]
        }));
      }
    }
  }

  onClickAddSecondaryRow(val) {
    this.f.numberOfsecondary_arr.setValue(parseInt(this.f.numberOfsecondary_arr.value) + val);
    const numberOfsecondary_arr = this.f.numberOfsecondary_arr.value - 1 || 0;
    if (this.s_stake_arr.length < numberOfsecondary_arr) {
      for (let i = this.s_stake_arr.length; i < numberOfsecondary_arr; i++) {
        this.s_stake_arr.push(this.formBuilder.group({
          document_name: ['', [Validators.required, Validators.maxLength(5000) ]],
        }));
      }
    }
  }



  onClickAddDefiningPrimaryRoleRow(val) {
    this.f.numberOfdefiningprimaryrole_arr.setValue(parseInt(this.f.numberOfdefiningprimaryrole_arr.value) + val);
    const numberOfdefiningprimaryrole_arr = this.f.numberOfdefiningprimaryrole_arr.value - 1 || 0;
    if (this.definingprimaryrole_array.length < numberOfdefiningprimaryrole_arr) {
      for (let i = this.definingprimaryrole_array.length; i < numberOfdefiningprimaryrole_arr; i++) {
        this.definingprimaryrole_array.push(this.formBuilder.group({
          document_select_stakeholder: ['', Validators.required],
          document_desc: ['', [Validators.required ]]
        }));
      }
    }
  }





  onClickAddEfforttoAddressIssueRow(val) {
    this.f.numberOfefforttoaddressissue_arr.setValue(parseInt(this.f.numberOfefforttoaddressissue_arr.value) + val);
    const numberOfefforttoaddressissue_arr = this.f.numberOfefforttoaddressissue_arr.value - 1 || 0;
    if (this.efforttoaddressissue_array.length < numberOfefforttoaddressissue_arr) {
      for (let i = this.efforttoaddressissue_array.length; i < numberOfefforttoaddressissue_arr; i++) {
        this.efforttoaddressissue_array.push(this.formBuilder.group({
          document_select_stakeholder: ['', Validators.required],
          document_desc: ['', [Validators.required ]]
        }));
      }
    }
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

     if (this.dynamicForm.invalid) {
      alert("Form invalid");
      return;
    }

    
    // display form values on success
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');


    let x: any = this.dynamicForm.value;

    var stakeholders = [];
    stakeholders.push(x["primary_stakeholder_text"]);
    stakeholders.push(x["secondary_stakeholder_text"]);
    
    for(let i=0; i< x["primary_stake_array"].length; i++){
      stakeholders.push(x["primary_stake_array"][i]["document_name"]);
    }
    for(let i=0; i< x["secondary_stake_array"].length; i++){
      stakeholders.push(x["secondary_stake_array"][i]["document_name"]);
    }

    if(stakeholders.indexOf(x["defining_primary_role_section_select"]) == -1){
      alert("'Defining Primary Role (Department-Wise)' stakeholder selected incorrectly.");
      return;
    }
  

    for (let i=0; i < this.definingprimaryrole_array.length;i++) {
      let tempobj = this.definingprimaryrole_array.value[i];
      let document_val = tempobj["document_select_stakeholder"];
  
        if(stakeholders.indexOf(document_val) == -1){
          alert("'Defining Primary Role (Department-Wise)' stakeholder selected incorrectly.");
          return;
        }
    }

    if(stakeholders.indexOf(x["current_effort_to_address_the_issue_section_select"]) == -1){
      alert("'Current Effort To Address The Issue' stakeholder selected incorrectly.");
      return;
    }
  
  
    for (let i=0; i < this.efforttoaddressissue_array.length;i++) {
      let tempobj = this.efforttoaddressissue_array.value[i];
      let document_val = tempobj["document_select_stakeholder"];
  
      if(stakeholders.indexOf(document_val) == -1){
          alert("'Current Effort To Address The Issue' stakeholder selected incorrectly.");
          return;
        }
    }

    if(stakeholders.indexOf(x["how_to_enhance_engagement_and_efficiency_section_select"]) == -1){
      alert("'How To Enhance Engagement And Efficiency' stakeholder selected incorrectly.");
      return;
    }
  
  
    for (let i=0; i < this.enhanceefficiency_array.length;i++) {
      let tempobj = this.enhanceefficiency_array.value[i];
      let document_val = tempobj["document_select_stakeholder"];
       
      if(stakeholders.indexOf(document_val) == -1){
          alert("'How To Enhance Engagement And Efficiency' stakeholder selected incorrectly.");
          return;
        }
    }

    if(stakeholders.indexOf(x["theme_lead_by_department_section_select"]) == -1){
      alert("'Theme Lead By Department' stakeholder selected incorrectly.");
      return;
    }
  
  
    for (let i=0; i < this.themeleadbydpt_array.length;i++) {
      let tempobj = this.themeleadbydpt_array.value[i];
      let document_val = tempobj["document_select_stakeholder"];
        
      if(stakeholders.indexOf(document_val) == -1){
          alert("'Theme Lead By Department' stakeholder selected incorrectly.");
          return;
        }
    }

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.form_2_date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    this.completeClicked = true;

    // alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
    this._diphHttpClientService.saveform2engageDetails(x, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          this.completeClicked = false;
          
          this.savedform = true;
          this.router.navigate(['dashboard/form2engageview']);
        },
        error => { this.completeClicked = false; alert("Error= " + error); });
  }

  partialSave(){

    
	if(this.f.form_2_venue.value == '' || this.f.form_2_venue.value == null){
		alert("Venue of the meeting is compulsary");
		return;
	}
	
	if(this.f.form_2_filled.value != "15" && (this.f.form_2_filled.value == null || this.f.form_2_filled.value == '')){ 
     alert("Chairperson of the meeting is compulsary");
     return;
  }
  if(this.f.form_2_filled.value == "15" && (this.f.form_2_filled_others.value == "null" || this.f.form_2_filled_others.value == "")){ 
    alert("Chairperson of the meeting is compulsary");
    return;
  }
  if(this.f.form_2_filled.value == "15" && this.f.form_2_filled_others.value == null){ 
    alert("Chairperson of the meeting is compulsary");
    return;
  }  
  
  for (let i=0; i < this.definingprimaryrole_array.length;i++) {
    let tempobj = this.definingprimaryrole_array.value[i];
    let document_val = tempobj["document_select_stakeholder"];
    let document_desc = tempobj["document_desc"];

      if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
        alert("'Defining Primary Role (Department-Wise)' expanded but not filled");
        return;
      }
  }

  for (let i=0; i < this.efforttoaddressissue_array.length;i++) {
    let tempobj = this.efforttoaddressissue_array.value[i];
    let document_val = tempobj["document_select_stakeholder"];
    let document_desc = tempobj["document_desc"];

    if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
        alert("'Current Effort To Address The Issue' expanded but not filled");
        return;
      }
  }

  for (let i=0; i < this.enhanceefficiency_array.length;i++) {
    let tempobj = this.enhanceefficiency_array.value[i];
    let document_val = tempobj["document_select_stakeholder"];
    let document_desc = tempobj["document_desc"];

      if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
        alert("'How To Enhance Engagement And Efficiency' expanded but not filled");
        return;
      }
  }

  for (let i=0; i < this.themeleadbydpt_array.length;i++) {
    let tempobj = this.themeleadbydpt_array.value[i];
    let document_val = tempobj["document_select_stakeholder"];
    let document_desc = tempobj["document_desc"];

    if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
        alert("'Theme Lead By Department' expanded but not filled");
        return;
      }
  }
  

    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');
    let login_userid = sessionStorage.getItem('userid');


    let x: any = this.dynamicForm.value;

    var stakeholders = [];
    stakeholders.push(x["primary_stakeholder_text"]);
    stakeholders.push(x["secondary_stakeholder_text"]);
    
    for(let i=0; i< x["primary_stake_array"].length; i++){
      stakeholders.push(x["primary_stake_array"][i]["document_name"]);
    }
    for(let i=0; i< x["secondary_stake_array"].length; i++){
      stakeholders.push(x["secondary_stake_array"][i]["document_name"]);
    }

    if(x["defining_primary_role_section_select"] != null && x["defining_primary_role_section_select"] != ''){
      if(stakeholders.indexOf(x["defining_primary_role_section_select"]) == -1){
        alert("'Defining Primary Role (Department-Wise)' stakeholder selected incorrectly.");
        return;
      }
    }
  
      for (let i=0; i < this.definingprimaryrole_array.length;i++) {
        let tempobj = this.definingprimaryrole_array.value[i];
        let document_val = tempobj["document_select_stakeholder"];
    
          if(document_val !=null && document_val != '' && stakeholders.indexOf(document_val) == -1){
            alert("'Defining Primary Role (Department-Wise)' stakeholder selected incorrectly.");
            return;
          }
      }
  
      if(x["current_effort_to_address_the_issue_section_select"] != null && x["current_effort_to_address_the_issue_section_select"] != ''){
      if(stakeholders.indexOf(x["current_effort_to_address_the_issue_section_select"]) == -1){
        alert("'Current Effort To Address The Issue' stakeholder selected incorrectly.");
        return;
      }
    }
    
      for (let i=0; i < this.efforttoaddressissue_array.length;i++) {
        let tempobj = this.efforttoaddressissue_array.value[i];
        let document_val = tempobj["document_select_stakeholder"];
    
        if(document_val !=null && document_val != '' && stakeholders.indexOf(document_val) == -1){
            alert("'Current Effort To Address The Issue' stakeholder selected incorrectly.");
            return;
          }
      }
  
      if(x["how_to_enhance_engagement_and_efficiency_section_select"] != null && x["how_to_enhance_engagement_and_efficiency_section_select"] != ''){
      if(stakeholders.indexOf(x["how_to_enhance_engagement_and_efficiency_section_select"]) == -1){
        alert("'How To Enhance Engagement And Efficiency' stakeholder selected incorrectly.");
        return;
      }
    }
    
      for (let i=0; i < this.enhanceefficiency_array.length;i++) {
        let tempobj = this.enhanceefficiency_array.value[i];
        let document_val = tempobj["document_select_stakeholder"];
         
        if(document_val !=null && document_val != '' && stakeholders.indexOf(document_val) == -1){
            alert("'How To Enhance Engagement And Efficiency' stakeholder selected incorrectly.");
            return;
          }
      }
  
      if(x["theme_lead_by_department_section_select"] != null && x["theme_lead_by_department_section_select"] != ''){
      if(stakeholders.indexOf(x["theme_lead_by_department_section_select"]) == -1){
        alert("'Theme Lead By Department' stakeholder selected incorrectly.");
        return;
      }
    }
    
      for (let i=0; i < this.themeleadbydpt_array.length;i++) {
        let tempobj = this.themeleadbydpt_array.value[i];
        let document_val = tempobj["document_select_stakeholder"];
          
        if(document_val !=null && document_val != '' && stakeholders.indexOf(document_val) == -1){
            alert("'Theme Lead By Department' stakeholder selected incorrectly.");
            return;
          }
      }
    
    if(this.f.defining_primary_role_section_text.value == '' || this.f.defining_primary_role_section_text.value == null){
      x.defining_primary_role_section_text = "No description";
    }
    if(this.f.current_effort_to_address_the_issue_section_text.value == '' || this.f.current_effort_to_address_the_issue_section_text.value == null){
      x.current_effort_to_address_the_issue_section_text = "No description";
    }
    if(this.f.how_to_enhance_engagement_and_efficiency_section_text.value == '' || this.f.how_to_enhance_engagement_and_efficiency_section_text.value == null){
      x.how_to_enhance_engagement_and_efficiency_section_text = "No description";
    }
    if(this.f.theme_lead_by_department_section_text.value == '' || this.f.theme_lead_by_department_section_text.value == null){
      x.theme_lead_by_department_section_text = "No description";
    }

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.form_2_date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    this.completeClicked = true;

    this._diphHttpClientService.saveform2engageDetails(x, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {
         // console.log(data);
         this.completeClicked = false;
          this.savedform = true;
          this.router.navigate(['dashboard/form2engageview']);
        },
        error => { this.completeClicked = false; alert("Error= " + error); });
  }

  /*
  validateSpecialCharsAndNum(val:string){    
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
  return  (!!(val).replace(/[A-Za-z\s]/gi, '').length);
  }
*/

  allowAlphabetsOnly(event) {
    // alert(event.which);
    if ((event.which > 64 && event.which < 91) || (event.which > 96 && event.which < 123)  || (event.which == 32)  || (event.which == 0))
      return true;
    else
      return false;
  }

  findvalue(event) {
    // alert(event.which);
    if (event.which > 31 && (event.which < 48 || event.which > 57)) {
      event.stopPropagation();
      return false;
    }
    return true;
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


  //After clicking NExt btton
  show_part2_also() {
   
    this.nextbtn = true;

    let finalresult = true;

    if(this.f.form_2_filled.value==''){
      finalresult = false;
    }
    else if(this.f.form_2_venue.value==''){
      finalresult = false;
    }
    else if( this.validate100charactersallowed(this.f.form_2_venue.value)){
      finalresult = false;
    }
    else if(this.f.primary_stakeholder_text.value==''){
      finalresult = false;
    }    
    else if(this.f.secondary_stakeholder_text.value==''){
      finalresult = false;
    }
    else if(this.validate100charactersallowed(this.f.primary_stakeholder_text.value)){
      finalresult = false;
    }
    else if( this.validate100charactersallowed(this.f.secondary_stakeholder_text.value)){
      finalresult = false;
    }

    try {
      for(let j=0;j<this.p_stake_arr.length;j++){

        if(this.p_stake_arr.controls[j].get('document_name').value == ''){
          finalresult = false;
        }
        else if( this.validate100charactersallowed(this.p_stake_arr.controls[j].get('document_name').value)){
          finalresult = false;
        }
      }

    } catch (error) {
      
    }

    try {
      for(let j=0;j<this.s_stake_arr.length;j++){

        if(this.s_stake_arr.controls[j].get('document_name').value == ''){
          finalresult = false;
        }
        else if( this.validate100charactersallowed(this.s_stake_arr.controls[j].get('document_name').value)){
          finalresult = false;
        }
      }

    } catch (error) {
      
    }

    

    if(this.f.form_2_filled.value =='15' &&  this.f.form_2_filled_others.value == ''){
      finalresult = false;
    }

    if(this.f.form_2_filled.value =='15' &&  this.f.form_2_filled_others.value == 'null'){
      finalresult = false;
    }

    if(this.f.form_2_filled.value =='15' &&  this.f.form_2_filled_others.value == null){
      finalresult = false;
    }
   
    

    if(!finalresult){
      alert("Form invalid");
      return;
    }
    
    if(this.f.primary_stakeholder_text.value==this.f.secondary_stakeholder_text.value){      
      alert("Please enter only unique Stakeholder names!");  
      return;
    } 

    try {
      for(let j=0;j<this.p_stake_arr.length;j++){

        if(this.p_stake_arr.controls[j].get('document_name').value == this.f.primary_stakeholder_text.value){
          alert("Please enter only unique Stakeholder names!");  
          return;
        }
        else if(this.p_stake_arr.controls[j].get('document_name').value == this.f.secondary_stakeholder_text.value){
          alert("Please enter only unique Stakeholder names!");  
          return;
        }
      }

    } catch (error) {
      
    }


    try {
      for(let j=0;j<this.s_stake_arr.length;j++){ 

        if(this.s_stake_arr.controls[j].get('document_name').value == this.f.primary_stakeholder_text.value){
          alert("Please enter only unique Stakeholder names!");  
          return;
        }
        else if(this.s_stake_arr.controls[j].get('document_name').value == this.f.secondary_stakeholder_text.value){
          alert("Please enter only unique Stakeholder names!");  
          return;
        }
      }

    } catch (error) {
      
    }


    try {
      for(let j=0;j<this.p_stake_arr.length;j++){

        for(let k=0;k<this.s_stake_arr.length;k++){
          if(this.p_stake_arr.controls[j].get('document_name').value == this.s_stake_arr.controls[k].get('document_name').value){
            alert("Please enter only unique Stakeholder names!");  
            return;
          }
        }
      }

    } catch (error) {
      
    }

  /*
    if(this.show_second_half_form == true){

      return;
    }
    */
    
    let x = document.getElementById("p_holder")["value"];
    let y = document.getElementById("s_holder")["value"];

    this.stakeholder_arr = [];
    this.stakeholder_arr.push({ id: "" + (1 + this.stakeholder_arr.length), name: "" + x });

    for (let i = 0; i < this.p_stake_arr.length; i++) {
      let str1 = this.p_stake_arr.value[i].document_name;
      this.stakeholder_arr.push({ id: "" + (1 + this.stakeholder_arr.length), name: "" + str1 });
    }

    this.stakeholder_arr.push({ id: "" + (1 + this.stakeholder_arr.length), name: "" + y });

    for (let j = 0; j < this.s_stake_arr.length; j++) {
      let str2 = this.s_stake_arr.value[j].document_name;
      this.stakeholder_arr.push({ id: "" + (1 + this.stakeholder_arr.length), name: "" + str2 });
    }   

    /*
    $(".prim_stakes").each(function(){
      $(this).attr("disabled","true"); 
    });
    */

    this.show_second_half_form = true;
  }

}
