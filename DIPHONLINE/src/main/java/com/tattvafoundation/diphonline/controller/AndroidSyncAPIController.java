package com.tattvafoundation.diphonline.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.APPLoginBean;
import com.tattvafoundation.diphonline.model.AllFormsDataFetchFromAndroidBean;
import com.tattvafoundation.diphonline.model.Areas_of_Indicators_List;
import com.tattvafoundation.diphonline.model.Form1AConsumeDataFromAndroidBean;
import com.tattvafoundation.diphonline.model.Form1ASendAllDataToAndroidBean;
import com.tattvafoundation.diphonline.model.Form1ASourceIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form1BConsumeDataFromAndroidBean;
import com.tattvafoundation.diphonline.model.Form2EnagageSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form2EngagestakeIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form3DefineActionIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form3DefineSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form4PlanSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpSendAllDataBean;
import com.tattvafoundation.diphonline.model.FormSupplementarySendAllDataBean;
import com.tattvafoundation.diphonline.model.I_SendAndroidAllForms;
import com.tattvafoundation.diphonline.model.SendAndroidAllFormsCorrectResponse_NEW;
import com.tattvafoundation.diphonline.model.SendAndroidAllFormsSynchedDataBean;
import com.tattvafoundation.diphonline.model.SendAndroidAuthenricationErrorResponse_NEW;
import com.tattvafoundation.diphonline.model.SendAndroidForm1BSynchedDataBean;
import com.tattvafoundation.diphonline.model.SendAndroidSynchedDataBean;
import com.tattvafoundation.diphonline.model.SendAndroidSynchedDataBeanNewFormat;
import com.tattvafoundation.diphonline.model.UserCredentialsFromAndroidBean;
import com.tattvafoundation.diphonline.model.UserDistrictCycleYearList;
import com.tattvafoundation.diphonline.repository.Form1ADAO;
import com.tattvafoundation.diphonline.repository.Form1BDAO;
import com.tattvafoundation.diphonline.repository.Form2EngageDAO;
import com.tattvafoundation.diphonline.repository.Form3DefineDAO;
import com.tattvafoundation.diphonline.repository.Form4PlanDAO;
import com.tattvafoundation.diphonline.repository.Form5FollowupDAO;
import com.tattvafoundation.diphonline.repository.LoginDAO;
import com.tattvafoundation.diphonline.repository.SupplementaryForm1ADAO;

@CrossOrigin(origins = "*")
@RestController
public class AndroidSyncAPIController {

	@Autowired
	public Form1ADAO dao;

	@Autowired
	public LoginDAO dao2;

	@Autowired
	public Form1BDAO dao3;

	@Autowired
	public Form2EngageDAO dao4;

	@Autowired
	public Form3DefineDAO dao5;

	@Autowired
	public Form4PlanDAO dao6;

	@Autowired
	public Form5FollowupDAO dao7;

	@Autowired
	public SupplementaryForm1ADAO dao8;
	
	@Autowired
	public LoginDAO dao9; 

