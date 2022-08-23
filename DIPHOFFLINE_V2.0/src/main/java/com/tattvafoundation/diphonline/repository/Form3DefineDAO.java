package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.model.AllFormsDataFetchFromAndroidBean;
import com.tattvafoundation.diphonline.model.DeleteForm3DefineResponse;
import com.tattvafoundation.diphonline.model.Form1ASourceIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form1BPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form2EnagageSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form3ActionEngmtActionReqTableDataBean;
import com.tattvafoundation.diphonline.model.Form3Define;
import com.tattvafoundation.diphonline.model.Form3DefineActionEngmtDetailsTableDataBean;
import com.tattvafoundation.diphonline.model.Form3DefineActionIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form3DefineCommonArray;
import com.tattvafoundation.diphonline.model.Form3DefineEdit;
import com.tattvafoundation.diphonline.model.Form3DefineEditOnlyArray;
import com.tattvafoundation.diphonline.model.Form3DefinePrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form3DefineSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form3DefineViewInEdit;
import com.tattvafoundation.diphonline.model.Form4PlanPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form4PlanSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpSendAllDataBean;
import com.tattvafoundation.diphonline.model.SavedForm3DefineResponse;
import com.tattvafoundation.diphonline.model.UserCredentialsFromAndroidBean;
import com.tattvafoundation.diphonline.model.User_Districts_Privileges;

@Repository
public class Form3DefineDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public Form3DefineEdit editUpdateForm3DefineToDb(Form3DefineEdit model) {

		Form3DefineEdit obj = new Form3DefineEdit();

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		int row = 0;

//		String sql1 = "INSERT INTO `form_3_1_verify`(`form_3_checkdate`, `form_3_meeting_venue`, "
//				+ "`form_3_filled_by`, `form3_chair_person`, `theme_id`, `district_id`, `cycle_id`,"
//				+ " `financial_year`, `user_id`, `record_created`)values (?,?,?," + "?,?,?,?,?,?," + "?)";
//

		String sql1 = "UPDATE `form_3_1_verify` SET `form_3_checkdate`=?,`form_3_meeting_venue`=?,"
				+ "`form_3_filled_by`=?,`form3_chair_person`=?,`form3_chair_person_others`=?,`theme_id`=?,`user_id`=?,"
				+ "`record_created`=?, `completed`=? WHERE `form_3_id`=?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setString(1, model.getForm_3_checkdate());
			ps.setString(2, model.getForm_3_meeting_venue());
			ps.setString(3, model.getForm_3_filled_by());
			ps.setString(4, model.getForm3_chair_person());
			ps.setString(5, model.getForm3_chair_person_others());
			ps.setString(6, "1");
			ps.setString(7, model.getUserid());
			ps.setString(8, formatedDateTime);
			ps.setString(9, model.getCompleted());
			ps.setString(10, model.getForm_3_id());
			return ps;
		});

		String sql2 = "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql2);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getService_action_part_of_engagement());
//			ps.setString(3, model.getService_description_p_f_h_s_p());
//			ps.setString(4, model.getService_possible_s_t_i());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form3DefineCommonArray> List1_service_insert = model.getService_array_insert();

		/*
		 * 
		 * 
		 * List<Form3DefineCommonArray> list6_policy_insert = new ArrayList<>();
		 * List<Form3DefineCommonArray> temp6_policy_insert = new ArrayList<>();
		 * 
		 * if (model.getPolicy_service_description_p_f_h_s_p() == null ||
		 * "null".equals(model.getPolicy_service_possible_s_t_i())) {
		 * 
		 * } else { temp6_policy_insert.add(new
		 * Form3DefineCommonArray(model.getPolicy_service_description_p_f_h_s_p(),
		 * model.getPolicy_service_possible_s_t_i(),
		 * model.getFirst_policy_document_action_required()));
		 * 
		 * list6_policy_insert.addAll(temp6_policy_insert); }
		 * 
		 * if (model.getFinance_array().size() != 0) {
		 * list6_policy_insert.addAll(model.getFinance_array()); }
		 * 
		 * System.out.println("Size of Policy Array = " + list6_policy_insert.size());
		 * 
		 * for (int pos = 0; pos < list6_policy_insert.size(); pos++) {
		 * 
		 * KeyHolder keyHolder2 = new GeneratedKeyHolder(); int index = pos;
		 * 
		 * row = jdbcTemplate.update(connection -> { PreparedStatement ps = connection
		 * .prepareStatement(
		 * "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`," +
		 * " `action_part_of_engagement`, `description_p_f_h_s_p`," +
		 * " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
		 * + "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
		 * Statement.RETURN_GENERATED_KEYS); ps.setString(1, "" + p_key);
		 * ps.setString(2, "Policy/governance"); ps.setString(3,
		 * list5_finance_insert.get(index).getDocument_description_p_f_h_s_p());
		 * ps.setString(4,
		 * list5_finance_insert.get(index).getDocument_possible_s_t_i());
		 * ps.setString(5, model.getDistrict()); ps.setString(6, model.getCycle());
		 * ps.setString(7, model.getYear()); ps.setString(8, model.getUserid());
		 * ps.setString(9, formatedDateTime);
		 * 
		 * return ps; }, keyHolder2);
		 * 
		 * // ResultSet rs = ps.getGeneratedKeys();
		 * 
		 * long p_key2 = keyHolder2.getKey().longValue();
		 * 
		 * System.out.println("Generated p_key is :: " + p_key2);
		 * 
		 * jdbcTemplate
		 * .batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
		 * +
		 * "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
		 * +
		 * "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
		 * + "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
		 * 
		 * public void setValues(PreparedStatement ps, int i) throws SQLException {
		 * ps.setString(1, "" + p_key); ps.setString(2, "Policy/governance");
		 * ps.setString(3,
		 * list6_policy_insert.get(index).getDocument_description_p_f_h_s_p());
		 * ps.setString(4, "1.1.1"); ps.setString(5,
		 * list6_policy_insert.get(index).getDocument_action_required().get(i));
		 * ps.setString(6, model.getDistrict()); ps.setString(7, model.getCycle());
		 * ps.setString(8, model.getYear()); ps.setString(9, model.getUserid());
		 * ps.setString(10, formatedDateTime); ps.setString(11, "" + p_key2); }
		 * 
		 * public int getBatchSize() { return
		 * list6_policy_insert.get(index).getDocument_action_required().size(); }
		 * 
		 * }); }
		 * 
		 * 
		 * 
		 * 
		 */

		for (int pos = 0; pos < List1_service_insert.size(); pos++) {
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + model.getForm_3_id());
				ps.setString(2, "Service delivery");
				ps.setString(3, "" + List1_service_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, "" + List1_service_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

			

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Service delivery");
									ps.setString(3,
											List1_service_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											List1_service_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return List1_service_insert.get(index).getDocument_action_required().size();
								}

							});
		}

//		if (List1_service_insert.size() != 0) {
//			jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//					+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//					+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//					+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							ps.setString(1, "" + model.getForm_3_id());
//							ps.setString(2, "Service delivery");
//							ps.setString(3, "" + List1_service_insert.get(i).getDocument_description_p_f_h_s_p());
//							ps.setString(4, "" + List1_service_insert.get(i).getDocument_possible_s_t_i());
//							ps.setString(5, "" + model.getDistrict());
//							ps.setString(6, "" + model.getCycle());
//							ps.setString(7, "" + model.getYear());
//							ps.setString(8, "" + model.getUserid());
//							ps.setString(9, "" + formatedDateTime);
//						}
//
//						public int getBatchSize() {
//							return List1_service_insert.size();
//						}
//
//					});
//		}

		List<Form3DefineCommonArray> list2_workforce_insert = model.getWorkforce_array_insert();

		for (int pos = 0; pos < list2_workforce_insert.size(); pos++) {
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + model.getForm_3_id());
				ps.setString(2, "Workforce");
				ps.setString(3, "" + list2_workforce_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, "" + list2_workforce_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

			

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Workforce");
									ps.setString(3,
											list2_workforce_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list2_workforce_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list2_workforce_insert.get(index).getDocument_action_required().size();
								}

							});
		}

//		if (list2_workforce_insert.size() != 0) {
//			jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//					+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//					+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//					+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							ps.setString(1, "" + model.getForm_3_id());
//							ps.setString(2, "Workforce");
//							ps.setString(3, "" + list2_workforce_insert.get(i).getDocument_description_p_f_h_s_p());
//							ps.setString(4, "" + list2_workforce_insert.get(i).getDocument_possible_s_t_i());
//							ps.setString(5, "" + model.getDistrict());
//							ps.setString(6, "" + model.getCycle());
//							ps.setString(7, "" + model.getYear());
//							ps.setString(8, "" + model.getUserid());
//							ps.setString(9, "" + formatedDateTime);
//						}
//
//						public int getBatchSize() {
//							return list2_workforce_insert.size();
//						}
//
//					});
//		}

		List<Form3DefineCommonArray> list3_supplies_insert = model.getSupplies_array_insert();

		for (int pos = 0; pos < list3_supplies_insert.size(); pos++) {
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + model.getForm_3_id());
				ps.setString(2, "Supplies & technology");
				ps.setString(3, "" + list3_supplies_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, "" + list3_supplies_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

			

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Supplies & technology");
									ps.setString(3,
											list3_supplies_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list3_supplies_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list3_supplies_insert.get(index).getDocument_action_required().size();
								}

							});
		}

//		if (list3_supplies_insert.size() != 0) {
//			jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//					+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//					+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//					+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							ps.setString(1, "" + model.getForm_3_id());
//							ps.setString(2, "Supplies & technology");
//							ps.setString(3, "" + list3_supplies_insert.get(i).getDocument_description_p_f_h_s_p());
//							ps.setString(4, "" + list3_supplies_insert.get(i).getDocument_possible_s_t_i());
//							ps.setString(5, "" + model.getDistrict());
//							ps.setString(6, "" + model.getCycle());
//							ps.setString(7, "" + model.getYear());
//							ps.setString(8, "" + model.getUserid());
//							ps.setString(9, "" + formatedDateTime);
//						}
//
//						public int getBatchSize() {
//							return list3_supplies_insert.size();
//						}
//
//					});
//		}

		List<Form3DefineCommonArray> list4_health_insert = model.getHealth_array_insert();

		for (int pos = 0; pos < list4_health_insert.size(); pos++) {
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + model.getForm_3_id());
				ps.setString(2, "Health information");
				ps.setString(3, "" + list4_health_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, "" + list4_health_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

			

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Health information");
									ps.setString(3, list4_health_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list4_health_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list4_health_insert.get(index).getDocument_action_required().size();
								}

							});
		}

//		if (list4_health_insert.size() != 0) {
//			jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//					+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//					+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//					+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							ps.setString(1, "" + model.getForm_3_id());
//							ps.setString(2, "Health information");
//							ps.setString(3, "" + list4_health_insert.get(i).getDocument_description_p_f_h_s_p());
//							ps.setString(4, "" + list4_health_insert.get(i).getDocument_possible_s_t_i());
//							ps.setString(5, "" + model.getDistrict());
//							ps.setString(6, "" + model.getCycle());
//							ps.setString(7, "" + model.getYear());
//							ps.setString(8, "" + model.getUserid());
//							ps.setString(9, "" + formatedDateTime);
//						}
//
//						public int getBatchSize() {
//							return list4_health_insert.size();
//						}
//
//					});
//		}

		List<Form3DefineCommonArray> list5_finance_insert = model.getFinance_array_insert();

		for (int pos = 0; pos < list5_finance_insert.size(); pos++) {
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + model.getForm_3_id());
				ps.setString(2, "Finance");
				ps.setString(3, "" + list5_finance_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, "" + list5_finance_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

			

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Finance");
									ps.setString(3,
											list5_finance_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list5_finance_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list5_finance_insert.get(index).getDocument_action_required().size();
								}

							});
		}

//		if (list5_finance_insert.size() != 0) {
//			jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//					+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//					+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//					+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							ps.setString(1, "" + model.getForm_3_id());
//							ps.setString(2, "Finance");
//							ps.setString(3, "" + list5_finance_insert.get(i).getDocument_description_p_f_h_s_p());
//							ps.setString(4, "" + list5_finance_insert.get(i).getDocument_possible_s_t_i());
//							ps.setString(5, "" + model.getDistrict());
//							ps.setString(6, "" + model.getCycle());
//							ps.setString(7, "" + model.getYear());
//							ps.setString(8, "" + model.getUserid());
//							ps.setString(9, "" + formatedDateTime);
//						}
//
//						public int getBatchSize() {
//							return list5_finance_insert.size();
//						}
//
//					});
//		}

		List<Form3DefineCommonArray> list6_policy_insert = model.getPolicy_array_insert();

		for (int pos = 0; pos < list6_policy_insert.size(); pos++) {
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + model.getForm_3_id());
				ps.setString(2, "Policy/governance");
				ps.setString(3, "" + list6_policy_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, "" + list6_policy_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

			

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Policy/governance");
									ps.setString(3, list6_policy_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list6_policy_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list6_policy_insert.get(index).getDocument_action_required().size();
								}

							});
		}

