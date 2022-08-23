package com.tattvafoundation.diphonline.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.CreateDistrictBean;
import com.tattvafoundation.diphonline.model.DistrictResponseBean;
import com.tattvafoundation.diphonline.repository.ManageDistrictsDAO;

@CrossOrigin(origins = "*")
@RestController
public class ManageDistrictsController {

	@Autowired
	ManageDistrictsDAO dao;
	
	// http://localhost:8080/diphonline/createnewdistrict
	@RequestMapping(path = "/createnewdistrict", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DistrictResponseBean createnewdistrict(@RequestBody CreateDistrictBean model) {
		
		DistrictResponseBean response = new DistrictResponseBean();
		try {
			response = dao.createnewdistrict(model); 
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("not_allowed");
			response.setMessage("Server Error.");
			// TODO: handle exception
		}
		
		
		return response;
	}
	
	// http://localhost:8080/diphonline/deletedistrict
	@RequestMapping(path = "/deletedistrict", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public DistrictResponseBean deletedistrict(@RequestBody CreateDistrictBean model) {
		
		DistrictResponseBean response = new DistrictResponseBean();
		try {
			response = dao.deletedistrict(model); 
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("not_allowed");
			response.setMessage("Server Error.");
			// TODO: handle exception
		}
		
		
		return response;
	}
	
}
