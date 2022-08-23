import { Component, OnInit, Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Injectable({
  providedIn: 'root'
})

@Component({
  selector: 'app-form1-a',
  templateUrl: './form1-a.component.html',
  styleUrls: ['./form1-a.component.css']
})
export class Form1aComponent implements OnInit {

  completeClicked : boolean = false;

  form1aobj: form1aModel = new form1aModel("", []);
  submitted = false;
  savedform = false;
  public serverjsonresponse: any;
  date_max= new Date(); 
  date = new FormControl(new Date());
  date_2 = new FormControl(new Date());
  minDate = new Date("12,01,2019");
  public verified_by_name_from_Db=[];

  dynamicForm: FormGroup;
  //submitted = false;

  constructor(location: PlatformLocation,private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
    history.pushState(null, null, location.href);
    location.onPopState(() => {
      history.pushState(null, null, location.href);
      return false;
    });

    let user = sessionStorage.getItem('username');
    if(user==null || user.length==0){
      this.router.navigate(['login']);
    }

    this.submitted = false;
    this.form1aobj = new form1aModel("", []);

  }

  ngOnInit() {

    this.dynamicForm = this.formBuilder.group({
      date_of_verification: ['2019-10-03'],
      //filled_by: ['',  [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]], 
      filled_by: ['',  [Validators.required, Validators.maxLength(5000) ]], 
      verified_by_name: ['',  [Validators.required]],
      verified_by_other_actual_name:new FormControl(),
      //state_policy_first_doc_val: ['',  [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      state_policy_first_doc_val: ['',  [Validators.required,  Validators.maxLength(5000) ]],
      state_policy_first_availability: ['', Validators.required],
      state_policy_first_d_source: [''],      
      //district_policy_first_doc_val: ['',  [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      district_policy_first_doc_val: ['',  [Validators.required,  Validators.maxLength(5000) ]],
      district_policy_first_availability: ['', Validators.required],
      district_policy_first_d_source: [''], 
      health_dept_first_doc_val: ['',  [Validators.required, Validators.maxLength(5000) ]], 
      health_dept_first_availability: ['', Validators.required],
      health_dept_first_d_source: [''], 
      non_health_dept_first_doc_val: ['',  [Validators.required, Validators.maxLength(5000) ]], 
      non_health_dept_first_availability: ['', Validators.required],
      non_health_dept_first_d_source: [''], 
      private_sec_first_doc_val: ['',  [Validators.required, Validators.maxLength(5000) ]], 
      private_sec_first_availability: ['', Validators.required],
      private_sec_first_d_source: [''], 
      large_scale_district_first_doc_val: ['',  [Validators.required, Validators.maxLength(5000) ]], 
      large_scale_district_first_availability: ['', Validators.required],
      large_scale_district_first_d_source: [''],
      numberOfTickets: ['1', Validators.required],
      numberOfdistrictrow: ['1', Validators.required],
      numberOfhealthdeptrow: ['1', Validators.required],
      numberOfnonhealthdeptrow: ['1', Validators.required],
      numberOfprivsecrow: ['1', Validators.required],
      numberOflargescaledistrictrow: ['1', Validators.required],
      large_scale_district_array: new FormArray([]),
      private_sec_array: new FormArray([]),
      non_health_dept_array: new FormArray([]), 
      health_dept_array: new FormArray([]),
      district_policy_array: new FormArray([]),
      state_policy_array: new FormArray([])      
    });

    // this.sp.push(this.formBuilder.group({
    //   document_val: ['',  [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]], 
    //   document_availability: ['', Validators.required],
    //   document_source: ['']
    // })); 

    


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



        setTimeout(() => 
            {
              this.autofill_cycle_1();
            }, 1000);
       

    $(document).ready(function () {


    });
  }

  // Autofill cycle 2,3 and 4 from cycle 1 of same year, if cycle 1 is new and form 1 and form 5 filled.
  autofill_cycle_1(){

    let district_id: string, cycle_id: string, year: string, user_id: string;
    let current_cycle: number;

    district_id = "" + sessionStorage.getItem('district');
    current_cycle = parseInt(sessionStorage.getItem('cycle'));
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    if(current_cycle > 1){

      let ans = confirm("Do you want to auto-fill Form 1A and Form 1B from cycle 1 ? ");
    
    if(ans){
     

    this._diphHttpClientService.getSavedForm1ADetails(district_id, "1", year, user_id)
      .subscribe(
        data => {
          
          if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {

            alert("Form 1A is not filled of cycle 1 and year "+year+"\n\n"+"[Note: All Primary forms of cycle 1 must be filled for auto-complete functionality.]");
            return;

          } else {

            
            
                  this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, "1", year, user_id)
                  .subscribe(
                    data5 => {

                      if (data5.date_of_meeting == null && data5.venue_of_meeting == null && data5.chairperson_of_meeting == null) {
                      
                        alert("Form 5 not filled of cycle 1 and year "+year+"\n\n"+"[Note: All Primary forms of cycle 1 must be filled for auto-complete functionality.]");
                        return;

                      } else {



                        this._diphHttpClientService.autofil_form1A_form1B(district_id, cycle_id, year, user_id).subscribe(
                          dataSave => {
                              //console.log(data);
                              //this.serverjsonresponse = data;
                              //return;          
                              //sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));
                              //this.savedform = true;
                              this.router.navigate(['dashboard']);
                         },
                        error => {          
                          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
                          this.router.navigate(['dashboard']);
                        });



                        
                      }
                    },
                    error => {
                      console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
                    });        





          }         
        },
        error => {
          console.log(error); 
          alert("Error: "+error);
        });

      }// end of confirm box

      }// end of if

  }


  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }

 

  onSubmit() {

    this.submitted = true;


    for(let i=0;i<this.sp.length;i++){
      let tempobj = this.sp.value[i];
      
      let document_val = tempobj["document_val"];
      let document_availability = tempobj["document_availability"];
      let document_source = tempobj["document_source"]; 

      if(document_availability == '1' && document_source == '' ){
        alert("Form invalid");
        return;
      }

      if(document_availability == '1' && this.validate100charactersallowed(document_source) ){
        alert("Form invalid");
        return;
      }

    }

    
    for(let i=0;i<this.dp.length;i++){
      let tempobj = this.dp.value[i];
      
      let document_val = tempobj["document_val"];
      let document_availability = tempobj["document_availability"];
      let document_source = tempobj["document_source"]; 

      if(document_availability == '1' && document_source == '' ){
        alert("Form invalid");
        return;
      }

      if(document_availability == '1' && this.validate100charactersallowed(document_source) ){
        alert("Form invalid");
        return;
      }

    }


    
    for(let i=0;i<this.hp.length;i++){
      let tempobj = this.hp.value[i];
      
      let document_val = tempobj["document_val"];
      let document_availability = tempobj["document_availability"];
      let document_source = tempobj["document_source"]; 

      if(document_availability == '1' && document_source == '' ){
        alert("Form invalid");
        return;
      }

      if(document_availability == '1' && this.validate100charactersallowed(document_source) ){
        alert("Form invalid");
        return;
      }

    }


    
    for(let i=0;i<this.nhp.length;i++){ 
      let tempobj = this.nhp.value[i];
      
      let document_val = tempobj["document_val"];
      let document_availability = tempobj["document_availability"];
      let document_source = tempobj["document_source"]; 

      if(document_availability == '1' && document_source == '' ){
        alert("Form invalid");
        return;
      }

      if(document_availability == '1' && this.validate100charactersallowed(document_source) ){
        alert("Form invalid");
        return;
      }

    }


    
    for(let i=0;i<this.pv.length;i++){
      let tempobj = this.pv.value[i];
      
      let document_val = tempobj["document_val"];
      let document_availability = tempobj["document_availability"];
      let document_source = tempobj["document_source"]; 

      if(document_availability == '1' && document_source == '' ){
        alert("Form invalid");
        return;
      }

      if(document_availability == '1' && this.validate100charactersallowed(document_source) ){
        alert("Form invalid");
        return;
      }

    }


    for(let i=0;i<this.lsd.length;i++){
      let tempobj = this.lsd.value[i];
      
      let document_val = tempobj["document_val"];
      let document_availability = tempobj["document_availability"];
      let document_source = tempobj["document_source"]; 

      if(document_availability == '1' && document_source == '' ){
        alert("Form invalid");
        return;
      }

      if(document_availability == '1' && this.validate100charactersallowed(document_source) ){
        alert("Form invalid");
        return;
      } 

    } 

    //alert("Form submitted New");
    // stop here if form is invalid
    if (this.dynamicForm.invalid) {
      alert("Form invalid");
      return;
    }

    //alert("this.f.verified_by_name.value = "+this.f.verified_by_name.value+",, this.f.verified_by_other_actual_name.value  =  "+this.f.verified_by_other_actual_name.value);

    if(this.f.verified_by_name.value == "15" && this.f.verified_by_other_actual_name.value == ""){ 
      alert("Form invalid");
      return;
    }
    if(this.f.verified_by_name.value == "15" && this.f.verified_by_other_actual_name.value == "null"){ 
      alert("Form invalid");
      return;
    }
    if(this.f.verified_by_name.value == "15" && this.f.verified_by_other_actual_name.value == null){ 
      alert("Form invalid");
      return;
    }

    if(this.f.state_policy_first_availability.value   == '1' &&  this.f.state_policy_first_d_source.value == ''){
      alert("Form invalid");
      return;
    }//validate100charactersallowed

    if(this.f.state_policy_first_availability.value   == '1' &&  this.validate100charactersallowed(this.f.state_policy_first_d_source.value)){
      alert("Form invalid");  
      return;
    }


    if(this.f.district_policy_first_availability.value   == '1' &&  this.f.district_policy_first_d_source.value == ''){
      alert("Form invalid");
      return;
    }

    if(this.f.district_policy_first_availability.value   == '1' &&  this.validate100charactersallowed(this.f.district_policy_first_d_source.value)){
      alert("Form invalid");  
      return;
    }


    if(this.f.health_dept_first_availability.value   == '1' &&  this.f.health_dept_first_d_source.value == ''){
      alert("Form invalid");
      return;
    }

    if(this.f.health_dept_first_availability.value   == '1' &&  this.validate100charactersallowed(this.f.health_dept_first_d_source.value)){
      alert("Form invalid");  
      return;
    } 


    if(this.f.non_health_dept_first_availability.value   == '1' &&  this.f.non_health_dept_first_d_source.value == ''){
      alert("Form invalid");
      return;
    }

    if(this.f.non_health_dept_first_availability.value   == '1' &&  this.validate100charactersallowed(this.f.non_health_dept_first_d_source.value)){
      alert("Form invalid");  
      return;
    } 

    if(this.f.private_sec_first_availability.value   == '1' &&  this.f.private_sec_first_d_source.value == ''){
      alert("Form invalid"); 
      return;
    }

    if(this.f.private_sec_first_availability.value   == '1' &&  this.validate100charactersallowed(this.f.private_sec_first_d_source.value)){
      alert("Form invalid");  
      return;
    } 

    if(this.f.large_scale_district_first_availability.value   == '1' &&  this.f.large_scale_district_first_d_source.value == ''){
      alert("Form invalid");  
      return;
    }

    if(this.f.large_scale_district_first_availability.value   == '1' &&  this.validate100charactersallowed(this.f.large_scale_district_first_d_source.value)){
      alert("Form invalid");  
      return;
    }   

    

    

    //convert dynamicForm of type FormBuilder to   simple js Object of type {};
    let x:any = this.dynamicForm.value;


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(x, null, 4));

    // display form values on success
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    this.completeClicked = true;

    this._diphHttpClientService.saveform1aDetails(x,login_district,login_cycle,login_year,login_userid,"1")
      .subscribe(
        data => {
          //console.log(data);
          this.serverjsonresponse = data;
          //return;          
          sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));
          this.savedform = true;
          this.completeClicked = false;
          this.router.navigate(['dashboard/form1aview']);
        },
        error => { 
		this.completeClicked = false;         
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
        });
    // this.save();
  }

  partialSave(){
    
    //this.submitted = true;

    if(this.f.filled_by.value == null || this.f.filled_by.value == ""){ 
      alert("Venue of the meeting is compulsary");
      return;
    }

    if(this.f.verified_by_name.value != "15" && (this.f.verified_by_name.value == null || this.f.verified_by_name.value == '')){ 
      alert("Chairperson of the meeting is compulsary");
      return;
    }
    if(this.f.verified_by_name.value == "15" && (this.f.verified_by_other_actual_name.value == "null" || this.f.verified_by_other_actual_name.value == "")){ 
      alert("Chairperson of the meeting is compulsary");
      return;
    }
    if(this.f.verified_by_name.value == "15" && this.f.verified_by_other_actual_name.value == null){ 
      alert("Chairperson of the meeting is compulsary");
      return;
    }

    for(let i=0;i<this.sp.length;i++){
      let tempobj = this.sp.value[i];
      
      let document_val = tempobj["document_val"];

      if(document_val == ''){
        alert("State Policy is document expanded but not filled");
        return;
      }

    }

    
    for(let i=0;i<this.dp.length;i++){
      let tempobj = this.dp.value[i];
      
      let document_val = tempobj["document_val"];
      
      if(document_val == ''){
        alert("District Policy document is expanded but not filled");
        return;
      }

    }


    
    for(let i=0;i<this.hp.length;i++){
      let tempobj = this.hp.value[i];
      
      let document_val = tempobj["document_val"];
      
      if(document_val == ''){
        alert("Health department document is expanded but not filled");
        return;
      }

    }


    
    for(let i=0;i<this.nhp.length;i++){ 
      let tempobj = this.nhp.value[i];
      
      let document_val = tempobj["document_val"];

      if(document_val == ''){
        alert("Non-Health department document is expanded but not filled");
        return;
      }

    }


    
    for(let i=0;i<this.pv.length;i++){
      let tempobj = this.pv.value[i];
      
      let document_val = tempobj["document_val"];

      if(document_val == ''){
        alert("Private Sector document is expanded but not filled");
        return;
      }

    }


    for(let i=0;i<this.lsd.length;i++){
      let tempobj = this.lsd.value[i];
      
      let document_val = tempobj["document_val"];

      if(document_val == ''){
        alert("Large Scale District Level Surveys document is expanded but not filled");
        return;
      }

    } 

    let x:any = this.dynamicForm.value;


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(x, null, 4));

    // display form values on success
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    this.completeClicked = true;

    this._diphHttpClientService.saveform1aDetails(x,login_district,login_cycle,login_year,login_userid, "0")
      .subscribe(
        data => {
          //console.log(data);
          this.serverjsonresponse = data;
          //return;          
          sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));
          this.savedform = true;
          this.completeClicked = false;
          this.router.navigate(['dashboard/form1aview']);
        },
        error => {     
	  this.completeClicked = false;     
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
        });

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



  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  //get t() { return this.f.tickets as FormArray; }
  get sp() { return this.f.state_policy_array as FormArray; }
  get dp() { return this.f.district_policy_array as FormArray; }
  get hp() { return this.f.health_dept_array as FormArray; }
  get nhp() { return this.f.non_health_dept_array as FormArray; }
  get pv() { return this.f.private_sec_array as FormArray; }
  get lsd() { return this.f.large_scale_district_array as FormArray; }


  onClickRemoveLargeScaleDistrict(e){
    this.lsd.removeAt(e);
    this.f.numberOflargescaledistrictrow.setValue(parseInt(this.f.numberOflargescaledistrictrow.value) - 1);
  }

  onClickRemovePrivSec(e){
    this.pv.removeAt(e);
    this.f.numberOfprivsecrow.setValue(parseInt(this.f.numberOfprivsecrow.value) - 1);
  }

  onClickRemoveNonHealthDept(e){
    this.nhp.removeAt(e);
    this.f.numberOfnonhealthdeptrow.setValue(parseInt(this.f.numberOfnonhealthdeptrow.value) - 1);
  }


  onClickRemoveHealthDept(e){
    this.hp.removeAt(e);
    this.f.numberOfhealthdeptrow.setValue(parseInt(this.f.numberOfhealthdeptrow.value) - 1);
  }

  onClickRemoveDistrict(e){
    this.dp.removeAt(e);
    this.f.numberOfdistrictrow.setValue(parseInt(this.f.numberOfdistrictrow.value) - 1);
  }

  onClickRemove(e) {  
    this.sp.removeAt(e);
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) - 1);
  }

  // onClickRemove_old(e) {

  //   //$(e.target).append("Abhishek");
  //   //console.log(e);
  //   this.t.removeAt(e);
  //   this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) - 1);
  //   // $(e.target).closest('tr').remove();
  // }

  onClickAddLargeScaleDistrict(val) {
    this.f.numberOflargescaledistrictrow.setValue(parseInt(this.f.numberOflargescaledistrictrow.value) + val);
    const numberOflargescaledistrictrow = this.f.numberOflargescaledistrictrow.value - 1 || 0;
    if (this.lsd.length < numberOflargescaledistrictrow) {
      for (let j = this.lsd.length; j < numberOflargescaledistrictrow; j++) {
        this.lsd.push(this.formBuilder.group({
          document_val: ['',  [Validators.required, Validators.maxLength(5000) ]],  
          document_availability: ['', Validators.required],
          document_source: ['']
        }));
      }
    }
  }



  onClickAddRowPrivSec(val) {
    this.f.numberOfprivsecrow.setValue(parseInt(this.f.numberOfprivsecrow.value) + val);
    const numberOfprivsecrow = this.f.numberOfprivsecrow.value - 1 || 0;
    if (this.pv.length < numberOfprivsecrow) {
      for (let j = this.pv.length; j < numberOfprivsecrow; j++) {
        this.pv.push(this.formBuilder.group({
          document_val: ['',  [Validators.required, Validators.maxLength(5000) ]],  
          document_availability: ['', Validators.required],
          document_source: ['']
        }));
      }
    }
  }
  
  onClickAddRowNonHealthDept(val) {
    this.f.numberOfnonhealthdeptrow.setValue(parseInt(this.f.numberOfnonhealthdeptrow.value) + val);
    const numberOfnonhealthdeptrow = this.f.numberOfnonhealthdeptrow.value - 1 || 0;
    if (this.nhp.length < numberOfnonhealthdeptrow) {
      for (let j = this.nhp.length; j < numberOfnonhealthdeptrow; j++) {
        this.nhp.push(this.formBuilder.group({
          document_val: ['',  [Validators.required, Validators.maxLength(5000) ]],  
          document_availability: ['', Validators.required],
          document_source: ['']
        }));
      }
    }
  }



  onClickAddRowHealthDept(val) {
    this.f.numberOfhealthdeptrow.setValue(parseInt(this.f.numberOfhealthdeptrow.value) + val);
    const numberOfhealthdeptrow = this.f.numberOfhealthdeptrow.value - 1 || 0;
    if (this.hp.length < numberOfhealthdeptrow) {
      for (let j = this.hp.length; j < numberOfhealthdeptrow; j++) {
        this.hp.push(this.formBuilder.group({
          document_val: ['',  [Validators.required, Validators.maxLength(5000) ]],  
          document_availability: ['', Validators.required],
          document_source: ['']
        }));
      }
    }
  }

  onClickAddRowDistrict(val) {
    this.f.numberOfdistrictrow.setValue(parseInt(this.f.numberOfdistrictrow.value) + val);
    const numberOfdistrictrow = this.f.numberOfdistrictrow.value - 1 || 0;
    if (this.dp.length < numberOfdistrictrow) {
      for (let j = this.dp.length; j < numberOfdistrictrow; j++) {
        this.dp.push(this.formBuilder.group({
          //document_val: ['',  [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],  
          document_val: ['',  [Validators.required,  Validators.maxLength(5000) ]],  
          document_availability: ['', Validators.required],
          document_source: ['']
        }));
      }
    }
  }


  onClickAddRow(val) {
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) + val);
    const numberOfTickets = this.f.numberOfTickets.value - 1 || 0;

    if (this.sp.length < numberOfTickets) {
      for (let i = this.sp.length; i < numberOfTickets; i++) {
        this.sp.push(this.formBuilder.group({
          //document_val: ['',  [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]], 
          document_val: ['',  [Validators.required, Validators.maxLength(5000) ]], 
          document_availability: ['', Validators.required],
          document_source: ['']
        }));
      }
    }

  }

  // onClickAddRow_old(val) {
  //   //alert(this.f.numberOfTickets.value);
  //   //console.log(this.f.numberOfTickets);
  //   this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) + val);
  //   const numberOfTickets = this.f.numberOfTickets.value - 1 || 0;
  //   if (this.t.length < numberOfTickets) {
  //     for (let i = this.t.length; i < numberOfTickets; i++) {
  //       this.t.push(this.formBuilder.group({
  //         name: ['', Validators.required],
  //         email: ['', [Validators.required, Validators.email]]
  //       }));
  //     }
  //   }
  // }


  // onReset() {
  //   // reset whole form back to initial state
  //   this.submitted = false;
  //   this.dynamicForm.reset();
  //   this.t.clear();
  // }

  // onClear() {
  //   // clear errors and reset ticket fields
  //   this.submitted = false;
  //   this.t.reset();
  // }


}   