//		if (list6_policy_insert.size() != 0) {
//			jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//					+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//					+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//					+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							ps.setString(1, "" + model.getForm_3_id());
//							ps.setString(2, "Policy/governance");
//							ps.setString(3, "" + list6_policy_insert.get(i).getDocument_description_p_f_h_s_p());
//							ps.setString(4, "" + list6_policy_insert.get(i).getDocument_possible_s_t_i());
//							ps.setString(5, "" + model.getDistrict());
//							ps.setString(6, "" + model.getCycle());
//							ps.setString(7, "" + model.getYear());
//							ps.setString(8, "" + model.getUserid());
//							ps.setString(9, "" + formatedDateTime);
//						}
//
//						public int getBatchSize() {
//							return list6_policy_insert.size();
//						}
//
//					});
//		}

		/*
		 * 
		 * row = jdbcTemplate.update(connection -> { PreparedStatement ps = connection
		 * .prepareStatement(
		 * "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`," +
		 * " `action_part_of_engagement`, `description_p_f_h_s_p`," +
		 * " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
		 * + "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
		 * Statement.RETURN_GENERATED_KEYS); ps.setString(1, "" + p_key);
		 * ps.setString(2, "Service delivery"); ps.setString(3,
		 * list1_service_insert.get(index).getDocument_description_p_f_h_s_p()); return
		 * ps; }, keyHolder2);
		 * 
		 */
		List<Form3DefineCommonArray> list1_update = model.getService_array();

		jdbcTemplate.batchUpdate(
				"UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
						+ "`user_id`=?," + "`record_created`=? WHERE `id`=? ",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form3DefineCommonArray obj = list1_update.get(i);
						ps.setString(1, obj.getDocument_description_p_f_h_s_p());
						ps.setString(2, obj.getDocument_possible_s_t_i());
						ps.setString(3, model.getUserid());
						ps.setString(4, formatedDateTime);
						ps.setString(5, "" + obj.getForm_3_1_action_part_engagement_nm_details_pkey());
					}

					public int getBatchSize() {
						return list1_update.size();
					}

				});

		List<Form3DefineCommonArray> list2_update = model.getWorkforce_array();

		jdbcTemplate.batchUpdate(
				"UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
						+ "`user_id`=?," + "`record_created`=? WHERE `id`=? ",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form3DefineCommonArray obj = list2_update.get(i);
						ps.setString(1, obj.getDocument_description_p_f_h_s_p());
						ps.setString(2, obj.getDocument_possible_s_t_i());
						ps.setString(3, model.getUserid());
						ps.setString(4, formatedDateTime);
						ps.setString(5, "" + obj.getForm_3_1_action_part_engagement_nm_details_pkey());
					}

					public int getBatchSize() {
						return list2_update.size();
					}

				});

		List<Form3DefineCommonArray> list3_update = model.getSupplies_array();

		jdbcTemplate.batchUpdate(
				"UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
						+ "`user_id`=?," + "`record_created`=? WHERE `id`=? ",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form3DefineCommonArray obj = list3_update.get(i);
						ps.setString(1, obj.getDocument_description_p_f_h_s_p());
						ps.setString(2, obj.getDocument_possible_s_t_i());
						ps.setString(3, model.getUserid());
						ps.setString(4, formatedDateTime);
						ps.setString(5, "" + obj.getForm_3_1_action_part_engagement_nm_details_pkey());
					}

					public int getBatchSize() {
						return list3_update.size();
					}

				});

		List<Form3DefineCommonArray> list4_update = model.getHealth_array();

		jdbcTemplate.batchUpdate(
				"UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
						+ "`user_id`=?," + "`record_created`=? WHERE `id`=? ",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form3DefineCommonArray obj = list4_update.get(i);
						ps.setString(1, obj.getDocument_description_p_f_h_s_p());
						ps.setString(2, obj.getDocument_possible_s_t_i());
						ps.setString(3, model.getUserid());
						ps.setString(4, formatedDateTime);
						ps.setString(5, "" + obj.getForm_3_1_action_part_engagement_nm_details_pkey());
					}

					public int getBatchSize() {
						return list4_update.size();
					}

				});

		List<Form3DefineCommonArray> list5_update = model.getFinance_array();

		jdbcTemplate.batchUpdate(
				"UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
						+ "`user_id`=?," + "`record_created`=? WHERE `id`=? ",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form3DefineCommonArray obj = list5_update.get(i);
						ps.setString(1, obj.getDocument_description_p_f_h_s_p());
						ps.setString(2, obj.getDocument_possible_s_t_i());
						ps.setString(3, model.getUserid());
						ps.setString(4, formatedDateTime);
						ps.setString(5, "" + obj.getForm_3_1_action_part_engagement_nm_details_pkey());
					}

					public int getBatchSize() {
						return list5_update.size();
					}

				});

		List<Form3DefineCommonArray> list6_update = model.getPolicy_array();

		jdbcTemplate.batchUpdate(
				"UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
						+ "`user_id`=?," + "`record_created`=? WHERE `id`=? ",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						Form3DefineCommonArray obj = list6_update.get(i);
						ps.setString(1, obj.getDocument_description_p_f_h_s_p());
						ps.setString(2, obj.getDocument_possible_s_t_i());
						ps.setString(3, model.getUserid());
						ps.setString(4, formatedDateTime);
						ps.setString(5, "" + obj.getForm_3_1_action_part_engagement_nm_details_pkey());
					}

					public int getBatchSize() {
						return list6_update.size();
					}

				});

		/*
		 * 
		 * jdbcTemplate
		 * .batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
		 * +
		 * "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
		 * +
		 * "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
		 * + "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() { public void
		 * setValues(PreparedStatement ps, int i) throws SQLException {
		 * 
		 * public int getBatchSize() { return
		 * list1_service_insert.get(index).getDocument_action_required().size(); }
		 * 
		 * });
		 */

