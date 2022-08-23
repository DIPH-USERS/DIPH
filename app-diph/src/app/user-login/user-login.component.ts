import { Component, OnInit, SecurityContext } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginToDashboard } from '../model/login_to_dashboard';
import { User } from '../model/user';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { DomSanitizer } from '@angular/platform-browser';


@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.css']
})
export class UserLoginComponent implements OnInit {

  private loginToDashboard: LoginToDashboard;
  public user = new User("", "", "", "");


  constructor(private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService, private sanitizer: DomSanitizer) { }

  ngOnInit() {

    let countryId: string = "";
    let regionId: string = "";
    let zoneId: string = "";
    let district: string = "";
    let cycle: string = "";
    let year: string = "";

    countryId = sessionStorage.getItem('countryId');

    regionId = sessionStorage.getItem('regionId');

    zoneId = sessionStorage.getItem('zoneId');

    district = sessionStorage.getItem('district');

    cycle = sessionStorage.getItem('cycle');

    year = sessionStorage.getItem('year');


    if (countryId == null || regionId == null) {
      countryId = this.route.snapshot.paramMap.get('countryId');
      regionId = this.route.snapshot.paramMap.get('regionId');
      zoneId = this.route.snapshot.paramMap.get('zoneId');
      district = this.route.snapshot.paramMap.get('district');
      cycle = this.route.snapshot.paramMap.get('cycle');
      year = this.route.snapshot.paramMap.get('year');
    }

    if (countryId == null || regionId == null || zoneId == null || district == null || cycle == null || year == null) {
      this.router.navigate(['']);
    }

  }
  ngAfterViewInit() {
    //alert("Loaded");
    $("#coverScreen").hide();


  }

  forgotpasswordclick(){    

    let countryId = this.route.snapshot.paramMap.get('countryId');
    let  regionId = this.route.snapshot.paramMap.get('regionId');
    let  zoneId = this.route.snapshot.paramMap.get('zoneId');
    let  district = this.route.snapshot.paramMap.get('district');
    let  cycle = this.route.snapshot.paramMap.get('cycle');
    let  year = this.route.snapshot.paramMap.get('year');

    //alert("moving to password = "+countryId+", "+regionId+", "+zoneId+", "+district+", "+cycle+", "+year);
    this.router.navigate(['forgotpassword', { countryId: countryId, regionId: regionId, zoneId: zoneId, district: district, cycle: cycle, year: year }]);
  }

  previouspage() {

    let countryId: string = "";
    let regionId: string = "";

    countryId = sessionStorage.getItem('countryId');

    regionId = sessionStorage.getItem('regionId');



    if (countryId == null || regionId == null) {
      countryId = this.route.snapshot.paramMap.get('countryId');
      regionId = this.route.snapshot.paramMap.get('regionId');
    }
    this.router.navigate(['district', { country: countryId, region: regionId }]);
  }

