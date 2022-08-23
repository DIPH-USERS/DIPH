import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation, DatePipe } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Observable } from 'rxjs';
import { form1bEdit } from '../model/form1bEdit';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'app-form5-follow-up',
  templateUrl: './form5-follow-up.component.html',
  styleUrls: ['./form5-follow-up.component.css']
})
export class Form5FollowUpComponent implements OnInit {

  completeClicked : boolean = false;

  @Input() customer: any = {};
  submitted = false;
  nextbtn = false;
  savedform = false;
  public serverjsonresponse: any;
  stakeholder_arr = [];
  form1a_all_doc_arr: any = [];
  primary_stakeholder_arr = [
    { id: '1', name: 'order 1' },
    { id: '2', name: 'order 2' },
    { id: '3', name: 'order 3' },
    { id: '4', name: 'order 4' }
  ];
  secondary_stakeholder_arr = [];
  action_required: any = {};
  public verified_by_name_from_Db = [];

  date = new FormControl(new Date());

  meetingdate_arr = [];

  timezerodate = new FormControl(null);
  timeonedate = new FormControl(null);
  timetwodate = new FormControl(null);
  timethreedate = new FormControl(null);

  service_timeline = [];
  workforce_timeline = [];
  supplies_timeline = [];
  health_timeline = [];
  finance_timeline = [];
  policy_timeline = [];

  date_2 = new FormControl(new Date());
  dynamicForm: FormGroup;


  form4response: any;

  constructor(location: PlatformLocation, private router: Router,
     private formBuilder: FormBuilder, 
     private _diphHttpClientService: DiphHttpClientService) {
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

    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    this.customer.theme_leader = "";

     //Ajax request for form4 Plan
     this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
     .subscribe(
       data => {


         if(data.date_of_meeting  == "" || data.date_of_meeting  == "null" ||  data.date_of_meeting  == null ||  data.date_of_meeting  == "undefined"){
           alert("Step 4 Plan(Form) not filled. Please fill Plan Form to create FollowUp Form"); 
           this.router.navigate(['dashboard']);

           return;
         }
         else{
          this.customer.theme_leader =  data.theme_leader;
         }


       },
       error => {
         console.log(error); alert("Error= " + error);
       });
   //End of Ajax for form4 Plan

  }




