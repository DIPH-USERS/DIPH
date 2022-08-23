package com.tattvafoundation.diphonline.model;

import java.util.List;


/*
 * 
 * { "date_of_verification": "33-44-55", "filled_by": "wefwg",
 * "verified_by_name": "District Maternity and Child Health Officer(DMCHO)",
 * "state_policy_first_doc_val": "ewgfg", "state_policy_first_availability":
 * "Yes", "state_policy_first_d_source": "ewfgg",
 * "district_policy_first_doc_val": "ewgeg",
 * "district_policy_first_availability": "Yes",
 * "district_policy_first_d_source": "ewfewg", "health_dept_first_doc_val":
 * "ewgfewg", "health_dept_first_availability": "Yes",
 * "health_dept_first_d_source": "egfeg", "non_health_dept_first_doc_val":
 * "wgwge", "non_health_dept_first_availability": "Yes",
 * "non_health_dept_first_d_source": "ewfg", "private_sec_first_doc_val":
 * "ewgfweg", "private_sec_first_availability": "Yes",
 * "private_sec_first_d_source": "egfg", "large_scale_district_first_doc_val":
 * "edg", "large_scale_district_first_availability": "Yes",
 * "large_scale_district_first_d_source": "ewgweg", "numberOfTickets": "1",
 * "numberOfdistrictrow": "1", "numberOfhealthdeptrow": "1",
 * "numberOfnonhealthdeptrow": "1", "numberOfprivsecrow": "1",
 * "numberOflargescaledistrictrow": 1, "large_scale_district_array": [
 * 
 * ], "private_sec_array": [
 * 
 * ], "non_health_dept_array": [
 * 
 * ], "health_dept_array": [
 * 
 * ], "district_policy_array": [
 * 
 * ], "state_policy_array": [
 * 
 * ] }
 * 
 */


public class Form1ASave {

	private String date_of_verification;
	private String filled_by;
	private String verified_by_name;
	private String verified_by_other_actual_name;

	private String state_policy_first_doc_val;
	private String state_policy_first_availability;
	private String state_policy_first_d_source;
	private String district_policy_first_doc_val;
	private String district_policy_first_availability;
	private String district_policy_first_d_source;
	private String health_dept_first_doc_val;
	private String health_dept_first_availability;
	private String health_dept_first_d_source;
	private String non_health_dept_first_doc_val;
	private String non_health_dept_first_availability;
	private String non_health_dept_first_d_source;
	private String private_sec_first_doc_val;
	private String private_sec_first_availability;
	private String private_sec_first_d_source;
	private String large_scale_district_first_doc_val;
	private String large_scale_district_first_availability;
	private String large_scale_district_first_d_source;
	private String numberOfTickets;
	private String numberOfdistrictrow;
	private String numberOfhealthdeptrow;
	private String numberOfnonhealthdeptrow;
	private String numberOfprivsecrow;
	private String numberOflargescaledistrictrow;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	private String completed;
	private List<Form1ASaveDocumentsArray> large_scale_district_array;
	private List<Form1ASaveDocumentsArray> private_sec_array;
	private List<Form1ASaveDocumentsArray> non_health_dept_array;
	private List<Form1ASaveDocumentsArray> health_dept_array;
	private List<Form1ASaveDocumentsArray> district_policy_array;
	private List<Form1ASaveDocumentsArray> state_policy_array;
	private String source;
	
	
	public Form1ASave() {
		
	}

