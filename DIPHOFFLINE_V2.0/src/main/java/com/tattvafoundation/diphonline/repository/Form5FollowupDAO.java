package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import com.tattvafoundation.diphonline.model.DeleteForm4PlanResponse;
import com.tattvafoundation.diphonline.model.Form3DefineActionIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpActionCommonArray;
import com.tattvafoundation.diphonline.model.Form5FollowUpIndicatorsInfo;
import com.tattvafoundation.diphonline.model.Form5FollowUpPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpSaveMeetingList;
import com.tattvafoundation.diphonline.model.Form5FollowUpSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpTotalCoverageIndicators;
import com.tattvafoundation.diphonline.model.Form5Followup;
import com.tattvafoundation.diphonline.model.Form5PartADIPHCycleIndicatorComparison;
import com.tattvafoundation.diphonline.model.Form5PartAStakesMeetingDataBean;
import com.tattvafoundation.diphonline.model.Form5PartBActionPlanIndicatorFollowUpStatus;
import com.tattvafoundation.diphonline.model.Form5PartBIndicatorProgress;
import com.tattvafoundation.diphonline.model.SavedForm5FollowupResponse;
import com.tattvafoundation.diphonline.model.UserCredentialsFromAndroidBean;
import com.tattvafoundation.diphonline.model.User_Districts_Privileges;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class Form5FollowupDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Form5FollowupDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DeleteForm4PlanResponse deleteForm5FollowupDetails(String district_id, String cycle_id, String year,
			String user_id) {

		DeleteForm4PlanResponse responseobj = new DeleteForm4PlanResponse();
	

		Object[] params1 = { district_id, cycle_id, year };
		int rows = jdbcTemplate.update(
				"DELETE FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params1);
		

		Object[] params2 = { district_id, cycle_id, year };
		int rows2 = jdbcTemplate.update(
				"DELETE FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params2);
		

		Object[] params3 = { district_id, cycle_id, year };
		int rows3 = jdbcTemplate.update(
				"DELETE FROM `followup_action_plan_time` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params3);
		

		Object[] params4 = { district_id, cycle_id, year };
		int rows4 = jdbcTemplate.update(
				"DELETE FROM `folloup_action_plan_tbl_parta` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params4);
		

		Object[] params5 = { district_id, cycle_id, year };
		int rows5 = jdbcTemplate.update(
				"DELETE FROM `folloup_action_plan_tbl` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params5);
		

		responseobj.setProcessname("deleted");
		if (true) {
			responseobj.setResponse_val("1");
		} else {
			responseobj.setResponse_val("0");
		}

		return responseobj;

	}

	public SavedForm5FollowupResponse editUpdateForm5FollowUpToDb(Form5Followup model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

	
		String sql1 = " UPDATE `folloup_action_plan_tbl` SET `date_of_meeting`=?, `venue_of_meeting`=?,"
				+ " `chairperson_of_meeting`=?, `chairperson_of_meeting_others`=?, `theme_leader`=?, `no_of_meetings_resp_team`=?,  `user_id`=?,`record_created`=?, `completed`=?  where `district_id`=? and `cycle_id`=? and `financial_year`=?";

		int row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setString(1, model.getDate_of_meeting());
			ps.setString(2, model.getVenue_of_meeting());
			ps.setString(3, model.getChairperson_of_meeting());
			ps.setString(4, model.getChairperson_of_meeting_others());
			ps.setString(5, model.getTheme_leader());
			ps.setString(6, model.getNo_of_meetings_resp_team());
			ps.setString(7, model.getUserid());
			ps.setString(8, formatedDateTime);
			ps.setString(9, model.getCompleted());
			ps.setString(10, model.getDistrict());
			ps.setString(11, model.getCycle());
			ps.setString(12, model.getYear());
			
			return ps;
		});

	
		List<Form5FollowUpSaveMeetingList> list_meeting = model.getMeeting_arr();

		List<Form5FollowUpSaveMeetingList> list_meeting_update = new ArrayList<>();
		List<Form5FollowUpSaveMeetingList> list_meeting_insert = new ArrayList<>();

		for (int j = 0; j < list_meeting.size(); j++) {
			Form5FollowUpSaveMeetingList objobj = list_meeting.get(j);
			if ("1".equals(objobj.getInsert())) {
				list_meeting_insert.add(objobj);
			} else {
				list_meeting_update.add(objobj);
			}
		}

		jdbcTemplate.batchUpdate(
				" UPDATE `folloup_action_plan_tbl_parta` SET `meeting_date`=?, "
						+ "`stakeholder_meeting`=?, `no_of_participants`=?,`user_id`=?, `record_created`=?  "
						+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `meeting_no`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpSaveMeetingList tempobj = list_meeting_update.get(i);

						ps.setString(1, "" + tempobj.getMeeting_date());
						ps.setString(2, "" + tempobj.getStakeholder_meeting());
						ps.setString(3, "" + tempobj.getNo_of_participants());
						ps.setString(4, "" + model.getUserid());
						ps.setString(5, "" + formatedDateTime);
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + tempobj.getMeeting_no());
					}

					public int getBatchSize() {
						return list_meeting_update.size();
					}

				});

		List<Form5FollowUpTotalCoverageIndicators> list_coverage_indicators_arr = model.getTotal_coverage_indi();

		jdbcTemplate.batchUpdate(" UPDATE `followup_action_plan_time` SET `time_zero`=?, "
				+ "`time_one`=?, `time_three`=?,`time_four`=?,`timezero_date`=?, `timeone_date`=?,`timetwo_date`=?,`timethree_date`=?,`user_id`=?, `record_created`=?  "
				+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `form5_time_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpTotalCoverageIndicators tempobj = list_coverage_indicators_arr.get(i);
						
						
						ps.setString(1, tempobj.getTime_0() == null ? "0.0" : "" + tempobj.getTime_0());
						ps.setString(2, tempobj.getTime_1() == null ? "0.0" : "" + tempobj.getTime_1());
						ps.setString(3, tempobj.getTime_2() == null ? "0.0" : "" + tempobj.getTime_2());
						ps.setString(4, tempobj.getTime_3() == null ? "0.0" : "" + tempobj.getTime_3());
						ps.setString(5, model.getTotal_coverage_indi_timezero_date() == null ? null : "" + model.getTotal_coverage_indi_timezero_date());
						ps.setString(6, model.getTotal_coverage_indi_timeone_date() == null ? null : "" + model.getTotal_coverage_indi_timeone_date());
						ps.setString(7, model.getTotal_coverage_indi_timetwo_date() == null ? null : "" + model.getTotal_coverage_indi_timetwo_date());
						ps.setString(8, model.getTotal_coverage_indi_timethree_date() == null ? null : "" + model.getTotal_coverage_indi_timethree_date());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);
						ps.setString(11, "" + model.getDistrict());
						ps.setString(12, "" + model.getCycle());
						ps.setString(13, "" + model.getYear());
						ps.setString(14, "" + tempobj.getForm5_time_id());
					}

					public int getBatchSize() {
						return list_coverage_indicators_arr.size();
					}

				});

		String sql_pkey = "SELECt * FROM `folloup_action_plan_tbl` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params_pkey = new Object[] { model.getDistrict(), model.getCycle(), model.getYear() };

		String p_key = jdbcTemplate.query(sql_pkey, params_pkey, rs -> {

			String primary_key = "";
			while (rs.next()) {

				primary_key = rs.getString("followup_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return primary_key;
		});

		if (list_meeting_insert.size() != 0) {
			try {
				int meetingNumber = list_meeting_update.size() + 1;
				jdbcTemplate.batchUpdate(
						"INSERT INTO `folloup_action_plan_tbl_parta`(`followup_id`, `meeting_no`, `meeting_date`,"
								+ "		`stakeholder_meeting`, `no_of_participants`, `district_id`, `cycle_id`, `financial_year`,`user_id`,"
								+ "	`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + p_key);
								
								//ps.setString(2, "" + list_meeting_insert.get(i).getMeeting_no());
								ps.setString(2, "" + (meetingNumber + i));								
								
								ps.setString(3, "" + list_meeting_insert.get(i).getMeeting_date());
								ps.setString(4, "" + list_meeting_insert.get(i).getStakeholder_meeting());
								ps.setString(5, "" + list_meeting_insert.get(i).getNo_of_participants());
								ps.setString(6, "" + model.getDistrict());
								ps.setString(7, "" + model.getCycle());
								ps.setString(8, "" + model.getYear());
								ps.setString(9, "" + model.getUserid());
								ps.setString(10, "" + formatedDateTime);
							}

							public int getBatchSize() {
								return list_meeting_insert.size();
							}

						});
			} catch (Exception e) {
				e.printStackTrace();			
			}

		}

		List<Form5FollowUpActionCommonArray> list_service_action_arr = model.getService_action_arr();

		jdbcTemplate.batchUpdate(" UPDATE `followup_actionind_status` SET `status`=?, "
				+ "`revised_timeline`=?, `change_in_responsibility`=?,`user_id`=?, `record_created`=?  "
				+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `follow_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpActionCommonArray tempobj = list_service_action_arr.get(i);

						ps.setString(1, "" + tempobj.getStatus());
						ps.setString(2, "" + tempobj.getRevised_timeline());
						ps.setString(3, "" + tempobj.getChange_in_responsibility());
						ps.setString(4, "" + model.getUserid());
						ps.setString(5, "" + formatedDateTime);
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + tempobj.getAction_req_id());
						ps.setString(10, "" + p_key);
					}

					public int getBatchSize() {
						return list_service_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_service_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_service_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate.batchUpdate(" UPDATE `followup_indicator_progress` SET `progress_indicators`=?, "
					+ "`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `followup_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form5FollowUpIndicatorsInfo tempobj = indi_arr.get(i);
							ps.setString(1, "" + tempobj.getProgress_indicators());
							ps.setString(2, "" + model.getUserid());
							ps.setString(3, "" + formatedDateTime);
							ps.setString(4, "" + model.getDistrict());
							ps.setString(5, "" + model.getCycle());
							ps.setString(6, "" + model.getYear());
							ps.setString(7, "" + list_service_action_arr.get(index).getAction_req_id());
							ps.setString(8, "" + p_key);
						}

						public int getBatchSize() {
							return indi_arr.size();
						}

					});

		}

		List<Form5FollowUpActionCommonArray> list_workforce_action_arr = model.getWorkforce_action_arr();

		jdbcTemplate.batchUpdate(" UPDATE `followup_actionind_status` SET `status`=?, "
				+ "`revised_timeline`=?, `change_in_responsibility`=?,`user_id`=?, `record_created`=?  "
				+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `follow_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpActionCommonArray tempobj = list_workforce_action_arr.get(i);

						ps.setString(1, "" + tempobj.getStatus());
						ps.setString(2, "" + tempobj.getRevised_timeline());
						ps.setString(3, "" + tempobj.getChange_in_responsibility());
						ps.setString(4, "" + model.getUserid());
						ps.setString(5, "" + formatedDateTime);
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + tempobj.getAction_req_id());
						ps.setString(10, "" + p_key);
					}

					public int getBatchSize() {
						return list_workforce_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_workforce_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_workforce_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate.batchUpdate(" UPDATE `followup_indicator_progress` SET `progress_indicators`=?, "
					+ "`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `followup_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form5FollowUpIndicatorsInfo tempobj = indi_arr.get(i);
							ps.setString(1, "" + tempobj.getProgress_indicators());
							ps.setString(2, "" + model.getUserid());
							ps.setString(3, "" + formatedDateTime);
							ps.setString(4, "" + model.getDistrict());
							ps.setString(5, "" + model.getCycle());
							ps.setString(6, "" + model.getYear());
							ps.setString(7, "" + list_workforce_action_arr.get(index).getAction_req_id());
							ps.setString(8, "" + p_key);
						}

						public int getBatchSize() {
							return indi_arr.size();
						}

					});

		}

		List<Form5FollowUpActionCommonArray> list_supplies_action_arr = model.getSupplies_action_arr();

		jdbcTemplate.batchUpdate(" UPDATE `followup_actionind_status` SET `status`=?, "
				+ "`revised_timeline`=?, `change_in_responsibility`=?,`user_id`=?, `record_created`=?  "
				+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `follow_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpActionCommonArray tempobj = list_supplies_action_arr.get(i);

						ps.setString(1, "" + tempobj.getStatus());
						ps.setString(2, "" + tempobj.getRevised_timeline());
						ps.setString(3, "" + tempobj.getChange_in_responsibility());
						ps.setString(4, "" + model.getUserid());
						ps.setString(5, "" + formatedDateTime);
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + tempobj.getAction_req_id());
						ps.setString(10, "" + p_key);
					}

					public int getBatchSize() {
						return list_supplies_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_supplies_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_supplies_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate.batchUpdate(" UPDATE `followup_indicator_progress` SET `progress_indicators`=?, "
					+ "`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `followup_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form5FollowUpIndicatorsInfo tempobj = indi_arr.get(i);
							ps.setString(1, "" + tempobj.getProgress_indicators());
							ps.setString(2, "" + model.getUserid());
							ps.setString(3, "" + formatedDateTime);
							ps.setString(4, "" + model.getDistrict());
							ps.setString(5, "" + model.getCycle());
							ps.setString(6, "" + model.getYear());
							ps.setString(7, "" + list_supplies_action_arr.get(index).getAction_req_id());
							ps.setString(8, "" + p_key);
						}

						public int getBatchSize() {
							return indi_arr.size();
						}

					});

		}

		List<Form5FollowUpActionCommonArray> list_health_action_arr = model.getHealth_action_arr();

		jdbcTemplate.batchUpdate(" UPDATE `followup_actionind_status` SET `status`=?, "
				+ "`revised_timeline`=?, `change_in_responsibility`=?,`user_id`=?, `record_created`=?  "
				+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `follow_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpActionCommonArray tempobj = list_health_action_arr.get(i);

						ps.setString(1, "" + tempobj.getStatus());
						ps.setString(2, "" + tempobj.getRevised_timeline());
						ps.setString(3, "" + tempobj.getChange_in_responsibility());
						ps.setString(4, "" + model.getUserid());
						ps.setString(5, "" + formatedDateTime);
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + tempobj.getAction_req_id());
						ps.setString(10, "" + p_key);
					}

					public int getBatchSize() {
						return list_health_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_health_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_health_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate.batchUpdate(" UPDATE `followup_indicator_progress` SET `progress_indicators`=?, "
					+ "`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `followup_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form5FollowUpIndicatorsInfo tempobj = indi_arr.get(i);
							ps.setString(1, "" + tempobj.getProgress_indicators());
							ps.setString(2, "" + model.getUserid());
							ps.setString(3, "" + formatedDateTime);
							ps.setString(4, "" + model.getDistrict());
							ps.setString(5, "" + model.getCycle());
							ps.setString(6, "" + model.getYear());
							ps.setString(7, "" + list_health_action_arr.get(index).getAction_req_id());
							ps.setString(8, "" + p_key);
						}

						public int getBatchSize() {
							return indi_arr.size();
						}

					});

		}

		List<Form5FollowUpActionCommonArray> list_finance_action_arr = model.getFinance_action_arr();

		jdbcTemplate.batchUpdate(" UPDATE `followup_actionind_status` SET `status`=?, "
				+ "`revised_timeline`=?, `change_in_responsibility`=?,`user_id`=?, `record_created`=?  "
				+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `follow_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpActionCommonArray tempobj = list_finance_action_arr.get(i);

						ps.setString(1, "" + tempobj.getStatus());
						ps.setString(2, "" + tempobj.getRevised_timeline());
						ps.setString(3, "" + tempobj.getChange_in_responsibility());
						ps.setString(4, "" + model.getUserid());
						ps.setString(5, "" + formatedDateTime);
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + tempobj.getAction_req_id());
						ps.setString(10, "" + p_key);
					}

					public int getBatchSize() {
						return list_finance_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_finance_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_finance_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate.batchUpdate(" UPDATE `followup_indicator_progress` SET `progress_indicators`=?, "
					+ "`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `followup_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form5FollowUpIndicatorsInfo tempobj = indi_arr.get(i);
							ps.setString(1, "" + tempobj.getProgress_indicators());
							ps.setString(2, "" + model.getUserid());
							ps.setString(3, "" + formatedDateTime);
							ps.setString(4, "" + model.getDistrict());
							ps.setString(5, "" + model.getCycle());
							ps.setString(6, "" + model.getYear());
							ps.setString(7, "" + list_finance_action_arr.get(index).getAction_req_id());
							ps.setString(8, "" + p_key);
						}

						public int getBatchSize() {
							return indi_arr.size();
						}

					});

		}

		List<Form5FollowUpActionCommonArray> list_policy_action_arr = model.getPolicy_action_arr();

		jdbcTemplate.batchUpdate(" UPDATE `followup_actionind_status` SET `status`=?, "
				+ "`revised_timeline`=?, `change_in_responsibility`=?,`user_id`=?, `record_created`=?  "
				+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `follow_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form5FollowUpActionCommonArray tempobj = list_policy_action_arr.get(i);

						ps.setString(1, "" + tempobj.getStatus());
						ps.setString(2, "" + tempobj.getRevised_timeline());
						ps.setString(3, "" + tempobj.getChange_in_responsibility());
						ps.setString(4, "" + model.getUserid());
						ps.setString(5, "" + formatedDateTime);
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + tempobj.getAction_req_id());
						ps.setString(10, "" + p_key);
					}

					public int getBatchSize() {
						return list_policy_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_policy_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_policy_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate.batchUpdate(" UPDATE `followup_indicator_progress` SET `progress_indicators`=?, "
					+ "`user_id`=?, `record_created`=?  "
					+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `followup_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form5FollowUpIndicatorsInfo tempobj = indi_arr.get(i);
							ps.setString(1, "" + tempobj.getProgress_indicators());
							ps.setString(2, "" + model.getUserid());
							ps.setString(3, "" + formatedDateTime);
							ps.setString(4, "" + model.getDistrict());
							ps.setString(5, "" + model.getCycle());
							ps.setString(6, "" + model.getYear());
							ps.setString(7, "" + list_policy_action_arr.get(index).getAction_req_id());
							ps.setString(8, "" + p_key);
						}

						public int getBatchSize() {
							return indi_arr.size();
						}

					});

		}

		SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	
	

	public Form5FollowUpSendAllDataBean retrieveForm5FollowUp_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(
			UserCredentialsFromAndroidBean model,String LoggedinUserId) {

		Form5FollowUpSendAllDataBean response = new Form5FollowUpSendAllDataBean();
		
		
		List<Form5FollowUpPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<Form5PartAStakesMeetingDataBean> sendlist2 = new ArrayList<>();
		List<Form5PartADIPHCycleIndicatorComparison> sendlist3 = new ArrayList<>();
		List<Form5PartBActionPlanIndicatorFollowUpStatus> sendlist4 = new ArrayList<>();
		List<Form5PartBIndicatorProgress> sendlist5 = new ArrayList<>();
		
		
		String sql_user_data = "SELECT (SELECT `user_id` FROM `user_info` where `user_nm`=?) `user_id`,(SELECT `user_nm` FROM `user_info` where `user_nm`=?) `user_nm`,`user_info_id`," + " CASE "
				+ "  WHEN GROUP_CONCAT(DISTINCT `district_id`) IS NULL THEN '-1' "
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

		
		
		
		if("-1".equals(user_priv.getAll_districts())) { 		
			
						
			response.setForm5followup(new ArrayList<>());
			response.setForm5partamajorholder(new ArrayList<>());
			response.setForm5partaindicator(new ArrayList<>());			
			response.setForm5partbindiprogress(new ArrayList<>());

			return response;
		}
		else {
			System.out.println("Not Returning in half!!! 0 or different value rather than -1");
		}
		
		
		String sql1 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql1 = "SELECT form5followup.`completed`, form5followup.`followup_id`,   form5followup.`date_of_meeting`,  form5followup. `venue_of_meeting`,"
					+ "   form5followup. `chairperson_of_meeting`,    form5followup.`chairperson_of_meeting_others`,    "
					+ "   form5followup.`theme_leader`,    form5followup.`no_of_meetings_resp_team`,    form5followup.`district_id`,    "
					+ "   form5followup. `cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`,  "
					+ "   form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` FROM  `folloup_action_plan_tbl` form5followup  "
					+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` > 2019 ";

		}
		else {
			sql1 = "SELECT form5followup.`completed`, form5followup.`followup_id`,   form5followup.`date_of_meeting`,  form5followup. `venue_of_meeting`,"
					+ "   form5followup. `chairperson_of_meeting`,    form5followup.`chairperson_of_meeting_others`,    "
					+ "   form5followup.`theme_leader`,    form5followup.`no_of_meetings_resp_team`,    form5followup.`district_id`,    "
					+ "   form5followup. `cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`,  "
					+ "   form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` FROM  `folloup_action_plan_tbl` form5followup  "
					+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` > 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form5followup.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form5followup.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist1 = jdbcTemplate.query(sql1, params1, rs -> {

			String primary_key = "";

			List<Form5FollowUpPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form5FollowUpPrimaryTableDataBean obj = new Form5FollowUpPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("followup_id"));
				obj.setMeetingdate(rs.getString("date_of_meeting"));
				obj.setMeetingvenue(rs.getString("venue_of_meeting"));
				
				/*-----------------------------------------------------------------------*/

				String temp_sql = "SELECt * FROM `form_3_1_verify` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] temp_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year") };

				String form3autoid = jdbcTemplate.query(temp_sql, temp_param, temp_rs -> {

					String val = "0";
					while (temp_rs.next()) {

						val = temp_rs.getString("form_3_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return val;
				});
				
				/*-----------------------------------------------------------------------*/

				obj.setThemeleaderid(form3autoid);
				obj.setNoofmeeting(rs.getString("no_of_meetings_resp_team"));
				obj.setChairpersonid(rs.getString("chairperson_of_meeting"));
				obj.setOtherchairperson(rs.getString("chairperson_of_meeting_others"));
				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCompleted(rs.getString("completed"));
				obj.setDatafrom("WEB");

				templist.add(obj);

				primary_key = rs.getString("followup_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		String sql2 = "";
		
		
		if("0".equals(user_priv.getAll_districts())) {
			sql2 = "SELECT form5followup.`followup_parta_id`,    form5followup.`followup_id`,    form5followup.`meeting_no`,  "
					+ "    form5followup.`meeting_date`,    form5followup.`stakeholder_meeting`,    form5followup.`no_of_participants`, "
					+ "    form5followup. `district_id`,    form5followup.`cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`, "
					+ "    form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`  FROM `folloup_action_plan_tbl_parta` form5followup  "
					+ "    left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form5followup.`financial_year` >= 2019 ";
	
		}
		else {
			sql2 = "SELECT form5followup.`followup_parta_id`,    form5followup.`followup_id`,    form5followup.`meeting_no`,  "
					+ "    form5followup.`meeting_date`,    form5followup.`stakeholder_meeting`,    form5followup.`no_of_participants`, "
					+ "    form5followup. `district_id`,    form5followup.`cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`, "
					+ "    form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`  FROM `folloup_action_plan_tbl_parta` form5followup  "
					+ "    left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form5followup.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form5followup.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form5followup.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist2 = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form5PartAStakesMeetingDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartAStakesMeetingDataBean obj = new Form5PartAStakesMeetingDataBean();

				obj.setAuto_id(rs.getString("followup_parta_id"));
				obj.setFollowup_id_server(rs.getString("followup_id"));
				obj.setMeeting_no(rs.getString("meeting_no"));
				obj.setMeeting_date(rs.getString("meeting_date"));
				obj.setNo_of_participants(rs.getString("no_of_participants"));
				obj.setStakeholder_meeting(rs.getString("stakeholder_meeting"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		String sql3 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql3 = "SELECT form5followup.`form5_time_id`,    form5followup.`followup_id`,    form5followup.`cov_indicators`,  "
					+ "   form5followup.`ci_source`,    form5followup.`time_zero`,    form5followup.`time_one`,    form5followup.`time_three`,  "
					+ "   form5followup.`time_four`,    form5followup.`timezero_date`,    form5followup.`timeone_date`,    form5followup.`timetwo_date`,  "
					+ "   form5followup.`timethree_date`,    form5followup.`district_id`,    form5followup.`cycle_id`,    form5followup.`financial_year`,  "
					+ "   form5followup.`user_id`,    form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`   FROM `followup_action_plan_time`  form5followup   "
					+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` >= 2019 ";
	
		}
		else {
			sql3 = "SELECT form5followup.`form5_time_id`,    form5followup.`followup_id`,    form5followup.`cov_indicators`,  "
					+ "   form5followup.`ci_source`,    form5followup.`time_zero`,    form5followup.`time_one`,    form5followup.`time_three`,  "
					+ "   form5followup.`time_four`,    form5followup.`timezero_date`,    form5followup.`timeone_date`,    form5followup.`timetwo_date`,  "
					+ "   form5followup.`timethree_date`,    form5followup.`district_id`,    form5followup.`cycle_id`,    form5followup.`financial_year`,  "
					+ "   form5followup.`user_id`,    form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`   FROM `followup_action_plan_time`  form5followup   "
					+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form5followup.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form5followup.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };
		
		
		sendlist3 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form5PartADIPHCycleIndicatorComparison> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartADIPHCycleIndicatorComparison obj = new Form5PartADIPHCycleIndicatorComparison();

				obj.setAuto_id(rs.getString("form5_time_id"));
				obj.setFollowup_id_server(rs.getString("followup_id"));
				obj.setIndicator_id(rs.getString("cov_indicators"));
				obj.setTime_zero(rs.getString("time_zero"));
				obj.setTime_one(rs.getString("time_one"));
				obj.setTime_two(rs.getString("time_three"));
				obj.setTime_three(rs.getString("time_four"));
				obj.setTimezero_date(rs.getString("timezero_date"));
				obj.setTimeone_date(rs.getString("timeone_date"));
				obj.setTimetwo_date(rs.getString("timetwo_date"));
				obj.setTimethree_date(rs.getString("timethree_date"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		
	
		String sql4 = "";
		
		
		if("0".equals(user_priv.getAll_districts())) {
			sql4 = "SELECT form5followup.`act_ind_stat_id`,    form5followup.`follow_id`, "
					+ "   form5followup. `act_point`,    form5followup.`status`,    form5followup.`revised_timeline`, "
					+ "   form5followup.`change_in_responsibility`,    form5followup.`district_id`,    form5followup.`cycle_id`,  "
					+ "   form5followup.`financial_year`,    form5followup.`user_id`,    form5followup.`record_created`, "
					+ "   d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM  `followup_actionind_status`  form5followup  "
					+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` >= 2019 ";
	
		}
		else {
			sql4 = "SELECT form5followup.`act_ind_stat_id`,    form5followup.`follow_id`, "
					+ "   form5followup. `act_point`,    form5followup.`status`,    form5followup.`revised_timeline`, "
					+ "   form5followup.`change_in_responsibility`,    form5followup.`district_id`,    form5followup.`cycle_id`,  "
					+ "   form5followup.`financial_year`,    form5followup.`user_id`,    form5followup.`record_created`, "
					+ "   d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM  `followup_actionind_status`  form5followup  "
					+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` >= 2019     and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form5followup.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form5followup.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params4 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist4 = jdbcTemplate.query(sql4, params4, rs -> {
			List<Form5PartBActionPlanIndicatorFollowUpStatus> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartBActionPlanIndicatorFollowUpStatus obj = new Form5PartBActionPlanIndicatorFollowUpStatus();
				obj.setAuto_id(rs.getString("act_ind_stat_id"));
				obj.setFollowup_id_server(rs.getString("follow_id"));
				obj.setAction_point_id(rs.getString("act_point"));
				obj.setStatus(rs.getString("status"));
				obj.setRevised_timeline(rs.getString("revised_timeline"));
				obj.setChange_in_responsibility(rs.getString("change_in_responsibility"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		
		String sql_outer = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql_outer = "SELECT  form5followup.`act_ind_stat_id`,   form5followup. `act_point`   FROM  `followup_actionind_status`  form5followup  "
					+ "         left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "         where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?  and  form5followup.`financial_year` >= 2019 ";
	
		}
		else {
			sql_outer = "SELECT  form5followup.`act_ind_stat_id`,   form5followup. `act_point`   FROM  `followup_actionind_status`  form5followup  "
					+ "         left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "         where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?  and  form5followup.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form5followup.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form5followup.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params_outer = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist5 = jdbcTemplate.query(sql_outer, params_outer, rs_outer -> {

			List<Form5PartBIndicatorProgress> totallist = new ArrayList<>();

			while (rs_outer.next()) {
				
				String sql5 = "";
				
				
				if("0".equals(user_priv.getAll_districts())) {
					sql5 = "SELECT form5followup.`ind_progress_id`,    form5followup.`followup_id`,    form5followup.`act_point`, "
							+ "   form5followup.`act_indicators`,    form5followup.`progress_indicators`,    form5followup.`district_id`,  "
							+ "   form5followup.`cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`,  "
							+ "   form5followup. `record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM `followup_indicator_progress`   form5followup   "
							+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
							+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and form5followup.`act_point`=? and  form5followup.`financial_year` >= 2019";

				}
				else {
					sql5 = "SELECT form5followup.`ind_progress_id`,    form5followup.`followup_id`,    form5followup.`act_point`, "
							+ "   form5followup.`act_indicators`,    form5followup.`progress_indicators`,    form5followup.`district_id`,  "
							+ "   form5followup.`cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`,  "
							+ "   form5followup. `record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM `followup_indicator_progress`   form5followup   "
							+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
							+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and form5followup.`act_point`=? and  form5followup.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form5followup.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form5followup.`financial_year` IN ("+user_priv.getAll_years()+");";

				}

				
				Object[] params5 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
						model.getCountry_id(), rs_outer.getString("act_point") };

				List<Form5PartBIndicatorProgress> againlist = jdbcTemplate.query(sql5, params5, rs -> {

					List<Form5PartBIndicatorProgress> templist = new ArrayList<>();
					while (rs.next()) {

						Form5PartBIndicatorProgress obj = new Form5PartBIndicatorProgress();

						obj.setAuto_id(rs.getString("ind_progress_id"));
						obj.setFollowup_id_server(rs.getString("followup_id"));
						obj.setFollowuppartb_id_server(rs_outer.getString("act_ind_stat_id"));
						obj.setAction_point_id(rs.getString("act_point"));
						
						/**********************Pbm here********************************/
						
						/*-----------------------------------------------------------------------*/

						String form4plan_indi_id_sql = "SELECT * FROM dev_action_plan_indicators where dev_indicator_id=? and district_id=? and cycle_id=? and financial_year=?";
						Object[] form4plan_indi_id_sql_param = new Object[] { rs.getString("act_indicators"),rs.getString("district_id"), rs.getString("cycle_id"),
								rs.getString("financial_year") };

						String form4plan_indi_id = jdbcTemplate.query(form4plan_indi_id_sql, form4plan_indi_id_sql_param, temp_rs -> {

							String val = "0";
							while (temp_rs.next()) {

								val = temp_rs.getString("indicator_id");
							}
							/* We can also return any variable-data from here but not used currently */
							return val;
						});
						
						/*-----------------------------------------------------------------------*/
						
						//obj.setIndicator_id(rs.getString("act_indicators"));
						
						obj.setIndicator_id(form4plan_indi_id);
						
						/**********************Pbm here********************************/
						
						obj.setProgress_indicators(rs.getString("progress_indicators"));
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
					/* We can also return any variable-data from here but not used currently */
					return templist;
				});

				totallist.addAll(againlist);

			}
			/* We can also return any variable-data from here but not used currently */
			return totallist;
		});

		response.setForm5followup(sendlist1);
		response.setForm5partamajorholder(sendlist2);
		response.setForm5partaindicator(sendlist3);
		
		if(sendlist4 == null) {
			sendlist4 = new ArrayList<>();
		}
		response.setForm5partb(sendlist4);
		
		if(sendlist5 == null) {
			sendlist5 = new ArrayList<>();
		}
		
		response.setForm5partbindiprogress(sendlist5);
		return response;

	}


	public Form5FollowUpSendAllDataBean retrieveForm5FollowUp_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(
			UserCredentialsFromAndroidBean model) {

		Form5FollowUpSendAllDataBean response = new Form5FollowUpSendAllDataBean();

		List<Form5FollowUpPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<Form5PartAStakesMeetingDataBean> sendlist2 = new ArrayList<>();
		List<Form5PartADIPHCycleIndicatorComparison> sendlist3 = new ArrayList<>();
		List<Form5PartBActionPlanIndicatorFollowUpStatus> sendlist4 = new ArrayList<>();
		List<Form5PartBIndicatorProgress> sendlist5 = new ArrayList<>();

		String sql1 = "SELECT form5followup.`completed`, form5followup.`followup_id`,   form5followup.`date_of_meeting`,  form5followup. `venue_of_meeting`,"
				+ "   form5followup. `chairperson_of_meeting`,    form5followup.`chairperson_of_meeting_others`,    "
				+ "   form5followup.`theme_leader`,    form5followup.`no_of_meetings_resp_team`,    form5followup.`district_id`,    "
				+ "   form5followup. `cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`,  "
				+ "   form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` FROM  `folloup_action_plan_tbl` form5followup  "
				+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` > 2019 ";

		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist1 = jdbcTemplate.query(sql1, params1, rs -> {

			String primary_key = "";

			List<Form5FollowUpPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form5FollowUpPrimaryTableDataBean obj = new Form5FollowUpPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("followup_id"));
				obj.setMeetingdate(rs.getString("date_of_meeting"));
				obj.setMeetingvenue(rs.getString("venue_of_meeting"));
				
				/*-----------------------------------------------------------------------*/

				String temp_sql = "SELECt * FROM `form_3_1_verify` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] temp_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year") };

				String form3autoid = jdbcTemplate.query(temp_sql, temp_param, temp_rs -> {

					String val = "0";
					while (temp_rs.next()) {

						val = temp_rs.getString("form_3_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return val;
				});
				
				/*-----------------------------------------------------------------------*/

				obj.setThemeleaderid(form3autoid);
				obj.setNoofmeeting(rs.getString("no_of_meetings_resp_team"));
				obj.setChairpersonid(rs.getString("chairperson_of_meeting"));
				obj.setOtherchairperson(rs.getString("chairperson_of_meeting_others"));
				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCompleted(rs.getString("completed"));
				obj.setDatafrom("WEB");

				templist.add(obj);

				primary_key = rs.getString("followup_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql2 = "SELECT form5followup.`followup_parta_id`,    form5followup.`followup_id`,    form5followup.`meeting_no`,  "
				+ "    form5followup.`meeting_date`,    form5followup.`stakeholder_meeting`,    form5followup.`no_of_participants`, "
				+ "    form5followup. `district_id`,    form5followup.`cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`, "
				+ "    form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`  FROM `folloup_action_plan_tbl_parta` form5followup  "
				+ "    left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form5followup.`financial_year` >= 2019 ";

		Object[] params2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist2 = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form5PartAStakesMeetingDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartAStakesMeetingDataBean obj = new Form5PartAStakesMeetingDataBean();

				obj.setAuto_id(rs.getString("followup_parta_id"));
				obj.setFollowup_id_server(rs.getString("followup_id"));
				obj.setMeeting_no(rs.getString("meeting_no"));
				obj.setMeeting_date(rs.getString("meeting_date"));
				obj.setNo_of_participants(rs.getString("no_of_participants"));
				obj.setStakeholder_meeting(rs.getString("stakeholder_meeting"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql3 = "SELECT form5followup.`form5_time_id`,    form5followup.`followup_id`,    form5followup.`cov_indicators`,  "
				+ "   form5followup.`ci_source`,    form5followup.`time_zero`,    form5followup.`time_one`,    form5followup.`time_three`,  "
				+ "   form5followup.`time_four`,    form5followup.`timezero_date`,    form5followup.`timeone_date`,    form5followup.`timetwo_date`,  "
				+ "   form5followup.`timethree_date`,    form5followup.`district_id`,    form5followup.`cycle_id`,    form5followup.`financial_year`,  "
				+ "   form5followup.`user_id`,    form5followup.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`   FROM `followup_action_plan_time`  form5followup   "
				+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` >= 2019 ";

		Object[] params3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };
		
		
		sendlist3 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form5PartADIPHCycleIndicatorComparison> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartADIPHCycleIndicatorComparison obj = new Form5PartADIPHCycleIndicatorComparison();

				obj.setAuto_id(rs.getString("form5_time_id"));
				obj.setFollowup_id_server(rs.getString("followup_id"));
				obj.setIndicator_id(rs.getString("cov_indicators"));
				obj.setTime_zero(rs.getString("time_zero"));
				obj.setTime_one(rs.getString("time_one"));
				obj.setTime_two(rs.getString("time_three"));
				obj.setTime_three(rs.getString("time_four"));
				obj.setTimezero_date(rs.getString("timezero_date"));
				obj.setTimeone_date(rs.getString("timeone_date"));
				obj.setTimetwo_date(rs.getString("timetwo_date"));
				obj.setTimethree_date(rs.getString("timethree_date"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		
		
		String sql4 = "SELECT form5followup.`act_ind_stat_id`,    form5followup.`follow_id`, "
				+ "   form5followup. `act_point`,    form5followup.`status`,    form5followup.`revised_timeline`, "
				+ "   form5followup.`change_in_responsibility`,    form5followup.`district_id`,    form5followup.`cycle_id`,  "
				+ "   form5followup.`financial_year`,    form5followup.`user_id`,    form5followup.`record_created`, "
				+ "   d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM  `followup_actionind_status`  form5followup  "
				+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id    "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?    and  form5followup.`financial_year` >= 2019 ";

		Object[] params4 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist4 = jdbcTemplate.query(sql4, params4, rs -> {
			List<Form5PartBActionPlanIndicatorFollowUpStatus> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartBActionPlanIndicatorFollowUpStatus obj = new Form5PartBActionPlanIndicatorFollowUpStatus();
				obj.setAuto_id(rs.getString("act_ind_stat_id"));
				obj.setFollowup_id_server(rs.getString("follow_id"));
				obj.setAction_point_id(rs.getString("act_point"));
				obj.setStatus(rs.getString("status"));
				obj.setRevised_timeline(rs.getString("revised_timeline"));
				obj.setChange_in_responsibility(rs.getString("change_in_responsibility"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql_outer = "SELECT  form5followup.`act_ind_stat_id`,   form5followup. `act_point`   FROM  `followup_actionind_status`  form5followup  "
				+ "         left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "         where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?  and  form5followup.`financial_year` >= 2019 ";

		Object[] params_outer = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist5 = jdbcTemplate.query(sql_outer, params_outer, rs_outer -> {

			List<Form5PartBIndicatorProgress> totallist = new ArrayList<>();

			while (rs_outer.next()) {

				String sql5 = "SELECT form5followup.`ind_progress_id`,    form5followup.`followup_id`,    form5followup.`act_point`, "
						+ "   form5followup.`act_indicators`,    form5followup.`progress_indicators`,    form5followup.`district_id`,  "
						+ "   form5followup.`cycle_id`,    form5followup.`financial_year`,    form5followup.`user_id`,  "
						+ "   form5followup. `record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM `followup_indicator_progress`   form5followup   "
						+ "   left join  `district` d on form5followup.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
						+ "    where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and form5followup.`act_point`=? and  form5followup.`financial_year` >= 2019";

				Object[] params5 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
						model.getCountry_id(), rs_outer.getString("act_point") };

				List<Form5PartBIndicatorProgress> againlist = jdbcTemplate.query(sql5, params5, rs -> {

					List<Form5PartBIndicatorProgress> templist = new ArrayList<>();
					while (rs.next()) {

						Form5PartBIndicatorProgress obj = new Form5PartBIndicatorProgress();

						obj.setAuto_id(rs.getString("ind_progress_id"));
						obj.setFollowup_id_server(rs.getString("followup_id"));
						obj.setFollowuppartb_id_server(rs_outer.getString("act_ind_stat_id"));
						obj.setAction_point_id(rs.getString("act_point"));
						
						/**********************Pbm here********************************/
						
						/*-----------------------------------------------------------------------*/

						String form4plan_indi_id_sql = "SELECT * FROM dev_action_plan_indicators where dev_indicator_id=? and district_id=? and cycle_id=? and financial_year=?";
						Object[] form4plan_indi_id_sql_param = new Object[] { rs.getString("act_indicators"),rs.getString("district_id"), rs.getString("cycle_id"),
								rs.getString("financial_year") };

						String form4plan_indi_id = jdbcTemplate.query(form4plan_indi_id_sql, form4plan_indi_id_sql_param, temp_rs -> {

							String val = "0";
							while (temp_rs.next()) {

								val = temp_rs.getString("indicator_id");
							}
							/* We can also return any variable-data from here but not used currently */
							return val;
						});
						
						/*-----------------------------------------------------------------------*/
						
						//obj.setIndicator_id(rs.getString("act_indicators"));
						
						obj.setIndicator_id(form4plan_indi_id);
						
						/**********************Pbm here********************************/
						
						obj.setProgress_indicators(rs.getString("progress_indicators"));
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
					/* We can also return any variable-data from here but not used currently */
					return templist;
				});

				totallist.addAll(againlist);

			}
			/* We can also return any variable-data from here but not used currently */
			return totallist;
		});

		response.setForm5followup(sendlist1);
		response.setForm5partamajorholder(sendlist2);
		response.setForm5partaindicator(sendlist3);
		
		if(sendlist4 == null) {
			sendlist4 = new ArrayList<>();
		}
		response.setForm5partb(sendlist4);
		
		if(sendlist5 == null) {
			sendlist5 = new ArrayList<>();
		}
		
		response.setForm5partbindiprogress(sendlist5);
		return response;

	}

	public Form5Followup retrieveForm5FollowupDetails(String district_id, String cycle_id, String year,
			String user_id) {

		Form5Followup obj = new Form5Followup();

//		INSERT INTO `folloup_action_plan_tbl`(`date_of_meeting`, `venue_of_meeting`, `chairperson_of_meeting`,
//		`theme_leader`, `no_of_meetings_resp_team`,  `district_id`, `cycle_id`, `financial_year`,`user_id`,
//		`record_created`) VALUES (:date_of_meeting,:venue_of_meeting,:chairperson_of_meeting,:theme_leader,
//				:no_of_meetings_resp_team,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql1 = "SELECt * FROM `folloup_action_plan_tbl` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params1 = new Object[] { district_id, cycle_id, year };

		String p_key = jdbcTemplate.query(sql1, params1, rs -> {

			String primary_key = "";
			while (rs.next()) {

				obj.setDate_of_meeting(rs.getString("date_of_meeting"));
				obj.setVenue_of_meeting(rs.getString("venue_of_meeting"));
				obj.setChairperson_of_meeting(rs.getString("chairperson_of_meeting"));
				obj.setChairperson_of_meeting_others(rs.getString("chairperson_of_meeting_others"));
				obj.setTheme_leader(rs.getString("theme_leader"));
				obj.setNo_of_meetings_resp_team(rs.getString("no_of_meetings_resp_team"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUserid(rs.getString("user_id"));
				obj.setCompleted(rs.getString("completed"));

				primary_key = rs.getString("followup_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return primary_key;
		});

//		INSERT INTO `folloup_action_plan_tbl_parta`(`followup_id`, `meeting_no`, `meeting_date`,
//		`stakeholder_meeting`, `no_of_participants`, `district_id`, `cycle_id`, `financial_year`,`user_id`,
//		`record_created`) VALUES (:followup_id,:meeting_no,:meeting_date,:stakeholder_meeting,:no_of_participants,
//				:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql2 = "SELECt * FROM `folloup_action_plan_tbl_parta` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? order by followup_parta_id";
		Object[] params2 = new Object[] { district_id, cycle_id, year, p_key };

		List<Form5FollowUpSaveMeetingList> list_meeting = new ArrayList<>();

		jdbcTemplate.query(sql2, params2, rs -> {

			String str = "";
			while (rs.next()) {
				Form5FollowUpSaveMeetingList temp = new Form5FollowUpSaveMeetingList();
				temp.setMeeting_no(rs.getString("meeting_no"));
				temp.setMeeting_date(rs.getString("meeting_date"));
				temp.setStakeholder_meeting(rs.getString("stakeholder_meeting"));
				temp.setNo_of_participants(rs.getString("no_of_participants"));

				list_meeting.add(temp);

//				obj.setMeeting_no(rs.getString("meeting_no"));
//				obj.setMeeting_date(rs.getString("meeting_date"));
//				obj.setStakeholder_meeting(rs.getString("stakeholder_meeting"));
//				obj.setNo_of_participants(rs.getString("no_of_participants"));
			}
			/* We can also return any variable-data from here but not used currently */
			return str;
		});

		obj.setMeeting_arr(list_meeting);

//		INSERT INTO `followup_action_plan_time`(`followup_id`, `cov_indicators`, `ci_source`,
//		`time_zero`, `time_one`, `time_three`, `time_four`, `timezero_date`, 
//		`timeone_date`, `timetwo_date`, `timethree_date`, `district_id`, `cycle_id`, `financial_year`,
//		`user_id`,`record_created`) VALUES (:followup_id,:cov_indicators,:ci_source,:time_zero,:time_one,
//				:time_three,:time_four,:timezero_date,:timeone_date,:timetwo_date,:timethree_date,:district_id,
//				:cycle_id,:financial_year,:user_id,:record_created)

		String sql3 = "SELECt * FROM `followup_action_plan_time` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=?";
		Object[] params3 = new Object[] { district_id, cycle_id, year, p_key };

		List<Form5FollowUpTotalCoverageIndicators> list_coverage_indicators_arr = new ArrayList<>();

		jdbcTemplate.query(sql3, params3, rs -> {

			String str = "";
			while (rs.next()) {

				Form5FollowUpTotalCoverageIndicators temp = new Form5FollowUpTotalCoverageIndicators();

				temp.setForm5_time_id(rs.getString("form5_time_id"));
				temp.setIndicator_id(rs.getString("cov_indicators"));

				jdbcTemplate.query(
						"SELECT `id`, `indicator_name`,   `area_id`,  `category` FROM `indicators` where `id`=?",
						new Object[] { rs.getString("cov_indicators") }, rs2 -> {

							if (rs2.next()) {
								temp.setIndicator_val(rs2.getString("indicator_name"));
							}
							return "";
						});

				temp.setTime_0(rs.getString("time_zero"));
				temp.setTime_1(rs.getString("time_one"));
				temp.setTime_2(rs.getString("time_three"));
				temp.setTime_3(rs.getString("time_four"));
				obj.setTotal_coverage_indi_timezero_date(rs.getString("timezero_date"));
				obj.setTotal_coverage_indi_timeone_date(rs.getString("timeone_date"));
				obj.setTotal_coverage_indi_timetwo_date(rs.getString("timetwo_date"));
				obj.setTotal_coverage_indi_timethree_date(rs.getString("timethree_date"));

				list_coverage_indicators_arr.add(temp);

//				obj.setCov_indicators(rs.getString("cov_indicators"));
//				obj.setCi_source(rs.getString("ci_source"));
//				obj.setTime_zero(rs.getString("time_zero"));
//				obj.setTime_one(rs.getString("time_one"));
//				obj.setTime_three(rs.getString("time_three"));
//				obj.setTime_four(rs.getString("time_four"));
//				obj.setTimezero_date(rs.getString("timezero_date"));
//				obj.setTimeone_date(rs.getString("timeone_date"));
//				obj.setTimetwo_date(rs.getString("timetwo_date"));
//				obj.setTimethree_date(rs.getString("timethree_date"));
			}
			/* We can also return any variable-data from here but not used currently */
			return str;
		});

		obj.setTotal_coverage_indi(list_coverage_indicators_arr);

//		SELECT  form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,
//		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, 
//		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  
//		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  
//		where dev_action_plan_action_points.district_id='1' and dev_action_plan_action_points.cycle_id='1' and dev_action_plan_action_points.financial_year='2019' and 
//		form_3_1_action_part_engagement_action_req.action_part='Workforce' order by form_3_1_action_part_engagement_action_req.action_req_id asc

//		String sql4 = "SELECT  form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,"
//				+ "		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, "
//				+ "		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  "
//				+ "		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  "
//				+ "		where dev_action_plan_action_points.district_id=? and dev_action_plan_action_points.cycle_id=? and dev_action_plan_action_points.financial_year=? and "
//				+ "		form_3_1_action_part_engagement_action_req.action_part='Service delivery' order by form_3_1_action_part_engagement_action_req.action_req_id asc";
//		Object[] params4 = new Object[] { district_id, cycle_id, year };
//
//		String service_act_point = jdbcTemplate.query(sql4, params4, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				str = rs.getString("action_point_name");
//			}
//
//			return str;
//		});
//
////		INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, 
////		`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`, 
////		`record_created`) VALUES (60,'101','753','1','1','1', '1','1','2000-12-31');
//
//		String sql5 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
//		Object[] params5 = new Object[] { district_id, cycle_id, year, p_key, service_act_point };
//
//		jdbcTemplate.query(sql5, params5, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				// select * from dev_action_plan_indicators where
//				// action_point_id='".$get_data_form5[$c]["action_point_name"]."' ORDER BY
//				// action_point_id ASC
//				obj.setService_act_point(rs.getString("act_point"));
//				obj.setService_act_indicators(rs.getString("act_indicators"));
//				obj.setService_progress_indicators(rs.getString("progress_indicators"));
//			}
//
//			return str;
//		});

		String sql4 = "SELECT  form_3_1_action_part_engagement_action_req.form_3_1_action_part_engagement_nm_details_pkey, form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,"
				+ "		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, "
				+ "		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  "
				+ "		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  "
				+ "		where dev_action_plan_action_points.district_id=? and dev_action_plan_action_points.cycle_id=? and dev_action_plan_action_points.financial_year=? and "
				+ "		form_3_1_action_part_engagement_action_req.action_part='Service delivery' order by form_3_1_action_part_engagement_action_req.action_req_id asc";
		Object[] params4 = new Object[] { district_id, cycle_id, year };

		List<Form5FollowUpActionCommonArray> list_service_action_arr = new ArrayList<>();

		String service_act_point = jdbcTemplate.query(sql4, params4, rs -> {

			String service_act_point_inner = "";
			while (rs.next()) {
				service_act_point_inner = rs.getString("action_point_name");

				Form5FollowUpActionCommonArray temp = new Form5FollowUpActionCommonArray();
				temp.setPerson_responsible(rs.getString("person_responsible"));
				temp.setAction_req_id(rs.getString("action_point_name"));
				temp.setDocument_action_required(rs.getString("action_required"));
				temp.setTarget("");
				temp.setForm_3_1_action_part_engagement_nm_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				temp.setSel_stakeholder("");
				temp.setTimeline(rs.getString("target"));

				String sql_inner1 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
				Object[] params_inner1 = new Object[] { district_id, cycle_id, year, p_key, service_act_point_inner };

				jdbcTemplate.query(sql_inner1, params_inner1, rs2 -> {
					while (rs2.next()) {
						temp.setStatus(rs2.getString("status"));
						temp.setRevised_timeline(rs2.getString("revised_timeline"));
						temp.setChange_in_responsibility(rs2.getString("change_in_responsibility"));
					}

					return "";
				});

				String sql_inner2 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
				Object[] params_inner2 = new Object[] { district_id, cycle_id, year, p_key, service_act_point_inner };

				List<Form5FollowUpIndicatorsInfo> sel_action_sel_indi = new ArrayList<>();

				jdbcTemplate.query(sql_inner2, params_inner2, rs3 -> {

					while (rs3.next()) {

						Form5FollowUpIndicatorsInfo obj2 = new Form5FollowUpIndicatorsInfo();

						obj2.setDev_indicator_id(rs3.getString("act_indicators"));
						obj2.setProgress_indicators(rs3.getString("progress_indicators"));

						jdbcTemplate.query("SELECt * FROM `dev_action_plan_indicators` where `dev_indicator_id`=?",
								new Object[] { rs3.getString("act_indicators") }, rs4 -> {
									while (rs4.next()) {
										obj2.setIndicator_val(rs4.getString("indicator_name"));
										obj2.setIndicator_id(rs4.getString("indicator_id"));
										obj2.setTarget(rs4.getString("target_value"));
										obj2.setArea_name(rs4.getString("area_name"));
										obj2.setArea_id(rs4.getString("area_id"));
									}
									return "";
								});

						sel_action_sel_indi.add(obj2);
					}

					return "";
				});

				temp.setSel_indicators(sel_action_sel_indi);

				list_service_action_arr.add(temp);
			}
			
			return service_act_point_inner;
		});

		obj.setService_action_arr(list_service_action_arr);
		
				
		String sql6 = "SELECT   form_3_1_action_part_engagement_action_req.form_3_1_action_part_engagement_nm_details_pkey,  form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,"
				+ "		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, "
				+ "		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  "
				+ "		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  "
				+ "		where dev_action_plan_action_points.district_id=? and dev_action_plan_action_points.cycle_id=? and dev_action_plan_action_points.financial_year=? and "
				+ "		form_3_1_action_part_engagement_action_req.action_part='Workforce' order by form_3_1_action_part_engagement_action_req.action_req_id asc";
		Object[] params6 = new Object[] { district_id, cycle_id, year };

		List<Form5FollowUpActionCommonArray> list_workforce_action_arr = new ArrayList<>();

		String workforce_act_point = jdbcTemplate.query(sql6, params6, rs -> {

			String workforce_act_point_inner = "";
			while (rs.next()) {
				workforce_act_point_inner = rs.getString("action_point_name");

				Form5FollowUpActionCommonArray temp = new Form5FollowUpActionCommonArray();
				temp.setPerson_responsible(rs.getString("person_responsible"));
				temp.setAction_req_id(rs.getString("action_point_name"));
				temp.setDocument_action_required(rs.getString("action_required"));
				temp.setTarget("");
				temp.setForm_3_1_action_part_engagement_nm_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				temp.setSel_stakeholder("");
				temp.setTimeline(rs.getString("target"));

				String sql_inner1 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
				Object[] params_inner1 = new Object[] { district_id, cycle_id, year, p_key, workforce_act_point_inner };

				jdbcTemplate.query(sql_inner1, params_inner1, rs2 -> {
					while (rs2.next()) {
						temp.setStatus(rs2.getString("status"));
						temp.setRevised_timeline(rs2.getString("revised_timeline"));
						temp.setChange_in_responsibility(rs2.getString("change_in_responsibility"));
					}

					return "";
				});

				String sql_inner2 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
				Object[] params_inner2 = new Object[] { district_id, cycle_id, year, p_key, workforce_act_point_inner };

				List<Form5FollowUpIndicatorsInfo> sel_action_sel_indi = new ArrayList<>();

				jdbcTemplate.query(sql_inner2, params_inner2, rs3 -> {

					while (rs3.next()) {

						Form5FollowUpIndicatorsInfo obj2 = new Form5FollowUpIndicatorsInfo();

						obj2.setDev_indicator_id(rs3.getString("act_indicators"));
						obj2.setProgress_indicators(rs3.getString("progress_indicators"));

						jdbcTemplate.query("SELECt * FROM `dev_action_plan_indicators` where `dev_indicator_id`=?",
								new Object[] { rs3.getString("act_indicators") }, rs4 -> {
									while (rs4.next()) {
										obj2.setIndicator_val(rs4.getString("indicator_name"));
										obj2.setIndicator_id(rs4.getString("indicator_id"));
										obj2.setTarget(rs4.getString("target_value"));
										obj2.setArea_name(rs4.getString("area_name"));
										obj2.setArea_id(rs4.getString("area_id"));
									}
									return "";
								});

						sel_action_sel_indi.add(obj2);
					}

					return "";
				});

				temp.setSel_indicators(sel_action_sel_indi);

				list_workforce_action_arr.add(temp);
			}

			return workforce_act_point_inner;
		});

		obj.setWorkforce_action_arr(list_workforce_action_arr);

//		String workforce_act_point = jdbcTemplate.query(sql6, params6, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				str = rs.getString("action_point_name");
//			}
//
//			return str;
//		});

//	INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, 
//	`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`, 
//	`record_created`) VALUES (60,'101','753','1','1','1', '1','1','2000-12-31');

//		String sql7 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
//		Object[] params7 = new Object[] { district_id, cycle_id, year, p_key, workforce_act_point };
//
//		jdbcTemplate.query(sql7, params7, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				// select * from dev_action_plan_indicators where
//				// action_point_id='".$get_data_form5[$c]["action_point_name"]."' ORDER BY
//				// action_point_id ASC
//				obj.setWorkforce_act_point(rs.getString("act_point"));
//				obj.setWorkforce_act_indicators(rs.getString("act_indicators"));
//				obj.setWorkforce_progress_indicators(rs.getString("progress_indicators"));
//			}
//
//			return str;
//		});

		String sql8 = "SELECT   form_3_1_action_part_engagement_action_req.form_3_1_action_part_engagement_nm_details_pkey,  form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,"
				+ "		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, "
				+ "		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  "
				+ "		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  "
				+ "		where dev_action_plan_action_points.district_id=? and dev_action_plan_action_points.cycle_id=? and dev_action_plan_action_points.financial_year=? and "
				+ "		form_3_1_action_part_engagement_action_req.action_part='Supplies & technology' order by form_3_1_action_part_engagement_action_req.action_req_id asc";
		Object[] params8 = new Object[] { district_id, cycle_id, year };

		List<Form5FollowUpActionCommonArray> list_supplies_action_arr = new ArrayList<>();

		String supplies_act_point = jdbcTemplate.query(sql8, params8, rs -> {

			String supplies_act_point_inner = "";
			while (rs.next()) {
				supplies_act_point_inner = rs.getString("action_point_name");

				Form5FollowUpActionCommonArray temp = new Form5FollowUpActionCommonArray();
				temp.setPerson_responsible(rs.getString("person_responsible"));
				temp.setAction_req_id(rs.getString("action_point_name"));
				temp.setDocument_action_required(rs.getString("action_required"));
				temp.setTarget("");
				temp.setForm_3_1_action_part_engagement_nm_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				temp.setSel_stakeholder("");
				temp.setTimeline(rs.getString("target"));

				String sql_inner1 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
				Object[] params_inner1 = new Object[] { district_id, cycle_id, year, p_key, supplies_act_point_inner };

				jdbcTemplate.query(sql_inner1, params_inner1, rs2 -> {
					while (rs2.next()) {
						temp.setStatus(rs2.getString("status"));
						temp.setRevised_timeline(rs2.getString("revised_timeline"));
						temp.setChange_in_responsibility(rs2.getString("change_in_responsibility"));
					}

					return "";
				});

				String sql_inner2 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
				Object[] params_inner2 = new Object[] { district_id, cycle_id, year, p_key, supplies_act_point_inner };

				List<Form5FollowUpIndicatorsInfo> sel_action_sel_indi = new ArrayList<>();

				jdbcTemplate.query(sql_inner2, params_inner2, rs3 -> {

					while (rs3.next()) {

						Form5FollowUpIndicatorsInfo obj2 = new Form5FollowUpIndicatorsInfo();

						obj2.setDev_indicator_id(rs3.getString("act_indicators"));
						obj2.setProgress_indicators(rs3.getString("progress_indicators"));

						jdbcTemplate.query("SELECt * FROM `dev_action_plan_indicators` where `dev_indicator_id`=?",
								new Object[] { rs3.getString("act_indicators") }, rs4 -> {
									while (rs4.next()) {
										obj2.setIndicator_val(rs4.getString("indicator_name"));
										obj2.setIndicator_id(rs4.getString("indicator_id"));
										obj2.setTarget(rs4.getString("target_value"));
										obj2.setArea_name(rs4.getString("area_name"));
										obj2.setArea_id(rs4.getString("area_id"));
									}
									return "";
								});

						sel_action_sel_indi.add(obj2);
					}

					return "";
				});

				temp.setSel_indicators(sel_action_sel_indi);

				list_supplies_action_arr.add(temp);
			}

			return supplies_act_point_inner;
		});

		obj.setSupplies_action_arr(list_supplies_action_arr);

//		String supplies_act_point = jdbcTemplate.query(sql8, params8, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				str = rs.getString("action_point_name");
//			}
//
//			return str;
//		});

//INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, 
//`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`, 
//`record_created`) VALUES (60,'101','753','1','1','1', '1','1','2000-12-31');

		String sql9 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
		Object[] params9 = new Object[] { district_id, cycle_id, year, p_key, supplies_act_point };

//		jdbcTemplate.query(sql9, params9, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				// select * from dev_action_plan_indicators where
//				// action_point_id='".$get_data_form5[$c]["action_point_name"]."' ORDER BY
//				// action_point_id ASC
//				obj.setSupplies_act_point(rs.getString("act_point"));
//				obj.setSupplies_act_indicators(rs.getString("act_indicators"));
//				obj.setSupplies_act_progress_indicators(rs.getString("progress_indicators"));
//			}
//
//			return str;
//		});

		String sql10 = "SELECT   form_3_1_action_part_engagement_action_req.form_3_1_action_part_engagement_nm_details_pkey,  form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,"
				+ "		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, "
				+ "		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  "
				+ "		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  "
				+ "		where dev_action_plan_action_points.district_id=? and dev_action_plan_action_points.cycle_id=? and dev_action_plan_action_points.financial_year=? and "
				+ "		form_3_1_action_part_engagement_action_req.action_part='Health information' order by form_3_1_action_part_engagement_action_req.action_req_id asc";
		Object[] params10 = new Object[] { district_id, cycle_id, year };

		List<Form5FollowUpActionCommonArray> list_health_action_arr = new ArrayList<>();

		String health_act_point = jdbcTemplate.query(sql10, params10, rs -> {

			String health_act_point_inner = "";
			while (rs.next()) {
				health_act_point_inner = rs.getString("action_point_name");

				Form5FollowUpActionCommonArray temp = new Form5FollowUpActionCommonArray();
				temp.setPerson_responsible(rs.getString("person_responsible"));
				temp.setAction_req_id(rs.getString("action_point_name"));
				temp.setDocument_action_required(rs.getString("action_required"));
				temp.setTarget("");
				temp.setForm_3_1_action_part_engagement_nm_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				temp.setSel_stakeholder("");
				temp.setTimeline(rs.getString("target"));

				String sql_inner1 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
				Object[] params_inner1 = new Object[] { district_id, cycle_id, year, p_key, health_act_point_inner };

				jdbcTemplate.query(sql_inner1, params_inner1, rs2 -> {
					while (rs2.next()) {
						temp.setStatus(rs2.getString("status"));
						temp.setRevised_timeline(rs2.getString("revised_timeline"));
						temp.setChange_in_responsibility(rs2.getString("change_in_responsibility"));
					}

					return "";
				});

				String sql_inner2 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
				Object[] params_inner2 = new Object[] { district_id, cycle_id, year, p_key, health_act_point_inner };

				List<Form5FollowUpIndicatorsInfo> sel_action_sel_indi = new ArrayList<>();

				jdbcTemplate.query(sql_inner2, params_inner2, rs3 -> {

					while (rs3.next()) {

						Form5FollowUpIndicatorsInfo obj2 = new Form5FollowUpIndicatorsInfo();

						obj2.setDev_indicator_id(rs3.getString("act_indicators"));
						obj2.setProgress_indicators(rs3.getString("progress_indicators"));

						jdbcTemplate.query("SELECt * FROM `dev_action_plan_indicators` where `dev_indicator_id`=?",
								new Object[] { rs3.getString("act_indicators") }, rs4 -> {
									while (rs4.next()) {
										obj2.setIndicator_val(rs4.getString("indicator_name"));
										obj2.setIndicator_id(rs4.getString("indicator_id"));
										obj2.setTarget(rs4.getString("target_value"));
										obj2.setArea_name(rs4.getString("area_name"));
										obj2.setArea_id(rs4.getString("area_id"));
									}
									return "";
								});

						sel_action_sel_indi.add(obj2);
					}

					return "";
				});

				temp.setSel_indicators(sel_action_sel_indi);

				list_health_action_arr.add(temp);
			}

			return health_act_point_inner;
		});

		obj.setHealth_action_arr(list_health_action_arr);

//		String health_act_point = jdbcTemplate.query(sql10, params10, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				str = rs.getString("action_point_name");
//			}
//
//			return str;
//		});

//INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, 
//`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`, 
//`record_created`) VALUES (60,'101','753','1','1','1', '1','1','2000-12-31');

		String sql11 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
		Object[] params11 = new Object[] { district_id, cycle_id, year, p_key, health_act_point };

//		jdbcTemplate.query(sql11, params11, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				// select * from dev_action_plan_indicators where
//				// action_point_id='".$get_data_form5[$c]["action_point_name"]."' ORDER BY
//				// action_point_id ASC
//				obj.setHealth_act_point(rs.getString("act_point"));
//				obj.setHealth_act_indicators(rs.getString("act_indicators"));
//				obj.setHealth_progress_indicators(rs.getString("progress_indicators"));
//			}
//
//			return str;
//		});

		String sql12 = "SELECT   form_3_1_action_part_engagement_action_req.form_3_1_action_part_engagement_nm_details_pkey,  form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,"
				+ "		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, "
				+ "		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  "
				+ "		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  "
				+ "		where dev_action_plan_action_points.district_id=? and dev_action_plan_action_points.cycle_id=? and dev_action_plan_action_points.financial_year=? and "
				+ "		form_3_1_action_part_engagement_action_req.action_part='Finance' order by form_3_1_action_part_engagement_action_req.action_req_id asc";
		Object[] params12 = new Object[] { district_id, cycle_id, year };

		List<Form5FollowUpActionCommonArray> list_finance_action_arr = new ArrayList<>();

		String finance_act_point = jdbcTemplate.query(sql12, params12, rs -> {

			String finance_act_point_inner = "";
			while (rs.next()) {
				finance_act_point_inner = rs.getString("action_point_name");

				Form5FollowUpActionCommonArray temp = new Form5FollowUpActionCommonArray();
				temp.setPerson_responsible(rs.getString("person_responsible"));
				temp.setAction_req_id(rs.getString("action_point_name"));
				temp.setDocument_action_required(rs.getString("action_required"));
				temp.setTarget("");
				temp.setForm_3_1_action_part_engagement_nm_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				temp.setSel_stakeholder("");
				temp.setTimeline(rs.getString("target"));

				String sql_inner1 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
				Object[] params_inner1 = new Object[] { district_id, cycle_id, year, p_key, finance_act_point_inner };

				jdbcTemplate.query(sql_inner1, params_inner1, rs2 -> {
					while (rs2.next()) {
						temp.setStatus(rs2.getString("status"));
						temp.setRevised_timeline(rs2.getString("revised_timeline"));
						temp.setChange_in_responsibility(rs2.getString("change_in_responsibility"));
					}

					return "";
				});

				String sql_inner2 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
				Object[] params_inner2 = new Object[] { district_id, cycle_id, year, p_key, finance_act_point_inner };

				List<Form5FollowUpIndicatorsInfo> sel_action_sel_indi = new ArrayList<>();

				jdbcTemplate.query(sql_inner2, params_inner2, rs3 -> {

					while (rs3.next()) {

						Form5FollowUpIndicatorsInfo obj2 = new Form5FollowUpIndicatorsInfo();

						obj2.setDev_indicator_id(rs3.getString("act_indicators"));
						obj2.setProgress_indicators(rs3.getString("progress_indicators"));

						jdbcTemplate.query("SELECt * FROM `dev_action_plan_indicators` where `dev_indicator_id`=?",
								new Object[] { rs3.getString("act_indicators") }, rs4 -> {
									while (rs4.next()) {
										obj2.setIndicator_val(rs4.getString("indicator_name"));
										obj2.setIndicator_id(rs4.getString("indicator_id"));
										obj2.setTarget(rs4.getString("target_value"));
										obj2.setArea_name(rs4.getString("area_name"));
										obj2.setArea_id(rs4.getString("area_id"));
									}
									return "";
								});

						sel_action_sel_indi.add(obj2);
					}

					return "";
				});

				temp.setSel_indicators(sel_action_sel_indi);

				list_finance_action_arr.add(temp);
			}

			return finance_act_point_inner;
		});

		obj.setFinance_action_arr(list_finance_action_arr);

//		String finance_act_point = jdbcTemplate.query(sql12, params12, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				str = rs.getString("action_point_name");
//			}
//
//			return str;
//		});

//INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, 
//`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`, 
//`record_created`) VALUES (60,'101','753','1','1','1', '1','1','2000-12-31');

		String sql13 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
		Object[] params13 = new Object[] { district_id, cycle_id, year, p_key, finance_act_point };

//		jdbcTemplate.query(sql13, params13, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				// select * from dev_action_plan_indicators where
//				// action_point_id='".$get_data_form5[$c]["action_point_name"]."' ORDER BY
//				// action_point_id ASC
//				obj.setFinance_act_point(rs.getString("act_point"));
//				obj.setFinance_act_indicators(rs.getString("act_indicators"));
//				obj.setFinance_progress_indicators(rs.getString("progress_indicators"));
//			}
//
//			return str;
//		});

		String sql14 = "SELECT   form_3_1_action_part_engagement_action_req.form_3_1_action_part_engagement_nm_details_pkey,  form_3_1_action_part_engagement_action_req.form3_sl_no,form_3_1_action_part_engagement_action_req.action_required,"
				+ "		dev_action_plan_action_points.`action_point_name`, dev_action_plan_action_points.`person_responsible`, "
				+ "		dev_action_plan_action_points.`target` FROM `dev_action_plan_action_points`  "
				+ "		inner join form_3_1_action_part_engagement_action_req on dev_action_plan_action_points.action_point_name=form_3_1_action_part_engagement_action_req.action_req_id  "
				+ "		where dev_action_plan_action_points.district_id=? and dev_action_plan_action_points.cycle_id=? and dev_action_plan_action_points.financial_year=? and "
				+ "		form_3_1_action_part_engagement_action_req.action_part='Policy/governance' order by form_3_1_action_part_engagement_action_req.action_req_id asc";
		Object[] params14 = new Object[] { district_id, cycle_id, year };

		List<Form5FollowUpActionCommonArray> list_policy_action_arr = new ArrayList<>();

		String policy_act_point = jdbcTemplate.query(sql14, params14, rs -> {

			String policy_act_point_inner = "";
			while (rs.next()) {
				policy_act_point_inner = rs.getString("action_point_name");

				Form5FollowUpActionCommonArray temp = new Form5FollowUpActionCommonArray();
				temp.setPerson_responsible(rs.getString("person_responsible"));
				temp.setAction_req_id(rs.getString("action_point_name"));
				temp.setDocument_action_required(rs.getString("action_required"));
				temp.setTarget("");
				temp.setForm_3_1_action_part_engagement_nm_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				temp.setSel_stakeholder("");
				temp.setTimeline(rs.getString("target"));

				String sql_inner1 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
				Object[] params_inner1 = new Object[] { district_id, cycle_id, year, p_key, policy_act_point_inner };

				jdbcTemplate.query(sql_inner1, params_inner1, rs2 -> {
					while (rs2.next()) {
						temp.setStatus(rs2.getString("status"));
						temp.setRevised_timeline(rs2.getString("revised_timeline"));
						temp.setChange_in_responsibility(rs2.getString("change_in_responsibility"));
					}

					return "";
				});

				String sql_inner2 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
				Object[] params_inner2 = new Object[] { district_id, cycle_id, year, p_key, policy_act_point_inner };

				List<Form5FollowUpIndicatorsInfo> sel_action_sel_indi = new ArrayList<>();

				jdbcTemplate.query(sql_inner2, params_inner2, rs3 -> {

					while (rs3.next()) {

						Form5FollowUpIndicatorsInfo obj2 = new Form5FollowUpIndicatorsInfo();

						obj2.setDev_indicator_id(rs3.getString("act_indicators"));
						obj2.setProgress_indicators(rs3.getString("progress_indicators"));

						jdbcTemplate.query("SELECt * FROM `dev_action_plan_indicators` where `dev_indicator_id`=?",
								new Object[] { rs3.getString("act_indicators") }, rs4 -> {
									while (rs4.next()) {
										obj2.setIndicator_val(rs4.getString("indicator_name"));
										obj2.setIndicator_id(rs4.getString("indicator_id"));
										obj2.setTarget(rs4.getString("target_value"));
										obj2.setArea_name(rs4.getString("area_name"));
										obj2.setArea_id(rs4.getString("area_id"));
									}
									return "";
								});

						sel_action_sel_indi.add(obj2);
					}

					return "";
				});

				temp.setSel_indicators(sel_action_sel_indi);

				list_policy_action_arr.add(temp);
			}

			return policy_act_point_inner;
		});

		obj.setPolicy_action_arr(list_policy_action_arr);

//		String policy_act_point = jdbcTemplate.query(sql14, params14, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				str = rs.getString("action_point_name");
//			}
//
//			return str;
//		});

//INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, 
//`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`, 
//`record_created`) VALUES (60,'101','753','1','1','1', '1','1','2000-12-31');

		String sql15 = "SELECt * FROM `followup_indicator_progress` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `followup_id`=? and `act_point`=?";
		Object[] params15 = new Object[] { district_id, cycle_id, year, p_key, policy_act_point };

//		jdbcTemplate.query(sql15, params15, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				// select * from dev_action_plan_indicators where
//				// action_point_id='".$get_data_form5[$c]["action_point_name"]."' ORDER BY
//				// action_point_id ASC
//				obj.setPolicy_act_point(rs.getString("act_point"));
//				obj.setPolicy_act_indicators(rs.getString("act_indicators"));
//				obj.setPolicy_progress_indicators(rs.getString("progress_indicators"));
//			}
//
//			return str;
//		});

		// INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`,
//		`revised_timeline`, `change_in_responsibility`,`district_id`, 
//		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (:follow_id,:act_point,:status,
//				:revised_timeline,:change_in_responsibility,:district_id,:cycle_id,:financial_year,
//				:user_id,:record_created)

//		String sql16 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
//		Object[] params16 = new Object[] { district_id, cycle_id, year, p_key, service_act_point };
//
//		jdbcTemplate.query(sql16, params16, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setService_act_point(rs.getString("act_point"));
//				obj.setService_status(rs.getString("status"));
//				obj.setService_revised_timeline(rs.getString("revised_timeline"));
//				obj.setService_change_in_responsibility(rs.getString("change_in_responsibility"));
//			}
//
//			return str;
//		});
//
//		String sql17 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
//		Object[] params17 = new Object[] { district_id, cycle_id, year, p_key, workforce_act_point };
//
//		jdbcTemplate.query(sql17, params17, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setWorkforce_act_point(rs.getString("act_point"));
//				obj.setWorkforce_status(rs.getString("status"));
//				obj.setWorkforce_revised_timeline(rs.getString("revised_timeline"));
//				obj.setWorkforce_change_in_responsibility(rs.getString("change_in_responsibility"));
//			}
//
//			return str;
//		});
//
//		String sql18 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
//		Object[] params18 = new Object[] { district_id, cycle_id, year, p_key, supplies_act_point };
//
//		jdbcTemplate.query(sql18, params18, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setSupplies_act_point(rs.getString("act_point"));
//				obj.setSupplies_status(rs.getString("status"));
//				obj.setSupplies_revised_timeline(rs.getString("revised_timeline"));
//				obj.setSupplies_change_in_responsibility(rs.getString("change_in_responsibility"));
//			}
//
//			return str;
//		});
//
//		String sql19 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
//		Object[] params19 = new Object[] { district_id, cycle_id, year, p_key, health_act_point };
//
//		jdbcTemplate.query(sql19, params19, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setHealth_act_point(rs.getString("act_point"));
//				obj.setHealth_status(rs.getString("status"));
//				obj.setHealth_revised_timeline(rs.getString("revised_timeline"));
//				obj.setHealth_change_in_responsibility(rs.getString("change_in_responsibility"));
//			}
//
//			return str;
//		});
//
//		String sql20 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
//		Object[] params20 = new Object[] { district_id, cycle_id, year, p_key, finance_act_point };
//
//		jdbcTemplate.query(sql20, params20, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setFinance_act_point(rs.getString("act_point"));
//				obj.setFinance_status(rs.getString("status"));
//				obj.setFinance_revised_timeline(rs.getString("revised_timeline"));
//				obj.setFinance_change_in_responsibility(rs.getString("change_in_responsibility"));
//			}
//
//			return str;
//		});
//
//		String sql21 = "SELECt * FROM `followup_actionind_status` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `follow_id`=? and `act_point`=?";
//		Object[] params21 = new Object[] { district_id, cycle_id, year, p_key, policy_act_point };
//
//		jdbcTemplate.query(sql21, params21, rs -> {
//
//			String str = "";
//			while (rs.next()) {
//				obj.setPolicy_act_point(rs.getString("act_point"));
//				obj.setPolicy_status(rs.getString("status"));
//				obj.setPolicy_revised_timeline(rs.getString("revised_timeline"));
//				obj.setPolicy_change_in_responsibility(rs.getString("change_in_responsibility"));
//			}
//
//			return str;
//		});

		return obj;
	}
	
	public void sendForm5FollowUpSaveForExistingDistrictCycleYear(String district_id,String cycle_id,
			String year,String country,String province,Form5FollowUpSendAllDataBean response ) {


		List<Form5FollowUpPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<Form5PartAStakesMeetingDataBean> sendlist2 = new ArrayList<>();
		List<Form5PartADIPHCycleIndicatorComparison> sendlist3 = new ArrayList<>();
		List<Form5PartBActionPlanIndicatorFollowUpStatus> sendlist4 = new ArrayList<>();
		List<Form5PartBIndicatorProgress> sendlist5 = new ArrayList<>();

		String sql1 = "SELECT form5followup.`followup_id`,   form5followup.`date_of_meeting`,  form5followup. `venue_of_meeting`,"
				+ "  form5followup. `chairperson_of_meeting`,    form5followup.`chairperson_of_meeting_others`, "
				+ "  form5followup.`theme_leader`,    form5followup.`no_of_meetings_resp_team`,  "
				+ "  form5followup.`district_id`,  form5followup. `cycle_id`,    form5followup.`financial_year`,  "
				+ "  form5followup.`user_id`,  form5followup.`record_created`,  form5followup.`completed` FROM  `folloup_action_plan_tbl` form5followup  "
				+ "	where  form5followup.`district_id`=? and form5followup. `cycle_id`=? and    form5followup.`financial_year`=?";

		Object[] params1 = new Object[] {district_id, cycle_id, year };

		sendlist1 = jdbcTemplate.query(sql1, params1, rs -> {

			String primary_key = "";

			List<Form5FollowUpPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form5FollowUpPrimaryTableDataBean obj = new Form5FollowUpPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("followup_id"));
				obj.setMeetingdate(rs.getString("date_of_meeting"));
				obj.setMeetingvenue(rs.getString("venue_of_meeting"));
				
				/*-----------------------------------------------------------------------*/

				String temp_sql = "SELECt * FROM `form_3_1_verify` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] temp_param = new Object[] { rs.getString("district_id"), rs.getString("cycle_id"),
						rs.getString("financial_year") };

				String form3autoid = jdbcTemplate.query(temp_sql, temp_param, temp_rs -> {

					String val = "0";
					while (temp_rs.next()) {

						val = temp_rs.getString("form_3_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return val;
				});
				
				/*-----------------------------------------------------------------------*/

				obj.setThemeleaderid(form3autoid);
				obj.setNoofmeeting(rs.getString("no_of_meetings_resp_team"));
				obj.setChairpersonid(rs.getString("chairperson_of_meeting"));
				obj.setOtherchairperson(rs.getString("chairperson_of_meeting_others"));
				//obj.setCountry_id(rs.getString("country_id"));
				//obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");
				obj.setCompleted(rs.getString("completed"));
				
				obj.setCountry_id(country);
				obj.setProvince_id(province);

				templist.add(obj);

				primary_key = rs.getString("followup_id");
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql2 = "SELECT form5followup.`followup_parta_id`,    form5followup.`followup_id`,    form5followup.`meeting_no`,  "
				+ " form5followup.`meeting_date`,    form5followup.`stakeholder_meeting`,    form5followup.`no_of_participants`, "
				+ "  form5followup. `district_id`,    form5followup.`cycle_id`,    form5followup.`financial_year`,  "
				+ "   form5followup.`user_id`, form5followup.`record_created`  FROM `folloup_action_plan_tbl_parta` form5followup  "
				+ "  where form5followup. `district_id`=?  and    form5followup.`cycle_id`=?   and  form5followup.`financial_year`=? ";

		Object[] params2 = new Object[] { district_id, cycle_id, year  };

		sendlist2 = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form5PartAStakesMeetingDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartAStakesMeetingDataBean obj = new Form5PartAStakesMeetingDataBean();

				obj.setAuto_id(rs.getString("followup_parta_id"));
				obj.setFollowup_id_server(rs.getString("followup_id"));
				obj.setMeeting_no(rs.getString("meeting_no"));
				obj.setMeeting_date(rs.getString("meeting_date"));
				obj.setNo_of_participants(rs.getString("no_of_participants"));
				obj.setStakeholder_meeting(rs.getString("stakeholder_meeting"));
				//obj.setCountry_id(rs.getString("country_id"));
				//obj.setProvince_id(rs.getString("state_id"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql3 = "SELECT form5followup.`form5_time_id`,    form5followup.`followup_id`,    form5followup.`cov_indicators`,  form5followup.`ci_source`,    form5followup.`time_zero`,    form5followup.`time_one`,    form5followup.`time_three`,   form5followup.`time_four`,  "
				+ "  form5followup.`timezero_date`,    form5followup.`timeone_date`,    form5followup.`timetwo_date`, "
				+ " form5followup.`timethree_date`,    form5followup.`district_id`,    form5followup.`cycle_id`,  "
				+ "  form5followup.`financial_year`,  form5followup.`user_id`,    form5followup.`record_created` FROM `followup_action_plan_time`  "
				+ "  form5followup   where form5followup.`district_id`=?  and    form5followup.`cycle_id`=?  and    form5followup.`financial_year`=?";

		Object[] params3 = new Object[] { district_id, cycle_id, year  };
		
		
		sendlist3 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form5PartADIPHCycleIndicatorComparison> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartADIPHCycleIndicatorComparison obj = new Form5PartADIPHCycleIndicatorComparison();

				obj.setAuto_id(rs.getString("form5_time_id"));
				obj.setFollowup_id_server(rs.getString("followup_id"));
				obj.setIndicator_id(rs.getString("cov_indicators"));
				obj.setTime_zero(rs.getString("time_zero"));
				obj.setTime_one(rs.getString("time_one"));
				obj.setTime_two(rs.getString("time_three"));
				obj.setTime_three(rs.getString("time_four"));
				obj.setTimezero_date(rs.getString("timezero_date"));
				obj.setTimeone_date(rs.getString("timeone_date"));
				obj.setTimetwo_date(rs.getString("timetwo_date"));
				obj.setTimethree_date(rs.getString("timethree_date"));
				//obj.setCountry_id(rs.getString("country_id"));
				//obj.setProvince_id(rs.getString("state_id"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		
		
		String sql4 = "SELECT form5followup.`act_ind_stat_id`,    form5followup.`follow_id`,  form5followup. `act_point`,  "
				+ "   form5followup.`status`,    form5followup.`revised_timeline`,   form5followup.`change_in_responsibility`,   "
				+ "   form5followup.`district_id`,    form5followup.`cycle_id`,   form5followup.`financial_year`,    form5followup.`user_id`,  "
				+ "   form5followup.`record_created`   FROM  `followup_actionind_status`  form5followup  "
				+ "   where form5followup.`district_id`=?  and    form5followup.`cycle_id`=?  and   form5followup.`financial_year`=?";

		Object[] params4 = new Object[] { district_id, cycle_id, year  };

		sendlist4 = jdbcTemplate.query(sql4, params4, rs -> {
			List<Form5PartBActionPlanIndicatorFollowUpStatus> templist = new ArrayList<>();

			while (rs.next()) {

				Form5PartBActionPlanIndicatorFollowUpStatus obj = new Form5PartBActionPlanIndicatorFollowUpStatus();
				obj.setAuto_id(rs.getString("act_ind_stat_id"));
				obj.setFollowup_id_server(rs.getString("follow_id"));
				obj.setAction_point_id(rs.getString("act_point"));
				obj.setStatus(rs.getString("status"));
				obj.setRevised_timeline(rs.getString("revised_timeline"));
				obj.setChange_in_responsibility(rs.getString("change_in_responsibility"));
				//obj.setCountry_id(rs.getString("country_id"));
				//obj.setProvince_id(rs.getString("state_id"));
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
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql_outer = "SELECT  form5followup.`act_ind_stat_id`,   form5followup. `act_point`   FROM  `followup_actionind_status`  form5followup  where form5followup.`district_id`=? and  form5followup.`cycle_id`=? and    form5followup.`financial_year`=? ";

		Object[] params_outer = new Object[] { district_id, cycle_id, year };

		sendlist5 = jdbcTemplate.query(sql_outer, params_outer, rs_outer -> {

			List<Form5PartBIndicatorProgress> totallist = new ArrayList<>();

			while (rs_outer.next()) {

				String sql5 = "SELECT form5followup.`ind_progress_id`,    form5followup.`followup_id`,    form5followup.`act_point`, form5followup.`act_indicators`,   "
						+ "   form5followup.`progress_indicators`,    form5followup.`district_id`,  form5followup.`cycle_id`,  "
						+ "   form5followup.`financial_year`,    form5followup.`user_id`,  form5followup. `record_created`  FROM `followup_indicator_progress`   form5followup  "
						+ "    where form5followup.`district_id`=?  and  form5followup.`cycle_id`=? and    form5followup.`financial_year`='2020'  and form5followup.`act_point`=?";

				Object[] params5 = new Object[] { district_id, cycle_id, year, rs_outer.getString("act_point") };

				List<Form5PartBIndicatorProgress> againlist = jdbcTemplate.query(sql5, params5, rs -> {

					List<Form5PartBIndicatorProgress> templist = new ArrayList<>();
					while (rs.next()) {

						Form5PartBIndicatorProgress obj = new Form5PartBIndicatorProgress();

						obj.setAuto_id(rs.getString("ind_progress_id"));
						obj.setFollowup_id_server(rs.getString("followup_id"));
						obj.setFollowuppartb_id_server(rs_outer.getString("act_ind_stat_id"));
						obj.setAction_point_id(rs.getString("act_point"));
						
						/**********************Pbm here********************************/
						
						/*-----------------------------------------------------------------------*/

						String form4plan_indi_id_sql = "SELECT * FROM dev_action_plan_indicators where dev_indicator_id=? and district_id=? and cycle_id=? and financial_year=?";
						Object[] form4plan_indi_id_sql_param = new Object[] { rs.getString("act_indicators"),rs.getString("district_id"), rs.getString("cycle_id"),
								rs.getString("financial_year") };

						String form4plan_indi_id = jdbcTemplate.query(form4plan_indi_id_sql, form4plan_indi_id_sql_param, temp_rs -> {

							String val = "0";
							while (temp_rs.next()) {

								val = temp_rs.getString("indicator_id");
							}
							/* We can also return any variable-data from here but not used currently */
							return val;
						});
						
						/*-----------------------------------------------------------------------*/
						
						//obj.setIndicator_id(rs.getString("act_indicators"));
						
						obj.setIndicator_id(form4plan_indi_id);
						
						/**********************Pbm here********************************/
						
						obj.setProgress_indicators(rs.getString("progress_indicators"));
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
					/* We can also return any variable-data from here but not used currently */
					return templist;
				});

				totallist.addAll(againlist);

			}
			/* We can also return any variable-data from here but not used currently */
			return totallist;
		});

		response.setForm5followup(sendlist1);
		response.setForm5partamajorholder(sendlist2);
		response.setForm5partaindicator(sendlist3);
		
		if(sendlist4 == null) {
			sendlist4 = new ArrayList<>();
		}
		response.setForm5partb(sendlist4);
		
		if(sendlist5 == null) {
			sendlist5 = new ArrayList<>();
		}
		
		response.setForm5partbindiprogress(sendlist5);
		//return response;

	
	}

	public Form5FollowUpSendAllDataBean consumeAllForm5FollowUpSaveandUpdate(List<Form3DefineActionIDDetailsBean> mapping2, AllFormsDataFetchFromAndroidBean model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);	

		Form5FollowUpSendAllDataBean response = new Form5FollowUpSendAllDataBean();

		Form5FollowUpSendAllDataBean form5 = model.getForm5();

		List<Form5FollowUpPrimaryTableDataBean> list1 = form5.getForm5followup();
		List<Form5PartAStakesMeetingDataBean> list2 = form5.getForm5partamajorholder();
		List<Form5PartADIPHCycleIndicatorComparison> list3 = form5.getForm5partaindicator();
		List<Form5PartBActionPlanIndicatorFollowUpStatus> list4 = form5.getForm5partb();
		List<Form5PartBIndicatorProgress> list5 = form5.getForm5partbindiprogress();

		List<Form5FollowUpPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<Form5PartAStakesMeetingDataBean> sendlist2 = new ArrayList<>();
		List<Form5PartADIPHCycleIndicatorComparison> sendlist3 = new ArrayList<>();
		List<Form5PartBActionPlanIndicatorFollowUpStatus> sendlist4 = new ArrayList<>();
		List<Form5PartBIndicatorProgress> sendlist5 = new ArrayList<>();

	
		for (int index = 0; index < list1.size(); index++) {

			Form5FollowUpPrimaryTableDataBean tempobj = list1.get(index);
		

			Form5FollowUpPrimaryTableDataBean sendobj = new Form5FollowUpPrimaryTableDataBean();

			if ("APP".equals(tempobj.getDatafrom())) {

				/*-------------------------------------------*/

				String sql_check = "SELECT * FROM `folloup_action_plan_tbl`  where  `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] params_check = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
						tempobj.getYear() };

				String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

					String keyval = "";
					while (rs.next()) {
						keyval = rs.getString("followup_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return keyval;
				});

				if ("".equals(checkid)) {

				} else {
					
					sendForm5FollowUpSaveForExistingDistrictCycleYear(tempobj.getDistrict_id(),tempobj.getCycle_id(),
							tempobj.getYear(),tempobj.getCountry_id(),tempobj.getProvince_id(), response );
					
					return response;
				}

				/*-------------------------------------------*/

				int row = 0;

				String sql1 = "INSERT INTO `folloup_action_plan_tbl`(`date_of_meeting`, `venue_of_meeting`, `chairperson_of_meeting`,`chairperson_of_meeting_others`,"
						+ "				`theme_leader`, `no_of_meetings_resp_team`,  `district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "				`record_created`,`completed`) VALUES (?,?,?,?," + "						?,?,?,?,?,?,?,?)";

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
					
					
					/*-----------------------------------------------------------------------*/

					String temp_sql = "SELECt * FROM `form_3_1_verify` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
					Object[] temp_param = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
							tempobj.getYear() };

					String form3autoid = jdbcTemplate.query(temp_sql, temp_param, temp_rs -> {

						String val = "0";
						while (temp_rs.next()) {

							val = temp_rs.getString("form_3_id");
						}
						/* We can also return any variable-data from here but not used currently */
						return val;
					});
					
					/*-----------------------------------------------------------------------*/
					
					
					/*------------------------------*/
					
					String plan_sql = "SELECt * FROM `dev_action_plan_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
					Object[] plan_params = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
							tempobj.getYear() };

					String plan_theme_leader = jdbcTemplate.query(plan_sql, plan_params, rs -> {

						String theme_leader = "";
						while (rs.next()) {

							theme_leader = 	rs.getString("theme_leader");
						}
						/* We can also return any variable-data from here but not used currently */
						return theme_leader;
					});
					/*--------------------------------*/
					

					ps.setString(5, plan_theme_leader);  
					sendobj.setThemeleaderid(tempobj.getThemeleaderid());

					ps.setString(6, tempobj.getNoofmeeting());
					sendobj.setNoofmeeting(tempobj.getNoofmeeting());

					ps.setString(7, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());

					ps.setString(8, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());

					ps.setString(9, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());

					ps.setString(10, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id());

					ps.setString(11, formatedDateTime);
					sendobj.setRecordcreated(formatedDateTime);
					
					ps.setString(12, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());

					return ps;
				}, keyHolder);

				// ResultSet rs = ps.getGeneratedKeys();

				long p_key = keyHolder.getKey().longValue();

				sendobj.setAuto_id("" + keyHolder.getKey().longValue());
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(formatedDateTime);
				sendobj.setDatafrom("WEB");

				sendlist1.add(sendobj);

				

				for (int x = 0; x < list2.size(); x++) {

					Form5PartAStakesMeetingDataBean meetingobj = list2.get(x);

					Form5PartAStakesMeetingDataBean sendmeetingobj = new Form5PartAStakesMeetingDataBean();

					if (tempobj.getDistrict_id().equals(meetingobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(meetingobj.getCycle_id())
							&& tempobj.getYear().equals(meetingobj.getYear())) {

						String sql2 = "INSERT INTO `folloup_action_plan_tbl_parta`(`followup_id`, `meeting_no`, `meeting_date`,"
								+ "		`stakeholder_meeting`, `no_of_participants`, `district_id`, `cycle_id`, `financial_year`,`user_id`,"
								+ "	`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?)";

						KeyHolder keyHolder2 = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							sendmeetingobj.setFollowup_id_server("" + p_key);

							ps.setString(2, "" + meetingobj.getMeeting_no());
							sendmeetingobj.setMeeting_no("" + meetingobj.getMeeting_no());
							
//							String outputDateStr = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+meetingobj.getMeeting_date();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}

							ps.setString(3, "" + meetingobj.getMeeting_date());
							sendmeetingobj.setMeeting_date("" + meetingobj.getMeeting_date());

							ps.setString(4, "" + meetingobj.getStakeholder_meeting());
							sendmeetingobj.setStakeholder_meeting("" + meetingobj.getStakeholder_meeting());

							ps.setString(5, "" + meetingobj.getNo_of_participants());
							sendmeetingobj.setNo_of_participants("" + meetingobj.getNo_of_participants());

							ps.setString(6, "" + meetingobj.getDistrict_id());
							sendmeetingobj.setDistrict_id("" + meetingobj.getDistrict_id());

							ps.setString(7, "" + meetingobj.getCycle_id());
							sendmeetingobj.setCycle_id("" + meetingobj.getCycle_id());

							ps.setString(8, "" + meetingobj.getYear());
							sendmeetingobj.setYear("" + meetingobj.getYear());

							ps.setString(9, "" + meetingobj.getUser_id());
							sendmeetingobj.setUser_id("" + meetingobj.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							sendmeetingobj.setRecordcreated("" + formatedDateTime);

							return ps;
						}, keyHolder2);

						sendmeetingobj.setAuto_id("" + keyHolder2.getKey().longValue());
						sendmeetingobj.setCountry_id(meetingobj.getCountry_id());
						sendmeetingobj.setProvince_id(meetingobj.getProvince_id());
						sendmeetingobj.setTimestamp(formatedDateTime);
						sendmeetingobj.setDatafrom("WEB");

						sendlist2.add(sendmeetingobj);
					}

				} // for loop

				

				for (int y = 0; y < list3.size(); y++) {

					Form5PartADIPHCycleIndicatorComparison indiobj = list3.get(y);

					Form5PartADIPHCycleIndicatorComparison sendindiobj = new Form5PartADIPHCycleIndicatorComparison();

					if (tempobj.getDistrict_id().equals(indiobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(indiobj.getCycle_id())
							&& tempobj.getYear().equals(indiobj.getYear())) {

						String sql3 = "INSERT INTO `followup_action_plan_time`(`followup_id`, `cov_indicators`, `ci_source`,"
								+ "		`time_zero`, `time_one`, `time_three`, `time_four`, `timezero_date`, "
								+ "		`timeone_date`, `timetwo_date`, `timethree_date`, `district_id`, `cycle_id`, `financial_year`,"
								+ "		`user_id`,`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?,?,?,"
								+ "		?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							sendindiobj.setFollowup_id_server("" + p_key);

							ps.setString(2, "" + indiobj.getIndicator_id());
							sendindiobj.setIndicator_id("" + indiobj.getIndicator_id());

							ps.setString(3, "" + "1");
							
							

							ps.setString(4, "" + indiobj.getTime_zero());
							sendindiobj.setTime_zero("" + indiobj.getTime_zero());
							
                            

							ps.setString(5, "" + indiobj.getTime_one());
							sendindiobj.setTime_one("" + indiobj.getTime_one());
							
                            
							
							

							ps.setString(6, "" + indiobj.getTime_two());
							sendindiobj.setTime_two("" + indiobj.getTime_two());
							
                            

							ps.setString(7, "" + indiobj.getTime_three());
							sendindiobj.setTime_three("" + indiobj.getTime_three());
							
//                            String outputDateStr1 = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+indiobj.getTimezero_date();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr1 = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//								e.printStackTrace();
//							}

							ps.setString(8, "" + indiobj.getTimezero_date());
							sendindiobj.setTimezero_date("" + indiobj.getTimezero_date());
							
//                            String outputDateStr2 = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+indiobj.getTimeone_date();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr2 = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//								e.printStackTrace();
//							}

							ps.setString(9, "" + indiobj.getTimeone_date());
							sendindiobj.setTimeone_date("" + indiobj.getTimeone_date());
							
//							String outputDateStr3 = "01-01-2020"; 
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+indiobj.getTimetwo_date();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr3 = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//								e.printStackTrace();
//							}

							ps.setString(10, "" + indiobj.getTimetwo_date());
							sendindiobj.setTimetwo_date("" + indiobj.getTimetwo_date());
							
							
//                            String outputDateStr4 = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+indiobj.getTimethree_date();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr4 = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//								e.printStackTrace();
//							}

							ps.setString(11, "" + indiobj.getTimethree_date());
							sendindiobj.setTimethree_date("" + indiobj.getTimethree_date());

							ps.setString(12, "" + indiobj.getDistrict_id());
							sendindiobj.setDistrict_id("" + indiobj.getDistrict_id());

							ps.setString(13, "" + indiobj.getCycle_id());
							sendindiobj.setCycle_id("" + indiobj.getCycle_id());

							ps.setString(14, "" + indiobj.getYear());
							sendindiobj.setYear("" + indiobj.getYear());

							ps.setString(15, "" + indiobj.getUser_id());
							sendindiobj.setUser_id("" + indiobj.getUser_id());

							ps.setString(16, "" + formatedDateTime);
							sendindiobj.setRecordcreated("" + formatedDateTime);

							return ps;
						}, keyHolder3);

						sendindiobj.setAuto_id("" + keyHolder3.getKey().longValue());
						sendindiobj.setCountry_id(indiobj.getCountry_id());
						sendindiobj.setProvince_id(indiobj.getProvince_id());
						sendindiobj.setTimestamp(formatedDateTime);
						sendindiobj.setDatafrom("WEB");

						sendlist3.add(sendindiobj);

					}

				} // for loop

				

				for (int z = 0; z < list4.size(); z++) {

					Form5PartBActionPlanIndicatorFollowUpStatus actionindstatusobj = list4.get(z);

					Form5PartBActionPlanIndicatorFollowUpStatus sendstatusobj = new Form5PartBActionPlanIndicatorFollowUpStatus();

					if (tempobj.getDistrict_id().equals(actionindstatusobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(actionindstatusobj.getCycle_id())
							&& tempobj.getYear().equals(actionindstatusobj.getYear())) {

						String sql4 = "INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, `revised_timeline`,"
								+ "`change_in_responsibility`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
								+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)";

						KeyHolder keyHolder4 = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							sendstatusobj.setFollowup_id_server("" + p_key);
							
							
							String final_actionid = ""+actionindstatusobj.getAction_point_id();
							
							for(int mycount=0;mycount<mapping2.size();mycount++) {
								
								Form3DefineActionIDDetailsBean mytempmapobj = mapping2.get(mycount);
								/*
								System.out.println("actionindstatusobj.getAction_point_id() = "+actionindstatusobj.getAction_point_id());
								
								System.out.println("mytempmapobj.getAppsend_actionid() = "+mytempmapobj.getAppsend_actionid());
								
								System.out.println("mytempmapobj.getForm5followfillcontinously() = "+mytempmapobj.getForm5followfillcontinously());
								
								System.out.println("mytempmapobj.getWebgen_actionid() = "+mytempmapobj.getWebgen_actionid());
								*/
								if(actionindstatusobj.getAction_point_id().equals(mytempmapobj.getAppsend_actionid()) 
										&& actionindstatusobj.getDistrict_id().equals(mytempmapobj.getDistrict())
										&& actionindstatusobj.getCycle_id().equals(mytempmapobj.getCycle())
										&& actionindstatusobj.getYear().equals(mytempmapobj.getYear()) 
										&& "1".equals(mytempmapobj.getForm5followfillcontinously())) {
									final_actionid = mytempmapobj.getWebgen_actionid();
								}
							}

							ps.setString(2, "" + final_actionid);
							sendstatusobj.setAction_point_id("" + final_actionid);

							ps.setString(3, "" + actionindstatusobj.getStatus());
							sendstatusobj.setStatus("" + actionindstatusobj.getStatus());
							
//							String outputDateStr = "01-01-2020";
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+actionindstatusobj.getRevised_timeline();
//								Date date = inputFormat.parse(inputDateStr);
//								 outputDateStr = outputFormat.format(date);
//							}
//							catch(Exception e) {
//								e.printStackTrace();
//							}
							
							

							ps.setString(4, "" + actionindstatusobj.getRevised_timeline());
							sendstatusobj.setRevised_timeline("" + actionindstatusobj.getRevised_timeline());

							ps.setString(5, "" + actionindstatusobj.getChange_in_responsibility());
							sendstatusobj
									.setChange_in_responsibility("" + actionindstatusobj.getChange_in_responsibility());

							ps.setString(6, "" + actionindstatusobj.getDistrict_id());
							sendstatusobj.setDistrict_id("" + actionindstatusobj.getDistrict_id());

							ps.setString(7, "" + actionindstatusobj.getCycle_id());
							sendstatusobj.setCycle_id("" + actionindstatusobj.getCycle_id());

							ps.setString(8, "" + actionindstatusobj.getYear());
							sendstatusobj.setYear("" + actionindstatusobj.getYear());

							ps.setString(9, "" + actionindstatusobj.getUser_id());
							sendstatusobj.setUser_id("" + actionindstatusobj.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							sendstatusobj.setRecordcreated("" + formatedDateTime);

							return ps;
						}, keyHolder4);

						sendstatusobj.setAuto_id("" + keyHolder4.getKey().longValue());
						sendstatusobj.setCountry_id(actionindstatusobj.getCountry_id());
						sendstatusobj.setProvince_id(actionindstatusobj.getProvince_id());
						sendstatusobj.setTimestamp(formatedDateTime);
						sendstatusobj.setDatafrom("WEB");

						sendlist4.add(sendstatusobj);

						/*-----------------------------*/

						

						for (int pos = 0; pos < list5.size(); pos++) {

							Form5PartBIndicatorProgress progobj = list5.get(pos);

							Form5PartBIndicatorProgress sendprogobj = new Form5PartBIndicatorProgress();

							if (tempobj.getDistrict_id().equals(progobj.getDistrict_id())
									&& tempobj.getCycle_id().equals(progobj.getCycle_id())
									&& tempobj.getYear().equals(progobj.getYear())
									&& actionindstatusobj.getAction_point_id().equals(progobj.getAction_point_id())) {

								String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
										+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
										+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)";

								KeyHolder keyHolder5 = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sql5,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, "" + p_key);
									sendprogobj.setFollowup_id_server("" + p_key);
									
									String final_actionid = ""+progobj.getAction_point_id();
									
									for(int mycount=0;mycount<mapping2.size();mycount++) {
										
										Form3DefineActionIDDetailsBean mytempmapobj = mapping2.get(mycount);
										
																				
										if(progobj.getAction_point_id().equals(mytempmapobj.getAppsend_actionid()) 
												&& progobj.getDistrict_id().equals(mytempmapobj.getDistrict())
												&& progobj.getCycle_id().equals(mytempmapobj.getCycle())
												&& progobj.getYear().equals(mytempmapobj.getYear()) 
												&& "1".equals(mytempmapobj.getForm5followfillcontinously())) {
											final_actionid = mytempmapobj.getWebgen_actionid();
										}
									}

									ps.setString(2, "" + final_actionid);
									sendprogobj.setAction_point_id(final_actionid);
									
									/********************Pbm is here********************/
									
									
									
									
									/*-----------------------------------------------------------------------*/

									String form4plan_indi_id_sql = "SELECT * FROM dev_action_plan_indicators where indicator_id=? and district_id=? and cycle_id=? and financial_year=?";
									Object[] form4plan_indi_id_sql_param = new Object[] { progobj.getIndicator_id(),progobj.getDistrict_id(), progobj.getCycle_id(),
											progobj.getYear() };

									String form4plan_indi_id = jdbcTemplate.query(form4plan_indi_id_sql, form4plan_indi_id_sql_param, temp_rs -> {

										String val = "0";
										while (temp_rs.next()) {

											val = temp_rs.getString("dev_indicator_id");
										}
										/* We can also return any variable-data from here but not used currently */
										return val;
									});
									
									/*-----------------------------------------------------------------------*/
									
									//ps.setString(3, "" + progobj.getIndicator_id());
									
									ps.setString(3, "" + form4plan_indi_id);
									sendprogobj.setIndicator_id(progobj.getIndicator_id());
									/********************Pbm is here********************/
									String prog = "0";

									if (null == progobj.getProgress_indicators()) {
										prog = "0";
									} else {
										prog = progobj.getProgress_indicators();
									}

									ps.setString(4, prog);
									sendprogobj.setProgress_indicators(prog);

									ps.setString(5, "" + progobj.getDistrict_id());
									sendprogobj.setDistrict_id(progobj.getDistrict_id());

									ps.setString(6, "" + progobj.getCycle_id());
									sendprogobj.setCycle_id(progobj.getCycle_id());

									ps.setString(7, "" + progobj.getYear());
									sendprogobj.setYear(progobj.getYear());

									ps.setString(8, "" + progobj.getUser_id());
									sendprogobj.setUser_id(progobj.getUser_id());

									ps.setString(9, "" + formatedDateTime);
									sendprogobj.setRecordcreated(formatedDateTime);

									return ps;
								}, keyHolder5);

								sendprogobj.setAuto_id("" + keyHolder5.getKey().longValue());
								sendprogobj.setCountry_id(progobj.getCountry_id());
								sendprogobj.setProvince_id(progobj.getProvince_id());
								sendprogobj.setTimestamp(formatedDateTime);
								sendprogobj.setDatafrom("WEB");

								sendprogobj.setFollowuppartb_id_server("" + keyHolder4.getKey().longValue());

								sendlist5.add(sendprogobj);
							}

						} // for loop

						/*------------------------------*/
					}

				} // for loop

			}

		}
		
		
		// Updation for WEB data
		for (int index = 0; index < list1.size(); index++) {
			
			Form5FollowUpPrimaryTableDataBean tempobj = list1.get(index);

		
			Form5FollowUpPrimaryTableDataBean sendobj = new Form5FollowUpPrimaryTableDataBean();

			if ("WEB".equals(tempobj.getDatafrom())) {


				String sql1 = " UPDATE `folloup_action_plan_tbl` SET `date_of_meeting`=?, `venue_of_meeting`=?,"
						+ " `chairperson_of_meeting`=?, `chairperson_of_meeting_others`=?, `theme_leader`=?, `no_of_meetings_resp_team`=?,  `user_id`=?,`record_created`=?,`completed`=?  where `district_id`=? and `cycle_id`=? and `financial_year`=?";

				int row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1);
					ps.setString(1, tempobj.getMeetingdate());
					sendobj.setMeetingdate(tempobj.getMeetingdate()); 
					
					ps.setString(2, tempobj.getMeetingvenue());
					sendobj.setMeetingvenue(tempobj.getMeetingvenue());
					
					
					ps.setString(3, tempobj.getChairpersonid());
					sendobj.setChairpersonid(tempobj.getChairpersonid());
					
					ps.setString(4, tempobj.getOtherchairperson());
					sendobj.setOtherchairperson(tempobj.getOtherchairperson());
					
					ps.setString(5, tempobj.getThemeleaderid());
					sendobj.setThemeleaderid(tempobj.getThemeleaderid());
					
					ps.setString(6, tempobj.getNoofmeeting());
					sendobj.setNoofmeeting(tempobj.getNoofmeeting());
					
					ps.setString(7, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id());
					
					ps.setString(8, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());
					
					ps.setString(9, formatedDateTime);
					sendobj.setRecordcreated(formatedDateTime);
					
					ps.setString(10, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());
					
					ps.setString(11, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());
					
					ps.setString(12, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());

					return ps;
				});
				
				sendobj.setAuto_id(tempobj.getAuto_id());
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(formatedDateTime);
				sendobj.setDatafrom("WEB");

				sendlist1.add(sendobj);
				
				
				for (int x = 0; x < list2.size(); x++) {

					Form5PartAStakesMeetingDataBean meetingobj = list2.get(x);

					Form5PartAStakesMeetingDataBean sendmeetingobj = new Form5PartAStakesMeetingDataBean();
					
					
					if("WEB".equals(tempobj.getDatafrom())  
							&&  "WEB".equals(meetingobj.getDatafrom())  
							&&  tempobj.getDistrict_id().equals(meetingobj.getDistrict_id()) 
							&&  tempobj.getCycle_id().equals(meetingobj.getCycle_id()) 
							&&  tempobj.getYear().equals(meetingobj.getYear())) { 
						
						
						String sql2 =" UPDATE `folloup_action_plan_tbl_parta` SET `meeting_date`=?, "
								+ "`stakeholder_meeting`=?, `no_of_participants`=?,`user_id`=?, `record_created`=?  "
								+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `meeting_no`=?";

						int row2 = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2);
							
							String outputDateStr = "";
							
							try {
								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
								String inputDateStr=""+meetingobj.getMeeting_date();
								Date date = inputFormat.parse(inputDateStr);
								outputDateStr = outputFormat.format(date);
							} catch (Exception e) {
								// TODO: handle exception
							}
							
							
							ps.setString(1, "" + outputDateStr);
							sendmeetingobj.setMeeting_date( meetingobj.getMeeting_date()); 
							
							ps.setString(2, "" + meetingobj.getStakeholder_meeting());
							sendmeetingobj.setStakeholder_meeting(meetingobj.getStakeholder_meeting()); 
							
							ps.setString(3, "" + meetingobj.getNo_of_participants());
							sendmeetingobj.setNo_of_participants(meetingobj.getNo_of_participants());
							
							ps.setString(4, "" + meetingobj.getUser_id());
							sendmeetingobj.setUser_id(meetingobj.getUser_id());
							
							
							ps.setString(5, "" + formatedDateTime);
							sendmeetingobj.setRecordcreated(""+formatedDateTime);
														
							ps.setString(6, "" + meetingobj.getDistrict_id() );
							sendmeetingobj.setDistrict_id(meetingobj.getDistrict_id());
							
							ps.setString(7, "" + meetingobj.getCycle_id());
							sendmeetingobj.setCycle_id(meetingobj.getCycle_id());
							
							ps.setString(8, "" + meetingobj.getYear());
							sendmeetingobj.setYear(meetingobj.getYear());
							
							ps.setString(9, "" + meetingobj.getMeeting_no());
							sendmeetingobj.setMeeting_no(meetingobj.getMeeting_no());

							return ps;
						});
						
						sendmeetingobj.setFollowup_id_server(tempobj.getAuto_id());
						sendmeetingobj.setAuto_id( meetingobj.getAuto_id());
						sendmeetingobj.setCountry_id(meetingobj.getCountry_id());
						sendmeetingobj.setProvince_id(meetingobj.getProvince_id());
						sendmeetingobj.setTimestamp(formatedDateTime);
						sendmeetingobj.setDatafrom("WEB");
						
						sendlist2.add(sendmeetingobj);
						
					}//If WEB update
					else if("WEB".equals(tempobj.getDatafrom())  
							&&  "APP".equals(meetingobj.getDatafrom())  
							&&  tempobj.getDistrict_id().equals(meetingobj.getDistrict_id()) 
							&&  tempobj.getCycle_id().equals(meetingobj.getCycle_id()) 
							&&  tempobj.getYear().equals(meetingobj.getYear())) {
						
						
						String sql3 = "INSERT INTO `folloup_action_plan_tbl_parta`(`followup_id`, `meeting_no`, `meeting_date`,"
								+ "		`stakeholder_meeting`, `no_of_participants`, `district_id`, `cycle_id`, `financial_year`,`user_id`,"
								+ "	`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, tempobj.getAuto_id() ); 
							sendmeetingobj.setFollowup_id_server("" + tempobj.getAuto_id());

							ps.setString(2, "" + meetingobj.getMeeting_no());
							sendmeetingobj.setMeeting_no("" + meetingobj.getMeeting_no());
							
//							String outputDateStr = "01-01-2020";
//							
//							
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+meetingobj.getMeeting_date();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}

							ps.setString(3, "" + meetingobj.getMeeting_date());
							sendmeetingobj.setMeeting_date("" + meetingobj.getMeeting_date());

							ps.setString(4, "" + meetingobj.getStakeholder_meeting());
							sendmeetingobj.setStakeholder_meeting("" + meetingobj.getStakeholder_meeting());

							ps.setString(5, "" + meetingobj.getNo_of_participants());
							sendmeetingobj.setNo_of_participants("" + meetingobj.getNo_of_participants());

							ps.setString(6, "" + meetingobj.getDistrict_id());
							sendmeetingobj.setDistrict_id("" + meetingobj.getDistrict_id());

							ps.setString(7, "" + meetingobj.getCycle_id());
							sendmeetingobj.setCycle_id("" + meetingobj.getCycle_id());

							ps.setString(8, "" + meetingobj.getYear());
							sendmeetingobj.setYear("" + meetingobj.getYear());

							ps.setString(9, "" + meetingobj.getUser_id());
							sendmeetingobj.setUser_id("" + meetingobj.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							sendmeetingobj.setRecordcreated("" + formatedDateTime);

							return ps;
						}, keyHolder3);
						
						sendmeetingobj.setFollowup_id_server(tempobj.getAuto_id());
						sendmeetingobj.setAuto_id( "" + keyHolder3.getKey().longValue());
						sendmeetingobj.setCountry_id(meetingobj.getCountry_id());
						sendmeetingobj.setProvince_id(meetingobj.getProvince_id());
						sendmeetingobj.setTimestamp(formatedDateTime);
						sendmeetingobj.setDatafrom("WEB");
						
						sendlist2.add(sendmeetingobj);
						
					}//If APP then insert
			    	
			    }//for loop for stakeholder meeting
				
				
				for (int y = 0; y < list3.size(); y++) {

					Form5PartADIPHCycleIndicatorComparison indiobj = list3.get(y);

					Form5PartADIPHCycleIndicatorComparison sendindiobj = new Form5PartADIPHCycleIndicatorComparison();
					
					if("WEB".equals(tempobj.getDatafrom())  
							&&  "WEB".equals(indiobj.getDatafrom())  
							&&  tempobj.getDistrict_id().equals(indiobj.getDistrict_id()) 
							&&  tempobj.getCycle_id().equals(indiobj.getCycle_id()) 
							&&  tempobj.getYear().equals(indiobj.getYear())) {
					
					String sql2 =" UPDATE `followup_action_plan_time` SET `time_zero`=?, "
							+ "`time_one`=?, `time_three`=?,`time_four`=?,`timezero_date`=?, `timeone_date`=?,`timetwo_date`=?,`timethree_date`=?,`user_id`=?, `record_created`=?  "
							+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `form5_time_id`=?";

					int row2 = jdbcTemplate.update(connection -> {
						PreparedStatement ps = connection.prepareStatement(sql2);
						ps.setString(1, "" + indiobj.getTime_zero());
						sendindiobj.setTime_zero("" + indiobj.getTime_zero());
						
						ps.setString(2, "" + indiobj.getTime_one());
						sendindiobj.setTime_one("" + indiobj.getTime_one());
						
						ps.setString(3, "" + indiobj.getTime_two());
						sendindiobj.setTime_two("" + indiobj.getTime_two());
						
						ps.setString(4, "" + indiobj.getTime_three());
						sendindiobj.setTime_three("" + indiobj.getTime_three());
						
//						String outputDateStr1 = "01-01-2020";
//						
//						try {
//							DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//							DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//							String inputDateStr=""+indiobj.getTimezero_date();
//							Date date = inputFormat.parse(inputDateStr);
//							outputDateStr1 = outputFormat.format(date);
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
						
						ps.setString(5, "" + indiobj.getTimezero_date() );
						sendindiobj.setTimezero_date("" + indiobj.getTimezero_date());
						
						
//                         String outputDateStr2 = "01-01-2020";
//						
//						try {
//							DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//							DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//							String inputDateStr=""+indiobj.getTimeone_date();
//							Date date = inputFormat.parse(inputDateStr);
//							outputDateStr2 = outputFormat.format(date);
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
						
						ps.setString(6, "" + indiobj.getTimeone_date());
						sendindiobj.setTimeone_date("" + indiobj.getTimeone_date());
						
//                        String outputDateStr3 = "01-01-2020";
//						
//						try {
//							DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//							DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//							String inputDateStr=""+indiobj.getTimetwo_date();
//							Date date = inputFormat.parse(inputDateStr);
//							outputDateStr3 = outputFormat.format(date);
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
						
						ps.setString(7, "" + indiobj.getTimetwo_date());
						sendindiobj.setTimetwo_date("" + indiobj.getTimetwo_date());
						
//                         String outputDateStr4 = "01-01-2020";
//						
//						try {
//							DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//							DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//							String inputDateStr=""+indiobj.getTimethree_date();
//							Date date = inputFormat.parse(inputDateStr);
//							outputDateStr4 = outputFormat.format(date);
//						} catch (Exception e) {
//							// TODO: handle exception
//							e.printStackTrace();
//						}
						
						ps.setString(8, "" + indiobj.getTimethree_date());
						sendindiobj.setTimethree_date("" + indiobj.getTimethree_date());
						
						ps.setString(9, "" + indiobj.getUser_id());
						sendindiobj.setUser_id("" + indiobj.getUser_id());
						
						ps.setString(10, "" + formatedDateTime);
						sendindiobj.setRecordcreated("" + formatedDateTime);
						
						ps.setString(11, "" + indiobj.getDistrict_id());
						sendindiobj.setDistrict_id("" + indiobj.getDistrict_id());
						
						ps.setString(12, "" + indiobj.getCycle_id());
						sendindiobj.setCycle_id("" + indiobj.getCycle_id());
						
						ps.setString(13, "" + indiobj.getYear() );
						sendindiobj.setYear("" + indiobj.getYear());
						
						ps.setString(14, "" + indiobj.getAuto_id());
						sendindiobj.setAuto_id("" + indiobj.getAuto_id());

						return ps;
					});
					
					sendindiobj.setIndicator_id(indiobj.getIndicator_id()); 
					sendindiobj.setFollowup_id_server(tempobj.getAuto_id());
					sendindiobj.setAuto_id( "" + indiobj.getAuto_id());
					sendindiobj.setCountry_id(indiobj.getCountry_id());
					sendindiobj.setProvince_id(indiobj.getProvince_id());
					sendindiobj.setTimestamp(formatedDateTime);
					sendindiobj.setDatafrom("WEB");
					
					sendlist3.add(sendindiobj);
					
				 }//if WEB then update
				}//for loop
				
				
				
				for (int z = 0; z < list4.size(); z++) {

					Form5PartBActionPlanIndicatorFollowUpStatus actionindstatusobj = list4.get(z);

					Form5PartBActionPlanIndicatorFollowUpStatus sendstatusobj = new Form5PartBActionPlanIndicatorFollowUpStatus();
					
					if("WEB".equals(tempobj.getDatafrom())  
							&&  "WEB".equals(actionindstatusobj.getDatafrom())  
							&&  tempobj.getDistrict_id().equals(actionindstatusobj.getDistrict_id()) 
							&&  tempobj.getCycle_id().equals(actionindstatusobj.getCycle_id()) 
							&&  tempobj.getYear().equals(actionindstatusobj.getYear())) {
						
					
						String sql2 =" UPDATE `followup_actionind_status` SET `status`=?, "
								+ "`revised_timeline`=?, `change_in_responsibility`=?,`user_id`=?, `record_created`=?  "
								+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `follow_id`=?";

						int row2 = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2);
							ps.setString(1, "" + actionindstatusobj.getStatus());
							sendstatusobj.setStatus("" + actionindstatusobj.getStatus()); 
							
//							String outputDateStr = "01-01-2020";
//							try {
//								DateFormat inputFormat = new SimpleDateFormat("yyyy-mm-dd");
//								DateFormat outputFormat = new SimpleDateFormat("dd-mm-yyyy");
//								String inputDateStr=""+actionindstatusobj.getRevised_timeline();
//								Date date = inputFormat.parse(inputDateStr);
//								outputDateStr = outputFormat.format(date);
//							} catch (Exception e) {
//								// TODO: handle exception
//							}
							
							ps.setString(2, "" + actionindstatusobj.getRevised_timeline());
							sendstatusobj.setRevised_timeline("" + actionindstatusobj.getRevised_timeline());
							
							ps.setString(3, "" + actionindstatusobj.getChange_in_responsibility());
							sendstatusobj.setChange_in_responsibility("" + actionindstatusobj.getChange_in_responsibility());
							
							ps.setString(4, "" + actionindstatusobj.getUser_id());
							sendstatusobj.setUser_id("" + actionindstatusobj.getUser_id());
							
							ps.setString(5, "" + formatedDateTime);
							sendstatusobj.setRecordcreated("" + formatedDateTime);
							
							ps.setString(6, "" + actionindstatusobj.getDistrict_id());
							sendstatusobj.setDistrict_id("" + actionindstatusobj.getDistrict_id());
							
							ps.setString(7, "" + actionindstatusobj.getCycle_id());
							sendstatusobj.setCycle_id("" + actionindstatusobj.getCycle_id());
							
							ps.setString(8, "" + actionindstatusobj.getYear());
							sendstatusobj.setYear("" + actionindstatusobj.getYear());
							
							ps.setString(9, "" + actionindstatusobj.getAction_point_id());
							sendstatusobj.setAction_point_id("" + actionindstatusobj.getAction_point_id());
							
							ps.setString(10, "" + actionindstatusobj.getFollowup_id_server());
							sendstatusobj.setFollowup_id_server("" + actionindstatusobj.getFollowup_id_server());

							return ps;
						});
						
						sendstatusobj.setAuto_id( "" + actionindstatusobj.getAuto_id());
						sendstatusobj.setCountry_id(actionindstatusobj.getCountry_id());
						sendstatusobj.setProvince_id(actionindstatusobj.getProvince_id());
						sendstatusobj.setTimestamp(formatedDateTime);
						sendstatusobj.setDatafrom("WEB");
						
						
						sendlist4.add(sendstatusobj);
						
						/*-------------------------------------------------------------------------*/
						
						
						for (int pos = 0; pos < list5.size(); pos++) {

							Form5PartBIndicatorProgress progobj = list5.get(pos);

							Form5PartBIndicatorProgress sendprogobj = new Form5PartBIndicatorProgress();

							if ("WEB".equals(tempobj.getDatafrom())  
									&&  "WEB".equals(progobj.getDatafrom())  
									&& tempobj.getDistrict_id().equals(progobj.getDistrict_id())
									&& tempobj.getCycle_id().equals(progobj.getCycle_id())
									&& tempobj.getYear().equals(progobj.getYear())
									&& actionindstatusobj.getAction_point_id().equals(progobj.getAction_point_id()))
							{
								
								
								String sql3 =" UPDATE `followup_indicator_progress` SET `progress_indicators`=?, "
										+ "`user_id`=?, `record_created`=?  "
										+ "  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `act_point`=? and `followup_id`=?";

								int row3 = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sql3);
									ps.setString(1, "" + progobj.getProgress_indicators());
									sendprogobj.setProgress_indicators("" + progobj.getProgress_indicators());
									
									ps.setString(2, "" + progobj.getUser_id());
									sendprogobj.setUser_id("" + progobj.getUser_id());
									
									ps.setString(3, "" + formatedDateTime);
									sendprogobj.setRecordcreated("" + formatedDateTime);
									
									ps.setString(4, "" + progobj.getDistrict_id());
									sendprogobj.setDistrict_id("" + progobj.getDistrict_id());
									
									ps.setString(5, "" + progobj.getCycle_id());
									sendprogobj.setCycle_id("" + progobj.getCycle_id());
									
									ps.setString(6, "" + progobj.getYear());
									sendprogobj.setYear("" + progobj.getYear());
									
									ps.setString(7, "" + progobj.getAction_point_id());
									sendprogobj.setAction_point_id("" + progobj.getAction_point_id());
									
									ps.setString(8, "" + progobj.getFollowup_id_server());
									sendprogobj.setFollowup_id_server("" + progobj.getFollowup_id_server());

									return ps;
								});
								
								sendprogobj.setFollowuppartb_id_server(actionindstatusobj.getAuto_id()); 
								sendprogobj.setIndicator_id(progobj.getIndicator_id());
								sendprogobj.setAuto_id( "" + progobj.getAuto_id());
								sendprogobj.setCountry_id(progobj.getCountry_id());
								sendprogobj.setProvince_id(progobj.getProvince_id());
								sendprogobj.setTimestamp(formatedDateTime);
								sendprogobj.setDatafrom("WEB");
								
								
								sendlist5.add(sendprogobj);
								
								
							}//If WEB then update
						
						}//for loop
						
						
					}//If WEB update
					

				}//for loop
				
				
				
				
				
				
				
			}//If WEB then update
			
		}//For loop for update
		
		
		
		

		response.setForm5followup(sendlist1);
		response.setForm5partamajorholder(sendlist2);
		response.setForm5partaindicator(sendlist3);
		response.setForm5partb(sendlist4);
		response.setForm5partbindiprogress(sendlist5);

		return response;
	}

	public SavedForm5FollowupResponse saveForm5FollowupToDb(Form5Followup model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

	
//		INSERT INTO `folloup_action_plan_tbl`(`date_of_meeting`, `venue_of_meeting`, `chairperson_of_meeting`,
//				`theme_leader`, `no_of_meetings_resp_team`,  `district_id`, `cycle_id`, `financial_year`,`user_id`,
//				`record_created`) VALUES (:date_of_meeting,:venue_of_meeting,:chairperson_of_meeting,:theme_leader,
//						:no_of_meetings_resp_team,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		int row = 0;

		String sql1 = "INSERT INTO `folloup_action_plan_tbl`(`date_of_meeting`, `venue_of_meeting`, `chairperson_of_meeting`,`chairperson_of_meeting_others`,"
				+ "				`theme_leader`, `no_of_meetings_resp_team`,  `district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "				`record_created`,`completed`) VALUES (?,?,?,?," + "						?,?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getDate_of_meeting());
			ps.setString(2, model.getVenue_of_meeting());
			ps.setString(3, model.getChairperson_of_meeting());
			ps.setString(4, model.getChairperson_of_meeting_others());
			ps.setString(5, model.getTheme_leader());
			ps.setString(6, model.getNo_of_meetings_resp_team());
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

	
//		INSERT INTO `folloup_action_plan_tbl_parta`(`followup_id`, `meeting_no`, `meeting_date`,
//				`stakeholder_meeting`, `no_of_participants`, `district_id`, `cycle_id`, `financial_year`,`user_id`,
//				`record_created`) VALUES (:followup_id,:meeting_no,:meeting_date,:stakeholder_meeting,:no_of_participants,
//						:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql2 = "INSERT INTO `folloup_action_plan_tbl_parta`(`followup_id`, `meeting_no`, `meeting_date`,"
				+ "		`stakeholder_meeting`, `no_of_participants`, `district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "	`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql2);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getMeeting_no());
//			ps.setString(3, model.getMeeting_date());
//			ps.setString(4, model.getStakeholder_meeting());
//			ps.setString(5, model.getNo_of_participants());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//
//			return ps;
//		});

		List<Form5FollowUpSaveMeetingList> list_meeting = model.getMeeting_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `folloup_action_plan_tbl_parta`(`followup_id`, `meeting_no`, `meeting_date`,"
						+ "		`stakeholder_meeting`, `no_of_participants`, `district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "	`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						
						//ps.setString(2, "" + list_meeting.get(i).getMeeting_no());
						int meetingNumber = i + 1;
						ps.setString(2, "" + meetingNumber);
						
						ps.setString(3, "" + list_meeting.get(i).getMeeting_date());
						ps.setString(4, "" + list_meeting.get(i).getStakeholder_meeting());
						ps.setString(5, "" + list_meeting.get(i).getNo_of_participants());
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);
					}

					public int getBatchSize() {
						return list_meeting.size();
					}

				});

	
		List<Form5FollowUpTotalCoverageIndicators> list_coverage_indicators_arr = model.getTotal_coverage_indi();

		jdbcTemplate.batchUpdate("INSERT INTO `followup_action_plan_time`(`followup_id`, `cov_indicators`, `ci_source`,"
				+ "		`time_zero`, `time_one`, `time_three`, `time_four`, `timezero_date`, "
				+ "		`timeone_date`, `timetwo_date`, `timethree_date`, `district_id`, `cycle_id`, `financial_year`,"
				+ "		`user_id`,`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?,?,?," + "		?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + list_coverage_indicators_arr.get(i).getIndicator_id());
						ps.setString(3, "" + "1");
						ps.setString(4, list_coverage_indicators_arr.get(i).getTime_0() == null ? "0.0" : "" + list_coverage_indicators_arr.get(i).getTime_0());
						ps.setString(5, list_coverage_indicators_arr.get(i).getTime_1() == null ? "0.0" : "" + list_coverage_indicators_arr.get(i).getTime_1());
						ps.setString(6, list_coverage_indicators_arr.get(i).getTime_2() == null ? "0.0" : "" + list_coverage_indicators_arr.get(i).getTime_2());
						ps.setString(7, list_coverage_indicators_arr.get(i).getTime_3() == null ? "0.0" : "" + list_coverage_indicators_arr.get(i).getTime_3());
						ps.setString(8, model.getTotal_coverage_indi_timezero_date() == null? null : "" + model.getTotal_coverage_indi_timezero_date());
						ps.setString(9, model.getTotal_coverage_indi_timeone_date() == null ? null : "" + model.getTotal_coverage_indi_timeone_date());
						ps.setString(10, model.getTotal_coverage_indi_timetwo_date() == null ? null : "" + model.getTotal_coverage_indi_timetwo_date());
						ps.setString(11, model.getTotal_coverage_indi_timethree_date() == null ? null :"" + model.getTotal_coverage_indi_timethree_date());
						ps.setString(12, "" + model.getDistrict());
						ps.setString(13, "" + model.getCycle());
						ps.setString(14, "" + model.getYear());
						ps.setString(15, "" + model.getUserid());
						ps.setString(16, "" + formatedDateTime);

					}

					public int getBatchSize() {
						return list_coverage_indicators_arr.size();
					}

				});


