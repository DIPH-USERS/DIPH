import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation, DatePipe } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form5-follow-up-edit',
  templateUrl: './form5-follow-up-edit.component.html',
  styleUrls: ['./form5-follow-up-edit.component.css']
})
export class Form5FollowUpEditComponent implements OnInit {

  completeClicked : boolean = false;

  @Input() customer: any = {};
  finallyloaded = false;
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
  public verified_by_name_from_Db=[];

  date = new FormControl(new Date());

  meetingdate_arr = [];

  timezerodate = new FormControl(new Date());
  timeonedate = new FormControl(new Date());
  timetwodate = new FormControl(new Date());
  timethreedate = new FormControl(new Date());


  service_timeline = [];
  workforce_timeline = [];
  supplies_timeline = [];
  health_timeline = [];
  finance_timeline = [];
  policy_timeline = [];

  
  date_2 = new FormControl(new Date());
  dynamicForm: FormGroup;
  minDate =new Date("12,01,2019");


  form4response: any;

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
    
    this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          this.customer= data;

            if(data.completed == '1')
                this.customer.completed = true;
            else if(data.completed == '0')
                 this.customer.completed = false;

         this.date = new FormControl(new Date(this.customer.date_of_meeting));
         
         if(this.customer.total_coverage_indi_timezero_date != null && this.customer.total_coverage_indi_timezero_date != '')
         {
          let tempdate = this.customer.total_coverage_indi_timezero_date;

          let tmp = (tempdate+"").split('-');
          let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

          this.timezerodate = new FormControl(new Date(finaldate));
         }else{
          this.timezerodate = new FormControl(null);
         }


         if(this.customer.total_coverage_indi_timeone_date != null && this.customer.total_coverage_indi_timeone_date != '')
         {
          let tempdate = this.customer.total_coverage_indi_timeone_date;

          let tmp = (tempdate+"").split('-');
          let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

          this.timeonedate = new FormControl(new Date(finaldate));
         }else{
          this.timeonedate = new FormControl(null);
         }


         if(this.customer.total_coverage_indi_timetwo_date != null && this.customer.total_coverage_indi_timetwo_date != '')
         {
          let tempdate = this.customer.total_coverage_indi_timetwo_date;

          let tmp = (tempdate+"").split('-');
          let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

          this.timetwodate = new FormControl(new Date(finaldate));
         }else{
          this.timetwodate = new FormControl(null);
         }


         if(this.customer.total_coverage_indi_timethree_date != null && this.customer.total_coverage_indi_timethree_date != '')
         {
          let tempdate = this.customer.total_coverage_indi_timethree_date;

          let tmp = (tempdate+"").split('-');
          let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

          this.timethreedate = new FormControl(new Date(finaldate));
        }else{
          this.timethreedate = new FormControl(null);
         }
         
         
         
         let mpos=0;

         for(let pos=0;pos<this.customer.meeting_arr.length;pos++){
          let tempobj = this.customer.meeting_arr[pos];

          let tmp = (tempobj.meeting_date+"").split('-');
          let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

          this.meetingdate_arr[mpos] = new FormControl(new Date(finaldate));
          mpos++;

         }

        //  alert(JSON.stringify(this.meetingdate_arr[0].value,null,4));
         
         /********************************************************/
         
        //  for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
        //   let tempobj = this.customer.service_action_arr[pos];

        //   let tmp = (tempobj.meeting_date+"").split('-');
        //   let finaldate = tmp[1]+'/'+tmp[0]+'/'+tmp[2];

        //   this.meetingdate_arr[pos] = new FormControl(new Date(finaldate));
        // }


         /******************************************************/

        let pos1=0;
            
            for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
              let tempobj = this.customer.service_action_arr[pos];

              let tmp = (tempobj.revised_timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              this.service_timeline[pos1] = new FormControl(new Date(finaldate));
              
              pos1++;

              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear(); 
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }


            let pos2=0;
            for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
              let tempobj = this.customer.workforce_action_arr[pos];

              let tmp = (tempobj.revised_timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              this.workforce_timeline[pos2] = new FormControl(new Date(finaldate));

             
              pos2++;

              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear(); 
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }


            let pos3=0;
            for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
              let tempobj = this.customer.supplies_action_arr[pos];

              let tmp = (tempobj.revised_timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              this.supplies_timeline[pos3] = new FormControl(new Date(finaldate));
              pos3++;

              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear(); 
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }


            let pos4=0;
            for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
              let tempobj = this.customer.health_action_arr[pos];

              let tmp = (tempobj.revised_timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              this.health_timeline[pos4] = new FormControl(new Date(finaldate));              
              pos4++;

              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear(); 
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }

