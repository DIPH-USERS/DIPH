package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form4Plan {
	
	
	private String date_of_meeting;
	private String venue_of_meeting;
	private String chariperson_of_meeting;
	private String chariperson_of_meeting_others;
	private String theme_id;
	private String theme_leader;
	private String service_action_part;
	private String service_action_point_name;
	private String service_responsible_dept;
	private String service_person_responsible;
	private String service_target;
	private String service_indicator_name;
	private String service_description_of_indicator;
	private String service_target_value;
	private String workforce_action_part;
	private String workforce_action_point_name;
	private String workforce_responsible_dept;
	private String workforce_person_responsible;
	private String workforce_target;
	private String workforce_indicator_name;
	private String workforce_description_of_indicator;
	private String workforce_target_value;
	private String supplies_action_part;
	private String supplies_action_point_name;
	private String supplies_responsible_dept;
	private String supplies_person_responsible;
	private String supplies_target;
	private String supplies_indicator_name;
	private String supplies_description_of_indicator;
	private String supplies_target_value;
	private String health_action_part;
	private String health_action_point_name;
	private String health_responsible_dept;
	private String health_person_responsible;
	private String health_target;
	private String health_indicator_name;
	private String health_description_of_indicator;
	private String health_target_value;
	private String finance_action_part;
	private String finance_action_point_name;
	private String finance_responsible_dept;
	private String finance_person_responsible;
	private String finance_target;
	private String finance_indicator_name;
	private String finance_description_of_indicator;
	private String finance_target_value;
	private String policy_action_part;
	private String policy_action_point_name;
	private String policy_responsible_dept;
	private String policy_person_responsible;
	private String policy_target;
	private String policy_indicator_name;
	private String policy_description_of_indicator;
	private String policy_target_value;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	private String completed;
	
	
	private List<Form4PlanCommonObject> service_action_arr;
	private List<Form4PlanCommonObject> workforce_action_arr;
	private List<Form4PlanCommonObject> supplies_action_arr;
	private List<Form4PlanCommonObject> health_action_arr;
	private List<Form4PlanCommonObject> finance_action_arr;
	private List<Form4PlanCommonObject> policy_action_arr;
	private String source;
	
	public Form4Plan() {
		
	}

	public Form4Plan(String date_of_meeting, String venue_of_meeting, String chariperson_of_meeting,
			String chariperson_of_meeting_others, String theme_id, String theme_leader, String service_action_part,
			String service_action_point_name, String service_responsible_dept, String service_person_responsible,
			String service_target, String service_indicator_name, String service_description_of_indicator,
			String service_target_value, String workforce_action_part, String workforce_action_point_name,
			String workforce_responsible_dept, String workforce_person_responsible, String workforce_target,
			String workforce_indicator_name, String workforce_description_of_indicator, String workforce_target_value,
			String supplies_action_part, String supplies_action_point_name, String supplies_responsible_dept,
			String supplies_person_responsible, String supplies_target, String supplies_indicator_name,
			String supplies_description_of_indicator, String supplies_target_value, String health_action_part,
			String health_action_point_name, String health_responsible_dept, String health_person_responsible,
			String health_target, String health_indicator_name, String health_description_of_indicator,
			String health_target_value, String finance_action_part, String finance_action_point_name,
			String finance_responsible_dept, String finance_person_responsible, String finance_target,
			String finance_indicator_name, String finance_description_of_indicator, String finance_target_value,
			String policy_action_part, String policy_action_point_name, String policy_responsible_dept,
			String policy_person_responsible, String policy_target, String policy_indicator_name,
			String policy_description_of_indicator, String policy_target_value, String district, String cycle,
			String year, String userid, String completed, List<Form4PlanCommonObject> service_action_arr,
			List<Form4PlanCommonObject> workforce_action_arr, List<Form4PlanCommonObject> supplies_action_arr,
			List<Form4PlanCommonObject> health_action_arr, List<Form4PlanCommonObject> finance_action_arr,
			List<Form4PlanCommonObject> policy_action_arr, String source) {
		this.date_of_meeting = date_of_meeting;
		this.venue_of_meeting = venue_of_meeting;
		this.chariperson_of_meeting = chariperson_of_meeting;
		this.chariperson_of_meeting_others = chariperson_of_meeting_others;
		this.theme_id = theme_id;
		this.theme_leader = theme_leader;
		this.service_action_part = service_action_part;
		this.service_action_point_name = service_action_point_name;
		this.service_responsible_dept = service_responsible_dept;
		this.service_person_responsible = service_person_responsible;
		this.service_target = service_target;
		this.service_indicator_name = service_indicator_name;
		this.service_description_of_indicator = service_description_of_indicator;
		this.service_target_value = service_target_value;
		this.workforce_action_part = workforce_action_part;
		this.workforce_action_point_name = workforce_action_point_name;
		this.workforce_responsible_dept = workforce_responsible_dept;
		this.workforce_person_responsible = workforce_person_responsible;
		this.workforce_target = workforce_target;
		this.workforce_indicator_name = workforce_indicator_name;
		this.workforce_description_of_indicator = workforce_description_of_indicator;
		this.workforce_target_value = workforce_target_value;
		this.supplies_action_part = supplies_action_part;
		this.supplies_action_point_name = supplies_action_point_name;
		this.supplies_responsible_dept = supplies_responsible_dept;
		this.supplies_person_responsible = supplies_person_responsible;
		this.supplies_target = supplies_target;
		this.supplies_indicator_name = supplies_indicator_name;
		this.supplies_description_of_indicator = supplies_description_of_indicator;
		this.supplies_target_value = supplies_target_value;
		this.health_action_part = health_action_part;
		this.health_action_point_name = health_action_point_name;
		this.health_responsible_dept = health_responsible_dept;
		this.health_person_responsible = health_person_responsible;
		this.health_target = health_target;
		this.health_indicator_name = health_indicator_name;
		this.health_description_of_indicator = health_description_of_indicator;
		this.health_target_value = health_target_value;
		this.finance_action_part = finance_action_part;
		this.finance_action_point_name = finance_action_point_name;
		this.finance_responsible_dept = finance_responsible_dept;
		this.finance_person_responsible = finance_person_responsible;
		this.finance_target = finance_target;
		this.finance_indicator_name = finance_indicator_name;
		this.finance_description_of_indicator = finance_description_of_indicator;
		this.finance_target_value = finance_target_value;
		this.policy_action_part = policy_action_part;
		this.policy_action_point_name = policy_action_point_name;
		this.policy_responsible_dept = policy_responsible_dept;
		this.policy_person_responsible = policy_person_responsible;
		this.policy_target = policy_target;
		this.policy_indicator_name = policy_indicator_name;
		this.policy_description_of_indicator = policy_description_of_indicator;
		this.policy_target_value = policy_target_value;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.completed = completed;
		this.service_action_arr = service_action_arr;
		this.workforce_action_arr = workforce_action_arr;
		this.supplies_action_arr = supplies_action_arr;
		this.health_action_arr = health_action_arr;
		this.finance_action_arr = finance_action_arr;
		this.policy_action_arr = policy_action_arr;
		this.source = source;
	}

	public String getDate_of_meeting() {
		return date_of_meeting;
	}

	public void setDate_of_meeting(String date_of_meeting) {
		this.date_of_meeting = date_of_meeting;
	}

	public String getVenue_of_meeting() {
		return venue_of_meeting;
	}

	public void setVenue_of_meeting(String venue_of_meeting) {
		this.venue_of_meeting = venue_of_meeting;
	}

	public String getChariperson_of_meeting() {
		return chariperson_of_meeting;
	}

	public void setChariperson_of_meeting(String chariperson_of_meeting) {
		this.chariperson_of_meeting = chariperson_of_meeting;
	}

	public String getChariperson_of_meeting_others() {
		return chariperson_of_meeting_others;
	}

	public void setChariperson_of_meeting_others(String chariperson_of_meeting_others) {
		this.chariperson_of_meeting_others = chariperson_of_meeting_others;
	}

	public String getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(String theme_id) {
		this.theme_id = theme_id;
	}

	public String getTheme_leader() {
		return theme_leader;
	}

	public void setTheme_leader(String theme_leader) {
		this.theme_leader = theme_leader;
	}

	public String getService_action_part() {
		return service_action_part;
	}

	public void setService_action_part(String service_action_part) {
		this.service_action_part = service_action_part;
	}

	public String getService_action_point_name() {
		return service_action_point_name;
	}

	public void setService_action_point_name(String service_action_point_name) {
		this.service_action_point_name = service_action_point_name;
	}

	public String getService_responsible_dept() {
		return service_responsible_dept;
	}

	public void setService_responsible_dept(String service_responsible_dept) {
		this.service_responsible_dept = service_responsible_dept;
	}

	public String getService_person_responsible() {
		return service_person_responsible;
	}

	public void setService_person_responsible(String service_person_responsible) {
		this.service_person_responsible = service_person_responsible;
	}

	public String getService_target() {
		return service_target;
	}

	public void setService_target(String service_target) {
		this.service_target = service_target;
	}

	public String getService_indicator_name() {
		return service_indicator_name;
	}

	public void setService_indicator_name(String service_indicator_name) {
		this.service_indicator_name = service_indicator_name;
	}

	public String getService_description_of_indicator() {
		return service_description_of_indicator;
	}

	public void setService_description_of_indicator(String service_description_of_indicator) {
		this.service_description_of_indicator = service_description_of_indicator;
	}

	public String getService_target_value() {
		return service_target_value;
	}

	public void setService_target_value(String service_target_value) {
		this.service_target_value = service_target_value;
	}

	public String getWorkforce_action_part() {
		return workforce_action_part;
	}

	public void setWorkforce_action_part(String workforce_action_part) {
		this.workforce_action_part = workforce_action_part;
	}

	public String getWorkforce_action_point_name() {
		return workforce_action_point_name;
	}

	public void setWorkforce_action_point_name(String workforce_action_point_name) {
		this.workforce_action_point_name = workforce_action_point_name;
	}

	public String getWorkforce_responsible_dept() {
		return workforce_responsible_dept;
	}

	public void setWorkforce_responsible_dept(String workforce_responsible_dept) {
		this.workforce_responsible_dept = workforce_responsible_dept;
	}

	public String getWorkforce_person_responsible() {
		return workforce_person_responsible;
	}

	public void setWorkforce_person_responsible(String workforce_person_responsible) {
		this.workforce_person_responsible = workforce_person_responsible;
	}

	public String getWorkforce_target() {
		return workforce_target;
	}

	public void setWorkforce_target(String workforce_target) {
		this.workforce_target = workforce_target;
	}

	public String getWorkforce_indicator_name() {
		return workforce_indicator_name;
	}

	public void setWorkforce_indicator_name(String workforce_indicator_name) {
		this.workforce_indicator_name = workforce_indicator_name;
	}

	public String getWorkforce_description_of_indicator() {
		return workforce_description_of_indicator;
	}

	public void setWorkforce_description_of_indicator(String workforce_description_of_indicator) {
		this.workforce_description_of_indicator = workforce_description_of_indicator;
	}

	public String getWorkforce_target_value() {
		return workforce_target_value;
	}

	public void setWorkforce_target_value(String workforce_target_value) {
		this.workforce_target_value = workforce_target_value;
	}

	public String getSupplies_action_part() {
		return supplies_action_part;
	}

	public void setSupplies_action_part(String supplies_action_part) {
		this.supplies_action_part = supplies_action_part;
	}

	public String getSupplies_action_point_name() {
		return supplies_action_point_name;
	}

	public void setSupplies_action_point_name(String supplies_action_point_name) {
		this.supplies_action_point_name = supplies_action_point_name;
	}

	public String getSupplies_responsible_dept() {
		return supplies_responsible_dept;
	}

	public void setSupplies_responsible_dept(String supplies_responsible_dept) {
		this.supplies_responsible_dept = supplies_responsible_dept;
	}

	public String getSupplies_person_responsible() {
		return supplies_person_responsible;
	}

	public void setSupplies_person_responsible(String supplies_person_responsible) {
		this.supplies_person_responsible = supplies_person_responsible;
	}

	public String getSupplies_target() {
		return supplies_target;
	}

	public void setSupplies_target(String supplies_target) {
		this.supplies_target = supplies_target;
	}

	public String getSupplies_indicator_name() {
		return supplies_indicator_name;
	}

	public void setSupplies_indicator_name(String supplies_indicator_name) {
		this.supplies_indicator_name = supplies_indicator_name;
	}

	public String getSupplies_description_of_indicator() {
		return supplies_description_of_indicator;
	}

	public void setSupplies_description_of_indicator(String supplies_description_of_indicator) {
		this.supplies_description_of_indicator = supplies_description_of_indicator;
	}

	public String getSupplies_target_value() {
		return supplies_target_value;
	}

	public void setSupplies_target_value(String supplies_target_value) {
		this.supplies_target_value = supplies_target_value;
	}

	public String getHealth_action_part() {
		return health_action_part;
	}

	public void setHealth_action_part(String health_action_part) {
		this.health_action_part = health_action_part;
	}

	public String getHealth_action_point_name() {
		return health_action_point_name;
	}

	public void setHealth_action_point_name(String health_action_point_name) {
		this.health_action_point_name = health_action_point_name;
	}

	public String getHealth_responsible_dept() {
		return health_responsible_dept;
	}

	public void setHealth_responsible_dept(String health_responsible_dept) {
		this.health_responsible_dept = health_responsible_dept;
	}

	public String getHealth_person_responsible() {
		return health_person_responsible;
	}

	public void setHealth_person_responsible(String health_person_responsible) {
		this.health_person_responsible = health_person_responsible;
	}

	public String getHealth_target() {
		return health_target;
	}

	public void setHealth_target(String health_target) {
		this.health_target = health_target;
	}

	public String getHealth_indicator_name() {
		return health_indicator_name;
	}

	public void setHealth_indicator_name(String health_indicator_name) {
		this.health_indicator_name = health_indicator_name;
	}

	public String getHealth_description_of_indicator() {
		return health_description_of_indicator;
	}

	public void setHealth_description_of_indicator(String health_description_of_indicator) {
		this.health_description_of_indicator = health_description_of_indicator;
	}

	public String getHealth_target_value() {
		return health_target_value;
	}

	public void setHealth_target_value(String health_target_value) {
		this.health_target_value = health_target_value;
	}

	public String getFinance_action_part() {
		return finance_action_part;
	}

	public void setFinance_action_part(String finance_action_part) {
		this.finance_action_part = finance_action_part;
	}

	public String getFinance_action_point_name() {
		return finance_action_point_name;
	}

	public void setFinance_action_point_name(String finance_action_point_name) {
		this.finance_action_point_name = finance_action_point_name;
	}

	public String getFinance_responsible_dept() {
		return finance_responsible_dept;
	}

	public void setFinance_responsible_dept(String finance_responsible_dept) {
		this.finance_responsible_dept = finance_responsible_dept;
	}

	public String getFinance_person_responsible() {
		return finance_person_responsible;
	}

	public void setFinance_person_responsible(String finance_person_responsible) {
		this.finance_person_responsible = finance_person_responsible;
	}

	public String getFinance_target() {
		return finance_target;
	}

	public void setFinance_target(String finance_target) {
		this.finance_target = finance_target;
	}

	public String getFinance_indicator_name() {
		return finance_indicator_name;
	}

	public void setFinance_indicator_name(String finance_indicator_name) {
		this.finance_indicator_name = finance_indicator_name;
	}

	public String getFinance_description_of_indicator() {
		return finance_description_of_indicator;
	}

	public void setFinance_description_of_indicator(String finance_description_of_indicator) {
		this.finance_description_of_indicator = finance_description_of_indicator;
	}

	public String getFinance_target_value() {
		return finance_target_value;
	}

	public void setFinance_target_value(String finance_target_value) {
		this.finance_target_value = finance_target_value;
	}

	public String getPolicy_action_part() {
		return policy_action_part;
	}

	public void setPolicy_action_part(String policy_action_part) {
		this.policy_action_part = policy_action_part;
	}

	public String getPolicy_action_point_name() {
		return policy_action_point_name;
	}

	public void setPolicy_action_point_name(String policy_action_point_name) {
		this.policy_action_point_name = policy_action_point_name;
	}

	public String getPolicy_responsible_dept() {
		return policy_responsible_dept;
	}

	public void setPolicy_responsible_dept(String policy_responsible_dept) {
		this.policy_responsible_dept = policy_responsible_dept;
	}

	public String getPolicy_person_responsible() {
		return policy_person_responsible;
	}

	public void setPolicy_person_responsible(String policy_person_responsible) {
		this.policy_person_responsible = policy_person_responsible;
	}

	public String getPolicy_target() {
		return policy_target;
	}

	public void setPolicy_target(String policy_target) {
		this.policy_target = policy_target;
	}

	public String getPolicy_indicator_name() {
		return policy_indicator_name;
	}

	public void setPolicy_indicator_name(String policy_indicator_name) {
		this.policy_indicator_name = policy_indicator_name;
	}

	public String getPolicy_description_of_indicator() {
		return policy_description_of_indicator;
	}

	public void setPolicy_description_of_indicator(String policy_description_of_indicator) {
		this.policy_description_of_indicator = policy_description_of_indicator;
	}

	public String getPolicy_target_value() {
		return policy_target_value;
	}

	public void setPolicy_target_value(String policy_target_value) {
		this.policy_target_value = policy_target_value;
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

	public List<Form4PlanCommonObject> getService_action_arr() {
		return service_action_arr;
	}

	public void setService_action_arr(List<Form4PlanCommonObject> service_action_arr) {
		this.service_action_arr = service_action_arr;
	}

	public List<Form4PlanCommonObject> getWorkforce_action_arr() {
		return workforce_action_arr;
	}

	public void setWorkforce_action_arr(List<Form4PlanCommonObject> workforce_action_arr) {
		this.workforce_action_arr = workforce_action_arr;
	}

	public List<Form4PlanCommonObject> getSupplies_action_arr() {
		return supplies_action_arr;
	}

	public void setSupplies_action_arr(List<Form4PlanCommonObject> supplies_action_arr) {
		this.supplies_action_arr = supplies_action_arr;
	}

	public List<Form4PlanCommonObject> getHealth_action_arr() {
		return health_action_arr;
	}

	public void setHealth_action_arr(List<Form4PlanCommonObject> health_action_arr) {
		this.health_action_arr = health_action_arr;
	}

	public List<Form4PlanCommonObject> getFinance_action_arr() {
		return finance_action_arr;
	}

	public void setFinance_action_arr(List<Form4PlanCommonObject> finance_action_arr) {
		this.finance_action_arr = finance_action_arr;
	}

	public List<Form4PlanCommonObject> getPolicy_action_arr() {
		return policy_action_arr;
	}

	public void setPolicy_action_arr(List<Form4PlanCommonObject> policy_action_arr) {
		this.policy_action_arr = policy_action_arr;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "Form4Plan [date_of_meeting=" + date_of_meeting + ", venue_of_meeting=" + venue_of_meeting
				+ ", chariperson_of_meeting=" + chariperson_of_meeting + ", chariperson_of_meeting_others="
				+ chariperson_of_meeting_others + ", theme_id=" + theme_id + ", theme_leader=" + theme_leader
				+ ", service_action_part=" + service_action_part + ", service_action_point_name="
				+ service_action_point_name + ", service_responsible_dept=" + service_responsible_dept
				+ ", service_person_responsible=" + service_person_responsible + ", service_target=" + service_target
				+ ", service_indicator_name=" + service_indicator_name + ", service_description_of_indicator="
				+ service_description_of_indicator + ", service_target_value=" + service_target_value
				+ ", workforce_action_part=" + workforce_action_part + ", workforce_action_point_name="
				+ workforce_action_point_name + ", workforce_responsible_dept=" + workforce_responsible_dept
				+ ", workforce_person_responsible=" + workforce_person_responsible + ", workforce_target="
				+ workforce_target + ", workforce_indicator_name=" + workforce_indicator_name
				+ ", workforce_description_of_indicator=" + workforce_description_of_indicator
				+ ", workforce_target_value=" + workforce_target_value + ", supplies_action_part="
				+ supplies_action_part + ", supplies_action_point_name=" + supplies_action_point_name
				+ ", supplies_responsible_dept=" + supplies_responsible_dept + ", supplies_person_responsible="
				+ supplies_person_responsible + ", supplies_target=" + supplies_target + ", supplies_indicator_name="
				+ supplies_indicator_name + ", supplies_description_of_indicator=" + supplies_description_of_indicator
				+ ", supplies_target_value=" + supplies_target_value + ", health_action_part=" + health_action_part
				+ ", health_action_point_name=" + health_action_point_name + ", health_responsible_dept="
				+ health_responsible_dept + ", health_person_responsible=" + health_person_responsible
				+ ", health_target=" + health_target + ", health_indicator_name=" + health_indicator_name
				+ ", health_description_of_indicator=" + health_description_of_indicator + ", health_target_value="
				+ health_target_value + ", finance_action_part=" + finance_action_part + ", finance_action_point_name="
				+ finance_action_point_name + ", finance_responsible_dept=" + finance_responsible_dept
				+ ", finance_person_responsible=" + finance_person_responsible + ", finance_target=" + finance_target
				+ ", finance_indicator_name=" + finance_indicator_name + ", finance_description_of_indicator="
				+ finance_description_of_indicator + ", finance_target_value=" + finance_target_value
				+ ", policy_action_part=" + policy_action_part + ", policy_action_point_name="
				+ policy_action_point_name + ", policy_responsible_dept=" + policy_responsible_dept
				+ ", policy_person_responsible=" + policy_person_responsible + ", policy_target=" + policy_target
				+ ", policy_indicator_name=" + policy_indicator_name + ", policy_description_of_indicator="
				+ policy_description_of_indicator + ", policy_target_value=" + policy_target_value + ", district="
				+ district + ", cycle=" + cycle + ", year=" + year + ", userid=" + userid + ", completed=" + completed
				+ ", service_action_arr=" + service_action_arr + ", workforce_action_arr=" + workforce_action_arr
				+ ", supplies_action_arr=" + supplies_action_arr + ", health_action_arr=" + health_action_arr
				+ ", finance_action_arr=" + finance_action_arr + ", policy_action_arr=" + policy_action_arr
				+ ", source=" + source + "]";
	}
	
	

}