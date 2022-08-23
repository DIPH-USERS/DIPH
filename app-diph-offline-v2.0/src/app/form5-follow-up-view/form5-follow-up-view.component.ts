import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation, DatePipe } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form5-follow-up-view',
  templateUrl: './form5-follow-up-view.component.html',
  styleUrls: ['./form5-follow-up-view.component.css']
})
export class Form5FollowUpViewComponent implements OnInit {

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
  public verified_by_name_from_Db = [];

  date = new FormControl(new Date());
  date_2 = new FormControl(new Date());
  minDate = new Date("12,01,2019");
  dynamicForm: FormGroup;


  form4response: any;

  status_arr = [{ status: "Completed", status_val: "Completed" },
  { status: "On target", status_val: "Ongoing- On Target" },
  { status: "Not on target", status_val: "Ongoing- Not on Target" },
  { status: "Not Started", status_val: "Not Started" },
  { status: "", status_val: "Please Select" },];

  constructor(location: PlatformLocation, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
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

    this.getform1aDetails(district_id, cycle_id, year, user_id);

    this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          this.customer = data;
          this.date = new FormControl(new Date(this.customer.date_of_meeting));


          this.customer.theme_leader = data.theme_leader;


          let temp1: string;
          temp1 = this.customer.chairperson_of_meeting;

          for (let j = 0; j < this.verified_by_name_from_Db.length; j++) {
            let tempobj = this.verified_by_name_from_Db[j];

            if (tempobj['id'] == temp1) {
              this.customer.chairperson_of_meeting = tempobj['name'];
            }
          }

          if (temp1 == '15') {
            this.customer.chairperson_of_meeting = this.customer.chairperson_of_meeting_others;
          }


          for(let pos=0;pos<this.customer.meeting_arr.length;pos++){
            let tempobj = this.customer.meeting_arr[pos];

            let tempdate = new Date("" + tempobj.meeting_date); 

            let finalday = tempdate.getDate();
            let finalmonth = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear();

            tempobj.meeting_date =  finalmonth + "-" + finalday + "-" + finalyear;
          }

          console.log(JSON.stringify(this.customer.service_action_arr));

          for(let pos=0;pos<this.customer.service_action_arr.length;pos++){
            let tempobj = this.customer.service_action_arr[pos];

            { 
            let tempdate = new Date("" + tempobj.revised_timeline); 

            let finalday = tempdate.getDate();
            let finalmonth = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear();
            tempobj.revised_timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }
            
            { 
              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
              }
          }


          for(let pos=0;pos<this.customer.workforce_action_arr.length;pos++){
            let tempobj = this.customer.workforce_action_arr[pos];

            {
            let tempdate = new Date("" + tempobj.revised_timeline); 

            let finalday = tempdate.getDate();
            let finalmonth = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear();

            tempobj.revised_timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }

            {
              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
              }
          }


          for(let pos=0;pos<this.customer.supplies_action_arr.length;pos++){
            let tempobj = this.customer.supplies_action_arr[pos];

            {
            let tempdate = new Date("" + tempobj.revised_timeline); 

            let finalday = tempdate.getDate();
            let finalmonth = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear();

            tempobj.revised_timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }
            {
              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
              }
          }

          for(let pos=0;pos<this.customer.health_action_arr.length;pos++){
            let tempobj = this.customer.health_action_arr[pos];
            {
            let tempdate = new Date("" + tempobj.revised_timeline); 

            let finalday = tempdate.getDate();
            let finalmonth = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear();

            tempobj.revised_timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }
            {
              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
              }
          }

          for(let pos=0;pos<this.customer.finance_action_arr.length;pos++){
            let tempobj = this.customer.finance_action_arr[pos];
            {
            let tempdate = new Date("" + tempobj.revised_timeline); 

            let finalday = tempdate.getDate();
            let finalmonth = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear();

            tempobj.revised_timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }
            {
              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }
          }

          for(let pos=0;pos<this.customer.policy_action_arr.length;pos++){
            let tempobj = this.customer.policy_action_arr[pos];
            {
            let tempdate = new Date("" + tempobj.revised_timeline); 

            let finalday = tempdate.getDate();
            let finalmonth = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear(); 

            tempobj.revised_timeline =  finalmonth + "-" + finalday + "-" + finalyear;
            }
            {
              let tempdate = new Date("" + tempobj.timeline); 
  
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear(); 
  
              tempobj.timeline =  finalmonth + "-" + finalday + "-" + finalyear;
              }
          }


          this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
            .subscribe(
              data => {

                let iphs_theme_name = data.iphs_theme_name;
                this.customer.theme_id = iphs_theme_name;

                for (let index in data.total_coverage_indi) {

                  let obj2 = index;


                  for (let j = 0; j < this.form1a_all_doc_arr.length; j++) {
                    let tempobj = this.form1a_all_doc_arr[j];
                    if (tempobj.doc_db_doc_id == data.total_coverage_indi[index].source) {
                      this.customer.total_coverage_indi[index].new_source = tempobj.document_val;

                    }
                  }
                }



                /***********************************

                this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
                  .subscribe(
                    data => {

                      //alert(data.form_3_filled_by);
                       
                     // this.customer.theme_leader = data.form_3_filled_by;
                      //console.log("Form3 Define");
                      //console.log(JSON.stringify(data,null,1000));

                    },
                    error => {
                      console.log(error); alert("Error in Form 3= " + error);
                    });

                ************************************/
              },
              error => {
                console.log(error); alert("Error= " + error);
              });

              //customer.total_coverage_indi_timezero_date

            if(this.customer.total_coverage_indi_timezero_date != null && this.customer.total_coverage_indi_timezero_date != '')
             {
              let tempdate = new Date("" + this.customer.total_coverage_indi_timezero_date); 
              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();        
              this.customer.total_coverage_indi_timezero_date =  finalmonth + "-" + finalday + "-" + finalyear;              
             }else{
              this.customer.total_coverage_indi_timezero_date = "";
             }


            if(this.customer.total_coverage_indi_timeone_date != null && this.customer.total_coverage_indi_timeone_date != '')
             {
              let tempdate = new Date("" + this.customer.total_coverage_indi_timeone_date); 

              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
        
              this.customer.total_coverage_indi_timeone_date =  finalmonth + "-" + finalday + "-" + finalyear;
            }else{
              this.customer.total_coverage_indi_timeone_date = "";
             }

            if(this.customer.total_coverage_indi_timetwo_date != null && this.customer.total_coverage_indi_timetwo_date != '')
             {
              let tempdate = new Date("" + this.customer.total_coverage_indi_timetwo_date); 

              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
        
              this.customer.total_coverage_indi_timetwo_date =  finalmonth + "-" + finalday + "-" + finalyear;
            }else{
              this.customer.total_coverage_indi_timetwo_date = "";
             }

             if(this.customer.total_coverage_indi_timethree_date != null && this.customer.total_coverage_indi_timethree_date != '')
             {
              let tempdate = new Date("" + this.customer.total_coverage_indi_timethree_date); 

              let finalday = tempdate.getDate();
              let finalmonth = (tempdate.getMonth() + 1);
              let finalyear = tempdate.getFullYear();
        
              this.customer.total_coverage_indi_timethree_date =  finalmonth + "-" + finalday + "-" + finalyear;
            }else{
              this.customer.total_coverage_indi_timethree_date = "";
             }



          this.finallyloaded = true;
          
        },
        error => {
          console.log(error); alert("Error= " + error);
        });

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
  onclickAddmeetingRow(i) {
    let temp_obj = {
      "meeting_no": "" + (i + 1),
      "meeting_date": "",
      "stakeholder_meeting": "",
      "no_of_participants": ""
    };
    this.customer.meeting_arr.push(temp_obj);
  }

  onclickRemovemeetingRow(i) {
    this.customer.meeting_arr.splice(i, 1);
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
        link.download = "form-FollowUp.xls";
        link.href = uri + base64(format(template, ctx));
        link.click();
}

}
