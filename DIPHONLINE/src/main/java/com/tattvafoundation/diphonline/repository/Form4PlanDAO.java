package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.Constants;
import com.tattvafoundation.diphonline.model.AllFormsDataFetchFromAndroidBean;
import com.tattvafoundation.diphonline.model.CategorizedIndicatorBean;
import com.tattvafoundation.diphonline.model.DeleteForm4PlanResponse;
import com.tattvafoundation.diphonline.model.EditIndicatorBean;
import com.tattvafoundation.diphonline.model.EditOptionalIndicatorBean;
import com.tattvafoundation.diphonline.model.Form2EngagestakeIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form3DefineActionIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form4ActionPlanActionPointsBean;
import com.tattvafoundation.diphonline.model.Form4ActionPlanIndicatorsBean;
import com.tattvafoundation.diphonline.model.Form4Plan;
import com.tattvafoundation.diphonline.model.Form4PlanCommonObject;
import com.tattvafoundation.diphonline.model.Form4PlanPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form4PlanSelIndicatorsList;
import com.tattvafoundation.diphonline.model.Form4PlanSendAllDataBean;
import com.tattvafoundation.diphonline.model.IndicatorBean;
import com.tattvafoundation.diphonline.model.Menu_Area_Indicator_Object;
import com.tattvafoundation.diphonline.model.SavedForm4PlanResponse;
import com.tattvafoundation.diphonline.model.UserCredentialsFromAndroidBean;
import com.tattvafoundation.diphonline.model.User_Districts_Privileges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class Form4PlanDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Form4PlanDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DeleteForm4PlanResponse deleteForm4PlanDetails(String district_id, String cycle_id, String year,
			String user_id) {

		DeleteForm4PlanResponse responseobj = new DeleteForm4PlanResponse();

		
		Object[] params1 = { district_id, cycle_id, year };
		int rows = jdbcTemplate.update(
				"DELETE FROM `dev_action_plan_indicators` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params1);
	

		Object[] params2 = { district_id, cycle_id, year };
		int rows2 = jdbcTemplate.update(
				"DELETE FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params2);
		

		Object[] params3 = { district_id, cycle_id, year };
		int rows3 = jdbcTemplate.update(
				"DELETE FROM `dev_action_plan_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params3);
		

		responseobj.setProcessname("deleted");
		if (rows > 1 && rows2 > 1 && rows3 > 1) {
			responseobj.setResponse_val("1");
		} else {
			responseobj.setResponse_val("0");
		}

		return responseobj;

	}

	public Form4PlanSendAllDataBean retrieveForm4Plan_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(
			UserCredentialsFromAndroidBean model, String LoggedinUserId) {

		Form4PlanSendAllDataBean response = new Form4PlanSendAllDataBean();

		
		String sql_user_data = "SELECT (SELECT `user_id` FROM `user_info` where `user_nm`=?) `user_id`,(SELECT `user_nm` FROM `user_info` where `user_nm`=?) `user_nm`,`user_info_id`,"
				+ " CASE " + "  WHEN GROUP_CONCAT(DISTINCT `district_id`) IS NULL THEN '-1' "
				+ "  ELSE GROUP_CONCAT(DISTINCT `district_id`)" + " END  all_districts, " + " CASE "
				+ "    WHEN  GROUP_CONCAT(DISTINCT `cycle_id`) IS NULL THEN '-1' "
				+ "    ELSE  GROUP_CONCAT(DISTINCT `cycle_id`) " + " END  all_cycles, " + " CASE "
				+ "    WHEN  GROUP_CONCAT(DISTINCT `year`) IS NULL THEN '-1' "
				+ "    ELSE  GROUP_CONCAT(DISTINCT `year`) "
				+ " END  all_years FROM  `geographical_combination` where user_info_id=(SELECT `user_id` FROM `user_info` where `user_nm`=?)";

		Object[] params_user_data = new Object[] { LoggedinUserId, LoggedinUserId, LoggedinUserId };

//		User_Districts_Privileges

		User_Districts_Privileges user_priv = jdbcTemplate.query(sql_user_data, params_user_data, rs -> {

			User_Districts_Privileges temp_obj = new User_Districts_Privileges();

			while (rs.next()) {
				temp_obj.setUser_id("" + rs.getString("user_id"));
				temp_obj.setUser_nm("" + rs.getString("user_nm"));
				temp_obj.setAll_districts("" + rs.getString("all_districts"));
				temp_obj.setAll_cycles("" + rs.getString("all_cycles"));
				temp_obj.setAll_years("" + rs.getString("all_years"));
			}
			/* We can also return any variable-data from here but not used currently */
			return temp_obj;
		});

		if ("-1".equals(user_priv.getAll_districts())) {

			
			response.setForm4Plan(new ArrayList<>());
			response.setForm4ActionPlanActionPoints(new ArrayList<>());
			response.setForm4ActionPlanIndicators(new ArrayList<>());

			response.setError_code("200");
			response.setMessage("Sending Form4 Plan Data");
			return response;
		} else {
			System.out.println("Not Returning in half!!! 0 or different value rather than -1");
		}

		List<Form4PlanPrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form4ActionPlanActionPointsBean> list2 = new ArrayList<>();
		List<Form4ActionPlanIndicatorsBean> list3 = new ArrayList<>();

		String sql1 = "";

		if ("0".equals(user_priv.getAll_districts())) {
			sql1 = "SELECT form4plan.`completed`, form4plan.`dev_action_id`,    form4plan.`date_of_meeting`,    form4plan.`venue_of_meeting`,"
					+ "    form4plan.`chariperson_of_meeting`,   form4plan.`chariperson_of_meeting_others`,    form4plan.`theme_id`,"
					+ "    form4plan.`theme_leader`,    form4plan.`district_id`,    form4plan.`cycle_id`,   form4plan. `financial_year`,"
					+ "    form4plan.`user_id`,    form4plan.`record_created` FROM `dev_action_plan_details`  form4plan  "
					+ "    left join  `district` d on form4plan.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019";

		} else {
			sql1 = "SELECT form4plan.`completed`, form4plan.`dev_action_id`,    form4plan.`date_of_meeting`,    form4plan.`venue_of_meeting`,"
					+ "    form4plan.`chariperson_of_meeting`,   form4plan.`chariperson_of_meeting_others`,    form4plan.`theme_id`,"
					+ "    form4plan.`theme_leader`,    form4plan.`district_id`,    form4plan.`cycle_id`,   form4plan. `financial_year`,"
					+ "    form4plan.`user_id`,    form4plan.`record_created` FROM `dev_action_plan_details`  form4plan  "
					+ "    left join  `district` d on form4plan.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019    and d.`district_id` IN ("
					+ user_priv.getAll_districts() + ")  and form4plan.`cycle_id` IN (" + user_priv.getAll_cycles()
					+ ")   and form4plan.`financial_year` IN (" + user_priv.getAll_years() + ");";

		}

		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form4PlanPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form4PlanPrimaryTableDataBean obj = new Form4PlanPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("dev_action_id"));
				obj.setMeetingdate(rs.getString("date_of_meeting"));
				obj.setMeetingvenue(rs.getString("venue_of_meeting"));
				obj.setChairpersonid(rs.getString("chariperson_of_meeting"));
				obj.setOtherchairperson(rs.getString("chariperson_of_meeting_others"));
				obj.setThemeid(rs.getString("theme_id"));
				obj.setThemeleaderofcycle(rs.getString("theme_leader"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setAuto_id(rs.getString("dev_action_id"));
				obj.setCountry_id(model.getCountry_id());
				obj.setProvince_id(model.getProvince_id());
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCompleted(rs.getString("completed"));
				obj.setDatafrom("WEB");

				templist.add(obj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql3 = "";

		if ("0".equals(user_priv.getAll_districts())) {
			sql3 = "SELECT form4plan.`dev_action_point_id`,    form4plan.`dev_action_id`,   form4plan. `action_part`, "
					+ "   form4plan.`action_point_name`,    form4plan.`responsible_dept`,    form4plan.`person_responsible`,  "
					+ "   form4plan.`target`,    form4plan.`district_id`,    form4plan.`cycle_id`,    form4plan.`financial_year`,  "
					+ "   form4plan.`user_id`,    form4plan.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
					+ "   d.`country_id` FROM  `dev_action_plan_action_points`   form4plan  "
					+ "   left join  `district` d on form4plan.district_id=d.district_id   "
					+ "   left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019;";

		} else {
			sql3 = "SELECT form4plan.`dev_action_point_id`,    form4plan.`dev_action_id`,   form4plan. `action_part`, "
					+ "   form4plan.`action_point_name`,    form4plan.`responsible_dept`,    form4plan.`person_responsible`,  "
					+ "   form4plan.`target`,    form4plan.`district_id`,    form4plan.`cycle_id`,    form4plan.`financial_year`,  "
					+ "   form4plan.`user_id`,    form4plan.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
					+ "   d.`country_id` FROM  `dev_action_plan_action_points`   form4plan  "
					+ "   left join  `district` d on form4plan.district_id=d.district_id   "
					+ "   left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019    and d.`district_id` IN ("
					+ user_priv.getAll_districts() + ")  and form4plan.`cycle_id` IN (" + user_priv.getAll_cycles()
					+ ")   and form4plan.`financial_year` IN (" + user_priv.getAll_years() + ");";

		}

		Object[] params3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list2 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form4ActionPlanActionPointsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form4ActionPlanActionPointsBean obj = new Form4ActionPlanActionPointsBean();

				obj.setAuto_id(rs.getString("dev_action_point_id"));
				obj.setForm4_id(rs.getString("dev_action_id"));

				String action_part_web = "SERVICED";

				if ("Service delivery".equals(rs.getString("action_part"))) {
					action_part_web = "SERVICED";
				}

				if ("Finance".equals(rs.getString("action_part"))) {
					action_part_web = "FINANCE";
				}

				if ("Workforce".equals(rs.getString("action_part"))) {
					action_part_web = "WORKFORCE";
				}

				if ("Health information".equals(rs.getString("action_part"))) {
					action_part_web = "HEALTHINFO";
				}

				if ("Policy/governance".equals(rs.getString("action_part"))) {
					action_part_web = "POLICYG";
				}

				if ("Supplies & technology".equals(rs.getString("action_part"))) {
					action_part_web = "SUPPLIES";
				}

				obj.setAction_part(action_part_web);
				obj.setForm3_actionreq_pkey(rs.getString("action_point_name"));

				obj.setResponsible_dept(rs.getString("responsible_dept"));
				obj.setPerson_responsible(rs.getString("person_responsible"));
				obj.setTimeline(rs.getString("target"));
				obj.setDistrict_id(rs.getString("district_id"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				obj.setForm3_actionreq_pkey_real_value(action_real_value);

				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				templist.add(obj);

			}

			return templist;
		});

		String sql3_indi = "";

		if ("0".equals(user_priv.getAll_districts())) {
			sql3_indi = "SELECT form4plan.`dev_indicator_id`,    form4plan.`dev_action_id`,  "
					+ "   form4plan.`action_point_id`,    form4plan.`indicator_name`,    form4plan.`description_of_indicator`, "
					+ "   form4plan.`target_value`,    form4plan.`district_id`,    form4plan.`cycle_id`,   form4plan. `financial_year`,  "
					+ "   form4plan.`user_id`,    form4plan.`record_created`,    form4plan.`indicator_id`,    form4plan.`area_name`,  "
					+ "   form4plan.`area_id`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`  FROM       `dev_action_plan_indicators`   form4plan  "
					+ "   left join  `district` d on form4plan.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019";

		} else {
			sql3_indi = "SELECT form4plan.`dev_indicator_id`,    form4plan.`dev_action_id`,  "
					+ "   form4plan.`action_point_id`,    form4plan.`indicator_name`,    form4plan.`description_of_indicator`, "
					+ "   form4plan.`target_value`,    form4plan.`district_id`,    form4plan.`cycle_id`,   form4plan. `financial_year`,  "
					+ "   form4plan.`user_id`,    form4plan.`record_created`,    form4plan.`indicator_id`,    form4plan.`area_name`,  "
					+ "   form4plan.`area_id`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`  FROM       `dev_action_plan_indicators`   form4plan  "
					+ "   left join  `district` d on form4plan.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019    and d.`district_id` IN ("
					+ user_priv.getAll_districts() + ")  and form4plan.`cycle_id` IN (" + user_priv.getAll_cycles()
					+ ")   and form4plan.`financial_year` IN (" + user_priv.getAll_years() + ");";

		}

		Object[] param3_indi = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		// Inner Query
		list3 = jdbcTemplate.query(sql3_indi, param3_indi, rs -> {

			List<Form4ActionPlanIndicatorsBean> templist = new ArrayList<>();

			while (rs.next()) {
				Form4ActionPlanIndicatorsBean obj = new Form4ActionPlanIndicatorsBean();

				obj.setAuto_id(rs.getString("dev_indicator_id"));
				obj.setForm4_id(rs.getString("dev_action_id"));
				obj.setForm3_actionreq_pkey(rs.getString("action_point_id"));

				obj.setIndicator_name(rs.getString("indicator_name"));
				obj.setIndicator_id(rs.getString("indicator_id"));
				obj.setArea_name(rs.getString("area_name"));
				obj.setArea_id(rs.getString("area_id"));
				obj.setTarget_value(rs.getString("target_value"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));

				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_id") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});
				obj.setForm3_actionreq_pkey_real_value(action_real_value);

				/* Action Responsible id */

				String sql_get_id = "SELECT * FROM dev_action_plan_action_points  where  `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_name`=? and `dev_action_id`=?";
				Object[] params_sql_get_id = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_id"),
						rs.getString("dev_action_id") };

				String str_get_id = jdbcTemplate.query(sql_get_id, params_sql_get_id, rst -> {

					String keyval = "";
					while (rst.next()) {

						keyval = rst.getString("dev_action_point_id");

					}

					return keyval;
				});

				/* End of Action Responsible id */

				obj.setAction_responsible_id(str_get_id);

				templist.add(obj);

			}

			return templist;
		});
		// End of Inner Query

		response.setForm4Plan(list1);
		response.setForm4ActionPlanActionPoints(list2);
		response.setForm4ActionPlanIndicators(list3);

		response.setError_code("200");
		response.setMessage("Sending Form4 Plan Data");

		return response;
	}

	public Form4PlanSendAllDataBean retrieveForm4Plan_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(
			UserCredentialsFromAndroidBean model) {

		Form4PlanSendAllDataBean response = new Form4PlanSendAllDataBean();

		List<Form4PlanPrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form4ActionPlanActionPointsBean> list2 = new ArrayList<>();
		List<Form4ActionPlanIndicatorsBean> list3 = new ArrayList<>();

		String sql1 = "SELECT form4plan.`completed`, form4plan.`dev_action_id`,    form4plan.`date_of_meeting`,    form4plan.`venue_of_meeting`,"
				+ "    form4plan.`chariperson_of_meeting`,   form4plan.`chariperson_of_meeting_others`,    form4plan.`theme_id`,"
				+ "    form4plan.`theme_leader`,    form4plan.`district_id`,    form4plan.`cycle_id`,   form4plan. `financial_year`,"
				+ "    form4plan.`user_id`,    form4plan.`record_created` FROM `dev_action_plan_details`  form4plan  "
				+ "    left join  `district` d on form4plan.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019";

		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form4PlanPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form4PlanPrimaryTableDataBean obj = new Form4PlanPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("dev_action_id"));
				obj.setMeetingdate(rs.getString("date_of_meeting"));
				obj.setMeetingvenue(rs.getString("venue_of_meeting"));
				obj.setChairpersonid(rs.getString("chariperson_of_meeting"));
				obj.setOtherchairperson(rs.getString("chariperson_of_meeting_others"));
				obj.setThemeid(rs.getString("theme_id"));
				obj.setThemeleaderofcycle(rs.getString("theme_leader"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setAuto_id(rs.getString("dev_action_id"));
				obj.setCountry_id(model.getCountry_id());
				obj.setProvince_id(model.getProvince_id());
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCompleted(rs.getString("completed"));
				obj.setDatafrom("WEB");

				templist.add(obj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql3 = "SELECT form4plan.`dev_action_point_id`,    form4plan.`dev_action_id`,   form4plan. `action_part`, "
				+ "   form4plan.`action_point_name`,    form4plan.`responsible_dept`,    form4plan.`person_responsible`,  "
				+ "   form4plan.`target`,    form4plan.`district_id`,    form4plan.`cycle_id`,    form4plan.`financial_year`,  "
				+ "   form4plan.`user_id`,    form4plan.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
				+ "   d.`country_id` FROM  `dev_action_plan_action_points`   form4plan  "
				+ "   left join  `district` d on form4plan.district_id=d.district_id   "
				+ "   left join  `country_states` cs on d.state_id=cs.cs_id    "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019;";

		Object[] params3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list2 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form4ActionPlanActionPointsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form4ActionPlanActionPointsBean obj = new Form4ActionPlanActionPointsBean();

				obj.setAuto_id(rs.getString("dev_action_point_id"));
				obj.setForm4_id(rs.getString("dev_action_id"));

				String action_part_web = "SERVICED";

				if ("Service delivery".equals(rs.getString("action_part"))) {
					action_part_web = "SERVICED";
				}

				if ("Finance".equals(rs.getString("action_part"))) {
					action_part_web = "FINANCE";
				}

				if ("Workforce".equals(rs.getString("action_part"))) {
					action_part_web = "WORKFORCE";
				}

				if ("Health information".equals(rs.getString("action_part"))) {
					action_part_web = "HEALTHINFO";
				}

				if ("Policy/governance".equals(rs.getString("action_part"))) {
					action_part_web = "POLICYG";
				}

				if ("Supplies & technology".equals(rs.getString("action_part"))) {
					action_part_web = "SUPPLIES";
				}

				obj.setAction_part(action_part_web);
				obj.setForm3_actionreq_pkey(rs.getString("action_point_name"));

				obj.setResponsible_dept(rs.getString("responsible_dept"));
				obj.setPerson_responsible(rs.getString("person_responsible"));
				obj.setTimeline(rs.getString("target"));
				obj.setDistrict_id(rs.getString("district_id"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				obj.setForm3_actionreq_pkey_real_value(action_real_value);

				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				templist.add(obj);

			}

			return templist;
		});

		String sql3_indi = "SELECT form4plan.`dev_indicator_id`,    form4plan.`dev_action_id`,  "
				+ "   form4plan.`action_point_id`,    form4plan.`indicator_name`,    form4plan.`description_of_indicator`, "
				+ "   form4plan.`target_value`,    form4plan.`district_id`,    form4plan.`cycle_id`,   form4plan. `financial_year`,  "
				+ "   form4plan.`user_id`,    form4plan.`record_created`,    form4plan.`indicator_id`,    form4plan.`area_name`,  "
				+ "   form4plan.`area_id`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`  FROM       `dev_action_plan_indicators`   form4plan  "
				+ "   left join  `district` d on form4plan.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form4plan.`financial_year` >= 2019";

		Object[] param3_indi = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		// Inner Query
		list3 = jdbcTemplate.query(sql3_indi, param3_indi, rs -> {

			List<Form4ActionPlanIndicatorsBean> templist = new ArrayList<>();

			while (rs.next()) {
				Form4ActionPlanIndicatorsBean obj = new Form4ActionPlanIndicatorsBean();

				obj.setAuto_id(rs.getString("dev_indicator_id"));
				obj.setForm4_id(rs.getString("dev_action_id"));
				obj.setForm3_actionreq_pkey(rs.getString("action_point_id"));

				obj.setIndicator_name(rs.getString("indicator_name"));
				obj.setIndicator_id(rs.getString("indicator_id"));
				obj.setArea_name(rs.getString("area_name"));
				obj.setArea_id(rs.getString("area_id"));
				obj.setTarget_value(rs.getString("target_value"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));

				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_id") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});
				obj.setForm3_actionreq_pkey_real_value(action_real_value);

				/* Action Responsible id */

				String sql_get_id = "SELECT * FROM dev_action_plan_action_points  where  `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_name`=? and `dev_action_id`=?";
				Object[] params_sql_get_id = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_id"),
						rs.getString("dev_action_id") };

				String str_get_id = jdbcTemplate.query(sql_get_id, params_sql_get_id, rst -> {

					String keyval = "";
					while (rst.next()) {

						keyval = rst.getString("dev_action_point_id");

					}

					return keyval;
				});

				/* End of Action Responsible id */

				obj.setAction_responsible_id(str_get_id);

				templist.add(obj);

			}

			return templist;
		});
		// End of Inner Query

		response.setForm4Plan(list1);
		response.setForm4ActionPlanActionPoints(list2);
		response.setForm4ActionPlanIndicators(list3);

		response.setError_code("200");
		response.setMessage("Sending Form4 Plan Data");

		return response;
	}

	public Form4Plan retrieveForm4PlanDetails(String district_id, String cycle_id, String year, String user_id) {

		Form4Plan obj = new Form4Plan();

//		String sql1 = "INSERT INTO `dev_action_plan_details`(`date_of_meeting`, `venue_of_meeting`, "
//				+ "`chariperson_of_meeting`, `theme_id`, `theme_leader`, `district_id`, `cycle_id`,"
//				+ " `financial_year`, `user_id`, `record_created`) VALUES (?,?," + "?,?,?,?,?,?," + "?,?)";

		String sql1 = "SELECt * FROM `dev_action_plan_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params1 = new Object[] { district_id, cycle_id, year };

		String p_key = jdbcTemplate.query(sql1, params1, rs -> {

			String primary_key = "";
			while (rs.next()) {

				obj.setDate_of_meeting(rs.getString("date_of_meeting"));
				obj.setVenue_of_meeting(rs.getString("venue_of_meeting"));
				obj.setChariperson_of_meeting(rs.getString("chariperson_of_meeting"));
				obj.setChariperson_of_meeting_others(rs.getString("chariperson_of_meeting_others"));
				obj.setTheme_id(rs.getString("theme_id"));
				obj.setTheme_leader(rs.getString("theme_leader"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUserid(rs.getString("user_id"));
				obj.setCompleted(rs.getString("completed"));
				primary_key = rs.getString("dev_action_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return primary_key;
		});

		String sql2 = "SELECT DISTINCT(theme_name) FROM `hsca_requirements_iphs` where district_id=? and cycle_id=? and financial_year=? and `requirements_id`=?";
		Object[] params2 = new Object[] { district_id, cycle_id, year, obj.getTheme_id() };

		String theme_name = jdbcTemplate.query(sql2, params2, rs -> {

			String str = "";
			while (rs.next()) {

				str = rs.getString("theme_name");
			}
			/* We can also return any variable-data from here but not used currently */
			return str;
		});

		obj.setTheme_id(theme_name);


		String sql3 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
		Object[] params3 = new Object[] { district_id, cycle_id, year, p_key, "Service delivery" };

		List<Form4PlanCommonObject> list1_service_arr = new ArrayList<>();

		jdbcTemplate.query(sql3, params3, rs -> {

			while (rs.next()) {
				Form4PlanCommonObject tempobj = new Form4PlanCommonObject();
				// very crucial variable for recognising tyoe of data
				tempobj.setDev_action_point_id(rs.getString("dev_action_point_id"));
				tempobj.setAction_req_id(rs.getString("action_point_name"));
				tempobj.setDocument_action_required("");
				tempobj.setSel_stakeholder(rs.getString("responsible_dept"));
				tempobj.setPerson_responsible(rs.getString("person_responsible"));
				tempobj.setTimeline(rs.getString("target"));
				tempobj.setTarget("");
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey("");

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { district_id, cycle_id, year,
						rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				tempobj.setDocument_action_required("" + action_real_value);

				/*
				 * 
				 * 
				 * sql2 =
				 * "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?"
				 * ; param2 = new Object[] { district_id, cycle_id, year, "Service delivery",
				 * list1_service_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey
				 * () };
				 * 
				 * int i = pos; List<String> act = jdbcTemplate.query(sql2, param2, rs -> {
				 * 
				 * List<String> list = new ArrayList<>(); while (rs.next()) { //
				 * list1_service_arr.get(i).setDocument_action_required(rs.getString(
				 * "action_required"));
				 * obj.setService_action_required(rs.getString("action_required"));
				 * list.add(rs.getString("action_required")); }
				 * 
				 * return list; });
				 * 
				 * 
				 * 
				 */

				String sql3_indi = "SELECT form.indicator_name,form.indicator_id,form.area_id,form.area_name,form.target_value,form.dev_indicator_id ,indi.definition FROM dev_action_plan_indicators form inner join indicators indi on  form.indicator_id=indi.id  where form.`district_id`=? and form.`cycle_id`=? and form.`financial_year`=?  and form.action_point_id=?   and form.dev_action_id=?";
				Object[] param3_indi = new Object[] { district_id, cycle_id, year, rs.getString("action_point_name"),
						p_key };

				// Inner Query
				List<Form4PlanSelIndicatorsList> get_indi = jdbcTemplate.query(sql3_indi, param3_indi, rs2 -> {

					List<Form4PlanSelIndicatorsList> sel_indi_list = new ArrayList<>();
					while (rs2.next()) {
						Form4PlanSelIndicatorsList indiobj = new Form4PlanSelIndicatorsList();
						indiobj.setIndicator_val("" + rs2.getString("indicator_name"));
						indiobj.setIndicator_id("" + rs2.getString("indicator_id"));
						indiobj.setArea_id("" + rs2.getString("area_id"));
						indiobj.setArea_name("" + rs2.getString("area_name"));
						indiobj.setTarget("" + rs2.getString("target_value"));
						indiobj.setDev_indicator_id("" + rs2.getString("dev_indicator_id"));
						indiobj.setIndicator_desc("" + rs2.getString("definition"));
						sel_indi_list.add(indiobj);
					}

					return sel_indi_list;
				});
				// End of Inner Query

				tempobj.setSel_indicators(get_indi);

				list1_service_arr.add(tempobj);

			}

			return "";
		});

		obj.setService_action_arr(list1_service_arr);

//		String sql77 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//				+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//				+ "		?,?,?,?,?,?,?,?)";

//		String sql4 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
//		Object[] params4 = new Object[] { district_id, cycle_id, year, p_key, "Workforce" };
//
//		String action_point_id_workforce = jdbcTemplate.query(sql4, params4, rs -> {
//
//			String action_point_id2 = "";
//			while (rs.next()) {
//				action_point_id2 = rs.getString("action_point_name");
//				obj.setWorkforce_action_part(rs.getString("action_part"));
//				obj.setWorkforce_action_point_name(rs.getString("action_point_name"));
//				obj.setWorkforce_responsible_dept(rs.getString("responsible_dept"));
//				obj.setWorkforce_person_responsible(rs.getString("person_responsible"));
//				obj.setWorkforce_target_value(rs.getString("target"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return action_point_id2;
//		});
//
//		String sql44 = "SELECT * FROM dev_action_plan_indicators  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and action_point_id=?   and dev_action_id=?";
//		Object[] params44 = new Object[] { district_id, cycle_id, year, action_point_id_workforce, p_key };
//
//		jdbcTemplate.query(sql44, params44, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setWorkforce_indicator_name(rs.getString("indicator_name"));
//				obj.setWorkforce_description_of_indicator(rs.getString("description_of_indicator"));
//				obj.setWorkforce_target_value(rs.getString("target_value"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return str;
//		});

		String sql4 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
		Object[] param4 = new Object[] { district_id, cycle_id, year, p_key, "Workforce" };

		List<Form4PlanCommonObject> list2_workforce_arr = new ArrayList<>();

		jdbcTemplate.query(sql4, param4, rs -> {

			while (rs.next()) {
				Form4PlanCommonObject tempobj = new Form4PlanCommonObject();
				// very crucial variable for recognising tyoe of data
				tempobj.setDev_action_point_id(rs.getString("dev_action_point_id"));
				tempobj.setAction_req_id(rs.getString("action_point_name"));
				tempobj.setDocument_action_required("");
				tempobj.setSel_stakeholder(rs.getString("responsible_dept"));
				tempobj.setPerson_responsible(rs.getString("person_responsible"));
				tempobj.setTimeline(rs.getString("target"));
				tempobj.setTarget("");
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey("");

				tempobj.setDocument_action_required("" + rs.getString("action_point_name"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { district_id, cycle_id, year,
						rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				tempobj.setDocument_action_required("" + action_real_value);

				String sql4_indi = "SELECT form.indicator_name,form.indicator_id,form.area_id,form.area_name,form.target_value,form.dev_indicator_id ,indi.definition FROM dev_action_plan_indicators form inner join indicators indi on  form.indicator_id=indi.id  where form.`district_id`=? and form.`cycle_id`=? and form.`financial_year`=?  and form.action_point_id=?   and form.dev_action_id=?";
				Object[] param4_indi = new Object[] { district_id, cycle_id, year, rs.getString("action_point_name"),
						p_key };

				// Inner Query
				List<Form4PlanSelIndicatorsList> get_indi = jdbcTemplate.query(sql4_indi, param4_indi, rs2 -> {

					List<Form4PlanSelIndicatorsList> sel_indi_list = new ArrayList<>();
					while (rs2.next()) {
						Form4PlanSelIndicatorsList indiobj = new Form4PlanSelIndicatorsList();
						indiobj.setIndicator_val("" + rs2.getString("indicator_name"));
						indiobj.setIndicator_id("" + rs2.getString("indicator_id"));
						indiobj.setArea_id("" + rs2.getString("area_id"));
						indiobj.setArea_name("" + rs2.getString("area_name"));
						indiobj.setTarget("" + rs2.getString("target_value"));
						indiobj.setDev_indicator_id("" + rs2.getString("dev_indicator_id"));
						indiobj.setIndicator_desc("" + rs2.getString("definition"));
						;
						sel_indi_list.add(indiobj);
					}

					return sel_indi_list;
				});
				// End of Inner Query

				tempobj.setSel_indicators(get_indi);

				list2_workforce_arr.add(tempobj);

			}

			return "";
		});

		obj.setWorkforce_action_arr(list2_workforce_arr);

//		String sql5 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
//		Object[] params5 = new Object[] { district_id, cycle_id, year, p_key, "Supplies & technology" };
//
//		String action_point_id_supplies = jdbcTemplate.query(sql5, params5, rs -> {
//
//			String action_point_id2 = "";
//			while (rs.next()) {
//				action_point_id2 = rs.getString("action_point_name");
//				obj.setSupplies_action_part(rs.getString("action_part"));
//				obj.setSupplies_action_point_name(rs.getString("action_point_name"));
//				obj.setSupplies_responsible_dept(rs.getString("responsible_dept"));
//				obj.setSupplies_person_responsible(rs.getString("person_responsible"));
//				obj.setSupplies_target_value(rs.getString("target"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return action_point_id2;
//		});
//
//		String sql55 = "SELECT * FROM dev_action_plan_indicators  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and action_point_id=?   and dev_action_id=?";
//		Object[] params55 = new Object[] { district_id, cycle_id, year, action_point_id_supplies, p_key };
//
//		jdbcTemplate.query(sql55, params55, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setSupplies_indicator_name(rs.getString("indicator_name"));
//				obj.setSupplies_description_of_indicator(rs.getString("description_of_indicator"));
//				obj.setSupplies_target_value(rs.getString("target_value"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return str;
//		});

		String sql5 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
		Object[] param5 = new Object[] { district_id, cycle_id, year, p_key, "Supplies & technology" };

		List<Form4PlanCommonObject> list3_supplies_arr = new ArrayList<>();

		jdbcTemplate.query(sql5, param5, rs -> {

			while (rs.next()) {
				Form4PlanCommonObject tempobj = new Form4PlanCommonObject();
				// very crucial variable for recognising tyoe of data
				tempobj.setDev_action_point_id(rs.getString("dev_action_point_id"));
				tempobj.setAction_req_id(rs.getString("action_point_name"));
				tempobj.setDocument_action_required("");
				tempobj.setSel_stakeholder(rs.getString("responsible_dept"));
				tempobj.setPerson_responsible(rs.getString("person_responsible"));
				tempobj.setTimeline(rs.getString("target"));
				tempobj.setTarget("");
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey("");
				tempobj.setDocument_action_required("" + rs.getString("action_point_name"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { district_id, cycle_id, year,
						rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				tempobj.setDocument_action_required("" + action_real_value);

				String sql5_indi = "SELECT form.indicator_name,form.indicator_id,form.area_id,form.area_name,form.target_value,form.dev_indicator_id ,indi.definition FROM dev_action_plan_indicators form inner join indicators indi on  form.indicator_id=indi.id  where form.`district_id`=? and form.`cycle_id`=? and form.`financial_year`=?  and form.action_point_id=?   and form.dev_action_id=?";
				Object[] param5_indi = new Object[] { district_id, cycle_id, year, rs.getString("action_point_name"),
						p_key };

				// Inner Query
				List<Form4PlanSelIndicatorsList> get_indi = jdbcTemplate.query(sql5_indi, param5_indi, rs2 -> {

					List<Form4PlanSelIndicatorsList> sel_indi_list = new ArrayList<>();
					while (rs2.next()) {
						Form4PlanSelIndicatorsList indiobj = new Form4PlanSelIndicatorsList();
						indiobj.setIndicator_val("" + rs2.getString("indicator_name"));
						indiobj.setIndicator_id("" + rs2.getString("indicator_id"));
						indiobj.setArea_id("" + rs2.getString("area_id"));
						indiobj.setArea_name("" + rs2.getString("area_name"));
						indiobj.setTarget("" + rs2.getString("target_value"));
						indiobj.setDev_indicator_id("" + rs2.getString("dev_indicator_id"));
						indiobj.setIndicator_desc("" + rs2.getString("definition"));
						;
						sel_indi_list.add(indiobj);
					}

					return sel_indi_list;
				});
				// End of Inner Query

				tempobj.setSel_indicators(get_indi);

				list3_supplies_arr.add(tempobj);

			}

			return "";
		});

		obj.setSupplies_action_arr(list3_supplies_arr);

//		String sql6 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
//		Object[] param6 = new Object[] { district_id, cycle_id, year, p_key, "Health information" };
//
//		String action_point_id_health = jdbcTemplate.query(sql6, param6, rs -> {
//
//			String action_point_id2 = "";
//			while (rs.next()) {
//				action_point_id2 = rs.getString("action_point_name");
//				obj.setHealth_action_part(rs.getString("action_part"));
//				obj.setHealth_action_point_name(rs.getString("action_point_name"));
//				obj.setHealth_responsible_dept(rs.getString("responsible_dept"));
//				obj.setHealth_person_responsible(rs.getString("person_responsible"));
//				obj.setHealth_target_value(rs.getString("target"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return action_point_id2;
//		});
//
//		String sql66 = "SELECT * FROM dev_action_plan_indicators  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and action_point_id=?   and dev_action_id=?";
//		Object[] params66 = new Object[] { district_id, cycle_id, year, action_point_id_health, p_key };
//
//		jdbcTemplate.query(sql66, params66, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setHealth_indicator_name(rs.getString("indicator_name"));
//				obj.setHealth_description_of_indicator(rs.getString("description_of_indicator"));
//				obj.setHealth_target_value(rs.getString("target_value"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return str;
//		});

		String sql6 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
		Object[] param6 = new Object[] { district_id, cycle_id, year, p_key, "Health information" };

		List<Form4PlanCommonObject> list4_health_arr = new ArrayList<>();

		jdbcTemplate.query(sql6, param6, rs -> {

			while (rs.next()) {
				Form4PlanCommonObject tempobj = new Form4PlanCommonObject();
				// very crucial variable for recognising tyoe of data
				tempobj.setDev_action_point_id(rs.getString("dev_action_point_id"));
				tempobj.setAction_req_id(rs.getString("action_point_name"));
				tempobj.setDocument_action_required("");
				tempobj.setSel_stakeholder(rs.getString("responsible_dept"));
				tempobj.setPerson_responsible(rs.getString("person_responsible"));
				tempobj.setTimeline(rs.getString("target"));
				tempobj.setTarget("");
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey("");
				tempobj.setDocument_action_required("" + rs.getString("action_point_name"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { district_id, cycle_id, year,
						rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				tempobj.setDocument_action_required("" + action_real_value);

				String sql6_indi = "SELECT form.indicator_name,form.indicator_id,form.area_id,form.area_name,form.target_value,form.dev_indicator_id ,indi.definition FROM dev_action_plan_indicators form inner join indicators indi on  form.indicator_id=indi.id  where form.`district_id`=? and form.`cycle_id`=? and form.`financial_year`=?  and form.action_point_id=?   and form.dev_action_id=?";
				Object[] param6_indi = new Object[] { district_id, cycle_id, year, rs.getString("action_point_name"),
						p_key };

				// Inner Query
				List<Form4PlanSelIndicatorsList> get_indi = jdbcTemplate.query(sql6_indi, param6_indi, rs2 -> {

					List<Form4PlanSelIndicatorsList> sel_indi_list = new ArrayList<>();
					while (rs2.next()) {
						Form4PlanSelIndicatorsList indiobj = new Form4PlanSelIndicatorsList();
						indiobj.setIndicator_val("" + rs2.getString("indicator_name"));
						indiobj.setIndicator_id("" + rs2.getString("indicator_id"));
						indiobj.setArea_id("" + rs2.getString("area_id"));
						indiobj.setArea_name("" + rs2.getString("area_name"));
						indiobj.setTarget("" + rs2.getString("target_value"));
						indiobj.setDev_indicator_id("" + rs2.getString("dev_indicator_id"));
						indiobj.setIndicator_desc("" + rs2.getString("definition"));
						;
						sel_indi_list.add(indiobj);
					}

					return sel_indi_list;
				});
				// End of Inner Query

				tempobj.setSel_indicators(get_indi);

				list4_health_arr.add(tempobj);

			}

			return "";
		});

		obj.setHealth_action_arr(list4_health_arr);

//		String sql7 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
//		Object[] param7 = new Object[] { district_id, cycle_id, year, p_key, "Finance" };
//
//		String action_point_id_finance = jdbcTemplate.query(sql7, param7, rs -> {
//
//			String action_point_id2 = "";
//			while (rs.next()) {
//				action_point_id2 = rs.getString("action_point_name");
//				obj.setFinance_action_part(rs.getString("action_part"));
//				obj.setFinance_action_point_name(rs.getString("action_point_name"));
//				obj.setFinance_responsible_dept(rs.getString("responsible_dept"));
//				obj.setFinance_person_responsible(rs.getString("person_responsible"));
//				obj.setFinance_target_value(rs.getString("target"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return action_point_id2;
//		});
//
//		String sql77 = "SELECT * FROM dev_action_plan_indicators  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and action_point_id=?   and dev_action_id=?";
//		Object[] params77 = new Object[] { district_id, cycle_id, year, action_point_id_finance, p_key };
//
//		jdbcTemplate.query(sql77, params77, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setFinance_indicator_name(rs.getString("indicator_name"));
//				obj.setFinance_description_of_indicator(rs.getString("description_of_indicator"));
//				obj.setFinance_target_value(rs.getString("target_value"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return str;
//		});

		String sql7 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
		Object[] param7 = new Object[] { district_id, cycle_id, year, p_key, "Finance" };

		List<Form4PlanCommonObject> list5_finance_arr = new ArrayList<>();

		jdbcTemplate.query(sql7, param7, rs -> {

			while (rs.next()) {
				Form4PlanCommonObject tempobj = new Form4PlanCommonObject();
				// very crucial variable for recognising tyoe of data
				tempobj.setDev_action_point_id(rs.getString("dev_action_point_id"));
				tempobj.setAction_req_id(rs.getString("action_point_name"));
				tempobj.setDocument_action_required("");
				tempobj.setSel_stakeholder(rs.getString("responsible_dept"));
				tempobj.setPerson_responsible(rs.getString("person_responsible"));
				tempobj.setTimeline(rs.getString("target"));
				tempobj.setTarget("");
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey("");
				tempobj.setDocument_action_required("" + rs.getString("action_point_name"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { district_id, cycle_id, year,
						rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				tempobj.setDocument_action_required("" + action_real_value);

				String sql7_indi = "SELECT form.indicator_name,form.indicator_id,form.area_id,form.area_name,form.target_value,form.dev_indicator_id ,indi.definition FROM dev_action_plan_indicators form inner join indicators indi on  form.indicator_id=indi.id  where form.`district_id`=? and form.`cycle_id`=? and form.`financial_year`=?  and form.action_point_id=?   and form.dev_action_id=?";
				Object[] param7_indi = new Object[] { district_id, cycle_id, year, rs.getString("action_point_name"),
						p_key };

				// Inner Query
				List<Form4PlanSelIndicatorsList> get_indi = jdbcTemplate.query(sql7_indi, param7_indi, rs2 -> {

					List<Form4PlanSelIndicatorsList> sel_indi_list = new ArrayList<>();
					while (rs2.next()) {
						Form4PlanSelIndicatorsList indiobj = new Form4PlanSelIndicatorsList();
						indiobj.setIndicator_val("" + rs2.getString("indicator_name"));
						indiobj.setIndicator_id("" + rs2.getString("indicator_id"));
						indiobj.setArea_id("" + rs2.getString("area_id"));
						indiobj.setArea_name("" + rs2.getString("area_name"));
						indiobj.setTarget("" + rs2.getString("target_value"));
						indiobj.setDev_indicator_id("" + rs2.getString("dev_indicator_id"));
						indiobj.setIndicator_desc("" + rs2.getString("definition"));
						sel_indi_list.add(indiobj);
					}

					return sel_indi_list;
				});
				// End of Inner Query

				tempobj.setSel_indicators(get_indi);

				list5_finance_arr.add(tempobj);

			}

			return "";
		});

		obj.setFinance_action_arr(list5_finance_arr);

//		String sql8 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
//		Object[] param8 = new Object[] { district_id, cycle_id, year, p_key, "Policy/governance" };
//
//		String action_point_id_policy = jdbcTemplate.query(sql8, param8, rs -> {
//
//			String action_point_id2 = "";
//			while (rs.next()) {
//				action_point_id2 = rs.getString("action_point_name");
//				obj.setPolicy_action_part(rs.getString("action_part"));
//				obj.setPolicy_action_point_name(rs.getString("action_point_name"));
//				obj.setPolicy_responsible_dept(rs.getString("responsible_dept"));
//				obj.setPolicy_person_responsible(rs.getString("person_responsible"));
//				obj.setPolicy_target_value(rs.getString("target"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return action_point_id2;
//		});
//
//		String sql88 = "SELECT * FROM dev_action_plan_indicators  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and action_point_id=?   and dev_action_id=?";
//		Object[] params88 = new Object[] { district_id, cycle_id, year, action_point_id_policy, p_key };
//
//		jdbcTemplate.query(sql88, params88, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setPolicy_indicator_name(rs.getString("indicator_name"));
//				obj.setPolicy_description_of_indicator(rs.getString("description_of_indicator"));
//				obj.setPolicy_target_value(rs.getString("target_value"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return str;
//		});

		String sql8 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
		Object[] param8 = new Object[] { district_id, cycle_id, year, p_key, "Policy/governance" };

		List<Form4PlanCommonObject> list6_policy_arr = new ArrayList<>();

		jdbcTemplate.query(sql8, param8, rs -> {

			while (rs.next()) {
				Form4PlanCommonObject tempobj = new Form4PlanCommonObject();
				// very crucial variable for recognising tyoe of data
				tempobj.setDev_action_point_id(rs.getString("dev_action_point_id"));
				tempobj.setAction_req_id(rs.getString("action_point_name"));
				tempobj.setDocument_action_required("");
				tempobj.setSel_stakeholder(rs.getString("responsible_dept"));
				tempobj.setPerson_responsible(rs.getString("person_responsible"));
				tempobj.setTimeline(rs.getString("target"));
				tempobj.setTarget("");
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey("");
				tempobj.setDocument_action_required("" + rs.getString("action_point_name"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { district_id, cycle_id, year,
						rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				tempobj.setDocument_action_required("" + action_real_value);

				String sql8_indi = "SELECT form.indicator_name,form.indicator_id,form.area_id,form.area_name,form.target_value,form.dev_indicator_id ,indi.definition FROM dev_action_plan_indicators form inner join indicators indi on  form.indicator_id=indi.id  where form.`district_id`=? and form.`cycle_id`=? and form.`financial_year`=?  and form.action_point_id=?   and form.dev_action_id=?";
				Object[] param8_indi = new Object[] { district_id, cycle_id, year, rs.getString("action_point_name"),
						p_key };

				// Inner Query
				List<Form4PlanSelIndicatorsList> get_indi = jdbcTemplate.query(sql8_indi, param8_indi, rs2 -> {

					List<Form4PlanSelIndicatorsList> sel_indi_list = new ArrayList<>();
					while (rs2.next()) {
						Form4PlanSelIndicatorsList indiobj = new Form4PlanSelIndicatorsList();
						indiobj.setIndicator_val("" + rs2.getString("indicator_name"));
						indiobj.setIndicator_id("" + rs2.getString("indicator_id"));
						indiobj.setArea_id("" + rs2.getString("area_id"));
						indiobj.setArea_name("" + rs2.getString("area_name"));
						indiobj.setTarget("" + rs2.getString("target_value"));
						indiobj.setDev_indicator_id("" + rs2.getString("dev_indicator_id"));
						indiobj.setIndicator_desc("" + rs2.getString("definition"));
						;
						sel_indi_list.add(indiobj);
					}

					return sel_indi_list;
				});
				// End of Inner Query

				tempobj.setSel_indicators(get_indi);

				list6_policy_arr.add(tempobj);

			}

			return "";
		});

		obj.setPolicy_action_arr(list6_policy_arr);

		return obj;
	}

	public SavedForm4PlanResponse editUpdateForm4PlanToDb(Form4Plan model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		
		int row = 0;

//		String sql1 = "INSERT INTO `dev_action_plan_details`(`date_of_meeting`, `venue_of_meeting`, "
//				+ "`chariperson_of_meeting`, `theme_id`, `theme_leader`, `district_id`, `cycle_id`,"
//				+ " `financial_year`, `user_id`, `record_created`) VALUES (?,?," + "?,?,?,?,?,?," + "?,?)";

		String sql1 = " UPDATE `dev_action_plan_details` SET `date_of_meeting`=?, `venue_of_meeting`=?,"
				+ " `chariperson_of_meeting`=?,  `chariperson_of_meeting_others`=?,`theme_leader`=? , `user_id`=? , `completed`=?  where `district_id`=? and `cycle_id`=? and `financial_year`=?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setString(1, model.getDate_of_meeting());
			ps.setString(2, model.getVenue_of_meeting());
			ps.setString(3, model.getChariperson_of_meeting());
			ps.setString(4, model.getChariperson_of_meeting_others());
			ps.setString(5, model.getTheme_leader());
			ps.setString(6, model.getUserid());
			ps.setString(7, model.getCompleted());
			ps.setString(8, model.getDistrict());
			ps.setString(9, model.getCycle());
			ps.setString(10, model.getYear());
			return ps;
		});

		
		// INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`,
//		`action_point_name`, `responsible_dept`, `person_responsible`,
//		`target`,`district_id`, `cycle_id`, `financial_year`,  `user_id`,
//		`record_created`) values(:dev_action_id,:action_part,:action_point_name,
//				:responsible_dept,:person_responsible,:target,:district_id,:cycle_id,
//				:financial_year,:user_id,:record_created)

		// SELECT * FROM `form_3_1_action_part_engagement_action_req` where
		// district_id='1' and cycle_id='1' and financial_year='2019' and
		// action_part='Service delivery' and action_required != '' ;

//		String sql3 = "SELECt * FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?  and `action_part`=?";
//		Object[] params3 = new Object[] { district_id, cycle_id, year, p_key, "Service delivery" };

		String sql2 = " UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
				+ "`person_responsible`=?, `target`=?,`user_id`=?  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `action_part`=?";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql2);
//			ps.setString(1, model.getService_responsible_dept());
//			ps.setString(2, model.getService_person_responsible());
//			String temp = model.getService_target();
//			if (temp == null || "null".equals(model.getService_target())) {
//				temp = "0";
//			} else {
//				temp = model.getService_target();
//			}
//			ps.setString(3, temp);
//			ps.setString(4, model.getUserid());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, "Service delivery");
//			return ps;
//		});
//
//		System.out.println("Rows Updated by Edit form is :: " + row);

		// Service Delivery

		/*
		 * List<Form3DefineCommonArray> list1_update = model.getService_array();
		 * 
		 * jdbcTemplate .batchUpdate(
		 * "UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
		 * + "`user_id`=?," + "`record_created`=? WHERE `id`=? ", new
		 * BatchPreparedStatementSetter() {
		 * 
		 * public void setValues(PreparedStatement ps, int i) throws SQLException {
		 * Form3DefineCommonArray obj=list1_update.get(i); ps.setString(1,
		 * obj.getDocument_description_p_f_h_s_p()); ps.setString(2,
		 * obj.getDocument_possible_s_t_i()); ps.setString(3, model.getUserid());
		 * ps.setString(4, formatedDateTime); ps.setString(5,
		 * ""+obj.getForm_3_1_action_part_engagement_nm_details_pkey()); }
		 * 
		 * public int getBatchSize() { return list1_update.size(); }
		 * 
		 * });
		 */

		String form5_sql = "SELECt * FROM `folloup_action_plan_tbl` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] form5_param = new Object[] { model.getDistrict(), model.getCycle(), model.getYear() };

		String form5_p_key = jdbcTemplate.query(form5_sql, form5_param, rs -> {

			String primary_key = "";
			while (rs.next()) {
				primary_key = rs.getString("followup_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return primary_key;
		});

		String temp_sql = "SELECt * FROM `dev_action_plan_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] temp_param = new Object[] { model.getDistrict(), model.getCycle(), model.getYear() };

		String p_key = jdbcTemplate.query(temp_sql, temp_param, rs -> {

			String primary_key = "";
			while (rs.next()) {
				primary_key = rs.getString("dev_action_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return primary_key;
		});

		List<Form4PlanCommonObject> list1_service_update = model.getService_action_arr();

		try {

			jdbcTemplate.batchUpdate(" UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
					+ "`person_responsible`=?, `target`=?,`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `action_point_name`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form4PlanCommonObject tempobj = list1_service_update.get(i);

							ps.setString(1, "" + tempobj.getSel_stakeholder());
							ps.setString(2, "" + tempobj.getPerson_responsible());
							ps.setString(3, "" + tempobj.getTimeline());
							ps.setString(4, "" + model.getUserid());
							ps.setString(5, "" + formatedDateTime);
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + tempobj.getAction_req_id());
						}

						public int getBatchSize() {
							return list1_service_update.size();
						}

					});

			for (int pos = 0; pos < list1_service_update.size(); pos++) {

				
				for (int count2 = 0; count2 < list1_service_update.get(pos).getSel_indicators().size(); count2++) {
					/*******************************************************************/

					String sql_check = "SELECT * FROM `dev_action_plan_indicators`  where  `district_id`=? and `cycle_id`=? and "
							+ " `financial_year`=? and `action_point_id`=? and `indicator_id`=?";

					Object[] params_check = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
							list1_service_update.get(pos).getAction_req_id(),
							list1_service_update.get(pos).getSel_indicators().get(count2).getIndicator_id() };

					String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

						String keyval = "";
						while (rs.next()) {
							keyval = rs.getString("dev_indicator_id");

						}
						/* We can also return any variable-data from here but not used currently */
						return keyval;
					});

					int index = pos;

					int inner_count = count2;

					if (!("".equals(checkid))) {

						// If exists then only update

						String sql_up = " UPDATE `dev_action_plan_indicators` SET `target_value` = ?, `user_id` = ?   "
								+ "     WHERE    `district_id`=? and `cycle_id`=? and  `financial_year`=?  "
								+ " and `action_point_id`=? and `indicator_id`=?";

						int row_up = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_up);

							ps.setString(1,
									list1_service_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(2, model.getUserid());

							ps.setString(3, model.getDistrict());

							ps.setString(4, model.getCycle());

							ps.setString(5, model.getYear());

							ps.setString(6, list1_service_update.get(index).getAction_req_id());

							ps.setString(7, list1_service_update.get(index).getSel_indicators().get(inner_count)
									.getIndicator_id());

							return ps;
						});

					} else {

						/*******************/

						String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);

							ps.setString(2, "" + list1_service_update.get(index).getAction_req_id());

							/*--------------------------------------------*/

							String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
									+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
									+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
							Object[] params_indi_name_sql = new Object[] { list1_service_update.get(index)
									.getSel_indicators().get(inner_count).getIndicator_id() };

							jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

								while (rs.next()) {

									ps.setString(3, "" + rs.getString("indicator_name"));
									// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

									ps.setString(4, "" + "");
									// sendindiobj.setForm4_id(indiobj.getForm4_id());

									ps.setString(5, "" + list1_service_update.get(index).getSel_indicators()
											.get(inner_count).getIndicator_id());

									ps.setString(6, "" + rs.getString("area_name"));
									// sendindiobj.setArea_name(indiobj.getArea_name());
								}
								/* We can also return any variable-data from here but not used currently */
								return "";
							});

							/*--------------------------------------------*/

							ps.setString(7, "" + list1_service_update.get(index).getSel_indicators().get(inner_count)
									.getArea_id());

							ps.setString(8, ""
									+ list1_service_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(9, model.getDistrict());
							ps.setString(10, model.getCycle());
							ps.setString(11, model.getYear());
							ps.setString(12, model.getUserid());
							ps.setString(13, formatedDateTime);
							return ps;
						}, keyHolder3);

						if ("".equals(form5_p_key)) {

						} else {

							

							String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

							KeyHolder keyHolder5 = new GeneratedKeyHolder();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + form5_p_key);

								String final_actionid = "" + list1_service_update.get(index).getAction_req_id();

								ps.setString(2, "" + final_actionid);

								ps.setString(3, "" + keyHolder3.getKey().intValue());

								ps.setString(4, "0");

								ps.setString(5, "" + model.getDistrict());

								ps.setString(6, "" + model.getCycle());

								ps.setString(7, "" + model.getYear());

								ps.setString(8, "" + model.getUserid());

								ps.setString(9, "" + formatedDateTime);

								return ps;
							}, keyHolder5);

						}

						/*********************/
					}

					/******************************************************************/
				} // for loop for selected indicators of each service action


			}

		} catch (Exception e) {
			
			e.printStackTrace();
		}

		// Workforce

		List<Form4PlanCommonObject> list2_workforce_update = model.getWorkforce_action_arr();

		try {

			jdbcTemplate.batchUpdate(" UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
					+ "`person_responsible`=?, `target`=?,`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `action_point_name`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form4PlanCommonObject tempobj = list2_workforce_update.get(i);

							ps.setString(1, "" + tempobj.getSel_stakeholder());
							ps.setString(2, "" + tempobj.getPerson_responsible());
							ps.setString(3, "" + tempobj.getTimeline());
							ps.setString(4, "" + model.getUserid());
							ps.setString(5, "" + formatedDateTime);
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + tempobj.getAction_req_id());
						}

						public int getBatchSize() {
							return list2_workforce_update.size();
						}

					});

			for (int pos = 0; pos < list2_workforce_update.size(); pos++) {

				Object[] params1 = { model.getDistrict(), model.getCycle(), model.getYear(),
						list2_workforce_update.get(pos).getAction_req_id() };

				for (int count2 = 0; count2 < list2_workforce_update.get(pos).getSel_indicators().size(); count2++) {
					/*******************************************************************/

					String sql_check = "SELECT * FROM `dev_action_plan_indicators`  where  `district_id`=? and `cycle_id`=? and "
							+ " `financial_year`=? and `action_point_id`=? and `indicator_id`=?";

					Object[] params_check = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
							list2_workforce_update.get(pos).getAction_req_id(),
							list2_workforce_update.get(pos).getSel_indicators().get(count2).getIndicator_id() };

					String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

						String keyval = "";
						while (rs.next()) {
							keyval = rs.getString("dev_indicator_id");

						}
						/* We can also return any variable-data from here but not used currently */
						return keyval;
					});

					int index = pos;

					int inner_count = count2;

					if (!("".equals(checkid))) {

						// If exists then only update

						String sql_up = " UPDATE `dev_action_plan_indicators` SET `target_value` = ?, `user_id` = ?   "
								+ "     WHERE    `district_id`=? and `cycle_id`=? and  `financial_year`=?  "
								+ " and `action_point_id`=? and `indicator_id`=?";

						int row_up = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_up);

							ps.setString(1,
									list2_workforce_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(2, model.getUserid());

							ps.setString(3, model.getDistrict());

							ps.setString(4, model.getCycle());

							ps.setString(5, model.getYear());

							ps.setString(6, list2_workforce_update.get(index).getAction_req_id());

							ps.setString(7, list2_workforce_update.get(index).getSel_indicators().get(inner_count)
									.getIndicator_id());

							return ps;
						});

					} else {

						/*******************/

						String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);

							ps.setString(2, "" + list2_workforce_update.get(index).getAction_req_id());

							/*--------------------------------------------*/

							String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
									+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
									+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
							Object[] params_indi_name_sql = new Object[] { list2_workforce_update.get(index)
									.getSel_indicators().get(inner_count).getIndicator_id() };

							jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

								while (rs.next()) {

									ps.setString(3, "" + rs.getString("indicator_name"));
									// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

									ps.setString(4, "" + "");
									// sendindiobj.setForm4_id(indiobj.getForm4_id());

									ps.setString(5, "" + list2_workforce_update.get(index).getSel_indicators()
											.get(inner_count).getIndicator_id());

									ps.setString(6, "" + rs.getString("area_name"));
									// sendindiobj.setArea_name(indiobj.getArea_name());
								}
								/* We can also return any variable-data from here but not used currently */
								return "";
							});

							/*--------------------------------------------*/

							ps.setString(7, "" + list2_workforce_update.get(index).getSel_indicators().get(inner_count)
									.getArea_id());

							ps.setString(8, "" + list2_workforce_update.get(index).getSel_indicators().get(inner_count)
									.getTarget());

							ps.setString(9, model.getDistrict());
							ps.setString(10, model.getCycle());
							ps.setString(11, model.getYear());
							ps.setString(12, model.getUserid());
							ps.setString(13, formatedDateTime);
							return ps;
						}, keyHolder3);

						if ("".equals(form5_p_key)) {

						} else {

							
							String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

							KeyHolder keyHolder5 = new GeneratedKeyHolder();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + form5_p_key);

								String final_actionid = "" + list2_workforce_update.get(index).getAction_req_id();

								ps.setString(2, "" + final_actionid);

								ps.setString(3, "" + keyHolder3.getKey().intValue());

								ps.setString(4, "0");

								ps.setString(5, "" + model.getDistrict());

								ps.setString(6, "" + model.getCycle());

								ps.setString(7, "" + model.getYear());

								ps.setString(8, "" + model.getUserid());

								ps.setString(9, "" + formatedDateTime);

								return ps;
							}, keyHolder5);

						}

						/*********************/
					}

					/******************************************************************/
				} // for loop for selected indicators of each workforce action

