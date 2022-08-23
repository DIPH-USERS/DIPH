package com.tattvafoundation.diphonline.model;

import java.util.List;
 

public class Form5Followup {
	private String date_of_meeting;
	private String venue_of_meeting;
	private String chairperson_of_meeting;
	private String chairperson_of_meeting_others;
	private String theme_id;
	private String theme_leader;
	private String no_of_meetings_resp_team;
	private List<Form5FollowUpSaveMeetingList> meeting_arr;
	
	private List<Form5FollowUpActionCommonArray> service_action_arr;
	private List<Form5FollowUpActionCommonArray> workforce_action_arr;
	private List<Form5FollowUpActionCommonArray> supplies_action_arr;
	private List<Form5FollowUpActionCommonArray> health_action_arr;
	private List<Form5FollowUpActionCommonArray> finance_action_arr;
	private List<Form5FollowUpActionCommonArray> policy_action_arr;
	
	private String meeting_no;
	private String meeting_date;
	private String stakeholder_meeting;
	private String no_of_participants;
	private String cov_indicators;
	private String ci_source;
	private String time_zero;
	private String time_one;
	private String time_three;
	private String time_four;
	private String timezero_date;
	private String timeone_date;
	private String timetwo_date;
	private String timethree_date;
	
	private List<Form5FollowUpTotalCoverageIndicators> total_coverage_indi;

	
	private String total_coverage_indi_timezero_date;
	private String total_coverage_indi_timeone_date;
	private String total_coverage_indi_timetwo_date;
	private String total_coverage_indi_timethree_date;
	
	

	
	private String service_act_point;
	private String service_act_indicators;
	private String service_progress_indicators;
	private String service_status;
	private String service_revised_timeline;
	private String service_change_in_responsibility;
	private String workforce_act_point;
	private String workforce_act_indicators;
	private String workforce_progress_indicators;
	private String workforce_status;
	private String workforce_revised_timeline;
	private String workforce_change_in_responsibility;
	private String supplies_act_point;
	private String supplies_act_indicators;
	private String supplies_act_progress_indicators;
	private String supplies_status;
	private String supplies_revised_timeline;
	private String supplies_change_in_responsibility;
	private String health_act_point;
	private String health_act_indicators;
	private String health_progress_indicators;
	private String health_status;
	private String health_revised_timeline;
	private String health_change_in_responsibility;
	private String finance_act_point;
	private String finance_act_indicators;
	private String finance_progress_indicators;
	private String finance_status;
	private String finance_revised_timeline;
	private String finance_change_in_responsibility;
	private String policy_act_point;
	private String policy_act_indicators;
	private String policy_progress_indicators;
	private String policy_status;
	private String policy_revised_timeline;
	private String policy_change_in_responsibility;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	private String completed;
	private String source;
	
	
	public Form5Followup() {
		
	}


