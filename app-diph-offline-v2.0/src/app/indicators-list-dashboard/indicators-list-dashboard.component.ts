import { Component, OnInit, Injectable } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { PlatformLocation } from '@angular/common';
import { FormBuilder, FormGroup, FormArray, Validators, FormControl } from '@angular/forms';
import { DiphHttpClientService } from '../services/diph-http-client-service.service';
import { MatDialog } from '@angular/material';
import { CreateindicatorModalDialogComponent } from '../createindicator-modal-dialog/createindicator-modal-dialog.component';
import { EditSelectedIndicatorComponent } from '../edit-selected-indicator/edit-selected-indicator.component';
import { EditSelectedOptionalIndicatorComponent } from '../edit-selected-optional-indicator/edit-selected-optional-indicator.component';
import { EditSelectedActionIndicatorComponent } from '../edit-selected-action-indicator/edit-selected-action-indicator.component';

declare var $: any;

@Component({
  selector: 'app-indicators-list-dashboard',
  templateUrl: './indicators-list-dashboard.component.html',
  styleUrls: ['./indicators-list-dashboard.component.css']
})
export class IndicatorsListDashboardComponent implements OnInit {

 
  JsonResponseObj: any;

  serverResponse: any;

  actiondataResponse:any;

  categoryTable: string = "ESSENTIAL";

  adminloggedIN:string="0";


  constructor(location: PlatformLocation, public dialog: MatDialog, private router: Router, private formBuilder: FormBuilder, private _diphHttpClientService: DiphHttpClientService) { }



