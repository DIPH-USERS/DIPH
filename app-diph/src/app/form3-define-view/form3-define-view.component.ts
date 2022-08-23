import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form3-define-view',
  templateUrl: './form3-define-view.component.html',
  styleUrls: ['./form3-define-view.component.css']
})
export class Form3DefineViewComponent implements OnInit {

  public jsonresponse: any;
  loading = true;
  stakeholder_arr = [];
  primary_stakeholder_arr = [
    { id: '1', name: 'order 1' },
    { id: '2', name: 'order 2' },
    { id: '3', name: 'order 3' },
    { id: '4', name: 'order 4' }
  ];
  secondary_stakeholder_arr = [];
  public verified_by_name_from_Db = [];

  public form1bresponse: any;


  constructor(private router: Router,private _diphHttpClientService: DiphHttpClientService) { }

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


      /*

      {
  "form_3_checkdate": "2016-06-15",
  "form_3_meeting_venue": "wefweg",
  "form_3_filled_by": "rgrh",
  "form3_chair_person": "2",
  "theme_id": "143",
  "service_action_part_of_engagement": "Service delivery",
  "service_description_p_f_h_s_p": "rgrwh",
  "service_possible_s_t_i": "rhgrh",
  "service_action_part": "Service delivery",
  "service_form3_sl_no": null,
  "service_action_required": "rgrwg",
  "workforce_service_action_part_of_engagement": "Workforce",
  "workforce_service_description_p_f_h_s_p": "rgrg",
  "workforce_service_possible_s_t_i": "rwgrwg",
  "workforce_action_part": "Workforce",
  "workforce_form3_sl_no": null,
  "workforce_action_required": "rwgrwg",
  "supplies_service_action_part_of_engagement": "Supplies & technology",
  "supplies_service_description_p_f_h_s_p": "wrgr",
  "supplies_service_possible_s_t_i": "rwgrwg",
  "supplies_action_part": "Supplies & technology",
  "supplies_form3_sl_no": null,
  "supplies_action_required": "rwgrwg",
  "health_service_action_part_of_engagement": "Health information",
  "health_service_description_p_f_h_s_p": "wrgrwg",
  "health_service_possible_s_t_i": "rwgrwg",
  "health_action_part": "Health information",
  "health_form3_sl_no": null,
  "health_action_required": "wgrwg",
  "finance_service_action_part_of_engagement": "Finance",
  "finance_service_description_p_f_h_s_p": "ewgrwg",
  "finance_service_possible_s_t_i": "wgwg",
  "finance_action_part": "Finance",
  "finance_form3_sl_no": null,
  "finance_action_required": "wgwgr",
  "policy_service_action_part_of_engagement": "Policy/governance",
  "policy_service_description_p_f_h_s_p": "wegwrg",
  "policy_service_possible_s_t_i": "ewgwrg",
  "policy_action_part": "Policy/governance",
  "policy_form3_sl_no": null,
  "policy_action_required": "wgwrg",
  "district": "1",
  "cycle": "1",
  "year": "2019",
  "userid": "1"
}

      */

      this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {

            this.form1bresponse = data

            

          },
          error => {
            console.log(error); alert("Error= " + error);
          });

      this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {

            this.jsonresponse = data;
            console.log(this.jsonresponse);

            let tempdate = new Date("" + this.jsonresponse.form_3_checkdate);
            let finalday:any = tempdate.getDate();
            let finalmonth:any = (tempdate.getMonth() + 1);
            let finalyear = tempdate.getFullYear();



            if (finalday < 10) {
              finalday = "0" + finalday;
            }
            else {
              finalday = finalday;
            }

            if (finalmonth < 10) {
              finalmonth = "0" + finalmonth;
            }
            else {
              finalmonth = finalmonth;
            }

            let finalfulldate =  finalmonth + "/" + finalday + "/" + finalyear;
            this.jsonresponse.form_3_checkdate = finalfulldate;

            this.loading = false;
            console.log(data);


            let temp1: string;
            temp1 = this.jsonresponse.form3_chair_person;

            for(let j=0;j<this.verified_by_name_from_Db.length;j++){
              let tempobj = this.verified_by_name_from_Db[j];

              if(tempobj['id'] == temp1){
                this.jsonresponse.form3_chair_person = tempobj['name'];
              }
            }

            if(temp1 == '15'){
              this.jsonresponse.form3_chair_person = this.jsonresponse.form3_chair_person_others;
            }

            // if (temp1 == "0") {
            //   this.jsonresponse.form3_chair_person = "Others";
            // } else if (temp1 == "1") {
            //   this.jsonresponse.form3_chair_person = "Chief Medical Officer of Health(CMOH)";
            // } else if (temp1 == "2") {
            //   this.jsonresponse.form3_chair_person = "District Maternity and Child Health Officer(DMCHO)";
            // } else if (temp1 == "3") {
            //   this.jsonresponse.form3_chair_person = "District Magistrate(DM)";
            // } else if (temp1 == "4") {
            //   this.jsonresponse.form3_chair_person = "Office-In-Charge,Health(OC-Health)";
            // } else {
            //   this.jsonresponse.form3_chair_person = "Others";
            // }

            /*
            
            */


            //var date = new Date("" + this.jsonresponse.form_2_date_of_meeting);



          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }, 3000);





    // var date = new Date(""+this.jsonresponse.date_of_verification);

    // var new_date = date.getDate();
    // var new_month = (date.getMonth() + 1);
    // var new_year = date.getFullYear();

    // let x: any;

    // if (new_date < 10) {
    //   x = "0" + new_date;
    // }
    // else {
    //   x = new_date;
    // }

    // let y: any;

    // if (new_month < 10) {
    //   y = "0" + new_month;
    // }
    // else {
    //   y = new_month;
    // }

    // let temp1: string;
    // temp1 = this.jsonresponse.verified_by_name_actual_name;

    // if (temp1 == "0") {
    //   this.jsonresponse.verified_by_name_actual_name = "Others";
    // } else if (temp1 == "1") {
    //   this.jsonresponse.verified_by_name_actual_name = "Chief Medical Officer of Health(CMOH)";
    // } else if (temp1 == "2") {
    //   this.jsonresponse.verified_by_name_actual_name = "District Maternity and Child Health Officer(DMCHO)";
    // } else if (temp1 == "3") {
    //   this.jsonresponse.verified_by_name_actual_name = "District Magistrate(DM)";
    // } else if (temp1 == "4") {
    //   this.jsonresponse.verified_by_name_actual_name = "Office-In-Charge,Health(OC-Health)";
    // } else {
    //   this.jsonresponse.verified_by_name_actual_name = "Others";
    // }

    //this.jsonresponse.date_of_verification = x + '/' + y + '/' + new_year;

  }
  //ngOnInit Ends

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
        link.download = "form-Define.xls";
        link.href = uri + base64(format(template, ctx));
        link.click();
}

  get example() { return JSON.stringify(this.jsonresponse) };

  previouspage() {
    this.router.navigate(['dashboard']);
    
  }

  onClickAddRow() {

  }

  onClickRemove() {

  }

}