//				int rows = jdbcTemplate.update(
//						"DELETE FROM `dev_action_plan_indicators` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_id`=?",
//						params1);
//				System.out.println("dev_action_plan_indicators = " + rows + " row(s) deleted.");
//
//				List<Form4PlanSelIndicatorsList> indi_list = list2_workforce_update.get(pos).getSel_indicators();
//				int index = pos;
//
//				if (indi_list.size() != 0) {
//					jdbcTemplate.batchUpdate(
//							"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//									+ "      `indicator_id` ,  `area_name` , `area_id`, "
//									+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//									+ "		?,?,?,?,?,?,?,?,?,?,?)",
//							new BatchPreparedStatementSetter() {
//
//								// indicator_id , area_name , area_id
//
//								public void setValues(PreparedStatement ps, int i) throws SQLException {
//									ps.setString(1, "" + p_key);
//									ps.setString(2, "" + list2_workforce_update.get(index).getAction_req_id());
//									ps.setString(3, "" + indi_list.get(i).getIndicator_val());
//									ps.setString(4, "" + "");
//									ps.setString(5, "" + indi_list.get(i).getIndicator_id());
//									ps.setString(6, "" + indi_list.get(i).getArea_name());
//									ps.setString(7, "" + indi_list.get(i).getArea_id());
//
//									ps.setString(8, "" + indi_list.get(i).getTarget());
//									ps.setString(9, model.getDistrict());
//									ps.setString(10, model.getCycle());
//									ps.setString(11, model.getYear());
//									ps.setString(12, model.getUserid());
//									ps.setString(13, formatedDateTime);
//								}
//
//								public int getBatchSize() {
//									return list2_workforce_update.get(index).getSel_indicators().size();
//								}
//
//							});
//				}

			}

		} catch (Exception e) {
			System.out.println("Exception in Workforce");
		}

		// Supplies & Technology

		List<Form4PlanCommonObject> list3_supplies_update = model.getSupplies_action_arr();

		try {

			jdbcTemplate.batchUpdate(" UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
					+ "`person_responsible`=?, `target`=?,`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `action_point_name`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form4PlanCommonObject tempobj = list3_supplies_update.get(i);

							ps.setString(1, "" + tempobj.getSel_stakeholder());
							ps.setString(2, "" + tempobj.getPerson_responsible());
							ps.setString(3, "" + tempobj.getTimeline());
							ps.setString(4, "" + model.getUserid());
							ps.setString(5, "" + formatedDateTime);
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + tempobj.getAction_req_id());
						}

						public int getBatchSize() {
							return list3_supplies_update.size();
						}

					});

			for (int pos = 0; pos < list3_supplies_update.size(); pos++) {

				Object[] params1 = { model.getDistrict(), model.getCycle(), model.getYear(),
						list3_supplies_update.get(pos).getAction_req_id() };

				for (int count2 = 0; count2 < list3_supplies_update.get(pos).getSel_indicators().size(); count2++) {
					/*******************************************************************/

					String sql_check = "SELECT * FROM `dev_action_plan_indicators`  where  `district_id`=? and `cycle_id`=? and "
							+ " `financial_year`=? and `action_point_id`=? and `indicator_id`=?";

					Object[] params_check = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
							list3_supplies_update.get(pos).getAction_req_id(),
							list3_supplies_update.get(pos).getSel_indicators().get(count2).getIndicator_id() };

					String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

						String keyval = "";
						while (rs.next()) {
							keyval = rs.getString("dev_indicator_id");

						}
						/* We can also return any variable-data from here but not used currently */
						return keyval;
					});

					int index = pos;

					int inner_count = count2;

					if (!("".equals(checkid))) {

						// If exists then only update

						String sql_up = " UPDATE `dev_action_plan_indicators` SET `target_value` = ?, `user_id` = ?   "
								+ "     WHERE    `district_id`=? and `cycle_id`=? and  `financial_year`=?  "
								+ " and `action_point_id`=? and `indicator_id`=?";

						int row_up = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_up);

							ps.setString(1,
									list3_supplies_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(2, model.getUserid());

							ps.setString(3, model.getDistrict());

							ps.setString(4, model.getCycle());

							ps.setString(5, model.getYear());

							ps.setString(6, list3_supplies_update.get(index).getAction_req_id());

							ps.setString(7, list3_supplies_update.get(index).getSel_indicators().get(inner_count)
									.getIndicator_id());

							return ps;
						});

					} else {

						/*******************/

						String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);

							ps.setString(2, "" + list3_supplies_update.get(index).getAction_req_id());

							/*--------------------------------------------*/

							String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
									+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
									+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
							Object[] params_indi_name_sql = new Object[] { list3_supplies_update.get(index)
									.getSel_indicators().get(inner_count).getIndicator_id() };

							jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

								while (rs.next()) {

									ps.setString(3, "" + rs.getString("indicator_name"));
									// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

									ps.setString(4, "" + "");
									// sendindiobj.setForm4_id(indiobj.getForm4_id());

									ps.setString(5, "" + list3_supplies_update.get(index).getSel_indicators()
											.get(inner_count).getIndicator_id());

									ps.setString(6, "" + rs.getString("area_name"));
									// sendindiobj.setArea_name(indiobj.getArea_name());
								}
								/* We can also return any variable-data from here but not used currently */
								return "";
							});

							/*--------------------------------------------*/

							ps.setString(7, "" + list3_supplies_update.get(index).getSel_indicators().get(inner_count)
									.getArea_id());

							ps.setString(8, "" + list3_supplies_update.get(index).getSel_indicators().get(inner_count)
									.getTarget());

							ps.setString(9, model.getDistrict());
							ps.setString(10, model.getCycle());
							ps.setString(11, model.getYear());
							ps.setString(12, model.getUserid());
							ps.setString(13, formatedDateTime);
							return ps;
						}, keyHolder3);

						if ("".equals(form5_p_key)) {

						} else {

							
							String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

							KeyHolder keyHolder5 = new GeneratedKeyHolder();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + form5_p_key);

								String final_actionid = "" + list3_supplies_update.get(index).getAction_req_id();

								ps.setString(2, "" + final_actionid);

								ps.setString(3, "" + keyHolder3.getKey().intValue());

								ps.setString(4, "0");

								ps.setString(5, "" + model.getDistrict());

								ps.setString(6, "" + model.getCycle());

								ps.setString(7, "" + model.getYear());

								ps.setString(8, "" + model.getUserid());

								ps.setString(9, "" + formatedDateTime);

								return ps;
							}, keyHolder5);

						}

						/*********************/
					}

					/******************************************************************/
				} // for loop for selected indicators of each supplies action

