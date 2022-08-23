import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


import {catchError} from 'rxjs/operators';
import { throwError } from 'rxjs'; 
import { Country } from '../model/country';
import { State } from '../model/state';
import { User } from '../model/user';
import { District } from '../model/district';
import { Cycle } from '../model/cycle';
import { Region } from '../model/region';


@Injectable({
  providedIn: 'root'
})
export class DiphHttpClientService {

  private url:string;
  private BASE_URL = window["cfgApiBaseUrl"];
  
  //var cfgApiBaseUrl = "http://localhost:8080/diphonline";       
    /*
    IDE:    http://localhost:8080/diphonline
    Tomcat: http://45.114.143.159:8080/diphonline
    Tomcat_Test: http://45.114.143.159:8080/diphonline_test
    */
    //window.cfgApiBaseUrl = cfgApiBaseUrl;(index.html)


  //http://localhost:8080/diphonline/getcountriesinfo
  private _country_details_api:string = this.BASE_URL+"/getcountriesinfo"; 
  private _state_details_api:string = this.BASE_URL+"/getstatesinfo/";
  private _state_for_district:string = this.BASE_URL+"/getstatesfordistrictsinfo?";
  private _region_details_api:string = this.BASE_URL+"/getregionsinfo/";
  private _authenticate_user_api:string = this.BASE_URL+"/authenticateuser?";
  
  private _district_details_api:string = this.BASE_URL+"/getsdistrictsinfo?"; 
  private _cycle_details_api:string = this.BASE_URL+"/getdcyclesinfo"; 

  private _district_detail_from_id_api:string = this.BASE_URL+"/getdistrictinfofromid/";
  private _cycle_detail_from_id_api:string = this.BASE_URL+"/getcycleinfofromid/";

  private _update_existing_user:string = this.BASE_URL+"/updateuser/";
  private _create_new_user:string = this.BASE_URL+"/createnewuser/";
  private _get_all_users_of_district:string = this.BASE_URL+"/viewExistingUsers";
  private _delete_user:string = this.BASE_URL+"/deleteuser";

  private _create_new_district:string = this.BASE_URL+"/createnewdistrict/";  
  private _delete_district:string = this.BASE_URL+"/deletedistrict"; 

  private _forgotPassword:string = this.BASE_URL+"/forgotPassword?"; 
  private _changePassword:string = this.BASE_URL+"/changePassword?";

  private source:string = "ONLINE_SOURCE_WEB";

  private _download_district_forms_data:string = this.BASE_URL+"/downloadOfflineDataVersion2";

  constructor(private http:HttpClient) { }

