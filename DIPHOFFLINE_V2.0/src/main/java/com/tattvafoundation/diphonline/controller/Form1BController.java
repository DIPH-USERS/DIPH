package com.tattvafoundation.diphonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.Constants;
import com.tattvafoundation.diphonline.model.DeleteForm1AResponse;
import com.tattvafoundation.diphonline.model.Form1BEditUpdate;
import com.tattvafoundation.diphonline.model.Form1BSave;
import com.tattvafoundation.diphonline.model.Form1BSaveDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BSaveIndicatorsModel;
import com.tattvafoundation.diphonline.model.Form1BView;
import com.tattvafoundation.diphonline.model.IndicatorsList;
import com.tattvafoundation.diphonline.model.SavedForm1BResponse;
import com.tattvafoundation.diphonline.repository.Form1BDAO;
import com.tattvafoundation.diphonline.service.ServiceUtil;

@CrossOrigin(origins = "*")
@RestController
public class Form1BController {

	@Autowired
	public Form1BDAO dao;

	// URL: http://localhost:80/diphonline/saveform1bdetails

	@RequestMapping(path = "/saveform1bdetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm1BResponse saveform1bdetails(@RequestBody Form1BSave model) {

				
		List<Form1BSaveDocumentsArray> list = model.getInfra_array();
		
		List<Form1BSaveDocumentsArray> list2 = model.getFina_array();
		List<Form1BSaveDocumentsArray> list3 = model.getSupp_array();
		List<Form1BSaveDocumentsArray> list4 = model.getTech_array();
		List<Form1BSaveDocumentsArray> list5 = model.getHr_array();
		List<Form1BSaveIndicatorsModel> list6 =model.getTotal_coverage_indi();

		SavedForm1BResponse responseobj = dao.saveForm1BToDb(model);
		
//		SavedForm1BResponse responseobj = null;
		
		return responseobj;
	}

	// URL:
	// http://localhost:80/diphonline/viewForm1B?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/viewForm1B", method = RequestMethod.GET)
	public Form1BView viewForm1B(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) { 

		Form1BView obj = dao.retrieveForm1BDetails(district_id, cycle_id, year, user_id);
		
		return obj;
	}

	// URL:
	// http://localhost:80/diphonline/deleteForm1B?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/deleteForm1B", method = RequestMethod.GET)
	public DeleteForm1AResponse deleteForm1B(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		DeleteForm1AResponse deletedstate = dao.deleteForm1BDetails(district_id, cycle_id, year, user_id);

		return deletedstate;
	}

	// getIndicatorsList
	// URL: http://localhost:8080/diphonline/getIndicatorsList(send json data)
	@RequestMapping(path = "/getIndicatorsList", method = RequestMethod.GET)
	public IndicatorsList getIndicatorsList() {// total_coverage_indi


		IndicatorsList responseobj = dao.getIndicatorsList();		
		
		return responseobj;
	}
	
	// getOptionalIndicatorsList
	// URL: http://localhost:8080/diphonline/getOptionalIndicatorsList(send json data)
	@RequestMapping(path = "/getOptionalIndicatorsList", method = RequestMethod.GET)
	public OptionalindicatorsList getOptionalIndicatorsList() {


		OptionalindicatorsList response =  dao.getOptIndicators();
		
//		System.out.println("Server returning the data");
//		System.out.println(responseobj);
//		System.out.println("Server returning the data");
		return response;
	}
	

	// URL: http://localhost:80/diphonline/editUpdateForm1A(send json data)
	@RequestMapping(path = "/editUpdateForm1B", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm1BResponse editUpdateForm1B(@RequestBody Form1BEditUpdate model) {// total_coverage_indi
		
		//SavedForm1BResponse responseobj = dao.editUpdateForm1BToDb(model);
		dao.deleteForm1BDetails(model.getDistrict(), model.getCycle(), model.getYear(), model.getUserid());
		Form1BSave newModel = ServiceUtil.Form1BEditUpdateToForm1BSaveConverter(model, Constants.ONLINE_SOURCE);		
		SavedForm1BResponse responseobj = dao.saveForm1BToDb(newModel);
		
		return responseobj;
	}
	
	// URL: http://localhost:80/diphonline/editUpdateForm1A(send json data)
		@RequestMapping(path = "/editUpdateForm1BWhenForm2Saved", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public SavedForm1BResponse editUpdateForm1BWhenForm2Saved(@RequestBody Form1BEditUpdate model) {// total_coverage_indi
			
			SavedForm1BResponse responseobj = dao.editUpdateForm1BToDb(model);						
			return responseobj;
			
		}
	
	
	

}