//				int rows = jdbcTemplate.update(
//						"DELETE FROM `dev_action_plan_indicators` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_id`=?",
//						params1);
//				System.out.println("dev_action_plan_indicators = " + rows + " row(s) deleted.");
//
//				List<Form4PlanSelIndicatorsList> indi_list = list3_supplies_update.get(pos).getSel_indicators();
//				int index = pos;
//
//				if (indi_list.size() != 0) {
//					jdbcTemplate.batchUpdate(
//							"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//									+ "      `indicator_id` ,  `area_name` , `area_id`, "
//									+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//									+ "		?,?,?,?,?,?,?,?,?,?,?)",
//							new BatchPreparedStatementSetter() {
//
//								// indicator_id , area_name , area_id
//
//								public void setValues(PreparedStatement ps, int i) throws SQLException {
//									ps.setString(1, "" + p_key);
//									ps.setString(2, "" + list3_supplies_update.get(index).getAction_req_id());
//									ps.setString(3, "" + indi_list.get(i).getIndicator_val());
//									ps.setString(4, "" + "");
//									ps.setString(5, "" + indi_list.get(i).getIndicator_id());
//									ps.setString(6, "" + indi_list.get(i).getArea_name());
//									ps.setString(7, "" + indi_list.get(i).getArea_id());
//
//									ps.setString(8, "" + indi_list.get(i).getTarget());
//									ps.setString(9, model.getDistrict());
//									ps.setString(10, model.getCycle());
//									ps.setString(11, model.getYear());
//									ps.setString(12, model.getUserid());
//									ps.setString(13, formatedDateTime);
//								}
//
//								public int getBatchSize() {
//									return list3_supplies_update.get(index).getSel_indicators().size();
//								}
//
//							});
//				}
			}

		} catch (Exception e) {
			System.out.println("Exception in Supplies");
		}

		// Health & Information

		List<Form4PlanCommonObject> list4_health_update = model.getHealth_action_arr();

		try {

			jdbcTemplate.batchUpdate(" UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
					+ "`person_responsible`=?, `target`=?,`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `action_point_name`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form4PlanCommonObject tempobj = list4_health_update.get(i);

							ps.setString(1, "" + tempobj.getSel_stakeholder());
							ps.setString(2, "" + tempobj.getPerson_responsible());
							ps.setString(3, "" + tempobj.getTimeline());
							ps.setString(4, "" + model.getUserid());
							ps.setString(5, "" + formatedDateTime);
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + tempobj.getAction_req_id());
						}

						public int getBatchSize() {
							return list4_health_update.size();
						}

					});

			for (int pos = 0; pos < list4_health_update.size(); pos++) {

				Object[] params1 = { model.getDistrict(), model.getCycle(), model.getYear(),
						list4_health_update.get(pos).getAction_req_id() };

				for (int count2 = 0; count2 < list4_health_update.get(pos).getSel_indicators().size(); count2++) {
					/*******************************************************************/

					String sql_check = "SELECT * FROM `dev_action_plan_indicators`  where  `district_id`=? and `cycle_id`=? and "
							+ " `financial_year`=? and `action_point_id`=? and `indicator_id`=?";

					Object[] params_check = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
							list4_health_update.get(pos).getAction_req_id(),
							list4_health_update.get(pos).getSel_indicators().get(count2).getIndicator_id() };

					String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

						String keyval = "";
						while (rs.next()) {
							keyval = rs.getString("dev_indicator_id");

						}
						/* We can also return any variable-data from here but not used currently */
						return keyval;
					});

					int index = pos;

					int inner_count = count2;

					if (!("".equals(checkid))) {

						// If exists then only update

						String sql_up = " UPDATE `dev_action_plan_indicators` SET `target_value` = ?, `user_id` = ?   "
								+ "     WHERE    `district_id`=? and `cycle_id`=? and  `financial_year`=?  "
								+ " and `action_point_id`=? and `indicator_id`=?";

						int row_up = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_up);

							ps.setString(1,
									list4_health_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(2, model.getUserid());

							ps.setString(3, model.getDistrict());

							ps.setString(4, model.getCycle());

							ps.setString(5, model.getYear());

							ps.setString(6, list4_health_update.get(index).getAction_req_id());

							ps.setString(7, list4_health_update.get(index).getSel_indicators().get(inner_count)
									.getIndicator_id());

							return ps;
						});

					} else {

						/*******************/

						String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);

							ps.setString(2, "" + list4_health_update.get(index).getAction_req_id());

							/*--------------------------------------------*/

							String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
									+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
									+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
							Object[] params_indi_name_sql = new Object[] { list4_health_update.get(index)
									.getSel_indicators().get(inner_count).getIndicator_id() };

							jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

								while (rs.next()) {

									ps.setString(3, "" + rs.getString("indicator_name"));
									// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

									ps.setString(4, "" + "");
									// sendindiobj.setForm4_id(indiobj.getForm4_id());

									ps.setString(5, "" + list4_health_update.get(index).getSel_indicators()
											.get(inner_count).getIndicator_id());

									ps.setString(6, "" + rs.getString("area_name"));
									// sendindiobj.setArea_name(indiobj.getArea_name());
								}
								/* We can also return any variable-data from here but not used currently */
								return "";
							});

							/*--------------------------------------------*/

							ps.setString(7, ""
									+ list4_health_update.get(index).getSel_indicators().get(inner_count).getArea_id());

							ps.setString(8, ""
									+ list4_health_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(9, model.getDistrict());
							ps.setString(10, model.getCycle());
							ps.setString(11, model.getYear());
							ps.setString(12, model.getUserid());
							ps.setString(13, formatedDateTime);
							return ps;
						}, keyHolder3);

						if ("".equals(form5_p_key)) {

						} else {

							
							String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

							KeyHolder keyHolder5 = new GeneratedKeyHolder();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + form5_p_key);

								String final_actionid = "" + list4_health_update.get(index).getAction_req_id();

								ps.setString(2, "" + final_actionid);

								ps.setString(3, "" + keyHolder3.getKey().intValue());

								ps.setString(4, "0");

								ps.setString(5, "" + model.getDistrict());

								ps.setString(6, "" + model.getCycle());

								ps.setString(7, "" + model.getYear());

								ps.setString(8, "" + model.getUserid());

								ps.setString(9, "" + formatedDateTime);

								return ps;
							}, keyHolder5);

						}

						/*********************/
					}

					/******************************************************************/
				} // for loop for selected indicators of each health action

