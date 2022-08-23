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

import com.tattvafoundation.diphonline.model.BasicFormInfo;
import com.tattvafoundation.diphonline.model.DeleteForm1AResponse;
import com.tattvafoundation.diphonline.model.Form1ADocumentsAvailable;
import com.tattvafoundation.diphonline.model.Form1ADocumentsCheclist;
import com.tattvafoundation.diphonline.model.Form1AEditUpdate;
import com.tattvafoundation.diphonline.model.Form1ASave;
import com.tattvafoundation.diphonline.model.Form1ASaveDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1AView;
import com.tattvafoundation.diphonline.repository.Form1ADAO;
import com.tattvafoundation.diphonline.repository.Form1BDAO;
import com.tattvafoundation.diphonline.repository.LoginDAO;
import com.tattvafoundation.diphonline.service.ServiceUtil;

@CrossOrigin(origins = "*")
@RestController
public class Form1AController {

	@Autowired
	public Form1ADAO dao;

	@Autowired
	public LoginDAO dao2;
	
	@Autowired
	private Form1BDAO form1BDao;
	

	@RequestMapping(path = "/autofillForm1A_Form1B", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Boolean autofillForm1A(@RequestBody BasicFormInfo model) {
		
			String district = model.getDistrict();
			String cycle = model.getCycle();
			String year = model.getYear();
			String userId = model.getUserid();
			
			return ServiceUtil.autoFillForm1A_Form1B(dao, form1BDao, district, cycle, year, userId);
	}

	
	

	// URL: http://localhost:80/diphonline/form1Adoc_available_info

	@RequestMapping("/form1Adoc_available_info")
	public List<Form1ADocumentsAvailable> form1Adoc_available_info(
			@RequestParam(value = "district_id", required = false, defaultValue = "1") String district_id,
			@RequestParam(value = "cycle_id", required = false, defaultValue = "1") String cycle_id,
			@RequestParam(value = "financial_year", required = false, defaultValue = "2016") String financial_year) {

		List<Form1ADocumentsAvailable> doclist = dao.getDocumentsAvailable(district_id, cycle_id, financial_year);

		return doclist;
	}

	// URL: http://localhost:80/diphonline/form1Adoc_db_chklist_info

	@RequestMapping("/form1Adoc_db_chklist_info")
	public List<Form1ADocumentsCheclist> form1Adoc_db_chklist_info(
			@RequestParam(value = "district_id", required = false, defaultValue = "1") String district_id,
			@RequestParam(value = "cycle_id", required = false, defaultValue = "1") String cycle_id,
			@RequestParam(value = "financial_year", required = false, defaultValue = "2016") String financial_year) {

		List<Form1ADocumentsCheclist> doclist = dao.getDocumentsChecklist(district_id, cycle_id, financial_year);

		return doclist;
	}

	// URL: http://localhost:80/diphonline/saveform1aDetails

	@RequestMapping(path = "/saveform1aDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Form1AView saveForm1A(@RequestBody Form1ASave model) {

		
		// example DocumentArray
		List<Form1ASaveDocumentsArray> list = model.getState_policy_array();

		List<Form1ASaveDocumentsArray> list2 = model.getDistrict_policy_array();

		List<Form1ASaveDocumentsArray> list3 = model.getHealth_dept_array();

		List<Form1ASaveDocumentsArray> list4 = model.getNon_health_dept_array();

		List<Form1ASaveDocumentsArray> list5 = model.getPrivate_sec_array();

		List<Form1ASaveDocumentsArray> list6 = model.getLarge_scale_district_array();		

		Form1AView obj = dao.saveForm1AToDb(model); 		

		return obj;
	}
	
	
	// URL: http://localhost:80/diphonline/editUpdateForm1A(send json data)
	@RequestMapping(path = "/editUpdateForm1A", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public Form1AView editUpdateForm1A(@RequestBody Form1AEditUpdate model) {

		
		// example DocumentArray
		List<Form1ASaveDocumentsArray> list = model.getState_policy_array();

		List<Form1ASaveDocumentsArray> list2 = model.getDistrict_policy_array();

		List<Form1ASaveDocumentsArray> list3 = model.getHealth_dept_array();

		List<Form1ASaveDocumentsArray> list4 = model.getNon_health_dept_array();

		List<Form1ASaveDocumentsArray> list5 = model.getPrivate_sec_array();

		List<Form1ASaveDocumentsArray> list6 = model.getLarge_scale_district_array();

		
		Form1AView obj = dao.updateEditedForm1AToDb(model);


		return obj;
	}

	// URL:
	// http://localhost:80/diphonline/viewForm1A?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/viewForm1A", method = RequestMethod.GET)
	public Form1AView viewForm1A(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {

		Form1AView obj = dao.retrieveForm1ADetails(district_id, cycle_id, year, user_id);

		// System.out.println(obj);

		return obj;
	}

	// URL: http://localhost:80/diphonline/deleteForm1A?district_id=1&cycle_id=1&year=2019&user_id=1

	@RequestMapping(path = "/deleteForm1A", method = RequestMethod.GET)
	public DeleteForm1AResponse deleteForm1A(@RequestParam(defaultValue = "1") String district_id,
			@RequestParam(defaultValue = "1") String cycle_id, @RequestParam(defaultValue = "2019") String year,
			@RequestParam(defaultValue = "1") String user_id) {
		 
		DeleteForm1AResponse deletedstate = dao.deletedForm1ADetails(district_id, cycle_id, year, user_id);
		
		
		return deletedstate;
	}
	
	

	// URL: http://localhost:80/diphonline/getuser2

	@RequestMapping(path = "/getuser2", method = RequestMethod.GET)
	public ZZ getuser2() {
		ZZ obj = new ZZ("Abhishek", "Kumar", "Java Developer");
		return obj;
	}
}



class Sample {

}

class ZZ {
	String firstName;
	String lastName;
	String about;

	public ZZ() {

	}

	public ZZ(String firstName, String lastName, String about) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.about = about;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

}