//		INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`,
//				`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,
//				`record_created`) VALUES (:followup_id,:act_point,:act_indicators,:progress_indicators,:district_id,:cycle_id,
//						:financial_year,:user_id,:record_created)

//		INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, 
//				`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`, 
//				`record_created`) VALUES (60,'101','753','1','1','1', '1','1','2000-12-31');

		String sql4 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`,"
				+ "		`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "		`record_created`) VALUES (?,?,?,?,?,?," + "		?,?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql4);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getService_act_point());
//			ps.setString(3, model.getService_act_indicators());
//			ps.setString(4, model.getService_progress_indicators());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});
		
		if ((model.getSource()).equals(Constants.OFFLINE_SOURCE)) {
		
		List<String> serviceActionPointList = form5FollowUpActionPointSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Service delivery");
		List<String> workforceActionPointList = form5FollowUpActionPointSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Workforce");
		List<String> supplyActionPointList = form5FollowUpActionPointSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Supplies & technology");
		List<String> healthInformationActionPointList = form5FollowUpActionPointSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Health information");
		List<String> financeActionPointList = form5FollowUpActionPointSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Finance");
		List<String> policyActionPointList = form5FollowUpActionPointSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Policy/governance");
		
		List<String> serviceActionRequiredList = form5FollowUpActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Service delivery");
		List<String> workforceActionRequiredList = form5FollowUpActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Workforce");
		List<String> supplyActionRequiredList = form5FollowUpActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Supplies & technology");
		List<String> healthInformationActionRequiredList = form5FollowUpActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Health information");
		List<String> financeActionRequiredList = form5FollowUpActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Finance");
		List<String> policyActionRequiredList = form5FollowUpActionRequiredSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), "Policy/governance");
		
		for (Form5FollowUpActionCommonArray serviceList : model.getService_action_arr()) {				
			
			try {
				serviceList.setDev_action_point_id(serviceActionRequiredList.get(Integer.valueOf(serviceList.getDev_action_point_id())));			
				serviceList.setAction_req_id(serviceActionPointList.get(Integer.valueOf(serviceList.getAction_req_id())));
				String actionPointId = serviceList.getAction_req_id();
				
				List<String> serviceDevIndicatorIdList = form5FollowUpDevIndicatorSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), actionPointId);
				
				for(Form5FollowUpIndicatorsInfo serviceIndicatorList :serviceList.getSel_indicators()) {
					try {
					serviceIndicatorList.setDev_indicator_id(serviceDevIndicatorIdList.get(Integer.valueOf(serviceIndicatorList.getDev_indicator_id())));
					}catch(Exception e) {
						LOGGER.error("Error in saveForm5FollowupToDb method in setting service array dev indicators : " + e);
					}
				}
			}
			catch(Exception e) {
				LOGGER.error("Error in saveForm5FollowupToDb method in service action array : " + e);
			}
			
		}

		for (Form5FollowUpActionCommonArray workforceList : model.getWorkforce_action_arr()) {				
			
			try {
				workforceList.setDev_action_point_id(workforceActionRequiredList.get(Integer.valueOf(workforceList.getDev_action_point_id())));						
				workforceList.setAction_req_id(workforceActionPointList.get(Integer.valueOf(workforceList.getAction_req_id())));
				String actionPointId = workforceList.getAction_req_id();
				
				List<String> workforceDevIndicatorIdList = form5FollowUpDevIndicatorSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), actionPointId);
				for(Form5FollowUpIndicatorsInfo workforceIndicatorList :workforceList.getSel_indicators()) {
					try {
					workforceIndicatorList.setDev_indicator_id(workforceDevIndicatorIdList.get(Integer.valueOf(workforceIndicatorList.getDev_indicator_id())));
					}catch(Exception e) {
						LOGGER.error("Error in saveForm5FollowupToDb method in setting work force array dev indicators : " + e);
					}
				}
			}
			catch(Exception e) {
				LOGGER.error("Error in saveForm5FollowupToDb method in work force action array : " + e);
			}
		}

		for (Form5FollowUpActionCommonArray suppliesList : model.getSupplies_action_arr()) {			
			
			try {
				suppliesList.setDev_action_point_id(supplyActionRequiredList.get(Integer.valueOf(suppliesList.getDev_action_point_id())));			
				suppliesList.setAction_req_id(supplyActionPointList.get(Integer.valueOf(suppliesList.getAction_req_id())));
				String actionPointId = suppliesList.getAction_req_id();
				
				List<String> supplyDevIndicatorIdList = form5FollowUpDevIndicatorSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), actionPointId);			
				for(Form5FollowUpIndicatorsInfo supplyIndicatorList :suppliesList.getSel_indicators()) {
					try {
					supplyIndicatorList.setDev_indicator_id(supplyDevIndicatorIdList.get(Integer.valueOf(supplyIndicatorList.getDev_indicator_id())));
					}catch(Exception e) {
						LOGGER.error("Error in saveForm5FollowupToDb method in setting supply array dev indicators : " + e);
					}
				}
			}catch(Exception e) {
				LOGGER.error("Error in saveForm5FollowupToDb method in supply action array : " + e);
			}
		}

		for (Form5FollowUpActionCommonArray healthList : model.getHealth_action_arr()) {			
			
			try {
				healthList.setDev_action_point_id(healthInformationActionRequiredList.get(Integer.valueOf(healthList.getDev_action_point_id())));			
				healthList.setAction_req_id(healthInformationActionPointList.get(Integer.valueOf(healthList.getAction_req_id())));
				String actionPointId =healthList.getAction_req_id();
				
				List<String> healthDevIndicatorIdList = form5FollowUpDevIndicatorSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), actionPointId);
				
				for(Form5FollowUpIndicatorsInfo healthIndicatorList :healthList.getSel_indicators()) {
					try {
					healthIndicatorList.setDev_indicator_id(healthDevIndicatorIdList.get(Integer.valueOf(healthIndicatorList.getDev_indicator_id())));
					}catch(Exception e) {
						LOGGER.error("Error in saveForm5FollowupToDb method in setting health array dev indicators : " + e);
					}
				}
			}
			catch(Exception e){
				LOGGER.error("Error in saveForm5FollowupToDb method in health action array : " + e);
			}
		}

		for (Form5FollowUpActionCommonArray financeList : model.getFinance_action_arr()) {
			
			try {
				financeList.setDev_action_point_id(financeActionRequiredList.get(Integer.valueOf(financeList.getDev_action_point_id())));			
				financeList.setAction_req_id(financeActionPointList.get(Integer.valueOf(financeList.getAction_req_id())));
				String actionPointId = financeList.getAction_req_id();
				
				List<String> financeDevIndicatorIdList = form5FollowUpDevIndicatorSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), actionPointId);
				for(Form5FollowUpIndicatorsInfo financeIndicatorList :financeList.getSel_indicators()) {
					try {
					financeIndicatorList.setDev_indicator_id(financeDevIndicatorIdList.get(Integer.valueOf(financeIndicatorList.getDev_indicator_id())));
					}catch(Exception e) {
						LOGGER.error("Error in saveForm5FollowupToDb method in setting finance array dev indicators : " + e);
					}
				}
			}
			catch(Exception e) {
				LOGGER.error("Error in saveForm5FollowupToDb method in finance action array : " + e);
			}
		}
		
		for (Form5FollowUpActionCommonArray policyList : model.getPolicy_action_arr()) {
			
			try {
				policyList.setDev_action_point_id(policyActionRequiredList.get(Integer.valueOf(policyList.getDev_action_point_id())));			
				policyList.setAction_req_id(policyActionPointList.get(Integer.valueOf(policyList.getAction_req_id())));
				String actionPointId = policyList.getAction_req_id();
				
				List<String> policyDevIndicatorIdList = form5FollowUpDevIndicatorSortedIdList(model.getDistrict(), model.getCycle(), model.getYear(), actionPointId);
				for(Form5FollowUpIndicatorsInfo policyIndicatorList :policyList.getSel_indicators()) {
					try {
					policyIndicatorList.setDev_indicator_id(policyDevIndicatorIdList.get(Integer.valueOf(policyIndicatorList.getDev_indicator_id())));
					}catch(Exception e) {
						LOGGER.error("Error in saveForm5FollowupToDb method in setting policy array dev indicators : " + e);
					}
				}
			}
			catch(Exception e) {
				LOGGER.error("Error in saveForm5FollowupToDb method in policy action array : " + e);
			}
		}
		
		}

		List<Form5FollowUpActionCommonArray> list_service_action_arr = model.getService_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, `revised_timeline`,"
						+ "`change_in_responsibility`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + list_service_action_arr.get(i).getAction_req_id());
						ps.setString(3, "" + list_service_action_arr.get(i).getStatus());
						ps.setString(4, "" + list_service_action_arr.get(i).getRevised_timeline());
						ps.setString(5, "" + list_service_action_arr.get(i).getChange_in_responsibility());
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);

					}

					public int getBatchSize() {
						return list_service_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_service_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_service_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate
					.batchUpdate(
							"INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)",
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "" + list_service_action_arr.get(index).getAction_req_id());
									ps.setString(3, "" + indi_arr.get(i).getDev_indicator_id());
									ps.setString(4, "" + indi_arr.get(i).getProgress_indicators());
									ps.setString(5, "" + model.getDistrict());
									ps.setString(6, "" + model.getCycle());
									ps.setString(7, "" + model.getYear());
									ps.setString(8, "" + model.getUserid());
									ps.setString(9, "" + formatedDateTime);

								}

								public int getBatchSize() {
									return indi_arr.size();
								}

							});
		}

		
		String sql5 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`,"
				+ "		`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "		`record_created`) VALUES (?,?,?,?,?,?," + "		?,?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql5);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getWorkforce_act_point());