//				int rows = jdbcTemplate.update(
//						"DELETE FROM `dev_action_plan_indicators` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_id`=?",
//						params1);
//				System.out.println("dev_action_plan_indicators = " + rows + " row(s) deleted.");
//
//				List<Form4PlanSelIndicatorsList> indi_list = list4_health_update.get(pos).getSel_indicators();
//				int index = pos;
//
//				if (indi_list.size() != 0) {
//					jdbcTemplate.batchUpdate(
//							"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//									+ "      `indicator_id` ,  `area_name` , `area_id`, "
//									+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//									+ "		?,?,?,?,?,?,?,?,?,?,?)",
//							new BatchPreparedStatementSetter() {
//
//								// indicator_id , area_name , area_id
//
//								public void setValues(PreparedStatement ps, int i) throws SQLException {
//									ps.setString(1, "" + p_key);
//									ps.setString(2, "" + list4_health_update.get(index).getAction_req_id());
//									ps.setString(3, "" + indi_list.get(i).getIndicator_val());
//									ps.setString(4, "" + "");
//									ps.setString(5, "" + indi_list.get(i).getIndicator_id());
//									ps.setString(6, "" + indi_list.get(i).getArea_name());
//									ps.setString(7, "" + indi_list.get(i).getArea_id());
//
//									ps.setString(8, "" + indi_list.get(i).getTarget());
//									ps.setString(9, model.getDistrict());
//									ps.setString(10, model.getCycle());
//									ps.setString(11, model.getYear());
//									ps.setString(12, model.getUserid());
//									ps.setString(13, formatedDateTime);
//								}
//
//								public int getBatchSize() {
//									return list4_health_update.get(index).getSel_indicators().size();
//								}
//
//							});
//				}
			}
		} catch (Exception e) {
			System.out.println("Exception in Health");
		}

		// Finance

		List<Form4PlanCommonObject> list5_finance_update = model.getFinance_action_arr();

		try {

			jdbcTemplate.batchUpdate(" UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
					+ "`person_responsible`=?, `target`=?,`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `action_point_name`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form4PlanCommonObject tempobj = list5_finance_update.get(i);

							ps.setString(1, "" + tempobj.getSel_stakeholder());
							ps.setString(2, "" + tempobj.getPerson_responsible());
							ps.setString(3, "" + tempobj.getTimeline());
							ps.setString(4, "" + model.getUserid());
							ps.setString(5, "" + formatedDateTime);
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + tempobj.getAction_req_id());
						}

						public int getBatchSize() {
							return list5_finance_update.size();
						}

					});

			for (int pos = 0; pos < list5_finance_update.size(); pos++) {

				Object[] params1 = { model.getDistrict(), model.getCycle(), model.getYear(),
						list5_finance_update.get(pos).getAction_req_id() };

				for (int count2 = 0; count2 < list5_finance_update.get(pos).getSel_indicators().size(); count2++) {
					/*******************************************************************/

					String sql_check = "SELECT * FROM `dev_action_plan_indicators`  where  `district_id`=? and `cycle_id`=? and "
							+ " `financial_year`=? and `action_point_id`=? and `indicator_id`=?";

					Object[] params_check = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
							list5_finance_update.get(pos).getAction_req_id(),
							list5_finance_update.get(pos).getSel_indicators().get(count2).getIndicator_id() };

					String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

						String keyval = "";
						while (rs.next()) {
							keyval = rs.getString("dev_indicator_id");

						}
						/* We can also return any variable-data from here but not used currently */
						return keyval;
					});

					int index = pos;

					int inner_count = count2;

					if (!("".equals(checkid))) {

						// If exists then only update

						String sql_up = " UPDATE `dev_action_plan_indicators` SET `target_value` = ?, `user_id` = ?   "
								+ "     WHERE    `district_id`=? and `cycle_id`=? and  `financial_year`=?  "
								+ " and `action_point_id`=? and `indicator_id`=?";

						int row_up = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_up);

							ps.setString(1,
									list5_finance_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(2, model.getUserid());

							ps.setString(3, model.getDistrict());

							ps.setString(4, model.getCycle());

							ps.setString(5, model.getYear());

							ps.setString(6, list5_finance_update.get(index).getAction_req_id());

							ps.setString(7, list5_finance_update.get(index).getSel_indicators().get(inner_count)
									.getIndicator_id());

							return ps;
						});

					} else {

						/*******************/

						String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);

							ps.setString(2, "" + list5_finance_update.get(index).getAction_req_id());

							/*--------------------------------------------*/

							String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
									+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
									+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
							Object[] params_indi_name_sql = new Object[] { list5_finance_update.get(index)
									.getSel_indicators().get(inner_count).getIndicator_id() };

							jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

								while (rs.next()) {

									ps.setString(3, "" + rs.getString("indicator_name"));
									// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

									ps.setString(4, "" + "");
									// sendindiobj.setForm4_id(indiobj.getForm4_id());

									ps.setString(5, "" + list5_finance_update.get(index).getSel_indicators()
											.get(inner_count).getIndicator_id());

									ps.setString(6, "" + rs.getString("area_name"));
									// sendindiobj.setArea_name(indiobj.getArea_name());
								}
								/* We can also return any variable-data from here but not used currently */
								return "";
							});

							/*--------------------------------------------*/

							ps.setString(7, "" + list5_finance_update.get(index).getSel_indicators().get(inner_count)
									.getArea_id());

							ps.setString(8, ""
									+ list5_finance_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(9, model.getDistrict());
							ps.setString(10, model.getCycle());
							ps.setString(11, model.getYear());
							ps.setString(12, model.getUserid());
							ps.setString(13, formatedDateTime);
							return ps;
						}, keyHolder3);

						if ("".equals(form5_p_key)) {

						} else {

							
							String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

							KeyHolder keyHolder5 = new GeneratedKeyHolder();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + form5_p_key);

								String final_actionid = "" + list5_finance_update.get(index).getAction_req_id();

								ps.setString(2, "" + final_actionid);

								ps.setString(3, "" + keyHolder3.getKey().intValue());

								ps.setString(4, "0");

								ps.setString(5, "" + model.getDistrict());

								ps.setString(6, "" + model.getCycle());

								ps.setString(7, "" + model.getYear());

								ps.setString(8, "" + model.getUserid());

								ps.setString(9, "" + formatedDateTime);

								return ps;
							}, keyHolder5);

						}

						/*********************/
					}

					/******************************************************************/
				} // for loop for selected indicators of each Finance action

//				int rows = jdbcTemplate.update(
//						"DELETE FROM `dev_action_plan_indicators` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_id`=?",
//						params1);
//				System.out.println("dev_action_plan_indicators = " + rows + " row(s) deleted.");
//
//				List<Form4PlanSelIndicatorsList> indi_list = list5_finance_update.get(pos).getSel_indicators();
//				int index = pos;
//
//				if (indi_list.size() != 0) {
//					jdbcTemplate.batchUpdate(
//							"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//									+ "      `indicator_id` ,  `area_name` , `area_id`, "
//									+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//									+ "		?,?,?,?,?,?,?,?,?,?,?)",
//							new BatchPreparedStatementSetter() {
//
//								// indicator_id , area_name , area_id
//
//								public void setValues(PreparedStatement ps, int i) throws SQLException {
//									ps.setString(1, "" + p_key);
//									ps.setString(2, "" + list5_finance_update.get(index).getAction_req_id());
//									ps.setString(3, "" + indi_list.get(i).getIndicator_val());
//									ps.setString(4, "" + "");
//									ps.setString(5, "" + indi_list.get(i).getIndicator_id());
//									ps.setString(6, "" + indi_list.get(i).getArea_name());
//									ps.setString(7, "" + indi_list.get(i).getArea_id());
//
//									ps.setString(8, "" + indi_list.get(i).getTarget());
//									ps.setString(9, model.getDistrict());
//									ps.setString(10, model.getCycle());
//									ps.setString(11, model.getYear());
//									ps.setString(12, model.getUserid());
//									ps.setString(13, formatedDateTime);
//								}
//
//								public int getBatchSize() {
//									return list5_finance_update.get(index).getSel_indicators().size();
//								}
//
//							});
//				}
			}

		} catch (Exception e) {
			System.out.println("Exception in Finance");
		}

		// Policy

		List<Form4PlanCommonObject> list6_policy_update = model.getPolicy_action_arr();

		try {

			jdbcTemplate.batchUpdate(" UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
					+ "`person_responsible`=?, `target`=?,`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `action_point_name`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form4PlanCommonObject tempobj = list6_policy_update.get(i);

							ps.setString(1, "" + tempobj.getSel_stakeholder());
							ps.setString(2, "" + tempobj.getPerson_responsible());
							ps.setString(3, "" + tempobj.getTimeline());
							ps.setString(4, "" + model.getUserid());
							ps.setString(5, "" + formatedDateTime);
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + tempobj.getAction_req_id());
						}

						public int getBatchSize() {
							return list6_policy_update.size();
						}

					});

			for (int pos = 0; pos < list6_policy_update.size(); pos++) {

				Object[] params1 = { model.getDistrict(), model.getCycle(), model.getYear(),
						list6_policy_update.get(pos).getAction_req_id() };

				for (int count2 = 0; count2 < list6_policy_update.get(pos).getSel_indicators().size(); count2++) {
					/*******************************************************************/

					String sql_check = "SELECT * FROM `dev_action_plan_indicators`  where  `district_id`=? and `cycle_id`=? and "
							+ " `financial_year`=? and `action_point_id`=? and `indicator_id`=?";

					Object[] params_check = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
							list6_policy_update.get(pos).getAction_req_id(),
							list6_policy_update.get(pos).getSel_indicators().get(count2).getIndicator_id() };

					String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

						String keyval = "";
						while (rs.next()) {
							keyval = rs.getString("dev_indicator_id");

						}
						/* We can also return any variable-data from here but not used currently */
						return keyval;
					});

					int index = pos;

					int inner_count = count2;

					if (!("".equals(checkid))) {

						// If exists then only update

						String sql_up = " UPDATE `dev_action_plan_indicators` SET `target_value` = ?, `user_id` = ?   "
								+ "     WHERE    `district_id`=? and `cycle_id`=? and  `financial_year`=?  "
								+ " and `action_point_id`=? and `indicator_id`=?";

						int row_up = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_up);

							ps.setString(1,
									list6_policy_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(2, model.getUserid());

							ps.setString(3, model.getDistrict());

							ps.setString(4, model.getCycle());

							ps.setString(5, model.getYear());

							ps.setString(6, list6_policy_update.get(index).getAction_req_id());

							ps.setString(7, list6_policy_update.get(index).getSel_indicators().get(inner_count)
									.getIndicator_id());

							return ps;
						});

					} else {

						/*******************/

						String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);

							ps.setString(2, "" + list6_policy_update.get(index).getAction_req_id());

							/*--------------------------------------------*/

							String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
									+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
									+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
							Object[] params_indi_name_sql = new Object[] { list6_policy_update.get(index)
									.getSel_indicators().get(inner_count).getIndicator_id() };

							jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

								while (rs.next()) {

									ps.setString(3, "" + rs.getString("indicator_name"));
									// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

									ps.setString(4, "" + "");
									// sendindiobj.setForm4_id(indiobj.getForm4_id());

									ps.setString(5, "" + list6_policy_update.get(index).getSel_indicators()
											.get(inner_count).getIndicator_id());

									ps.setString(6, "" + rs.getString("area_name"));
									// sendindiobj.setArea_name(indiobj.getArea_name());
								}
								/* We can also return any variable-data from here but not used currently */
								return "";
							});

							/*--------------------------------------------*/

							ps.setString(7, ""
									+ list6_policy_update.get(index).getSel_indicators().get(inner_count).getArea_id());

							ps.setString(8, ""
									+ list6_policy_update.get(index).getSel_indicators().get(inner_count).getTarget());

							ps.setString(9, model.getDistrict());
							ps.setString(10, model.getCycle());
							ps.setString(11, model.getYear());
							ps.setString(12, model.getUserid());
							ps.setString(13, formatedDateTime);
							return ps;
						}, keyHolder3);

						if ("".equals(form5_p_key)) {

						} else {

						
							String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

							KeyHolder keyHolder5 = new GeneratedKeyHolder();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + form5_p_key);

								String final_actionid = "" + list6_policy_update.get(index).getAction_req_id();

								ps.setString(2, "" + final_actionid);

								ps.setString(3, "" + keyHolder3.getKey().intValue());

								ps.setString(4, "0");

								ps.setString(5, "" + model.getDistrict());

								ps.setString(6, "" + model.getCycle());

								ps.setString(7, "" + model.getYear());

								ps.setString(8, "" + model.getUserid());

								ps.setString(9, "" + formatedDateTime);

								return ps;
							}, keyHolder5);

						}

						/*********************/
					}

					/******************************************************************/
				} // for loop for selected indicators of each policy action

