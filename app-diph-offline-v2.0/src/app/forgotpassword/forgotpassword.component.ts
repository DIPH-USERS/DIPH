import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';

@Component({
  selector: 'app-forgotpassword',
  templateUrl: './forgotpassword.component.html',
  styleUrls: ['./forgotpassword.component.css']
})
export class ForgotpasswordComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute, private _diphHttpClientService: DiphHttpClientService) { }

  ngOnInit() {
    this.Captcha(); 
  }

  previouspage() {

    //countryId=65;regionId=3;zoneId=20;district=3;cycle=1;year=2020

    

    // countryId = sessionStorage.getItem('countryId');

    // regionId = sessionStorage.getItem('regionId');



    // if (countryId == null || regionId == null) {
    //   countryId = this.route.snapshot.paramMap.get('countryId');
    //   regionId = this.route.snapshot.paramMap.get('regionId');
    // }

    let countryId = this.route.snapshot.paramMap.get('countryId');
    let  regionId = this.route.snapshot.paramMap.get('regionId');
    let  zoneId = this.route.snapshot.paramMap.get('zoneId');
    let  district = this.route.snapshot.paramMap.get('district');
    let  cycle = this.route.snapshot.paramMap.get('cycle');
    let  year = this.route.snapshot.paramMap.get('year');

    this.router.navigate(['login', { countryId: countryId, regionId: regionId, zoneId: zoneId, district: district, cycle: cycle, year: year }]);

    
    //this.router.navigate(['/country']);
  }

  ngAfterViewInit() {
    //alert("Loaded");
    $("#coverScreen").hide();
  }

  onforgotPassword(forgotpassword_form:any){ 
   

    if(forgotpassword_form.email == null || forgotpassword_form.email == ""){
      alert("Please Enter a valid userid");
      return;
    }

    

    if($("#txtInput").val() == "" || $("#txtInput").val() == null){
      alert("Please enter matching captcha value..");
      return;
    }
    else{
      if(this.ValidCaptcha()){

      }else{
        alert("Captcha not matched!\nTry again");
        this.Captcha();
        return;
      }
    }

    try {
      $("#coverScreen").show();
    } catch (error) {      
    }

    this._diphHttpClientService.forgotPassword(forgotpassword_form.email).subscribe(data => {

      if(data.result == "200"){
        try {
          $("#coverScreen").hide();
        } catch (error) {          
        }
        alert("Message send successfully!!");

        sessionStorage.removeItem('username');
        sessionStorage.clear();
        this.router.navigate(['/country']);
      }
      else{
        alert(data.message);
        this.Captcha();
        try {
          $("#coverScreen").hide();
        } catch (error) {
          
        }
      }

    });
  }


  Captcha() {
    let alpha = new Array('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
        'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
        'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
        'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4',
        '5', '6', '7', '8', '9');
    let i;
    for (i = 0; i < 6; i++) {
      var a = alpha[Math.floor(Math.random() * alpha.length)];
      var b = alpha[Math.floor(Math.random() * alpha.length)];
      var c = alpha[Math.floor(Math.random() * alpha.length)];
      var d = alpha[Math.floor(Math.random() * alpha.length)];
      var e = alpha[Math.floor(Math.random() * alpha.length)];
      var f = alpha[Math.floor(Math.random() * alpha.length)];
      var g = alpha[Math.floor(Math.random() * alpha.length)];
    }
    let code = a + ' ' + b + ' ' + ' ' + c + ' ' + d ;
    // document.getElementById("mainCaptcha").innerHTML = code
    // document.getElementById("mainCaptcha").value = code

    $("#mainCaptcha").html(code);
    $("#mainCaptcha").val(code);
  }
  ValidCaptcha() {
    // var string1 = this.removeSpaces(document.getElementById('mainCaptcha').value);
    // var string2 = this.removeSpaces(document.getElementById('txtInput').value);
    let string1 = this.removeSpaces($("#mainCaptcha").val());
    let string2 = this.removeSpaces($("#txtInput").val());
    if (string1 == string2) {
      //alert("true");
      return true;
    } else {
      //alert("false");
      return false;
    }
  }
  removeSpaces(str:any) {
    return str.split(' ').join('');
  }

}
