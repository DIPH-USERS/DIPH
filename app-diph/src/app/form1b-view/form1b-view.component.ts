import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { ReactiveFormsModule } from '@angular/forms';
import { analyzeAndValidateNgModules } from '@angular/compiler';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form1b-view',
  templateUrl: './form1b-view.component.html',
  styleUrls: ['./form1b-view.component.css']
})
export class Form1bViewComponent implements OnInit {


  public games: any;
  public abhi: any;
  public jsonresponse: any={};
  form1a_all_doc_arr: any = [];
  public verified_by_name_from_Db = [];

  users: any; // <-- Do not use $ at the end of a variable unless it is an observable
  loading = true;


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
            console.log(JSON.stringify(this.form1a_all_doc_arr,null,4));  
          },
          error => {
            console.log(error); alert("Error= " + error);
          });



      this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            this.users = [{ name: "Abhishek Kumar" }];
            
            this.jsonresponse = data;
            console.log("data = "+data);
            console.log(this.jsonresponse);

            var date = new Date("" + this.jsonresponse.date_of_verification);

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
            temp1 = this.jsonresponse.verified_by_name;

            
            //alert("this.jsonresponse.verified_by_name = "+this.jsonresponse.verified_by_name);

            for(let j=0;j<this.verified_by_name_from_Db.length;j++){
              let tempobj = this.verified_by_name_from_Db[j];

              if(tempobj['id'] == temp1){
                this.jsonresponse.verified_by_name = tempobj['name'];
              }
            }

            if(temp1 == "15"){ 
              this.jsonresponse.verified_by_name = this.jsonresponse.verified_by_other_actual_name;
            }

            // if (temp1 == "0") {
            //   this.jsonresponse.verified_by_name = "Others";
            // } else if (temp1 == "1") {
            //   this.jsonresponse.verified_by_name = "Chief Medical Officer of Health(CMOH)";
            // } else if (temp1 == "2") {
            //   this.jsonresponse.verified_by_name = "District Maternity and Child Health Officer(DMCHO)";
            // } else if (temp1 == "3") {
            //   this.jsonresponse.verified_by_name = "District Magistrate(DM)";
            // } else if (temp1 == "4") {
            //   this.jsonresponse.verified_by_name = "Office-In-Charge,Health(OC-Health)";
            // } else {
            //   this.jsonresponse.verified_by_name = "Others";
            // }

            this.jsonresponse.date_of_verification = x + '/' + y + '/' + new_year;

            // let temp2: string;
            // temp2 = this.jsonresponse.total_area_demogra_id;

            // if (temp2 == "0") {
            //   this.jsonresponse.total_area_demogra_id = "Others";
            // } else if (temp2 == "1") {
            //   this.jsonresponse.total_area_demogra_id = "Chief Medical Officer of Health(CMOH)";
            // } else if (temp2 == "2") {
            //   this.jsonresponse.total_area_demogra_id = "District Maternity and Child Health Officer(DMCHO)";
            // } else if (temp2 == "3") {
            //   this.jsonresponse.total_area_demogra_id = "District Magistrate(DM)";
            // } else if (temp2 == "4") {
            //   this.jsonresponse.total_area_demogra_id = "Office-In-Charge,Health(OC-Health)";
            // } else {
            //   this.jsonresponse.total_area_demogra_id = "Others";
            // }
            for(let i=0;i<this.form1a_all_doc_arr.length;i++){

              let tempobj= this.form1a_all_doc_arr[i];

              for(let j=0;j<data.total_coverage_indi.length;j++){
                let obj2 = data.total_coverage_indi[j];

                //console.log(JSON.stringify(obj2,null,4));

                if(tempobj.doc_db_doc_id == obj2.source){
                  this.jsonresponse.total_coverage_indi[j].source=tempobj.document_val;
                }
              }

              if(tempobj.doc_db_doc_id == data.total_area_demogra_id){
                this.jsonresponse.total_area_demogra_id  =  tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id ==  data.total_pop_demogra_id){
                this.jsonresponse.total_pop_demogra_id = tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id ==  data.num_women_in_reproductive_age_15_49_y_source){
                this.jsonresponse.num_women_in_reproductive_age_15_49_y_source  = tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id == data.num_child_under_5_y_demogra_id){
                this.jsonresponse.num_child_under_5_y_demogra_id =  tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id == data.rural_pop_demogra_id){
                this.jsonresponse.rural_pop_demogra_id =  tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id == data.sc_pop_demogra_id){
                this.jsonresponse.sc_pop_demogra_id =  tempobj.document_val;
              }
              
              if(tempobj.doc_db_doc_id == data.st_pop_demogra_id){
                this.jsonresponse.st_pop_demogra_id =  tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id == data.pop_density_demogra_id){
                this.jsonresponse.pop_density_demogra_id =  tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id == data.total_literacy_demogra_id){
                this.jsonresponse.total_literacy_demogra_id =  tempobj.document_val;
              }

              if(tempobj.doc_db_doc_id == data.fem_literacy_demogra_id){
                this.jsonresponse.fem_literacy_demogra_id =  tempobj.document_val;
              }
            }


          // for(){

          // }






          this.loading = false;
            
          },
          error => {
            console.log(error); alert("Error= " + error);
          });

          
    }, 3000);


    
    $(document).ready(function () {
      $(window).on('load', function () {
        $("#coverScreen").hide();
        });

        // $("#ucNoteGrid_grdViewNotes_ctl01_btnPrint").click(function () {
        //   $("#coverScreen").show();
        //   });

    });    

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
        link.download = "form1B.xls";
        link.href = uri + base64(format(template, ctx));
        link.click();
}


  previouspage() {
    this.router.navigate(['dashboard']);
    
  }

  get example() { return JSON.stringify(this.jsonresponse) };




}