  public _download_district_forms_data_function(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>
  { 
    return this.http.get(this._download_district_forms_data+"?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id).pipe(catchError(this.errorHandler));
  }

  public uploadOfflineFormData(offlineData:any,login_district:string,login_cycle:string,login_year:string,login_userid:string): Observable<any> {
    
    offlineData.district = login_district;
    offlineData.cycle = login_cycle;
    offlineData.year = login_year;
    offlineData.userid = login_userid;
    return this.http.post(this.BASE_URL+"/saveOnlineDataVersion2", offlineData, {responseType: 'text'})
    .pipe(catchError(this.errorHandler));
  }

  public getOfflineFile(): Observable<any>
  { 
    return this.http.get(this.BASE_URL+"/download", {responseType:'blob'})
    .pipe(catchError(this.errorHandler));
  }

  public lockcurrentcycle(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>
  { 
    return this.http.get(this.BASE_URL+"/lockcurrentcycle?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  } 


  public getuserallowedstatus(username:string,district_id:string,cycle_id:string,year:string): Observable<any>
  { 

    return this.http.get(this.BASE_URL+"/getcurrentuserallowedstatus?username="+username+"&district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year)
    .pipe(catchError(this.errorHandler));
  } 


  public getcyclelockstatus(district_id:string,cycle_id:string,year:string): Observable<any>
  { 
    return this.http.get(this.BASE_URL+"/getcyclelockstatus?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year)
    .pipe(catchError(this.errorHandler));
  } 


  public unlockcurrentcycle(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>
  { 
    return this.http.get(this.BASE_URL+"/unlockcurrentcycle?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  } 

  public updateUser(editUser_form:any): Observable<any>
  { 
    return this.http.post(this._update_existing_user,editUser_form)
    .pipe(catchError(this.errorHandler));
  }

  public createUser(newUser_form:any, user:string, district:string): Observable<any>
  {    
    newUser_form.loggedUser = user;
    newUser_form.loggedUserDistrict = district;   
    return this.http.post(this._create_new_user,newUser_form)
    .pipe(catchError(this.errorHandler));
  }

  public createDistrict(newDistrict_form:any): Observable<any>
  {    
    return this.http.post(this._create_new_district,newDistrict_form)
    .pipe(catchError(this.errorHandler));
  }  

  
  public getAllUsersOfDistrict(user:string, district:string): Observable<any>
  {    
    let obj:any = {};
    obj.loggedUser = user;
    obj.loggedUserDistrict = district;
    return this.http.post(this._get_all_users_of_district, obj)
    .pipe(catchError(this.errorHandler));
  }

  public deleteUser(username:string, loggedUser:string): Observable<any>
  {    
    let obj:any = {};
    obj.username = username;
    obj.loggedUser = loggedUser;
    return this.http.post(this._delete_user, obj)
    .pipe(catchError(this.errorHandler));
  }

  public deleteDistrict(district_name:string): Observable<any>
  {    
    let obj:any = {};
    obj.district_name = district_name;
    return this.http.post(this._delete_district, obj)
    .pipe(catchError(this.errorHandler));
  }


  public getDistrictDetailsFromId(id:string): Observable<District>
  {
    return this.http.get<District>(this._district_detail_from_id_api+id)
    .pipe(catchError(this.errorHandler));
  }

  public getCycleDetailsFromId(id:string): Observable<Cycle>
  {        
    return this.http.get<Cycle>(this._cycle_detail_from_id_api+id)
    .pipe(catchError(this.errorHandler));
    
  }

  public getCountryDetails(): Observable<Country[]> 
  {
    //http://localhost:8080/diphonline/getcountriesinfo
    return this.http.get<Country[]>(this._country_details_api)
    .pipe(catchError(this.errorHandler));
  }

  public getRegionDetails(id:string): Observable<Region[]>
  {        
    return this.http.get<Region[]>(this._region_details_api+id)
    .pipe(catchError(this.errorHandler));    
  }

  public getCurrentRegionDetails(id:string): Observable<Region>
  {        
    return this.http.get<Region>(this.BASE_URL+"/getregioninfo/"+id)
    .pipe(catchError(this.errorHandler));    
  }

  public getStateofDistrictDetails(obj:any): Observable<any>
  {        
    return this.http.get(this._state_for_district+"district_id="+obj[0])
    .pipe(catchError(this.errorHandler));
    
  }

  public getStateDetails(id:string): Observable<State[]>
  {        
    return this.http.get<State[]>(this._state_details_api+id)
    .pipe(catchError(this.errorHandler));
    
  }

  public changePassword(changepassword_form:any): Observable<any>
  {        
    return this.http.post(this._changePassword,changepassword_form)
    .pipe(catchError(this.errorHandler));
  }

  public forgotPassword(email:string): Observable<any>
  {        
    return this.http.get(this._forgotPassword+"email="+email)
    .pipe(catchError(this.errorHandler));
  }

  public authenticateUser(username:string, pwd:string): Observable<User>
  {  
    var authString = ('Authorization: Basic ' + btoa(username + ':' + pwd));
    const headers = new HttpHeaders(authString);
    var user = {
      "user_nm": username,
      "user_pass": pwd
    };  
    this.url = this._authenticate_user_api;
    return this.http.post<User>(this.url, user, { headers }).pipe(catchError(this.errorHandler));
  }

  public getDistrictDetails(id:string): Observable<District[]>
  {
    return this.http.get<District[]>(this._district_details_api+"state_id="+id)
    .pipe(catchError(this.errorHandler));
  }

  public getCycleDetails(): Observable<Cycle[]>
  {
    return this.http.get<Cycle[]>(this._cycle_details_api)
    .pipe(catchError(this.errorHandler));
  }

  errorHandler(error:HttpErrorResponse){
       return throwError(error.message || "Server Error");
  }

  public deleteSupplementaryForm1ADetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/deleteSupplementaryForm1A?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public deleteForm5FollowupDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/deleteForm5Followup?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public deleteForm4PlanDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/deleteForm4Plan?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }


  public deleteForm1BDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/deleteForm1B?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public deleteForm2EngageDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/deleteForm2Engage?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public deleteForm3DefineDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/deleteForm3Define?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public deleteForm1ADetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/deleteForm1A?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getSavedSupplementaryForm1ADetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewSupplementaryForm1A?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getSavedForm5FollowupDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewForm5Followup?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getSavedForm4PlanDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewForm4Plan?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getInEditForm3DefineDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewInEditForm3Define?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getSavedForm3DefineDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewForm3Define?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }
  
  public getSavedForm2EngageDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewForm2Engage?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getIndicatorsList(): Observable<any>{
    return this.http.get(this.BASE_URL+"/getIndicatorsList")
    .pipe(catchError(this.errorHandler));
  }

  public getSavedForm1BDetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewForm1B?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getSavedForm1ADetails(district_id:string,cycle_id:string,year:string,user_id:string): Observable<any>{
    return this.http.get(this.BASE_URL+"/viewForm1A?district_id="+district_id+"&cycle_id="+cycle_id+"&year="+year+"&user_id="+user_id)
    .pipe(catchError(this.errorHandler));
  }

  public getVerified_by_names(): Observable<any>{
    return this.http.get(this.BASE_URL+"/getVerifiedByNamesList")
    .pipe(catchError(this.errorHandler));
  }
  
  public savecontactdetails(contact:any): Observable<any> {
    return this.http.post(this.BASE_URL+"/saveContactDetails",contact)
    .pipe(catchError(this.errorHandler));
  }

  public savefeedbackdetails(formfeedback:any): Observable<any> {
    return this.http.post(this.BASE_URL+"/saveFeedbackDetails",formfeedback)
    .pipe(catchError(this.errorHandler));
  }

  public saveform1bdetails(form1bObj:any,total_coverage_indi:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form1bObj.total_coverage_indi = total_coverage_indi;
    //console.log("\n\nForm - 1B : "+JSON.stringify(form1bObj)+ "\n\n");
    form1bObj.district = login_district;
    form1bObj.cycle = login_cycle;
    form1bObj.year = login_year;
    form1bObj.userid = login_userid;
    
    form1bObj.completed = completed;
    form1bObj.source = this.source;
    //console.log("form1bObj : "+JSON.stringify(form1bObj));
    return this.http.post(this.BASE_URL+"/saveform1bdetails",form1bObj)
    .pipe(catchError(this.errorHandler));
  }

  public savesupplemmentaryform1aDetails(form1aObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    //console.log("\n\nForm - Supplementry : "+JSON.stringify(form1aObj)+ "\n\n");
    form1aObj.district = login_district;
    form1aObj.cycle = login_cycle;
    form1aObj.year = login_year;
    form1aObj.userid = login_userid;
    form1aObj.completed = completed;
    form1aObj.source = this.source;
    
    return this.http.post(this.BASE_URL+"/savesupplemmentaryform1aDetails",form1aObj)
    .pipe(catchError(this.errorHandler));
  }

  public saveform5followupDetails(form1aObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    //console.log("\n\nForm - Followup : "+JSON.stringify(form1aObj)+ "\n\n");
    form1aObj.district = login_district;
    form1aObj.cycle = login_cycle;
    form1aObj.year = login_year;
    form1aObj.userid = login_userid;
    form1aObj.completed = completed;
    form1aObj.source = this.source;
    
    return this.http.post(this.BASE_URL+"/saveform5followupDetails",form1aObj)
    .pipe(catchError(this.errorHandler));
  }

  public saveform4planDetails(form1aObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    //console.log("\n\nForm - Plan : "+JSON.stringify(form1aObj)+ "\n\n");
    form1aObj.district = login_district;
    form1aObj.cycle = login_cycle;
    form1aObj.year = login_year;
    form1aObj.userid = login_userid;
    form1aObj.completed = completed;
    form1aObj.source = this.source;
    
    return this.http.post(this.BASE_URL+"/saveform4planDetails",form1aObj)
    .pipe(catchError(this.errorHandler));
  }


  public saveform3defineDetails(form1aObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    //console.log("\n\nForm - Define : "+JSON.stringify(form1aObj)+ "\n\n");
    form1aObj.district = login_district;
    form1aObj.cycle = login_cycle;
    form1aObj.year = login_year;
    form1aObj.userid = login_userid;
    form1aObj.completed = completed;
    form1aObj.source = this.source;
    
    return this.http.post(this.BASE_URL+"/saveform3defineDetails",form1aObj)
    .pipe(catchError(this.errorHandler));
  }

  public saveform2engageDetails(form1aObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    //console.log("\n\nForm - Enngage : "+JSON.stringify(form1aObj)+ "\n\n");
    form1aObj.district = login_district;
    form1aObj.cycle = login_cycle;
    form1aObj.year = login_year;
    form1aObj.userid = login_userid;
    form1aObj.completed = completed;
    form1aObj.source = this.source;
    
    return this.http.post(this.BASE_URL+"/saveform2engageDetails",form1aObj)
    .pipe(catchError(this.errorHandler));
    
  }


  public saveform1aDetails(form1aObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,partial_save:string): Observable<any> {
    
    //console.log("\n\nForm - 1A : "+JSON.stringify(form1aObj)+ "\n\n");
    form1aObj.district = login_district;
    form1aObj.cycle = login_cycle;
    form1aObj.year = login_year;
    form1aObj.userid = login_userid;
    form1aObj.completed = partial_save;
    form1aObj.source = this.source;
    
    return this.http.post(this.BASE_URL+"/saveform1aDetails",form1aObj)
    .pipe(catchError(this.errorHandler)); 
  }

  public autofil_form1A_form1B(login_district:string,login_cycle:string,login_year:string,login_userid:string): Observable<any> {
    
    //console.log("\n\nForm - 1A : "+JSON.stringify(form1aObj)+ "\n\n");
    let basic_form_information = {
      district: login_district,
      cycle: login_cycle,
      year: login_year,
      userid: login_userid,
      source: this.source
    };

    return this.http.post(this.BASE_URL+"/autofillForm1A_Form1B", basic_form_information)
    .pipe(catchError(this.errorHandler)); 
  }

  public editUpdateSupplementaryForm1ADetails(form1bObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    //alert("Called data = "+form1aObj["numberOfTickets"]);
    form1bObj.district = login_district;
    form1bObj.cycle = login_cycle;
    form1bObj.year = login_year;
    form1bObj.userid = login_userid;
    form1bObj.completed = completed;
    form1bObj.source = this.source;
    return this.http.post(this.BASE_URL+"/editUpdateSupplementaryForm1ADetails",form1bObj)
    .pipe(catchError(this.errorHandler));
  }

  public updateSelectedActionIndicator(IndicatorBean:any): Observable<any> { 
    return this.http.post(this.BASE_URL+"/updateSelectedActionIndicator",IndicatorBean) 
    .pipe(catchError(this.errorHandler));
  }

  public updateSelectedOptionalIndicator(IndicatorBean:any): Observable<any> {
    return this.http.post(this.BASE_URL+"/updateSelectedOptionalIndicator",IndicatorBean) 
    .pipe(catchError(this.errorHandler));
  }

  public updateSelectedIndicator(IndicatorBean:any): Observable<any> {
    return this.http.post(this.BASE_URL+"/updateSelectedIndicator",IndicatorBean)
    .pipe(catchError(this.errorHandler));
  }

  public createNewCategorizedIndicator(IndicatorBean:any): Observable<any> {
    return this.http.post(this.BASE_URL+"/createNewCategorizedIndicator",IndicatorBean)
    .pipe(catchError(this.errorHandler));
  }

  public createNewIndicator(IndicatorBean:any): Observable<any> {
    return this.http.post(this.BASE_URL+"/createNewIndicator",IndicatorBean)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm5FollowUpDetails(form1bObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form1bObj.district = login_district;
    form1bObj.cycle = login_cycle;
    form1bObj.year = login_year;
    form1bObj.userid = login_userid;
    form1bObj.completed = completed;
    form1bObj.source = this.source;
    return this.http.post(this.BASE_URL+"/editUpdateForm5FollowUp",form1bObj)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm4PlanDetails(form1bObj:any,total_coverage_indi:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form1bObj.district = login_district;
    form1bObj.cycle = login_cycle;
    form1bObj.year = login_year;
    form1bObj.userid = login_userid;
    form1bObj.total_coverage_indi = total_coverage_indi;
    form1bObj.completed = completed;
    form1bObj.source = this.source;
    return this.http.post(this.BASE_URL+"/editUpdateForm4Plan",form1bObj)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm4PlanWhenFollowUpFilled(form1bObj:any,total_coverage_indi:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form1bObj.district = login_district;
    form1bObj.cycle = login_cycle;
    form1bObj.year = login_year;
    form1bObj.userid = login_userid;
    form1bObj.total_coverage_indi = total_coverage_indi;
    form1bObj.completed = completed;
    form1bObj.source = this.source;
    return this.http.post(this.BASE_URL+"/editUpdateForm4PlanWhenFollowUpFilled",form1bObj)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm3DefineDetails(form3Obj:any,
   
    login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form3Obj.district = login_district;
    form3Obj.cycle = login_cycle;
    form3Obj.year = login_year;
    form3Obj.userid = login_userid;
    form3Obj.completed = completed;
    form3Obj.source = this.source;
   
    return this.http.post(this.BASE_URL+"/editUpdateForm3Define",form3Obj)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm3DefineWhenPlanFilled(form3Obj:any,
   
    login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form3Obj.district = login_district;
    form3Obj.cycle = login_cycle;
    form3Obj.year = login_year;
    form3Obj.userid = login_userid;
    form3Obj.completed = completed;
    form3Obj.source = this.source;
   
    return this.http.post(this.BASE_URL+"/editUpdateForm3DefineWhenPlanFilled",form3Obj)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm1BDetails(form1bObj:any,total_coverage_indi:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form1bObj.district = login_district;
    form1bObj.cycle = login_cycle;
    form1bObj.year = login_year;
    form1bObj.userid = login_userid;
    form1bObj.completed = completed;
    form1bObj.source = this.source;
    //form1bObj.total_coverage_indi = total_coverage_indi;
    return this.http.post(this.BASE_URL+"/editUpdateForm1B",form1bObj)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm1BWhenForm2Saved(form1bObj:any,total_coverage_indi:any,login_district:string,login_cycle:string,login_year:string,login_userid:string,completed:string): Observable<any> {
    
    form1bObj.district = login_district;
    form1bObj.cycle = login_cycle;
    form1bObj.year = login_year;
    form1bObj.userid = login_userid;
    form1bObj.completed = completed;
    form1bObj.source = this.source;
    //form1bObj.total_coverage_indi = total_coverage_indi;
    return this.http.post(this.BASE_URL+"/editUpdateForm1BWhenForm2Saved",form1bObj)
    .pipe(catchError(this.errorHandler));
  }

  public editUpdateForm2EngageDetails(form2Obj:any,
    definingprimaryrole_array_insert:any,
    efforttoaddressissue_array_insert:any,
    enhanceefficiency_array_insert:any,
    themeleadbydpt_array_insert:any,login_district:string,login_cycle:string,login_year:string,login_userid:string, completed:string): Observable<any> {
    
    form2Obj.district = login_district;
    form2Obj.cycle = login_cycle;
    form2Obj.year = login_year;
    form2Obj.userid = login_userid;
    //form2Obj.definingprimaryrole_array_insert = definingprimaryrole_array_insert;
    let i=0;
    for(i=0; i<definingprimaryrole_array_insert.length; i++){
      form2Obj.definingprimaryrole_array.push(definingprimaryrole_array_insert[i]);
    }
    //form2Obj.efforttoaddressissue_array_insert = efforttoaddressissue_array_insert;
    for(i=0; i<efforttoaddressissue_array_insert.length; i++){
      form2Obj.efforttoaddressissue_array.push(efforttoaddressissue_array_insert[i]);
    }
    //form2Obj.enhanceefficiency_array_insert = enhanceefficiency_array_insert;
    for(i=0; i<enhanceefficiency_array_insert.length; i++){
      form2Obj.enhanceefficiency_array.push(enhanceefficiency_array_insert[i]);
    }
    //form2Obj.themeleadbydpt_array_insert = themeleadbydpt_array_insert;
    for(i=0; i<themeleadbydpt_array_insert.length; i++){
      form2Obj.themeleadbydpt_array.push(themeleadbydpt_array_insert[i]);
    }
    form2Obj.completed = completed;
    form2Obj.source = this.source;

    //console.log("form2Obj : "+JSON.stringify(form2Obj));

    //return null;
    
    return this.http.post(this.BASE_URL+"/editUpdateForm2Engage",form2Obj)
    .pipe(catchError(this.errorHandler));
    
  }

  public editUpdateForm2EngageWhen3Filled(form2Obj:any,
    definingprimaryrole_array_insert:any,
    efforttoaddressissue_array_insert:any,
    enhanceefficiency_array_insert:any,
    themeleadbydpt_array_insert:any,login_district:string,login_cycle:string,login_year:string,login_userid:string, completed:string): Observable<any> {
    
    form2Obj.district = login_district;
    form2Obj.cycle = login_cycle;
    form2Obj.year = login_year;
    form2Obj.userid = login_userid;

    form2Obj.definingprimaryrole_array_insert = definingprimaryrole_array_insert;
    
    form2Obj.efforttoaddressissue_array_insert = efforttoaddressissue_array_insert;
    
    form2Obj.enhanceefficiency_array_insert = enhanceefficiency_array_insert;
    
    form2Obj.themeleadbydpt_array_insert = themeleadbydpt_array_insert;
    
    form2Obj.completed = completed;
    form2Obj.source = this.source;

    //console.log("form2Obj : "+JSON.stringify(form2Obj));

    //return null;
    
    return this.http.post(this.BASE_URL+"/editUpdateForm2EngageWhen3Filled",form2Obj)
    .pipe(catchError(this.errorHandler));
    
  }



  public editUpdateForm1ADetails(form1aObj:any,login_district:string,login_cycle:string,login_year:string,login_userid:string, completed:string): Observable<any> {
    
    form1aObj.district = login_district;
    form1aObj.cycle = login_cycle;
    form1aObj.year = login_year;
    form1aObj.userid = login_userid;
    form1aObj.completed = completed;
    form1aObj.source = this.source;
   
    return this.http.post(this.BASE_URL+"/editUpdateForm1A",form1aObj)
    .pipe(catchError(this.errorHandler));
  }

  public getAllIndicators(): Observable<any>{
    return this.http.get(this.BASE_URL+"/getIndicatorsList")
    .pipe(catchError(this.errorHandler));
  }

  public getAllOptionalIndicators(): Observable<any>{
    return this.http.get(this.BASE_URL+"/getOptionalIndicatorsList")
    .pipe(catchError(this.errorHandler));
  }

   
}
