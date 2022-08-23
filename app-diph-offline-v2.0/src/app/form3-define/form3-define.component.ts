import { Component, OnInit, Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form3-define',
  templateUrl: './form3-define.component.html',
  styleUrls: ['./form3-define.component.css']
})
export class Form3DefineComponent implements OnInit {

  completeClicked : boolean = false;

  service_data_not_available = false;
  workforce_data_not_available = false;
  supply_data_not_available = false;
  health_data_not_available = false;
  finance_data_not_available = false;
  policy_data_not_available = false;

  date = new FormControl(new Date()); 
  date_max= new Date(); 
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
  loading = true;
  public verified_by_name_from_Db=[];
  minDate =new Date("12,01,2019");


  dynamicForm: FormGroup;
  public jsonresponse: any;

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
      
    this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {

        if ( (data.form_2_date_of_meeting == null && data.form_2_venue == null && data.form_2_filled == null) || 
              (data.form_2_date_of_meeting != null && data.form_2_venue != null && data.form_2_filled != null && data.completed == '0') ) {
            alert("Please Fill Form1A to Fill Form Engage");
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

      form_3_checkdate: ['2016-06-15'],
      form_3_meeting_venue: ['',  [Validators.required, Validators.maxLength(100) ]],
      form_3_filled_by: ['',  [Validators.required, Validators.maxLength(100) ]],
      form3_chair_person: ['', Validators.required],
      form3_chair_person_others: new FormControl(),
      theme_id: ['143'],

      service_action_part_of_engagement: ['Service delivery'],
      service_description_p_f_h_s_p: ['', [Validators.required]],
      service_possible_s_t_i: ['', [Validators.required]],

      service_action_part: ['Service delivery'],
      service_form3_sl_no: new FormControl(),
      service_action_required: new FormControl(),

      workforce_service_action_part_of_engagement: ['Workforce'],
      workforce_service_description_p_f_h_s_p: ['', [Validators.required]],
      workforce_service_possible_s_t_i: ['', [Validators.required]],


      workforce_action_part: ['Workforce'],
      workforce_form3_sl_no: new FormControl(),
      workforce_action_required: new FormControl(),


      supplies_service_action_part_of_engagement: ['Supplies & technology'],
      supplies_service_description_p_f_h_s_p: ['', [Validators.required]],
      supplies_service_possible_s_t_i: ['', [Validators.required]],

      supplies_action_part: ['Supplies & technology'],
      supplies_form3_sl_no: new FormControl(),
      supplies_action_required: new FormControl(),


      health_service_action_part_of_engagement: ['Health information'],
      health_service_description_p_f_h_s_p: ['', [Validators.required]],
      health_service_possible_s_t_i: ['', [Validators.required]],


      health_action_part: ['Health information'],
      health_form3_sl_no: new FormControl(),
      health_action_required: new FormControl(),


      finance_service_action_part_of_engagement: ['Finance'],
      finance_service_description_p_f_h_s_p: ['', [Validators.required]],
      finance_service_possible_s_t_i: ['', [Validators.required]],


      finance_action_part: ['Finance'],
      finance_form3_sl_no: new FormControl(),
      finance_action_required: new FormControl(),


      policy_service_action_part_of_engagement: ['Policy/governance'],
      policy_service_description_p_f_h_s_p: ['', [Validators.required]],
      policy_service_possible_s_t_i: ['', [Validators.required]],   


      policy_action_part: ['Policy/governance'],
      policy_form3_sl_no: new FormControl(),
      policy_action_required: new FormControl(),

      numberOfTickets: ['1'],      
      numberOfworkforcerow: ['1'],
      numberOfsuppliesrow: ['1'],
      numberOfhealthrow: ['1'],
      numberOffinancerow: ['1'],
      numberOfpolicyrow: ['1'],
      /*
      service
workforce
supplies
health
finance
policy
      */
     service_array: new FormArray([]),
     workforce_array: new FormArray([]),
     supplies_array: new FormArray([]),
     health_array: new FormArray([]),
     finance_array: new FormArray([]),
     policy_array: new FormArray([]),

     
    });

    

