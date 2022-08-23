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
import com.tattvafoundation.diphonline.controller.OptionalindicatorBean;
import com.tattvafoundation.diphonline.controller.OptionalindicatorsList;
import com.tattvafoundation.diphonline.model.AllFormsDataFetchFromAndroidBean;
import com.tattvafoundation.diphonline.model.Area_Indicator_Object;
import com.tattvafoundation.diphonline.model.Area_Indicator_Save_Edit;
import com.tattvafoundation.diphonline.model.Areas_of_Indicators_List;
import com.tattvafoundation.diphonline.model.DeleteForm1AResponse;
import com.tattvafoundation.diphonline.model.Form1ASourceIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form1BConsumeDataFromAndroidBean;
import com.tattvafoundation.diphonline.model.Form1BEditKeyNgoDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BEditKeyStakeholderDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BEditUpdate;
import com.tattvafoundation.diphonline.model.Form1BIndicatorsDetailsBean;
import com.tattvafoundation.diphonline.model.Form1BIndicatorsTableDataBean;
import com.tattvafoundation.diphonline.model.Form1BNgoTableDataBean;
import com.tattvafoundation.diphonline.model.Form1BPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form1BSave;
import com.tattvafoundation.diphonline.model.Form1BSaveDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BSaveIndicatorsModel;
import com.tattvafoundation.diphonline.model.Form1BSaveKeyNgoDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BSaveKeyStakeholderDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BStakeHolderTableDataBean;
import com.tattvafoundation.diphonline.model.Form1BView;
import com.tattvafoundation.diphonline.model.Form1BdocumentsBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpSendAllDataBean;
import com.tattvafoundation.diphonline.model.IndicatorsList;
import com.tattvafoundation.diphonline.model.Menu_Area_Indicator_Object;
import com.tattvafoundation.diphonline.model.SavedForm1BResponse;
import com.tattvafoundation.diphonline.model.SendAndroidForm1BSynchedDataBean;
import com.tattvafoundation.diphonline.model.User_Districts_Privileges;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Repository
public class Form1BDAO {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Form1BDAO.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public SavedForm1BResponse editUpdateForm1BToDb(Form1BEditUpdate model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);		

		int row = 0;


		String sql1 = " UPDATE `hsca_district_demographic_dtls` SET `date_of_verification`=?, `filled_by`=?,`chairperson_of_meeting`=?,`chairperson_of_meeting_others`=?,"
				+ "				`total_area`=?, `total_area_demogra_id`=?, `total_pop`=?, `total_pop_demogra_id`=?,"
				+ "				`num_women_in_reproductive_age_15_49_y`=?, `num_women_in_reproductive_age_15_49_y_source`=?, "
				+ "				`num_child_under_5_y`=?, `num_child_under_5_y_demogra_id`=?, `rural_pop`=?, `rural_pop_demogra_id`=?,"
				+ "				`sc_pop`=?, `sc_pop_demogra_id`=?, `st_pop`=?, `st_pop_demogra_id`=?, `pop_density`=?, "
				+ "				`pop_density_demogra_id`=?, `total_literacy`=?, `total_literacy_demogra_id`=?,"
				+ "				`fem_literacy`=?, `fem_literacy_demogra_id`=?, "
				+ "				`user_id`=?,`record_created`=?,`completed`=? WHERE `dist_demogra_dtl_id`=?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setString(1, model.getDate_of_verification());
			ps.setString(2, model.getFilled_by());
			ps.setString(3, model.getVerified_by_name());
			ps.setString(4, model.getVerified_by_other_actual_name());
			ps.setString(5, model.getTotal_area());
			ps.setString(6, model.getTotal_area_demogra_id());
			ps.setString(7, model.getTotal_pop());
			ps.setString(8, model.getTotal_pop_demogra_id());
			ps.setString(9, model.getNum_women_in_reproductive_age_15_49_y());
			ps.setString(10, model.getNum_women_in_reproductive_age_15_49_y_source());
			ps.setString(11, model.getNum_child_under_5_y());
			ps.setString(12, model.getNum_child_under_5_y_demogra_id());
			ps.setString(13, model.getRural_pop());
			ps.setString(14, model.getRural_pop_demogra_id());
			ps.setString(15, model.getSc_pop());
			ps.setString(16, model.getSc_pop_demogra_id());
			ps.setString(17, model.getSt_pop());
			ps.setString(18, model.getSt_pop_demogra_id());
			ps.setString(19, model.getPop_density());
			ps.setString(20, model.getPop_density_demogra_id());
			ps.setString(21, model.getTotal_literacy());
			ps.setString(22, model.getTotal_literacy_demogra_id());
			ps.setString(23, model.getFem_literacy());
			ps.setString(24, model.getFem_literacy_demogra_id());
			ps.setString(25, model.getUserid());
			ps.setString(26, formatedDateTime);
			ps.setString(27, model.getCompleted());
			ps.setString(28, model.getDist_demogra_dtl_id());
			return ps;
		});
		

		int row2 = 0;

		String sql2 = "UPDATE `hsca_district_others` SET `total_area_others`=?,"
				+ "`total_pop_others`=?, `num_women_in_reproductive_others`=?,"
				+ "`no_of_child_under5_others`=?,`rural_pop_others`=?,"
				+ "`sc_pop_others`=?, `st_pop_others`=?,`pop_dens_others`=?,  "
				+ "`tot_lit_others`=?, `female_lit_others`=?, `user_id`=?,"
				+ "`record_created`=? WHERE `dist_demogra_dtl_id`=?";

		row2 = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2);
			ps.setString(1, model.getTotal_area_others());
			ps.setString(2, model.getTotal_pop_others());
			ps.setString(3, model.getNum_women_in_reproductive_others());
			ps.setString(4, model.getNo_of_child_under5_others());
			ps.setString(5, model.getRural_pop_others());
			ps.setString(6, model.getSc_pop_others());
			ps.setString(7, model.getSt_pop_others());
			ps.setString(8, model.getPop_dens_others());
			ps.setString(9, model.getTot_lit_others());
			ps.setString(10, model.getFemale_lit_others());
			ps.setString(11, model.getUserid());
			ps.setString(12, formatedDateTime);
			ps.setString(13, model.getDist_demogra_dtl_id());

			return ps;
		});

		

		/*-------------------------------------------------------------------*/

		if (model.getKey_ngo_info_array().size() != 0) {

			List<Form1BEditKeyNgoDocumentsArray> ngo_info_array = model.getKey_ngo_info_array();

			List<Form1BEditKeyNgoDocumentsArray> ngo_info_array_insert = new ArrayList<>();
			List<Form1BEditKeyNgoDocumentsArray> ngo_info_array_update = new ArrayList<>();

			for (int i = 0; i < ngo_info_array.size(); i++) {
				Form1BEditKeyNgoDocumentsArray tempobj = ngo_info_array.get(i);

				if ("0".equals(tempobj.getPrimary_key())) {
					ngo_info_array_insert.add(tempobj);
				} else {
					ngo_info_array_update.add(tempobj);
				}
			}
			

			if (ngo_info_array_insert.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `key_ngo_demogra`(`dist_demogra_dtl_id`, `key_ngo_info`, `key_ngo_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)  "
								+ "VALUES (?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + model.getDist_demogra_dtl_id());
								ps.setString(2, ngo_info_array_insert.get(i).getNgo_name());
								ps.setString(3, ngo_info_array_insert.get(i).getNgo_details());
								ps.setString(4, model.getDistrict());
								ps.setString(5, model.getCycle());
								ps.setString(6, model.getYear());
								ps.setString(7, model.getUserid());
								ps.setString(8, formatedDateTime);
							}

							public int getBatchSize() {
								return ngo_info_array_insert.size();
							}

						});
			}

			int key_ngo_row = 0;

			jdbcTemplate.batchUpdate(
					"UPDATE `key_ngo_demogra` SET  `key_ngo_info` = ?, `key_ngo_source` = ?, `user_id`=?,  `record_created` = ? WHERE `key_ngo_id` = ?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + ngo_info_array_update.get(i).getNgo_name());
							ps.setString(2, "" + ngo_info_array_update.get(i).getNgo_details());
							ps.setString(3, "" + model.getUserid());
							ps.setString(4, "" + formatedDateTime);
							ps.setString(5, "" + ngo_info_array_update.get(i).getPrimary_key());
						}

						public int getBatchSize() {
							return ngo_info_array_update.size();
						}

					});

			

		}

		if (model.getKey_ngo_source_array().size() != 0) {

			List<Form1BEditKeyStakeholderDocumentsArray> ngo_source_array = model.getKey_ngo_source_array();

			List<Form1BEditKeyStakeholderDocumentsArray> ngo_source_array_insert = new ArrayList<>();
			List<Form1BEditKeyStakeholderDocumentsArray> ngo_source_array_update = new ArrayList<>();

			for (int i = 0; i < ngo_source_array.size(); i++) {
				Form1BEditKeyStakeholderDocumentsArray tempobj = ngo_source_array.get(i);

				if ("0".equals(tempobj.getPrimary_key())) {
					ngo_source_array_insert.add(tempobj);
				} else {
					ngo_source_array_update.add(tempobj);
				}
			}

			

			if (ngo_source_array_insert.size() != 0) {
				jdbcTemplate.batchUpdate(
						"INSERT INTO `key_pvt_demogra`(`dist_demogra_dtl_id`, `key_pvt_info`, `key_pvt_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)    "
								+ "VALUES (?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + model.getDist_demogra_dtl_id());
								ps.setString(2, ngo_source_array_insert.get(i).getStakeholder_name());
								ps.setString(3, ngo_source_array_insert.get(i).getContact_details());
								ps.setString(4, model.getDistrict());
								ps.setString(5, model.getCycle());
								ps.setString(6, model.getYear());
								ps.setString(7, model.getUserid());
								ps.setString(8, formatedDateTime);
							}

							public int getBatchSize() {
								return ngo_source_array_insert.size();
							}

						});
			}

			int key_ngo_src_row = 0;

			jdbcTemplate.batchUpdate(
					"UPDATE `key_pvt_demogra` SET `key_pvt_info` = ?, `key_pvt_source` = ?, `user_id` = ?, `record_created` = ? WHERE `key_pvt_id` = ?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + ngo_source_array_update.get(i).getStakeholder_name());
							ps.setString(2, "" + ngo_source_array_update.get(i).getContact_details());
							ps.setString(3, "" + model.getUserid());
							ps.setString(4, "" + formatedDateTime);
							ps.setString(5, "" + ngo_source_array_update.get(i).getPrimary_key());
						}

						public int getBatchSize() {
							return ngo_source_array_update.size();
						}

					});			

		}

		/*-----------------------------------------------------------------*/



		int row3 = 0;

		String sql3 = "UPDATE `hsca_requirements_iphs` SET `theme_name`=?," + " `ci_sl_no`=?,`coverage_indicators`=?,"
				+ " `source`=?,`data_mcts`=?,`expected_status`=?,"
				+ " `gap_hmis`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?";

		row3 = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql3);
			ps.setString(1, model.getIphs_theme_name());
			ps.setString(2, "2.2.1");
			ps.setString(3, model.getIphs_coverage_indicators());
			ps.setString(4, model.getIphs_source());
			ps.setString(5, model.getIphs_data());
			ps.setString(6, "100");
			ps.setString(7, model.getIphs_gap_hmis());
			ps.setString(8, model.getUserid());
			ps.setString(9, formatedDateTime);
			ps.setString(10, model.getDist_demogra_dtl_id());

			return ps;
		});


		int row4 = 0;


		if (model.getInfra_array().size() != 0) {

			List<Form1BSaveDocumentsArray> infra_array = model.getInfra_array();

			List<Form1BSaveDocumentsArray> infra_array_insert = new ArrayList<>();
			List<Form1BSaveDocumentsArray> infra_array_update = new ArrayList<>();

			for (int i = 0; i < infra_array.size(); i++) {
				Form1BSaveDocumentsArray tempobj = infra_array.get(i);

				if ("0".equals(tempobj.getPrimary_key())) {
					infra_array_insert.add(tempobj);
				} else {
					infra_array_update.add(tempobj);
				}
			}


			if (infra_array_insert.size() != 0) {
				jdbcTemplate.batchUpdate(
						"insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id, details_infra, sanctioned_infra,"
								+ "		available_functional_infra, gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
								+ "		) values (?,?,?,?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + model.getDist_demogra_dtl_id());
								ps.setString(2, "" + infra_array_insert.get(i).getDocument_details());
								ps.setString(3, "" + infra_array_insert.get(i).getDocument_sanctioned());
								ps.setString(4, "" + infra_array_insert.get(i).getDocument_available_functional());
								ps.setString(5, "" + infra_array_insert.get(i).getDocument_gap());
								ps.setString(6, "" + model.getDistrict());
								ps.setString(7, "" + model.getCycle());
								ps.setString(8, "" + model.getYear());
								ps.setString(9, "" + model.getUserid());
								ps.setString(10, "" + formatedDateTime);
							}

							public int getBatchSize() {
								return infra_array_insert.size();
							}

						});
			}

			if (infra_array_update.size() != 0) {
				jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_infrastructure` SET `details_infra`=?,"
						+ " `sanctioned_infra`=?,`available_functional_infra`=?,"
						+ " `gap_infra`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?  and `infra_structure_details_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								// ps.setString(1, "" +model.getDist_demogra_dtl_id());
								ps.setString(1, "" + infra_array_update.get(i).getDocument_details());
								ps.setString(2, "" + infra_array_update.get(i).getDocument_sanctioned());
								ps.setString(3, "" + infra_array_update.get(i).getDocument_available_functional());
								ps.setString(4, "" + infra_array_update.get(i).getDocument_gap());
								ps.setString(5, "" + model.getUserid());
								ps.setString(6, "" + formatedDateTime);
								ps.setString(7, "" + model.getDist_demogra_dtl_id());
								ps.setString(8, "" + infra_array_update.get(i).getPrimary_key());
							}

							public int getBatchSize() {
								return infra_array_update.size();
							}

						});
			}

		}



		int row5 = 0;

		String sql5 = "UPDATE `hsca_theme_1_gen_res_finance` SET `details_fina`=?,"
				+ " `sanctioned_fina`=?,`available_functional_fina`=?,"
				+ " `gap_fina`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?";


		if (model.getFina_array().size() != 0) {

			List<Form1BSaveDocumentsArray> fina_array = model.getFina_array();

			List<Form1BSaveDocumentsArray> fina_array_insert = new ArrayList<>();
			List<Form1BSaveDocumentsArray> fina_array_update = new ArrayList<>();

			for (int i = 0; i < fina_array.size(); i++) {
				Form1BSaveDocumentsArray tempobj = fina_array.get(i);

				if ("0".equals(tempobj.getPrimary_key())) {
					fina_array_insert.add(tempobj);
				} else {
					fina_array_update.add(tempobj);
				}
			}

			
			if (fina_array_insert.size() != 0) {
				jdbcTemplate.batchUpdate(
						"insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
								+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
								+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + model.getDist_demogra_dtl_id());
								ps.setString(2, "" + fina_array_insert.get(i).getDocument_details());
								ps.setString(3, "" + fina_array_insert.get(i).getDocument_sanctioned());
								ps.setString(4, "" + fina_array_insert.get(i).getDocument_available_functional());
								ps.setString(5, "" + fina_array_insert.get(i).getDocument_gap());
								ps.setString(6, "" + model.getDistrict());
								ps.setString(7, "" + model.getCycle());
								ps.setString(8, "" + model.getYear());
								ps.setString(9, "" + model.getUserid());
								ps.setString(10, "" + formatedDateTime);
							}

							public int getBatchSize() {
								return fina_array_insert.size();
							}

						});
			}

			if (fina_array_update.size() != 0) {
				jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_gen_res_finance` SET `details_fina`=?,"
						+ " `sanctioned_fina`=?,`available_functional_fina`=?,"
						+ " `gap_fina`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `finance_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								// ps.setString(1, "" +model.getDist_demogra_dtl_id());
								ps.setString(1, "" + fina_array_update.get(i).getDocument_details());
								ps.setString(2, "" + fina_array_update.get(i).getDocument_sanctioned());
								ps.setString(3, "" + fina_array_update.get(i).getDocument_available_functional());
								ps.setString(4, "" + fina_array_update.get(i).getDocument_gap());
								ps.setString(5, "" + model.getUserid());
								ps.setString(6, "" + formatedDateTime);
								ps.setString(7, "" + model.getDist_demogra_dtl_id());
								ps.setString(8, "" + fina_array_update.get(i).getPrimary_key());
							}

							public int getBatchSize() {
								return fina_array_update.size();
							}

						});
			}

		}

		int row6 = 0;



		if (model.getSupp_array().size() != 0) {

			List<Form1BSaveDocumentsArray> supp_array = model.getSupp_array();

			List<Form1BSaveDocumentsArray> supp_array_insert = new ArrayList<>();
			List<Form1BSaveDocumentsArray> supp_array_update = new ArrayList<>();

			for (int i = 0; i < supp_array.size(); i++) {
				Form1BSaveDocumentsArray tempobj = supp_array.get(i);

				if ("0".equals(tempobj.getPrimary_key())) {
					supp_array_insert.add(tempobj);
				} else {
					supp_array_update.add(tempobj);
				}
			}
			

			if (supp_array_insert.size() != 0) {
				jdbcTemplate.batchUpdate("insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
						+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
						+ "cycle_id,financial_year," + "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + model.getDist_demogra_dtl_id());
								ps.setString(2, "" + supp_array_insert.get(i).getDocument_details());
								ps.setString(3, "" + supp_array_insert.get(i).getDocument_sanctioned());
								ps.setString(4, "" + supp_array_insert.get(i).getDocument_available_functional());
								ps.setString(5, "" + supp_array_insert.get(i).getDocument_gap());
								ps.setString(6, "" + model.getDistrict());
								ps.setString(7, "" + model.getCycle());
								ps.setString(8, "" + model.getYear());
								ps.setString(9, "" + model.getUserid());
								ps.setString(10, "" + formatedDateTime);
							}

							public int getBatchSize() {
								return supp_array_insert.size();
							}

						});
			}

			if (supp_array_update.size() != 0) {
				jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_gen_res_supplies` SET `details_supp`=?,"
						+ " `sanctioned_supp`=?,`available_functional_supp`=?,"
						+ " `gap_supp`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?  and `supplies_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								// ps.setString(1, "" +model.getDist_demogra_dtl_id());
								ps.setString(1, "" + supp_array_update.get(i).getDocument_details());
								ps.setString(2, "" + supp_array_update.get(i).getDocument_sanctioned());
								ps.setString(3, "" + supp_array_update.get(i).getDocument_available_functional());
								ps.setString(4, "" + supp_array_update.get(i).getDocument_gap());
								ps.setString(5, "" + model.getUserid());
								ps.setString(6, "" + formatedDateTime);
								ps.setString(7, "" + model.getDist_demogra_dtl_id());
								ps.setString(8, "" + supp_array_update.get(i).getPrimary_key());
							}

							public int getBatchSize() {
								return supp_array_update.size();
							}

						});
			}

		}


		if (model.getTech_array().size() != 0) {

			List<Form1BSaveDocumentsArray> tech_array = model.getTech_array();

			List<Form1BSaveDocumentsArray> tech_array_insert = new ArrayList<>();
			List<Form1BSaveDocumentsArray> tech_array_update = new ArrayList<>();

			for (int i = 0; i < tech_array.size(); i++) {
				Form1BSaveDocumentsArray tempobj = tech_array.get(i);

				if ("0".equals(tempobj.getPrimary_key())) {
					tech_array_insert.add(tempobj);
				} else {
					tech_array_update.add(tempobj);
				}
			}

			
			if (tech_array_insert.size() != 0) {
				jdbcTemplate.batchUpdate("insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
						+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
						+ "user_id,record_created) values (" + "?," + "?,?,?,?,?,?,?," + "?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + model.getDist_demogra_dtl_id());
								ps.setString(2, "" + tech_array_insert.get(i).getDocument_details());
								ps.setString(3, "" + tech_array_insert.get(i).getDocument_sanctioned());
								ps.setString(4, "" + tech_array_insert.get(i).getDocument_available_functional());
								ps.setString(5, "" + tech_array_insert.get(i).getDocument_gap());
								ps.setString(6, "" + model.getDistrict());
								ps.setString(7, "" + model.getCycle());
								ps.setString(8, "" + model.getYear());
								ps.setString(9, "" + model.getUserid());
								ps.setString(10, "" + formatedDateTime);
							}

							public int getBatchSize() {
								return tech_array_insert.size();
							}

						});
			}

			if (tech_array_update.size() != 0) {
				jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_gen_res_technology` SET `details_tech`=?,"
						+ " `sanctioned_tech`=?,`available_functional_tech`=?,"
						+ " `gap_tech`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `technology_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								// ps.setString(1, "" +model.getDist_demogra_dtl_id());
								ps.setString(1, "" + tech_array_update.get(i).getDocument_details());
								ps.setString(2, "" + tech_array_update.get(i).getDocument_sanctioned());
								ps.setString(3, "" + tech_array_update.get(i).getDocument_available_functional());
								ps.setString(4, "" + tech_array_update.get(i).getDocument_gap());
								ps.setString(5, "" + model.getUserid());
								ps.setString(6, "" + formatedDateTime);
								ps.setString(7, "" + model.getDist_demogra_dtl_id());
								ps.setString(8, "" + tech_array_update.get(i).getPrimary_key());
							}

							public int getBatchSize() {
								return tech_array_update.size();
							}

						});
			}

		}



		int row9 = 0;

		if (model.getHr_array().size() != 0) {

			List<Form1BSaveDocumentsArray> hr_array = model.getHr_array();

			List<Form1BSaveDocumentsArray> hr_array_insert = new ArrayList<>();
			List<Form1BSaveDocumentsArray> hr_array_update = new ArrayList<>();

			for (int i = 0; i < hr_array.size(); i++) {
				Form1BSaveDocumentsArray tempobj = hr_array.get(i);

				if ("0".equals(tempobj.getDist_demogra_dtl_id())) {
					hr_array_insert.add(tempobj);
				} else {
					hr_array_update.add(tempobj);
				}
			}

			
			if (hr_array_insert.size() != 0) {
				jdbcTemplate.batchUpdate("insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
						+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
						+ "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, "" + model.getDist_demogra_dtl_id());
								ps.setString(2, "" + hr_array_insert.get(i).getDocument_details());
								ps.setString(3, "" + hr_array_insert.get(i).getDocument_sanctioned());
								ps.setString(4, "" + hr_array_insert.get(i).getDocument_available_functional());
								ps.setString(5, "" + hr_array_insert.get(i).getDocument_gap());
								ps.setString(6, "" + model.getDistrict());
								ps.setString(7, "" + model.getCycle());
								ps.setString(8, "" + model.getYear());
								ps.setString(9, "" + model.getUserid());
								ps.setString(10, "" + formatedDateTime);
							}

							public int getBatchSize() {
								return hr_array_insert.size();
							}

						});
			}

			if (hr_array_update.size() != 0) {
				jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_hr` SET `details_hr`=?,"
						+ " `sanctioned_hr`=?,`available_functional_hr`=?,"
						+ " `gap_hr`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `hr_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								// ps.setString(1, "" +model.getDist_demogra_dtl_id());
								ps.setString(1, "" + hr_array_update.get(i).getDocument_details());
								ps.setString(2, "" + hr_array_update.get(i).getDocument_sanctioned());
								ps.setString(3, "" + hr_array_update.get(i).getDocument_available_functional());
								ps.setString(4, "" + hr_array_update.get(i).getDocument_gap());
								ps.setString(5, "" + model.getUserid());
								ps.setString(6, "" + formatedDateTime);
								ps.setString(7, "" + model.getDist_demogra_dtl_id());
								ps.setString(8, "" + hr_array_update.get(i).getPrimary_key());
							}

							public int getBatchSize() {
								return hr_array_update.size();
							}

						});
			}

		}

		List<Area_Indicator_Save_Edit> list = model.getTotal_coverage_indi();


		try {
			jdbcTemplate.batchUpdate("UPDATE `hsca_requirements_iphs` SET `theme_name`=?, "
					+ "				 `ci_sl_no`=?,`coverage_indicators`=?, "
					+ "				 `source`=?,`data_mcts`=?,`expected_status`=?, "
					+ "				 `gap_hmis`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {

//							ps.setString(1, model.getIphs_theme_name());
//							ps.setString(2, "2.2.1");
//							ps.setString(3, model.getIphs_coverage_indicators());
//							ps.setString(4, model.getIphs_source());
//							ps.setString(5, model.getIphs_data());
//							ps.setString(6, "100");
//							ps.setString(7, model.getIphs_gap_hmis());
//							ps.setString(8, model.getUserid());
//							ps.setString(9, formatedDateTime); 
//							ps.setString(10, model.getDist_demogra_dtl_id());
							ps.setString(1, model.getIphs_theme_name());
							ps.setString(2, "2.2.1");
							ps.setString(3, list.get(i).getIndicator_val());
							ps.setString(4, list.get(i).getSource());
							ps.setString(5, list.get(i).getData());
							ps.setString(6, list.get(i).getExpected());
							ps.setString(7, list.get(i).getGap());
							ps.setString(8, model.getUserid());
							ps.setString(9, formatedDateTime);
							ps.setString(10, model.getDist_demogra_dtl_id());

						}

						public int getBatchSize() {
							return list.size();
						}

					});
		} catch (Exception e) {
			// TODO: handle exception
		}

		
		/*********************Change code from here****************************/
