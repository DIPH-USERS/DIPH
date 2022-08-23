import { Component, OnInit } from '@angular/core';
import { PlatformLocation } from '@angular/common';
import { Router } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { District } from '../model/district';

@Component({
  selector: 'app-manage-district',
  templateUrl: './manage-district.component.html',
  styleUrls: ['./manage-district.component.css']
})
export class ManageDistrictComponent implements OnInit {

  DistrictResponseObj: any;
  deleteResponseObj: any;
  newUser_form: any;
  public districts = new Array<District>();

  constructor(private router: Router, private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {

    let user = sessionStorage.getItem('username');
    if (user == null || user.length == 0) {
      this.router.navigate(['']);
    }

    let userStatus = sessionStorage.getItem('userStatus');

    if ("0" == userStatus) {
      this.router.navigate(['/dashboard']);
    }
    else if ("1" == userStatus) {
      //No action
    }
    else {
      this.router.navigate(['']);
    }
    this.showAllDistrict();
  }

  previouspage() {
    let ans = confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");

    if (ans) {
      this.router.navigate(['dashboard']);
    }

  }

  validate100charactersallowed(val:string){
    if(val != null && val.length <=100){      
      return false;
    }
    else if(val != null && val.length > 100){
      return true;
    }
    else if(val == null || (val!= null && val.trim() == "")){
      return true; 
    }
  }

  validateSpecialCharsAndNum(val:string){    
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
  return  (!!(val).replace(/[A-Za-z\s]/gi, '').length);
  }


  onNewDistrictSubmit(newDistrict_form: any) {

    if(newDistrict_form.district_name != null  && newDistrict_form.district_name.trim() == ""){
      alert("District name must be of 3 letters or above");
      return;
    }

    if (newDistrict_form.district_name.length < 3) {
      alert("District name must be of 3 letters or above");
      return;
    }

    if(this.validate100charactersallowed(newDistrict_form.district_name)){
      alert("Total Characters within and equal to 100 length only allowed. ");
      return;
    }

    if(this.validateSpecialCharsAndNum(newDistrict_form.district_name)){
      alert("Alphabets and space only allowed.");
      return;
    }

    

    let countryId: string = sessionStorage.getItem('countryId');
    let zoneId: string = sessionStorage.getItem('zoneId');

    newDistrict_form.country_id = "" + countryId;
    newDistrict_form.state_id = "" + zoneId;

    // alert(JSON.stringify(newDistrict_form,null,4));

    // return;

    this._diphHttpClientService.createDistrict(newDistrict_form).subscribe(data => {
      if (data.status == "created") {
        alert("New District Created");
        //this.showAllDistrict();
        this.router.routeReuseStrategy.shouldReuseRoute = () => false;
        this.router.onSameUrlNavigation = 'reload';
        this.router.navigate(['/dashboard/manage-district']);
      }
      else if (data.status == "exists")
        alert("District Already Exists");
      else alert("District Can Not Be Created");


      this.router.navigate(['/dashboard/manage-district']);;
    });


  }

  showAllDistrict() {


    let countryId: string = sessionStorage.getItem('countryId');
    let zoneId: string = sessionStorage.getItem('zoneId');


    //alert("countryId = "+countryId+", zoneId = "+zoneId);

    this._diphHttpClientService.getDistrictDetails(zoneId).subscribe(data => {
      this.districts = data;
    });

  }

  deleteDistrict(district_id: string) {

    let result = confirm("Do you really want to delete district ? ");

    if (result) {
      this._diphHttpClientService.deleteDistrict(district_id)
        .subscribe(
          data => {
            this.deleteResponseObj = data;
            if (data.status == "deleted") {
              alert("District has been deleted");
              this.showAllDistrict();
            } else {
              alert("District Can Not Be Deleted");
            }
          },
          error => {
            console.log(error); alert("Error= " + error);
          });
    }
  }


}