//			ps.setString(3, model.getWorkforce_act_indicators());
//			ps.setString(4, model.getWorkforce_progress_indicators());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form5FollowUpActionCommonArray> list_workforce_action_arr = model.getWorkforce_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, `revised_timeline`,"
						+ "`change_in_responsibility`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + list_workforce_action_arr.get(i).getAction_req_id());
						ps.setString(3, "" + list_workforce_action_arr.get(i).getStatus());
						ps.setString(4, "" + list_workforce_action_arr.get(i).getRevised_timeline());
						ps.setString(5, "" + list_workforce_action_arr.get(i).getChange_in_responsibility());
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);

					}

					public int getBatchSize() {
						return list_workforce_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_workforce_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_workforce_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate
					.batchUpdate(
							"INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)",
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "" + list_workforce_action_arr.get(index).getAction_req_id());
									ps.setString(3, "" + indi_arr.get(i).getDev_indicator_id());
									ps.setString(4, "" + indi_arr.get(i).getProgress_indicators());
									ps.setString(5, "" + model.getDistrict());
									ps.setString(6, "" + model.getCycle());
									ps.setString(7, "" + model.getYear());
									ps.setString(8, "" + model.getUserid());
									ps.setString(9, "" + formatedDateTime);

								}

								public int getBatchSize() {
									return indi_arr.size();
								}

							});
		}

	
		String sql6 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`,"
				+ "		`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "		`record_created`) VALUES (?,?,?,?,?,?," + "		?,?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql6);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getSupplies_act_point());