//				int rows = jdbcTemplate.update(
//						"DELETE FROM `dev_action_plan_indicators` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_id`=?",
//						params1);
//				System.out.println("dev_action_plan_indicators = " + rows + " row(s) deleted.");
//
//				List<Form4PlanSelIndicatorsList> indi_list = list6_policy_update.get(pos).getSel_indicators();
//				int index = pos;
//
//				if (indi_list.size() != 0) {
//					jdbcTemplate.batchUpdate(
//							"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//									+ "      `indicator_id` ,  `area_name` , `area_id`, "
//									+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//									+ "		?,?,?,?,?,?,?,?,?,?,?)",
//							new BatchPreparedStatementSetter() {
//
//								// indicator_id , area_name , area_id
//
//								public void setValues(PreparedStatement ps, int i) throws SQLException {
//									ps.setString(1, "" + p_key);
//									ps.setString(2, "" + list6_policy_update.get(index).getAction_req_id());
//									ps.setString(3, "" + indi_list.get(i).getIndicator_val());
//									ps.setString(4, "" + "");
//									ps.setString(5, "" + indi_list.get(i).getIndicator_id());
//									ps.setString(6, "" + indi_list.get(i).getArea_name());
//									ps.setString(7, "" + indi_list.get(i).getArea_id());
//
//									ps.setString(8, "" + indi_list.get(i).getTarget());
//									ps.setString(9, model.getDistrict());
//									ps.setString(10, model.getCycle());
//									ps.setString(11, model.getYear());
//									ps.setString(12, model.getUserid());
//									ps.setString(13, formatedDateTime);
//								}
//
//								public int getBatchSize() {
//									return list6_policy_update.get(index).getSel_indicators().size();
//								}
//
//							});
//				}
			}

		} catch (Exception e) {

			System.out.println("Exception in Policy");

			// TODO: handle exception
		}

		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;

	}

	public SavedForm4PlanResponse updateSelectedOptionalIndicatorInDb(EditOptionalIndicatorBean model) {

		String sql_update = "UPDATE `indicators_optional` SET `indicator_name` = ?,`definition` = ?,"
				+ "          `numerator` = ?,`denominator` = ?,`source` = ?,`Theme` = ?,`Current_Available` = ?,"
				+ "          `Frequency` = ?,`area_id` = ?,`category` = ? WHERE `id` = ?";

		int row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql_update);
			ps.setString(1, model.getEdit_indicator_name());
			ps.setString(2, model.getEdit_indicator_desc());
			ps.setString(3, model.getEdit_indicator_numerator());
			ps.setString(4, model.getEdit_indicator_denominator());
			ps.setString(5, model.getEdit_indicator_source());
			ps.setString(6, model.getTheme());
			ps.setString(7, model.getCurrent_available());
			ps.setString(8, model.getEdit_indicator_frequency());
			ps.setString(9, model.getEdit_indicator_area_id());
			ps.setString(10, model.getEdit_indicator_category());
			ps.setString(11, model.getIndicator_id());

			return ps;
		});

		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		if (row > 1) {
			responseobj.setProcessname("Updated successfully!!!");

			responseobj.setResponse_val("1");
		} else {
			responseobj.setProcessname("Changes not updated");

			responseobj.setResponse_val("0");
		}

		return responseobj;
	}

	public SavedForm4PlanResponse updateSelectedActionIndicatorInDb(EditIndicatorBean model) {

		String sql_update = "UPDATE `indicators` SET `indicator_name` = ?,`definition` = ?,`numerator` = ?,`denominator` = ?,"
				+ "          `source` = ?,`frequency` = ?,`area_id` = ?,`category` = ?  " + "           WHERE `id` = ?";

		int row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql_update);
			ps.setString(1, model.getEdit_indicator_name());
			ps.setString(2, model.getEdit_indicator_desc());
			ps.setString(3, model.getEdit_indicator_numerator());
			ps.setString(4, model.getEdit_indicator_denominator());
			ps.setString(5, model.getEdit_indicator_source());
			ps.setString(6, model.getEdit_indicator_frequency());
			ps.setString(7, model.getEdit_indicator_area_id());
			ps.setString(8, model.getEdit_indicator_category());
			ps.setString(9, model.getIndicator_id());

			return ps;
		});

		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		if (row > 1) {
			responseobj.setProcessname("Updated successfully!!!");

			responseobj.setResponse_val("1");
		} else {
			responseobj.setProcessname("Changes not updated");

			responseobj.setResponse_val("0");
		}

		return responseobj;

	}

	public SavedForm4PlanResponse updateSelectedIndicatorInDb(EditIndicatorBean model) {

		String sql_update = "UPDATE `indicators` SET `indicator_name` = ?,`definition` = ?,`numerator` = ?,`denominator` = ?,"
				+ "          `source` = ?,`frequency` = ?,`area_id` = ?,`category` = ?  " + "           WHERE `id` = ?";

		int row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql_update);
			ps.setString(1, model.getEdit_indicator_name());
			ps.setString(2, model.getEdit_indicator_desc());
			ps.setString(3, model.getEdit_indicator_numerator());
			ps.setString(4, model.getEdit_indicator_denominator());
			ps.setString(5, model.getEdit_indicator_source());
			ps.setString(6, model.getEdit_indicator_frequency());
			ps.setString(7, model.getEdit_indicator_area_id());
			ps.setString(8, model.getEdit_indicator_category());
			ps.setString(9, model.getIndicator_id());

			return ps;
		});

		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		if (row > 1) {
			responseobj.setProcessname("Updated successfully!!!");

			responseobj.setResponse_val("1");
		} else {
			responseobj.setProcessname("Changes not updated");

			responseobj.setResponse_val("0");
		}

		return responseobj;
	}

	public SavedForm4PlanResponse createNewCategorizedIndicatorInDb(CategorizedIndicatorBean model) {

		int row = 0;		

		if ("-1".equals(model.getNew_indicator_area_id())) {
			String sql1 = "INSERT INTO `area` (`id`, `area_name`) VALUES (NULL, ?)";

			KeyHolder keyHolder = new GeneratedKeyHolder();

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, model.getNew_area_name());
				return ps;
			}, keyHolder);

			long pk = keyHolder.getKey().longValue();

			model.setNew_indicator_area_id("" + pk);

			if ("ESSENTIAL".equals(model.getNew_indicator_type()) || "ACTION".equals(model.getNew_indicator_type())) {
				String sql11 = "INSERT INTO `indicators` " + "( `indicator_name`, `definition`, `numerator`, "
						+ "`denominator`, `source`, `frequency`, `area_id`, `category`) VALUES ( ?,?," + "?,?,?,?,?,?)";

				keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql11, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, model.getNew_indicator_name());
					ps.setString(2, model.getNew_indicator_desc());
					ps.setString(3, model.getNew_indicator_numerator());
					ps.setString(4, model.getNew_indicator_denominator());
					ps.setString(5, model.getNew_indicator_source());
					ps.setString(6, model.getNew_indicator_frequency());
					ps.setString(7, model.getNew_indicator_area_id());
					ps.setString(8, model.getNew_indicator_type());

					return ps;
				}, keyHolder);

			} else {
				String sql11 = "INSERT INTO `indicators_optional`(`indicator_name`,`definition`,`numerator`,`denominator`,"
						+ "   `source`,`Frequency`,`area_id`,`category`)VALUES" + "   (?,?,?,?,?,?,?,?);";

				keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql11, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, model.getNew_indicator_name());
					ps.setString(2, model.getNew_indicator_desc());
					ps.setString(3, model.getNew_indicator_numerator());
					ps.setString(4, model.getNew_indicator_denominator());
					ps.setString(5, model.getNew_indicator_source());
					ps.setString(6, model.getNew_indicator_frequency());
					ps.setString(7, model.getNew_indicator_area_id());
					ps.setString(8, model.getNew_indicator_type());

					return ps;
				}, keyHolder);
			}
		} else {
			if ("ESSENTIAL".equals(model.getNew_indicator_type()) || "ACTION".equals(model.getNew_indicator_type())) {
				String sql1 = "INSERT INTO `indicators` " + "( `indicator_name`, `definition`, `numerator`, "
						+ "`denominator`, `source`, `frequency`, `area_id`, `category`) VALUES ( ?,?," + "?,?,?,?,?,?)";

				KeyHolder keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, model.getNew_indicator_name());
					ps.setString(2, model.getNew_indicator_desc());
					ps.setString(3, model.getNew_indicator_numerator());
					ps.setString(4, model.getNew_indicator_denominator());
					ps.setString(5, model.getNew_indicator_source());
					ps.setString(6, model.getNew_indicator_frequency());
					ps.setString(7, model.getNew_indicator_area_id());
					ps.setString(8, model.getNew_indicator_type());

					return ps;
				}, keyHolder);

			} else {
				String sql1 = "INSERT INTO `indicators_optional`(`indicator_name`,`definition`,`numerator`,`denominator`,"
						+ "   `source`,`Frequency`,`area_id`,`category`)VALUES" + "   (?,?,?,?,?,?,?,?);";

				KeyHolder keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, model.getNew_indicator_name());
					ps.setString(2, model.getNew_indicator_desc());
					ps.setString(3, model.getNew_indicator_numerator());
					ps.setString(4, model.getNew_indicator_denominator());
					ps.setString(5, model.getNew_indicator_source());
					ps.setString(6, model.getNew_indicator_frequency());
					ps.setString(7, model.getNew_indicator_area_id());
					ps.setString(8, model.getNew_indicator_type());

					return ps;
				}, keyHolder);
			}
		}

		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	public SavedForm4PlanResponse createNewIndicatorInDb(IndicatorBean model) {

		int row = 0;

		String sql1 = "INSERT INTO `indicators` " + "( `indicator_name`, `definition`, `numerator`, "
				+ "`denominator`, `source`, `frequency`, `area_id`, `category`) VALUES ( ?,?," + "?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getNew_indicator_name());
			ps.setString(2, model.getNew_indicator_desc());
			ps.setString(3, model.getNew_indicator_numerator());
			ps.setString(4, model.getNew_indicator_denominator());
			ps.setString(5, model.getNew_indicator_source());
			ps.setString(6, model.getNew_indicator_frequency());
			ps.setString(7, model.getNew_indicator_area_id());
			ps.setString(8, "ACTION");

			return ps;
		}, keyHolder);

		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	public void sendForm4PlanSaveForExistingDistrictCycleYear(String district_id, String cycle_id, String year,
			String country, String province, Form4PlanSendAllDataBean response) {

		List<Form4PlanPrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form4ActionPlanActionPointsBean> list2 = new ArrayList<>();
		List<Form4ActionPlanIndicatorsBean> list3 = new ArrayList<>();

		String sql1 = "SELECT form4plan.`dev_action_id`,    form4plan.`date_of_meeting`,    form4plan.`venue_of_meeting`,  form4plan.`chariperson_of_meeting`,   form4plan.`chariperson_of_meeting_others`,  "
				+ "  form4plan.`theme_id`,  form4plan.`theme_leader`,    form4plan.`district_id`, "
				+ "   form4plan.`cycle_id`,   form4plan. `financial_year`,  form4plan.`user_id`, "
				+ "   form4plan.`record_created`, form4plan.`completed` FROM `dev_action_plan_details`  form4plan  "
				+ "  where form4plan.`district_id`=? and    form4plan.`cycle_id`=?  and   form4plan. `financial_year`=?";

		Object[] params1 = new Object[] { district_id, cycle_id, year };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form4PlanPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form4PlanPrimaryTableDataBean obj = new Form4PlanPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("dev_action_id"));
				obj.setMeetingdate(rs.getString("date_of_meeting"));
				obj.setMeetingvenue(rs.getString("venue_of_meeting"));
				obj.setChairpersonid(rs.getString("chariperson_of_meeting"));
				obj.setOtherchairperson(rs.getString("chariperson_of_meeting_others"));
				obj.setThemeid(rs.getString("theme_id"));
				obj.setThemeleaderofcycle(rs.getString("theme_leader"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setAuto_id(rs.getString("dev_action_id"));
				// obj.setCountry_id(model.getCountry_id());
				// obj.setProvince_id(model.getProvince_id());
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");
				obj.setCompleted(rs.getString("completed"));
				obj.setCountry_id(country);
				obj.setProvince_id(province);

				templist.add(obj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql3 = "SELECT form4plan.`dev_action_point_id`,    form4plan.`dev_action_id`,   form4plan. `action_part`, form4plan.`action_point_name`, "
				+ "   form4plan.`responsible_dept`,    form4plan.`person_responsible`,  form4plan.`target`, "
				+ "   form4plan.`district_id`,    form4plan.`cycle_id`,    form4plan.`financial_year`,  form4plan.`user_id`,  "
				+ "  form4plan.`record_created`  FROM  `dev_action_plan_action_points`   form4plan   where  "
				+ " form4plan.`district_id`=?  and    form4plan.`cycle_id`=?  and    form4plan.`financial_year`=?";

		Object[] params3 = new Object[] { district_id, cycle_id, year };

		list2 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form4ActionPlanActionPointsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form4ActionPlanActionPointsBean obj = new Form4ActionPlanActionPointsBean();

				obj.setAuto_id(rs.getString("dev_action_point_id"));
				obj.setForm4_id(rs.getString("dev_action_id"));

				String action_part_web = "SERVICED";

				if ("Service delivery".equals(rs.getString("action_part"))) {
					action_part_web = "SERVICED";
				}

				if ("Finance".equals(rs.getString("action_part"))) {
					action_part_web = "FINANCE";
				}

				if ("Workforce".equals(rs.getString("action_part"))) {
					action_part_web = "WORKFORCE";
				}

				if ("Health information".equals(rs.getString("action_part"))) {
					action_part_web = "HEALTHINFO";
				}

				if ("Policy/governance".equals(rs.getString("action_part"))) {
					action_part_web = "POLICYG";
				}

				if ("Supplies & technology".equals(rs.getString("action_part"))) {
					action_part_web = "SUPPLIES";
				}

				obj.setAction_part(action_part_web);
				obj.setForm3_actionreq_pkey(rs.getString("action_point_name"));

				obj.setResponsible_dept(rs.getString("responsible_dept"));
				obj.setPerson_responsible(rs.getString("person_responsible"));
				obj.setTimeline(rs.getString("target"));
				obj.setDistrict_id(rs.getString("district_id"));

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_name") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});

				obj.setForm3_actionreq_pkey_real_value(action_real_value);

				// obj.setCountry_id(rs.getString("country_id"));
				// obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				obj.setCountry_id(country);
				obj.setProvince_id(province);

				templist.add(obj);

			}

			return templist;
		});

		String sql3_indi = "SELECT form4plan.`dev_indicator_id`,    form4plan.`dev_action_id`,  form4plan.`action_point_id`, "
				+ "   form4plan.`indicator_name`,    form4plan.`description_of_indicator`, form4plan.`target_value`, "
				+ "   form4plan.`district_id`,    form4plan.`cycle_id`,   form4plan. `financial_year`, 	form4plan.`user_id`,  "
				+ "  form4plan.`record_created`,    form4plan.`indicator_id`,    form4plan.`area_name`,  form4plan.`area_id`  FROM   "
				+ "    `dev_action_plan_indicators`   form4plan   where  form4plan.`district_id`=? and    form4plan.`cycle_id`=? and   form4plan. `financial_year`=?";

		Object[] param3_indi = new Object[] { district_id, cycle_id, year };

		// Inner Query
		list3 = jdbcTemplate.query(sql3_indi, param3_indi, rs -> {

			List<Form4ActionPlanIndicatorsBean> templist = new ArrayList<>();

			while (rs.next()) {
				Form4ActionPlanIndicatorsBean obj = new Form4ActionPlanIndicatorsBean();

				obj.setAuto_id(rs.getString("dev_indicator_id"));
				obj.setForm4_id(rs.getString("dev_action_id"));
				obj.setForm3_actionreq_pkey(rs.getString("action_point_id"));

				obj.setIndicator_name(rs.getString("indicator_name"));
				obj.setIndicator_id(rs.getString("indicator_id"));
				obj.setArea_name(rs.getString("area_name"));
				obj.setArea_id(rs.getString("area_id"));
				obj.setTarget_value(rs.getString("target_value"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));

				// obj.setCountry_id(rs.getString("country_id"));
				// obj.setProvince_id(rs.getString("state_id"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				obj.setCountry_id(country);
				obj.setProvince_id(province);

				String temp_str = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? "
						+ " and `cycle_id`=? and `financial_year`=? and action_req_id=?";

				Object[] temp_str_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_id") };

				String action_real_value = jdbcTemplate.query(temp_str, temp_str_param, rst -> {

					String action_name = "";
					while (rst.next()) {

						action_name = rst.getString("action_required");
					}

					return action_name;
				});
				obj.setForm3_actionreq_pkey_real_value(action_real_value);

				/* Action Responsible id */

				String sql_get_id = "SELECT * FROM dev_action_plan_action_points  where  `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_name`=? and `dev_action_id`=?";
				Object[] params_sql_get_id = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year"), rs.getString("action_point_id"),
						rs.getString("dev_action_id") };

				String str_get_id = jdbcTemplate.query(sql_get_id, params_sql_get_id, rst -> {

					String keyval = "";
					while (rst.next()) {

						keyval = rst.getString("dev_action_point_id");

					}

					return keyval;
				});

				/* End of Action Responsible id */

				obj.setAction_responsible_id(str_get_id);

				templist.add(obj);

			}

			return templist;
		});
		// End of Inner Query

		response.setForm4Plan(list1);
		response.setForm4ActionPlanActionPoints(list2);
		response.setForm4ActionPlanIndicators(list3);

		response.setError_code("200");
		response.setMessage("Sending Form4 Plan Data");

		// return response;

	}

	public Form4PlanSendAllDataBean consumeAllForm4PlanSaveandUpdate(List<Form2EngagestakeIDDetailsBean> mapping3,
			List<Form3DefineActionIDDetailsBean> mapping2, AllFormsDataFetchFromAndroidBean model) {

		Form4PlanSendAllDataBean response = new Form4PlanSendAllDataBean();

		String sql10 = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,i.id as `indicator_id`,i.indicator_name,"
				+ " i.definition, i.numerator, i.denominator, i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id";
		Object[] params10 = new Object[] {};

		List<Menu_Area_Indicator_Object> getlist2 = jdbcTemplate.query(sql10, params10, rs -> {

			List<Menu_Area_Indicator_Object> templist = new ArrayList<>();
			while (rs.next()) {
				Menu_Area_Indicator_Object val = new Menu_Area_Indicator_Object();

				val.setArea_id("" + rs.getString("area_id"));
				val.setArea_name("" + rs.getString("area_name"));
				val.setIndicator_val("" + rs.getString("indicator_name"));
				val.setIndicator_id("" + rs.getString("indicator_id"));
				val.setDefinition("" + rs.getString("definition"));
				val.setDenominator("" + rs.getString("denominator"));
				val.setFrequency("" + rs.getString("frequency"));
				val.setNumerator("" + rs.getString("numerator"));
				val.setSource("" + rs.getString("source"));
				val.setCategory("" + rs.getString("category"));
				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);		

		Form4PlanSendAllDataBean form4plan = model.getForm4();

		List<Form4PlanPrimaryTableDataBean> list1 = form4plan.getForm4Plan();

		List<Form4PlanPrimaryTableDataBean> list_primary_send = new ArrayList<>();
		List<Form4ActionPlanActionPointsBean> list_actions_send = new ArrayList<>();
		List<Form4ActionPlanIndicatorsBean> list_indi_send = new ArrayList<>();

	
		for (int index = 0; index < list1.size(); index++) {

			Form4PlanPrimaryTableDataBean tempobj = list1.get(index);

			

			Form4PlanPrimaryTableDataBean sendobj = new Form4PlanPrimaryTableDataBean();

			if ("APP".equals(tempobj.getDatafrom())) {

				/*-------------------------------------------*/

				String sql_check = "SELECT * FROM `dev_action_plan_details`  where  `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] params_check = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
						tempobj.getYear() };

				String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

					String keyval = "";
					while (rs.next()) {
						keyval = rs.getString("dev_action_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return keyval;
				});

				if ("".equals(checkid)) {

				} else {

					sendForm4PlanSaveForExistingDistrictCycleYear(tempobj.getDistrict_id(), tempobj.getCycle_id(),
							tempobj.getYear(), tempobj.getCountry_id(), tempobj.getProvince_id(), response);

					return response;
				}

				/*-------------------------------------------*/

				int row = 0;

				String sql1 = "INSERT INTO `dev_action_plan_details`(`date_of_meeting`, `venue_of_meeting`, "
						+ "`chariperson_of_meeting`, `chariperson_of_meeting_others`,`theme_id`, `theme_leader`, `district_id`, `cycle_id`,"
						+ " `financial_year`, `user_id`, `record_created`, `completed`) VALUES (?,?," + "?,?,?,?,?,?,"
						+ "?,?,?,?)";

				KeyHolder keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, tempobj.getMeetingdate());
					sendobj.setMeetingdate(tempobj.getMeetingdate());

					ps.setString(2, tempobj.getMeetingvenue());
					sendobj.setMeetingvenue(tempobj.getMeetingvenue());

					ps.setString(3, tempobj.getChairpersonid());
					sendobj.setChairpersonid(tempobj.getChairpersonid());

					ps.setString(4, tempobj.getOtherchairperson());
					sendobj.setOtherchairperson(tempobj.getOtherchairperson());

					ps.setString(5, tempobj.getThemeid());
					sendobj.setThemeid(tempobj.getThemeid());

					ps.setString(6, tempobj.getThemeleaderofcycle());
					sendobj.setThemeleaderofcycle(tempobj.getThemeleaderofcycle());

					ps.setString(7, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());

					ps.setString(8, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());

					ps.setString(9, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());

					ps.setString(10, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id());

					ps.setString(11, formatedDateTime);
					sendobj.setRecordcreated(tempobj.getRecordcreated());

					ps.setString(12, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());

					return ps;
				}, keyHolder);

				long p_key = keyHolder.getKey().longValue();

				sendobj.setAuto_id("" + p_key);
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(formatedDateTime);
				sendobj.setDatafrom("WEB");

				

				list_primary_send.add(sendobj);

				List<Form4ActionPlanActionPointsBean> list2 = form4plan.getForm4ActionPlanActionPoints();

				for (int x = 0; x < list2.size(); x++) {

					Form4ActionPlanActionPointsBean actionsobj = list2.get(x);

					Form4ActionPlanActionPointsBean sendactionsobj = new Form4ActionPlanActionPointsBean();

					if (tempobj.getDistrict_id().equals(actionsobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(actionsobj.getCycle_id())
							&& tempobj.getYear().equals(actionsobj.getYear())) {
						KeyHolder keyHolder2 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(
									"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
											+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
											+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?,"
											+ "?,?,?,?,?,?,?,?)",
									Statement.RETURN_GENERATED_KEYS);

							ps.setString(1, "" + p_key);
							sendactionsobj.setForm4_id("" + p_key);

							String action_part_web = "Service delivery";

							if ("SERVICED".equals(actionsobj.getAction_part())) {
								action_part_web = "Service delivery";
							}

							if ("FINANCE".equals(actionsobj.getAction_part())) {
								action_part_web = "Finance";
							}

							if ("WORKFORCE".equals(actionsobj.getAction_part())) {
								action_part_web = "Workforce";
							}

							if ("HEALTHINFO".equals(actionsobj.getAction_part())) {
								action_part_web = "Health information";
							}

							if ("POLICYG".equals(actionsobj.getAction_part())) {
								action_part_web = "Policy/governance";
							}

							if ("SUPPLIES".equals(actionsobj.getAction_part())) {
								action_part_web = "Supplies & technology";
							}

							ps.setString(2, "" + action_part_web);
							sendactionsobj.setAction_part(actionsobj.getAction_part());

							String final_actionid = "" + actionsobj.getForm3_actionreq_pkey();

							for (int mycount = 0; mycount < mapping2.size(); mycount++) {

								Form3DefineActionIDDetailsBean mytempmapobj = mapping2.get(mycount);

								if (actionsobj.getForm3_actionreq_pkey().equals(mytempmapobj.getAppsend_actionid())
										&& actionsobj.getDistrict_id().equals(mytempmapobj.getDistrict())
										&& actionsobj.getCycle_id().equals(mytempmapobj.getCycle())
										&& actionsobj.getYear().equals(mytempmapobj.getYear())
										&& "1".equals(mytempmapobj.getForm4planfillcontinously())) {
									final_actionid = mytempmapobj.getWebgen_actionid();
								}
							}

							ps.setString(3, "" + final_actionid);
							sendactionsobj.setForm3_actionreq_pkey(final_actionid);

							String final_stakesid = "" + actionsobj.getResponsible_dept();

							for (int mycount = 0; mycount < mapping3.size(); mycount++) {

								Form2EngagestakeIDDetailsBean mytempmapobj = mapping3.get(mycount);

								
								if (actionsobj.getResponsible_dept().equals(mytempmapobj.getAppsend_stakesid())
										&& actionsobj.getDistrict_id().equals(mytempmapobj.getDistrict())
										&& actionsobj.getCycle_id().equals(mytempmapobj.getCycle())
										&& actionsobj.getYear().equals(mytempmapobj.getYear())
										&& "1".equals(mytempmapobj.getForm4planfillcontinously())) {
									final_stakesid = mytempmapobj.getWebgen_stakesid();
								}
							}

							ps.setString(4, "" + final_stakesid);
							sendactionsobj.setResponsible_dept(final_stakesid);

							ps.setString(5, "" + actionsobj.getPerson_responsible());
							sendactionsobj.setPerson_responsible(actionsobj.getPerson_responsible());

//							String outputDateStr = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+actionsobj.getTimeline();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}

							ps.setString(6, "" + actionsobj.getTimeline());
							sendactionsobj.setTimeline(actionsobj.getTimeline());

							ps.setString(7, "" + actionsobj.getDistrict_id());
							sendactionsobj.setDistrict_id(actionsobj.getDistrict_id());

							ps.setString(8, "" + actionsobj.getCycle_id());
							sendactionsobj.setCycle_id(actionsobj.getCycle_id());

							ps.setString(9, "" + actionsobj.getYear());
							sendactionsobj.setYear(actionsobj.getYear());

							ps.setString(10, "" + actionsobj.getUser_id());
							sendactionsobj.setUser_id(actionsobj.getUser_id());

							ps.setString(11, "" + formatedDateTime);
							sendactionsobj.setRecordcreated(formatedDateTime);

							return ps;
						}, keyHolder2);

						String current_table_pkey = "" + keyHolder2.getKey().longValue();

						sendactionsobj.setAuto_id("" + keyHolder2.getKey().longValue());
						sendactionsobj.setCountry_id(actionsobj.getCountry_id());
						sendactionsobj.setProvince_id(actionsobj.getProvince_id());
						sendactionsobj.setTimestamp(formatedDateTime);
						sendactionsobj.setDatafrom("WEB");

						list_actions_send.add(sendactionsobj);

						/*------------------------Indicators for Actions--------------------------------*/

						List<Form4ActionPlanIndicatorsBean> list3 = form4plan.getForm4ActionPlanIndicators();

						for (int y = 0; y < list3.size(); y++) {

							Form4ActionPlanIndicatorsBean indiobj = list3.get(y);

							Form4ActionPlanIndicatorsBean sendindiobj = new Form4ActionPlanIndicatorsBean();

//							System.out.println("actionsobj.getDistrict_id() = "+actionsobj.getDistrict_id()+",, indiobj.getDistrict_id() = "+indiobj.getDistrict_id());
//							System.out.println("actionsobj.getCycle_id()    = "+actionsobj.getCycle_id()+",, indiobj.getCycle_id() = "+indiobj.getCycle_id());
//							System.out.println("actionsobj.getYear()        = "+actionsobj.getYear()+",, indiobj.getYear()  = "+indiobj.getYear());
//							System.out.println("actionsobj.getForm3_actionreq_pkey() = "+actionsobj.getForm3_actionreq_pkey()+",, indiobj.getForm3_actionreq_pkey() = "+indiobj.getForm3_actionreq_pkey());
//							System.out.println("actionsobj.getForm4_id()  = "+actionsobj.getForm4_id()+",,  indiobj.getForm4_id() = "+indiobj.getForm4_id());

							if (actionsobj.getDistrict_id().equals(indiobj.getDistrict_id())
									&& actionsobj.getCycle_id().equals(indiobj.getCycle_id())
									&& actionsobj.getYear().equals(indiobj.getYear())
									&& actionsobj.getForm3_actionreq_pkey().equals(indiobj.getForm3_actionreq_pkey())
									&& actionsobj.getForm4_id().equals(indiobj.getForm4_id())) {

								KeyHolder keyHolder3 = new GeneratedKeyHolder();

								jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(
											"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
													+ "      `indicator_id` ,  `area_name` , `area_id`, "
													+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
													+ "		?,?,?,?,?,?,?,?,?,?,?)",
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, "" + p_key);
									sendindiobj.setForm4_id("" + p_key);

									String final_actionid = "" + indiobj.getForm3_actionreq_pkey();

									for (int mycount = 0; mycount < mapping2.size(); mycount++) {

										Form3DefineActionIDDetailsBean mytempmapobj = mapping2.get(mycount);

										if (indiobj.getForm3_actionreq_pkey().equals(mytempmapobj.getAppsend_actionid())
												&& indiobj.getDistrict_id().equals(mytempmapobj.getDistrict())
												&& indiobj.getCycle_id().equals(mytempmapobj.getCycle())
												&& indiobj.getYear().equals(mytempmapobj.getYear())
												&& "1".equals(mytempmapobj.getForm4planfillcontinously())) {
											final_actionid = mytempmapobj.getWebgen_actionid();
										}
									}

									ps.setString(2, "" + final_actionid);
									sendindiobj.setForm3_actionreq_pkey("" + final_actionid);

									for (int pos = 0; pos < getlist2.size(); pos++) {

										Menu_Area_Indicator_Object alldataobj = getlist2.get(pos);
										if (alldataobj.getIndicator_id().equals(indiobj.getIndicator_id())) {
											indiobj.setArea_name(alldataobj.getArea_name());
											indiobj.setArea_id(alldataobj.getArea_id());
											indiobj.setIndicator_name(alldataobj.getIndicator_val());
										}
									}

									ps.setString(3, "" + indiobj.getIndicator_name());
									sendindiobj.setIndicator_name("" + indiobj.getIndicator_name());

									ps.setString(4, "");

									ps.setString(5, "" + indiobj.getIndicator_id());
									sendindiobj.setIndicator_id("" + indiobj.getIndicator_id());

									ps.setString(6, "" + indiobj.getArea_name());
									sendindiobj.setArea_name("" + indiobj.getArea_name());

									ps.setString(7, "" + indiobj.getArea_id());
									sendindiobj.setArea_id("" + indiobj.getArea_id());

									ps.setString(8, "" + indiobj.getTarget_value());
									sendindiobj.setTarget_value("" + indiobj.getTarget_value());

									ps.setString(9, "" + indiobj.getDistrict_id());
									sendindiobj.setDistrict_id("" + indiobj.getDistrict_id());

									ps.setString(10, "" + indiobj.getCycle_id());
									sendindiobj.setCycle_id("" + indiobj.getCycle_id());

									ps.setString(11, "" + indiobj.getYear());
									sendindiobj.setYear("" + indiobj.getYear());

									ps.setString(12, "" + indiobj.getUser_id());
									sendindiobj.setUser_id("" + indiobj.getUser_id());

									ps.setString(13, formatedDateTime);
									sendindiobj.setRecordcreated("" + formatedDateTime);

									return ps;
								}, keyHolder3);

								sendindiobj.setAuto_id("" + keyHolder3.getKey().longValue());
								sendindiobj.setCountry_id(indiobj.getCountry_id());
								sendindiobj.setProvince_id(indiobj.getProvince_id());
								sendindiobj.setTimestamp(formatedDateTime);
								sendindiobj.setDatafrom("WEB");
								sendindiobj.setAction_responsible_id(current_table_pkey);

								list_indi_send.add(sendindiobj);

							} // End of If for checking for current district year cycle

						}

						/*------------------------Indicators for Actions---------------------------------*/

					} // If to check if district year cycle is matched for current incoming json

				} // Loop2 Action req form3define

			} // End of If for Insert tables in case of APP

		} // End of Main Loop

		/**************************************/

		/***************************************/

		// Update WEB data started
		for (int index = 0; index < list1.size(); index++) {

			Form4PlanPrimaryTableDataBean tempobj = list1.get(index);

		
			Form4PlanPrimaryTableDataBean sendobj = new Form4PlanPrimaryTableDataBean();

			String form5_sql = "SELECt * FROM `folloup_action_plan_tbl` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
			Object[] form5_param = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(), tempobj.getYear() };

			String form5_p_key = jdbcTemplate.query(form5_sql, form5_param, rs -> {

				String primary_key = "";
				while (rs.next()) {
					primary_key = rs.getString("followup_id");
				}
				/* We can also return any variable-data from here but not used currently */
				return primary_key;
			});

			if ("WEB".equals(tempobj.getDatafrom())) {

				int row = 0;

				String sql1 = " UPDATE `dev_action_plan_details` SET `date_of_meeting`=?, `venue_of_meeting`=?,"
						+ " `chariperson_of_meeting`=?,  `chariperson_of_meeting_others`=?,`theme_leader`=? , `user_id`=?, `completed`=?  where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1);
					ps.setString(1, tempobj.getMeetingdate());
					sendobj.setMeetingdate(tempobj.getMeetingdate());

					ps.setString(2, tempobj.getMeetingvenue());
					sendobj.setMeetingvenue(tempobj.getMeetingvenue());

					ps.setString(3, tempobj.getChairpersonid());
					sendobj.setChairpersonid(tempobj.getChairpersonid());

					ps.setString(4, tempobj.getOtherchairperson());
					sendobj.setOtherchairperson(tempobj.getOtherchairperson());

					ps.setString(5, tempobj.getThemeleaderofcycle());
					sendobj.setThemeleaderofcycle(tempobj.getThemeleaderofcycle());

					ps.setString(6, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id());

					ps.setString(7, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());

					ps.setString(8, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());

					ps.setString(9, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());

					ps.setString(10, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());

					ps.setString(11, tempobj.getAuto_id());
					sendobj.setAuto_id(tempobj.getAuto_id());
					return ps;
				});

				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(tempobj.getRecordcreated());
				sendobj.setDatafrom("WEB");

				/*----------------*/

				String retrieve_sql = "SELECT * from `dev_action_plan_details`   "
						+ "    where `district_id`=? and `cycle_id`=? and `financial_year`=? and `dev_action_id`=?";

				Object[] params_retrieve_sql = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
						tempobj.getYear(), tempobj.getAuto_id() };

				String record_c = jdbcTemplate.query(retrieve_sql, params_retrieve_sql, rs -> {

					String record_data = "";

					while (rs.next()) {
						record_data = rs.getString("record_created");
					}
					/* We can also return any variable-data from here but not used currently */
					return "" + record_data;
				});

				/*-----------------*/

				sendobj.setRecordcreated(record_c);

				list_primary_send.add(sendobj);

				/*-----------------------------ActionReqObj--------------------*/

				List<Form4ActionPlanActionPointsBean> list2 = form4plan.getForm4ActionPlanActionPoints();

				for (int x = 0; x < list2.size(); x++) {

					Form4ActionPlanActionPointsBean actionobj = list2.get(x);

					Form4ActionPlanActionPointsBean sendactionobj = new Form4ActionPlanActionPointsBean();

					if ("WEB".equals(actionobj.getDatafrom()) && tempobj.getAuto_id().equals(actionobj.getForm4_id())) {
						String sql2 = " UPDATE `dev_action_plan_action_points` SET `responsible_dept`=?, "
								+ "`person_responsible`=?, `target`=?,`user_id`=?, `record_created`=?  "
								+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `dev_action_point_id`=?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2);
							ps.setString(1, "" + actionobj.getResponsible_dept());
							sendactionobj.setResponsible_dept(actionobj.getResponsible_dept());

							ps.setString(2, "" + actionobj.getPerson_responsible());
							sendactionobj.setPerson_responsible(actionobj.getPerson_responsible());

//                            String outputDateStr = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+actionobj.getTimeline();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}

							ps.setString(3, "" + actionobj.getTimeline());
							sendactionobj.setTimeline(actionobj.getTimeline());

							ps.setString(4, "" + actionobj.getUser_id());
							sendactionobj.setUser_id(actionobj.getUser_id());

							ps.setString(5, "" + formatedDateTime);
							sendactionobj.setRecordcreated(formatedDateTime);

							ps.setString(6, "" + actionobj.getDistrict_id());
							sendactionobj.setDistrict_id(actionobj.getDistrict_id());

							ps.setString(7, "" + actionobj.getCycle_id());
							sendactionobj.setCycle_id(actionobj.getCycle_id());

							ps.setString(8, "" + actionobj.getYear());
							sendactionobj.setYear(actionobj.getYear());

							ps.setString(9, "" + actionobj.getAuto_id());
							sendactionobj.setAuto_id(actionobj.getAuto_id());
							return ps;
						});

						sendactionobj.setTimestamp(formatedDateTime);
						sendactionobj.setCountry_id(actionobj.getCountry_id());
						sendactionobj.setProvince_id(actionobj.getProvince_id());
						sendactionobj.setForm3_actionreq_pkey(actionobj.getForm3_actionreq_pkey());
						sendactionobj.setForm4_id(actionobj.getForm4_id());
						sendactionobj.setAction_part(actionobj.getAction_part());
						sendactionobj.setDatafrom("WEB");

						list_actions_send.add(sendactionobj);
					}

					else if ("APP".equals(actionobj.getDatafrom())
							&& tempobj.getAuto_id().equals(actionobj.getForm4_id())) {

						KeyHolder keyHolder2 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(
									"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
											+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
											+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?,"
											+ "?,?,?,?,?,?,?,?)",
									Statement.RETURN_GENERATED_KEYS);

							ps.setString(1, "" + tempobj.getAuto_id());
							sendactionobj.setForm4_id(tempobj.getAuto_id());

							String action_part_web = "Service delivery";

							if ("SERVICED".equals(actionobj.getAction_part())) {
								action_part_web = "Service delivery";
							}

							if ("FINANCE".equals(actionobj.getAction_part())) {
								action_part_web = "Finance";
							}

							if ("WORKFORCE".equals(actionobj.getAction_part())) {
								action_part_web = "Workforce";
							}

							if ("HEALTHINFO".equals(actionobj.getAction_part())) {
								action_part_web = "Health information";
							}

							if ("POLICYG".equals(actionobj.getAction_part())) {
								action_part_web = "Policy/governance";
							}

							ps.setString(2, "" + action_part_web);
							sendactionobj.setAction_part(action_part_web);

							ps.setString(3, "" + actionobj.getForm3_actionreq_pkey());
							sendactionobj.setForm3_actionreq_pkey(actionobj.getForm3_actionreq_pkey());

							ps.setString(4, "" + actionobj.getResponsible_dept());
							sendactionobj.setResponsible_dept(actionobj.getResponsible_dept());

							ps.setString(5, "" + actionobj.getPerson_responsible());
							sendactionobj.setPerson_responsible(actionobj.getPerson_responsible());

//                            String outputDateStr = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+actionobj.getTimeline();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}

							ps.setString(6, "" + actionobj.getTimeline());
							sendactionobj.setTimeline(actionobj.getTimeline());

							ps.setString(7, "" + actionobj.getDistrict_id());
							sendactionobj.setDistrict_id(actionobj.getDistrict_id());

							ps.setString(8, "" + actionobj.getCycle_id());
							sendactionobj.setCycle_id(actionobj.getCycle_id());

							ps.setString(9, "" + actionobj.getYear());
							sendactionobj.setYear(actionobj.getYear());

							ps.setString(10, "" + actionobj.getUser_id());
							sendactionobj.setUser_id(actionobj.getUser_id());

							ps.setString(11, "" + actionobj.getRecordcreated());
							sendactionobj.setRecordcreated(actionobj.getRecordcreated());

							return ps;
						}, keyHolder2);

						sendactionobj.setAuto_id("" + keyHolder2.getKey().longValue());
						sendactionobj.setTimestamp(formatedDateTime);
						sendactionobj.setCountry_id(actionobj.getCountry_id());
						sendactionobj.setProvince_id(actionobj.getProvince_id());
						sendactionobj.setForm3_actionreq_pkey(actionobj.getForm3_actionreq_pkey());
						sendactionobj.setForm4_id(actionobj.getForm4_id());
						sendactionobj.setAction_part(actionobj.getAction_part());
						sendactionobj.setDatafrom("WEB");

						list_actions_send.add(sendactionobj);

					}

				}

				/*----------------------------ActionReq------------------------*/

				/*-----------------Indicators Data----------------*/

				List<Form4ActionPlanIndicatorsBean> list3 = form4plan.getForm4ActionPlanIndicators();

				for (int y = 0; y < list3.size(); y++) {

					Form4ActionPlanIndicatorsBean indiobj = list3.get(y);

					Form4ActionPlanIndicatorsBean sendindiobj = new Form4ActionPlanIndicatorsBean();

					/*------------Imp code-----------*/

					
					String sql_get_id = "SELECT * FROM dev_action_plan_action_points  where  `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_name`=? and `dev_action_id`=?";
					Object[] params_sql_get_id = new Object[] { indiobj.getDistrict_id(), indiobj.getCycle_id(),
							indiobj.getYear(), indiobj.getForm3_actionreq_pkey(), indiobj.getForm4_id() };

					String str_get_id = jdbcTemplate.query(sql_get_id, params_sql_get_id, rs -> {

						String keyval = "";
						while (rs.next()) {

							keyval = rs.getString("dev_action_point_id");

						}

						return keyval;
					});

					/*--------End of Imp Code-----------*/

					if ("WEB".equals(indiobj.getDatafrom()) && tempobj.getAuto_id().equals(indiobj.getForm4_id())) {

						/*-------------------------------------------*/

						String sql_check = "SELECT * FROM `dev_action_plan_indicators`  where  `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_id`=? and `indicator_id`=?  and `dev_action_id`=?";
						Object[] params_check = new Object[] { indiobj.getDistrict_id(), indiobj.getCycle_id(),
								indiobj.getYear(), indiobj.getForm3_actionreq_pkey(), indiobj.getIndicator_id(),
								indiobj.getForm4_id() };

						String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

							String keyval = "";
							while (rs.next()) {
								keyval = rs.getString("dev_indicator_id");

								/*----------------------------*/

								String update_sql = "UPDATE `dev_action_plan_indicators` SET `target_value` = ?, `user_id` = ? "
										+ "  WHERE  `district_id`=? and `cycle_id`=?  and  "
										+ " `financial_year`=? and `action_point_id`=? and `indicator_id`=?  and `dev_action_id`=?";

								jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(update_sql);
									ps.setString(1, "" + indiobj.getTarget_value());
									ps.setString(2, "" + indiobj.getUser_id());
									ps.setString(3, "" + indiobj.getDistrict_id());
									ps.setString(4, "" + indiobj.getCycle_id());
									ps.setString(5, "" + indiobj.getYear());
									ps.setString(6, "" + indiobj.getForm3_actionreq_pkey());
									ps.setString(7, "" + indiobj.getIndicator_id());
									ps.setString(8, "" + indiobj.getForm4_id());

									return ps;
								});

								/*------------------------------*/

								sendindiobj.setForm4_id(indiobj.getForm4_id());
								sendindiobj.setForm3_actionreq_pkey(indiobj.getForm3_actionreq_pkey());
								sendindiobj.setIndicator_name(indiobj.getIndicator_name());
								sendindiobj.setIndicator_id(indiobj.getIndicator_id());
								sendindiobj.setArea_name(indiobj.getArea_name());
								sendindiobj.setArea_id(indiobj.getArea_id());
								sendindiobj.setTarget_value(indiobj.getTarget_value());

								sendindiobj.setDistrict_id(indiobj.getDistrict_id());
								sendindiobj.setCycle_id(indiobj.getCycle_id());
								sendindiobj.setYear(indiobj.getYear());
								sendindiobj.setUser_id(indiobj.getUser_id());
								sendindiobj.setRecordcreated(formatedDateTime);
								sendindiobj.setAuto_id(keyval);
								sendindiobj.setTimestamp(formatedDateTime);
								sendindiobj.setCountry_id(indiobj.getCountry_id());
								sendindiobj.setProvince_id(indiobj.getProvince_id());
								sendindiobj.setForm4_id(indiobj.getForm4_id());
								sendindiobj.setDatafrom("WEB");
							}
							/* We can also return any variable-data from here but not used currently */
							return keyval;
						});

						if (!("".equals(checkid))) {

							if ("".equals(str_get_id)) {
								str_get_id = indiobj.getAction_responsible_id();
							}

							sendindiobj.setAction_responsible_id(str_get_id);
							list_indi_send.add(sendindiobj);
						} else {

							String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
									+ "      `indicator_id` ,  `area_name` , `area_id`, "
									+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
									+ "		?,?,?,?,?,?,?,?,?,?,?)";

							KeyHolder keyHolder3 = new GeneratedKeyHolder();

							jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql3,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + indiobj.getForm4_id());
								sendindiobj.setForm4_id(indiobj.getForm4_id());

								ps.setString(2, "" + indiobj.getForm3_actionreq_pkey());
								sendindiobj.setForm3_actionreq_pkey(indiobj.getForm3_actionreq_pkey());

								/*--------------------------------------------*/

								String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
										+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
										+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
								Object[] params_indi_name_sql = new Object[] { indiobj.getIndicator_id() };

								jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

									while (rs.next()) {
										sendindiobj.setIndicator_name(rs.getString("indicator_name"));
										sendindiobj.setArea_name(rs.getString("area_name"));

										ps.setString(3, "" + rs.getString("indicator_name"));
										// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

										ps.setString(4, "" + "");
										// sendindiobj.setForm4_id(indiobj.getForm4_id());

										ps.setString(5, "" + indiobj.getIndicator_id());
										sendindiobj.setIndicator_id(indiobj.getIndicator_id());

										ps.setString(6, "" + rs.getString("area_name"));
										// sendindiobj.setArea_name(indiobj.getArea_name());
									}
									/* We can also return any variable-data from here but not used currently */
									return "";
								});

								/*--------------------------------------------*/

								ps.setString(7, "" + indiobj.getArea_id());
								sendindiobj.setArea_id(indiobj.getArea_id());

								ps.setString(8, "" + indiobj.getTarget_value());
								sendindiobj.setTarget_value(indiobj.getTarget_value());

								ps.setString(9, indiobj.getDistrict_id());
								sendindiobj.setDistrict_id(indiobj.getDistrict_id());

								ps.setString(10, indiobj.getCycle_id());
								sendindiobj.setCycle_id(indiobj.getCycle_id());

								ps.setString(11, indiobj.getYear());
								sendindiobj.setYear(indiobj.getYear());

								ps.setString(12, indiobj.getUser_id());
								sendindiobj.setUser_id(indiobj.getUser_id());

								ps.setString(13, formatedDateTime);
								sendindiobj.setRecordcreated(formatedDateTime);
								return ps;
							}, keyHolder3);

							sendindiobj.setAuto_id("" + keyHolder3.getKey().longValue());
							sendindiobj.setTimestamp(formatedDateTime);
							sendindiobj.setCountry_id(indiobj.getCountry_id());
							sendindiobj.setProvince_id(indiobj.getProvince_id());
							sendindiobj.setForm4_id(indiobj.getForm4_id());
							sendindiobj.setDatafrom("WEB");

							if ("".equals(str_get_id)) {
								str_get_id = indiobj.getAction_responsible_id();
							}
							sendindiobj.setAction_responsible_id(str_get_id);
							list_indi_send.add(sendindiobj);

							/**********************/

							if ("".equals(form5_p_key)) {

							} else {

						
								String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
										+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
										+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

								KeyHolder keyHolder5 = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sql5,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, "" + form5_p_key);

									String final_actionid = "" + indiobj.getForm3_actionreq_pkey();

									ps.setString(2, "" + final_actionid);

									ps.setString(3, "" + +keyHolder3.getKey().longValue());

									ps.setString(4, "0");

									ps.setString(5, "" + indiobj.getDistrict_id());

									ps.setString(6, "" + indiobj.getCycle_id());

									ps.setString(7, "" + indiobj.getYear());

									ps.setString(8, "" + indiobj.getUser_id());

									ps.setString(9, "" + formatedDateTime);

									return ps;
								}, keyHolder5);

							}

							/**********************/
						}

						/*-------------------------------------------*/
					}

					if ("APP".equals(indiobj.getDatafrom()) && tempobj.getAuto_id().equals(indiobj.getForm4_id()))
					// Do insert Indicators
					{
						/*-------------------------------------------*/

						String sql3 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)";

						KeyHolder keyHolder4 = new GeneratedKeyHolder();

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + indiobj.getForm4_id());
							sendindiobj.setForm4_id(indiobj.getForm4_id());

							ps.setString(2, "" + indiobj.getForm3_actionreq_pkey());
							sendindiobj.setForm3_actionreq_pkey(indiobj.getForm3_actionreq_pkey());

							/*--------------------------------------------*/

							String indi_name_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,"
									+ "i.id as `indicator_id`,i.indicator_name,	 i.definition, i.numerator, i.denominator, "
									+ "i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id where i.id=?";
							Object[] params_indi_name_sql = new Object[] { indiobj.getIndicator_id() };

							jdbcTemplate.query(indi_name_sql, params_indi_name_sql, rs -> {

								while (rs.next()) {
									sendindiobj.setIndicator_name(rs.getString("indicator_name"));

									ps.setString(3, "" + rs.getString("indicator_name"));
									sendindiobj.setArea_name(rs.getString("area_name"));

									ps.setString(4, "" + "");

									ps.setString(5, "" + indiobj.getIndicator_id());
									sendindiobj.setIndicator_id(indiobj.getIndicator_id());

									ps.setString(6, "" + rs.getString("area_name"));
									// sendindiobj.setArea_name(indiobj.getArea_name());
								}
								/* We can also return any variable-data from here but not used currently */
								return "";
							});

							/*--------------------------------------------*/

							// ps.setString(3, "" + indiobj.getIndicator_name());
							// sendindiobj.setIndicator_name(indiobj.getIndicator_name());

							ps.setString(7, "" + indiobj.getArea_id());
							sendindiobj.setArea_id(indiobj.getArea_id());

							ps.setString(8, "" + indiobj.getTarget_value());
							sendindiobj.setTarget_value(indiobj.getTarget_value());

							ps.setString(9, indiobj.getDistrict_id());
							sendindiobj.setDistrict_id(indiobj.getDistrict_id());

							ps.setString(10, indiobj.getCycle_id());
							sendindiobj.setCycle_id(indiobj.getCycle_id());

							ps.setString(11, indiobj.getYear());
							sendindiobj.setYear(indiobj.getYear());

							ps.setString(12, indiobj.getUser_id());
							sendindiobj.setUser_id(indiobj.getUser_id());

							ps.setString(13, formatedDateTime);
							sendindiobj.setRecordcreated(formatedDateTime);
							return ps;
						}, keyHolder4);

						sendindiobj.setAuto_id("" + keyHolder4.getKey().longValue());
						sendindiobj.setTimestamp(formatedDateTime);
						sendindiobj.setCountry_id(indiobj.getCountry_id());
						sendindiobj.setProvince_id(indiobj.getProvince_id());
						sendindiobj.setForm4_id(indiobj.getForm4_id());
						sendindiobj.setDatafrom("WEB");

						if ("".equals(str_get_id)) {
							str_get_id = indiobj.getAction_responsible_id();
						}

						sendindiobj.setAction_responsible_id(str_get_id);

						list_indi_send.add(sendindiobj);

						/**********************/
						if ("".equals(form5_p_key)) {

						} else {

				
							String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

							KeyHolder keyHolder5 = new GeneratedKeyHolder();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + form5_p_key);

								String final_actionid = "" + indiobj.getForm3_actionreq_pkey();

								ps.setString(2, "" + final_actionid);

								ps.setString(3, "" + +keyHolder4.getKey().longValue());

								ps.setString(4, "0");

								ps.setString(5, "" + indiobj.getDistrict_id());

								ps.setString(6, "" + indiobj.getCycle_id());

								ps.setString(7, "" + indiobj.getYear());

								ps.setString(8, "" + indiobj.getUser_id());

								ps.setString(9, "" + formatedDateTime);

								return ps;
							}, keyHolder5);

						}

						/**********************/

						/*-------------------------------------------*/
					}

				} // indicator for loop

				/*----------------Indicators Data----------------*/
			} // Main Web data update if condition

		} // Main Web data update for loop

		response.setForm4Plan(list_primary_send);
		response.setForm4ActionPlanActionPoints(list_actions_send);
		response.setForm4ActionPlanIndicators(list_indi_send);
		response.setError_code("200");
		response.setMessage("Form4 Plan All Cycles and Year for given district");

		return response;
	}

	public SavedForm4PlanResponse saveForm4PlanToDb(Form4Plan model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

	
		// INSERT INTO `dev_action_plan_details`(`date_of_meeting`, `venue_of_meeting`,
		// `chariperson_of_meeting`,
//		`theme_id`, `theme_leader`, `district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`) VALUES 
//(:date_of_meeting,:venue_of_meeting,:chariperson_of_meeting,:theme_id,:theme_leader,:district_id,:cycle_id,
//		:financial_year,:user_id,:record_created)

		int row = 0;

		String sql1 = "INSERT INTO `dev_action_plan_details`(`date_of_meeting`, `venue_of_meeting`, "
				+ "`chariperson_of_meeting`, `chariperson_of_meeting_others`,`theme_id`, `theme_leader`, `district_id`, `cycle_id`,"
				+ " `financial_year`, `user_id`, `record_created`, `completed`) VALUES (?,?," + "?,?,?,?,?,?,"
				+ "?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getDate_of_meeting());
			ps.setString(2, model.getVenue_of_meeting());
			ps.setString(3, model.getChariperson_of_meeting());
			ps.setString(4, model.getChariperson_of_meeting_others());
			ps.setString(5, model.getTheme_id());
			ps.setString(6, model.getTheme_leader());
			ps.setString(7, model.getDistrict());
			ps.setString(8, model.getCycle());
			ps.setString(9, model.getYear());
			ps.setString(10, model.getUserid());
			ps.setString(11, formatedDateTime);
			ps.setString(12, model.getCompleted());

			return ps;
		}, keyHolder);

		// ResultSet rs = ps.getGeneratedKeys();

		long p_key = keyHolder.getKey().longValue();


		if ((model.getSource()).equals(Constants.OFFLINE_SOURCE)) {

			List<String> list = form4PlanSortedIdList(model.getDistrict(), model.getCycle(), model.getYear());
			
			List<String> serviceActionList = form3DefineActionSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Service delivery");
			List<String> workforceActionList = form3DefineActionSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Workforce");
			List<String> supplyActionList = form3DefineActionSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Supplies & technology");
			List<String> healthInformationActionList = form3DefineActionSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Health information");
			List<String> financeActionList = form3DefineActionSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Finance");
			List<String> policyActionList = form3DefineActionSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Policy/governance");
			
			List<String> serviceActionRequiredList = form3DefineActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Service delivery");
			List<String> workforceActionRequiredList = form3DefineActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Workforce");
			List<String> supplyActionRequiredList = form3DefineActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Supplies & technology");
			List<String> healthInformationActionRequiredList = form3DefineActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Health information");
			List<String> financeActionRequiredList = form3DefineActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Finance");
			List<String> policyActionRequiredList = form3DefineActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Policy/governance");			
			
			/*
			System.out.println("serviceActionList : "+serviceActionList);
			System.out.println("workforceActionList : "+workforceActionList);
			System.out.println("supplyActionList : "+supplyActionList);
			System.out.println("healthInformationActionList : "+healthInformationActionList);
			System.out.println("financeActionList : "+financeActionList);
			System.out.println("policyActionList : "+policyActionList);
			*/

			for (Form4PlanCommonObject serviceList : model.getService_action_arr()) {	
				try {
					
				serviceList.setAction_req_id(serviceActionRequiredList.get(Integer.valueOf(serviceList.getAction_req_id())));				
				serviceList.setSel_stakeholder(list.get(Integer.valueOf(serviceList.getSel_stakeholder())));
				
				try {
					serviceList.setForm_3_1_action_part_engagement_nm_details_pkey(serviceActionList.get(Integer.valueOf(serviceList.getForm_3_1_action_part_engagement_nm_details_pkey())));
				}catch(Exception e) {}
				
				}catch(Exception e) {
					LOGGER.error("Error in saveForm4PlanToDb method in service action array : " + e);
				}
			}
			 
			for (Form4PlanCommonObject workforceList : model.getWorkforce_action_arr()) {				
				try {
								
				workforceList.setAction_req_id(workforceActionRequiredList.get(Integer.valueOf(workforceList.getAction_req_id())));				
				workforceList.setSel_stakeholder(list.get(Integer.valueOf(workforceList.getSel_stakeholder())));
				
				try {
					workforceList.setForm_3_1_action_part_engagement_nm_details_pkey(workforceActionList.get(Integer.valueOf(workforceList.getForm_3_1_action_part_engagement_nm_details_pkey())));
				}catch(Exception e) {}
				
				}catch(Exception e) {
					LOGGER.error("Error in saveForm4PlanToDb method in work force action array : " + e);
				}
			}

			for (Form4PlanCommonObject suppliesList : model.getSupplies_action_arr()) {
				try {
					
				
				suppliesList.setAction_req_id(supplyActionRequiredList.get(Integer.valueOf(suppliesList.getAction_req_id())));				
				suppliesList.setSel_stakeholder(list.get(Integer.valueOf(suppliesList.getSel_stakeholder())));
				
				try {
					suppliesList.setForm_3_1_action_part_engagement_nm_details_pkey(supplyActionList.get(Integer.valueOf(suppliesList.getForm_3_1_action_part_engagement_nm_details_pkey())));
				}catch(Exception e) {}
				
				}catch(Exception e) {
					LOGGER.error("Error in saveForm4PlanToDb method in supply action array : " + e);
				}
			}

			for (Form4PlanCommonObject healthList : model.getHealth_action_arr()) {
				try {
								
				healthList.setAction_req_id(healthInformationActionRequiredList.get(Integer.valueOf(healthList.getAction_req_id())));				
				healthList.setSel_stakeholder(list.get(Integer.valueOf(healthList.getSel_stakeholder())));
				
				try {
					healthList.setForm_3_1_action_part_engagement_nm_details_pkey(healthInformationActionList.get(Integer.valueOf(healthList.getForm_3_1_action_part_engagement_nm_details_pkey())));
				}catch(Exception e) {}
				
				}catch(Exception e) {
					LOGGER.error("Error in saveForm4PlanToDb method in health action array : " + e);
				}
			}

			for (Form4PlanCommonObject financeList : model.getFinance_action_arr()) {
				try {
					
				financeList.setAction_req_id(financeActionRequiredList.get(Integer.valueOf(financeList.getAction_req_id())));				
				financeList.setSel_stakeholder(list.get(Integer.valueOf(financeList.getSel_stakeholder())));
				
				try {
					financeList.setForm_3_1_action_part_engagement_nm_details_pkey(financeActionList.get(Integer.valueOf(financeList.getForm_3_1_action_part_engagement_nm_details_pkey())));
				}catch(Exception e) {}
				
				}catch(Exception e) {
					LOGGER.error("Error in saveForm4PlanToDb method in finance action array : " + e);
				}
			}
			
			for (Form4PlanCommonObject policyList : model.getPolicy_action_arr()) {
				try {
					
				policyList.setAction_req_id(policyActionRequiredList.get(Integer.valueOf(policyList.getAction_req_id())));				
				policyList.setSel_stakeholder(list.get(Integer.valueOf(policyList.getSel_stakeholder())));
				
				try {
					policyList.setForm_3_1_action_part_engagement_nm_details_pkey(policyActionList.get(Integer.valueOf(policyList.getForm_3_1_action_part_engagement_nm_details_pkey())));
				}catch(Exception e) {}
				
				}catch(Exception e) {
					LOGGER.error("Error in saveForm4PlanToDb method in policy action array : " + e);
				}
			}
		}

		List<Form4PlanCommonObject> list1_service = model.getService_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
						+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
						+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + "Service delivery");
						ps.setString(3, "" + list1_service.get(i).getAction_req_id());
						ps.setString(4, "" + list1_service.get(i).getSel_stakeholder());
						ps.setString(5, "" + list1_service.get(i).getPerson_responsible());
						ps.setString(6, "" + list1_service.get(i).getTimeline());
						ps.setString(7, model.getDistrict());
						ps.setString(8, model.getCycle());
						ps.setString(9, model.getYear());
						ps.setString(10, model.getUserid());
						ps.setString(11, formatedDateTime);
					}

					public int getBatchSize() {
						return list1_service.size();
					}

				});

		for (int index = 0; index < list1_service.size(); index++) {

			List<Form4PlanSelIndicatorsList> indi_list = list1_service.get(index).getSel_indicators();

			int pos = index;

			if (indi_list.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + p_key);
								ps.setString(2, "" + list1_service.get(pos).getAction_req_id());
								ps.setString(3, "" + indi_list.get(i).getIndicator_val());
								ps.setString(4, "" + "");

								ps.setString(5, "" + indi_list.get(i).getIndicator_id());
								ps.setString(6, "" + indi_list.get(i).getArea_name());
								ps.setString(7, "" + indi_list.get(i).getArea_id());

								ps.setString(8, "" + indi_list.get(i).getTarget());
								ps.setString(9, model.getDistrict());
								ps.setString(10, model.getCycle());
								ps.setString(11, model.getYear());
								ps.setString(12, model.getUserid());
								ps.setString(13, formatedDateTime);
							}

							public int getBatchSize() {
								return list1_service.get(pos).getSel_indicators().size();
							}

						});
			}

		}

