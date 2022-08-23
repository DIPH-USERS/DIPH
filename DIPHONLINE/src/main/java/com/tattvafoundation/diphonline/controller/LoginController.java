package com.tattvafoundation.diphonline.controller;

import java.util.ArrayList;
import java.util.List;

//import java.util.List;

//import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.indicator.entity.User;
import com.tattvafoundation.diphonline.model.APPLoginBean;
import com.tattvafoundation.diphonline.model.APPLoginPostParameterBean;
import com.tattvafoundation.diphonline.model.APPLoginPostParameterBean_NEW;
import com.tattvafoundation.diphonline.model.ChangePasswordBean;
import com.tattvafoundation.diphonline.model.ContactFormDetailsBean;
import com.tattvafoundation.diphonline.model.CurrentCycleStatus;
import com.tattvafoundation.diphonline.model.FeedbackDetailsBean;
import com.tattvafoundation.diphonline.model.FeedbackSendModel;
import com.tattvafoundation.diphonline.model.LockCycleStatusBean;
import com.tattvafoundation.diphonline.model.Login;
import com.tattvafoundation.diphonline.model.Userallowedstatus;
import com.tattvafoundation.diphonline.repository.LoginDAO;

//Working

@CrossOrigin(origins = "*")
@RestController
public class LoginController {

	@Autowired
	public LoginDAO dao;
	
	//http:localhost:8080/diphonline/saveContactDetails
	@RequestMapping(path = "/saveContactDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public FeedbackSendModel saveContactDetails(@RequestBody ContactFormDetailsBean model) {
		
		String status = "0";
		
		try {
			status =	dao.sendcontactformMail(model);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			status = "0";
		}
	
		FeedbackSendModel result = new FeedbackSendModel();
		
		result.setStatus(status); 
		
		return result;
	}
	
