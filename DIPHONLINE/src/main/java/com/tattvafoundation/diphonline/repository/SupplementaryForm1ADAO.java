package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.model.AllFormsDataFetchFromAndroidBean;
import com.tattvafoundation.diphonline.model.DeleteForm4PlanResponse;
import com.tattvafoundation.diphonline.model.Form1ADocumentsTableDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form5FollowUpSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form5PartAStakesMeetingDataBean;
import com.tattvafoundation.diphonline.model.FormSupplementaryAActionTableDataBean;
import com.tattvafoundation.diphonline.model.FormSupplementaryAPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.FormSupplementarySendAllDataBean;
import com.tattvafoundation.diphonline.model.SavedForm5FollowupResponse;
import com.tattvafoundation.diphonline.model.Supplementaryform1A;
import com.tattvafoundation.diphonline.model.Supplementaryform1ACommonArray;
import com.tattvafoundation.diphonline.model.UserCredentialsFromAndroidBean;
import com.tattvafoundation.diphonline.model.User_Districts_Privileges;

@Repository
public class SupplementaryForm1ADAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public SavedForm5FollowupResponse editUpdateSupplementaryForm1ADetails(Supplementaryform1A model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

	
		int row = 0;

//		INSERT INTO `data_extraction`(`document_title`, `date_of_release`, `primary_theme`, `goal`, `part`, 
//		`district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`) 
//VALUES (:document_title,:date_of_release,:primary_theme,:goal,:part,:district_id,:cycle_id,
//		:financial_year,:user_id,:record_created)

//		String sql2 = "SELECt * FROM `data_extraction` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";

		String sql1 = " UPDATE `data_extraction` SET `document_title`=?, `date_of_release`=?,"
				+ " `primary_theme`=?,  `goal`=? , `user_id`=?, `completed`=?  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1);
			ps.setString(1, model.getParta_document_title());
			ps.setString(2, model.getParta_date_of_release());
			ps.setString(3, model.getParta_primary_theme());
			ps.setString(4, model.getParta_goal());
			ps.setString(5, model.getUserid());
			ps.setString(6, model.getCompleted());
			ps.setString(7, model.getDistrict());
			ps.setString(8, model.getCycle());
			ps.setString(9, model.getYear());
			ps.setString(10, "PART-A");
			return ps;
		});

	
		String sql2 = " UPDATE `data_extraction` SET `document_title`=?, `date_of_release`=?,"
				+ " `primary_theme`=?,  `goal`=? , `user_id`=?, `completed`=?  where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2);
			ps.setString(1, model.getPartb_document_title());
			ps.setString(2, model.getPartb_date_of_release());
			ps.setString(3, model.getPartb_primary_theme());
			ps.setString(4, model.getPartb_goal());
			ps.setString(5, model.getUserid());
			ps.setString(6, model.getCompleted());
			ps.setString(7, model.getDistrict());
			ps.setString(8, model.getCycle());
			ps.setString(9, model.getYear());
			ps.setString(10, "PART-B");
			return ps;
		});

	
//		INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`)
//		VALUES (:part,:da_action_point,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql3 = " UPDATE `data_extraction_action_point` SET `da_action_point`=?"
				+ "   where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";
//
//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql3);
//			ps.setString(1, model.getParta_da_action_point());
//			ps.setString(2, model.getDistrict());
//			ps.setString(3, model.getCycle());
//			ps.setString(4, model.getYear());
//			ps.setString(5, "PART-A");
//			return ps;
//		});

		List<Supplementaryform1ACommonArray> list_parta_action_arr = model.getParta_da_action_point_array();

		List<Supplementaryform1ACommonArray> list_parta_action_update = new ArrayList<>();
		List<Supplementaryform1ACommonArray> list_parta_action_insert = new ArrayList<>();

		for (int i = 0; i < list_parta_action_arr.size(); i++) {

			Supplementaryform1ACommonArray tempobj = list_parta_action_arr.get(i);

			if ("1".equals(tempobj.getInsert())) {
				list_parta_action_insert.add(tempobj);
			} else {
				list_parta_action_update.add(tempobj);
			}

		}

		if (list_parta_action_update.size() != 0) {
			jdbcTemplate.batchUpdate(" UPDATE `data_extraction_action_point` SET `da_action_point`=?"
					+ "   where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=? and `data_action_point_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Supplementaryform1ACommonArray tempobj = list_parta_action_update.get(i);

							ps.setString(1, "" + tempobj.getDocument_name());
							ps.setString(2, model.getDistrict());
							ps.setString(3, model.getCycle());
							ps.setString(4, model.getYear());
							ps.setString(5, "PART-A");
							ps.setString(6, "" + tempobj.getData_action_point_id());
						}

						public int getBatchSize() {
							return list_parta_action_update.size();
						}

					});
		}

		if (list_parta_action_insert.size() != 0) {
			jdbcTemplate
					.batchUpdate("INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
							+ " `cycle_id`, `financial_year`, `user_id`, `record_created`) "
							+ "			VALUES (?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + "PART-A");
									ps.setString(2, "" + list_parta_action_insert.get(i).getDocument_name());
									ps.setString(3, model.getDistrict());
									ps.setString(4, model.getCycle());
									ps.setString(5, model.getYear());
									ps.setString(6, model.getUserid());
									ps.setString(7, formatedDateTime);
								}

								public int getBatchSize() {
									return list_parta_action_insert.size();
								}

							});
		}

	
		String sql4 = " UPDATE `data_extraction_action_point` SET `da_action_point`=?"
				+ "   where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql4);
