package com.tattvafoundation.diphonline.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.VerifiedByNameBean;
import com.tattvafoundation.diphonline.model.VerifiedByNamesResponse;
import com.tattvafoundation.diphonline.repository.VerifiedByNameDAO;

@CrossOrigin(origins = "*")
@RestController
public class VerifiedByNameController {
	
	@Autowired
	public VerifiedByNameDAO dao;

	// http://localhost:8080/diphonline/getVerifiedByNamesList

		@RequestMapping("/getVerifiedByNamesList")
		public VerifiedByNamesResponse getVerifiedByNamesList() {

			List<VerifiedByNameBean> faulty_list = new ArrayList<>();
			VerifiedByNamesResponse response = new VerifiedByNamesResponse();
			
			try {
				response = dao.getVerifiedByNames();
			} 
			catch (Exception e) { 
				// TODO: handle exception
				response.setVerified_by_name(faulty_list); 
				response.setStatus("0");
				response.setMessage("Server Error! Cannot retrieve names"); 
			}				

			return response;
		}
		
		//http://localhost:8080/diphonline/getVerifiedByNamesListforApp
		
		@RequestMapping("/getVerifiedByNamesListforApp")
		public List<VerifiedByNameBean> getVerifiedByNamesList_forApp() {
		
			List<VerifiedByNameBean> faulty_list = new ArrayList<>();
			VerifiedByNamesResponse response = new VerifiedByNamesResponse();
			
			try {
				response = dao.getVerifiedByNames();
			} 
			catch (Exception e) { 
				// TODO: handle exception
				response.setVerified_by_name(faulty_list); 
				response.setStatus("0");
				response.setMessage("Server Error! Cannot retrieve names"); 
			}
			return response.getVerified_by_name();
		}
	
}
