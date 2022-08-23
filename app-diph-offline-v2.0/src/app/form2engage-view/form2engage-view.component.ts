import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form2engage-view',
  templateUrl: './form2engage-view.component.html',
  styleUrls: ['./form2engage-view.component.css']
})
export class Form2engageViewComponent implements OnInit {


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


  constructor(private router: Router, private _diphHttpClientService: DiphHttpClientService) { }

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

      this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {

            // {
            //   "form_2_date_of_meeting": "2016-06-15",
            //   "form_2_venue": "ergerghr",
            //   "form_2_filled": "3",
            //   "primary_stake_label": "Primary Stakeholder",
            //   "primary_stakeholder_text": "aaaa",
            //   "secondary_stake_label": "Secondary Stakeholder",
            //   "secondary_stakeholder_text": "bbbbbb",
            //   "defining_primary_role_section_select": "1",
            //   "defining_primary_role_section_text": "ewfwegw",
            //   "current_effort_to_address_the_issue_section_select": "1",
            //   "current_effort_to_address_the_issue_section_text": "wegwrg",
            //   "how_to_enhance_engagement_and_efficiency_section_select": "2",
            //   "how_to_enhance_engagement_and_efficiency_section_text": "rwgwrg",
            //   "theme_lead_by_department_section_select": null,
            //   "theme_lead_by_department_section_text": null,
            //   "district": "1",
            //   "cycle": "1",
            //   "year": "2019",
            //   "userid": "1"
            // }


            this.loading = false;
            console.log(data);

            /*
            
            */
            this.jsonresponse = data;
            console.log(this.jsonresponse);

            //Get actual name of Stakeholder from its primary key
            for(let i=0;i<this.jsonresponse.definingprimaryrole_array.length;i++){
              let tempobj1 = this.jsonresponse.definingprimaryrole_array[i];

              let res_arr:any = this.jsonresponse.primary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr.length!=0){
                tempobj1.document_select_stakeholder =  res_arr[0].document_name;
              }    
              
              let res_arr2:any = this.jsonresponse.secondary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr2.length!=0){
                tempobj1.document_select_stakeholder =  res_arr2[0].document_name;
              } 
            }

            //Get actual name of Stakeholder from its primary key
            for(let i=0;i<this.jsonresponse.efforttoaddressissue_array.length;i++){
              let tempobj1 = this.jsonresponse.efforttoaddressissue_array[i];

              let res_arr:any = this.jsonresponse.primary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr.length!=0){
                tempobj1.document_select_stakeholder =  res_arr[0].document_name;
              }    
              
              let res_arr2:any = this.jsonresponse.secondary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr2.length!=0){
                tempobj1.document_select_stakeholder =  res_arr2[0].document_name;
              } 
            }

            //Get actual name of Stakeholder from its primary key
            for(let i=0;i<this.jsonresponse.enhanceefficiency_array.length;i++){
              let tempobj1 = this.jsonresponse.enhanceefficiency_array[i];

              let res_arr:any = this.jsonresponse.primary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr.length!=0){
                tempobj1.document_select_stakeholder =  res_arr[0].document_name;
              }    
              
              let res_arr2:any = this.jsonresponse.secondary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr2.length!=0){
                tempobj1.document_select_stakeholder =  res_arr2[0].document_name;
              } 
            }

            //Get actual name of Stakeholder from its primary key
            for(let i=0;i<this.jsonresponse.themeleadbydpt_array.length;i++){
              let tempobj1 = this.jsonresponse.themeleadbydpt_array[i];

              let res_arr:any = this.jsonresponse.primary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr.length!=0){
                tempobj1.document_select_stakeholder =  res_arr[0].document_name;
              }    
              
              let res_arr2:any = this.jsonresponse.secondary_stake_array.filter(t => t.document_id == tempobj1.document_select_stakeholder);
              if(res_arr2.length!=0){
                tempobj1.document_select_stakeholder =  res_arr2[0].document_name;
              } 
            }

            var date = new Date("" + this.jsonresponse.form_2_date_of_meeting);

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

            let temp1: string;
            temp1 = this.jsonresponse.form_2_filled;

            for(let j=0;j<this.verified_by_name_from_Db.length;j++){
              let tempobj = this.verified_by_name_from_Db[j];

              if(tempobj['id'] == temp1){
                this.jsonresponse.form_2_filled = tempobj['name'];
              }
            }

            if(temp1 == '15'){
              this.jsonresponse.form_2_filled = this.jsonresponse.form_2_filled_others;
            }

            // if (temp1 == "0") {
            //   this.jsonresponse.form_2_filled = "Others";
            // } else if (temp1 == "1") {
            //   this.jsonresponse.form_2_filled = "Chief Medical Officer of Health(CMOH)";
            // } else if (temp1 == "2") {
            //   this.jsonresponse.form_2_filled = "District Maternity and Child Health Officer(DMCHO)";
            // } else if (temp1 == "3") {
            //   this.jsonresponse.form_2_filled = "District Magistrate(DM)";
            // } else if (temp1 == "4") {
            //   this.jsonresponse.form_2_filled = "Office-In-Charge,Health(OC-Health)";
            // } else {
            //   this.jsonresponse.form_2_filled = "Others";
            // }

            this.jsonresponse.form_2_date_of_meeting = x + '/' + y + '/' + new_year;

            let temp2: string;
            temp2 = this.jsonresponse.defining_primary_role_section_select;

            if (temp2 == "1") { 
              this.jsonresponse.defining_primary_role_section_select = this.jsonresponse.primary_stakeholder_text;
            } else if (temp2 == "2") {
              this.jsonresponse.defining_primary_role_section_select = this.jsonresponse.secondary_stakeholder_text;
            } 


            let temp3: string;
            temp3 = this.jsonresponse.current_effort_to_address_the_issue_section_select;

            if (temp3 == "1") {
              this.jsonresponse.current_effort_to_address_the_issue_section_select = this.jsonresponse.primary_stakeholder_text;
            } else if (temp3 == "2") {
              this.jsonresponse.current_effort_to_address_the_issue_section_select = this.jsonresponse.secondary_stakeholder_text;
            } 



            let temp4: string;
            temp4 = this.jsonresponse.how_to_enhance_engagement_and_efficiency_section_select;

            if (temp4 == "1") {
              this.jsonresponse.how_to_enhance_engagement_and_efficiency_section_select = this.jsonresponse.primary_stakeholder_text;
            } else if (temp4 == "2") {
              this.jsonresponse.how_to_enhance_engagement_and_efficiency_section_select = this.jsonresponse.secondary_stakeholder_text;
            } 


            let temp5: string;
            temp5 = this.jsonresponse.theme_lead_by_department_section_select;

            if (temp5 == "1") {
              this.jsonresponse.theme_lead_by_department_section_select = this.jsonresponse.primary_stakeholder_text;
            } else if (temp5 == "2") {
              this.jsonresponse.theme_lead_by_department_section_select = this.jsonresponse.secondary_stakeholder_text;
            } 

          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }, 2000);





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

    // this.jsonresponse.date_of_verification = x + '/' + y + '/' + new_year;

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
        link.download = "form-Enggage.xls";
        link.href = uri + base64(format(template, ctx));
        link.click();
}

  previouspage() {
    this.router.navigate(['dashboard']);
    
  }

  get example() { return JSON.stringify(this.jsonresponse) };

  onClickAddRow() {

  }

  onClickRemove() {

  }

}
