import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { Observable } from 'rxjs';
import { ReactiveFormsModule } from '@angular/forms';
import { analyzeAndValidateNgModules } from '@angular/compiler';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-form1-a-view',
  templateUrl: './form1-a-view.component.html',
  styleUrls: ['./form1-a-view.component.css']
})


/*
{
  "date_of_verification": "2019-09-09",
  "filled_by": "dsvfdsgv",
  "verified_by_name": "3",
  "large_scale_district_array": [
    {
      "document_val": "fdvfdb",
      "document_availability": "Yes",
      "document_source": "fvfsv"
    }
  ],
  "private_sec_array": [
    {
      "document_val": "f fd",
      "document_availability": "Yes",
      "document_source": "f f "
    }
  ],
  "non_health_dept_array": [
    {
      "document_val": "fd fd ",
      "document_availability": "No",
      "document_source": "fd fd"
    }
  ],
  "health_dept_array": [
    {
      "document_val": "fd fd ",
      "document_availability": "No",
      "document_source": "fb fd "
    }
  ],
  "district_policy_array": [
    {
      "document_val": "fd fd ",
      "document_availability": "No",
      "document_source": "fd f"
    }
  ],
  "state_policy_array": [
    {
      "document_val": "v bvc ",
      "document_availability": "Yes",
      "document_source": "v v "
    }
  ]
}
*/


export class Form1AViewComponent implements OnInit {


  public games: any;
  public abhi: any;
  public jsonresponse: any;

  users: any; // <-- Do not use $ at the end of a variable unless it is an observable
  loading = true;


  constructor(private router: Router,private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {

    this.abhi = [

      {
        "id": "1",
        "name": "DOTA 2",
        "genre": "Strategy"
      }

    ];

    this.games = [

      {
        "id": "1",
        "name": "DOTA 2",
        "genre": "Strategy"
      },
      {
        "id": "2",
        "name": "GTA 5",
        "genre": "RPG"
      }

    ];

    setTimeout(() => {
     
      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            this.users = [{ name: "Abhishek Kumar" }];
            this.loading = false;
           // console.log(data);
            this.jsonresponse = data;
           // console.log(this.jsonresponse);

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


            //alert(" this.jsonresponse.verified_by_name = "+ this.jsonresponse.verified_by_name);
            //alert(" this.jsonresponse.verified_by_name_actual_name = "+ this.jsonresponse.verified_by_name_actual_name);
            
            this.jsonresponse.date_of_verification = y + '/' + x + '/' + new_year;


            this._diphHttpClientService.getVerified_by_names()
              .subscribe(
                data => {

                 
                  
                  if (data['status'] == "1") {
                    let verified_by_name_arr: [] = data['verified_by_name'];

                    let temp1: string;
                    temp1 = this.jsonresponse.verified_by_name;
                    for (let j = 0; j < verified_by_name_arr.length; j++) {
                      let tempobj = verified_by_name_arr[j];

                      if (tempobj['id'] == temp1) {
                        this.jsonresponse.verified_by_name = tempobj['name'];
                      }
                    }

                    if(temp1 == '15'){
                      this.jsonresponse.verified_by_name = this.jsonresponse.verified_by_name_actual_name;
                    }

                  }
                  else {
                    alert("Error in retrieving \n'Chairperson names from Server'");
                  }

                  

                },
                error => {
                  console.log(error); alert("Error= " + error);
                });



          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }, 3000);

    this.jsonresponse = {
      "date_of_verification": "",
      "filled_by": "",
      "verified_by_name": "",
      "verified_by_name_actual_name": "",
      "large_scale_district_array": [
        {
          "document_val": "",
          "document_availability": "",
          "document_source": ""
        }
      ],
      "private_sec_array": [
        {
          "document_val": "",
          "document_availability": "",
          "document_source": ""
        }
      ],
      "non_health_dept_array": [
        {
          "document_val": "",
          "document_availability": "",
          "document_source": ""
        }
      ],
      "health_dept_array": [
        {
          "document_val": "",
          "document_availability": "",
          "document_source": ""
        }
      ],
      "district_policy_array": [
        {
          "document_val": "",
          "document_availability": "",
          "document_source": ""
        }
      ],
      "state_policy_array": [
        {
          "document_val": "",
          "document_availability": "",
          "document_source": ""
        }
      ]
    };




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
            link.download = "form1A.xls";
            link.href = uri + base64(format(template, ctx));
            link.click();
}

  
  previouspage() {
    this.router.navigate(['dashboard']);
    
  }

  get example() { return JSON.stringify(this.jsonresponse) };

}