//		String s2 = "SELECT * FROM `form_3_1_action_part_engagement_action_req`  where district_id=? and cycle_id=? and financial_year=? and  action_part=? and action_required != '' ;";
//		Object[] params2 = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(), "Workforce" };
//
//		String action_point_name2 = null;
//
//		action_point_name2 = jdbcTemplate.query(s2, params2, rs -> {
//
//			String tempstr = "";
//			while (rs.next()) {
//
//				tempstr = rs.getString("action_req_id");
//				;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "" + tempstr;
//		});
//
//		model.setWorkforce_action_point_name(action_point_name2);
//
//		String sql3 = "INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
//				+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
//				+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql3);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getWorkforce_action_part());
//			ps.setString(3, model.getWorkforce_action_point_name());
//			ps.setString(4, model.getWorkforce_responsible_dept());
//			ps.setString(5, model.getWorkforce_person_responsible());
//			ps.setString(6, model.getWorkforce_target_value());
//			ps.setString(7, model.getDistrict());
//			ps.setString(8, model.getCycle());
//			ps.setString(9, model.getYear());
//			ps.setString(10, model.getUserid());
//			ps.setString(11, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_action_points service" + row);
//
//		String sql33 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//				+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//				+ "		?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql33);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getWorkforce_action_point_name());
//			ps.setString(3, model.getWorkforce_indicator_name());
//			ps.setString(4, model.getWorkforce_description_of_indicator());
//			ps.setString(5, model.getWorkforce_target_value());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_indicators service" + row);

		List<Form4PlanCommonObject> list2_workforce = model.getWorkforce_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
						+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
						+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + "Workforce");
						ps.setString(3, "" + list2_workforce.get(i).getAction_req_id());
						ps.setString(4, "" + list2_workforce.get(i).getSel_stakeholder());
						ps.setString(5, "" + list2_workforce.get(i).getPerson_responsible());
						ps.setString(6, "" + list2_workforce.get(i).getTimeline());
						ps.setString(7, model.getDistrict());
						ps.setString(8, model.getCycle());
						ps.setString(9, model.getYear());
						ps.setString(10, model.getUserid());
						ps.setString(11, formatedDateTime);
					}

					public int getBatchSize() {
						return list2_workforce.size();
					}

				});

		for (int index = 0; index < list2_workforce.size(); index++) {

			List<Form4PlanSelIndicatorsList> indi_list = list2_workforce.get(index).getSel_indicators();

			int pos = index;

			if (indi_list.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + p_key);
								ps.setString(2, "" + list2_workforce.get(pos).getAction_req_id());
								ps.setString(3, "" + indi_list.get(i).getIndicator_val());
								ps.setString(4, "" + "");

								ps.setString(5, "" + indi_list.get(i).getIndicator_id());
								ps.setString(6, "" + indi_list.get(i).getArea_name());
								ps.setString(7, "" + indi_list.get(i).getArea_id());

								ps.setString(8, "" + indi_list.get(i).getTarget());
								ps.setString(9, model.getDistrict());
								ps.setString(10, model.getCycle());
								ps.setString(11, model.getYear());
								ps.setString(12, model.getUserid());
								ps.setString(13, formatedDateTime);
							}

							public int getBatchSize() {
								return list2_workforce.get(pos).getSel_indicators().size();
							}

						});
			}

		}