	//http:localhost:8080/diphonline/saveFeedbackDetails
	@RequestMapping(path = "/saveFeedbackDetails", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public FeedbackSendModel saveFeedbackDetails(@RequestBody FeedbackDetailsBean model) {
		
		String status = "0";
		
		try {
			status =	dao.sendfeedbackMail(model);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			status = "0";
		}
	
		FeedbackSendModel result = new FeedbackSendModel();
		
		result.setStatus(status); 
		
		return result;
	}

	// http:localhost:8080/diphonline/authenticateuser

	@RequestMapping("/authenticateuser")
	public Login authenticateuser(@RequestBody User user) {
		
		Login obj = dao.isauthenticated(user.getUser_nm(), user.getUser_pass());

		return obj;
	}// username:status:authentic
	
	//AllUsersBean
	
	// http:localhost:8080/diphonline/getcurrentuserallowedstatus	
	@RequestMapping(path = "/getcurrentuserallowedstatus")	
	public Userallowedstatus getcurrentuserallowedstatus(@RequestParam String username,
			@RequestParam String district_id, @RequestParam String cycle_id,
			@RequestParam String year) {
			 
			 Userallowedstatus response = new Userallowedstatus();
			 
			 response =  dao.processcurrentuserallowedstatus(username,district_id, cycle_id, year); 
			
			return response;
		}
	

	// http:localhost:8080/diphonline/getcyclelockstatus
	@RequestMapping(path = "/getcyclelockstatus")
	public CurrentCycleStatus getcyclelockstatus(@RequestParam String district_id, @RequestParam String cycle_id,
			@RequestParam String year) {

		CurrentCycleStatus response = new CurrentCycleStatus();
		
		
		try {
			response = dao.cyclelockstatus(district_id, cycle_id, year);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatus("0");
			response.setMessage("Server Error (" + e.toString() + ")");
		}
		
		return response;
	}

	// http:localhost:8080/diphonline/unlockcurrentcycle
	@RequestMapping(path = "/unlockcurrentcycle")
	public LockCycleStatusBean unlockcurrentcycle(@RequestParam String district_id, @RequestParam String cycle_id,
			@RequestParam String year, @RequestParam String user_id) {

		LockCycleStatusBean response = new LockCycleStatusBean();

		try {
			response = dao.unlockcycledao(district_id, cycle_id, year, user_id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatus("0");
			response.setMessage("Server Error (" + e.toString() + ")");
		}

		return response;
	}

	// http:localhost:8080/diphonline/lockcurrentcycle
	@RequestMapping(path = "/lockcurrentcycle")
	public LockCycleStatusBean lockcurrentcycle(@RequestParam String district_id, @RequestParam String cycle_id,
			@RequestParam String year, @RequestParam String user_id) {

		LockCycleStatusBean response = new LockCycleStatusBean();

		try {
			response = dao.lockcycledao(district_id, cycle_id, year, user_id);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setStatus("0");
			response.setMessage("Server Error (" + e.toString() + ")");
		}

		return response;
	}

	// http:localhost:8080/diphonline/forgotPassword
	@RequestMapping(path = "/changePassword", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ChangePasswordBean changePassword(@RequestBody ChangePasswordBean model) {

		ChangePasswordBean response = new ChangePasswordBean();

		try {
			response = dao.changeoldPasswordOfUser(model);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setResult("100");
			response.setMessage("Server Error");
		}

		return response;
	}

	// http:localhost:8080/diphonline/forgotPassword
	@RequestMapping(path = "/forgotPassword")
	public ForgotPasswordBean forgotPassword(@RequestParam(value = "email", required = true) String username) {

		ForgotPasswordBean response = new ForgotPasswordBean();

		try {
			response = dao.sendPasswordToUser(username);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			response.setResult("100");
			response.setMessage("Server Error");
		}

		return response;
	}

	
	// http://localhost:8080/diphonline/authenticateuser_app_new
	/*
	{
		"country_id":"65",
		"region_id":"3",
		"province_id":"20",
		"district_id":"6",
		"cycle":"6",
		"year":"6",
		"username":"ethiopia_2020",
		"password":"new_2020"
	}
	*/
		@RequestMapping(path = "/authenticateuser_app_new", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public APPLoginBean authenticateuserForApp_NEW(@RequestBody APPLoginPostParameterBean_NEW model) { 

			APPLoginBean response = new APPLoginBean();

			String username = model.getUsername();
			String password = model.getPassword();
			try {
				
				response = dao.isauthenticated_NEW(model);
				
//				Login obj = dao.isauthenticated_NEW(model);
//
//				List<Login> l = new ArrayList<>();
//
//				l.add(obj);
//
//				if (-1 == obj.getId()) {
//					response.setResult(l);
//					response.setError_code("100");
//					response.setMessage("Invalid Login Credentials");
//				} else {
//					response.setResult(l);
//					response.setError_code("200");
//					response.setMessage("Login Authentication Status");
//				}

			} catch (Exception e) {
				response.setError_code("100");
				response.setMessage("Server Error");
				// TODO: handle exception
			}

			return response;
		}// username:status:authentic

	
	
	// http://localhost:8080/diphonline/authenticateuser_app
	@RequestMapping(path = "/authenticateuser_app", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public APPLoginBean authenticateuserForApp(@RequestBody APPLoginPostParameterBean model) {

		APPLoginBean response = new APPLoginBean();

		String username = model.getUsername();
		String password = model.getPassword();
		try {
			Login obj = dao.isauthenticated(username, password);

			List<Login> l = new ArrayList<>();

			l.add(obj);

			if (-1 == obj.getId()) {
				response.setResult(l);
				response.setError_code("100");
				response.setMessage("Invalid Login Credentials");
			} else {
				response.setResult(l);
				response.setError_code("200");
				response.setMessage("Login Authentication Status");
			}

		} catch (Exception e) {
			response.setError_code("100");
			response.setMessage("Server Error");
			// TODO: handle exception
		}

		return response;
	}// username:status:authentic

}