	public Form1ASave(String date_of_verification, String filled_by, String verified_by_name,
			String verified_by_other_actual_name, String state_policy_first_doc_val,
			String state_policy_first_availability, String state_policy_first_d_source,
			String district_policy_first_doc_val, String district_policy_first_availability,
			String district_policy_first_d_source, String health_dept_first_doc_val,
			String health_dept_first_availability, String health_dept_first_d_source,
			String non_health_dept_first_doc_val, String non_health_dept_first_availability,
			String non_health_dept_first_d_source, String private_sec_first_doc_val,
			String private_sec_first_availability, String private_sec_first_d_source,
			String large_scale_district_first_doc_val, String large_scale_district_first_availability,
			String large_scale_district_first_d_source, String numberOfTickets, String numberOfdistrictrow,
			String numberOfhealthdeptrow, String numberOfnonhealthdeptrow, String numberOfprivsecrow,
			String numberOflargescaledistrictrow, String district, String cycle, String year, String userid,String completed,
			List<Form1ASaveDocumentsArray> large_scale_district_array, List<Form1ASaveDocumentsArray> private_sec_array,
			List<Form1ASaveDocumentsArray> non_health_dept_array, List<Form1ASaveDocumentsArray> health_dept_array,
			List<Form1ASaveDocumentsArray> district_policy_array, List<Form1ASaveDocumentsArray> state_policy_array, String source) {
		this.date_of_verification = date_of_verification;
		this.filled_by = filled_by;
		this.verified_by_name = verified_by_name;
		this.verified_by_other_actual_name = verified_by_other_actual_name;
		this.state_policy_first_doc_val = state_policy_first_doc_val;
		this.state_policy_first_availability = state_policy_first_availability;
		this.state_policy_first_d_source = state_policy_first_d_source;
		this.district_policy_first_doc_val = district_policy_first_doc_val;
		this.district_policy_first_availability = district_policy_first_availability;
		this.district_policy_first_d_source = district_policy_first_d_source;
		this.health_dept_first_doc_val = health_dept_first_doc_val;
		this.health_dept_first_availability = health_dept_first_availability;
		this.health_dept_first_d_source = health_dept_first_d_source;
		this.non_health_dept_first_doc_val = non_health_dept_first_doc_val;
		this.non_health_dept_first_availability = non_health_dept_first_availability;
		this.non_health_dept_first_d_source = non_health_dept_first_d_source;
		this.private_sec_first_doc_val = private_sec_first_doc_val;
		this.private_sec_first_availability = private_sec_first_availability;
		this.private_sec_first_d_source = private_sec_first_d_source;
		this.large_scale_district_first_doc_val = large_scale_district_first_doc_val;
		this.large_scale_district_first_availability = large_scale_district_first_availability;
		this.large_scale_district_first_d_source = large_scale_district_first_d_source;
		this.numberOfTickets = numberOfTickets;
		this.numberOfdistrictrow = numberOfdistrictrow;
		this.numberOfhealthdeptrow = numberOfhealthdeptrow;
		this.numberOfnonhealthdeptrow = numberOfnonhealthdeptrow;
		this.numberOfprivsecrow = numberOfprivsecrow;
		this.numberOflargescaledistrictrow = numberOflargescaledistrictrow;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.completed = completed;
		this.large_scale_district_array = large_scale_district_array;
		this.private_sec_array = private_sec_array;
		this.non_health_dept_array = non_health_dept_array;
		this.health_dept_array = health_dept_array;
		this.district_policy_array = district_policy_array;
		this.state_policy_array = state_policy_array;
		this.source = source;
	}

	public String getDate_of_verification() {
		return date_of_verification;
	}

	public void setDate_of_verification(String date_of_verification) {
		this.date_of_verification = date_of_verification;
	}

	public String getFilled_by() {
		return filled_by;
	}

	public void setFilled_by(String filled_by) {
		this.filled_by = filled_by;
	}

	public String getVerified_by_name() {
		return verified_by_name;
	}

	public void setVerified_by_name(String verified_by_name) {
		this.verified_by_name = verified_by_name;
	}

	public String getVerified_by_other_actual_name() {
		return verified_by_other_actual_name;
	}

	public void setVerified_by_other_actual_name(String verified_by_other_actual_name) {
		this.verified_by_other_actual_name = verified_by_other_actual_name;
	}

	public String getState_policy_first_doc_val() {
		return state_policy_first_doc_val;
	}

	public void setState_policy_first_doc_val(String state_policy_first_doc_val) {
		this.state_policy_first_doc_val = state_policy_first_doc_val;
	}

	public String getState_policy_first_availability() {
		return state_policy_first_availability;
	}

	public void setState_policy_first_availability(String state_policy_first_availability) {
		this.state_policy_first_availability = state_policy_first_availability;
	}

	public String getState_policy_first_d_source() {
		return state_policy_first_d_source;
	}

	public void setState_policy_first_d_source(String state_policy_first_d_source) {
		this.state_policy_first_d_source = state_policy_first_d_source;
	}

	public String getDistrict_policy_first_doc_val() {
		return district_policy_first_doc_val;
	}

	public void setDistrict_policy_first_doc_val(String district_policy_first_doc_val) {
		this.district_policy_first_doc_val = district_policy_first_doc_val;
	}

	public String getDistrict_policy_first_availability() {
		return district_policy_first_availability;
	}

	public void setDistrict_policy_first_availability(String district_policy_first_availability) {
		this.district_policy_first_availability = district_policy_first_availability;
	}

