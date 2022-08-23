import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-changepassword',
  templateUrl: './changepassword.component.html',
  styleUrls: ['./changepassword.component.css']
})
export class ChangepasswordComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    //alert("Loaded");
    $("#coverScreen").hide();
  }

  previouspage() {    
    this.router.navigate(['/dashboard']);
  }

  onchangePassword(changepassword_form:any){
    if(changepassword_form.old_password == null || changepassword_form.old_password == ""){
      alert("Please enter a value in old password");
      return;
    }

    if(changepassword_form.new_password == null || changepassword_form.new_password == ""){
      alert("Please enter a value in new password");
      return;
    }

    if(changepassword_form.confirm_password == null || changepassword_form.confirm_password == ""){
      alert("Please enter a value in confirm new password");
      return;
    }

    if(changepassword_form.confirm_password == changepassword_form.new_password){

      let username = sessionStorage.getItem('username');
      changepassword_form.username = username;

      try {
        $("#coverScreen").show();
      } catch (error) {      
      }
      this._diphHttpClientService.changePassword(changepassword_form).subscribe(data => {

        if(data.result == "200"){
          try {
            $("#coverScreen").hide();
          } catch (error) {          
          } 
          alert("Password Changed  Successfully!!");
          sessionStorage.removeItem('username');
          sessionStorage.clear();
          this.router.navigate(['/country']);
        }
        else{
          alert(data.message);
          try {
            $("#coverScreen").hide();
          } catch (error) {      
          }
        }
  
      });

    }
    else{
      alert("New Password and confirm password don't match!!");
      return;
    }

  }

}