//			ps.setString(1, model.getPartb_da_action_point());
//			ps.setString(2, model.getDistrict());
//			ps.setString(3, model.getCycle());
//			ps.setString(4, model.getYear());
//			ps.setString(5, "PART-B");
//			return ps;
//		});

		List<Supplementaryform1ACommonArray> list_partb_action_arr = model.getPartb_da_action_point_array();

		List<Supplementaryform1ACommonArray> list_partb_action_update = new ArrayList<>();
		List<Supplementaryform1ACommonArray> list_partb_action_insert = new ArrayList<>();

		for (int i = 0; i < list_partb_action_arr.size(); i++) {

			Supplementaryform1ACommonArray tempobj = list_partb_action_arr.get(i);

			if ("1".equals(tempobj.getInsert())) {
				list_partb_action_insert.add(tempobj);
			} else {
				list_partb_action_update.add(tempobj);
			}
		}

		if (list_partb_action_update.size() != 0) {
			jdbcTemplate.batchUpdate(" UPDATE `data_extraction_action_point` SET `da_action_point`=?"
					+ "   where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=? and `data_action_point_id`=?",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							Supplementaryform1ACommonArray tempobj = list_partb_action_update.get(i);

							ps.setString(1, "" + tempobj.getDocument_name());
							ps.setString(2, model.getDistrict());
							ps.setString(3, model.getCycle());
							ps.setString(4, model.getYear());
							ps.setString(5, "PART-B");
							ps.setString(6, "" + tempobj.getData_action_point_id());
						}

						public int getBatchSize() {
							return list_partb_action_update.size();
						}

					});
		}

		if (list_partb_action_insert.size() != 0) {
			jdbcTemplate
					.batchUpdate("INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
							+ " `cycle_id`, `financial_year`, `user_id`, `record_created`) "
							+ "			VALUES (?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + "PART-B");
									ps.setString(2, "" + list_partb_action_insert.get(i).getDocument_name());
									ps.setString(3, model.getDistrict());
									ps.setString(4, model.getCycle());
									ps.setString(5, model.getYear());
									ps.setString(6, model.getUserid());
									ps.setString(7, formatedDateTime);
								}

								public int getBatchSize() {
									return list_partb_action_insert.size();
								}

							});
		}

	
		SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;

	}

	public DeleteForm4PlanResponse deleteSupplementaryForm1ADetails(String district_id, String cycle_id, String year,
			String user_id) {

		DeleteForm4PlanResponse responseobj = new DeleteForm4PlanResponse();

		

		Object[] params1 = { district_id, cycle_id, year };
		int rows = jdbcTemplate.update(
				"DELETE FROM `data_extraction` where `district_id`=? and `cycle_id`=? and `financial_year`=?", params1);
		

		Object[] params2 = { district_id, cycle_id, year };
		int rows2 = jdbcTemplate.update(
				"DELETE FROM `data_extraction_action_point` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params2);
		

		responseobj.setProcessname("deleted");
		if (rows > 1 && rows2 > 1) {
			responseobj.setResponse_val("1");
		} else {
			responseobj.setResponse_val("0");
		}

		return responseobj;

	}
	
	public void sendForm5FollowUpSaveForExistingDistrictCycleYear(String district_id,String cycle_id,
			String year,String country,String province,FormSupplementarySendAllDataBean response ) {


		List<FormSupplementaryAPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<FormSupplementaryAActionTableDataBean> sendlist2 = new ArrayList<>();

		String sql2 = "SELECT suppform.`data_extraction_id`,    suppform.`document_title`,   suppform.`date_of_release`, "
				+ "   suppform.`primary_theme`,   suppform.`goal`,   suppform.`part`,   suppform.`district_id`,  "
				+ "    suppform.`cycle_id`,    suppform.`financial_year`,    suppform.`user_id`,  suppform.`record_created`,  suppform.`completed`  FROM `data_extraction` suppform   "
				+ "    where suppform.`district_id`=?  and   suppform.`cycle_id`=?  and    suppform.`financial_year`=?";

		Object[] params2 = new Object[] { district_id, cycle_id, year };

		sendlist1 = jdbcTemplate.query(sql2, params2, rs -> {

			List<FormSupplementaryAPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				FormSupplementaryAPrimaryTableDataBean obj = new FormSupplementaryAPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("data_extraction_id"));
				obj.setDocument_title(rs.getString("document_title"));
				obj.setDate_of_release(rs.getString("date_of_release"));
				obj.setPrimary_theme(rs.getString("primary_theme"));
				obj.setGoal(rs.getString("goal"));

				String partval = "PARTA";

				if ("PART-A".equals(rs.getString("part"))) {
					partval = "PARTA";
				}

				else if ("PART-B".equals(rs.getString("part"))) {
					partval = "PARTB";
				}
				obj.setPart(partval);
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				//obj.setCountry_id(model.getCountry_id());
				//obj.setProvince_id(model.getProvince_id());
				obj.setDatafrom("WEB");
				obj.setCompleted(rs.getString("completed"));
				obj.setCountry_id(country);
				obj.setProvince_id(province);

				templist.add(obj);

			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql4 = "SELECT suppform.`data_action_point_id`,   suppform.`part`,    suppform.`da_action_point`, "
				+ "  suppform. `district_id`,   suppform.`cycle_id`,  suppform.`financial_year`,   "
				+ "  suppform.`user_id`,   suppform.`record_created`  FROM `data_extraction_action_point`  suppform   "
				+ "  where suppform. `district_id`=?  and  suppform.`cycle_id`=? and  suppform.`financial_year`=?";
		
		Object[] params4 = new Object[] {  district_id, cycle_id, year  };

		sendlist2 = jdbcTemplate.query(sql4, params4, rs -> {

			String str = "";
			List<FormSupplementaryAActionTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {

				FormSupplementaryAActionTableDataBean obj = new FormSupplementaryAActionTableDataBean();

				obj.setAuto_id(rs.getString("data_action_point_id"));
				obj.setDa_action_point(rs.getString("da_action_point"));
				obj.setPart(rs.getString("part"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				//obj.setCountry_id(model.getCountry_id());
				//obj.setProvince_id(model.getProvince_id());
				obj.setDatafrom("WEB");
				
				obj.setCountry_id(country);
				obj.setProvince_id(province);

				templist.add(obj);

			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		response.setExtractdata(sendlist1);
		response.setExtractdataaction(sendlist2);
		response.setError_code("200");
		response.setMessage("Sending Supplementary Form Data");

		//return response;
	
	}

	public FormSupplementarySendAllDataBean consumeAllFormSupplementaryASaveandUpdate(
			AllFormsDataFetchFromAndroidBean model) {

	
		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		
		FormSupplementarySendAllDataBean response = new FormSupplementarySendAllDataBean();

		FormSupplementarySendAllDataBean formsupp = model.getFormsupplementary();

		List<FormSupplementaryAPrimaryTableDataBean> list1 = formsupp.getExtractdata();

		List<FormSupplementaryAPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<FormSupplementaryAActionTableDataBean> sendlist2 = new ArrayList<>();

		for (int index = 0; index < list1.size(); index++) {

			FormSupplementaryAPrimaryTableDataBean tempobj = list1.get(index);		

			FormSupplementaryAPrimaryTableDataBean sendobj = new FormSupplementaryAPrimaryTableDataBean();

			if ("APP".equals(tempobj.getDatafrom())) {

				int row = 0;

				String partval = "PARTA";

				if ("PARTA".equals(tempobj.getPart())) {
					partval = "PART-A";
				} else if ("PARTB".equals(tempobj.getPart())) {
					partval = "PART-B";
				}

				String partval1 = partval;

				/*-------------------------------------------*/

				String sql_check = "SELECT * FROM `data_extraction`  where  `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";
				Object[] params_check = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
						tempobj.getYear(), partval };

				String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

					String keyval = "";
					while (rs.next()) {
						keyval = rs.getString("data_extraction_id");
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

				String sql1 = "INSERT INTO `data_extraction`(`document_title`, `date_of_release`, `primary_theme`, `goal`, `part`, "
						+ "						`district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`, `completed`) "
						+ "				VALUES (?,?,?,?,?,?,?," + "						?,?,?,?)";

				KeyHolder keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, tempobj.getDocument_title());
					sendobj.setDocument_title(tempobj.getDocument_title());

					ps.setString(2, tempobj.getDate_of_release());
					sendobj.setDate_of_release(tempobj.getDate_of_release());

					ps.setString(3, tempobj.getPrimary_theme());
					sendobj.setPrimary_theme(tempobj.getPrimary_theme());

					ps.setString(4, tempobj.getGoal());
					sendobj.setGoal(tempobj.getGoal());

					ps.setString(5, partval1);
					sendobj.setPart(tempobj.getPart());

					ps.setString(6, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());

					ps.setString(7, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());

					ps.setString(8, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());

					ps.setString(9, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id());

					ps.setString(10, formatedDateTime);
					sendobj.setRecordcreated(tempobj.getRecordcreated());
					
					ps.setString(11, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());

					return ps;
				}, keyHolder);

				// ResultSet rs = ps.getGeneratedKeys();

				long p_key = keyHolder.getKey().longValue();

				sendobj.setAuto_id("" + keyHolder.getKey().longValue());
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(tempobj.getRecordcreated());
				sendobj.setDatafrom("WEB");

				sendlist1.add(sendobj);

				List<FormSupplementaryAActionTableDataBean> list2 = formsupp.getExtractdataaction();

				for (int x = 0; x < list2.size(); x++) {

					FormSupplementaryAActionTableDataBean actionobj = list2.get(x);

					FormSupplementaryAActionTableDataBean sendactionobj = new FormSupplementaryAActionTableDataBean();

					if (tempobj.getDistrict_id().equals(actionobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(actionobj.getCycle_id())
							&& tempobj.getYear().equals(actionobj.getYear()) && tempobj.getDatafrom().equals("APP")
							&& actionobj.getDatafrom().equals("APP") && tempobj.getPart().equals(actionobj.getPart())) {

						String sql2 = "INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
								+ " `cycle_id`, `financial_year`, `user_id`, `record_created`) "
								+ "			VALUES (?,?,?,?,?,?,?)";

						KeyHolder keyHolder2 = new GeneratedKeyHolder();

						row = jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);

							String partval2 = "PARTA";

							if ("PARTA".equals(tempobj.getPart())) {
								partval2 = "PART-A";
							} else if ("PARTB".equals(tempobj.getPart())) {
								partval2 = "PART-B";
							}
							ps.setString(1, "" + partval2);
							sendactionobj.setPart(actionobj.getPart());

							ps.setString(2, "" + actionobj.getDa_action_point());
							sendactionobj.setDa_action_point(actionobj.getDa_action_point());

							ps.setString(3, actionobj.getDistrict_id());
							sendactionobj.setDistrict_id(actionobj.getDistrict_id());

							ps.setString(4, actionobj.getCycle_id());
							sendactionobj.setCycle_id(actionobj.getCycle_id());

							ps.setString(5, actionobj.getYear());
							sendactionobj.setYear(actionobj.getYear());

							ps.setString(6, actionobj.getUser_id());
							sendactionobj.setUser_id(actionobj.getUser_id());

							ps.setString(7, formatedDateTime);
							sendactionobj.setRecordcreated(actionobj.getRecordcreated());

							return ps;
						}, keyHolder2);

						sendactionobj.setAuto_id("" + keyHolder2.getKey().longValue());

						sendactionobj.setCountry_id(actionobj.getCountry_id());
						sendactionobj.setProvince_id(actionobj.getProvince_id());
						sendactionobj.setTimestamp(actionobj.getRecordcreated());
						sendactionobj.setDatafrom("WEB");

						sendlist2.add(sendactionobj);
					}
				}

			}

		}

		for (int index = 0; index < list1.size(); index++) {

			FormSupplementaryAPrimaryTableDataBean tempobj = list1.get(index);

		
			FormSupplementaryAPrimaryTableDataBean sendobj = new FormSupplementaryAPrimaryTableDataBean();

			if ("WEB".equals(tempobj.getDatafrom())) {

				String sql1 = "UPDATE `data_extraction` SET      `document_title` = ?,    `date_of_release` = ?, "
						+ "   `primary_theme` = ?,    `goal` = ?,       "
						+ "    `user_id` = ?,    `record_created` = ?,    `completed` = ? WHERE    `data_extraction_id` = ? and  `district_id` = ?  and    `cycle_id` = ?  and    `financial_year` = ? and `part` = ?";

				jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1);

					ps.setString(1, tempobj.getDocument_title());
					sendobj.setDocument_title(tempobj.getDocument_title());

					ps.setString(2, tempobj.getDate_of_release());
					sendobj.setDate_of_release(tempobj.getDate_of_release());

					ps.setString(3, tempobj.getPrimary_theme());
					sendobj.setPrimary_theme(tempobj.getPrimary_theme());

					ps.setString(4, tempobj.getGoal());
					sendobj.setGoal(tempobj.getGoal());

					ps.setString(5, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id());

					ps.setString(6, tempobj.getRecordcreated());
					sendobj.setRecordcreated(tempobj.getRecordcreated());
					
					ps.setString(7, tempobj.getCompleted());
					sendobj.setCompleted(tempobj.getCompleted());

					ps.setString(8, tempobj.getAuto_id());
					sendobj.setAuto_id(tempobj.getAuto_id());

					ps.setString(9, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());

					ps.setString(10, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());

					ps.setString(11, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());
					
					String partval2 = "PARTA";

					if ("PARTA".equals(tempobj.getPart())) {
						partval2 = "PART-A";
					} else if ("PARTB".equals(tempobj.getPart())) {
						partval2 = "PART-B";
					}
					
					ps.setString(12, partval2);
					sendobj.setPart(tempobj.getPart());

					return ps;
				});

				sendobj.setAuto_id(tempobj.getAuto_id());
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(tempobj.getRecordcreated());
				sendobj.setDatafrom("WEB");

				sendlist1.add(sendobj);

				List<FormSupplementaryAActionTableDataBean> list2 = formsupp.getExtractdataaction();

				for (int x = 0; x < list2.size(); x++) {

					FormSupplementaryAActionTableDataBean actionobj = list2.get(x);

					FormSupplementaryAActionTableDataBean sendactionobj = new FormSupplementaryAActionTableDataBean();

					if (tempobj.getDistrict_id().equals(actionobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(actionobj.getCycle_id())
							&& tempobj.getYear().equals(actionobj.getYear()) && tempobj.getDatafrom().equals("WEB")
							&& actionobj.getDatafrom().equals("WEB") && tempobj.getPart().equals(actionobj.getPart())) {

						String sql2 = "UPDATE `data_extraction_action_point` SET   `da_action_point` = ?, "
								+ "   `user_id` = ?,   `record_created` = ? WHERE `data_action_point_id` = ? and `district_id` = ? and `cycle_id` = ? and `financial_year` = ? and `part` = ?";

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2);

							ps.setString(1, actionobj.getDa_action_point());
							sendactionobj.setDa_action_point(actionobj.getDa_action_point());

							ps.setString(2, actionobj.getUser_id());
							sendactionobj.setUser_id(actionobj.getUser_id());

							ps.setString(3, actionobj.getRecordcreated());
							sendactionobj.setRecordcreated(actionobj.getRecordcreated());

							ps.setString(4, actionobj.getAuto_id());
							sendactionobj.setAuto_id(actionobj.getAuto_id());

							ps.setString(5, actionobj.getDistrict_id());
							sendactionobj.setDistrict_id(actionobj.getDistrict_id());

							ps.setString(6, actionobj.getCycle_id());
							sendactionobj.setCycle_id(actionobj.getCycle_id());

							ps.setString(7, actionobj.getYear());
							sendactionobj.setYear(actionobj.getYear());
							
							String partval2 = "PARTA";

							if ("PARTA".equals(actionobj.getPart())) {
								partval2 = "PART-A";
							} else if ("PARTB".equals(actionobj.getPart())) {
								partval2 = "PART-B";
							}
							
							ps.setString(8, partval2);
							sendactionobj.setPart(actionobj.getPart());

							return ps;
						});

						sendactionobj.setAuto_id(actionobj.getAuto_id());

						sendactionobj.setCountry_id(actionobj.getCountry_id());
						sendactionobj.setProvince_id(actionobj.getProvince_id());
						sendactionobj.setTimestamp(actionobj.getRecordcreated());
						sendactionobj.setDatafrom("WEB");
						
						sendlist2.add(sendactionobj);
					}
					
					else if (tempobj.getDistrict_id().equals(actionobj.getDistrict_id())
							&& tempobj.getCycle_id().equals(actionobj.getCycle_id())
							&& tempobj.getYear().equals(actionobj.getYear()) && tempobj.getDatafrom().equals("WEB")
							&& actionobj.getDatafrom().equals("APP") && tempobj.getPart().equals(actionobj.getPart())) {
						
						String sql2 = "INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
								+ " `cycle_id`, `financial_year`, `user_id`, `record_created`) "
								+ "			VALUES (?,?,?,?,?,?,?)";

						KeyHolder keyHolder2 = new GeneratedKeyHolder();

						 jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
						
							String partval2 = "PARTA";
							
							if("PARTA".equals(tempobj.getPart())) {
								partval2 = "PART-A";
							}
							else if("PARTB".equals(tempobj.getPart())) {
								partval2 = "PART-B";
							}
							ps.setString(1, "" + partval2);
							sendactionobj.setPart(actionobj.getPart());
							
							ps.setString(2, "" + actionobj.getDa_action_point());
							sendactionobj.setDa_action_point(actionobj.getDa_action_point()); 
							
							ps.setString(3, actionobj.getDistrict_id());
							sendactionobj.setDistrict_id(actionobj.getDistrict_id());
							
							ps.setString(4, actionobj.getCycle_id());
							sendactionobj.setCycle_id(actionobj.getCycle_id());
							
							ps.setString(5, actionobj.getYear());
							sendactionobj.setYear(actionobj.getYear());
							
							ps.setString(6, actionobj.getUser_id());
							sendactionobj.setUser_id(actionobj.getUser_id());
							
							ps.setString(7, formatedDateTime);
							sendactionobj.setRecordcreated(actionobj.getRecordcreated());

							return ps;
						}, keyHolder2);
						
						sendactionobj.setAuto_id(""+keyHolder2.getKey().longValue());
						
						sendactionobj.setCountry_id(actionobj.getCountry_id());
						sendactionobj.setProvince_id(actionobj.getProvince_id());
						sendactionobj.setTimestamp(actionobj.getRecordcreated());
						sendactionobj.setDatafrom("WEB");
						
						sendlist2.add(sendactionobj);
						
					}

				} // 2nd for loop

			} // for loop
		}

	
		response.setExtractdata(sendlist1);
		response.setExtractdataaction(sendlist2);

		return response;
	}

	public SavedForm5FollowupResponse savsupplemmentaryform1aToDb(Supplementaryform1A model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

	
//				INSERT INTO `data_extraction`(`document_title`, `date_of_release`, `primary_theme`, `goal`, `part`, 
//						`district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`) 
//				VALUES (:document_title,:date_of_release,:primary_theme,:goal,:part,:district_id,:cycle_id,
//						:financial_year,:user_id,:record_created)

		int row = 0;

		String sql1 = "INSERT INTO `data_extraction`(`document_title`, `date_of_release`, `primary_theme`, `goal`, `part`, "
				+ "						`district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`, `completed`) "
				+ "				VALUES (?,?,?,?,?,?,?," + "						?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getParta_document_title());
			ps.setString(2, model.getParta_date_of_release());
			ps.setString(3, model.getParta_primary_theme());
			ps.setString(4, model.getParta_goal());
			ps.setString(5, model.getParta_part());
			ps.setString(6, model.getDistrict());
			ps.setString(7, model.getCycle());
			ps.setString(8, model.getYear());
			ps.setString(9, model.getUserid());
			ps.setString(10, formatedDateTime);
			ps.setString(11, model.getCompleted());
			return ps;
		}, keyHolder);

		// ResultSet rs = ps.getGeneratedKeys();

		long p_key = keyHolder.getKey().longValue();

		
		String sql2 = "INSERT INTO `data_extraction`(`document_title`, `date_of_release`, `primary_theme`, `goal`, `part`, "
				+ "						`district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`, `completed`) "
				+ "				VALUES (?,?,?,?,?,?,?," + "						?,?,?,?)";

		KeyHolder keyHolder2 = new GeneratedKeyHolder();

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getPartb_document_title());
			ps.setString(2, model.getPartb_date_of_release());
			ps.setString(3, model.getPartb_primary_theme());
			ps.setString(4, model.getPartb_goal());
			ps.setString(5, model.getPartb_part());
			ps.setString(6, model.getDistrict());
			ps.setString(7, model.getCycle());
			ps.setString(8, model.getYear());
			ps.setString(9, model.getUserid());
			ps.setString(10, formatedDateTime);
			ps.setString(11, model.getCompleted());

			return ps;
		}, keyHolder2);

		// ResultSet rs = ps.getGeneratedKeys();

		long p_key2 = keyHolder2.getKey().longValue();

		