//			ps.setString(3, model.getSupplies_act_indicators());
//			ps.setString(4, model.getSupplies_act_progress_indicators());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form5FollowUpActionCommonArray> list_supplies_action_arr = model.getSupplies_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, `revised_timeline`,"
						+ "`change_in_responsibility`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + list_supplies_action_arr.get(i).getAction_req_id());
						ps.setString(3, "" + list_supplies_action_arr.get(i).getStatus());
						ps.setString(4, "" + list_supplies_action_arr.get(i).getRevised_timeline());
						ps.setString(5, "" + list_supplies_action_arr.get(i).getChange_in_responsibility());
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);

					}

					public int getBatchSize() {
						return list_supplies_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_supplies_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_supplies_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate
					.batchUpdate(
							"INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)",
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "" + list_supplies_action_arr.get(index).getAction_req_id());
									ps.setString(3, "" + indi_arr.get(i).getDev_indicator_id());
									ps.setString(4, "" + indi_arr.get(i).getProgress_indicators());
									ps.setString(5, "" + model.getDistrict());
									ps.setString(6, "" + model.getCycle());
									ps.setString(7, "" + model.getYear());
									ps.setString(8, "" + model.getUserid());
									ps.setString(9, "" + formatedDateTime);

								}

								public int getBatchSize() {
									return indi_arr.size();
								}

							});
		}

	
		String sql7 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`,"
				+ "		`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "		`record_created`) VALUES (?,?,?,?,?,?," + "		?,?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql7);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getHealth_act_point());
