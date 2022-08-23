import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { MatDialog } from '@angular/material';
import { Form1bModalDialogComponent } from '../form1b-modal-dialog/form1b-modal-dialog.component';
import { Form4ModalDialogComponent } from '../form4-modal-dialog/form4-modal-dialog.component';

@Component({
  selector: 'app-form4-plan',
  templateUrl: './form4-plan.component.html',
  styleUrls: ['./form4-plan.component.css']
})
export class Form4PlanComponent implements OnInit {

  completeClicked : boolean = false;

  @Input() customer: any = {};
  date = new FormControl(new Date());

  service_timeline = [];
  workforce_timeline = [];
  supplies_timeline = [];
  health_timeline = [];
  finance_timeline = [];
  policy_timeline = [];

  date_max = new Date();
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
  // action_required: any = {};
  all_areas_indicators_arr = [];
  total_coverage_indi = [];
  selected_indi: any = [];
  public verified_by_name_from_Db = [];

  theme_id: string;
  theme_name: string;
  minDate = new Date("12,01,2019");


  dynamicForm: FormGroup;

  constructor(location: PlatformLocation, public dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
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

    this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        dataDefine => {

         
          if ( (dataDefine.form_3_meeting_venue == null || dataDefine.form_3_meeting_venue == '' || dataDefine.form_3_filled_by == null || dataDefine.form_3_filled_by == '') || 
               (dataDefine.completed == "0") ) {
            alert("Please complete Define Form to create Plan Form");
            this.router.navigate(['dashboard']);
            return;
          }
    });


    this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {


          if (data.form_2_date_of_meeting == "" || data.form_2_date_of_meeting == "null" || data.form_2_date_of_meeting == null || data.form_2_date_of_meeting == "undefined") {
            alert("Step 2 Engage(Form) not filled. Please fill Engage and Define Form to create Plan Form");
            this.router.navigate(['dashboard']);

            return;
          }

          jsonresponse = data;

          this.theme_id = jsonresponse.theme_id;

          prim_text = jsonresponse.primary_stakeholder_text;
          sec_text = jsonresponse.secondary_stakeholder_text;
          prim_id = jsonresponse.primary_stakeholder_id;
          sec_id = jsonresponse.secondary_stakeholder_id;


          let temp_stakeholder_arr = [];

          for (let x = 0; x < jsonresponse.primary_stake_array.length; x++) {
            //document_name
            let temp_arr = jsonresponse.primary_stake_array[x];

           // let str1 = temp_arr.document_name;

            temp_stakeholder_arr.push(temp_arr);
          }



          for (let y = 0; y < jsonresponse.secondary_stake_array.length; y++) {
            let temp_arr2 = jsonresponse.secondary_stake_array[y];

          //  let str2 = temp_arr2.document_name;

            temp_stakeholder_arr.push(temp_arr2);
          }

          //alert(JSON.stringify(temp_stakeholder_arr,null,4));

          this.stakeholder_arr = temp_stakeholder_arr;


          // this.stakeholder_arr.push({ id: jsonresponse.primary_stakeholder_id, name: jsonresponse.primary_stakeholder_text });
          // this.stakeholder_arr.push({ id: jsonresponse.secondary_stakeholder_id, name: jsonresponse.secondary_stakeholder_text });

          this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
            .subscribe(
              data => {

                console.log("data : "+JSON.stringify(data));
                //data.form_3_checkdate

                /*

                if (data.form_3_checkdate == "" || data.form_3_checkdate == "null" || data.form_3_checkdate == null || data.form_3_checkdate == "undefined") {
                  alert("Step 3 Define(Form) not filled. Please fill it to create Plan Form");
                  this.router.navigate(['dashboard']);

                  return;
                }
                */

                this.customer.venue_of_meeting = "";
                this.customer.chariperson_of_meeting = "";
                this.customer.chariperson_of_meeting_others = "";
                this.customer.date_of_meeting = "";
                this.customer.theme_leader = "";
                this.customer.theme_id = "" + this.theme_id;

                let temp_res_obj: any = {};

                temp_res_obj.policy = data.policy_action_required;
                temp_res_obj.finance = data.finance_action_required;
                temp_res_obj.health = data.health_action_required;
                temp_res_obj.supplies = data.supplies_action_required;
                temp_res_obj.workforce = data.workforce_action_required;
                temp_res_obj.service = data.service_action_required;

                let service_action_arr = [];

                let service_arr = data.service_array;

                //console.log(JSON.stringify(service_arr));

                for (let x = 0; x < service_arr.length; x++) {
                  let temp_arr_obj = service_arr[x];
                  let obj = {};
                  for (let y = 0; y < temp_arr_obj.document_action_required.length; y++) {
                    obj = {
                      "action_req_id": "" + temp_arr_obj.action_req_id[y],
                      "document_action_required": "" + temp_arr_obj.document_action_required[y],
                      "form_3_1_action_part_engagement_nm_details_pkey": "" + temp_arr_obj.form_3_1_action_part_engagement_nm_details_pkey,
                      "sel_indicators": [],
                      "sel_stakeholder": "",
                      "person_responsible": "",
                      "timeline": ""
                    };
                    service_action_arr.push(obj);
                  }
                }

                this.customer.service_action_arr = service_action_arr;


                let pos1=0;

            for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
              // let tempobj = this.customer.service_action_arr[pos];

              // let tmp = (tempobj.timeline+"").split('-');
              // let finaldate = tmp[1]+'/'+tmp[0]+'/'+tmp[2];

              this.service_timeline[pos1] = new FormControl(null);
              
              pos1++;
            }


            



                let workforce_action_arr = [];

                let workforce_arr = data.workforce_array;

                for (let x = 0; x < workforce_arr.length; x++) {
                  let temp_arr_obj = workforce_arr[x];
                  let obj = {};
                  for (let y = 0; y < temp_arr_obj.document_action_required.length; y++) {
                    obj = {
                      "action_req_id": "" + temp_arr_obj.action_req_id[y],
                      "document_action_required": "" + temp_arr_obj.document_action_required[y],
                      "form_3_1_action_part_engagement_nm_details_pkey": "" + temp_arr_obj.form_3_1_action_part_engagement_nm_details_pkey,
                      "sel_indicators": [],
                      "sel_stakeholder": "",
                      "person_responsible": "",
                      "timeline": ""
                    };
                    workforce_action_arr.push(obj);
                  }
                }

                this.customer.workforce_action_arr = workforce_action_arr;


                let pos2=0;
            for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
              // let tempobj = this.customer.workforce_action_arr[pos];

              // let tmp = (tempobj.timeline+"").split('-');
              // let finaldate = tmp[1]+'/'+tmp[0]+'/'+tmp[2];

              this.workforce_timeline[pos2] = new FormControl(null);

              //this.workforce_timeline[pos2] = new FormControl(tempobj.timeline);
              pos2++;
            }


            







                let supplies_action_arr = [];

                let supplies_arr = data.supplies_array;

                for (let x = 0; x < supplies_arr.length; x++) {
                  let temp_arr_obj = supplies_arr[x];
                  let obj = {};
                  for (let y = 0; y < temp_arr_obj.document_action_required.length; y++) {
                    obj = {
                      "action_req_id": "" + temp_arr_obj.action_req_id[y],
                      "document_action_required": "" + temp_arr_obj.document_action_required[y],
                      "form_3_1_action_part_engagement_nm_details_pkey": "" + temp_arr_obj.form_3_1_action_part_engagement_nm_details_pkey,
                      "sel_indicators": [],
                      "sel_stakeholder": "",
                      "person_responsible": "",
                      "timeline": ""
                    };
                    supplies_action_arr.push(obj);
                  }
                }

                this.customer.supplies_action_arr = supplies_action_arr;



                let pos3=0;
            for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
              // let tempobj = this.customer.supplies_action_arr[pos];

              // let tmp = (tempobj.timeline+"").split('-');
              // let finaldate = tmp[1]+'/'+tmp[0]+'/'+tmp[2];

              this.supplies_timeline[pos3] = new FormControl(null);

              //this.supplies_timeline[pos3] = new FormControl(tempobj.timeline);
              pos3++;
            }

            