//		Object[] params_d = { model.getDistrict(), model.getCycle(), model.getYear() };
//
//		int rows9 = jdbcTemplate.update(
//				"DELETE FROM `form1b_selected_indicators` where `district_id`=? and `cycle_id`=? and `year`=?",
//				params_d);
//		
//		if(rows9 >0) {
//			System.out.println("Deleted Form1B Indicators"); 
//		}
//		else {
//			System.out.println("Couldnt delete indicators"); 
//		}
//
//		if (model.getTotal_coverage_indi().size() != 0) {
//
//
//			List<Area_Indicator_Save_Edit> list5_insert = model.getTotal_coverage_indi();
//
//			jdbcTemplate.batchUpdate("insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,"
//					+ "					`indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,`user_id`,`dist_demogra_dtl_id`) values(?,"
//					+ "							?,?,?,?,?,?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {
//
//						public void setValues(PreparedStatement ps, int i) throws SQLException {
//							// ps.setString(1, "" + p_key);
//							ps.setString(1, "" + list5_insert.get(i).getArea_id());
//							ps.setString(2, "" + list5_insert.get(i).getIndicator_id());
//							ps.setString(3, "" + list5_insert.get(i).getArea_name());
//							ps.setString(4, "" + list5_insert.get(i).getIndicator_val());
//							ps.setString(5, "" + list5_insert.get(i).getData());
//							ps.setString(6, "" + list5_insert.get(i).getGap());
//							ps.setString(7, "" + list5_insert.get(i).getExpected());
//							ps.setString(8, "" + list5_insert.get(i).getSource());
//							ps.setString(9, "" + model.getDistrict());
//							ps.setString(10, "" + model.getCycle());
//							ps.setString(11, "" + model.getYear());
//							ps.setString(12, "" + formatedDateTime);
//							ps.setString(13, "" + model.getUserid());
//							ps.setString(14, "" + model.getDist_demogra_dtl_id());
//
//						}
//
//						public int getBatchSize() {
//							return list5_insert.size();
//						}
//
//					});
//
//		}
		
		/***********************Upto here**************************/
		
		
		if (model.getTotal_coverage_indi().size() != 0) {
			
			/******************Check if form5 is filled already***************************/
			
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
			
			/******************End of Check if form5 is filled already***************************/
			
			List<Area_Indicator_Save_Edit> list5_insert = model.getTotal_coverage_indi();
			
			//select indicator_id from `form1b_selected_indicators` where   indicator_id  IN (45,46) and `district_id`=3 and `cycle_id`=1 and `year`='2020';
			
			StringBuilder builder = new StringBuilder();
			
			for(int pos=0;pos<list5_insert.size();pos++) {				
				 builder.append("?,");				
				//list5_insert.get(pos).getIndicator_id();				
			}
			
			//Going to Query how many indicators have been added already in Db.			
			String sql_check = "select indicator_id from `form1b_selected_indicators` where   indicator_id  IN ("+builder.deleteCharAt( builder.length() -1 ).toString()+") and `district_id`=? and `cycle_id`=? and `year`=?";
			
			Object[] params_check = new Object[list5_insert.size()+3];    
			
			for(int pos=0;pos<list5_insert.size();pos++) {
				params_check[pos] = list5_insert.get(pos).getIndicator_id();				
			}
			
			params_check[list5_insert.size()] = ""+model.getDistrict();
			params_check[list5_insert.size()+1] = ""+model.getCycle();
			params_check[list5_insert.size()+2] = ""+model.getYear();
			

			List<String> checkid_list = jdbcTemplate.query(sql_check, params_check, rs -> {

				List<String> keyval_list = new ArrayList<>();
				while (rs.next()) {
					keyval_list.add(rs.getString("indicator_id"));					
				}
				/* We can also return any variable-data from here but not used currently */
				return keyval_list;
			});
			
			
			for(int pos=0;pos<list5_insert.size();pos++) {				
				 		
				if(checkid_list.contains(list5_insert.get(pos).getIndicator_id())) { 
					
					int i= pos;
					
					/**********************If exists Update form1b indicators**************************/

					String indi_sql = "UPDATE `form1b_selected_indicators` SET `data` = ?,`gap` = ?,`expected` = ?,`source` = ?,`record_created` = ?,`user_id` = ? WHERE  dist_demogra_dtl_id=? and indicator_id=?";

					jdbcTemplate.update(connection -> {
						PreparedStatement ps = connection.prepareStatement(indi_sql);

						ps.setString(1, list5_insert.get(i).getData());
						ps.setString(2,""+   list5_insert.get(i).getGap()  );
						ps.setString(3, list5_insert.get(i).getExpected());
						ps.setString(4, list5_insert.get(i).getSource());
						ps.setString(5, formatedDateTime);
						ps.setString(6,  model.getUserid());
						ps.setString(7, model.getDist_demogra_dtl_id());
						ps.setString(8, list5_insert.get(i).getIndicator_id());
						
						return ps;
					});
					
					/*******************************************************/
				}
				else
				{
					
					int i= pos;
					String sql_indi_insert_new = "insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,"
							+ " `indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,"
							+ " `user_id`,`dist_demogra_dtl_id`)   values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

					KeyHolder keyHolder5 = new GeneratedKeyHolder();

					row = jdbcTemplate.update(connection -> {
						PreparedStatement ps = connection.prepareStatement(sql_indi_insert_new,
								Statement.RETURN_GENERATED_KEYS);
						ps.setString(1, "" + list5_insert.get(i).getArea_id());
						ps.setString(2, "" + list5_insert.get(i).getIndicator_id());
						ps.setString(3, "" + list5_insert.get(i).getArea_name());
						ps.setString(4, "" + list5_insert.get(i).getIndicator_val());
						ps.setString(5, "" + list5_insert.get(i).getData());
						ps.setString(6, "" + list5_insert.get(i).getGap());
						ps.setString(7, "" + list5_insert.get(i).getExpected());
						ps.setString(8, "" + list5_insert.get(i).getSource());
						ps.setString(9, "" + model.getDistrict());
						ps.setString(10, "" + model.getCycle());
						ps.setString(11, "" + model.getYear());
						ps.setString(12, "" + formatedDateTime);
						ps.setString(13, "" + model.getUserid());
						ps.setString(14, "" + model.getDist_demogra_dtl_id());
						

						return ps;
					}, keyHolder5);	
					
					
					//Now fill indicators in form5 if already filled.
					if("".equals(form5_p_key)) {
						
					}
					else {			
						
						
												
						String sql_form5 = "INSERT INTO `followup_action_plan_time`(`followup_id`, `cov_indicators`, `ci_source`,"
								+ "		`time_zero`, `time_one`, `time_three`, `time_four`, `timezero_date`, "
								+ "		`timeone_date`, `timetwo_date`, `timethree_date`, `district_id`, `cycle_id`, `financial_year`,"
								+ "		`user_id`,`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?,?,?,"
								+ "		?,?,?,?)";

						KeyHolder keyHolder3 = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql_form5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + form5_p_key);
							ps.setString(2, "" + list5_insert.get(i).getIndicator_id());
							ps.setString(3, "" + "1");
							ps.setString(4, "" + list5_insert.get(i).getData());
							ps.setString(5, "" + "0");
							ps.setString(6, "" + "0");
							ps.setString(7, "" + "0");
							ps.setString(8, "" + formatedDateTime);
							ps.setString(9, "" + formatedDateTime);
							ps.setString(10, "" + formatedDateTime);
							ps.setString(11, "" + formatedDateTime);
							ps.setString(12, "" + model.getDistrict());
							ps.setString(13, "" + model.getCycle());
							ps.setString(14, "" + model.getYear());
							ps.setString(15, "" + model.getUserid()); 
							ps.setString(16, "" + formatedDateTime);

							return ps;
						}, keyHolder3); 
						
					}
				}
			}
			
			
		}//End of model.getTotal_coverage_indi().size() != 0

		SavedForm1BResponse responseobj = new SavedForm1BResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}

	public SavedForm1BResponse saveForm1BToDb(Form1BSave model) {
		
		LocalDateTime current = LocalDateTime.now();		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formatedDateTime = current.format(format);
		
		//System.out.println("model : "+model);
		int row = 0;

		String sql1 = "INSERT INTO `hsca_district_demographic_dtls`(`date_of_verification`, `filled_by`,`chairperson_of_meeting`,`chairperson_of_meeting_others`,"
				+ " `total_area`, `total_area_demogra_id`, `total_pop`, `total_pop_demogra_id`,"
				+ " `num_women_in_reproductive_age_15_49_y`, `num_women_in_reproductive_age_15_49_y_source`, "
				+ "`num_child_under_5_y`, `num_child_under_5_y_demogra_id`, `rural_pop`, `rural_pop_demogra_id`,"
				+ " `sc_pop`, `sc_pop_demogra_id`, `st_pop`, `st_pop_demogra_id`, `pop_density`, "
				+ "`pop_density_demogra_id`, `total_literacy`, `total_literacy_demogra_id`,"
				+ " `fem_literacy`, `fem_literacy_demogra_id`, `district_id`, `cycle_id`, `financial_year`,"
				+ " `user_id`,`record_created`,`completed`)" + " VALUES (?,?,?,?,?," + "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?,"
				+ "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?" + ",?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		// Logic for mapping the dropdown ids start
		if((model.getSource()).equals(Constants.OFFLINE_SOURCE)) {
			try {
			List<String> list = formBSortedIdList(model.getDistrict(), model.getCycle(), model.getYear());			
			model.setTotal_area_demogra_id(list.get(Integer.valueOf(model.getTotal_area_demogra_id())));
			model.setTotal_pop_demogra_id(list.get(Integer.valueOf(model.getTotal_pop_demogra_id())));
			model.setNum_women_in_reproductive_age_15_49_y_source(list.get(Integer.valueOf(model.getNum_women_in_reproductive_age_15_49_y_source())));
			model.setNum_child_under_5_y_demogra_id(list.get(Integer.valueOf(model.getNum_child_under_5_y_demogra_id())));
			model.setRural_pop_demogra_id(list.get(Integer.valueOf(model.getRural_pop_demogra_id())));
			model.setPop_density_demogra_id(list.get(Integer.valueOf(model.getPop_density_demogra_id())));
			model.setTotal_literacy_demogra_id(list.get(Integer.valueOf(model.getTotal_literacy_demogra_id())));
			model.setFem_literacy_demogra_id(list.get(Integer.valueOf(model.getFem_literacy_demogra_id())));
			
			for(Form1BSaveIndicatorsModel indicator: model.getTotal_coverage_indi()) {
				indicator.setSource(list.get(Integer.valueOf(indicator.getSource())));
			}
			}
			catch(Exception e) {
				LOGGER.info("Error in insert saveForm1BToDb method: " + e);
			}
			
		}
		
		// Logic for mapping the dropdown ids end

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getDate_of_verification());
			ps.setString(2, model.getFilled_by());
			ps.setString(3, model.getVerified_by_name());
			ps.setString(4, model.getVerified_by_other_actual_name());
			ps.setString(5, model.getTotal_area());
			ps.setString(6, model.getTotal_area_demogra_id());
			ps.setString(7, model.getTotal_pop());
			ps.setString(8, model.getTotal_pop_demogra_id());
			ps.setString(9, model.getNum_women_in_reproductive_age_15_49_y());
			ps.setString(10, model.getNum_women_in_reproductive_age_15_49_y_source());
			ps.setString(11, model.getNum_child_under_5_y());
			ps.setString(12, model.getNum_child_under_5_y_demogra_id());
			ps.setString(13, model.getRural_pop());
			ps.setString(14, model.getRural_pop_demogra_id());
			ps.setString(15, model.getSc_pop());
			ps.setString(16, model.getSc_pop_demogra_id());
			ps.setString(17, model.getSt_pop());
			ps.setString(18, model.getSt_pop_demogra_id());
			ps.setString(19, model.getPop_density());
			ps.setString(20, model.getPop_density_demogra_id());
			ps.setString(21, model.getTotal_literacy());
			ps.setString(22, model.getTotal_literacy_demogra_id());
			ps.setString(23, model.getFem_literacy());
			ps.setString(24, model.getFem_literacy_demogra_id());
			ps.setString(25, model.getDistrict());
			ps.setString(26, model.getCycle());
			ps.setString(27, model.getYear());
			ps.setString(28, model.getUserid());
			ps.setString(29, formatedDateTime);
			ps.setString(30, model.getCompleted());

			return ps;
		}, keyHolder);

		// ResultSet rs = ps.getGeneratedKeys();

		long p_key = keyHolder.getKey().longValue();
		

		String sql2 = "INSERT INTO `hsca_district_others`(`dist_demogra_dtl_id`, `total_area_others`,"
				+ " `total_pop_others`, `num_women_in_reproductive_others`, `no_of_child_under5_others`,"
				+ " `rural_pop_others`,`sc_pop_others`, `st_pop_others`, `pop_dens_others`, `tot_lit_others`, "
				+ "`female_lit_others`,  `district_id`, `cycle_id`, `financial_year`, `user_id`, "
				+ "`record_created`) VALUES (?,?,?," + "?,?,?," + "?,?, ?,?,?," + "?,?,?,?,?)";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2);
			ps.setString(1, "" + p_key);
			ps.setString(2, model.getTotal_area_others());
			ps.setString(3, model.getTotal_pop_others());
			ps.setString(4, model.getNum_women_in_reproductive_others());
			ps.setString(5, model.getNo_of_child_under5_others());
			ps.setString(6, model.getRural_pop_others());
			ps.setString(7, model.getSc_pop_others());
			ps.setString(8, model.getSt_pop_others());
			ps.setString(9, model.getPop_dens_others());
			ps.setString(10, model.getTot_lit_others());
			ps.setString(11, model.getFemale_lit_others());
			ps.setString(12, model.getDistrict());
			ps.setString(13, model.getCycle());
			ps.setString(14, model.getYear());
			ps.setString(15, model.getUserid());
			ps.setString(16, formatedDateTime);

			return ps;
		});
		

		/*---------------------------------------------*/

		List<Form1BSaveKeyNgoDocumentsArray> key_ngo = model.getKey_ngo_info_array();

		List<Form1BSaveKeyStakeholderDocumentsArray> key_stakeholder = model.getKey_ngo_source_array();

		String str11 = "INSERT INTO `key_ngo_demogra`(`dist_demogra_dtl_id`, `key_ngo_info`, `key_ngo_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`) VALUES (:dist_demogra_dtl_id,:key_ngo_info,:key_ngo_source,:district,:cycle,:financial_year,:user_id,:record_created)";

		if (key_ngo != null) {
			jdbcTemplate.batchUpdate(
					"INSERT INTO `key_ngo_demogra`(`dist_demogra_dtl_id`, `key_ngo_info`, `key_ngo_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)  "
							+ "VALUES (?,?,?,?,?,?,?,?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + p_key);
							ps.setString(2, key_ngo.get(i).getNgo_name());
							ps.setString(3, key_ngo.get(i).getNgo_details());
							ps.setString(4, model.getDistrict());
							ps.setString(5, model.getCycle());
							ps.setString(6, model.getYear());
							ps.setString(7, model.getUserid());
							ps.setString(8, formatedDateTime);
						}

						public int getBatchSize() {
							return key_ngo.size();
						}

					});
		}

		String str22 = "INSERT INTO `key_pvt_demogra`(`dist_demogra_dtl_id`, `key_pvt_info`, `key_pvt_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`) VALUES (:dist_demogra_dtl_id,:key_pvt_info,:key_pvt_source,:district,:cycle,:financial_year,:user_id,:record_created)";

		if (key_stakeholder != null) {
			jdbcTemplate.batchUpdate(
					"INSERT INTO `key_pvt_demogra`(`dist_demogra_dtl_id`, `key_pvt_info`, `key_pvt_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)    "
							+ "VALUES (?,?,?,?,?,?,?,?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + p_key);
							ps.setString(2, key_stakeholder.get(i).getStakeholder_name());
							ps.setString(3, key_stakeholder.get(i).getContact_details());
							ps.setString(4, model.getDistrict());
							ps.setString(5, model.getCycle());
							ps.setString(6, model.getYear());
							ps.setString(7, model.getUserid());
							ps.setString(8, formatedDateTime);
						}

						public int getBatchSize() {
							return key_stakeholder.size();
						}

					});
		}

		/*---------------------------------------------*/

		List<Area_Indicator_Object> ll = model.getAreas_Id_Indicators_map_list();

		if (ll != null) {
			jdbcTemplate.batchUpdate(
					"INSERT INTO `hsca_requirements_iphs`( `dist_demogra_dtl_id`, `theme_name`, "
							+ "	`coverage_indicators`, `source`, `data_mcts`, "
							+ "	`gap_hmis`, `expected_status`, `ci_sl_no`,`district_id`, `cycle_id`, `financial_year`, "
							+ "	`user_id`, `record_created`) VALUES (?,?,?,?, ?,?,?,?," + "		?,?,?,?,?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + p_key);
							ps.setString(2, model.getIphs_theme_name());
							ps.setString(3, ll.get(i).getIndicator_val());

							String s1 = "100";
							String s2 = "0";
							String s3 = "1";

							if (ll.get(i).getSource() != null) {
								s3 = ll.get(i).getSource();
							}

							ps.setString(4, s3);

							if (ll.get(i).getData() != null) {
								s1 = ll.get(i).getData();
							}
							ps.setString(5, s1);

							if (ll.get(i).getGap() != null) {
								s2 = ll.get(i).getGap();
							}

							ps.setString(6, s2);

							if (ll.get(i).getExpected() != null) {
								s1 = ll.get(i).getExpected();
							}

							ps.setString(7, s1);

							String s4 = "" + (i + 1);

							if (ll.get(i).getCi_sl_no() != null) {
								s4 = ll.get(i).getCi_sl_no();
							}

							ps.setString(8, s4);
							ps.setString(9, model.getDistrict());
							ps.setString(10, model.getCycle());
							ps.setString(11, model.getYear());
							ps.setString(12, model.getUserid());
							ps.setString(13, formatedDateTime);
						}

						public int getBatchSize() {
							return ll.size();
						}

					});
		} else {

			String sql3 = "INSERT INTO `hsca_requirements_iphs`( `dist_demogra_dtl_id`, `theme_name`, "
					+ "`coverage_indicators`, `source`, `data_mcts`, "
					+ "`gap_hmis`, `district_id`, `cycle_id`, `financial_year`, "
					+ "`user_id`, `record_created`, `ci_sl_no`,`expected_status`) VALUES (?,?," + "?,?, ?,?,?,?,"
					+ "?,?,?,?,?)";

			row = jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql3);
				ps.setString(1, "" + p_key);
				ps.setString(2, model.getIphs_theme_name());
				ps.setString(3, model.getIphs_coverage_indicators());
				ps.setString(4, model.getIphs_source());
				ps.setString(5, model.getIphs_data());
				ps.setString(6, model.getIphs_gap_hmis());
				ps.setString(7, model.getDistrict());
				ps.setString(8, model.getCycle());
				ps.setString(9, model.getYear());
				ps.setString(10, model.getUserid());
				ps.setString(11, formatedDateTime);
				ps.setString(12, "2.1.1");
				ps.setString(13, "100");

				return ps;
			});

			
		}

		// Converting to batchUpdate

		String sql4 = "insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id,details_infra,sanctioned_infra,"
				+ "available_functional_infra,gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
				+ ") values (?,?,?, " + "?,?,?,?,?,?,?)";

		if (true) {

			Form1BSaveDocumentsArray temp_obj = new Form1BSaveDocumentsArray();
			temp_obj.setDocument_details(model.getDetails_infra());
			temp_obj.setDocument_sanctioned(model.getSanctioned_infra());
			temp_obj.setDocument_available_functional(model.getAvailable_functional_infra());
			temp_obj.setDocument_gap(model.getGap_infra());

			List<Form1BSaveDocumentsArray> list1_1 = new ArrayList<>();

			List<Form1BSaveDocumentsArray> list1_2 = model.getInfra_array();

			list1_1.add(temp_obj);
			list1_1.addAll(list1_2);

			List<Form1BSaveDocumentsArray> list1_insert = list1_1;

			jdbcTemplate.batchUpdate(
					"insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id, details_infra, sanctioned_infra,"
							+ "		available_functional_infra, gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
							+ "		) values (?,?,?,?,?,?,?,?,?,?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + p_key);
							ps.setString(2, "" + list1_insert.get(i).getDocument_details());
							ps.setString(3, "" + list1_insert.get(i).getDocument_sanctioned());
							ps.setString(4, "" + list1_insert.get(i).getDocument_available_functional());
							ps.setString(5, "" + list1_insert.get(i).getDocument_gap());
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + model.getUserid());
							ps.setString(10, "" + formatedDateTime);
						}

						public int getBatchSize() {
							return list1_insert.size();
						}

					});
		}



		String sql5 = "insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
				+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
				+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)";

		if (true) {

			Form1BSaveDocumentsArray temp_obj = new Form1BSaveDocumentsArray();
			temp_obj.setDocument_details(model.getDetails_fina());
			temp_obj.setDocument_sanctioned(model.getSanctioned_fina());
			temp_obj.setDocument_available_functional(model.getAvailable_functional_fina());
			temp_obj.setDocument_gap(model.getGap_fina());

			List<Form1BSaveDocumentsArray> list2_1 = new ArrayList<>();
			List<Form1BSaveDocumentsArray> list2_2 = model.getFina_array();

			list2_1.add(temp_obj);
			list2_1.addAll(list2_2);

			List<Form1BSaveDocumentsArray> list2_insert = list2_1;

			jdbcTemplate
					.batchUpdate(
							"insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
									+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
									+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)",
							new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + p_key);
									ps.setString(2, "" + list2_insert.get(i).getDocument_details());
									ps.setString(3, "" + list2_insert.get(i).getDocument_sanctioned());
									ps.setString(4, "" + list2_insert.get(i).getDocument_available_functional());
									ps.setString(5, "" + list2_insert.get(i).getDocument_gap());
									ps.setString(6, "" + model.getDistrict());
									ps.setString(7, "" + model.getCycle());
									ps.setString(8, "" + model.getYear());
									ps.setString(9, "" + model.getUserid());
									ps.setString(10, "" + formatedDateTime);
								}

								public int getBatchSize() {
									return list2_insert.size();
								}

							});
		}



		String sql6 = "insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
				+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
				+ "cycle_id,financial_year," + "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)";

		if (true) {

			Form1BSaveDocumentsArray temp_obj = new Form1BSaveDocumentsArray();
			temp_obj.setDocument_details(model.getDetails_supp());
			temp_obj.setDocument_sanctioned(model.getSanctioned_supp());
			temp_obj.setDocument_available_functional(model.getAvailable_functional_supp());
			temp_obj.setDocument_gap(model.getGap_supp());

			List<Form1BSaveDocumentsArray> list3_1 = new ArrayList<>();

			List<Form1BSaveDocumentsArray> list3_2 = model.getSupp_array();

			list3_1.add(temp_obj);
			list3_1.addAll(list3_2);

			List<Form1BSaveDocumentsArray> list3_insert = list3_1;

			jdbcTemplate.batchUpdate("insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
					+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
					+ "cycle_id,financial_year," + "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + p_key);
							ps.setString(2, "" + list3_insert.get(i).getDocument_details());
							ps.setString(3, "" + list3_insert.get(i).getDocument_sanctioned());
							ps.setString(4, "" + list3_insert.get(i).getDocument_available_functional());
							ps.setString(5, "" + list3_insert.get(i).getDocument_gap());
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + model.getUserid());
							ps.setString(10, "" + formatedDateTime);
						}

						public int getBatchSize() {
							return list3_insert.size();
						}

					});
		}



		String sql7 = "insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
				+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
				+ "user_id,record_created) values (" + "?," + "?,?,?,?,?,?,?," + "?,?)";

		if (true) {

			Form1BSaveDocumentsArray temp_obj = new Form1BSaveDocumentsArray();
			temp_obj.setDocument_details(model.getDetails_tech());
			temp_obj.setDocument_sanctioned(model.getSanctioned_tech());
			temp_obj.setDocument_available_functional(model.getAvailable_functional_tech());
			temp_obj.setDocument_gap(model.getGap_tech());

			List<Form1BSaveDocumentsArray> list4_1 = new ArrayList<>();

			List<Form1BSaveDocumentsArray> list4_2 = model.getTech_array();

			list4_1.add(temp_obj);
			list4_1.addAll(list4_2);

			List<Form1BSaveDocumentsArray> list4_insert = list4_1;

			jdbcTemplate.batchUpdate("insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
					+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
					+ "user_id,record_created) values (" + "?," + "?,?,?,?,?,?,?," + "?,?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + p_key);
							ps.setString(2, "" + list4_insert.get(i).getDocument_details());
							ps.setString(3, "" + list4_insert.get(i).getDocument_sanctioned());
							ps.setString(4, "" + list4_insert.get(i).getDocument_available_functional());
							ps.setString(5, "" + list4_insert.get(i).getDocument_gap());
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + model.getUserid());
							ps.setString(10, "" + formatedDateTime);
						}

						public int getBatchSize() {
							return list4_insert.size();
						}

					});
		}


		//System.out.println("Total rows updated   in tech" + row);

		String sql8 = "insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
				+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
				+ "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)";

		//if (model.getHr_array().size() != 0) {
		if (true) {

			Form1BSaveDocumentsArray temp_obj = new Form1BSaveDocumentsArray();
			temp_obj.setDocument_details(model.getDetails_hr());
			temp_obj.setDocument_sanctioned(model.getSanctioned_hr());
			temp_obj.setDocument_available_functional(model.getAvailable_functional_hr());
			temp_obj.setDocument_gap(model.getGap_hr());

			List<Form1BSaveDocumentsArray> list5_1 = new ArrayList<>();

			List<Form1BSaveDocumentsArray> list5_2 = model.getHr_array();

			list5_1.add(temp_obj);
			list5_1.addAll(list5_2);

			List<Form1BSaveDocumentsArray> list5_insert = list5_1;

			jdbcTemplate.batchUpdate("insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
					+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
					+ "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + p_key);
							ps.setString(2, "" + list5_insert.get(i).getDocument_details());
							ps.setString(3, "" + list5_insert.get(i).getDocument_sanctioned());
							ps.setString(4, "" + list5_insert.get(i).getDocument_available_functional());
							ps.setString(5, "" + list5_insert.get(i).getDocument_gap());
							ps.setString(6, "" + model.getDistrict());
							ps.setString(7, "" + model.getCycle());
							ps.setString(8, "" + model.getYear());
							ps.setString(9, "" + model.getUserid());
							ps.setString(10, "" + formatedDateTime);
						}

						public int getBatchSize() {
							return list5_insert.size();
						}

					});
		}
		/*
		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql8);
			ps.setString(1, "" + p_key);
			ps.setString(2, model.getDetails_hr());
			ps.setString(3, model.getSanctioned_hr());
			ps.setString(4, model.getAvailable_functional_hr());
			ps.setString(5, model.getGap_hr());
			ps.setString(6, model.getDistrict());
			ps.setString(7, model.getCycle());
			ps.setString(8, model.getYear());
			ps.setString(9, model.getUserid());
			ps.setString(10, formatedDateTime);

			return ps;
		});*/

		//System.out.println("Total rows updated  in hr " + row);


		if (model.getTotal_coverage_indi().size() != 0) {


			List<Form1BSaveIndicatorsModel> list5_insert = model.getTotal_coverage_indi();

			jdbcTemplate.batchUpdate("insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,"
					+ "					`indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,`user_id`,`dist_demogra_dtl_id`) values(?,"
					+ "							?,?,?,?,?,?,?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							// ps.setString(1, "" + p_key);
							ps.setString(1, "" + list5_insert.get(i).getArea_id());
							ps.setString(2, "" + list5_insert.get(i).getIndicator_id());
							ps.setString(3, "" + list5_insert.get(i).getArea_name());
							ps.setString(4, "" + list5_insert.get(i).getIndicator_val());
							ps.setString(5, "" + list5_insert.get(i).getData());
							ps.setString(6, "" + list5_insert.get(i).getGap());
							ps.setString(7, "" + list5_insert.get(i).getExpected());
							ps.setString(8, "" + list5_insert.get(i).getSource());
							ps.setString(9, "" + model.getDistrict());
							ps.setString(10, "" + model.getCycle());
							ps.setString(11, "" + model.getYear());
							ps.setString(12, "" + formatedDateTime);
							ps.setString(13, "" + model.getUserid());
							ps.setString(14, "" + "" + p_key);

						}

						public int getBatchSize() {
							return list5_insert.size();
						}

					});

		}

		SavedForm1BResponse responseobj = new SavedForm1BResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	
	public void sendForm1BSaveForExistingDistrictCycleYear(String district_id,String cycle_id,
			String year,String country,String province,SendAndroidForm1BSynchedDataBean response ) {


		

		String sql = "SELECT form1b.`dist_demogra_dtl_id`,    form1b.`date_of_verification`,  "
				+ "  form1b.`chairperson_of_meeting`,  form1b.`chairperson_of_meeting_others`, "
				+ "   form1b.`filled_by`,    form1b.`total_area`,   form1b.`total_area_demogra_id`, "
				+ "   form1b.`total_pop`,    form1b.`total_pop_demogra_id`,   form1b.`num_women_in_reproductive_age_15_49_y`,    form1b.`num_women_in_reproductive_age_15_49_y_source`,  "
				+ "  form1b.`num_child_under_5_y`,    form1b.`num_child_under_5_y_demogra_id`,    form1b.`rural_pop`, "
				+ "  form1b.`rural_pop_demogra_id`,    form1b.`sc_pop`,    form1b.`sc_pop_demogra_id`,    form1b.`st_pop`, "
				+ "   form1b.`st_pop_demogra_id`,    form1b.`pop_density`,    form1b.`pop_density_demogra_id`, "
				+ "   form1b.`total_literacy`,   form1b.`total_literacy_demogra_id`,    form1b.`fem_literacy`,    form1b.`fem_literacy_demogra_id`, "
				+ "  form1b.`district_id`,    form1b.`cycle_id`,    form1b.`financial_year`,    form1b.`user_id`, "
				+ "  form1b.`record_created`, form1bothers.`total_area_others` , form1bothers.`total_pop_others`,  "
				+ " form1bothers.`num_women_in_reproductive_others`, form1bothers.`no_of_child_under5_others`,  "
				+ "  form1bothers.`rural_pop_others`, form1bothers.`sc_pop_others`, form1bothers.`st_pop_others`,  "
				+ " form1bothers.`pop_dens_others`, form1bothers.`tot_lit_others`, form1bothers.`female_lit_others`, form1b.`completed`  FROM `hsca_district_demographic_dtls` form1b "
				+ "   left join `hsca_district_others` form1bothers on form1b.`dist_demogra_dtl_id`=form1bothers.`dist_demogra_dtl_id` "
				+ "   where form1b.`district_id`=? and  form1b.`cycle_id`=? and  form1b.`financial_year`=?";
		
		Object[] params = new Object[] { district_id, cycle_id,  year };

		List<Form1BPrimaryTableDataBean> l = jdbcTemplate.query(sql, params, rs -> {

			List<Form1BPrimaryTableDataBean> list1 = new ArrayList<>();

			while (rs.next()) {

				try {
					Form1BPrimaryTableDataBean obj = new Form1BPrimaryTableDataBean();

					obj.setAuto_id(rs.getString("dist_demogra_dtl_id"));
					obj.setMeetingdate(rs.getString("date_of_verification"));
					obj.setMeetingvenue(rs.getString("filled_by"));
					obj.setChairpersonid(rs.getString("chairperson_of_meeting"));

					String check = "";
					if (rs.getString("chairperson_of_meeting_others") == null
							|| "null".equals(rs.getString("chairperson_of_meeting_others"))) {
						check = "";
					} else {
						check = rs.getString("chairperson_of_meeting_others");
					}
					obj.setOtherchairperson(check);
					obj.setTotal_area(rs.getString("total_area"));
					obj.setTotal_area_demogra_id(rs.getString("total_area_demogra_id"));
					obj.setTotal_area_others(rs.getString("total_area_others"));
					obj.setTotal_pop(rs.getString("total_pop"));
					obj.setTotal_pop_demogra_id(rs.getString("total_pop_demogra_id"));
					obj.setTotal_pop_others(rs.getString("total_pop_others"));
					obj.setNum_women_in_reproductive_age_15_49_y(rs.getString("num_women_in_reproductive_age_15_49_y"));
					obj.setNum_women_in_reproductive_age_15_49_y_source_id(
							rs.getString("num_women_in_reproductive_age_15_49_y_source"));
					obj.setNum_women_in_reproductive_others(rs.getString("num_women_in_reproductive_others"));
					obj.setNum_child_under_5_y(rs.getString("num_child_under_5_y"));
					obj.setNum_child_under_5_y_demogra_id(rs.getString("num_child_under_5_y_demogra_id"));
					obj.setNum_child_under_5_others(rs.getString("no_of_child_under5_others"));
					obj.setRural_pop(rs.getString("rural_pop"));
					obj.setRural_pop_demogra_id(rs.getString("rural_pop_demogra_id"));
					obj.setRural_pop_others(rs.getString("rural_pop_others"));
					obj.setSc_pop(rs.getString("sc_pop"));
					obj.setSc_pop_demogra_id(rs.getString("sc_pop_demogra_id"));
					obj.setSc_pop_others(rs.getString("sc_pop_others"));
					obj.setSt_pop(rs.getString("st_pop"));
					obj.setSt_pop_demogra_id(rs.getString("st_pop_demogra_id"));
					obj.setSt_pop_others(rs.getString("st_pop_others"));
					obj.setPop_density(rs.getString("pop_density"));
					obj.setPop_density_demogra_id(rs.getString("pop_density_demogra_id"));
					obj.setPop_dens_others(rs.getString("pop_dens_others"));
					obj.setTotal_literacy(rs.getString("total_literacy"));
					obj.setTotal_literacy_demogra_id(rs.getString("total_literacy_demogra_id"));
					obj.setTotal_lit_others(rs.getString("tot_lit_others"));
					obj.setFem_literacy(rs.getString("fem_literacy"));
					obj.setFem_literacy_demogra_id(rs.getString("fem_literacy_demogra_id"));
					obj.setFem_lit_others(rs.getString("female_lit_others"));
					//obj.setCountry_id(rs.getString("country_id"));
					//obj.setProvince_id(rs.getString("state_id"));
					obj.setDistrict_id(rs.getString("district_id"));
					obj.setCycle_id(rs.getString("cycle_id"));
					obj.setYear(rs.getString("financial_year"));
					obj.setUser_id(rs.getString("user_id"));
					obj.setRecordcreated(rs.getString("record_created"));
					obj.setDatafrom("WEB");
					obj.setCompleted(rs.getString("completed"));
					obj.setCountry_id(country);
					obj.setProvince_id(province);

					list1.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}

			
			return list1;
		});

		response.setFormB(l);

		String sql_key1 = "SELECT ngo.`key_ngo_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_ngo_info`,  "
				+ "  ngo.`key_ngo_source`,    ngo.`district_id`,    ngo.`cycle_id`,    ngo.`financial_year`, "
				+ "  ngo.`user_id`,    ngo.`record_created`   FROM  `key_ngo_demogra` ngo   where ngo.`district_id`=? "
				+ "  and ngo.`cycle_id`=? and  ngo.`financial_year`=? ";
		
		Object[] params_key1 = new Object[] { district_id, cycle_id,  year };

		List<Form1BNgoTableDataBean> list2 = jdbcTemplate.query(sql_key1, params_key1, rs -> {

			List<Form1BNgoTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {

				Form1BNgoTableDataBean obj = new Form1BNgoTableDataBean();

				obj.setAuto_id(rs.getString("key_ngo_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setNgoname(rs.getString("key_ngo_info"));
				obj.setNgocontactdetails(rs.getString("key_ngo_source"));
				obj.setTimestamp(rs.getString("record_created"));
				//obj.setCountry_id(rs.getString("country_id"));
				//obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setDatafrom("WEB");
				
				obj.setCountry_id(country);
				obj.setProvince_id(province);

				templist.add(obj);
			}
			
			return templist;
		});

		//System.out.println(list2);

		response.setFormBngo(list2);

		String sql_key2 = " SELECT ngo.`key_pvt_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_pvt_info`,   ngo.`key_pvt_source`,    ngo.`district_id`, "
				+ "   ngo.`cycle_id`,    ngo.`financial_year`,  ngo.`user_id`,  "
				+ "  ngo.`record_created`  FROM `key_pvt_demogra`  ngo  where ngo.`district_id`=? and  ngo.`cycle_id`=? and  ngo.`financial_year`=? ";
		
		Object[] params_key2 = new Object[] { district_id, cycle_id,  year };

		List<Form1BStakeHolderTableDataBean> list3 = jdbcTemplate.query(sql_key2, params_key2, rs -> {

			List<Form1BStakeHolderTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BStakeHolderTableDataBean val = new Form1BStakeHolderTableDataBean();

				val.setAuto_id(rs.getString("key_pvt_id"));
				val.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				val.setNgoname(rs.getString("key_pvt_info"));
				val.setNgocontactdetails(rs.getString("key_pvt_source"));
				//val.setCountry_id(rs.getString("country_id"));
				//val.setProvince_id(rs.getString("state_id"));
				val.setDistrict_id(rs.getString("district_id"));
				val.setCycle_id(rs.getString("cycle_id"));
				val.setYear(rs.getString("financial_year"));
				val.setUser_id(rs.getString("user_id"));
				val.setRecordcreated(rs.getString("record_created"));
				val.setTimestamp(rs.getString("record_created"));
				val.setDatafrom("WEB");
				
				val.setCountry_id(country);
				val.setProvince_id(province);

				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		response.setFormBstakeholders(list3);

		String sql4 = "SELECT infra.`infra_structure_details_id`,    infra.`dist_demogra_dtl_id`,   infra.`details_infra`,    infra.`sanctioned_infra`,  "
				+ "  infra.`available_functional_infra`,  infra.`gap_infra`,    infra.`district_id`, "
				+ "   infra.`cycle_id`,    infra.`financial_year`,  infra.`user_id`,  "
				+ "  infra.`record_created`  FROM  `hsca_theme_1_infrastructure`  infra    where infra.`district_id`=? "
				+ "  and  infra.`cycle_id`=? and  infra.`financial_year`=?";
		
		Object[] params4 = new Object[] {district_id, cycle_id,  year  };

		List<Form1BdocumentsBean> list4 = jdbcTemplate.query(sql4, params4, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("infra_structure_details_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_infra"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_infra")) || rs.getString("sanctioned_infra") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_infra"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_infra"))
						|| rs.getString("available_functional_infra") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_infra"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_infra")) || rs.getString("gap_infra") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_infra"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBinfra(list4);

		String sql5 = "SELECT fin.`finance_id`,    fin.`dist_demogra_dtl_id`,    fin.`details_fina`, "
				+ "  fin.`sanctioned_fina`,    fin.`available_functional_fina`,    fin.`gap_fina`,  "
				+ "  fin.`district_id`,    fin.`cycle_id`,    fin.`financial_year`,    fin.`user_id`,  "
				+ "  fin.`record_created`  FROM   `hsca_theme_1_gen_res_finance`  fin   where  fin.`district_id`=? and fin.`cycle_id`=?  and  fin.`financial_year`=?";
		
		Object[] params5 = new Object[] { district_id, cycle_id,  year  };

		List<Form1BdocumentsBean> list5 = jdbcTemplate.query(sql5, params5, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("finance_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_fina"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_fina")) || rs.getString("sanctioned_fina") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_fina"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_fina"))
						|| rs.getString("available_functional_fina") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_fina"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_fina")) || rs.getString("gap_fina") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_fina"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBfinance(list5);

		String sql6 = "SELECT supp.`supplies_id`,    supp.`dist_demogra_dtl_id`,    supp.`details_supp`, "
				+ "  supp.`sanctioned_supp`,    supp.`available_functional_supp`,    supp.`gap_supp`, "
				+ "  supp.`district_id`, supp.`cycle_id`,    supp.`financial_year`,    supp.`user_id`,  "
				+ "  supp.`record_created`   FROM `hsca_theme_1_gen_res_supplies`   supp  "
				+ " 	where supp.`district_id`=? and  supp.`cycle_id`=?  and    supp.`financial_year` =?";
		
		Object[] params6 = new Object[] { district_id, cycle_id,  year   };

		List<Form1BdocumentsBean> list6 = jdbcTemplate.query(sql6, params6, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("supplies_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_supp"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_supp")) || rs.getString("sanctioned_supp") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_supp"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_supp"))
						|| rs.getString("available_functional_supp") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_supp"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_supp")) || rs.getString("gap_supp") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_supp"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBsupplies(list6);

		String sql7 = " SELECT tech.`technology_id`,    tech.`dist_demogra_dtl_id`,    tech.`details_tech`,    tech.`sanctioned_tech`,  "
				+ " tech.`available_functional_tech`,   tech.`gap_tech`,    tech.`district_id`,    tech.`cycle_id`,  "
				+ "  tech.`financial_year`, tech.`user_id`,    tech.`record_created` 	FROM  `hsca_theme_1_gen_res_technology`  tech  "
				+ "    where tech.`district_id`=?  and  tech.`cycle_id`=?  and  tech.`financial_year`=?";
		
		Object[] params7 = new Object[] { district_id, cycle_id,  year   };

		List<Form1BdocumentsBean> list7 = jdbcTemplate.query(sql7, params7, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("technology_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_tech"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_tech")) || rs.getString("sanctioned_tech") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_tech"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_tech"))
						|| rs.getString("available_functional_tech") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_tech"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_tech")) || rs.getString("gap_tech") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_tech"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBtechnology(list7);

		String sql8 = "SELECT hr.`hr_id`,    hr.`dist_demogra_dtl_id`,    hr.`details_hr`,    hr.`sanctioned_hr`,  "
				+ "  hr.`available_functional_hr`,   hr.`gap_hr`,    hr.`district_id`,    hr.`cycle_id`, "
				+ "   hr.`financial_year`,	hr.`user_id`,    hr.`record_created`   FROM `hsca_theme_1_hr`  hr "
				+ "   where hr.`district_id`=?  and   hr.`cycle_id`=? and    hr.`financial_year`=?";

		Object[] params8 = new Object[] { district_id, cycle_id,  year};

		List<Form1BdocumentsBean> list8 = jdbcTemplate.query(sql8, params8, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("hr_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_hr"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_hr")) || rs.getString("sanctioned_hr") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_hr"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_hr"))
						|| rs.getString("available_functional_hr") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_hr"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_hr")) || rs.getString("gap_hr") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_hr"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBhumanresource(list8);

		String sql10 = "SELECT indi.`id`,  indi.`dist_demogra_dtl_id` ,  iphs.`theme_name`, indi.`area_id`,  "
				+ "   indi.`indicator_id`,    indi.`area_name`,    indi.`indicator_name`,  "
				+ "  indi.`data`,    indi.`gap`,   indi.`expected`,    indi.`source`, "
				+ "   indi.`district_id`,    indi.`cycle_id`,    indi.`year`, "
				+ "  indi.`record_created`,    indi.`user_id`  FROM `form1b_selected_indicators`  indi  left join `hsca_requirements_iphs` iphs on  "
				+ "  iphs.dist_demogra_dtl_id=  indi.`dist_demogra_dtl_id`  where  indi.`district_id`=? and  indi.`cycle_id`=?   and  indi.`year`= ?";

		Object[] params10 = new Object[] { district_id, cycle_id,  year };

		List<Form1BIndicatorsTableDataBean> list9 = jdbcTemplate.query(sql10, params10, rs -> {

			List<Form1BIndicatorsTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BIndicatorsTableDataBean obj = new Form1BIndicatorsTableDataBean();

				obj.setAuto_id(rs.getString("id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setTheme(rs.getString("theme_name"));
				obj.setAreaid(rs.getString("area_id"));
				obj.setIndicatorid(rs.getString("indicator_id"));
				obj.setSourceid(rs.getString("source"));
				obj.setDatapresent(rs.getString("data"));
				obj.setDataexpected(rs.getString("expected"));
				obj.setDatagap(rs.getString("gap"));
				//obj.setCountry_id(rs.getString("country_id"));
				//obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("year"));
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

		response.setFormBcoverageIndicators(list9);

		response.setError_code("200");
		response.setMessage("Sending Form B Data");


		//return response;
	
	}
 
	public SendAndroidForm1BSynchedDataBean consumeForm1BSaveAndUpdateData(AllFormsDataFetchFromAndroidBean getobj,List<Form1ASourceIDDetailsBean> mapping) {
		SendAndroidForm1BSynchedDataBean response = new SendAndroidForm1BSynchedDataBean();

		Form1BConsumeDataFromAndroidBean model = getobj.getForm1b();

		String allareas_sql = "SELECT `id`,  `area_name` FROM  `area`";
		Object[] allareas_params = new Object[] {};

		List<Areas_of_Indicators_List> getlist1 = jdbcTemplate.query(allareas_sql, allareas_params, rs -> {

			List<Areas_of_Indicators_List> templist = new ArrayList<>();
			while (rs.next()) {
				Areas_of_Indicators_List val = new Areas_of_Indicators_List();

				val.setArea_name("" + rs.getString("area_name"));
				val.setId("" + rs.getString("id"));
				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String allindi_sql = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,i.id as `indicator_id`,i.indicator_name,"
				+ " i.definition, i.numerator, i.denominator, i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id";
		Object[] allindi_params = new Object[] {};

		List<Menu_Area_Indicator_Object> getlist2 = jdbcTemplate.query(allindi_sql, allindi_params, rs -> {

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

		
		List<Form1BPrimaryTableDataBean> list1 = model.getFormB();

		List<Form1BIndicatorsTableDataBean> sendindilist = new ArrayList<>();
		List<Form1BdocumentsBean> sendhrlist = new ArrayList<>();
		List<Form1BdocumentsBean> sendtechlist = new ArrayList<>();
		List<Form1BdocumentsBean> sendsupplist = new ArrayList<>();
		List<Form1BdocumentsBean> sendfinalist = new ArrayList<>();
		List<Form1BdocumentsBean> sendinfralist = new ArrayList<>();
		List<Form1BStakeHolderTableDataBean> sendstakeslist = new ArrayList<>();
		List<Form1BNgoTableDataBean> sendngolist = new ArrayList<>();
		List<Form1BPrimaryTableDataBean> sendform1blist = new ArrayList<>();

		for (int x = 0; x < list1.size(); x++) {

			Form1BPrimaryTableDataBean tempobj = list1.get(x);

			Form1BPrimaryTableDataBean sendobj = new Form1BPrimaryTableDataBean();

			if ("APP".equals(tempobj.getDatafrom())) {

				String flag = "0";

				/*-----------------------------------------------*/

				String sql = "SELECt * FROM `hsca_district_demographic_dtls` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] params = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(), tempobj.getYear() };

				flag = jdbcTemplate.query(sql, params, rs -> {

					String check = "0";

					while (rs.next()) {
						check = rs.getString("dist_demogra_dtl_id");
					}
					/* We can also return any variable-data from here but not used currently */
					return "" + check;
				});

				/*-----------------------------------------------*/

				if ("0".equals(flag)) {

					

				} else {

					
					//continue;
					
					
					sendForm1BSaveForExistingDistrictCycleYear(tempobj.getDistrict_id(),tempobj.getCycle_id(),
							tempobj.getYear(),tempobj.getCountry_id(),tempobj.getProvince_id(),response );
					
					
					return response;
				}

				int row = 0;

				String sql1 = "INSERT INTO `hsca_district_demographic_dtls`(`date_of_verification`, `filled_by`,`chairperson_of_meeting`,`chairperson_of_meeting_others`,"
						+ " `total_area`, `total_area_demogra_id`, `total_pop`, `total_pop_demogra_id`,"
						+ " `num_women_in_reproductive_age_15_49_y`, `num_women_in_reproductive_age_15_49_y_source`, "
						+ "`num_child_under_5_y`, `num_child_under_5_y_demogra_id`, `rural_pop`, `rural_pop_demogra_id`,"
						+ " `sc_pop`, `sc_pop_demogra_id`, `st_pop`, `st_pop_demogra_id`, `pop_density`, "
						+ "`pop_density_demogra_id`, `total_literacy`, `total_literacy_demogra_id`,"
						+ " `fem_literacy`, `fem_literacy_demogra_id`, `district_id`, `cycle_id`, `financial_year`,"
						+ " `user_id`,`record_created`,`completed`)" + " VALUES (?,?,?,?,?," + "?,?,?," + "?,?,?," + "?,?,?,"
						+ "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?,?)";

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

					ps.setString(5, tempobj.getTotal_area());
					sendobj.setTotal_area(tempobj.getTotal_area());
					
					
					String final_areaid = ""+tempobj.getTotal_area_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getTotal_area_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_areaid = mytempmapobj.getWebgen_sourceid();
						}
						
						
					}

					ps.setString(6, final_areaid);
					sendobj.setTotal_area_demogra_id(final_areaid);
					
					
                    

					ps.setString(7, tempobj.getTotal_pop());
					sendobj.setTotal_pop(tempobj.getTotal_pop());
					
                    String final_popid = ""+tempobj.getTotal_pop_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getTotal_pop_demogra_id())   
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_popid = mytempmapobj.getWebgen_sourceid();
						}
						
						
					}

					ps.setString(8, final_popid);
					sendobj.setTotal_pop_demogra_id(final_popid);

					ps.setString(9, tempobj.getNum_women_in_reproductive_age_15_49_y());
					sendobj.setNum_women_in_reproductive_age_15_49_y(
							tempobj.getNum_women_in_reproductive_age_15_49_y());
					
					
                     String final_womenid = ""+tempobj.getNum_women_in_reproductive_age_15_49_y_source_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getNum_women_in_reproductive_age_15_49_y_source_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_womenid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}
					
					

					ps.setString(10, final_womenid);
					sendobj.setNum_women_in_reproductive_age_15_49_y_source_id(final_womenid);

					ps.setString(11, tempobj.getNum_child_under_5_y());
					sendobj.setNum_child_under_5_y(tempobj.getNum_child_under_5_y());
					
					
                    String final_childid = ""+tempobj.getNum_child_under_5_y_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getNum_child_under_5_y_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_childid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}

					ps.setString(12, final_childid);
					sendobj.setNum_child_under_5_y_demogra_id(final_childid);

					ps.setString(13, tempobj.getRural_pop());
					sendobj.setRural_pop(tempobj.getRural_pop());
					
					
                    String final_ruralid = ""+tempobj.getRural_pop_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getRural_pop_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_ruralid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}
					

					ps.setString(14, final_ruralid);
					sendobj.setRural_pop_demogra_id(final_ruralid);
					
					

					ps.setString(15, tempobj.getSc_pop());
					sendobj.setSc_pop(tempobj.getSc_pop());
					
					
                     String final_scpopid = ""+tempobj.getSc_pop_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getSc_pop_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_scpopid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}

					ps.setString(16, final_scpopid);
					sendobj.setSc_pop_demogra_id(final_scpopid);

					ps.setString(17, tempobj.getSt_pop());
					sendobj.setSt_pop(tempobj.getSt_pop());
					
                    String final_stpopid = ""+tempobj.getSt_pop_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getSt_pop_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_stpopid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}

					ps.setString(18, final_stpopid);
					sendobj.setSt_pop_demogra_id(final_stpopid);

					ps.setString(19, tempobj.getPop_density());
					sendobj.setPop_density(tempobj.getPop_density());
					
                    String final_popdenid = ""+tempobj.getPop_density_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getPop_density_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_popdenid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}

					ps.setString(20, final_popdenid);
					sendobj.setPop_density_demogra_id(final_popdenid);

					ps.setString(21, tempobj.getTotal_literacy());
					sendobj.setTotal_literacy(tempobj.getTotal_literacy());
					
                     String final_totlitid = ""+tempobj.getTotal_literacy_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getTotal_literacy_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_totlitid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}

					ps.setString(22, final_totlitid);
					sendobj.setTotal_literacy_demogra_id(final_totlitid);

					ps.setString(23, tempobj.getFem_literacy());
					sendobj.setFem_literacy(tempobj.getFem_literacy());
					
                    String final_femlitid = ""+tempobj.getFem_literacy_demogra_id();
					
					for(int mycount=0;mycount<mapping.size();mycount++) {
						
						Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
						
						if(mytempmapobj.getAppsend_sourceid().equals(tempobj.getFem_literacy_demogra_id())  
								
								&&  mytempmapobj.getDistrict().equals(tempobj.getDistrict_id())
								&&  mytempmapobj.getCycle().equals(tempobj.getCycle_id())
								&&  mytempmapobj.getYear().equals(tempobj.getYear())) {
							final_femlitid = mytempmapobj.getWebgen_sourceid();
						}						
						
					}

					ps.setString(24, final_femlitid);
					sendobj.setFem_literacy_demogra_id(final_femlitid);

					ps.setString(25, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());

					ps.setString(26, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());

					ps.setString(27, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());

					ps.setString(28, tempobj.getUser_id());
					sendobj.setUser_id("" + tempobj.getUser_id());

					ps.setString(29, formatedDateTime);
					sendobj.setRecordcreated(formatedDateTime);
					
					ps.setString(30, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());

					return ps;
				}, keyHolder);

				// ResultSet rs = ps.getGeneratedKeys();

				long p_key = keyHolder.getKey().longValue();
				

				String sql2 = "INSERT INTO `hsca_district_others`(`dist_demogra_dtl_id`, `total_area_others`,"
						+ " `total_pop_others`, `num_women_in_reproductive_others`, `no_of_child_under5_others`,"
						+ " `rural_pop_others`,`sc_pop_others`, `st_pop_others`, `pop_dens_others`, `tot_lit_others`, "
						+ "`female_lit_others`,  `district_id`, `cycle_id`, `financial_year`, `user_id`, "
						+ "`record_created`) VALUES (?,?,?," + "?,?,?," + "?,?, ?,?,?," + "?,?,?,?,?)";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql2);
					ps.setString(1, "" + p_key);
					ps.setString(2, tempobj.getTotal_area_others());
					sendobj.setTotal_area_others("" + tempobj.getTotal_area_others());
					ps.setString(3, tempobj.getTotal_pop_others());
					sendobj.setTotal_pop_others("" + tempobj.getTotal_pop_others());
					ps.setString(4, tempobj.getNum_women_in_reproductive_others());
					sendobj.setNum_women_in_reproductive_others("" + tempobj.getNum_women_in_reproductive_others());
					ps.setString(5, tempobj.getNum_child_under_5_others());
					sendobj.setNum_child_under_5_others("" + tempobj.getNum_child_under_5_others());
					ps.setString(6, tempobj.getRural_pop_others());
					sendobj.setRural_pop_others("" + tempobj.getRural_pop_others());
					ps.setString(7, tempobj.getSc_pop_others());
					sendobj.setSc_pop_others("" + tempobj.getSc_pop_others());
					ps.setString(8, tempobj.getSt_pop_others());
					sendobj.setSt_pop_others("" + tempobj.getSt_pop_others());
					ps.setString(9, tempobj.getPop_dens_others());
					sendobj.setPop_dens_others("" + tempobj.getPop_dens_others());
					ps.setString(10, tempobj.getTotal_lit_others());
					sendobj.setTotal_lit_others("" + tempobj.getTotal_lit_others());
					ps.setString(11, tempobj.getFem_lit_others());
					sendobj.setFem_lit_others("" + tempobj.getFem_lit_others());
					ps.setString(12, tempobj.getDistrict_id());
					sendobj.setDistrict_id("" + tempobj.getDistrict_id());
					ps.setString(13, tempobj.getCycle_id());
					sendobj.setCycle_id("" + tempobj.getCycle_id());
					ps.setString(14, tempobj.getYear());
					sendobj.setYear("" + tempobj.getYear());
					ps.setString(15, tempobj.getUser_id());
					sendobj.setUser_id("" + tempobj.getUser_id());
					ps.setString(16, formatedDateTime);

					return ps;
				});

				sendobj.setAuto_id("" + p_key);
				sendobj.setDatafrom("WEB");

				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());

				sendform1blist.add(sendobj);

				

				/*--------------------*/

				Form1BIndicatorsTableDataBean indibean = model.getFormBcoverageIndicators().get(0);

				String sql_theme = "INSERT INTO `hsca_requirements_iphs`( `dist_demogra_dtl_id`, `theme_name`, "
						+ "`coverage_indicators`, `source`, `data_mcts`, "
						+ "`gap_hmis`, `district_id`, `cycle_id`, `financial_year`, "
						+ "`user_id`, `record_created`, `ci_sl_no`,`expected_status`) VALUES (?,?," + "?,?, ?,?,?,?,"
						+ "?,?,?,?,?)";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql_theme);
					ps.setString(1, "" + p_key);
					ps.setString(2, indibean.getTheme());
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(5, "0");
					ps.setString(6, "0");
					ps.setString(7, indibean.getDistrict_id());
					ps.setString(8, indibean.getCycle_id());
					ps.setString(9, indibean.getYear());
					ps.setString(10, indibean.getUser_id());
					ps.setString(11, formatedDateTime);
					ps.setString(12, "2.1.1");
					ps.setString(13, "100");

					return ps;
				});

				List<Form1BIndicatorsTableDataBean> list2 = model.getFormBcoverageIndicators();

				for (int i = 0; i < list2.size(); i++) {

					Form1BIndicatorsTableDataBean indiref = list2.get(i);

					if ("APP".equals(indiref.getDatafrom()) && tempobj.getDistrict_id().equals(indiref.getDistrict_id())
							&& tempobj.getCycle_id().equals(indiref.getCycle_id())
							&& tempobj.getYear().equals(indiref.getYear())) {

						String indi_sql = "insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,"
								+ "					`indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,`user_id`,`dist_demogra_dtl_id`) values(?,"
								+ "							?,?,?,?,?,?,?,?,?,?,?,?,?)";

						String area_name = "";
						String indicator_name = "";

						for (int index = 0; index < getlist1.size(); index++) {

							Areas_of_Indicators_List temp_obj = getlist1.get(index);

							if (indiref.getAreaid().equals(temp_obj.getId())) {
								area_name = temp_obj.getArea_name();
							}
						}

						for (int index = 0; index < getlist2.size(); index++) {

							Menu_Area_Indicator_Object temp_obj = getlist2.get(index);

							if (indiref.getIndicatorid().equals(temp_obj.getIndicator_id())) {
								indicator_name = temp_obj.getIndicator_val();
							}
						}

						final String a_name = area_name;
						final String i_name = indicator_name;
						
						
						/*******************Code for Form5 Indicators*************************/
						
						List<Form5FollowUpPrimaryTableDataBean> form5lists =new ArrayList<>();
						try {
							 Form5FollowUpSendAllDataBean form5_all_obj = getobj.getForm5();
							 
							 if(form5_all_obj == null) {
								 form5_all_obj =  new Form5FollowUpSendAllDataBean();
							 }
							 
							 if(form5_all_obj.getForm5followup() == null) {							 
								 form5_all_obj = new Form5FollowUpSendAllDataBean();							 
							 }
							 
							 form5lists =   form5_all_obj.getForm5followup();
							 
							 if(form5lists == null) {
								 form5lists = new ArrayList<>();
							 }
						} catch (Exception e) {
							// TODO: handle exception
							
							e.printStackTrace();
						}
						
						Form1BIndicatorsDetailsBean sourceidmap = new Form1BIndicatorsDetailsBean();
						
						if(form5lists.size() == 0) {
							sourceidmap.setForm5fillcontinously("0");
						}
						
						 
						 
						
						/********************************************************************/

						Form1BIndicatorsTableDataBean addindibean = new Form1BIndicatorsTableDataBean();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(indi_sql,
									Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, indiref.getAreaid());
							addindibean.setAreaid(indiref.getAreaid());

							ps.setString(2, indiref.getIndicatorid());
							addindibean.setIndicatorid(indiref.getIndicatorid());

							ps.setString(3, "" + a_name);
							ps.setString(4, "" + i_name);
							
							ps.setString(5, indiref.getDataexpected());
							addindibean.setDatapresent(indiref.getDatapresent());

							ps.setString(6,""+ (Integer.parseInt(indiref.getDatapresent()) - Integer.parseInt(indiref.getDataexpected())))  ;
							addindibean.setDatagap(indiref.getDatagap());

							ps.setString(7, indiref.getDatapresent());
							addindibean.setDataexpected(indiref.getDataexpected());
							/*
							ps.setString(5, indiref.getDatapresent());
							addindibean.setDatapresent(indiref.getDatapresent());

							ps.setString(6, indiref.getDatagap());
							addindibean.setDatagap(indiref.getDatagap());

							ps.setString(7, indiref.getDataexpected());
							addindibean.setDataexpected(indiref.getDataexpected());
							*/
							/*-----------------------------------------------------------*/
							
							
							String final_sourceid = ""+indiref.getSourceid();
							
							for(int mycount=0;mycount<mapping.size();mycount++) {
								
								Form1ASourceIDDetailsBean mytempmapobj = mapping.get(mycount);  
								
																
								if(mytempmapobj.getAppsend_sourceid().equals(indiref.getSourceid())  
										
										&&  mytempmapobj.getDistrict().equals(indiref.getDistrict_id())
										&&  mytempmapobj.getCycle().equals(indiref.getCycle_id())
										&&  mytempmapobj.getYear().equals(indiref.getYear())) {
									final_sourceid = mytempmapobj.getWebgen_sourceid();
								}
								
								
							}
							
							
							
							
							/*-----------------------------------------------------------*/

							ps.setString(8, final_sourceid);
							addindibean.setSourceid(final_sourceid);

							ps.setString(9, indiref.getDistrict_id());
							addindibean.setDistrict_id(indiref.getDistrict_id());

							ps.setString(10, indiref.getCycle_id());
							addindibean.setCycle_id(indiref.getCycle_id());

							ps.setString(11, indiref.getYear());
							addindibean.setYear(indiref.getYear());

							ps.setString(12, "" + formatedDateTime);
							addindibean.setRecordcreated("" + formatedDateTime);

							ps.setString(13, indiref.getUser_id());
							addindibean.setUser_id(indiref.getUser_id());

							ps.setString(14, "" + p_key);
							addindibean.setHsca_district_demographic_id("" + p_key);

							return ps;
						}, keyHolder);
						
						//sourceidmap.setAppsend_indi_id(indiref.getIndicatorid());
						//sourceidmap.setDistrict(""); 

						addindibean.setCountry_id(indiref.getCountry_id());
						addindibean.setProvince_id(indiref.getProvince_id());
						addindibean.setTheme(indiref.getTheme());
						addindibean.setAuto_id("" + keyHolder.getKey().longValue());
						addindibean.setTimestamp(formatedDateTime);
						addindibean.setDatafrom("WEB");

						sendindilist.add(addindibean);
					}

				} // list_indicators_app.size()

				/*-------------------------------------------------------------------------------*/

				List<Form1BNgoTableDataBean> list3 = model.getFormBngo();

				for (int y = 0; y < list3.size(); y++) {

					Form1BNgoTableDataBean ngoref = list3.get(y);

					
					if ("APP".equals(ngoref.getDatafrom()) && tempobj.getDistrict_id().equals(ngoref.getDistrict_id())
							&& tempobj.getCycle_id().equals(ngoref.getCycle_id())
							&& tempobj.getYear().equals(ngoref.getYear())) {

						String sql3 = "INSERT INTO `key_ngo_demogra`(`dist_demogra_dtl_id`, `key_ngo_info`, `key_ngo_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)  "
								+ "VALUES (?,?,?,?,?,?,?,?)";

						Form1BNgoTableDataBean addBean2 = new Form1BNgoTableDataBean();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							addBean2.setHsca_district_demographic_id("" + p_key);

							ps.setString(2, ngoref.getNgoname());
							addBean2.setNgoname(ngoref.getNgoname());

							ps.setString(3, ngoref.getNgocontactdetails());
							addBean2.setNgocontactdetails(ngoref.getNgocontactdetails());

							ps.setString(4, ngoref.getDistrict_id());
							addBean2.setDistrict_id(ngoref.getDistrict_id());

							ps.setString(5, ngoref.getCycle_id());
							addBean2.setCycle_id(ngoref.getCycle_id());

							ps.setString(6, ngoref.getYear());
							addBean2.setYear(ngoref.getYear());

							ps.setString(7, ngoref.getUser_id());
							addBean2.setUser_id(ngoref.getUser_id());

							ps.setString(8, formatedDateTime);
							addBean2.setRecordcreated(formatedDateTime);

							return ps;
						}, keyHolder);

						addBean2.setAuto_id("" + keyHolder.getKey().longValue());
						addBean2.setDatafrom("WEB");

						addBean2.setCountry_id(ngoref.getCountry_id());
						addBean2.setProvince_id(ngoref.getProvince_id());
						addBean2.setTimestamp(formatedDateTime);
						sendngolist.add(addBean2);

					} // If APP and district cycle year mathes

				} // Ngo For loop

				List<Form1BStakeHolderTableDataBean> list4 = model.getFormBstakeholders();

				for (int z = 0; z < list4.size(); z++) {

					Form1BStakeHolderTableDataBean stakeref = list4.get(z);

					
					if ("APP".equals(stakeref.getDatafrom())
							&& tempobj.getDistrict_id().equals(stakeref.getDistrict_id())
							&& tempobj.getCycle_id().equals(stakeref.getCycle_id())
							&& tempobj.getYear().equals(stakeref.getYear())) {

						String sql4 = "INSERT INTO `key_pvt_demogra`(`dist_demogra_dtl_id`, `key_pvt_info`, `key_pvt_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)    "
								+ "VALUES (?,?,?,?,?,?,?,?)";

						Form1BStakeHolderTableDataBean addbean3 = new Form1BStakeHolderTableDataBean();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							addbean3.setHsca_district_demographic_id("" + p_key);

							ps.setString(2, stakeref.getNgoname());
							addbean3.setNgoname(stakeref.getNgoname());

							ps.setString(3, stakeref.getNgocontactdetails());
							addbean3.setNgocontactdetails(stakeref.getNgocontactdetails());

							ps.setString(4, stakeref.getDistrict_id());
							addbean3.setDistrict_id(stakeref.getDistrict_id());

							ps.setString(5, stakeref.getCycle_id());
							addbean3.setCycle_id(stakeref.getCycle_id());

							ps.setString(6, stakeref.getYear());
							addbean3.setYear(stakeref.getYear());

							ps.setString(7, stakeref.getUser_id());
							addbean3.setUser_id(stakeref.getUser_id());

							ps.setString(8, formatedDateTime);
							addbean3.setRecordcreated(formatedDateTime);
							return ps;
						}, keyHolder);

						addbean3.setAuto_id("" + keyHolder.getKey().longValue());
						addbean3.setDatafrom("WEB");

						addbean3.setCountry_id(stakeref.getCountry_id());
						addbean3.setProvince_id(stakeref.getProvince_id());
						addbean3.setTimestamp(formatedDateTime);
						sendstakeslist.add(addbean3);
					} // If stakesbean is of APP insert data

				} // Ngo Stakes List For Loop

				/*--------------------------Ngo Work inserted--------------------------------*/

				List<Form1BdocumentsBean> list5 = model.getFormBinfra();

				for (int i = 0; i < list5.size(); i++) {

					Form1BdocumentsBean bean = list5.get(i);

					
					if ("APP".equals(bean.getDatafrom()) && tempobj.getDistrict_id().equals(bean.getDistrict_id())
							&& tempobj.getCycle_id().equals(bean.getCycle_id())
							&& tempobj.getYear().equals(bean.getYear())) {

						String sql5 = "insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id, details_infra, sanctioned_infra,"
								+ "		available_functional_infra, gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
								+ "		) values (?,?,?,?,?,?,?,?,?,?)";

						Form1BdocumentsBean addbean4 = new Form1BdocumentsBean();
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							addbean4.setHsca_district_demographic_id("" + p_key);

							ps.setString(2, "" + bean.getDetails());
							addbean4.setDetails(bean.getDetails());

							ps.setString(3, "" + bean.getSanctioned());
							addbean4.setSanctioned(bean.getSanctioned());

							ps.setString(4, "" + bean.getAvailable());
							addbean4.setAvailable(bean.getAvailable());

							ps.setString(5, "" + bean.getGap());
							addbean4.setGap(bean.getGap());

							ps.setString(6, "" + bean.getDistrict_id());
							addbean4.setDistrict_id(bean.getDistrict_id());

							ps.setString(7, "" + bean.getCycle_id());
							addbean4.setCycle_id(bean.getCycle_id());

							ps.setString(8, "" + bean.getYear());
							addbean4.setYear(bean.getYear());

							ps.setString(9, "" + bean.getUser_id());
							addbean4.setUser_id(bean.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							addbean4.setRecordcreated("" + formatedDateTime);

							return ps;
						}, keyHolder);

						addbean4.setAuto_id("" + keyHolder.getKey().longValue());
						addbean4.setDatafrom("WEB");

						addbean4.setCountry_id(bean.getCountry_id());
						addbean4.setProvince_id(bean.getProvince_id());
						addbean4.setTimestamp(formatedDateTime);
						sendinfralist.add(addbean4);

					} // Infra If value is to be inserted

				} // Infra data for loop

				/*--------------------------------------------------------------------------------*/

				List<Form1BdocumentsBean> list6 = model.getFormBfinance();

				for (int i = 0; i < list6.size(); i++) {

					Form1BdocumentsBean bean = list6.get(i);

					

					if ("APP".equals(bean.getDatafrom()) && tempobj.getDistrict_id().equals(bean.getDistrict_id())
							&& tempobj.getCycle_id().equals(bean.getCycle_id())
							&& tempobj.getYear().equals(bean.getYear())) {

						String sql5 = "insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
								+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
								+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)";
						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							addbean5.setHsca_district_demographic_id("" + p_key);

							ps.setString(2, "" + bean.getDetails());
							addbean5.setDetails(bean.getDetails());

							ps.setString(3, "" + bean.getSanctioned());
							addbean5.setSanctioned(bean.getSanctioned());

							ps.setString(4, "" + bean.getAvailable());
							addbean5.setAvailable(bean.getAvailable());

							ps.setString(5, "" + bean.getGap());
							addbean5.setGap(bean.getGap());

							ps.setString(6, "" + bean.getDistrict_id());
							addbean5.setDistrict_id(bean.getDistrict_id());

							ps.setString(7, "" + bean.getCycle_id());
							addbean5.setCycle_id(bean.getCycle_id());

							ps.setString(8, "" + bean.getYear());
							addbean5.setYear(bean.getYear());

							ps.setString(9, "" + bean.getUser_id());
							addbean5.setUser_id(bean.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(bean.getCountry_id());
						addbean5.setProvince_id(bean.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);
						sendfinalist.add(addbean5);

					} // If APP data to be inserted

				} // Finance list loop

				List<Form1BdocumentsBean> list7 = model.getFormBsupplies();

				for (int i = 0; i < list7.size(); i++) {

					Form1BdocumentsBean bean = list7.get(i);
					

					if ("APP".equals(bean.getDatafrom()) && tempobj.getDistrict_id().equals(bean.getDistrict_id())
							&& tempobj.getCycle_id().equals(bean.getCycle_id())
							&& tempobj.getYear().equals(bean.getYear())) {

						String sql5 = "insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
								+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
								+ "cycle_id,financial_year," + "user_id,record_created) values (?," + "?,?,?,?,?,?,?,"
								+ "?,?)";

						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							addbean5.setHsca_district_demographic_id("" + p_key);
							ps.setString(2, "" + bean.getDetails());
							addbean5.setDetails(bean.getDetails());
							ps.setString(3, "" + bean.getSanctioned());
							addbean5.setSanctioned(bean.getSanctioned());
							ps.setString(4, "" + bean.getAvailable());
							addbean5.setAvailable(bean.getAvailable());
							ps.setString(5, "" + bean.getGap());
							addbean5.setGap(bean.getGap());
							ps.setString(6, "" + bean.getDistrict_id());
							addbean5.setDistrict_id(bean.getDistrict_id());
							ps.setString(7, "" + bean.getCycle_id());
							addbean5.setCycle_id(bean.getCycle_id());
							ps.setString(8, "" + bean.getYear());
							addbean5.setYear(bean.getYear());
							ps.setString(9, "" + bean.getUser_id());
							addbean5.setUser_id(bean.getUser_id());
							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(bean.getCountry_id());
						addbean5.setProvince_id(bean.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);
						sendsupplist.add(addbean5);

					} // If supp is APP insert data
				} // For loop for supplies

				/*----------------------------------------------------------------------------------------*/

				List<Form1BdocumentsBean> list8 = model.getFormBtechnology();

				for (int i = 0; i < list8.size(); i++) {

					Form1BdocumentsBean bean = list8.get(i);

					

					if ("APP".equals(bean.getDatafrom()) && tempobj.getDistrict_id().equals(bean.getDistrict_id())
							&& tempobj.getCycle_id().equals(bean.getCycle_id())
							&& tempobj.getYear().equals(bean.getYear())) {

						String sql5 = "insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
								+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
								+ "user_id,record_created) values (" + "?," + "?,?,?,?,?,?,?," + "?,?)";

						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							addbean5.setHsca_district_demographic_id("" + p_key);
							ps.setString(2, "" + bean.getDetails());
							addbean5.setDetails(bean.getDetails());
							ps.setString(3, "" + bean.getSanctioned());
							addbean5.setSanctioned(bean.getSanctioned());
							ps.setString(4, "" + bean.getAvailable());
							addbean5.setAvailable(bean.getAvailable());
							ps.setString(5, "" + bean.getGap());
							addbean5.setGap(bean.getGap());
							ps.setString(6, "" + bean.getDistrict_id());
							addbean5.setDistrict_id(bean.getDistrict_id());
							ps.setString(7, "" + bean.getCycle_id());
							addbean5.setCycle_id(bean.getCycle_id());
							ps.setString(8, "" + bean.getYear());
							addbean5.setYear(bean.getYear());
							ps.setString(9, "" + bean.getUser_id());
							addbean5.setUser_id(bean.getUser_id());
							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(bean.getCountry_id());
						addbean5.setProvince_id(bean.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);
						sendtechlist.add(addbean5);

					} // If tech data is to be inserted

				} // For loop for tech list

				List<Form1BdocumentsBean> list9 = model.getFormBhumanresource();

				for (int i = 0; i < list9.size(); i++) {

					Form1BdocumentsBean bean = list9.get(i);

					
					if ("APP".equals(bean.getDatafrom()) && tempobj.getDistrict_id().equals(bean.getDistrict_id())
							&& tempobj.getCycle_id().equals(bean.getCycle_id())
							&& tempobj.getYear().equals(bean.getYear())) {

						String sql5 = "insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
								+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
								+ "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)";

						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							addbean5.setHsca_district_demographic_id("" + p_key);

							ps.setString(2, "" + bean.getDetails());
							addbean5.setDetails(bean.getDetails());

							ps.setString(3, "" + bean.getSanctioned());
							addbean5.setSanctioned(bean.getSanctioned());

							ps.setString(4, "" + bean.getAvailable());
							addbean5.setAvailable(bean.getAvailable());

							ps.setString(5, "" + bean.getGap());
							addbean5.setGap(bean.getGap());

							ps.setString(6, "" + bean.getDistrict_id());
							addbean5.setDistrict_id(bean.getDistrict_id());

							ps.setString(7, "" + bean.getCycle_id());
							addbean5.setCycle_id(bean.getCycle_id());

							ps.setString(8, "" + bean.getYear());
							addbean5.setYear(bean.getYear());

							ps.setString(9, "" + bean.getUser_id());
							addbean5.setUser_id(bean.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(bean.getCountry_id());
						addbean5.setProvince_id(bean.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);

						sendhrlist.add(addbean5);

					} // If hr data is to be inserted from APP

				} // For loop for hr data

			} // Check if current district is for app only
		} // Main for loop for Form B APP insert data
		
		
		
		
		

		List<Form1BPrimaryTableDataBean> list_form1b_update = model.getFormB();

		for (int x = 0; x < list_form1b_update.size(); x++) {

			Form1BPrimaryTableDataBean tempobj = list_form1b_update.get(x);

			Form1BPrimaryTableDataBean mybean = new Form1BPrimaryTableDataBean();
			
			/****************************Check form5 is filled or not***********************************/
			
			
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
			
			
			/***********************************form5 is filled****************************/
			
			
			

			if ("WEB".equals(tempobj.getDatafrom())) {

				int row = 0;

				String sql1 = " UPDATE `hsca_district_demographic_dtls` SET `date_of_verification`=?, `filled_by`=?,`chairperson_of_meeting`=?,`chairperson_of_meeting_others`=?,"
						+ "				`total_area`=?, `total_area_demogra_id`=?, `total_pop`=?, `total_pop_demogra_id`=?,"
						+ "				`num_women_in_reproductive_age_15_49_y`=?, `num_women_in_reproductive_age_15_49_y_source`=?, "
						+ "				`num_child_under_5_y`=?, `num_child_under_5_y_demogra_id`=?, `rural_pop`=?, `rural_pop_demogra_id`=?,"
						+ "				`sc_pop`=?, `sc_pop_demogra_id`=?, `st_pop`=?, `st_pop_demogra_id`=?, `pop_density`=?, "
						+ "				`pop_density_demogra_id`=?, `total_literacy`=?, `total_literacy_demogra_id`=?,"
						+ "				`fem_literacy`=?, `fem_literacy_demogra_id`=?, "
						+ "				`user_id`=?,`record_created`=?,`completed`=? WHERE `dist_demogra_dtl_id`=?";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1);
					ps.setString(1, tempobj.getMeetingdate());
					mybean.setMeetingdate(tempobj.getMeetingdate());

					ps.setString(2, tempobj.getMeetingvenue());
					mybean.setMeetingvenue(tempobj.getMeetingvenue());

					ps.setString(3, tempobj.getChairpersonid());
					mybean.setChairpersonid(tempobj.getChairpersonid());

					ps.setString(4, tempobj.getOtherchairperson());
					mybean.setOtherchairperson(tempobj.getOtherchairperson());

					ps.setString(5, tempobj.getTotal_area());
					mybean.setTotal_area(tempobj.getTotal_area());

					ps.setString(6, tempobj.getTotal_area_demogra_id());
					mybean.setTotal_area_demogra_id(tempobj.getTotal_area_demogra_id());

					ps.setString(7, tempobj.getTotal_pop());
					mybean.setTotal_pop(tempobj.getTotal_pop());

					ps.setString(8, tempobj.getTotal_pop_demogra_id());
					mybean.setTotal_pop_demogra_id(tempobj.getTotal_pop_demogra_id());

					ps.setString(9, tempobj.getNum_women_in_reproductive_age_15_49_y());
					mybean.setNum_women_in_reproductive_age_15_49_y(tempobj.getNum_women_in_reproductive_age_15_49_y());

					ps.setString(10, tempobj.getNum_women_in_reproductive_age_15_49_y_source_id());
					mybean.setNum_women_in_reproductive_age_15_49_y_source_id(
							tempobj.getNum_women_in_reproductive_age_15_49_y_source_id());

					ps.setString(11, tempobj.getNum_child_under_5_y());
					mybean.setNum_child_under_5_y(tempobj.getNum_child_under_5_y());

					ps.setString(12, tempobj.getNum_child_under_5_y_demogra_id());
					mybean.setNum_child_under_5_y_demogra_id(tempobj.getNum_child_under_5_y_demogra_id());

					ps.setString(13, tempobj.getRural_pop());
					mybean.setRural_pop(tempobj.getRural_pop());

					ps.setString(14, tempobj.getRural_pop_demogra_id());
					mybean.setRural_pop_demogra_id(tempobj.getRural_pop_demogra_id());

					ps.setString(15, tempobj.getSc_pop());
					mybean.setSc_pop(tempobj.getSc_pop());

					ps.setString(16, tempobj.getSc_pop_demogra_id());
					mybean.setSc_pop_demogra_id(tempobj.getSc_pop_demogra_id());

					ps.setString(17, tempobj.getSt_pop());
					mybean.setSt_pop(tempobj.getSt_pop());

					ps.setString(18, tempobj.getSt_pop_demogra_id());
					mybean.setSt_pop_demogra_id(tempobj.getSt_pop_demogra_id());

					ps.setString(19, tempobj.getPop_density());
					mybean.setPop_density(tempobj.getPop_density());

					ps.setString(20, tempobj.getPop_density_demogra_id());
					mybean.setPop_density_demogra_id(tempobj.getPop_density_demogra_id());

					ps.setString(21, tempobj.getTotal_literacy());
					mybean.setTotal_literacy(tempobj.getTotal_literacy());

					ps.setString(22, tempobj.getTotal_literacy_demogra_id());
					mybean.setTotal_literacy_demogra_id(tempobj.getTotal_literacy_demogra_id());

					ps.setString(23, tempobj.getFem_literacy());
					mybean.setFem_literacy(tempobj.getFem_literacy());

					ps.setString(24, tempobj.getFem_literacy_demogra_id());
					mybean.setFem_literacy_demogra_id(tempobj.getFem_literacy_demogra_id());

					ps.setString(25, tempobj.getUser_id());
					mybean.setUser_id(tempobj.getUser_id());

					ps.setString(26, formatedDateTime);
					mybean.setRecordcreated(formatedDateTime);
					
					ps.setString(27, tempobj.getCompleted());
					mybean.setCompleted(tempobj.getCompleted());

					ps.setString(28, tempobj.getAuto_id());
					mybean.setAuto_id(tempobj.getAuto_id());
					return ps;
				});

				

				int row2 = 0;

				String sql2 = "UPDATE `hsca_district_others` SET `total_area_others`=?,"
						+ "`total_pop_others`=?, `num_women_in_reproductive_others`=?,"
						+ "`no_of_child_under5_others`=?,`rural_pop_others`=?,"
						+ "`sc_pop_others`=?, `st_pop_others`=?,`pop_dens_others`=?,  "
						+ "`tot_lit_others`=?, `female_lit_others`=?, `user_id`=?,"
						+ "`record_created`=? WHERE `dist_demogra_dtl_id`=?";

				row2 = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql2);
					ps.setString(1, tempobj.getTotal_area_others());
					mybean.setTotal_area_others(tempobj.getTotal_area_others());

					ps.setString(2, tempobj.getTotal_pop_others());
					mybean.setTotal_pop_others(tempobj.getTotal_pop_others());

					ps.setString(3, tempobj.getNum_women_in_reproductive_others());
					mybean.setNum_women_in_reproductive_others(tempobj.getNum_women_in_reproductive_others());

					ps.setString(4, tempobj.getNum_child_under_5_others());
					mybean.setNum_child_under_5_others(tempobj.getNum_child_under_5_others());

					ps.setString(5, tempobj.getRural_pop_others());
					mybean.setRural_pop_others(tempobj.getRural_pop_others());

					ps.setString(6, tempobj.getSc_pop_others());
					mybean.setSc_pop_others(tempobj.getSc_pop_others());

					ps.setString(7, tempobj.getSt_pop_others());
					mybean.setSt_pop_others(tempobj.getSt_pop_others());

					ps.setString(8, tempobj.getPop_dens_others());
					mybean.setPop_dens_others(tempobj.getPop_dens_others());

					ps.setString(9, tempobj.getTotal_lit_others());
					mybean.setTotal_lit_others(tempobj.getTotal_lit_others());

					ps.setString(10, tempobj.getFem_lit_others());
					mybean.setFem_lit_others(tempobj.getFem_lit_others());

					ps.setString(11, tempobj.getUser_id());
					mybean.setUser_id(tempobj.getUser_id());

					ps.setString(12, formatedDateTime);
					mybean.setRecordcreated(tempobj.getRecordcreated());

					ps.setString(13, tempobj.getAuto_id());

					return ps;
				});

				mybean.setCountry_id(tempobj.getCountry_id());
				mybean.setProvince_id(tempobj.getProvince_id());
				mybean.setDistrict_id(tempobj.getDistrict_id());
				mybean.setCycle_id(tempobj.getCycle_id());
				mybean.setYear(tempobj.getYear());

				mybean.setDatafrom("WEB");

				sendform1blist.add(mybean);

				/*-------------------------------------------------------------------------------------------*/

				List<Form1BNgoTableDataBean> list_ngo_update = model.getFormBngo();

				for (int y = 0; y < list_ngo_update.size(); y++) {

					int key_ngo_row = 0;

					Form1BNgoTableDataBean ngoref = list_ngo_update.get(y);

					if ("WEB".equals(tempobj.getDatafrom()) && tempobj.getDistrict_id().equals(ngoref.getDistrict_id())
							&& tempobj.getCycle_id().equals(ngoref.getCycle_id())
							&& tempobj.getYear().equals(ngoref.getYear()) && "WEB".equals(ngoref.getDatafrom())) {

						String sql3 = "UPDATE `key_ngo_demogra` SET  `key_ngo_info` = ?, `key_ngo_source` = ?, `user_id`=?, "
								+ " `record_created` = ? WHERE `key_ngo_id` = ?";

						Form1BNgoTableDataBean sendngo = new Form1BNgoTableDataBean();

						key_ngo_row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);
							ps.setString(1, ngoref.getNgoname());

							sendngo.setNgoname(ngoref.getNgoname());

							ps.setString(2, ngoref.getNgocontactdetails());
							sendngo.setNgocontactdetails(ngoref.getNgocontactdetails());

							ps.setString(3, ngoref.getUser_id());
							sendngo.setUser_id(ngoref.getUser_id());

							ps.setString(4, formatedDateTime);
							sendngo.setRecordcreated(formatedDateTime);

							ps.setString(5, ngoref.getAuto_id());
							sendngo.setAuto_id(ngoref.getAuto_id());

							return ps;
						});

						sendngo.setCountry_id(ngoref.getCountry_id());
						sendngo.setProvince_id(ngoref.getProvince_id());
						sendngo.setDistrict_id(ngoref.getDistrict_id());
						sendngo.setCycle_id(ngoref.getCycle_id());
						sendngo.setYear(ngoref.getYear());
						sendngo.setHsca_district_demographic_id(ngoref.getHsca_district_demographic_id());
						sendngo.setTimestamp(formatedDateTime);
						sendngo.setUser_id(ngoref.getUser_id());

						sendngo.setDatafrom("WEB");

						sendngolist.add(sendngo);

						

					}
					
					/************************************/
					
					
					
					 if ("APP".equals(ngoref.getDatafrom()) && tempobj.getDistrict_id().equals(ngoref.getDistrict_id())
							&& tempobj.getCycle_id().equals(ngoref.getCycle_id())
							&& tempobj.getYear().equals(ngoref.getYear())  
							&& "WEB".equals(tempobj.getDatafrom()) ) {

						String sql3 = "INSERT INTO `key_ngo_demogra`(`dist_demogra_dtl_id`, `key_ngo_info`, `key_ngo_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)  "
								+ "VALUES (?,?,?,?,?,?,?,?)";

						Form1BNgoTableDataBean addBean2 = new Form1BNgoTableDataBean();
						
						KeyHolder keyHolder = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + tempobj.getAuto_id());
							addBean2.setHsca_district_demographic_id("" + tempobj.getAuto_id());

							ps.setString(2, ngoref.getNgoname());
							addBean2.setNgoname(ngoref.getNgoname());

							ps.setString(3, ngoref.getNgocontactdetails());
							addBean2.setNgocontactdetails(ngoref.getNgocontactdetails());

							ps.setString(4, ngoref.getDistrict_id());
							addBean2.setDistrict_id(ngoref.getDistrict_id());

							ps.setString(5, ngoref.getCycle_id());
							addBean2.setCycle_id(ngoref.getCycle_id());

							ps.setString(6, ngoref.getYear());
							addBean2.setYear(ngoref.getYear());

							ps.setString(7, ngoref.getUser_id());
							addBean2.setUser_id(ngoref.getUser_id());

							ps.setString(8, formatedDateTime);
							addBean2.setRecordcreated(formatedDateTime);

							return ps;
						}, keyHolder);

						addBean2.setAuto_id("" + keyHolder.getKey().longValue());
						addBean2.setDatafrom("WEB");

						addBean2.setCountry_id(ngoref.getCountry_id());
						addBean2.setProvince_id(ngoref.getProvince_id());
						addBean2.setTimestamp(formatedDateTime);
						sendngolist.add(addBean2);

					}//else if for Edit Insert key ngo 
					
					/************************************/

				} // For loop for Ngo Update data

				List<Form1BStakeHolderTableDataBean> list_stakes_update = model.getFormBstakeholders();

				for (int z = 0; z < list_stakes_update.size(); z++) {

					Form1BStakeHolderTableDataBean stakesref = list_stakes_update.get(z);

					if ("WEB".equals(tempobj.getDatafrom())
							&& tempobj.getDistrict_id().equals(stakesref.getDistrict_id())
							&& tempobj.getCycle_id().equals(stakesref.getCycle_id())
							&& tempobj.getYear().equals(stakesref.getYear()) && "WEB".equals(stakesref.getDatafrom())) {

						int key_ngo_src_row = 0;

						String sql3 = "UPDATE `key_pvt_demogra` SET `key_pvt_info` = ?, `key_pvt_source` = ?, "
								+ " `user_id` = ?, `record_created` = ? WHERE `key_pvt_id` = ?";

						Form1BStakeHolderTableDataBean sendstakesbean = new Form1BStakeHolderTableDataBean();

						key_ngo_src_row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);
							ps.setString(1, stakesref.getNgoname());
							sendstakesbean.setNgoname(stakesref.getNgoname());

							ps.setString(2, stakesref.getNgocontactdetails());
							sendstakesbean.setNgocontactdetails(stakesref.getNgocontactdetails());

							ps.setString(3, stakesref.getUser_id());
							sendstakesbean.setUser_id(stakesref.getUser_id());

							ps.setString(4, "" + formatedDateTime);
							sendstakesbean.setRecordcreated(formatedDateTime);

							ps.setString(5, stakesref.getAuto_id());
							sendstakesbean.setAuto_id(stakesref.getAuto_id());

							return ps;
						});

						sendstakesbean.setCountry_id(stakesref.getCountry_id());
						sendstakesbean.setProvince_id(stakesref.getProvince_id());
						sendstakesbean.setDistrict_id(stakesref.getDistrict_id());
						sendstakesbean.setCycle_id(stakesref.getCycle_id());
						sendstakesbean.setHsca_district_demographic_id(stakesref.getHsca_district_demographic_id());
						sendstakesbean.setYear(stakesref.getYear());
						sendstakesbean.setTimestamp(stakesref.getTimestamp());

						sendstakesbean.setDatafrom("WEB");

						sendstakeslist.add(sendstakesbean);

						

					} // If stakes is- web data to be updated
					
					/****************************************************/
					
					 if ("APP".equals(stakesref.getDatafrom())
							&& tempobj.getDistrict_id().equals(stakesref.getDistrict_id())
							&& tempobj.getCycle_id().equals(stakesref.getCycle_id())
							&& tempobj.getYear().equals(stakesref.getYear()) 
							&& "WEB".equals(tempobj.getDatafrom())) { 

						String sql4 = "INSERT INTO `key_pvt_demogra`(`dist_demogra_dtl_id`, `key_pvt_info`, `key_pvt_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)    "
								+ "VALUES (?,?,?,?,?,?,?,?)";

						Form1BStakeHolderTableDataBean addbean3 = new Form1BStakeHolderTableDataBean();
						KeyHolder keyHolder = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql4, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + tempobj.getAuto_id());
							addbean3.setHsca_district_demographic_id("" + tempobj.getAuto_id());

							ps.setString(2, stakesref.getNgoname());
							addbean3.setNgoname(stakesref.getNgoname());

							ps.setString(3, stakesref.getNgocontactdetails());
							addbean3.setNgocontactdetails(stakesref.getNgocontactdetails());

							ps.setString(4, stakesref.getDistrict_id());
							addbean3.setDistrict_id(stakesref.getDistrict_id());

							ps.setString(5, stakesref.getCycle_id());
							addbean3.setCycle_id(stakesref.getCycle_id());

							ps.setString(6, stakesref.getYear());
							addbean3.setYear(stakesref.getYear());

							ps.setString(7, stakesref.getUser_id());
							addbean3.setUser_id(stakesref.getUser_id());

							ps.setString(8, formatedDateTime);
							addbean3.setRecordcreated(formatedDateTime);
							return ps;
						}, keyHolder);

						addbean3.setAuto_id("" + keyHolder.getKey().longValue());
						addbean3.setDatafrom("WEB");

						addbean3.setCountry_id(stakesref.getCountry_id());
						addbean3.setProvince_id(stakesref.getProvince_id());
						addbean3.setTimestamp(formatedDateTime);
						sendstakeslist.add(addbean3);
					} //else if for Edit Insert stakes data
					
					/****************************************************/

				} // For loop for Stakes update data

				List<Form1BdocumentsBean> listinfraupdate = model.getFormBinfra();

				for (int pos = 0; pos < listinfraupdate.size(); pos++) {

					Form1BdocumentsBean infraobj = listinfraupdate.get(pos);

					Form1BdocumentsBean sendinfra = new Form1BdocumentsBean();

					if ("WEB".equals(tempobj.getDatafrom()) && "WEB".equals(infraobj.getDatafrom())
							&& tempobj.getDistrict_id().equals(infraobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(infraobj.getCycle_id())
							&& tempobj.getYear().equals(infraobj.getYear())) {

						String sql3 = "UPDATE `hsca_theme_1_infrastructure` SET `details_infra`=?,"
								+ " `sanctioned_infra`=?,`available_functional_infra`=?,"
								+ " `gap_infra`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?  and `infra_structure_details_id`=?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);
							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + infraobj.getDetails());
							ps.setString(2, "" + infraobj.getSanctioned());
							ps.setString(3, "" + infraobj.getAvailable());
							ps.setString(4, "" + infraobj.getGap());
							ps.setString(5, "" + infraobj.getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + infraobj.getHsca_district_demographic_id());
							ps.setString(8, "" + infraobj.getAuto_id());

							sendinfra.setDetails(infraobj.getDetails());
							sendinfra.setSanctioned(infraobj.getSanctioned());
							sendinfra.setAvailable(infraobj.getAvailable());
							sendinfra.setGap(infraobj.getGap());
							sendinfra.setUser_id(infraobj.getUser_id());
							sendinfra.setRecordcreated(formatedDateTime);
							sendinfra.setHsca_district_demographic_id(infraobj.getHsca_district_demographic_id());
							sendinfra.setAuto_id(infraobj.getAuto_id());
							sendinfra.setCountry_id(infraobj.getCountry_id());
							sendinfra.setDistrict_id(infraobj.getDistrict_id());
							sendinfra.setCycle_id(infraobj.getCycle_id());
							sendinfra.setProvince_id(infraobj.getProvince_id());
							sendinfra.setYear(infraobj.getYear());
							sendinfra.setTimestamp(infraobj.getTimestamp());

							sendinfra.setDatafrom("WEB");

							sendinfralist.add(sendinfra);

							return ps;
						});

					} // If WEB type is found then udate
					
					/***************************************/
					
					 if ("APP".equals(infraobj.getDatafrom()) && tempobj.getDistrict_id().equals(infraobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(infraobj.getCycle_id())
							&& tempobj.getYear().equals(infraobj.getYear()) 
							&& "WEB".equals(tempobj.getDatafrom())) { 

						String sql5 = "insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id, details_infra, sanctioned_infra,"
								+ "		available_functional_infra, gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
								+ "		) values (?,?,?,?,?,?,?,?,?,?)";

						Form1BdocumentsBean addbean4 = new Form1BdocumentsBean();
						
						KeyHolder keyHolder = new GeneratedKeyHolder();
						
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + tempobj.getAuto_id());
							addbean4.setHsca_district_demographic_id("" + tempobj.getAuto_id());

							ps.setString(2, "" + infraobj.getDetails());
							addbean4.setDetails(infraobj.getDetails());

							ps.setString(3, "" + infraobj.getSanctioned());
							addbean4.setSanctioned(infraobj.getSanctioned());

							ps.setString(4, "" + infraobj.getAvailable());
							addbean4.setAvailable(infraobj.getAvailable());

							ps.setString(5, "" + infraobj.getGap());
							addbean4.setGap(infraobj.getGap());

							ps.setString(6, "" + infraobj.getDistrict_id());
							addbean4.setDistrict_id(infraobj.getDistrict_id());

							ps.setString(7, "" + infraobj.getCycle_id());
							addbean4.setCycle_id(infraobj.getCycle_id());

							ps.setString(8, "" + infraobj.getYear());
							addbean4.setYear(infraobj.getYear());

							ps.setString(9, "" + infraobj.getUser_id());
							addbean4.setUser_id(infraobj.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							addbean4.setRecordcreated("" + formatedDateTime);

							return ps;
						}, keyHolder);

						addbean4.setAuto_id("" + keyHolder.getKey().longValue());
						addbean4.setDatafrom("WEB");

						addbean4.setCountry_id(infraobj.getCountry_id());
						addbean4.setProvince_id(infraobj.getProvince_id());
						addbean4.setTimestamp(formatedDateTime);
						sendinfralist.add(addbean4);

					}
					
					/**************************************/
					
				} // Infra For Loop Web

				List<Form1BdocumentsBean> listfinaupdate = model.getFormBfinance();

				for (int pos = 0; pos < listfinaupdate.size(); pos++) {

					Form1BdocumentsBean finaobj = listfinaupdate.get(pos);

					Form1BdocumentsBean sendfina = new Form1BdocumentsBean();

					if ("WEB".equals(tempobj.getDatafrom()) && "WEB".equals(finaobj.getDatafrom())
							&& tempobj.getDistrict_id().equals(finaobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(finaobj.getCycle_id())
							&& tempobj.getYear().equals(finaobj.getYear())) {

						String sql3 = "UPDATE `hsca_theme_1_gen_res_finance` SET `details_fina`=?,"
								+ " `sanctioned_fina`=?,`available_functional_fina`=?,"
								+ " `gap_fina`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `finance_id`=?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);
							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + finaobj.getDetails());
							ps.setString(2, "" + finaobj.getSanctioned());
							ps.setString(3, "" + finaobj.getAvailable());
							ps.setString(4, "" + finaobj.getGap());
							ps.setString(5, "" + finaobj.getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + finaobj.getHsca_district_demographic_id());
							ps.setString(8, "" + finaobj.getAuto_id());

							sendfina.setDetails(finaobj.getDetails());
							sendfina.setSanctioned(finaobj.getSanctioned());
							sendfina.setAvailable(finaobj.getAvailable());
							sendfina.setGap(finaobj.getGap());
							sendfina.setUser_id(finaobj.getUser_id());
							sendfina.setRecordcreated(formatedDateTime);
							sendfina.setHsca_district_demographic_id(finaobj.getHsca_district_demographic_id());
							sendfina.setAuto_id(finaobj.getAuto_id());
							sendfina.setCountry_id(finaobj.getCountry_id());
							sendfina.setDistrict_id(finaobj.getDistrict_id());
							sendfina.setCycle_id(finaobj.getCycle_id());
							sendfina.setProvince_id(finaobj.getProvince_id());
							sendfina.setYear(finaobj.getYear());
							sendfina.setTimestamp(finaobj.getTimestamp());

							sendfina.setDatafrom("WEB");

							sendfinalist.add(sendfina);

							return ps;
						});

					} // If fina data is of WEB type
					
					/*************************************/
					
					if ("APP".equals(finaobj.getDatafrom()) && tempobj.getDistrict_id().equals(finaobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(finaobj.getCycle_id())
							&& tempobj.getYear().equals(finaobj.getYear()) 
							&& "WEB".equals(tempobj.getDatafrom())) { 

						String sql5 = "insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
								+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
								+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)";
						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						
						KeyHolder keyHolder = new GeneratedKeyHolder();
						
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + tempobj.getAuto_id());
							addbean5.setHsca_district_demographic_id("" + tempobj.getAuto_id());

							ps.setString(2, "" + finaobj.getDetails());
							addbean5.setDetails(finaobj.getDetails());

							ps.setString(3, "" + finaobj.getSanctioned());
							addbean5.setSanctioned(finaobj.getSanctioned());

							ps.setString(4, "" + finaobj.getAvailable());
							addbean5.setAvailable(finaobj.getAvailable());

							ps.setString(5, "" + finaobj.getGap());
							addbean5.setGap(finaobj.getGap());

							ps.setString(6, "" + finaobj.getDistrict_id());
							addbean5.setDistrict_id(finaobj.getDistrict_id());

							ps.setString(7, "" + finaobj.getCycle_id());
							addbean5.setCycle_id(finaobj.getCycle_id());

							ps.setString(8, "" + finaobj.getYear());
							addbean5.setYear(finaobj.getYear());

							ps.setString(9, "" + finaobj.getUser_id());
							addbean5.setUser_id(finaobj.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(finaobj.getCountry_id());
						addbean5.setProvince_id(finaobj.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);
						sendfinalist.add(addbean5);

					} 
					
					/**************************************/

				} // for loop for fina

				List<Form1BdocumentsBean> listsuppupdate = model.getFormBsupplies();

				for (int pos = 0; pos < listsuppupdate.size(); pos++) {

					Form1BdocumentsBean suppobj = listsuppupdate.get(pos);

					Form1BdocumentsBean sendsupp = new Form1BdocumentsBean();

					if ("WEB".equals(tempobj.getDatafrom()) && "WEB".equals(suppobj.getDatafrom())
							&& tempobj.getDistrict_id().equals(suppobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(suppobj.getCycle_id())
							&& tempobj.getYear().equals(suppobj.getYear())) {

						String sql3 = "UPDATE `hsca_theme_1_gen_res_supplies` SET `details_supp`=?,"
								+ " `sanctioned_supp`=?,`available_functional_supp`=?,"
								+ " `gap_supp`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?  and `supplies_id`=?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);

							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + suppobj.getDetails());
							ps.setString(2, "" + suppobj.getSanctioned());
							ps.setString(3, "" + suppobj.getAvailable());
							ps.setString(4, "" + suppobj.getGap());
							ps.setString(5, "" + suppobj.getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + suppobj.getHsca_district_demographic_id());
							ps.setString(8, "" + suppobj.getAuto_id());

							sendsupp.setDetails(suppobj.getDetails());
							sendsupp.setSanctioned(suppobj.getSanctioned());
							sendsupp.setAvailable(suppobj.getAvailable());
							sendsupp.setGap(suppobj.getGap());
							sendsupp.setUser_id(suppobj.getUser_id());
							sendsupp.setRecordcreated(formatedDateTime);
							sendsupp.setHsca_district_demographic_id(suppobj.getHsca_district_demographic_id());
							sendsupp.setAuto_id(suppobj.getAuto_id());
							sendsupp.setCountry_id(suppobj.getCountry_id());
							sendsupp.setDistrict_id(suppobj.getDistrict_id());
							sendsupp.setCycle_id(suppobj.getCycle_id());
							sendsupp.setProvince_id(suppobj.getProvince_id());
							sendsupp.setYear(suppobj.getYear());
							sendsupp.setTimestamp(suppobj.getTimestamp());

							sendsupp.setDatafrom("WEB");

							sendsupplist.add(sendsupp);

							return ps;
						});

					} // If WEB type dtaa is updated
					
					/********************************************/
					
					 if ("APP".equals(suppobj.getDatafrom()) && tempobj.getDistrict_id().equals(suppobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(suppobj.getCycle_id())
							&& tempobj.getYear().equals(suppobj.getYear())
							&& "WEB".equals(tempobj.getDatafrom())) { 

						String sql5 = "insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
								+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
								+ "cycle_id,financial_year," + "user_id,record_created) values (?," + "?,?,?,?,?,?,?,"
								+ "?,?)";

						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						
						KeyHolder keyHolder = new GeneratedKeyHolder();
						
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + tempobj.getAuto_id());
							addbean5.setHsca_district_demographic_id("" + tempobj.getAuto_id());
							ps.setString(2, "" + suppobj.getDetails());
							addbean5.setDetails(suppobj.getDetails());
							ps.setString(3, "" + suppobj.getSanctioned());
							addbean5.setSanctioned(suppobj.getSanctioned());
							ps.setString(4, "" + suppobj.getAvailable());
							addbean5.setAvailable(suppobj.getAvailable());
							ps.setString(5, "" + suppobj.getGap());
							addbean5.setGap(suppobj.getGap());
							ps.setString(6, "" + suppobj.getDistrict_id());
							addbean5.setDistrict_id(suppobj.getDistrict_id());
							ps.setString(7, "" + suppobj.getCycle_id());
							addbean5.setCycle_id(suppobj.getCycle_id());
							ps.setString(8, "" + suppobj.getYear());
							addbean5.setYear(suppobj.getYear());
							ps.setString(9, "" + suppobj.getUser_id());
							addbean5.setUser_id(suppobj.getUser_id());
							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(suppobj.getCountry_id());
						addbean5.setProvince_id(suppobj.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);
						sendsupplist.add(addbean5);

					} 
					
					/********************************************/

				} // For loop for supp data

				/*--------------------------------------------------------------------------------*/

				List<Form1BdocumentsBean> listtechupdate = model.getFormBtechnology();

				for (int pos = 0; pos < listtechupdate.size(); pos++) {

					Form1BdocumentsBean techobj = listtechupdate.get(pos);

					Form1BdocumentsBean sendtech = new Form1BdocumentsBean();

					if ("WEB".equals(tempobj.getDatafrom()) && "WEB".equals(techobj.getDatafrom())
							&& tempobj.getDistrict_id().equals(techobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(techobj.getCycle_id())
							&& tempobj.getYear().equals(techobj.getYear())) {

						String sql3 = "UPDATE `hsca_theme_1_gen_res_technology` SET `details_tech`=?,"
								+ " `sanctioned_tech`=?,`available_functional_tech`=?,"
								+ " `gap_tech`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `technology_id`=?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);
							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + techobj.getDetails());
							ps.setString(2, "" + techobj.getSanctioned());
							ps.setString(3, "" + techobj.getAvailable());
							ps.setString(4, "" + techobj.getGap());
							ps.setString(5, "" + techobj.getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + techobj.getHsca_district_demographic_id());
							ps.setString(8, "" + techobj.getAuto_id());

							sendtech.setDetails(techobj.getDetails());
							sendtech.setSanctioned(techobj.getSanctioned());
							sendtech.setAvailable(techobj.getAvailable());
							sendtech.setGap(techobj.getGap());
							sendtech.setUser_id(techobj.getUser_id());
							sendtech.setRecordcreated(formatedDateTime);
							sendtech.setHsca_district_demographic_id(techobj.getHsca_district_demographic_id());
							sendtech.setAuto_id(techobj.getAuto_id());
							sendtech.setCountry_id(techobj.getCountry_id());
							sendtech.setDistrict_id(techobj.getDistrict_id());
							sendtech.setCycle_id(techobj.getCycle_id());
							sendtech.setProvince_id(techobj.getProvince_id());
							sendtech.setYear(techobj.getYear());
							sendtech.setTimestamp(techobj.getTimestamp());

							sendtech.setDatafrom("WEB");

							sendtechlist.add(sendtech);

							return ps;
						});

					} // If tech list is of WEB update type
					
					/********************************************/
					
					if ("APP".equals(techobj.getDatafrom()) && tempobj.getDistrict_id().equals(techobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(techobj.getCycle_id())
							&& tempobj.getYear().equals(techobj.getYear())
							&& "WEB".equals(tempobj.getDatafrom())) { 

						String sql5 = "insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
								+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
								+ "user_id,record_created) values (" + "?," + "?,?,?,?,?,?,?," + "?,?)";

						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						
						KeyHolder keyHolder = new GeneratedKeyHolder();
						
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + tempobj.getAuto_id());
							addbean5.setHsca_district_demographic_id("" + tempobj.getAuto_id());
							
							ps.setString(2, "" + techobj.getDetails());
							addbean5.setDetails(techobj.getDetails());
							
							ps.setString(3, "" + techobj.getSanctioned());
							addbean5.setSanctioned(techobj.getSanctioned());
							
							ps.setString(4, "" + techobj.getAvailable());
							addbean5.setAvailable(techobj.getAvailable());
							
							ps.setString(5, "" + techobj.getGap());
							addbean5.setGap(techobj.getGap());
							
							ps.setString(6, "" + techobj.getDistrict_id());
							addbean5.setDistrict_id(techobj.getDistrict_id());
							
							ps.setString(7, "" + techobj.getCycle_id());
							addbean5.setCycle_id(techobj.getCycle_id());
							
							ps.setString(8, "" + techobj.getYear());
							addbean5.setYear(techobj.getYear());
							
							ps.setString(9, "" + techobj.getUser_id());
							addbean5.setUser_id(techobj.getUser_id());
							
							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(techobj.getCountry_id());
						addbean5.setProvince_id(techobj.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);
						sendtechlist.add(addbean5);

					} 
					
					/********************************************/

				} // for loop for tech update

				/*----------------------------------------------------------------------*/

				List<Form1BdocumentsBean> listhrupdate = model.getFormBhumanresource();

				for (int pos = 0; pos < listhrupdate.size(); pos++) {

					Form1BdocumentsBean hrobj = listhrupdate.get(pos);

					Form1BdocumentsBean sendhr = new Form1BdocumentsBean();

					if ("WEB".equals(tempobj.getDatafrom()) && "WEB".equals(hrobj.getDatafrom())
							&& tempobj.getDistrict_id().equals(hrobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(hrobj.getCycle_id())
							&& tempobj.getYear().equals(hrobj.getYear())) {

						String sql3 = "UPDATE `hsca_theme_1_hr` SET `details_hr`=?,"
								+ " `sanctioned_hr`=?,`available_functional_hr`=?,"
								+ " `gap_hr`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `hr_id`=?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);
							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + hrobj.getDetails());

							ps.setString(2, "" + hrobj.getSanctioned());

							ps.setString(3, "" + hrobj.getAvailable());

							ps.setString(4, "" + hrobj.getGap());

							ps.setString(5, "" + hrobj.getUser_id());

							ps.setString(6, "" + formatedDateTime);

							ps.setString(7, "" + hrobj.getHsca_district_demographic_id());

							ps.setString(8, "" + hrobj.getAuto_id());

							sendhr.setDetails(hrobj.getDetails());
							sendhr.setSanctioned(hrobj.getSanctioned());
							sendhr.setAvailable(hrobj.getAvailable());
							sendhr.setGap(hrobj.getGap());
							sendhr.setUser_id(hrobj.getUser_id());
							sendhr.setRecordcreated(formatedDateTime);
							sendhr.setHsca_district_demographic_id(hrobj.getHsca_district_demographic_id());
							sendhr.setAuto_id(hrobj.getAuto_id());
							sendhr.setCountry_id(hrobj.getCountry_id());
							sendhr.setDistrict_id(hrobj.getDistrict_id());
							sendhr.setCycle_id(hrobj.getCycle_id());
							sendhr.setProvince_id(hrobj.getProvince_id());
							sendhr.setYear(hrobj.getYear());
							sendhr.setTimestamp(hrobj.getTimestamp());
							sendhr.setDatafrom("WEB");

							sendhrlist.add(sendhr);

							return ps;
						});

					} // If hr is WEB type data
					
					/*******************************************/
					
					if ("APP".equals(hrobj.getDatafrom()) && tempobj.getDistrict_id().equals(hrobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(hrobj.getCycle_id())
							&& tempobj.getYear().equals(hrobj.getYear())
							&& "WEB".equals(tempobj.getDatafrom())) {

						String sql5 = "insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
								+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
								+ "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)";

						Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
						
						KeyHolder keyHolder = new GeneratedKeyHolder();
						
						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql5, Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + tempobj.getAuto_id());
							addbean5.setHsca_district_demographic_id("" +  tempobj.getAuto_id());

							ps.setString(2, "" + hrobj.getDetails());
							addbean5.setDetails(hrobj.getDetails());

							ps.setString(3, "" + hrobj.getSanctioned());
							addbean5.setSanctioned(hrobj.getSanctioned());

							ps.setString(4, "" + hrobj.getAvailable());
							addbean5.setAvailable(hrobj.getAvailable());

							ps.setString(5, "" + hrobj.getGap());
							addbean5.setGap(hrobj.getGap());

							ps.setString(6, "" + hrobj.getDistrict_id());
							addbean5.setDistrict_id(hrobj.getDistrict_id());

							ps.setString(7, "" + hrobj.getCycle_id());
							addbean5.setCycle_id(hrobj.getCycle_id());

							ps.setString(8, "" + hrobj.getYear());
							addbean5.setYear(hrobj.getYear());

							ps.setString(9, "" + hrobj.getUser_id());
							addbean5.setUser_id(hrobj.getUser_id());

							ps.setString(10, "" + formatedDateTime);
							addbean5.setRecordcreated("" + formatedDateTime);
							return ps;
						}, keyHolder);

						addbean5.setAuto_id("" + keyHolder.getKey().longValue());
						addbean5.setDatafrom("WEB");

						addbean5.setCountry_id(hrobj.getCountry_id());
						addbean5.setProvince_id(hrobj.getProvince_id());
						addbean5.setTimestamp(formatedDateTime);

						sendhrlist.add(addbean5);

					} 
					
					/*******************************************/

				} // For loop for hr list

				/*-------------------------------------------------------------------------------*/

				List<Form1BIndicatorsTableDataBean> listindiupdate = model.getFormBcoverageIndicators();

				//for loop to traverse to all indicators size
				for (int myindex = 0; myindex < listindiupdate.size(); myindex++) {

					Form1BIndicatorsTableDataBean indi_obj = listindiupdate.get(myindex);

					Form1BIndicatorsTableDataBean sendindiobj = new Form1BIndicatorsTableDataBean();

					if ("WEB".equals(tempobj.getDatafrom())
							&& tempobj.getDistrict_id().equals(indi_obj.getDistrict_id())
							&& tempobj.getCycle_id().equals(indi_obj.getCycle_id())
							&& tempobj.getYear().equals(indi_obj.getYear())) {

						String sql3 = "UPDATE `hsca_requirements_iphs` SET `theme_name`=?, "
								+ "				 `ci_sl_no`=?,`coverage_indicators`=?, "
								+ "				 `source`=?,`data_mcts`=?,`expected_status`=?, "
								+ "				 `gap_hmis`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql3);

							ps.setString(1, indi_obj.getTheme());
							ps.setString(2, "2.2.1");
							ps.setString(3, "");
							ps.setString(4, "0");
							ps.setString(5, "0");
							ps.setString(6, "0");
							ps.setString(7, "0");
							ps.setString(8, indi_obj.getUser_id());
							ps.setString(9, formatedDateTime);
							ps.setString(10, indi_obj.getHsca_district_demographic_id());
							return ps;
						});

						/*---------------------------------------------------------------------------*/

						String sql_check = "SELECT * FROM form1b_selected_indicators where dist_demogra_dtl_id=? and indicator_id=?";

						
						
						Object[] params_check = new Object[] { indi_obj.getHsca_district_demographic_id(),
								indi_obj.getIndicatorid() }; 

						String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

							String keyval = "";
							while (rs.next()) {
								keyval = rs.getString("id");
							}
							/* We can also return any variable-data from here but not used currently */
							return keyval;
						});
						
						

						if(! ("".equals(checkid)) ){
							
							//UPDATE `form1b_selected_indicators` SET `id` = '',`dist_demogra_dtl_id` = '',`area_id` = '',`indicator_id` = '',`area_name` = '',`indicator_name` = '',`data` = '',`gap` = '',`expected` = '',`source` = '',`district_id` = '',`cycle_id` = '',`year` = '',`record_created` = '',`user_id` = '' WHERE `id` = '';
							/*******************************************************/
							
							String indi_sql = "UPDATE `form1b_selected_indicators` SET `data` = ?,`gap` = ?,`expected` = ?,`source` = ?,`record_created` = ?,`user_id` = ? WHERE  dist_demogra_dtl_id=? and indicator_id=?";

							jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(indi_sql);

								ps.setString(1, indi_obj.getDataexpected());
								ps.setString(2,""+   (Integer.parseInt(indi_obj.getDatapresent()) - Integer.parseInt(indi_obj.getDataexpected()))  );
								ps.setString(3, indi_obj.getDatapresent());
								ps.setString(4, indi_obj.getSourceid());
								ps.setString(5, formatedDateTime);
								ps.setString(6, indi_obj.getUser_id());
								ps.setString(7, indi_obj.getHsca_district_demographic_id());
								ps.setString(8, indi_obj.getIndicatorid());
								
								return ps;
							});
							
							/*******************************************************/
							sendindiobj.setAreaid("" + indi_obj.getAreaid());
							
							sendindiobj.setIndicatorid("" + indi_obj.getIndicatorid());	
							
							sendindiobj.setDatapresent("" + indi_obj.getDatapresent());
							
							//sendindiobj.setDatagap("" + indi_obj.getDatagap());
							
							sendindiobj.setDatagap("" + (Integer.parseInt(indi_obj.getDataexpected()) -  Integer.parseInt(indi_obj.getDatapresent()) ) ); 
							
							sendindiobj.setDataexpected("" + indi_obj.getDataexpected());
							
							sendindiobj.setSourceid("" + indi_obj.getSourceid());
							
							sendindiobj.setDistrict_id("" + indi_obj.getDistrict_id());
							
							sendindiobj.setCycle_id("" + indi_obj.getCycle_id());
							
							sendindiobj.setYear("" + indi_obj.getYear());
							
							sendindiobj.setRecordcreated("" + formatedDateTime);
							
							sendindiobj.setUser_id("" + indi_obj.getUser_id());							

							sendindiobj.setHsca_district_demographic_id(indi_obj.getHsca_district_demographic_id());
						sendindiobj.setTheme(indi_obj.getTheme());
						sendindiobj.setAuto_id("" + checkid);
						sendindiobj.setCountry_id(indi_obj.getCountry_id());
						sendindiobj.setProvince_id(indi_obj.getProvince_id());
						sendindiobj.setDistrict_id(indi_obj.getDistrict_id());
						sendindiobj.setCycle_id(indi_obj.getCycle_id());
						sendindiobj.setYear(indi_obj.getYear());
						sendindiobj.setTimestamp(formatedDateTime);
						sendindiobj.setDatafrom("WEB");

						sendindilist.add(sendindiobj);


						

						} else {
							/*----------------------------------------------------*/

							KeyHolder keyHolder = new GeneratedKeyHolder();

							String sql_indi = "insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,"
									+ "					`indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,`user_id`,`dist_demogra_dtl_id`) values(?,"
									+ "							?,?,?,?,?,?,?,?,?,?,?,?,?)";

							jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql_indi,
										Statement.RETURN_GENERATED_KEYS);
								String area_name = "";
								String indicator_name = "";

								for (int index = 0; index < getlist1.size(); index++) {

									Areas_of_Indicators_List temp_obj1 = getlist1.get(index);

									if (indi_obj.getAreaid().equals(temp_obj1.getId())) {
										area_name = temp_obj1.getArea_name();
									}
								}

								for (int index = 0; index < getlist2.size(); index++) {

									Menu_Area_Indicator_Object temp_obj2 = getlist2.get(index);

									if (indi_obj.getIndicatorid().equals(temp_obj2.getIndicator_id())) {
										indicator_name = temp_obj2.getIndicator_val();
									}
								}

								final String a_name = area_name;
								final String i_name = indicator_name;

								// ps.setString(1, "" + p_key);
								ps.setString(1, "" + indi_obj.getAreaid());
								sendindiobj.setAreaid("" + indi_obj.getAreaid());

								ps.setString(2, "" + indi_obj.getIndicatorid());
								sendindiobj.setIndicatorid("" + indi_obj.getIndicatorid());

								ps.setString(3, "" + "" + a_name);
								ps.setString(4, "" + "" + i_name);
								
								ps.setString(5, "" + indi_obj.getDataexpected() );
								sendindiobj.setDatapresent("" + indi_obj.getDatapresent()); 

								ps.setString(6, ""+ (Integer.parseInt(indi_obj.getDatapresent()) - Integer.parseInt(indi_obj.getDataexpected())));
								sendindiobj.setDatagap("" + (Integer.parseInt(indi_obj.getDataexpected()) - Integer.parseInt(indi_obj.getDatapresent())));

								ps.setString(7, "" + indi_obj.getDatapresent());
								sendindiobj.setDataexpected("" + indi_obj.getDataexpected());

								/*
								ps.setString(5, "" + indi_obj.getDatapresent());
								sendindiobj.setDatapresent("" + indi_obj.getDatapresent());

								ps.setString(6, "" + indi_obj.getDatagap());
								sendindiobj.setDatagap("" + indi_obj.getDatagap());

								ps.setString(7, "" + indi_obj.getDataexpected());
								sendindiobj.setDataexpected("" + indi_obj.getDataexpected());
								*/

								ps.setString(8, "" + indi_obj.getSourceid());
								sendindiobj.setSourceid("" + indi_obj.getSourceid());

								ps.setString(9, "" + indi_obj.getDistrict_id());
								sendindiobj.setDistrict_id("" + indi_obj.getDistrict_id());

								ps.setString(10, "" + indi_obj.getCycle_id());
								sendindiobj.setCycle_id("" + indi_obj.getCycle_id());

								ps.setString(11, "" + indi_obj.getYear());
								sendindiobj.setYear("" + indi_obj.getYear());

								ps.setString(12, "" + formatedDateTime);
								sendindiobj.setRecordcreated("" + formatedDateTime);

								ps.setString(13, "" + indi_obj.getUser_id());
								sendindiobj.setUser_id("" + indi_obj.getUser_id());

								ps.setString(14, "" + indi_obj.getHsca_district_demographic_id());

								sendindiobj.setHsca_district_demographic_id(indi_obj.getHsca_district_demographic_id());

								return ps;
							}, keyHolder);

							sendindiobj.setTheme(indi_obj.getTheme());
							sendindiobj.setAuto_id("" + keyHolder.getKey().longValue());
							sendindiobj.setCountry_id(indi_obj.getCountry_id());
							sendindiobj.setProvince_id(indi_obj.getProvince_id());
							sendindiobj.setDistrict_id(indi_obj.getDistrict_id());
							sendindiobj.setCycle_id(indi_obj.getCycle_id());
							sendindiobj.setYear(indi_obj.getYear());
							sendindiobj.setTimestamp(formatedDateTime);
							sendindiobj.setDatafrom("WEB");

							sendindilist.add(sendindiobj);

							/*-----------------------------------------------------*/
							
							
							/*****************Fill indicators in form5**************************/
							if("".equals(form5_p_key)) {
								
							}
							else {
							
								
								String sql_form5 = "INSERT INTO `followup_action_plan_time`(`followup_id`, `cov_indicators`, `ci_source`,"
										+ "		`time_zero`, `time_one`, `time_three`, `time_four`, `timezero_date`, "
										+ "		`timeone_date`, `timetwo_date`, `timethree_date`, `district_id`, `cycle_id`, `financial_year`,"
										+ "		`user_id`,`record_created`) VALUES (?,?,?,?,?," + "		?,?,?,?,?,?,?,"
										+ "		?,?,?,?)";

								KeyHolder keyHolder3 = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sql_form5, Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, "" + form5_p_key);	
									ps.setString(2, "" + indi_obj.getIndicatorid());
									ps.setString(3, "" + "1");
									ps.setString(4, "" + "0");
									ps.setString(5, "" + "0");

									ps.setString(6, "" + "0");
									ps.setString(7, "" + "0");

									ps.setString(8, "" + formatedDateTime);

									ps.setString(9, "" + formatedDateTime);
									ps.setString(10, "" + formatedDateTime);

									ps.setString(11, "" + formatedDateTime);

									ps.setString(12, "" + indi_obj.getDistrict_id());

									ps.setString(13, "" + indi_obj.getCycle_id());

									ps.setString(14, "" + indi_obj.getYear());

									ps.setString(15, "" + indi_obj.getUser_id());

									ps.setString(16, "" + formatedDateTime);
									

									return ps;
								}, keyHolder3);
							}
							
							/**********************End of Fill indicators in form5*********************/
						}

						/*--------------------------------------------------------------------------*/

					} // If cond if indictors are of WEB type to be updated and if of APP type then
						// inserted

				} // For loop for indictors web update data

			} // If APP data says its id is from WEB

		} // for loop for Update data
		
		
		
		/***********************Deleting extra indicators***********************************/
		/*****************Cant perform delete as app doesnt allow to delete**/
		

		