//			ps.setString(3, model.getHealth_act_indicators());
//			ps.setString(4, model.getHealth_progress_indicators());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form5FollowUpActionCommonArray> list_health_action_arr = model.getHealth_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, `revised_timeline`,"
						+ "`change_in_responsibility`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + list_health_action_arr.get(i).getAction_req_id());
						ps.setString(3, "" + list_health_action_arr.get(i).getStatus());
						ps.setString(4, "" + list_health_action_arr.get(i).getRevised_timeline());
						ps.setString(5, "" + list_health_action_arr.get(i).getChange_in_responsibility());
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);

					}

					public int getBatchSize() {
						return list_health_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_health_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_health_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate
					.batchUpdate(
							"INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)",
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "" + list_health_action_arr.get(index).getAction_req_id());
									ps.setString(3, "" + indi_arr.get(i).getDev_indicator_id());
									ps.setString(4, "" + indi_arr.get(i).getProgress_indicators());
									ps.setString(5, "" + model.getDistrict());
									ps.setString(6, "" + model.getCycle());
									ps.setString(7, "" + model.getYear());
									ps.setString(8, "" + model.getUserid());
									ps.setString(9, "" + formatedDateTime);

								}

								public int getBatchSize() {
									return indi_arr.size();
								}

							});
		}


		String sql8 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`,"
				+ "		`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "		`record_created`) VALUES (?,?,?,?,?,?," + "		?,?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql8);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getFinance_act_point());
