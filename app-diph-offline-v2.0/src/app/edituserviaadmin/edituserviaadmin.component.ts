import { Component, OnInit, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Country } from '../model/country';
import { District } from '../model/district';
import { State } from '../model/state';
import { Region } from '../model/region';

@Component({
  selector: 'app-edituserviaadmin',
  templateUrl: './edituserviaadmin.component.html',
  styleUrls: ['./edituserviaadmin.component.css']
})
export class EdituserviaadminComponent implements OnInit {

  @Input() customer: any = { district_id: [], cycle_id: [], years: [] };
  public countries = new Array<Country>();
  public regions = new Array<Region>();
  public states = new Array<State>();
  public districts = new Array<District>();
  public cycles = [];
  public years = [];
  // public district_arr = [];
  // public cycle_arr = [];
  // public year_arr = [];

  constructor(private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {



    this._diphHttpClientService.getCountryDetails().subscribe(data => {
      this.countries = data;

      this.customer.country_id = "65";
      setTimeout(() => {
        // $("#country option[value=65]").attr('selected', 'selected');
        // $("#country").val("65");
        // try {
        //   $("#country option[value=65]").attr('[attr.disabled]', 'true');
        // } catch (error) {

        // }

        let dropDown = document.getElementById("region") as HTMLSelectElement;;
        dropDown.selectedIndex = 0;
        dropDown.value = "";
        this.regions = [];
        this.states = [];
        this.districts = [];

        let editinguserobj = JSON.parse(sessionStorage.getItem('editinguser'));


        this.customer.username = editinguserobj.user_nm;
        this.customer.password = editinguserobj.user_pass;
        this.customer.email = editinguserobj.emailId;

        // $("#username").val(editinguserobj.user_nm);
        // $("#user_password").val(editinguserobj.user_pass);
        // $("#email").val(editinguserobj.emailId);

        let district_arr: [] = editinguserobj.district;
        let cycle_arr: [] = editinguserobj.cycle;
        let year_arr: [] = editinguserobj.year;

        this.regions = JSON.parse(sessionStorage.getItem('regions'));


        this._diphHttpClientService.getStateofDistrictDetails(district_arr).subscribe(data => {
          
          this.states = data.statesList;

          let temp_districts = data.districtsList;
          temp_districts = data.districtsList;

         

          setTimeout(() => {

            let sel_state = "0";

            for (let i = 0; i < temp_districts.length; i++) {

              let tempobj: any = temp_districts[i];

              sel_state = tempobj.state_id;

              for (let j = 0; j < district_arr.length; j++) {
                if (district_arr[j] == tempobj.district_id) {
                  this.customer.district_id.push(district_arr[j]);
                  // $('#district option[ng-reflect-value='+district_arr[j]+']').attr('selected', 'selected');
                }
              }
            }

            this.districts = temp_districts;
            



            for (let z = 0; z < this.states.length; z++) {
              let tempobj: any = this.states[z];
              if (sel_state == tempobj.cs_id) {
                this.customer.state_id = sel_state;

                this.customer.region_id = tempobj.region_id;
                // alert(JSON.stringify(tempobj,null,4));


                // $('#state option[ng-reflect-value='+sel_state+']').attr('selected', 'selected');
              }
            }



            // alert(JSON.stringify(this.customer,null,4)); 

          }, 2000);
        });



        this._diphHttpClientService.getCycleDetails().subscribe(data => {

          let temp_cycles = data;
          temp_cycles = data;
          setTimeout(() => {

            for (let i = 0; i < temp_cycles.length; i++) {

              let tempobj = temp_cycles[i];

              for (let j = 0; j < cycle_arr.length; j++) {

                if (tempobj.cycle_id == cycle_arr[j]) {
                  this.customer.cycle_id.push(tempobj.cycle_id);
                  // $('#cycle option[ng-reflect-value='+tempobj.cycle_id+']').attr('selected', 'selected');
                }

              }

            }

            this.cycles = temp_cycles;


            // alert(JSON.stringify(this.customer,null,4));

          }, 2000);
        });

        let temp_years = [{ year_id: "2020", year_name: "2020" }, { year_id: "2021", year_name: "2021" }, { year_id: "2022", year_name: "2022" }, { year_id: "2023", year_name: "2023" }, { year_id: "2024", year_name: "2024" }, { year_id: "2025", year_name: "2025" }];

        // this.years = [{ year_id: "2020", year_name: "2020" }, { year_id: "2021", year_name: "2021" }, { year_id: "2022", year_name: "2022" }];

       

        setTimeout(() => {

          for (let j = 0; j < temp_years.length; j++) {
            let yearobj = temp_years[j];

            for (let pos = 0; pos < year_arr.length; pos++) {

             
              if (yearobj.year_id == year_arr[pos]) {

                this.customer.years.push(year_arr[pos]);

                
                //$("#year option[ng-reflect-value='2022']").prop("selected", true);

                // $('#year option:contains('+year_arr[pos]+')').attr('selected', 'selected');

                // $("#year option[ng-reflect-value='" + this.year_arr[pos] + "']").attr('selected', 'selected');
              }
              
            }
          }

          this.years = temp_years;

          this.customer.can_create = editinguserobj.can_create;
          this.customer.can_edit = editinguserobj.can_edit;
          this.customer.can_view = editinguserobj.can_view;
          this.customer.can_delete = editinguserobj.can_delete;



          try {
            // $('#createUser option[ng-reflect-value='+editinguserobj.can_create+']').attr('selected', 'selected');


            // $('#editUser option[ng-reflect-value='+editinguserobj.can_edit+']').attr('selected', 'selected');


            // $('#viewUser option[ng-reflect-value='+editinguserobj.can_view+']').attr('selected', 'selected');


            // $('#deleteUser option[ng-reflect-value='+editinguserobj.can_delete+']').attr('selected', 'selected');

          } catch (error) {

          }


          try {
            $("#coverScreen").hide();
          } catch (error) {

          }

        }, 2000);

      });
    });

  }

  allowyears(val: string) {
    // alert("val = "+val+", :: cycleid == "+JSON.stringify(this.customer.cycle_id,null,4));
    if (this.customer.years.includes(val) == true) {
      return true;
    }
    else {
      return false;
    }
  }


  allowcycles(val: number) {
    // alert("val = "+val+", :: cycleid == "+JSON.stringify(this.customer.cycle_id,null,4));
    if (this.customer.cycle_id.includes(val) == true) {
      return true;
    }
    else {
      return false;
    }
  }


  allowdistricts(val: string) {

    val = "" + val;    


    if (this.customer.district_id.includes(val) == true) {
      return true;
    }
    else {
      return false;
    }

  }


  isGreaterThan5(element, index, array) {

    for (let i = 0; i < this.districts.length; i++) {

      let tempobj: any = this.districts[i];

      if (tempobj.district_id == element) {
        return true;
      }
      else {
        return false;
      }
    }
    return element;
  }



  ngAfterViewInit() {
    //alert("Loaded");
    // $("#coverScreen").hide();
  }

  onSelectRegion(region_id: string) {
    let dropDown = document.getElementById("state") as HTMLSelectElement;;
    dropDown.selectedIndex = 0;
    this.states = [];
    this.districts = [];
    this.customer.state_id = "";
    this.customer.district_id = [];

    this._diphHttpClientService.getStateDetails(region_id).subscribe(data => {
      this.states = data;
    });
  }

  onSelectState(state_id: string) {

    this.customer.state_id = "" + state_id;

    let dropDown = document.getElementById("district") as HTMLSelectElement;
    dropDown.selectedIndex = 0;
    this.districts = [];

    this._diphHttpClientService.getDistrictDetails(state_id).subscribe(data => {
      this.districts = data;
    });
  }

  onSelectDistrict(district_id: any) {

    // let x:any = document.getElementById('district') as HTMLSelectElement;
    //  let txt = "";
    //  let val = "";
    //  this.customer.district_id = [];
    //  for (let i = 0; i < x.length; i++) {
    //      txt +=x[i].text + ",";
    //      val +=x[i].value + ",";
    //      this.customer.district_id.push(x[i].value);
    //   }

    let selectElement = document.getElementById('district') as HTMLSelectElement;
    let selectedValues = Array.from(selectElement.selectedOptions)
      .map(option => option.value); // make sure you know what '.map' does

    // you could also do: selectElement.options

    this.customer.district_id = selectedValues;
    //alert(JSON.stringify(this.customer,null,4));


    let dropDown = document.getElementById("cycle") as HTMLSelectElement;
    dropDown.selectedIndex = 0;
    this.cycles = [];

    this._diphHttpClientService.getCycleDetails().subscribe(data => {
      this.cycles = data;
    });
  }


  onSelectCycle(cycle_id: string) {

    let selectElement = document.getElementById('cycle') as HTMLSelectElement;
    let selectedValues = Array.from(selectElement.selectedOptions)
      .map(option => option.value); // make sure you know what '.map' does

    // you could also do: selectElement.options

    this.customer.cycle_id = selectedValues;
    //alert(JSON.stringify(this.customer,null,4));

    let dropDown = document.getElementById("year") as HTMLSelectElement;
    dropDown.selectedIndex = 0;
    this.years = [];

    this.years = [{ year_id: "2020", year_name: "2020" }, { year_id: "2021", year_name: "2021" }, { year_id: "2022", year_name: "2022" }, { year_id: "2023", year_name: "2023" }, { year_id: "2024", year_name: "2024" }, { year_id: "2025", year_name: "2025" }];
  }

  onSelectYear(year: string) {
    let selectElement = document.getElementById('year') as HTMLSelectElement;
    let selectedValues = Array.from(selectElement.selectedOptions)
      .map(option => option.value); // make sure you know what '.map' does

    // you could also do: selectElement.options

    this.customer.years = selectedValues;
    //alert(JSON.stringify(this.customer,null,4));
  }

  ValidateEmail(mail) {
    if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail)) {
      return (false)
    }
    alert("You have entered an invalid email address!")
    return (true)
  }


  onEditUserSubmit() {

    let dst = this.customer.district_id;

    let cyc = this.customer.cycle_id;

    let ye = this.customer.years;


    this.customer.district_id = dst.reduce(function (a, b) {
      if (a.indexOf(b) < 0) a.push(b);
      return a;
    }, []);

    this.customer.cycle_id = cyc.reduce(function (a, b) {
      if (a.indexOf(b) < 0) a.push(b);
      return a;
    }, []);

    this.customer.years = ye.reduce(function (a, b) {
      if (a.indexOf(b) < 0) a.push(b);
      return a;
    }, []);

    //   var names = ["Mike","Matt","Nancy","Adam","Jenny","Nancy","Carl"];

    // var uniq = names.reduce(function(a,b){
    //     if (a.indexOf(b) < 0 ) a.push(b);
    //     return a;
    //   },[]);

    


    let editUser_form: any = {};    

    if (this.customer.password != "" && this.customer.password != null && this.customer.password.length < 4) {
      alert("Please Enter Password");
      return;
    }

    if (this.customer.email == null || this.customer.email == "") {
      alert("Please Enter Email");
      return;
    }

    if (this.ValidateEmail(this.customer.email)) {
      return;
    }

    if (this.customer.state_id == "" || this.customer.state_id == null) {
      alert("Please select any Zone"); return;
    }


    if (this.customer.district_id.length == 0) {
      alert("Please select any Woreda"); return;
    }

  
    if (this.customer.cycle_id.length == 0) {
      alert("Please select any Cycle"); return;
    }


    if (this.customer.years.length == 0) {
      alert("Please select any Year"); return;
    }  

    
    editUser_form.createUser = this.customer.can_create;
    editUser_form.deleteUser = this.customer.can_delete;
    editUser_form.editUser = this.customer.can_edit;
    editUser_form.viewUser = this.customer.can_view;

    editUser_form.username = this.customer.username;
    editUser_form.user_password = this.customer.password;
    editUser_form.email = this.customer.email;

    editUser_form.cycle = this.customer.cycle_id;
    editUser_form.district = this.customer.district_id;
    editUser_form.year = this.customer.years;



    this._diphHttpClientService.updateUser(editUser_form).subscribe(data => { 
         alert("User Updated Successfully!!!");     

      this.router.navigate(['/dashboard/manage-user']);;  
    }); 


  }
  previouspage() {
    let ans = confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    if (ans) {
      this.router.navigate(['/dashboard/manage-user']);
    }
  }

}
