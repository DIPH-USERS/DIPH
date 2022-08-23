import { Component, OnInit, Injectable, Input  } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form3-define-edit',
  templateUrl: './form3-define-edit.component.html',
  styleUrls: ['./form3-define-edit.component.css']
})
export class Form3DefineEditComponent implements OnInit {

  completeClicked : boolean = false;

  service_data_not_available = false;
  workforce_data_not_available = false;
  supply_data_not_available = false;
  health_data_not_available = false;
  finance_data_not_available = false;
  policy_data_not_available = false;

  @Input() customer: any;
  date = new FormControl(new Date()); 
  date_max= new Date(); 
  submitted = false;
  nextbtn = false;
  savedform = false;
  public serverjsonresponse: any;
  stakeholder_arr = [];
  form_plan_not_filled = false;
  primary_stakeholder_arr = [
    { id: '1', name: 'order 1' },
    { id: '2', name: 'order 2' },
    { id: '3', name: 'order 3' },
    { id: '4', name: 'order 4' }
  ];
  secondary_stakeholder_arr = [];
  loading = true;
  public verified_by_name_from_Db=[];
  minDate =new Date("12,01,2019");
  public jsonresponse: any;

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

    

   setTimeout(() => {
    console.log("Hello from setTimeout");
    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');



    this._diphHttpClientService.getInEditForm3DefineDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          

          //console.log("data : "+JSON.stringify(data));
          this.customer = data;

          if(data.completed == '1')
            this.customer.completed = true;
              else if(data.completed == '0')
            this.customer.completed = false;

          this.customer.service_array_insert=[];
          this.customer.workforce_array_insert=[];
          this.customer.supplies_array_insert=[];
          this.customer.health_array_insert=[];
          this.customer.finance_array_insert=[];
          this.customer.policy_array_insert=[];

          this.date.setValue(data.form_3_checkdate);
          this.loading = false;
        },
        error => {
          console.log(error); alert("Error= " + error);
        });