//			ps.setString(3, model.getFinance_act_indicators());
//			ps.setString(4, model.getFinance_progress_indicators());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form5FollowUpActionCommonArray> list_finance_action_arr = model.getFinance_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, `revised_timeline`,"
						+ "`change_in_responsibility`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + list_finance_action_arr.get(i).getAction_req_id());
						ps.setString(3, "" + list_finance_action_arr.get(i).getStatus());
						ps.setString(4, "" + list_finance_action_arr.get(i).getRevised_timeline());
						ps.setString(5, "" + list_finance_action_arr.get(i).getChange_in_responsibility());
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);

					}

					public int getBatchSize() {
						return list_finance_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_finance_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_finance_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate
					.batchUpdate(
							"INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)",
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "" + list_finance_action_arr.get(index).getAction_req_id());
									ps.setString(3, "" + indi_arr.get(i).getDev_indicator_id());
									ps.setString(4, "" + indi_arr.get(i).getProgress_indicators());
									ps.setString(5, "" + model.getDistrict());
									ps.setString(6, "" + model.getCycle());
									ps.setString(7, "" + model.getYear());
									ps.setString(8, "" + model.getUserid());
									ps.setString(9, "" + formatedDateTime);

								}

								public int getBatchSize() {
									return indi_arr.size();
								}

							});
		}


		String sql9 = "INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`,"
				+ "		`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
				+ "		`record_created`) VALUES (?,?,?,?,?,?," + "		?,?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql9);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getPolicy_act_point());