//		List<Form3DefineCommonArray> list6_update = model.getPolicy_array();

		class SingleAction_Desc_Soln {

			private String desc;
			private String p_soln;
			private String action;
			private String action_req_id;

			public SingleAction_Desc_Soln() {

			}

			public SingleAction_Desc_Soln(String desc, String p_soln, String action, String action_req_id) {
				super();
				this.desc = desc;
				this.p_soln = p_soln;
				this.action = action;
				this.action_req_id = action_req_id;
			}

			public String getDesc() {
				return desc;
			}

			public void setDesc(String desc) {
				this.desc = desc;
			}

			public String getP_soln() {
				return p_soln;
			}

			public void setP_soln(String p_soln) {
				this.p_soln = p_soln;
			}

			public String getAction() {
				return action;
			}

			public void setAction(String action) {
				this.action = action;
			}

			public String getAction_req_id() {
				return action_req_id;
			}

			public void setAction_req_id(String action_req_id) {
				this.action_req_id = action_req_id;
			}

			@Override
			public String toString() {
				return "SingleAction_Desc_Soln [desc=" + desc + ", p_soln=" + p_soln + ", action=" + action
						+ ", action_req_id=" + action_req_id + "]";
			}

		}

		// Start code here
		for (int pos = 0; pos < list1_update.size(); pos++) {
			Form3DefineCommonArray temp_obj = list1_update.get(pos);

			List<SingleAction_Desc_Soln> new_list = new ArrayList<>();

			List<SingleAction_Desc_Soln> insert_action_list = new ArrayList<>();

			

			for (int index = 0; index < temp_obj.getDocument_action_required().size(); index++) {

				if ("".equals(temp_obj.getAction_req_id().get(index))
						|| "".equals((temp_obj.getAction_req_id().get(index)).trim())) {
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					insert_action_list.add(ins);
				} else {
					
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					new_list.add(ins);
				}

			}

			jdbcTemplate.batchUpdate(
					"UPDATE `form_3_1_action_part_engagement_action_req` SET `prob_desc`=?,`action_required`=?,"
							+ "`user_id`=?," + "`record_created`=? WHERE `action_req_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							SingleAction_Desc_Soln obj = new_list.get(i);
							ps.setString(1, obj.getDesc());
							ps.setString(2, obj.getAction());
							ps.setString(3, model.getUserid());
							ps.setString(4, formatedDateTime);
							ps.setString(5, obj.getAction_req_id());
						}

						public int getBatchSize() {
							return new_list.size();
						}

					});

			int final_pos = pos;

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {

									SingleAction_Desc_Soln obj = insert_action_list.get(i);

									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Service delivery");
									ps.setString(3, "" + obj.getDesc());
									ps.setString(4, "1.1.1");
									ps.setString(5, "" + obj.getAction());
									ps.setString(6, "" + model.getDistrict());
									ps.setString(7, "" + model.getCycle());
									ps.setString(8, "" + model.getYear());
									ps.setString(9, "" + model.getUserid());
									ps.setString(10, "" + formatedDateTime);// list1_update
									ps.setString(11, "" + list1_update.get(final_pos)
											.getForm_3_1_action_part_engagement_nm_details_pkey());

								}

								public int getBatchSize() {
									return insert_action_list.size();
								}

							});
		}

		// End code here

		for (int pos = 0; pos < list2_update.size(); pos++) {
			Form3DefineCommonArray temp_obj = list2_update.get(pos);

			List<SingleAction_Desc_Soln> new_list = new ArrayList<>();

			List<SingleAction_Desc_Soln> insert_action_list = new ArrayList<>();

			

			for (int index = 0; index < temp_obj.getDocument_action_required().size(); index++) {

				if ("".equals(temp_obj.getAction_req_id().get(index))
						|| "".equals((temp_obj.getAction_req_id().get(index)).trim())) {
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					insert_action_list.add(ins);
				} else {
					
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					new_list.add(ins);
				}

			}

			jdbcTemplate.batchUpdate(
					"UPDATE `form_3_1_action_part_engagement_action_req` SET `prob_desc`=?,`action_required`=?,"
							+ "`user_id`=?," + "`record_created`=? WHERE `action_req_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							SingleAction_Desc_Soln obj = new_list.get(i);
							ps.setString(1, obj.getDesc());
							ps.setString(2, obj.getAction());
							ps.setString(3, model.getUserid());
							ps.setString(4, formatedDateTime);
							ps.setString(5, obj.getAction_req_id());
						}

						public int getBatchSize() {
							return new_list.size();
						}

					});

			int final_pos = pos;

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {

									SingleAction_Desc_Soln obj = insert_action_list.get(i);

									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Workforce");
									ps.setString(3, "" + obj.getDesc());
									ps.setString(4, "1.1.1");
									ps.setString(5, "" + obj.getAction());
									ps.setString(6, "" + model.getDistrict());
									ps.setString(7, "" + model.getCycle());
									ps.setString(8, "" + model.getYear());
									ps.setString(9, "" + model.getUserid());
									ps.setString(10, "" + formatedDateTime);// list1_update
									ps.setString(11, "" + list2_update.get(final_pos)
											.getForm_3_1_action_part_engagement_nm_details_pkey());

								}

								public int getBatchSize() {
									return insert_action_list.size();
								}

							});
		}

		for (int pos = 0; pos < list3_update.size(); pos++) {
			Form3DefineCommonArray temp_obj = list3_update.get(pos);

			List<SingleAction_Desc_Soln> new_list = new ArrayList<>();

			List<SingleAction_Desc_Soln> insert_action_list = new ArrayList<>();

			

			for (int index = 0; index < temp_obj.getDocument_action_required().size(); index++) {

				if ("".equals(temp_obj.getAction_req_id().get(index))
						|| "".equals((temp_obj.getAction_req_id().get(index)).trim())) {
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					insert_action_list.add(ins);
				} else {
					
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					new_list.add(ins);
				}

			}

			jdbcTemplate.batchUpdate(
					"UPDATE `form_3_1_action_part_engagement_action_req` SET `prob_desc`=?,`action_required`=?,"
							+ "`user_id`=?," + "`record_created`=? WHERE `action_req_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							SingleAction_Desc_Soln obj = new_list.get(i);
							ps.setString(1, obj.getDesc());
							ps.setString(2, obj.getAction());
							ps.setString(3, model.getUserid());
							ps.setString(4, formatedDateTime);
							ps.setString(5, obj.getAction_req_id());
						}

						public int getBatchSize() {
							return new_list.size();
						}

					});

			int final_pos = pos;

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {

									SingleAction_Desc_Soln obj = insert_action_list.get(i);

									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Supplies & technology");
									ps.setString(3, "" + obj.getDesc());
									ps.setString(4, "1.1.1");
									ps.setString(5, "" + obj.getAction());
									ps.setString(6, "" + model.getDistrict());
									ps.setString(7, "" + model.getCycle());
									ps.setString(8, "" + model.getYear());
									ps.setString(9, "" + model.getUserid());
									ps.setString(10, "" + formatedDateTime);// list1_update
									ps.setString(11, "" + list3_update.get(final_pos)
											.getForm_3_1_action_part_engagement_nm_details_pkey());

								}

								public int getBatchSize() {
									return insert_action_list.size();
								}

							});
		}

		for (int pos = 0; pos < list4_update.size(); pos++) {
			Form3DefineCommonArray temp_obj = list4_update.get(pos);

			List<SingleAction_Desc_Soln> new_list = new ArrayList<>();

			List<SingleAction_Desc_Soln> insert_action_list = new ArrayList<>();

			

			for (int index = 0; index < temp_obj.getDocument_action_required().size(); index++) {

				if ("".equals(temp_obj.getAction_req_id().get(index))
						|| "".equals((temp_obj.getAction_req_id().get(index)).trim())) {
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					insert_action_list.add(ins);
				} else {
				
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					new_list.add(ins);
				}

			}

			jdbcTemplate.batchUpdate(
					"UPDATE `form_3_1_action_part_engagement_action_req` SET `prob_desc`=?,`action_required`=?,"
							+ "`user_id`=?," + "`record_created`=? WHERE `action_req_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							SingleAction_Desc_Soln obj = new_list.get(i);
							ps.setString(1, obj.getDesc());
							ps.setString(2, obj.getAction());
							ps.setString(3, model.getUserid());
							ps.setString(4, formatedDateTime);
							ps.setString(5, obj.getAction_req_id());
						}

						public int getBatchSize() {
							return new_list.size();
						}

					});

			int final_pos = pos;

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {

									SingleAction_Desc_Soln obj = insert_action_list.get(i);

									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Health information");
									ps.setString(3, "" + obj.getDesc());
									ps.setString(4, "1.1.1");
									ps.setString(5, "" + obj.getAction());
									ps.setString(6, "" + model.getDistrict());
									ps.setString(7, "" + model.getCycle());
									ps.setString(8, "" + model.getYear());
									ps.setString(9, "" + model.getUserid());
									ps.setString(10, "" + formatedDateTime);// list1_update
									ps.setString(11, "" + list4_update.get(final_pos)
											.getForm_3_1_action_part_engagement_nm_details_pkey());

								}

								public int getBatchSize() {
									return insert_action_list.size();
								}

							});
		}

		for (int pos = 0; pos < list5_update.size(); pos++) {
			Form3DefineCommonArray temp_obj = list5_update.get(pos);

			List<SingleAction_Desc_Soln> new_list = new ArrayList<>();

			List<SingleAction_Desc_Soln> insert_action_list = new ArrayList<>();

			

			for (int index = 0; index < temp_obj.getDocument_action_required().size(); index++) {

				if ("".equals(temp_obj.getAction_req_id().get(index))
						|| "".equals((temp_obj.getAction_req_id().get(index)).trim())) {
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					insert_action_list.add(ins);
				} else {
					
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					new_list.add(ins);
				}

			}

			jdbcTemplate.batchUpdate(
					"UPDATE `form_3_1_action_part_engagement_action_req` SET `prob_desc`=?,`action_required`=?,"
							+ "`user_id`=?," + "`record_created`=? WHERE `action_req_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							SingleAction_Desc_Soln obj = new_list.get(i);
							ps.setString(1, obj.getDesc());
							ps.setString(2, obj.getAction());
							ps.setString(3, model.getUserid());
							ps.setString(4, formatedDateTime);
							ps.setString(5, obj.getAction_req_id());
						}

						public int getBatchSize() {
							return new_list.size();
						}

					});

			int final_pos = pos;

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {

									SingleAction_Desc_Soln obj = insert_action_list.get(i);

									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Finance");
									ps.setString(3, "" + obj.getDesc());
									ps.setString(4, "1.1.1");
									ps.setString(5, "" + obj.getAction());
									ps.setString(6, "" + model.getDistrict());
									ps.setString(7, "" + model.getCycle());
									ps.setString(8, "" + model.getYear());
									ps.setString(9, "" + model.getUserid());
									ps.setString(10, "" + formatedDateTime);// list1_update
									ps.setString(11, "" + list5_update.get(final_pos)
											.getForm_3_1_action_part_engagement_nm_details_pkey());

								}

								public int getBatchSize() {
									return insert_action_list.size();
								}

							});
		}

		for (int pos = 0; pos < list6_update.size(); pos++) {
			Form3DefineCommonArray temp_obj = list6_update.get(pos);

			List<SingleAction_Desc_Soln> new_list = new ArrayList<>();

			List<SingleAction_Desc_Soln> insert_action_list = new ArrayList<>();

			

			for (int index = 0; index < temp_obj.getDocument_action_required().size(); index++) {

				if ("".equals(temp_obj.getAction_req_id().get(index))
						|| "".equals((temp_obj.getAction_req_id().get(index)).trim())) {
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					insert_action_list.add(ins);
				} else {
				
					SingleAction_Desc_Soln ins = new SingleAction_Desc_Soln();
					ins.setDesc("" + temp_obj.getDocument_description_p_f_h_s_p());
					ins.setAction("" + temp_obj.getDocument_action_required().get(index));
					ins.setP_soln("" + temp_obj.getDocument_possible_s_t_i());
					ins.setAction_req_id("" + temp_obj.getAction_req_id().get(index));
					new_list.add(ins);
				}

			}

			jdbcTemplate.batchUpdate(
					"UPDATE `form_3_1_action_part_engagement_action_req` SET `prob_desc`=?,`action_required`=?,"
							+ "`user_id`=?," + "`record_created`=? WHERE `action_req_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							SingleAction_Desc_Soln obj = new_list.get(i);
							ps.setString(1, obj.getDesc());
							ps.setString(2, obj.getAction());
							ps.setString(3, model.getUserid());
							ps.setString(4, formatedDateTime);
							ps.setString(5, obj.getAction_req_id());
						}

						public int getBatchSize() {
							return new_list.size();
						}

					});

			int final_pos = pos;

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {

									SingleAction_Desc_Soln obj = insert_action_list.get(i);

									ps.setString(1, "" + model.getForm_3_id());
									ps.setString(2, "Policy/governance");
									ps.setString(3, "" + obj.getDesc());
									ps.setString(4, "1.1.1");
									ps.setString(5, "" + obj.getAction());
									ps.setString(6, "" + model.getDistrict());
									ps.setString(7, "" + model.getCycle());
									ps.setString(8, "" + model.getYear());
									ps.setString(9, "" + model.getUserid());
									ps.setString(10, "" + formatedDateTime);// list1_update
									ps.setString(11, "" + list6_update.get(final_pos)
											.getForm_3_1_action_part_engagement_nm_details_pkey());

								}

								public int getBatchSize() {
									return insert_action_list.size();
								}

							});
		}

		return obj;

	}
	
	public void sendForm3DefineSaveForExistingDistrictCycleYear(String district_id,String cycle_id,
			String year,String country,String province,Form3DefineSendAllDataBean response ) {

		
			

		List<Form3DefinePrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form3DefineActionEngmtDetailsTableDataBean> list2 = new ArrayList<>();
		List<Form3ActionEngmtActionReqTableDataBean> list3 = new ArrayList<>();

		String sql1 = "SELECT form3verify.`form_3_id`,    form3verify.`form_3_checkdate`,  form3verify.`form_3_meeting_venue`,    form3verify.`form_3_filled_by`,  form3verify.`form3_chair_person`, "
				+ "   form3verify.`form3_chair_person_others`, form3verify.`theme_id`,    form3verify.`district_id`,  "
				+ "  form3verify.`cycle_id`,   form3verify.`financial_year`,   form3verify.`user_id`,  "
				+ "  form3verify.`record_created`,form3verify.`completed` FROM `form_3_1_verify`   form3verify    where form3verify.`district_id`=?  "
				+ "  and  form3verify.`cycle_id`=?  and   form3verify.`financial_year`=?";

		Object[] params1 = new Object[] { district_id, cycle_id, year };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form3DefinePrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form3DefinePrimaryTableDataBean tempobj = new Form3DefinePrimaryTableDataBean();

				tempobj.setAuto_id(rs.getString("form_3_id"));
				tempobj.setMeetingdate(rs.getString("form_3_checkdate"));
				tempobj.setMeetingvenue(rs.getString("form_3_meeting_venue"));
				tempobj.setThemeleaderofcycle(rs.getString("form_3_filled_by"));
				tempobj.setChairpersonid(rs.getString("form3_chair_person"));
				tempobj.setOtherchairperson(rs.getString("form3_chair_person_others"));
				tempobj.setThemeid(rs.getString("theme_id"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				//tempobj.setCountry_id(rs.getString("country_id"));
				//tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setDatafrom("WEB");
				tempobj.setCompleted(rs.getString("completed"));
				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql2 = "SELECT  form3engdetails.`id`,   form3engdetails.`form_3_id`, form3engdetails.`action_part_of_engagement`,  form3engdetails.`description_p_f_h_s_p`,  "
				+ "   form3engdetails.`possible_s_t_i`,   form3engdetails. `district_id`,  form3engdetails. `cycle_id`, "
				+ "    form3engdetails.`financial_year`, form3engdetails.`user_id`,   form3engdetails. `record_created`  "
				+ "  FROM  `form_3_1_action_part_engagement_nm_details`   form3engdetails   where form3engdetails. `district_id`=?  and form3engdetails. `cycle_id`=?   and  form3engdetails.`financial_year`=?";

		Object[] param2 = new Object[] {district_id, cycle_id, year  };

		list2 = jdbcTemplate.query(sql2, param2, rs -> {

			List<Form3DefineActionEngmtDetailsTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form3DefineActionEngmtDetailsTableDataBean tempobj = new Form3DefineActionEngmtDetailsTableDataBean();

				tempobj.setAuto_id(rs.getString("id"));
				tempobj.setForm_3_id(rs.getString("form_3_id"));
				
				String actionname = "Service delivery";
				
				if("Service delivery".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "SERVICED";
				}
				else if("Workforce".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "WORKFORCE";
				}
				else if("Supplies & technology".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "SUPPLIES";				
				}				
				else if("Health information".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "HEALTHINFO";
				}
				else if("Finance".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "FINANCE";
				}
				else if("Policy/governance".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "POLICYG";
				}				
				tempobj.setAction_name(actionname);
				tempobj.setDescription_text(rs.getString("description_p_f_h_s_p"));
				tempobj.setPossible_soln(rs.getString("possible_s_t_i"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				//tempobj.setCountry_id(rs.getString("country_id"));
				//tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setDatafrom("WEB");
				
				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql3 = "SELECT form3actionreq.`action_req_id`,    form3actionreq.`form_3_id`,  form3actionreq.`action_part`,    form3actionreq.`prob_desc`,  "
				+ "  form3actionreq.`form3_sl_no`, form3actionreq.`action_required`,    form3actionreq.`district_id`, "
				+ "   form3actionreq.`cycle_id`, form3actionreq.`financial_year`,    form3actionreq.`user_id`, "
				+ "   form3actionreq.`record_created`,  form3actionreq.`form_3_1_action_part_engagement_nm_details_pkey` "
				+ "   FROM `form_3_1_action_part_engagement_action_req`   form3actionreq    where form3actionreq.`district_id`=?  and  form3actionreq.`cycle_id`=?  and form3actionreq.`financial_year`=?";

		Object[] param3 = new Object[] { district_id, cycle_id, year  };

		list3 = jdbcTemplate.query(sql3, param3, rs -> {

			List<Form3ActionEngmtActionReqTableDataBean> list = new ArrayList<>();
			while (rs.next()) {

				Form3ActionEngmtActionReqTableDataBean tempobj = new Form3ActionEngmtActionReqTableDataBean();

				tempobj.setAuto_id(rs.getString("action_req_id"));
				tempobj.setForm_3_id(rs.getString("form_3_id"));
				
                 String actionname = "Service delivery";
				
				if("Service delivery".equals(rs.getString("action_part"))) { 
					actionname = "SERVICED";
				}
				else if("Workforce".equals(rs.getString("action_part"))) { 
					actionname = "WORKFORCE";
				}
				else if("Supplies & technology".equals(rs.getString("action_part"))) { 
					actionname = "SUPPLIES";				
				}				
				else if("Health information".equals(rs.getString("action_part"))) { 
					actionname = "HEALTHINFO";
				}
				else if("Finance".equals(rs.getString("action_part"))) { 
					actionname = "FINANCE";
				}
				else if("Policy/governance".equals(rs.getString("action_part"))) { 
					actionname = "POLICYG";
				}	
				
				tempobj.setAction_part(actionname);
				tempobj.setProb_desc(rs.getString("prob_desc"));
				tempobj.setAction_required(rs.getString("action_required"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				tempobj.setForm_3_action_engmt_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				//tempobj.setCountry_id(rs.getString("country_id"));
				//tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setDatafrom("WEB");
				
				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				list.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return list;
		});

		response.setForm3Define(list1);
		response.setForm3ActionEngmtDetails(list2);
		response.setForm3ActionEngmtActionReq(list3);
		
		response.setError_code("200"); 
		response.setMessage("Form3 Define All Cycles and Year for given district"  );

		//return response;
	
	}

	public Form3DefineSendAllDataBean consumeAllForm3DefineSaveandUpdate(AllFormsDataFetchFromAndroidBean model) {

		Form3DefineSendAllDataBean response = new Form3DefineSendAllDataBean();

		
		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		

		Form3DefineSendAllDataBean form3define = model.getForm3();

		List<Form3DefinePrimaryTableDataBean> list1 = form3define.getForm3Define();

		List<Form3DefinePrimaryTableDataBean> list_primary_send = new ArrayList<>();
		List<Form3DefineActionEngmtDetailsTableDataBean> list_details_send = new ArrayList<>();
		List<Form3ActionEngmtActionReqTableDataBean> list_actions_send = new ArrayList<>();
		
		List<Form3DefineActionIDDetailsBean> mapping= new ArrayList<>();
		
		//List<Form3DefineActionIDDetailsBean> mapping_form5= new ArrayList<>();

		for (int index = 0; index < list1.size(); index++) {

			Form3DefinePrimaryTableDataBean tempobj = list1.get(index);

			Form3DefinePrimaryTableDataBean sendobj = new Form3DefinePrimaryTableDataBean();

			if ("APP".equals(tempobj.getDatafrom())) {

				/*-------------------------------------------*/

				String sql_check = "SELECT * FROM `form_3_1_verify`  where  `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] params_check = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
						tempobj.getYear() };

				String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

					String keyval = "";
					while (rs.next()) {
						keyval = rs.getString("form_3_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return keyval;
				});

				if ("".equals(checkid)) {

				} else {
					
					
					sendForm3DefineSaveForExistingDistrictCycleYear(tempobj.getDistrict_id(),tempobj.getCycle_id(),
							tempobj.getYear(),tempobj.getCountry_id(),tempobj.getProvince_id(), response );
					
					
					return response;
				}

				/*-------------------------------------------*/

				int row = 0;

				String sql1 = "INSERT INTO `form_3_1_verify`(`form_3_checkdate`, `form_3_meeting_venue`, "
						+ "`form_3_filled_by`, `form3_chair_person`,`form3_chair_person_others`, `theme_id`, `district_id`, `cycle_id`,"
						+ " `financial_year`, `user_id`, `record_created`, `completed`)values (?,?,?," + "?,?,?,?,?,?,?," + "?,?)";

				KeyHolder keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, tempobj.getMeetingdate());
					sendobj.setMeetingdate(tempobj.getMeetingdate());
					
					ps.setString(2, tempobj.getMeetingvenue());
					sendobj.setMeetingvenue(tempobj.getMeetingvenue());
					
					ps.setString(3, tempobj.getThemeleaderofcycle());
					sendobj.setThemeleaderofcycle(tempobj.getThemeleaderofcycle());
					
					ps.setString(4, tempobj.getChairpersonid());
					sendobj.setChairpersonid(tempobj.getChairpersonid());
					
					ps.setString(5, tempobj.getOtherchairperson());
					sendobj.setOtherchairperson(tempobj.getOtherchairperson());
					
					ps.setString(6, tempobj.getThemeid());
					sendobj.setThemeid(tempobj.getThemeid());
					
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
				
				sendobj.setTimestamp(formatedDateTime);
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id()); 
				sendobj.setAuto_id(""+keyHolder.getKey().longValue()); 
				sendobj.setDatafrom("WEB"); 
				
				list_primary_send.add(sendobj);

				

				List<Form3DefineActionEngmtDetailsTableDataBean> list2 = form3define.getForm3ActionEngmtDetails();
				
				
				HashMap<String, String> mymap = new HashMap<>();  
				

				for (int x = 0; x < list2.size(); x++) {

					Form3DefineActionEngmtDetailsTableDataBean detailsobj = list2.get(x);
					
					Form3DefineActionEngmtDetailsTableDataBean senddetailsobj = new Form3DefineActionEngmtDetailsTableDataBean();

					KeyHolder keyHolder2 = new GeneratedKeyHolder();
					
					
					if(tempobj.getDistrict_id().equals(detailsobj.getDistrict_id())   
							&&  tempobj.getCycle_id().equals(detailsobj.getCycle_id())   
							&&  tempobj.getYear().equals(detailsobj.getYear()))  {
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(
									"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
											+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
											+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
											+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
									Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							senddetailsobj.setForm_3_id( "" + p_key); 
							
							String action="SERVICED";
							
							if("SERVICED".equals(detailsobj.getAction_name())) 
							{
								action="Service delivery";
							}
							else if("WORKFORCE".equals(detailsobj.getAction_name())) 
							{
								action="Workforce";
							}
							else if("SUPPLIES".equals(detailsobj.getAction_name())) 
							{
								action="Supplies & technology";
							}
							else if("HEALTHINFO".equals(detailsobj.getAction_name())) 
							{
								action="Health information";
							}
							else if("FINANCE".equals(detailsobj.getAction_name())) 
							{
								action="Finance";
							}
							else if("POLICYG".equals(detailsobj.getAction_name())) 
							{
								action="Policy/governance";
							}
							
							
							ps.setString(2, action /* "Service delivery" */);
							senddetailsobj.setAction_name(detailsobj.getAction_name()); 
							
							ps.setString(3, detailsobj.getDescription_text());
							senddetailsobj.setDescription_text(detailsobj.getDescription_text());
							
							ps.setString(4, detailsobj.getPossible_soln());
							senddetailsobj.setPossible_soln(detailsobj.getPossible_soln()); 
							
							ps.setString(5, detailsobj.getDistrict_id());
							senddetailsobj.setDistrict_id(detailsobj.getDistrict_id()); 
							
							ps.setString(6, detailsobj.getCycle_id());
							senddetailsobj.setCycle_id(detailsobj.getCycle_id()); 
							
							ps.setString(7, detailsobj.getYear());
							senddetailsobj.setYear(detailsobj.getYear()); 
							
							ps.setString(8, detailsobj.getUser_id());
							senddetailsobj.setUser_id(detailsobj.getUser_id()); 
							
							ps.setString(9, formatedDateTime);
							senddetailsobj.setRecordcreated(formatedDateTime); 
							

							return ps;
						}, keyHolder2);

						// ResultSet rs = ps.getGeneratedKeys();

						long p_key2 = keyHolder2.getKey().longValue();
						
						
						mymap.put(detailsobj.getAuto_id(), ""+keyHolder2.getKey().longValue());
						
						senddetailsobj.setTimestamp(formatedDateTime);
						senddetailsobj.setCountry_id(detailsobj.getCountry_id());
						senddetailsobj.setProvince_id(detailsobj.getProvince_id()); 
						senddetailsobj.setAuto_id(""+keyHolder2.getKey().longValue()); 
						senddetailsobj.setDatafrom("WEB"); 
						
						list_details_send.add(senddetailsobj);

						
					}

					

					List<Form3ActionEngmtActionReqTableDataBean> list3 = form3define.getForm3ActionEngmtActionReq();

					for (int y = 0; y < list3.size(); y++) {

						Form3ActionEngmtActionReqTableDataBean actionsobj = list3.get(y);
						Form3ActionEngmtActionReqTableDataBean actionssend = new Form3ActionEngmtActionReqTableDataBean();

						if (detailsobj.getAuto_id().equals(actionsobj.getForm_3_action_engmt_details_pkey())) {
							
							
							Form4PlanSendAllDataBean form4plan_all_obj = model.getForm4();
							
							if(form4plan_all_obj == null) {
								form4plan_all_obj = new Form4PlanSendAllDataBean();
							}
							
							List<Form4PlanPrimaryTableDataBean> form4_lists = form4plan_all_obj.getForm4Plan();
							
							if(form4_lists == null) {
								
								form4_lists = new ArrayList<>();
								
							}
							
							
							Form5FollowUpSendAllDataBean form5followup_all_obj = model.getForm5();
							
							if(form5followup_all_obj == null) {
								form5followup_all_obj = new Form5FollowUpSendAllDataBean();
							}
							
							List<Form5FollowUpPrimaryTableDataBean> form5_lists = form5followup_all_obj.getForm5followup();
							
							if(form5_lists == null) {
								form5_lists = new ArrayList<>();
							}
							
							
							Form3DefineActionIDDetailsBean actionidmap = new Form3DefineActionIDDetailsBean();

							KeyHolder keyHolder3 = new GeneratedKeyHolder();

							jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(
										"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
												+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
												+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
												+ "?,?,?,?,?,?,?)",
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								actionssend.setForm_3_id("" + p_key); 
								
								String action="SERVICED";
								
								if("SERVICED".equals(actionsobj.getAction_part())) 
								{
									action="Service delivery";
								}
								else if("WORKFORCE".equals(actionsobj.getAction_part())) 
								{
									action="Workforce";
								}
								else if("SUPPLIES".equals(actionsobj.getAction_part())) 
								{
									action="Supplies & technology";
								}
								else if("HEALTHINFO".equals(actionsobj.getAction_part())) 
								{
									action="Health information";
								}
								else if("FINANCE".equals(actionsobj.getAction_part())) 
								{
									action="Finance";
								}
								else if("POLICYG".equals(actionsobj.getAction_part())) 
								{
									action="Policy/governance";
								}
								
								
								ps.setString(2, action /* "Service delivery" */);
								actionssend.setAction_part(actionsobj.getAction_part()); 
								
								ps.setString(3, actionsobj.getProb_desc());
								actionssend.setProb_desc(actionsobj.getProb_desc()); 
								
								ps.setString(4, "1.1.1");
								ps.setString(5, actionsobj.getAction_required());
								actionssend.setAction_required(actionsobj.getAction_required()); 
								
								ps.setString(6, actionsobj.getDistrict_id());
								actionssend.setDistrict_id(actionsobj.getDistrict_id()); 
								
								ps.setString(7, actionsobj.getCycle_id());
								actionssend.setCycle_id(actionsobj.getCycle_id()); 
								
								ps.setString(8, actionsobj.getYear());
								actionssend.setYear(actionsobj.getYear()); 
								
								ps.setString(9, actionsobj.getUser_id());
								actionssend.setUser_id(actionsobj.getUser_id()); 
								
								ps.setString(10, formatedDateTime);
								actionssend.setRecordcreated(actionsobj.getRecordcreated()); 
								
								
								/*-----------------------------------------------------*/
								
								String desc_g_pkey ="";								
								for (Map.Entry<String, String> entry : mymap.entrySet()) { 
								   
								    
								    if(entry.getKey().equals(actionsobj.getForm_3_action_engmt_details_pkey())) {
								    	desc_g_pkey =entry.getValue();
								    }
								}
								
								/*-----------------------------------------------------*/
								
								ps.setString(11, desc_g_pkey);
								actionssend.setForm_3_action_engmt_details_pkey(desc_g_pkey); 
								return ps;
							}, keyHolder3);
							
							
							actionssend.setTimestamp(formatedDateTime);
							actionssend.setCountry_id(actionsobj.getCountry_id());
							actionssend.setProvince_id(actionsobj.getProvince_id()); 
							actionssend.setAuto_id(""+keyHolder3.getKey().longValue()); 
							actionssend.setDatafrom("WEB"); 
							
							list_actions_send.add(actionssend);
							
							actionidmap.setDistrict(""+actionsobj.getDistrict_id());
							actionidmap.setCycle(""+actionsobj.getCycle_id());
							actionidmap.setYear(""+actionsobj.getYear());
							actionidmap.setAppsend_actionid(""+actionsobj.getAuto_id());
							actionidmap.setWebgen_actionid(""+keyHolder3.getKey().longValue());
							actionidmap.setForm4planfillcontinously("1");
							
							if(form4_lists.size() == 0) {
								actionidmap.setForm4planfillcontinously("0");
							}
							
							for(int mycount=0;mycount<form4_lists.size();mycount++) {
								Form4PlanPrimaryTableDataBean tempform4plandata = form4_lists.get(mycount);
								
								if(tempform4plandata.getDistrict_id().equals(tempobj.getDistrict_id()) 
										&& tempform4plandata.getCycle_id().equals(tempobj.getCycle_id())
										&& tempform4plandata.getYear().equals(tempobj.getYear()) 
										&& "APP".equals(tempform4plandata.getDatafrom()) 
										&& "APP".equals(tempobj.getDatafrom()) ) {
									actionidmap.setForm4planfillcontinously("1");
									
								}
							}
							
							if(form5_lists.size() == 0) {
								actionidmap.setForm5followfillcontinously("0");
							}
							
							
							for(int mycount=0;mycount<form5_lists.size();mycount++) {
								Form5FollowUpPrimaryTableDataBean tempform5followdata = form5_lists.get(mycount);
								
								if(tempform5followdata.getDistrict_id().equals(tempobj.getDistrict_id()) 
										&& tempform5followdata.getCycle_id().equals(tempobj.getCycle_id())
										&& tempform5followdata.getYear().equals(tempobj.getYear()) 
										&& "APP".equals(tempform5followdata.getDatafrom()) 
										&& "APP".equals(tempobj.getDatafrom()) ) {
									actionidmap.setForm5followfillcontinously("1"); 									
								}
							}
							
							
							mapping.add(actionidmap);
						}

					}

				}

			} else {
				
				continue;
			}
		}
/**************************************************************************************************************************/
		
		
		// Updation for WEB data
		for (int index = 0; index < list1.size(); index++) {

			Form3DefinePrimaryTableDataBean tempobj = list1.get(index);

			Form3DefinePrimaryTableDataBean sendobj = new Form3DefinePrimaryTableDataBean();

			if ("APP".equals(tempobj.getDatafrom())) {

				
				continue;
			} else {
				
				int row = 0;

				String sql1 = "UPDATE `form_3_1_verify` SET `form_3_checkdate`=?,`form_3_meeting_venue`=?,"
						+ "`form_3_filled_by`=?,`form3_chair_person`=?,`form3_chair_person_others`=?,`theme_id`=?,`user_id`=?,"
						+ "`record_created`=?,"+ "`completed`=? WHERE `form_3_id`=?";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1);
					ps.setString(1, tempobj.getMeetingdate());
					sendobj.setMeetingdate(tempobj.getMeetingdate()); 
					
					ps.setString(2, tempobj.getMeetingvenue());
					sendobj.setMeetingvenue(tempobj.getMeetingvenue()); 
					
					ps.setString(3, tempobj.getThemeleaderofcycle());
					sendobj.setThemeleaderofcycle(tempobj.getThemeleaderofcycle()); 
					
					ps.setString(4, tempobj.getChairpersonid());
					sendobj.setChairpersonid(tempobj.getChairpersonid()); 
					
					ps.setString(5, tempobj.getOtherchairperson());
					sendobj.setOtherchairperson(tempobj.getOtherchairperson()); 
					
					ps.setString(6, tempobj.getThemeid());
					sendobj.setThemeid(tempobj.getThemeid()); 
					
					ps.setString(7, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id()); 
					
					ps.setString(8, formatedDateTime);
					sendobj.setRecordcreated(tempobj.getRecordcreated()); 
					
					ps.setString(9, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());
					
					ps.setString(10, tempobj.getAuto_id());
					sendobj.setAuto_id(tempobj.getAuto_id()); 
					return ps;
				});
				
				sendobj.setTimestamp(tempobj.getTimestamp()); 
				sendobj.setCountry_id(tempobj.getCountry_id()); 
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setDistrict_id(tempobj.getDistrict_id()); 
				sendobj.setCycle_id(tempobj.getCycle_id()); 
				sendobj.setYear(tempobj.getYear()); 
				sendobj.setDatafrom("WEB");  
				
				list_primary_send.add(sendobj);
 
				List<Form3DefineActionEngmtDetailsTableDataBean> list2 = form3define.getForm3ActionEngmtDetails();

				for (int x = 0; x < list2.size(); x++) {

					Form3DefineActionEngmtDetailsTableDataBean detailsobj = list2.get(x);
					
					Form3DefineActionEngmtDetailsTableDataBean senddetails = new Form3DefineActionEngmtDetailsTableDataBean();

					if ("WEB".equals(detailsobj.getDatafrom())  
							&&  "WEB".equals(tempobj.getDatafrom()) 
							&&  tempobj.getDistrict_id().equals(detailsobj.getDistrict_id()) 
							&&  tempobj.getCycle_id().equals(detailsobj.getCycle_id()) 
							&&  tempobj.getYear().equals(detailsobj.getYear())) { 
						
						String sql2 = "UPDATE `form_3_1_action_part_engagement_nm_details` SET `description_p_f_h_s_p`=?,`possible_s_t_i`=?,"
								+ "`user_id`=?," + "`record_created`=? WHERE `id`=? ";

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2);
							ps.setString(1, detailsobj.getDescription_text());
							senddetails.setDescription_text(detailsobj.getDescription_text()); 
							
							ps.setString(2, detailsobj.getPossible_soln());
							senddetails.setPossible_soln(detailsobj.getPossible_soln()); 
							
							ps.setString(3, detailsobj.getUser_id());
							senddetails.setUser_id(detailsobj.getUser_id()); 
							
							ps.setString(4, formatedDateTime);
							senddetails.setRecordcreated(formatedDateTime); 
							
							ps.setString(5, detailsobj.getAuto_id());
							senddetails.setAuto_id(detailsobj.getAuto_id()); 
							return ps;
						});
						
						senddetails.setTimestamp(detailsobj.getTimestamp()); 
						senddetails.setCountry_id(detailsobj.getCountry_id()); 
						senddetails.setProvince_id(detailsobj.getProvince_id()); 
						senddetails.setDistrict_id(detailsobj.getDistrict_id());
						senddetails.setCycle_id(detailsobj.getCycle_id());
						senddetails.setYear(detailsobj.getYear());
						senddetails.setForm_3_id(detailsobj.getForm_3_id());
						senddetails.setAction_name(detailsobj.getAction_name()); 
						senddetails.setDatafrom("WEB"); 
						
						list_details_send.add(senddetails);
						

						List<Form3ActionEngmtActionReqTableDataBean> list3_1 = form3define.getForm3ActionEngmtActionReq();

						for (int z1 = 0; z1 < list3_1.size(); z1++) {

							Form3ActionEngmtActionReqTableDataBean actionobj1 = list3_1.get(z1);
							Form3ActionEngmtActionReqTableDataBean sendaction = new Form3ActionEngmtActionReqTableDataBean();

							if ("WEB".equals(detailsobj.getDatafrom())
									&& detailsobj.getAuto_id().equals(actionobj1.getForm_3_action_engmt_details_pkey())
									&& "APP".equals(actionobj1.getDatafrom())) {

								KeyHolder keyHolder3 = new GeneratedKeyHolder();

								jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(
											"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
													+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
													+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
													+ "?,?,?,?,?,?,?)",
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, tempobj.getAuto_id());
									sendaction.setForm_3_id(tempobj.getAuto_id()); 
									
									String action="SERVICED";
									
									if("SERVICED".equals(actionobj1.getAction_part())) 
									{
										action="Service delivery";
									}
									else if("WORKFORCE".equals(actionobj1.getAction_part())) 
									{
										action="Workforce";
									}
									else if("SUPPLIES".equals(actionobj1.getAction_part())) 
									{
										action="Supplies & technology";
									}
									else if("HEALTHINFO".equals(actionobj1.getAction_part())) 
									{
										action="Health information";
									}
									else if("FINANCE".equals(actionobj1.getAction_part())) 
									{
										action="Finance";
									}
									else if("POLICYG".equals(actionobj1.getAction_part())) 
									{
										action="Policy/governance";
									}
									
									ps.setString(2, action /* "Service delivery" */);
									sendaction.setAction_part(actionobj1.getAction_part()); 
									
									ps.setString(3, actionobj1.getProb_desc());
									sendaction.setProb_desc(actionobj1.getProb_desc()); 
									
									ps.setString(4, "1.1.1");
									ps.setString(5, actionobj1.getAction_required());
									sendaction.setAction_required(actionobj1.getAction_required()); 
									
									ps.setString(6, actionobj1.getDistrict_id());
									sendaction.setDistrict_id(actionobj1.getDistrict_id()); 
									
									ps.setString(7, actionobj1.getCycle_id());
									sendaction.setCycle_id(actionobj1.getCycle_id()); 
									
									ps.setString(8, actionobj1.getYear());
									sendaction.setYear(actionobj1.getYear()); 
									
									ps.setString(9, actionobj1.getUser_id());
									sendaction.setUser_id(actionobj1.getUser_id()); 
									
									ps.setString(10, formatedDateTime);
									sendaction.setRecordcreated(formatedDateTime); 
									
									ps.setString(11, detailsobj.getAuto_id());
									sendaction.setForm_3_action_engmt_details_pkey(detailsobj.getAuto_id());  

									return ps;
								}, keyHolder3);
								
								
								sendaction.setAuto_id(""+keyHolder3.getKey().longValue()); 
								sendaction.setTimestamp(formatedDateTime);
								sendaction.setCountry_id(actionobj1.getCountry_id());
								sendaction.setProvince_id(actionobj1.getProvince_id()); 
								sendaction.setDatafrom("WEB");
								
								list_actions_send.add(sendaction);
							}

						}

					} 
					
					if("APP".equals(detailsobj.getDatafrom()) 
							&& "WEB".equals(tempobj.getDatafrom()) 
							&&  tempobj.getDistrict_id().equals(detailsobj.getDistrict_id())
							&&  tempobj.getCycle_id().equals(detailsobj.getCycle_id()) 
							&&  tempobj.getYear().equals(detailsobj.getYear()))   
					{

						KeyHolder keyHolder2 = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(
									"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
											+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
											+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
											+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
									Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, tempobj.getAuto_id());
							senddetails.setForm_3_id(tempobj.getAuto_id()); 
							
							String action="SERVICED";
							
							if("SERVICED".equals(detailsobj.getAction_name())) 
							{
								action="Service delivery";
							}
							else if("WORKFORCE".equals(detailsobj.getAction_name())) 
							{
								action="Workforce";
							}
							else if("SUPPLIES".equals(detailsobj.getAction_name())) 
							{
								action="Supplies & technology";
							}
							else if("HEALTHINFO".equals(detailsobj.getAction_name())) 
							{
								action="Health information";
							}
							else if("FINANCE".equals(detailsobj.getAction_name())) 
							{
								action="Finance";
							}
							else if("POLICYG".equals(detailsobj.getAction_name())) 
							{
								action="Policy/governance";
							}
							
							
							ps.setString(2, action /* "Service delivery" */);
							senddetails.setAction_name(detailsobj.getAction_name());
							
							ps.setString(3, detailsobj.getDescription_text());
							senddetails.setDescription_text(detailsobj.getDescription_text());
							
							ps.setString(4, detailsobj.getPossible_soln());
							senddetails.setPossible_soln(detailsobj.getPossible_soln());
							
							ps.setString(5, detailsobj.getDistrict_id());
							senddetails.setDistrict_id(detailsobj.getDistrict_id());
							
							ps.setString(6, detailsobj.getCycle_id());
							senddetails.setCycle_id(detailsobj.getCycle_id());
							
							ps.setString(7, detailsobj.getYear());
							senddetails.setYear(detailsobj.getYear());
							
							ps.setString(8, detailsobj.getUser_id());
							senddetails.setUser_id(detailsobj.getUser_id());
							
							ps.setString(9, formatedDateTime);
							senddetails.setRecordcreated(formatedDateTime);

							return ps;
						}, keyHolder2);
						
						senddetails.setAuto_id(""+keyHolder2.getKey().longValue()); 
						senddetails.setTimestamp(formatedDateTime);
						senddetails.setCountry_id(detailsobj.getCountry_id());
						senddetails.setProvince_id(detailsobj.getProvince_id()); 
						senddetails.setDatafrom("WEB");
						
						list_details_send.add(senddetails);
						

						List<Form3ActionEngmtActionReqTableDataBean> list3_2 = form3define.getForm3ActionEngmtActionReq();
	
					for (int z1 = 0; z1 < list3_2.size(); z1++) 
					{
	
						Form3ActionEngmtActionReqTableDataBean actionobj2 = list3_2.get(z1);
						
						Form3ActionEngmtActionReqTableDataBean senddaction = new Form3ActionEngmtActionReqTableDataBean();
						
						
						if("APP".equals(detailsobj.getDatafrom()) 
								&& detailsobj.getAuto_id().equals(actionobj2.getForm_3_action_engmt_details_pkey()) 
								&& "APP".equals(actionobj2.getDatafrom())  
								&& "WEB".equals(tempobj.getDatafrom()))   
						{
							KeyHolder keyHolder4 = new GeneratedKeyHolder();

							jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(
										"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
												+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
												+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
												+ "?,?,?,?,?,?,?)",
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, tempobj.getAuto_id());
								senddaction.setForm_3_id(tempobj.getAuto_id());
								
								
								String action="SERVICED";
								
								if("SERVICED".equals(actionobj2.getAction_part())) 
								{
									action="Service delivery";
								}
								else if("WORKFORCE".equals(actionobj2.getAction_part())) 
								{
									action="Workforce";
								}
								else if("SUPPLIES".equals(actionobj2.getAction_part())) 
								{
									action="Supplies & technology";
								}
								else if("HEALTHINFO".equals(actionobj2.getAction_part())) 
								{
									action="Health information";
								}
								else if("FINANCE".equals(actionobj2.getAction_part())) 
								{
									action="Finance";
								}
								else if("POLICYG".equals(actionobj2.getAction_part())) 
								{
									action="Policy/governance";
								}
								
								ps.setString(2, action /* "Service delivery" */);
								senddaction.setAction_part(actionobj2.getAction_part()); 
								
								ps.setString(3, actionobj2.getProb_desc());
								senddaction.setProb_desc(actionobj2.getProb_desc()); 
								
								ps.setString(4, "1.1.1");
								ps.setString(5, actionobj2.getAction_required());
								senddaction.setAction_required(actionobj2.getAction_required()); 
								
								ps.setString(6, actionobj2.getDistrict_id());
								senddaction.setDistrict_id(actionobj2.getDistrict_id()); 
								
								ps.setString(7, actionobj2.getCycle_id());
								senddaction.setCycle_id(actionobj2.getCycle_id()); 
								
								ps.setString(8, actionobj2.getYear());
								senddaction.setYear(actionobj2.getYear()); 
								
								ps.setString(9, actionobj2.getUser_id());
								senddaction.setUser_id(actionobj2.getUser_id()); 
								
								ps.setString(10, formatedDateTime);
								senddaction.setRecordcreated(formatedDateTime); 
								
								ps.setString(11, ""+keyHolder2.getKey().longValue());
								senddaction.setForm_3_action_engmt_details_pkey(""+keyHolder2.getKey().longValue());   
								

								return ps;
							}, keyHolder4);
							
							senddaction.setAuto_id(""+keyHolder4.getKey().longValue());
							senddaction.setTimestamp(formatedDateTime);
							senddaction.setCountry_id(detailsobj.getCountry_id());
							senddaction.setProvince_id(detailsobj.getProvince_id()); 
							senddaction.setDatafrom("WEB");
							
							list_actions_send.add(senddaction);
						}
						
					}
					
					
					
					

					}//

				}
				
				
				List<Form3ActionEngmtActionReqTableDataBean> list4 = form3define.getForm3ActionEngmtActionReq();
				
				for(int pos=0;pos<list4.size();pos++) {
					
					Form3ActionEngmtActionReqTableDataBean actionreq = list4.get(pos);
					
					Form3ActionEngmtActionReqTableDataBean sendaction = new Form3ActionEngmtActionReqTableDataBean();
					
					if("WEB".equals(actionreq.getDatafrom())) {
						
						
						String sql_last = "UPDATE `form_3_1_action_part_engagement_action_req` SET `prob_desc`=?,`action_required`=?,"
								+ "`user_id`=?," + "`record_created`=? WHERE `action_req_id`=?";

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_last);
							ps.setString(1, actionreq.getProb_desc());
							sendaction.setProb_desc(actionreq.getProb_desc()); 
							
							ps.setString(2, actionreq.getAction_required());
							sendaction.setAction_required(actionreq.getAction_required());
							
							ps.setString(3, actionreq.getUser_id());
							sendaction.setUser_id(actionreq.getUser_id());
							
							ps.setString(4, formatedDateTime);
							sendaction.setRecordcreated(formatedDateTime);
							
							ps.setString(5, actionreq.getAuto_id());
							sendaction.setAuto_id(actionreq.getAuto_id());
							return ps;
						});
						
						
						sendaction.setTimestamp(formatedDateTime);
						sendaction.setCountry_id(actionreq.getCountry_id());
						sendaction.setProvince_id(actionreq.getProvince_id());
						sendaction.setDistrict_id(actionreq.getDistrict_id()); 
						sendaction.setYear(actionreq.getYear()); 
						sendaction.setCycle_id(actionreq.getCycle_id()); 
						sendaction.setForm_3_id(actionreq.getForm_3_id()); 
						sendaction.setAction_part(actionreq.getAction_part()); 
						sendaction.setForm_3_action_engmt_details_pkey(actionreq.getForm_3_action_engmt_details_pkey()); 
						sendaction.setDatafrom("WEB");
						
						list_actions_send.add(sendaction);
					}
					
					
					
				}

			}
		}
		
		
		response.setForm3Define(list_primary_send);
		response.setForm3ActionEngmtDetails(list_details_send);
		response.setForm3ActionEngmtActionReq(list_actions_send); 
		response.setError_code("200"); 
		response.setMessage("Form3 Define All Cycles and Year for given district"  );		
		response.setMapping(mapping); 

		return response;
	}

	public SavedForm3DefineResponse saveForm3DefineToDb(Form3Define model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		

		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		int row = 0;

		String sql1 = "INSERT INTO `form_3_1_verify`(`form_3_checkdate`, `form_3_meeting_venue`, "
				+ "`form_3_filled_by`, `form3_chair_person`,`form3_chair_person_others`, `theme_id`, `district_id`, `cycle_id`,"
				+ " `financial_year`, `user_id`, `record_created`, `completed`)values (?,?,?," + "?,?,?,?,?,?,?," + "?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getForm_3_checkdate());
			ps.setString(2, model.getForm_3_meeting_venue());
			ps.setString(3, model.getForm_3_filled_by());
			ps.setString(4, model.getForm3_chair_person());
			ps.setString(5, model.getForm3_chair_person_others());
			ps.setString(6, model.getTheme_id());
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

	

		String sql2 = "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql2);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getService_action_part_of_engagement());
//			ps.setString(3, model.getService_description_p_f_h_s_p());
//			ps.setString(4, model.getService_possible_s_t_i());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		

		List<Form3DefineCommonArray> list1_service_insert = new ArrayList<>();
		List<Form3DefineCommonArray> temp1_service_insert = new ArrayList<>();

		if (model.getService_description_p_f_h_s_p() == null || "null".equals(model.getService_possible_s_t_i())) {

		} else {
			temp1_service_insert.add(new Form3DefineCommonArray(model.getService_description_p_f_h_s_p(),
					model.getService_possible_s_t_i(), model.getFirst_service_document_action_required()));

			list1_service_insert.addAll(temp1_service_insert);
		}

		if (model.getService_array().size() != 0) {
			list1_service_insert.addAll(model.getService_array());
		}

		
		for (int pos = 0; pos < list1_service_insert.size(); pos++) {
			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Service delivery");
				ps.setString(3, list1_service_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, list1_service_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

		

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "Service delivery");
									ps.setString(3,
											list1_service_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list1_service_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list1_service_insert.get(index).getDocument_action_required().size();
								}

							});

		}