//				INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`)
//				VALUES (:part,:da_action_point,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql3 = "INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
				+ " `cycle_id`, `financial_year`, `user_id`, `record_created`) " + "			VALUES (?,?,?,?,?,?,?)";
//
//				row = jdbcTemplate.update(connection -> {
//					PreparedStatement ps = connection.prepareStatement(sql3);
//					ps.setString(1, model.getParta_part());
//					ps.setString(2, model.getParta_da_action_point());
//					ps.setString(3, model.getDistrict());
//					ps.setString(4, model.getCycle());
//					ps.setString(5, model.getYear());
//					ps.setString(6, model.getUserid());
//					ps.setString(7, formatedDateTime);
//
//					return ps;
//				});

		List<Supplementaryform1ACommonArray> list_parta_action = model.getParta_da_action_point_array();

		if (model.getParta_da_action_point_array().size() != 0) {

			jdbcTemplate
					.batchUpdate("INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
							+ " `cycle_id`, `financial_year`, `user_id`, `record_created`) "
							+ "			VALUES (?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getParta_part());
									ps.setString(2, "" + list_parta_action.get(i).getDocument_name());
									ps.setString(3, model.getDistrict());
									ps.setString(4, model.getCycle());
									ps.setString(5, model.getYear());
									ps.setString(6, model.getUserid());
									ps.setString(7, formatedDateTime);
								}

								public int getBatchSize() {
									return list_parta_action.size();
								}

							});

		}

	
