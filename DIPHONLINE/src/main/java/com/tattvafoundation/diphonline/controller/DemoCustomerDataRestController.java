package com.tattvafoundation.diphonline.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tattvafoundation.diphonline.model.Customer;
import com.tattvafoundation.diphonline.repository.DemoDAO;


@RestController
public class DemoCustomerDataRestController {
	
	@Autowired
	public DemoDAO dao;
	

	//URL: http://localhost:8080/diphonline/getcustInfo

	@RequestMapping("/getcustInfo")
	public List<Customer> customerInformation() {

		List<Customer> customers = dao.isData();

		return customers;
	}
}