  onLogin(loginFormData: any) {    

    if (loginFormData.username.length == 0 || loginFormData.password.length == 0) {
      alert("Please fill your username/password");
      return;
    }
    
    var sanitizedUsername:string = this.sanitizer.sanitize(SecurityContext.HTML, loginFormData.username);
    var sanitizedPassword:string = this.sanitizer.sanitize(SecurityContext.HTML, loginFormData.password);

       
    let countryId: string = this.route.snapshot.paramMap.get('countryId');
    let regionId: string = this.route.snapshot.paramMap.get('regionId');
    let zoneId: string = this.route.snapshot.paramMap.get('zoneId');

    let district: string = this.route.snapshot.paramMap.get('district');
    let cycle: string = this.route.snapshot.paramMap.get('cycle');
    let year: string = this.route.snapshot.paramMap.get('year');

    this._diphHttpClientService.authenticateUser(sanitizedUsername, sanitizedPassword).subscribe(data => {

      //debugger;

      try {
        $("#coverScreen").show();
      } catch (error) {

      }

      if (data.status == "authentic") {

        let session_username = ""+data.username;
        let session_userStatus = ""+data.userStatus;
        let session_countryId = ""+countryId;
        let session_regionId = ""+regionId;
        let session_zoneId = ""+zoneId;
        let session_userid = ""+data.id;

        let session_district = ""+district;
        let session_cycle = ""+cycle;
        let session_year = ""+year;

        var authString = ('Basic ' + btoa(loginFormData.username + ':' + loginFormData.password));
        sessionStorage.setItem('authString', authString);
        sessionStorage.setItem('username', loginFormData.username);
        //debugger;

        //Check weather the logged in user is allowed to log in this district
        this._diphHttpClientService.getAllUsersOfDistrict(data.username, district)
          .subscribe(
            result => {
              
              //debugger;

              let currentdata;
              for (let pos = 0; pos < result.userList.length; pos++) {
                let tempobj = result.userList[pos];
                if (tempobj.user_nm == data.username) {
                  currentdata = tempobj;
                  break;
                }
              }

              let count = 0;

              let district_arr = [];
              let cycle_arr = [];
              let year_arr = [];


              let dst = currentdata.district;
              let cyc = currentdata.cycle;
              let ye = currentdata.year;

              //.reduce is used to remove duplicate values from Array
              district_arr = dst.reduce(function (a, b) {
                if (a.indexOf(b) < 0) a.push(b);
                return a;
              }, []);

              cycle_arr = cyc.reduce(function (a, b) {
                if (a.indexOf(b) < 0) a.push(b);
                return a;
              }, []);

              year_arr = ye.reduce(function (a, b) {
                if (a.indexOf(b) < 0) a.push(b);
                return a;
              }, []);

              currentdata.district = district_arr;

              currentdata.cycle = cycle_arr;

              currentdata.year = year_arr;


              //Check if Logged in user is Admin or not
              if (data.userStatus == "1") {

                this._diphHttpClientService.getcyclelockstatus(district, cycle, year).subscribe(
                  data => {

                    if (data.status == "0") {
                      localStorage.setItem('username', session_username);
                      sessionStorage.setItem('username', session_username);
                      sessionStorage.setItem('userStatus', session_userStatus);
                      sessionStorage.setItem('countryId', session_countryId);
                      sessionStorage.setItem('regionId', session_regionId);
                      sessionStorage.setItem('zoneId', session_zoneId);
                      sessionStorage.setItem('userid', session_userid);

                      sessionStorage.setItem('district', session_district);
                      sessionStorage.setItem('cycle', session_cycle);
                      sessionStorage.setItem('year', session_year);
                      sessionStorage.setItem('lockcurrentcycle', '0');
                      count++;
                      this.router.navigate(['dashboard']);
                    }
                    else if (data.status == "1") {
                      sessionStorage.setItem('username', session_username);
                      sessionStorage.setItem('userStatus', session_userStatus);
                      sessionStorage.setItem('countryId', session_countryId);
                      sessionStorage.setItem('regionId', session_regionId);
                      sessionStorage.setItem('zoneId', session_zoneId);
                      sessionStorage.setItem('userid', session_userid);

                      sessionStorage.setItem('district', session_district);
                      sessionStorage.setItem('cycle', session_cycle);
                      sessionStorage.setItem('year', session_year);
                      sessionStorage.setItem('lockcurrentcycle', '1');
                      count++;
                      this.router.navigate(['dashboard']);
                    }
                    else {
                      sessionStorage.setItem('username', session_username);
                      sessionStorage.setItem('userStatus', session_userStatus);
                      sessionStorage.setItem('countryId', session_countryId);
                      sessionStorage.setItem('regionId', session_regionId);
                      sessionStorage.setItem('zoneId', session_zoneId);
                      sessionStorage.setItem('userid', session_userid);

                      sessionStorage.setItem('district', session_district);
                      sessionStorage.setItem('cycle', session_cycle);
                      sessionStorage.setItem('year', session_year);
                      sessionStorage.setItem('lockcurrentcycle', '0');
                      count++;
                      this.router.navigate(['dashboard']);
                    }

                  },
                  error => {
                     alert("Error= " + error);
                  });
              }
              else {


                //District user



                this._diphHttpClientService.getuserallowedstatus(currentdata.user_nm, district, cycle, year).subscribe(
                  data => {

                    // alert(JSON.stringify(data,null,4));


                    if (data.allowed == "1") {

                      



                      this._diphHttpClientService.getcyclelockstatus(district, cycle, year).subscribe(
                        data => {

                          if (data.status == "0") {
                            sessionStorage.setItem('username', session_username);
                            sessionStorage.setItem('userStatus', session_userStatus);
                            sessionStorage.setItem('countryId', session_countryId);
                            sessionStorage.setItem('regionId', session_regionId);
                            sessionStorage.setItem('zoneId', session_zoneId);
                            sessionStorage.setItem('userid', session_userid);

                            sessionStorage.setItem('district', session_district);
                            sessionStorage.setItem('cycle', session_cycle);
                            sessionStorage.setItem('year', session_year);
                            sessionStorage.setItem('lockcurrentcycle', '0');
                            count++;
                            this.router.navigate(['dashboard']);
                          }
                          else if (data.status == "1") {
                            sessionStorage.setItem('username', session_username);
                            sessionStorage.setItem('userStatus', session_userStatus);
                            sessionStorage.setItem('countryId', session_countryId);
                            sessionStorage.setItem('regionId', session_regionId);
                            sessionStorage.setItem('zoneId', session_zoneId);
                            sessionStorage.setItem('userid', session_userid);

                            sessionStorage.setItem('district', session_district);
                            sessionStorage.setItem('cycle', session_cycle);
                            sessionStorage.setItem('year', session_year);
                            sessionStorage.setItem('lockcurrentcycle', '1');
                            count++;
                            this.router.navigate(['dashboard']);
                          }
                          else {
                            sessionStorage.setItem('username', session_username);
                            sessionStorage.setItem('userStatus', session_userStatus);
                            sessionStorage.setItem('countryId', session_countryId);
                            sessionStorage.setItem('regionId', session_regionId);
                            sessionStorage.setItem('zoneId', session_zoneId);
                            sessionStorage.setItem('userid', session_userid);

                            sessionStorage.setItem('district', session_district);
                            sessionStorage.setItem('cycle', session_cycle);
                            sessionStorage.setItem('year', session_year);
                            sessionStorage.setItem('lockcurrentcycle', '0');
                            count++;
                            this.router.navigate(['dashboard']);
                          }

                        },
                        error => {
                          alert("Error= " + error);
                        });
                    }
                    else {
                      alert("User not allowed for current district,year and cycle");
                      try {
                        $("#coverScreen").hide();
                      } catch (error) {

                      }
                    }





                  },//check user id allowed
                  error => {
                    alert("Error= " + error);
                  });
              }






            },//get all users of district
            error => {
              alert("Error= " + error);
            });


      }
      else {
        try {
          $("#coverScreen").hide();
        } catch (error) {

        }

        alert("Please fill your valid credentials");
        return;
      }
    },
    error => { 
      alert("Please fill your valid credentials");
      return;
    });

  }


  isUserLoggedIn() {
    let user = sessionStorage.getItem('username');    
    return !(user === null);
  }

  logOut() {
    sessionStorage.removeItem('username');
    sessionStorage.removeItem('authString');
  }

}
