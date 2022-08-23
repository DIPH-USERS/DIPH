import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-supplementary-form1a-view',
  templateUrl: './supplementary-form1a-view.component.html',
  styleUrls: ['./supplementary-form1a-view.component.css']
})
export class SupplementaryForm1aViewComponent implements OnInit {

  constructor(private router: Router, private _diphHttpClientService: DiphHttpClientService) { }


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

  action_required: any = {};


  ngOnInit() {

    setTimeout(() => {
      console.log("Hello from setTimeout");
      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');



      this._diphHttpClientService.getSavedSupplementaryForm1ADetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {

            console.log(data);

            this.jsonresponse = data;
            console.log("this.jsonresponse");
            console.log(this.jsonresponse);

            this.loading = false;
            /*
            parta_document_title:new FormControl(),
      parta_date_of_release:new FormControl(),
      parta_primary_theme:[' '],
      parta_goal:new FormControl(),
      parta_part:['PART-A'],
      parta_da_action_point:new FormControl(),
      partb_document_title:new FormControl(),
      partb_date_of_release:new FormControl(),
      partb_primary_theme:[' '],
      partb_goal:new FormControl(),
      partb_part:['PART-B'],
      partb_da_action_point:new FormControl(),
            */

            let date3 = new Date(this.jsonresponse.parta_date_of_release);

            var new_date3 = date3.getDate();
            var new_month3 = (date3.getMonth() + 1);
            var new_year3 = date3.getFullYear();

            this.jsonresponse.parta_date_of_release = new_month3 +"/" +new_date3+"/"+new_year3;


            let date4 = new Date(this.jsonresponse.partb_date_of_release);

            var new_date4 = date4.getDate();
            var new_month4 = (date4.getMonth() + 1);
            var new_year4 = date4.getFullYear();

            this.jsonresponse.partb_date_of_release = new_month4 +"/" +new_date4+"/"+new_year4;

          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }, 3000);


  }
  //ngOnInit Ends


  previouspage() {
    this.router.navigate(['dashboard']);

  }


  onSubmit() {
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
        link.download = "Supplementry-Form.xls";
        link.href = uri + base64(format(template, ctx));
        link.click();
}

  get example() { return JSON.stringify(this.jsonresponse) };

  onClickAddRow() {

  }

  onClickRemove() {

  }

}
