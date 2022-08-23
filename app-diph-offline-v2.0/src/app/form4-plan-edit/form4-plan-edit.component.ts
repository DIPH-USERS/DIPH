import { Component, OnInit, Input } from '@angular/core';
import { Observable } from 'rxjs';
import { form1bEdit } from '../model/form1bEdit';
import { PlatformLocation } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { Router } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { MatDialog } from '@angular/material';
import { Form1bModalDialogComponent } from '../form1b-modal-dialog/form1b-modal-dialog.component';
import { Form4ModalDialogComponent } from '../form4-modal-dialog/form4-modal-dialog.component';
import { isNull } from 'util';

@Component({
  selector: 'app-form4-plan-edit',
  templateUrl: './form4-plan-edit.component.html',
  styleUrls: ['./form4-plan-edit.component.css']
})
export class Form4PlanEditComponent implements OnInit {

  completeClicked : boolean = false;

  @Input() customer: any = {};
  finallyloaded = false;
  date = new FormControl(new Date());

  form_followup_not_filled = false;

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
  minDate =new Date("12,01,2019"); 

  theme_id: string;
  theme_name: string;
  loading = true;


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

    this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chariperson_of_meeting == null) {
            alert("No data set for chosen District, Cycle and Year");
            console.log("No data set for chosen District, Cycle and Year, Redirecting to dashboard");
            this.router.navigate(['dashboard']);
          }
          else {

            this.customer = {};

            this.customer.service_action_arr = data.service_action_arr;
            this.customer.workforce_action_arr = data.workforce_action_arr;
            this.customer.supplies_action_arr = data.supplies_action_arr;
            this.customer.health_action_arr = data.health_action_arr;
            this.customer.finance_action_arr = data.finance_action_arr;
            this.customer.policy_action_arr = data.policy_action_arr;

          
            if(data.completed == '1')
            this.customer.completed = true;
              else if(data.completed == '0')
            this.customer.completed = false;

            

            this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
            .subscribe(
              response => {
       
                //console.log(response);    
                if (response == null || response.completed == null || response.userid == null)
                    this.form_followup_not_filled = true;
                      else
                    this.form_followup_not_filled = false;
                
                //This code is used in Indicators List (Fetches only ESSENTIAL and ACTION Indicators)
            this._diphHttpClientService.getAllIndicators()
            .subscribe(
              data => {
                
                this.global_indi_obj = data.areas_Id_Indicators_map_list;

                let global_indi_obj = null;
                global_indi_obj = data.areas_Id_Indicators_map_list;

      
                if (response.date_of_meeting == null && response.venue_of_meeting == null && response.chairperson_of_meeting == null) {
                  

                  //customer.service_delivery[{action_one,indi:[]},{},{}]

                  for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
                    let tempobj = this.customer.service_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "0";
                    }                  
                  }

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


                  

                  for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
                    let tempobj = this.customer.workforce_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "0";
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



                  for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
                    let tempobj = this.customer.supplies_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "0";
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


                  for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
                    let tempobj = this.customer.health_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "0";
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


                  for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
                    let tempobj = this.customer.finance_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "0";
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

                  for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
                    let tempobj = this.customer.policy_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "0";
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
      
                } else { 
                  //alert("Form5 filled");


                  for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
                    let tempobj = this.customer.service_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "1";
                    }                  
                  }

                  
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


                  for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
                    let tempobj = this.customer.workforce_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "1";
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


                  for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
                    let tempobj = this.customer.supplies_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "1";
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

                  for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
                    let tempobj = this.customer.health_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "1";
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


                  for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
                    let tempobj = this.customer.finance_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "1";
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

                  for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
                    let tempobj = this.customer.policy_action_arr[pos];
                    for(let index=0;index<tempobj.sel_indicators.length;index++){
                      let temp2obj = tempobj.sel_indicators[index];
                      temp2obj["form5_filled_flag"]  = "1";
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

                }

                

                
              },
              error => {
                console.log(error); alert("Error= " + error);
              });
      
               
              },
              error => {
                console.log(error); alert("Error= " + error);
              });


            

            let pos1=0;

            for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
              let tempobj = this.customer.service_action_arr[pos];

              let tmp = (tempobj.timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              if(tempobj.timeline == "null")
                this.service_timeline[pos1] = new FormControl(null);              
              else
                this.service_timeline[pos1] = new FormControl(new Date(finaldate));
              // this.service_timeline[pos1] = tempobj.timeline;
              pos1++;
            }

            let pos2=0;
            for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
              let tempobj = this.customer.workforce_action_arr[pos];

              let tmp = (tempobj.timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];              

              if(tempobj.timeline == "null")
                this.workforce_timeline[pos2] = new FormControl(null);
              else
              this.workforce_timeline[pos2] = new FormControl(new Date(finaldate));

              //this.workforce_timeline[pos2] = new FormControl(tempobj.timeline);
              pos2++;
            }

            let pos3=0;
            for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
              let tempobj = this.customer.supplies_action_arr[pos];

              let tmp = (tempobj.timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              if(tempobj.timeline == "null")
                this.supplies_timeline[pos3] = new FormControl(null);
              else
              this.supplies_timeline[pos3] = new FormControl(new Date(finaldate));

              //this.supplies_timeline[pos3] = new FormControl(tempobj.timeline);
              pos3++;
            }

            let pos4=0;
            for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
              let tempobj = this.customer.health_action_arr[pos];

              let tmp = (tempobj.timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              if(tempobj.timeline == "null")
                this.health_timeline[pos4] = new FormControl(null);
              else
              this.health_timeline[pos4] = new FormControl(new Date(finaldate));

              //this.health_timeline[pos4] = new FormControl(tempobj.timeline);
              pos4++;
            }

            let pos5=0;
            for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
              let tempobj = this.customer.finance_action_arr[pos];

              let tmp = (tempobj.timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              if(tempobj.timeline == "null")
              this.finance_timeline[pos5] = new FormControl(null);
            else
              this.finance_timeline[pos5] = new FormControl(new Date(finaldate));

              //this.finance_timeline[pos5] = new FormControl(tempobj.timeline);
              pos5++;
            }

            let pos6=0;
            for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
              let tempobj = this.customer.policy_action_arr[pos];

              let tmp = (tempobj.timeline+"").split('-');
              let finaldate = tmp[1]+'/'+tmp[2]+'/'+tmp[0];

              if(tempobj.timeline == "null")
              this.policy_timeline[pos6] = new FormControl(null);
            else
              this.policy_timeline[pos6] = new FormControl(new Date(finaldate));

              //this.policy_timeline[pos6] = new FormControl(tempobj.timeline);
              pos6++;
            }
            

            //this.service_timeline = new FormControl(new Date());
            //this.workforce_timeline = new FormControl(new Date());
            //this.supplies_timeline = new FormControl(new Date());
            //this.health_timeline = new FormControl(new Date());
            //this.finance_timeline = new FormControl(new Date());
            //this.policy_timeline = new FormControl(new Date());

            this.customer.date_of_meeting = data.date_of_meeting;
            this.date = new FormControl(this.customer.date_of_meeting);
            this.customer.venue_of_meeting = data.venue_of_meeting;
            this.customer.chariperson_of_meeting = data.chariperson_of_meeting;
            this.customer.chariperson_of_meeting_others = data.chariperson_of_meeting_others;
            this.customer.theme_id = data.theme_id;
            this.customer.theme_leader = data.theme_leader;
            this.customer.service_action_part = data.service_action_part;
            this.customer.service_action_point_name = data.service_action_point_name;
            this.customer.service_responsible_dept = data.service_responsible_dept;
            this.customer.service_person_responsible = data.service_person_responsible;
            this.customer.service_target = data.service_target;
            this.customer.service_indicator_name = data.service_indicator_name;
            this.customer.service_description_of_indicator = data.service_description_of_indicator;
            this.customer.service_target_value = data.service_target_value;
            this.customer.workforce_action_part = data.workforce_action_part;
            this.customer.workforce_action_point_name = data.workforce_action_point_name;
            this.customer.workforce_responsible_dept = data.workforce_responsible_dept;
            this.customer.workforce_person_responsible = data.workforce_person_responsible;
            this.customer.workforce_target = data.workforce_target;
            this.customer.workforce_indicator_name = data.workforce_indicator_name;
            this.customer.workforce_description_of_indicator = data.workforce_description_of_indicator;
            this.customer.workforce_target_value = data.workforce_target_value;
            this.customer.supplies_action_part = data.supplies_action_part;
            this.customer.supplies_action_point_name = data.supplies_action_point_name;
            this.customer.supplies_responsible_dept = data.supplies_responsible_dept;
            this.customer.supplies_person_responsible = data.supplies_person_responsible;
            this.customer.supplies_target = data.supplies_target;
            this.customer.supplies_indicator_name = data.supplies_indicator_name;
            this.customer.supplies_description_of_indicator = data.supplies_description_of_indicator;
            this.customer.supplies_target_value = data.supplies_target_value;
            this.customer.health_action_part = data.health_action_part;
            this.customer.health_action_point_name = data.health_action_point_name;
            this.customer.health_responsible_dept = data.health_responsible_dept;
            this.customer.health_person_responsible = data.health_person_responsible;
            this.customer.health_target = data.health_target;
            this.customer.health_target = data.health_target;
            this.customer.health_indicator_name = data.health_indicator_name;
            this.customer.health_description_of_indicator = data.health_description_of_indicator;
            this.customer.health_target_value = data.health_target_value;
            this.customer.finance_action_part = data.finance_action_part;
            this.customer.finance_action_point_name = data.finance_action_point_name;
            this.customer.finance_responsible_dept = data.finance_responsible_dept;
            this.customer.finance_person_responsible = data.finance_person_responsible;
            this.customer.finance_target = data.finance_target;
            this.customer.finance_indicator_name = data.finance_indicator_name;
            this.customer.finance_description_of_indicator = data.finance_description_of_indicator;
            this.customer.finance_target_value = data.finance_target_value;
            this.customer.policy_action_part = data.policy_action_part;
            this.customer.policy_action_point_name = data.policy_action_point_name;
            this.customer.policy_responsible_dept = data.policy_responsible_dept;
            this.customer.policy_person_responsible = data.policy_person_responsible;
            this.customer.policy_target = data.policy_target;
            this.customer.policy_indicator_name = data.policy_indicator_name;
            this.customer.policy_description_of_indicator = data.policy_description_of_indicator;
            this.customer.policy_target_value = data.policy_target_value;


            //alert(JSON.stringify(this.customer, null, 4));
          }

          this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
            .subscribe(
              data => {

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

                  let str1 = temp_arr.document_name;

                  temp_stakeholder_arr.push(temp_arr);
                }



                for (let y = 0; y < jsonresponse.secondary_stake_array.length; y++) {
                  let temp_arr2 = jsonresponse.secondary_stake_array[y];

                  let str2 = temp_arr2.document_name;

                  temp_stakeholder_arr.push(temp_arr2);
                }

                //alert(JSON.stringify(temp_stakeholder_arr,null,4));

                this.stakeholder_arr = temp_stakeholder_arr;


                // this.stakeholder_arr.push({ id: jsonresponse.primary_stakeholder_id, name: jsonresponse.primary_stakeholder_text });
                // this.stakeholder_arr.push({ id: jsonresponse.secondary_stakeholder_id, name: jsonresponse.secondary_stakeholder_text });

                this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
                  .subscribe(
                    data => {


                      this.customer.theme_id = "" + this.theme_id;



                      this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
                        .subscribe(
                          data => {

                            let iphs_theme_name = data.iphs_theme_name;

                            this.theme_name = iphs_theme_name;

                            this.all_areas_indicators_arr = data.areas_Id_Indicators_map_list;


                            this.finallyloaded = true;

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
        });//Closing View Form4Plan


    $(document).ready(function () {


    });
  }

  all_actions_indi_obj={};




  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }


  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }



  onClickAddRow() {

  }

  onClickRemove() {

  }






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

    //alert("final_obj = "+JSON.stringify(final_obj,null,1000));

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
                  this.router.navigate(['/dashboard/form4planedit']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error); });
          }
        }


      }
      else {
        //this.selected_indi = result;

       
        this.show_selected_indi_Onscreen(result, arr_name, index);
      }



    });
  }


  global_indi_obj = null;


  show_selected_indi_Onscreen(result: any, category_of_indicator: string, index_no: number) {

    //alert("FinalResult = "+JSON.stringify(result,null,1000));

    // console.log(JSON.stringify(result,null,1000));

    for(let pos=0;pos<result.length;pos++){

      let tempobj = result[pos];

      if(Object.keys(tempobj).indexOf('target') !== -1){
        //alert("found");
      }else{
        //alert("not found");
        tempobj["target"] =  "0";
      }
    } 

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
             //this.customer.service_action_arr[j].sel_indicators[k] = obj;
             this.customer.service_action_arr[j]["sel_indicators"][k] = obj;
             } 
          }
        } 
      }

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


  onPrint() {
    window.print();
  }

  onSubmit() {

    this.submitted = true;

    let finalresult = true;
    let no_data_available = 'No Data Available';
    /*
    if(! this.form_followup_not_filled){
      alert("Form Plan data is used in next form. Data can not be edited.");
      window.location.reload();
      return;
    }
    */
    //alert("this.customer.venue_of_meeting = '"+this.customer.venue_of_meeting+"',  this.customer.theme_leader = '"+this.customer.theme_leader+"'");

    if(this.customer.venue_of_meeting == ''){
      finalresult = false;
    }/*
    else if(this.validateSpecialCharsAndNum(this.customer.venue_of_meeting)  ){
      finalresult = false;
    }*/
    else if(this.validate100charactersallowed(this.customer.venue_of_meeting)  ){
      finalresult = false;
    }
    else if(this.customer.chariperson_of_meeting == ''){
      finalresult = false;
    }
    else if(this.customer.theme_leader == ''){ 
      finalresult = false;
    }
    else if(this.validate100charactersallowed(this.customer.theme_leader)  ){
      finalresult = false;
    }
    else if(this.customer.chariperson_of_meeting == '15'  &&  this.customer.chariperson_of_meeting_others  == '' ){
      finalresult = false;
    }

    


    for(let j=0;j<this.customer.service_action_arr.length;j++){
      let tempobj = this.customer.service_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){


      if(tempobj['sel_stakeholder'] == ''){
        finalresult = false;
      } 
      else 
      if(tempobj['person_responsible'] == ''){
        finalresult = false;
      }
     
      else 
      if(this.validate100charactersallowed(tempobj['person_responsible'])  ){
        finalresult = false;
      }
      else 
      if(tempobj['timeline'] == ''){
        finalresult = false;
      }
      else{
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let tempobj2 = tempobj['sel_indicators'][z];

          if(tempobj2['target'] == '' ){
            finalresult = false;
          }
          else if (this.validateNumbers( tempobj2['target'])) {
            finalresult = false;
          }
        }
      }

    }

    }


    for(let j=0;j<this.customer.workforce_action_arr.length;j++){
      let tempobj = this.customer.workforce_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if(tempobj['sel_stakeholder'] == ''){
        finalresult = false;
      }
      else 
      if(tempobj['person_responsible'] == ''){
        finalresult = false;
      }
     
      else 
      if(this.validate100charactersallowed(tempobj['person_responsible'])  ){
        finalresult = false;
      }
      else 
      if(tempobj['timeline'] == ''){
        finalresult = false;
      }
      else{
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let tempobj2 = tempobj['sel_indicators'][z];

          if(tempobj2['target'] == '' ){
            finalresult = false;
          }
          else if (this.validateNumbers( tempobj2['target'])) {
            finalresult = false;
          }
        }
      }

    }

    }



    for(let j=0;j<this.customer.supplies_action_arr.length;j++){
      let tempobj = this.customer.supplies_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){


      if(tempobj['sel_stakeholder'] == ''){
        finalresult = false;
      }
      else 
      if(tempobj['person_responsible'] == ''){
        finalresult = false;
      }
      
      else 
      if(this.validate100charactersallowed(tempobj['person_responsible'])  ){
        finalresult = false;
      }
      else 
      if(tempobj['timeline'] == ''){
        finalresult = false;
      }
      else{
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let tempobj2 = tempobj['sel_indicators'][z];

          if(tempobj2['target'] == '' ){
            finalresult = false;
          }
          else if (this.validateNumbers( tempobj2['target'])) {
            finalresult = false;
          }
        }
      }

    }

    }




    for(let j=0;j<this.customer.health_action_arr.length;j++){
      let tempobj = this.customer.health_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if(tempobj['sel_stakeholder'] == ''){
        finalresult = false;
      }
      else 
      if(tempobj['person_responsible'] == ''){
        finalresult = false;
      }
     
      else 
      if(this.validate100charactersallowed(tempobj['person_responsible'])  ){
        finalresult = false;
      }
      else 
      if(tempobj['timeline'] == ''){
        finalresult = false;
      }
      else{
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let tempobj2 = tempobj['sel_indicators'][z];

          if(tempobj2['target'] == '' ){
            finalresult = false;
          }
          else if (this.validateNumbers( tempobj2['target'])) {
            finalresult = false;
          }
        }
      }

    }

    }




    for(let j=0;j<this.customer.finance_action_arr.length;j++){
      let tempobj = this.customer.finance_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if(tempobj['sel_stakeholder'] == ''){
        finalresult = false;
      }
      else 
      if(tempobj['person_responsible'] == ''){
        finalresult = false;
      }
     
      else 
      if(this.validate100charactersallowed(tempobj['person_responsible'])  ){
        finalresult = false;
      }
      else 
      if(tempobj['timeline'] == ''){
        finalresult = false;
      }
      else{
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let tempobj2 = tempobj['sel_indicators'][z];

          if(tempobj2['target'] == '' ){
            finalresult = false;
          }
          else if (this.validateNumbers( tempobj2['target'])) {
            finalresult = false;
          }
        }
      }

    }

    }





    for(let j=0;j<this.customer.policy_action_arr.length;j++){
      let tempobj = this.customer.policy_action_arr[j];

      if(tempobj['document_action_required'] != no_data_available){

      if(tempobj['sel_stakeholder'] == ''){
        finalresult = false;
      }
      else 
      if(tempobj['person_responsible'] == ''){
        finalresult = false;
      }
     
      else 
      if(this.validate100charactersallowed(tempobj['person_responsible'])  ){
        finalresult = false;
      }
      else 
      if(tempobj['timeline'] == ''){
        finalresult = false;
      }
      else{
        for(let z=0;z<(tempobj['sel_indicators']).length;z++){
          let tempobj2 = tempobj['sel_indicators'][z];

          if(tempobj2['target'] == '' ){
            finalresult = false;
          }
          else if (this.validateNumbers( tempobj2['target'])) {
            finalresult = false;
          }
        }
      }

    }

    }

    if(!finalresult){
      alert("Form invalid");
      return;
    }
    
    
    for(let pos=0;pos<this.customer.service_action_arr.length;pos++){ 
      let tempobj = this.customer.service_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;
      let tempdate = new Date("" + this.service_timeline[pos].value);

      if(indicator_length == 0){
        alert("No indicator selected in Service Delivery");
        return;
      }

      if(indicator_length > 0 && (tempdate.getDate() == null || isNaN(tempdate.getDate()))){
        alert("Service Delivery 'Timeline' not filled");
        return;
      }    
      
	    
      if(tempdate.getDate() == null || isNaN(tempdate.getDate())){
        tempobj.timeline = null;         
      }else{
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
      }

    }else{
      let tempdateDefault = new Date("" + '1900-01-01');
      let finalday = tempdateDefault.getDate();
        let finalmonth = (tempdateDefault.getMonth() + 1);
        let finalyear = tempdateDefault.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
    }
    
    }


    for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
      let tempobj = this.customer.workforce_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;
      let tempdate = new Date("" + this.workforce_timeline[pos].value);

      if(indicator_length == 0){
        alert("No indicator selected in Workforce");
        return;
      }

      if(indicator_length > 0 && (tempdate.getDate() == null || isNaN(tempdate.getDate()))){
        alert("Workforce 'Timeline' not filled");
        return;
      }
      
      if(tempdate.getDate() == null || isNaN(tempdate.getDate())){
        tempobj.timeline = null;         
      }else{
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
      }


    }else{
      let tempdateDefault = new Date("" + '1900-01-01');
      let finalday = tempdateDefault.getDate();
        let finalmonth = (tempdateDefault.getMonth() + 1);
        let finalyear = tempdateDefault.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
    }

    }  


    for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
      let tempobj = this.customer.supplies_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;
      let tempdate = new Date("" + this.supplies_timeline[pos].value);

      if(indicator_length == 0){
        alert("No indicator selected in Supplies & Technology");
        return;
      }

      if(indicator_length > 0 && (tempdate.getDate() == null || isNaN(tempdate.getDate()))){
        alert("Supplies & Technology 'Timeline' not filled");
      return;
      }

     
      if(tempdate.getDate() == null || isNaN(tempdate.getDate())){
        tempobj.timeline = null;         
      }else{
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
      }

    }else{
      let tempdateDefault = new Date("" + '1900-01-01');
      let finalday = tempdateDefault.getDate();
        let finalmonth = (tempdateDefault.getMonth() + 1);
        let finalyear = tempdateDefault.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
    }

    }

    

    for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
      let tempobj = this.customer.health_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;
      let tempdate = new Date("" + this.health_timeline[pos].value);

      if(indicator_length == 0){
        alert("No indicator selected in Health Information");
        return;
      }

      if(indicator_length > 0 && (tempdate.getDate() == null || isNaN(tempdate.getDate()))){
        alert("Health Information 'Timeline' not filled");
        return;
      }

      
      if(tempdate.getDate() == null || isNaN(tempdate.getDate())){
        tempobj.timeline = null;         
      }else{
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
      }


    }else{
      let tempdateDefault = new Date("" + '1900-01-01');
      let finalday = tempdateDefault.getDate();
        let finalmonth = (tempdateDefault.getMonth() + 1);
        let finalyear = tempdateDefault.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
    }

    }


    for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
      let tempobj = this.customer.finance_action_arr[pos];

      if(tempobj['document_action_required'] != no_data_available){


      let indicator_length = (tempobj['sel_indicators']).length;
      let tempdate = new Date("" + this.finance_timeline[pos].value);

      if(indicator_length == 0){
        alert("No indicator selected in Finance");
        return;
      }

      if(indicator_length > 0 && (tempdate.getDate() == null || isNaN(tempdate.getDate()))){
        alert("Finance 'Timeline' not filled");
        return;
      }

     
      if(tempdate.getDate() == null || isNaN(tempdate.getDate())){
        tempobj.timeline = null;         
      }else{
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
      }

    }else{
      let tempdateDefault = new Date("" + '1900-01-01');
      let finalday = tempdateDefault.getDate();
        let finalmonth = (tempdateDefault.getMonth() + 1);
        let finalyear = tempdateDefault.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
    }

    }


    for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
      let tempobj = this.customer.policy_action_arr[pos];


      if(tempobj['document_action_required'] != no_data_available){

      let indicator_length = (tempobj['sel_indicators']).length;
      let tempdate = new Date("" + this.policy_timeline[pos].value);

      if(indicator_length == 0){
        alert("No indicator selected in Policy/Governance");
        return;
      }

      if(indicator_length > 0 && (tempdate.getDate() == null || isNaN(tempdate.getDate()))){
        alert("Policy/Governance 'Timeline' not filled");
        return;
      }
	
      
      if(tempdate.getDate() == null || isNaN(tempdate.getDate())){
        tempobj.timeline = null;         
      }else{
        let finalday = tempdate.getDate();
        let finalmonth = (tempdate.getMonth() + 1);
        let finalyear = tempdate.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
      }

    }else{
      let tempdateDefault = new Date("" + '1900-01-01');
      let finalday = tempdateDefault.getDate();
        let finalmonth = (tempdateDefault.getMonth() + 1);
        let finalyear = tempdateDefault.getFullYear();
        let finalfulldate =  finalyear + "-" + finalmonth + "-" + finalday; 
        tempobj.timeline = finalfulldate;
    }

    }


    //alert("Form submitted");
    this.loading = true;
    //console.log(this.customer);


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');
    //this.customer.total_coverage_indi = total_coverage_indi;

    this.completeClicked = true;

    if(this.form_followup_not_filled){
      
    this._diphHttpClientService.editUpdateForm4PlanDetails(this.customer, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          this.completeClicked = false;         
          this.router.navigate(['dashboard/form4planview']);  
        },
        error => { 
	        this.completeClicked = false;
          alert("Error= " + error);
          this.router.navigate(['dashboard']);
        });
    }else{
      
      this._diphHttpClientService.editUpdateForm4PlanWhenFollowUpFilled(this.customer, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          this.completeClicked = false;         
          this.router.navigate(['dashboard/form4planview']);  
        },
        error => { 
	        this.completeClicked = false;
          alert("Error= " + error);
          this.router.navigate(['dashboard']);
        });
    }

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
  
    this.loading = true;
    //console.log(this.customer);


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    this.customer.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');
    //this.customer.total_coverage_indi = total_coverage_indi;

    this.completeClicked = true;

    this._diphHttpClientService.editUpdateForm4PlanDetails(this.customer, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {
          alert("Successfully submitted!!");
          this.completeClicked = false;
          this.router.navigate(['dashboard/form4planview']);  
        },
        error => { this.completeClicked = false; alert("Some Error Found.Please try again\nContact system admin in case of repeated error."); });
    
  }

}