//		Newly stopped code

//		jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Service delivery");
//						ps.setString(3, list1_service_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, list1_service_insert.get(i).getDocument_possible_s_t_i());
//						ps.setString(5, model.getDistrict());
//						ps.setString(6, model.getCycle());
//						ps.setString(7, model.getYear());
//						ps.setString(8, model.getUserid());
//						ps.setString(9, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list1_service_insert.size();
//					}
//
//				});

//		String sql3 = "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql3);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getWorkforce_service_action_part_of_engagement());
//			ps.setString(3, model.getWorkforce_service_description_p_f_h_s_p());
//			ps.setString(4, model.getWorkforce_service_possible_s_t_i());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form3DefineCommonArray> list2_workforce_insert = new ArrayList<>();
		List<Form3DefineCommonArray> temp2_workforce_insert = new ArrayList<>();

		if (model.getWorkforce_service_description_p_f_h_s_p() == null
				|| "null".equals(model.getWorkforce_service_possible_s_t_i())) {

		} else {
			temp2_workforce_insert.add(new Form3DefineCommonArray(model.getWorkforce_service_description_p_f_h_s_p(),
					model.getWorkforce_service_possible_s_t_i(), model.getFirst_workforce_document_action_required()));

			list2_workforce_insert.addAll(temp2_workforce_insert);
		}

		if (model.getWorkforce_array().size() != 0) {
			list2_workforce_insert.addAll(model.getWorkforce_array());
		}

		

		for (int pos = 0; pos < list2_workforce_insert.size(); pos++) {

			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Workforce");
				ps.setString(3, list2_workforce_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, list2_workforce_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

		

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "Workforce");
									ps.setString(3,
											list2_workforce_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list2_workforce_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list2_workforce_insert.get(index).getDocument_action_required().size();
								}

							});

		}

