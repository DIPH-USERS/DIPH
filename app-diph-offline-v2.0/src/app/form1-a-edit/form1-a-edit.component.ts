import { Component, OnInit, Injectable, Input } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { Observable } from 'rxjs';
import { form1aEdit } from '../model/form1aEdit';

@Component({
  selector: 'app-form1-a-edit',
  templateUrl: './form1-a-edit.component.html',
  styleUrls: ['./form1-a-edit.component.css']
})
export class Form1AEditComponent implements OnInit {

  completeClicked : boolean = false;

  @Input() customer: any;
  public jsonresponse: any;
  loading = true;
  resp: any;
  public verified_by_name_from_Db = [];
  minDate = new Date("12,01,2019");


  user: Observable<any>;


  constructor(location: PlatformLocation, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
    history.pushState(null, null, location.href);
    location.onPopState(() => {
      history.pushState(null, null, location.href);
      return false;
    });
   }



  ngOnInit() {


    this._diphHttpClientService.getVerified_by_names()
      .subscribe(
        data => {
          console.log("data : "+JSON.stringify(data));
          if (data['status'] == "1") {
            this.verified_by_name_from_Db = data['verified_by_name'];
          }
          else {
            alert("Error in retrieving \n'Chairperson names from Server'");
          }


          setTimeout(() => {
            console.log("Hello from setTimeout");

            let district_id: string, cycle_id: string, year: string, user_id: string;

            district_id = "" + sessionStorage.getItem('district');
            cycle_id = "" + sessionStorage.getItem('cycle');
            year = "" + sessionStorage.getItem('year');
            user_id = "" + sessionStorage.getItem('userid');

            this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
              .subscribe(
                data => {
                 // console.log("data = ");
                 // console.log(data);

                  if (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) {
                    alert("No data set for chosen District, Cycle and Year");
                    //console.log("No data set for chosen District, Cycle and Year");
                    this.router.navigate(['/']);
                  }
                  else {

                    this.customer = {};

                    this.date.setValue(data.date_of_verification);

                    this.customer.date_of_verification = data.date_of_verification;

                    this.customer.filled_by = data.filled_by;

                    this.customer.verified_by_name = data.verified_by_name;

                    this.customer.verified_by_name_actual_name = data.verified_by_name_actual_name;

                    this.customer.doc_db_check_id = data.doc_db_check_id;

                    this.customer.large_scale_district_array = data.large_scale_district_array;

                    this.customer.private_sec_array = data.private_sec_array;

                    this.customer.non_health_dept_array = data.non_health_dept_array;

                    this.customer.health_dept_array = data.health_dept_array;

                    this.customer.district_policy_array = data.district_policy_array;

                    this.customer.state_policy_array = data.state_policy_array;

                    if(data.completed == '1')
                      this.customer.completed = true;
                    else if(data.completed == '0')
                    this.customer.completed = false;


                    // Setting values to show in form
                    var date = new Date("" + this.customer.date_of_verification);

                    var new_date = date.getDate();
                    var new_month = (date.getMonth() + 1);
                    var new_year = date.getFullYear();

                    let x: any;

                    if (new_date < 10) {
                      x = "0" + new_date;
                    }
                    else {
                      x = new_date;
                    }

                    let y: any;

                    if (new_month < 10) {
                      y = "0" + new_month;
                    }
                    else {
                      y = new_month;
                    }

                    // let temp1: string;
                    // temp1 = this.customer.verified_by_name_actual_name;

                    // if (temp1 == "0") {
                    //   this.customer.verified_by_name_actual_name = "Others";
                    // } else if (temp1 == "1") {
                    //   this.customer.verified_by_name_actual_name = "Chief Medical Officer of Health(CMOH)";
                    // } else if (temp1 == "2") {
                    //   this.customer.verified_by_name_actual_name = "District Maternity and Child Health Officer(DMCHO)";
                    // } else if (temp1 == "3") {
                    //   this.customer.verified_by_name_actual_name = "District Magistrate(DM)";
                    // } else if (temp1 == "4") {
                    //   this.customer.verified_by_name_actual_name = "Office-In-Charge,Health(OC-Health)";
                    // } else {
                    //   this.customer.verified_by_name_actual_name = "Others";
                    // }

                    this.customer.date_of_verification = x + '/' + y + '/' + new_year;
                    //Setting values completed
                    //Now show fetched values from Server
                    this.loading = false;

                    //console.log("this.customer = ");
                    //console.log(this.customer);


                  }

                },
                error => {
                  console.log(error); alert("Error in fetching data from Server= " + error);
                });


          }, 3000);

        },
        error => {
          console.log(error); alert("Error= " + error);
        });





  }
  //ngOnInit Ends

