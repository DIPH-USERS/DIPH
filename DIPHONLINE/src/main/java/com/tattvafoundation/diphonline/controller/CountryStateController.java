
package com.tattvafoundation.diphonline.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.APICountry;
import com.tattvafoundation.diphonline.model.C_Region;
import com.tattvafoundation.diphonline.model.C_States;
import com.tattvafoundation.diphonline.model.Countries;
//import com.tattvafoundation.diphonline.model.C_States;
//import com.tattvafoundation.diphonline.model.Countries;
import com.tattvafoundation.diphonline.model.Countries_States;
import com.tattvafoundation.diphonline.model.Country;
import com.tattvafoundation.diphonline.model.Cycle;
import com.tattvafoundation.diphonline.model.District;
import com.tattvafoundation.diphonline.model.Regions;
import com.tattvafoundation.diphonline.model.States;
import com.tattvafoundation.diphonline.model.StatesandDistrictsBean;
import com.tattvafoundation.diphonline.repository.CountryStateDAO;

@CrossOrigin(origins = "*")
@RestController
public class CountryStateController {

	@Autowired
	public CountryStateDAO dao;

	/*
	 * 
	 * private _district_detail_from_id_api:string =
	 * this.BASE_URL+"/getdistrictinfofromid/"; private
	 * _cycle_detail_from_id_api:string = this.BASE_URL+"/getcycleinfofromid/";
	 * 
	 * constructor(private http:HttpClient) { }
	 * 
	 * public getDistrictDetailsFromId(id:string): Observable<District> { return
	 * this.http.get<District>(this._district_detail_from_id_api+id)
	 * .pipe(catchError(this.errorHandler)); }
	 * 
	 * public getCycleDetailsFromId(id:string): Observable<Cycle> { return
	 * this.http.get<Cycle>(this._cycle_detail_from_id_api+id)
	 * .pipe(catchError(this.errorHandler));
	 * 
	 * }
	 * 
	 * 
	 */

	// /getdistrictinfofromid/
	// URL: http://localhost:8080/diphonline/getdistrictinfofromid/10
	@RequestMapping("/getdistrictinfofromid/{id}")
	public District getdistrictinfofromid(@PathVariable String id) {

		District d = new District();

		d = dao.getDistrict(id);

		return d;
	}

	// URL: http://localhost:8080/diphonline/getcycleinfofromid/10
	@RequestMapping("/getcycleinfofromid/{id}")
	public Cycle getcycleinfofromid(@PathVariable String id) {

		Cycle c = new Cycle();

		c = dao.getCycle(id);

		return c;
	}

	// URL: http://localhost:8080/diphonline/getcountriesinfo

	@RequestMapping("/getcountriesinfo")
	public List<Country> countryInformation() {

		List<Country> countries = new ArrayList<>();

		try {
			countries = dao.getAllCountries();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Exception encountered " + e);
		}

//		System.out.println(countries);

		return countries;
	}

	// URL: http://localhost:8080/diphonline/getcountriesinfo_app

	@RequestMapping(path = "/getcountriesinfo_app", method = RequestMethod.POST)
	public Countries countryInformationForAndroidApp() {

		Countries obj = new Countries();
		List<Country> countries = new ArrayList<>();
		try {
			countries = dao.getAllCountries();
			obj.setResult(countries);
			obj.setError_code("200");
			obj.setMessage("Country List");
		} catch (Exception e) {
			// TODO: handle exception
			obj.setResult(countries);
			obj.setError_code("100");
			obj.setMessage("Server Error");
		}

		return obj;
	}

	// URL: http://localhost:8080/diphonline/getregionsinfo

	@RequestMapping("/getregionsinfo/{id}")
	public List<Regions> regionsInformation(@PathVariable String id) {

		List<Regions> regions = dao.getAllRegionsForSpecificCountry(id);

		return regions;
	}
	
	// URL: http://localhost:8080/diphonline/getregioninfo

		@RequestMapping("/getregioninfo/{id}")
		public Regions regionInformation(@PathVariable String id) {

			Regions region = dao.getRegionsForid(id);
			
			return region;
		}
	
	
	// URL: http://localhost:8080/diphonline/getStateofDistrictDetails

		@RequestMapping("/getstatesfordistrictsinfo")
		public StatesandDistrictsBean getstatesfordistrictsInfo(@RequestParam(defaultValue = "1") String district_id) {

			StatesandDistrictsBean response = dao.states_for_districts(district_id);

			return response;
		}  
	

	// URL: http://localhost:8080/diphonline/getstatesinfo

	@RequestMapping("/getstatesinfo/{id}")
	public List<States> cstatesInformation(@PathVariable String id) {

		List<States> states = dao.getAllStatesForSpecificRegion(id);

		return states;
	}

	// URL: http://localhost:8080/diphonline/getregionsinfo_app
	/*
	 * { "country_id":"65" }
	 */

	@RequestMapping(path = "/getregionsinfo_app", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public C_Region regionsInformationForAndroidApp(@RequestBody APICountry model) {

		C_Region c_regions = new C_Region();

		List<Regions> templist = new ArrayList<>();

		try {
			List<Regions> regions = dao.getAllRegionsForSpecificCountry(model.getCountry_id());

			c_regions.setResult(regions);
			c_regions.setError_code("200");
			c_regions.setMessage("Regions List");
		} catch (Exception e) {
			// TODO: handle exception
			c_regions.setResult(templist);
			c_regions.setError_code("100");
			c_regions.setMessage("Server Error");
		}

		return c_regions;
	}

	// URL: http://localhost:8080/diphonline/getstatesinfo_app
	/*
	 * { "country_id":"65" }
	 */

	@RequestMapping(path = "/getstatesinfo_app", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public C_States cstatesInformationForAndroidApp(@RequestBody APICountry model) {

		C_States c_states = new C_States();

		List<States> states = new ArrayList<>();

		try {
			states = dao.getAllStatesForSpecificRegion(model.getCountry_id(), model.getRegion_id());
			c_states.setResult(states);
			c_states.setError_code("200");
			c_states.setMessage("States List");
		} catch (Exception e) {
			// TODO: handle exception
			c_states.setResult(states);
			c_states.setError_code("100");
			c_states.setMessage("Server Error");
		}

		return c_states;
	}

	// URL: http://localhost:8080/diphonline/getcountry_statesinfo

	@RequestMapping("/getcountry_statesinfo")
	public Countries_States country_statesInformation(@RequestParam(defaultValue = "65") String country_id) {

		Countries_States states = dao.getCountriesAndStates(country_id);

		return states;
	}

}