	public String getDistrict_policy_first_d_source() {
		return district_policy_first_d_source;
	}

	public void setDistrict_policy_first_d_source(String district_policy_first_d_source) {
		this.district_policy_first_d_source = district_policy_first_d_source;
	}

	public String getHealth_dept_first_doc_val() {
		return health_dept_first_doc_val;
	}

	public void setHealth_dept_first_doc_val(String health_dept_first_doc_val) {
		this.health_dept_first_doc_val = health_dept_first_doc_val;
	}

	public String getHealth_dept_first_availability() {
		return health_dept_first_availability;
	}

	public void setHealth_dept_first_availability(String health_dept_first_availability) {
		this.health_dept_first_availability = health_dept_first_availability;
	}

	public String getHealth_dept_first_d_source() {
		return health_dept_first_d_source;
	}

	public void setHealth_dept_first_d_source(String health_dept_first_d_source) {
		this.health_dept_first_d_source = health_dept_first_d_source;
	}

	public String getNon_health_dept_first_doc_val() {
		return non_health_dept_first_doc_val;
	}

	public void setNon_health_dept_first_doc_val(String non_health_dept_first_doc_val) {
		this.non_health_dept_first_doc_val = non_health_dept_first_doc_val;
	}

	public String getNon_health_dept_first_availability() {
		return non_health_dept_first_availability;
	}

	public void setNon_health_dept_first_availability(String non_health_dept_first_availability) {
		this.non_health_dept_first_availability = non_health_dept_first_availability;
	}

	public String getNon_health_dept_first_d_source() {
		return non_health_dept_first_d_source;
	}

	public void setNon_health_dept_first_d_source(String non_health_dept_first_d_source) {
		this.non_health_dept_first_d_source = non_health_dept_first_d_source;
	}

	public String getPrivate_sec_first_doc_val() {
		return private_sec_first_doc_val;
	}

	public void setPrivate_sec_first_doc_val(String private_sec_first_doc_val) {
		this.private_sec_first_doc_val = private_sec_first_doc_val;
	}

	public String getPrivate_sec_first_availability() {
		return private_sec_first_availability;
	}

	public void setPrivate_sec_first_availability(String private_sec_first_availability) {
		this.private_sec_first_availability = private_sec_first_availability;
	}

	public String getPrivate_sec_first_d_source() {
		return private_sec_first_d_source;
	}

	public void setPrivate_sec_first_d_source(String private_sec_first_d_source) {
		this.private_sec_first_d_source = private_sec_first_d_source;
	}

	public String getLarge_scale_district_first_doc_val() {
		return large_scale_district_first_doc_val;
	}

	public void setLarge_scale_district_first_doc_val(String large_scale_district_first_doc_val) {
		this.large_scale_district_first_doc_val = large_scale_district_first_doc_val;
	}

	public String getLarge_scale_district_first_availability() {
		return large_scale_district_first_availability;
	}

	public void setLarge_scale_district_first_availability(String large_scale_district_first_availability) {
		this.large_scale_district_first_availability = large_scale_district_first_availability;
	}

	public String getLarge_scale_district_first_d_source() {
		return large_scale_district_first_d_source;
	}

	public void setLarge_scale_district_first_d_source(String large_scale_district_first_d_source) {
		this.large_scale_district_first_d_source = large_scale_district_first_d_source;
	}

	public String getNumberOfTickets() {
		return numberOfTickets;
	}

	public void setNumberOfTickets(String numberOfTickets) {
		this.numberOfTickets = numberOfTickets;
	}

	public String getNumberOfdistrictrow() {
		return numberOfdistrictrow;
	}

	public void setNumberOfdistrictrow(String numberOfdistrictrow) {
		this.numberOfdistrictrow = numberOfdistrictrow;
	}

	public String getNumberOfhealthdeptrow() {
		return numberOfhealthdeptrow;
	}

	public void setNumberOfhealthdeptrow(String numberOfhealthdeptrow) {
		this.numberOfhealthdeptrow = numberOfhealthdeptrow;
	}

	public String getNumberOfnonhealthdeptrow() {
		return numberOfnonhealthdeptrow;
	}

	public void setNumberOfnonhealthdeptrow(String numberOfnonhealthdeptrow) {
		this.numberOfnonhealthdeptrow = numberOfnonhealthdeptrow;
	}

	public String getNumberOfprivsecrow() {
		return numberOfprivsecrow;
	}

