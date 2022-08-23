package com.tattvafoundation.diphonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.DeleteForm4PlanResponse;
import com.tattvafoundation.diphonline.model.Form5Followup; 
import com.tattvafoundation.diphonline.model.SavedForm5FollowupResponse;
import com.tattvafoundation.diphonline.repository.Form5FollowupDAO;

@CrossOrigin(origins = "*")
@RestController
public class Form5FollowupController {

	@Autowired
	public Form5FollowupDAO dao; 
	
	// URL: http://localhost:80/diphonline/deleteForm5Followup?district_id=1&cycle_id=1&year=2019&user_id=1

		@RequestMapping(path = "/deleteForm5Followup", method = RequestMethod.GET)
		public DeleteForm4PlanResponse deleteForm5Followup(@RequestParam(defaultValue = "1") String district_id,
				@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
				@RequestParam(defaultValue = "1") String user_id) {

			DeleteForm4PlanResponse deletedstate = dao.deleteForm5FollowupDetails(district_id, cycle_id, year, user_id);

			return deletedstate;
		}
	// URL: http://localhost/diphonline/viewForm5Followup?district_id=1&cycle_id=1&year=2019&user_id=1

		@RequestMapping(path = "/viewForm5Followup", method = RequestMethod.GET)
		public Form5Followup viewForm5Followup(@RequestParam(defaultValue = "1") String district_id,
				@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
				@RequestParam(defaultValue = "1") String user_id) {

			Form5Followup obj = dao.retrieveForm5FollowupDetails(district_id, cycle_id, year, user_id);

			
			return obj;
		}

	// URL: http://localhost/diphonline/saveform5followupDetails

	@RequestMapping(path = "/saveform5followupDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm5FollowupResponse saveform5followupDetails(@RequestBody Form5Followup model) {

		
		SavedForm5FollowupResponse responseobj = dao.saveForm5FollowupToDb(model);
		//SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	
	
	// URL: http://localhost/diphonline/editUpdateForm5FollowUp(send json data)

		@RequestMapping(path = "/editUpdateForm5FollowUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public SavedForm5FollowupResponse editUpdateForm5FollowUp(@RequestBody Form5Followup model) {// total_coverage_indi

			
			SavedForm5FollowupResponse responseobj = dao.editUpdateForm5FollowUpToDb(model);
			//SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
			responseobj.setProcessname("saved");

			responseobj.setResponse_val("1");

			return responseobj;
		}

}