  ngOnInit() {

    this.adminloggedIN =  sessionStorage.getItem('userStatus');

    $(document).ready(function () {
      $("#searchInput").on("keyup", function () {
        var value = $(this).val().toLowerCase();
        $(".indicatorTableBody tr").filter(function () {
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
      });

    });

    $(document).on('click', '.wrapper', function () {
      $(this).closest('td').find(".tooltip").toggle();

    });




    this._diphHttpClientService.getAllIndicators()
      .subscribe(
        data => {
          // this.JsonResponseObj = data.areas_Id_Indicators_map_list;

          let ESSENTIAL = data.areas_Id_Indicators_map_list.filter(t => t.category == 'ESSENTIAL');
          this.JsonResponseObj = ESSENTIAL;

          if(this.JsonResponseObj != null && this.JsonResponseObj.length >0){
            this.unique_area_arr =["All"].concat( this.getUnique(this.JsonResponseObj.map(a => a.area_name))); ;
          //this.sel_area_essential_obj =   this.JsonResponseObj.filter(t => t.area_name == ""+this.JsonResponseObj[0].area_name);

          this.sel_area_essential_obj =   this.JsonResponseObj;
          this.default_essential_obj  = ""+this.unique_area_arr[0];          
        }
         // console.log(this.JsonResponseObj);

          let ACTION = data.areas_Id_Indicators_map_list.filter(t => t.category == 'ACTION');
          this.actiondataResponse = ACTION;
          
        if(this.actiondataResponse != null && this.actiondataResponse.length >0){
          //this.sel_area_action_obj =  this.actiondataResponse.filter(t => t.area_name == ""+this.actiondataResponse[0].area_name);
          this.sel_area_action_obj =  this.actiondataResponse;

         //this.default_action_obj   = ""+this.actiondataResponse[0].area_name;
         this.default_action_obj   = "All"
        }

          this.all_areas_indicators_arr = data.areas_Id_Indicators_map_list;
        },
        error => {
          console.log(error); alert("Error= " + error);
        });


    this._diphHttpClientService.getAllOptionalIndicators()
      .subscribe(
        data => {
          this.serverResponse = data.optional_indicators_list;

        },
        error => {
          console.log(error); alert("Error in Loading Optional Indicators= ");
        });


    // this.serverResponse[0]={
    //   indicator_val:"",
    //   frequency:"",
    //   theme:"",
    //   current_available:""
    // }
  }

  public unique_area_arr = [];

  public sel_area_essential_obj = {};

  public sel_area_action_obj = {};

  public default_essential_obj = "";

  public default_action_obj = "";

  //To change essential to optionl to action
  changetable(val: string) {
    // alert(val);
    this.categoryTable = val;

    $("#searchInput").val("");

    if(val == 'ESSENTIAL'){      
    this.unique_area_arr = ["All"].concat(this.getUnique(this.JsonResponseObj.map(a => a.area_name)));
    }

    if(val == 'ACTION'){
      this.unique_area_arr = ["All"].concat(  this.getUnique(this.actiondataResponse.map(a => a.area_name)));
    }
  }

  public get_sel_area_indicators(str1,str2){

    if(str2 == 'ESSENTIAL'){

      
      if(str1 == "All"){
        this.sel_area_essential_obj = this.JsonResponseObj;

        this.default_essential_obj  = "All";
      }
      else{
        this.sel_area_essential_obj = this.JsonResponseObj.filter(t => t.area_name == str1);

      this.default_essential_obj  = str1;
      }

      
    }
    if(str2 == 'ACTION'){

      if(str1 == "All"){
        this.sel_area_action_obj = this.actiondataResponse;

      this.default_action_obj  = str1;
      }
      else{
        this.sel_area_action_obj = this.actiondataResponse.filter(t => t.area_name == str1);

      this.default_action_obj  = str1;
      }


      
    }   
    
  }

  
// Defining function to get unique values from an array
getUnique(array){
  var uniqueArray = [];
  
  // Loop through array values
  for(let i=0; i < array.length; i++){
      if(uniqueArray.indexOf(array[i]) === -1) {
          uniqueArray.push(array[i]);
      }
  }
  return uniqueArray;
}

  makeFirstLetterCapitol(val:string){
    if(val !=null && val !=''){
      let lower = val.toLowerCase();

    // const upper = lower.charAt(0).toUpperCase() + lower.substring(1);

    // const upper = lower.replace(/^\w/, function (chr) {
    //   return chr.toUpperCase();
    // });

    const upper = lower.replace(/^\w/, c => c.toUpperCase());

    return   upper.replace(/\b[a-z]|['_][a-z]|\B[A-Z]/g, function(x){return x[0]==="'"||x[0]==="_"?x:String.fromCharCode(x.charCodeAt(0)^32)});  
    }
    else
    {
      return val;
    }

    
  }


//   getStringFromHtml(text:string){
//     const html = text;
//     const div = document.createElement('div');
//     div.innerHTML = html;
//     return div.textContent || div.innerText || '';
// }


  all_areas_indicators_arr = [];

  total_coverage_indi = [];


  dialog_response: any = null;

  openDialog(): void {
    const dialogRef = this.dialog.open(CreateindicatorModalDialogComponent, {
      data: {
        myvar: "My variable value",
        arr: this.all_areas_indicators_arr,
        dialog_result: this.dialog_response,
        // selected_indi: this.selected_indi
      },
      height: '90%',
      width: '70%'
      // maxHeight:'100%',
      // maxWidth: '100%',
      // width:'1000px',
      // height:'1000px'
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
      // console.log("Json string passsed on save will be printed below in result variable");
      // console.log(result);


      let final_obj = [];

      let count = 0;
      for (var i in result) {
        if (i == "status") {
          count++;
        }
      }


      if (result == null) {

      }
      else if (count != 0) {
        let indicatorbean_obj: any;
        for (var i in result) {
          if (i == "bean") {
            indicatorbean_obj = result[i];
            // alert(JSON.stringify(indicatorbean_obj, null, 4));
            this._diphHttpClientService.createNewCategorizedIndicator(indicatorbean_obj)
              .subscribe(  
                data => {
                  alert("Successfully submitted!!");
                  console.log(data);
                  this.router.routeReuseStrategy.shouldReuseRoute = () => false;
                  this.router.onSameUrlNavigation = 'reload';
                  this.router.navigate(['/dashboard/indicatorslist']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error); 
              });
          }
        }
      }



    });
  }

  

  editOptionalIndicator(index:any){
    this.openEditOptionalIndicatorModalDialog(this.serverResponse[index]);
  }


  editIndicator(index:any) {
    //this.openEditIndicatorModalDialog(this.JsonResponseObj[index]);
    this.openEditIndicatorModalDialog(this.sel_area_essential_obj[index]);
  }

  editActionIndicator(index:any){
    //this.openEditActionIndicatorModalDialog(this.actiondataResponse[index]);
    this.openEditActionIndicatorModalDialog(this.sel_area_action_obj[index]);
  }

  openEditActionIndicatorModalDialog(currentObj:any): void {
    const dialogRef = this.dialog.open(EditSelectedActionIndicatorComponent, {
      data: {
        myvar: "My variable value",
        arr: this.all_areas_indicators_arr,
        currentObj:currentObj,
        dialog_result: this.dialog_response,
      },
      height: '90%',
      width: '70%'
    });


    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
      // console.log("Json string passsed on save will be printed below in result variable");
      // console.log(result);


      let final_obj = [];

      let count = 0;
      for (var i in result) {
        if (i == "status") {
          count++;
        }
      }


      if (result == null) {

      }
      else if (count != 0) {
        let indicatorbean_obj: any;
        for (var i in result) {
          if (i == "bean") {
            indicatorbean_obj = result[i];
            // alert(JSON.stringify(indicatorbean_obj, null, 4));


            this._diphHttpClientService.updateSelectedActionIndicator(indicatorbean_obj)
              .subscribe(
                data => {
                  alert("Successfully submitted!!");
                  console.log(data);
                  this.router.routeReuseStrategy.shouldReuseRoute = () => false;
                  this.router.onSameUrlNavigation = 'reload';
                  this.router.navigate(['/dashboard/indicatorslist']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error);
               });


          }
        }
      }



    });
  }




  openEditOptionalIndicatorModalDialog(currentObj:any): void {
    const dialogRef = this.dialog.open(EditSelectedOptionalIndicatorComponent, {
      data: {
        myvar: "My variable value",
        arr: this.all_areas_indicators_arr,
        currentObj:currentObj,
        dialog_result: this.dialog_response,
      },
      height: '90%',
      width: '70%'
    });


    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
      // console.log("Json string passsed on save will be printed below in result variable");
      // console.log(result);


      let final_obj = [];

      let count = 0;
      for (var i in result) {
        if (i == "status") {
          count++;
        }
      }


      if (result == null) {

      }
      else if (count != 0) {
        let indicatorbean_obj: any;
        for (var i in result) {
          if (i == "bean") {
            indicatorbean_obj = result[i];
            //alert(JSON.stringify(indicatorbean_obj, null, 4));


            this._diphHttpClientService.updateSelectedOptionalIndicator(indicatorbean_obj)
              .subscribe(
                data => {
                  alert("Successfully submitted!!");
                  console.log(data);
                  this.router.routeReuseStrategy.shouldReuseRoute = () => false;
                  this.router.onSameUrlNavigation = 'reload';
                  this.router.navigate(['/dashboard/indicatorslist']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error);
               });


          }
        }
      }



    });
  }