    this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            this.jsonresponse = data;
            this.loading = false;
          },
          error => {
            console.log(error); alert("Error in fetching data from Server= " + error);
          });

    
    $(document).ready(function () {


    });
  }

  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get dprs() { return this.f.defining_primary_role_section_select as FormArray; }
  get service_array() { return this.f.service_array as FormArray; }
  get workforce_array() { return this.f.workforce_array as FormArray; }
  get supplies_array() { return this.f.supplies_array as FormArray; }
  get health_array() { return this.f.health_array as FormArray; }
  get finance_array() { return this.f.finance_array as FormArray; }
  get policy_array() { return this.f.policy_array as FormArray; }


  
  service_subarr = 0;
  first_service_document_action_required = [""];
  first_workforce_document_action_required = [""];
  first_supplies_document_action_required = [""];
  first_health_document_action_required = [""];
  first_finance_document_action_required = [""];
  first_policy_document_action_required = [""];

  // service_action_part_of_engagement: ['Service delivery'],
  // service_description_p_f_h_s_p: new FormControl(),
  // service_possible_s_t_i: new FormControl(),

  // service_action_part: ['Service delivery'],
  // service_form3_sl_no: new FormControl(),
  // service_action_required: new FormControl(),

  onClickAddWorkforceActionRow(i:number){
    this.workforce_array.value[i].document_action_required.push("");
  }
  onClickRemoveWorkforceActionRow(i,j){
    this.workforce_array.value[i].document_action_required.splice(j,1);
  }

  onClickAddWorkforceRow(val) {
    this.f.numberOfworkforcerow.setValue(parseInt(this.f.numberOfworkforcerow.value) + val);
    const numberOfworkforcerow = this.f.numberOfworkforcerow.value - 1 || 0;
    if (this.workforce_array.length < numberOfworkforcerow) {
      for (let i = this.workforce_array.length; i < numberOfworkforcerow; i++) {
        this.workforce_array.push(this.formBuilder.group({
          document_description_p_f_h_s_p: ['', [Validators.required]],
          document_possible_s_t_i: ['', [Validators.required]],
          document_action_required: [[""]]
        }));
      }
      //this.workforce_array.value[this.workforce_array.length-1].document_action_required.push("");
    }
  }

  onClickAddSuppliesActionRow(i:number){
    this.supplies_array.value[i].document_action_required.push("");
  }
  onClickRemoveSuppliesActionRow(i,j){
    this.supplies_array.value[i].document_action_required.splice(j,1);
  }


  onClickAddSuppliesRow(val) {
    this.f.numberOfsuppliesrow.setValue(parseInt(this.f.numberOfsuppliesrow.value) + val);
    const numberOfsuppliesrow = this.f.numberOfsuppliesrow.value - 1 || 0;
    if (this.supplies_array.length < numberOfsuppliesrow) {
      for (let i = this.supplies_array.length; i < numberOfsuppliesrow; i++) {
        this.supplies_array.push(this.formBuilder.group({
          document_description_p_f_h_s_p: ['', [Validators.required]],
          document_possible_s_t_i: ['', [Validators.required]],
          document_action_required: [[]]
        }));
      }
      this.supplies_array.value[this.supplies_array.length-1].document_action_required.push("");
    }
  }

  onClickAddHealthActionRow(i:number){
    this.health_array.value[i].document_action_required.push("");
  }
  onClickRemoveHealthActionRow(i,j){
    this.health_array.value[i].document_action_required.splice(j,1);
  }


  onClickAddHealthRow(val) {
    this.f.numberOfhealthrow.setValue(parseInt(this.f.numberOfhealthrow.value) + val);
    const numberOfhealthrow = this.f.numberOfhealthrow.value - 1 || 0;
    if (this.health_array.length < numberOfhealthrow) {
      for (let i = this.health_array.length; i < numberOfhealthrow; i++) {
        this.health_array.push(this.formBuilder.group({
          document_description_p_f_h_s_p: ['', [Validators.required]],
          document_possible_s_t_i: ['', [Validators.required]],
          document_action_required: [[]]
        }));
      }
      this.health_array.value[this.health_array.length-1].document_action_required.push("");
    }
  }

  onClickAddFinanceActionRow(i:number){
    this.finance_array.value[i].document_action_required.push("");
  }
  onClickRemoveFinanceActionRow(i,j){
    this.finance_array.value[i].document_action_required.splice(j,1);
  }


  onClickAddFinanceRow(val) {
    this.f.numberOffinancerow.setValue(parseInt(this.f.numberOffinancerow.value) + val);
    const numberOffinancerow = this.f.numberOffinancerow.value - 1 || 0;
    if (this.finance_array.length < numberOffinancerow) {
      for (let i = this.finance_array.length; i < numberOffinancerow; i++) {
        this.finance_array.push(this.formBuilder.group({
          document_description_p_f_h_s_p: ['', [Validators.required]],
          document_possible_s_t_i: ['', [Validators.required]],
          document_action_required: [[]]
        }));
      }

      this.finance_array.value[this.finance_array.length-1].document_action_required.push("");
    }
  }

  
  onClickAddPolicyActionRow(i:number){
    this.policy_array.value[i].document_action_required.push("");
  }
  onClickRemovePolicyActionRow(i,j){
    this.policy_array.value[i].document_action_required.splice(j,1);
  }


  onClickAddPolicyRow(val) { 
    this.f.numberOfpolicyrow.setValue(parseInt(this.f.numberOfpolicyrow.value) + val);
    const numberOfpolicyrow = this.f.numberOfpolicyrow.value - 1 || 0;
    if (this.policy_array.length < numberOfpolicyrow) {
      for (let i = this.policy_array.length; i < numberOfpolicyrow; i++) {
        // let arr = new FormArray([]) ;
        // arr.push(this.formBuilder.group({
        //   name: ['']
        // }));
        this.policy_array.push(this.formBuilder.group({
          document_description_p_f_h_s_p: ['', [Validators.required]],
          document_possible_s_t_i: ['', [Validators.required]],
          document_action_required: [[]]
        }));
      }
      this.policy_array.value[this.policy_array.length-1].document_action_required.push("");
    }
  }

  // get f() { return this.dynamicForm.controls; }
  // get dprs() { return this.f.defining_primary_role_section_select as FormArray; }
  // get service_array() { return this.f.service_array as FormArray; }

  // get sa(){return this.service_array.controls;};
  // get aa(){return this.sa.document_action_required as FormArray;}


  

  check(val){
    alert(val);
  }

  onclickAddFirstPolicyActionRow(){
    this.first_policy_document_action_required.push("");
  }

  onclickRemoveFirstPolicyActionRow(e){
    this.first_policy_document_action_required.splice(e,1);
  }


  onclickAddFirstFinanceActionRow(){
    this.first_finance_document_action_required.push("");
  }

  onclickRemoveFirstFinanceActionRow(e){
    this.first_finance_document_action_required.splice(e,1);
  }


  onclickAddFirstHealthActionRow(){
    this.first_health_document_action_required.push("");
  }

  onclickRemoveFirstHealthActionRow(e){
    this.first_health_document_action_required.splice(e,1);
  }

  onclickAddFirstSuppliesActionRow(){
    this.first_supplies_document_action_required.push("");
  }

  onclickRemoveFirstSuppliesActionRow(e){
    this.first_supplies_document_action_required.splice(e,1);
  }


  onclickAddFirstWorkforceActionRow(){
    this.first_workforce_document_action_required.push("");
  }

  onclickRemoveFirstWorkforceActionRow(e){
    this.first_workforce_document_action_required.splice(e,1);
  }


  onclickAddFirstServiceActionRow(){
    this.first_service_document_action_required.push("");
  }

  onclickRemoveFirstServiceActionRow(e){
    this.first_service_document_action_required.splice(e,1);
  }

  arr = [];

  onClickAddServiceRow(val) {
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) + val);
    const numberOfTickets = this.f.numberOfTickets.value - 1 || 0;
    if (this.service_array.length < numberOfTickets) {
      for (let i = this.service_array.length; i < numberOfTickets; i++) {

        // let arr= this.formBuilder.group({
        //   param1: ['param1'],
        //   param2: ['param2'],
        //   param3: ['param3']
        // });

        

        this.service_array.push(this.formBuilder.group({
          document_description_p_f_h_s_p: ['', [Validators.required]],
          document_possible_s_t_i: ['', [Validators.required]],
          document_action_required: [[]]
        }));

       

        // console.log(this.service_array.value[0].document_action_required); 
        // this.service_array.value[0].document_action_required.push(4);
        // alert(JSON.stringify(this.service_array.value[0].document_action_required,null,4));
      }

       this.service_array.value[this.service_array.length-1].document_action_required.push("");
    }
  }

  onClickAddServiceActionRow(i:number){
    this.service_array.value[i].document_action_required.push("");
  }
  onClickRemoveServiceActionRow(i,j){
    this.service_array.value[i].document_action_required.splice(j,1);
  }

  onClickRemoveServiceRow(e) {
    this.service_array.removeAt(e);
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) - 1);
  }
 

  onClickRemoveWorkforceRow(e) {
    this.workforce_array.removeAt(e);
    this.f.numberOfworkforcerow.setValue(parseInt(this.f.numberOfworkforcerow.value) - 1);
  }

  onClickRemoveSuppliesRow(e) {
    this.supplies_array.removeAt(e);
    this.f.numberOfsuppliesrow.setValue(parseInt(this.f.numberOfsuppliesrow.value) - 1);
  }

  onClickRemoveHealthRow(e) {
    this.health_array.removeAt(e);
    this.f.numberOfhealthrow.setValue(parseInt(this.f.numberOfhealthrow.value) - 1);
  }

  onClickRemoveFinanceRow(e) {
    this.finance_array.removeAt(e);
    this.f.numberOffinancerow.setValue(parseInt(this.f.numberOffinancerow.value) - 1);
  }

  onClickRemovePolicyRow(e) {
    this.policy_array.removeAt(e);
    this.f.numberOfpolicyrow.setValue(parseInt(this.f.numberOfpolicyrow.value) - 1);
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

    for(let j=0;j<this.first_service_document_action_required.length;j++){
      if(this.first_service_document_action_required[j] == ''){        
        finalresult = false;
      }
      /*
      if(this.validateSpecialCharsAndNum(this.first_service_document_action_required[j])){        
        finalresult = false;
      }
      
      if(this.validate100charactersallowed(this.first_service_document_action_required[j])){        
        finalresult = false;
      }*/
    } 

    for(let j=0;j<this.first_workforce_document_action_required.length;j++){
      if(this.first_workforce_document_action_required[j] == ''){        
        finalresult = false;
      }
      /*
      if(this.validateSpecialCharsAndNum(this.first_workforce_document_action_required[j])){        
        finalresult = false;
      }
      
      if(this.validate100charactersallowed(this.first_workforce_document_action_required[j])){        
        finalresult = false;
      }*/
    }

    for(let j=0;j<this.first_supplies_document_action_required.length;j++){
      if(this.first_supplies_document_action_required[j] == ''){        
        finalresult = false;
      }
      /*
      if(this.validateSpecialCharsAndNum(this.first_supplies_document_action_required[j])){        
        finalresult = false;
      }
      
      if(this.validate100charactersallowed(this.first_supplies_document_action_required[j])){        
        finalresult = false;
      }*/
    }

    for(let j=0;j<this.first_health_document_action_required.length;j++){
      if(this.first_health_document_action_required[j] == ''){        
        finalresult = false;
      }
      /*
      if(this.validateSpecialCharsAndNum(this.first_health_document_action_required[j])){        
        finalresult = false;
      }
      
      if(this.validate100charactersallowed(this.first_health_document_action_required[j])){        
        finalresult = false;
      }*/
    }

    for(let j=0;j<this.first_finance_document_action_required.length;j++){
      if(this.first_finance_document_action_required[j] == ''){        
        finalresult = false;
      }
      /*
      if(this.validateSpecialCharsAndNum(this.first_finance_document_action_required[j])){        
        finalresult = false;
      }
      
      if(this.validate100charactersallowed(this.first_finance_document_action_required[j])){        
        finalresult = false;
      }*/
    }

    for(let j=0;j<this.first_policy_document_action_required.length;j++){
      if(this.first_policy_document_action_required[j] == ''){        
        finalresult = false;
      }
      /*
      if(this.validateSpecialCharsAndNum(this.first_policy_document_action_required[j])){        
        finalresult = false;
      }
      
      if(this.validate100charactersallowed(this.first_policy_document_action_required[j])){        
        finalresult = false;
      }*/
    }


    for(let j=0;j<this.service_array.length;j++){
      let tempobj= this.service_array.value[j];
      //alert(JSON.stringify(tempobj,null,4));

      for(let z=0;z<tempobj.document_action_required.length;z++){
        let  str= tempobj.document_action_required[z];
        //alert(JSON.stringify(str,null,4));
        if(str == ''){
          finalresult = false;
        }
        /*
        if(this.validateSpecialCharsAndNum(str)){
          finalresult = false;
        }
        
        if(this.validate100charactersallowed(str)){
          finalresult = false;
        }*/
      }
    }


    for(let j=0;j<this.workforce_array.length;j++){
      let tempobj= this.workforce_array.value[j];
      //alert(JSON.stringify(tempobj,null,4));

      for(let z=0;z<tempobj.document_action_required.length;z++){
        let  str= tempobj.document_action_required[z];
        //alert(JSON.stringify(str,null,4));
        if(str == ''){
          finalresult = false;
        }
        /*
        if(this.validateSpecialCharsAndNum(str)){
          finalresult = false;
        }
        
        if(this.validate100charactersallowed(str)){
          finalresult = false;
        }*/
      }
    }


    for(let j=0;j<this.supplies_array.length;j++){
      let tempobj= this.supplies_array.value[j];
      //alert(JSON.stringify(tempobj,null,4));

      for(let z=0;z<tempobj.document_action_required.length;z++){
        let  str= tempobj.document_action_required[z];
        //alert(JSON.stringify(str,null,4));
        if(str == ''){
          finalresult = false;
        }
        /*
        if(this.validateSpecialCharsAndNum(str)){
          finalresult = false;
        }
        
        if(this.validate100charactersallowed(str)){
          finalresult = false;
        }*/
      }
    }


    for(let j=0;j<this.health_array.length;j++){
      let tempobj= this.health_array.value[j];
      //alert(JSON.stringify(tempobj,null,4));

      for(let z=0;z<tempobj.document_action_required.length;z++){
        let  str= tempobj.document_action_required[z];
        //alert(JSON.stringify(str,null,4));
        if(str == ''){
          finalresult = false;
        }
        /*
        if(this.validateSpecialCharsAndNum(str)){
          finalresult = false;
        }
        
        if(this.validate100charactersallowed(str)){
          finalresult = false;
        }*/
      }
    }


    for(let j=0;j<this.finance_array.length;j++){
      let tempobj= this.finance_array.value[j];
      //alert(JSON.stringify(tempobj,null,4));

      for(let z=0;z<tempobj.document_action_required.length;z++){
        let  str= tempobj.document_action_required[z];
        //alert(JSON.stringify(str,null,4));
        if(str == ''){
          finalresult = false;
        }
        /*
        if(this.validateSpecialCharsAndNum(str)){
          finalresult = false;
        }
        
        if(this.validate100charactersallowed(str)){
          finalresult = false;
        }*/
      }
    }



    for(let j=0;j<this.policy_array.length;j++){
      let tempobj= this.policy_array.value[j];
      //alert(JSON.stringify(tempobj,null,4));

      for(let z=0;z<tempobj.document_action_required.length;z++){
        let  str= tempobj.document_action_required[z];
        //alert(JSON.stringify(str,null,4));
        if(str == ''){
          finalresult = false;
        }
        /*
        if(this.validateSpecialCharsAndNum(str)){
          finalresult = false;
        }
        
        if(this.validate100charactersallowed(str)){
          finalresult = false;
        } */
      }
    }

    // stop here if form is invalid
    if (this.dynamicForm.invalid) {      
      finalresult = false;
    }

    if(this.f.form3_chair_person.value == '15'  &&  this.f.form3_chair_person_others.value == null){
      finalresult = false;
    }

    if(this.f.form3_chair_person.value == '15'  &&  this.f.form3_chair_person_others.value == ''){
      finalresult = false;
    }

    if(this.f.form3_chair_person.value == '15'  &&  this.f.form3_chair_person_others.value == 'null'){
      finalresult = false;
    }

    if(!finalresult){
      alert("Form invalid");
      return;
    }

    

  // Year should be in this format
    // 2016-06-15

    let x: any = this.dynamicForm.value;

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.form_3_checkdate = new_year2 + "-" + new_month2 + "-" + new_date2;

    
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    


    x.first_service_document_action_required = this.first_service_document_action_required;
    x.first_workforce_document_action_required = this.first_workforce_document_action_required;
    x.first_supplies_document_action_required = this.first_supplies_document_action_required;
    x.first_health_document_action_required = this.first_health_document_action_required;
    x.first_finance_document_action_required = this.first_finance_document_action_required;
    x.first_policy_document_action_required = this.first_policy_document_action_required;

    //alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
    this._diphHttpClientService.saveform3defineDetails(x, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          console.log(data);
          //this.serverjsonresponse = data;

          // sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));
          this.savedform = true;
          this.router.navigate(['dashboard/form3defineview']);
        },
        error => { console.log(error); alert("Error= " + error); });
  }

  onSubmit() {

    this.submitted = true;
    let finalresult = true;
   

    let no_data = "No Data Available";

    if (this.f.form_3_meeting_venue.value == null || this.f.form_3_meeting_venue.value == '') {
      alert("Venue of the meeting is compulsary");
      return;
    }
  
    if (this.f.form_3_filled_by.value == null || this.f.form_3_filled_by.value == '') {
        alert("Theme Leader of the cycle is compulsary");
        return;
    }

    if(this.f.form3_chair_person.value == '15'  &&  (this.f.form3_chair_person_others.value == null || this.f.form3_chair_person_others.value == '' || this.f.form3_chair_person_others.value == 'null')){
      alert("Enter Chairperson of meeting");
      return;
    }  
    if(this.f.form3_chair_person.value == '' || this.f.form3_chair_person.value == null){
      alert("Enter Chairperson of meeting");
      return;
    } 


    if(this.service_data_not_available){
      
      this.f.service_description_p_f_h_s_p.setValue(no_data);
      this.f.service_possible_s_t_i.setValue(no_data);
      this.first_service_document_action_required.length = 1;
      this.first_service_document_action_required[0] = no_data;
      this.service_array.clear();
    } else {

      finalresult = false;

      if(this.f.service_description_p_f_h_s_p.value == '' || this.f.service_possible_s_t_i.value == ''){
        finalresult = true;
      }
      for(let i=0; i < this.first_service_document_action_required.length; i++){
        if(this.first_service_document_action_required[i] == ''){
          finalresult = true;
        }
      }
      for(let i=0; i < this.service_array.length; i++){

        if(this.service_array.value[i].document_description_p_f_h_s_p == ''){
          finalresult = true;
        }
        if(this.service_array.value[i].document_possible_s_t_i == ''){
          finalresult = true;
        }

        for(let k=0; k < this.service_array.value[i].document_action_required.length ; k++){
          
          if(this.service_array.value[i].document_action_required[k] == ''){
            finalresult = true;
          }
        }
      }
      
      if(finalresult){
        alert("Fill all the points of Service Delivery");
        return;
      }

    } 

    if(this.workforce_data_not_available){
      
      this.f.workforce_service_description_p_f_h_s_p.setValue(no_data);
      this.f.workforce_service_possible_s_t_i.setValue(no_data);
      this.first_workforce_document_action_required.length = 1;
      this.first_workforce_document_action_required[0] = no_data;
      this.workforce_array.clear();
    } else {

      finalresult = false;

      if(this.f.workforce_service_description_p_f_h_s_p.value == '' || this.f.workforce_service_possible_s_t_i.value == ''){
        finalresult = true;
      }
      for(let i=0; i < this.first_workforce_document_action_required.length; i++){
        if(this.first_workforce_document_action_required[i] == ''){
          finalresult = true;
        }
      }
      for(let i=0; i < this.workforce_array.length; i++){

        if(this.workforce_array.value[i].document_description_p_f_h_s_p == ''){
          finalresult = true;
        }
        if(this.workforce_array.value[i].document_possible_s_t_i == ''){
          finalresult = true;
        }

        for(let k=0; k < this.workforce_array.value[i].document_action_required.length ; k++){
          
          if(this.workforce_array.value[i].document_action_required[k] == ''){
            finalresult = true;
          }
        }
      }
      
      if(finalresult){
        alert("Fill all the points of Workforce");
        return;
      }

    } 

    if(this.supply_data_not_available){
      
      this.f.supplies_service_description_p_f_h_s_p.setValue(no_data);
      this.f.supplies_service_possible_s_t_i.setValue(no_data);
      this.first_supplies_document_action_required.length = 1;
      this.first_supplies_document_action_required[0] = no_data;
      this.supplies_array.clear();
    } else {

      finalresult = false;

      if(this.f.supplies_service_description_p_f_h_s_p.value == '' || this.f.supplies_service_possible_s_t_i.value == ''){
        finalresult = true;
      }
      for(let i=0; i < this.first_supplies_document_action_required.length; i++){
        if(this.first_supplies_document_action_required[i] == ''){
          finalresult = true;
        }
      }
      for(let i=0; i < this.supplies_array.length; i++){

        if(this.supplies_array.value[i].document_description_p_f_h_s_p == ''){
          finalresult = true;
        }
        if(this.supplies_array.value[i].document_possible_s_t_i == ''){
          finalresult = true;
        }

        for(let k=0; k < this.supplies_array.value[i].document_action_required.length ; k++){
          
          if(this.supplies_array.value[i].document_action_required[k] == ''){
            finalresult = true;
          }
        }
      }
      
      if(finalresult){
        alert("Fill all the points of Supplies & Technology");
        return;
      }

    } 
    
    if(this.health_data_not_available){
      
      this.f.health_service_description_p_f_h_s_p.setValue(no_data);
      this.f.health_service_possible_s_t_i.setValue(no_data);
      this.first_health_document_action_required.length = 1;
      this.first_health_document_action_required[0] = no_data;
      this.health_array.clear();
    }  else {

      finalresult = false;

      if(this.f.health_service_description_p_f_h_s_p.value == '' || this.f.health_service_possible_s_t_i.value == ''){
        finalresult = true;
      }
      for(let i=0; i < this.first_health_document_action_required.length; i++){
        if(this.first_health_document_action_required[i] == ''){
          finalresult = true;
        }
      }
      for(let i=0; i < this.health_array.length; i++){

        if(this.health_array.value[i].document_description_p_f_h_s_p == ''){
          finalresult = true;
        }
        if(this.health_array.value[i].document_possible_s_t_i == ''){
          finalresult = true;
        }

        for(let k=0; k < this.health_array.value[i].document_action_required.length ; k++){
          
          if(this.health_array.value[i].document_action_required[k] == ''){
            finalresult = true;
          }
        }
      }
      
      if(finalresult){
        alert("Fill all the points of Health Information");
        return;
      }

    }

    if(this.finance_data_not_available){
      
      this.f.finance_service_description_p_f_h_s_p.setValue(no_data);
      this.f.finance_service_possible_s_t_i.setValue(no_data);
      this.first_finance_document_action_required.length = 1;
      this.first_finance_document_action_required[0] = no_data;
      this.finance_array.clear();
    } else {

      finalresult = false;

      if(this.f.finance_service_description_p_f_h_s_p.value == '' || this.f.finance_service_possible_s_t_i.value == ''){
        finalresult = true;
      }
      for(let i=0; i < this.first_finance_document_action_required.length; i++){
        if(this.first_finance_document_action_required[i] == ''){
          finalresult = true;
        }
      }
      for(let i=0; i < this.finance_array.length; i++){

        if(this.finance_array.value[i].document_description_p_f_h_s_p == ''){
          finalresult = true;
        }
        if(this.finance_array.value[i].document_possible_s_t_i == ''){
          finalresult = true;
        }

        for(let k=0; k < this.finance_array.value[i].document_action_required.length ; k++){
          
          if(this.finance_array.value[i].document_action_required[k] == ''){
            finalresult = true;
          }
        }
      }
      
      if(finalresult){
        alert("Fill all the points of Finance");
        return;
      }

    }

    if(this.policy_data_not_available){
      
      this.f.policy_service_description_p_f_h_s_p.setValue(no_data);
      this.f.policy_service_possible_s_t_i.setValue(no_data);
      this.first_policy_document_action_required.length = 1;
      this.first_policy_document_action_required[0] = no_data;
      this.policy_array.clear();
    } else {

      finalresult = false;

      if(this.f.policy_service_description_p_f_h_s_p.value == '' || this.f.policy_service_possible_s_t_i.value == ''){
        finalresult = true;
      }
      for(let i=0; i < this.first_policy_document_action_required.length; i++){
        if(this.first_policy_document_action_required[i] == ''){
          finalresult = true;
        }
      }
      for(let i=0; i < this.policy_array.length; i++){

        if(this.policy_array.value[i].document_description_p_f_h_s_p == ''){
          finalresult = true;
        }
        if(this.policy_array.value[i].document_possible_s_t_i == ''){
          finalresult = true;
        }

        for(let k=0; k < this.policy_array.value[i].document_action_required.length ; k++){
          
          if(this.policy_array.value[i].document_action_required[k] == ''){
            finalresult = true;
          }
        }
      }
      
      if(finalresult){
        alert("Fill all the points of Policy/Governance");
        return;
      }

    }


    let x: any = this.dynamicForm.value;

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.form_3_checkdate = new_year2 + "-" + new_month2 + "-" + new_date2;

    
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    


    x.first_service_document_action_required = this.first_service_document_action_required;
    x.first_workforce_document_action_required = this.first_workforce_document_action_required;
    x.first_supplies_document_action_required = this.first_supplies_document_action_required;
    x.first_health_document_action_required = this.first_health_document_action_required;
    x.first_finance_document_action_required = this.first_finance_document_action_required;
    x.first_policy_document_action_required = this.first_policy_document_action_required;

    this.completeClicked = true;

    this._diphHttpClientService.saveform3defineDetails(x, login_district, login_cycle, login_year, login_userid, "1")
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


  partialSave(){

  
  if (this.f.form_3_meeting_venue.value == null || this.f.form_3_meeting_venue.value == '') {
      alert("Venue of the meeting is compulsary");
      return;
  }else if(this.validate100charactersallowed(this.f.form_3_meeting_venue.value)){
    alert("Venue of the meeting can not exceed 100 characters");
    return;
  }
  
  if (this.f.form_3_filled_by.value == null || this.f.form_3_filled_by.value == '') {
      alert("Theme Leader of the cycle is compulsary");
      return;
  }else if(this.validate100charactersallowed(this.f.form_3_filled_by.value)){
    alert("Theme Leader of the cycle can not exceed 100 characters");
    return;
  }
  
  if (this.f.form3_chair_person.value != "15" && (this.f.form3_chair_person.value == null || this.f.form3_chair_person.value == '')) {
      alert("Chairperson of the meeting is compulsary");
      return;
  }
  if (this.f.form3_chair_person.value == "15" && (this.f.form3_chair_person_others.value == "null" || this.f.form3_chair_person_others.value == "")) {
      alert("Chairperson of the meeting is compulsary");
      return;
  }
  if (this.f.form3_chair_person.value == "15" && this.f.form3_chair_person_others.value == null) {
      alert("Chairperson of the meeting is compulsary");
      return;
  }  

  for(let i=0;i<this.service_array.length;i++){
    let tempobj = this.service_array.value[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Service Delivery description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Service Delivery description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.first_service_document_action_required.length;i++){
    let action_point = this.first_service_document_action_required[i];
    if(action_point == null || action_point == ''){
      alert("Service Delivery action point is expanded but not filled");
      return;
    }
}

  for(let i=0;i<this.workforce_array.length;i++){
    let tempobj = this.workforce_array.value[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Workforce description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Workforce description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.first_workforce_document_action_required.length;i++){
    let action_point = this.first_workforce_document_action_required[i];
    if(action_point == null || action_point == ''){
      alert("Workforce action point is expanded but not filled");
      return;
    }
}
  
  for(let i=0;i<this.supplies_array.length;i++){
    let tempobj = this.supplies_array.value[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Supplies & Technology description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Supplies & Technology description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.first_supplies_document_action_required.length;i++){
    let action_point = this.first_supplies_document_action_required[i];
    if(action_point == null || action_point == ''){
      alert("Supplies & Technology action point is expanded but not filled");
      return;
    }
}

  for(let i=0;i<this.health_array.length;i++){
    let tempobj = this.health_array.value[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Health Information description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Health Information description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.first_health_document_action_required.length;i++){
    let action_point = this.first_health_document_action_required[i];
    if(action_point == null || action_point == ''){
      alert("Health Information action point is expanded but not filled");
      return;
    }
}

  for(let i=0;i<this.finance_array.length;i++){
    let tempobj = this.finance_array.value[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Finance description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Finance description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.first_finance_document_action_required.length;i++){
    let action_point = this.first_finance_document_action_required[i];
    if(action_point == null || action_point == ''){
      alert("Finance action point is expanded but not filled");
      return;
    }
}

  for(let i=0;i<this.policy_array.length;i++){
    let tempobj = this.policy_array.value[i];
    let problem = tempobj["document_description_p_f_h_s_p"];
    if(problem == null || problem == ''){
      alert("Policy/Governance description is expanded but not filled");
      return;
    }/*else if(this.validate100charactersallowed(problem)){
      alert("Policy/Governance description can not exceed 100 characters");
      return;
    }*/
  }

  for(let i=1;i<this.first_policy_document_action_required.length;i++){
    let action_point = this.first_policy_document_action_required[i];
    if(action_point == null || action_point == ''){
      alert("Policy/Governance action point is expanded but not filled");
      return;
    }
}

  for(let j=0;j<this.service_array.length;j++){
    let tempobj= this.service_array.value[j];   

    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Service Delivery action point is expanded but not filled");
        return;
      }
    }
  }


  for(let j=0;j<this.workforce_array.length;j++){
    let tempobj= this.workforce_array.value[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Workforce description is expanded but not filled");
       return;
      }
    }
  }


  for(let j=0;j<this.supplies_array.length;j++){
    let tempobj= this.supplies_array.value[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Supplies & Technology description is expanded but not filled");
        return;
      }
    }
  }


  for(let j=0;j<this.health_array.length;j++){
    let tempobj= this.health_array.value[j];
    

    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Health Information description is expanded but not filled");
        return;
      }
    }
  }


  for(let j=0;j<this.finance_array.length;j++){
    let tempobj= this.finance_array.value[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Finance description is expanded but not filled");
        return;
      }
    }
  }



  for(let j=0;j<this.policy_array.length;j++){
    let tempobj= this.policy_array.value[j];
    
    for(let z=1;z<tempobj.document_action_required.length;z++){
      let  str= tempobj.document_action_required[z];
      
      if(str == ''){
        alert("Policy/Governance is expanded but not filled");
        return;
      }
    }
  }


  let x: any = this.dynamicForm.value;

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.form_3_checkdate = new_year2 + "-" + new_month2 + "-" + new_date2;
	
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');
    let login_userid = sessionStorage.getItem('userid');    


    x.first_service_document_action_required = this.first_service_document_action_required;
    x.first_workforce_document_action_required = this.first_workforce_document_action_required;
    x.first_supplies_document_action_required = this.first_supplies_document_action_required;
    x.first_health_document_action_required = this.first_health_document_action_required;
    x.first_finance_document_action_required = this.first_finance_document_action_required;
    x.first_policy_document_action_required = this.first_policy_document_action_required;

    this.completeClicked = true;
    
    this._diphHttpClientService.saveform3defineDetails(x, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => { 
          this.completeClicked = false;         
          this.savedform = true;
          this.router.navigate(['dashboard/form3defineview']);
        },
        error => { this.completeClicked = false; alert("Error= " + error); });
  
  }

  show_part2_also() {

  }


  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }

}