                let health_action_arr = [];

                let health_arr = data.health_array;

                for (let x = 0; x < health_arr.length; x++) {
                  let temp_arr_obj = health_arr[x];
                  let obj = {};
                  for (let y = 0; y < temp_arr_obj.document_action_required.length; y++) {
                    obj = {
                      "action_req_id": "" + temp_arr_obj.action_req_id[y],
                      "document_action_required": "" + temp_arr_obj.document_action_required[y],
                      "form_3_1_action_part_engagement_nm_details_pkey": "" + temp_arr_obj.form_3_1_action_part_engagement_nm_details_pkey,
                      "sel_indicators": [],
                      "sel_stakeholder": "",
                      "person_responsible": "",
                      "timeline": ""
                    };
                    health_action_arr.push(obj);
                  }
                }

                // this.action_required = temp_res_obj;
                this.customer.health_action_arr = health_action_arr;



                let pos4=0;
            for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
              // let tempobj = this.customer.health_action_arr[pos];

              // let tmp = (tempobj.timeline+"").split('-');
              // let finaldate = tmp[1]+'/'+tmp[0]+'/'+tmp[2];

              this.health_timeline[pos4] = new FormControl(null);

              //this.health_timeline[pos4] = new FormControl(tempobj.timeline);
              pos4++;
            }


            








                let finance_action_arr = [];
                let finance_arr = data.finance_array;

                for (let x = 0; x < finance_arr.length; x++) {
                  let temp_arr_obj = finance_arr[x];
                  let obj = {};
                  for (let y = 0; y < temp_arr_obj.document_action_required.length; y++) {
                    obj = {
                      "action_req_id": "" + temp_arr_obj.action_req_id[y],
                      "document_action_required": "" + temp_arr_obj.document_action_required[y],
                      "form_3_1_action_part_engagement_nm_details_pkey": "" + temp_arr_obj.form_3_1_action_part_engagement_nm_details_pkey,
                      "sel_indicators": [],
                      "sel_stakeholder": "",
                      "person_responsible": "",
                      "timeline": ""
                    };
                    finance_action_arr.push(obj);
                  }
                }

                // this.action_required = temp_res_obj;
                this.customer.finance_action_arr = finance_action_arr;


                let pos5=0;
            for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
              // let tempobj = this.customer.finance_action_arr[pos];

              // let tmp = (tempobj.timeline+"").split('-');
              // let finaldate = tmp[1]+'/'+tmp[0]+'/'+tmp[2];

              this.finance_timeline[pos5] = new FormControl(null);

              //this.finance_timeline[pos5] = new FormControl(tempobj.timeline);
              pos5++;
            }


            









                let policy_action_arr = [];

                let policy_arr = data.policy_array;


                for (let x = 0; x < policy_arr.length; x++) {
                  let temp_arr_obj = policy_arr[x];
                  let obj = {};
                  for (let y = 0; y < temp_arr_obj.document_action_required.length; y++) {
                    obj = {
                      "action_req_id": "" + temp_arr_obj.action_req_id[y],
                      "document_action_required": "" + temp_arr_obj.document_action_required[y],
                      "form_3_1_action_part_engagement_nm_details_pkey": "" + temp_arr_obj.form_3_1_action_part_engagement_nm_details_pkey,
                      "sel_indicators": [],
                      "sel_stakeholder": "",
                      "person_responsible": "",
                      "timeline": ""
                    };
                    policy_action_arr.push(obj);
                  }
                }

                // this.action_required = temp_res_obj;
                this.customer.policy_action_arr = policy_action_arr;

                let pos6=0;
            for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
              // let tempobj = this.customer.policy_action_arr[pos];

              // let tmp = (tempobj.timeline+"").split('-');
              // let finaldate = tmp[1]+'/'+tmp[0]+'/'+tmp[2];

              this.policy_timeline[pos6] = new FormControl(null);

              //this.policy_timeline[pos6] = new FormControl(tempobj.timeline);
              pos6++;
            }




                this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
                  .subscribe(
                    data => {

                      let iphs_theme_name = data.iphs_theme_name;

                      this.theme_name = iphs_theme_name;

                      this.customer.theme_id = "" + data.theme_id; 

                      this.all_areas_indicators_arr = data.areas_Id_Indicators_map_list;

                      

                     //This code is used in Indicators List (Fetches only ESSENTIAL and ACTION Indicators)
              this._diphHttpClientService.getAllIndicators()
              .subscribe(
                indi_data => { 

                  this.global_indi_obj = indi_data.areas_Id_Indicators_map_list;; 


                  let global_indi_obj = null;

                  global_indi_obj = indi_data.areas_Id_Indicators_map_list;

                  for(let i=0;i<global_indi_obj.length;i++){
                    let temp_o = global_indi_obj[i];

                    for(let j=0;j<this.customer.service_action_arr.length;j++){
                      let temp_obj = this.customer.service_action_arr[j];

                      for(let k=0;k<temp_obj.sel_indicators.length;k++){
                        let temp_indi = temp_obj.sel_indicators[k];

                        if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
                          let obj:any = {};
                          obj = temp_indi;
                          obj.category = ""+temp_o["category"];
                          obj.frequency = ""+temp_o["frequency"];
                         // temp_obj.sel_indicators[k] = obj;
                         //this.customer.service_action_arr[j].sel_indicators[k] = obj;
                         this.customer.service_action_arr[j]["sel_indicators"][k] = obj;
                         }
                      }
                    } 
                  }


                  for(let i=0;i<global_indi_obj.length;i++){
                    let temp_o = global_indi_obj[i];

                    for(let j=0;j<this.customer.workforce_action_arr.length;j++){
                      let temp_obj = this.customer.workforce_action_arr[j];

                      for(let k=0;k<temp_obj.sel_indicators.length;k++){
                        let temp_indi = temp_obj.sel_indicators[k];

                        if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
                          let obj:any = {};
                          obj = temp_indi;
                          obj.category = ""+temp_o["category"];
                          obj.frequency = ""+temp_o["frequency"];
                         // temp_obj.sel_indicators[k] = obj;
                         //this.customer.workforce_action_arr[j].sel_indicators[k] = obj;
                         this.customer.workforce_action_arr[j]["sel_indicators"][k] = obj;
                         }
                      }
                    } 
                  }


                  for(let i=0;i<global_indi_obj.length;i++){
                    let temp_o = global_indi_obj[i];

                    for(let j=0;j<this.customer.supplies_action_arr.length;j++){
                      let temp_obj = this.customer.supplies_action_arr[j];

                      for(let k=0;k<temp_obj.sel_indicators.length;k++){
                        let temp_indi = temp_obj.sel_indicators[k];

                        if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
                          let obj:any = {};
                          obj = temp_indi;
                          obj.category = ""+temp_o["category"];
                          obj.frequency = ""+temp_o["frequency"];
                         // temp_obj.sel_indicators[k] = obj;
                         //this.customer.supplies_action_arr[j].sel_indicators[k] = obj;
                         this.customer.supplies_action_arr[j]["sel_indicators"][k] = obj;
                         }
                      }
                    } 
                  }

                  for(let i=0;i<global_indi_obj.length;i++){
                    let temp_o = global_indi_obj[i];

                    for(let j=0;j<this.customer.health_action_arr.length;j++){
                      let temp_obj = this.customer.health_action_arr[j];

                      for(let k=0;k<temp_obj.sel_indicators.length;k++){
                        let temp_indi = temp_obj.sel_indicators[k];

                        if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
                          let obj:any = {};
                          obj = temp_indi;
                          obj.category = ""+temp_o["category"];
                          obj.frequency = ""+temp_o["frequency"];
                         // temp_obj.sel_indicators[k] = obj;
                         //this.customer.health_action_arr[j].sel_indicators[k] = obj;
                         this.customer.health_action_arr[j]["sel_indicators"][k] = obj;
                         }
                      }
                    } 
                  }

                  for(let i=0;i<global_indi_obj.length;i++){
                    let temp_o = global_indi_obj[i];

                    for(let j=0;j<this.customer.finance_action_arr.length;j++){
                      let temp_obj = this.customer.finance_action_arr[j];

                      for(let k=0;k<temp_obj.sel_indicators.length;k++){
                        let temp_indi = temp_obj.sel_indicators[k];

                        if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
                          let obj:any = {};
                          obj = temp_indi;
                          obj.category = ""+temp_o["category"];
                          obj.frequency = ""+temp_o["frequency"];
                         // temp_obj.sel_indicators[k] = obj;
                         //this.customer.finance_action_arr[j].sel_indicators[k] = obj;
                         this.customer.finance_action_arr[j]["sel_indicators"][k] = obj;
                         }
                      }
                    } 
                  }


                  for(let i=0;i<global_indi_obj.length;i++){
                    let temp_o = global_indi_obj[i];

                    for(let j=0;j<this.customer.policy_action_arr.length;j++){
                      let temp_obj = this.customer.policy_action_arr[j];

                      for(let k=0;k<temp_obj.sel_indicators.length;k++){
                        let temp_indi = temp_obj.sel_indicators[k];

                        if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
                          let obj:any = {};
                          obj = temp_indi;
                          obj.category = ""+temp_o["category"];
                          obj.frequency = ""+temp_o["frequency"];
                         // temp_obj.sel_indicators[k] = obj;
                         //this.customer.policy_action_arr[j].sel_indicators[k] = obj;
                         this.customer.policy_action_arr[j]["sel_indicators"][k] = obj;
                         }
                      }
                    } 
                  }

                      let service_arr=[];

                            for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
                             // let tempobj = this.customer.service_action_arr[pos];                
                             service_arr.push(JSON.parse(JSON.stringify(this.all_areas_indicators_arr)));                               
                            }
                            this.all_actions_indi_obj["service_action_arr"] = service_arr;

                            let workforce_arr=[]; 

                            for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
                             // let tempobj = this.customer.workforce_action_arr[pos];                
                             workforce_arr.push(JSON.parse(JSON.stringify(this.all_areas_indicators_arr)));                               
                            }
                            this.all_actions_indi_obj["workforce_action_arr"] = workforce_arr;

                            let supplies_arr=[];

                            for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
                             // let tempobj = this.customer.supplies_action_arr[pos];                
                             supplies_arr.push(JSON.parse(JSON.stringify(this.all_areas_indicators_arr)));                               
                            }
                            this.all_actions_indi_obj["supplies_action_arr"] = supplies_arr;

                            let health_arr=[];

                            for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
                             // let tempobj = this.customer.health_action_arr[pos];                
                             health_arr.push(JSON.parse(JSON.stringify(this.all_areas_indicators_arr)));                               
                            }
                            this.all_actions_indi_obj["health_action_arr"] = health_arr;

                            let finance_arr=[];

                            for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
                             // let tempobj = this.customer.finance_action_arr[pos];                
                             finance_arr.push(JSON.parse(JSON.stringify(this.all_areas_indicators_arr)));                               
                            }
                            this.all_actions_indi_obj["finance_action_arr"] = finance_arr;

                            let policy_arr=[];

                            for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
                             // let tempobj = this.customer.policy_action_arr[pos];                
                             policy_arr.push(JSON.parse(JSON.stringify(this.all_areas_indicators_arr)));                               
                            }
                            this.all_actions_indi_obj["policy_action_arr"] = policy_arr;
                            
                          },
                          error => {
                            console.log(error); alert("Error= " + error);
                          });


                    },
                    error => {
                      console.log(error); alert("Error= " + error);
                    });


              },
              error => {
                console.log(error); alert("Error= " + error);
              });


        },
        error => {
          console.log(error); alert("Error= " + error);
        });




    // this.dynamicForm = this.formBuilder.group({




    //   date_of_meeting: ['2016-06-15'],
    //   venue_of_meeting: new FormControl(),
    //   chariperson_of_meeting: new FormControl(),
    //   theme_id: ['143'],
    //   theme_leader: new FormControl(),
    //   service_action_part: ['Service delivery'],
    //   service_action_point_name: new FormControl(),
    //   service_responsible_dept: new FormControl(),
    //   service_person_responsible: new FormControl(),
    //   service_target: new FormControl(),
    //   service_indicator_name: new FormControl(),
    //   service_description_of_indicator: new FormControl(),
    //   service_target_value: new FormControl(),


    //   workforce_action_part: ['Workforce'],
    //   workforce_action_point_name: new FormControl(),
    //   workforce_responsible_dept: new FormControl(),
    //   workforce_person_responsible: new FormControl(),
    //   workforce_target: new FormControl(),
    //   workforce_indicator_name: new FormControl(),
    //   workforce_description_of_indicator: new FormControl(),
    //   workforce_target_value: new FormControl(),



    //   supplies_action_part: ['Supplies & technology'],
    //   supplies_action_point_name: new FormControl(),
    //   supplies_responsible_dept: new FormControl(),
    //   supplies_person_responsible: new FormControl(),
    //   supplies_target: new FormControl(),
    //   supplies_indicator_name: new FormControl(),
    //   supplies_description_of_indicator: new FormControl(),
    //   supplies_target_value: new FormControl(),



    //   health_action_part: ['Health information'],
    //   health_action_point_name: new FormControl(),
    //   health_responsible_dept: new FormControl(),
    //   health_person_responsible: new FormControl(),
    //   health_target: new FormControl(),
    //   health_indicator_name: new FormControl(),
    //   health_description_of_indicator: new FormControl(),
    //   health_target_value: new FormControl(),



    //   finance_action_part: ['Finance'],
    //   finance_action_point_name: new FormControl(),
    //   finance_responsible_dept: new FormControl(),
    //   finance_person_responsible: new FormControl(),
    //   finance_target: new FormControl(),
    //   finance_indicator_name: new FormControl(),
    //   finance_description_of_indicator: new FormControl(),
    //   finance_target_value: new FormControl(),



    //   policy_action_part: ['Policy/governance'],
    //   policy_action_point_name: new FormControl(),
    //   policy_responsible_dept: new FormControl(),
    //   policy_person_responsible: new FormControl(),
    //   policy_target: new FormControl(),
    //   policy_indicator_name: new FormControl(),
    //   policy_description_of_indicator: new FormControl(),
    //   policy_target_value: new FormControl()
    // });

    $(document).ready(function () {


    });
  }


  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get dprs() { return this.f.defining_primary_role_section_select as FormArray; }


  onClickAddRow() {

  }

  onClickRemove() {

  }




  all_actions_indi_obj={};

  dialog_response: any = null;

  openDialog(arr_name, index): void {

    let final_obj = null;

    if (arr_name == "service_action_arr") {
      this.selected_indi = this.customer.service_action_arr[index].sel_indicators;

      let arr=this.all_actions_indi_obj["service_action_arr"];

      final_obj  = arr[index];

    }
    else if (arr_name == "workforce_action_arr") {
      this.selected_indi = this.customer.workforce_action_arr[index].sel_indicators;

      let arr=this.all_actions_indi_obj["workforce_action_arr"];

      final_obj  = arr[index];

    }
    else if (arr_name == "supplies_action_arr") {
      this.selected_indi = this.customer.supplies_action_arr[index].sel_indicators;

      let arr=this.all_actions_indi_obj["supplies_action_arr"];

      final_obj  = arr[index];

    } else if (arr_name == "health_action_arr") {
      this.selected_indi = this.customer.health_action_arr[index].sel_indicators;

      let arr=this.all_actions_indi_obj["health_action_arr"];

      final_obj  = arr[index];

    } else if (arr_name == "finance_action_arr") {
      this.selected_indi = this.customer.finance_action_arr[index].sel_indicators;

      let arr=this.all_actions_indi_obj["finance_action_arr"];

      final_obj  = arr[index];

    } else if (arr_name == "policy_action_arr") {
      this.selected_indi = this.customer.policy_action_arr[index].sel_indicators;

      let arr=this.all_actions_indi_obj["policy_action_arr"];

      final_obj  = arr[index];
    }

    const dialogRef = this.dialog.open(Form4ModalDialogComponent, {
      data: {
        myvar: "My variable value",
         //arr: this.all_areas_indicators_arr,
         arr: final_obj,
        dialog_result: this.dialog_response,
        selected_indi: this.selected_indi
      },
      height: '70%',
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
                  this.router.navigate(['/dashboard/form4plan']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error); });
          }
        }

      }
      else {
        //this.selected_indi = result;



        for (var obj in result) {
          //alert(JSON.stringify(result[obj]));

          try {
            if (result[obj].target) {

            }
            else {
              result[obj].target = "0";
            }
          } catch (error) {
            console.log("Error at :: " + JSON.stringify(result[obj]));
          }

        }

        this.show_selected_indi_Onscreen(result, arr_name, index);
      }



    });
  }

  global_indi_obj = null;


  show_selected_indi_Onscreen(result: any, category_of_indicator: string, index_no: number) {

    //alert(JSON.stringify(result,null,100));

    if (category_of_indicator == "service_action_arr") {
      this.customer.service_action_arr[index_no].sel_indicators = result;

      for(let i=0;i<this.global_indi_obj.length;i++){
        let temp_o = this.global_indi_obj[i];

        for(let j=0;j<this.customer.service_action_arr.length;j++){
          let temp_obj = this.customer.service_action_arr[j];

          for(let k=0;k<temp_obj.sel_indicators.length;k++){
            let temp_indi = temp_obj.sel_indicators[k];

            if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
              let obj:any = {};
              obj = temp_indi;
              obj.category = ""+temp_o["category"];
              obj.frequency = ""+temp_o["frequency"];
             // temp_obj.sel_indicators[k] = obj;
             //this.customer.policy_action_arr[j].sel_indicators[k] = obj;
             this.customer.service_action_arr[j]["sel_indicators"][k] = obj;
             } 
          }
        } 
      }

     // alert("service result "+JSON.stringify(result,null,1000)); 

    }
    else if (category_of_indicator == "workforce_action_arr") {
      this.customer.workforce_action_arr[index_no].sel_indicators = result;

      for(let i=0;i<this.global_indi_obj.length;i++){
        let temp_o = this.global_indi_obj[i];

        for(let j=0;j<this.customer.workforce_action_arr.length;j++){
          let temp_obj = this.customer.workforce_action_arr[j];

          for(let k=0;k<temp_obj.sel_indicators.length;k++){
            let temp_indi = temp_obj.sel_indicators[k];

            if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
              let obj:any = {};
              obj = temp_indi;
              obj.category = ""+temp_o["category"];
              obj.frequency = ""+temp_o["frequency"];
             // temp_obj.sel_indicators[k] = obj;
             //this.customer.workforce_action_arr[j].sel_indicators[k] = obj;
             this.customer.workforce_action_arr[j]["sel_indicators"][k] = obj;
             } 
          }
        } 
      }



    }
    else if (category_of_indicator == "supplies_action_arr") {
      this.customer.supplies_action_arr[index_no].sel_indicators = result;

      for(let i=0;i<this.global_indi_obj.length;i++){
        let temp_o = this.global_indi_obj[i];

        for(let j=0;j<this.customer.supplies_action_arr.length;j++){
          let temp_obj = this.customer.supplies_action_arr[j];

          for(let k=0;k<temp_obj.sel_indicators.length;k++){
            let temp_indi = temp_obj.sel_indicators[k];

            if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
              let obj:any = {};
              obj = temp_indi;
              obj.category = ""+temp_o["category"];
              obj.frequency = ""+temp_o["frequency"];
             // temp_obj.sel_indicators[k] = obj;
             //this.customer.supplies_action_arr[j].sel_indicators[k] = obj;
             this.customer.supplies_action_arr[j]["sel_indicators"][k] = obj;
             } 
          }
        } 
      }

    } else if (category_of_indicator == "health_action_arr") {
      this.customer.health_action_arr[index_no].sel_indicators = result;

      for(let i=0;i<this.global_indi_obj.length;i++){
        let temp_o = this.global_indi_obj[i];

        for(let j=0;j<this.customer.health_action_arr.length;j++){
          let temp_obj = this.customer.health_action_arr[j];

          for(let k=0;k<temp_obj.sel_indicators.length;k++){
            let temp_indi = temp_obj.sel_indicators[k];

            if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
              let obj:any = {};
              obj = temp_indi;
              obj.category = ""+temp_o["category"];
              obj.frequency = ""+temp_o["frequency"];
             // temp_obj.sel_indicators[k] = obj;
             //this.customer.health_action_arr[j].sel_indicators[k] = obj;
             this.customer.health_action_arr[j]["sel_indicators"][k] = obj;
             } 
          }
        } 
      }

    } else if (category_of_indicator == "finance_action_arr") {
      this.customer.finance_action_arr[index_no].sel_indicators = result;

      for(let i=0;i<this.global_indi_obj.length;i++){
        let temp_o = this.global_indi_obj[i];

        for(let j=0;j<this.customer.finance_action_arr.length;j++){
          let temp_obj = this.customer.finance_action_arr[j];

          for(let k=0;k<temp_obj.sel_indicators.length;k++){
            let temp_indi = temp_obj.sel_indicators[k];

            if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
              let obj:any = {};
              obj = temp_indi;
              obj.category = ""+temp_o["category"];
              obj.frequency = ""+temp_o["frequency"];
             // temp_obj.sel_indicators[k] = obj;
             //this.customer.finance_action_arr[j].sel_indicators[k] = obj;
             this.customer.finance_action_arr[j]["sel_indicators"][k] = obj;
             } 
          }
        } 
      }

    } else if (category_of_indicator == "policy_action_arr") {
      this.customer.policy_action_arr[index_no].sel_indicators = result;

      for(let i=0;i<this.global_indi_obj.length;i++){
        let temp_o = this.global_indi_obj[i];

        for(let j=0;j<this.customer.policy_action_arr.length;j++){
          let temp_obj = this.customer.policy_action_arr[j];

          for(let k=0;k<temp_obj.sel_indicators.length;k++){
            let temp_indi = temp_obj.sel_indicators[k];

            if(temp_indi["indicator_id"] == temp_o["indicator_id"]){
              let obj:any = {};
              obj = temp_indi;
              obj.category = ""+temp_o["category"];
              obj.frequency = ""+temp_o["frequency"];
             // temp_obj.sel_indicators[k] = obj;
             //this.customer.policy_action_arr[j].sel_indicators[k] = obj;
             this.customer.policy_action_arr[j]["sel_indicators"][k] = obj;
             } 
          }
        } 
      }


    }
  }
  

  validateSpecialCharsAndNum(val: string) {
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
    return (!!(val).replace(/[A-Za-z\s]/gi, '').length);
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



  onSubmit() {
    // Year should be in this format
    // 2016-06-15

    this.submitted = true;

    let finalresult = true;
    let no_data_available = 'No Data Available';


    for(let pos=0;pos<this.customer.service_action_arr.length;pos++){ 
      let tempobj = this.customer.service_action_arr[pos];
      
      if(tempobj['document_action_required'] != no_data_available){
        
      
      let indicator_length = (tempobj['sel_indicators']).length;

      if(indicator_length == 0){
        alert("No indicator selected in Service Delivery");
        return;
      }

      if(indicator_length > 0 && this.service_timeline[0].value == null){
        alert("Service Delivery 'Timeline' not filled");
        return;
      }

	 if(this.service_timeline[pos].value != null && this.service_timeline[pos].value != ''){
      let tempdate = new Date("" + this.service_timeline[pos].value);

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
	 }else{
    tempobj.timeline = null;
	 }

  }else{
    let tempdate = new Date("" + '1900-01-01');

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
  }

    }


    for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
      let tempobj = this.customer.workforce_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;

      if(indicator_length == 0){
        alert("No indicator selected in Workforce");
        return;
      }

      if(indicator_length > 0 && this.workforce_timeline[0].value == null){
        alert("Workforce 'Timeline' not filled");
        return;
      }

	if(this.workforce_timeline[pos].value != null && this.workforce_timeline[pos].value != ''){
      let tempdate = new Date("" + this.workforce_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
	  }else{
      tempobj.timeline = null;
	 }

  }else{
    let tempdate = new Date("" + '1900-01-01');

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
  }

    } 


    for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
      let tempobj = this.customer.supplies_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;

      if(indicator_length == 0){
        alert("No indicator selected in Supplies & Technology");
        return;
      }

      if(indicator_length > 0 && this.supplies_timeline[0].value == null){
        alert("Supplies & Technology 'Timeline' not filled");
      return;
      }
	if(this.supplies_timeline[pos].value != null && this.supplies_timeline[pos].value != ''){
      let tempdate = new Date("" + this.supplies_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
	  }else{
      tempobj.timeline = null;
	 }

  }else{
    let tempdate = new Date("" + '1900-01-01');

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
  }

    }

    for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
      let tempobj = this.customer.health_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;

      if(indicator_length == 0){
        alert("No indicator selected in Health Information");
        return;
      }

      if(indicator_length > 0 && this.health_timeline[0].value == null){
        alert("Health Information 'Timeline' not filled");
      return;
      }
	if(this.health_timeline[pos].value != null && this.health_timeline[pos].value != ''){
      let tempdate = new Date("" + this.health_timeline[pos].value); 

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
	  }else{
      tempobj.timeline = null;
	 }

  }else{
    let tempdate = new Date("" + '1900-01-01');

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
  }

    }


    for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
      let tempobj = this.customer.finance_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;

      if(indicator_length == 0){
        alert("No indicator selected in Finance");
        return;
      }

      if(indicator_length > 0 && this.finance_timeline[0].value == null){
        alert("Finance 'Timeline' not filled");
        return;
      }

	if(this.finance_timeline[pos].value != null && this.finance_timeline[pos].value != ''){
      let tempdate = new Date("" + this.finance_timeline[pos].value); 
     
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
	  }else{
      tempobj.timeline = null;
	 }

  }else{
    let tempdate = new Date("" + '1900-01-01');

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
  }

    }


    for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
      let tempobj = this.customer.policy_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;

      if(indicator_length == 0){
        alert("No indicator selected in Policy/Governance");
        return;
      }

      if(indicator_length > 0 && this.policy_timeline[0].value == null){
        alert("Policy/Governance 'Timeline' not filled");
      return;
      }
	
	if(this.policy_timeline[pos].value != null && this.policy_timeline[pos].value != ''){
      let tempdate = new Date("" + this.policy_timeline[pos].value); 
      
      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
	  }else{
      tempobj.timeline = null;
	 }

  }else{
    let tempdate = new Date("" + '1900-01-01');

      let finalday = tempdate.getDate();
      let finalmonth = (tempdate.getMonth() + 1);
      let finalyear = tempdate.getFullYear();

      let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

      tempobj.timeline = finalfulldate;
  }

    }


    if (this.customer.venue_of_meeting == '') {
      finalresult = false;
    }/*
    else if (this.validateSpecialCharsAndNum(  this.customer.venue_of_meeting)) {
      finalresult = false;
    }*/
    else if (this.validate100charactersallowed(  this.customer.venue_of_meeting)) {
      finalresult = false;
    }
    else if (this.customer.chariperson_of_meeting == '') {
      finalresult = false;
    }
    else if (this.customer.chariperson_of_meeting == '15' && this.customer.chariperson_of_meeting_others == '') {
      finalresult = false;
    }
    else if (this.customer.theme_leader == '') {
      finalresult = false;
    }/*
    else if (this.validateSpecialCharsAndNum(this.customer.venue_of_meeting)) {
      finalresult = false;
    }
    else if (this.validateSpecialCharsAndNum(this.customer.theme_leader)) {
      finalresult = false;
    }*/
    else if (this.validate100charactersallowed(  this.customer.theme_leader)) {
      finalresult = false;
    }


    for (let j = 0; j < this.customer.service_action_arr.length; j++) {
      let tempobj = this.customer.service_action_arr[j];


      if(tempobj['document_action_required'] != no_data_available){

      if (tempobj['sel_stakeholder'] == '') {
        finalresult = false;
      }
      else if (tempobj['person_responsible'] == '') {
          finalresult = false;
        }/*
        else if (this.validateSpecialCharsAndNum(tempobj['person_responsible'])) {
          finalresult = false;
        }*/
        else if (this.validate100charactersallowed(tempobj['person_responsible'])) {
          finalresult = false;
        }
        else
          if (tempobj['timeline'] == '') {
            finalresult = false;
          }
          else {
            for (let z = 0; z < (tempobj['sel_indicators']).length; z++) {
              let tempobj2 = tempobj['sel_indicators'][z];

              if (tempobj2['target'] == '') {
                finalresult = false;
              }
              else if (this.validateNumbers( tempobj2['target'])) {
                finalresult = false;
              }
            }
          }

        }

    }


    for (let j = 0; j < this.customer.workforce_action_arr.length; j++) {
      let tempobj = this.customer.workforce_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if (tempobj['sel_stakeholder'] == '') {
        finalresult = false;
      }
      else
        if (tempobj['person_responsible'] == '') {
          finalresult = false;
        }/*
        else if (this.validateSpecialCharsAndNum(tempobj['person_responsible'])) {
          finalresult = false;
        }*/
        else if (this.validate100charactersallowed(tempobj['person_responsible'])) {
          finalresult = false;
        }
        else
          if (tempobj['timeline'] == '') {
            finalresult = false;
          }
          else {
            for (let z = 0; z < (tempobj['sel_indicators']).length; z++) {
              let tempobj2 = tempobj['sel_indicators'][z];

              if (tempobj2['target'] == '') {
                finalresult = false;
              }
              else if (this.validateNumbers( tempobj2['target'])) {
                finalresult = false;
              }
            }
          }

        }

    }



    for (let j = 0; j < this.customer.supplies_action_arr.length; j++) {
      let tempobj = this.customer.supplies_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if (tempobj['sel_stakeholder'] == '') {
        finalresult = false;
      }
      else
        if (tempobj['person_responsible'] == '') {
          finalresult = false;
        }/*
        else if (this.validateSpecialCharsAndNum(tempobj['person_responsible'])) {
          finalresult = false;
        }*/
        else if (this.validate100charactersallowed(tempobj['person_responsible'])) {
          finalresult = false;
        }
        else
          if (tempobj['timeline'] == '') {
            finalresult = false;
          }
          else {
            for (let z = 0; z < (tempobj['sel_indicators']).length; z++) {
              let tempobj2 = tempobj['sel_indicators'][z];

              if (tempobj2['target'] == '') {
                finalresult = false;
              }
              else if (this.validateNumbers( tempobj2['target'])) {
                finalresult = false;
              }
            }
          }

        }

    }




    for (let j = 0; j < this.customer.health_action_arr.length; j++) {
      let tempobj = this.customer.health_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if (tempobj['sel_stakeholder'] == '') {
        finalresult = false;
      }
      else
        if (tempobj['person_responsible'] == '') {
          finalresult = false;
        }/*
        else if (this.validateSpecialCharsAndNum(tempobj['person_responsible'])) {
          finalresult = false;
        }*/
        else if (this.validate100charactersallowed(tempobj['person_responsible'])) {
          finalresult = false;
        }
        else
          if (tempobj['timeline'] == '') {
            finalresult = false;
          }
          else {
            for (let z = 0; z < (tempobj['sel_indicators']).length; z++) {
              let tempobj2 = tempobj['sel_indicators'][z];

              if (tempobj2['target'] == '') {
                finalresult = false;
              }
              else if (this.validateNumbers( tempobj2['target'])) {
                finalresult = false;
              }
            }
          }

        }

    }




    for (let j = 0; j < this.customer.finance_action_arr.length; j++) {
      let tempobj = this.customer.finance_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if (tempobj['sel_stakeholder'] == '') {
        finalresult = false;
      }
      else
        if (tempobj['person_responsible'] == '') {
          finalresult = false;
        }/*
        else if (this.validateSpecialCharsAndNum(tempobj['person_responsible'])) {
          finalresult = false;
        }*/
        else if (this.validate100charactersallowed(tempobj['person_responsible'])) {
          finalresult = false;
        }
        else
          if (tempobj['timeline'] == '') {
            finalresult = false;
          }
          else {
            for (let z = 0; z < (tempobj['sel_indicators']).length; z++) {
              let tempobj2 = tempobj['sel_indicators'][z];

              if (tempobj2['target'] == '') {
                finalresult = false;
              }
              else if (this.validateNumbers( tempobj2['target'])) {
                finalresult = false;
              }
            }
          }

        }

    }





    for (let j = 0; j < this.customer.policy_action_arr.length; j++) {
      let tempobj = this.customer.policy_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if (tempobj['sel_stakeholder'] == '') {
        finalresult = false;
      }
      else
        if (tempobj['person_responsible'] == '') {
          finalresult = false;
        }/*
        else if (this.validateSpecialCharsAndNum(tempobj['person_responsible'])) {
          finalresult = false;
        }*/
        else if (this.validate100charactersallowed(tempobj['person_responsible'])) {
          finalresult = false;
        }
        else
          if (tempobj['timeline'] == '') {
            finalresult = false;
          }
          else {
            for (let z = 0; z < (tempobj['sel_indicators']).length; z++) {
              let tempobj2 = tempobj['sel_indicators'][z];

              if (tempobj2['target'] == '') {
                finalresult = false;
              }
              else if (this.validateNumbers( tempobj2['target'])) {
                finalresult = false;
              }
            }
          }

        }

    }

    if (!finalresult) {
      alert("Form invalid");
      return;
    }

    //console.log(this.dynamicForm.value);
    // display form values on success
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

   

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    let final_dst: string, final_cyc: string, final_year: string, final_u_id: string;

    final_dst = "" + sessionStorage.getItem('district');
    final_cyc = "" + sessionStorage.getItem('cycle');
    final_year = "" + sessionStorage.getItem('year');
    final_u_id = "" + sessionStorage.getItem('userid');

    this.completeClicked = true;

    this._diphHttpClientService.getSavedForm1BDetails(final_dst, final_cyc, final_year, final_u_id)
      .subscribe(
        result_data => {

          this.customer.theme_id = result_data.theme_id;

          //console.log("this.customer : "+JSON.stringify);
          //alert("login_district = "+login_district+"\nlogin_cycle = "+login_cycle+"\n login_year = "+login_year);
          this._diphHttpClientService.saveform4planDetails(this.customer, login_district, login_cycle, login_year, login_userid, "1")
            .subscribe(
              data => {
               // console.log(data);
                this.savedform = true;
                this.completeClicked = false;
                this.router.navigate(['/dashboard/form4planview']);
              },
              error => { 
	      this.completeClicked = false;
                alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
                this.router.navigate(['dashboard']);
               });
        },
        error => {
	this.completeClicked = false;
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
        });




  }


  partialSave(){

     
	if (this.customer.venue_of_meeting == null || this.customer.venue_of_meeting == '') {
    alert("Venue of meeting is compulsary");
  return;
  }
  else if (this.validate100charactersallowed(this.customer.venue_of_meeting)) {
    alert("Venue of meeting can not exceed 5000 characters");
  return;
  }
  
if (this.customer.chariperson_of_meeting == null || this.customer.chariperson_of_meeting == '') {
    alert("Chairperson of meeting is compulsary");
  return;
  }
  else if (this.customer.chariperson_of_meeting == '15' && this.customer.chariperson_of_meeting_others == '') {
     alert("Chairperson of meeting is compulsary");
  return;
  }
  
if (this.customer.theme_leader == null || this.customer.theme_leader == '') {
     alert("Theme leader is compulsary");
  return;
  }
  else if (this.validate100charactersallowed(this.customer.theme_leader)) {
    alert("Theme leader can not exceed 5000 characters");
  return;
  }


  for(let pos=0;pos<this.customer.service_action_arr.length;pos++){ 
    let tempobj = this.customer.service_action_arr[pos];

 if(this.service_timeline[pos].value != null && this.service_timeline[pos].value != ''){
    let tempdate = new Date("" + this.service_timeline[pos].value);

    let finalday = tempdate.getDate();
    let finalmonth = (tempdate.getMonth() + 1);
    let finalyear = tempdate.getFullYear();

    let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday;

    tempobj.timeline = finalfulldate;
 }else{
   tempobj.timeline = null;
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

    tempobj.timeline = finalfulldate;
  }else{
   tempobj.timeline = null;
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

    tempobj.timeline = finalfulldate;
  }else{
   tempobj.timeline = null;
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

    tempobj.timeline = finalfulldate;
  }else{
   tempobj.timeline = null;
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

    tempobj.timeline = finalfulldate;
  }else{
   tempobj.timeline = null;
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

    tempobj.timeline = finalfulldate;
  }else{
   tempobj.timeline = null;
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

  this.customer.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

  let final_dst: string, final_cyc: string, final_year: string, final_u_id: string;

  final_dst = "" + sessionStorage.getItem('district');
  final_cyc = "" + sessionStorage.getItem('cycle');
  final_year = "" + sessionStorage.getItem('year');
  final_u_id = "" + sessionStorage.getItem('userid');

  this.completeClicked = true;

  this._diphHttpClientService.getSavedForm1BDetails(final_dst, final_cyc, final_year, final_u_id)
    .subscribe(
      result_data => {

        this.customer.theme_id = result_data.theme_id;
        //console.log("this.customer : "+JSON.stringify(this.customer));
        this._diphHttpClientService.saveform4planDetails(this.customer, login_district, login_cycle, login_year, login_userid, "0")
          .subscribe(
            data => {
              this.completeClicked = false;
              this.savedform = true;
             
              this.router.navigate(['/dashboard/form4planview']);
            },
            error => { 
	    this.completeClicked = false;
              alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
              this.router.navigate(['dashboard']);
             });
      },
      error => {
      this.completeClicked = false;
        alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
        this.router.navigate(['dashboard']);
      });

  }

  show_part2_also() {

  }

  previouspage() {
    let ans = confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");

    if (ans) {
      this.router.navigate(['dashboard']);
    }

  }

}