//		List<Form1BIndicatorsTableDataBean> listindifromapp = model.getFormBcoverageIndicators();
//		
//		
//		for(int pos=0;pos<listindifromapp.size();pos++) {
//			
//			Form1BIndicatorsTableDataBean indi_obj = listindifromapp.get(pos);
//			
//			ArrayList l = new ArrayList();
//			
//			l.add(indi_obj.getIndicatorid());
//			
//		}
//			
//			
//			Form1BIndicatorsTableDataBean indi_obj = listindifromapp.get(0);
//			
//			
//			String sql_check = "SELECT * FROM form1b_selected_indicators where dist_demogra_dtl_id=?";
//
//			
//			
//			Object[] params_check = new Object[] { indi_obj.getHsca_district_demographic_id()};
//
//			String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {
//
//				String keyval = "";
//				while (rs.next()) {
//					keyval = rs.getString("indicator_id");
//					
//					for() {
//						
//					}
//					
//					if(indi_obj.getIndicatorid().equals(keyval)) {
//						
//					}
//					
//					
//				}
//				
//				return keyval;
//			});
			
		
		
		/**********************************************************/
		

		response.setFormB(sendform1blist);
		response.setFormBngo(sendngolist);
		response.setFormBstakeholders(sendstakeslist);
		response.setFormBcoverageIndicators(sendindilist);
		response.setFormBinfra(sendinfralist);
		response.setFormBfinance(sendfinalist);
		response.setFormBsupplies(sendsupplist);
		response.setFormBtechnology(sendtechlist);
		response.setFormBhumanresource(sendhrlist);
		response.setError_code("200");
		response.setMessage("Sending Form1B Updated and Consumed Data");

		return response;
	}

	public SendAndroidForm1BSynchedDataBean consumeForm1BSaveAndUpdateData_OLD(
			AllFormsDataFetchFromAndroidBean getobj) {

		SendAndroidForm1BSynchedDataBean response = new SendAndroidForm1BSynchedDataBean();

		Form1BConsumeDataFromAndroidBean model = getobj.getForm1b();

		String sql9 = "SELECT `id`,  `area_name` FROM  `area`";
		Object[] params9 = new Object[] {};

		List<Areas_of_Indicators_List> getlist1 = jdbcTemplate.query(sql9, params9, rs -> {

			List<Areas_of_Indicators_List> templist = new ArrayList<>();
			while (rs.next()) {
				Areas_of_Indicators_List val = new Areas_of_Indicators_List();

				val.setArea_name("" + rs.getString("area_name"));
				val.setId("" + rs.getString("id"));
				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

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

		

		List<Form1BPrimaryTableDataBean> list_formB = model.getFormB();

		List<Form1BPrimaryTableDataBean> list_formB_add = new ArrayList<>();

		List<Form1BPrimaryTableDataBean> list_formB_app = new ArrayList<>();
		List<Form1BPrimaryTableDataBean> list_formB_web = new ArrayList<>();

		for (int i = 0; i < list_formB.size(); i++) {

			Form1BPrimaryTableDataBean obj = list_formB.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_formB_app.add(obj);
			} else {
				list_formB_web.add(obj);
			}

		}

		

		List<Form1BNgoTableDataBean> list_ngo = model.getFormBngo();

		List<Form1BNgoTableDataBean> list_ngo_add = new ArrayList<>();

		List<Form1BNgoTableDataBean> list_ngo_app = new ArrayList<>();
		List<Form1BNgoTableDataBean> list_ngo_web = new ArrayList<>();

		for (int i = 0; i < list_ngo.size(); i++) {

			Form1BNgoTableDataBean obj = list_ngo.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_ngo_app.add(obj);
			} else {
				list_ngo_web.add(obj);
			}

		}

		

		List<Form1BStakeHolderTableDataBean> list_stakes = model.getFormBstakeholders();

		List<Form1BStakeHolderTableDataBean> list_stakes_add = new ArrayList<>();

		List<Form1BStakeHolderTableDataBean> list_stakes_app = new ArrayList<>();
		List<Form1BStakeHolderTableDataBean> list_stakes_web = new ArrayList<>();

		for (int i = 0; i < list_stakes.size(); i++) {

			Form1BStakeHolderTableDataBean obj = list_stakes.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_stakes_app.add(obj);
			} else {
				list_stakes_web.add(obj);
			}

		}

		

		List<Form1BIndicatorsTableDataBean> list_indicators = model.getFormBcoverageIndicators();

		List<Form1BIndicatorsTableDataBean> list_indicators_add = new ArrayList<>();

		List<Form1BIndicatorsTableDataBean> list_indicators_app = new ArrayList<>();
		List<Form1BIndicatorsTableDataBean> list_indicators_web = new ArrayList<>();

		for (int i = 0; i < list_indicators.size(); i++) {

			Form1BIndicatorsTableDataBean obj = list_indicators.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_indicators_app.add(obj);
			} else {
				list_indicators_web.add(obj);
			}

		}

		

		List<Form1BdocumentsBean> list_infra = model.getFormBinfra();

		List<Form1BdocumentsBean> list_infra_add = new ArrayList<>();

		List<Form1BdocumentsBean> list_infra_app = new ArrayList<>();
		List<Form1BdocumentsBean> list_infra_web = new ArrayList<>();

		for (int i = 0; i < list_infra.size(); i++) {

			Form1BdocumentsBean obj = list_infra.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_infra_app.add(obj);
			} else {
				list_infra_web.add(obj);
			}

		}

		

		List<Form1BdocumentsBean> list_fina = model.getFormBfinance();

		List<Form1BdocumentsBean> list_fina_add = new ArrayList<>();

		List<Form1BdocumentsBean> list_fina_app = new ArrayList<>();
		List<Form1BdocumentsBean> list_fina_web = new ArrayList<>();

		for (int i = 0; i < list_fina.size(); i++) {

			Form1BdocumentsBean obj = list_fina.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_fina_app.add(obj);
			} else {
				list_fina_web.add(obj);
			}

		}

		

		List<Form1BdocumentsBean> list_supp = model.getFormBsupplies();

		List<Form1BdocumentsBean> list_supp_add = new ArrayList<>();

		List<Form1BdocumentsBean> list_supp_app = new ArrayList<>();
		List<Form1BdocumentsBean> list_supp_web = new ArrayList<>();

		for (int i = 0; i < list_supp.size(); i++) {

			Form1BdocumentsBean obj = list_supp.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_supp_app.add(obj);
			} else {
				list_supp_web.add(obj);
			}

		}

		

		List<Form1BdocumentsBean> list_tech = model.getFormBtechnology();

		List<Form1BdocumentsBean> list_tech_add = new ArrayList<>();

		List<Form1BdocumentsBean> list_tech_app = new ArrayList<>();
		List<Form1BdocumentsBean> list_tech_web = new ArrayList<>();

		for (int i = 0; i < list_tech.size(); i++) {

			Form1BdocumentsBean obj = list_tech.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_tech_app.add(obj);
			} else {
				list_tech_web.add(obj);
			}

		}

		

		List<Form1BdocumentsBean> list_hr = model.getFormBhumanresource();

		List<Form1BdocumentsBean> list_hr_add = new ArrayList<>();

		List<Form1BdocumentsBean> list_hr_app = new ArrayList<>();
		List<Form1BdocumentsBean> list_hr_web = new ArrayList<>();

		for (int i = 0; i < list_hr.size(); i++) {

			Form1BdocumentsBean obj = list_hr.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_hr_app.add(obj);
			} else {
				list_hr_web.add(obj);
			}

		}

		

		/*--------------------------------------------------------------------*/

		if (list_formB_app.size() != 0) {

			for (int x = 0; x < list_formB_app.size(); x++) {

				String flag = "0";

				{
					String sql = "SELECt * FROM `hsca_district_demographic_dtls` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
					Object[] params = new Object[] { list_formB_app.get(x).getDistrict_id(),
							list_formB_app.get(x).getCycle_id(), list_formB_app.get(x).getYear() };

					flag = jdbcTemplate.query(sql, params, rs -> {

						String check = "0";

						while (rs.next()) {
							check = rs.getString("dist_demogra_dtl_id");
						}
						/* We can also return any variable-data from here but not used currently */
						return "" + check;
					});
				}

				if ("0".equals(flag)) {

				

				} else {

					
					continue;
				}

				Form1BPrimaryTableDataBean obj = list_formB_app.get(x);

				int row = 0;

				String sql1 = "INSERT INTO `hsca_district_demographic_dtls`(`date_of_verification`, `filled_by`,`chairperson_of_meeting`,`chairperson_of_meeting_others`,"
						+ " `total_area`, `total_area_demogra_id`, `total_pop`, `total_pop_demogra_id`,"
						+ " `num_women_in_reproductive_age_15_49_y`, `num_women_in_reproductive_age_15_49_y_source`, "
						+ "`num_child_under_5_y`, `num_child_under_5_y_demogra_id`, `rural_pop`, `rural_pop_demogra_id`,"
						+ " `sc_pop`, `sc_pop_demogra_id`, `st_pop`, `st_pop_demogra_id`, `pop_density`, "
						+ "`pop_density_demogra_id`, `total_literacy`, `total_literacy_demogra_id`,"
						+ " `fem_literacy`, `fem_literacy_demogra_id`, `district_id`, `cycle_id`, `financial_year`,"
						+ " `user_id`,`record_created`,`completed`)" + " VALUES (?,?,?,?,?," + "?,?,?," + "?,?,?," + "?,?,?,"
						+ "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?,?)";

				KeyHolder keyHolder = new GeneratedKeyHolder();

				Form1BPrimaryTableDataBean addobj1 = new Form1BPrimaryTableDataBean();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, obj.getMeetingdate());
					addobj1.setMeetingdate(obj.getMeetingdate());

					ps.setString(2, obj.getMeetingvenue());
					addobj1.setMeetingvenue(obj.getMeetingvenue());

					ps.setString(3, obj.getChairpersonid());
					addobj1.setChairpersonid(obj.getChairpersonid());

					ps.setString(4, obj.getOtherchairperson());
					addobj1.setOtherchairperson(obj.getOtherchairperson());

					ps.setString(5, obj.getTotal_area());
					addobj1.setTotal_area(obj.getTotal_area());

					ps.setString(6, obj.getTotal_area_demogra_id());
					addobj1.setTotal_area_demogra_id(obj.getTotal_area_demogra_id());
					ps.setString(7, obj.getTotal_pop());
					addobj1.setTotal_pop(obj.getTotal_pop());
					ps.setString(8, obj.getTotal_pop_demogra_id());
					addobj1.setTotal_pop_demogra_id(obj.getTotal_pop_demogra_id());
					ps.setString(9, obj.getNum_women_in_reproductive_age_15_49_y());
					addobj1.setNum_women_in_reproductive_age_15_49_y(obj.getNum_women_in_reproductive_age_15_49_y());
					ps.setString(10, obj.getNum_women_in_reproductive_age_15_49_y_source_id());
					addobj1.setNum_women_in_reproductive_age_15_49_y_source_id(
							obj.getNum_women_in_reproductive_age_15_49_y_source_id());
					ps.setString(11, obj.getNum_child_under_5_y());
					addobj1.setNum_child_under_5_y(obj.getNum_child_under_5_y());
					ps.setString(12, obj.getNum_child_under_5_y_demogra_id());
					addobj1.setNum_child_under_5_y_demogra_id(obj.getNum_child_under_5_y_demogra_id());
					ps.setString(13, obj.getRural_pop());
					addobj1.setRural_pop(obj.getRural_pop());
					ps.setString(14, obj.getRural_pop_demogra_id());
					addobj1.setRural_pop_demogra_id(obj.getRural_pop_demogra_id());
					ps.setString(15, obj.getSc_pop());
					addobj1.setSc_pop(obj.getSc_pop());
					ps.setString(16, obj.getSc_pop_demogra_id());
					addobj1.setSc_pop_demogra_id(obj.getSc_pop_demogra_id());
					ps.setString(17, obj.getSt_pop());
					addobj1.setSt_pop(obj.getSt_pop());
					ps.setString(18, obj.getSt_pop_demogra_id());
					addobj1.setSt_pop_demogra_id(obj.getSt_pop_demogra_id());
					ps.setString(19, obj.getPop_density());
					addobj1.setPop_density(obj.getPop_density());
					ps.setString(20, obj.getPop_density_demogra_id());
					addobj1.setPop_density_demogra_id(obj.getPop_density_demogra_id());
					ps.setString(21, obj.getTotal_literacy());
					addobj1.setTotal_literacy(obj.getTotal_literacy());
					ps.setString(22, obj.getTotal_literacy_demogra_id());
					addobj1.setTotal_literacy_demogra_id(obj.getTotal_literacy_demogra_id());
					ps.setString(23, obj.getFem_literacy());
					addobj1.setFem_literacy(obj.getFem_literacy());
					ps.setString(24, obj.getFem_literacy_demogra_id());
					addobj1.setFem_literacy_demogra_id(obj.getFem_literacy_demogra_id());
					ps.setString(25, obj.getDistrict_id());
					addobj1.setDistrict_id(obj.getDistrict_id());
					ps.setString(26, obj.getCycle_id());
					addobj1.setCycle_id(obj.getCycle_id());
					ps.setString(27, obj.getYear());
					addobj1.setYear(obj.getYear());
					ps.setString(28, obj.getUser_id());
					addobj1.setUser_id("" + obj.getUser_id());
					ps.setString(29, formatedDateTime);
					addobj1.setRecordcreated(formatedDateTime);
					ps.setString(30, obj.getCompleted());
					addobj1.setUser_id(obj.getCompleted());

					return ps;
				}, keyHolder);

				// ResultSet rs = ps.getGeneratedKeys();

				long p_key = keyHolder.getKey().longValue();
				

				String sql2 = "INSERT INTO `hsca_district_others`(`dist_demogra_dtl_id`, `total_area_others`,"
						+ " `total_pop_others`, `num_women_in_reproductive_others`, `no_of_child_under5_others`,"
						+ " `rural_pop_others`,`sc_pop_others`, `st_pop_others`, `pop_dens_others`, `tot_lit_others`, "
						+ "`female_lit_others`,  `district_id`, `cycle_id`, `financial_year`, `user_id`, "
						+ "`record_created`) VALUES (?,?,?," + "?,?,?," + "?,?, ?,?,?," + "?,?,?,?,?)";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql2);
					ps.setString(1, "" + p_key);
					ps.setString(2, obj.getTotal_area_others());
					addobj1.setTotal_area_others("" + obj.getTotal_area_others());
					ps.setString(3, obj.getTotal_pop_others());
					addobj1.setTotal_pop_others("" + obj.getTotal_pop_others());
					ps.setString(4, obj.getNum_women_in_reproductive_others());
					addobj1.setNum_women_in_reproductive_others("" + obj.getNum_women_in_reproductive_others());
					ps.setString(5, obj.getNum_child_under_5_others());
					addobj1.setNum_child_under_5_others("" + obj.getNum_child_under_5_others());
					ps.setString(6, obj.getRural_pop_others());
					addobj1.setRural_pop_others("" + obj.getRural_pop_others());
					ps.setString(7, obj.getSc_pop_others());
					addobj1.setSc_pop_others("" + obj.getSc_pop_others());
					ps.setString(8, obj.getSt_pop_others());
					addobj1.setSt_pop_others("" + obj.getSt_pop_others());
					ps.setString(9, obj.getPop_dens_others());
					addobj1.setPop_dens_others("" + obj.getPop_dens_others());
					ps.setString(10, obj.getTotal_lit_others());
					addobj1.setTotal_lit_others("" + obj.getTotal_lit_others());
					ps.setString(11, obj.getFem_lit_others());
					addobj1.setFem_lit_others("" + obj.getFem_lit_others());
					ps.setString(12, obj.getDistrict_id());
					addobj1.setDistrict_id("" + obj.getDistrict_id());
					ps.setString(13, obj.getCycle_id());
					addobj1.setCycle_id("" + obj.getCycle_id());
					ps.setString(14, obj.getYear());
					addobj1.setYear("" + obj.getYear());
					ps.setString(15, obj.getUser_id());
					addobj1.setUser_id("" + obj.getUser_id());
					ps.setString(16, formatedDateTime);

					return ps;
				});

				addobj1.setAuto_id("" + p_key);
				addobj1.setDatafrom("WEB");

				addobj1.setCountry_id(obj.getCountry_id());
				addobj1.setProvince_id(obj.getProvince_id());

				list_formB_add.add(addobj1);

			

				Form1BIndicatorsTableDataBean indibean = list_indicators_app.get(0);

				String sql_theme = "INSERT INTO `hsca_requirements_iphs`( `dist_demogra_dtl_id`, `theme_name`, "
						+ "`coverage_indicators`, `source`, `data_mcts`, "
						+ "`gap_hmis`, `district_id`, `cycle_id`, `financial_year`, "
						+ "`user_id`, `record_created`, `ci_sl_no`,`expected_status`) VALUES (?,?," + "?,?, ?,?,?,?,"
						+ "?,?,?,?,?)";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql_theme);
					ps.setString(1, "" + p_key);
					ps.setString(2, indibean.getTheme());
					ps.setString(3, "");
					ps.setString(4, "");
					ps.setString(5, "0");
					ps.setString(6, "0");
					ps.setString(7, indibean.getDistrict_id());
					ps.setString(8, indibean.getCycle_id());
					ps.setString(9, indibean.getYear());
					ps.setString(10, indibean.getUser_id());
					ps.setString(11, formatedDateTime);
					ps.setString(12, "2.1.1");
					ps.setString(13, "100");

					return ps;
				});

				if (list_indicators_app.size() != 0) {

					for (int i = 0; i < list_indicators_app.size(); i++) {

						Form1BIndicatorsTableDataBean indiref = list_indicators_app.get(i);

						if (obj.getDistrict_id().equals(indiref.getDistrict_id())
								&& obj.getCycle_id().equals(indiref.getCycle_id())
								&& obj.getYear().equals(indiref.getYear())) {

							String indi_sql = "insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,"
									+ "					`indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,`user_id`,`dist_demogra_dtl_id`) values(?,"
									+ "							?,?,?,?,?,?,?,?,?,?,?,?,?)";

							String area_name = "";
							String indicator_name = "";

							for (int index = 0; index < getlist1.size(); index++) {

								Areas_of_Indicators_List temp_obj = getlist1.get(index);

								if (indiref.getAreaid().equals(temp_obj.getId())) {
									area_name = temp_obj.getArea_name();
								}
							}

							for (int index = 0; index < getlist2.size(); index++) {

								Menu_Area_Indicator_Object temp_obj = getlist2.get(index);

								if (indiref.getIndicatorid().equals(temp_obj.getIndicator_id())) {
									indicator_name = temp_obj.getIndicator_val();
								}
							}

							final String a_name = area_name;
							final String i_name = indicator_name;

							Form1BIndicatorsTableDataBean addindibean = new Form1BIndicatorsTableDataBean();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(indi_sql,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, indiref.getAreaid());
								addindibean.setAreaid(indiref.getAreaid());
								ps.setString(2, indiref.getIndicatorid());
								addindibean.setIndicatorid(indiref.getIndicatorid());
								ps.setString(3, "" + a_name);
								ps.setString(4, "" + i_name);
								ps.setString(5, indiref.getDatapresent());
								addindibean.setDatapresent(indiref.getDatapresent());
								ps.setString(6, indiref.getDatagap());
								addindibean.setDatagap(indiref.getDatagap());
								ps.setString(7, indiref.getDataexpected());
								addindibean.setDataexpected(indiref.getDataexpected());
								ps.setString(8, indiref.getSourceid());
								addindibean.setSourceid(indiref.getSourceid());
								ps.setString(9, indiref.getDistrict_id());
								addindibean.setDistrict_id(indiref.getDistrict_id());
								ps.setString(10, indiref.getCycle_id());
								addindibean.setCycle_id(indiref.getCycle_id());
								ps.setString(11, indiref.getYear());
								addindibean.setYear(indiref.getYear());
								ps.setString(12, "" + formatedDateTime);
								addindibean.setRecordcreated("" + formatedDateTime);
								ps.setString(13, indiref.getUser_id());
								addindibean.setUser_id(indiref.getUser_id());
								ps.setString(14, "" + p_key);
								addindibean.setHsca_district_demographic_id("" + p_key);

								return ps;
							}, keyHolder);

							addindibean.setCountry_id(indiref.getCountry_id());
							addindibean.setProvince_id(indiref.getProvince_id());
							addindibean.setTheme(indiref.getTheme());
							addindibean.setAuto_id("" + keyHolder.getKey().longValue());
							addindibean.setTimestamp(formatedDateTime);
							addindibean.setDatafrom("WEB");

							list_indicators_add.add(addindibean);
						}

					}

				} // list_indicators_app.size()

				if (list_ngo_app.size() != 0) {

					for (int y = 0; y < list_ngo_app.size(); y++) {

						Form1BNgoTableDataBean ngoref = list_ngo_app.get(y);

						

						if (obj.getDistrict_id().equals(ngoref.getDistrict_id())
								&& obj.getCycle_id().equals(ngoref.getCycle_id())
								&& obj.getYear().equals(ngoref.getYear())) {

							String sql3 = "INSERT INTO `key_ngo_demogra`(`dist_demogra_dtl_id`, `key_ngo_info`, `key_ngo_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)  "
									+ "VALUES (?,?,?,?,?,?,?,?)";

							Form1BNgoTableDataBean addBean2 = new Form1BNgoTableDataBean();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql3,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								addBean2.setHsca_district_demographic_id("" + p_key);
								ps.setString(2, ngoref.getNgoname());
								addBean2.setNgoname(ngoref.getNgoname());
								ps.setString(3, ngoref.getNgocontactdetails());
								addBean2.setNgocontactdetails(ngoref.getNgocontactdetails());
								ps.setString(4, ngoref.getDistrict_id());
								addBean2.setDistrict_id(ngoref.getDistrict_id());
								ps.setString(5, ngoref.getCycle_id());
								addBean2.setCycle_id(ngoref.getCycle_id());
								ps.setString(6, ngoref.getYear());
								addBean2.setYear(ngoref.getYear());
								ps.setString(7, ngoref.getUser_id());
								addBean2.setUser_id(ngoref.getUser_id());
								ps.setString(8, formatedDateTime);
								addBean2.setRecordcreated(formatedDateTime);

								return ps;
							}, keyHolder);

							addBean2.setAuto_id("" + keyHolder.getKey().longValue());
							addBean2.setDatafrom("WEB");

							addBean2.setCountry_id(ngoref.getCountry_id());
							addBean2.setProvince_id(ngoref.getProvince_id());
							addBean2.setTimestamp(formatedDateTime);
							list_ngo_add.add(addBean2);

						}

					}

				} // ngo_app sze check

				if (list_stakes_app.size() != 0) {

					for (int z = 0; z < list_stakes_app.size(); z++) {

						Form1BStakeHolderTableDataBean stakeref = list_stakes_app.get(z);

						

						if (obj.getDistrict_id().equals(stakeref.getDistrict_id())
								&& obj.getCycle_id().equals(stakeref.getCycle_id())
								&& obj.getYear().equals(stakeref.getYear())) {

							String sql4 = "INSERT INTO `key_pvt_demogra`(`dist_demogra_dtl_id`, `key_pvt_info`, `key_pvt_source`, `district_id`, `cycle_id`, `financial_year`, `user_id`,`record_created`)    "
									+ "VALUES (?,?,?,?,?,?,?,?)";

							Form1BStakeHolderTableDataBean addbean3 = new Form1BStakeHolderTableDataBean();

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql4,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								addbean3.setHsca_district_demographic_id("" + p_key);
								ps.setString(2, stakeref.getNgoname());
								addbean3.setNgoname(stakeref.getNgoname());
								ps.setString(3, stakeref.getNgocontactdetails());
								addbean3.setNgocontactdetails(stakeref.getNgocontactdetails());
								ps.setString(4, stakeref.getDistrict_id());
								addbean3.setDistrict_id(stakeref.getDistrict_id());
								ps.setString(5, stakeref.getCycle_id());
								addbean3.setCycle_id(stakeref.getCycle_id());
								ps.setString(6, stakeref.getYear());
								addbean3.setYear(stakeref.getYear());
								ps.setString(7, stakeref.getUser_id());
								addbean3.setUser_id(stakeref.getUser_id());
								ps.setString(8, formatedDateTime);
								addbean3.setRecordcreated(formatedDateTime);
								return ps;
							}, keyHolder);

							addbean3.setAuto_id("" + keyHolder.getKey().longValue());
							addbean3.setDatafrom("WEB");

							addbean3.setCountry_id(stakeref.getCountry_id());
							addbean3.setProvince_id(stakeref.getProvince_id());
							addbean3.setTimestamp(formatedDateTime);
							list_stakes_add.add(addbean3);
						}

					}

				} // if(list_stakes_app.size() != 0) {

				if (list_infra_app.size() != 0) {

					for (int i = 0; i < list_infra_app.size(); i++) {

						Form1BdocumentsBean bean = list_infra_app.get(i);

						

						if (obj.getDistrict_id().equals(bean.getDistrict_id())
								&& obj.getCycle_id().equals(bean.getCycle_id())
								&& obj.getYear().equals(bean.getYear())) {

							String sql5 = "insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id, details_infra, sanctioned_infra,"
									+ "		available_functional_infra, gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
									+ "		) values (?,?,?,?,?,?,?,?,?,?)";
							Form1BdocumentsBean addbean4 = new Form1BdocumentsBean();
							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								addbean4.setHsca_district_demographic_id("" + p_key);
								ps.setString(2, "" + bean.getDetails());
								addbean4.setDetails(bean.getDetails());
								ps.setString(3, "" + bean.getSanctioned());
								addbean4.setSanctioned(bean.getSanctioned());
								ps.setString(4, "" + bean.getAvailable());
								addbean4.setAvailable(bean.getAvailable());
								ps.setString(5, "" + bean.getGap());
								addbean4.setGap(bean.getGap());
								ps.setString(6, "" + bean.getDistrict_id());
								addbean4.setDistrict_id(bean.getDistrict_id());
								ps.setString(7, "" + bean.getCycle_id());
								addbean4.setCycle_id(bean.getCycle_id());
								ps.setString(8, "" + bean.getYear());
								addbean4.setYear(bean.getYear());
								ps.setString(9, "" + bean.getUser_id());
								addbean4.setUser_id(bean.getUser_id());
								ps.setString(10, "" + formatedDateTime);
								addbean4.setRecordcreated("" + formatedDateTime);
								return ps;
							}, keyHolder);

							addbean4.setAuto_id("" + keyHolder.getKey().longValue());
							addbean4.setDatafrom("WEB");

							addbean4.setCountry_id(bean.getCountry_id());
							addbean4.setProvince_id(bean.getProvince_id());
							addbean4.setTimestamp(formatedDateTime);
							list_infra_add.add(addbean4);
						}
					}

				} // list_infra_app.size()

				if (list_fina_app.size() != 0) {

					for (int i = 0; i < list_fina_app.size(); i++) {

						Form1BdocumentsBean bean = list_fina_app.get(i);

						

						if (obj.getDistrict_id().equals(bean.getDistrict_id())
								&& obj.getCycle_id().equals(bean.getCycle_id())
								&& obj.getYear().equals(bean.getYear())) {

							String sql5 = "insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
									+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
									+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)";
							Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								addbean5.setHsca_district_demographic_id("" + p_key);
								ps.setString(2, "" + bean.getDetails());
								addbean5.setDetails(bean.getDetails());
								ps.setString(3, "" + bean.getSanctioned());
								addbean5.setSanctioned(bean.getSanctioned());
								ps.setString(4, "" + bean.getAvailable());
								addbean5.setAvailable(bean.getAvailable());
								ps.setString(5, "" + bean.getGap());
								addbean5.setGap(bean.getGap());
								ps.setString(6, "" + bean.getDistrict_id());
								addbean5.setDistrict_id(bean.getDistrict_id());
								ps.setString(7, "" + bean.getCycle_id());
								addbean5.setCycle_id(bean.getCycle_id());
								ps.setString(8, "" + bean.getYear());
								addbean5.setYear(bean.getYear());
								ps.setString(9, "" + bean.getUser_id());
								addbean5.setUser_id(bean.getUser_id());
								ps.setString(10, "" + formatedDateTime);
								addbean5.setRecordcreated("" + formatedDateTime);
								return ps;
							}, keyHolder);

							addbean5.setAuto_id("" + keyHolder.getKey().longValue());
							addbean5.setDatafrom("WEB");

							addbean5.setCountry_id(bean.getCountry_id());
							addbean5.setProvince_id(bean.getProvince_id());
							addbean5.setTimestamp(formatedDateTime);
							list_fina_add.add(addbean5);

						}

					}

				} // list_fina_app.size()

				if (list_supp_app.size() != 0) {

					for (int i = 0; i < list_supp_app.size(); i++) {

						Form1BdocumentsBean bean = list_supp_app.get(i);

						

						if (obj.getDistrict_id().equals(bean.getDistrict_id())
								&& obj.getCycle_id().equals(bean.getCycle_id())
								&& obj.getYear().equals(bean.getYear())) {

							String sql5 = "insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
									+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
									+ "cycle_id,financial_year," + "user_id,record_created) values (?,"
									+ "?,?,?,?,?,?,?," + "?,?)";

							Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								addbean5.setHsca_district_demographic_id("" + p_key);
								ps.setString(2, "" + bean.getDetails());
								addbean5.setDetails(bean.getDetails());
								ps.setString(3, "" + bean.getSanctioned());
								addbean5.setSanctioned(bean.getSanctioned());
								ps.setString(4, "" + bean.getAvailable());
								addbean5.setAvailable(bean.getAvailable());
								ps.setString(5, "" + bean.getGap());
								addbean5.setGap(bean.getGap());
								ps.setString(6, "" + bean.getDistrict_id());
								addbean5.setDistrict_id(bean.getDistrict_id());
								ps.setString(7, "" + bean.getCycle_id());
								addbean5.setCycle_id(bean.getCycle_id());
								ps.setString(8, "" + bean.getYear());
								addbean5.setYear(bean.getYear());
								ps.setString(9, "" + bean.getUser_id());
								addbean5.setUser_id(bean.getUser_id());
								ps.setString(10, "" + formatedDateTime);
								addbean5.setRecordcreated("" + formatedDateTime);
								return ps;
							}, keyHolder);

							addbean5.setAuto_id("" + keyHolder.getKey().longValue());
							addbean5.setDatafrom("WEB");

							addbean5.setCountry_id(bean.getCountry_id());
							addbean5.setProvince_id(bean.getProvince_id());
							addbean5.setTimestamp(formatedDateTime);
							list_supp_add.add(addbean5);

						}
					}

				} // list_supp_app.size()

				if (list_tech_app.size() != 0) {

					for (int i = 0; i < list_tech_app.size(); i++) {

						Form1BdocumentsBean bean = list_tech_app.get(i);

						

						if (obj.getDistrict_id().equals(bean.getDistrict_id())
								&& obj.getCycle_id().equals(bean.getCycle_id())
								&& obj.getYear().equals(bean.getYear())) {

							String sql5 = "insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
									+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
									+ "user_id,record_created) values (" + "?," + "?,?,?,?,?,?,?," + "?,?)";

							Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								addbean5.setHsca_district_demographic_id("" + p_key);
								ps.setString(2, "" + bean.getDetails());
								addbean5.setDetails(bean.getDetails());
								ps.setString(3, "" + bean.getSanctioned());
								addbean5.setSanctioned(bean.getSanctioned());
								ps.setString(4, "" + bean.getAvailable());
								addbean5.setAvailable(bean.getAvailable());
								ps.setString(5, "" + bean.getGap());
								addbean5.setGap(bean.getGap());
								ps.setString(6, "" + bean.getDistrict_id());
								addbean5.setDistrict_id(bean.getDistrict_id());
								ps.setString(7, "" + bean.getCycle_id());
								addbean5.setCycle_id(bean.getCycle_id());
								ps.setString(8, "" + bean.getYear());
								addbean5.setYear(bean.getYear());
								ps.setString(9, "" + bean.getUser_id());
								addbean5.setUser_id(bean.getUser_id());
								ps.setString(10, "" + formatedDateTime);
								addbean5.setRecordcreated("" + formatedDateTime);
								return ps;
							}, keyHolder);

							addbean5.setAuto_id("" + keyHolder.getKey().longValue());
							addbean5.setDatafrom("WEB");

							addbean5.setCountry_id(bean.getCountry_id());
							addbean5.setProvince_id(bean.getProvince_id());
							addbean5.setTimestamp(formatedDateTime);
							list_tech_add.add(addbean5);

						}

					}

				} // list_tech_app.size()

				if (list_hr_app.size() != 0) {

					for (int i = 0; i < list_hr_app.size(); i++) {

						Form1BdocumentsBean bean = list_hr_app.get(i);

						

						if (obj.getDistrict_id().equals(bean.getDistrict_id())
								&& obj.getCycle_id().equals(bean.getCycle_id())
								&& obj.getYear().equals(bean.getYear())) {

							String sql5 = "insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
									+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
									+ "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)";

							Form1BdocumentsBean addbean5 = new Form1BdocumentsBean();
							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql5,
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + p_key);
								addbean5.setHsca_district_demographic_id("" + p_key);
								ps.setString(2, "" + bean.getDetails());
								addbean5.setDetails(bean.getDetails());
								ps.setString(3, "" + bean.getSanctioned());
								addbean5.setSanctioned(bean.getSanctioned());
								ps.setString(4, "" + bean.getAvailable());
								addbean5.setAvailable(bean.getAvailable());
								ps.setString(5, "" + bean.getGap());
								addbean5.setGap(bean.getGap());
								ps.setString(6, "" + bean.getDistrict_id());
								addbean5.setDistrict_id(bean.getDistrict_id());
								ps.setString(7, "" + bean.getCycle_id());
								addbean5.setCycle_id(bean.getCycle_id());
								ps.setString(8, "" + bean.getYear());
								addbean5.setYear(bean.getYear());
								ps.setString(9, "" + bean.getUser_id());
								addbean5.setUser_id(bean.getUser_id());
								ps.setString(10, "" + formatedDateTime);
								addbean5.setRecordcreated("" + formatedDateTime);
								return ps;
							}, keyHolder);

							addbean5.setAuto_id("" + keyHolder.getKey().longValue());
							addbean5.setDatafrom("WEB");

							addbean5.setCountry_id(bean.getCountry_id());
							addbean5.setProvince_id(bean.getProvince_id());
							addbean5.setTimestamp(formatedDateTime);

							list_hr_add.add(addbean5);

						}

					}

				} // list_hr_app.size()

			}

		}

		/*---------------------------------------------------------------------*/

	

		if (list_formB_web.size() != 0) {

			for (int x = 0; x < list_formB_web.size(); x++) {

				Form1BPrimaryTableDataBean obj = list_formB_web.get(x);

				Form1BPrimaryTableDataBean mybean = new Form1BPrimaryTableDataBean();

				int row = 0;

				String sql1 = " UPDATE `hsca_district_demographic_dtls` SET `date_of_verification`=?, `filled_by`=?,`chairperson_of_meeting`=?,`chairperson_of_meeting_others`=?,"
						+ "				`total_area`=?, `total_area_demogra_id`=?, `total_pop`=?, `total_pop_demogra_id`=?,"
						+ "				`num_women_in_reproductive_age_15_49_y`=?, `num_women_in_reproductive_age_15_49_y_source`=?, "
						+ "				`num_child_under_5_y`=?, `num_child_under_5_y_demogra_id`=?, `rural_pop`=?, `rural_pop_demogra_id`=?,"
						+ "				`sc_pop`=?, `sc_pop_demogra_id`=?, `st_pop`=?, `st_pop_demogra_id`=?, `pop_density`=?, "
						+ "				`pop_density_demogra_id`=?, `total_literacy`=?, `total_literacy_demogra_id`=?,"
						+ "				`fem_literacy`=?, `fem_literacy_demogra_id`=?, "
						+ "				`user_id`=?,`record_created`=?,`completed`=? WHERE `dist_demogra_dtl_id`=?";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1);
					ps.setString(1, obj.getMeetingdate());
					mybean.setMeetingdate(obj.getMeetingdate());

					ps.setString(2, obj.getMeetingvenue());
					mybean.setMeetingvenue(obj.getMeetingvenue());

					ps.setString(3, obj.getChairpersonid());
					mybean.setChairpersonid(obj.getChairpersonid());

					ps.setString(4, obj.getOtherchairperson());
					mybean.setOtherchairperson(obj.getOtherchairperson());

					ps.setString(5, obj.getTotal_area());
					mybean.setTotal_area(obj.getTotal_area());

					ps.setString(6, obj.getTotal_area_demogra_id());
					mybean.setTotal_area_demogra_id(obj.getTotal_area_demogra_id());

					ps.setString(7, obj.getTotal_pop());
					mybean.setTotal_pop(obj.getTotal_pop());

					ps.setString(8, obj.getTotal_pop_demogra_id());
					mybean.setTotal_pop_demogra_id(obj.getTotal_pop_demogra_id());

					ps.setString(9, obj.getNum_women_in_reproductive_age_15_49_y());
					mybean.setNum_women_in_reproductive_age_15_49_y(obj.getNum_women_in_reproductive_age_15_49_y());

					ps.setString(10, obj.getNum_women_in_reproductive_age_15_49_y_source_id());
					mybean.setNum_women_in_reproductive_age_15_49_y_source_id(
							obj.getNum_women_in_reproductive_age_15_49_y_source_id());

					ps.setString(11, obj.getNum_child_under_5_y());
					mybean.setNum_child_under_5_y(obj.getNum_child_under_5_y());

					ps.setString(12, obj.getNum_child_under_5_y_demogra_id());
					mybean.setNum_child_under_5_y_demogra_id(obj.getNum_child_under_5_y_demogra_id());

					ps.setString(13, obj.getRural_pop());
					mybean.setRural_pop(obj.getRural_pop());

					ps.setString(14, obj.getRural_pop_demogra_id());
					mybean.setRural_pop_demogra_id(obj.getRural_pop_demogra_id());

					ps.setString(15, obj.getSc_pop());
					mybean.setSc_pop(obj.getSc_pop());

					ps.setString(16, obj.getSc_pop_demogra_id());
					mybean.setSc_pop_demogra_id(obj.getSc_pop_demogra_id());

					ps.setString(17, obj.getSt_pop());
					mybean.setSt_pop(obj.getSt_pop());

					ps.setString(18, obj.getSt_pop_demogra_id());
					mybean.setSt_pop_demogra_id(obj.getSt_pop_demogra_id());

					ps.setString(19, obj.getPop_density());
					mybean.setPop_density(obj.getPop_density());

					ps.setString(20, obj.getPop_density_demogra_id());
					mybean.setPop_density_demogra_id(obj.getPop_density_demogra_id());

					ps.setString(21, obj.getTotal_literacy());
					mybean.setTotal_literacy(obj.getTotal_literacy());

					ps.setString(22, obj.getTotal_literacy_demogra_id());
					mybean.setTotal_literacy_demogra_id(obj.getTotal_literacy_demogra_id());

					ps.setString(23, obj.getFem_literacy());
					mybean.setFem_literacy(obj.getFem_literacy());

					ps.setString(24, obj.getFem_literacy_demogra_id());
					mybean.setFem_literacy_demogra_id(obj.getFem_literacy_demogra_id());

					ps.setString(25, obj.getUser_id());
					mybean.setUser_id(obj.getUser_id());

					ps.setString(26, formatedDateTime);
					mybean.setRecordcreated(formatedDateTime);
					
					ps.setString(27, obj.getCompleted());
					mybean.setCompleted(obj.getCompleted());

					ps.setString(28, obj.getAuto_id());
					mybean.setAuto_id(obj.getAuto_id());
					return ps;
				});

				

				int row2 = 0;

				String sql2 = "UPDATE `hsca_district_others` SET `total_area_others`=?,"
						+ "`total_pop_others`=?, `num_women_in_reproductive_others`=?,"
						+ "`no_of_child_under5_others`=?,`rural_pop_others`=?,"
						+ "`sc_pop_others`=?, `st_pop_others`=?,`pop_dens_others`=?,  "
						+ "`tot_lit_others`=?, `female_lit_others`=?, `user_id`=?,"
						+ "`record_created`=? WHERE `dist_demogra_dtl_id`=?";

				row2 = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql2);
					ps.setString(1, obj.getTotal_area_others());
					mybean.setTotal_area_others(obj.getTotal_area_others());

					ps.setString(2, obj.getTotal_pop_others());
					mybean.setTotal_pop_others(obj.getTotal_pop_others());

					ps.setString(3, obj.getNum_women_in_reproductive_others());
					mybean.setNum_women_in_reproductive_others(obj.getNum_women_in_reproductive_others());

					ps.setString(4, obj.getNum_child_under_5_others());
					mybean.setNum_child_under_5_others(obj.getNum_child_under_5_others());

					ps.setString(5, obj.getRural_pop_others());
					mybean.setRural_pop_others(obj.getRural_pop_others());

					ps.setString(6, obj.getSc_pop_others());
					mybean.setSc_pop_others(obj.getSc_pop_others());

					ps.setString(7, obj.getSt_pop_others());
					mybean.setSt_pop_others(obj.getSt_pop_others());

					ps.setString(8, obj.getPop_dens_others());
					mybean.setPop_dens_others(obj.getPop_dens_others());

					ps.setString(9, obj.getTotal_lit_others());
					mybean.setTotal_lit_others(obj.getTotal_lit_others());

					ps.setString(10, obj.getFem_lit_others());
					mybean.setFem_lit_others(obj.getFem_lit_others());

					ps.setString(11, obj.getUser_id());
					mybean.setUser_id(obj.getUser_id());

					ps.setString(12, formatedDateTime);
					mybean.setRecordcreated(obj.getRecordcreated());

					ps.setString(13, obj.getAuto_id());

					return ps;
				});

				mybean.setCountry_id(obj.getCountry_id());
				mybean.setProvince_id(obj.getProvince_id());
				mybean.setDistrict_id(obj.getDistrict_id());
				mybean.setCycle_id(obj.getCycle_id());
				mybean.setYear(obj.getYear());

				mybean.setDatafrom("WEB");

				list_formB_add.add(mybean);

			}
		}

		if (list_ngo_web.size() != 0) {

			int key_ngo_row = 0;

			for (int y = 0; y < list_ngo_web.size(); y++) {

				Form1BNgoTableDataBean ngoref = list_ngo_web.get(y);

				String sql3 = "UPDATE `key_ngo_demogra` SET  `key_ngo_info` = ?, `key_ngo_source` = ?, `user_id`=?, "
						+ " `record_created` = ? WHERE `key_ngo_id` = ?";

				Form1BNgoTableDataBean mybean = new Form1BNgoTableDataBean();

				key_ngo_row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql3);
					ps.setString(1, ngoref.getNgoname());

					mybean.setNgoname(ngoref.getNgoname());

					ps.setString(2, ngoref.getNgocontactdetails());
					mybean.setNgocontactdetails(ngoref.getNgocontactdetails());

					ps.setString(3, ngoref.getUser_id());
					mybean.setUser_id(ngoref.getUser_id());

					ps.setString(4, formatedDateTime);
					mybean.setRecordcreated(formatedDateTime);

					ps.setString(5, ngoref.getAuto_id());
					mybean.setAuto_id(ngoref.getAuto_id());

					return ps;
				});

				mybean.setCountry_id(ngoref.getCountry_id());
				mybean.setProvince_id(ngoref.getProvince_id());
				mybean.setDistrict_id(ngoref.getDistrict_id());
				mybean.setCycle_id(ngoref.getCycle_id());
				mybean.setYear(ngoref.getYear());
				mybean.setHsca_district_demographic_id(ngoref.getHsca_district_demographic_id());
				mybean.setTimestamp(formatedDateTime);
				mybean.setUser_id(ngoref.getUser_id());

				mybean.setDatafrom("WEB");

				list_ngo_add.add(mybean);

				

			}

		} // list_ngo_web.size()

		if (list_stakes_web.size() != 0) {

			for (int z = 0; z < list_stakes_web.size(); z++) {

				Form1BStakeHolderTableDataBean stakesref = list_stakes_web.get(z);

				int key_ngo_src_row = 0;

				String sql3 = "UPDATE `key_pvt_demogra` SET `key_pvt_info` = ?, `key_pvt_source` = ?, "
						+ " `user_id` = ?, `record_created` = ? WHERE `key_pvt_id` = ?";

				Form1BStakeHolderTableDataBean mybean = new Form1BStakeHolderTableDataBean();

				key_ngo_src_row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql3);
					ps.setString(1, stakesref.getNgoname());
					mybean.setNgoname(stakesref.getNgoname());

					ps.setString(2, stakesref.getNgocontactdetails());
					mybean.setNgocontactdetails(stakesref.getNgocontactdetails());

					ps.setString(3, stakesref.getUser_id());
					mybean.setUser_id(stakesref.getUser_id());

					ps.setString(4, "" + formatedDateTime);
					mybean.setRecordcreated(formatedDateTime);

					ps.setString(5, stakesref.getAuto_id());
					mybean.setAuto_id(stakesref.getAuto_id());

					return ps;
				});

				mybean.setCountry_id(stakesref.getCountry_id());
				mybean.setProvince_id(stakesref.getProvince_id());
				mybean.setDistrict_id(stakesref.getDistrict_id());
				mybean.setCycle_id(stakesref.getCycle_id());
				mybean.setHsca_district_demographic_id(stakesref.getHsca_district_demographic_id());
				mybean.setYear(stakesref.getYear());
				mybean.setTimestamp(stakesref.getTimestamp());

				mybean.setDatafrom("WEB");

				list_stakes_add.add(mybean);

				

			}

		} // list_stakes_web.size()

		if (list_infra_web.size() != 0) {

			jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_infrastructure` SET `details_infra`=?,"
					+ " `sanctioned_infra`=?,`available_functional_infra`=?,"
					+ " `gap_infra`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?  and `infra_structure_details_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {

							Form1BdocumentsBean mybean = new Form1BdocumentsBean();

							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + list_infra_web.get(i).getDetails());
							ps.setString(2, "" + list_infra_web.get(i).getSanctioned());
							ps.setString(3, "" + list_infra_web.get(i).getAvailable());
							ps.setString(4, "" + list_infra_web.get(i).getGap());
							ps.setString(5, "" + list_infra_web.get(i).getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + list_infra_web.get(i).getHsca_district_demographic_id());
							ps.setString(8, "" + list_infra_web.get(i).getAuto_id());

							mybean.setDetails(list_infra_web.get(i).getDetails());
							mybean.setSanctioned(list_infra_web.get(i).getSanctioned());
							mybean.setAvailable(list_infra_web.get(i).getAvailable());
							mybean.setGap(list_infra_web.get(i).getGap());
							mybean.setUser_id(list_infra_web.get(i).getUser_id());
							mybean.setRecordcreated(formatedDateTime);
							mybean.setHsca_district_demographic_id(
									list_infra_web.get(i).getHsca_district_demographic_id());
							mybean.setAuto_id(list_infra_web.get(i).getAuto_id());
							mybean.setCountry_id(list_infra_web.get(i).getCountry_id());
							mybean.setDistrict_id(list_infra_web.get(i).getDistrict_id());
							mybean.setCycle_id(list_infra_web.get(i).getCycle_id());
							mybean.setProvince_id(list_infra_web.get(i).getProvince_id());
							mybean.setYear(list_infra_web.get(i).getYear());
							mybean.setTimestamp(list_infra_web.get(i).getTimestamp());

							mybean.setDatafrom("WEB");

							list_infra_app.add(mybean);
						}

						public int getBatchSize() {
							return list_infra_web.size();
						}

					});

		} // list_infra_web.size()

		if (list_fina_web.size() != 0) {

			jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_gen_res_finance` SET `details_fina`=?,"
					+ " `sanctioned_fina`=?,`available_functional_fina`=?,"
					+ " `gap_fina`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `finance_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {

							Form1BdocumentsBean mybean = new Form1BdocumentsBean();

							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + list_fina_web.get(i).getDetails());
							ps.setString(2, "" + list_fina_web.get(i).getSanctioned());
							ps.setString(3, "" + list_fina_web.get(i).getAvailable());
							ps.setString(4, "" + list_fina_web.get(i).getGap());
							ps.setString(5, "" + list_fina_web.get(i).getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + list_fina_web.get(i).getHsca_district_demographic_id());
							ps.setString(8, "" + list_fina_web.get(i).getAuto_id());

							mybean.setDetails(list_fina_web.get(i).getDetails());
							mybean.setSanctioned(list_fina_web.get(i).getSanctioned());
							mybean.setAvailable(list_fina_web.get(i).getAvailable());
							mybean.setGap(list_fina_web.get(i).getGap());
							mybean.setUser_id(list_fina_web.get(i).getUser_id());
							mybean.setRecordcreated(formatedDateTime);
							mybean.setHsca_district_demographic_id(
									list_fina_web.get(i).getHsca_district_demographic_id());
							mybean.setAuto_id(list_fina_web.get(i).getAuto_id());
							mybean.setCountry_id(list_fina_web.get(i).getCountry_id());
							mybean.setDistrict_id(list_fina_web.get(i).getDistrict_id());
							mybean.setCycle_id(list_fina_web.get(i).getCycle_id());
							mybean.setProvince_id(list_fina_web.get(i).getProvince_id());
							mybean.setYear(list_fina_web.get(i).getYear());
							mybean.setTimestamp(list_fina_web.get(i).getTimestamp());

							mybean.setDatafrom("WEB");

							list_fina_app.add(mybean);
						}

						public int getBatchSize() {
							return list_fina_web.size();
						}

					});
		} // list_fina_web.size()

		if (list_supp_web.size() != 0) {

			jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_gen_res_supplies` SET `details_supp`=?,"
					+ " `sanctioned_supp`=?,`available_functional_supp`=?,"
					+ " `gap_supp`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?  and `supplies_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {

							Form1BdocumentsBean mybean = new Form1BdocumentsBean();

							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + list_supp_web.get(i).getDetails());
							ps.setString(2, "" + list_supp_web.get(i).getSanctioned());
							ps.setString(3, "" + list_supp_web.get(i).getAvailable());
							ps.setString(4, "" + list_supp_web.get(i).getGap());
							ps.setString(5, "" + list_supp_web.get(i).getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + list_supp_web.get(i).getHsca_district_demographic_id());
							ps.setString(8, "" + list_supp_web.get(i).getAuto_id());

							mybean.setDetails(list_supp_web.get(i).getDetails());
							mybean.setSanctioned(list_supp_web.get(i).getSanctioned());
							mybean.setAvailable(list_supp_web.get(i).getAvailable());
							mybean.setGap(list_supp_web.get(i).getGap());
							mybean.setUser_id(list_supp_web.get(i).getUser_id());
							mybean.setRecordcreated(formatedDateTime);
							mybean.setHsca_district_demographic_id(
									list_supp_web.get(i).getHsca_district_demographic_id());
							mybean.setAuto_id(list_supp_web.get(i).getAuto_id());
							mybean.setCountry_id(list_supp_web.get(i).getCountry_id());
							mybean.setDistrict_id(list_supp_web.get(i).getDistrict_id());
							mybean.setCycle_id(list_supp_web.get(i).getCycle_id());
							mybean.setProvince_id(list_supp_web.get(i).getProvince_id());
							mybean.setYear(list_supp_web.get(i).getYear());
							mybean.setTimestamp(list_supp_web.get(i).getTimestamp());

							mybean.setDatafrom("WEB");

							list_supp_add.add(mybean);
						}

						public int getBatchSize() {
							return list_supp_web.size();
						}

					});
		}

		if (list_tech_web.size() != 0) {
			jdbcTemplate.batchUpdate("UPDATE `hsca_theme_1_gen_res_technology` SET `details_tech`=?,"
					+ " `sanctioned_tech`=?,`available_functional_tech`=?,"
					+ " `gap_tech`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `technology_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Form1BdocumentsBean mybean = new Form1BdocumentsBean();

							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + list_tech_web.get(i).getDetails());
							ps.setString(2, "" + list_tech_web.get(i).getSanctioned());
							ps.setString(3, "" + list_tech_web.get(i).getAvailable());
							ps.setString(4, "" + list_tech_web.get(i).getGap());
							ps.setString(5, "" + list_tech_web.get(i).getUser_id());
							ps.setString(6, "" + formatedDateTime);
							ps.setString(7, "" + list_tech_web.get(i).getHsca_district_demographic_id());
							ps.setString(8, "" + list_tech_web.get(i).getAuto_id());

							mybean.setDetails(list_tech_web.get(i).getDetails());
							mybean.setSanctioned(list_tech_web.get(i).getSanctioned());
							mybean.setAvailable(list_tech_web.get(i).getAvailable());
							mybean.setGap(list_tech_web.get(i).getGap());
							mybean.setUser_id(list_tech_web.get(i).getUser_id());
							mybean.setRecordcreated(formatedDateTime);
							mybean.setHsca_district_demographic_id(
									list_tech_web.get(i).getHsca_district_demographic_id());
							mybean.setAuto_id(list_tech_web.get(i).getAuto_id());
							mybean.setCountry_id(list_tech_web.get(i).getCountry_id());
							mybean.setDistrict_id(list_tech_web.get(i).getDistrict_id());
							mybean.setCycle_id(list_tech_web.get(i).getCycle_id());
							mybean.setProvince_id(list_tech_web.get(i).getProvince_id());
							mybean.setYear(list_tech_web.get(i).getYear());
							mybean.setTimestamp(list_tech_web.get(i).getTimestamp());

							mybean.setDatafrom("WEB");

							list_tech_add.add(mybean);
						}

						public int getBatchSize() {
							return list_tech_web.size();
						}

					});
		}

		if (list_hr_web.size() != 0) {
			jdbcTemplate.batchUpdate(
					"UPDATE `hsca_theme_1_hr` SET `details_hr`=?," + " `sanctioned_hr`=?,`available_functional_hr`=?,"
							+ " `gap_hr`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=? and `hr_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {

							Form1BdocumentsBean mybean = new Form1BdocumentsBean();

							// ps.setString(1, "" +model.getDist_demogra_dtl_id());
							ps.setString(1, "" + list_hr_web.get(i).getDetails());

							ps.setString(2, "" + list_hr_web.get(i).getSanctioned());

							ps.setString(3, "" + list_hr_web.get(i).getAvailable());

							ps.setString(4, "" + list_hr_web.get(i).getGap());

							ps.setString(5, "" + list_hr_web.get(i).getUser_id());

							ps.setString(6, "" + formatedDateTime);

							ps.setString(7, "" + list_hr_web.get(i).getHsca_district_demographic_id());

							ps.setString(8, "" + list_hr_web.get(i).getAuto_id());

							mybean.setDetails(list_hr_web.get(i).getDetails());
							mybean.setSanctioned(list_hr_web.get(i).getSanctioned());
							mybean.setAvailable(list_hr_web.get(i).getAvailable());
							mybean.setGap(list_hr_web.get(i).getGap());
							mybean.setUser_id(list_hr_web.get(i).getUser_id());
							mybean.setRecordcreated(formatedDateTime);
							mybean.setHsca_district_demographic_id(
									list_hr_web.get(i).getHsca_district_demographic_id());
							mybean.setAuto_id(list_hr_web.get(i).getAuto_id());
							mybean.setCountry_id(list_hr_web.get(i).getCountry_id());
							mybean.setDistrict_id(list_hr_web.get(i).getDistrict_id());
							mybean.setCycle_id(list_hr_web.get(i).getCycle_id());
							mybean.setProvince_id(list_hr_web.get(i).getProvince_id());
							mybean.setYear(list_hr_web.get(i).getYear());
							mybean.setTimestamp(list_hr_web.get(i).getTimestamp());
							mybean.setDatafrom("WEB");

							list_hr_add.add(mybean);

						}

						public int getBatchSize() {
							return list_hr_web.size();
						}

					});
		} // list_hr_web.size()

		if (list_indicators_web.size() != 0) {

			try {
				jdbcTemplate.batchUpdate("UPDATE `hsca_requirements_iphs` SET `theme_name`=?, "
						+ "				 `ci_sl_no`=?,`coverage_indicators`=?, "
						+ "				 `source`=?,`data_mcts`=?,`expected_status`=?, "
						+ "				 `gap_hmis`=?,`user_id`=?,`record_created`=?  WHERE `dist_demogra_dtl_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list_indicators_web.get(i).getTheme());
								ps.setString(2, "2.2.1");
								ps.setString(3, "");
								ps.setString(4, "0");
								ps.setString(5, "0");
								ps.setString(6, "0");
								ps.setString(7, "0");
								ps.setString(8, list_indicators_web.get(i).getUser_id());
								ps.setString(9, formatedDateTime);
								ps.setString(10, list_indicators_web.get(i).getHsca_district_demographic_id());

							}

							public int getBatchSize() {
								return list_indicators_web.size();
							}

						});
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			for (int i = 0; i < list_indicators_web.size(); i++) {

				Object[] params_d = { list_indicators_web.get(i).getDistrict_id(),
						list_indicators_web.get(i).getCycle_id(), list_indicators_web.get(i).getYear(),
						list_indicators_web.get(i).getHsca_district_demographic_id() };

				int rows9 = jdbcTemplate.update(
						"DELETE FROM `form1b_selected_indicators` where `district_id`=? and `cycle_id`=? and `year`=?  and `dist_demogra_dtl_id`=?",
						params_d);

			}

			for (int pos = 0; pos < list_indicators_web.size(); pos++) {

				KeyHolder keyHolder = new GeneratedKeyHolder();

				String sql_indi = "insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,"
						+ "					`indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,`user_id`,`dist_demogra_dtl_id`) values(?,"
						+ "							?,?,?,?,?,?,?,?,?,?,?,?,?)";

				final int i = pos;
				Form1BIndicatorsTableDataBean mybean = new Form1BIndicatorsTableDataBean();

				jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql_indi, Statement.RETURN_GENERATED_KEYS);
					String area_name = "";
					String indicator_name = "";

					for (int index = 0; index < getlist1.size(); index++) {

						Areas_of_Indicators_List temp_obj = getlist1.get(index);

						if (list_indicators_web.get(i).getAreaid().equals(temp_obj.getId())) {
							area_name = temp_obj.getArea_name();
						}
					}

					for (int index = 0; index < getlist2.size(); index++) {

						Menu_Area_Indicator_Object temp_obj = getlist2.get(index);

						if (list_indicators_web.get(i).getIndicatorid().equals(temp_obj.getIndicator_id())) {
							indicator_name = temp_obj.getIndicator_val();
						}
					}

					final String a_name = area_name;
					final String i_name = indicator_name;

					// ps.setString(1, "" + p_key);
					ps.setString(1, "" + list_indicators_web.get(i).getAreaid());
					mybean.setAreaid("" + list_indicators_web.get(i).getAreaid());

					ps.setString(2, "" + list_indicators_web.get(i).getIndicatorid());
					mybean.setIndicatorid("" + list_indicators_web.get(i).getIndicatorid());

					ps.setString(3, "" + "" + a_name);
					ps.setString(4, "" + "" + i_name);
					ps.setString(5, "" + list_indicators_web.get(i).getDatapresent());
					mybean.setDatapresent("" + list_indicators_web.get(i).getDatapresent());

					ps.setString(6, "" + list_indicators_web.get(i).getDatagap());
					mybean.setDatagap("" + list_indicators_web.get(i).getDatagap());

					ps.setString(7, "" + list_indicators_web.get(i).getDataexpected());
					mybean.setDataexpected("" + list_indicators_web.get(i).getDataexpected());

					ps.setString(8, "" + list_indicators_web.get(i).getSourceid());
					mybean.setSourceid("" + list_indicators_web.get(i).getSourceid());

					ps.setString(9, "" + list_indicators_web.get(i).getDistrict_id());
					mybean.setDatapresent("" + list_indicators_web.get(i).getDistrict_id());

					ps.setString(10, "" + list_indicators_web.get(i).getCycle_id());
					mybean.setDatapresent("" + list_indicators_web.get(i).getCycle_id());

					ps.setString(11, "" + list_indicators_web.get(i).getYear());
					mybean.setDatapresent("" + list_indicators_web.get(i).getYear());

					ps.setString(12, "" + formatedDateTime);
					mybean.setRecordcreated("" + formatedDateTime);

					ps.setString(13, "" + list_indicators_web.get(i).getUser_id());
					mybean.setUser_id("" + list_indicators_web.get(i).getUser_id());

					ps.setString(14, "" + list_indicators_web.get(i).getHsca_district_demographic_id());

					mybean.setHsca_district_demographic_id(
							"" + list_indicators_web.get(i).getHsca_district_demographic_id());

					return ps;
				}, keyHolder);

				mybean.setTheme(list_indicators_web.get(i).getTheme());
				mybean.setAuto_id("" + keyHolder.getKey().longValue());
				mybean.setCountry_id(list_indicators_web.get(i).getCountry_id());
				mybean.setProvince_id(list_indicators_web.get(i).getProvince_id());
				mybean.setDistrict_id(list_indicators_web.get(i).getDistrict_id());
				mybean.setCycle_id(list_indicators_web.get(i).getCycle_id());
				mybean.setYear(list_indicators_web.get(i).getYear());
				mybean.setTimestamp(formatedDateTime);
				mybean.setDatafrom("WEB");

				list_indicators_add.add(mybean);

			}

		} // list_indicators_web.size()

		response.setFormB(list_formB_add);
		response.setFormBngo(list_ngo_add);
		response.setFormBstakeholders(list_stakes_add);
		response.setFormBcoverageIndicators(list_indicators_add);
		response.setFormBinfra(list_infra_add);
		response.setFormBfinance(list_fina_add);
		response.setFormBsupplies(list_supp_add);
		response.setFormBtechnology(list_tech_add);
		response.setFormBhumanresource(list_hr_add);
		response.setError_code("200");
		response.setMessage("Sending Form1B Updated and Consumed Data");

		return response;
	}
	

	public SendAndroidForm1BSynchedDataBean retrieveForm1B_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(
			String country_id, String region_id, String state_id, String district_id, String user_id,String LoggedinUserId) {

		SendAndroidForm1BSynchedDataBean response = new SendAndroidForm1BSynchedDataBean();
		
		
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
			
			
			response.setFormBcoverageIndicators(new ArrayList<>());

			response.setError_code("200");
			response.setMessage("Sending Form B Data");
		}
		else {
			
		}
		
		
		String sql = "";
		
		
		if("0".equals(user_priv.getAll_districts())) {
			sql = "SELECT form1b.`completed`, form1b.`dist_demogra_dtl_id`,    form1b.`date_of_verification`,    form1b.`chairperson_of_meeting`,  "
					+ "  form1b.`chairperson_of_meeting_others`,    form1b.`filled_by`,    form1b.`total_area`,  "
					+ "  form1b.`total_area_demogra_id`,    form1b.`total_pop`,    form1b.`total_pop_demogra_id`, "
					+ "   form1b.`num_women_in_reproductive_age_15_49_y`,    form1b.`num_women_in_reproductive_age_15_49_y_source`,  "
					+ "  form1b.`num_child_under_5_y`,    form1b.`num_child_under_5_y_demogra_id`,    form1b.`rural_pop`,  "
					+ "  form1b.`rural_pop_demogra_id`,    form1b.`sc_pop`,    form1b.`sc_pop_demogra_id`,    form1b.`st_pop`,  "
					+ "  form1b.`st_pop_demogra_id`,    form1b.`pop_density`,    form1b.`pop_density_demogra_id`,    form1b.`total_literacy`, "
					+ "   form1b.`total_literacy_demogra_id`,    form1b.`fem_literacy`,    form1b.`fem_literacy_demogra_id`, "
					+ "   form1b.`district_id`,    form1b.`cycle_id`,    form1b.`financial_year`,    form1b.`user_id`,  "
					+ "  form1b.`record_created`,d.`country_id`,d.`state_id`,cs.`region_id`, form1bothers.`total_area_others` , "
					+ "  form1bothers.`total_pop_others`, form1bothers.`num_women_in_reproductive_others`, form1bothers.`no_of_child_under5_others`, "
					+ " form1bothers.`rural_pop_others`, form1bothers.`sc_pop_others`, form1bothers.`st_pop_others`, form1bothers.`pop_dens_others`, form1bothers.`tot_lit_others`, form1bothers.`female_lit_others`  FROM `hsca_district_demographic_dtls` form1b    left join `hsca_district_others` form1bothers on form1b.`dist_demogra_dtl_id`=form1bothers.`dist_demogra_dtl_id`  "
					+ "  left join  `district` d on form1b.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form1b.`financial_year` >= 2019";
		
		}
		else {
			sql = "SELECT form1b.`completed`, form1b.`dist_demogra_dtl_id`,    form1b.`date_of_verification`,    form1b.`chairperson_of_meeting`,  "
					+ "  form1b.`chairperson_of_meeting_others`,    form1b.`filled_by`,    form1b.`total_area`,  "
					+ "  form1b.`total_area_demogra_id`,    form1b.`total_pop`,    form1b.`total_pop_demogra_id`, "
					+ "   form1b.`num_women_in_reproductive_age_15_49_y`,    form1b.`num_women_in_reproductive_age_15_49_y_source`,  "
					+ "  form1b.`num_child_under_5_y`,    form1b.`num_child_under_5_y_demogra_id`,    form1b.`rural_pop`,  "
					+ "  form1b.`rural_pop_demogra_id`,    form1b.`sc_pop`,    form1b.`sc_pop_demogra_id`,    form1b.`st_pop`,  "
					+ "  form1b.`st_pop_demogra_id`,    form1b.`pop_density`,    form1b.`pop_density_demogra_id`,    form1b.`total_literacy`, "
					+ "   form1b.`total_literacy_demogra_id`,    form1b.`fem_literacy`,    form1b.`fem_literacy_demogra_id`, "
					+ "   form1b.`district_id`,    form1b.`cycle_id`,    form1b.`financial_year`,    form1b.`user_id`,  "
					+ "  form1b.`record_created`,d.`country_id`,d.`state_id`,cs.`region_id`, form1bothers.`total_area_others` , "
					+ "  form1bothers.`total_pop_others`, form1bothers.`num_women_in_reproductive_others`, form1bothers.`no_of_child_under5_others`, "
					+ " form1bothers.`rural_pop_others`, form1bothers.`sc_pop_others`, form1bothers.`st_pop_others`, form1bothers.`pop_dens_others`, form1bothers.`tot_lit_others`, form1bothers.`female_lit_others`  FROM `hsca_district_demographic_dtls` form1b    left join `hsca_district_others` form1bothers on form1b.`dist_demogra_dtl_id`=form1bothers.`dist_demogra_dtl_id`  "
					+ "  left join  `district` d on form1b.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form1b.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form1b.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1b.`financial_year` IN ("+user_priv.getAll_years()+");";
		
		}

		
		Object[] params = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BPrimaryTableDataBean> l = jdbcTemplate.query(sql, params, rs -> {

			List<Form1BPrimaryTableDataBean> list1 = new ArrayList<>();

			while (rs.next()) {

				try {
					Form1BPrimaryTableDataBean obj = new Form1BPrimaryTableDataBean();

					obj.setAuto_id(rs.getString("dist_demogra_dtl_id"));
					obj.setMeetingdate(rs.getString("date_of_verification"));
					obj.setMeetingvenue(rs.getString("filled_by"));
					obj.setChairpersonid(rs.getString("chairperson_of_meeting"));

					String check = "";
					if (rs.getString("chairperson_of_meeting_others") == null
							|| "null".equals(rs.getString("chairperson_of_meeting_others"))) {
						check = "";
					} else {
						check = rs.getString("chairperson_of_meeting_others");
					}
					obj.setOtherchairperson(check);
					obj.setTotal_area(rs.getString("total_area"));
					obj.setTotal_area_demogra_id(rs.getString("total_area_demogra_id"));
					obj.setTotal_area_others(rs.getString("total_area_others"));
					obj.setTotal_pop(rs.getString("total_pop"));
					obj.setTotal_pop_demogra_id(rs.getString("total_pop_demogra_id"));
					obj.setTotal_pop_others(rs.getString("total_pop_others"));
					obj.setNum_women_in_reproductive_age_15_49_y(rs.getString("num_women_in_reproductive_age_15_49_y"));
					obj.setNum_women_in_reproductive_age_15_49_y_source_id(
							rs.getString("num_women_in_reproductive_age_15_49_y_source"));
					obj.setNum_women_in_reproductive_others(rs.getString("num_women_in_reproductive_others"));
					obj.setNum_child_under_5_y(rs.getString("num_child_under_5_y"));
					obj.setNum_child_under_5_y_demogra_id(rs.getString("num_child_under_5_y_demogra_id"));
					obj.setNum_child_under_5_others(rs.getString("no_of_child_under5_others"));
					obj.setRural_pop(rs.getString("rural_pop"));
					obj.setRural_pop_demogra_id(rs.getString("rural_pop_demogra_id"));
					obj.setRural_pop_others(rs.getString("rural_pop_others"));
					obj.setSc_pop(rs.getString("sc_pop"));
					obj.setSc_pop_demogra_id(rs.getString("sc_pop_demogra_id"));
					obj.setSc_pop_others(rs.getString("sc_pop_others"));
					obj.setSt_pop(rs.getString("st_pop"));
					obj.setSt_pop_demogra_id(rs.getString("st_pop_demogra_id"));
					obj.setSt_pop_others(rs.getString("st_pop_others"));
					obj.setPop_density(rs.getString("pop_density"));
					obj.setPop_density_demogra_id(rs.getString("pop_density_demogra_id"));
					obj.setPop_dens_others(rs.getString("pop_dens_others"));
					obj.setTotal_literacy(rs.getString("total_literacy"));
					obj.setTotal_literacy_demogra_id(rs.getString("total_literacy_demogra_id"));
					obj.setTotal_lit_others(rs.getString("tot_lit_others"));
					obj.setFem_literacy(rs.getString("fem_literacy"));
					obj.setFem_literacy_demogra_id(rs.getString("fem_literacy_demogra_id"));
					obj.setFem_lit_others(rs.getString("female_lit_others"));
					obj.setCountry_id(rs.getString("country_id"));
					obj.setProvince_id(rs.getString("state_id"));
					obj.setDistrict_id(rs.getString("district_id"));
					obj.setCycle_id(rs.getString("cycle_id"));
					obj.setYear(rs.getString("financial_year"));
					obj.setUser_id(rs.getString("user_id"));
					obj.setRecordcreated(rs.getString("record_created"));
					obj.setCompleted(rs.getString("completed"));
					obj.setDatafrom("WEB");

					list1.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

//				obj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
//				obj.setDate_of_verification(rs.getString("date_of_verification"));
//				obj.setFilled_by(rs.getString("filled_by"));
//				obj.setVerified_by_name(rs.getString("chairperson_of_meeting"));
//				obj.setVerified_by_other_actual_name(rs.getString("chairperson_of_meeting_others"));
//				obj.setTotal_area(rs.getString("total_area"));
//				obj.setTotal_area_demogra_id(rs.getString("total_area_demogra_id"));
//				obj.setTotal_pop(rs.getString("total_pop"));
//				obj.setTotal_pop_demogra_id(rs.getString("total_pop_demogra_id"));
//				obj.setNum_women_in_reproductive_age_15_49_y(rs.getString("num_women_in_reproductive_age_15_49_y"));
//				obj.setNum_women_in_reproductive_age_15_49_y_source(
//						rs.getString("num_women_in_reproductive_age_15_49_y_source"));
//				obj.setNum_child_under_5_y(rs.getString("num_child_under_5_y"));
//				obj.setNum_child_under_5_y_demogra_id(rs.getString("num_child_under_5_y_demogra_id"));
//				obj.setRural_pop(rs.getString("rural_pop"));
//				obj.setRural_pop_demogra_id(rs.getString("rural_pop_demogra_id"));
//				obj.setSc_pop(rs.getString("sc_pop"));
//				obj.setSc_pop_demogra_id(rs.getString("sc_pop_demogra_id"));
//				obj.setSt_pop(rs.getString("st_pop"));
//				obj.setSt_pop_demogra_id(rs.getString("st_pop_demogra_id"));
//				obj.setPop_density(rs.getString("pop_density"));
//				obj.setPop_density_demogra_id(rs.getString("pop_density_demogra_id"));
//				obj.setTotal_literacy(rs.getString("total_literacy"));
//				obj.setTotal_literacy_demogra_id(rs.getString("total_literacy_demogra_id"));
//				obj.setFem_literacy(rs.getString("fem_literacy"));
//				obj.setFem_literacy_demogra_id(rs.getString("fem_literacy_demogra_id"));
//				obj.setDistrict(rs.getString("district_id"));
//				obj.setCycle(rs.getString("cycle_id"));
//				obj.setYear(rs.getString("financial_year"));
			}

			/* We can also return any variable-data from here but not used currently */
			return list1;
		});

		response.setFormB(l);
		
		String sql_key1 = "";
		
		
		if("0".equals(user_priv.getAll_districts())) {
			sql_key1 = "SELECT ngo.`key_ngo_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_ngo_info`,  "
					+ "  ngo.`key_ngo_source`,    ngo.`district_id`,    ngo.`cycle_id`,    ngo.`financial_year`, "
					+ "    ngo.`user_id`,    ngo.`record_created` , d.`district_id` as `dist2`, d.`state_id`,cs.`region_id`, "
					+ "d.`country_id`  FROM  `key_ngo_demogra` ngo left join  `district` d on ngo.district_id=d.district_id   "
					+ " left join   `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and  "
					+ "  cs.`region_id`=?  and  d.`country_id`=?   and  ngo.`financial_year` >= 2019; ";
			
		}
		else {
			sql_key1 = "SELECT ngo.`key_ngo_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_ngo_info`,  "
					+ "  ngo.`key_ngo_source`,    ngo.`district_id`,    ngo.`cycle_id`,    ngo.`financial_year`, "
					+ "    ngo.`user_id`,    ngo.`record_created` , d.`district_id` as `dist2`, d.`state_id`,cs.`region_id`, "
					+ "d.`country_id`  FROM  `key_ngo_demogra` ngo left join  `district` d on ngo.district_id=d.district_id   "
					+ " left join   `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and  "
					+ "  cs.`region_id`=?  and  d.`country_id`=?   and  ngo.`financial_year` >= 2019     and d.`district_id` IN ("+user_priv.getAll_districts()+")  and ngo.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and ngo.`financial_year` IN ("+user_priv.getAll_years()+");";
			
		}

		
		Object[] params_key1 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BNgoTableDataBean> list2 = jdbcTemplate.query(sql_key1, params_key1, rs -> {

			List<Form1BNgoTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {

				Form1BNgoTableDataBean obj = new Form1BNgoTableDataBean();

				obj.setAuto_id(rs.getString("key_ngo_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setNgoname(rs.getString("key_ngo_info"));
				obj.setNgocontactdetails(rs.getString("key_ngo_source"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				templist.add(obj);

//				Form1BEditKeyNgoDocumentsArray val = new Form1BEditKeyNgoDocumentsArray();
//
//				val.setNgo_name("" + rs.getString("key_ngo_info"));
//				val.setNgo_details("" + rs.getString("key_ngo_source"));
//				val.setPrimary_key("" + rs.getString("key_ngo_id")); 
//				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		

		response.setFormBngo(list2);
		
		String sql_key2 = "";
		if("0".equals(user_priv.getAll_districts())) {
			sql_key2 = "SELECT ngo.`key_pvt_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_pvt_info`,   "
					+ " ngo.`key_pvt_source`,    ngo.`district_id`,    ngo.`cycle_id`,    ngo.`financial_year`,  "
					+ "  ngo.`user_id`,    ngo.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
					+ "  d.`country_id` FROM `key_pvt_demogra`  ngo left join  `district` d on ngo.district_id=d.district_id   "
					+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and  "
					+ "  cs.`region_id`=?  and  d.`country_id`=?   and  ngo.`financial_year` >= 2019; ";
		}
		else {
			sql_key2 = "SELECT ngo.`key_pvt_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_pvt_info`,   "
					+ " ngo.`key_pvt_source`,    ngo.`district_id`,    ngo.`cycle_id`,    ngo.`financial_year`,  "
					+ "  ngo.`user_id`,    ngo.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
					+ "  d.`country_id` FROM `key_pvt_demogra`  ngo left join  `district` d on ngo.district_id=d.district_id   "
					+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and  "
					+ "  cs.`region_id`=?  and  d.`country_id`=?   and  ngo.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and ngo.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and ngo.`financial_year` IN ("+user_priv.getAll_years()+"); ";
		}

		
		
		Object[] params_key2 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BStakeHolderTableDataBean> list3 = jdbcTemplate.query(sql_key2, params_key2, rs -> {

			List<Form1BStakeHolderTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BStakeHolderTableDataBean val = new Form1BStakeHolderTableDataBean();

				val.setAuto_id(rs.getString("key_pvt_id"));
				val.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				val.setNgoname(rs.getString("key_pvt_info"));
				val.setNgocontactdetails(rs.getString("key_pvt_source"));
				val.setCountry_id(rs.getString("country_id"));
				val.setProvince_id(rs.getString("state_id"));
				val.setDistrict_id(rs.getString("district_id"));
				val.setCycle_id(rs.getString("cycle_id"));
				val.setYear(rs.getString("financial_year"));
				val.setUser_id(rs.getString("user_id"));
				val.setRecordcreated(rs.getString("record_created"));
				val.setTimestamp(rs.getString("record_created"));
				val.setDatafrom("WEB");

				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		response.setFormBstakeholders(list3);
		
		
		String sql4 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql4 = "SELECT infra.`infra_structure_details_id`,    infra.`dist_demogra_dtl_id`,  "
					+ "  infra.`details_infra`,    infra.`sanctioned_infra`,    infra.`available_functional_infra`, "
					+ "   infra.`gap_infra`,    infra.`district_id`,    infra.`cycle_id`,    infra.`financial_year`,  "
					+ "  infra.`user_id`,    infra.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
					+ "  d.`country_id` FROM  `hsca_theme_1_infrastructure`  infra left join  `district` d   "
					+ "  on infra.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "  where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   "
					+ "  and  infra.`financial_year` >= 2019;";
		}
		else {
			sql4 = "SELECT infra.`infra_structure_details_id`,    infra.`dist_demogra_dtl_id`,  "
					+ "  infra.`details_infra`,    infra.`sanctioned_infra`,    infra.`available_functional_infra`, "
					+ "   infra.`gap_infra`,    infra.`district_id`,    infra.`cycle_id`,    infra.`financial_year`,  "
					+ "  infra.`user_id`,    infra.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
					+ "  d.`country_id` FROM  `hsca_theme_1_infrastructure`  infra left join  `district` d   "
					+ "  on infra.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "  where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   "
					+ "  and  infra.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and infra.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and infra.`financial_year` IN ("+user_priv.getAll_years()+"); ";
		}

		
		
		Object[] params4 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list4 = jdbcTemplate.query(sql4, params4, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("infra_structure_details_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_infra"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_infra")) || rs.getString("sanctioned_infra") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_infra"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_infra"))
						|| rs.getString("available_functional_infra") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_infra"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_infra")) || rs.getString("gap_infra") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_infra"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBinfra(list4);
		
		String sql5 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql5 = "SELECT fin.`finance_id`,    fin.`dist_demogra_dtl_id`,    fin.`details_fina`,  "
					+ "  fin.`sanctioned_fina`,    fin.`available_functional_fina`,    fin.`gap_fina`, "
					+ "   fin.`district_id`,    fin.`cycle_id`,    fin.`financial_year`,    fin.`user_id`,  "
					+ "  fin.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` FROM  "
					+ "   `hsca_theme_1_gen_res_finance`  fin  left join  `district` d on fin.district_id=d.district_id   "
					+ "  left join    `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? "
					+ "  and  cs.`region_id`=?  and  d.`country_id`=?   and  fin.`financial_year` >= 2019;";
				
		}
		else {
			sql5 = "SELECT fin.`finance_id`,    fin.`dist_demogra_dtl_id`,    fin.`details_fina`,  "
					+ "  fin.`sanctioned_fina`,    fin.`available_functional_fina`,    fin.`gap_fina`, "
					+ "   fin.`district_id`,    fin.`cycle_id`,    fin.`financial_year`,    fin.`user_id`,  "
					+ "  fin.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` FROM  "
					+ "   `hsca_theme_1_gen_res_finance`  fin  left join  `district` d on fin.district_id=d.district_id   "
					+ "  left join    `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? "
					+ "  and  cs.`region_id`=?  and  d.`country_id`=?   and  fin.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and fin.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and fin.`financial_year` IN ("+user_priv.getAll_years()+"); ";
			
		}

		
		Object[] params5 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list5 = jdbcTemplate.query(sql5, params5, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("finance_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_fina"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_fina")) || rs.getString("sanctioned_fina") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_fina"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_fina"))
						|| rs.getString("available_functional_fina") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_fina"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_fina")) || rs.getString("gap_fina") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_fina"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBfinance(list5);
		
		
		String sql6 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql6 = "SELECT supp.`supplies_id`,    supp.`dist_demogra_dtl_id`,    supp.`details_supp`,  "
					+ "  supp.`sanctioned_supp`,    supp.`available_functional_supp`,    supp.`gap_supp`,   supp.`district_id`, "
					+ "   supp.`cycle_id`,    supp.`financial_year`,    supp.`user_id`,    supp.`record_created`,d.`district_id` as `dst2`,d.`state_id`, "
					+ " cs.`region_id`,d.`country_id`  FROM `hsca_theme_1_gen_res_supplies`   supp  "
					+ "  left join  `district` d on supp.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  supp.`financial_year` >= 2019";
			
		}
		else {
			sql6 = "SELECT supp.`supplies_id`,    supp.`dist_demogra_dtl_id`,    supp.`details_supp`,  "
					+ "  supp.`sanctioned_supp`,    supp.`available_functional_supp`,    supp.`gap_supp`,   supp.`district_id`, "
					+ "   supp.`cycle_id`,    supp.`financial_year`,    supp.`user_id`,    supp.`record_created`,d.`district_id` as `dst2`,d.`state_id`, "
					+ " cs.`region_id`,d.`country_id`  FROM `hsca_theme_1_gen_res_supplies`   supp  "
					+ "  left join  `district` d on supp.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  supp.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and supp.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and supp.`financial_year` IN ("+user_priv.getAll_years()+"); ";
			
		}

		
		Object[] params6 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list6 = jdbcTemplate.query(sql6, params6, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("supplies_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_supp"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_supp")) || rs.getString("sanctioned_supp") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_supp"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_supp"))
						|| rs.getString("available_functional_supp") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_supp"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_supp")) || rs.getString("gap_supp") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_supp"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBsupplies(list6);
		
		String sql7 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql7 = "SELECT tech.`technology_id`,    tech.`dist_demogra_dtl_id`,    tech.`details_tech`,    tech.`sanctioned_tech`,  "
					+ "  tech.`available_functional_tech`,   tech.`gap_tech`,    tech.`district_id`,    tech.`cycle_id`,    tech.`financial_year`, "
					+ "   tech.`user_id`,    tech.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
					+ "  FROM  `hsca_theme_1_gen_res_technology`  tech  left join  `district` d on tech.district_id=d.district_id   "
					+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and   "
					+ " cs.`region_id`=?  and  d.`country_id`=?   and  tech.`financial_year` >= 2019;";
			
		}
		else {
			sql7 = "SELECT tech.`technology_id`,    tech.`dist_demogra_dtl_id`,    tech.`details_tech`,    tech.`sanctioned_tech`,  "
					+ "  tech.`available_functional_tech`,   tech.`gap_tech`,    tech.`district_id`,    tech.`cycle_id`,    tech.`financial_year`, "
					+ "   tech.`user_id`,    tech.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
					+ "  FROM  `hsca_theme_1_gen_res_technology`  tech  left join  `district` d on tech.district_id=d.district_id   "
					+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and   "
					+ " cs.`region_id`=?  and  d.`country_id`=?   and  tech.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and tech.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and tech.`financial_year` IN ("+user_priv.getAll_years()+"); ";
			
		}

		
		
		Object[] params7 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list7 = jdbcTemplate.query(sql7, params7, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("technology_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_tech"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_tech")) || rs.getString("sanctioned_tech") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_tech"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_tech"))
						|| rs.getString("available_functional_tech") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_tech"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_tech")) || rs.getString("gap_tech") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_tech"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBtechnology(list7);
		
		String sql8 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql8 = "SELECT hr.`hr_id`,    hr.`dist_demogra_dtl_id`,    hr.`details_hr`,    hr.`sanctioned_hr`, "
					+ "  hr.`available_functional_hr`,   hr.`gap_hr`,    hr.`district_id`,    hr.`cycle_id`,    hr.`financial_year`,"
					+ "	hr.`user_id`,    hr.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
					+ "   FROM `hsca_theme_1_hr`  hr  left join  `district` d on hr.district_id=d.district_id  "
					+ "  left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and "
					+ "   cs.`region_id`=?  and  d.`country_id`=?   and  hr.`financial_year` >= 2019";

		}
		else {
			sql8 = "SELECT hr.`hr_id`,    hr.`dist_demogra_dtl_id`,    hr.`details_hr`,    hr.`sanctioned_hr`, "
					+ "  hr.`available_functional_hr`,   hr.`gap_hr`,    hr.`district_id`,    hr.`cycle_id`,    hr.`financial_year`,"
					+ "	hr.`user_id`,    hr.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
					+ "   FROM `hsca_theme_1_hr`  hr  left join  `district` d on hr.district_id=d.district_id  "
					+ "  left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and "
					+ "   cs.`region_id`=?  and  d.`country_id`=?   and  hr.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and hr.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and hr.`financial_year` IN ("+user_priv.getAll_years()+"); ";

		}

		
		Object[] params8 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list8 = jdbcTemplate.query(sql8, params8, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("hr_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_hr"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_hr")) || rs.getString("sanctioned_hr") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_hr"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_hr"))
						|| rs.getString("available_functional_hr") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_hr"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_hr")) || rs.getString("gap_hr") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_hr"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBhumanresource(list8);
		
		
		String sql10 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql10 = "SELECT indi.`id`,  indi.`dist_demogra_dtl_id` ,  iphs.`theme_name`, indi.`area_id`,   "
					+ " indi.`indicator_id`,    indi.`area_name`,    indi.`indicator_name`,    indi.`data`,    indi.`gap`,  "
					+ "  indi.`expected`,    indi.`source`,    indi.`district_id`,    indi.`cycle_id`,    indi.`year`, "
					+ "   indi.`record_created`,    indi.`user_id`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
					+ "  FROM `form1b_selected_indicators`  indi  left join `hsca_requirements_iphs` iphs on  "
					+ " iphs.dist_demogra_dtl_id=  indi.`dist_demogra_dtl_id` and iphs. `district_id`= indi.`district_id` and  "
					+ "  indi.`cycle_id`=iphs.`cycle_id`  left join  `district` d on indi.district_id=d.district_id  "
					+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and "
					+ "  cs.`region_id`=?  and  d.`country_id`=?   and  indi.`year` >= 2019;";
	
		}
		else {
			sql10 = "SELECT indi.`id`,  indi.`dist_demogra_dtl_id` ,  iphs.`theme_name`, indi.`area_id`,   "
					+ " indi.`indicator_id`,    indi.`area_name`,    indi.`indicator_name`,    indi.`data`,    indi.`gap`,  "
					+ "  indi.`expected`,    indi.`source`,    indi.`district_id`,    indi.`cycle_id`,    indi.`year`, "
					+ "   indi.`record_created`,    indi.`user_id`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
					+ "  FROM `form1b_selected_indicators`  indi  left join `hsca_requirements_iphs` iphs on  "
					+ " iphs.dist_demogra_dtl_id=  indi.`dist_demogra_dtl_id` and iphs. `district_id`= indi.`district_id` and  "
					+ "  indi.`cycle_id`=iphs.`cycle_id`  left join  `district` d on indi.district_id=d.district_id  "
					+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and "
					+ "  cs.`region_id`=?  and  d.`country_id`=?   and  indi.`year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and indi.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and indi.`year` IN ("+user_priv.getAll_years()+"); ";

		}

		
		Object[] params10 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BIndicatorsTableDataBean> list9 = jdbcTemplate.query(sql10, params10, rs -> {

			List<Form1BIndicatorsTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BIndicatorsTableDataBean obj = new Form1BIndicatorsTableDataBean();

				obj.setAuto_id(rs.getString("id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setTheme(rs.getString("theme_name"));
				obj.setAreaid(rs.getString("area_id"));
				obj.setIndicatorid(rs.getString("indicator_id"));
				obj.setSourceid(rs.getString("source"));
				obj.setDatapresent(rs.getString("data"));
				obj.setDataexpected(rs.getString("expected"));
				obj.setDatagap(rs.getString("gap"));
				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				templist.add(obj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		response.setFormBcoverageIndicators(list9);

		response.setError_code("200");
		response.setMessage("Sending Form B Data");

//		String sql3 = "SELECt * FROM `hsca_requirements_iphs` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
//		Object[] params3 = new Object[] { district_id, cycle_id, year };
//
//		List list_indi = new ArrayList();
//
//		jdbcTemplate.query(sql3, params3, rs -> {
//
//			while (rs.next()) {
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		return response;
	}


	public SendAndroidForm1BSynchedDataBean retrieveForm1B_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(
			String country_id, String region_id, String state_id, String district_id, String user_id) {

		SendAndroidForm1BSynchedDataBean response = new SendAndroidForm1BSynchedDataBean();

		String sql = "SELECT form1b.`completed`, form1b.`dist_demogra_dtl_id`,    form1b.`date_of_verification`,    form1b.`chairperson_of_meeting`,  "
				+ "  form1b.`chairperson_of_meeting_others`,    form1b.`filled_by`,    form1b.`total_area`,  "
				+ "  form1b.`total_area_demogra_id`,    form1b.`total_pop`,    form1b.`total_pop_demogra_id`, "
				+ "   form1b.`num_women_in_reproductive_age_15_49_y`,    form1b.`num_women_in_reproductive_age_15_49_y_source`,  "
				+ "  form1b.`num_child_under_5_y`,    form1b.`num_child_under_5_y_demogra_id`,    form1b.`rural_pop`,  "
				+ "  form1b.`rural_pop_demogra_id`,    form1b.`sc_pop`,    form1b.`sc_pop_demogra_id`,    form1b.`st_pop`,  "
				+ "  form1b.`st_pop_demogra_id`,    form1b.`pop_density`,    form1b.`pop_density_demogra_id`,    form1b.`total_literacy`, "
				+ "   form1b.`total_literacy_demogra_id`,    form1b.`fem_literacy`,    form1b.`fem_literacy_demogra_id`, "
				+ "   form1b.`district_id`,    form1b.`cycle_id`,    form1b.`financial_year`,    form1b.`user_id`,  "
				+ "  form1b.`record_created`,d.`country_id`,d.`state_id`,cs.`region_id`, form1bothers.`total_area_others` , "
				+ "  form1bothers.`total_pop_others`, form1bothers.`num_women_in_reproductive_others`, form1bothers.`no_of_child_under5_others`, "
				+ " form1bothers.`rural_pop_others`, form1bothers.`sc_pop_others`, form1bothers.`st_pop_others`, form1bothers.`pop_dens_others`, form1bothers.`tot_lit_others`, form1bothers.`female_lit_others`  FROM `hsca_district_demographic_dtls` form1b    left join `hsca_district_others` form1bothers on form1b.`dist_demogra_dtl_id`=form1bothers.`dist_demogra_dtl_id`  "
				+ "  left join  `district` d on form1b.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form1b.`financial_year` >= 2019";
		Object[] params = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BPrimaryTableDataBean> l = jdbcTemplate.query(sql, params, rs -> {

			List<Form1BPrimaryTableDataBean> list1 = new ArrayList<>();

			while (rs.next()) {

				try {
					Form1BPrimaryTableDataBean obj = new Form1BPrimaryTableDataBean();

					obj.setAuto_id(rs.getString("dist_demogra_dtl_id"));
					obj.setMeetingdate(rs.getString("date_of_verification"));
					obj.setMeetingvenue(rs.getString("filled_by"));
					obj.setChairpersonid(rs.getString("chairperson_of_meeting"));

					String check = "";
					if (rs.getString("chairperson_of_meeting_others") == null
							|| "null".equals(rs.getString("chairperson_of_meeting_others"))) {
						check = "";
					} else {
						check = rs.getString("chairperson_of_meeting_others");
					}
					obj.setOtherchairperson(check);
					obj.setTotal_area(rs.getString("total_area"));
					obj.setTotal_area_demogra_id(rs.getString("total_area_demogra_id"));
					obj.setTotal_area_others(rs.getString("total_area_others"));
					obj.setTotal_pop(rs.getString("total_pop"));
					obj.setTotal_pop_demogra_id(rs.getString("total_pop_demogra_id"));
					obj.setTotal_pop_others(rs.getString("total_pop_others"));
					obj.setNum_women_in_reproductive_age_15_49_y(rs.getString("num_women_in_reproductive_age_15_49_y"));
					obj.setNum_women_in_reproductive_age_15_49_y_source_id(
							rs.getString("num_women_in_reproductive_age_15_49_y_source"));
					obj.setNum_women_in_reproductive_others(rs.getString("num_women_in_reproductive_others"));
					obj.setNum_child_under_5_y(rs.getString("num_child_under_5_y"));
					obj.setNum_child_under_5_y_demogra_id(rs.getString("num_child_under_5_y_demogra_id"));
					obj.setNum_child_under_5_others(rs.getString("no_of_child_under5_others"));
					obj.setRural_pop(rs.getString("rural_pop"));
					obj.setRural_pop_demogra_id(rs.getString("rural_pop_demogra_id"));
					obj.setRural_pop_others(rs.getString("rural_pop_others"));
					obj.setSc_pop(rs.getString("sc_pop"));
					obj.setSc_pop_demogra_id(rs.getString("sc_pop_demogra_id"));
					obj.setSc_pop_others(rs.getString("sc_pop_others"));
					obj.setSt_pop(rs.getString("st_pop"));
					obj.setSt_pop_demogra_id(rs.getString("st_pop_demogra_id"));
					obj.setSt_pop_others(rs.getString("st_pop_others"));
					obj.setPop_density(rs.getString("pop_density"));
					obj.setPop_density_demogra_id(rs.getString("pop_density_demogra_id"));
					obj.setPop_dens_others(rs.getString("pop_dens_others"));
					obj.setTotal_literacy(rs.getString("total_literacy"));
					obj.setTotal_literacy_demogra_id(rs.getString("total_literacy_demogra_id"));
					obj.setTotal_lit_others(rs.getString("tot_lit_others"));
					obj.setFem_literacy(rs.getString("fem_literacy"));
					obj.setFem_literacy_demogra_id(rs.getString("fem_literacy_demogra_id"));
					obj.setFem_lit_others(rs.getString("female_lit_others"));
					obj.setCountry_id(rs.getString("country_id"));
					obj.setProvince_id(rs.getString("state_id"));
					obj.setDistrict_id(rs.getString("district_id"));
					obj.setCycle_id(rs.getString("cycle_id"));
					obj.setYear(rs.getString("financial_year"));
					obj.setUser_id(rs.getString("user_id"));
					obj.setRecordcreated(rs.getString("record_created"));
					obj.setCompleted(rs.getString("completed"));
					obj.setDatafrom("WEB");

					list1.add(obj);
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}

//				obj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
//				obj.setDate_of_verification(rs.getString("date_of_verification"));
//				obj.setFilled_by(rs.getString("filled_by"));
//				obj.setVerified_by_name(rs.getString("chairperson_of_meeting"));
//				obj.setVerified_by_other_actual_name(rs.getString("chairperson_of_meeting_others"));
//				obj.setTotal_area(rs.getString("total_area"));
//				obj.setTotal_area_demogra_id(rs.getString("total_area_demogra_id"));
//				obj.setTotal_pop(rs.getString("total_pop"));
//				obj.setTotal_pop_demogra_id(rs.getString("total_pop_demogra_id"));
//				obj.setNum_women_in_reproductive_age_15_49_y(rs.getString("num_women_in_reproductive_age_15_49_y"));
//				obj.setNum_women_in_reproductive_age_15_49_y_source(
//						rs.getString("num_women_in_reproductive_age_15_49_y_source"));
//				obj.setNum_child_under_5_y(rs.getString("num_child_under_5_y"));
//				obj.setNum_child_under_5_y_demogra_id(rs.getString("num_child_under_5_y_demogra_id"));
//				obj.setRural_pop(rs.getString("rural_pop"));
//				obj.setRural_pop_demogra_id(rs.getString("rural_pop_demogra_id"));
//				obj.setSc_pop(rs.getString("sc_pop"));
//				obj.setSc_pop_demogra_id(rs.getString("sc_pop_demogra_id"));
//				obj.setSt_pop(rs.getString("st_pop"));
//				obj.setSt_pop_demogra_id(rs.getString("st_pop_demogra_id"));
//				obj.setPop_density(rs.getString("pop_density"));
//				obj.setPop_density_demogra_id(rs.getString("pop_density_demogra_id"));
//				obj.setTotal_literacy(rs.getString("total_literacy"));
//				obj.setTotal_literacy_demogra_id(rs.getString("total_literacy_demogra_id"));
//				obj.setFem_literacy(rs.getString("fem_literacy"));
//				obj.setFem_literacy_demogra_id(rs.getString("fem_literacy_demogra_id"));
//				obj.setDistrict(rs.getString("district_id"));
//				obj.setCycle(rs.getString("cycle_id"));
//				obj.setYear(rs.getString("financial_year"));
			}

			/* We can also return any variable-data from here but not used currently */
			return list1;
		});

		response.setFormB(l);

		String sql_key1 = "SELECT ngo.`key_ngo_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_ngo_info`,  "
				+ "  ngo.`key_ngo_source`,    ngo.`district_id`,    ngo.`cycle_id`,    ngo.`financial_year`, "
				+ "    ngo.`user_id`,    ngo.`record_created` , d.`district_id` as `dist2`, d.`state_id`,cs.`region_id`, "
				+ "d.`country_id`  FROM  `key_ngo_demogra` ngo left join  `district` d on ngo.district_id=d.district_id   "
				+ " left join   `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and  "
				+ "  cs.`region_id`=?  and  d.`country_id`=?   and  ngo.`financial_year` >= 2019; ";
		Object[] params_key1 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BNgoTableDataBean> list2 = jdbcTemplate.query(sql_key1, params_key1, rs -> {

			List<Form1BNgoTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {

				Form1BNgoTableDataBean obj = new Form1BNgoTableDataBean();

				obj.setAuto_id(rs.getString("key_ngo_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setNgoname(rs.getString("key_ngo_info"));
				obj.setNgocontactdetails(rs.getString("key_ngo_source"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				templist.add(obj);

//				Form1BEditKeyNgoDocumentsArray val = new Form1BEditKeyNgoDocumentsArray();
//
//				val.setNgo_name("" + rs.getString("key_ngo_info"));
//				val.setNgo_details("" + rs.getString("key_ngo_source"));
//				val.setPrimary_key("" + rs.getString("key_ngo_id")); 
//				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		

		response.setFormBngo(list2);

		String sql_key2 = "SELECT ngo.`key_pvt_id`,    ngo.`dist_demogra_dtl_id`,    ngo.`key_pvt_info`,   "
				+ " ngo.`key_pvt_source`,    ngo.`district_id`,    ngo.`cycle_id`,    ngo.`financial_year`,  "
				+ "  ngo.`user_id`,    ngo.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
				+ "  d.`country_id` FROM `key_pvt_demogra`  ngo left join  `district` d on ngo.district_id=d.district_id   "
				+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and  "
				+ "  cs.`region_id`=?  and  d.`country_id`=?   and  ngo.`financial_year` >= 2019; ";
		Object[] params_key2 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BStakeHolderTableDataBean> list3 = jdbcTemplate.query(sql_key2, params_key2, rs -> {

			List<Form1BStakeHolderTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BStakeHolderTableDataBean val = new Form1BStakeHolderTableDataBean();

				val.setAuto_id(rs.getString("key_pvt_id"));
				val.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				val.setNgoname(rs.getString("key_pvt_info"));
				val.setNgocontactdetails(rs.getString("key_pvt_source"));
				val.setCountry_id(rs.getString("country_id"));
				val.setProvince_id(rs.getString("state_id"));
				val.setDistrict_id(rs.getString("district_id"));
				val.setCycle_id(rs.getString("cycle_id"));
				val.setYear(rs.getString("financial_year"));
				val.setUser_id(rs.getString("user_id"));
				val.setRecordcreated(rs.getString("record_created"));
				val.setTimestamp(rs.getString("record_created"));
				val.setDatafrom("WEB");

				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		response.setFormBstakeholders(list3);

		String sql4 = "SELECT infra.`infra_structure_details_id`,    infra.`dist_demogra_dtl_id`,  "
				+ "  infra.`details_infra`,    infra.`sanctioned_infra`,    infra.`available_functional_infra`, "
				+ "   infra.`gap_infra`,    infra.`district_id`,    infra.`cycle_id`,    infra.`financial_year`,  "
				+ "  infra.`user_id`,    infra.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`, "
				+ "  d.`country_id` FROM  `hsca_theme_1_infrastructure`  infra left join  `district` d   "
				+ "  on infra.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "  where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   "
				+ "  and  infra.`financial_year` >= 2019;";
		Object[] params4 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list4 = jdbcTemplate.query(sql4, params4, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("infra_structure_details_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_infra"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_infra")) || rs.getString("sanctioned_infra") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_infra"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_infra"))
						|| rs.getString("available_functional_infra") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_infra"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_infra")) || rs.getString("gap_infra") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_infra"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBinfra(list4);

		String sql5 = "SELECT fin.`finance_id`,    fin.`dist_demogra_dtl_id`,    fin.`details_fina`,  "
				+ "  fin.`sanctioned_fina`,    fin.`available_functional_fina`,    fin.`gap_fina`, "
				+ "   fin.`district_id`,    fin.`cycle_id`,    fin.`financial_year`,    fin.`user_id`,  "
				+ "  fin.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` FROM  "
				+ "   `hsca_theme_1_gen_res_finance`  fin  left join  `district` d on fin.district_id=d.district_id   "
				+ "  left join    `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? "
				+ "  and  cs.`region_id`=?  and  d.`country_id`=?   and  fin.`financial_year` >= 2019;";
		Object[] params5 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list5 = jdbcTemplate.query(sql5, params5, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("finance_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_fina"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_fina")) || rs.getString("sanctioned_fina") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_fina"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_fina"))
						|| rs.getString("available_functional_fina") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_fina"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_fina")) || rs.getString("gap_fina") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_fina"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBfinance(list5);

		String sql6 = "SELECT supp.`supplies_id`,    supp.`dist_demogra_dtl_id`,    supp.`details_supp`,  "
				+ "  supp.`sanctioned_supp`,    supp.`available_functional_supp`,    supp.`gap_supp`,   supp.`district_id`, "
				+ "   supp.`cycle_id`,    supp.`financial_year`,    supp.`user_id`,    supp.`record_created`,d.`district_id` as `dst2`,d.`state_id`, "
				+ " cs.`region_id`,d.`country_id`  FROM `hsca_theme_1_gen_res_supplies`   supp  "
				+ "  left join  `district` d on supp.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  supp.`financial_year` >= 2019";
		Object[] params6 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list6 = jdbcTemplate.query(sql6, params6, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("supplies_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_supp"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_supp")) || rs.getString("sanctioned_supp") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_supp"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_supp"))
						|| rs.getString("available_functional_supp") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_supp"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_supp")) || rs.getString("gap_supp") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_supp"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBsupplies(list6);

		String sql7 = "SELECT tech.`technology_id`,    tech.`dist_demogra_dtl_id`,    tech.`details_tech`,    tech.`sanctioned_tech`,  "
				+ "  tech.`available_functional_tech`,   tech.`gap_tech`,    tech.`district_id`,    tech.`cycle_id`,    tech.`financial_year`, "
				+ "   tech.`user_id`,    tech.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
				+ "  FROM  `hsca_theme_1_gen_res_technology`  tech  left join  `district` d on tech.district_id=d.district_id   "
				+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and   "
				+ " cs.`region_id`=?  and  d.`country_id`=?   and  tech.`financial_year` >= 2019;";
		Object[] params7 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list7 = jdbcTemplate.query(sql7, params7, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("technology_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_tech"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_tech")) || rs.getString("sanctioned_tech") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_tech"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_tech"))
						|| rs.getString("available_functional_tech") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_tech"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_tech")) || rs.getString("gap_tech") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_tech"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBtechnology(list7);

		String sql8 = "SELECT hr.`hr_id`,    hr.`dist_demogra_dtl_id`,    hr.`details_hr`,    hr.`sanctioned_hr`, "
				+ "  hr.`available_functional_hr`,   hr.`gap_hr`,    hr.`district_id`,    hr.`cycle_id`,    hr.`financial_year`,"
				+ "	hr.`user_id`,    hr.`record_created` ,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
				+ "   FROM `hsca_theme_1_hr`  hr  left join  `district` d on hr.district_id=d.district_id  "
				+ "  left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and "
				+ "   cs.`region_id`=?  and  d.`country_id`=?   and  hr.`financial_year` >= 2019";

		Object[] params8 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BdocumentsBean> list8 = jdbcTemplate.query(sql8, params8, rs -> {

			List<Form1BdocumentsBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form1BdocumentsBean obj = new Form1BdocumentsBean();

				obj.setAuto_id(rs.getString("hr_id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDetails(rs.getString("details_hr"));

				String sanc = "";
				String avai = "";
				String gap = "";

				if ("".equals(rs.getString("sanctioned_hr")) || rs.getString("sanctioned_hr") == null) {
					sanc = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("sanctioned_hr"));
						int int_val = (int) d;
						sanc = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						sanc = "0";
					}
				}

				if ("".equals(rs.getString("available_functional_hr"))
						|| rs.getString("available_functional_hr") == null) {
					avai = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("available_functional_hr"));
						int int_val = (int) d;
						avai = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						avai = "0";
					}
				}

				if ("".equals(rs.getString("gap_hr")) || rs.getString("gap_hr") == null) {
					gap = "0";
				} else {
					try {
						double d = Double.parseDouble(rs.getString("gap_hr"));
						int int_val = (int) d;
						gap = "" + int_val;
					} catch (Exception e) {
						// TODO: handle exception
						gap = "0";
					}
				}

				obj.setSanctioned(sanc);
				obj.setAvailable(avai);
				obj.setGap(gap);
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

		response.setFormBhumanresource(list8);

		String sql10 = "SELECT indi.`id`,  indi.`dist_demogra_dtl_id` ,  iphs.`theme_name`, indi.`area_id`,   "
				+ " indi.`indicator_id`,    indi.`area_name`,    indi.`indicator_name`,    indi.`data`,    indi.`gap`,  "
				+ "  indi.`expected`,    indi.`source`,    indi.`district_id`,    indi.`cycle_id`,    indi.`year`, "
				+ "   indi.`record_created`,    indi.`user_id`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id` "
				+ "  FROM `form1b_selected_indicators`  indi  left join `hsca_requirements_iphs` iphs on  "
				+ " iphs.dist_demogra_dtl_id=  indi.`dist_demogra_dtl_id` and iphs. `district_id`= indi.`district_id` and  "
				+ "  indi.`cycle_id`=iphs.`cycle_id`  left join  `district` d on indi.district_id=d.district_id  "
				+ " left join  `country_states` cs on d.state_id=cs.cs_id    where d.`district_id`=? and  d.`state_id`=? and "
				+ "  cs.`region_id`=?  and  d.`country_id`=?   and  indi.`year` >= 2019;";

		Object[] params10 = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1BIndicatorsTableDataBean> list9 = jdbcTemplate.query(sql10, params10, rs -> {

			List<Form1BIndicatorsTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BIndicatorsTableDataBean obj = new Form1BIndicatorsTableDataBean();

				obj.setAuto_id(rs.getString("id"));
				obj.setHsca_district_demographic_id(rs.getString("dist_demogra_dtl_id"));
				obj.setTheme(rs.getString("theme_name"));
				obj.setAreaid(rs.getString("area_id"));
				obj.setIndicatorid(rs.getString("indicator_id"));
				obj.setSourceid(rs.getString("source"));
				obj.setDatapresent(rs.getString("data"));
				obj.setDataexpected(rs.getString("expected"));
				obj.setDatagap(rs.getString("gap"));
				obj.setCountry_id(rs.getString("country_id"));
				obj.setProvince_id(rs.getString("state_id"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setDatafrom("WEB");

				templist.add(obj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		response.setFormBcoverageIndicators(list9);

		response.setError_code("200");
		response.setMessage("Sending Form B Data");

//		String sql3 = "SELECt * FROM `hsca_requirements_iphs` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
//		Object[] params3 = new Object[] { district_id, cycle_id, year };
//
//		List list_indi = new ArrayList();
//
//		jdbcTemplate.query(sql3, params3, rs -> {
//
//			while (rs.next()) {
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		return response;
	}

	public Form1BView retrieveForm1BDetails(String district_id, String cycle_id, String year, String user_id) {

		Form1BView obj = new Form1BView();

//		String sql1 = "INSERT INTO `hsca_district_demographic_dtls`(`date_of_verification`, `filled_by`,`chairperson_of_meeting`,"
//				+ " `total_area`, `total_area_demogra_id`, `total_pop`, `total_pop_demogra_id`,"
//				+ " `num_women_in_reproductive_age_15_49_y`, `num_women_in_reproductive_age_15_49_y_source`, "
//				+ "`num_child_under_5_y`, `num_child_under_5_y_demogra_id`, `rural_pop`, `rural_pop_demogra_id`,"
//				+ " `sc_pop`, `sc_pop_demogra_id`, `st_pop`, `st_pop_demogra_id`, `pop_density`, "
//				+ "`pop_density_demogra_id`, `total_literacy`, `total_literacy_demogra_id`,"
//				+ " `fem_literacy`, `fem_literacy_demogra_id`, `district_id`, `cycle_id`, `financial_year`,"
//				+ " `user_id`,`record_created`)" + " VALUES (?,?,?,?," + "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?,"
//				+ "?,?,?," + "?,?,?," + "?,?,?," + "?,?,?)";

		String sql = "SELECt * FROM `hsca_district_demographic_dtls` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {
				obj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
				obj.setDate_of_verification(rs.getString("date_of_verification"));
				obj.setFilled_by(rs.getString("filled_by"));
				obj.setVerified_by_name(rs.getString("chairperson_of_meeting"));
				obj.setVerified_by_other_actual_name(rs.getString("chairperson_of_meeting_others"));
				obj.setTotal_area(rs.getString("total_area"));
				obj.setTotal_area_demogra_id(rs.getString("total_area_demogra_id"));
				obj.setTotal_pop(rs.getString("total_pop"));
				obj.setTotal_pop_demogra_id(rs.getString("total_pop_demogra_id"));
				obj.setNum_women_in_reproductive_age_15_49_y(rs.getString("num_women_in_reproductive_age_15_49_y"));
				obj.setNum_women_in_reproductive_age_15_49_y_source(
						rs.getString("num_women_in_reproductive_age_15_49_y_source"));
				obj.setNum_child_under_5_y(rs.getString("num_child_under_5_y"));
				obj.setNum_child_under_5_y_demogra_id(rs.getString("num_child_under_5_y_demogra_id"));
				obj.setRural_pop(rs.getString("rural_pop"));
				obj.setRural_pop_demogra_id(rs.getString("rural_pop_demogra_id"));
				obj.setSc_pop(rs.getString("sc_pop"));
				obj.setSc_pop_demogra_id(rs.getString("sc_pop_demogra_id"));
				obj.setSt_pop(rs.getString("st_pop"));
				obj.setSt_pop_demogra_id(rs.getString("st_pop_demogra_id"));
				obj.setPop_density(rs.getString("pop_density"));
				obj.setPop_density_demogra_id(rs.getString("pop_density_demogra_id"));
				obj.setTotal_literacy(rs.getString("total_literacy"));
				obj.setTotal_literacy_demogra_id(rs.getString("total_literacy_demogra_id"));
				obj.setFem_literacy(rs.getString("fem_literacy"));
				obj.setFem_literacy_demogra_id(rs.getString("fem_literacy_demogra_id"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setCompleted(rs.getString("completed"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		String sql2 = "INSERT INTO `hsca_district_others`(`dist_demogra_dtl_id`, `total_area_others`,"
//				+ " `total_pop_others`, `num_women_in_reproductive_others`, `no_of_child_under5_others`,"
//				+ " `rural_pop_others`,`sc_pop_others`, `st_pop_others`, `pop_dens_others`, `tot_lit_others`, "
//				+ "`female_lit_others`,  `district_id`, `cycle_id`, `financial_year`, `user_id`, "
//				+ "`record_created`) VALUES (?,?,?," + "?,?,?," + "?,?, ?,?,?," + "?,?,?,?,?)";

		String sql2 = "SELECt * FROM `hsca_district_others` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params2 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql2, params2, rs -> {

			while (rs.next()) {

				obj.setTotal_area_others(rs.getString("total_area_others"));
				obj.setTotal_pop_others(rs.getString("total_pop_others"));
				obj.setNum_women_in_reproductive_others(rs.getString("num_women_in_reproductive_others"));
				obj.setNo_of_child_under5_others(rs.getString("no_of_child_under5_others"));
				obj.setRural_pop_others(rs.getString("rural_pop_others"));
				obj.setSc_pop_others(rs.getString("sc_pop_others"));
				obj.setSt_pop_others(rs.getString("st_pop_others"));
				obj.setPop_dens_others(rs.getString("pop_dens_others"));
				obj.setTot_lit_others(rs.getString("tot_lit_others"));
				obj.setFemale_lit_others(rs.getString("female_lit_others"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		/*---------------------------------------------------------*/

		String sql_key1 = "SELECT `key_ngo_id`,  `dist_demogra_dtl_id`, `key_ngo_info`,  `key_ngo_source`, `district_id`, `cycle_id`,  `financial_year`,  `user_id`, `record_created` FROM `key_ngo_demogra` where `district_id`=? and `cycle_id`=? and `financial_year`=? ";
		Object[] params_key1 = new Object[] { district_id, cycle_id, year };

		List<Form1BEditKeyNgoDocumentsArray> keyngo_arr = jdbcTemplate.query(sql_key1, params_key1, rs -> {

			List<Form1BEditKeyNgoDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BEditKeyNgoDocumentsArray val = new Form1BEditKeyNgoDocumentsArray();

				val.setNgo_name("" + rs.getString("key_ngo_info"));
				val.setNgo_details("" + rs.getString("key_ngo_source"));
				val.setPrimary_key("" + rs.getString("key_ngo_id"));
				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setKey_ngo_info_array(keyngo_arr);

		String sql_key2 = "SELECT `key_pvt_id`,  `dist_demogra_dtl_id`,  `key_pvt_info`, `key_pvt_source`,  `district_id`, `cycle_id`, `financial_year`,  `user_id`,  `record_created` FROM `key_pvt_demogra` where `district_id`=? and `cycle_id`=? and `financial_year`=? ";
		Object[] params_key2 = new Object[] { district_id, cycle_id, year };

		List<Form1BEditKeyStakeholderDocumentsArray> keystake_arr = jdbcTemplate.query(sql_key2, params_key2, rs -> {

			List<Form1BEditKeyStakeholderDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BEditKeyStakeholderDocumentsArray val = new Form1BEditKeyStakeholderDocumentsArray();

				val.setStakeholder_name("" + rs.getString("key_pvt_info"));
				val.setContact_details("" + rs.getString("key_pvt_source"));
				val.setPrimary_key("" + rs.getString("key_pvt_id"));
				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setKey_ngo_source_array(keystake_arr);

		/*--------------------------------------------------------*/

//		String sql3 = "INSERT INTO `hsca_requirements_iphs`( `dist_demogra_dtl_id`, `theme_name`, "
//				+ "`coverage_indicators`, `source`, `data_mcts`, "
//				+ "`gap_hmis`, `district_id`, `cycle_id`, `financial_year`, "
//				+ "`user_id`, `record_created`, `ci_sl_no`,`expected_status`) VALUES (?,?," + "?,?, ?,?,?,?," + "?,?,?,?,?)";

		String sql3 = "SELECt * FROM `hsca_requirements_iphs` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params3 = new Object[] { district_id, cycle_id, year };

		List list_indi = new ArrayList();

		jdbcTemplate.query(sql3, params3, rs -> {

			while (rs.next()) {

				obj.setIphs_theme_name(rs.getString("theme_name"));
				obj.setIphs_coverage_indicators(rs.getString("coverage_indicators"));
				obj.setIphs_source(rs.getString("source"));
				obj.setIphs_data(rs.getString("data_mcts"));
				obj.setIphs_gap_hmis(rs.getString("gap_hmis"));
				obj.setTheme_id(rs.getString("requirements_id"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		String sql4 = "insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id,details_infra,sanctioned_infra,"
//				+ "available_functional_infra,gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
//				+ ") values (?,?,?, " + "?,?,?,?,?,?,?)";

		String sql4 = "SELECt * FROM `hsca_theme_1_infrastructure` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params4 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql4, params4, rs -> {

			while (rs.next()) {

				obj.setDetails_infra(rs.getString("details_infra"));
				obj.setSanctioned_infra(rs.getString("sanctioned_infra"));
				obj.setAvailable_functional_infra(rs.getString("available_functional_infra"));
				obj.setGap_infra(rs.getString("gap_infra"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//String sql5 = "insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
//				+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
//				+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)";

		String sql5 = "SELECt * FROM `hsca_theme_1_gen_res_finance` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params5 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql5, params5, rs -> {

			while (rs.next()) {

				obj.setDetails_fina(rs.getString("details_fina"));
				obj.setSanctioned_fina(rs.getString("sanctioned_fina"));
				obj.setAvailable_functional_fina(rs.getString("available_functional_fina"));
				obj.setGap_fina(rs.getString("gap_fina"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		String sql6 = "insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
//				+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
//				+ "cycle_id,financial_year," + "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)";

		String sql6 = "SELECt * FROM `hsca_theme_1_gen_res_supplies` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params6 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql6, params6, rs -> {

			while (rs.next()) {

				obj.setDetails_supp(rs.getString("details_supp"));
				obj.setSanctioned_supp(rs.getString("sanctioned_supp"));
				obj.setAvailable_functional_supp(rs.getString("available_functional_supp"));
				obj.setGap_supp(rs.getString("gap_supp"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		String sql7 = "insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
//				+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
//				+ "user_id,record_created) values (" + "?,"
//				+ "?,?,?,?,?,?,?,"
//				+ "?,?)";

		String sql7 = "SELECt * FROM `hsca_theme_1_gen_res_technology` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params7 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql7, params7, rs -> {

			while (rs.next()) {

				obj.setDetails_tech(rs.getString("details_tech"));
				obj.setSanctioned_tech(rs.getString("sanctioned_tech"));
				obj.setAvailable_functional_tech(rs.getString("available_functional_tech"));
				obj.setGap_tech(rs.getString("gap_tech"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		
//		String sql8 = "insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
//				+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
//				+ "user_id,record_created) values (?,"
//				+ "?,?,?,?,?,?,?,"
//				+ "?,?)";

		String sql8 = "SELECt * FROM `hsca_theme_1_hr` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params8 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql8, params8, rs -> {

			while (rs.next()) {

				obj.setDetails_hr(rs.getString("details_hr"));
				obj.setSanctioned_hr(rs.getString("sanctioned_hr"));
				obj.setAvailable_functional_hr(rs.getString("available_functional_hr"));
				obj.setGap_hr(rs.getString("gap_hr"));
				obj.setUserid(rs.getString("user_id"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		// SELECT a.id as `area_id`,i.indicator_name,a.area_name,i.id as `indicator_id`
		// FROM spreadcr_diph_ethiopia.indicators i left join area a on i.area_id=a.id
		// String sql9 = "select a.id as
		// `area_id`,a.area_name,i.indicator_name,map.indicator_id from
		// area_indicator_mapping map left join area a on map.area_id = a.id left join
		// indicators i on map.indicator_id=i.id;";

		String sql9 = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,i.id as `indicator_id`,i.definition FROM indicators i left join area a on i.area_id=a.id";
		Object[] params9 = new Object[] {};

		List<Area_Indicator_Object> getlist = jdbcTemplate.query(sql9, params9, rs -> {

			List<Area_Indicator_Object> templist = new ArrayList<>();
			while (rs.next()) {
				Area_Indicator_Object val = new Area_Indicator_Object();

				val.setArea_id("" + rs.getString("area_id"));
				val.setArea_name("" + rs.getString("area_name"));
				val.setIndicator_val("" + rs.getString("indicator_name"));
				val.setIndicator_id("" + rs.getString("indicator_id"));
				val.setIndicator_desc(""+rs.getString("definition"));  
				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setAreas_Id_Indicators_map_list(getlist);

		//
//		Areas_Id_Indicators_map ob = new Areas_Id_Indicators_map();
//		
//		List<Areas_Id_Indicators_map> mylist = new ArrayList<>();
//		mylist.add(ob);
//		HashMap<String,List<String>> innermap = new HashMap<>();
//		
//		
//		
//		innermap.put(""+rs.getString("area_name"),"");
//		//innermap.put(""+rs.getString("area_name"), ""+rs.getString("indicator_name"));
//		
//		HashMap<String,HashMap<String,List<String>>> mymap = new HashMap<>();
//		mymap.put(""+rs.getString("area_id"), innermap);
//		
//		ob.setMapdata(mymap);
//		obj.setAreas_Id_Indicators_map_list(mylist);

//		insert into form1b_selected_indicators(`area_id`,`indicator_id`,`area_name`,
//		`indicator_name`,`data`,`gap`,`expected`,`source`,`district_id`,`cycle_id`,`year`,`record_created`,`user_id`) values(?,
//				?,?,?,?,?,?,?,?,?,?,?,?);

		String sql10 = "SELECT form.*,indi.definition FROM form1b_selected_indicators form inner join indicators indi on form.indicator_id=indi.id  where form.`district_id`=? and form.`cycle_id`=? and form.`year`=?";
		Object[] params10 = new Object[] { district_id, cycle_id, year };

		List<Form1BSaveIndicatorsModel> l_temp = jdbcTemplate.query(sql10, params10, rs -> {

			List<Form1BSaveIndicatorsModel> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BSaveIndicatorsModel val = new Form1BSaveIndicatorsModel();

				val.setArea_id("" + rs.getString("area_id"));
				val.setArea_name("" + rs.getString("area_name"));
				val.setIndicator_val("" + rs.getString("indicator_name"));
				val.setIndicator_id("" + rs.getString("indicator_id"));
				val.setData("" + rs.getString("data"));
				val.setGap("" + rs.getString("gap"));
				val.setExpected("" + rs.getString("expected"));
				val.setSource("" + rs.getString("source"));
				val.setIndicator_desc("" + rs.getString("definition"));

				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setTotal_coverage_indi(l_temp);

//		"insert into hsca_theme_1_infrastructure(dist_demogra_dtl_id, details_infra, sanctioned_infra,"
//		+ "		available_functional_infra, gap_infra,district_id,cycle_id,financial_year,user_id,record_created "
//		+ "		) values (?,?,?,?,?,?,?,?,?,?)"

		String sql11 = "SELECT * FROM hsca_theme_1_infrastructure  where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params11 = new Object[] { district_id, cycle_id, year };

		List<Form1BSaveDocumentsArray> infra_list = jdbcTemplate.query(sql11, params11, rs -> {

			List<Form1BSaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BSaveDocumentsArray tempobj = new Form1BSaveDocumentsArray();

				tempobj.setDocument_details(rs.getString("details_infra"));
				tempobj.setDocument_sanctioned(rs.getString("sanctioned_infra"));
				tempobj.setDocument_available_functional(rs.getString("available_functional_infra"));
				tempobj.setDocument_gap(rs.getString("gap_infra"));
				tempobj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
				tempobj.setPrimary_key(rs.getString("infra_structure_details_id"));
				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setInfra_array(infra_list);

//		"insert into hsca_theme_1_gen_res_finance(dist_demogra_dtl_id,details_fina,sanctioned_fina,"
//		+ "available_functional_fina,gap_fina,district_id,cycle_id,financial_year,user_id,"
//		+ "record_created) values (?,?,?," + "?,?,?,?,?,?,?)"

		String sql12 = "SELECT * FROM hsca_theme_1_gen_res_finance  where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params12 = new Object[] { district_id, cycle_id, year };

		List<Form1BSaveDocumentsArray> fin_list = jdbcTemplate.query(sql12, params12, rs -> {

			List<Form1BSaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BSaveDocumentsArray tempobj = new Form1BSaveDocumentsArray();

				tempobj.setDocument_details(rs.getString("details_fina"));
				tempobj.setDocument_sanctioned(rs.getString("sanctioned_fina"));
				tempobj.setDocument_available_functional(rs.getString("available_functional_fina"));
				tempobj.setDocument_gap(rs.getString("gap_fina"));
				tempobj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
				tempobj.setPrimary_key(rs.getString("finance_id"));
				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setFina_array(fin_list);

//		"insert into hsca_theme_1_gen_res_supplies(dist_demogra_dtl_id,"
//		+ "details_supp,sanctioned_supp,available_functional_supp,gap_supp,district_id,"
//		+ "cycle_id,financial_year," + "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)"

		String sql13 = "SELECT * FROM hsca_theme_1_gen_res_supplies  where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params13 = new Object[] { district_id, cycle_id, year };

		List<Form1BSaveDocumentsArray> supp_list = jdbcTemplate.query(sql13, params13, rs -> {

			List<Form1BSaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BSaveDocumentsArray tempobj = new Form1BSaveDocumentsArray();

				tempobj.setDocument_details(rs.getString("details_supp"));
				tempobj.setDocument_sanctioned(rs.getString("sanctioned_supp"));
				tempobj.setDocument_available_functional(rs.getString("available_functional_supp"));
				tempobj.setDocument_gap(rs.getString("gap_supp"));
				tempobj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
				tempobj.setPrimary_key(rs.getString("supplies_id"));
				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setSupp_array(supp_list);

//		insert into hsca_theme_1_gen_res_technology(dist_demogra_dtl_id,"
//				+ "details_tech,sanctioned_tech,available_functional_tech,gap_tech,district_id,cycle_id,financial_year,"
//				+ "user_id,record_created) values (" + "?," + "?,?,?,?,?,?,?," + "?,?)

		String sql14 = "SELECT * FROM hsca_theme_1_gen_res_technology  where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params14 = new Object[] { district_id, cycle_id, year };

		List<Form1BSaveDocumentsArray> tech_list = jdbcTemplate.query(sql14, params14, rs -> {

			List<Form1BSaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BSaveDocumentsArray tempobj = new Form1BSaveDocumentsArray();

				tempobj.setDocument_details(rs.getString("details_tech"));
				tempobj.setDocument_sanctioned(rs.getString("sanctioned_tech"));
				tempobj.setDocument_available_functional(rs.getString("available_functional_tech"));
				tempobj.setDocument_gap(rs.getString("gap_tech"));
				tempobj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
				tempobj.setPrimary_key(rs.getString("technology_id"));
				templist.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setTech_array(tech_list);

//		"insert into hsca_theme_1_hr(dist_demogra_dtl_id,"
//		+ "details_hr,sanctioned_hr,available_functional_hr,gap_hr,district_id,cycle_id,financial_year,"
//		+ "user_id,record_created) values (?," + "?,?,?,?,?,?,?," + "?,?)"

//		String sql8 = "SELECt * FROM `hsca_theme_1_hr` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
//		Object[] params8 = new Object[] { district_id, cycle_id, year };
//
//		jdbcTemplate.query(sql8, params8, rs -> {
//
//			while (rs.next()) {
//
//				obj.setDetails_hr(rs.getString("details_hr"));
//				obj.setSanctioned_hr(rs.getString("sanctioned_hr"));
//				obj.setAvailable_functional_hr(rs.getString("available_functional_hr"));
//				obj.setGap_hr(rs.getString("gap_hr"));
//				obj.setUserid(rs.getString("user_id"));
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});

		String sql15 = "SELECT * FROM hsca_theme_1_hr  where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params15 = new Object[] { district_id, cycle_id, year };

		List<Form1BSaveDocumentsArray> hr_list = jdbcTemplate.query(sql15, params15, rs -> {

			List<Form1BSaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {
				Form1BSaveDocumentsArray tempobj = new Form1BSaveDocumentsArray();

				tempobj.setDocument_details(rs.getString("details_hr"));
				tempobj.setDocument_sanctioned(rs.getString("sanctioned_hr"));
				tempobj.setDocument_available_functional(rs.getString("available_functional_hr"));
				tempobj.setDocument_gap(rs.getString("gap_hr"));
				tempobj.setDist_demogra_dtl_id(rs.getString("dist_demogra_dtl_id"));
				tempobj.setPrimary_key(rs.getString("hr_id"));

				templist.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		obj.setHr_array(hr_list);

		return obj;

	}

	public List<Areas_of_Indicators_List> getIndicatorAreasList() {

		// SELECT `area`.`id`, `area`.`area_name` FROM `area`;

		String sql9 = "SELECT `id`,  `area_name` FROM  `area`";
		Object[] params9 = new Object[] {};

		List<Areas_of_Indicators_List> getlist = jdbcTemplate.query(sql9, params9, rs -> {

			List<Areas_of_Indicators_List> templist = new ArrayList<>();
			while (rs.next()) {
				Areas_of_Indicators_List val = new Areas_of_Indicators_List();

				val.setArea_name("" + rs.getString("area_name"));
				val.setId("" + rs.getString("id"));
				templist.add(val);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		return getlist;
	}

	public OptionalindicatorsList getOptIndicators() {

		OptionalindicatorsList response = new OptionalindicatorsList();

		List<OptionalindicatorBean> list1 = new ArrayList<>(); 
		
		String sql9 = "SELECT i.`id`,   i.`indicator_name`,   IFNULL(i.`definition`,'') definition, IFNULL( i.`numerator`,'')  numerator,"
				+ "  IFNULL( i.`denominator`,'')   denominator,  IFNULL(  i.`source`,'')  source,  IFNULL(  i.`Theme`,'')  Theme, "
				+ " IFNULL(  i.`Current_Available`,'')  Current_Available,  IFNULL(  i.`Frequency`,'') Frequency,  i.`area_id`,"
				+ " IFNULL(  a.`area_name`,'') area_name,   i.`category`  FROM `indicators_optional` i "
				+ " left join `area` a on i.`area_id`=a.`id`  order by a.`area_name`;";

//		String sql9 = "SELECT i.`id`,   i.`indicator_name`,   i.`definition`,  i.`numerator`,  "
//				+ "   i.`denominator`,   i.`source`,   i.`Theme`,   i.`Current_Available`,   i.`Frequency`,  "
//				+ "   i.`area_id`,  a.`area_name`,   i.`category`  FROM `indicators_optional` i  "
//				+ "   left join `area` a on i.`area_id`=a.`id`  order by a.`area_name`";
		Object[] params9 = new Object[] {};

		list1 = jdbcTemplate.query(sql9, params9, rs -> {

			List<OptionalindicatorBean> templist = new ArrayList<>();
			while (rs.next()) {
				OptionalindicatorBean obj = new OptionalindicatorBean();
				obj.setIndicator_id(rs.getString("id"));
				obj.setDefinition(rs.getString("definition"));
				obj.setNumerator(rs.getString("numerator"));
				obj.setDenominator(rs.getString("denominator"));
				obj.setSource(rs.getString("source"));
				obj.setArea_id(rs.getString("area_id"));
				obj.setCategory(rs.getString("category"));
				obj.setIndicator_val(rs.getString("indicator_name"));
				obj.setTheme(rs.getString("Theme"));
				obj.setCurrent_available(rs.getString("Current_Available"));
				obj.setFrequency(rs.getString("Frequency"));
				obj.setArea_name(rs.getString("area_name"));

				templist.add(obj);
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		response.setOptional_indicators_list(list1);

		return response;
	}

	public IndicatorsList getIndicatorsList() {

		IndicatorsList obj = new IndicatorsList(); 

//		String sql9 = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,i.id as `indicator_id` FROM indicators i left join area a on i.area_id=a.id";

		String sql9="SELECT a.id as `area_id`,i.indicator_name, IFNULL(a.area_name,'') area_name,"
				+ " i.id as `indicator_id`,i.indicator_name, IFNULL(i.definition,'')  `definition`,  IFNULL(i.numerator,'') numerator, IFNULL( i.denominator,'') denominator, IFNULL( i.source,'') `source`, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id  order by a.area_name;";
		
//		String sql9 = "SELECT a.id as `area_id`,i.indicator_name,a.area_name,i.id as `indicator_id`,i.indicator_name,"
//				+ " i.definition, i.numerator, i.denominator, i.source, i.frequency , i.category FROM indicators i left join area a on i.area_id=a.id  order by a.area_name ";
		Object[] params9 = new Object[] {};

		List<Menu_Area_Indicator_Object> getlist = jdbcTemplate.query(sql9, params9, rs -> {

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

		obj.setAreas_Id_Indicators_map_list(getlist);
		;

		return obj;
	}

//	class Area_Indicator_Object{
//		String area_id;
//		String area_name;
//		String indicator_list;
//		
//		
//		
//	}

	public DeleteForm1AResponse deleteForm1BDetails(String district_id, String cycle_id, String year, String user_id) {

		DeleteForm1AResponse responseobj = new DeleteForm1AResponse();

		
		Object[] params1 = { district_id, cycle_id, year };
		int rows = jdbcTemplate.update(
				"DELETE FROM `hsca_district_demographic_dtls` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params1);
		

		// Delete a student record from database where the students
		// id matches with the specified parameter.
		Object[] params = { district_id, cycle_id, year };
		int rows2 = jdbcTemplate.update(
				"DELETE FROM `hsca_district_others` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params);
		

		Object[] params12 = { district_id, cycle_id, year };
		int rows21 = jdbcTemplate.update(
				"DELETE FROM `key_ngo_demogra` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params12);

		

		Object[] params24 = { district_id, cycle_id, year };
		int rows22 = jdbcTemplate.update(
				"DELETE FROM `key_pvt_demogra` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params24);

		
		int rows3 = jdbcTemplate.update(
				"DELETE FROM `hsca_requirements_iphs` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params);

		int rows4 = jdbcTemplate.update(
				"DELETE FROM `hsca_theme_1_infrastructure` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params);

		int rows5 = jdbcTemplate.update(
				"DELETE FROM `hsca_theme_1_gen_res_finance` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params);

		int rows6 = jdbcTemplate.update(
				"DELETE FROM `hsca_theme_1_gen_res_supplies` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params);

		int rows7 = jdbcTemplate.update(
				"DELETE FROM `hsca_theme_1_gen_res_technology` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params);

		int rows8 = jdbcTemplate.update(
				"DELETE FROM `hsca_theme_1_hr` where `district_id`=? and `cycle_id`=? and `financial_year`=?", params);

		int rows9 = jdbcTemplate.update(
				"DELETE FROM `form1b_selected_indicators` where `district_id`=? and `cycle_id`=? and `year`=?", params);

		responseobj.setProcessname("deleted");
		if (rows > 1 && rows2 > 1 && rows3 > 1 && rows4 > 1 && rows5 > 1 && rows6 > 1 && rows7 > 1 && rows8 > 1) {
			responseobj.setResponse_val("1");
		} else {
			responseobj.setResponse_val("0");
		}

		return responseobj;
	}
	
	private List<String> formBSortedIdList(String district_id, String cycle_id, String year){
		
		List<Integer> list = new ArrayList<Integer>();
		
		
		String sql = "SELECT doc_db_doc_id FROM `doc_db_documents` where `district_id`=? and `cycle_id`=? and `year`=?";
		Object[] params = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {
				list.add(Integer.valueOf(rs.getString("doc_db_doc_id")));
			}			
			return "";
		});
		
		Collections.sort(list);
		List<String> sortedList = list.stream().map(Object::toString).collect(Collectors.toList());
		return sortedList;
	}  

}
