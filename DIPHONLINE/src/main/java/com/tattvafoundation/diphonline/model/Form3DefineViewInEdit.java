package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form3DefineViewInEdit {
	
	private String form_3_checkdate;
	private String form_3_meeting_venue;
	private String form_3_filled_by;
	private String form3_chair_person;
	private String form3_chair_person_others;
	private String theme_id;
	private String service_action_part_of_engagement;
	private String service_description_p_f_h_s_p;
	private String service_possible_s_t_i;
	private String service_action_part;
	private String service_form3_sl_no;
	private String service_action_required;
	private String workforce_service_action_part_of_engagement;
	private String workforce_service_description_p_f_h_s_p;
	private String workforce_service_possible_s_t_i;
	private String workforce_action_part;
	private String workforce_form3_sl_no;
	private String workforce_action_required;
	private String supplies_service_action_part_of_engagement;
	private String supplies_service_description_p_f_h_s_p;
	private String supplies_service_possible_s_t_i;
	private String supplies_action_part;
	private String supplies_form3_sl_no;
	private String supplies_action_required;
	private String health_service_action_part_of_engagement;
	private String health_service_description_p_f_h_s_p;
	private String health_service_possible_s_t_i;
	private String health_action_part;
	private String health_form3_sl_no;
	private String health_action_required;
	private String finance_service_action_part_of_engagement;
	private String finance_service_description_p_f_h_s_p;
	private String finance_service_possible_s_t_i;
	private String finance_action_part;
	private String finance_form3_sl_no;
	private String finance_action_required;
	private String policy_service_action_part_of_engagement;
	private String policy_service_description_p_f_h_s_p;
	private String policy_service_possible_s_t_i;
	private String policy_action_part;
	private String policy_form3_sl_no;
	private String policy_action_required;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	private String completed;
	private String form_3_id;
	
	
	private List<Form3DefineEditOnlyArray> service_array;
	private List<Form3DefineEditOnlyArray> workforce_array;
	private List<Form3DefineEditOnlyArray> supplies_array;
	private List<Form3DefineEditOnlyArray> health_array;
	private List<Form3DefineEditOnlyArray> finance_array;
	private List<Form3DefineEditOnlyArray> policy_array;
	
	private List<String> first_service_document_action_required;
	private List<String> first_workforce_document_action_required;
	private List<String> first_supplies_document_action_required;
	private List<String> first_health_document_action_required;
	private List<String> first_finance_document_action_required;
	private List<String> first_policy_document_action_required;
	
	public Form3DefineViewInEdit() {
		
	}

	public Form3DefineViewInEdit(String form_3_checkdate, String form_3_meeting_venue, String form_3_filled_by,
			String form3_chair_person, String form3_chair_person_others, String theme_id,
			String service_action_part_of_engagement, String service_description_p_f_h_s_p,
			String service_possible_s_t_i, String service_action_part, String service_form3_sl_no,
			String service_action_required, String workforce_service_action_part_of_engagement,
			String workforce_service_description_p_f_h_s_p, String workforce_service_possible_s_t_i,
			String workforce_action_part, String workforce_form3_sl_no, String workforce_action_required,
			String supplies_service_action_part_of_engagement, String supplies_service_description_p_f_h_s_p,
			String supplies_service_possible_s_t_i, String supplies_action_part, String supplies_form3_sl_no,
			String supplies_action_required, String health_service_action_part_of_engagement,
			String health_service_description_p_f_h_s_p, String health_service_possible_s_t_i,
			String health_action_part, String health_form3_sl_no, String health_action_required,
			String finance_service_action_part_of_engagement, String finance_service_description_p_f_h_s_p,
			String finance_service_possible_s_t_i, String finance_action_part, String finance_form3_sl_no,
			String finance_action_required, String policy_service_action_part_of_engagement,
			String policy_service_description_p_f_h_s_p, String policy_service_possible_s_t_i,
			String policy_action_part, String policy_form3_sl_no, String policy_action_required, String district,
			String cycle, String year, String userid, String completed, String form_3_id, List<Form3DefineEditOnlyArray> service_array,
			List<Form3DefineEditOnlyArray> workforce_array, List<Form3DefineEditOnlyArray> supplies_array,
			List<Form3DefineEditOnlyArray> health_array, List<Form3DefineEditOnlyArray> finance_array,
			List<Form3DefineEditOnlyArray> policy_array, List<String> first_service_document_action_required,
			List<String> first_workforce_document_action_required, List<String> first_supplies_document_action_required,
			List<String> first_health_document_action_required, List<String> first_finance_document_action_required,
			List<String> first_policy_document_action_required) {
		this.form_3_checkdate = form_3_checkdate;
		this.form_3_meeting_venue = form_3_meeting_venue;
		this.form_3_filled_by = form_3_filled_by;
		this.form3_chair_person = form3_chair_person;
		this.form3_chair_person_others = form3_chair_person_others;
		this.theme_id = theme_id;
		this.service_action_part_of_engagement = service_action_part_of_engagement;
		this.service_description_p_f_h_s_p = service_description_p_f_h_s_p;
		this.service_possible_s_t_i = service_possible_s_t_i;
		this.service_action_part = service_action_part;
		this.service_form3_sl_no = service_form3_sl_no;
		this.service_action_required = service_action_required;
		this.workforce_service_action_part_of_engagement = workforce_service_action_part_of_engagement;
		this.workforce_service_description_p_f_h_s_p = workforce_service_description_p_f_h_s_p;
		this.workforce_service_possible_s_t_i = workforce_service_possible_s_t_i;
		this.workforce_action_part = workforce_action_part;
		this.workforce_form3_sl_no = workforce_form3_sl_no;
		this.workforce_action_required = workforce_action_required;
		this.supplies_service_action_part_of_engagement = supplies_service_action_part_of_engagement;
		this.supplies_service_description_p_f_h_s_p = supplies_service_description_p_f_h_s_p;
		this.supplies_service_possible_s_t_i = supplies_service_possible_s_t_i;
		this.supplies_action_part = supplies_action_part;
		this.supplies_form3_sl_no = supplies_form3_sl_no;
		this.supplies_action_required = supplies_action_required;
		this.health_service_action_part_of_engagement = health_service_action_part_of_engagement;
		this.health_service_description_p_f_h_s_p = health_service_description_p_f_h_s_p;
		this.health_service_possible_s_t_i = health_service_possible_s_t_i;
		this.health_action_part = health_action_part;
		this.health_form3_sl_no = health_form3_sl_no;
		this.health_action_required = health_action_required;
		this.finance_service_action_part_of_engagement = finance_service_action_part_of_engagement;
		this.finance_service_description_p_f_h_s_p = finance_service_description_p_f_h_s_p;
		this.finance_service_possible_s_t_i = finance_service_possible_s_t_i;
		this.finance_action_part = finance_action_part;
		this.finance_form3_sl_no = finance_form3_sl_no;
		this.finance_action_required = finance_action_required;
		this.policy_service_action_part_of_engagement = policy_service_action_part_of_engagement;
		this.policy_service_description_p_f_h_s_p = policy_service_description_p_f_h_s_p;
		this.policy_service_possible_s_t_i = policy_service_possible_s_t_i;
		this.policy_action_part = policy_action_part;
		this.policy_form3_sl_no = policy_form3_sl_no;
		this.policy_action_required = policy_action_required;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.completed = completed;
		this.form_3_id = form_3_id;
		this.service_array = service_array;
		this.workforce_array = workforce_array;
		this.supplies_array = supplies_array;
		this.health_array = health_array;
		this.finance_array = finance_array;
		this.policy_array = policy_array;
		this.first_service_document_action_required = first_service_document_action_required;
		this.first_workforce_document_action_required = first_workforce_document_action_required;
		this.first_supplies_document_action_required = first_supplies_document_action_required;
		this.first_health_document_action_required = first_health_document_action_required;
		this.first_finance_document_action_required = first_finance_document_action_required;
		this.first_policy_document_action_required = first_policy_document_action_required;
	}

	public String getForm_3_checkdate() {
		return form_3_checkdate;
	}

	public void setForm_3_checkdate(String form_3_checkdate) {
		this.form_3_checkdate = form_3_checkdate;
	}

	public String getForm_3_meeting_venue() {
		return form_3_meeting_venue;
	}

	public void setForm_3_meeting_venue(String form_3_meeting_venue) {
		this.form_3_meeting_venue = form_3_meeting_venue;
	}

	public String getForm_3_filled_by() {
		return form_3_filled_by;
	}

	public void setForm_3_filled_by(String form_3_filled_by) {
		this.form_3_filled_by = form_3_filled_by;
	}

	public String getForm3_chair_person() {
		return form3_chair_person;
	}

	public void setForm3_chair_person(String form3_chair_person) {
		this.form3_chair_person = form3_chair_person;
	}

	public String getForm3_chair_person_others() {
		return form3_chair_person_others;
	}

	public void setForm3_chair_person_others(String form3_chair_person_others) {
		this.form3_chair_person_others = form3_chair_person_others;
	}

	public String getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(String theme_id) {
		this.theme_id = theme_id;
	}

	public String getService_action_part_of_engagement() {
		return service_action_part_of_engagement;
	}

	public void setService_action_part_of_engagement(String service_action_part_of_engagement) {
		this.service_action_part_of_engagement = service_action_part_of_engagement;
	}

	public String getService_description_p_f_h_s_p() {
		return service_description_p_f_h_s_p;
	}

	public void setService_description_p_f_h_s_p(String service_description_p_f_h_s_p) {
		this.service_description_p_f_h_s_p = service_description_p_f_h_s_p;
	}

	public String getService_possible_s_t_i() {
		return service_possible_s_t_i;
	}

	public void setService_possible_s_t_i(String service_possible_s_t_i) {
		this.service_possible_s_t_i = service_possible_s_t_i;
	}

	public String getService_action_part() {
		return service_action_part;
	}

	public void setService_action_part(String service_action_part) {
		this.service_action_part = service_action_part;
	}

	public String getService_form3_sl_no() {
		return service_form3_sl_no;
	}

	public void setService_form3_sl_no(String service_form3_sl_no) {
		this.service_form3_sl_no = service_form3_sl_no;
	}

	public String getService_action_required() {
		return service_action_required;
	}

	public void setService_action_required(String service_action_required) {
		this.service_action_required = service_action_required;
	}

	public String getWorkforce_service_action_part_of_engagement() {
		return workforce_service_action_part_of_engagement;
	}

	public void setWorkforce_service_action_part_of_engagement(String workforce_service_action_part_of_engagement) {
		this.workforce_service_action_part_of_engagement = workforce_service_action_part_of_engagement;
	}

	public String getWorkforce_service_description_p_f_h_s_p() {
		return workforce_service_description_p_f_h_s_p;
	}

	public void setWorkforce_service_description_p_f_h_s_p(String workforce_service_description_p_f_h_s_p) {
		this.workforce_service_description_p_f_h_s_p = workforce_service_description_p_f_h_s_p;
	}

	public String getWorkforce_service_possible_s_t_i() {
		return workforce_service_possible_s_t_i;
	}

	public void setWorkforce_service_possible_s_t_i(String workforce_service_possible_s_t_i) {
		this.workforce_service_possible_s_t_i = workforce_service_possible_s_t_i;
	}

	public String getWorkforce_action_part() {
		return workforce_action_part;
	}

	public void setWorkforce_action_part(String workforce_action_part) {
		this.workforce_action_part = workforce_action_part;
	}

	public String getWorkforce_form3_sl_no() {
		return workforce_form3_sl_no;
	}

	public void setWorkforce_form3_sl_no(String workforce_form3_sl_no) {
		this.workforce_form3_sl_no = workforce_form3_sl_no;
	}

	public String getWorkforce_action_required() {
		return workforce_action_required;
	}

	public void setWorkforce_action_required(String workforce_action_required) {
		this.workforce_action_required = workforce_action_required;
	}

	public String getSupplies_service_action_part_of_engagement() {
		return supplies_service_action_part_of_engagement;
	}

	public void setSupplies_service_action_part_of_engagement(String supplies_service_action_part_of_engagement) {
		this.supplies_service_action_part_of_engagement = supplies_service_action_part_of_engagement;
	}

	public String getSupplies_service_description_p_f_h_s_p() {
		return supplies_service_description_p_f_h_s_p;
	}

	public void setSupplies_service_description_p_f_h_s_p(String supplies_service_description_p_f_h_s_p) {
		this.supplies_service_description_p_f_h_s_p = supplies_service_description_p_f_h_s_p;
	}

	public String getSupplies_service_possible_s_t_i() {
		return supplies_service_possible_s_t_i;
	}

	public void setSupplies_service_possible_s_t_i(String supplies_service_possible_s_t_i) {
		this.supplies_service_possible_s_t_i = supplies_service_possible_s_t_i;
	}

	public String getSupplies_action_part() {
		return supplies_action_part;
	}

	public void setSupplies_action_part(String supplies_action_part) {
		this.supplies_action_part = supplies_action_part;
	}

	public String getSupplies_form3_sl_no() {
		return supplies_form3_sl_no;
	}

	public void setSupplies_form3_sl_no(String supplies_form3_sl_no) {
		this.supplies_form3_sl_no = supplies_form3_sl_no;
	}

	public String getSupplies_action_required() {
		return supplies_action_required;
	}

	public void setSupplies_action_required(String supplies_action_required) {
		this.supplies_action_required = supplies_action_required;
	}

	public String getHealth_service_action_part_of_engagement() {
		return health_service_action_part_of_engagement;
	}

	public void setHealth_service_action_part_of_engagement(String health_service_action_part_of_engagement) {
		this.health_service_action_part_of_engagement = health_service_action_part_of_engagement;
	}

	public String getHealth_service_description_p_f_h_s_p() {
		return health_service_description_p_f_h_s_p;
	}

	public void setHealth_service_description_p_f_h_s_p(String health_service_description_p_f_h_s_p) {
		this.health_service_description_p_f_h_s_p = health_service_description_p_f_h_s_p;
	}

	public String getHealth_service_possible_s_t_i() {
		return health_service_possible_s_t_i;
	}

	public void setHealth_service_possible_s_t_i(String health_service_possible_s_t_i) {
		this.health_service_possible_s_t_i = health_service_possible_s_t_i;
	}

	public String getHealth_action_part() {
		return health_action_part;
	}

	public void setHealth_action_part(String health_action_part) {
		this.health_action_part = health_action_part;
	}

	public String getHealth_form3_sl_no() {
		return health_form3_sl_no;
	}

	public void setHealth_form3_sl_no(String health_form3_sl_no) {
		this.health_form3_sl_no = health_form3_sl_no;
	}

	public String getHealth_action_required() {
		return health_action_required;
	}

	public void setHealth_action_required(String health_action_required) {
		this.health_action_required = health_action_required;
	}

	public String getFinance_service_action_part_of_engagement() {
		return finance_service_action_part_of_engagement;
	}

	public void setFinance_service_action_part_of_engagement(String finance_service_action_part_of_engagement) {
		this.finance_service_action_part_of_engagement = finance_service_action_part_of_engagement;
	}

	public String getFinance_service_description_p_f_h_s_p() {
		return finance_service_description_p_f_h_s_p;
	}

	public void setFinance_service_description_p_f_h_s_p(String finance_service_description_p_f_h_s_p) {
		this.finance_service_description_p_f_h_s_p = finance_service_description_p_f_h_s_p;
	}

	public String getFinance_service_possible_s_t_i() {
		return finance_service_possible_s_t_i;
	}

	public void setFinance_service_possible_s_t_i(String finance_service_possible_s_t_i) {
		this.finance_service_possible_s_t_i = finance_service_possible_s_t_i;
	}

	public String getFinance_action_part() {
		return finance_action_part;
	}

	public void setFinance_action_part(String finance_action_part) {
		this.finance_action_part = finance_action_part;
	}

	public String getFinance_form3_sl_no() {
		return finance_form3_sl_no;
	}

	public void setFinance_form3_sl_no(String finance_form3_sl_no) {
		this.finance_form3_sl_no = finance_form3_sl_no;
	}

	public String getFinance_action_required() {
		return finance_action_required;
	}

	public void setFinance_action_required(String finance_action_required) {
		this.finance_action_required = finance_action_required;
	}

	public String getPolicy_service_action_part_of_engagement() {
		return policy_service_action_part_of_engagement;
	}

	public void setPolicy_service_action_part_of_engagement(String policy_service_action_part_of_engagement) {
		this.policy_service_action_part_of_engagement = policy_service_action_part_of_engagement;
	}

	public String getPolicy_service_description_p_f_h_s_p() {
		return policy_service_description_p_f_h_s_p;
	}

	public void setPolicy_service_description_p_f_h_s_p(String policy_service_description_p_f_h_s_p) {
		this.policy_service_description_p_f_h_s_p = policy_service_description_p_f_h_s_p;
	}

	public String getPolicy_service_possible_s_t_i() {
		return policy_service_possible_s_t_i;
	}

	public void setPolicy_service_possible_s_t_i(String policy_service_possible_s_t_i) {
		this.policy_service_possible_s_t_i = policy_service_possible_s_t_i;
	}

	public String getPolicy_action_part() {
		return policy_action_part;
	}

	public void setPolicy_action_part(String policy_action_part) {
		this.policy_action_part = policy_action_part;
	}

	public String getPolicy_form3_sl_no() {
		return policy_form3_sl_no;
	}

	public void setPolicy_form3_sl_no(String policy_form3_sl_no) {
		this.policy_form3_sl_no = policy_form3_sl_no;
	}

	public String getPolicy_action_required() {
		return policy_action_required;
	}

	public void setPolicy_action_required(String policy_action_required) {
		this.policy_action_required = policy_action_required;
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

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public String getForm_3_id() {
		return form_3_id;
	}

	public void setForm_3_id(String form_3_id) {
		this.form_3_id = form_3_id;
	}

	public List<Form3DefineEditOnlyArray> getService_array() {
		return service_array;
	}

	public void setService_array(List<Form3DefineEditOnlyArray> service_array) {
		this.service_array = service_array;
	}

	public List<Form3DefineEditOnlyArray> getWorkforce_array() {
		return workforce_array;
	}

	public void setWorkforce_array(List<Form3DefineEditOnlyArray> workforce_array) {
		this.workforce_array = workforce_array;
	}

	public List<Form3DefineEditOnlyArray> getSupplies_array() {
		return supplies_array;
	}

	public void setSupplies_array(List<Form3DefineEditOnlyArray> supplies_array) {
		this.supplies_array = supplies_array;
	}

	public List<Form3DefineEditOnlyArray> getHealth_array() {
		return health_array;
	}

	public void setHealth_array(List<Form3DefineEditOnlyArray> health_array) {
		this.health_array = health_array;
	}

	public List<Form3DefineEditOnlyArray> getFinance_array() {
		return finance_array;
	}

	public void setFinance_array(List<Form3DefineEditOnlyArray> finance_array) {
		this.finance_array = finance_array;
	}

	public List<Form3DefineEditOnlyArray> getPolicy_array() {
		return policy_array;
	}

	public void setPolicy_array(List<Form3DefineEditOnlyArray> policy_array) {
		this.policy_array = policy_array;
	}

	public List<String> getFirst_service_document_action_required() {
		return first_service_document_action_required;
	}

	public void setFirst_service_document_action_required(List<String> first_service_document_action_required) {
		this.first_service_document_action_required = first_service_document_action_required;
	}

	public List<String> getFirst_workforce_document_action_required() {
		return first_workforce_document_action_required;
	}

	public void setFirst_workforce_document_action_required(List<String> first_workforce_document_action_required) {
		this.first_workforce_document_action_required = first_workforce_document_action_required;
	}

	public List<String> getFirst_supplies_document_action_required() {
		return first_supplies_document_action_required;
	}

	public void setFirst_supplies_document_action_required(List<String> first_supplies_document_action_required) {
		this.first_supplies_document_action_required = first_supplies_document_action_required;
	}

	public List<String> getFirst_health_document_action_required() {
		return first_health_document_action_required;
	}

	public void setFirst_health_document_action_required(List<String> first_health_document_action_required) {
		this.first_health_document_action_required = first_health_document_action_required;
	}

	public List<String> getFirst_finance_document_action_required() {
		return first_finance_document_action_required;
	}

	public void setFirst_finance_document_action_required(List<String> first_finance_document_action_required) {
		this.first_finance_document_action_required = first_finance_document_action_required;
	}

	public List<String> getFirst_policy_document_action_required() {
		return first_policy_document_action_required;
	}

	public void setFirst_policy_document_action_required(List<String> first_policy_document_action_required) {
		this.first_policy_document_action_required = first_policy_document_action_required;
	}

	@Override
	public String toString() {
		return "Form3DefineViewInEdit [form_3_checkdate=" + form_3_checkdate + ", form_3_meeting_venue="
				+ form_3_meeting_venue + ", form_3_filled_by=" + form_3_filled_by + ", form3_chair_person="
				+ form3_chair_person + ", form3_chair_person_others=" + form3_chair_person_others + ", theme_id="
				+ theme_id + ", service_action_part_of_engagement=" + service_action_part_of_engagement
				+ ", service_description_p_f_h_s_p=" + service_description_p_f_h_s_p + ", service_possible_s_t_i="
				+ service_possible_s_t_i + ", service_action_part=" + service_action_part + ", service_form3_sl_no="
				+ service_form3_sl_no + ", service_action_required=" + service_action_required
				+ ", workforce_service_action_part_of_engagement=" + workforce_service_action_part_of_engagement
				+ ", workforce_service_description_p_f_h_s_p=" + workforce_service_description_p_f_h_s_p
				+ ", workforce_service_possible_s_t_i=" + workforce_service_possible_s_t_i + ", workforce_action_part="
				+ workforce_action_part + ", workforce_form3_sl_no=" + workforce_form3_sl_no
				+ ", workforce_action_required=" + workforce_action_required
				+ ", supplies_service_action_part_of_engagement=" + supplies_service_action_part_of_engagement
				+ ", supplies_service_description_p_f_h_s_p=" + supplies_service_description_p_f_h_s_p
				+ ", supplies_service_possible_s_t_i=" + supplies_service_possible_s_t_i + ", supplies_action_part="
				+ supplies_action_part + ", supplies_form3_sl_no=" + supplies_form3_sl_no
				+ ", supplies_action_required=" + supplies_action_required
				+ ", health_service_action_part_of_engagement=" + health_service_action_part_of_engagement
				+ ", health_service_description_p_f_h_s_p=" + health_service_description_p_f_h_s_p
				+ ", health_service_possible_s_t_i=" + health_service_possible_s_t_i + ", health_action_part="
				+ health_action_part + ", health_form3_sl_no=" + health_form3_sl_no + ", health_action_required="
				+ health_action_required + ", finance_service_action_part_of_engagement="
				+ finance_service_action_part_of_engagement + ", finance_service_description_p_f_h_s_p="
				+ finance_service_description_p_f_h_s_p + ", finance_service_possible_s_t_i="
				+ finance_service_possible_s_t_i + ", finance_action_part=" + finance_action_part
				+ ", finance_form3_sl_no=" + finance_form3_sl_no + ", finance_action_required="
				+ finance_action_required + ", policy_service_action_part_of_engagement="
				+ policy_service_action_part_of_engagement + ", policy_service_description_p_f_h_s_p="
				+ policy_service_description_p_f_h_s_p + ", policy_service_possible_s_t_i="
				+ policy_service_possible_s_t_i + ", policy_action_part=" + policy_action_part + ", policy_form3_sl_no="
				+ policy_form3_sl_no + ", policy_action_required=" + policy_action_required + ", district=" + district
				+ ", cycle=" + cycle + ", year=" + year + ", userid=" + userid + ", completed=" + completed
				+ ", form_3_id=" + form_3_id + ", service_array=" + service_array + ", workforce_array="
				+ workforce_array + ", supplies_array=" + supplies_array + ", health_array=" + health_array
				+ ", finance_array=" + finance_array + ", policy_array=" + policy_array
				+ ", first_service_document_action_required=" + first_service_document_action_required
				+ ", first_workforce_document_action_required=" + first_workforce_document_action_required
				+ ", first_supplies_document_action_required=" + first_supplies_document_action_required
				+ ", first_health_document_action_required=" + first_health_document_action_required
				+ ", first_finance_document_action_required=" + first_finance_document_action_required
				+ ", first_policy_document_action_required=" + first_policy_document_action_required + "]";
	}

	
}
