package com.tattvafoundation.diphonline.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.APICountry;
import com.tattvafoundation.diphonline.model.APPAllDistrictsAllCyclesBean;
import com.tattvafoundation.diphonline.model.APPDistrictCyclePostParameterBean;
import com.tattvafoundation.diphonline.model.Areas_of_Indicators_List;
import com.tattvafoundation.diphonline.model.C_States;
import com.tattvafoundation.diphonline.model.Cycle;
import com.tattvafoundation.diphonline.model.District;
import com.tattvafoundation.diphonline.model.IndicatorsList;
import com.tattvafoundation.diphonline.model.States;
import com.tattvafoundation.diphonline.model.VerifiedByNameBean;
import com.tattvafoundation.diphonline.model.VerifiedByNamesResponse;
import com.tattvafoundation.diphonline.repository.CountryStateDAO;
import com.tattvafoundation.diphonline.repository.DistrictCycleYearDAO;
import com.tattvafoundation.diphonline.repository.Form1BDAO;
import com.tattvafoundation.diphonline.repository.VerifiedByNameDAO;

@CrossOrigin(origins = "*")
@RestController
public class DistrictCycleYearController {

	@Autowired
	public DistrictCycleYearDAO dao;
	
	@Autowired
	public Form1BDAO dao2;
	
	@Autowired
	public VerifiedByNameDAO dao3;
	
	@Autowired
	public CountryStateDAO dao4; 
	

	// URL: http://localhost:8080/diphonline/getsdistrictsinfo?country_id=96

	@RequestMapping("/getsdistrictsinfo")
	public List<District> sdistrictsInformation(@RequestParam String state_id) {

		List<District> districts = dao.getAllDistrictsforSpecificStateCountry(state_id);

		return districts;
	}
	
	// URL: http://localhost:8080/diphonline/getalldistrictsallcyclesinfo_app
	@RequestMapping("/getalldistrictsallcyclesinfo_app")
	public APPAllDistrictsAllCyclesBean sdistrictsInformationd_dcyclesInformationForApp(@RequestBody APPDistrictCyclePostParameterBean model) {

		
		String country_id = model.getCountry_id();
		String region_id = model.getRegion_id();
		APPAllDistrictsAllCyclesBean response = new APPAllDistrictsAllCyclesBean(); 
		List<States> states = new ArrayList<>();
		
		try {
			
			states = dao4.getAllStatesForSpecificRegion(model.getCountry_id(), model.getRegion_id());
			
			List<District> districts = dao.getAllDistrictsSpecificStatesForCertainRegionCountry(country_id,region_id);
			List<Cycle> cycles = dao.getAllCyclesforDistrict(country_id);
			
			IndicatorsList responseobj = dao2.getIndicatorsList();
			
			List<Areas_of_Indicators_List> getlist = dao2.getIndicatorAreasList();
			
			response.setStatesList(states); 
			response.setDistrictList(districts);
			response.setCyclesList(cycles); 
			response.setAreas_Id_Indicators_map_list(responseobj.getAreas_Id_Indicators_map_list()); 
			response.setAreas_list(getlist); 
			
			List<VerifiedByNameBean> faulty_list = new ArrayList<>();
			VerifiedByNamesResponse newobj = new VerifiedByNamesResponse();
			
			try {
				newobj = dao3.getVerifiedByNames();
			} 
			catch (Exception e) { 
				// TODO: handle exception
				newobj.setVerified_by_name(faulty_list); 
				newobj.setStatus("0");
				newobj.setMessage("Server Error! Cannot retrieve names"); 
			}
			
			response.setVerified_by_name(newobj.getVerified_by_name()); 
			
			response.setError_code("200");
			response.setMessage("All Districts, All Cycles, All Indicators(with other properties) and All Areas List"); 
		} catch (Exception e) {			
			
			response.setStatesList(new ArrayList<>()); 
			response.setDistrictList(new ArrayList<>());
			response.setCyclesList(new ArrayList<>());
			
			response.setError_code("100");
			response.setMessage("Server Error");
			// TODO: handle exception
		}
				
		return response;
	}

	// URL: http://localhost:8080/diphonline/getdcyclesinfo

	@RequestMapping("/getdcyclesinfo")
	public List<Cycle> dcyclesInformation(@RequestParam(defaultValue = "65") String country_id) {

		List<Cycle> cycles = dao.getAllCyclesforDistrict(country_id);

		return cycles;
	}
	
	

}