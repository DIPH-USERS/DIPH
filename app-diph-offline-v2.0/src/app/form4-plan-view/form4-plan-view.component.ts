import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { MatDialog } from '@angular/material';
import { Form1bModalDialogComponent } from '../form1b-modal-dialog/form1b-modal-dialog.component';

@Component({
  selector: 'app-form4-plan-view',
  templateUrl: './form4-plan-view.component.html',
  styleUrls: ['./form4-plan-view.component.css']
})
export class Form4PlanViewComponent implements OnInit {

  @Input() customer: any = {};
  date = new FormControl(new Date());
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
  loading = true;


  dynamicForm: FormGroup;

  constructor(location: PlatformLocation, public dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
    location.onPopState(() => {
      console.log("pressed back in add!!!!!");
      history.forward();
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

            

            for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
              let tempobj=this.customer.service_action_arr[pos];
              let tempdate = new Date("" + tempobj.timeline);
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              let finalfulldate =  finalmonth + "/" + finalday + "/" + finalyear;

              tempobj.timeline = finalfulldate;
              if(isNaN(tempdate.getDate()))tempobj.timeline = "";
            }

            for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
              let tempobj=this.customer.workforce_action_arr[pos];
              let tempdate = new Date("" + tempobj.timeline);
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              let finalfulldate =  finalmonth + "/" + finalday + "/" + finalyear;

              tempobj.timeline = finalfulldate;
              if(isNaN(tempdate.getDate()))tempobj.timeline = "";
            }

            for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
              let tempobj=this.customer.supplies_action_arr[pos];
              let tempdate = new Date("" + tempobj.timeline);
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              let finalfulldate =  finalmonth + "/" + finalday + "/" + finalyear;

              tempobj.timeline = finalfulldate;
              if(isNaN(tempdate.getDate()))tempobj.timeline = "";
            }

            for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
              let tempobj=this.customer.health_action_arr[pos];
              let tempdate = new Date("" + tempobj.timeline);
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              let finalfulldate =  finalmonth + "/" + finalday + "/" + finalyear;