//		jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Workforce");
//						ps.setString(3, list2_workforce_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, list2_workforce_insert.get(i).getDocument_possible_s_t_i());
//						ps.setString(5, model.getDistrict());
//						ps.setString(6, model.getCycle());
//						ps.setString(7, model.getYear());
//						ps.setString(8, model.getUserid());
//						ps.setString(9, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list2_workforce_insert.size();
//					}
//
//				});

	

//		String sql4 = "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql4);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getSupplies_service_action_part_of_engagement());
//			ps.setString(3, model.getSupplies_service_description_p_f_h_s_p());
//			ps.setString(4, model.getSupplies_service_possible_s_t_i());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form3DefineCommonArray> list3_supplies_insert = new ArrayList<>();
		List<Form3DefineCommonArray> temp3_supplies_insert = new ArrayList<>();

		if (model.getSupplies_service_description_p_f_h_s_p() == null
				|| "null".equals(model.getSupplies_service_possible_s_t_i())) {

		} else {
			temp3_supplies_insert.add(new Form3DefineCommonArray(model.getSupplies_service_description_p_f_h_s_p(),
					model.getSupplies_service_possible_s_t_i(), model.getFirst_supplies_document_action_required()));

			list3_supplies_insert.addAll(temp3_supplies_insert);
		}

		if (model.getSupplies_array().size() != 0) {
			list3_supplies_insert.addAll(model.getSupplies_array());
		}

	

		for (int pos = 0; pos < list3_supplies_insert.size(); pos++) {

			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Supplies & technology");
				ps.setString(3, list3_supplies_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, list3_supplies_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

			

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "Supplies & technology");
									ps.setString(3,
											list3_supplies_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list3_supplies_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list3_supplies_insert.get(index).getDocument_action_required().size();
								}

							});

		}

		// Newly added Code

