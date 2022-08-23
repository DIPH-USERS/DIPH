import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Observable } from 'rxjs';
import { form1bEdit } from '../model/form1bEdit';

@Component({
  selector: 'app-form2engage-edit',
  templateUrl: './form2engage-edit.component.html',
  styleUrls: ['./form2engage-edit.component.css']
})
export class Form2engageEditComponent implements OnInit {

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

  form_define_not_filled = false; 

  date = new FormControl('2019-08-22T23:00:00');
  date_max= new Date(); 
  @Input() customer: any;
  public jsonresponse: any;
  loading = true;
  resp: any;
  all_areas_indicators_arr = [];
  public verified_by_name_from_Db = [];
  minDate =new Date("12,01,2019");

  total_coverage_indi = [];
  selected_indi: any;

  dialog_response: any = null;

  user: Observable<any>;
  saved_form_indicators = [];

  sanctioned_year: string = new Date().getFullYear() + "";

  all_stake_holders = [];

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
      //console.log("Hello from setTimeout");
      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this.sanctioned_year = year + "-" + (parseInt(year) + 1);

      this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          if (data == null || data.completed == null || data.userid == null)

            this.form_define_not_filled = true;
          else 
            this.form_define_not_filled = false;
          
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });

      this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {

            //console.log(data);

            this.customer = data;
            this.customer.form_2_id = data.form_2_id;
            this.customer.form_2_date_of_meeting = data.form_2_date_of_meeting;
            this.customer.form_2_venue = data.form_2_venue;
            this.customer.form_2_filled = data.form_2_filled;
            this.customer.primary_stake_label = data.primary_stake_label;
            this.customer.primary_stakeholder_text = data.primary_stakeholder_text;
            this.customer.primary_stakeholder_id = data.primary_stakeholder_id;
            this.customer.secondary_stake_label = data.secondary_stake_label;
            this.customer.secondary_stakeholder_text = data.secondary_stakeholder_text;
            this.customer.secondary_stakeholder_id = data.secondary_stakeholder_id;
            this.customer.defining_primary_role_section_select = data.defining_primary_role_section_select;
            this.customer.defining_primary_role_section_text = data.defining_primary_role_section_text;
            this.customer.current_effort_to_address_the_issue_section_select = data.current_effort_to_address_the_issue_section_select;
            this.customer.current_effort_to_address_the_issue_section_text = data.current_effort_to_address_the_issue_section_text;
            this.customer.how_to_enhance_engagement_and_efficiency_section_select = data.how_to_enhance_engagement_and_efficiency_section_select;
            this.customer.how_to_enhance_engagement_and_efficiency_section_text = data.how_to_enhance_engagement_and_efficiency_section_text;
            this.customer.theme_lead_by_department_section_select = data.theme_lead_by_department_section_select;
            this.customer.theme_lead_by_department_section_text = data.theme_lead_by_department_section_text;
            this.customer.district = data.district;
            this.customer.cycle = data.cycle;
            this.customer.year = data.year;
            this.customer.userid = data.userid;
            this.customer.primary_stake_array = data.primary_stake_array;
            this.customer.secondary_stake_array = data.secondary_stake_array;
            this.customer.definingprimaryrole_array = data.definingprimaryrole_array;
            this.customer.efforttoaddressissue_array = data.efforttoaddressissue_array;
            this.customer.enhanceefficiency_array = data.enhanceefficiency_array;
            this.customer.themeleadbydpt_array = data.themeleadbydpt_array;

            if(data.completed == '1')
            this.customer.completed = true;
              else if(data.completed == '0')
            this.customer.completed = false;


            for (let p = 0; p < data.primary_stake_array.length; p++) {
              this.all_stake_holders.push(data.primary_stake_array[p]);
            }


            for (let s = 0; s < data.secondary_stake_array.length; s++) {
              this.all_stake_holders.push(data.secondary_stake_array[s]);
            }



            var date = new Date("" + this.customer.form_2_date_of_meeting);

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


            this.customer.form_2_date_of_meeting = x + '/' + y + '/' + new_year;
            this.date = new FormControl(new_year + '-' + y + '-' + x + 'T23:00:00');
            this.loading = false;
          },
          error => {
            console.log(error); alert("Error= " + error);
          });

    }, 3000);
  }

  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get dprs() { return this.f.defining_primary_role_section_select as FormArray; }


  onClickAddRow() {

  }

  onClickRemove() {

  }

  onClickRemoveDefiningPrimaryRow(e){
    this.definingprimaryrole_array.splice(e,1);
  }

  onClickRemoveEfforttoAddressIssueRow(e){
    this.efforttoaddressissue_array.splice(e,1);
  }

  onClickRemoveEnhanceEfficiencyRow(e){
    this.enhanceefficiency_array.splice(e,1);
  }

  onClickRemoveThemeLeadbyDptRow(e){
    this.themeleadbydpt_array.splice(e,1);
  }

  efforttoaddressissue_array=[];
  definingprimaryrole_array=[];
  themeleadbydpt_array=[];
  enhanceefficiency_array=[];

  onClickAddEnhanceEfficiencyRow(val) {
    this.enhanceefficiency_array.push({
      "document_select_stakeholder": "",
      "document_desc": " ",
      "form_2_section_id":"0"
    });
  }

  


  onClickAddThemeLeadbyDptRow(val) {
    this.themeleadbydpt_array.push({
      "document_select_stakeholder": "",
      "document_desc": " ",
      "form_2_section_id":"0"
    });
  }
  

  onClickAddDefiningPrimaryRoleRow(val) {

    this.definingprimaryrole_array.push({
      "document_select_stakeholder": "",
      "document_desc": " ",
      "form_2_section_id":"0"
    });

  }

 
  onClickAddEfforttoAddressIssueRow(val) {
    this.efforttoaddressissue_array.push({
      "document_select_stakeholder": "",
      "document_desc": " ",
      "form_2_section_id":"0"
    });
  }



  onClickAddPrimaryStakeholderRow(val) {

    let maxId = this.getMaxIdOfArray(this.customer.primary_stake_array);
    maxId++;
    this.customer.primary_stake_array.push({
      "document_label": "Primary Stakeholder",
      "document_name": "",
      "document_id": "" + maxId
    });   
  }

  onClickRemovePrimaryStakeholderRow(e) {
    this.customer.primary_stake_array.splice(e,1);
    this.filterPartB_arrays();  
  }

  onClickAddSecondaryStakeholderRow(val) {
   
    let maxId = this.getMaxIdOfArray(this.customer.secondary_stake_array);
    maxId++;
    this.customer.secondary_stake_array.push({
      "document_label": "Secondary Stakeholder",
      "document_name": "",
      "document_id": "" + maxId
    });  
  }

  onClickRemoveSecondaryStakeholderRow(e) {
    this.customer.secondary_stake_array.splice(e,1);
    this.filterPartB_arrays();
  }

  getMaxIdOfArray(arr){

    let max = arr[0].document_id;
    for(let i=0;i<arr.length;i++){
      if(arr[i].document_id>max) max=arr[i].document_id;
    }
    return max;
  }
  
  getAllStakeholdersId(){

    let i=0;
    let stakeholder_id_array = [];

    for(i=0; i<this.customer.primary_stake_array.length; i++){
      stakeholder_id_array.push(this.customer.primary_stake_array[i].document_id);
    }
    for(i=0; i<this.customer.secondary_stake_array.length; i++){
      stakeholder_id_array.push(this.customer.secondary_stake_array[i].document_id);
    }
    return stakeholder_id_array;
  }

  filterPartB_arrays(){
    
    let i=0;
    let stakeholder_id_array = this.getAllStakeholdersId();
    //
    for(i=0; i<this.customer.definingprimaryrole_array.length; i++){

      if( ! stakeholder_id_array.includes(this.customer.definingprimaryrole_array[i].document_select_stakeholder) )
        this.customer.definingprimaryrole_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }
    for(i=0; i<this.definingprimaryrole_array.length; i++){

      if( ! stakeholder_id_array.includes(this.definingprimaryrole_array[i].document_select_stakeholder) )
        this.definingprimaryrole_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }
    //
    for(i=0; i<this.customer.themeleadbydpt_array.length; i++){

      if( ! stakeholder_id_array.includes(this.customer.themeleadbydpt_array[i].document_select_stakeholder) )
        this.customer.themeleadbydpt_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }
    for(i=0; i<this.themeleadbydpt_array.length; i++){

      if( ! stakeholder_id_array.includes(this.themeleadbydpt_array[i].document_select_stakeholder) )
        this.themeleadbydpt_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }
    //
    for(i=0; i<this.customer.enhanceefficiency_array.length; i++){

      if( ! stakeholder_id_array.includes(this.customer.enhanceefficiency_array[i].document_select_stakeholder) )
        this.customer.enhanceefficiency_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }
    for(i=0; i<this.enhanceefficiency_array.length; i++){

      if( ! stakeholder_id_array.includes(this.enhanceefficiency_array[i].document_select_stakeholder) )
        this.enhanceefficiency_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }
    //
    for(i=0; i<this.customer.efforttoaddressissue_array.length; i++){

      if( ! stakeholder_id_array.includes(this.customer.efforttoaddressissue_array[i].document_select_stakeholder) )
        this.customer.efforttoaddressissue_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }
    for(i=0; i<this.efforttoaddressissue_array.length; i++){

      if( ! stakeholder_id_array.includes(this.efforttoaddressissue_array[i].document_select_stakeholder) )
        this.efforttoaddressissue_array[i].document_select_stakeholder = stakeholder_id_array[0];
    }

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

  findvalue(event) {
    // alert(event.which);
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

  onSubmit() {


    this.submitted = true;
    let finalresult = true;

    /*
    if(! this.form_define_not_filled){
        alert("Form Engage data is used in next form. Data can not be edited.");
        window.location.reload();
        return;
    }
    */

    if (this.customer.form_2_venue == '') { 
      finalresult = false;
    }/* else if (this.validateSpecialCharsAndNum(this.customer.form_2_venue)) {
      finalresult = false;
    }*/
    else if (this.validate100charactersallowed(this.customer.form_2_venue)) {
      finalresult = false;
    }
    if (this.customer.form_2_filled == '') {
      finalresult = false;
    }
    if (this.customer.form_2_filled == '15' && this.customer.form_2_filled_others == '') {
      finalresult = false;
    }
    if (this.customer.form_2_filled == '15' && this.customer.form_2_filled_others == null) {
      finalresult = false;
    }
    if (this.customer.form_2_filled == '15' && this.customer.form_2_filled_others == 'null') {
      finalresult = false;
    }

   

    for(let x=0;x<this.customer.definingprimaryrole_array.length;x++){
      let tempobj = this.customer.definingprimaryrole_array[x];

      if(tempobj.document_desc.trim() == "" 
      // || this.validateSpecialCharsAndNum(tempobj.document_desc)  
      // || this.validate100charactersallowed(tempobj.document_desc) 
      ){
        finalresult = false;
      }

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }

    for(let x=0;x<this.definingprimaryrole_array.length;x++){
      let tempobj = this.definingprimaryrole_array[x];

      if(tempobj.document_desc.trim() == "" 
      // || this.validateSpecialCharsAndNum(tempobj.document_desc)   
      // || this.validate100charactersallowed(tempobj.document_desc) 
      ){
        finalresult = false;
      }

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }

    for(let x=0;x<this.customer.efforttoaddressissue_array.length;x++){
      let tempobj = this.customer.efforttoaddressissue_array[x];

      if(tempobj.document_desc.trim() == ""  
      // || this.validateSpecialCharsAndNum(tempobj.document_desc)   
      // || this.validate100charactersallowed(tempobj.document_desc)
      ){
        finalresult = false;
      }

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }

    for(let x=0;x<this.efforttoaddressissue_array.length;x++){
      let tempobj = this.efforttoaddressissue_array[x];

      if(tempobj.document_desc.trim() == ""  
      // || this.validateSpecialCharsAndNum(tempobj.document_desc)    
      // || this.validate100charactersallowed(tempobj.document_desc)
      ){
        finalresult = false;
      }

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }

    for(let x=0;x<this.customer.enhanceefficiency_array.length;x++){
      let tempobj = this.customer.enhanceefficiency_array[x];

      if(tempobj.document_desc.trim() == "" 
      // || this.validateSpecialCharsAndNum(tempobj.document_desc)   
      // || this.validate100charactersallowed(tempobj.document_desc)
      ){
        finalresult = false;
      }

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }

    for(let x=0;x<this.enhanceefficiency_array.length;x++){
      let tempobj = this.enhanceefficiency_array[x];

      if(tempobj.document_desc.trim() == "" 
      // || this.validateSpecialCharsAndNum(tempobj.document_desc)    
      // || this.validate100charactersallowed(tempobj.document_desc)
      ){
        finalresult = false;
      }

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }

    

    for(let x=0;x<this.customer.themeleadbydpt_array.length;x++){
      let tempobj = this.customer.themeleadbydpt_array[x];  

      if(tempobj.document_desc.trim() == "" ){
        finalresult = false;
      }

      // if( this.validateSpecialCharsAndNum(tempobj.document_desc.trim())    || this.validate100charactersallowed(tempobj.document_desc)){
      //   finalresult = false;
      // }

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }


    for(let x=0;x<this.themeleadbydpt_array.length;x++){
      let tempobj = this.themeleadbydpt_array[x];

      if(tempobj.document_desc.trim() == ""  
      // || this.validateSpecialCharsAndNum(tempobj.document_desc)    
      // || this.validate100charactersallowed(tempobj.document_desc)
      ){
        finalresult = false;
      } 

      if(tempobj.document_select_stakeholder.trim() == ""){
        finalresult = false;
      }
    }

    if(!finalresult){
      alert("Form invalid");
      return;
    }

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.form_2_date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    
    // display form values on success
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');


    //alert(JSON.stringify(this.enhanceefficiency_array,null,4));

   
    // this.customer.definingprimaryrole_array = this.customer.definingprimaryrole_array.concat(this.definingprimaryrole_array);
    // this.customer.efforttoaddressissue_array = this.customer.efforttoaddressissue_array.concat(this.efforttoaddressissue_array);
    // this.customer.enhanceefficiency_array= this.customer.enhanceefficiency_array.concat(this.enhanceefficiency_array);
    // this.customer.themeleadbydpt_array= this.customer.themeleadbydpt_array.concat(this.themeleadbydpt_array);

    this.completeClicked = true;

    //console.log("this.customer : "+JSON.stringify(this.customer));

    if(this.form_define_not_filled){      

      this._diphHttpClientService.editUpdateForm2EngageDetails(this.customer,this.definingprimaryrole_array,
        this.efforttoaddressissue_array,this.enhanceefficiency_array,this.themeleadbydpt_array,
        login_district, login_cycle, login_year, login_userid, "1")
        .subscribe(
          data => {
            this.completeClicked = false;
            
            this.savedform = true;
            this.router.navigate(['dashboard/form2engageview']);
          },
          error => { 
            this.completeClicked = false;
            alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
            this.router.navigate(['dashboard']);
           });

    }else{      

      this._diphHttpClientService.editUpdateForm2EngageWhen3Filled(this.customer,this.definingprimaryrole_array,
        this.efforttoaddressissue_array,this.enhanceefficiency_array,this.themeleadbydpt_array,
        login_district, login_cycle, login_year, login_userid, "1")
        .subscribe(
          data => {
            this.completeClicked = false;
            
            this.savedform = true;
            this.router.navigate(['dashboard/form2engageview']);
          },
          error => { 
            this.completeClicked = false;
            alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
            this.router.navigate(['dashboard']);
           });
    }
    
    
         
  }

  partialSave(){
    
    if(this.customer.form_2_venue == '' || this.customer.form_2_venue == null){
      alert("Venue of the meeting is compulsary");
      return;
    }
    
     if(this.customer.form_2_filled != "15" && (this.customer.form_2_filled == null || this.customer.form_2_filled == '')){ 
       alert("Chairperson of the meeting is compulsary");
       return;
    }
    if(this.customer.form_2_filled == "15" && (this.customer.form_2_filled_others == "null" || this.customer.form_2_filled_others == "")){ 
      alert("Chairperson of the meeting is compulsary");
      return;
    }
    if(this.customer.form_2_filled == "15" && this.customer.form_2_filled_others == null){ 
      alert("Chairperson of the meeting is compulsary");
      return;
    }
  
    
    for (let i=0; i < this.definingprimaryrole_array.length;i++) {
      let tempobj = this.definingprimaryrole_array[i];
      let document_val = tempobj.document_select_stakeholder.trim();
      let document_desc = tempobj.document_desc.trim();
  
        if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
          alert("'Defining Primary Role (Department-Wise)' expanded but not filled");
          return;
        }
    }
  
    for (let i=0; i < this.efforttoaddressissue_array.length;i++) {
      let tempobj = this.efforttoaddressissue_array[i];
      let document_val = tempobj.document_select_stakeholder.trim();
      let document_desc = tempobj.document_desc.trim();
  
      if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
          alert("'Current Effort To Address The Issue' expanded but not filled");
          return;
        }
    }
  
    for (let i=0; i < this.enhanceefficiency_array.length;i++) {
      let tempobj = this.enhanceefficiency_array[i];
      let document_val = tempobj.document_select_stakeholder.trim();
      let document_desc = tempobj.document_desc.trim();
  
        if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
          alert("'How To Enhance Engagement And Efficiency' expanded but not filled");
          return;
        }
    }
  
    for (let i=0; i < this.themeleadbydpt_array.length;i++) {
      let tempobj = this.themeleadbydpt_array[i];
      let document_val = tempobj.document_select_stakeholder.trim();
      let document_desc = tempobj.document_desc.trim();
  
      if((document_val == null || document_val == '') && (document_desc == null || document_desc == '')){
          alert("'Theme Lead By Department' expanded but not filled");
          return;
        }
    }
    
  
      var date2 = new Date("" + this.date.value);
  
      var new_date2 = date2.getDate();
      var new_month2 = (date2.getMonth() + 1);
      var new_year2 = date2.getFullYear();
  
      this.customer.form_2_date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;
  
      
      
      let login_district = sessionStorage.getItem('district');
      let login_cycle = sessionStorage.getItem('cycle');
      let login_year = sessionStorage.getItem('year');
      let login_userid = sessionStorage.getItem('userid');
  
      this.completeClicked = true;

      this._diphHttpClientService.editUpdateForm2EngageDetails(this.customer,this.definingprimaryrole_array,
        this.efforttoaddressissue_array,this.enhanceefficiency_array,this.themeleadbydpt_array,
        login_district, login_cycle, login_year, login_userid, "0")
        .subscribe(
          data => {
            this.completeClicked = false;
            
            this.savedform = true;
            this.router.navigate(['dashboard/form2engageview']);
          },
          error => { 
            this.completeClicked = false;
            alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
            this.router.navigate(['dashboard']);
           });

  }

  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }

  //After clicking NExt btton
  show_part2_also() {
      
    try {
      for(let j=0;j<this.customer.primary_stake_array.length;j++){

        if(this.customer.primary_stake_array[j].document_name == ''){
          alert("Primary Stakeholder name can not be blank");  
          return;
        }
      }

    } catch (error) {
      
    }

    try {
      for(let j=0;j<this.customer.secondary_stake_array.length;j++){

        if(this.customer.secondary_stake_array[j].document_name == ''){
          alert("Secondary Stakeholder name can not be blank");  
          return;
        }
      }

    } catch (error) {
      
    }
   

      
    try {
      for(let i=0;i<this.customer.primary_stake_array.length;i++){

        var searchName = this.customer.primary_stake_array[i].document_name; 

        for(let j=0;j<this.customer.primary_stake_array.length;j++){
          if(i != j && searchName == this.customer.primary_stake_array[j].document_name){
            alert("Please enter only unique Primary Stakeholder names!");  
            return;
          }
        }
        
      }

    } catch (error) {
      
    }

    try {
      for(let i=0;i<this.customer.secondary_stake_array.length;i++){

        var searchName = this.customer.secondary_stake_array[i].document_name; 

        for(let j=0;j<this.customer.secondary_stake_array.length;j++){
          if(i != j && searchName == this.customer.secondary_stake_array[j].document_name){
            alert("Please enter only unique Seconday Stakeholder names!");  
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
    this.all_stake_holders = [];    

    for (let i = 0; i < this.customer.primary_stake_array.length; i++) {
      
      this.all_stake_holders.push(this.customer.primary_stake_array[i]);     
    }
   
    for (let j = 0; j < this.customer.secondary_stake_array.length; j++) {
      
      this.all_stake_holders.push(this.customer.secondary_stake_array[j]);     
    }   

   
  }

}