  openEditIndicatorModalDialog(currentObj:any): void {
    const dialogRef = this.dialog.open(EditSelectedIndicatorComponent, {
      data: {
        myvar: "My variable value",
        arr: this.all_areas_indicators_arr,
        currentObj:currentObj,
        dialog_result: this.dialog_response,
        // selected_indi: this.selected_indi
      },
      height: '90%',
      width: '70%'
      // maxHeight:'100%',
      // maxWidth: '100%',
      // width:'1000px',
      // height:'1000px'
    });

    dialogRef.afterClosed().subscribe(result => {
      // console.log('The dialog was closed');
      // console.log("Json string passsed on save will be printed below in result variable");
      // console.log(result);


      let final_obj = [];

      let count = 0;
      for (var i in result) {
        if (i == "status") {
          count++;
        }
      }


      if (result == null) {

      }
      else if (count != 0) {
        let indicatorbean_obj: any;
        for (var i in result) {
          if (i == "bean") {
            indicatorbean_obj = result[i];
            //alert(JSON.stringify(indicatorbean_obj, null, 4));


            this._diphHttpClientService.updateSelectedIndicator(indicatorbean_obj)
              .subscribe(
                data => {
                  alert("Successfully submitted!!");
                  console.log(data);
                  this.router.routeReuseStrategy.shouldReuseRoute = () => false;
                  this.router.onSameUrlNavigation = 'reload';
                  this.router.navigate(['/dashboard/indicatorslist']);
                },
                error => { console.log(error); alert("Error in saving new Indicator= " + error);
               });


          }
        }
      }



    });
  }

  previouspage() {
    this.router.navigate(['dashboard']);

  }

}