//		jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Supplies & technology");
//						ps.setString(3, list3_supplies_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, list3_supplies_insert.get(i).getDocument_possible_s_t_i());
//						ps.setString(5, model.getDistrict());
//						ps.setString(6, model.getCycle());
//						ps.setString(7, model.getYear());
//						ps.setString(8, model.getUserid());
//						ps.setString(9, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list3_supplies_insert.size();
//					}
//
//				});

	

//		String sql5 = "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql5);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getHealth_service_action_part_of_engagement());
//			ps.setString(3, model.getHealth_service_description_p_f_h_s_p());
//			ps.setString(4, model.getHealth_service_possible_s_t_i());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form3DefineCommonArray> list4_health_insert = new ArrayList<>();
		List<Form3DefineCommonArray> temp4_health_insert = new ArrayList<>();

		if (model.getHealth_service_description_p_f_h_s_p() == null
				|| "null".equals(model.getHealth_service_possible_s_t_i())) {

		} else {
			temp4_health_insert.add(new Form3DefineCommonArray(model.getHealth_service_description_p_f_h_s_p(),
					model.getHealth_service_possible_s_t_i(), model.getFirst_health_document_action_required()));

			list4_health_insert.addAll(temp4_health_insert);
		}

		if (model.getHealth_array().size() != 0) {
			list4_health_insert.addAll(model.getHealth_array());
		}

	

		for (int pos = 0; pos < list4_health_insert.size(); pos++) {

			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Health information");
				ps.setString(3, list4_health_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, list4_health_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

		

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "Health information");
									ps.setString(3, list4_health_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list4_health_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list4_health_insert.get(index).getDocument_action_required().size();
								}

							});

		}

		// Newly Stopped Code

//		jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Health information");
//						ps.setString(3, list4_health_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, list4_health_insert.get(i).getDocument_possible_s_t_i());
//						ps.setString(5, model.getDistrict());
//						ps.setString(6, model.getCycle());
//						ps.setString(7, model.getYear());
//						ps.setString(8, model.getUserid());
//						ps.setString(9, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list4_health_insert.size();
//					}
//
//				});

	

//		String sql6 = "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql6);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getFinance_service_action_part_of_engagement());
//			ps.setString(3, model.getFinance_service_description_p_f_h_s_p());
//			ps.setString(4, model.getFinance_service_possible_s_t_i());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form3DefineCommonArray> list5_finance_insert = new ArrayList<>();
		List<Form3DefineCommonArray> temp5_finance_insert = new ArrayList<>();

		if (model.getFinance_service_description_p_f_h_s_p() == null
				|| "null".equals(model.getHealth_service_possible_s_t_i())) {

		} else {
			temp5_finance_insert.add(new Form3DefineCommonArray(model.getFinance_service_description_p_f_h_s_p(),
					model.getFinance_service_possible_s_t_i(), model.getFirst_finance_document_action_required()));

			list5_finance_insert.addAll(temp5_finance_insert);
		}

		if (model.getFinance_array().size() != 0) {
			list5_finance_insert.addAll(model.getFinance_array());
		}



		for (int pos = 0; pos < list5_finance_insert.size(); pos++) {

			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Finance");
				ps.setString(3, list5_finance_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, list5_finance_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

		

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "Finance");
									ps.setString(3,
											list5_finance_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list5_finance_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list5_finance_insert.get(index).getDocument_action_required().size();
								}

							});

		}

		// Newly Stopped Code
//		jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Finance");
//						ps.setString(3, list5_finance_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, list5_finance_insert.get(i).getDocument_possible_s_t_i());
//						ps.setString(5, model.getDistrict());
//						ps.setString(6, model.getCycle());
//						ps.setString(7, model.getYear());
//						ps.setString(8, model.getUserid());
//						ps.setString(9, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list5_finance_insert.size();
//					}
//
//				});

	

//		String sql7 = "INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql7);
//			ps.setString(1, "" + p_key);
//			ps.setString(2, model.getPolicy_service_action_part_of_engagement());
//			ps.setString(3, model.getPolicy_service_description_p_f_h_s_p());
//			ps.setString(4, model.getPolicy_service_possible_s_t_i());
//			ps.setString(5, model.getDistrict());
//			ps.setString(6, model.getCycle());
//			ps.setString(7, model.getYear());
//			ps.setString(8, model.getUserid());
//			ps.setString(9, formatedDateTime);
//
//			return ps;
//		});

		List<Form3DefineCommonArray> list6_policy_insert = new ArrayList<>();
		List<Form3DefineCommonArray> temp6_policy_insert = new ArrayList<>();

		if (model.getPolicy_service_description_p_f_h_s_p() == null
				|| "null".equals(model.getPolicy_service_possible_s_t_i())) {

		} else {
			temp6_policy_insert.add(new Form3DefineCommonArray(model.getPolicy_service_description_p_f_h_s_p(),
					model.getPolicy_service_possible_s_t_i(), model.getFirst_policy_document_action_required()));

			list6_policy_insert.addAll(temp6_policy_insert);
		}

		if (model.getPolicy_array().size() != 0) {
			list6_policy_insert.addAll(model.getPolicy_array());
		}

	

		for (int pos = 0; pos < list6_policy_insert.size(); pos++) {

			KeyHolder keyHolder2 = new GeneratedKeyHolder();
			int index = pos;

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement(
								"INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
										+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
										+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
										+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Policy/governance");
				ps.setString(3, list6_policy_insert.get(index).getDocument_description_p_f_h_s_p());
				ps.setString(4, list6_policy_insert.get(index).getDocument_possible_s_t_i());
				ps.setString(5, model.getDistrict());
				ps.setString(6, model.getCycle());
				ps.setString(7, model.getYear());
				ps.setString(8, model.getUserid());
				ps.setString(9, formatedDateTime);

				return ps;
			}, keyHolder2);

			// ResultSet rs = ps.getGeneratedKeys();

			long p_key2 = keyHolder2.getKey().longValue();

	

			jdbcTemplate
					.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
							+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
							+ "`user_id`, `record_created`,`form_3_1_action_part_engagement_nm_details_pkey`) VALUES (?,?,?,?,"
							+ "?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "Policy/governance");
									ps.setString(3, list6_policy_insert.get(index).getDocument_description_p_f_h_s_p());
									ps.setString(4, "1.1.1");
									ps.setString(5,
											list6_policy_insert.get(index).getDocument_action_required().get(i));
									ps.setString(6, model.getDistrict());
									ps.setString(7, model.getCycle());
									ps.setString(8, model.getYear());
									ps.setString(9, model.getUserid());
									ps.setString(10, formatedDateTime);
									ps.setString(11, "" + p_key2);
								}

								public int getBatchSize() {
									return list6_policy_insert.get(index).getDocument_action_required().size();
								}

							});
			
		}
		
		// Newly Stopped Code

//		jdbcTemplate.batchUpdate("INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`,"
//				+ " `action_part_of_engagement`, `description_p_f_h_s_p`,"
//				+ " `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) values (?,?,?," + "?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Policy/governance");
//						ps.setString(3, list6_policy_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, list6_policy_insert.get(i).getDocument_possible_s_t_i());
//						ps.setString(5, model.getDistrict());
//						ps.setString(6, model.getCycle());
//						ps.setString(7, model.getYear());
//						ps.setString(8, model.getUserid());
//						ps.setString(9, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list6_policy_insert.size();
//					}
//
//				});



//		String sql8 = "INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//				+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//				+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)"

		// Newly Stopped Code

//		jdbcTemplate.batchUpdate(
//				"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//						+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//						+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
//				new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Service delivery");
//						ps.setString(3, list1_service_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, "1.1.1");
//						ps.setString(5, list1_service_insert.get(i).getDocument_action_required());
//						ps.setString(6, model.getDistrict());
//						ps.setString(7, model.getCycle());
//						ps.setString(8, model.getYear());
//						ps.setString(9, model.getUserid());
//						ps.setString(10, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list1_service_insert.size();
//					}
//
//				});

	

//		String sql9 = "INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//				+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//				+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)";
//

		// Newly Stopped Code

//		jdbcTemplate.batchUpdate(
//				"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//						+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//						+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
//				new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Workforce");
//						ps.setString(3, list2_workforce_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, "1.1.1");
//						ps.setString(5, list2_workforce_insert.get(i).getDocument_action_required());
//						ps.setString(6, model.getDistrict());
//						ps.setString(7, model.getCycle());
//						ps.setString(8, model.getYear());
//						ps.setString(9, model.getUserid());
//						ps.setString(10, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list2_workforce_insert.size();
//					}
//
//				});



//		String sql10 = "INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//				+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//				+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)";

		// Newly Stopped Code

//
//		jdbcTemplate.batchUpdate(
//				"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//						+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//						+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
//				new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Supplies & technology");
//						ps.setString(3, list3_supplies_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, "1.1.1");
//						ps.setString(5, list3_supplies_insert.get(i).getDocument_action_required());
//						ps.setString(6, model.getDistrict());
//						ps.setString(7, model.getCycle());
//						ps.setString(8, model.getYear());
//						ps.setString(9, model.getUserid());
//						ps.setString(10, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list3_supplies_insert.size();
//					}
//
//				});



//		String sql11 = "INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//				+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//				+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)";
//

		// Newly Stopped Code

//		jdbcTemplate.batchUpdate(
//				"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//						+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//						+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
//				new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Finance");
//						ps.setString(3, list5_finance_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, "1.1.1");
//						ps.setString(5, list5_finance_insert.get(i).getDocument_action_required());
//						ps.setString(6, model.getDistrict());
//						ps.setString(7, model.getCycle());
//						ps.setString(8, model.getYear());
//						ps.setString(9, model.getUserid());
//						ps.setString(10, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list5_finance_insert.size();
//					}
//
//				});



//		String sql12 = "INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//				+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//				+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)";
//

		// Newly Stopped Code

//		jdbcTemplate.batchUpdate(
//				"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//						+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//						+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
//				new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Health information");
//						ps.setString(3, list4_health_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, "1.1.1");
//						ps.setString(5, list4_health_insert.get(i).getDocument_action_required());
//						ps.setString(6, model.getDistrict());
//						ps.setString(7, model.getCycle());
//						ps.setString(8, model.getYear());
//						ps.setString(9, model.getUserid());
//						ps.setString(10, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list4_health_insert.size();
//					}
//
//				});



//		String sql13 = "INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//				+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//				+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)";

		// Newly Stopped Code

