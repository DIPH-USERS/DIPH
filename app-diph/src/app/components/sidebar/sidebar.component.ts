import { Component, OnInit } from '@angular/core';
import { PlatformLocation, Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from 'src/app/services/diph-http-client-service.service';
import * as fileSaver from 'file-saver';


@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {

  private BASE_URL = window["cfgApiBaseUrl"];
  public  load_manage_user=false;
  public offlineFile;
  private flagform1a = "0";
  private flagform1b = "0";
  private flagform2 = "0";
  private flagform3 = "0";
  private flagform4 = "0";
  private flagform5 = "0";
  private flagsecform = "0";

  constructor(location: PlatformLocation, private router: Router, private _diphHttpClientService: DiphHttpClientService, public _location: Location) { }
  
  

  navigateToID(val:string){

    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
          this.router.onSameUrlNavigation = 'reload';
          // this.router.navigate(['/dashboard']);

    this.router.navigate( ['/dashboard/general-guide' ], {fragment: val});
    
  }

 

  ngOnInit() {

    

    let userStatus = sessionStorage.getItem('userStatus');

    if("1" == userStatus){
      this.load_manage_user=true;
    }
    else{
      this.load_manage_user=false;
    }

    setTimeout(() => {
      $(document).ready(function () {
       
      });
    }, 1000);

    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid'); 

    this.offlineFile = this.BASE_URL + "/download";

    // this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
    //   .subscribe(
    //     data => {
    //       console.log(data);

    //       if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {
             
    //       } else {
            
    //         this.flagform1b = "1";
    //         this.lockCurrentCycle("form1b");
    //       }
    //     },
    //     error => {
    //       console.log(error); alert("Error= " + error);
    //     });



    // this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
    //   .subscribe(
    //     data => {
    //       console.log(data);

    //       if (data.form_2_date_of_meeting == null && data.form_2_venue == null && data.form_2_filled == null) {
          
    //       } else {
           
    //         this.flagform2 = "1";
    //         this.lockCurrentCycle("form2");
    //       }
    //     },
    //     error => {
    //       console.log(error); alert("Error= " + error);
    //     });



    // this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
    //   .subscribe(
    //     data => {

    //       console.log(data);

    //       if (data.form_3_checkdate == null && data.form_3_meeting_venue == null && data.form_3_filled_by == null) {
           
    //       } else {
           
    //         this.flagform3 = "1";
    //         this.lockCurrentCycle("form3");
    //       }
    //     },
    //     error => {
    //       console.log(error); alert("Error= " + error);
    //     });



    // this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
    //   .subscribe(
    //     data => {

    //       console.log(data);

    //       if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chariperson_of_meeting == null) {
           
    //       } else {
            
    //         this.flagform4 = "1";
    //         this.lockCurrentCycle("form4");
    //       }
    //     },
    //     error => {
    //       console.log(error); alert("Error= " + error);
    //     });


    // this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
    //   .subscribe(
    //     data => {

    //       console.log(data);

    //       if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chairperson_of_meeting == null) {
                       
    //       } else {
            
    //         this.flagform5 = "1";
    //         this.lockCurrentCycle("form5");
    //       }
    //     },
    //     error => {
    //       console.log(error); alert("Error= " + error);
    //     });


    // this._diphHttpClientService.getSavedSupplementaryForm1ADetails(district_id, cycle_id, year, user_id)
    //   .subscribe(
    //     data => {

    //       if (data.parta_document_title == null && data.parta_date_of_release == null && data.parta_goal == null) {

            
    //       } else {
    //         this.flagsecform = "1";
    //         this.lockCurrentCycle("secform");
    //       }
    //     },
    //     error => {
    //       console.log(error); alert("Error= " + error);
    //     });
    
  }

  // lockCurrentCycle(val: string) {

  //   if ((this.flagform1a == "1") && (this.flagform1b == "1") && (this.flagform2 == "1") && (this.flagform3 == "1") && (this.flagform4 == "1") && (this.flagform5 == "1") && (this.flagsecform == "1")) {
  //     alert("Side bar All forms Completed");
  //   }

  // }

  /*
  downloadOfflineFile(){
    
    let answer = confirm("Are you sure, you want to download offline version ?");

    if (answer) {
    this._diphHttpClientService.getOfflineFile()
      .subscribe(
        data => {          
          const blob = new Blob([data], {type: 'application/zip; charset=utf-8'});
          fileSaver.saveAs(blob, "DIPH-OFFLINE-PACKAGE.zip");
        },
        error => {
          console.log(error); alert("Error= " + error);
        });
      }else return false;
  }*/

}