//				INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`)
//				VALUES (:part,:da_action_point,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql4 = "INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
				+ " `cycle_id`, `financial_year`, `user_id`, `record_created`)\r\n" + "			VALUES (?,?,?,?,"
				+ "?,?,?)";

//		row = jdbcTemplate.update(connection -> {
//			PreparedStatement ps = connection.prepareStatement(sql4);
//			ps.setString(1, model.getPartb_part());
//			ps.setString(2, model.getPartb_da_action_point());
//			ps.setString(3, model.getDistrict());
//			ps.setString(4, model.getCycle());
//			ps.setString(5, model.getYear());
//			ps.setString(6, model.getUserid());
//			ps.setString(7, formatedDateTime);
//
//			return ps;
//		});

		List<Supplementaryform1ACommonArray> list_partb_action = model.getPartb_da_action_point_array();

		if (model.getPartb_da_action_point_array().size() != 0) {

			jdbcTemplate
					.batchUpdate("INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`,"
							+ " `cycle_id`, `financial_year`, `user_id`, `record_created`) "
							+ "			VALUES (?,?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getPartb_part());
									ps.setString(2, "" + list_partb_action.get(i).getDocument_name());
									ps.setString(3, model.getDistrict());
									ps.setString(4, model.getCycle());
									ps.setString(5, model.getYear());
									ps.setString(6, model.getUserid());
									ps.setString(7, formatedDateTime);
								}

								public int getBatchSize() {
									return list_partb_action.size();
								}

							});

		}

	
		SavedForm5FollowupResponse responseobj = new SavedForm5FollowupResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
	

	public FormSupplementarySendAllDataBean retrieveFormSupplementaryA_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(
			UserCredentialsFromAndroidBean model,String LoggedinUserId) {

		FormSupplementarySendAllDataBean response = new FormSupplementarySendAllDataBean(); 
		
		
		List<FormSupplementaryAPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<FormSupplementaryAActionTableDataBean> sendlist2 = new ArrayList<>();
		
		
		
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
			
			
			response.setExtractdata(new ArrayList<>());
			response.setExtractdataaction(new ArrayList<>());
			response.setError_code("200");
			response.setMessage("Sending Supplementary Form Data");

			return response;
		}
		else {
			System.out.println("Not Returning in half!!! 0 or different value rather than -1");
		}
		
		
		String sql2 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql2 = "SELECT suppform.`completed`, suppform.`data_extraction_id`,    suppform.`document_title`,   suppform.`date_of_release`,  "
					+ "   suppform.`primary_theme`,   suppform.`goal`,   suppform.`part`,   suppform.`district_id`,   "
					+ "   suppform.`cycle_id`,    suppform.`financial_year`,    suppform.`user_id`,   "
					+ "   suppform.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`   "
					+ "   FROM `data_extraction` suppform  left join  `district` d on suppform.district_id=d.district_id   "
					+ "   left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  suppform.`financial_year` >= 2019;";
	
		}
		else {
			sql2 = "SELECT suppform.`completed`, suppform.`data_extraction_id`,    suppform.`document_title`,   suppform.`date_of_release`,  "
					+ "   suppform.`primary_theme`,   suppform.`goal`,   suppform.`part`,   suppform.`district_id`,   "
					+ "   suppform.`cycle_id`,    suppform.`financial_year`,    suppform.`user_id`,   "
					+ "   suppform.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`   "
					+ "   FROM `data_extraction` suppform  left join  `district` d on suppform.district_id=d.district_id   "
					+ "   left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  suppform.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and suppform.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and suppform.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist1 = jdbcTemplate.query(sql2, params2, rs -> {

			List<FormSupplementaryAPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				FormSupplementaryAPrimaryTableDataBean obj = new FormSupplementaryAPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("data_extraction_id"));
				obj.setDocument_title(rs.getString("document_title"));
				obj.setDate_of_release(rs.getString("date_of_release"));
				obj.setPrimary_theme(rs.getString("primary_theme"));
				obj.setGoal(rs.getString("goal"));

				String partval = "PARTA";

				if ("PART-A".equals(rs.getString("part"))) {
					partval = "PARTA";
				}

				else if ("PART-B".equals(rs.getString("part"))) {
					partval = "PARTB";
				}
				obj.setPart(partval);
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCountry_id(model.getCountry_id());
				obj.setProvince_id(model.getProvince_id());
				obj.setCompleted(rs.getString("completed"));
				obj.setDatafrom("WEB");

				templist.add(obj);

			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		String sql4 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql4 = "SELECT suppform.`data_action_point_id`,   suppform.`part`,    suppform.`da_action_point`, "
					+ "     suppform. `district_id`,   suppform.`cycle_id`,  suppform.`financial_year`,    suppform.`user_id`,  "
					+ "     suppform.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM `data_extraction_action_point`  suppform   "
					+ "     left join  `district` d on suppform.district_id=d.district_id   "
					+ "     left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "     where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  suppform.`financial_year` >= 2019;";
			
		}
		else {
			sql4 = "SELECT suppform.`data_action_point_id`,   suppform.`part`,    suppform.`da_action_point`, "
					+ "     suppform. `district_id`,   suppform.`cycle_id`,  suppform.`financial_year`,    suppform.`user_id`,  "
					+ "     suppform.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM `data_extraction_action_point`  suppform   "
					+ "     left join  `district` d on suppform.district_id=d.district_id   "
					+ "     left join  `country_states` cs on d.state_id=cs.cs_id    "
					+ "     where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  suppform.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and suppform.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and suppform.`financial_year` IN ("+user_priv.getAll_years()+");";
			
		}

		
		
		Object[] params4 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist2 = jdbcTemplate.query(sql4, params4, rs -> {

			String str = "";
			List<FormSupplementaryAActionTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {

				FormSupplementaryAActionTableDataBean obj = new FormSupplementaryAActionTableDataBean();

				obj.setAuto_id(rs.getString("data_action_point_id"));
				obj.setDa_action_point(rs.getString("da_action_point"));
				obj.setPart(rs.getString("part"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCountry_id(model.getCountry_id());
				obj.setProvince_id(model.getProvince_id());
				obj.setDatafrom("WEB");

				templist.add(obj);

			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		response.setExtractdata(sendlist1);
		response.setExtractdataaction(sendlist2);
		response.setError_code("200");
		response.setMessage("Sending Supplementary Form Data");

		return response;
	}


	public FormSupplementarySendAllDataBean retrieveFormSupplementaryA_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(
			UserCredentialsFromAndroidBean model) {

		FormSupplementarySendAllDataBean response = new FormSupplementarySendAllDataBean();

		List<FormSupplementaryAPrimaryTableDataBean> sendlist1 = new ArrayList<>();
		List<FormSupplementaryAActionTableDataBean> sendlist2 = new ArrayList<>();

		String sql2 = "SELECT suppform.`completed`, suppform.`data_extraction_id`,    suppform.`document_title`,   suppform.`date_of_release`,  "
				+ "   suppform.`primary_theme`,   suppform.`goal`,   suppform.`part`,   suppform.`district_id`,   "
				+ "   suppform.`cycle_id`,    suppform.`financial_year`,    suppform.`user_id`,   "
				+ "   suppform.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`   "
				+ "   FROM `data_extraction` suppform  left join  `district` d on suppform.district_id=d.district_id   "
				+ "   left join  `country_states` cs on d.state_id=cs.cs_id    "
				+ "   where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  suppform.`financial_year` >= 2019;";

		Object[] params2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist1 = jdbcTemplate.query(sql2, params2, rs -> {

			List<FormSupplementaryAPrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				FormSupplementaryAPrimaryTableDataBean obj = new FormSupplementaryAPrimaryTableDataBean();

				obj.setAuto_id(rs.getString("data_extraction_id"));
				obj.setDocument_title(rs.getString("document_title"));
				obj.setDate_of_release(rs.getString("date_of_release"));
				obj.setPrimary_theme(rs.getString("primary_theme"));
				obj.setGoal(rs.getString("goal"));

				String partval = "PARTA";

				if ("PART-A".equals(rs.getString("part"))) {
					partval = "PARTA";
				}

				else if ("PART-B".equals(rs.getString("part"))) {
					partval = "PARTB";
				}
				obj.setPart(partval);
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCountry_id(model.getCountry_id());
				obj.setProvince_id(model.getProvince_id());
				obj.setCompleted(rs.getString("completed"));
				obj.setDatafrom("WEB");

				templist.add(obj);

			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql4 = "SELECT suppform.`data_action_point_id`,   suppform.`part`,    suppform.`da_action_point`, "
				+ "     suppform. `district_id`,   suppform.`cycle_id`,  suppform.`financial_year`,    suppform.`user_id`,  "
				+ "     suppform.`record_created`,d.`district_id` as `dst2`,d.`state_id`,cs.`region_id`,d.`country_id`    FROM `data_extraction_action_point`  suppform   "
				+ "     left join  `district` d on suppform.district_id=d.district_id   "
				+ "     left join  `country_states` cs on d.state_id=cs.cs_id    "
				+ "     where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  suppform.`financial_year` >= 2019;";
		Object[] params4 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		sendlist2 = jdbcTemplate.query(sql4, params4, rs -> {

			String str = "";
			List<FormSupplementaryAActionTableDataBean> templist = new ArrayList<>();
			while (rs.next()) {

				FormSupplementaryAActionTableDataBean obj = new FormSupplementaryAActionTableDataBean();

				obj.setAuto_id(rs.getString("data_action_point_id"));
				obj.setDa_action_point(rs.getString("da_action_point"));
				obj.setPart(rs.getString("part"));
				obj.setDistrict_id(rs.getString("district_id"));
				obj.setCycle_id(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUser_id(rs.getString("user_id"));
				obj.setRecordcreated(rs.getString("record_created"));
				obj.setTimestamp(rs.getString("record_created"));
				obj.setCountry_id(model.getCountry_id());
				obj.setProvince_id(model.getProvince_id());
				obj.setDatafrom("WEB");

				templist.add(obj);

			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		response.setExtractdata(sendlist1);
		response.setExtractdataaction(sendlist2);
		response.setError_code("200");
		response.setMessage("Sending Supplementary Form Data");

		return response;
	}

	public Supplementaryform1A retrieveSupplementaryForm1ADetails(String district_id, String cycle_id, String year,
			String user_id) {

		Supplementaryform1A obj = new Supplementaryform1A();

//		INSERT INTO `data_extraction`(`document_title`, `date_of_release`, `primary_theme`, `goal`, `part`, 
//		`district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`) 
//VALUES (:document_title,:date_of_release,:primary_theme,:goal,:part,:district_id,:cycle_id,
//		:financial_year,:user_id,:record_created)

		String sql2 = "SELECt * FROM `data_extraction` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";
		Object[] params2 = new Object[] { district_id, cycle_id, year, "PART-A" };

		jdbcTemplate.query(sql2, params2, rs -> {

			String str = "";
			while (rs.next()) {

				obj.setParta_document_title(rs.getString("document_title"));
				obj.setParta_date_of_release(rs.getString("date_of_release"));
				obj.setParta_primary_theme(rs.getString("primary_theme"));
				obj.setParta_goal(rs.getString("goal"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUserid(rs.getString("user_id"));
				obj.setCompleted(rs.getString("completed"));

			}
			/* We can also return any variable-data from here but not used currently */
			return str;
		});

		String sql3 = "SELECt * FROM `data_extraction` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";
		Object[] params3 = new Object[] { district_id, cycle_id, year, "PART-B" };

		jdbcTemplate.query(sql3, params3, rs -> {

			String str = "";
			while (rs.next()) {

				obj.setPartb_document_title(rs.getString("document_title"));
				obj.setPartb_date_of_release(rs.getString("date_of_release"));
				obj.setPartb_primary_theme(rs.getString("primary_theme"));
				obj.setPartb_goal(rs.getString("goal"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUserid(rs.getString("user_id"));
				obj.setCompleted(rs.getString("completed"));

			}
			/* We can also return any variable-data from here but not used currently */
			return str;
		});

//		INSERT INTO `data_extraction_action_point`(`part`, `da_action_point`, `district_id`, `cycle_id`, `financial_year`, `user_id`, `record_created`)
//		VALUES (:part,:da_action_point,:district_id,:cycle_id,:financial_year,:user_id,:record_created)

		String sql4 = "SELECt * FROM `data_extraction_action_point` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";
		Object[] params4 = new Object[] { district_id, cycle_id, year, "PART-A" };

		List<Supplementaryform1ACommonArray> list_parta_action_arr = new ArrayList<>();

		jdbcTemplate.query(sql4, params4, rs -> {

			String str = "";
			while (rs.next()) {
				Supplementaryform1ACommonArray tempobj = new Supplementaryform1ACommonArray();
				tempobj.setDocument_name(rs.getString("da_action_point"));
				tempobj.setData_action_point_id(rs.getString("data_action_point_id"));

				list_parta_action_arr.add(tempobj);
				obj.setParta_da_action_point(rs.getString("da_action_point"));

			}
			/* We can also return any variable-data from here but not used currently */
			return str;
		});

		obj.setParta_da_action_point_array(list_parta_action_arr);

		String sql5 = "SELECt * FROM `data_extraction_action_point` where `district_id`=? and `cycle_id`=? and `financial_year`=?  and `part`=?";
		Object[] params5 = new Object[] { district_id, cycle_id, year, "PART-B" };

		List<Supplementaryform1ACommonArray> list_partb_action_arr = new ArrayList<>();

		jdbcTemplate.query(sql5, params5, rs -> {

			String str = "";
			while (rs.next()) {
				Supplementaryform1ACommonArray tempobj = new Supplementaryform1ACommonArray();
				tempobj.setDocument_name(rs.getString("da_action_point"));
				tempobj.setData_action_point_id(rs.getString("data_action_point_id"));

				list_partb_action_arr.add(tempobj);
				obj.setPartb_da_action_point(rs.getString("da_action_point"));

			}
			/* We can also return any variable-data from here but not used currently */
			return str;
		});

		obj.setPartb_da_action_point_array(list_partb_action_arr);

		return obj;
	}

}