            let pos5=0;
            for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
              let tempobj = this.customer.finance_action_arr[pos];

              let tmp = (tempobj.revised_timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              this.finance_timeline[pos5] = new FormControl(new Date(finaldate));              
              pos5++;

              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear(); 
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }

            let pos6=0;
            for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
              let tempobj = this.customer.policy_action_arr[pos];

              let tmp = (tempobj.revised_timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              this.policy_timeline[pos6] = new FormControl(new Date(finaldate));              
              pos6++;

              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear(); 
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }
         /******************************************************/

          this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
          .subscribe(
            data => {

              let iphs_theme_name = data.iphs_theme_name;
              this.customer.theme_id = iphs_theme_name;
             
              for (let index in data.total_coverage_indi) {

                let obj2 = index;
                

                for(let j=0;j<this.form1a_all_doc_arr.length;j++)
                {
                  let tempobj= this.form1a_all_doc_arr[j];
                  if(tempobj.doc_db_doc_id == data.total_coverage_indi[index].source){
                    this.customer.total_coverage_indi[index].new_source  =  tempobj.document_val;
                    
                  }
                }
              }
              /*********************************************************/

              /***********************************/

              // this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
              // .subscribe(
              //   data => {

              //     //alert(data.form_3_filled_by);

              //     this.customer.theme_leader = data.form_3_filled_by;
              //     //console.log("Form3 Define");
              //     //console.log(JSON.stringify(data,null,1000));

              //   },
              //   error => {
              //     console.log(error); alert("Error in Form 3= " + error);
              //   });

            /************************************/

              /********************************************************/

            },
            error => {
              console.log(error); alert("Error= " + error);
            });