  onPrint() {
    window.print();
  }

  dynamic_state_policy_array = [];
  dynamic_district_policy_array = [];
  dynamic_health_policy_array = [];
  dynamic_non_health_policy_array = [];
  dynamic_private_sec_policy_array = [];
  dynamic_large_scale_policy_array = [];

  onClickAddRow(val: number) {

    this.dynamic_state_policy_array.push({

      "document_val": "",
      "document_availability": "",
      "document_source": "",
      "doc_db_doc_id": null

    });

  }

  onClickAddhealthRow(val: number) {

    this.dynamic_health_policy_array.push({

      "document_val": "",
      "document_availability": "",
      "document_source": "",
      "doc_db_doc_id": null

    });

  }

  onClickAddnonhealthRow(val: number) {

    this.dynamic_non_health_policy_array.push({

      "document_val": "",
      "document_availability": "",
      "document_source": "",
      "doc_db_doc_id": null

    });

  }



  onClickAddprivatesecRow(val: number) {

    this.dynamic_private_sec_policy_array.push({

      "document_val": "",
      "document_availability": "",
      "document_source": "",
      "doc_db_doc_id": null

    });

  }


  onClickAddlargescaleRow(val: number) {

    this.dynamic_large_scale_policy_array.push({

      "document_val": "",
      "document_availability": "",
      "document_source": "",
      "doc_db_doc_id": null

    });

  }


  onClickAddDistrictRow(val: number) {

    this.dynamic_district_policy_array.push({

      "document_val": "",
      "document_availability": "",
      "document_source": "",
      "doc_db_doc_id": null

    });

  }

  onClickDeleteRow(index: number) {
    this.dynamic_state_policy_array.splice(index, 1);
  }

  onClickDeleteHealthRow(index: number) {
    this.dynamic_health_policy_array.splice(index, 1);
  }

  onClickDeleteNonHealthRow(index: number) {
    this.dynamic_non_health_policy_array.splice(index, 1);
  }

  onClickDeletePrivateSecRow(index: number) {
    this.dynamic_private_sec_policy_array.splice(index, 1);
  }

  onClickDeleteLargeScaleRow(index: number) {
    this.dynamic_large_scale_policy_array.splice(index, 1);
  }

  onClickDeleteDistrictRow(index: number) {
    this.dynamic_district_policy_array.splice(index, 1);
  }


  date = new FormControl('2019-08-22T23:00:00');
  date_2 = new FormControl('2019-08-22T23:00:00');

  date_max = new Date();
  submitted = false;

  /*
  validateSpecialCharsAndNum(val: string) {
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
    return ((val).replace(/[A-Za-z\s]/gi, '').length);
  }
*/
  validate100charactersallowed(val:string){
    if(val != null && val.length <=5000){      
      return false;
    }
    else if(val != null && val.length > 5000){
      return true;
    }
    else if(val == null || (val!= null && val.trim() == "")){
      return true; 
    }
  }

  validateNumbers(val: string) {
    //alert((!!(val).replace(/[\w\s\d]/gi, '').length));
    return (!!(val).replace(/[0-9\s]/gi, '').length);
  }

  allowAlphabetsOnly(event) {
    // alert(event.which);
    if ((event.which > 64 && event.which < 91) || (event.which > 96 && event.which < 123) || (event.which == 32) || (event.which == 0))
      return true;
    else
      return false;
  }