//		String s3 = "SELECT * FROM `form_3_1_action_part_engagement_action_req`  where district_id=? and cycle_id=? and financial_year=? and  action_part=? and action_required != '' ;";
//		Object[] params3 = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
//				"Supplies & technology" };
//
//		String action_point_name3 = null;
//
//		action_point_name3 = jdbcTemplate.query(s3, params3, rs -> {
//
//			String tempstr = "";
//			while (rs.next()) {
//
//				tempstr = rs.getString("action_req_id");
//				;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "" + tempstr;
//		});
//
//		model.setSupplies_action_point_name(action_point_name3);
//
//		String sql4 = "INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
//				+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
//				+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql4);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getSupplies_action_part());
//			ps.setString(3, model.getSupplies_action_point_name());
//			ps.setString(4, model.getSupplies_responsible_dept());
//			ps.setString(5, model.getSupplies_person_responsible());
//			ps.setString(6, model.getSupplies_target_value());
//			ps.setString(7, model.getDistrict());
//			ps.setString(8, model.getCycle());
//			ps.setString(9, model.getYear());
//			ps.setString(10, model.getUserid());
//			ps.setString(11, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_action_points service" + row);
//
//		String sql44 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//				+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//				+ "		?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql44);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getSupplies_action_point_name());
//			ps.setString(3, model.getSupplies_indicator_name());
//			ps.setString(4, model.getSupplies_description_of_indicator());
//			ps.setString(5, model.getSupplies_target_value());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_indicators service" + row);

		List<Form4PlanCommonObject> list3_supplies = model.getSupplies_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
						+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
						+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + "Supplies & technology");
						ps.setString(3, "" + list3_supplies.get(i).getAction_req_id());
						ps.setString(4, "" + list3_supplies.get(i).getSel_stakeholder());
						ps.setString(5, "" + list3_supplies.get(i).getPerson_responsible());
						ps.setString(6, "" + list3_supplies.get(i).getTimeline());
						ps.setString(7, model.getDistrict());
						ps.setString(8, model.getCycle());
						ps.setString(9, model.getYear());
						ps.setString(10, model.getUserid());
						ps.setString(11, formatedDateTime);
					}

					public int getBatchSize() {
						return list3_supplies.size();
					}

				});

		for (int index = 0; index < list3_supplies.size(); index++) {

			List<Form4PlanSelIndicatorsList> indi_list = list3_supplies.get(index).getSel_indicators();

			int pos = index;

			if (indi_list.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "

								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + p_key);
								ps.setString(2, "" + list3_supplies.get(pos).getAction_req_id());
								ps.setString(3, "" + indi_list.get(i).getIndicator_val());
								ps.setString(4, "" + "");

								ps.setString(5, "" + indi_list.get(i).getIndicator_id());
								ps.setString(6, "" + indi_list.get(i).getArea_name());
								ps.setString(7, "" + indi_list.get(i).getArea_id());

								ps.setString(8, "" + indi_list.get(i).getTarget());
								ps.setString(9, model.getDistrict());
								ps.setString(10, model.getCycle());
								ps.setString(11, model.getYear());
								ps.setString(12, model.getUserid());
								ps.setString(13, formatedDateTime);
							}

							public int getBatchSize() {
								return list3_supplies.get(pos).getSel_indicators().size();
							}

						});
			}

		}