//			ps.setString(3, model.getPolicy_act_indicators());
//			ps.setString(4, model.getPolicy_progress_indicators());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form5FollowUpActionCommonArray> list_policy_action_arr = model.getPolicy_action_arr();

		jdbcTemplate.batchUpdate(
				"INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, `revised_timeline`,"
						+ "`change_in_responsibility`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
						+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "" + list_policy_action_arr.get(i).getAction_req_id());
						ps.setString(3, "" + list_policy_action_arr.get(i).getStatus());
						ps.setString(4, "" + list_policy_action_arr.get(i).getRevised_timeline());
						ps.setString(5, "" + list_policy_action_arr.get(i).getChange_in_responsibility());
						ps.setString(6, "" + model.getDistrict());
						ps.setString(7, "" + model.getCycle());
						ps.setString(8, "" + model.getYear());
						ps.setString(9, "" + model.getUserid());
						ps.setString(10, "" + formatedDateTime);

					}

					public int getBatchSize() {
						return list_policy_action_arr.size();
					}

				});

		for (int pos = 0; pos < list_policy_action_arr.size(); pos++) {
			List<Form5FollowUpIndicatorsInfo> indi_arr = list_policy_action_arr.get(pos).getSel_indicators();

			int index = pos;

			jdbcTemplate
					.batchUpdate(
							"INSERT INTO `followup_indicator_progress`(`followup_id`, `act_point`, `act_indicators`, "
									+ "`progress_indicators`,`district_id`, `cycle_id`, `financial_year`,`user_id`,"
									+ "`record_created`) VALUES (?,?,?,?," + "?,?,?,?,?)",
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "" + list_policy_action_arr.get(index).getAction_req_id());
									ps.setString(3, "" + indi_arr.get(i).getDev_indicator_id());
									ps.setString(4, "" + indi_arr.get(i).getProgress_indicators());
									ps.setString(5, "" + model.getDistrict());
									ps.setString(6, "" + model.getCycle());
									ps.setString(7, "" + model.getYear());
									ps.setString(8, "" + model.getUserid());
									ps.setString(9, "" + formatedDateTime);

								}

								public int getBatchSize() {
									return indi_arr.size();
								}

							});
		}

	
//INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, 
//		`revised_timeline`, `change_in_responsibility`,`district_id`, 
//		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (:follow_id,:act_point,:status,
//				:revised_timeline,:change_in_responsibility,:district_id,:cycle_id,:financial_year,
//				:user_id,:record_created)

		String sql10 = "INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, "
				+ "		`revised_timeline`, `change_in_responsibility`,`district_id`, "
				+ "		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (?,?,?," + "	?,?,?,?,?,"
				+ "	?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql10);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getService_act_point());
//			ps.setString(3, model.getService_status());
//			ps.setString(4, model.getService_revised_timeline());
//			ps.setString(5, model.getService_change_in_responsibility());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//			return ps;
//		});

	
		String sql11 = "INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, "
				+ "		`revised_timeline`, `change_in_responsibility`,`district_id`, "
				+ "		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (?,?,?," + "	?,?,?,?,?,"
				+ "	?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql11);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getWorkforce_act_point());
//			ps.setString(3, model.getWorkforce_status());
//			ps.setString(4, model.getWorkforce_revised_timeline());
//			ps.setString(5, model.getWorkforce_change_in_responsibility());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//			return ps;
//		});


		String sql12 = "INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, "
				+ "		`revised_timeline`, `change_in_responsibility`,`district_id`, "
				+ "		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (?,?,?," + "	?,?,?,?,?,"
				+ "	?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql12);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getSupplies_act_point());
//			ps.setString(3, model.getSupplies_status());
//			ps.setString(4, model.getSupplies_revised_timeline());
//			ps.setString(5, model.getSupplies_change_in_responsibility());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//			return ps;
//		});


		String sql13 = "INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, "
				+ "		`revised_timeline`, `change_in_responsibility`,`district_id`, "
				+ "		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (?,?,?," + "	?,?,?,?,?,"
				+ "	?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql13);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getHealth_act_point());
//			ps.setString(3, model.getHealth_status());
//			ps.setString(4, model.getHealth_revised_timeline());
//			ps.setString(5, model.getHealth_change_in_responsibility());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//			return ps;
//		});

		
		String sql14 = "INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, "
				+ "		`revised_timeline`, `change_in_responsibility`,`district_id`, "
				+ "		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (?,?,?," + "	?,?,?,?,?,"
				+ "	?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql14);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getFinance_act_point());
//			ps.setString(3, model.getFinance_status());
//			ps.setString(4, model.getFinance_revised_timeline());
//			ps.setString(5, model.getFinance_change_in_responsibility());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//			return ps;
//		});

		
		String sql15 = "INSERT INTO `followup_actionind_status`(`follow_id`, `act_point`, `status`, "
				+ "		`revised_timeline`, `change_in_responsibility`,`district_id`, "
				+ "		`cycle_id`, `financial_year`,`user_id`,`record_created`) VALUES (?,?,?," + "	?,?,?,?,?,"
				+ "	?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql15);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getPolicy_act_point());
//			ps.setString(3, model.getPolicy_status());
//			ps.setString(4, model.getPolicy_revised_timeline());
//			ps.setString(5, model.getPolicy_change_in_responsibility());
//			ps.setString(6, model.getDistrict());
//			ps.setString(7, model.getCycle());
//			ps.setString(8, model.getYear());
//			ps.setString(9, model.getUserid());
//			ps.setString(10, formatedDateTime);
//			return ps;
//		});

	
		SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	
	private List<String> form5FollowUpActionPointSortedIdList(String district_id, String cycle_id, String year, String actionPartOfEnggagement) {

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

	private List<String> form5FollowUpActionRequiredSortedIdList(String district_id, String cycle_id, String year, String actionPartOfEnggagement) {

		List<Integer> list = new ArrayList<Integer>();

		String sql = "SELECT dev_action_point_id FROM `dev_action_plan_action_points` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_part`=?;";
		Object[] params = new Object[] { district_id, cycle_id, year, actionPartOfEnggagement };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {
				list.add(Integer.valueOf(rs.getString("dev_action_point_id")));
			}
			return "";
		});

		Collections.sort(list);
		List<String> sortedList = list.stream().map(Object::toString).collect(Collectors.toList());
		return sortedList;
	}
	
	private List<String> form5FollowUpDevIndicatorSortedIdList(String district_id, String cycle_id, String year, String actionPointId) {

		List<Integer> list = new ArrayList<Integer>();

		String sql = "SELECT dev_indicator_id FROM `dev_action_plan_indicators` where `district_id`=? and `cycle_id`=? and `financial_year`=? and `action_point_id`=?;";
		Object[] params = new Object[] { district_id, cycle_id, year, actionPointId };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {
				list.add(Integer.valueOf(rs.getString("dev_indicator_id")));
			}
			return "";
		});

		Collections.sort(list);
		List<String> sortedList = list.stream().map(Object::toString).collect(Collectors.toList());
		return sortedList;
	}

}