	public Form5Followup(String date_of_meeting, String venue_of_meeting, String chairperson_of_meeting,
			String chairperson_of_meeting_others, String theme_id, String theme_leader, String no_of_meetings_resp_team,
			List<Form5FollowUpSaveMeetingList> meeting_arr, List<Form5FollowUpActionCommonArray> service_action_arr,
			List<Form5FollowUpActionCommonArray> workforce_action_arr,
			List<Form5FollowUpActionCommonArray> supplies_action_arr,
			List<Form5FollowUpActionCommonArray> health_action_arr,
			List<Form5FollowUpActionCommonArray> finance_action_arr,
			List<Form5FollowUpActionCommonArray> policy_action_arr, String meeting_no, String meeting_date,
			String stakeholder_meeting, String no_of_participants, String cov_indicators, String ci_source,
			String time_zero, String time_one, String time_three, String time_four, String timezero_date,
			String timeone_date, String timetwo_date, String timethree_date,
			List<Form5FollowUpTotalCoverageIndicators> total_coverage_indi, String total_coverage_indi_timezero_date,
			String total_coverage_indi_timeone_date, String total_coverage_indi_timetwo_date,
			String total_coverage_indi_timethree_date, String service_act_point, String service_act_indicators,
			String service_progress_indicators, String service_status, String service_revised_timeline,
			String service_change_in_responsibility, String workforce_act_point, String workforce_act_indicators,
			String workforce_progress_indicators, String workforce_status, String workforce_revised_timeline,
			String workforce_change_in_responsibility, String supplies_act_point, String supplies_act_indicators,
			String supplies_act_progress_indicators, String supplies_status, String supplies_revised_timeline,
			String supplies_change_in_responsibility, String health_act_point, String health_act_indicators,
			String health_progress_indicators, String health_status, String health_revised_timeline,
			String health_change_in_responsibility, String finance_act_point, String finance_act_indicators,
			String finance_progress_indicators, String finance_status, String finance_revised_timeline,
			String finance_change_in_responsibility, String policy_act_point, String policy_act_indicators,
			String policy_progress_indicators, String policy_status, String policy_revised_timeline,
			String policy_change_in_responsibility, String district, String cycle, String year, String userid, String completed, String source) {
		this.date_of_meeting = date_of_meeting;
		this.venue_of_meeting = venue_of_meeting;
		this.chairperson_of_meeting = chairperson_of_meeting;
		this.chairperson_of_meeting_others = chairperson_of_meeting_others;
		this.theme_id = theme_id;
		this.theme_leader = theme_leader;
		this.no_of_meetings_resp_team = no_of_meetings_resp_team;
		this.meeting_arr = meeting_arr;
		this.service_action_arr = service_action_arr;
		this.workforce_action_arr = workforce_action_arr;
		this.supplies_action_arr = supplies_action_arr;
		this.health_action_arr = health_action_arr;
		this.finance_action_arr = finance_action_arr;
		this.policy_action_arr = policy_action_arr;
		this.meeting_no = meeting_no;
		this.meeting_date = meeting_date;
		this.stakeholder_meeting = stakeholder_meeting;
		this.no_of_participants = no_of_participants;
		this.cov_indicators = cov_indicators;
		this.ci_source = ci_source;
		this.time_zero = time_zero;
		this.time_one = time_one;
		this.time_three = time_three;
		this.time_four = time_four;
		this.timezero_date = timezero_date;
		this.timeone_date = timeone_date;
		this.timetwo_date = timetwo_date;
		this.timethree_date = timethree_date;
		this.total_coverage_indi = total_coverage_indi;
		this.total_coverage_indi_timezero_date = total_coverage_indi_timezero_date;
		this.total_coverage_indi_timeone_date = total_coverage_indi_timeone_date;
		this.total_coverage_indi_timetwo_date = total_coverage_indi_timetwo_date;
		this.total_coverage_indi_timethree_date = total_coverage_indi_timethree_date;
		this.service_act_point = service_act_point;
		this.service_act_indicators = service_act_indicators;
		this.service_progress_indicators = service_progress_indicators;
		this.service_status = service_status;
		this.service_revised_timeline = service_revised_timeline;
		this.service_change_in_responsibility = service_change_in_responsibility;
		this.workforce_act_point = workforce_act_point;
		this.workforce_act_indicators = workforce_act_indicators;
		this.workforce_progress_indicators = workforce_progress_indicators;
		this.workforce_status = workforce_status;
		this.workforce_revised_timeline = workforce_revised_timeline;
		this.workforce_change_in_responsibility = workforce_change_in_responsibility;
		this.supplies_act_point = supplies_act_point;
		this.supplies_act_indicators = supplies_act_indicators;
		this.supplies_act_progress_indicators = supplies_act_progress_indicators;
		this.supplies_status = supplies_status;
		this.supplies_revised_timeline = supplies_revised_timeline;
		this.supplies_change_in_responsibility = supplies_change_in_responsibility;
		this.health_act_point = health_act_point;
		this.health_act_indicators = health_act_indicators;
		this.health_progress_indicators = health_progress_indicators;
		this.health_status = health_status;
		this.health_revised_timeline = health_revised_timeline;
		this.health_change_in_responsibility = health_change_in_responsibility;
		this.finance_act_point = finance_act_point;
		this.finance_act_indicators = finance_act_indicators;
		this.finance_progress_indicators = finance_progress_indicators;
		this.finance_status = finance_status;
		this.finance_revised_timeline = finance_revised_timeline;
		this.finance_change_in_responsibility = finance_change_in_responsibility;
		this.policy_act_point = policy_act_point;
		this.policy_act_indicators = policy_act_indicators;
		this.policy_progress_indicators = policy_progress_indicators;
		this.policy_status = policy_status;
		this.policy_revised_timeline = policy_revised_timeline;
		this.policy_change_in_responsibility = policy_change_in_responsibility;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.completed = completed;
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


	public String getChairperson_of_meeting() {
		return chairperson_of_meeting;
	}


	public void setChairperson_of_meeting(String chairperson_of_meeting) {
		this.chairperson_of_meeting = chairperson_of_meeting;
	}


	public String getChairperson_of_meeting_others() {
		return chairperson_of_meeting_others;
	}


	public void setChairperson_of_meeting_others(String chairperson_of_meeting_others) {
		this.chairperson_of_meeting_others = chairperson_of_meeting_others;
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


	public String getNo_of_meetings_resp_team() {
		return no_of_meetings_resp_team;
	}


	public void setNo_of_meetings_resp_team(String no_of_meetings_resp_team) {
		this.no_of_meetings_resp_team = no_of_meetings_resp_team;
	}


	public List<Form5FollowUpSaveMeetingList> getMeeting_arr() {
		return meeting_arr;
	}


	public void setMeeting_arr(List<Form5FollowUpSaveMeetingList> meeting_arr) {
		this.meeting_arr = meeting_arr;
	}


	public List<Form5FollowUpActionCommonArray> getService_action_arr() {
		return service_action_arr;
	}


	public void setService_action_arr(List<Form5FollowUpActionCommonArray> service_action_arr) {
		this.service_action_arr = service_action_arr;
	}


	public List<Form5FollowUpActionCommonArray> getWorkforce_action_arr() {
		return workforce_action_arr;
	}


	public void setWorkforce_action_arr(List<Form5FollowUpActionCommonArray> workforce_action_arr) {
		this.workforce_action_arr = workforce_action_arr;
	}


	public List<Form5FollowUpActionCommonArray> getSupplies_action_arr() {
		return supplies_action_arr;
	}


	public void setSupplies_action_arr(List<Form5FollowUpActionCommonArray> supplies_action_arr) {
		this.supplies_action_arr = supplies_action_arr;
	}


	public List<Form5FollowUpActionCommonArray> getHealth_action_arr() {
		return health_action_arr;
	}


	public void setHealth_action_arr(List<Form5FollowUpActionCommonArray> health_action_arr) {
		this.health_action_arr = health_action_arr;
	}


	public List<Form5FollowUpActionCommonArray> getFinance_action_arr() {
		return finance_action_arr;
	}


	public void setFinance_action_arr(List<Form5FollowUpActionCommonArray> finance_action_arr) {
		this.finance_action_arr = finance_action_arr;
	}


	public List<Form5FollowUpActionCommonArray> getPolicy_action_arr() {
		return policy_action_arr;
	}


	public void setPolicy_action_arr(List<Form5FollowUpActionCommonArray> policy_action_arr) {
		this.policy_action_arr = policy_action_arr;
	}


	public String getMeeting_no() {
		return meeting_no;
	}


	public void setMeeting_no(String meeting_no) {
		this.meeting_no = meeting_no;
	}


	public String getMeeting_date() {
		return meeting_date;
	}


	public void setMeeting_date(String meeting_date) {
		this.meeting_date = meeting_date;
	}


	public String getStakeholder_meeting() {
		return stakeholder_meeting;
	}


	public void setStakeholder_meeting(String stakeholder_meeting) {
		this.stakeholder_meeting = stakeholder_meeting;
	}


	public String getNo_of_participants() {
		return no_of_participants;
	}


	public void setNo_of_participants(String no_of_participants) {
		this.no_of_participants = no_of_participants;
	}


	public String getCov_indicators() {
		return cov_indicators;
	}


	public void setCov_indicators(String cov_indicators) {
		this.cov_indicators = cov_indicators;
	}


	public String getCi_source() {
		return ci_source;
	}


	public void setCi_source(String ci_source) {
		this.ci_source = ci_source;
	}


	public String getTime_zero() {
		return time_zero;
	}


	public void setTime_zero(String time_zero) {
		this.time_zero = time_zero;
	}


	public String getTime_one() {
		return time_one;
	}


	public void setTime_one(String time_one) {
		this.time_one = time_one;
	}


	public String getTime_three() {
		return time_three;
	}


	public void setTime_three(String time_three) {
		this.time_three = time_three;
	}


	public String getTime_four() {
		return time_four;
	}


	public void setTime_four(String time_four) {
		this.time_four = time_four;
	}


	public String getTimezero_date() {
		return timezero_date;
	}


	public void setTimezero_date(String timezero_date) {
		this.timezero_date = timezero_date;
	}


	public String getTimeone_date() {
		return timeone_date;
	}


	public void setTimeone_date(String timeone_date) {
		this.timeone_date = timeone_date;
	}


	public String getTimetwo_date() {
		return timetwo_date;
	}


	public void setTimetwo_date(String timetwo_date) {
		this.timetwo_date = timetwo_date;
	}


	public String getTimethree_date() {
		return timethree_date;
	}


	public void setTimethree_date(String timethree_date) {
		this.timethree_date = timethree_date;
	}


	public List<Form5FollowUpTotalCoverageIndicators> getTotal_coverage_indi() {
		return total_coverage_indi;
	}


	public void setTotal_coverage_indi(List<Form5FollowUpTotalCoverageIndicators> total_coverage_indi) {
		this.total_coverage_indi = total_coverage_indi;
	}


	public String getTotal_coverage_indi_timezero_date() {
		return total_coverage_indi_timezero_date;
	}


	public void setTotal_coverage_indi_timezero_date(String total_coverage_indi_timezero_date) {
		this.total_coverage_indi_timezero_date = total_coverage_indi_timezero_date;
	}


	public String getTotal_coverage_indi_timeone_date() {
		return total_coverage_indi_timeone_date;
	}


	public void setTotal_coverage_indi_timeone_date(String total_coverage_indi_timeone_date) {
		this.total_coverage_indi_timeone_date = total_coverage_indi_timeone_date;
	}


	public String getTotal_coverage_indi_timetwo_date() {
		return total_coverage_indi_timetwo_date;
	}


	public void setTotal_coverage_indi_timetwo_date(String total_coverage_indi_timetwo_date) {
		this.total_coverage_indi_timetwo_date = total_coverage_indi_timetwo_date;
	}


	public String getTotal_coverage_indi_timethree_date() {
		return total_coverage_indi_timethree_date;
	}


	public void setTotal_coverage_indi_timethree_date(String total_coverage_indi_timethree_date) {
		this.total_coverage_indi_timethree_date = total_coverage_indi_timethree_date;
	}


	public String getService_act_point() {
		return service_act_point;
	}


	public void setService_act_point(String service_act_point) {
		this.service_act_point = service_act_point;
	}


	public String getService_act_indicators() {
		return service_act_indicators;
	}


	public void setService_act_indicators(String service_act_indicators) {
		this.service_act_indicators = service_act_indicators;
	}


	public String getService_progress_indicators() {
		return service_progress_indicators;
	}


	public void setService_progress_indicators(String service_progress_indicators) {
		this.service_progress_indicators = service_progress_indicators;
	}


	public String getService_status() {
		return service_status;
	}


	public void setService_status(String service_status) {
		this.service_status = service_status;
	}


	public String getService_revised_timeline() {
		return service_revised_timeline;
	}


	public void setService_revised_timeline(String service_revised_timeline) {
		this.service_revised_timeline = service_revised_timeline;
	}


	public String getService_change_in_responsibility() {
		return service_change_in_responsibility;
	}


	public void setService_change_in_responsibility(String service_change_in_responsibility) {
		this.service_change_in_responsibility = service_change_in_responsibility;
	}


	public String getWorkforce_act_point() {
		return workforce_act_point;
	}


	public void setWorkforce_act_point(String workforce_act_point) {
		this.workforce_act_point = workforce_act_point;
	}


	public String getWorkforce_act_indicators() {
		return workforce_act_indicators;
	}


	public void setWorkforce_act_indicators(String workforce_act_indicators) {
		this.workforce_act_indicators = workforce_act_indicators;
	}


	public String getWorkforce_progress_indicators() {
		return workforce_progress_indicators;
	}


	public void setWorkforce_progress_indicators(String workforce_progress_indicators) {
		this.workforce_progress_indicators = workforce_progress_indicators;
	}


	public String getWorkforce_status() {
		return workforce_status;
	}


	public void setWorkforce_status(String workforce_status) {
		this.workforce_status = workforce_status;
	}


	public String getWorkforce_revised_timeline() {
		return workforce_revised_timeline;
	}


	public void setWorkforce_revised_timeline(String workforce_revised_timeline) {
		this.workforce_revised_timeline = workforce_revised_timeline;
	}


	public String getWorkforce_change_in_responsibility() {
		return workforce_change_in_responsibility;
	}


	public void setWorkforce_change_in_responsibility(String workforce_change_in_responsibility) {
		this.workforce_change_in_responsibility = workforce_change_in_responsibility;
	}


	public String getSupplies_act_point() {
		return supplies_act_point;
	}


	public void setSupplies_act_point(String supplies_act_point) {
		this.supplies_act_point = supplies_act_point;
	}


	public String getSupplies_act_indicators() {
		return supplies_act_indicators;
	}


	public void setSupplies_act_indicators(String supplies_act_indicators) {
		this.supplies_act_indicators = supplies_act_indicators;
	}


	public String getSupplies_act_progress_indicators() {
		return supplies_act_progress_indicators;
	}


	public void setSupplies_act_progress_indicators(String supplies_act_progress_indicators) {
		this.supplies_act_progress_indicators = supplies_act_progress_indicators;
	}


	public String getSupplies_status() {
		return supplies_status;
	}


	public void setSupplies_status(String supplies_status) {
		this.supplies_status = supplies_status;
	}


	public String getSupplies_revised_timeline() {
		return supplies_revised_timeline;
	}


	public void setSupplies_revised_timeline(String supplies_revised_timeline) {
		this.supplies_revised_timeline = supplies_revised_timeline;
	}


	public String getSupplies_change_in_responsibility() {
		return supplies_change_in_responsibility;
	}


	public void setSupplies_change_in_responsibility(String supplies_change_in_responsibility) {
		this.supplies_change_in_responsibility = supplies_change_in_responsibility;
	}


	public String getHealth_act_point() {
		return health_act_point;
	}


	public void setHealth_act_point(String health_act_point) {
		this.health_act_point = health_act_point;
	}


	public String getHealth_act_indicators() {
		return health_act_indicators;
	}


	public void setHealth_act_indicators(String health_act_indicators) {
		this.health_act_indicators = health_act_indicators;
	}


	public String getHealth_progress_indicators() {
		return health_progress_indicators;
	}


	public void setHealth_progress_indicators(String health_progress_indicators) {
		this.health_progress_indicators = health_progress_indicators;
	}


	public String getHealth_status() {
		return health_status;
	}


	public void setHealth_status(String health_status) {
		this.health_status = health_status;
	}


	public String getHealth_revised_timeline() {
		return health_revised_timeline;
	}


	public void setHealth_revised_timeline(String health_revised_timeline) {
		this.health_revised_timeline = health_revised_timeline;
	}


	public String getHealth_change_in_responsibility() {
		return health_change_in_responsibility;
	}


	public void setHealth_change_in_responsibility(String health_change_in_responsibility) {
		this.health_change_in_responsibility = health_change_in_responsibility;
	}


	public String getFinance_act_point() {
		return finance_act_point;
	}


	public void setFinance_act_point(String finance_act_point) {
		this.finance_act_point = finance_act_point;
	}


	public String getFinance_act_indicators() {
		return finance_act_indicators;
	}


	public void setFinance_act_indicators(String finance_act_indicators) {
		this.finance_act_indicators = finance_act_indicators;
	}


	public String getFinance_progress_indicators() {
		return finance_progress_indicators;
	}


	public void setFinance_progress_indicators(String finance_progress_indicators) {
		this.finance_progress_indicators = finance_progress_indicators;
	}


	public String getFinance_status() {
		return finance_status;
	}


	public void setFinance_status(String finance_status) {
		this.finance_status = finance_status;
	}


	public String getFinance_revised_timeline() {
		return finance_revised_timeline;
	}


	public void setFinance_revised_timeline(String finance_revised_timeline) {
		this.finance_revised_timeline = finance_revised_timeline;
	}


	public String getFinance_change_in_responsibility() {
		return finance_change_in_responsibility;
	}


	public void setFinance_change_in_responsibility(String finance_change_in_responsibility) {
		this.finance_change_in_responsibility = finance_change_in_responsibility;
	}


	public String getPolicy_act_point() {
		return policy_act_point;
	}


	public void setPolicy_act_point(String policy_act_point) {
		this.policy_act_point = policy_act_point;
	}


	public String getPolicy_act_indicators() {
		return policy_act_indicators;
	}


	public void setPolicy_act_indicators(String policy_act_indicators) {
		this.policy_act_indicators = policy_act_indicators;
	}


	public String getPolicy_progress_indicators() {
		return policy_progress_indicators;
	}


	public void setPolicy_progress_indicators(String policy_progress_indicators) {
		this.policy_progress_indicators = policy_progress_indicators;
	}


	public String getPolicy_status() {
		return policy_status;
	}


	public void setPolicy_status(String policy_status) {
		this.policy_status = policy_status;
	}


	public String getPolicy_revised_timeline() {
		return policy_revised_timeline;
	}


	public void setPolicy_revised_timeline(String policy_revised_timeline) {
		this.policy_revised_timeline = policy_revised_timeline;
	}


	public String getPolicy_change_in_responsibility() {
		return policy_change_in_responsibility;
	}


	public void setPolicy_change_in_responsibility(String policy_change_in_responsibility) {
		this.policy_change_in_responsibility = policy_change_in_responsibility;
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


	public String getSource() {
		return source;
	}


	public void setSource(String source) {
		this.source = source;
	}


	@Override
	public String toString() {
		return "Form5Followup [date_of_meeting=" + date_of_meeting + ", venue_of_meeting=" + venue_of_meeting
				+ ", chairperson_of_meeting=" + chairperson_of_meeting + ", chairperson_of_meeting_others="
				+ chairperson_of_meeting_others + ", theme_id=" + theme_id + ", theme_leader=" + theme_leader
				+ ", no_of_meetings_resp_team=" + no_of_meetings_resp_team + ", meeting_arr=" + meeting_arr
				+ ", service_action_arr=" + service_action_arr + ", workforce_action_arr=" + workforce_action_arr
				+ ", supplies_action_arr=" + supplies_action_arr + ", health_action_arr=" + health_action_arr
				+ ", finance_action_arr=" + finance_action_arr + ", policy_action_arr=" + policy_action_arr
				+ ", meeting_no=" + meeting_no + ", meeting_date=" + meeting_date + ", stakeholder_meeting="
				+ stakeholder_meeting + ", no_of_participants=" + no_of_participants + ", cov_indicators="
				+ cov_indicators + ", ci_source=" + ci_source + ", time_zero=" + time_zero + ", time_one=" + time_one
				+ ", time_three=" + time_three + ", time_four=" + time_four + ", timezero_date=" + timezero_date
				+ ", timeone_date=" + timeone_date + ", timetwo_date=" + timetwo_date + ", timethree_date="
				+ timethree_date + ", total_coverage_indi=" + total_coverage_indi
				+ ", total_coverage_indi_timezero_date=" + total_coverage_indi_timezero_date
				+ ", total_coverage_indi_timeone_date=" + total_coverage_indi_timeone_date
				+ ", total_coverage_indi_timetwo_date=" + total_coverage_indi_timetwo_date
				+ ", total_coverage_indi_timethree_date=" + total_coverage_indi_timethree_date + ", service_act_point="
				+ service_act_point + ", service_act_indicators=" + service_act_indicators
				+ ", service_progress_indicators=" + service_progress_indicators + ", service_status=" + service_status
				+ ", service_revised_timeline=" + service_revised_timeline + ", service_change_in_responsibility="
				+ service_change_in_responsibility + ", workforce_act_point=" + workforce_act_point
				+ ", workforce_act_indicators=" + workforce_act_indicators + ", workforce_progress_indicators="
				+ workforce_progress_indicators + ", workforce_status=" + workforce_status
				+ ", workforce_revised_timeline=" + workforce_revised_timeline + ", workforce_change_in_responsibility="
				+ workforce_change_in_responsibility + ", supplies_act_point=" + supplies_act_point
				+ ", supplies_act_indicators=" + supplies_act_indicators + ", supplies_act_progress_indicators="
				+ supplies_act_progress_indicators + ", supplies_status=" + supplies_status
				+ ", supplies_revised_timeline=" + supplies_revised_timeline + ", supplies_change_in_responsibility="
				+ supplies_change_in_responsibility + ", health_act_point=" + health_act_point
				+ ", health_act_indicators=" + health_act_indicators + ", health_progress_indicators="
				+ health_progress_indicators + ", health_status=" + health_status + ", health_revised_timeline="
				+ health_revised_timeline + ", health_change_in_responsibility=" + health_change_in_responsibility
				+ ", finance_act_point=" + finance_act_point + ", finance_act_indicators=" + finance_act_indicators
				+ ", finance_progress_indicators=" + finance_progress_indicators + ", finance_status=" + finance_status
				+ ", finance_revised_timeline=" + finance_revised_timeline + ", finance_change_in_responsibility="
				+ finance_change_in_responsibility + ", policy_act_point=" + policy_act_point
				+ ", policy_act_indicators=" + policy_act_indicators + ", policy_progress_indicators="
				+ policy_progress_indicators + ", policy_status=" + policy_status + ", policy_revised_timeline="
				+ policy_revised_timeline + ", policy_change_in_responsibility=" + policy_change_in_responsibility
				+ ", district=" + district + ", cycle=" + cycle + ", year=" + year + ", userid=" + userid
				+ ", completed=" + completed + ", source=" + source + "]";
	}
	
	

}