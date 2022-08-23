import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { District } from '../model/district';
import { Country } from '../model/country';
import { State } from '../model/state';
import { Region } from '../model/region';

@Component({
  selector: 'app-manage-user',
  templateUrl: './manage-user.component.html',
  styleUrls: ['./manage-user.component.css']
})
export class ManageUserComponent implements OnInit {

  public countries = new Array<Country>();
  public regions = new Array<Region>();
  public states = new Array<State>();
  public districts = new Array<District>();
  public cycles =[];
  public years =[];

  UsersResponseObj:any;
  deleteResponseObj:any;
  newUser_form: any;

  constructor( private router: Router, private route: ActivatedRoute,  private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {

    let userStatus = sessionStorage.getItem('userStatus');

    if("0" == userStatus){
      this.router.navigate(['/dashboard']);
    }
    else if("1" == userStatus){
      //No action
    }
    else{
      this.router.navigate(['']);
    }

    let user = sessionStorage.getItem('username');
    if (user == null || user.length == 0) {
      this.router.navigate(['login']);
    }

    let countryId: string = sessionStorage.getItem('countryId');
    let regionId: string = sessionStorage.getItem('regionId');
    let stateid: string = sessionStorage.getItem('zoneId');
    let district: string = sessionStorage.getItem('district');


    this._diphHttpClientService.getCountryDetails().subscribe(data => {
      this.countries = data;

      setTimeout(() => {
        $("#country option[value=65]").attr('selected', 'selected');
        $("#country").val("65");

        try {
          $("#country option[value=65]").attr(' [attr.disabled]', 'true');  
        } catch (error) {          
        }
        

        let dropDown = document.getElementById("region") as HTMLSelectElement;;
      dropDown.selectedIndex = 0;
      dropDown.value="";
      this.regions = [];
      this.states = [];
      this.districts = [];

    this._diphHttpClientService.getRegionDetails("65").subscribe(data => {
      this.regions = data; 
      sessionStorage.setItem('regions',JSON.stringify(data));
    });
        },
          2000);

      

    //   $('select[name^="country"] option:selected').attr("selected",null);

    // $('select[name^="country"] option[value="65"]').attr("selected","selected");
    });


    

    this.showAllUsersOfDistrict();
    
  }


  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['/dashboard']);
    }
    
  }

  edituserviaadmin(val:string){
    
    
    for(let i=0;i<this.UsersResponseObj.userList.length;i++){
      let tempobj = this.UsersResponseObj.userList[i];

      if(tempobj.user_nm == val){
        sessionStorage.setItem('editinguser',JSON.stringify(tempobj));
      }
      
    }


    this.router.navigate(['/dashboard/edituserviaadmin']);
  }

  onSelectCountry(country_id: string) {
    let dropDown = document.getElementById("region") as HTMLSelectElement;;
      dropDown.selectedIndex = 0;
      dropDown.value="";
      this.regions = [];
      this.states = [];
      this.districts = [];

    this._diphHttpClientService.getRegionDetails(country_id).subscribe(data => {
      this.regions = data; 
    });
  }

  onSelectRegion(region_id: string) {
    let dropDown = document.getElementById("state") as HTMLSelectElement;;
      dropDown.selectedIndex = 0;
      this.states = [];
      this.districts = [];

    this._diphHttpClientService.getStateDetails(region_id).subscribe(data => {
      this.states = data; 
    });
  }

  onSelectState(state_id: string) {
    let dropDown = document.getElementById("district") as HTMLSelectElement;
      dropDown.selectedIndex = 0;
      this.districts = [];

    this._diphHttpClientService.getDistrictDetails(state_id).subscribe(data => {
      this.districts = data; 
    });
  }

  onSelectDistrict(district_id: string) {
    let dropDown = document.getElementById("cycle") as HTMLSelectElement;
      dropDown.selectedIndex = 0;
      this.cycles = [];

    this._diphHttpClientService.getCycleDetails().subscribe(data => {
      this.cycles = data; 
    });


  }

  onSelectCycle(cycle_id: string) {
    let dropDown = document.getElementById("year") as HTMLSelectElement;
      dropDown.selectedIndex = 0;
      this.years = [];

      this.years = [{year_id:"2020",year_name:"2020"},{year_id:"2021",year_name:"2021"},{year_id:"2022",year_name:"2022"}];
  }

   ValidateEmail(mail) 
{
 if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(mail))
  {
    return (false)
  }
    alert("You have entered an invalid email address!")
    return (true)
}


  onNewUserSubmit(newUser_form: any) {

    //alert(JSON.stringify(newUser_form,null,4));

    if (newUser_form.username == null || newUser_form.email == null || newUser_form.user_password == null || newUser_form.username.length == 0 || newUser_form.user_password.length == 0 ) {
      alert("Please Enter Username/Password to create new user");      
      return;
    }

    if(this.ValidateEmail(newUser_form.email)){
      return;
    }

    if (newUser_form.username.length < 4 || newUser_form.user_password.length < 4) {
      alert("Username/Password must be of 4 letters or above");      
      return;
    }

    newUser_form.country = "65";
    newUser_form.userStatus = "0";

    if(newUser_form.country == null || newUser_form.region == null || newUser_form.state == null || newUser_form.district == null || newUser_form.country.length == 0 || newUser_form.region.length == 0 || newUser_form.state.length == 0 || newUser_form.district.length == 0 || newUser_form.cycle.length == 0  || newUser_form.year.length == 0){
      alert("Please Enter Country/Region/Province/Woreda/Cycle/Year to create new user");      
      return;
    }


    // if (newUser_form.createUser  == null || newUser_form.editUser  == null || newUser_form.viewUser  == null || newUser_form.deleteUser  == null ||  newUser_form.createUser.length == 0 || newUser_form.editUser.length == 0 || newUser_form.viewUser.length == 0 || newUser_form.deleteUser.length == 0) {
    //   alert("Please Select Priviliges of user (Yes/No)");      
    //   return;
    // }

    let user = sessionStorage.getItem('username');
    let district = sessionStorage.getItem('district');

    
    
    this._diphHttpClientService.createUser(newUser_form, user, district).subscribe(data => {     
      if(data.status == "created"){
        alert("New User Created");
        this.showAllUsersOfDistrict();
      }
      else if(data.status == "exists")
        alert("User Already Created");        
      else alert("User can not be created");      
   
      $("#reset_user_form").click();
       
      // this.regions = [];
      $('#region option:selected').remove();
      let tempstr= "<option value='' selected disabled>Select Region</option>"+($("#region").html());
      $("#region").html(tempstr);
      this.states = [];
      this.districts = [];
      this.cycles = [];
      this.years = [];
     
      this.router.navigate(['/dashboard/manage-user']);;  
    }); 
   

  }

 showAllUsersOfDistrict(){

  let user = sessionStorage.getItem('username');
    let district = sessionStorage.getItem('district');    

  this._diphHttpClientService.getAllUsersOfDistrict(user, district)
      .subscribe(
        data => {        
         this.UsersResponseObj =  data;

        //  alert(JSON.stringify(this.UsersResponseObj,null,100));
         this.UsersResponseObj.userList = this.UsersResponseObj.userList.filter(function( obj ) {
          return obj.user_status != '1';
      });;         
        },
        error => {
          alert("Error= " + error);
        });   
  }

  deleteUser(username:string){

   let result = confirm("Do you really want to delete user ? ");
   let loggeduser = sessionStorage.getItem('username');  

   if(result){
    this._diphHttpClientService.deleteUser(username,loggeduser)
      .subscribe(
        data => {        
         this.deleteResponseObj =  data;  
         if(data.status == "deleted"){
          alert("User has been deleted");
          this.showAllUsersOfDistrict();
        }else{
          alert("User can not be deleted deleted");          
        }       
        },
        error => {
          alert("Error= " + error);
        });
      }
 } 

}
