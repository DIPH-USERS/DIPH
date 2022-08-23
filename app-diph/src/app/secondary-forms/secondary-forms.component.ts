import { Component, OnInit } from '@angular/core';
import { PlatformLocation, Location } from '@angular/common';
import { Router } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-secondary-forms',
  templateUrl: './secondary-forms.component.html',
  styleUrls: ['./secondary-forms.component.css']
})
export class SecondaryFormsComponent implements OnInit {



  form1afilledalready = false;
  form1bfilledalready = false;
  showtr = false;
  private flagform1a = "0";
  private flagform1b = "0";
  private flagform2 = "0";
  private flagform3 = "0";
  private flagform4 = "0";
  private flagform5 = "0";
  private flagsecform = "0";

  supplementryFormStatus = "new";

  constructor(location: PlatformLocation, private router: Router, private _diphHttpClientService: DiphHttpClientService, public _location: Location) {
    location.onPopState(() => {
      console.log("pressed back in add!!!!!");
      //this.router.navigateByUrl('dashboard');
      history.forward();
    });
  }

  ngOnInit() {

    let user = sessionStorage.getItem('username');
    if (user == null || user.length == 0) {
      this.router.navigate(['login']);
    }


    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {
        console.log(data);

        if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {

        } else {

          this.flagform1a = "1";
          this.lockCurrentCycle("form1a");
        }
      },
      error => {
        console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
      });




  this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {
        console.log(data);

        if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {

        } else {

          this.flagform1b = "1";
          this.lockCurrentCycle("form1b");
        }
      },
      error => {
        console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
      });



  this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {
        console.log(data);

        if (data.form_2_date_of_meeting == null && data.form_2_venue == null && data.form_2_filled == null) {

        } else {

          this.flagform2 = "1";
          this.lockCurrentCycle("form2");
        }
      },
      error => {
        console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
      });



  this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {

        console.log(data);

        if (data.form_3_checkdate == null && data.form_3_meeting_venue == null && data.form_3_filled_by == null) {

        } else {

          this.flagform3 = "1";
          this.lockCurrentCycle("form3");
        }
      },
      error => {
        console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
      });



  this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {

        console.log(data);

        if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chariperson_of_meeting == null) {

        } else {

          this.flagform4 = "1";
          this.lockCurrentCycle("form4");
        }
      },
      error => {
        console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
      });


  this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
    .subscribe(
      data => {

        console.log(data);

        if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chairperson_of_meeting == null) {

        } else {

          this.flagform5 = "1";
          this.lockCurrentCycle("form5");
        }
      },
      error => {
        console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
      });




    this._diphHttpClientService.getSavedSupplementaryForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          console.log(data);

          if (data.parta_document_title == null && data.parta_date_of_release == null && data.parta_goal == null) {
            //alert("New form");
            $("#dashboard-supplementaryform1a-view").css("display", "none");
            $("#dashboard-supplementaryform1a-new").css("display", "");
            //dashboard-formA-view
          } else {
            this.flagsecform = "1";
            this.lockCurrentCycle("secform");
            this.form1bfilledalready = true;
            $("#dashboard-supplementaryform1a-view").css("display", "");
            $("#dashboard-supplementaryform1a-new").css("display", "none");

            if(data.completed != null && data.completed == '0'){
              this.supplementryFormStatus = "progress";
            }else if(data.completed != null && data.completed == '1'){
              this.supplementryFormStatus = "completed";
            }else{
              this.supplementryFormStatus = "new";
            }
          }

        },
        error => {
          console.log(error); alert("Error:\n1-Please check your internet connection and reload the page.\n2-Contact Administrator");
        });


        let lockcurrentcycle =  sessionStorage.getItem('lockcurrentcycle');  
             

        //If table is locked already
        if(lockcurrentcycle == "1"){
 
          let supplementaryform1a = document.getElementById("dashboard-supplementaryform1a-view-span") as HTMLElement;

          supplementaryform1a.style.visibility = "hidden";
 
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
             if(sessionStorage.getItem('userStatus') == "1"){
               let lockbtn = document.getElementById("lockbtn") as HTMLElement;
               try {
                 lockbtn.removeAttribute("disabled");
               } catch (error) {              
               }
               try {
                 lockbtn.classList.remove("btn-secondary");
               } catch (error) {              
               }
               try {
                 lockbtn.classList.add("btn-danger");
               } catch (error) {              
               }
             }
            
 
 
        }
        //If table is not locked
        else
        {
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
         if(sessionStorage.getItem('userStatus') == "1"){
           let unlockbtn = document.getElementById("unlockbtn") as HTMLElement;
           try {
             unlockbtn.removeAttribute("disabled");
           } catch (error) {              
           }
           try {
             unlockbtn.classList.remove("btn-secondary");
           } catch (error) {              
           }
           try {
             unlockbtn.classList.add("btn-danger");
           } catch (error) {              
           }
         }
 
        }



  }

  lockCurrentCycle(val: string) {

    if ((this.flagform1a == "1") && (this.flagform1b == "1") && (this.flagform2 == "1") && (this.flagform3 == "1") && (this.flagform4 == "1") && (this.flagform5 == "1") && (this.flagsecform == "1")) {
      // alert("Dashboard All forms Completed");
      let unlockbtn = document.getElementById("unlockbtn") as HTMLElement;
      try {
        unlockbtn.removeAttribute("disabled");
      } catch (error) {              
      }
      try {
        unlockbtn.classList.remove("btn-secondary");
      } catch (error) {              
      }
      try {
        unlockbtn.classList.add("btn-danger");
      } catch (error) {              
      }
    }

  }



  onClickdeletesupplementaryform1aDetails() {
    let answer = confirm("Are you sure, you want to delete?"); 

    if (answer) {
      alert("Deleting form Details");

    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    this._diphHttpClientService.deleteSupplementaryForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
          console.log(data);
          alert("User details data deleted successfully!!!");

          this.router.routeReuseStrategy.shouldReuseRoute = () => false;
          this.router.onSameUrlNavigation = 'reload';
          this.router.navigate(['/dashboard/secondaryforms']);


        },
        error => {
          console.log(error); alert("Error= " + error);
        });

      }
  }

}