        this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            this.jsonresponse = data;
          },
          error => {
            console.log(error); alert("Error in fetching data from Server= " + error);
          });



          this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
          .subscribe(
        data => {

          if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chariperson_of_meeting == null)             
            this.form_plan_not_filled = true;
             else             
            this.form_plan_not_filled = false;
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });


  }, 3000);
  }

  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get dprs() { return this.f.defining_primary_role_section_select as FormArray; }


  onClickAddRow() {

  }

  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }

  

  onclickAddserviceActionRow(i){
    this.customer.service_array_insert[i].document_action_required.push("");
  }

  onclickAddworkforceActionRow(i){
    this.customer.workforce_array_insert[i].document_action_required.push("");
  }

  onclickAddsuppliesActionRow(i){
    this.customer.supplies_array_insert[i].document_action_required.push("");
  }

  onclickAddhealthActionRow(i){
    this.customer.health_array_insert[i].document_action_required.push("");
  }

  onclickAddfinanceActionRow(i){
    this.customer.finance_array_insert[i].document_action_required.push("");
  }

  onclickAddpolicyActionRow(i){
    this.customer.policy_array_insert[i].document_action_required.push("");
  }

  onclickRemoveserviceActionRow(i,j){
    this.customer.service_array_insert[i].document_action_required.splice(j,1);
  }

  onclickRemoveworkforceActionRow(i,j){
    this.customer.workforce_array_insert[i].document_action_required.splice(j,1);
  }

  onclickRemovesuppliesActionRow(i,j){
    this.customer.supplies_array_insert[i].document_action_required.splice(j,1);
  }

  onclickRemovehealthActionRow(i,j){
    this.customer.health_array_insert[i].document_action_required.splice(j,1);
  }

  onclickRemovefinanceActionRow(i,j){
    this.customer.finance_array_insert[i].document_action_required.splice(j,1);
  }

  onclickRemovepolicyActionRow(i,j){
    this.customer.policy_array_insert[i].document_action_required.splice(j,1);
  }

 

  onclickAddFirstServiceActionRow(i,j){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.service_array[i].document_action_required.push("");
    this.customer.service_array[i].action_req_id.push("");
    this.customer.service_array[i].use_in_ui=[];
    for(let x=0;x<this.customer.service_array[i].document_action_required.length;x++){
      this.customer.service_array[i].use_in_ui.push("");
    }
  }

  onclickRemoveFirstServiceActionRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.service_array[i].document_action_required.splice(e,1);
    this.customer.service_array[i].action_req_id.splice(e,1);
  }

  // Mohsin New Changes starts
  onClickRemoveServiceArrayRow(i){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.service_array.splice(i,1)
  }
  onclickRemoveServiceActionRequiredRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.service_array[i].document_action_required.splice(e,1);
    this.customer.service_array[i].action_req_id.splice(e,1);
  }


  onClickRemoveWorkforceArrayRow(i){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.workforce_array.splice(i,1)
  }
  onclickRemoveWorkforceActionRequiredRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.workforce_array[i].document_action_required.splice(e,1);
    this.customer.workforce_array[i].action_req_id.splice(e,1);
  }


  onClickRemoveSupplyArrayRow(i){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.supplies_array.splice(i,1)
  }
  onclickRemoveSupplyActionRequiredRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.supplies_array[i].document_action_required.splice(e,1);
    this.customer.supplies_array[i].action_req_id.splice(e,1);
  }


  onClickRemoveHealthArrayRow(i){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.health_array.splice(i,1)
  }
  onclickRemoveHealthActionRequiredRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.health_array[i].document_action_required.splice(e,1);
    this.customer.health_array[i].action_req_id.splice(e,1);
  }

  onClickRemoveFinanceArrayRow(i){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.finance_array.splice(i,1)
  }
  onclickRemoveFinanceActionRequiredRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.finance_array[i].document_action_required.splice(e,1);
    this.customer.finance_array[i].action_req_id.splice(e,1);
  }

  onClickRemovePolicyArrayRow(i){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.policy_array.splice(i,1)
  }
  onclickRemovePolicyActionRequiredRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.policy_array[i].document_action_required.splice(e,1);
    this.customer.policy_array[i].action_req_id.splice(e,1);
  }
  // Mohsin New Changes ends

  onclickAddFirstWorkforceActionRow(i,j){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.workforce_array[i].document_action_required.push("");
    this.customer.workforce_array[i].action_req_id.push("");
    this.customer.workforce_array[i].use_in_ui=[];
    for(let x=0;x<this.customer.workforce_array[i].document_action_required.length;x++){
      this.customer.workforce_array[i].use_in_ui.push("");
    }
  }

  
  onclickRemoveFirstWorkforceActionRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.workforce_array[i].document_action_required.splice(e,1);
    this.customer.workforce_array[i].action_req_id.splice(e,1);
  }

  onclickAddFirstSuppliesActionRow(i,j){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.supplies_array[i].document_action_required.push("");
    this.customer.supplies_array[i].action_req_id.push("");
    this.customer.supplies_array[i].use_in_ui=[];
    for(let x=0;x<this.customer.supplies_array[i].document_action_required.length;x++){
      this.customer.supplies_array[i].use_in_ui.push("");
    }
  }

  
  onclickRemoveFirstSuppliesActionRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.supplies_array[i].document_action_required.splice(e,1);
    this.customer.supplies_array[i].action_req_id.splice(e,1);
  }

  onclickAddFirstHealthActionRow(i,j){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.health_array[i].document_action_required.push("");
    this.customer.health_array[i].action_req_id.push("");
    this.customer.health_array[i].use_in_ui=[];
    for(let x=0;x<this.customer.health_array[i].document_action_required.length;x++){
      this.customer.health_array[i].use_in_ui.push("");
    }
  }

  
  onclickRemoveFirstHealthActionRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }
    
    this.customer.health_array[i].document_action_required.splice(e,1);
    this.customer.health_array[i].action_req_id.splice(e,1);
  }

  onclickAddFirstFinanceActionRow(i,j){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.finance_array[i].document_action_required.push("");
    this.customer.finance_array[i].action_req_id.push("");
    this.customer.finance_array[i].use_in_ui=[];
    for(let x=0;x<this.customer.finance_array[i].document_action_required.length;x++){
      this.customer.finance_array[i].use_in_ui.push("");
    }
  }

  
  onclickRemoveFirstFinanceActionRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.finance_array[i].document_action_required.splice(e,1);
    this.customer.finance_array[i].action_req_id.splice(e,1);
  }


  onclickAddFirstPolicyActionRow(i,j){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.policy_array[i].document_action_required.push("");
    this.customer.policy_array[i].action_req_id.push("");
    this.customer.policy_array[i].use_in_ui=[];
    for(let x=0;x<this.customer.policy_array[i].document_action_required.length;x++){
      this.customer.policy_array[i].use_in_ui.push("");
    }

  }

  
  onclickRemoveFirstPolicyActionRow(i,e){

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.policy_array[i].document_action_required.splice(e,1);
    this.customer.policy_array[i].action_req_id.splice(e,1);
  }

  onClickAddServiceRow() {

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.service_array_insert.push({
      "document_description_p_f_h_s_p": "",
      "document_possible_s_t_i": "",
      "document_action_required": [""] 
    });
  }

  onClickAddWorkforceRow() {

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.workforce_array_insert.push({
      "document_description_p_f_h_s_p": "",
      "document_possible_s_t_i": "",
      "document_action_required": [""]
    });
  }

  onClickAddSuppliesRow() {

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.supplies_array_insert.push({
      "document_description_p_f_h_s_p": "",
      "document_possible_s_t_i": "",
      "document_action_required": [""]
    });
  }

  onClickAddHealthRow() {

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.health_array_insert.push({
      "document_description_p_f_h_s_p": "",
      "document_possible_s_t_i": "",
      "document_action_required": [""]
    });
  }

  onClickAddFinanceRow() {

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.finance_array_insert.push({
      "document_description_p_f_h_s_p": "",
      "document_possible_s_t_i": "",
      "document_action_required": [""]
    });
  }

  onClickAddPolicyRow() {

    if(!this.form_plan_not_filled){
      alert("Form 'Plan' is filled, only text editing is allowed now.");
      return;
    }

    this.customer.policy_array_insert.push({
      "document_description_p_f_h_s_p": "",
      "document_possible_s_t_i": "",
      "document_action_required": [""]
    });
  }
 

  onClickRemoveServiceRow(e) {
    this.customer.service_array_insert.splice(e,1);
  }

  onClickRemoveWorkforceRow(e) {
    this.customer.workforce_array_insert.splice(e,1);
  }

  onClickRemoveSuppliesRow(e) {
    this.customer.supplies_array_insert.splice(e,1);
  }

  onClickRemoveHealthRow(e) {
    this.customer.health_array_insert.splice(e,1);
  }

  onClickRemoveFinanceRow(e) {
    this.customer.finance_array_insert.splice(e,1);
  }

  onClickRemovePolicyRow(e) {
    this.customer.policy_array_insert.splice(e,1);
  }

  validateSpecialCharsAndNum(val:string){    
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
  return  (!!(val).replace(/[A-Za-z\s]/gi, '').length);
  }

  validate100charactersallowed(val:string){

    if(val != null && val.length <=100){      
      return false;
    }
    else if(val != null && val.length > 100){
      return true;
    }
    else if(val == null || (val!= null && val.trim() == "")){
      return true; 
    }

  } 


  onSubmit_old() {

    this.submitted = true;
    let finalresult = true;

    if(! this.form_plan_not_filled){
      alert("Form Define data is used in next form. Data can not be edited.");
      window.location.reload();
      return;
    }

    if(this.customer.form_3_meeting_venue == ''){
      finalresult = false;
    } /*   
    else if(this.validateSpecialCharsAndNum(this.customer.form_3_meeting_venue)){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.form_3_meeting_venue)){
      finalresult = false;
    } 
    else if(this.customer.form_3_filled_by == ''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.form_3_filled_by)){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.form_3_filled_by)){
      finalresult = false;
    }
    else if(this.customer.form3_chair_person == ''){
      finalresult = false;
    }

    if(this.customer.form3_chair_person == '15'  &&  this.customer.form3_chair_person_others == null){
      finalresult = false;
    }

    if(this.customer.form3_chair_person == '15'  &&  this.customer.form3_chair_person_others  == ''){
      finalresult = false;
    }

    if(this.customer.form3_chair_person == '15'  &&  this.customer.form3_chair_person_others  == 'null'){
      finalresult = false;
    }


    for(let j=0;j<this.customer.service_array.length;j++){
      let tempobj = this.customer.service_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_description_p_f_h_s_p']) ){
        finalresult = false;
      }*/     
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }
      else if(this.validate100charactersallowed(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }*/
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }/*
          else if(this.validateSpecialCharsAndNum(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }
          else if(this.validate100charactersallowed(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }*/
        }
      }
    }

    for(let j=0;j<this.customer.service_array_insert.length;j++){
      let tempobj = this.customer.service_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }





    for(let j=0;j<this.customer.workforce_array.length;j++){
      let tempobj = this.customer.workforce_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_description_p_f_h_s_p']) ){
        finalresult = false;
      }*/      
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }
      else if(this.validate100charactersallowed(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }*/
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }/*
          else if(this.validateSpecialCharsAndNum(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }
          else if(this.validate100charactersallowed(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }*/
        }
      }
    }

    for(let j=0;j<this.customer.workforce_array_insert.length;j++){
      let tempobj = this.customer.workforce_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }




    for(let j=0;j<this.customer.supplies_array.length;j++){
      let tempobj = this.customer.supplies_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_description_p_f_h_s_p']) ){
        finalresult = false;
      }*/      
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }
      else if(this.validate100charactersallowed(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }*/
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }/*
          else if(this.validateSpecialCharsAndNum(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }
          else if(this.validate100charactersallowed(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }*/
        }
      }
    }

    for(let j=0;j<this.customer.supplies_array_insert.length;j++){
      let tempobj = this.customer.supplies_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }






    for(let j=0;j<this.customer.health_array.length;j++){
      let tempobj = this.customer.health_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_description_p_f_h_s_p']) ){
        finalresult = false;
      }*/     
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }
      else if(this.validate100charactersallowed(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }*/
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }/*
          else if(this.validateSpecialCharsAndNum(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }
          else if(this.validate100charactersallowed(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }*/
        }
      }
    }

    for(let j=0;j<this.customer.health_array_insert.length;j++){
      let tempobj = this.customer.health_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }







    for(let j=0;j<this.customer.finance_array.length;j++){
      let tempobj = this.customer.finance_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_description_p_f_h_s_p']) ){
        finalresult = false;
      }*/
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }
      else if(this.validate100charactersallowed(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }*/
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }/*
          else if(this.validateSpecialCharsAndNum(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }
          else if(this.validate100charactersallowed(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }*/
        }
      }
    }

    for(let j=0;j<this.customer.finance_array_insert.length;j++){
      let tempobj = this.customer.finance_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }






    for(let j=0;j<this.customer.policy_array.length;j++){
      let tempobj = this.customer.policy_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_description_p_f_h_s_p']) ){
        finalresult = false;
      }*/
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }/*
      else if(this.validateSpecialCharsAndNum(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }
      else if(this.validate100charactersallowed(tempobj['document_possible_s_t_i'])){
        finalresult = false;
      }*/
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }/*
          else if(this.validateSpecialCharsAndNum(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }
          else if(this.validate100charactersallowed(tempobj['document_action_required'][z])  ){
            finalresult = false;
          }*/
        }
      }
    }

    for(let j=0;j<this.customer.policy_array_insert.length;j++){
      let tempobj = this.customer.policy_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }


    if(!finalresult){
      alert("Form Invalid");
      return false;
    }
  // Year should be in this format
    // 2016-06-15

    // var date2 = new Date("" + this.date.value);

    // var new_date2 = date2.getDate();
    // var new_month2 = (date2.getMonth() + 1);
    // var new_year2 = date2.getFullYear();

    // this.customer.form_2_date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    
    // display form values on success
    // alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.form_3_checkdate = new_year2 + "-" + new_month2 + "-" + new_date2;

    //console.log("this.customer : "+JSON.stringify(this.customer));return;
    

    //alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
    this._diphHttpClientService.editUpdateForm3DefineDetails(this.customer,  
      // this.service_array,
      // this.workforce_array,
      // this.supplies_array,
      // this.health_array,
      // this.finance_array,
      // this.policy_array,
      login_district, login_cycle, login_year, login_userid,"1")
      .subscribe(
        data => {
          console.log(data);
          //this.serverjsonresponse = data;

          //sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));
          this.savedform = true;
          this.router.navigate(['dashboard/form3defineview']);
        },
        error => { console.log(error); alert("Error= " + error); });
  }

  onSubmit() {

    this.submitted = true;
    let finalresult = true;

    let no_data = "No Data Available";

    let no_data_array = {
        document_description_p_f_h_s_p: no_data,
        document_possible_s_t_i: no_data,
        document_action_required: [no_data],
        action_req_id: [""],
        use_in_ui: null,
        form_3_1_action_part_engagement_nm_details_pkey: ""
      };
    /*
    if(! this.form_plan_not_filled){
      alert("Form Define data is used in next form. Data can not be edited.");
      window.location.reload();
      return;
    }
    */
    if(this.customer.form_3_meeting_venue == ''){
      alert("Venue of the meeting is compulsary");
      return;
    } 
    
    else if(this.customer.form_3_filled_by == ''){
      alert("Theme Leader of the cycle is compulsary");
      return;
    }
    
    if(this.customer.form3_chair_person == ''){
      alert("Enter Chairperson of meeting");
      return;
    }

    if(this.customer.form3_chair_person == '15'  &&  this.customer.form3_chair_person_others == null){
      alert("Enter Chairperson of meeting");
      return;
    }

    if(this.customer.form3_chair_person == '15'  &&  this.customer.form3_chair_person_others  == ''){
      alert("Enter Chairperson of meeting");
      return;
    }

    if(this.customer.form3_chair_person == '15'  &&  this.customer.form3_chair_person_others  == 'null'){
      alert("Enter Chairperson of meeting");
      return;
    }

    // for Sevice Delivery

    if(this.service_data_not_available){
      
      this.customer.service_description_p_f_h_s_p = no_data;      
      this.customer.service_possible_s_t_i = no_data;
      this.customer.service_array_insert.length = 0;

      this.customer.service_array.length = 0;
      this.customer.service_array.push(no_data_array);
    } else{  

      finalresult = true;

    for(let j=0;j<this.customer.service_array.length;j++){
      let tempobj = this.customer.service_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }     
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    for(let j=0;j<this.customer.service_array_insert.length;j++){
      let tempobj = this.customer.service_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    if( ! finalresult){
      alert("Fill all the points of Service Delivery");
      return;
    }

  }

  // for Workforce

  if(this.workforce_data_not_available){
      
    this.customer.workforce_service_description_p_f_h_s_p = no_data;
    this.customer.workforce_service_possible_s_t_i = no_data;
    this.customer.workforce_array_insert.length = 0;

    this.customer.workforce_array.length= 0;
    this.customer.workforce_array.push(no_data_array);
  } else{  

    finalresult = true;

    for(let j=0;j<this.customer.workforce_array.length;j++){
      let tempobj = this.customer.workforce_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }      
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    for(let j=0;j<this.customer.workforce_array_insert.length;j++){
      let tempobj = this.customer.workforce_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    if( ! finalresult){
      alert("Fill all the points of Workforce");
      return;
    }
  }


  // for Supply

  if(this.supply_data_not_available){
      
    this.customer.supplies_service_description_p_f_h_s_p = no_data;
    this.customer.supplies_service_possible_s_t_i = no_data;
    this.customer.supplies_array_insert.length = 0;

    this.customer.supplies_array.length = 0;
    this.customer.supplies_array.push(no_data_array);
  } else{  

    finalresult = true;

    for(let j=0;j<this.customer.supplies_array.length;j++){
      let tempobj = this.customer.supplies_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }     
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    for(let j=0;j<this.customer.supplies_array_insert.length;j++){
      let tempobj = this.customer.supplies_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    if( ! finalresult){
      alert("Fill all the points of Supplies & Technology");
      return;
    }


  }


  
  // for Health Information

  if(this.health_data_not_available){
      
    this.customer.health_service_description_p_f_h_s_p = no_data;
    this.customer.health_service_possible_s_t_i = no_data;
    this.customer.health_array_insert.length = 0;

    this.customer.health_array.length = 0;
    this.customer.health_array.push(no_data_array);
  } else{  

    finalresult = true;

    for(let j=0;j<this.customer.health_array.length;j++){
      let tempobj = this.customer.health_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }    
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    for(let j=0;j<this.customer.health_array_insert.length;j++){
      let tempobj = this.customer.health_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    if(! finalresult){
      alert("Fill all the points of Health Information");
      return;
    }
  }

  
  
  // for Finance

  if(this.finance_data_not_available){
      
    this.customer.finance_service_description_p_f_h_s_p = no_data;
    this.customer.finance_service_possible_s_t_i = no_data;
    this.customer.finance_array_insert.length = 0;

    this.customer.finance_array.length = 0;
    this.customer.finance_array.push(no_data_array);
  } else{  

    finalresult = true;

    for(let j=0;j<this.customer.finance_array.length;j++){
      let tempobj = this.customer.finance_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    for(let j=0;j<this.customer.finance_array_insert.length;j++){
      let tempobj = this.customer.finance_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    if( ! finalresult){
      alert("Fill all the points of Finance");
      return;
    }

  }



   
  // for Policy

  if(this.policy_data_not_available){
      
    this.customer.policy_service_description_p_f_h_s_p = no_data;
    this.customer.policy_service_possible_s_t_i = no_data;
    this.customer.policy_array_insert.length = 0;

    this.customer.policy_array.length = 0;
    this.customer.policy_array.push(no_data_array);
  } else{  

    finalresult = true;

    for(let j=0;j<this.customer.policy_array.length;j++){
      let tempobj = this.customer.policy_array[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    for(let j=0;j<this.customer.policy_array_insert.length;j++){
      let tempobj = this.customer.policy_array_insert[j];

      if(tempobj['document_description_p_f_h_s_p'] == ''){
        finalresult = false;
      }
      else if(tempobj['document_possible_s_t_i'] == ''){
        finalresult = false;
      }
      else{

        for(let z=0;z<(tempobj['document_action_required']).length;z++){

          if(tempobj['document_action_required'][z] == ''){
            finalresult = false;
          }
        }
      }
    }

    if( ! finalresult){
      alert("Fill all the points of Policy/Governance");
      return;
    }

  }

/*
    if(!finalresult){
      alert("Form Invalid");
      return false;
    }
    */
  // Year should be in this format
    // 2016-06-15

    
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');
    let login_userid = sessionStorage.getItem('userid');


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.form_3_checkdate = new_year2 + "-" + new_month2 + "-" + new_date2;

    this.completeClicked = true;

    if(this.form_plan_not_filled){
      
      this._diphHttpClientService.editUpdateForm3DefineDetails(this.customer,  
        // this.service_array,
        // this.workforce_array,
        // this.supplies_array,
        // this.health_array,
        // this.finance_array,
        // this.policy_array,
        login_district, login_cycle, login_year, login_userid,"1")
        .subscribe(
          data => {
            this.completeClicked = false;
            //this.serverjsonresponse = data;
  
            //sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));
            this.savedform = true;
            this.router.navigate(['dashboard/form3defineview']);
          },
          error => { 
            this.completeClicked = false;
            alert("Error : "+error);
            this.router.navigate(['dashboard']);
           });
    }else{
      
      this._diphHttpClientService.editUpdateForm3DefineWhenPlanFilled(this.customer,  
        // this.service_array,
        // this.workforce_array,
        // this.supplies_array,
        // this.health_array,
        // this.finance_array,
        // this.policy_array,
        login_district, login_cycle, login_year, login_userid,"1")
        .subscribe(
          data => {
            this.completeClicked = false;
            //this.serverjsonresponse = data;
  
            //sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));
            this.savedform = true;
            this.router.navigate(['dashboard/form3defineview']);
          },
          error => { 
            this.completeClicked = false;
            alert("Error : "+error);
            this.router.navigate(['dashboard']);
           });
    }
    
    
  }

  partialSave(){

   
  if (this.customer.form_3_meeting_venue == null || this.customer.form_3_meeting_venue == '') {
      alert("Venue of the meeting is compulsary");
      return;
  }else if(this.validate100charactersallowed(this.customer.form_3_meeting_venue)){
    alert("Venue of the meeting can not exceed 100 characters");
    return;
  }
  
  if (this.customer.form_3_filled_by == null || this.customer.form_3_filled_by == '') {
      alert("Theme Leader of the cycle is compulsary");
      return;
  }else if(this.validate100charactersallowed(this.customer.form_3_filled_by)){
    alert("Theme Leader of the cycle can not exceed 100 characters");
    return;
  }
  
  if (this.customer.form3_chair_person != "15" && (this.customer.form3_chair_person == null || this.customer.form3_chair_person == '')) {
      alert("Chairperson of the meeting is compulsary");
      return;
  }
  if (this.customer.form3_chair_person == "15" && (this.customer.form3_chair_person_others == "null" || this.customer.form3_chair_person_others == "")) {
      alert("Chairperson of the meeting is compulsary");
      return;
  }
  if (this.customer.form3_chair_person == "15" && this.customer.form3_chair_person_others == null) {
      alert("Chairperson of the meeting is compulsary");
      return;
  }

  for(let i=0;i<this.customer.service_array_insert.length;i++){
    let tempobj = this.customer.service_array_insert[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Service Delivery description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Service Delivery description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.customer.service_array[0].document_action_required.length;i++){
    let tempObj = this.customer.service_array[0].document_action_required[i];
    if(tempObj == null || tempObj == ''){
      alert("Service Delivery action point is expanded but not filled");
      return;
    }
  }  

  for(let i=0;i<this.customer.workforce_array_insert.length;i++){
    let tempobj = this.customer.workforce_array_insert[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Workforce description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Workforce description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.customer.workforce_array[0].document_action_required.length;i++){
    let tempObj = this.customer.workforce_array[0].document_action_required[i];
    if(tempObj == null || tempObj == ''){
      alert("Workforce action point is expanded but not filled");
      return;
    }
  }  
  
  for(let i=0;i<this.customer.supplies_array_insert.length;i++){
    let tempobj = this.customer.supplies_array_insert[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Supplies & Technology description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Supplies & Technology description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.customer.supplies_array[0].document_action_required.length;i++){
    let tempObj = this.customer.supplies_array[0].document_action_required[i];
    if(tempObj == null || tempObj == ''){
      alert("Supplies & Technology action point is expanded but not filled");
      return;
    }
  } 

  for(let i=0;i<this.customer.health_array_insert.length;i++){
    let tempobj = this.customer.health_array_insert[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Health Information description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Health Information description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.customer.health_array[0].document_action_required.length;i++){
    let tempObj = this.customer.health_array[0].document_action_required[i];
    if(tempObj == null || tempObj == ''){
      alert("Health Information action point is expanded but not filled");
      return;
    }
  } 


  for(let i=0;i<this.customer.finance_array_insert.length;i++){
    let tempobj = this.customer.finance_array_insert[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Finance description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Finance description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.customer.finance_array[0].document_action_required.length;i++){
    let tempObj = this.customer.finance_array[0].document_action_required[i];
    if(tempObj == null || tempObj == ''){
      alert("Finance action point is expanded but not filled");
      return;
    }
  } 

  for(let i=0;i<this.customer.policy_array_insert.length;i++){
    let tempobj = this.customer.policy_array_insert[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Policy/Governance is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Policy/Governance description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.customer.policy_array[0].document_action_required.length;i++){
    let tempObj = this.customer.policy_array[0].document_action_required[i];
    if(tempObj == null || tempObj == ''){
      alert("Policy/Governance action point is expanded but not filled");
      return;
    }
  } 

  for(let j=0;j<this.customer.service_array_insert.length;j++){
    let tempobj= this.customer.service_array_insert[j];   

    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Service Delivery action point is expanded but not filled");
        return;
      }
    }
  }


  for(let j=0;j<this.customer.workforce_array_insert.length;j++){
    let tempobj= this.customer.workforce_array_insert[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Workforce description is expanded but not filled");
       return;
      }
    }
  }


  for(let j=0;j<this.customer.supplies_array_insert.length;j++){
    let tempobj= this.customer.supplies_array_insert[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Supplies & Technology description is expanded but not filled");
        return;
      }
    }
  }


  for(let j=0;j<this.customer.health_array_insert.length;j++){
    let tempobj= this.customer.health_array_insert[j];
    

    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Health Information description is expanded but not filled");
        return;
      }
    }
  }


  for(let j=0;j<this.customer.finance_array_insert.length;j++){
    let tempobj= this.customer.finance_array_insert[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Finance description is expanded but not filled");
        return;
      }
    }
  }



  for(let j=0;j<this.customer.policy_array_insert.length;j++){
    let tempobj= this.customer.policy_array_insert[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Policy/Governance is expanded but not filled");
        return;
      }
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

  this.customer.form_3_checkdate = new_year2 + "-" + new_month2 + "-" + new_date2;

  this.completeClicked = true;

  this._diphHttpClientService.editUpdateForm3DefineDetails(this.customer, login_district, login_cycle, login_year, login_userid,"0")
    .subscribe(
      data => {  
        this.completeClicked = false;      
        this.savedform = true;
        this.router.navigate(['dashboard/form3defineview']);
      },
      error => { 
       this.completeClicked = false;
        alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
        this.router.navigate(['dashboard']);
       });



  }

  show_part2_also() {

  }

}