          this.finallyloaded = true;
        },
        error => {
          console.log(error); alert("Error= " + error);
        });

    $(document).ready(function () {


    });
  }

  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }


  onClickAddRow() {

  }

  onClickRemove() {

  }
  onclickAddmeetingRow(i){
    let temp_obj= {
      "meeting_no":""+(i+2),
      "meeting_date":"",
      "stakeholder_meeting":"",
      "no_of_participants":"",
      "insert":"1"
    };
    this.customer.meeting_arr.push(temp_obj);

    this.meetingdate_arr.push(new FormControl(new Date()));
  }

  onclickRemovemeetingRow(i){
    this.customer.meeting_arr.splice(i,1);
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

  //New Modifications start

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

  //New Modifications end

  onSubmit() {


    this.submitted = true;

    let finalresult = true; 
    
    let no_data_available = 'No Data Available';
    let default_completed = 'Completed';
  
    if(this.timezerodate.value == null || this.timeonedate.value == null || this.timetwodate.value == null || this.timethreedate.value == null){
      alert("Time 0, Time 1, Time 2 and Time 3 must be in filled");
      return;
    }

    let selectedTime0 = (this.timezerodate.value).getTime();
    let selectedTime1 = (this.timeonedate.value).getTime();
    let selectedTime2 = (this.timetwodate.value).getTime();
    let selectedTime3 = (this.timethreedate.value).getTime();
	
	  if(! (selectedTime0 <= selectedTime1 && selectedTime1 <= selectedTime2 && selectedTime2 <= selectedTime3) ){
      alert("Time 0, Time 1, Time 2 and Time 3 must be in ascending order");
      return;
    }
 //console.log(JSON.stringify(this.customer.service_action_arr[0]));
    /*
    if(this.customer.service_action_arr[0].status != 'Completed'){
      alert("Service Delivery 'Status of Action Points' is not completed");
      return;
    }

    if(this.customer.workforce_action_arr[0].status != 'Completed'){
      alert("Work Force 'Status of Action Points' is not completed");
      return;
   }

   if(this.customer.supplies_action_arr[0].status != 'Completed'){
    alert("Supplies & Technology 'Status of Action Points' is not completed");
    return;
   }

   if(this.customer.health_action_arr[0].status != 'Completed'){
    alert("Health Information 'Status of Action Points' is not completed");
    return;
   }

   if(this.customer.finance_action_arr[0].status != 'Completed'){
    alert("Finance 'Status of Action Points' is not completed");
    return;
   }

   if(this.customer.policy_action_arr[0].status != 'Completed'){
    alert("Policy/Governance 'Status of Action Points' is not completed");
    return;
   }
   */

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

    

    for(let pos=0;pos<this.customer.meeting_arr.length;pos++){
      let tempobj = this.customer.meeting_arr[pos];

      let tempdate = new Date("" + this.meetingdate_arr[pos].value);

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      //let finalfulldate =  finalday + "-" + finalmonth + "-" + finalyear;

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;
      
      tempobj.meeting_date = finalfulldate;
    }


    for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
      
      if(this.customer.service_action_arr[pos]['document_action_required'] == no_data_available){
        this.customer.service_action_arr[pos]['status'] = default_completed;
      }

      let tempobj = this.customer.service_action_arr[pos];

      {
      let tempdate = new Date("" + this.service_timeline[pos].value);
       
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      //let finalfulldate =  finalday + "-" + finalmonth + "-" + finalyear;
      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
     }

     {
      let tempdate = new Date("" + tempobj.timeline); 
  
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear(); 

      tempobj.timeline =  finalyear + "-" + finalmonth + "-" + finalday;
     }
    }

    for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){

      if(this.customer.workforce_action_arr[pos]['document_action_required'] == no_data_available){
        this.customer.workforce_action_arr[pos]['status'] = default_completed;
      }

      let tempobj = this.customer.workforce_action_arr[pos];
       {
      let tempdate = new Date("" + this.workforce_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
     }

     {
      let tempdate = new Date("" + tempobj.timeline); 
  
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear(); 

      tempobj.timeline =  finalyear + "-" + finalmonth + "-" + finalday;
     }
    }


    for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){

      if(this.customer.supplies_action_arr[pos]['document_action_required'] == no_data_available){
        this.customer.supplies_action_arr[pos]['status'] = default_completed;
      }

      let tempobj = this.customer.supplies_action_arr[pos];
      {
      let tempdate = new Date("" + this.supplies_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
      }
      {
        let tempdate = new Date("" + tempobj.timeline); 
    
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear(); 
  
        tempobj.timeline =  finalyear + "-" + finalmonth + "-" + finalday;
       }
    }


    for(let pos=0;pos<this.customer.health_action_arr.length;pos++){

      if(this.customer.health_action_arr[pos]['document_action_required'] == no_data_available){
        this.customer.health_action_arr[pos]['status'] = default_completed;
      }

      let tempobj = this.customer.health_action_arr[pos];

      {
      let tempdate = new Date("" + this.health_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
      }

      {
        let tempdate = new Date("" + tempobj.timeline); 
    
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear(); 
  
        tempobj.timeline =  finalyear + "-" + finalmonth + "-" + finalday;
       }
    }


    for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){

      if(this.customer.finance_action_arr[pos]['document_action_required'] == no_data_available){
        this.customer.finance_action_arr[pos]['status'] = default_completed;
      }

      let tempobj = this.customer.finance_action_arr[pos];

      {
      let tempdate = new Date("" + this.finance_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
      }

      {
        let tempdate = new Date("" + tempobj.timeline); 
    
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear(); 
  
        tempobj.timeline =  finalyear + "-" + finalmonth + "-" + finalday;
       }
    }


    for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){

      if(this.customer.policy_action_arr[pos]['document_action_required'] == no_data_available){
        this.customer.policy_action_arr[pos]['status'] = default_completed;
      }

      let tempobj = this.customer.policy_action_arr[pos];

      {
      let tempdate = new Date("" + this.policy_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.revised_timeline = finalfulldate;
      }

      {
        let tempdate = new Date("" + tempobj.timeline); 
    
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear(); 
  
        tempobj.timeline =  finalyear + "-" + finalmonth + "-" + finalday;
       }
    }

    if(this.customer.venue_of_meeting == ''){
      finalresult = false;
    }
    else if(this.validate100charactersallowed(this.customer.venue_of_meeting)  ){
      finalresult = false;
    }


    if(this.customer.chairperson_of_meeting == ''){
      finalresult = false;
    }
    else if(this.customer.chairperson_of_meeting == '15'  &&  this.customer.chairperson_of_meeting_others == ''){
      finalresult = false;
    }
    else if(this.customer.chairperson_of_meeting == '15'  &&  this.validate100charactersallowed(this.customer.chairperson_of_meeting_others)){
      finalresult = false;
    }

    if(this.customer.no_of_meetings_resp_team == ''){
      finalresult = false;
    }
    else if(this.validateNumbers( this.customer.no_of_meetings_resp_team) ){
      finalresult = false;
    }

    for(let index=0;index<this.customer.meeting_arr.length;index++){
      let tempobj = this.customer.meeting_arr[index];

      //alert(JSON.stringify(tempobj,null,100));
      /*
      if(this.validateSpecialCharsAndNum(tempobj.stakeholder_meeting)){
        finalresult = false;
      }
      else */if(this.validate100charactersallowed(tempobj.stakeholder_meeting)){
        finalresult = false;
      }

      if(tempobj.no_of_participants == ''){
        finalresult = false;
      }
      else if(this.validateNumbers(tempobj.no_of_participants)  ){
        finalresult = false;
      }
    }

    for(let index=0;index<this.customer.service_action_arr.length;index++){
      let tempobj = this.customer.service_action_arr[index];

      if(tempobj.change_in_responsibility != '' && this.validate100charactersallowed(tempobj.change_in_responsibility)  ){
        finalresult = false;
      }

      for(let pos=0;pos<tempobj.sel_indicators.length;pos++){

        let tempobj2 = tempobj.sel_indicators[pos];

        if(tempobj2.progress_indicators == ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(tempobj2.progress_indicators)  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(tempobj2.progress_indicators)){
          //alert("Greater than 100");
          finalresult = false;
        }
      }

      
    }

    for(let index=0;index<this.customer.workforce_action_arr.length;index++){
      let tempobj = this.customer.workforce_action_arr[index];

      if(tempobj.change_in_responsibility != '' && this.validate100charactersallowed(tempobj.change_in_responsibility)  ){
        finalresult = false;
      }


      for(let pos=0;pos<tempobj.sel_indicators.length;pos++){

        let tempobj2 = tempobj.sel_indicators[pos];

        if(tempobj2.progress_indicators == ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(tempobj2.progress_indicators)  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(tempobj2.progress_indicators)){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }

    for(let index=0;index<this.customer.supplies_action_arr.length;index++){
      let tempobj = this.customer.supplies_action_arr[index];

      if(tempobj.change_in_responsibility != '' && this.validate100charactersallowed(tempobj.change_in_responsibility)  ){
        finalresult = false;
      }

      for(let pos=0;pos<tempobj.sel_indicators.length;pos++){

        let tempobj2 = tempobj.sel_indicators[pos];

        if(tempobj2.progress_indicators == ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(tempobj2.progress_indicators)  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(tempobj2.progress_indicators)){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }

    for(let index=0;index<this.customer.health_action_arr.length;index++){
      let tempobj = this.customer.health_action_arr[index];

      if(tempobj.change_in_responsibility != '' && this.validate100charactersallowed(tempobj.change_in_responsibility)  ){
        finalresult = false;
      }


      for(let pos=0;pos<tempobj.sel_indicators.length;pos++){

        let tempobj2 = tempobj.sel_indicators[pos];

        if(tempobj2.progress_indicators == ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(tempobj2.progress_indicators)  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(tempobj2.progress_indicators)){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }  

    for(let index=0;index<this.customer.finance_action_arr.length;index++){
      let tempobj = this.customer.finance_action_arr[index];

     if(tempobj.change_in_responsibility != '' && this.validate100charactersallowed(tempobj.change_in_responsibility)  ){
        finalresult = false;
      }

      for(let pos=0;pos<tempobj.sel_indicators.length;pos++){

        let tempobj2 = tempobj.sel_indicators[pos];

        if(tempobj2.progress_indicators == ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(tempobj2.progress_indicators)  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(tempobj2.progress_indicators)){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }

    for(let index=0;index<this.customer.policy_action_arr.length;index++){
      let tempobj = this.customer.policy_action_arr[index];

      if(tempobj.change_in_responsibility != '' && this.validate100charactersallowed(tempobj.change_in_responsibility)  ){
        finalresult = false;
      }

      for(let pos=0;pos<tempobj.sel_indicators.length;pos++){

        let tempobj2 = tempobj.sel_indicators[pos];

        if(tempobj2.progress_indicators == ''){
          finalresult = false;
        }
        else if(this.allowDecimalsIntegersOnly(tempobj2.progress_indicators)  ){
          finalresult = false;
        }
        else if(this.allowIntegersUpto100(tempobj2.progress_indicators)){
          //alert("Greater than 100");
          finalresult = false;
        }
      }
    }
    //policy_action_arr



    if(!finalresult){
      alert("Invalid");
      return;
    }



    // Year should be in this format
    // 2016-06-15

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

    
    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    this.completeClicked = true;
    //alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
    this._diphHttpClientService.editUpdateForm5FollowUpDetails(x, login_district, login_cycle, login_year, login_userid, "1")
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
          alert("Error : "+error);
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

    this._diphHttpClientService.editUpdateForm5FollowUpDetails(x, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {
          this.completeClicked = false;
          this.savedform = true; 
          this.router.navigate(['dashboard']);
          this.router.navigate(['dashboard/form5followupview']);
        },
        error => { 
          this.completeClicked = false;
          alert("error : "+error);
          this.router.navigate(['dashboard']);
         });

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
