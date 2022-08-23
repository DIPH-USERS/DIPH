import { Component, OnInit, Input } from '@angular/core';
import { PlatformLocation } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Country } from '../model/country';
import { State } from '../model/state';
import { Region } from '../model/region';
import { District } from '../model/district';

@Component({
  selector: 'app-contact-form',
  templateUrl: './contact-form.component.html',
  styleUrls: ['./contact-form.component.css']
})
export class ContactFormComponent implements OnInit {

  @Input() customer: any = {};

  selectedCountry = 0;
  selectedState = 0;
  registeredUser = "0";

  public errorMessage: any;
  public countries = new Array<Country>();
  public states = new Array<State>();
  public regions = new Array<Region>();
  public districts = new Array<District>();

  public global_countryId = "";
  public global_regionId = "";
  constructor(location: PlatformLocation, private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() { 
    this.customer.username = "";
    this.customer.country_n = "";
    this.customer.region_n = "";
    this.customer.state_n = "";
    this.customer.district_n = "";
    this.customer.organization_n = "";
    this.customer.address_n = "";
    this.customer.mobile_n = "";
    this.customer.email = "";
    this.customer.subject_n = "";
    this.customer.message_desc = "";

    this._diphHttpClientService.getCountryDetails().subscribe(data => {
      this.countries = data;
      let username = sessionStorage.getItem('username');
      let userStatus = sessionStorage.getItem('userStatus');
      let countryId = sessionStorage.getItem('countryId');
      let regionId = sessionStorage.getItem('regionId');
      let zoneId = sessionStorage.getItem('zoneId');
      let userid = sessionStorage.getItem('userid');

      let district = sessionStorage.getItem('district');
      let cycle = sessionStorage.getItem('cycle');
      let year = sessionStorage.getItem('year');


      if ((username != null) && (userStatus != null) && (countryId != null)
        && (regionId != null) && (zoneId != null) && (userid != null) && (district != null)
        && (cycle != null) && (year != null)) {

        this._diphHttpClientService.getRegionDetails(countryId).subscribe(data => {
          this.regions = data;

          this._diphHttpClientService.getStateDetails(regionId).subscribe(data => {
            this.states = data;

            this._diphHttpClientService.getDistrictDetails(zoneId).subscribe(data => {
              this.districts = data;
              this.customer.country_n = "" + countryId;
              this.customer.district_n = "" + district;
              this.registeredUser = "1";

              //document.getElementById("mySelect").disabled = true;

              let ele1 = document.getElementById("country_n") as HTMLElement;
              ele1.setAttribute("disabled", "true");
              // ele1.setAttribute("[attr.disabled]", "true");
              //ele1.disabled = true;
              let ele2 = document.getElementById("district_n") as HTMLElement;
              ele2.setAttribute("disabled", "true");
              // ele2.setAttribute("[attr.disabled]", "true");  
            });
          });
        });


      }
      else {
        this.registeredUser = "0";
      }
    });
  }

  previouspage() {

    // alert("Inside previous page");

    let username = sessionStorage.getItem('username');
    let userStatus = sessionStorage.getItem('userStatus');
    let countryId = sessionStorage.getItem('countryId');
    let regionId = sessionStorage.getItem('regionId');
    let zoneId = sessionStorage.getItem('zoneId');
    let userid = sessionStorage.getItem('userid');

    let district = sessionStorage.getItem('district');
    let cycle = sessionStorage.getItem('cycle');
    let year = sessionStorage.getItem('year');

    // alert("username = "+username+", userStatus = "+userStatus+
    // ", countryId = "+countryId+", regionId = "+regionId+", zoneId ="+zoneId);

    if ((username != null) && (userStatus != null) && (countryId != null)
      && (regionId != null) && (zoneId != null) && (userid != null) && (district != null)
      && (cycle != null) && (year != null)) {

      this.router.navigate(['/dashboard']);
    }
    else {
      this.router.navigate(['/']);
    }

    //this.router.navigate(['country']);
  }


  onSelectCountry(country_id: string) {
    let dropDown = document.getElementById("region_n") as HTMLSelectElement;;
    dropDown.selectedIndex = 0;
    this.regions = [];

    this._diphHttpClientService.getRegionDetails(country_id).subscribe(data => {
      this.regions = data;
    });
  }

  onSelectRegion(regionId: string) {
    this._diphHttpClientService.getStateDetails(regionId).subscribe(data => {
      this.states = data;
    });
  }

  onSelectZone(zoneid: string) {
    this._diphHttpClientService.getDistrictDetails(zoneid).subscribe(data => {
      this.districts = data;
    });
  }

  validateSpecialCharsAndNum(val:string){    
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
  return  (!!(val).replace(/[A-Za-z\s]/gi, '').length);
  }

  

  validateNumbers(val: string) {
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
    return (!!(val).replace(/[0-9\s]/gi, '').length);
  }

  validate10numbersallowed(val:string){
    if(val != null && val.length ==10){      
      return false;
    }
    else if(val != null && val.length < 10){
      return true;
    }
    else if(val != null && val.length > 10){
      return true;
    }
    else if(val == null || (val!= null && val.trim() == "")){
      return true; 
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

  ValidateEmail(mail) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
      return (false)
    }
    alert("You have entered an invalid email address!")
    return (true)
  }

  submitted = false;

  onsubmit() {

    this.submitted = true;
    let finalresult = true;


    if (this.customer.username == "" ) {
      finalresult = false;
    }

    

   

    if (this.customer.country_n == "") {
      finalresult = false;
    }
    if (this.customer.district_n == "") {
      finalresult = false;
    }
    if (this.customer.organization_n == "") {
      finalresult = false;
    }
    if (this.customer.address_n == "") {
      finalresult = false;
    }
    if (this.customer.mobile_n == "") {
      finalresult = false;
    }
    if (this.customer.email == "") {
      finalresult = false;
    }
    if (this.customer.subject_n == "") {
      finalresult = false;
    }
    if (this.customer.message_desc == "") {
      finalresult = false;
    }




    if (finalresult == false) {
      alert("Please fill up all the details.");
      return;
    }
    else if (this.ValidateEmail(this.customer.email)) {
     // alert("Invalid email format");
      return;
    }
    else  if(this.validate100charactersallowed(this.customer.username)){
      //alert("Alphabets allowed upto 100 characters");
      return;
    }
    else  if (this.validate100charactersallowed(this.customer.organization_n)) {
      return;
    }
    else  if (this.validate100charactersallowed(this.customer.address_n)) {
      return;
    }
    else  if (this.validate10numbersallowed(this.customer.mobile_n)) {
      return;
    }
    else if(this.validateSpecialCharsAndNum(this.customer.username)){
      //alert("Only alphabets allowed");
      return;
    }

     if(this.customer.username != null  && this.customer.username.trim() == ""){
       //alert("Username cannot be empty");
       return;
     }

     if(this.customer.organization_n != null  && this.customer.organization_n.trim() == ""){
      //alert("Username cannot be empty");
      return;
    }

    if(this.customer.address_n != null  && this.customer.address_n.trim() == ""){
      //alert("Username cannot be empty");
      return;
    }

    if(this.customer.mobile_n != null  && this.customer.mobile_n.trim() == ""){
      //alert("Username cannot be empty");
      return;
    }



    let countrySelect = document.getElementById("country_n") as HTMLSelectElement;
    let selectedcountry = countrySelect.options[countrySelect.selectedIndex].text;

    this.customer.selectedcountry = selectedcountry + "";

    let districtSelect = document.getElementById("district_n") as HTMLSelectElement;
    let selecteddistrict = districtSelect.options[districtSelect.selectedIndex].text;

    this.customer.selecteddistrict = selecteddistrict + "";

    let _username = sessionStorage.getItem('username');
    let _userStatus = sessionStorage.getItem('userStatus');
    let _countryId = sessionStorage.getItem('countryId');
    let _regionId = sessionStorage.getItem('regionId');
    let _zoneId = sessionStorage.getItem('zoneId');
    let _userid = sessionStorage.getItem('userid');

    let _district = sessionStorage.getItem('district');
    let _cycle = sessionStorage.getItem('cycle');
    let _year = sessionStorage.getItem('year');

    // alert("username = "+username+", userStatus = "+userStatus+
    // ", countryId = "+countryId+", regionId = "+regionId+", zoneId ="+zoneId);

    if ((_username != null) && (_userStatus != null) && (_countryId != null)
      && (_regionId != null) && (_zoneId != null) && (_userid != null) && (_district != null)
      && (_cycle != null) && (_year != null)) {     
        this.customer.registeredUser = "1";
    }
    else {
      this.customer.registeredUser = "0";
    }

    alert("Sending Message. Please wait!!");

    this._diphHttpClientService.savecontactdetails(this.customer)
      .subscribe(
        data => {

          if (data.status == "1") {
            alert("Contact Message Successfully submitted!!");
            console.log(data);
            let username = sessionStorage.getItem('username');
            let userStatus = sessionStorage.getItem('userStatus');
            let countryId = sessionStorage.getItem('countryId');
            let regionId = sessionStorage.getItem('regionId');
            let zoneId = sessionStorage.getItem('zoneId');
            let userid = sessionStorage.getItem('userid');
            let district = sessionStorage.getItem('district');
            let cycle = sessionStorage.getItem('cycle');
            let year = sessionStorage.getItem('year');

            if ((username != null) && (userStatus != null) && (countryId != null)
              && (regionId != null) && (zoneId != null) && (userid != null) && (district != null)
              && (cycle != null) && (year != null)) {

              this.router.navigate(['/dashboard']);
            }
            else {
              this.router.navigate(['/']);
            }
          }
          else {
            alert("Some error found! Please contact Server Admin");
          }

        },
        error => { console.log(error); alert("Server Error!! Please contact Administrator"); console.log("Error= " + error); });
  }

}



