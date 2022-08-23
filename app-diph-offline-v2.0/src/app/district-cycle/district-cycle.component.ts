import { Component, OnInit } from '@angular/core';
import { PlatformLocation } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { District } from '../model/district';
import { Cycle } from '../model/cycle';
import { State } from '../model/state';

@Component({
  selector: 'app-district-cycle',
  templateUrl: './district-cycle.component.html',
  styleUrls: ['./district-cycle.component.css']
})
export class DistrictCycleComponent implements OnInit {

  public states = new Array<State>();
  public districts = new Array<District>();
  public cycles = new Array<Cycle>();

  public global_countryId = "";
  public global_regionId = "";

  constructor(location: PlatformLocation, private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService) {
    location.onPopState(() => {
      console.log("pressed back in add!!!!!");
      //this.router.navigateByUrl('district');
      history.forward();
    });
  }

  ngOnInit() {
    // sessionStorage.removeItem('district');
    // sessionStorage.removeItem('cycle');
    // sessionStorage.removeItem('year');
    // let user = sessionStorage.getItem('username');
    // if (user == null || user.length == 0) {
    //   this.router.navigate(['login']);
    // }

    let countryId: string = "";
    let regionId: string = "";

    countryId = sessionStorage.getItem('countryId');

    regionId = sessionStorage.getItem('regionId');

   

    if (countryId == null || regionId == null ) {
      countryId = this.route.snapshot.paramMap.get('country');      
      regionId = this.route.snapshot.paramMap.get('region');
      
    }



    this._diphHttpClientService.getStateDetails(regionId).subscribe(data => {
      this.states = data;
    });
    this._diphHttpClientService.getCycleDetails().subscribe(data => {
      this.cycles = data;
    });

    $(document).ready(function () {


      // $("#ucNoteGrid_grdViewNotes_ctl01_btnPrint").click(function () {
      //   $("#coverScreen").show();
      //   });

    });
  }

  ngAfterViewInit() {
    //alert("Loaded");
    // $("#coverScreen").hide();
  }

  onSelectZone(zoneid: string) {
    this._diphHttpClientService.getDistrictDetails(zoneid).subscribe(data => {
      this.districts = data;
    });
  }

  previouspage() {
    let countryId: string = "";
    let regionId: string = "";

    countryId = sessionStorage.getItem('countryId');

    regionId = sessionStorage.getItem('regionId');



    if (countryId == null || regionId == null) {
      countryId = this.route.snapshot.paramMap.get('country');
      regionId = this.route.snapshot.paramMap.get('region');

      if(countryId == null || regionId == null){
        countryId = this.global_countryId;
        regionId = this.global_regionId;
      }
      
    }
    
    this.router.navigate(['country', { country: countryId, region: regionId }]);
    //this.router.navigate(['country']);
  }

  onDistrictSubmit(districtCycleFormData: any) {

    if (districtCycleFormData.district.length == 0 || districtCycleFormData.cycle.length == 0 || districtCycleFormData.year.length == 0) {
      alert("Please select valid District/Cycle/Year");
      districtCycleFormData.resetForm();
      return;
    }

    //$("#coverScreen").show();

    // sessionStorage.setItem('district', districtCycleFormData.district);
    // sessionStorage.setItem('cycle', districtCycleFormData.cycle);
    // sessionStorage.setItem('year', districtCycleFormData.year);

    let countryId: string = "";
    let regionId: string = "";

    countryId = sessionStorage.getItem('countryId');

    regionId = sessionStorage.getItem('regionId');



    if (countryId == null || regionId == null) {
      countryId = this.route.snapshot.paramMap.get('country');
      regionId = this.route.snapshot.paramMap.get('region');

      if (countryId == null || countryId == regionId) {
        this.router.navigate(['']);
      }
      else {
        this.router.navigate(['login', { countryId: countryId, regionId: regionId, zoneId: districtCycleFormData.state, district: districtCycleFormData.district, cycle: districtCycleFormData.cycle, year: districtCycleFormData.year }]);
      }

    }
    /*If already Loginthen goes to Else*/
    else {
      
      //District user

      let district  = districtCycleFormData.district;
      let cycle =  districtCycleFormData.cycle;
      let year =  districtCycleFormData.year;

      let user_nm = sessionStorage.getItem('username');

      this._diphHttpClientService.getuserallowedstatus(user_nm, district, cycle, year).subscribe(
        data => {

          if (data.allowed == "1") {

            this._diphHttpClientService.getcyclelockstatus(district, cycle, year).subscribe(
              data => {

                if (data.status == "0") {
                  sessionStorage.setItem('zoneId', districtCycleFormData.state);
                  sessionStorage.setItem('district', districtCycleFormData.district);
                  sessionStorage.setItem('cycle', districtCycleFormData.cycle);
                  sessionStorage.setItem('year', districtCycleFormData.year);
                  sessionStorage.setItem('lockcurrentcycle', '0');
                  
                  this.router.navigate(['dashboard']);
                }
                else if (data.status == "1") {
                  sessionStorage.setItem('zoneId', districtCycleFormData.state);
                  sessionStorage.setItem('district', districtCycleFormData.district);
                  sessionStorage.setItem('cycle', districtCycleFormData.cycle);
                  sessionStorage.setItem('year', districtCycleFormData.year);
                  sessionStorage.setItem('lockcurrentcycle', '1');
                 
                  this.router.navigate(['dashboard']);
                }
                else {
                  sessionStorage.setItem('zoneId', districtCycleFormData.state);
                  sessionStorage.setItem('district', districtCycleFormData.district);
                  sessionStorage.setItem('cycle', districtCycleFormData.cycle);
                  sessionStorage.setItem('year', districtCycleFormData.year);
                  sessionStorage.setItem('lockcurrentcycle', '0');
                  
                  this.router.navigate(['dashboard']);
                }

              },
              error => {
                console.log(error); alert("Error= " + error);
              });
          }
          else {
            alert("User not allowed for current district,year and cycle");            
          }





        },//check user id allowed
        error => {
          console.log(error); alert("Error= " + error);
        });


      /*
      sessionStorage.setItem('district', districtCycleFormData.district);
      sessionStorage.setItem('cycle', districtCycleFormData.cycle);
      sessionStorage.setItem('year', districtCycleFormData.year);


       this._diphHttpClientService.getcyclelockstatus(districtCycleFormData.district, districtCycleFormData.cycle, districtCycleFormData.year).subscribe(
        data => {

          if(data.status == "0"){
            sessionStorage.setItem('lockcurrentcycle','0');
          }
          else if(data.status == "1"){
            sessionStorage.setItem('lockcurrentcycle','1');
          }
          else{
            sessionStorage.setItem('lockcurrentcycle','0');
          }
        },
        error => {
          console.log(error); alert("Error= " + error);
        });     

      this.router.navigate(['dashboard']);
        */

    }



    // this.router.navigate(['dashboard']);
  }


}