//		jdbcTemplate.batchUpdate(
//				"INSERT INTO `form_3_1_action_part_engagement_action_req`(`form_3_id`, `action_part`, "
//						+ "`prob_desc`, `form3_sl_no`, `action_required`,`district_id`, `cycle_id`, `financial_year`, "
//						+ "`user_id`, `record_created`) VALUES (?,?,?,?," + "?,?,?,?,?,?)",
//				new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//						ps.setString(1, "" + p_key);
//						ps.setString(2, "Policy/governance");
//						ps.setString(3, list6_policy_insert.get(i).getDocument_description_p_f_h_s_p());
//						ps.setString(4, "1.1.1");
//						ps.setString(5, list6_policy_insert.get(i).getDocument_action_required());
//						ps.setString(6, model.getDistrict());
//						ps.setString(7, model.getCycle());
//						ps.setString(8, model.getYear());
//						ps.setString(9, model.getUserid());
//						ps.setString(10, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list6_policy_insert.size();
//					}
//
//				});



		SavedForm3DefineResponse responseobj = new SavedForm3DefineResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	
	
	public Form3DefineSendAllDataBean retrieveForm3Define_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(
			UserCredentialsFromAndroidBean model,String LoggedinUserId) {
		
	
		Form3DefineSendAllDataBean response = new Form3DefineSendAllDataBean();
		
		


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
			
						
			response.setForm3Define(new ArrayList<>());
			response.setForm3ActionEngmtDetails(new ArrayList<>());
			response.setForm3ActionEngmtActionReq(new ArrayList<>());
			
			response.setError_code("200"); 
			response.setMessage("Form3 Define All Cycles and Year for given district"  );
		}
		else {
			System.out.println("Not Returning in half!!! 0 or different value rather than -1");
		}

		List<Form3DefinePrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form3DefineActionEngmtDetailsTableDataBean> list2 = new ArrayList<>();
		List<Form3ActionEngmtActionReqTableDataBean> list3 = new ArrayList<>();
		
		
		String sql1 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql1 = "SELECT form3verify.`completed`, form3verify.`form_3_id`, form3verify.`form_3_checkdate`,  "
					+ "  form3verify.`form_3_meeting_venue`,    form3verify.`form_3_filled_by`,  "
					+ "  form3verify.`form3_chair_person`,    form3verify.`form3_chair_person_others`, "
					+ "   form3verify.`theme_id`,    form3verify.`district_id`,    form3verify.`cycle_id`, "
					+ "  form3verify.`financial_year`,   form3verify.`user_id`,    form3verify.`record_created`,"
					+ "  d.`district_id` as `dst2`, d.`country_id`, d.`state_id`, cs.`region_id` FROM `form_3_1_verify`   form3verify  "
					+ "  left join  `district` d on form3verify.district_id=d.district_id   "
					+ "  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3verify.`financial_year` >= 2019";

		}
		else {
			sql1 = "SELECT form3verify.`completed`, form3verify.`form_3_id`,    form3verify.`form_3_checkdate`,  "
					+ "  form3verify.`form_3_meeting_venue`,    form3verify.`form_3_filled_by`,  "
					+ "  form3verify.`form3_chair_person`,    form3verify.`form3_chair_person_others`, "
					+ "   form3verify.`theme_id`,    form3verify.`district_id`,    form3verify.`cycle_id`, "
					+ "  form3verify.`financial_year`,   form3verify.`user_id`,    form3verify.`record_created`,"
					+ "  d.`district_id` as `dst2`, d.`country_id`, d.`state_id`, cs.`region_id` FROM `form_3_1_verify`   form3verify  "
					+ "  left join  `district` d on form3verify.district_id=d.district_id   "
					+ "  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3verify.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form3verify.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form3verify.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form3DefinePrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form3DefinePrimaryTableDataBean tempobj = new Form3DefinePrimaryTableDataBean();

				tempobj.setAuto_id(rs.getString("form_3_id"));
				tempobj.setMeetingdate(rs.getString("form_3_checkdate"));
				tempobj.setMeetingvenue(rs.getString("form_3_meeting_venue"));
				tempobj.setThemeleaderofcycle(rs.getString("form_3_filled_by"));
				tempobj.setChairpersonid(rs.getString("form3_chair_person"));
				tempobj.setOtherchairperson(rs.getString("form3_chair_person_others"));
				tempobj.setThemeid(rs.getString("theme_id"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				tempobj.setCountry_id(rs.getString("country_id"));
				tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setCompleted(rs.getString("completed"));
				tempobj.setDatafrom("WEB");

				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		String sql2 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql2 = "SELECT  form3engdetails.`id`,   form3engdetails.`form_3_id`, "
					+ " form3engdetails.`action_part_of_engagement`,  form3engdetails.`description_p_f_h_s_p`,"
					+ "  form3engdetails.`possible_s_t_i`,   form3engdetails. `district_id`,  "
					+ "  form3engdetails. `cycle_id`,    form3engdetails.`financial_year`,  "
					+ "  form3engdetails.`user_id`,   form3engdetails. `record_created`,d.`district_id` as `dst2`,"
					+ " d.`country_id`, d.`state_id`, cs.`region_id`  FROM  `form_3_1_action_part_engagement_nm_details`   form3engdetails  "
					+ "  left join  `district` d on form3engdetails.district_id=d.district_id  "
					+ "  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3engdetails.`financial_year` >= 2019";
	
		}
		else {
			sql2 = "SELECT  form3engdetails.`id`,   form3engdetails.`form_3_id`, "
					+ " form3engdetails.`action_part_of_engagement`,  form3engdetails.`description_p_f_h_s_p`,"
					+ "  form3engdetails.`possible_s_t_i`,   form3engdetails. `district_id`,  "
					+ "  form3engdetails. `cycle_id`,    form3engdetails.`financial_year`,  "
					+ "  form3engdetails.`user_id`,   form3engdetails. `record_created`,d.`district_id` as `dst2`,"
					+ " d.`country_id`, d.`state_id`, cs.`region_id`  FROM  `form_3_1_action_part_engagement_nm_details`   form3engdetails  "
					+ "  left join  `district` d on form3engdetails.district_id=d.district_id  "
					+ "  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3engdetails.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form3engdetails.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form3engdetails.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] param2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list2 = jdbcTemplate.query(sql2, param2, rs -> {

			List<Form3DefineActionEngmtDetailsTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form3DefineActionEngmtDetailsTableDataBean tempobj = new Form3DefineActionEngmtDetailsTableDataBean();

				tempobj.setAuto_id(rs.getString("id"));
				tempobj.setForm_3_id(rs.getString("form_3_id"));
				
				String actionname = "Service delivery";
				
				if("Service delivery".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "SERVICED";
				}
				else if("Workforce".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "WORKFORCE";
				}
				else if("Supplies & technology".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "SUPPLIES";				
				}				
				else if("Health information".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "HEALTHINFO";
				}
				else if("Finance".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "FINANCE";
				}
				else if("Policy/governance".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "POLICYG";
				}				
				tempobj.setAction_name(actionname);
				tempobj.setDescription_text(rs.getString("description_p_f_h_s_p"));
				tempobj.setPossible_soln(rs.getString("possible_s_t_i"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				tempobj.setCountry_id(rs.getString("country_id"));
				tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setDatafrom("WEB");

				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		
		String sql3 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql3 = "SELECT form3actionreq.`action_req_id`,    form3actionreq.`form_3_id`,  "
					+ "  form3actionreq.`action_part`,    form3actionreq.`prob_desc`,    form3actionreq.`form3_sl_no`, "
					+ "   form3actionreq.`action_required`,    form3actionreq.`district_id`,    form3actionreq.`cycle_id`, "
					+ "   form3actionreq.`financial_year`,    form3actionreq.`user_id`,    form3actionreq.`record_created`,  "
					+ "  form3actionreq.`form_3_1_action_part_engagement_nm_details_pkey`,d.`district_id` as `dst2`, d.`country_id`,"
					+ " d.`state_id`, cs.`region_id`    FROM `form_3_1_action_part_engagement_action_req`   form3actionreq  "
					+ "  left join  `district` d on form3actionreq.district_id=d.district_id  "
					+ "   left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3actionreq.`financial_year` >= 2019";
	
		}
		else {
			sql3 = "SELECT form3actionreq.`action_req_id`,    form3actionreq.`form_3_id`,  "
					+ "  form3actionreq.`action_part`,    form3actionreq.`prob_desc`,    form3actionreq.`form3_sl_no`, "
					+ "   form3actionreq.`action_required`,    form3actionreq.`district_id`,    form3actionreq.`cycle_id`, "
					+ "   form3actionreq.`financial_year`,    form3actionreq.`user_id`,    form3actionreq.`record_created`,  "
					+ "  form3actionreq.`form_3_1_action_part_engagement_nm_details_pkey`,d.`district_id` as `dst2`, d.`country_id`,"
					+ " d.`state_id`, cs.`region_id`    FROM `form_3_1_action_part_engagement_action_req`   form3actionreq  "
					+ "  left join  `district` d on form3actionreq.district_id=d.district_id  "
					+ "   left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3actionreq.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form3actionreq.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form3actionreq.`financial_year` IN ("+user_priv.getAll_years()+");";

		}
		

		
		Object[] param3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list3 = jdbcTemplate.query(sql3, param3, rs -> {

			List<Form3ActionEngmtActionReqTableDataBean> list = new ArrayList<>();
			while (rs.next()) {

				Form3ActionEngmtActionReqTableDataBean tempobj = new Form3ActionEngmtActionReqTableDataBean();

				tempobj.setAuto_id(rs.getString("action_req_id"));
				tempobj.setForm_3_id(rs.getString("form_3_id"));
				
                 String actionname = "Service delivery";
				
				if("Service delivery".equals(rs.getString("action_part"))) { 
					actionname = "SERVICED";
				}
				else if("Workforce".equals(rs.getString("action_part"))) { 
					actionname = "WORKFORCE";
				}
				else if("Supplies & technology".equals(rs.getString("action_part"))) { 
					actionname = "SUPPLIES";				
				}				
				else if("Health information".equals(rs.getString("action_part"))) { 
					actionname = "HEALTHINFO";
				}
				else if("Finance".equals(rs.getString("action_part"))) { 
					actionname = "FINANCE";
				}
				else if("Policy/governance".equals(rs.getString("action_part"))) { 
					actionname = "POLICYG";
				}	
				
				tempobj.setAction_part(actionname);
				tempobj.setProb_desc(rs.getString("prob_desc"));
				tempobj.setAction_required(rs.getString("action_required"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				tempobj.setForm_3_action_engmt_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				tempobj.setCountry_id(rs.getString("country_id"));
				tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setDatafrom("WEB");

				list.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return list;
		});

		response.setForm3Define(list1);
		response.setForm3ActionEngmtDetails(list2);
		response.setForm3ActionEngmtActionReq(list3);
		
		response.setError_code("200"); 
		response.setMessage("Form3 Define All Cycles and Year for given district"  );

		return response;
	}


	public Form3DefineSendAllDataBean retrieveForm3Define_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(
			UserCredentialsFromAndroidBean model) {
		
		
		Form3DefineSendAllDataBean response = new Form3DefineSendAllDataBean();

		List<Form3DefinePrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form3DefineActionEngmtDetailsTableDataBean> list2 = new ArrayList<>();
		List<Form3ActionEngmtActionReqTableDataBean> list3 = new ArrayList<>();

		String sql1 = "SELECT form3verify.`completed`, form3verify.`form_3_id`,    form3verify.`form_3_checkdate`,  "
				+ "  form3verify.`form_3_meeting_venue`,    form3verify.`form_3_filled_by`,  "
				+ "  form3verify.`form3_chair_person`,    form3verify.`form3_chair_person_others`, "
				+ "   form3verify.`theme_id`,    form3verify.`district_id`,    form3verify.`cycle_id`, "
				+ "  form3verify.`financial_year`,   form3verify.`user_id`,    form3verify.`record_created`,"
				+ "  d.`district_id` as `dst2`, d.`country_id`, d.`state_id`, cs.`region_id` FROM `form_3_1_verify`   form3verify  "
				+ "  left join  `district` d on form3verify.district_id=d.district_id   "
				+ "  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3verify.`financial_year` >= 2019";

		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form3DefinePrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form3DefinePrimaryTableDataBean tempobj = new Form3DefinePrimaryTableDataBean();

				tempobj.setAuto_id(rs.getString("form_3_id"));
				tempobj.setMeetingdate(rs.getString("form_3_checkdate"));
				tempobj.setMeetingvenue(rs.getString("form_3_meeting_venue"));
				tempobj.setThemeleaderofcycle(rs.getString("form_3_filled_by"));
				tempobj.setChairpersonid(rs.getString("form3_chair_person"));
				tempobj.setOtherchairperson(rs.getString("form3_chair_person_others"));
				tempobj.setThemeid(rs.getString("theme_id"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				tempobj.setCountry_id(rs.getString("country_id"));
				tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setCompleted(rs.getString("completed"));
				tempobj.setDatafrom("WEB");

				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql2 = "SELECT  form3engdetails.`id`,   form3engdetails.`form_3_id`, "
				+ " form3engdetails.`action_part_of_engagement`,  form3engdetails.`description_p_f_h_s_p`,"
				+ "  form3engdetails.`possible_s_t_i`,   form3engdetails. `district_id`,  "
				+ "  form3engdetails. `cycle_id`,    form3engdetails.`financial_year`,  "
				+ "  form3engdetails.`user_id`,   form3engdetails. `record_created`,d.`district_id` as `dst2`,"
				+ " d.`country_id`, d.`state_id`, cs.`region_id`  FROM  `form_3_1_action_part_engagement_nm_details`   form3engdetails  "
				+ "  left join  `district` d on form3engdetails.district_id=d.district_id  "
				+ "  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3engdetails.`financial_year` >= 2019";

		Object[] param2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list2 = jdbcTemplate.query(sql2, param2, rs -> {

			List<Form3DefineActionEngmtDetailsTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form3DefineActionEngmtDetailsTableDataBean tempobj = new Form3DefineActionEngmtDetailsTableDataBean();

				tempobj.setAuto_id(rs.getString("id"));
				tempobj.setForm_3_id(rs.getString("form_3_id"));
				
				String actionname = "Service delivery";
				
				if("Service delivery".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "SERVICED";
				}
				else if("Workforce".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "WORKFORCE";
				}
				else if("Supplies & technology".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "SUPPLIES";				
				}				
				else if("Health information".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "HEALTHINFO";
				}
				else if("Finance".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "FINANCE";
				}
				else if("Policy/governance".equals(rs.getString("action_part_of_engagement"))) { 
					actionname = "POLICYG";
				}				
				tempobj.setAction_name(actionname);
				tempobj.setDescription_text(rs.getString("description_p_f_h_s_p"));
				tempobj.setPossible_soln(rs.getString("possible_s_t_i"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				tempobj.setCountry_id(rs.getString("country_id"));
				tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setDatafrom("WEB");

				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql3 = "SELECT form3actionreq.`action_req_id`,    form3actionreq.`form_3_id`,  "
				+ "  form3actionreq.`action_part`,    form3actionreq.`prob_desc`,    form3actionreq.`form3_sl_no`, "
				+ "   form3actionreq.`action_required`,    form3actionreq.`district_id`,    form3actionreq.`cycle_id`, "
				+ "   form3actionreq.`financial_year`,    form3actionreq.`user_id`,    form3actionreq.`record_created`,  "
				+ "  form3actionreq.`form_3_1_action_part_engagement_nm_details_pkey`,d.`district_id` as `dst2`, d.`country_id`,"
				+ " d.`state_id`, cs.`region_id`    FROM `form_3_1_action_part_engagement_action_req`   form3actionreq  "
				+ "  left join  `district` d on form3actionreq.district_id=d.district_id  "
				+ "   left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form3actionreq.`financial_year` >= 2019";

		Object[] param3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list3 = jdbcTemplate.query(sql3, param3, rs -> {

			List<Form3ActionEngmtActionReqTableDataBean> list = new ArrayList<>();
			while (rs.next()) {

				Form3ActionEngmtActionReqTableDataBean tempobj = new Form3ActionEngmtActionReqTableDataBean();

				tempobj.setAuto_id(rs.getString("action_req_id"));
				tempobj.setForm_3_id(rs.getString("form_3_id"));
				
                 String actionname = "Service delivery";
				
				if("Service delivery".equals(rs.getString("action_part"))) { 
					actionname = "SERVICED";
				}
				else if("Workforce".equals(rs.getString("action_part"))) { 
					actionname = "WORKFORCE";
				}
				else if("Supplies & technology".equals(rs.getString("action_part"))) { 
					actionname = "SUPPLIES";				
				}				
				else if("Health information".equals(rs.getString("action_part"))) { 
					actionname = "HEALTHINFO";
				}
				else if("Finance".equals(rs.getString("action_part"))) { 
					actionname = "FINANCE";
				}
				else if("Policy/governance".equals(rs.getString("action_part"))) { 
					actionname = "POLICYG";
				}	
				
				tempobj.setAction_part(actionname);
				tempobj.setProb_desc(rs.getString("prob_desc"));
				tempobj.setAction_required(rs.getString("action_required"));
				tempobj.setDistrict_id(rs.getString("district_id"));
				tempobj.setCycle_id(rs.getString("cycle_id"));
				tempobj.setYear(rs.getString("financial_year"));
				tempobj.setUser_id(rs.getString("user_id"));
				tempobj.setRecordcreated(rs.getString("record_created"));
				tempobj.setForm_3_action_engmt_details_pkey(
						rs.getString("form_3_1_action_part_engagement_nm_details_pkey"));
				tempobj.setCountry_id(rs.getString("country_id"));
				tempobj.setProvince_id(rs.getString("state_id"));
				tempobj.setTimestamp(rs.getString("record_created"));
				tempobj.setDatafrom("WEB");

				list.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return list;
		});

		response.setForm3Define(list1);
		response.setForm3ActionEngmtDetails(list2);
		response.setForm3ActionEngmtActionReq(list3);
		
		response.setError_code("200"); 
		response.setMessage("Form3 Define All Cycles and Year for given district"  );

		return response;
	}

	public Form3DefineViewInEdit retrieveInEditForm3DefineDetails(String district_id, String cycle_id, String year,
			String user_id) {

		Form3DefineViewInEdit obj = new Form3DefineViewInEdit();

//		INSERT INTO `form_3_1_verify`(`form_3_checkdate`, `form_3_meeting_venue`, 
//				`form_3_filled_by`, `form3_chair_person`, `theme_id`, `district_id`, `cycle_id`, `financial_year`,
//				`user_id`, `record_created`)values (:form_3_checkdate,:form_3_meeting_venue,:form_3_filled_by,:form3_chair_person,:theme_id,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql1 = "SELECt * FROM `form_3_1_verify` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params1 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql1, params1, rs -> {

			while (rs.next()) {

				obj.setForm_3_checkdate(rs.getString("form_3_checkdate"));
				obj.setForm_3_meeting_venue(rs.getString("form_3_meeting_venue"));
				obj.setForm_3_filled_by(rs.getString("form_3_filled_by"));
				obj.setForm3_chair_person(rs.getString("form3_chair_person"));
				obj.setForm3_chair_person_others(rs.getString("form3_chair_person_others"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUserid(rs.getString("user_id"));
				obj.setForm_3_id(rs.getString("form_3_id"));
				obj.setCompleted(rs.getString("completed"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`, `action_part_of_engagement`, 
//				`description_p_f_h_s_p`, `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, 
//				`record_created`) values (:form_3_id,:action_part_of_engagement,:description_p_f_h_s_p,:possible_s_t_i,
//						:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=? order by id";
		Object[] param2 = new Object[] { district_id, cycle_id, year, "Service delivery" };

		List<Form3DefineEditOnlyArray> list1_service_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineEditOnlyArray tempobj = new Form3DefineEditOnlyArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list1_service_arr.add(tempobj);

				obj.setService_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setService_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Workforce" };

		List<Form3DefineEditOnlyArray> list2_workforce_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineEditOnlyArray tempobj = new Form3DefineEditOnlyArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list2_workforce_arr.add(tempobj);

				obj.setWorkforce_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setWorkforce_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Supplies & technology" };

		List<Form3DefineEditOnlyArray> list3_supplies_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineEditOnlyArray tempobj = new Form3DefineEditOnlyArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list3_supplies_arr.add(tempobj);

				obj.setSupplies_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setSupplies_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Health information" };

		List<Form3DefineEditOnlyArray> list4_health_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineEditOnlyArray tempobj = new Form3DefineEditOnlyArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list4_health_arr.add(tempobj);

				obj.setHealth_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setHealth_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Finance" };

		List<Form3DefineEditOnlyArray> list5_finance_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineEditOnlyArray tempobj = new Form3DefineEditOnlyArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list5_finance_arr.add(tempobj);

				obj.setFinance_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setFinance_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Policy/governance" };

		List<Form3DefineEditOnlyArray> list6_policy_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineEditOnlyArray tempobj = new Form3DefineEditOnlyArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list6_policy_arr.add(tempobj);

				obj.setPolicy_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setPolicy_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		for (int pos = 0; pos < list1_service_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Service delivery",
					list1_service_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list1_service_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setService_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list1_service_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list1_service_arr.get(i).setAction_req_id(tot_actions_p_key);

		}

//		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? order by action_req_id";
//		param2 = new Object[] { district_id, cycle_id, year, "Service delivery" };
//
//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list1_service_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setService_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Workforce" };

		for (int pos = 0; pos < list2_workforce_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Workforce",
					list2_workforce_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list2_workforce_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setWorkforce_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});
			list2_workforce_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list2_workforce_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list2_workforce_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setWorkforce_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Supplies & technology" };

		for (int pos = 0; pos < list3_supplies_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Supplies & technology",
					list3_supplies_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list3_supplies_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setSupplies_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});
			list3_supplies_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list3_supplies_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list3_supplies_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setSupplies_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Health information" };

		for (int pos = 0; pos < list4_health_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Health information",
					list4_health_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list4_health_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setSupplies_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});
			list4_health_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list4_health_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list4_health_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setHealth_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Finance" };

		for (int pos = 0; pos < list5_finance_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Finance",
					list5_finance_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list5_finance_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setSupplies_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list5_finance_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list5_finance_arr.get(i).setAction_req_id(tot_actions_p_key);

		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list5_finance_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setFinance_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Policy/governance" };

		for (int pos = 0; pos < list6_policy_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Policy/governance",
					list6_policy_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> tot_actions = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list6_policy_arr.get(i).setDocument_action_required(tot_actions);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list6_policy_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list6_policy_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setPolicy_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		obj.setService_array(list1_service_arr);
		obj.setWorkforce_array(list2_workforce_arr);
		obj.setSupplies_array(list3_supplies_arr);
		obj.setHealth_array(list4_health_arr);
		obj.setFinance_array(list5_finance_arr);
		obj.setPolicy_array(list6_policy_arr);

		return obj;

	}

	public Form3Define retrieveForm3DefineDetails(String district_id, String cycle_id, String year, String user_id) {

		Form3Define obj = new Form3Define();

//		INSERT INTO `form_3_1_verify`(`form_3_checkdate`, `form_3_meeting_venue`, 
//				`form_3_filled_by`, `form3_chair_person`, `theme_id`, `district_id`, `cycle_id`, `financial_year`,
//				`user_id`, `record_created`)values (:form_3_checkdate,:form_3_meeting_venue,:form_3_filled_by,:form3_chair_person,:theme_id,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql1 = "SELECt * FROM `form_3_1_verify` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params1 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql1, params1, rs -> {

			while (rs.next()) {

				obj.setForm_3_checkdate(rs.getString("form_3_checkdate"));
				obj.setForm_3_meeting_venue(rs.getString("form_3_meeting_venue"));
				obj.setForm_3_filled_by(rs.getString("form_3_filled_by"));
				obj.setForm3_chair_person(rs.getString("form3_chair_person"));
				obj.setForm3_chair_person_others(rs.getString("form3_chair_person_others"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUserid(rs.getString("user_id"));
				obj.setForm_3_id(rs.getString("form_3_id"));
				obj.setCompleted(rs.getString("completed"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		INSERT INTO `form_3_1_action_part_engagement_nm_details`(`form_3_id`, `action_part_of_engagement`, 
//				`description_p_f_h_s_p`, `possible_s_t_i`,`district_id`, `cycle_id`, `financial_year`, `user_id`, 
//				`record_created`) values (:form_3_id,:action_part_of_engagement,:description_p_f_h_s_p,:possible_s_t_i,
//						:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=? order by id";
		Object[] param2 = new Object[] { district_id, cycle_id, year, "Service delivery" };

		List<Form3DefineCommonArray> list1_service_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineCommonArray tempobj = new Form3DefineCommonArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list1_service_arr.add(tempobj);

				obj.setService_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setService_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Workforce" };

		List<Form3DefineCommonArray> list2_workforce_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineCommonArray tempobj = new Form3DefineCommonArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list2_workforce_arr.add(tempobj);

				obj.setWorkforce_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setWorkforce_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Supplies & technology" };

		List<Form3DefineCommonArray> list3_supplies_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineCommonArray tempobj = new Form3DefineCommonArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list3_supplies_arr.add(tempobj);

				obj.setSupplies_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setSupplies_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Health information" };

		List<Form3DefineCommonArray> list4_health_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineCommonArray tempobj = new Form3DefineCommonArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list4_health_arr.add(tempobj);

				obj.setHealth_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setHealth_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Finance" };

		List<Form3DefineCommonArray> list5_finance_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineCommonArray tempobj = new Form3DefineCommonArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list5_finance_arr.add(tempobj);

				obj.setFinance_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setFinance_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part_of_engagement`=?  order by id";
		param2 = new Object[] { district_id, cycle_id, year, "Policy/governance" };

		List<Form3DefineCommonArray> list6_policy_arr = new ArrayList<>();

		jdbcTemplate.query(sql2, param2, rs -> {

			while (rs.next()) {
				Form3DefineCommonArray tempobj = new Form3DefineCommonArray();
				tempobj.setDocument_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				tempobj.setDocument_possible_s_t_i(rs.getString("possible_s_t_i"));
				tempobj.setForm_3_1_action_part_engagement_nm_details_pkey(rs.getString("id"));

				list6_policy_arr.add(tempobj);

				obj.setPolicy_service_description_p_f_h_s_p(rs.getString("description_p_f_h_s_p"));
				obj.setPolicy_service_possible_s_t_i(rs.getString("possible_s_t_i"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		for (int pos = 0; pos < list1_service_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Service delivery",
					list1_service_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list1_service_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setService_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list1_service_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list1_service_arr.get(i).setAction_req_id(tot_actions_p_key);

		}

//		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? order by action_req_id";
//		param2 = new Object[] { district_id, cycle_id, year, "Service delivery" };
//
//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list1_service_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setService_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Workforce" };

		for (int pos = 0; pos < list2_workforce_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Workforce",
					list2_workforce_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list2_workforce_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setWorkforce_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});
			list2_workforce_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list2_workforce_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list2_workforce_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setWorkforce_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Supplies & technology" };

		for (int pos = 0; pos < list3_supplies_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Supplies & technology",
					list3_supplies_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list3_supplies_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setSupplies_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});
			list3_supplies_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list3_supplies_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list3_supplies_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setSupplies_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Health information" };

		for (int pos = 0; pos < list4_health_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Health information",
					list4_health_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list4_health_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setSupplies_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});
			list4_health_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list4_health_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list4_health_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setHealth_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Finance" };

		for (int pos = 0; pos < list5_finance_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Finance",
					list5_finance_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> act = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {
					// list5_finance_arr.get(i).setDocument_action_required(rs.getString("action_required"));
					obj.setSupplies_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list5_finance_arr.get(i).setDocument_action_required(act);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list5_finance_arr.get(i).setAction_req_id(tot_actions_p_key);

		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list5_finance_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setFinance_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=?  order by action_req_id";
		param2 = new Object[] { district_id, cycle_id, year, "Policy/governance" };

		for (int pos = 0; pos < list6_policy_arr.size(); pos++) {

			sql2 = "SELECt * FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and   `action_part`=? and `form_3_1_action_part_engagement_nm_details_pkey`=?";
			param2 = new Object[] { district_id, cycle_id, year, "Policy/governance",
					list6_policy_arr.get(pos).getForm_3_1_action_part_engagement_nm_details_pkey() };

			int i = pos;
			List<String> tot_actions = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_required"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list6_policy_arr.get(i).setDocument_action_required(tot_actions);

			List<String> tot_actions_p_key = jdbcTemplate.query(sql2, param2, rs -> {

				List<String> list = new ArrayList<>();
				while (rs.next()) {

					obj.setPolicy_action_required(rs.getString("action_required"));
					list.add(rs.getString("action_req_id"));
				}
				/* We can also return any variable-data from here but not used currently */
				return list;
			});

			list6_policy_arr.get(i).setAction_req_id(tot_actions_p_key);
		}

//		jdbcTemplate.query(sql2, param2, rs -> {
//
//			int i = 0;
//			while (rs.next()) {
//				list6_policy_arr.get(i).setDocument_action_required(rs.getString("action_required"));
//				obj.setPolicy_action_required(rs.getString("action_required"));
//				i++;
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		obj.setService_array(list1_service_arr);
		obj.setWorkforce_array(list2_workforce_arr);
		obj.setSupplies_array(list3_supplies_arr);
		obj.setHealth_array(list4_health_arr);
		obj.setFinance_array(list5_finance_arr);
		obj.setPolicy_array(list6_policy_arr);

		return obj;
	}

	public DeleteForm3DefineResponse deleteForm3DefineDetails(String district_id, String cycle_id, String year,
			String user_id) {

		DeleteForm3DefineResponse responseobj = new DeleteForm3DefineResponse();

		
		Object[] params1 = { district_id, cycle_id, year };
		int rows = jdbcTemplate.update(
				"DELETE FROM `form_3_1_action_part_engagement_action_req` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params1);
	

		

		Object[] params2 = { district_id, cycle_id, year };
		int rows2 = jdbcTemplate.update(
				"DELETE FROM `form_3_1_action_part_engagement_nm_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params2);
		

		Object[] params3 = { district_id, cycle_id, year };
		int rows3 = jdbcTemplate.update(
				"DELETE FROM `form_3_1_verify` where `district_id`=? and `cycle_id`=? and `financial_year`=?", params3);
		

		responseobj.setProcessname("deleted");
		if (rows > 1 && rows2 > 1 && rows3 > 1) {
			responseobj.setResponse_val("1");
		} else {
			responseobj.setResponse_val("0");
		}

		return responseobj;
	}

}
