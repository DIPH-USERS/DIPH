import { Component, OnInit, Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import form1aModel from '../model/form1aModel';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { MatDialog } from '@angular/material';
import { Form1bModalDialogComponent } from '../form1b-modal-dialog/form1b-modal-dialog.component';
import { ThrowStmt } from '@angular/compiler';

@Component({
  selector: 'app-form1b',
  templateUrl: './form1b.component.html',
  styleUrls: ['./form1b.component.css']
})
export class Form1bComponent implements OnInit {

  completeClicked : boolean = false;

  form1aobj: form1aModel = new form1aModel("", []);
  submitted = false;
  savedform = false;
  public serverjsonresponse: any;
  selected_indi: any;
  selectDefault: string = "";
  form1a_all_doc_arr: any = [];
  public verified_by_name_from_Db = [];
  minDate = new Date("12,01,2019");
  theme_header: string = "";
  populationDesity = new Number(0.0);
  numberOfWomen = new Number(0.0);
  numberOfChildren = new Number(0.0);
  numberOfWomenConversionFactor = 1;
  numberOfChildrenConversionFactor = 1;


  dynamicForm: FormGroup;

  date = new FormControl(new Date());
  date_2 = new FormControl(new Date());
  date_max = new Date();
  sanctioned_year: string = new Date().getFullYear() + "";

  constructor(location: PlatformLocation, public dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) {
    history.pushState(null, null, location.href);
    location.onPopState(() => {
      history.pushState(null, null, location.href);
      return false;
    });

    let user = sessionStorage.getItem('username');
    if (user == null || user.length == 0) {
      this.router.navigate(['login']);
    }

    this.submitted = false;
    this.form1aobj = new form1aModel("", []);

  }

  proceedIfForm1AIsFilled(): boolean {

    let region_id:string, district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');
    region_id = "" + sessionStorage.getItem('regionId');

    this._diphHttpClientService.getSavedForm1ADetails(district_id, cycle_id, year, user_id)
      .subscribe(
        data => {
                  
          if ( (data.date_of_verification == null && data.filled_by == null && data.verified_by_name == null) || 
               (data.date_of_verification != null && data.filled_by != null && data.verified_by_name != null && data.completed == '0') ) {
            alert("Please Fill Form1A to Fill Form1B");
            console.log("Please Fill Form1A to Fill Form1B");
            console.log("No data set for chosen District(Current District : " + district_id + "),\n Cycle(Current Cycle : " + cycle_id + ") and Year(Current Year : " + year + ")");
            this.router.navigate(['/dashboard']);
            return false;
          }
          console.log("data.state_policy_array : "+JSON.stringify(data.state_policy_array));
          let arr1 = [];
          for (let i = 0; i < data.state_policy_array.length; i++) {
            let tempobj = data.state_policy_array[i];

            arr1.push(tempobj);
          }

          let arr2 = [];
          for (let i = 0; i < data.district_policy_array.length; i++) {
            let tempobj = data.district_policy_array[i];

            arr2.push(tempobj);
          }

          let arr3 = [];
          for (let i = 0; i < data.health_dept_array.length; i++) {
            let tempobj = data.health_dept_array[i];

            arr3.push(tempobj);
          }

          let arr4 = [];
          for (let i = 0; i < data.non_health_dept_array.length; i++) {
            let tempobj = data.non_health_dept_array[i];

            arr4.push(tempobj);
          }

          let arr5 = [];
          for (let i = 0; i < data.private_sec_array.length; i++) {
            let tempobj = data.private_sec_array[i];

            arr5.push(tempobj);
          }

          let arr6 = [];
          for (let i = 0; i < data.large_scale_district_array.length; i++) {
            let tempobj = data.large_scale_district_array[i];

            arr6.push(tempobj);
          }

          arr1 = arr1.concat(arr2);

          arr1 = arr1.concat(arr3);

          arr1 = arr1.concat(arr4);

          arr1 = arr1.concat(arr5);

          arr1 = arr1.concat(arr6);

          this.form1a_all_doc_arr = this.form1a_all_doc_arr.concat(arr1);

          this._diphHttpClientService.getCurrentRegionDetails(region_id)
          .subscribe(
            dataRegion => {
                            
              if(dataRegion.reproductive_age_women_cfactor != null || dataRegion.reproductive_age_women_cfactor != '')
                  this.numberOfWomenConversionFactor = parseFloat(dataRegion.reproductive_age_women_cfactor);
              if(dataRegion.under_five_children_cfactor != null || dataRegion.under_five_children_cfactor != '')
                  this.numberOfChildrenConversionFactor = parseFloat(dataRegion.under_five_children_cfactor);
            },
            error => {
              console.log(error); alert("Error to get current region = " + error);
            });


          //alert(JSON.stringify(this.form1a_all_doc_arr,null,4));
        },
        error => {
          console.log(error); alert("Error= " + error);
        });

    return true;
  }

  ngOnInit() {





    this._diphHttpClientService.getVerified_by_names()
      .subscribe(
        data => {

          if (data['status'] == "1") {
            this.verified_by_name_from_Db = data['verified_by_name'];
          }
          else {
            alert("Error in retrieving \n'Chairperson names from Server'");
          }

        },
        error => {
          console.log(error); alert("Error= " + error);
        });


    this.dynamicForm = this.formBuilder.group({
      date_of_verification: ['2019-10-03'],//2019-09-09
      // filled_by: new FormControl(),
      //filled_by: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      filled_by: ['', [Validators.required,  Validators.maxLength(5000) ]],
      // verified_by_name: new FormControl(),
      verified_by_name: ['', Validators.required],
      verified_by_other_actual_name: new FormControl(),
      // total_area: new FormControl(),
      total_area: ['', [Validators.required]],    
      // total_area_demogra_id: new FormControl(),
      total_area_demogra_id: ['', Validators.required],
      // total_area_others: new FormControl(),
      total_area_others: ['', [Validators.required]],
      // total_pop: new FormControl(),
      total_pop: ['', Validators.required],
      // total_pop_demogra_id: new FormControl(),
      total_pop_demogra_id: ['', Validators.required],
      // total_pop_others: new FormControl(),
      total_pop_others: ['', [Validators.required]],
      // num_women_in_reproductive_age_15_49_y: new FormControl(),
      num_women_in_reproductive_age_15_49_y: ['', Validators.required],
      // num_women_in_reproductive_age_15_49_y_source: new FormControl(),
      num_women_in_reproductive_age_15_49_y_source: ['', Validators.required],
      // num_women_in_reproductive_others: new FormControl(),
      num_women_in_reproductive_others: ['', [Validators.required]],
      // num_child_under_5_y: new FormControl(),
      num_child_under_5_y: ['', Validators.required],
      // num_child_under_5_y_demogra_id: new FormControl(),
      num_child_under_5_y_demogra_id: ['', Validators.required], 
      // no_of_child_under5_others: new FormControl(),
      no_of_child_under5_others: ['', [Validators.required]],
      // rural_pop: new FormControl(),
      rural_pop: ['', [Validators.required, Validators.pattern("(([0-9]+)|([0-9]+[.][0-9]+))")]],  
      // rural_pop_demogra_id: new FormControl(),
      rural_pop_demogra_id: ['', Validators.required],
      // rural_pop_others: new FormControl(),
      rural_pop_others: ['', [Validators.required]],
      // sc_pop: new FormControl(),
      sc_pop: ['0'],
      // sc_pop_demogra_id: new FormControl(),
      sc_pop_demogra_id: ['0'],
      // sc_pop_others: new FormControl(),
      sc_pop_others: ['0'],
      // st_pop: new FormControl(),
      st_pop: ['0'],
      // st_pop_demogra_id: new FormControl(),
      st_pop_demogra_id: ['0'],
      // st_pop_others: new FormControl(),
      st_pop_others: ['0'],
      // pop_density: new FormControl(),
      pop_density: ['', Validators.required],
      // pop_density_demogra_id: new FormControl(),
      pop_density_demogra_id: ['', Validators.required],
      // pop_dens_others: new FormControl(),
      pop_dens_others: ['', [Validators.required]],
      // total_literacy: new FormControl(),
      total_literacy: ['', [Validators.required, Validators.pattern("(([0-9]+)|([0-9]+[.][0-9]+))")]],
      // total_literacy_demogra_id: new FormControl(),
      total_literacy_demogra_id: ['', Validators.required],
      // tot_lit_others: new FormControl(),
      tot_lit_others: ['', [Validators.required]],
      // fem_literacy: new FormControl(),
      fem_literacy: ['', [Validators.required, Validators.pattern("(([0-9]+)|([0-9]+[.][0-9]+))")]],
      // fem_literacy_demogra_id: new FormControl(),
      fem_literacy_demogra_id: ['', Validators.required],
      // female_lit_others: new FormControl(),
      female_lit_others: ['', [Validators.required]],

      key_ngo_info_array: new FormArray([]),
      numberOfngoinfo: ['1'],


      key_ngo_source_array: new FormArray([]), 
      numberOfngosrc: ['1'],

      // iphs_theme_name: new FormControl(),
      //iphs_theme_name:  ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      iphs_theme_name:  ['', [Validators.required, Validators.maxLength(5000) ]],
      iphs_coverage_indicators: new FormControl(),
      iphs_source: ['1'],
      iphs_data: ['100'],
      iphs_expected_status: ['100'],
      iphs_gap_hmis: ['0'],

      // details_infra: new FormControl(),
      // sanctioned_infra: new FormControl(),
      // available_functional_infra: new FormControl(),
      // gap_infra: new FormControl(),

      //details_infra: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      details_infra: ['', [Validators.required, Validators.maxLength(5000) ]],
      sanctioned_infra: ['', Validators.required],
      available_functional_infra: ['', Validators.required],
      gap_infra: new FormControl(),

      // details_fina: new FormControl(),
      // sanctioned_fina: new FormControl(),
      // available_functional_fina: new FormControl(),
      // gap_fina: new FormControl(),

      //details_fina: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      details_fina: ['', [Validators.required, Validators.maxLength(5000) ]],
      sanctioned_fina: ['', Validators.required],
      available_functional_fina: ['', Validators.required],
      gap_fina: new FormControl(),


      // details_supp: new FormControl(),
      // sanctioned_supp: new FormControl(),
      // available_functional_supp: new FormControl(),
      // gap_supp: new FormControl(),

      //details_supp: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      details_supp: ['', [Validators.required, Validators.maxLength(5000) ]],
      sanctioned_supp: ['', Validators.required],
      available_functional_supp: ['', Validators.required],
      gap_supp: new FormControl(),


      // details_tech: new FormControl(),
      // sanctioned_tech: new FormControl(),
      // available_functional_tech: new FormControl(),
      // gap_tech: new FormControl(),


      //details_tech: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      details_tech: ['', [Validators.required, Validators.maxLength(5000) ]],
      sanctioned_tech: ['', Validators.required],
      available_functional_tech: ['', Validators.required],
      gap_tech: new FormControl(), 


      // details_hr: new FormControl(),
      // sanctioned_hr: new FormControl(),
      // available_functional_hr: new FormControl(),
      // gap_hr: new FormControl(),


      //details_hr: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
      details_hr: ['', [Validators.required, Validators.maxLength(5000) ]],
      sanctioned_hr: ['', Validators.required],
      available_functional_hr: ['', Validators.required],
      gap_hr: new FormControl(),

      // infra_array: new FormArray([]),
      infra_array: new FormArray([]),
      numberOfTickets: ['1'],

      fina_array: new FormArray([]),
      numberOffina: ['1'],

      supp_array: new FormArray([]),
      numberOfsupp: ['1'],


      tech_array: new FormArray([]),
      numberOftech: ['1'],


      hr_array: new FormArray([]),
      numberOfhr: ['1'], 
    });


    this.f.numberOfngosrc.setValue(parseInt(this.f.numberOfngosrc.value) + 1);
    const numberOfngosrc = this.f.numberOfngosrc.value - 1 || 0;
    if (this.key_ngo_source_array.length < numberOfngosrc) {
      for (let i = this.key_ngo_source_array.length; i < numberOfngosrc; i++) {
        this.key_ngo_source_array.push(this.formBuilder.group({

          //stakeholder_name:  ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          stakeholder_name:  ['', [Validators.required, Validators.maxLength(5000) ]],
          contact_details:  ['', [Validators.required]],
        }));

      }
    }


    this.f.numberOfngoinfo.setValue(parseInt(this.f.numberOfngoinfo.value) + 1);
    const numberOfngoinfo = this.f.numberOfngoinfo.value - 1 || 0;
    if (this.key_ngo_info_array.length < numberOfngoinfo) {
      for (let i = this.key_ngo_info_array.length; i < numberOfngoinfo; i++) {
        this.key_ngo_info_array.push(this.formBuilder.group({

          //ngo_name:  ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          ngo_name:  ['', [Validators.required, Validators.maxLength(5000) ]],
          ngo_details:  ['', [Validators.required]],
        }));

      }
    }

    let district_id: string, cycle_id: string, year: string, user_id: string;

    district_id = "" + sessionStorage.getItem('district');
    cycle_id = "" + sessionStorage.getItem('cycle');
    year = "" + sessionStorage.getItem('year');
    user_id = "" + sessionStorage.getItem('userid');

    this.sanctioned_year = year + "-" + (parseInt(year) + 1);

    this.selected_indi = [];



    this._diphHttpClientService.getIndicatorsList()
      .subscribe(
        data => {
          //console.log("indicator data : "+JSON.stringify(data))
          this.all_areas_indicators_arr = data.areas_Id_Indicators_map_list;
        },
        error => {
          console.log(error); alert("Error in fetching data from Server= " + error);
        });


    if (!this.proceedIfForm1AIsFilled()) {
      return;
    }
    else {

    }

    //Event Binding in Angular
    this.sanctioned_infra.valueChanges.subscribe(
      sanc_infra => {

      //let gap_infra2 = this.sanctioned_infra.value - this.available_functional_infra.value;
      //    if(gap_infra2 < 0)
      //   {
      //     this.gap_infra.setValue("0");
      //   }
      //   else
      //   {
      //     this.gap_infra.setValue(this.sanctioned_infra.value - this.available_functional_infra.value);
      //  }
      this.gap_infra.setValue(this.sanctioned_infra.value - this.available_functional_infra.value);
      }
    );

    this.available_functional_infra.valueChanges.subscribe(
      avail_infra => {
        this.gap_infra.setValue(this.sanctioned_infra.value - this.available_functional_infra.value);
      }
    );

    this.sanctioned_fina.valueChanges.subscribe(
      sanc_fina => {
        this.gap_fina.setValue(this.sanctioned_fina.value - this.available_functional_fina.value);
      }
    );

    this.available_functional_fina.valueChanges.subscribe(
      avail_fina => {
        this.gap_fina.setValue(this.sanctioned_fina.value - this.available_functional_fina.value);
      }
    );


    this.sanctioned_supp.valueChanges.subscribe(
      sanc_supp => {
        this.gap_supp.setValue(this.sanctioned_supp.value - this.available_functional_supp.value);
      }
    );


    this.available_functional_supp.valueChanges.subscribe(
      avail_supp => {
        this.gap_supp.setValue(this.sanctioned_supp.value - this.available_functional_supp.value);
      }
    );


    this.sanctioned_tech.valueChanges.subscribe(
      sanc_tech => {
        this.gap_tech.setValue(this.sanctioned_tech.value - this.available_functional_tech.value);
      }
    );

    this.available_functional_tech.valueChanges.subscribe(
      avail_tech => {
        this.gap_tech.setValue(this.sanctioned_tech.value - this.available_functional_tech.value);
      }
    );


    this.sanctioned_hr.valueChanges.subscribe(
      sanc_hr => {
        this.gap_hr.setValue(this.sanctioned_hr.value - this.available_functional_hr.value);
      }
    );

    this.available_functional_hr.valueChanges.subscribe(
      avail_hr => {
        this.gap_hr.setValue(this.sanctioned_hr.value - this.available_functional_hr.value);
      }
    );



    $(document).ready(function () {


    });
  }


  sanctioned_hr_callback() {
    this.gap_hr.setValue(this.sanctioned_hr.value - this.available_functional_hr.value);
  }

  available_functional_hr_callback() {
    this.gap_hr.setValue(this.sanctioned_hr.value - this.available_functional_hr.value);
  }

  sanctioned_tech_callback() {
    this.gap_tech.setValue(this.sanctioned_tech.value - this.available_functional_tech.value);
  }

  available_functional_tech_callback() {
    this.gap_tech.setValue(this.sanctioned_tech.value - this.available_functional_tech.value);
  }

  sanctioned_supp_callback() {
    this.gap_supp.setValue(this.sanctioned_supp.value - this.available_functional_supp.value);
  }

  available_functional_supp_callback() {
    this.gap_supp.setValue(this.sanctioned_supp.value - this.available_functional_supp.value);
  }

  sanctioned_fina_callback() {
    this.gap_fina.setValue(this.sanctioned_fina.value - this.available_functional_fina.value);
  }

  available_functional_fina_callback() {
    this.gap_fina.setValue(this.sanctioned_fina.value - this.available_functional_fina.value);
  }

  sanctioned_infra_callback(val: string) {
    this.gap_infra.setValue(this.sanctioned_infra.value - this.available_functional_infra.value);
  }

  available_functional_infra_callback() {
    this.gap_infra.setValue(this.sanctioned_infra.value - this.available_functional_infra.value);
  }





  get sanctioned_hr() {
    return this.dynamicForm.get('sanctioned_hr');
  }

  get available_functional_hr() {
    return this.dynamicForm.get('available_functional_hr');
  }

  get gap_hr() {
    return this.dynamicForm.get('gap_hr');
  }


  get sanctioned_tech() {
    return this.dynamicForm.get('sanctioned_tech');
  }


  get available_functional_tech() {
    return this.dynamicForm.get('available_functional_tech');
  }

  get gap_tech() {
    return this.dynamicForm.get('gap_tech');
  }

  get sanctioned_supp() {
    return this.dynamicForm.get('sanctioned_supp');
  }


  get available_functional_supp() {
    return this.dynamicForm.get('available_functional_supp');
  }

  get gap_supp() {
    return this.dynamicForm.get('gap_supp');
  }

  get sanctioned_fina() {
    return this.dynamicForm.get('sanctioned_fina');
  }

  get available_functional_fina() {
    return this.dynamicForm.get('available_functional_fina');
  }

  get gap_fina() {
    return this.dynamicForm.get('gap_fina');
  }


  get available_functional_infra() {
    return this.dynamicForm.get('available_functional_infra');
  }
  get gap_infra() {
    return this.dynamicForm.get('gap_infra');
  }

  get sanctioned_infra() {
    return this.dynamicForm.get('sanctioned_infra');
  }

  // convenience getters for easy access to form fields
  get f() { return this.dynamicForm.controls; }
  get infra_arr() { return this.f.infra_array as FormArray; }
  get fina_array() { return this.f.fina_array as FormArray; }
  get supp_array() { return this.f.supp_array as FormArray; }
  get tech_array() { return this.f.tech_array as FormArray; }
  get hr_array() { return this.f.hr_array as FormArray; }
  get key_ngo_info_array() { return this.f.key_ngo_info_array as FormArray; }
  get key_ngo_source_array() { return this.f.key_ngo_source_array as FormArray; }

  

  onClickAddRowfina(val: any) {

    this.f.numberOffina.setValue(parseInt(this.f.numberOffina.value) + val);
    const numberOffina = this.f.numberOffina.value - 1 || 0;
    if (this.fina_array.length < numberOffina) {
      for (let j = this.fina_array.length; j < numberOffina; j++) {
        this.fina_array.push(this.formBuilder.group({
          // document_details: [''],
          // document_sanctioned: [''],
          // document_available_functional: [''],
          // document_gap: [''],
          //document_details:  ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          document_details:  ['', [Validators.required, Validators.maxLength(5000) ]],
          document_sanctioned: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_available_functional: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_gap: ['0'],
        }));



        this.fina_array.controls[j].get('document_sanctioned').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.fina_array.controls[j].patchValue({
              document_gap: this.fina_array.controls[j].get('document_sanctioned').value - this.fina_array.controls[j].get('document_available_functional').value
            });
          }
        );;

        this.fina_array.controls[j].get('document_available_functional').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.fina_array.controls[j].patchValue({
              document_gap: this.fina_array.controls[j].get('document_sanctioned').value - this.fina_array.controls[j].get('document_available_functional').value
            });
          }
        );;
      }
    }
  }

  onClickAddRowsupp(val: any) {

    this.f.numberOfsupp.setValue(parseInt(this.f.numberOfsupp.value) + val);
    const numberOfsupp = this.f.numberOfsupp.value - 1 || 0;
    if (this.supp_array.length < numberOfsupp) {
      for (let j = this.supp_array.length; j < numberOfsupp; j++) {
        this.supp_array.push(this.formBuilder.group({
          // document_details: [''],
          // document_sanctioned: [''],
          // document_available_functional: [''],
          // document_gap: [''],
          //document_details: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          document_details: ['', [Validators.required, Validators.maxLength(5000) ]],
          document_sanctioned: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_available_functional: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_gap: ['0'],
        }));


        this.supp_array.controls[j].get('document_sanctioned').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.supp_array.controls[j].patchValue({
              document_gap: this.supp_array.controls[j].get('document_sanctioned').value - this.supp_array.controls[j].get('document_available_functional').value
            });
          }
        );;

        this.supp_array.controls[j].get('document_available_functional').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.supp_array.controls[j].patchValue({
              document_gap: this.supp_array.controls[j].get('document_sanctioned').value - this.supp_array.controls[j].get('document_available_functional').value
            });
          }
        );;
      }
    }
  }



  onClickAddRowtech(val: any) {

    this.f.numberOftech.setValue(parseInt(this.f.numberOftech.value) + val);
    const numberOftech = this.f.numberOftech.value - 1 || 0;
    if (this.tech_array.length < numberOftech) {
      for (let j = this.tech_array.length; j < numberOftech; j++) {
        this.tech_array.push(this.formBuilder.group({
          // document_details: [''],
          // document_sanctioned: [''],
          // document_available_functional: [''],
          // document_gap: [''],

          //document_details:  ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          document_details:  ['', [Validators.required, Validators.maxLength(5000) ]],
          document_sanctioned: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_available_functional: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_gap: ['0'],
        }));


        this.tech_array.controls[j].get('document_sanctioned').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.tech_array.controls[j].patchValue({
              document_gap: this.tech_array.controls[j].get('document_sanctioned').value - this.tech_array.controls[j].get('document_available_functional').value
            });
          }
        );;

        this.tech_array.controls[j].get('document_available_functional').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.tech_array.controls[j].patchValue({
              document_gap: this.tech_array.controls[j].get('document_sanctioned').value - this.tech_array.controls[j].get('document_available_functional').value
            });
          }
        );;
      }
    }
  }


  onClickAddRowhr(val: any) {

    this.f.numberOfhr.setValue(parseInt(this.f.numberOfhr.value) + val);
    const numberOfhr = this.f.numberOfhr.value - 1 || 0;
    if (this.hr_array.length < numberOfhr) {
      for (let j = this.hr_array.length; j < numberOfhr; j++) {
        this.hr_array.push(this.formBuilder.group({
          // document_details: [''],
          // document_sanctioned: [''],
          // document_available_functional: [''],
          // document_gap: [''],


          //document_details:  ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          document_details:  ['', [Validators.required, Validators.maxLength(5000) ]],
          document_sanctioned: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_available_functional: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_gap: ['0'],
        }));


        this.hr_array.controls[j].get('document_sanctioned').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.hr_array.controls[j].patchValue({
              document_gap: this.hr_array.controls[j].get('document_sanctioned').value - this.hr_array.controls[j].get('document_available_functional').value
            });
          }
        );;

        this.hr_array.controls[j].get('document_available_functional').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.hr_array.controls[j].patchValue({
              document_gap: this.hr_array.controls[j].get('document_sanctioned').value - this.hr_array.controls[j].get('document_available_functional').value
            });
          }
        );;
      }
    }
  }




  onClickRemovefina(e) {
    this.fina_array.removeAt(e);
    this.f.numberOffina.setValue(parseInt(this.f.numberOffina.value) - 1);
  }


  onClickRemovesupp(e) {
    this.supp_array.removeAt(e);
    this.f.numberOfsupp.setValue(parseInt(this.f.numberOfsupp.value) - 1);
  }

  onClickRemovetech(e) {
    this.tech_array.removeAt(e);
    this.f.numberOftech.setValue(parseInt(this.f.numberOftech.value) - 1);
  }

  onClickRemovehr(e) {
    this.hr_array.removeAt(e);
    this.f.numberOfhr.setValue(parseInt(this.f.numberOfhr.value) - 1);
  }

  onClickRemove(index_position) {
    this.infra_arr.removeAt(index_position);
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) - 1);
  }

  onClickRemoveRowngoInfo(e) {
    this.key_ngo_info_array.removeAt(e);
    this.f.numberOfngoinfo.setValue(parseInt(this.f.numberOfngoinfo.value) - 1);
  }

  onClickRemoveRowngoSrc(e) {
    this.key_ngo_source_array.removeAt(e);
    this.f.numberOfngosrc.setValue(parseInt(this.f.numberOfngosrc.value) - 1);
  }

  onClickAddRowngoInfo(val) {
    this.f.numberOfngoinfo.setValue(parseInt(this.f.numberOfngoinfo.value) + val);
    const numberOfngoinfo = this.f.numberOfngoinfo.value - 1 || 0;
    if (this.key_ngo_info_array.length < numberOfngoinfo) {
      for (let i = this.key_ngo_info_array.length; i < numberOfngoinfo; i++) {
        this.key_ngo_info_array.push(this.formBuilder.group({

          //ngo_name: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          ngo_name: ['', [Validators.required, Validators.maxLength(5000) ]],
          ngo_details: ['', [Validators.required]]
        }));

      }
    }
  }


  onClickAddRowngoSrc(val) {
    this.f.numberOfngosrc.setValue(parseInt(this.f.numberOfngosrc.value) + val);
    const numberOfngosrc = this.f.numberOfngosrc.value - 1 || 0;
    if (this.key_ngo_source_array.length < numberOfngosrc) {
      for (let i = this.key_ngo_source_array.length; i < numberOfngosrc; i++) {
        this.key_ngo_source_array.push(this.formBuilder.group({

          //stakeholder_name: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          stakeholder_name: ['', [Validators.required, Validators.maxLength(5000) ]],
          contact_details: ['', [Validators.required]]
        }));

      }
    }
  }


  // infra_array: new FormArray([]),
  //     numberOfTickets: ['1'],

  onClickAddRowInfra(val) {
    this.f.numberOfTickets.setValue(parseInt(this.f.numberOfTickets.value) + val);
    const numberOfTickets = this.f.numberOfTickets.value - 1 || 0;
    if (this.infra_arr.length < numberOfTickets) {
      for (let i = this.infra_arr.length; i < numberOfTickets; i++) {
        this.infra_arr.push(this.formBuilder.group({
          //document_details: ['', [Validators.required, Validators.pattern("[A-Za-z ]+"),  Validators.maxLength(5000) ]],
          document_details: ['', [Validators.required, Validators.maxLength(5000) ]],
          document_sanctioned: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_available_functional: ['', [Validators.required, Validators.pattern("^[0-9]*$")]],
          document_gap: ['0'],
        }));

        /*Code for Dynamic Event Binding */

        // {'document_sanctioned':111,'document_available_functional':2222}
       

        //valuechanges function denotes that whenever the input value of "document_sanctioned" is 
         //changed then the callback func of subscribe will be called.

        this.infra_arr.controls[i]/*an object of the array*/.get('document_sanctioned').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.infra_arr.controls[i].patchValue({
              document_gap: this.infra_arr.controls[i].get('document_sanctioned').value - this.infra_arr.controls[i].get('document_available_functional').value
            });
          }
        );;

        this.infra_arr.controls[i].get('document_available_functional').valueChanges.subscribe(
          document_sanctioned => {
            // alert(document_sanctioned);
            this.infra_arr.controls[i].patchValue({
              document_gap: this.infra_arr.controls[i].get('document_sanctioned').value - this.infra_arr.controls[i].get('document_available_functional').value
            });
          }
        );;

        //     this.someArray.controls.forEach(
        //       control => {
        //           control.valueChanges.subscribe(
        //                () => {
        //               console.log(this.someArray.controls.indexOf(control)) // logs index of changed item in form array
        //                }
        //           )
        //       }
        //  )

      }
    }
  }



  movetonexttab() {

  }




  all_areas_indicators_arr = [];

  total_coverage_indi = [];


  dialog_response: any = null;

  openDialog(): void {
    const dialogRef = this.dialog.open(Form1bModalDialogComponent, {
      data: {
        myvar: "My variable value",
        arr: this.all_areas_indicators_arr,
        dialog_result: this.dialog_response,
        selected_indi: this.selected_indi
      },
      height: '90%',
      width: '70%'
      // maxHeight:'100%',
      // maxWidth: '100%',
      // width:'1000px',
      // height:'1000px'
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log('The dialog was closed');
      console.log("Json string passsed on save will be printed below in result variable");
      console.log(result);


      let final_obj = [];

      let count = 0;
      for (var i in result) {
        if (i == "status") {
          count++;
        }
      }


      if (result == null) {

      }
      //If new Indicator is created then this else-if will run()
      else if (count != 0) {
        let indicatorbean_obj: any;
        for (var i in result) {
          if (i == "bean") {
            indicatorbean_obj = result[i];
            // alert(JSON.stringify(indicatorbean_obj, null, 4));
            this._diphHttpClientService.createNewIndicator(indicatorbean_obj)
              .subscribe(
                data => {
                  alert("Successfully submitted!!");
                  console.log(data);
                  this.router.routeReuseStrategy.shouldReuseRoute = () => false;
                  this.router.onSameUrlNavigation = 'reload';
                  this.router.navigate(['/dashboard/form1b']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error); });
          }
        }
      }
      //When selected indicators are returned
      else {
        this.selected_indi = result;
        //this.dialog_response = result;

        this.total_coverage_indi = result;


        //This code is used in Indicators List (Fetches only ESSENTIAL and ACTION Indicators)
        this._diphHttpClientService.getAllIndicators()
        .subscribe(
          data => {
            console.log("getAllIndicators : "+JSON.stringify(data));
            for(let i=0;i<data.areas_Id_Indicators_map_list.length;i++){
              let temp_o = data.areas_Id_Indicators_map_list[i];

              for(let j=0;j<this.total_coverage_indi.length;j++){
                let mytempobj = this.total_coverage_indi[j];

                if(mytempobj["indicator_id"] == temp_o["indicator_id"]){
                 let obj:any = {};
                 obj = mytempobj;
                 obj.category = ""+temp_o["category"];
                 obj.frequency = ""+temp_o["frequency"];
                 this.total_coverage_indi[j] = obj;  
                }
              }
            }
          },
          error => {
            console.log(error); alert("Error= " + error);
          });


       
      }



    });
  }

  allowIntegersUpto100(val:string){
    if( (val != null && parseFloat(val) <= 100)  && (val != null && parseFloat(val) >=0) ){      
      return false; 
    }
    else {
      return true;
    }
  }

  allowpositiveInt(num:number){
    let regexp = /^[1-9][0-9]*$/g;
    let result = regexp.test(""+num);
    return !result;   
  }