              tempobj.timeline = finalfulldate;
              if(isNaN(tempdate.getDate()))tempobj.timeline = "";
            }

            for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
              let tempobj=this.customer.finance_action_arr[pos];
              let tempdate = new Date("" + tempobj.timeline);
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              let finalfulldate =  finalmonth + "/" + finalday + "/" + finalyear;

              tempobj.timeline = finalfulldate;
              if(isNaN(tempdate.getDate()))tempobj.timeline = "";
            }

            for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
              let tempobj=this.customer.policy_action_arr[pos];
              let tempdate = new Date("" + tempobj.timeline);
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              let finalfulldate =  finalmonth + "/" + finalday + "/" + finalyear;

              tempobj.timeline = finalfulldate;
              if(isNaN(tempdate.getDate()))tempobj.timeline = "";
            }

            this.customer.date_of_meeting = data.date_of_meeting;

            {
              let tempdate = new Date("" + this.customer.date_of_meeting);
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              this.customer.date_of_meeting =  finalmonth + "/" + finalday + "/" + finalyear;
            }

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




            //alert("this.customer.chariperson_of_meeting = "+this.customer.chariperson_of_meeting);
            //alert("this.customer.chariperson_of_meeting_others = "+this.customer.chariperson_of_meeting_others);

            let temp1: string;
            temp1 = this.customer.chariperson_of_meeting;

            for (let j = 0; j < this.verified_by_name_from_Db.length; j++) {
              let tempobj = this.verified_by_name_from_Db[j];

              if (tempobj['id'] == temp1) {
                this.customer.chariperson_of_meeting = tempobj['name'];
              }
            }

            if (temp1 == '15') {
              this.customer.chariperson_of_meeting = this.customer.chariperson_of_meeting_others;
            }


            // alert(JSON.stringify(this.customer, null, 4));
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

                  //let str1 = temp_arr.document_name;

                  temp_stakeholder_arr.push(temp_arr);
                }



                for (let y = 0; y < jsonresponse.secondary_stake_array.length; y++) {
                  let temp_arr2 = jsonresponse.secondary_stake_array[y];

                  //let str2 = temp_arr2.document_name;

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

                            


                            this.loading = false;
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


  previouspage() {
    this.router.navigate(['dashboard']);
    
  }


  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }



  onClickAddRow() {

  }

  onClickRemove() {

  }






  dialog_response: any = null;

  openDialog(arr_name, index): void {


    if (arr_name == "service_action_arr") {
      this.selected_indi = this.customer.service_action_arr[index].sel_indicators;
    }
    else if (arr_name == "workforce_action_arr") {
      this.selected_indi = this.customer.workforce_action_arr[index].sel_indicators;
    }
    else if (arr_name == "supplies_action_arr") {
      this.selected_indi = this.customer.supplies_action_arr[index].sel_indicators;
    } else if (arr_name == "health_action_arr") {
      this.selected_indi = this.customer.health_action_arr[index].sel_indicators;
    } else if (arr_name == "finance_action_arr") {
      this.selected_indi = this.customer.finance_action_arr[index].sel_indicators;
    } else if (arr_name == "policy_action_arr") {
      this.selected_indi = this.customer.policy_action_arr[index].sel_indicators;
    }

    const dialogRef = this.dialog.open(Form1bModalDialogComponent, {
      data: {
        myvar: "My variable value",
        arr: this.all_areas_indicators_arr,
        dialog_result: this.dialog_response,
        selected_indi: this.selected_indi
      },
      height: '90%',
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

      }
      else {
        //this.selected_indi = result;


        this.show_selected_indi_Onscreen(result, arr_name, index);
      }



    });
  }


  show_selected_indi_Onscreen(result: any, category_of_indicator: string, index_no: number) {

    if (category_of_indicator == "service_action_arr") {
      this.customer.service_action_arr[index_no].sel_indicators = result;
    }
    else if (category_of_indicator == "workforce_action_arr") {
      this.customer.workforce_action_arr[index_no].sel_indicators = result;
    }
    else if (category_of_indicator == "supplies_action_arr") {
      this.customer.supplies_action_arr[index_no].sel_indicators = result;
    } else if (category_of_indicator == "health_action_arr") {
      this.customer.health_action_arr[index_no].sel_indicators = result;
    } else if (category_of_indicator == "finance_action_arr") {
      this.customer.finance_action_arr[index_no].sel_indicators = result;
    } else if (category_of_indicator == "policy_action_arr") {
      this.customer.policy_action_arr[index_no].sel_indicators = result;
    }
  }


  onPrint() {
          
    var printContents = document.getElementById("toPrint").innerHTML;
    
    var popupWin = window.open('', '_blank', 'fullscreen=1');
    popupWin.document.open();
    popupWin.document.write('<html>\n\
                            <link rel="stylesheet" type="text/css" href="node_modules/bootstrap/dist/css/bootstrap.min.css" />\n\
                            <body onload="window.print()">' + printContents + '</html>');
    popupWin.document.close();
}

onPrintExcel() {

var printContents = document.getElementById("toPrint").innerHTML;

var htmls = "";
        var uri = 'data:application/vnd.ms-excel;base64,';
        var template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>'; 
        var base64 = function(s) {
            return window.btoa(unescape(encodeURIComponent(s)))
        };

        var format = function(s, c) {
            return s.replace(/{(\w+)}/g, function(m, p) {
                return c[p];
            })
        };

        htmls = printContents;

        var ctx = {
            worksheet : 'Worksheet',
            table : htmls
        }


        var link = document.createElement("a");
        link.download = "form-Plan.xls";
        link.href = uri + base64(format(template, ctx));
        link.click();
}
}
