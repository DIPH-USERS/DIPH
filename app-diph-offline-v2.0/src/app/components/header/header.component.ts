import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { DiphHttpClientService } from '../../services/diph-http-client-service.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  district_name: string;
  cycle_name: string;
  year: string;
  userName: string;
  private flagform1a = "0";
  private flagform1b = "0";
  private flagform2 = "0";
  private flagform3 = "0";
  private flagform4 = "0";
  private flagform5 = "0";
  private flagsecform = "0";

  constructor(private router: Router, private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {

    let district_id: string, cycle_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    this.userName = "" + sessionStorage.getItem('username');
    
    this._diphHttpClientService.getDistrictDetailsFromId(district_id).subscribe(data => {
      this.district_name = data.district_name;
    });
    this._diphHttpClientService.getCycleDetailsFromId(cycle_id).subscribe(data => {
      this.cycle_name = data.cycle_name;
    });
    this.year = sessionStorage.getItem('year');



    let year: string, user_id: string;


    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');


    this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {    

          //console.log("getSavedForm1ADetails : "+data.completed);

          if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {

          } else if(data.completed == "1") {

            this.flagform1a = "1";
            this.lockCurrentCycle("form1a");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });




    this._diphHttpClientService.getSavedForm1BDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
          
          //console.log("getSavedForm1BDetails : "+data.completed);

          if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {

          } else if(data.completed == "1"){

            this.flagform1b = "1";
            this.lockCurrentCycle("form1b");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });



    this._diphHttpClientService.getSavedForm2EngageDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
          
          //console.log("getSavedForm2EngageDetails : "+data.completed);

          if (data.form_2_date_of_meeting == null && data.form_2_venue == null && data.form_2_filled == null) {

          } else if(data.completed == "1"){

            this.flagform2 = "1";
            this.lockCurrentCycle("form2");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });



    this._diphHttpClientService.getSavedForm3DefineDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          //console.log("getSavedForm3DefineDetails : "+data.completed);

          if (data.form_3_checkdate == null && data.form_3_meeting_venue == null && data.form_3_filled_by == null) {

          } else if(data.completed == "1"){

            this.flagform3 = "1";
            this.lockCurrentCycle("form3");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });



    this._diphHttpClientService.getSavedForm4PlanDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          //console.log("getSavedForm4PlanDetails : "+data.completed);

          if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chariperson_of_meeting == null) {

          } else if(data.completed == "1"){

            this.flagform4 = "1";
            this.lockCurrentCycle("form4");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });


    this._diphHttpClientService.getSavedForm5FollowupDetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          //console.log("getSavedForm5FollowupDetails : "+data.completed);

          if (data.date_of_meeting == null && data.venue_of_meeting == null && data.chairperson_of_meeting == null) {

          } else if(data.completed == "1"){

            this.flagform5 = "1";
            this.lockCurrentCycle("form5");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });


        /*
    this._diphHttpClientService.getSavedSupplementaryForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {

          if (data.parta_document_title == null && data.parta_date_of_release == null && data.parta_goal == null) {


          } else if(data.completed == "1"){
            this.flagsecform = "1";
            this.lockCurrentCycle("secform");
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });
        */

       let lockcurrentcycle =  sessionStorage.getItem('lockcurrentcycle');           

       //If table is locked already
       if(lockcurrentcycle == "1"){

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

  logOut() {
    sessionStorage.removeItem('username');
    sessionStorage.clear();
    localStorage.removeItem('username');
    localStorage.clear();
    this.router.navigate(['']);
  }

  lockCurrentCycle(val: string) {

    if ((this.flagform1a == "1") && (this.flagform1b == "1") && (this.flagform2 == "1") && (this.flagform3 == "1") && (this.flagform4 == "1") && (this.flagform5 == "1")) {
      // alert("Header All forms Completed");

      let lockcurrentcycle =  sessionStorage.getItem('lockcurrentcycle');  
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


      // if(lockcurrentcycle == "1"){

      //   let unlockedbtnspan = document.getElementById("unlockedbtnspan") as HTMLElement;

      //       try {
      //         unlockedbtnspan.style.display = "none"; 
      //       } catch (error) {              
      //       }
      //       let lockedbtnspan = document.getElementById("lockedbtnspan") as HTMLElement;    
      //       try {
      //         lockedbtnspan.style.display = "block"; 
      //       } catch (error) {              
      //       }

      //       //Check for Admin and district user
      //       if(sessionStorage.getItem('userStatus') == "1"){
      //         let lockbtn = document.getElementById("lockbtn") as HTMLElement;
      //         try {
      //           lockbtn.removeAttribute("disabled");
      //         } catch (error) {              
      //         }
      //         try {
      //           lockbtn.classList.remove("btn-secondary");
      //         } catch (error) {              
      //         }
      //         try {
      //           lockbtn.classList.add("btn-danger");
      //         } catch (error) {              
      //         }
      //       }
           


      //  }
    }


    

  }

  onBtnPressLockCycle() {
    let form1aspan = document.getElementById("dashboard-formA-view-span") as HTMLElement;
    let form1bspan = document.getElementById("dashboard-formB-view-span") as HTMLElement;
    let form2span = document.getElementById("dashboard-form2engage-view-span") as HTMLElement;
    let form3span = document.getElementById("dashboard-form3define-view-span") as HTMLElement;
    let form4span = document.getElementById("dashboard-form4plan-view-span") as HTMLElement;
    let form5span = document.getElementById("dashboard-form5followup-view-span") as HTMLElement;


    let result = confirm(`Are you sure to lock current cycle data for all forms.  
    This can only be unlocked by admin.`);

   

      //If Yes then move forward.
    if (result) {

      if ((this.flagform1a == "1") && 
      (this.flagform1b == "1") && (this.flagform2 == "1") 
      && (this.flagform3 == "1") && (this.flagform4 == "1") 
      && (this.flagform5 == "1")) {

        let district_id: string, cycle_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');

      let year: string, user_id: string;

      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.lockcurrentcycle(district_id, cycle_id, year, user_id).subscribe(
        data => {

          if(data.status == "1"){
            alert("Cycle locked successfully!!!");

            sessionStorage.setItem('lockcurrentcycle','1');
            
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
            

            let lockbtn = document.getElementById("lockbtn") as HTMLElement;

            try {
              //If admin then dont make display none
              if(sessionStorage.getItem('userStatus') != "1"){
                //lockbtn.style.display = "none";
                lockbtn.attributes['disabled'] = '';
              }

              //Now open lockedbtnspan means display it block
              let lockedbtnspan =  document.getElementById("lockedbtnspan") as HTMLElement;
              lockedbtnspan.style.display = "";
            } catch (error) {              
            }

            //If the person locking it is Admin then remove disabled attribute and remove class button secondary
            if(sessionStorage.getItem('userStatus') == "1"){
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
          else{
            alert("Server Error");
            console.log(data.message);
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });

      }
      else{
        alert("Please complete all the Primary forms before locking the cycle.");
      }

      

    }
  }

  onBtnPressUnLockCycle(){
    let form1aspan = document.getElementById("dashboard-formA-view-span") as HTMLElement;
    let form1bspan = document.getElementById("dashboard-formB-view-span") as HTMLElement;
    let form2span = document.getElementById("dashboard-form2engage-view-span") as HTMLElement;
    let form3span = document.getElementById("dashboard-form3define-view-span") as HTMLElement;
    let form4span = document.getElementById("dashboard-form4plan-view-span") as HTMLElement;
    let form5span = document.getElementById("dashboard-form5followup-view-span") as HTMLElement;


  let result=  confirm("Do you want to unlock current Cycle?");

  if(result){
    let district_id: string, cycle_id: string;

      district_id = "" + sessionStorage.getItem('district');
      cycle_id = "" + sessionStorage.getItem('cycle');
      let year: string, user_id: string;
      year = "" + sessionStorage.getItem('year');
      user_id = "" + sessionStorage.getItem('userid');

      this._diphHttpClientService.unlockcurrentcycle(district_id, cycle_id, year, user_id).subscribe(
        data => {

          if(data.status == "1"){
            alert("Cycle Unlocked successfully!!!");

            sessionStorage.setItem('lockcurrentcycle','0'); 
            
            form1aspan.style.visibility = "visible";
            form1bspan.style.visibility = "visible";
            form2span.style.visibility = "visible";
            form3span.style.visibility = "visible";
            form4span.style.visibility = "visible";
            form5span.style.visibility = "visible";

            let lockedbtnspan = document.getElementById("lockedbtnspan") as HTMLElement;

            lockedbtnspan.style.display = "none"; 

            //First open display of unlockedbtnspan

            let unlockedbtnspan = document.getElementById("unlockedbtnspan") as HTMLElement;

            unlockedbtnspan.style.display = ""; 

            //Now change properties of button
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
          else{
            alert("Server Error");
            console.log(data.message);
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });
  }


  }

  openChangePassword() {

    this.router.navigate(['changepassword']);
  }

}