  findvalue(event) {
    // alert(event.which);
    if (event.which > 31 && (event.which < 48 || event.which > 57)) {
      event.stopPropagation();
      return false;
    }
    return true;
  }

  submit() {

   
    this.loading = true;
    //console.log(this.customer);

    this.submitted = true;
    let finalresult = true;

    //alert("this.customer.filled_by = "+this.customer.filled_by+",, "+this.validateSpecialCharsAndNum(this.customer.filled_by));

    if (this.customer.filled_by == '') {
      finalresult = false;
    }/*
    else if (this.validateSpecialCharsAndNum(this.customer.filled_by)) {
      finalresult = false;
    }*/
    else if (this.validate100charactersallowed(this.customer.filled_by)) {
      finalresult = false;
    } 


    if (this.customer.verified_by_name == '') {
      finalresult = false;
    }
    else if (this.customer.verified_by_name == '15' && this.customer.verified_by_name_actual_name == '') {
      finalresult = false;
    }


    for (let j = 0; j < this.customer.state_policy_array.length; j++) {
      if (this.customer.state_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.customer.state_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.customer.state_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.customer.state_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.customer.state_policy_array[j]['document_availability'] == '1' && this.customer.state_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.customer.state_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.customer.state_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if (this.customer.state_policy_array[j]['document_availability'] == '1' &&  this.validateSpecialCharsAndNum(this.customer.state_policy_array[j]['document_source'])  ) {
      //   finalresult = false;
      // } 
      
    }


    for (let j = 0; j < this.dynamic_state_policy_array.length; j++) {  
      if (this.dynamic_state_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.dynamic_state_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.dynamic_state_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.dynamic_state_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_state_policy_array[j]['document_availability'] == '1' && this.dynamic_state_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_state_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.dynamic_state_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if( this.validateSpecialCharsAndNum(this.customer.infra_array[j]['document_details'])){
      //   finalresult = false;
      // }
    }






    for (let j = 0; j < this.customer.district_policy_array.length; j++) {
      if (this.customer.district_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.customer.district_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.customer.district_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.customer.district_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.customer.district_policy_array[j]['document_availability'] == '1' && this.customer.district_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.customer.district_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.customer.district_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if (this.customer.district_policy_array[j]['document_availability'] == '1' &&  this.validateSpecialCharsAndNum(this.customer.district_policy_array[j]['document_source'])  ) {
      //   finalresult = false;
      // } 
    }


    for (let j = 0; j < this.dynamic_district_policy_array.length; j++) {
      if (this.dynamic_district_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.dynamic_district_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.dynamic_district_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.dynamic_district_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_district_policy_array[j]['document_availability'] == '1' && this.dynamic_district_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_district_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.dynamic_district_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if( this.validateSpecialCharsAndNum(this.customer.infra_array[j]['document_details'])){
      //   finalresult = false;
      // }
    }





    for (let j = 0; j < this.customer.health_dept_array.length; j++) {
      if (this.customer.health_dept_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.customer.health_dept_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.customer.health_dept_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.customer.health_dept_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.customer.health_dept_array[j]['document_availability'] == '1' && this.customer.health_dept_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.customer.health_dept_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.customer.health_dept_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if (this.customer.health_dept_array[j]['document_availability'] == '1' &&  this.validateSpecialCharsAndNum(this.customer.health_dept_array[j]['document_source'])  ) {
      //   finalresult = false;
      // } 
    }


    for (let j = 0; j < this.dynamic_health_policy_array.length; j++) {
      if (this.dynamic_health_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.dynamic_health_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.dynamic_health_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.dynamic_health_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_health_policy_array[j]['document_availability'] == '1' && this.dynamic_health_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_health_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.dynamic_health_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if( this.validateSpecialCharsAndNum(this.customer.infra_array[j]['document_details'])){
      //   finalresult = false;
      // }
    }





    for (let j = 0; j < this.customer.non_health_dept_array.length; j++) {
      if (this.customer.non_health_dept_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.customer.non_health_dept_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.customer.non_health_dept_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.customer.non_health_dept_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.customer.non_health_dept_array[j]['document_availability'] == '1' && this.customer.non_health_dept_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.customer.non_health_dept_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.customer.non_health_dept_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if (this.customer.non_health_dept_array[j]['document_availability'] == '1' &&  this.validateSpecialCharsAndNum(this.customer.non_health_dept_array[j]['document_source'])  ) {
      //   finalresult = false;
      // } 
    }


    for (let j = 0; j < this.dynamic_non_health_policy_array.length; j++) {
      if (this.dynamic_non_health_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.dynamic_non_health_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.dynamic_non_health_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.dynamic_non_health_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_non_health_policy_array[j]['document_availability'] == '1' && this.dynamic_non_health_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_non_health_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.dynamic_non_health_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if( this.validateSpecialCharsAndNum(this.customer.infra_array[j]['document_details'])){
      //   finalresult = false;
      // }
    }





    for (let j = 0; j < this.customer.private_sec_array.length; j++) {
      if (this.customer.private_sec_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.customer.private_sec_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.customer.private_sec_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.customer.private_sec_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.customer.private_sec_array[j]['document_availability'] == '1' && this.customer.private_sec_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.customer.private_sec_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.customer.private_sec_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if (this.customer.private_sec_array[j]['document_availability'] == '1' &&  this.validateSpecialCharsAndNum(this.customer.private_sec_array[j]['document_source'])  ) {
      //   finalresult = false;
      // } 
    }


    for (let j = 0; j < this.dynamic_private_sec_policy_array.length; j++) {
      if (this.dynamic_private_sec_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.dynamic_private_sec_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.dynamic_private_sec_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.dynamic_private_sec_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_private_sec_policy_array[j]['document_availability'] == '1' && this.dynamic_private_sec_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_private_sec_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.dynamic_private_sec_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if( this.validateSpecialCharsAndNum(this.customer.infra_array[j]['document_details'])){
      //   finalresult = false;
      // }
    }




    for (let j = 0; j < this.customer.large_scale_district_array.length; j++) {
      if (this.customer.large_scale_district_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.customer.large_scale_district_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.customer.large_scale_district_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.customer.large_scale_district_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.customer.large_scale_district_array[j]['document_availability'] == '1' && this.customer.large_scale_district_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.customer.large_scale_district_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.customer.large_scale_district_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if (this.customer.large_scale_district_array[j]['document_availability'] == '1' &&  this.validateSpecialCharsAndNum(this.customer.large_scale_district_array[j]['document_source'])  ) {
      //   finalresult = false;
      // }  
    }


    for (let j = 0; j < this.dynamic_large_scale_policy_array.length; j++) {
      if (this.dynamic_large_scale_policy_array[j]['document_val'] == '') {
        finalresult = false;
      }/*
      else if (this.validateSpecialCharsAndNum( this.dynamic_large_scale_policy_array[j]['document_val']) ) {
        finalresult = false;
      }*/
      else if(this.validate100charactersallowed( this.dynamic_large_scale_policy_array[j]['document_val']) ) {
        finalresult = false;
      }
      else if (this.dynamic_large_scale_policy_array[j]['document_availability'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_large_scale_policy_array[j]['document_availability'] == '1' && this.dynamic_large_scale_policy_array[j]['document_source'] == '') {
        finalresult = false;
      }
      else if (this.dynamic_large_scale_policy_array[j]['document_availability'] == '1' &&  this.validate100charactersallowed(this.dynamic_large_scale_policy_array[j]['document_source'])  ) {
        finalresult = false;
      }
      // else if( this.validateSpecialCharsAndNum(this.customer.infra_array[j]['document_details'])){
      //   finalresult = false;
      // } 
    }

   

    if (!finalresult) {
      alert("Form invalid");
      this.loading = false;
      return;
    }


    this.customer.state_policy_array = this.customer.state_policy_array.concat(this.dynamic_state_policy_array);
    this.customer.district_policy_array = this.customer.district_policy_array.concat(this.dynamic_district_policy_array);
    this.customer.health_dept_array = this.customer.health_dept_array.concat(this.dynamic_health_policy_array);
    this.customer.non_health_dept_array = this.customer.non_health_dept_array.concat(this.dynamic_non_health_policy_array);
    this.customer.private_sec_array = this.customer.private_sec_array.concat(this.dynamic_private_sec_policy_array);
    this.customer.large_scale_district_array = this.customer.large_scale_district_array.concat(this.dynamic_large_scale_policy_array);

    //alert('SUCCESS!! :-)\n\n' + "\n old array::" + JSON.stringify(this.customer.state_policy_array, null, 4));





    let temp1: string;
    temp1 = this.customer.verified_by_name_actual_name;

    if (temp1 == "Others") {
      this.customer.verified_by_name_actual_name = "0";
    } else if (temp1 == "Chief Medical Officer of Health(CMOH)") {
      this.customer.verified_by_name_actual_name = "1";
    } else if (temp1 == "District Maternity and Child Health Officer(DMCHO)") {
      this.customer.verified_by_name_actual_name = "2";
    } else if (temp1 == "District Magistrate(DM)") {
      this.customer.verified_by_name_actual_name = "3";
    } else if (temp1 == "Office-In-Charge,Health(OC-Health)") {
      this.customer.verified_by_name_actual_name = "4";
    }
   
    let x: any = this.customer;
    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

    this.customer.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    this.completeClicked = true; 

    this._diphHttpClientService.editUpdateForm1ADetails(x, login_district, login_cycle, login_year, login_userid,"1")
      .subscribe(
        data => {
          alert("Successfully submitted!!");
          this.completeClicked = false;
          this.router.navigate(['dashboard/form1aview']);
        },
        error => { this.completeClicked = false; alert("Error= " + error); });
  }

  partialSave(){

    
	  var errorCaught = false;
	
	if (this.customer.filled_by == '') {
      alert("Venue of the meeting not filled");
	    return;
    }
    else if (this.validate100charactersallowed(this.customer.filled_by)) {
      alert("Venue of the meeting can not be more than 5000 characters");
	    return;
    } 


    if (this.customer.verified_by_name == '') {
      alert("Chanirperson of the meeting not filled");
	    return;
    }
    else if (this.customer.verified_by_name == '15' && this.customer.verified_by_name_actual_name == '') {
      alert("Chanirperson of the meeting not filled");
	    return;
    }

	errorCaught = false;

    for (let j = 0; j < this.dynamic_state_policy_array.length; j++) {  
      if (this.dynamic_state_policy_array[j]['document_val'] == '') {
        errorCaught = true;
      }
      else if(this.validate100charactersallowed( this.dynamic_state_policy_array[j]['document_val']) ) {
        errorCaught = true;
      }
    }

	if(errorCaught){
		alert("State Policy expanded but not filled");
		return;
	}

	errorCaught = false;
	
    for (let j = 0; j < this.dynamic_district_policy_array.length; j++) {
      if (this.dynamic_district_policy_array[j]['document_val'] == '') {
        errorCaught = true;
      }
      else if(this.validate100charactersallowed( this.dynamic_district_policy_array[j]['document_val']) ) {
        errorCaught = true;
      }      
    }

	if(errorCaught){
		alert("District Policy expanded but not filled");
		return;
	}

	errorCaught = false;
	
    for (let j = 0; j < this.dynamic_health_policy_array.length; j++) {
      if (this.dynamic_health_policy_array[j]['document_val'] == '') {
        errorCaught = true;
      }
      else if(this.validate100charactersallowed( this.dynamic_health_policy_array[j]['document_val']) ) {
        errorCaught = true;
      }
    }

	if(errorCaught){
		alert("Health department Policy expanded but not filled");
		return;
	}

	errorCaught = false;

    for (let j = 0; j < this.dynamic_non_health_policy_array.length; j++) {
      if (this.dynamic_non_health_policy_array[j]['document_val'] == '') {
        errorCaught = true;
      }
      else if(this.validate100charactersallowed( this.dynamic_non_health_policy_array[j]['document_val']) ) {
        errorCaught = true;
      }
    }

	if(errorCaught){
		alert("Non-Health department Policy expanded but not filled");
		return;
	}

	errorCaught = false;

    for (let j = 0; j < this.dynamic_private_sec_policy_array.length; j++) {
      if (this.dynamic_private_sec_policy_array[j]['document_val'] == '') {
        errorCaught = true;
      }
      else if(this.validate100charactersallowed( this.dynamic_private_sec_policy_array[j]['document_val']) ) {
        errorCaught = true;
      }
    }

	if(errorCaught){
		alert("Private Sectort Policy expanded but not filled");
		return;
	}

	errorCaught = false;

    for (let j = 0; j < this.dynamic_large_scale_policy_array.length; j++) {
      if (this.dynamic_large_scale_policy_array[j]['document_val'] == '') {
        errorCaught = true;
      }
      else if(this.validate100charactersallowed( this.dynamic_large_scale_policy_array[j]['document_val']) ) {
        errorCaught = true;
      }
    }
	
	if(errorCaught){
		alert("Large Scale District Level Surveys Policy expanded but not filled");
		return;
  }
  
      this.customer.state_policy_array = this.customer.state_policy_array.concat(this.dynamic_state_policy_array);
      this.customer.district_policy_array = this.customer.district_policy_array.concat(this.dynamic_district_policy_array);
      this.customer.health_dept_array = this.customer.health_dept_array.concat(this.dynamic_health_policy_array);
      this.customer.non_health_dept_array = this.customer.non_health_dept_array.concat(this.dynamic_non_health_policy_array);
      this.customer.private_sec_array = this.customer.private_sec_array.concat(this.dynamic_private_sec_policy_array);
      this.customer.large_scale_district_array = this.customer.large_scale_district_array.concat(this.dynamic_large_scale_policy_array);

      let temp1: string;
      temp1 = this.customer.verified_by_name_actual_name;

      if (temp1 == "Others") {
        this.customer.verified_by_name_actual_name = "0";
      } else if (temp1 == "Chief Medical Officer of Health(CMOH)") {
        this.customer.verified_by_name_actual_name = "1";
      } else if (temp1 == "District Maternity and Child Health Officer(DMCHO)") {
        this.customer.verified_by_name_actual_name = "2";
      } else if (temp1 == "District Magistrate(DM)") {
        this.customer.verified_by_name_actual_name = "3";
      } else if (temp1 == "Office-In-Charge,Health(OC-Health)") {
        this.customer.verified_by_name_actual_name = "4";
      }
    
      let x: any = this.customer;
      var date2 = new Date("" + this.date.value);

      var new_date2 = date2.getDate();
      var new_month2 = (date2.getMonth() + 1);
      var new_year2 = date2.getFullYear();

      x.date_of_meeting = new_year2 + "-" + new_month2 + "-" + new_date2;

      this.customer.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

      let login_district = sessionStorage.getItem('district');
      let login_cycle = sessionStorage.getItem('cycle');
      let login_year = sessionStorage.getItem('year');//userid
      let login_userid = sessionStorage.getItem('userid');

      this.completeClicked = true; 

  this._diphHttpClientService.editUpdateForm1ADetails(x, login_district, login_cycle, login_year, login_userid,"0")
    .subscribe(
      data => {
        alert("Successfully submitted!!");
        this.completeClicked = false;
        this.router.navigate(['dashboard/form1aview']);
      },
      error => { this.completeClicked = false; alert("Error= " + error); });

  }

  get example() { return JSON.stringify(this.customer) };

  set_verify_by_name(val) {

    console.log("val = ");
    console.log(val);
    alert("'" + val + "'");
  }

  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }

}
