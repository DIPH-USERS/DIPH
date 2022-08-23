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
import com.tattvafoundation.diphonline.model.SavedForm5FollowupResponse;
import com.tattvafoundation.diphonline.model.Supplementaryform1A;
import com.tattvafoundation.diphonline.repository.SupplementaryForm1ADAO;


@CrossOrigin(origins = "*")
@RestController
public class SupplementaryForm1AController {

	@Autowired
	public SupplementaryForm1ADAO dao; 
	
	
	// URL: http://localhost/diphonline/editUpdateSupplementaryForm1ADetails(send json data)

		@RequestMapping(path = "/editUpdateSupplementaryForm1ADetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public SavedForm5FollowupResponse editUpdateSupplementaryForm1ADetails(@RequestBody Supplementaryform1A model) {// total_coverage_indi

			
			SavedForm5FollowupResponse responseobj = dao.editUpdateSupplementaryForm1ADetails(model); 
//			 SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
			responseobj.setProcessname("saved");

			responseobj.setResponse_val("1");
			
			return responseobj;
		}
	
	// URL: http://localhost:80/diphonline/deleteSupplementaryForm1A?district_id=1&cycle_id=1&year=2019&user_id=1

		@RequestMapping(path = "/deleteSupplementaryForm1A", method = RequestMethod.GET)
		public DeleteForm4PlanResponse deleteSupplementaryForm1A(@RequestParam(defaultValue = "1") String district_id,
				@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
				@RequestParam(defaultValue = "1") String user_id) {

			DeleteForm4PlanResponse deletedstate = dao.deleteSupplementaryForm1ADetails(district_id, cycle_id, year, user_id);

			return deletedstate;
		}
	// URL: http://localhost/diphonline/viewForm5Followup?district_id=1&cycle_id=1&year=2019&user_id=1

		@RequestMapping(path = "/viewSupplementaryForm1A", method = RequestMethod.GET)
		public Supplementaryform1A viewSupplementaryForm1A(@RequestParam(defaultValue = "1") String district_id,
				@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
				@RequestParam(defaultValue = "1") String user_id) {

			Supplementaryform1A obj = dao.retrieveSupplementaryForm1ADetails(district_id, cycle_id, year, user_id);

			
			return obj;
		}

	// URL: http://localhost/diphonline/savesupplemmentaryform1aDetails

		
		
		
	@RequestMapping(path = "/savesupplemmentaryform1aDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public SavedForm5FollowupResponse savesupplemmentaryform1aDetails(@RequestBody Supplementaryform1A model) {

	
		SavedForm5FollowupResponse responseobj = dao.savsupplemmentaryform1aToDb(model);
//		 SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	
}