//		String s4 = "SELECT * FROM `form_3_1_action_part_engagement_action_req`  where district_id=? and cycle_id=? and financial_year=? and  action_part=? and action_required != '' ;";
//		Object[] params4 = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(),
//				"Health information" };
//
//		String action_point_name4 = null;
//
//		action_point_name4 = jdbcTemplate.query(s4, params4, rs -> {
//
//			String tempstr = "";
//			while (rs.next()) {
//
//				tempstr = rs.getString("action_req_id");
//				;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "" + tempstr;
//		});
//
//		model.setHealth_action_point_name(action_point_name4);
//
//		String sql5 = "INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
//				+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
//				+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql5);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getHealth_action_part());
//			ps.setString(3, model.getHealth_action_point_name());
//			ps.setString(4, model.getHealth_responsible_dept());
//			ps.setString(5, model.getHealth_person_responsible());
//			ps.setString(6, model.getHealth_target_value());
//			ps.setString(7, model.getDistrict());
//			ps.setString(8, model.getCycle());
//			ps.setString(9, model.getYear());
//			ps.setString(10, model.getUserid());
//			ps.setString(11, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_action_points service" + row);
//
//		String sql55 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//				+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//				+ "		?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql55);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getHealth_action_point_name());
//			ps.setString(3, model.getHealth_indicator_name());
//			ps.setString(4, model.getHealth_description_of_indicator());
//			ps.setString(5, model.getHealth_target_value());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_indicators service" + row);

		List<Form4PlanCommonObject> list4_health = model.getHealth_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
						+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
						+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + "Health information");
						ps.setString(3, "" + list4_health.get(i).getAction_req_id());
						ps.setString(4, "" + list4_health.get(i).getSel_stakeholder());
						ps.setString(5, "" + list4_health.get(i).getPerson_responsible());
						ps.setString(6, "" + list4_health.get(i).getTimeline());
						ps.setString(7, model.getDistrict());
						ps.setString(8, model.getCycle());
						ps.setString(9, model.getYear());
						ps.setString(10, model.getUserid());
						ps.setString(11, formatedDateTime);
					}

					public int getBatchSize() {
						return list4_health.size();
					}

				});

		for (int index = 0; index < list4_health.size(); index++) {

			List<Form4PlanSelIndicatorsList> indi_list = list4_health.get(index).getSel_indicators();

			int pos = index;

			if (indi_list.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)",

						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + p_key);
								ps.setString(2, "" + list4_health.get(pos).getAction_req_id());
								ps.setString(3, "" + indi_list.get(i).getIndicator_val());
								ps.setString(4, "" + "");

								ps.setString(5, "" + indi_list.get(i).getIndicator_id());
								ps.setString(6, "" + indi_list.get(i).getArea_name());
								ps.setString(7, "" + indi_list.get(i).getArea_id());

								ps.setString(8, "" + indi_list.get(i).getTarget());
								ps.setString(9, model.getDistrict());
								ps.setString(10, model.getCycle());
								ps.setString(11, model.getYear());
								ps.setString(12, model.getUserid());
								ps.setString(13, formatedDateTime);
							}

							public int getBatchSize() {
								return list4_health.get(pos).getSel_indicators().size();
							}

						});
			}

		}

//		String s5 = "SELECT * FROM `form_3_1_action_part_engagement_action_req`  where district_id=? and cycle_id=? and financial_year=? and  action_part=? and action_required != '' ;";
//		Object[] params5 = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(), "Finance" };
//
//		String action_point_name5 = null;
//
//		action_point_name5 = jdbcTemplate.query(s5, params5, rs -> {
//
//			String tempstr = "";
//			while (rs.next()) {
//
//				tempstr = rs.getString("action_req_id");
//				;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "" + tempstr;
//		});
//
//		model.setFinance_action_point_name(action_point_name5);
//
//		String sql6 = "INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
//				+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
//				+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql6);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getFinance_action_part());
//			ps.setString(3, model.getFinance_action_point_name());
//			ps.setString(4, model.getFinance_responsible_dept());
//			ps.setString(5, model.getFinance_person_responsible());
//			ps.setString(6, model.getFinance_target_value());
//			ps.setString(7, model.getDistrict());
//			ps.setString(8, model.getCycle());
//			ps.setString(9, model.getYear());
//			ps.setString(10, model.getUserid());
//			ps.setString(11, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_action_points service" + row);
//
//		String sql66 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//				+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//				+ "		?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql66);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getFinance_action_point_name());
//			ps.setString(3, model.getFinance_indicator_name());
//			ps.setString(4, model.getFinance_description_of_indicator());
//			ps.setString(5, model.getFinance_target_value());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//
//			return ps;
//		});

		List<Form4PlanCommonObject> list5_finance = model.getFinance_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
						+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
						+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + "Finance");
						ps.setString(3, "" + list5_finance.get(i).getAction_req_id());
						ps.setString(4, "" + list5_finance.get(i).getSel_stakeholder());
						ps.setString(5, "" + list5_finance.get(i).getPerson_responsible());
						ps.setString(6, "" + list5_finance.get(i).getTimeline());
						ps.setString(7, model.getDistrict());
						ps.setString(8, model.getCycle());
						ps.setString(9, model.getYear());
						ps.setString(10, model.getUserid());
						ps.setString(11, formatedDateTime);
					}

					public int getBatchSize() {
						return list5_finance.size();
					}

				});

		for (int index = 0; index < list5_finance.size(); index++) {

			List<Form4PlanSelIndicatorsList> indi_list = list5_finance.get(index).getSel_indicators();

			int pos = index;

			if (indi_list.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + p_key);
								ps.setString(2, "" + list5_finance.get(pos).getAction_req_id());
								ps.setString(3, "" + indi_list.get(i).getIndicator_val());
								ps.setString(4, "" + "");

								ps.setString(5, "" + indi_list.get(i).getIndicator_id());
								ps.setString(6, "" + indi_list.get(i).getArea_name());
								ps.setString(7, "" + indi_list.get(i).getArea_id());

								ps.setString(8, "" + indi_list.get(i).getTarget());
								ps.setString(9, model.getDistrict());
								ps.setString(10, model.getCycle());
								ps.setString(11, model.getYear());
								ps.setString(12, model.getUserid());
								ps.setString(13, formatedDateTime);
							}

							public int getBatchSize() {
								return list5_finance.get(pos).getSel_indicators().size();
							}

						});
			}

		}

//		String s6 = "SELECT * FROM `form_3_1_action_part_engagement_action_req`  where district_id=? and cycle_id=? and financial_year=? and  action_part=? and action_required != '' ;";
//		Object[] params6 = new Object[] { model.getDistrict(), model.getCycle(), model.getYear(), "Policy/governance" };
//
//		String action_point_name6 = null;
//
//		action_point_name6 = jdbcTemplate.query(s6, params6, rs -> {
//
//			String tempstr = "";
//			while (rs.next()) {
//
//				tempstr = rs.getString("action_req_id");
//				;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "" + tempstr;
//		});
//
//		model.setPolicy_action_point_name(action_point_name6);
//
//		String sql7 = "INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
//				+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
//				+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql7);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getPolicy_action_part());
//			ps.setString(3, model.getPolicy_action_point_name());
//			ps.setString(4, model.getPolicy_responsible_dept());
//			ps.setString(5, model.getPolicy_person_responsible());
//			ps.setString(6, model.getPolicy_target_value());
//			ps.setString(7, model.getDistrict());
//			ps.setString(8, model.getCycle());
//			ps.setString(9, model.getYear());
//			ps.setString(10, model.getUserid());
//			ps.setString(11, formatedDateTime);
//
//			return ps;
//		});
//
//		System.out.println("Total rows updated   in   dev_action_plan_action_points service" + row);
//
//		String sql77 = "INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
//				+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
//				+ "		?,?,?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql77);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getPolicy_action_point_name());
//			ps.setString(3, model.getPolicy_indicator_name());
//			ps.setString(4, model.getPolicy_description_of_indicator());
//			ps.setString(5, model.getPolicy_target_value());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//
//			return ps;
//		});

		List<Form4PlanCommonObject> list6_policy = model.getPolicy_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `dev_action_plan_action_points`(`dev_action_id`, `action_part`, `action_point_name`, "
						+ "`responsible_dept`, `person_responsible`, `target`,`district_id`, `cycle_id`, "
						+ "`financial_year`,  `user_id`, `record_created`) values(?,?,?," + "?,?,?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + "Policy/governance");
						ps.setString(3, "" + list6_policy.get(i).getAction_req_id());
						ps.setString(4, "" + list6_policy.get(i).getSel_stakeholder());
						ps.setString(5, "" + list6_policy.get(i).getPerson_responsible());
						ps.setString(6, "" + list6_policy.get(i).getTimeline());
						ps.setString(7, model.getDistrict());
						ps.setString(8, model.getCycle());
						ps.setString(9, model.getYear());
						ps.setString(10, model.getUserid());
						ps.setString(11, formatedDateTime);
					}

					public int getBatchSize() {
						return list6_policy.size();
					}

				});

		for (int index = 0; index < list6_policy.size(); index++) {

			List<Form4PlanSelIndicatorsList> indi_list = list6_policy.get(index).getSel_indicators();

			int pos = index;

			if (indi_list.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `dev_action_plan_indicators`(`dev_action_id`,`action_point_id`, `indicator_name`, `description_of_indicator`, "
								+ "      `indicator_id` ,  `area_name` , `area_id`, "
								+ "		`target_value`, `district_id`, `cycle_id`, `financial_year`,  `user_id`, `record_created`) values(?,?,"
								+ "		?,?,?,?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							// indicator_id , area_name , area_id

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + p_key);
								ps.setString(2, "" + list6_policy.get(pos).getAction_req_id());
								ps.setString(3, "" + indi_list.get(i).getIndicator_val());
								ps.setString(4, "" + "");

								ps.setString(5, "" + indi_list.get(i).getIndicator_id());
								ps.setString(6, "" + indi_list.get(i).getArea_name());
								ps.setString(7, "" + indi_list.get(i).getArea_id());

								ps.setString(8, "" + indi_list.get(i).getTarget());
								ps.setString(9, model.getDistrict());
								ps.setString(10, model.getCycle());
								ps.setString(11, model.getYear());
								ps.setString(12, model.getUserid());
								ps.setString(13, formatedDateTime);
							}

							public int getBatchSize() {
								return list6_policy.get(pos).getSel_indicators().size();
							}

						});
			}

		}

		SavedForm4PlanResponse responseobj = new SavedForm4PlanResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	private List<String> form4PlanSortedIdList(String district_id, String cycle_id, String year) {

		List<Integer> list = new ArrayList<Integer>();

		String sql = "SELECT form_2_stk_id FROM `form_2_stakeholders` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {
				list.add(Integer.valueOf(rs.getString("form_2_stk_id")));
			}
			return "";
		});

		Collections.sort(list);
		List<String> sortedList = list.stream().map(Object::toString).collect(Collectors.toList());
		return sortedList;
	}
	
	private List<String> form3DefineActionSortedIdList(String district_id, String cycle_id, String year, String actionPartOfEnggagement) {

		List<Integer> list = new ArrayList<Integer>();

		String sql = "SELECT id FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_part_of_engagement`=?;";
		Object[] params = new Object[] { district_id, cycle_id, year, actionPartOfEnggagement };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {
				list.add(Integer.valueOf(rs.getString("id")));
			}
			return "";
		});

		Collections.sort(list);
		List<String> sortedList = list.stream().map(Object::toString).collect(Collectors.toList());
		return sortedList;
	}
	
	private List<String> form3DefineActionRequiredSortedIdList(String district_id, String cycle_id, String year, String actionPartOfEnggagement) {

		List<Integer> list = new ArrayList<Integer>();

		String sql = "SELECT action_req_id FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_part`=?;";
		Object[] params = new Object[] { district_id, cycle_id, year, actionPartOfEnggagement };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {
				list.add(Integer.valueOf(rs.getString("action_req_id")));
			}
			return "";
		});

		Collections.sort(list);
		List<String> sortedList = list.stream().map(Object::toString).collect(Collectors.toList());
		return sortedList;
	}

}