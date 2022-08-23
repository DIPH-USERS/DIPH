import { Component, OnInit } from '@angular/core';
import { PlatformLocation, Location } from '@angular/common';
import { Router } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { MatDialog } from '@angular/material';
import { DeleteConfirmationDialogComponent } from '../delete-confirmation-dialog/delete-confirmation-dialog.component';
import * as fileSaver from 'file-saver';
import { District } from '../model/district';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  form1afilledalready = false;
  form1bfilledalready = false;
  showtr = false;

  form1AStatus = "new";
  form1BStatus = "new";
  formEngageStatus = "new";
  formDefineStatus = "new";
  formPlanStatus = "new";
  formFollowUpStatus = "new";

  private flagform1a = "0";
  private flagform1b = "0";
  private flagform2 = "0";
  private flagform3 = "0";
  private flagform4 = "0";
  private flagform5 = "0";
  private flagsecform = "0";

  supp1a = "0";
  priform5 = "0";
  priform4 = "0";
  priform3 = "0";
  priform2 = "0";
  priform1b = "0";
  priform1a = "0";
  
  jsonFile: File;
  district_name: any;

  constructor(location: PlatformLocation, public dialog: MatDialog, private router: Router, private _diphHttpClientService: DiphHttpClientService, public _location: Location) {
    history.pushState(null, null, location.href);
    location.onPopState(() => {
      history.pushState(null, null, location.href);
      return false;
    });
    // this.setCookie('loggedin','true',1);
  }

  // setCookie(cookiename, cookievalue, expdays) {
  //   var d = new Date();
  //   d.setTime(d.getTime()+(expdays * 24 * 60 * 60 * 1000));
  //   var expires = "expires=" + d.toUTCString();
  //   document.cookie = cookiename + "=" + cookievalue + "; " + expires;
  // }

  ngAfterContentInit() {
    // alert("All initialized");
    // alert($("#dashboard-form2engage-new").css("display"));
  }

  ngOnInit() {

    let lockcurrentcycle = sessionStorage.getItem('lockcurrentcycle');

    let user = sessionStorage.getItem('username');
    if (user == null || user.length == 0) {
      this.router.navigate(['']);
    }


    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');



    this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
          //console.log(data);

          if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {
            //alert("New form");
            $("#dashboard-formA-view").css("display", "none");
            $("#dashboard-formA-new").css("display", "");
            //dashboard-formA-view

          } else {
            this.form1afilledalready = true;
            $("#dashboard-formA-view").css("display", "");
            $("#dashboard-formA-new").css("display", "none");

            if(data.completed != null && data.completed == '0'){
              this.form1AStatus = "progress";
            }else if(data.completed != null && data.completed == '1'){
              this.form1AStatus = "completed";
            }else{
              this.form1AStatus = "new";
            }
            
            if (lockcurrentcycle == "1") {
              $("#dashboard-formA-view-span").css("visibility", "hidden");
            }

            this.flagform1a = "1";
            this.lockCurrentCycle("form1a");
          }

          this.priform1a = "1";
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });




    this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
          //console.log(data);

          if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {
            //alert("New form");
            $("#dashboard-formB-view").css("display", "none");
            $("#dashboard-formB-new").css("display", "");
            //dashboard-formA-view

          } else {
            this.form1bfilledalready = true;
            $("#dashboard-formB-view").css("display", "");
            $("#dashboard-formB-new").css("display", "none");

            $("#dashboard-formA-view .delete-form-button").css("display", "none");

            if(data.completed != null && data.completed == '0'){
              this.form1BStatus = "progress";
            }else if(data.completed != null && data.completed == '1'){
              this.form1BStatus = "completed";
            }else{
              this.form1BStatus = "new";
            }

            if (lockcurrentcycle == "1") {
              $("#dashboard-formB-view-span").css("visibility", "hidden");
            }

            this.flagform1b = "1";
            this.lockCurrentCycle("form1b");
          }

          this.priform1b = "1";
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });



    this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
          //console.log(data);

          if (data.form_2_date_of_meeting == null && data.form_2_venue == null && data.form_2_filled == null) {
            //alert("New form");
            $("#dashboard-form2engage-view").css("display", "none");
            $("#dashboard-form2engage-new").css("display", "");
            //dashboard-formA-view

          } else {
            this.form1bfilledalready = true;
            $("#dashboard-form2engage-view").css("display", "");
            $("#dashboard-form2engage-new").css("display", "none");

            $("#dashboard-formB-view .delete-form-button").css("display", "none");

            if(data.completed != null && data.completed == '0'){
              this.formEngageStatus = "progress";
            }else if(data.completed != null && data.completed == '1'){
              this.formEngageStatus = "completed";
            }else{
              this.formEngageStatus = "new";
            }

            if (lockcurrentcycle == "1") {
              $("#dashboard-form2engage-view-span").css("visibility", "hidden");
            }

            this.flagform2 = "1";
            this.lockCurrentCycle("form2");
          }

          this.priform2 = "1";
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });



    this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          //console.log(data);

          if (data.form_3_checkdate == null && data.form_3_meeting_venue == null && data.form_3_filled_by == null) {
            //alert("New form");
            $("#dashboard-form3define-view").css("display", "none");
            $("#dashboard-form3define-new").css("display", "");
            //dashboard-formA-view

          } else {
            this.form1bfilledalready = true;
            $("#dashboard-form3define-view").css("display", "");
            $("#dashboard-form3define-new").css("display", "none");

            $("#dashboard-form2engage-view .delete-form-button").css("display", "none");

            if(data.completed != null && data.completed == '0'){
              this.formDefineStatus = "progress";
            }else if(data.completed != null && data.completed == '1'){
              this.formDefineStatus = "completed";
            }else{
              this.formDefineStatus = "new";
            }

            if (lockcurrentcycle == "1") {
              $("#dashboard-form3define-view-span").css("visibility", "hidden");
            }

            this.flagform3 = "1";
            this.lockCurrentCycle("form3");
          }

          this.priform3 = "1";
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });



    this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          //console.log(data);

          if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chariperson_of_meeting == null) {
            //alert("New form");
            $("#dashboard-form4plan-view").css("display", "none");
            $("#dashboard-form4plan-new").css("display", "");
            //dashboard-formA-view

          } else {
            this.form1bfilledalready = true;
            $("#dashboard-form4plan-view").css("display", "");
            $("#dashboard-form4plan-new").css("display", "none");

            $("#dashboard-form3define-view .delete-form-button").css("display", "none");

            if(data.completed != null && data.completed == '0'){
              this.formPlanStatus = "progress";
            }else if(data.completed != null && data.completed == '1'){
              this.formPlanStatus = "completed";
            }else{
              this.formPlanStatus = "new";
            }

            if (lockcurrentcycle == "1") {
              $("#dashboard-form4plan-view-span").css("visibility", "hidden");
            }

            this.flagform4 = "1";
            this.lockCurrentCycle("form4");
          }

          this.priform4 = "1";
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });


    this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

         // console.log(data);



          if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chairperson_of_meeting == null) {
            //alert("New form");
            $("#dashboard-form5followup-view").css("display", "none");
            $("#dashboard-form5followup-new").css("display", "");
            //dashboard-formA-view


          } else {
            this.form1bfilledalready = true;
            $("#dashboard-form5followup-view").css("display", "");
            $("#dashboard-form5followup-new").css("display", "none");

            $("#dashboard-form4plan-view .delete-form-button").css("display", "none");

            if(data.completed != null && data.completed == '0'){
              this.formFollowUpStatus = "progress";
            }else if(data.completed != null && data.completed == '1'){
              this.formFollowUpStatus = "completed";
            }else{
              this.formFollowUpStatus = "new";
            }

            if (lockcurrentcycle == "1") {
              $("#dashboard-form5followup-view-span").css("visibility", "hidden");
            }

            this.flagform5 = "1";
            this.lockCurrentCycle("form5");
          }

          this.priform5 = "1";
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });


    this._diphHttpClientService.getSavedSupplementaryForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          if (data.parta_document_title == null && data.parta_date_of_release == null && data.parta_goal == null) {


          } else {
            this.flagsecform = "1";
            this.lockCurrentCycle("secform");
          }


          this.supp1a = "1";
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });


    // $(document).ready(function () {

    //   $("#dashboard-formA").removeClass("d-flex");
    //   $("#dashboard-formB").removeClass("d-flex");
    //   $("#dashboard-formA").css("display", "none");
    //   $("#dashboard-formB").css("display", "none");



    //   $("#dashboard-form-1A-1B-btn").click(function () {

    //     if ($("#dashboard-formA").hasClass('d-flex')) {
    //       $("#dashboard-formA").removeClass("d-flex");
    //       $("#dashboard-formB").removeClass("d-flex");
    //       $("#dashboard-formA").css("display", "none");
    //       $("#dashboard-formB").css("display", "none");
    //     } else {
    //       $("#dashboard-formA").css("display", "");
    //       $("#dashboard-formB").css("display", "");
    //       $("#dashboard-formA").addClass("d-flex");
    //       $("#dashboard-formB").addClass("d-flex");
    //     }

    //   });

    // });





    //If table is locked already
    if (lockcurrentcycle == "1") {


      let form1aspan = document.getElementById("dashboard-formA-view-span") as HTMLElement;
      let form1bspan = document.getElementById("dashboard-formB-view-span") as HTMLElement;
      let form2span = document.getElementById("dashboard-form2engage-view-span") as HTMLElement;
      let form3span = document.getElementById("dashboard-form3define-view-span") as HTMLElement;
      let form4span = document.getElementById("dashboard-form4plan-view-span") as HTMLElement;
      let form5span = document.getElementById("dashboard-form5followup-view-span") as HTMLElement;

      form1aspan.style.visibility = "hidden";
      form1bspan.style.visibility = "hidden";
      form2span.style.visibility = "hidden";
      form3span.style.visibility = "hidden";
      form4span.style.visibility = "hidden";
      form5span.style.visibility = "hidden";




      let unlockedbtnspan = document.getElementById("unlockedbtnspan") as HTMLElement;

      try {
        unlockedbtnspan.style.display = "none";
      } catch (error) {
      }
      let lockedbtnspan = document.getElementById("lockedbtnspan") as HTMLElement;
      try {
        lockedbtnspan.style.display = "block";
      } catch (error) {
      }

      //Check for Admin and district user
      if (sessionStorage.getItem('userStatus') == "1") {
        let lockbtn = document.getElementById("lockbtn") as HTMLElement;
        // try {
        //   lockbtn.removeAttribute("disabled");
        // } catch (error) {
        // }
        // try {
        //   lockbtn.classList.remove("btn-secondary");
        // } catch (error) {
        // }
        // try {
        //   lockbtn.classList.add("btn-danger");
        // } catch (error) {
        // }
      }



    }
    //If table is not locked
    else {
      let unlockedbtnspan = document.getElementById("unlockedbtnspan") as HTMLElement;

      try {
        unlockedbtnspan.style.display = "block";
      } catch (error) {
      }
      let lockedbtnspan = document.getElementById("lockedbtnspan") as HTMLElement;
      try {
        lockedbtnspan.style.display = "none";
      } catch (error) {
      }
      //Check for Admin and district user
      if (sessionStorage.getItem('userStatus') == "1") {
        let unlockbtn = document.getElementById("unlockbtn") as HTMLElement;
        // try {
        //   unlockbtn.removeAttribute("disabled");
        // } catch (error) {
        // }
        // try {
        //   unlockbtn.classList.remove("btn-secondary");
        // } catch (error) {
        // }
        // try {
        //   unlockbtn.classList.add("btn-danger");
        // } catch (error) {
        // }
      }

    }




  }




  ngAfterViewInit() {
    setTimeout(function () {
      try {
        $("#coverScreen").hide();
      } catch (error) {

      }
    }, 2000);
    // alert("Dashboard loaded");
  }



  lockCurrentCycle(val: string) {

    if ((this.flagform1a == "1") && (this.flagform1b == "1") && (this.flagform2 == "1") && (this.flagform3 == "1") && (this.flagform4 == "1") && (this.flagform5 == "1") && (this.flagsecform == "1")) {
      // alert("Dashboard All forms Completed");
      let unlockbtn = document.getElementById("unlockbtn") as HTMLElement;
      // try {
      //   unlockbtn.removeAttribute("disabled");
      // } catch (error) {
      // }
      // try {
      //   unlockbtn.classList.remove("btn-secondary");
      // } catch (error) {
      // }
      // try {
      //   unlockbtn.classList.add("btn-danger");
      // } catch (error) {
      // }
      let lockcurrentcycle = sessionStorage.getItem('lockcurrentcycle');

      if (lockcurrentcycle == "1") {

        let form1aspan = document.getElementById("dashboard-formA-view-span") as HTMLElement;
        let form1bspan = document.getElementById("dashboard-formB-view-span") as HTMLElement;
        let form2span = document.getElementById("dashboard-form2engage-view-span") as HTMLElement;
        let form3span = document.getElementById("dashboard-form3define-view-span") as HTMLElement;
        let form4span = document.getElementById("dashboard-form4plan-view-span") as HTMLElement;
        let form5span = document.getElementById("dashboard-form5followup-view-span") as HTMLElement;

        form1aspan.style.visibility = "hidden";
        form1bspan.style.visibility = "hidden";
        form2span.style.visibility = "hidden";
        form3span.style.visibility = "hidden";
        form4span.style.visibility = "hidden";
        form5span.style.visibility = "hidden";
      }



    }
    // else
    // {
    //   alert("this.flagform1a="+this.flagform1a+"\n"+
    //   "this.flagform1b="+this.flagform1b+"\n"+
    //   "this.flagform2="+this.flagform2+"\n"+
    //   "this.flagform3="+this.flagform3+"\n"+
    //   "this.flagform4="+this.flagform4+"\n"+
    //   "this.flagform5="+this.flagform5+"\n"+
    //   "this.flagsecform="+this.flagsecform);
    // }



  }


  onClickdeleteform5FollowupDetails() {

    let answer = confirm("Are you sure, you want to delete?");

    if (answer) {
      alert("Deleting Form5Followup Details");

      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.deleteForm5FollowupDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            //console.log(data);
            alert("User details data deleted successfully!!!");

            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = 'reload';
            this.router.navigate(['/dashboard']);


          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }


  }

  onClickdeleteform4PlanDetails(): void {

    let answer = confirm("Are you sure, you want to delete?");

    if (answer) {
      alert("Deleting Form4Plan Details");

      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.deleteForm4PlanDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            //console.log(data);
            alert("User details data deleted successfully!!!");

            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = 'reload';
            this.router.navigate(['/dashboard']);


          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }


  }

  onClickdeleteform1BDetails(): void {

    let answer = confirm("Are you sure, you want to delete?");

    if (answer) {
      alert("Deleting form1B Details");

      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.deleteForm1BDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            //console.log(data);
            alert("User details data deleted successfully!!!");


            // this.router.navigateByUrl("/dashboard", { skipLocationChange: true }).then(() => {
            //   console.log(decodeURI(this._location.path()));
            //   this.router.navigate([decodeURI(this._location.path())]);
            // });

            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = 'reload';
            this.router.navigate(['/dashboard']);


          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }



  }

  onClickdeleteform2EngageDetails(): void {

    let answer = confirm("Are you sure, you want to delete?");

    if (answer) {
      alert("Deleting Step 2 Details");

      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.deleteForm2EngageDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
           // console.log(data);
            alert("User details data deleted successfully!!!");

            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = 'reload';
            this.router.navigate(['/dashboard']);

          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }


  }


  onClickdeleteform3DefineDetails() {

    let answer = confirm("Are you sure, you want to delete?");

    if (answer) {
      alert("Deleting form Details");

      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.deleteForm3DefineDetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
           // console.log(data);
            alert("User details data deleted successfully!!!");
            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = 'reload';
            this.router.navigate(['/dashboard']);
          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }


  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DeleteConfirmationDialogComponent, {
      data: {
        myvar: "My variable value"
      },
      height: '60%',
      width: '50%'
    });

    dialogRef.afterClosed().subscribe(result => {

    });
  }


  onClickdeleteform1ADetails(): void {

    let answer = confirm("Are you sure, you want to delete?");

    if (answer) {
      alert("Deleting form Details");

      let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.deleteForm1ADetails(district_id, cycle_id, year, user_id)
        .subscribe(
          data => {
            //console.log(data);
            alert("User details data deleted successfully!!!");


            // this.router.navigateByUrl("/dashboard", { skipLocationChange: true }).then(() => {
            //   console.log(decodeURI(this._location.path()));
            //   this.router.navigate([decodeURI(this._location.path())]);
            // });

            this.router.routeReuseStrategy.shouldReuseRoute = () => false;
            this.router.onSameUrlNavigation = 'reload';
            this.router.navigate(['/dashboard']);


          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }


  }

  uploadFile(file: File[]){
    this.jsonFile = file[0];
  }

  validateAndUpload(){

    var fileName = (this.jsonFile).name;
    
    var fileExtension = fileName.slice((fileName.lastIndexOf(".") - 1 >>> 0) + 2);

    if(fileExtension != "json"){
      alert("File extension is not valid");
      return;
    }   
    
    var fileToken = (fileName.substring(0,fileName.lastIndexOf("."))).split('_'); 
    var fileSource = fileToken[0];

    if(fileSource != "OnlineFile"){

      alert("File downladed from offline application are valid here.");
      return;
    }

    var district_id_in_filename = fileToken[4];
    var cycle_id_in_filename = fileToken[2];
    var year_in_filename = fileToken[3];
    
    let district_id: string, cycle_id: string, year: string, user_id:string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {  

          if (data ==null || (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null)) {            
            if(district_id_in_filename != district_id){
              alert("File does not match with the District");
              return;
            }
        
            if(cycle_id_in_filename != cycle_id){
              alert("File does not match with the cycle");
              return;
            }
        
            if(year_in_filename != year){
              alert("File does not match with the year");
              return;
            }
        
            var reader = new FileReader();      
            // reader.readAsDataURL(this.jsonFile); 
            reader.readAsText(this.jsonFile); 
            reader.onload = (_event) => { 
        
              let uploadData:any = reader.result;
                    
              this._diphHttpClientService.uploadOfflineFormData(JSON.parse(uploadData), district_id, cycle_id, year, user_id)
                .subscribe(
                  data => {
                        alert(data);
                        window.location.reload();
                        },
                  error => {
                    console.log(error); alert("Error= " + error);
                  });
                  
            }
          }else{
            alert("Form(s) are alredy filled by authentic user.");
            return;           
          }           
        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });
    
  }

  downloadOfflineFile(){

    let answer = confirm("You want to download the form(s) data file?");

    if (answer) {

    let district_id: string, cycle_id: string, year: string, user_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');
      this._diphHttpClientService.getDistrictDetailsFromId(district_id).subscribe(districtData => {
        this.district_name = districtData.district_name;
    this._diphHttpClientService._download_district_forms_data_function(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
          if(data != null){         
          const blob = new Blob([JSON.stringify(data)], {type: 'application/json; charset=utf-8'});
          //Tarmaber_1_2020_24          
          var filename = "OfflineFile_" + this.district_name + "_" + cycle_id + "_" + year + "_" +district_id + ".json";
          fileSaver.saveAs(blob, filename);
          }else{
            alert("File can not be downloaded as all forms are empty.");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });});
      }
  }

}
