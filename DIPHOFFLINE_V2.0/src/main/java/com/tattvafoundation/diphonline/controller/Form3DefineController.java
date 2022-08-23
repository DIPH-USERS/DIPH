package com.tattvafoundation.diphonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.DeleteForm3DefineResponse;
import com.tattvafoundation.diphonline.model.Form3Define;
import com.tattvafoundation.diphonline.model.Form3DefineEdit;
import com.tattvafoundation.diphonline.model.Form3DefineViewInEdit;
import com.tattvafoundation.diphonline.model.SavedForm3DefineResponse;
import com.tattvafoundation.diphonline.repository.Form3DefineDAO;
import com.tattvafoundation.diphonline.service.ServiceUtil;

@CrossOrigin(origins = "*")
@RestController
public class Form3DefineController {

	@Autowired
	public Form3DefineDAO dao;
	
	// URL: http://localhost:80/diphonline/editUpdateForm1A(send json data)
		@RequestMapping(path = "/editUpdateForm3Define", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public SavedForm3DefineResponse editUpdateForm3Define(@RequestBody Form3DefineEdit model) {

			dao.deleteForm3DefineDetails(model.getDistrict(), model.getCycle(), model.getYear(), model.getUserid());
			Form3Define newModel = ServiceUtil.Form3DefineToForm3DefineEditConverter(model);
			SavedForm3DefineResponse obj = dao.saveForm3DefineToDb(newModel);
			
			//Form3DefineEdit obj = dao.editUpdateForm3DefineToDb(model);
			
			return obj;
		}
	
	
	// URL: http://localhost:80/diphonline/editUpdateForm1A(send json data)
	@RequestMapping(path = "/editUpdateForm3DefineWhenPlanFilled", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Form3DefineEdit editUpdateForm3DefineWhenPlanFilled(@RequestBody Form3DefineEdit model) {

		Form3DefineEdit obj = dao.editUpdateForm3DefineToDb(model);
		
		return obj;
	}
	
	
	
	// URL: http://localhost/diphonline/deleteForm3Define?district_id=1&cycle_id=1&year=2019&user_id=1
	@RequestMapping(path = "/deleteForm3Define", method = RequestMethod.GET)
	public DeleteForm3DefineResponse deleteForm3Define(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		DeleteForm3DefineResponse deletedstate = dao.deleteForm3DefineDetails(district_id, cycle_id, year, user_id);
		
		
		return deletedstate;
	}

	// URL: http://localhost/diphonline/saveform3defineDetails

	@RequestMapping(path = "/saveform3defineDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm3DefineResponse saveform3defineDetails(@RequestBody Form3Define model) {
		
		SavedForm3DefineResponse responseobj = dao.saveForm3DefineToDb(model);
//		SavedForm3DefineResponse responseobj = new SavedForm3DefineResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	// URL:
	// http://localhost:80/diphonline/viewForm3Define?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/viewForm3Define", method = RequestMethod.GET)
	public Form3Define viewForm3Define(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		Form3Define obj = dao.retrieveForm3DefineDetails(district_id, cycle_id, year, user_id);

		// System.out.println(obj);

		return obj;
	}
	
	
	
	
	// URL:
	// http://localhost:80/diphonline/viewInEditForm3Define?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/viewInEditForm3Define", method = RequestMethod.GET)
	public Form3DefineViewInEdit viewInEditForm3Define(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {	
		

		Form3DefineViewInEdit obj = dao.retrieveInEditForm3DefineDetails(district_id, cycle_id, year, user_id);

		// System.out.println(obj);

		return obj;
	}
}