	// URL: http://localhost:8080/diphonline/getAllFormDatafromServerToApp_Authenticated_NEW
	@RequestMapping(path = "/getAllFormDatafromServerToApp_Authenticated_NEW", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public I_SendAndroidAllForms getAllFormDatafromServerToApp_Authenticated_NEW(
			@RequestBody UserCredentialsFromAndroidBean model) {
		
		APPLoginBean instance = dao9.isauthenticatedForGeographicalCombo_NEW(model);
		
		UserDistrictCycleYearList prototype = dao9.getAllowedDistrictsCycleYear_NEW(model);
		
		List<Areas_of_Indicators_List> get_areas_list = dao3.getIndicatorAreasList();
		
		if("100".equals(instance.getError_code())) { 
			SendAndroidAuthenricationErrorResponse_NEW response = new SendAndroidAuthenricationErrorResponse_NEW();
			response.setError_code("100");
			response.setMessage("User not allowed for this Geograpical Combination");
			response.setDistrictList(prototype.getDistrictList());
			response.setCyclesList(prototype.getCyclesList());
			response.setYearList(prototype.getYearList());
			response.setAreas_list(get_areas_list); 
			return response; 
		}
		else {
			//go for more
		}

		SendAndroidAllFormsCorrectResponse_NEW response = new SendAndroidAllFormsCorrectResponse_NEW();
		
		response.setDistrictList(prototype.getDistrictList());
		response.setCyclesList(prototype.getCyclesList());
		response.setYearList(prototype.getYearList()); 
		response.setAreas_list(get_areas_list);
		response.setAreas_Id_Indicators_map_list(dao3.getIndicatorsList().getAreas_Id_Indicators_map_list());

		response.setOptional_indicators_list(dao3.getOptIndicators().getOptional_indicators_list());

		try {
			Form1ASendAllDataToAndroidBean obj = dao
					.retrieveForm1A_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model.getCountry_id(),
							model.getRegion_id(), model.getProvince_id(), model.getDistrict_id(), model.getUser_id(),model.getLoggedinUserId());

			List<Form1ASendAllDataToAndroidBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1a(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form1ASendAllDataToAndroidBean> templist = new ArrayList<>();
			templist.add(new Form1ASendAllDataToAndroidBean());
			response.setForm1a(templist);
			response.getForm1a().get(0).setFormA(new ArrayList<>());
			response.getForm1a().get(0).setFormAdocument(new ArrayList<>());
			response.getForm1a().get(0).setError_code("100");
			response.getForm1a().get(0).setMessage("No Data Found");
		}

		try {
			SendAndroidForm1BSynchedDataBean obj = dao3
					.retrieveForm1B_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model.getCountry_id(),
							model.getRegion_id(), model.getProvince_id(), model.getDistrict_id(), model.getUser_id(),model.getLoggedinUserId());
			List<SendAndroidForm1BSynchedDataBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1b(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<SendAndroidForm1BSynchedDataBean> templist = new ArrayList<>();
			templist.add(new SendAndroidForm1BSynchedDataBean());
			response.setForm1b(templist);
			response.getForm1b().get(0).setFormB(new ArrayList<>());
			response.getForm1b().get(0).setFormBngo(new ArrayList<>());
			response.getForm1b().get(0).setFormBstakeholders(new ArrayList<>());
			response.getForm1b().get(0).setFormBcoverageIndicators(new ArrayList<>());
			response.getForm1b().get(0).setFormBinfra(new ArrayList<>());
			response.getForm1b().get(0).setFormBfinance(new ArrayList<>());
			response.getForm1b().get(0).setFormBsupplies(new ArrayList<>());
			response.getForm1b().get(0).setFormBtechnology(new ArrayList<>());
			response.getForm1b().get(0).setFormBhumanresource(new ArrayList<>());
			response.getForm1b().get(0).setError_code("100");
			response.getForm1b().get(0).setMessage("No Data Found");
		}

		try {
			List<Form2EnagageSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao4.retrieveForm2Engage_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm2(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form2EnagageSendAllDataBean> templist = new ArrayList<>();
			templist.add(new Form2EnagageSendAllDataBean());
			response.setForm2(templist);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		}

		try {
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao5.retrieveForm3Define_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm3(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form3DefineSendAllDataBean());
			response.setForm3(listing);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		}

		try {
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao6.retrieveForm4Plan_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm4(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form4PlanSendAllDataBean());
			response.setForm4(listing);
			response.getForm4().get(0).setForm4Plan(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanActionPoints(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanIndicators(new ArrayList<>());
			response.getForm4().get(0).setError_code("100");
			response.getForm4().get(0).setMessage("No Data Found");
		}

		try {
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao7.retrieveForm5FollowUp_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm5(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form5FollowUpSendAllDataBean());
			response.setForm5(listing);
			response.getForm5().get(0).setForm5followup(new ArrayList<>());
			response.getForm5().get(0).setForm5partamajorholder(new ArrayList<>());
			response.getForm5().get(0).setForm5partaindicator(new ArrayList<>());
			response.getForm5().get(0).setForm5partb(new ArrayList<>());
			response.getForm5().get(0).setForm5partbindiprogress(new ArrayList<>());
			response.getForm5().get(0).setError_code("100");
			response.getForm5().get(0).setMessage("No Data Found");
		}

		try {
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(dao8.retrieveFormSupplementaryA_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setFormsupplementary(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(new FormSupplementarySendAllDataBean());
			response.setFormsupplementary(listing);
			response.getFormsupplementary().get(0).setExtractdata(new ArrayList<>());
			response.getFormsupplementary().get(0).setExtractdataaction(new ArrayList<>());
			response.getFormsupplementary().get(0).setError_code("100");
			response.getFormsupplementary().get(0).setMessage("No Data Found");
		}

		int flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0;

		if (response.getForm2() == null || (response.getForm2().size() == 0)) {
			List<Form2EnagageSendAllDataBean> l = new ArrayList<>();
			l.add(new Form2EnagageSendAllDataBean());
			response.setForm2(l);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		} else {
			flag2++;
		}

		if (response.getForm3() == null || response.getForm3().size() == 0) {
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form3DefineSendAllDataBean());
			response.setForm3(listing);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		} else {
			flag3++;
		}

		if (response.getForm4() == null || response.getForm4().size() == 0) {
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form4PlanSendAllDataBean());
			response.setForm4(listing);
			response.getForm4().get(0).setForm4Plan(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanActionPoints(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanIndicators(new ArrayList<>());
			response.getForm4().get(0).setError_code("100");
			response.getForm4().get(0).setMessage("No Data Found");
		} else {
			flag4++;
		}

		if (response.getForm5() == null || response.getForm5().size() == 0) {
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form5FollowUpSendAllDataBean());
			response.setForm5(listing);
			response.getForm5().get(0).setForm5followup(new ArrayList<>());
			response.getForm5().get(0).setForm5partamajorholder(new ArrayList<>());
			response.getForm5().get(0).setForm5partaindicator(new ArrayList<>());
			response.getForm5().get(0).setForm5partb(new ArrayList<>());
			response.getForm5().get(0).setError_code("100");
			response.getForm5().get(0).setMessage("No Data Found");
		} else {
			flag5++;
		}

		if (response.getFormsupplementary() == null || response.getFormsupplementary().size() == 0) {
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(new FormSupplementarySendAllDataBean());
			response.setFormsupplementary(listing);
			response.getFormsupplementary().get(0).setExtractdata(new ArrayList<>());
			response.getFormsupplementary().get(0).setExtractdataaction(new ArrayList<>());
			response.getFormsupplementary().get(0).setError_code("100");
			response.getFormsupplementary().get(0).setMessage("No Data Found");
		}

		// Check if all the forms for current district are empty
		if (flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {
			response.setError_code("100");
			response.setMessage("Server Error");
		} else {
			response.setError_code("200");
			response.setMessage("Sending All forms Data");
		}

		return response;

	

	}


	// URL: http://localhost:8080/diphonline/getAllFormDatafromServerToApp_Authenticated
	@RequestMapping(path = "/getAllFormDatafromServerToApp_Authenticated", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendAndroidAllFormsSynchedDataBean getAllFormDatafromServerToApp_Authenticated(
			@RequestBody UserCredentialsFromAndroidBean model) {

		SendAndroidAllFormsSynchedDataBean response = new SendAndroidAllFormsSynchedDataBean();

		response.setAreas_Id_Indicators_map_list(dao3.getIndicatorsList().getAreas_Id_Indicators_map_list());

		response.setOptional_indicators_list(dao3.getOptIndicators().getOptional_indicators_list());

		try {
			Form1ASendAllDataToAndroidBean obj = dao
					.retrieveForm1A_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model.getCountry_id(),
							model.getRegion_id(), model.getProvince_id(), model.getDistrict_id(), model.getUser_id(),model.getLoggedinUserId());

			List<Form1ASendAllDataToAndroidBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1a(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form1ASendAllDataToAndroidBean> templist = new ArrayList<>();
			templist.add(new Form1ASendAllDataToAndroidBean());
			response.setForm1a(templist);
			response.getForm1a().get(0).setFormA(new ArrayList<>());
			response.getForm1a().get(0).setFormAdocument(new ArrayList<>());
			response.getForm1a().get(0).setError_code("100");
			response.getForm1a().get(0).setMessage("No Data Found");
		}

		try {
			SendAndroidForm1BSynchedDataBean obj = dao3
					.retrieveForm1B_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model.getCountry_id(),
							model.getRegion_id(), model.getProvince_id(), model.getDistrict_id(), model.getUser_id(),model.getLoggedinUserId());
			List<SendAndroidForm1BSynchedDataBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1b(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<SendAndroidForm1BSynchedDataBean> templist = new ArrayList<>();
			templist.add(new SendAndroidForm1BSynchedDataBean());
			response.setForm1b(templist);
			response.getForm1b().get(0).setFormB(new ArrayList<>());
			response.getForm1b().get(0).setFormBngo(new ArrayList<>());
			response.getForm1b().get(0).setFormBstakeholders(new ArrayList<>());
			response.getForm1b().get(0).setFormBcoverageIndicators(new ArrayList<>());
			response.getForm1b().get(0).setFormBinfra(new ArrayList<>());
			response.getForm1b().get(0).setFormBfinance(new ArrayList<>());
			response.getForm1b().get(0).setFormBsupplies(new ArrayList<>());
			response.getForm1b().get(0).setFormBtechnology(new ArrayList<>());
			response.getForm1b().get(0).setFormBhumanresource(new ArrayList<>());
			response.getForm1b().get(0).setError_code("100");
			response.getForm1b().get(0).setMessage("No Data Found");
		}

		try {
			List<Form2EnagageSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao4.retrieveForm2Engage_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm2(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form2EnagageSendAllDataBean> templist = new ArrayList<>();
			templist.add(new Form2EnagageSendAllDataBean());
			response.setForm2(templist);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		}

		try {
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao5.retrieveForm3Define_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm3(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form3DefineSendAllDataBean());
			response.setForm3(listing);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		}

		try {
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao6.retrieveForm4Plan_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm4(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form4PlanSendAllDataBean());
			response.setForm4(listing);
			response.getForm4().get(0).setForm4Plan(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanActionPoints(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanIndicators(new ArrayList<>());
			response.getForm4().get(0).setError_code("100");
			response.getForm4().get(0).setMessage("No Data Found");
		}

		try {
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao7.retrieveForm5FollowUp_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setForm5(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form5FollowUpSendAllDataBean());
			response.setForm5(listing);
			response.getForm5().get(0).setForm5followup(new ArrayList<>());
			response.getForm5().get(0).setForm5partamajorholder(new ArrayList<>());
			response.getForm5().get(0).setForm5partaindicator(new ArrayList<>());
			response.getForm5().get(0).setForm5partb(new ArrayList<>());
			response.getForm5().get(0).setForm5partbindiprogress(new ArrayList<>());
			response.getForm5().get(0).setError_code("100");
			response.getForm5().get(0).setMessage("No Data Found");
		}

		try {
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(dao8.retrieveFormSupplementaryA_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(model,model.getLoggedinUserId()));
			response.setFormsupplementary(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(new FormSupplementarySendAllDataBean());
			response.setFormsupplementary(listing);
			response.getFormsupplementary().get(0).setExtractdata(new ArrayList<>());
			response.getFormsupplementary().get(0).setExtractdataaction(new ArrayList<>());
			response.getFormsupplementary().get(0).setError_code("100");
			response.getFormsupplementary().get(0).setMessage("No Data Found");
		}

		int flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0;

		if (response.getForm2() == null || (response.getForm2().size() == 0)) {
			List<Form2EnagageSendAllDataBean> l = new ArrayList<>();
			l.add(new Form2EnagageSendAllDataBean());
			response.setForm2(l);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		} else {
			flag2++;
		}

		if (response.getForm3() == null || response.getForm3().size() == 0) {
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form3DefineSendAllDataBean());
			response.setForm3(listing);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		} else {
			flag3++;
		}

		if (response.getForm4() == null || response.getForm4().size() == 0) {
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form4PlanSendAllDataBean());
			response.setForm4(listing);
			response.getForm4().get(0).setForm4Plan(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanActionPoints(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanIndicators(new ArrayList<>());
			response.getForm4().get(0).setError_code("100");
			response.getForm4().get(0).setMessage("No Data Found");
		} else {
			flag4++;
		}

		if (response.getForm5() == null || response.getForm5().size() == 0) {
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form5FollowUpSendAllDataBean());
			response.setForm5(listing);
			response.getForm5().get(0).setForm5followup(new ArrayList<>());
			response.getForm5().get(0).setForm5partamajorholder(new ArrayList<>());
			response.getForm5().get(0).setForm5partaindicator(new ArrayList<>());
			response.getForm5().get(0).setForm5partb(new ArrayList<>());
			response.getForm5().get(0).setError_code("100");
			response.getForm5().get(0).setMessage("No Data Found");
		} else {
			flag5++;
		}

		if (response.getFormsupplementary() == null || response.getFormsupplementary().size() == 0) {
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(new FormSupplementarySendAllDataBean());
			response.setFormsupplementary(listing);
			response.getFormsupplementary().get(0).setExtractdata(new ArrayList<>());
			response.getFormsupplementary().get(0).setExtractdataaction(new ArrayList<>());
			response.getFormsupplementary().get(0).setError_code("100");
			response.getFormsupplementary().get(0).setMessage("No Data Found");
		}

		// Check if all the forms for current district are empty
		if (flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {
			response.setError_code("100");
			response.setMessage("Server Error");
		} else {
			response.setError_code("200");
			response.setMessage("Sending All forms Data");
		}

		return response;

	}

	// URL: http://localhost:8080/diphonline/getAllFormDatafromServerToApp
	@RequestMapping(path = "/getAllFormDatafromServerToApp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendAndroidAllFormsSynchedDataBean getAllFormDatafromServerToApp(
			@RequestBody UserCredentialsFromAndroidBean model) {

		SendAndroidAllFormsSynchedDataBean response = new SendAndroidAllFormsSynchedDataBean();

		response.setAreas_Id_Indicators_map_list(dao3.getIndicatorsList().getAreas_Id_Indicators_map_list());

		response.setOptional_indicators_list(dao3.getOptIndicators().getOptional_indicators_list());

		try {
			Form1ASendAllDataToAndroidBean obj = dao
					.retrieveForm1A_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(model.getCountry_id(),
							model.getRegion_id(), model.getProvince_id(), model.getDistrict_id(), model.getUser_id());

			List<Form1ASendAllDataToAndroidBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1a(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form1ASendAllDataToAndroidBean> templist = new ArrayList<>();
			templist.add(new Form1ASendAllDataToAndroidBean());
			response.setForm1a(templist);
			response.getForm1a().get(0).setFormA(new ArrayList<>());
			response.getForm1a().get(0).setFormAdocument(new ArrayList<>());
			response.getForm1a().get(0).setError_code("100");
			response.getForm1a().get(0).setMessage("No Data Found");
		}

		try {
			SendAndroidForm1BSynchedDataBean obj = dao3
					.retrieveForm1B_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(model.getCountry_id(),
							model.getRegion_id(), model.getProvince_id(), model.getDistrict_id(), model.getUser_id());
			List<SendAndroidForm1BSynchedDataBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1b(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<SendAndroidForm1BSynchedDataBean> templist = new ArrayList<>();
			templist.add(new SendAndroidForm1BSynchedDataBean());
			response.setForm1b(templist);
			response.getForm1b().get(0).setFormB(new ArrayList<>());
			response.getForm1b().get(0).setFormBngo(new ArrayList<>());
			response.getForm1b().get(0).setFormBstakeholders(new ArrayList<>());
			response.getForm1b().get(0).setFormBcoverageIndicators(new ArrayList<>());
			response.getForm1b().get(0).setFormBinfra(new ArrayList<>());
			response.getForm1b().get(0).setFormBfinance(new ArrayList<>());
			response.getForm1b().get(0).setFormBsupplies(new ArrayList<>());
			response.getForm1b().get(0).setFormBtechnology(new ArrayList<>());
			response.getForm1b().get(0).setFormBhumanresource(new ArrayList<>());
			response.getForm1b().get(0).setError_code("100");
			response.getForm1b().get(0).setMessage("No Data Found");
		}

		try {
			List<Form2EnagageSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao4.retrieveForm2Engage_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(model));
			response.setForm2(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form2EnagageSendAllDataBean> templist = new ArrayList<>();
			templist.add(new Form2EnagageSendAllDataBean());
			response.setForm2(templist);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		}

		try {
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao5.retrieveForm3Define_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(model));
			response.setForm3(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form3DefineSendAllDataBean());
			response.setForm3(listing);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		}

		try {
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao6.retrieveForm4Plan_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(model));
			response.setForm4(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form4PlanSendAllDataBean());
			response.setForm4(listing);
			response.getForm4().get(0).setForm4Plan(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanActionPoints(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanIndicators(new ArrayList<>());
			response.getForm4().get(0).setError_code("100");
			response.getForm4().get(0).setMessage("No Data Found");
		}

		try {
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao7.retrieveForm5FollowUp_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(model));
			response.setForm5(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form5FollowUpSendAllDataBean());
			response.setForm5(listing);
			response.getForm5().get(0).setForm5followup(new ArrayList<>());
			response.getForm5().get(0).setForm5partamajorholder(new ArrayList<>());
			response.getForm5().get(0).setForm5partaindicator(new ArrayList<>());
			response.getForm5().get(0).setForm5partb(new ArrayList<>());
			response.getForm5().get(0).setForm5partbindiprogress(new ArrayList<>());
			response.getForm5().get(0).setError_code("100");
			response.getForm5().get(0).setMessage("No Data Found");
		}

		try {
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(dao8.retrieveFormSupplementaryA_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(model));
			response.setFormsupplementary(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(new FormSupplementarySendAllDataBean());
			response.setFormsupplementary(listing);
			response.getFormsupplementary().get(0).setExtractdata(new ArrayList<>());
			response.getFormsupplementary().get(0).setExtractdataaction(new ArrayList<>());
			response.getFormsupplementary().get(0).setError_code("100");
			response.getFormsupplementary().get(0).setMessage("No Data Found");
		}

		int flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0;

		if (response.getForm2() == null || (response.getForm2().size() == 0)) {
			List<Form2EnagageSendAllDataBean> l = new ArrayList<>();
			l.add(new Form2EnagageSendAllDataBean());
			response.setForm2(l);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		} else {
			flag2++;
		}

		if (response.getForm3() == null || response.getForm3().size() == 0) {
			List<Form3DefineSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form3DefineSendAllDataBean());
			response.setForm3(listing);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		} else {
			flag3++;
		}

		if (response.getForm4() == null || response.getForm4().size() == 0) {
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form4PlanSendAllDataBean());
			response.setForm4(listing);
			response.getForm4().get(0).setForm4Plan(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanActionPoints(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanIndicators(new ArrayList<>());
			response.getForm4().get(0).setError_code("100");
			response.getForm4().get(0).setMessage("No Data Found");
		} else {
			flag4++;
		}

		if (response.getForm5() == null || response.getForm5().size() == 0) {
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form5FollowUpSendAllDataBean());
			response.setForm5(listing);
			response.getForm5().get(0).setForm5followup(new ArrayList<>());
			response.getForm5().get(0).setForm5partamajorholder(new ArrayList<>());
			response.getForm5().get(0).setForm5partaindicator(new ArrayList<>());
			response.getForm5().get(0).setForm5partb(new ArrayList<>());
			response.getForm5().get(0).setError_code("100");
			response.getForm5().get(0).setMessage("No Data Found");
		} else {
			flag5++;
		}

		if (response.getFormsupplementary() == null || response.getFormsupplementary().size() == 0) {
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(new FormSupplementarySendAllDataBean());
			response.setFormsupplementary(listing);
			response.getFormsupplementary().get(0).setExtractdata(new ArrayList<>());
			response.getFormsupplementary().get(0).setExtractdataaction(new ArrayList<>());
			response.getFormsupplementary().get(0).setError_code("100");
			response.getFormsupplementary().get(0).setMessage("No Data Found");
		}

		// Check if all the forms for current district are empty
		if (flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {
			response.setError_code("100");
			response.setMessage("Server Error");
		} else {
			response.setError_code("200");
			response.setMessage("Sending All forms Data");
		}

		return response;
	}

	// URL: http://localhost:8080/diphonline/getFormBDataFromServerToApp

	@RequestMapping(path = "/getFormBDataFromServerToApp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendAndroidForm1BSynchedDataBean getFormBDataFromServerToApp(
			@RequestBody UserCredentialsFromAndroidBean model) {

		SendAndroidForm1BSynchedDataBean response = new SendAndroidForm1BSynchedDataBean();

		String country_id = model.getCountry_id();
		String region_id = model.getRegion_id();
		String state_id = model.getProvince_id();
		String district_id = model.getDistrict_id();
		String user_id = model.getUser_id();

		try {

			SendAndroidForm1BSynchedDataBean obj = dao3
					.retrieveForm1B_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(country_id, region_id,
							state_id, district_id, user_id);

			response.setFormB(obj.getFormB());
			response.setFormBngo(obj.getFormBngo());
			response.setFormBstakeholders(obj.getFormBstakeholders());
			response.setFormBcoverageIndicators(obj.getFormBcoverageIndicators());
			response.setFormBinfra(obj.getFormBinfra());
			response.setFormBfinance(obj.getFormBfinance());
			response.setFormBsupplies(obj.getFormBsupplies());
			response.setFormBtechnology(obj.getFormBtechnology());
			response.setFormBhumanresource(obj.getFormBhumanresource());
			response.setError_code("200");
			response.setMessage("Sending Updated and Consumed Data");

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();

			response = new SendAndroidForm1BSynchedDataBean();
			response.setFormB(new ArrayList<>());
			response.setFormBngo(new ArrayList<>());
			response.setFormBstakeholders(new ArrayList<>());
			response.setFormBstakeholders(new ArrayList<>());
			response.setFormBcoverageIndicators(new ArrayList<>());
			response.setFormBinfra(new ArrayList<>());
			response.setFormBfinance(new ArrayList<>());
			response.setFormBsupplies(new ArrayList<>());
			response.setFormBtechnology(new ArrayList<>());
			response.setFormBhumanresource(new ArrayList<>());
			response.setError_code("100");
			response.setMessage("Server Error");
		}

		return response;
	}

	// URL: http://localhost:8080/diphonline/getFormADataFromServerToApp

	@RequestMapping(path = "/getFormADataFromServerToApp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendAndroidSynchedDataBeanNewFormat getFormADataFromServerToApp(
			@RequestBody UserCredentialsFromAndroidBean model) {

		String country_id = model.getCountry_id();
		String region_id = model.getRegion_id();
		String province_id = model.getProvince_id();
		String distrct_id = model.getDistrict_id();
		String user_id = model.getUser_id();

//		SendAndroidSynchedDataBean response = new SendAndroidSynchedDataBean();

		SendAndroidSynchedDataBeanNewFormat r = new SendAndroidSynchedDataBeanNewFormat();

		Form1ASendAllDataToAndroidBean result = new Form1ASendAllDataToAndroidBean();

		try {

			result = dao.retrieveForm1A_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(country_id, region_id,
					province_id, distrct_id, user_id);
			System.out.println(result);

			if (result.getFormA().size() != 0 && result.getFormAdocument().size() != 0) {
				r.setFormA(result.getFormA());
				r.setFormAdocument(result.getFormAdocument());
				r.setError_code("200");
				r.setMessage("Sending Updated and Consumed Data");
			} else {
				r = new SendAndroidSynchedDataBeanNewFormat();
				r.setFormA(new ArrayList<>());
				r.setFormAdocument(new ArrayList<>());
				r.setError_code("100");
				r.setMessage("No Data Found");
			}

		} catch (Exception e) {
			e.printStackTrace();

			r = new SendAndroidSynchedDataBeanNewFormat();
			r.setFormA(new ArrayList<>());
			r.setFormAdocument(new ArrayList<>());
			r.setError_code("100");
			r.setMessage("Server Error");
		}

		return r;
	}

	// URL: http://localhost:8080/diphonline/consumeAllFormDataFromAppToServer
	@RequestMapping(path = "/consumeAllFormDataFromAppToServer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendAndroidAllFormsSynchedDataBean consumeAllFormDataFromAppToServer(
			@RequestBody AllFormsDataFetchFromAndroidBean model) {

		SendAndroidAllFormsSynchedDataBean response = new SendAndroidAllFormsSynchedDataBean();

		response.setAreas_Id_Indicators_map_list(dao3.getIndicatorsList().getAreas_Id_Indicators_map_list());

		response.setOptional_indicators_list(dao3.getOptIndicators().getOptional_indicators_list());

		List<Form1ASourceIDDetailsBean> mapping = new ArrayList<>();

		try {
			Form1ASendAllDataToAndroidBean obj = dao.consumeForm1ASaveAndUpdateData(model);
			mapping = obj.getMapping();
			List<Form1ASendAllDataToAndroidBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1a(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<Form1ASendAllDataToAndroidBean> templist = new ArrayList<>();
			templist.add(new Form1ASendAllDataToAndroidBean());
			response.setForm1a(templist);
			response.getForm1a().get(0).setFormA(new ArrayList<>());
			response.getForm1a().get(0).setFormAdocument(new ArrayList<>());
			response.getForm1a().get(0).setError_code("100");
			response.getForm1a().get(0).setMessage("No Data Found");
		}

		try {

			SendAndroidForm1BSynchedDataBean obj = dao3.consumeForm1BSaveAndUpdateData(model, mapping);
			List<SendAndroidForm1BSynchedDataBean> listing = new ArrayList<>();
			listing.add(obj);
			response.setForm1b(listing);
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			List<SendAndroidForm1BSynchedDataBean> templist = new ArrayList<>();
			templist.add(new SendAndroidForm1BSynchedDataBean());
			response.setForm1b(templist);
			response.getForm1b().get(0).setFormB(new ArrayList<>());
			response.getForm1b().get(0).setFormBngo(new ArrayList<>());
			response.getForm1b().get(0).setFormBstakeholders(new ArrayList<>());
			response.getForm1b().get(0).setFormBcoverageIndicators(new ArrayList<>());
			response.getForm1b().get(0).setFormBinfra(new ArrayList<>());
			response.getForm1b().get(0).setFormBfinance(new ArrayList<>());
			response.getForm1b().get(0).setFormBsupplies(new ArrayList<>());
			response.getForm1b().get(0).setFormBtechnology(new ArrayList<>());
			response.getForm1b().get(0).setFormBhumanresource(new ArrayList<>());
			response.getForm1b().get(0).setError_code("100");
			response.getForm1b().get(0).setMessage("No Data Found");
		}

		List<Form2EngagestakeIDDetailsBean> mapping3 = new ArrayList<>();

		try {
			List<Form2EnagageSendAllDataBean> l = new ArrayList<>();

			Form2EnagageSendAllDataBean obj = dao4.consumeAllForm2EnagageSaveandUpdate(model);

			mapping3 = obj.getMapping();

			l.add(obj);
			response.setForm2(l);
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			List<Form2EnagageSendAllDataBean> l = new ArrayList<>();
			l.add(new Form2EnagageSendAllDataBean());
			response.setForm2(l);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		}

		List<Form3DefineActionIDDetailsBean> mapping2 = new ArrayList<>();
		try {
			List<Form3DefineSendAllDataBean> l = new ArrayList<>();
			Form3DefineSendAllDataBean obj = dao5.consumeAllForm3DefineSaveandUpdate(model);
			mapping2 = obj.getMapping();
			l.add(obj);
			response.setForm3(l);
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			List<Form3DefineSendAllDataBean> l = new ArrayList<>();
			l.add(new Form3DefineSendAllDataBean());
			response.setForm3(l);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		}

		try {
			List<Form4PlanSendAllDataBean> l = new ArrayList<>();
			l.add(dao6.consumeAllForm4PlanSaveandUpdate(mapping3, mapping2, model));
			response.setForm4(l);
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			List<Form4PlanSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form4PlanSendAllDataBean());
			response.setForm4(listing);
			response.getForm4().get(0).setForm4Plan(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanActionPoints(new ArrayList<>());
			response.getForm4().get(0).setForm4ActionPlanIndicators(new ArrayList<>());
			response.getForm4().get(0).setError_code("100");
			response.getForm4().get(0).setMessage("No Data Found");
		}

		try {
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(dao7.consumeAllForm5FollowUpSaveandUpdate(mapping2, model));
			response.setForm5(listing);
		} catch (Exception e) {
			// TODO: handle exception
//			e.printStackTrace();
			List<Form5FollowUpSendAllDataBean> listing = new ArrayList<>();
			listing.add(new Form5FollowUpSendAllDataBean());
			response.setForm5(listing);
			response.getForm5().get(0).setForm5followup(new ArrayList<>());
			response.getForm5().get(0).setForm5partamajorholder(new ArrayList<>());
			response.getForm5().get(0).setForm5partaindicator(new ArrayList<>());
			response.getForm5().get(0).setForm5partb(new ArrayList<>());
			response.getForm5().get(0).setForm5partbindiprogress(new ArrayList<>());
			response.getForm5().get(0).setError_code("100");
			response.getForm5().get(0).setMessage("No Data Found");
		}

		try {
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(dao8.consumeAllFormSupplementaryASaveandUpdate(model));
			response.setFormsupplementary(listing);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			List<FormSupplementarySendAllDataBean> listing = new ArrayList<>();
			listing.add(new FormSupplementarySendAllDataBean());
			response.setFormsupplementary(listing);
			response.getFormsupplementary().get(0).setExtractdata(new ArrayList<>());
			response.getFormsupplementary().get(0).setExtractdataaction(new ArrayList<>());
			response.getFormsupplementary().get(0).setError_code("100");
			response.getFormsupplementary().get(0).setMessage("No Data Found");
		}

		int flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0;

		if (response.getForm2() == null || response.getForm2().size() == 0) {

			List<Form2EnagageSendAllDataBean> l = new ArrayList<>();
			l.add(new Form2EnagageSendAllDataBean());
			response.setForm2(l);
			response.getForm2().get(0).setForm2Engage(new ArrayList<>());
			response.getForm2().get(0).setForm2stakeholders(new ArrayList<>());
			response.getForm2().get(0).setForm2docs(new ArrayList<>());
			response.getForm2().get(0).setError_code("100");
			response.getForm2().get(0).setMessage("No Data Found");
		} else {
			flag2++;
		}

		if (response.getForm3() == null || response.getForm3().size() == 0) {
			List<Form3DefineSendAllDataBean> l = new ArrayList<>();
			l.add(new Form3DefineSendAllDataBean());
			response.setForm3(l);
			response.getForm3().get(0).setForm3Define(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtDetails(new ArrayList<>());
			response.getForm3().get(0).setForm3ActionEngmtActionReq(new ArrayList<>());
			response.getForm3().get(0).setError_code("100");
			response.getForm3().get(0).setMessage("No Data Found");
		} else {
			flag3++;
		}

		if (response.getForm4() == null || response.getForm4().size() == 0) {
			response.setForm4(new ArrayList<>());
		} else {
			flag4++;
		}

		if (response.getForm5() == null || response.getForm5().size() == 0) {
			response.setForm5(new ArrayList<>());
		} else {
			flag5++;
		}

		if (flag2 == 0 && flag3 == 0 && flag4 == 0 && flag5 == 0) {
			response.setError_code("100");
			response.setMessage("Server Error");
		} else {
			response.setError_code("200");
			response.setMessage("Sending All forms Data");
		}

		return response;
	}

	// URL: http://localhost:8080/diphonline/consumeFormBDataFromAppToServer

	@RequestMapping(path = "/consumeFormBDataFromAppToServer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendAndroidForm1BSynchedDataBean consumeFormBDataFromAppToServer(
			@RequestBody Form1BConsumeDataFromAndroidBean model) {

		System.out.println(model);

		SendAndroidForm1BSynchedDataBean response = new SendAndroidForm1BSynchedDataBean();

//		try {
//			SendAndroidForm1BSynchedDataBean obj = dao3.consumeForm1BSaveAndUpdateData(model);
//			response.setFormB(obj.getFormB());
//			response.setFormBngo(obj.getFormBngo());
//			response.setFormBstakeholders(obj.getFormBstakeholders());
//			response.setFormBcoverageIndicators(obj.getFormBcoverageIndicators());
//			response.setFormBinfra(obj.getFormBinfra());
//			response.setFormBfinance(obj.getFormBfinance());
//			response.setFormBsupplies(obj.getFormBsupplies());
//			response.setFormBtechnology(obj.getFormBtechnology());
//			response.setFormBhumanresource(obj.getFormBhumanresource());
//			response.setError_code("200");
//			response.setMessage("Sending Updated and Consumed Data");
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//
//			response = new SendAndroidForm1BSynchedDataBean();
//			response.setFormB(new ArrayList<>());
//			response.setFormBngo(new ArrayList<>());
//			response.setFormBstakeholders(new ArrayList<>());
//			response.setFormBstakeholders(new ArrayList<>());
//			response.setFormBcoverageIndicators(new ArrayList<>());
//			response.setFormBinfra(new ArrayList<>());
//			response.setFormBfinance(new ArrayList<>());
//			response.setFormBsupplies(new ArrayList<>());
//			response.setFormBtechnology(new ArrayList<>());
//			response.setFormBhumanresource(new ArrayList<>());
//			response.setError_code("100");
//			response.setMessage("Server Error");
//		}

		return response;
	}

	// URL: http://localhost:8080/diphonline/consumeFormADataFromAppToServer

	@RequestMapping(path = "/consumeFormADataFromAppToServer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SendAndroidSynchedDataBeanNewFormat saveForm1A(@RequestBody Form1AConsumeDataFromAndroidBean model) {

		SendAndroidSynchedDataBean response = new SendAndroidSynchedDataBean();

		SendAndroidSynchedDataBeanNewFormat r = new SendAndroidSynchedDataBeanNewFormat();

//		System.out.println(model);
//
//		Form1ASendAllDataToAndroidBean result = new Form1ASendAllDataToAndroidBean();
//		try {
//			result = dao.consumeForm1ASaveAndUpdateData(model);
//
//			r.setFormA(result.getFormA());
//			r.setFormAdocument(result.getFormAdocument());
//			r.setError_code("200");
//			r.setMessage("Sending Updated and Consumed Data");
//
//			response.setResult(result);
//			response.setError_code("200");
//			response.setMessage("Sending Updated and Consumed Data");
//		} catch (Exception e) {
//			e.printStackTrace();
//			// TODO: handle exception
//			response = new SendAndroidSynchedDataBean();
//			response.setError_code("100");
//			response.setMessage("Server Error");
//			r = new SendAndroidSynchedDataBeanNewFormat();
//			r.setFormA(new ArrayList<>());
//			r.setFormAdocument(new ArrayList<>());
//			r.setError_code("100");
//			r.setMessage("Server Error");
//		}

		return r;
	}

}
