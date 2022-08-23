package com.tattvafoundation.diphonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.DeleteForm2EngageResponse;
import com.tattvafoundation.diphonline.model.Form2Engage;
import com.tattvafoundation.diphonline.model.Form2EngageEdit;
import com.tattvafoundation.diphonline.model.SavedForm2EngageResponse;
import com.tattvafoundation.diphonline.repository.Form2EngageDAO;
import com.tattvafoundation.diphonline.service.ServiceUtil;

@CrossOrigin(origins = "*")
@RestController
public class Form2EngageController {

	@Autowired
	public Form2EngageDAO dao;// viewForm2Engage

	// URL: http://localhost/diphonline/saveform2engageDetails

	@RequestMapping(path = "/saveform2engageDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm2EngageResponse saveform2engageDetails(@RequestBody Form2Engage model) {
				
		SavedForm2EngageResponse responseobj = dao.saveForm2EngageToDb(model);
		
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	// URL:
	// http://localhost:80/diphonline/viewForm2Engage?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/viewForm2Engage", method = RequestMethod.GET)
	public Form2Engage viewForm2Engage(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		Form2Engage obj = dao.retrieveForm2EngageDetails(district_id, cycle_id, year, user_id);

		// System.out.println(obj);

		return obj;
	}

	//  URL: http://localhost:80/diphonline/deleteForm2Engage?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/deleteForm2Engage", method = RequestMethod.GET)
	public DeleteForm2EngageResponse deleteForm2Engage(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		DeleteForm2EngageResponse deletedstate = dao.deleteForm2EngageDetails(district_id, cycle_id, year, user_id);
		
		
		return deletedstate;
	}
	
	
	@RequestMapping(path = "/editUpdateForm2Engage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm2EngageResponse editUpdateForm2Engage(@RequestBody Form2EngageEdit model) {
		
		dao.deleteForm2EngageDetails(model.getDistrict(), model.getCycle(), model.getYear(), model.getUserid());
		Form2Engage convertedModel = ServiceUtil.form2EngageToForm2EngageEditConverter(model);		
		SavedForm2EngageResponse responseobj = dao.saveForm2EngageToDb(convertedModel);

		//SavedForm2EngageResponse responseobj = dao.updateEditedForm2EngageToDb(model);
		
		//SavedForm2EngageResponse responseobj = new SavedForm2EngageResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");
		

		return responseobj;
	}
	
	@RequestMapping(path = "/editUpdateForm2EngageWhen3Filled", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm2EngageResponse editUpdateForm2EngageWhen3Filled(@RequestBody Form2EngageEdit model) {
		
		SavedForm2EngageResponse responseobj = dao.updateEditedForm2EngageToDb(model);
		
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");
		

		return responseobj;
	}

}