  getform1aDetails(district_id: string, cycle_id: string, year: string, user_id: string) {
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

          //console.log("All objects");
          //console.log(JSON.stringify(this.form1a_all_doc_arr, null, 4));
        },
        error => {
          console.log(error); alert("Error= " + error);
        });
  }

  ngOnInit() {

    this.customer.total_coverage_indi = [];

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


    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    let jsonresponse: any;
    let prim_text: string;
    let sec_text: string;
    let prim_id: string;
    let sec_id: string;

    this.getform1aDetails(district_id, cycle_id, year, user_id);

    this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          // if(data.form_2_date_of_meeting  == "" || data.form_2_date_of_meeting  == "null" ||  data.form_2_date_of_meeting  == null ||  data.form_2_date_of_meeting  == "undefined"){
          //   alert("Step 2 Engage(Form) not filled. Please fill Engage, Define and Plan Form to create FollowUp Form"); 
          //   this.router.navigate(['dashboard']);

          //   return;
          // }

          //console.log("data form 2 : "+JSON.stringify(data));

          jsonresponse = data;

          prim_text = jsonresponse.primary_stakeholder_text;
          sec_text = jsonresponse.secondary_stakeholder_text;
          prim_id = jsonresponse.primary_stakeholder_id;
          sec_id = jsonresponse.secondary_stakeholder_id;



          this.stakeholder_arr.push({ id: jsonresponse.primary_stakeholder_id, name: jsonresponse.primary_stakeholder_text });
          this.stakeholder_arr.push({ id: jsonresponse.secondary_stakeholder_id, name: jsonresponse.secondary_stakeholder_text });

          //console.log("data form 2 stakeholder_arr: "+JSON.stringify(this.stakeholder_arr));

          this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
            .subscribe(
              data => {

                // if(data.form_3_checkdate  == "" || data.form_3_checkdate  == "null" ||  data.form_3_checkdate  == null ||  data.form_3_checkdate  == "undefined"){
                //   alert("Step 3 Define(Form) not filled. Please fill Define and Plan Form to create FollowUp Form"); 
                //   this.router.navigate(['dashboard']);

                //   return;
                // }

                let temp_res_obj: any = {};



                temp_res_obj.policy = data.policy_action_required;
                temp_res_obj.finance = data.finance_action_required;
                temp_res_obj.health = data.health_action_required;
                temp_res_obj.supplies = data.supplies_action_required;
                temp_res_obj.workforce = data.workforce_action_required;
                temp_res_obj.service = data.service_action_required;


                this.action_required = temp_res_obj;





                //Ajax request for form4 Plan
                this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
                  .subscribe(
                    data => {


                      // if(data.date_of_meeting  == "" || data.date_of_meeting  == "null" ||  data.date_of_meeting  == null ||  data.date_of_meeting  == "undefined"){
                      //   alert("Step 4 Plan(Form) not filled. Please fill Plan Form to create FollowUp Form"); 
                      //   this.router.navigate(['dashboard']);

                      //   return;
                      // }

                      this.form4response = data;
                      this.customer.date_of_meeting = "";
                      this.customer.venue_of_meeting = "";
                      this.customer.chairperson_of_meeting = "";
                      this.customer.chairperson_of_meeting_others = "";
                      this.customer.theme_id = "";
                      // this.customer.theme_leader = "";
                      this.customer.no_of_meetings_resp_team = "";

                      let temp_obj = {
                        "meeting_no": "1",
                        "meeting_date": "",
                        "stakeholder_meeting": "",
                        "no_of_participants": ""
                      };

                      //Bcoz only one value is at initial
                      this.meetingdate_arr[0] =  new FormControl(new Date());

                      this.customer.meeting_arr = [temp_obj];
                      //this.customer.meeting_arr.push({"meeting_no","meeting_date","stakeholder_meeting","no_of_participants"});
                      this.customer.cov_indicators_arr = [];
                      //this.customer.cov_indicators.push({"cov_indicators","time_zero","time_one","time_three","time_four","timezero_date","timeone_date","timetwo_date","timethree_date"});
                      this.customer.service_action_arr = data.service_action_arr;

                      for (let index in this.customer.service_action_arr) {
                        this.customer.service_action_arr[index].status = "";
                        this.customer.service_action_arr[index].revised_timeline = "";
                        this.customer.service_action_arr[index].change_in_responsibility = "";//progress_indicators
                        //alert(this.customer.service_action_arr[index].sel_indicators);

                        for (let pos in this.customer.service_action_arr[index].sel_indicators) {
                          this.customer.service_action_arr[index].sel_indicators[pos].progress_indicators = "";
                        }
                      }




                      let pos1=0;

                      for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
                        let tempobj = this.customer.service_action_arr[pos];

                        this.service_timeline[pos1] = new FormControl(new Date("" + tempobj.timeline));
                        
                        pos1++;
                      }

                      for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
                        let tempobj = this.customer.service_action_arr[pos];

                        let tempdate = new Date("" + tempobj.timeline);

                        let finalday = tempdate.getDate();
                        let finalmonth = (tempdate.getMonth() + 1);
                        let finalyear = tempdate.getFullYear();

                        let finalfulldate =  finalmonth + "-" + finalday + "-" + finalyear;

                        if(tempobj.timeline == "null" || tempobj.timeline == null)
                          tempobj.timeline = null;
                        else
                          tempobj.timeline = finalfulldate;
                      }


                      this.customer.workforce_action_arr = data.workforce_action_arr;

                      for (let index in this.customer.workforce_action_arr) {
                        this.customer.workforce_action_arr[index].status = "";
                        this.customer.workforce_action_arr[index].revised_timeline = "";
                        this.customer.workforce_action_arr[index].change_in_responsibility = "";
                        for (let pos in this.customer.workforce_action_arr[index].sel_indicators) {
                          this.customer.workforce_action_arr[index].sel_indicators[pos].progress_indicators = "";
                        }
                      }

                      let pos2=0;
                      for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
                        let tempobj = this.customer.workforce_action_arr[pos];          
          
                        this.workforce_timeline[pos2] = new FormControl(new Date("" + tempobj.timeline));          
                        pos2++;
                      }


                      for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
                        let tempobj = this.customer.workforce_action_arr[pos];

                        let tempdate = new Date("" + tempobj.timeline);

                        let finalday = tempdate.getDate();
                        let finalmonth = (tempdate.getMonth() + 1);
                        let finalyear = tempdate.getFullYear();

                        let finalfulldate =  finalmonth + "-" + finalday + "-" + finalyear;

                        if(tempobj.timeline == "null" || tempobj.timeline == null)
                          tempobj.timeline = null;
                        else
                          tempobj.timeline = finalfulldate;
                      }



                      this.customer.supplies_action_arr = data.supplies_action_arr;

                      for (let index in this.customer.supplies_action_arr) {
                        this.customer.supplies_action_arr[index].status = "";
                        this.customer.supplies_action_arr[index].revised_timeline = "";
                        this.customer.supplies_action_arr[index].change_in_responsibility = "";
                        for (let pos in this.customer.supplies_action_arr[index].sel_indicators) {
                          this.customer.supplies_action_arr[index].sel_indicators[pos].progress_indicators = "";
                        }

                      }

                      let pos3=0;
                      for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
                        let tempobj = this.customer.supplies_action_arr[pos];

                        this.supplies_timeline[pos3] = new FormControl(new Date("" + tempobj.timeline));
                        pos3++;
                      }


                      for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
                        let tempobj = this.customer.supplies_action_arr[pos];

                        let tempdate = new Date("" + tempobj.timeline);

                        let finalday = tempdate.getDate();
                        let finalmonth = (tempdate.getMonth() + 1);
                        let finalyear = tempdate.getFullYear();

                        let finalfulldate =  finalmonth + "-" + finalday + "-" + finalyear;

                        if(tempobj.timeline == "null" || tempobj.timeline == null)
                          tempobj.timeline = null;
                        else
                          tempobj.timeline = finalfulldate;
                      }



                      this.customer.health_action_arr = data.health_action_arr;

                      for (let index in this.customer.health_action_arr) {
                        this.customer.health_action_arr[index].status = "";
                        this.customer.health_action_arr[index].revised_timeline = "";
                        this.customer.health_action_arr[index].change_in_responsibility = "";
                        for (let pos in this.customer.health_action_arr[index].sel_indicators) {
                          this.customer.health_action_arr[index].sel_indicators[pos].progress_indicators = "";
                        }

                      }


                      let pos4=0;
                      for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
                        let tempobj = this.customer.health_action_arr[pos];

                        this.health_timeline[pos4] = new FormControl(new Date("" + tempobj.timeline));
                        pos4++;
                      }

                      for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
                        let tempobj = this.customer.health_action_arr[pos];

                        let tempdate = new Date("" + tempobj.timeline);

                        let finalday = tempdate.getDate();
                        let finalmonth = (tempdate.getMonth() + 1);
                        let finalyear = tempdate.getFullYear();

                        let finalfulldate =  finalmonth + "-" + finalday + "-" + finalyear;

                        if(tempobj.timeline == "null" || tempobj.timeline == null)
                          tempobj.timeline = null;
                        else
                          tempobj.timeline = finalfulldate;
                      }


                      this.customer.finance_action_arr = data.finance_action_arr;

                      for (let index in this.customer.finance_action_arr) {
                        this.customer.finance_action_arr[index].status = "";
                        this.customer.finance_action_arr[index].revised_timeline = "";
                        this.customer.finance_action_arr[index].change_in_responsibility = "";
                        for (let pos in this.customer.finance_action_arr[index].sel_indicators) {
                          this.customer.finance_action_arr[index].sel_indicators[pos].progress_indicators = "";
                        }
                      }


                      let pos5=0;
                      for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
                        let tempobj = this.customer.finance_action_arr[pos];

                        this.finance_timeline[pos5] = new FormControl(new Date("" + tempobj.timeline));
                        pos5++;
                      }

                      for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
                        let tempobj = this.customer.finance_action_arr[pos];

                        let tempdate = new Date("" + tempobj.timeline);

                        let finalday = tempdate.getDate();
                        let finalmonth = (tempdate.getMonth() + 1);
                        let finalyear = tempdate.getFullYear();

                        let finalfulldate =  finalmonth + "-" + finalday + "-" + finalyear;

                        if(tempobj.timeline == "null" || tempobj.timeline == null)
                          tempobj.timeline = null;
                        else
                          tempobj.timeline = finalfulldate;
                      }
                      

                      this.customer.policy_action_arr = data.policy_action_arr;

                      for (let index in this.customer.policy_action_arr) {
                        this.customer.policy_action_arr[index].status = "";
                        this.customer.policy_action_arr[index].revised_timeline = "";
                        this.customer.policy_action_arr[index].change_in_responsibility = "";
                        for (let pos in this.customer.policy_action_arr[index].sel_indicators) {
                          this.customer.policy_action_arr[index].sel_indicators[pos].progress_indicators = "";
                        }
                      }

                      let pos6=0;
                      for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
                        let tempobj = this.customer.policy_action_arr[pos];          
          
                        this.policy_timeline[pos6] = new FormControl(new Date("" + tempobj.timeline));
                        pos6++;
                      }


                      for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
                        let tempobj = this.customer.policy_action_arr[pos];

                        let tempdate = new Date("" + tempobj.timeline);

                        let finalday = tempdate.getDate();
                        let finalmonth = (tempdate.getMonth() + 1);
                        let finalyear = tempdate.getFullYear();

                        let finalfulldate =  finalmonth + "-" + finalday + "-" + finalyear;

                        if(tempobj.timeline == "null" || tempobj.timeline == null)
                          tempobj.timeline = null;
                        else
                          tempobj.timeline = finalfulldate;
                      }



                      this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
                        .subscribe(
                          data => {

                            let iphs_theme_name = data.iphs_theme_name;
                            this.customer.theme_id = iphs_theme_name;
                            this.customer.total_coverage_indi = data.total_coverage_indi;



                            for (let index in this.customer.total_coverage_indi) {

                              for(let j=0;j<this.form1a_all_doc_arr.length;j++)
                              {
                                let tempobj= this.form1a_all_doc_arr[j];
                                if(tempobj.doc_db_doc_id == this.customer.total_coverage_indi[index].source){
                                  this.customer.total_coverage_indi[index].new_source  =  tempobj.document_val;
                                }
                              }

                              this.customer.total_coverage_indi[index].source = this.customer.total_coverage_indi[index].source;
                              this.customer.total_coverage_indi[index].time_0 = this.customer.total_coverage_indi[index].data;
                              this.customer.total_coverage_indi[index].time_1 = "0.0";
                              this.customer.total_coverage_indi[index].time_2 = "0.0";
                              this.customer.total_coverage_indi[index].time_3 = "0.0";
                            }

                            this.customer.total_coverage_indi_timezero_date = "";
                            this.customer.total_coverage_indi_timeone_date = "";
                            this.customer.total_coverage_indi_timetwo_date = "";
                            this.customer.total_coverage_indi_timethree_date = "";

                            this.timezerodate = new FormControl(null);
                            this.timeonedate = new FormControl(null);
                            this.timetwodate = new FormControl(null);
                            this.timethreedate = new FormControl(null);


                            // this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
                            //   .subscribe(
                            //     data => {

                                  //alert(data.form_3_filled_by);

                            //       this.customer.theme_leader = data.form_3_filled_by; 
                                  //console.log("Form3 Define");
                                  //console.log(JSON.stringify(data,null,1000));

                            //     },
                            //     error => {
                            //       console.log(error); alert("Error= " + error);
                            //     });


                          },
                          error => {
                            console.log(error); alert("Error= " + error);
                          });






                    },
                    error => {
                      console.log(error); alert("Error= " + error);
                    });
                //End of Ajax for form4 Plan


              },
              error => {
                console.log(error); alert("Error= " + error);
              });


        },
        error => {
          console.log(error); alert("Error= " + error);
        });







    $(document).ready(function () {


    });
  }



  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");    
    if(ans){
      this.router.navigate(['dashboard']);
    }    
  }

  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get dprs() { return this.f.defining_primary_role_section_select as FormArray; }


  onClickAddRow() {

  }

  onClickRemove() {

  }
  onclickAddmeetingRow(i) {
    let temp_obj = {
      "meeting_no": "" + (i + 2),
      "meeting_date": "",
      "stakeholder_meeting": "",
      "no_of_participants": ""
    };
    //temp_obj["meeting_date"]  = new FormControl(new Date());
    this.customer.meeting_arr.push(temp_obj);

    

    this.meetingdate_arr[i+(this.meetingdate_arr.length)] = new FormControl(new Date());
  }

  onclickRemovemeetingRow(i) {
    this.customer.meeting_arr.splice(i, 1);
  }


  
  validateSpecialCharsAndNum(val:string){    
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
  return  (!!(val).replace(/[A-Za-z\s]/gi, '').length);
  }

  validateNumbers(val: string) {
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
    return (!!(val).replace(/[0-9\s]/gi, '').length);
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

  validate200charactersallowed(val:string){
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

  allowDecimalsIntegersOnly(str:string){
    //let str = "1,2"
    let regexp = /^[0-9]+([,.][0-9]+)?$/g;
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

  previousDateCheckTime1(event){    

    let selectedTime0 = this.timezerodate.value;
    
    if(selectedTime0 == null || selectedTime0 == ''){
      alert("Time 0 should be filled before Time 1");
      this.timeonedate = new FormControl(null);
    }
  }

  previousDateCheckTime2(event){    

    let selectedTime1 = this.timeonedate.value;
    
    if(selectedTime1 == null || selectedTime1 == ''){
      alert("Time 1 should be filled before Time 2");
      this.timetwodate = new FormControl(null);
    }
  }

  previousDateCheckTime3(event){    

    let selectedTime2 = this.timetwodate.value;
    
    if(selectedTime2 == null || selectedTime2 == ''){
      alert("Time 2 should be filled before Time 3");
      this.timethreedate = new FormControl(null);
    }
  }

  onOptionsSelected_service(e, array_index){    

    let val = e.target.value;
   
    if(val == "Completed"){
        let tempobj = array_index;      
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let indicator = tempobj['sel_indicators'][z];
          let progress = indicator['progress_indicators'];
          if(!isNaN(progress) && progress != "100"){            
            alert("All indicators progress % should be 100 in 'Service Delivery'");
            e.target.value = "";
            return;
          }      
        }      
    } 
  }

  onOptionsSelected_workforce(e, array_index){    

    let val = e.target.value;
    if(val == "Completed"){      
        let tempobj = array_index;      
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let indicator = tempobj['sel_indicators'][z];
          let progress = indicator['progress_indicators'];
          if(!isNaN(progress) && progress != "100"){            
            alert("All indicators progress % should be 100 in 'Work Force'");
            e.target.value = "";
            return;
          }      
        }      
    } 
  }

  onOptionsSelected_supply(e, array_index){    

    let val = e.target.value;
    if(val == "Completed"){
      let tempobj = array_index;      
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let indicator = tempobj['sel_indicators'][z];
          let progress = indicator['progress_indicators'];
          if(!isNaN(progress) && progress != "100"){            
            alert("All indicators progress % should be 100 in 'Supplies & Technology'");
            e.target.value = "";
            return;
          }      
        }      
    } 
  }

  onOptionsSelected_health(e, array_index){    

    let val = e.target.value;
    if(val == "Completed"){
      let tempobj = array_index;      
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let indicator = tempobj['sel_indicators'][z];
          let progress = indicator['progress_indicators'];
          if(!isNaN(progress) && progress != "100"){            
            alert("All indicators progress % should be 100 in 'Health Information'");
            e.target.value = "";
            return;
          }      
        }      
    } 
  }

  onOptionsSelected_finance(e, array_index){    

    let val = e.target.value;
    if(val == "Completed"){
      let tempobj = array_index;     
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let indicator = tempobj['sel_indicators'][z];
          let progress = indicator['progress_indicators'];
          if(!isNaN(progress) && progress != "100"){            
            alert("All indicators progress % should be 100 in 'Finance'");
            e.target.value = "";
            return;
          }      
        }      
    } 
  }

  onOptionsSelected_policy(e, array_index){    

    let val = e.target.value;
    if(val == "Completed"){
      let tempobj = array_index;     
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let indicator = tempobj['sel_indicators'][z];
          let progress = indicator['progress_indicators'];
          if(!isNaN(progress) && progress != "100"){            
            alert("All indicators progress % should be 100 in 'Policy/Governance'");
            e.target.value = "";
            return;
          }      
        }      
    } 
  }


  minDate =new Date("12,01,2019");

  onSubmit() {
    // Year should be in this format
    // 2016-06-15

    this.submitted = true;

    let finalresult = true;

    let no_data_available = 'No Data Available';
    let default_completed = 'Completed';

    if(this.customer.venue_of_meeting == ''){
      finalresult = false;
    }
    else if(this.validate100charactersallowed(this.customer.venue_of_meeting)  ){
      finalresult = false;
    }
    else if(this.customer.chairperson_of_meeting == ''){
      finalresult = false;
    }
    else if(this.customer.no_of_meetings_resp_team == ''){
      finalresult = false;
    }
    else if(this.validateNumbers(this.customer.no_of_meetings_resp_team)   ){
      finalresult = false;
    }
   
    if(this.customer.chairperson_of_meeting == '15' && this.customer.chairperson_of_meeting_others == ''){
      finalresult = false;
    }
    else if(this.customer.chairperson_of_meeting == '15' &&  this.validate100charactersallowed(this.customer.chairperson_of_meeting_others)){
      finalresult = false;
    }

    

    //customer.chairperson_of_meeting_others

    for(let j=0;j<this.customer.meeting_arr.length;j++){
      let tempobj = this.customer.meeting_arr[j];

      if(tempobj['stakeholder_meeting']==''){
        finalresult = false;
      }
      else if(tempobj['no_of_participants']==''){
        finalresult = false;
      }
      else if(this.validateNumbers(tempobj['no_of_participants'])   ){
        finalresult = false;
      }
      else if( this.validate200charactersallowed(tempobj['stakeholder_meeting'])){
        finalresult = false;
      }
    }


    

    for(let pos=0;pos<this.customer.total_coverage_indi.length;pos++){

      let tempobj = this.customer.total_coverage_indi[pos];

      if(tempobj.time_0==''){
        finalresult = false;
      }
      else if(tempobj.time_1==''){
        finalresult = false;
      }
      else if(tempobj.time_2==''){
        finalresult = false;
      }
      else if(tempobj.time_3==''){
        finalresult = false;
      }
      /* 
      else if(this.allowDecimalsIntegersOnly(tempobj.time_1)){
        finalresult = false;
      } 
      else if(this.allowDecimalsIntegersOnly(tempobj.time_2)){
        finalresult = false;
      } 
      else if(this.allowDecimalsIntegersOnly(tempobj.time_3)){
        finalresult = false;
      }
      
      else if(this.allowIntegersUpto100(tempobj.time_1)){
        finalresult = false;
      } 
      else if(this.allowIntegersUpto100(tempobj.time_2)){
        finalresult = false;
      }
      else if(this.allowIntegersUpto100(tempobj.time_3)){
        finalresult = false;
      }
      */
    }

    



    for(let j=0;j<this.customer.service_action_arr.length;j++){

      if(this.customer.service_action_arr[j]['document_action_required'] == no_data_available){
        this.customer.service_action_arr[j]['status'] = default_completed;
      }

      let tempobj = this.customer.service_action_arr[j];

      if(tempobj['status'] == ''){
        finalresult = false;
      }/*
      else if(tempobj['change_in_responsibility'] == ''){
        finalresult = false;
      }*/      
      else if( tempobj['change_in_responsibility'] != '' && this.validate100charactersallowed(tempobj['change_in_responsibility'])){
        finalresult = false;
      }

      for(let z=0;z<(tempobj['sel_indicators']).length;z++){
        let obj2 = tempobj['sel_indicators'][z];

        if(obj2['progress_indicators']== ''){
          finalresult = false;
        }//
        else if(this.allowDecimalsIntegersOnly(obj2['progress_indicators'])  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(obj2['progress_indicators'])){
          //alert("Greater than 100");
          finalresult = false;
        }
        
      }
    }



    for(let j=0;j<this.customer.workforce_action_arr.length;j++){

      if(this.customer.workforce_action_arr[j]['document_action_required'] == no_data_available){
        this.customer.workforce_action_arr[j]['status'] = default_completed;
      }

      let tempobj = this.customer.workforce_action_arr[j];

      if(tempobj['status'] == ''){
        finalresult = false;
      }/*
      else if(tempobj['change_in_responsibility'] == ''){
        finalresult = false;
      }*/
      else if( tempobj['change_in_responsibility'] != '' && this.validate100charactersallowed(tempobj['change_in_responsibility'])){
        finalresult = false;
      }

      for(let z=0;z<(tempobj['sel_indicators']).length;z++){
        let obj2 = tempobj['sel_indicators'][z];

        if(obj2['progress_indicators']== ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(obj2['progress_indicators'])  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(obj2['progress_indicators'])){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }




    for(let j=0;j<this.customer.supplies_action_arr.length;j++){

      if(this.customer.supplies_action_arr[j]['document_action_required'] == no_data_available){
        this.customer.supplies_action_arr[j]['status'] = default_completed;
      }

      let tempobj = this.customer.supplies_action_arr[j];

      if(tempobj['status'] == ''){
        finalresult = false;
      }/*
      else if(tempobj['change_in_responsibility'] == ''){
        finalresult = false;
      }*/
      else if( tempobj['change_in_responsibility'] != '' && this.validate100charactersallowed(tempobj['change_in_responsibility'])){
        finalresult = false;
      }

      for(let z=0;z<(tempobj['sel_indicators']).length;z++){
        let obj2 = tempobj['sel_indicators'][z];

        if(obj2['progress_indicators']== ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(obj2['progress_indicators'])  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(obj2['progress_indicators'])){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }




    for(let j=0;j<this.customer.health_action_arr.length;j++){

      if(this.customer.health_action_arr[j]['document_action_required'] == no_data_available){
        this.customer.health_action_arr[j]['status'] = default_completed;
      }

      let tempobj = this.customer.health_action_arr[j];

      if(tempobj['status'] == ''){
        finalresult = false;
      }/*
      else if(tempobj['change_in_responsibility'] == ''){
        finalresult = false;
      }*/
      else if( tempobj['change_in_responsibility'] != '' && this.validate100charactersallowed(tempobj['change_in_responsibility'])){
        finalresult = false;
      }

      for(let z=0;z<(tempobj['sel_indicators']).length;z++){
        let obj2 = tempobj['sel_indicators'][z];

        if(obj2['progress_indicators']== ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(obj2['progress_indicators'])  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(obj2['progress_indicators'])){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }




    for(let j=0;j<this.customer.finance_action_arr.length;j++){

      if(this.customer.finance_action_arr[j]['document_action_required'] == no_data_available){
        this.customer.finance_action_arr[j]['status'] = default_completed;
      }

      let tempobj = this.customer.finance_action_arr[j];

      if(tempobj['status'] == ''){
        finalresult = false;
      }/*
      else if(tempobj['change_in_responsibility'] == ''){
        finalresult = false;
      }*/
      else if( tempobj['change_in_responsibility'] != '' && this.validate100charactersallowed(tempobj['change_in_responsibility'])){
        finalresult = false;
      }

      for(let z=0;z<(tempobj['sel_indicators']).length;z++){
        let obj2 = tempobj['sel_indicators'][z];

        if(obj2['progress_indicators']== ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(obj2['progress_indicators'])  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(obj2['progress_indicators'])){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }




    for(let j=0;j<this.customer.policy_action_arr.length;j++){

      if(this.customer.policy_action_arr[j]['document_action_required'] == no_data_available){
        this.customer.policy_action_arr[j]['status'] = default_completed;
      }

      let tempobj = this.customer.policy_action_arr[j];

      if(tempobj['status'] == ''){
        finalresult = false;
      }/*
      else if(tempobj['change_in_responsibility'] == ''){
        finalresult = false;
      }
      else if( tempobj['change_in_responsibility'] != '' && this.validateSpecialCharsAndNum(tempobj['change_in_responsibility'])){
        finalresult = false;
      }*/
      else if( tempobj['change_in_responsibility'] != '' && this.validate100charactersallowed(tempobj['change_in_responsibility'])){
        finalresult = false;
      }

      for(let z=0;z<(tempobj['sel_indicators']).length;z++){
        let obj2 = tempobj['sel_indicators'][z];

        if(obj2['progress_indicators']== ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(obj2['progress_indicators'])  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(obj2['progress_indicators'])){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }


    if (!finalresult) {
      alert("Form invalid");
      return;
    }


    if(this.timezerodate.value == null || this.timeonedate.value == null || this.timetwodate.value == null || this.timethreedate.value == null){
      alert("All timelines 'Time 0', 'Time 1', 'Time 2' and 'Time 3' must be filled to complete the Follow up");
      return;
    }

    let selectedTime0 = (this.timezerodate.value).getTime();
    let selectedTime1 = (this.timeonedate.value).getTime();
    let selectedTime2 = (this.timetwodate.value).getTime();
    let selectedTime3 = (this.timethreedate.value).getTime();

    {
      let tempdate =  this.timezerodate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      //let finalfulldate =  finalday + "-" + finalmonth + "-" + finalyear;
      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;


      this.customer.total_coverage_indi_timezero_date = finalfulldate;
    }

    {
      let tempdate =  this.timeonedate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;
      
      this.customer.total_coverage_indi_timeone_date = finalfulldate;
    }

    {
      let tempdate =  this.timetwodate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;
      
      this.customer.total_coverage_indi_timetwo_date = finalfulldate;
    }

    {
      let tempdate =  this.timethreedate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;
      
      this.customer.total_coverage_indi_timethree_date = finalfulldate;
    }    

    

    if( ! (selectedTime0 <= selectedTime1 && selectedTime1 <= selectedTime2 && selectedTime2 <= selectedTime3) ){
      alert("Time 0, Time 1, Time 2 and Time 3 must be filled in ascending order");
      return;
    }
    
    // this.customer.total_coverage_indi_timezero_date = this.timezerodate.value;
    // this.customer.total_coverage_indi_timeone_date = this.timeonedate.value;
    // this.customer.total_coverage_indi_timetwo_date = this.timetwodate.value;
    // this.customer.total_coverage_indi_timethree_date = this.timethreedate.value;


    for(let pos=0;pos<this.customer.service_action_arr.length;pos++){ 
      let tempobj = this.customer.service_action_arr[pos];

      let tempdate = new Date("" + this.service_timeline[pos].value);

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      //let finalfulldate =  finalday + "-" + finalmonth + "-" + finalyear;
      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate; 
    }

    for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
      let tempobj = this.customer.workforce_action_arr[pos];

      let tempdate = new Date("" + this.workforce_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
    }


    for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
      let tempobj = this.customer.supplies_action_arr[pos];

      let tempdate = new Date("" + this.supplies_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
    }


    for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
      let tempobj = this.customer.health_action_arr[pos];

      let tempdate = new Date("" + this.health_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
    }


    for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
      let tempobj = this.customer.finance_action_arr[pos];

      let tempdate = new Date("" + this.finance_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
    }


    for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
      let tempobj = this.customer.policy_action_arr[pos];

      let tempdate = new Date("" + this.policy_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
    }


    //Bcoz only one value is at initial
    // this.meetingdate_arr[0] =  new FormControl(new Date());

    for(let pos=0;pos<this.customer.meeting_arr.length;pos++){
      let tempobj =this.customer.meeting_arr[pos];

      let tempdate = new Date("" + this.meetingdate_arr[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      //tempobj.meeting_date =  finalday + "-" + finalmonth + "-" + finalyear;
      tempobj.meeting_date =  finalyear + "-" + finalmonth + "-" + finalday;
    }


    // console.log(this.dynamicForm.value);
    // display form values on success
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    // let x: any = this.dynamicForm.value;

    let x: any = this.customer;
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(x, null, 4));

    //console.log("x =");
    //console.log(x);


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear(); 

    x.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;  

    this.completeClicked = true;

    //alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
    this._diphHttpClientService.saveform5followupDetails(x, login_district, login_cycle, login_year, login_userid ,"1")
      .subscribe(
        data => {
          this.completeClicked = false;
          this.savedform = true;
          //this.router.navigate(['dashboard']);
          //this.router.navigate(['dashboard/form5followupview']);
          this.router.navigate(['dashboard/form5followupview'])
          .then(() => {
                window.location.reload();
          });
        },
        error => {
	this.completeClicked = false; 
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
         });
  }

  partialSave(){

    if(this.customer.venue_of_meeting == ''){
      alert("Venue of the meeting is compulsary");
	  return;
    }
    else if(this.validate100charactersallowed(this.customer.venue_of_meeting)  ){
      alert("Venue of the meeting can not exceed 5000 characters");
	  return;
    }
	
    if(this.customer.chairperson_of_meeting == ''){
      alert("Chairperson of the meeting is compulsary");
	  return;
    }
    else if(this.customer.chairperson_of_meeting == '15' && this.customer.chairperson_of_meeting_others == ''){
      alert("Chairperson of the meeting is compulsary");
	  return;
    }
    else if(this.customer.chairperson_of_meeting == '15' &&  this.validate100charactersallowed(this.customer.chairperson_of_meeting_others)){
      alert("Chairperson of the meeting can not exceed 5000 characters");
	  return;
    }	
    else if(this.customer.no_of_meetings_resp_team == ''){
      alert("'Number of meeting for the respective theme' is compulsary");
	  return;
    }
    else if(this.validateNumbers(this.customer.no_of_meetings_resp_team)   ){
      alert("'Number of meeting for the respective theme' should be whole number");
	  return;
    }
   
    
	
    

    //customer.chairperson_of_meeting_others

    for(let j=1;j<this.customer.meeting_arr.length;j++){
      let tempobj = this.customer.meeting_arr[j];
	  
	  if(tempobj['stakeholder_meeting']=='' && tempobj['no_of_participants']==''){
		  alert("Stakeholders meeting is expanded but not filled");
		  return;
	  }
	  if(tempobj['stakeholder_meeting']!=null && tempobj['stakeholder_meeting']!='' &&  this.validate100charactersallowed(tempobj['stakeholder_meeting'])){
		  alert("Participant in meeting can not exceed 5000 characters");
		  return;
	  }     
    }


    let selectedTime0 = null;
    let selectedTime1 = null;
    let selectedTime2 = null;
    let selectedTime3 = null;

    if(this.timezerodate.value != null && this.timezerodate.value != '')
    {
      selectedTime0 = (this.timezerodate.value).getTime();
      let tempdate =  this.timezerodate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();
      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;					
			this.customer.total_coverage_indi_timezero_date = finalfulldate;
    }else 
      this.customer.total_coverage_indi_timezero_date = null;



    if(this.timeonedate.value != null && this.timeonedate.value != '')    
    {
      selectedTime1 = (this.timeonedate.value).getTime()
      let tempdate =  this.timeonedate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();
      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
			this.customer.total_coverage_indi_timeone_date = finalfulldate;
    }else 
      this.customer.total_coverage_indi_timeone_date = null;


    if(this.timetwodate.value != null && this.timetwodate.value != '')
    {
      selectedTime2 = (this.timetwodate.value).getTime()
      let tempdate =  this.timetwodate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();
      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
			this.customer.total_coverage_indi_timetwo_date = finalfulldate;
    }else 
      this.customer.total_coverage_indi_timetwo_date = null;


    if(this.timethreedate.value != null && this.timethreedate.value != '')
    {
      selectedTime3 = (this.timethreedate.value).getTime()
      let tempdate =  this.timethreedate.value;
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();
      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;  
	  	this.customer.total_coverage_indi_timethree_date = finalfulldate;
    }else 
      this.customer.total_coverage_indi_timethree_date = null;   

    
	if((this.timezerodate.value != null && this.timeonedate.value != null) && (this.timezerodate.value != '' && this.timeonedate.value != '')){
		if( ! (selectedTime0 <= selectedTime1) ){
		  alert("Time 0 and Time 1 must be filled in ascending order");
		  return;
		}
	}
	
	if((this.timeonedate.value != null && this.timetwodate.value != null) && (this.timeonedate.value != '' && this.timetwodate.value != '')){
		if( ! (selectedTime1 <= selectedTime2) ){
		  alert("Time 1 and Time 2 must be filled in ascending order");
		  return;
		}
	}
	
	if((this.timetwodate.value != null && this.timethreedate.value != null) && (this.timetwodate.value != '' && this.timethreedate.value != '')){
		if( ! (selectedTime2 <= selectedTime3) ){
		  alert("Time 2 and Time 3 must be filled in ascending order");
		  return;
		}
	}
    
   

    for(let pos=0;pos<this.customer.service_action_arr.length;pos++){ 
      let tempobj = this.customer.service_action_arr[pos];

		if(this.service_timeline[pos].value != null && this.service_timeline[pos].value != ''){
			  let tempdate = new Date("" + this.service_timeline[pos].value);

			  let finalday = tempdate.getDate();
			  let finalmonth = (tempdate.getMonth() + 1);
			  let finalyear = tempdate.getFullYear();
			  let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;
			  tempobj.revised_timeline = finalfulldate;
		}else{
			tempobj.revised_timeline = null;
		}	  
    }

    for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
      let tempobj = this.customer.workforce_action_arr[pos];

		if(this.workforce_timeline[pos].value != null && this.workforce_timeline[pos].value != ''){
		  let tempdate = new Date("" + this.workforce_timeline[pos].value); 

		  let finalday = tempdate.getDate();
		  let finalmonth = (tempdate.getMonth() + 1);
		  let finalyear = tempdate.getFullYear();

		  let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

		  tempobj.revised_timeline = finalfulldate;
	  }else{
			tempobj.revised_timeline = null;
		}
    }


    for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
      let tempobj = this.customer.supplies_action_arr[pos];
	  
		if(this.supplies_timeline[pos].value != null && this.supplies_timeline[pos].value != ''){
		  let tempdate = new Date("" + this.supplies_timeline[pos].value); 

		  let finalday = tempdate.getDate();
		  let finalmonth = (tempdate.getMonth() + 1);
		  let finalyear = tempdate.getFullYear();

		  let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

		  tempobj.revised_timeline = finalfulldate;
	  }else{
			tempobj.revised_timeline = null;
		}
    }


    for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
      let tempobj = this.customer.health_action_arr[pos];
	  
	  if(this.health_timeline[pos].value != null && this.health_timeline[pos].value != ''){
		  let tempdate = new Date("" + this.health_timeline[pos].value); 

		  let finalday = tempdate.getDate();
		  let finalmonth = (tempdate.getMonth() + 1);
		  let finalyear = tempdate.getFullYear();

		  let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

		  tempobj.revised_timeline = finalfulldate;
	  }else{
			tempobj.revised_timeline = null;
		}
    }


    for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
      let tempobj = this.customer.finance_action_arr[pos];
	
		if(this.finance_timeline[pos].value != null && this.finance_timeline[pos].value != ''){
		  let tempdate = new Date("" + this.finance_timeline[pos].value); 

		  let finalday = tempdate.getDate();
		  let finalmonth = (tempdate.getMonth() + 1);
		  let finalyear = tempdate.getFullYear();

		  let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

		  tempobj.revised_timeline = finalfulldate;
	   }else{
			tempobj.revised_timeline = null;
		}
    }


    for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
      let tempobj = this.customer.policy_action_arr[pos];

		if(this.policy_timeline[pos].value != null && this.policy_timeline[pos].value != ''){
		  let tempdate = new Date("" + this.policy_timeline[pos].value); 

		  let finalday = tempdate.getDate();
		  let finalmonth = (tempdate.getMonth() + 1);
		  let finalyear = tempdate.getFullYear();

		  let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

		  tempobj.revised_timeline = finalfulldate;
	  }else{
			tempobj.revised_timeline = null;
		}
    }


    
    for(let pos=0;pos<this.customer.meeting_arr.length;pos++){
      let tempobj =this.customer.meeting_arr[pos];

		if(this.meetingdate_arr[pos].value != null && this.meetingdate_arr[pos].value != ''){
		  let tempdate = new Date("" + this.meetingdate_arr[pos].value); 

		  let finalday = tempdate.getDate();
		  let finalmonth = (tempdate.getMonth() + 1);
		  let finalyear = tempdate.getFullYear()
		  
		  tempobj.meeting_date =  finalyear + "-" + finalmonth + "-" + finalday;
	  }else{
			tempobj.meeting_date = null;
		}
    }


    
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');
    let login_userid = sessionStorage.getItem('userid');

   

    let x: any = this.customer;
   

   
    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear(); 

    x.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2; 	

    this.completeClicked = true;
   
    this._diphHttpClientService.saveform5followupDetails(x, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {
          this.completeClicked = false;
          this.savedform = true;
          this.router.navigate(['dashboard']);
          this.router.navigate(['dashboard/form5followupview']);
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
