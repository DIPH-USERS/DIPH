import { Component, OnInit } from '@angular/core';
import { PlatformLocation } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Country } from '../model/country';
import { State } from '../model/state';
import { Region } from '../model/region';


@Component({
  selector: 'app-country-state',
  templateUrl: './country-state.component.html',
  styleUrls: ['./country-state.component.css']
})
export class CountryStateComponent implements OnInit {

  selectedCountry = 65;
  selectedState = 0;

  public errorMessage: any;
  public countries = new Array<Country>();//Compile time type checking
  public states = new Array<State>();
  public regions = new Array<Region>();

  constructor(location: PlatformLocation, private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {

    //this.selectedCountry = 65;
    this._diphHttpClientService.getCountryDetails().subscribe(data => {
      this.countries = data;
          
      this.regions = [];

      this._diphHttpClientService.getRegionDetails(this.selectedCountry.toString()).subscribe(data => {
        this.regions = data;
      });
    });

    


  }

  previouspage() {
    
    
    this.router.navigate(['/']);
    //this.router.navigate(['country']);
  }


  ngAfterViewInit() {

    
    // let countryId: string = "";
    // let regionId: string = "";

    // countryId = sessionStorage.getItem('countryId');

    // regionId = sessionStorage.getItem('regionId');



    // if (countryId == null || regionId == null) {
    //   countryId = this.route.snapshot.paramMap.get('country');
    //   regionId = this.route.snapshot.paramMap.get('region');
    // }

    // alert(countryId+" "+regionId);

    // let dd = document.getElementById('country') as HTMLSelectElement;;
    // alert(dd.options.length);
    //   for (let i = 0; i < dd.options.length; i++) {
    //     if (dd.options[i].value === ("" + countryId)) {
    //       dd.selectedIndex = i;
    //       break;
    //     }
    //   }
    
    //alert("Loaded");
    // $("#coverScreen").hide();
  }

  onSelectCountry(country_id: string) {
    let dropDown = document.getElementById("region") as HTMLSelectElement;;
    dropDown.selectedIndex = 0;
    this.regions = [];

    this._diphHttpClientService.getRegionDetails(country_id).subscribe(data => {
      this.regions = data;
    });
  }

  onCountryStateFormSubmit(countryStateFormData: any) {

    if (countryStateFormData.country.length == 0 || countryStateFormData.region.length == 0) {
      alert("Please select valid Country/State");
      return;
    }
    //this.router.navigate(['login', { country: countryStateFormData.country, region: countryStateFormData.region }]);
    this.router.navigate(['district', { country: countryStateFormData.country, region: countryStateFormData.region }]);
  }

}