allowdespositiveInt(num:number){
    let regexp = /^[1-9][0-9]|[1-9.][0-9]*$/g;
    let result = regexp.test(""+num);
    return !result;   
  }

  calculatePopulationDensity(population, area){
    
    this.populationDesity = 0.0;
    var temp = 0.0;
    if(population !=null && population != '' && population != 0){
      if(area !=null && area != '' && area != 0){
        temp = population/area;
        this.populationDesity = parseFloat(temp.toFixed(2));        
      }
    }
  }

  calculateNumberOfWomen(population){

    this.numberOfWomen = 0.0;
    var temp = 0.0;
    if(population !=null && population != '' && population != 0){      
        temp = (population*this.numberOfWomenConversionFactor)/100;
        this.numberOfWomen = parseInt(temp.toFixed(0));       
    }
  }
  calculateNumberOfChildren(population){

    this.numberOfChildren = 0.0;
    var temp = 0.0;
    if(population !=null && population != '' && population != 0){      
      temp = (population*this.numberOfChildrenConversionFactor)/100;
        this.numberOfChildren = parseInt(temp.toFixed(0));       
    }
  }

  onSubmit() {

    
    let finalresult = true;

   

    if (this.allowdespositiveInt(this.f.total_area.value)) {
      finalresult = false;
    }

    if (this.allowpositiveInt(this.f.total_pop.value)) {
      finalresult = false;
    }

    if (this.allowpositiveInt(this.f.num_women_in_reproductive_age_15_49_y.value)) {
      finalresult = false;
    }

    if (this.allowpositiveInt(this.f.num_child_under_5_y.value)) {
      finalresult = false;
    }

    if (this.allowdespositiveInt(this.f.pop_density.value)) {
      finalresult = false;
    }

    if(this.allowIntegersUpto100(this.f.rural_pop.value)){
      finalresult = false;
    }
    /*
    if(this.allowIntegersUpto100(this.f.sc_pop.value)){
      finalresult = false;
    }

    if(this.allowIntegersUpto100(this.f.st_pop.value)){
      finalresult = false;
    }
    */
    if(this.allowIntegersUpto100(this.f.total_literacy.value)){
      finalresult = false;
    }

    if(this.allowIntegersUpto100(this.f.fem_literacy.value)){    
      finalresult = false;
    }

    this.submitted = true;
    //alert("Form submitted New");
    // stop here if form is invalid
    if (this.dynamicForm.invalid) {
      alert("Form invalid");
      return;
    }

    if(this.total_coverage_indi.length == 0){
      alert("Atleast one indicator should be selected in 'WHO Health System Blocks' section");
      return;
    }

    
    for (let z = 0; z < this.total_coverage_indi.length; z++) {
      if (this.total_coverage_indi[z]['source'] == '') {
        finalresult = false;
      }
      else if (this.total_coverage_indi[z]['data'] == '') {
        finalresult = false;
      }
      else if (this.total_coverage_indi[z]['expected'] == '') {
        finalresult = false;
      }
    }


    //alert("this.f.verified_by_name.value = "+this.f.verified_by_name.value);
    //alert("this.f.verified_by_other_actual_name.value = "+this.f.verified_by_other_actual_name.value);

    if (this.f.verified_by_name.value == '15' && this.f.verified_by_other_actual_name.value == '') {
      finalresult = false;
    }

    if (this.f.verified_by_name.value == '15' && this.f.verified_by_other_actual_name.value == 'null') {
      finalresult = false;
    }

    if (this.f.verified_by_name.value == '15' && this.f.verified_by_other_actual_name.value == null) {
      finalresult = false;
    }

    if (!finalresult) {
      alert("Form invalid");
      return;
    }


    //Since form is valid we will forward to Ajaxresponse

    // display form values on success
    //alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.dynamicForm.value, null, 4));
    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    this.savedform = true;

    // this.customer.state_policy_array= this.customer.state_policy_array.concat(this.dynamic_state_policy_array);
    // this.customer.district_policy_array= this.customer.district_policy_array.concat(this.dynamic_district_policy_array);
    // this.customer.health_dept_array= this.customer.health_dept_array.concat(this.dynamic_health_policy_array);
    // this.customer.non_health_dept_array= this.customer.non_health_dept_array.concat(this.dynamic_non_health_policy_array);
    // this.customer.private_sec_array= this.customer.private_sec_array.concat(this.dynamic_private_sec_policy_array);
    // this.customer.large_scale_district_array= this.customer.large_scale_district_array.concat(this.dynamic_large_scale_policy_array);


    // get f() { return this.dynamicForm.controls; }
    // get infra_arr() { return this.f.infra_array as FormArray; }
    // get fina_array() { return this.f.fina_array as FormArray; }
    // get supp_array() { return this.f.supp_array as FormArray; }
    // get tech_array() { return this.f.tech_array as FormArray; }
    // get hr_array() { return this.f.hr_array as FormArray; }



    //Now reactive Form named as "dynamicForm" is converted o normal js object
    let x: any = this.dynamicForm.value;

    let str1 = this.infra_arr.value;

    
    //console.log("x =");
    //console.log(x);


    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

    // alert('SUCCESS!! :-)\n\n' + JSON.stringify(x, null, 4));

    this.completeClicked = true;

    this._diphHttpClientService.saveform1bdetails(x, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "1")
      .subscribe(
        data => {
          //console.log(data);
          this.serverjsonresponse = data;

          this.completeClicked = false;

          this.router.navigate(['dashboard/form1bview']);
        },
        error => {
          //If error is thrown then form is shown again to submit again or go to dash board
          this.savedform = false;
	  this.completeClicked = false;
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
        });
  }

  partialSave(){

    

        if (this.f.filled_by.value ==null || this.f.filled_by.value == '') {
          alert("Venue of the meeting is compulsary");
          return;
        }

        if(this.f.verified_by_name.value != "15" && (this.f.verified_by_name.value == null || this.f.verified_by_name.value == '')){ 
          alert("Chairperson of the meeting is compulsary");
          return;
        }
        if(this.f.verified_by_name.value == "15" && (this.f.verified_by_other_actual_name.value == "null" || this.f.verified_by_other_actual_name.value == "")){ 
          alert("Chairperson of the meeting is compulsary");
          return;
        }
        if(this.f.verified_by_name.value == "15" && this.f.verified_by_other_actual_name.value == null){ 
          alert("Chairperson of the meeting is compulsary");
          return;
        }

        if (this.f.total_area.value !=null && this.f.total_area.value != '' && this.allowdespositiveInt(this.f.total_area.value)) {
          alert("Total area(Sq.km) must be positive number");
          return;
        }
        
        if (this.f.total_pop.value !=null && this.f.total_pop.value != '' && this.allowpositiveInt(this.f.total_pop.value)) {
          alert("Total population must be positive number");
        return;
        }
        
        if (this.f.num_women_in_reproductive_age_15_49_y.value !=null && this.f.num_women_in_reproductive_age_15_49_y.value != '' && this.allowpositiveInt(this.f.num_women_in_reproductive_age_15_49_y.value)) {
          alert("Number of women in the reproductive age group must be positive number");
        return;
        }
       
        if (this.f.num_child_under_5_y.value !=null && this.f.num_child_under_5_y.value != '' && this.allowpositiveInt(this.f.num_child_under_5_y.value)) {
          alert("Number of children under 5 years must be positive number");
        return;
        }        
        
        if(this.f.rural_pop.value !=null && this.f.rural_pop.value != '' && (this.allowIntegersUpto100(this.f.rural_pop.value))){
          alert("Rural Population must be positive number less or equal to 100");
        return;
        }
        
        if(this.f.total_literacy.value !=null && this.f.total_literacy.value != '' && (this.allowIntegersUpto100(this.f.total_literacy.value))){
          alert("Total literacy must be positive number less or equal to 100");
        return;
        }
        
        if(this.f.fem_literacy.value !=null && this.f.fem_literacy.value != '' && (this.allowIntegersUpto100(this.f.fem_literacy.value))){    
          alert("Female literacy must be positive number less or equal to 100");
        return;
        }  
        

      for(let i=1;i< this.key_ngo_info_array.length;i++){
          let tempobj = this.key_ngo_info_array.value[i];
          
          let ngoName = tempobj["ngo_name"];

          if(ngoName == ''){
            alert("NGO name expanded but not filled");
            return;
          }

        }

      for(let i=1;i< this.key_ngo_source_array.length ;i++){
          let tempobj = this.key_ngo_source_array.value[i];
          
          let ngoName = tempobj["stakeholder_name"];

          if(ngoName == ''){
            alert("Private Stakeholder name expanded but not filled");
            return;
          }
        }


        
        for(let i=0;i< this.infra_arr.length ;i++){
          let tempobj = this.infra_arr.value[i];
          
          let docDetails = tempobj["document_details"];

          if(docDetails == ''){
            alert("Infrastruture details expanded but not filled");
            return;
          }

        }

      for(let i=0;i< this.fina_array.length ;i++){
          let tempobj = this.fina_array.value[i];
          
          let docDetails = tempobj["document_details"];

          if(docDetails == ''){
            alert("Finances details expanded but not filled");
            return;
          }

        }

      for(let i=0;i< this.supp_array.length ;i++){
          let tempobj = this.supp_array.value[i];
          
          let docDetails = tempobj["document_details"];

          if(docDetails == ''){
            alert("Supplies details expanded but not filled");
            return;
          }

        }

      for(let i=0;i< this.tech_array.length ;i++){
          let tempobj = this.tech_array.value[i];
          
          let docDetails = tempobj["document_details"];

          if(docDetails == ''){
            alert("Technology details expanded but not filled");
            return;
          }

        }

      for(let i=0;i< this.hr_array.length ;i++){
          let tempobj = this.hr_array.value[i];
          
          let docDetails = tempobj["document_details"];

          if(docDetails == ''){
            alert("Human resources details expanded but not filled");
            return;
          }

        }

    let login_district = sessionStorage.getItem('district');
    let login_cycle = sessionStorage.getItem('cycle');
    let login_year = sessionStorage.getItem('year');//userid
    let login_userid = sessionStorage.getItem('userid');

    this.savedform = true;

    
    //Now reactive Form named as "dynamicForm" is converted o normal js object
    let x: any = this.dynamicForm.value;

    let str1 = this.infra_arr.value;

    

    var date2 = new Date("" + this.date.value);

    var new_date2 = date2.getDate();
    var new_month2 = (date2.getMonth() + 1);
    var new_year2 = date2.getFullYear();

    x.date_of_verification = new_year2 + "-" + new_month2 + "-" + new_date2;

    this.completeClicked = true;
   
    this._diphHttpClientService.saveform1bdetails(x, this.total_coverage_indi, login_district, login_cycle, login_year, login_userid, "0")
      .subscribe(
        data => {
          this.completeClicked = false;
          this.serverjsonresponse = data;

          //sessionStorage.setItem('serverjsonresponse', JSON.stringify(data));

          this.router.navigate(['dashboard/form1bview']);
        },
        error => {
          //If error is thrown then form is shown again to submit again or go to dash board
          this.completeClicked = false;
          this.savedform = false;
	  console.log(error); alert("Error= " + error);
          alert("Network Error : \n1-Please check your internet connection and try again.\n2-Contact your server/network admin. \n\n Leaving the page.");
          this.router.navigate(['dashboard']);
        });


  }


  previouspage() {
    let ans=confirm("Going back will result in loss of unsaved data.\nPress OK to continue.");
    
    if(ans){
      this.router.navigate(['dashboard']);
    }
    
  }

  findvalue(event) { 
    // alert(event.which); > 31 <48    ||  > 31 && >57
    if (event.which > 31 && (event.which < 48 || event.which > 57)) {
      event.stopPropagation();
      return false;
    }
    return true;
  }

  allowNumbersOnly(event) {
    // alert(event.which);
    if ((event.which > 64 && event.which < 91) || (event.which > 96 && event.which < 123) || (event.which == 32) || (event.which == 0))
      return false;
    else
      return true;
  }

}  