	public void setNumberOfprivsecrow(String numberOfprivsecrow) {
		this.numberOfprivsecrow = numberOfprivsecrow;
	}

	public String getNumberOflargescaledistrictrow() {
		return numberOflargescaledistrictrow;
	}

	public void setNumberOflargescaledistrictrow(String numberOflargescaledistrictrow) {
		this.numberOflargescaledistrictrow = numberOflargescaledistrictrow;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<Form1ASaveDocumentsArray> getLarge_scale_district_array() {
		return large_scale_district_array;
	}

	public void setLarge_scale_district_array(List<Form1ASaveDocumentsArray> large_scale_district_array) {
		this.large_scale_district_array = large_scale_district_array;
	}

	public List<Form1ASaveDocumentsArray> getPrivate_sec_array() {
		return private_sec_array;
	}

	public void setPrivate_sec_array(List<Form1ASaveDocumentsArray> private_sec_array) {
		this.private_sec_array = private_sec_array;
	}

	public List<Form1ASaveDocumentsArray> getNon_health_dept_array() {
		return non_health_dept_array;
	}

	public void setNon_health_dept_array(List<Form1ASaveDocumentsArray> non_health_dept_array) {
		this.non_health_dept_array = non_health_dept_array;
	}

	public List<Form1ASaveDocumentsArray> getHealth_dept_array() {
		return health_dept_array;
	}

	public void setHealth_dept_array(List<Form1ASaveDocumentsArray> health_dept_array) {
		this.health_dept_array = health_dept_array;
	}

	public List<Form1ASaveDocumentsArray> getDistrict_policy_array() {
		return district_policy_array;
	}

	public void setDistrict_policy_array(List<Form1ASaveDocumentsArray> district_policy_array) {
		this.district_policy_array = district_policy_array;
	}

	public List<Form1ASaveDocumentsArray> getState_policy_array() {
		return state_policy_array;
	}

	public void setState_policy_array(List<Form1ASaveDocumentsArray> state_policy_array) {
		this.state_policy_array = state_policy_array;
	}

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Form1ASave [date_of_verification=" + date_of_verification + ", filled_by=" + filled_by
				+ ", verified_by_name=" + verified_by_name + ", verified_by_other_actual_name="
				+ verified_by_other_actual_name + ", state_policy_first_doc_val=" + state_policy_first_doc_val
				+ ", state_policy_first_availability=" + state_policy_first_availability
				+ ", state_policy_first_d_source=" + state_policy_first_d_source + ", district_policy_first_doc_val="
				+ district_policy_first_doc_val + ", district_policy_first_availability="
				+ district_policy_first_availability + ", district_policy_first_d_source="
				+ district_policy_first_d_source + ", health_dept_first_doc_val=" + health_dept_first_doc_val
				+ ", health_dept_first_availability=" + health_dept_first_availability + ", health_dept_first_d_source="
				+ health_dept_first_d_source + ", non_health_dept_first_doc_val=" + non_health_dept_first_doc_val
				+ ", non_health_dept_first_availability=" + non_health_dept_first_availability
				+ ", non_health_dept_first_d_source=" + non_health_dept_first_d_source + ", private_sec_first_doc_val="
				+ private_sec_first_doc_val + ", private_sec_first_availability=" + private_sec_first_availability
				+ ", private_sec_first_d_source=" + private_sec_first_d_source + ", large_scale_district_first_doc_val="
				+ large_scale_district_first_doc_val + ", large_scale_district_first_availability="
				+ large_scale_district_first_availability + ", large_scale_district_first_d_source="
				+ large_scale_district_first_d_source + ", numberOfTickets=" + numberOfTickets
				+ ", numberOfdistrictrow=" + numberOfdistrictrow + ", numberOfhealthdeptrow=" + numberOfhealthdeptrow
				+ ", numberOfnonhealthdeptrow=" + numberOfnonhealthdeptrow + ", numberOfprivsecrow="
				+ numberOfprivsecrow + ", numberOflargescaledistrictrow=" + numberOflargescaledistrictrow
				+ ", district=" + district + ", cycle=" + cycle + ", year=" + year + ", userid=" + userid
				+ ", completed=" + completed + ", large_scale_district_array=" + large_scale_district_array
				+ ", private_sec_array=" + private_sec_array + ", non_health_dept_array=" + non_health_dept_array
				+ ", health_dept_array=" + health_dept_array + ", district_policy_array=" + district_policy_array
				+ ", state_policy_array=" + state_policy_array + ", source=" + source + "]";
	}

	

}