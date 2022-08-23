package com.tattvafoundation.diphonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.CategorizedIndicatorBean;
import com.tattvafoundation.diphonline.model.DeleteForm4PlanResponse;
import com.tattvafoundation.diphonline.model.EditIndicatorBean;
import com.tattvafoundation.diphonline.model.EditOptionalIndicatorBean;
import com.tattvafoundation.diphonline.model.Form4Plan;
import com.tattvafoundation.diphonline.model.IndicatorBean;
import com.tattvafoundation.diphonline.model.SavedForm4PlanResponse;
import com.tattvafoundation.diphonline.repository.Form4PlanDAO;

@CrossOrigin(origins = "*")
@RestController
public class Form4PlanController {

	@Autowired
	public Form4PlanDAO dao;

	// URL: http://localhost/diphonline/editUpdateForm4Plan(send json data)

	@RequestMapping(path = "/editUpdateForm4Plan", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse editUpdateForm4Plan(@RequestBody Form4Plan model) {// total_coverage_indi

		dao.deleteForm4PlanDetails(model.getDistrict(), model.getCycle(), model.getYear(), model.getUserid());		
		SavedForm4PlanResponse responseobj = dao.saveForm4PlanToDb(model);		
		
		//SavedForm4PlanResponse responseobj = dao.editUpdateForm4PlanToDb(model);
		// SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();

		
		return responseobj;
	}
	
	@RequestMapping(path = "/editUpdateForm4PlanWhenFollowUpFilled", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse editUpdateForm4PlanWhenFollowUpFilled(@RequestBody Form4Plan model) {// total_coverage_indi

		SavedForm4PlanResponse responseobj = dao.editUpdateForm4PlanToDb(model);
		return responseobj;
	}

	// URL: http://localhost/diphonline/saveform4planDetails

	@RequestMapping(path = "/saveform4planDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse saveform4planDetails(@RequestBody Form4Plan model) {
		
		SavedForm4PlanResponse responseobj = dao.saveForm4PlanToDb(model);
		 //SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	// URL:
	// http://localhost/diphonline/viewForm4Plan?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/viewForm4Plan", method = RequestMethod.GET)
	public Form4Plan viewForm4Plan(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		Form4Plan obj = dao.retrieveForm4PlanDetails(district_id, cycle_id, year, user_id);

		// System.out.println(obj);

		return obj;
	}

	// deleteForm4Plan

	// URL:
	// http://localhost:80/diphonline/deleteForm4Plan?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/deleteForm4Plan", method = RequestMethod.GET)
	public DeleteForm4PlanResponse deleteForm4Plan(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		DeleteForm4PlanResponse deletedstate = dao.deleteForm4PlanDetails(district_id, cycle_id, year, user_id);

		return deletedstate;
	}
	
	@RequestMapping(path = "/updateSelectedOptionalIndicator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse updateSelectedOptionalIndicator(@RequestBody EditOptionalIndicatorBean model) {

		System.out.println(model);
		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		try {
			 responseobj = dao.updateSelectedOptionalIndicatorInDb(model);
			 	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responseobj.setProcessname("ServerError");
			responseobj.setResponse_val("0");
		}
	    
		

		return responseobj;
	}
	
	@RequestMapping(path = "/updateSelectedActionIndicator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse updateSelectedActionIndicator(@RequestBody EditIndicatorBean model) {

		
		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		try {
			 responseobj = dao.updateSelectedActionIndicatorInDb(model);
			 	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responseobj.setProcessname("ServerError");
			responseobj.setResponse_val("0");
		} 
	    
		

		return responseobj;
	}
	
	
	@RequestMapping(path = "/updateSelectedIndicator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse updateSelectedIndicator(@RequestBody EditIndicatorBean model) {

		
		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		try {
			 responseobj = dao.updateSelectedIndicatorInDb(model);
			 	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responseobj.setProcessname("ServerError");
			responseobj.setResponse_val("0");
		}
	    
		

		return responseobj;
	}
	
	
	@RequestMapping(path = "/createNewCategorizedIndicator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse createNewCategorizedIndicator(@RequestBody CategorizedIndicatorBean model) {

		
	     SavedForm4PlanResponse responseobj = dao.createNewCategorizedIndicatorInDb(model);
		 //SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	
	
	@RequestMapping(path = "/createNewIndicator", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm4PlanResponse createNewIndicator(@RequestBody IndicatorBean model) {

		
	     SavedForm4PlanResponse responseobj = dao.createNewIndicatorInDb(model);
		 //SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	
}


