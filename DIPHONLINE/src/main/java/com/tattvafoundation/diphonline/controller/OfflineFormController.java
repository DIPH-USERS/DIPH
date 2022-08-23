package com.tattvafoundation.diphonline.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.OfflineFormBean;
import com.tattvafoundation.diphonline.model.OfflineFormBeanVersion2;
import com.tattvafoundation.diphonline.service.OfflineFormService;

/**
 * <h1>Offline form controller!</h1> 
 * Controller for handling the requests with 
 *  JSON data from offline HTML forms.
 * <p>
 *
 * @author Mohd. Mohsin Ansari
 * @version 1.0
 * @since 2020-11-23
 */

@CrossOrigin(origins = "*")
@RestController
public class OfflineFormController {

	@Autowired
	private OfflineFormService offlineFormService;

	/**
	 * This method is used to import JSON data from HTML form 
	 * and provide how many forms data is imported in response.
	 * 
	 * @param model This is a offline form bean 
	 * @param district_id This is the district id of logged in user
	 * @param cycle_id This is the cycle of logged in user
	 * @param financial_year This is the year of logged in user
	 * @param user_id This is the user id of logged in user
	 * @return String How many forms data is imported.
	 * URL: http://localhost:8080/diphonline/saveOfflineData
	 */

	
	@RequestMapping(path = "/saveOfflineData", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveForm1A(@RequestBody OfflineFormBean model) {
		
		String response = offlineFormService.jsonDataImport(model, model.getDistrict(), model.getCycle(), model.getYear(), model.getUserid());
		return response;
	}	
	
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public InputStreamResource FileSystemResource (HttpServletResponse response) throws IOException {
		
		return offlineFormService.FileSystemResource(response);
	}
	
	@RequestMapping(path = "/saveOnlineDataVersion2", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String saveForm1AVersion2(@RequestBody OfflineFormBeanVersion2 model) {
		
		String response = offlineFormService.jsonDataImportVersion2(model, model.getDistrict(), model.getCycle(), model.getYear(), model.getUserid());		
		return response;
	}
	
	@RequestMapping(value = "/downloadOfflineDataVersion2", method = RequestMethod.GET)
	public OfflineFormBeanVersion2 downloadOfflineData(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2021") String year, @RequestParam(defaultValue = "1") String user_id) throws IOException {		
		
		return offlineFormService.jsonDataExportVersion2(district_id, cycle_id, year, user_id);
	}

}
