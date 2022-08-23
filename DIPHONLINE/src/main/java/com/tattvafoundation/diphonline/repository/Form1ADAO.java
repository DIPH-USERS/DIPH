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
import com.tattvafoundation.diphonline.model.DeleteForm1AResponse;
import com.tattvafoundation.diphonline.model.Form1AConsumeDataFromAndroidBean;
import com.tattvafoundation.diphonline.model.Form1ADocumentsAvailable;
import com.tattvafoundation.diphonline.model.Form1ADocumentsCheclist;
import com.tattvafoundation.diphonline.model.Form1ADocumentsTableDataBean;
import com.tattvafoundation.diphonline.model.Form1AEditUpdate;
import com.tattvafoundation.diphonline.model.Form1APrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form1ASave;
import com.tattvafoundation.diphonline.model.Form1ASaveDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1ASendAllDataToAndroidBean;
import com.tattvafoundation.diphonline.model.Form1ASourceIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form1AView;
import com.tattvafoundation.diphonline.model.Form1BConsumeDataFromAndroidBean;
import com.tattvafoundation.diphonline.model.Form1BPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.User_Districts_Privileges;

@Repository
public class Form1ADAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	// private static final String SQL_countries = "SELECT * FROM countries where
	// status='active'";

	public List<Form1ADocumentsAvailable> getDocumentsAvailable(String district_id, String cycle_id,
			String financial_year) {
		List<Map<String, Object>> rows = jdbcTemplate
				.queryForList("SELECT * FROM `doc_db_documents` where doc_availability='1' and district_id='"
						+ district_id + "' and cycle_id='" + cycle_id + "' and year='" + financial_year + "'");

		List<Form1ADocumentsAvailable> doclist = new ArrayList<>();

		for (Map<String, Object> row : rows) {// country_id,country_name,status
			Form1ADocumentsAvailable docObj = new Form1ADocumentsAvailable();

			docObj.setDoc_level((String) row.get("doc_level"));
			docObj.setDoc_document((String) row.get("doc_document"));
			docObj.setDoc_source((String) row.get("doc_source"));

			doclist.add(docObj);

		}
		
		return doclist;
	}

	public List<Form1ADocumentsCheclist> getDocumentsChecklist(String district_id, String cycle_id,
			String financial_year) {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT doc_db_check_id,DATE_FORMAT(date_of_verification,'%d-%m-%Y') as `correct_date_of_verification`,date_of_verification,filled_by,verified_by,verified_by_other, completed FROM `doc_db_verify` where district_id='"
						+ district_id + "' and cycle_id='" + cycle_id + "' and year='" + financial_year + "'");

		List<Form1ADocumentsCheclist> doclist = new ArrayList<>();

		for (Map<String, Object> row : rows) {// country_id,country_name,status
			Form1ADocumentsCheclist docObj = new Form1ADocumentsCheclist();

			docObj.setDoc_db_check_id((long) row.get("doc_db_check_id"));
			docObj.setDate_of_verification("" + row.get("correct_date_of_verification"));
			docObj.setFilled_by((String) row.get("filled_by"));
			docObj.setVerified_by((int) row.get("verified_by"));			
			docObj.setVerified_by_other((String) row.get("verified_by_other"));
			docObj.setCompleted((String) row.get("completed"));

			doclist.add(docObj);
		}
		
		return doclist;
	}

	public DeleteForm1AResponse deletedForm1ADetails(String district_id, String cycle_id, String year, String user_id) {

		DeleteForm1AResponse responseobj = new DeleteForm1AResponse();

		
		Object[] params1 = { district_id, cycle_id, year };
		int rows = jdbcTemplate
				.update("DELETE FROM `doc_db_verify` where `district_id`=? and `cycle_id`=? and `year`=?", params1);
		

		// Delete a student record from database where the students
		// id matches with the specified parameter.
		Object[] params = { district_id, cycle_id, year };
		int rows2 = jdbcTemplate
				.update("DELETE FROM `doc_db_documents` where `district_id`=? and `cycle_id`=? and `year`=?", params);
		

		responseobj.setProcessname("deleted");
		if (rows > 1 && rows2 > 1) {
			responseobj.setResponse_val("1");
		} else {
			responseobj.setResponse_val("0");
		}

		return responseobj;
	}

	public Form1AView retrieveForm1ADetails(String district_id, String cycle_id, String year, String user_id) {
		Form1AView obj = new Form1AView();

//		jdbcTemplate.query(sql, objarr, rs -> {
//
//			while (rs.next()) {
//				
//				
//			}
//			/* We can also return any variable-data from here but not used currently */
//			return "";
//		});
		String sql = "SELECt * FROM `doc_db_verify` where `district_id`=? and `cycle_id`=? and `year`=?";
		Object[] params = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql, params, rs -> {

			while (rs.next()) {

				obj.setDate_of_verification(rs.getString("date_of_verification"));
				obj.setFilled_by(rs.getString("filled_by"));
				obj.setVerified_by_name(rs.getString("verified_by"));
				obj.setVerified_by_name_actual_name(rs.getString("verified_by_other"));
				obj.setDoc_db_check_id(rs.getString("doc_db_check_id"));
				obj.setCompleted(rs.getString("completed"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		sql = "SELECT * FROM  `doc_db_documents`  where `district_id`=? and `cycle_id`=? and `year`=?   and  doc_level=? ";
		params = new Object[] { district_id, cycle_id, year, "State level" };

		List<Form1ASaveDocumentsArray> list1 = jdbcTemplate.query(sql, params, rs -> {

			List<Form1ASaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {

				Form1ASaveDocumentsArray tempobj = new Form1ASaveDocumentsArray();

				tempobj.setDocument_val(rs.getString("doc_document"));
				tempobj.setDocument_availability(rs.getString("doc_availability"));
				tempobj.setDocument_source(rs.getString("doc_source"));
				tempobj.setDoc_db_doc_id(rs.getString("doc_db_doc_id"));
				templist.add(tempobj);
			}

			return templist;
		});

		sql = "SELECT * FROM  `doc_db_documents`  where `district_id`=? and `cycle_id`=? and `year`=?   and  doc_level=? ";
		params = new Object[] { district_id, cycle_id, year, "District level" };

		List<Form1ASaveDocumentsArray> list2 = jdbcTemplate.query(sql, params, rs -> {

			List<Form1ASaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {

				Form1ASaveDocumentsArray tempobj = new Form1ASaveDocumentsArray();

				tempobj.setDocument_val(rs.getString("doc_document"));
				tempobj.setDocument_availability(rs.getString("doc_availability"));
				tempobj.setDocument_source(rs.getString("doc_source"));
				tempobj.setDoc_db_doc_id(rs.getString("doc_db_doc_id"));
				templist.add(tempobj);
			}

			return templist;
		});

		sql = "SELECT * FROM  `doc_db_documents`  where `district_id`=? and `cycle_id`=? and `year`=?   and  doc_level=? ";
		params = new Object[] { district_id, cycle_id, year, "Health Department" };

		List<Form1ASaveDocumentsArray> list3 = jdbcTemplate.query(sql, params, rs -> {

			List<Form1ASaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {

				Form1ASaveDocumentsArray tempobj = new Form1ASaveDocumentsArray();

				tempobj.setDocument_val(rs.getString("doc_document"));
				tempobj.setDocument_availability(rs.getString("doc_availability"));
				tempobj.setDocument_source(rs.getString("doc_source"));
				tempobj.setDoc_db_doc_id(rs.getString("doc_db_doc_id"));
				templist.add(tempobj);
			}

			return templist;
		});

		sql = "SELECT * FROM  `doc_db_documents`  where `district_id`=? and `cycle_id`=? and `year`=?   and  doc_level=? ";
		params = new Object[] { district_id, cycle_id, year, "Non-Health Department" };

		List<Form1ASaveDocumentsArray> list4 = jdbcTemplate.query(sql, params, rs -> {

			List<Form1ASaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {

				Form1ASaveDocumentsArray tempobj = new Form1ASaveDocumentsArray();

				tempobj.setDocument_val(rs.getString("doc_document"));
				tempobj.setDocument_availability(rs.getString("doc_availability"));
				tempobj.setDocument_source(rs.getString("doc_source"));
				tempobj.setDoc_db_doc_id(rs.getString("doc_db_doc_id"));
				templist.add(tempobj);
			}

			return templist;
		});

		sql = "SELECT * FROM  `doc_db_documents`  where `district_id`=? and `cycle_id`=? and `year`=?   and  doc_level=? ";
		params = new Object[] { district_id, cycle_id, year, "Private Sector" };

		List<Form1ASaveDocumentsArray> list5 = jdbcTemplate.query(sql, params, rs -> {

			List<Form1ASaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {

				Form1ASaveDocumentsArray tempobj = new Form1ASaveDocumentsArray();

				tempobj.setDocument_val(rs.getString("doc_document"));
				tempobj.setDocument_availability(rs.getString("doc_availability"));
				tempobj.setDocument_source(rs.getString("doc_source"));
				tempobj.setDoc_db_doc_id(rs.getString("doc_db_doc_id"));
				templist.add(tempobj);
			}

			return templist;
		});

		sql = "SELECT * FROM  `doc_db_documents`  where `district_id`=? and `cycle_id`=? and `year`=?   and  doc_level=? ";
		params = new Object[] { district_id, cycle_id, year, "Large scale district level surveys" };

		List<Form1ASaveDocumentsArray> list6 = jdbcTemplate.query(sql, params, rs -> {

			List<Form1ASaveDocumentsArray> templist = new ArrayList<>();
			while (rs.next()) {

				Form1ASaveDocumentsArray tempobj = new Form1ASaveDocumentsArray();

				tempobj.setDocument_val(rs.getString("doc_document"));
				tempobj.setDocument_availability(rs.getString("doc_availability"));
				tempobj.setDocument_source(rs.getString("doc_source"));
				tempobj.setDoc_db_doc_id(rs.getString("doc_db_doc_id"));
				templist.add(tempobj);
			}

			return templist;
		});

		obj.setState_policy_array(list1);
		obj.setDistrict_policy_array(list2);
		obj.setHealth_dept_array(list3);
		obj.setNon_health_dept_array(list4);
		obj.setPrivate_sec_array(list5);
		obj.setLarge_scale_district_array(list6);

		return obj;
	}

	public Form1AView updateEditedForm1AToDb(Form1AEditUpdate model) {

		Form1AView obj = new Form1AView();

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);		

		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		int row = 0;

		String sql2 = "UPDATE `doc_db_verify` SET `date_of_verification`=?,`filled_by`=?,"
				+ "`verified_by`=?,`verified_by_other`=?,`user_id`=?,`record_created`=?,`completed`=? WHERE `doc_db_check_id`=?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2);
			ps.setString(1, model.getDate_of_verification());
			ps.setString(2, model.getFilled_by());
			ps.setString(3, model.getVerified_by_name());
			ps.setString(4, model.getVerified_by_name_actual_name());
			ps.setString(5, model.getUserid());
			ps.setString(6, formatedDateTime);
			ps.setString(7, model.getCompleted());
			ps.setString(8, model.getDoc_db_check_id());
			return ps;
		});		

		obj.setDate_of_verification(model.getDate_of_verification());
		obj.setFilled_by(model.getFilled_by());
		obj.setVerified_by_name(model.getVerified_by_name());
		obj.setVerified_by_name_actual_name(model.getVerified_by_name());
		obj.setDoc_db_check_id(model.getDoc_db_check_id());

//		State policy
		List<Form1ASaveDocumentsArray> list1 = new ArrayList<>();
		List<Form1ASaveDocumentsArray> list1_insert = new ArrayList<>();		

		if (model.getState_policy_array().size() != 0) {

			List<Form1ASaveDocumentsArray> temp = new ArrayList<>();
			temp = model.getState_policy_array();

			for (int i = 0; i < temp.size(); i++) {
				Form1ASaveDocumentsArray temp_obj = temp.get(i);
				String Doc_db_doc_id = temp_obj.getDoc_db_doc_id();

				if (Doc_db_doc_id == null || "".equals(Doc_db_doc_id) || "null".equals(Doc_db_doc_id)) {					
					list1_insert.add(temp_obj);
				} else {					
					list1.add(temp_obj);
				}
			}
			
		}

//		State policy

//		District policy

		List<Form1ASaveDocumentsArray> list2 = new ArrayList<>();
		List<Form1ASaveDocumentsArray> list2_insert = new ArrayList<>();		

		if (model.getDistrict_policy_array().size() != 0) {

			List<Form1ASaveDocumentsArray> temp = new ArrayList<>();
			temp = model.getDistrict_policy_array();

			for (int i = 0; i < temp.size(); i++) {
				Form1ASaveDocumentsArray temp_obj = temp.get(i);
				String Doc_db_doc_id = temp_obj.getDoc_db_doc_id();

				if (Doc_db_doc_id == null || "".equals(Doc_db_doc_id) || "null".equals(Doc_db_doc_id)) {
					list2_insert.add(temp_obj);
				} else {
					list2.add(temp_obj);
				}
			}

		}

//		District policy

//		Health

		List<Form1ASaveDocumentsArray> list3 = new ArrayList<>();
		List<Form1ASaveDocumentsArray> list3_insert = new ArrayList<>();

		if (model.getHealth_dept_array().size() != 0) {

			List<Form1ASaveDocumentsArray> temp = new ArrayList<>();
			temp = model.getHealth_dept_array();

			for (int i = 0; i < temp.size(); i++) {
				Form1ASaveDocumentsArray temp_obj = temp.get(i);
				String Doc_db_doc_id = temp_obj.getDoc_db_doc_id();

				if (Doc_db_doc_id == null || "".equals(Doc_db_doc_id) || "null".equals(Doc_db_doc_id)) {
					list3_insert.add(temp_obj);
				} else {
					list3.add(temp_obj);
				}
			}

			
		}

//		Health

//		Non-Health

		List<Form1ASaveDocumentsArray> list4 = new ArrayList<>();
		List<Form1ASaveDocumentsArray> list4_insert = new ArrayList<>();

		if (model.getNon_health_dept_array().size() != 0) {

			List<Form1ASaveDocumentsArray> temp = new ArrayList<>();
			temp = model.getNon_health_dept_array();

			for (int i = 0; i < temp.size(); i++) {
				Form1ASaveDocumentsArray temp_obj = temp.get(i);
				String Doc_db_doc_id = temp_obj.getDoc_db_doc_id();

				if (Doc_db_doc_id == null || "".equals(Doc_db_doc_id) || "null".equals(Doc_db_doc_id)) {
					list4_insert.add(temp_obj);
				} else {
					list4.add(temp_obj);
				}
			}

			
		}

//		Non-Health

//		Private

		List<Form1ASaveDocumentsArray> list5 = new ArrayList<>();
		List<Form1ASaveDocumentsArray> list5_insert = new ArrayList<>();

		if (model.getPrivate_sec_array().size() != 0) {

			List<Form1ASaveDocumentsArray> temp = new ArrayList<>();
			temp = model.getPrivate_sec_array();

			for (int i = 0; i < temp.size(); i++) {
				Form1ASaveDocumentsArray temp_obj = temp.get(i);
				String Doc_db_doc_id = temp_obj.getDoc_db_doc_id();

				if (Doc_db_doc_id == null || "".equals(Doc_db_doc_id) || "null".equals(Doc_db_doc_id)) {
					list5_insert.add(temp_obj);
				} else {
					list5.add(temp_obj);
				}
			}
			
		}

//		Private

//		Large Scale

		List<Form1ASaveDocumentsArray> list6 = new ArrayList<>();
		List<Form1ASaveDocumentsArray> list6_insert = new ArrayList<>();
		if (model.getLarge_scale_district_array().size() != 0) {

			List<Form1ASaveDocumentsArray> temp = new ArrayList<>();
			temp = model.getLarge_scale_district_array();

			for (int i = 0; i < temp.size(); i++) {
				Form1ASaveDocumentsArray temp_obj = temp.get(i);
				String Doc_db_doc_id = temp_obj.getDoc_db_doc_id();

				if (Doc_db_doc_id == null || "".equals(Doc_db_doc_id) || "null".equals(Doc_db_doc_id)) {
					list6_insert.add(temp_obj);
				} else {
					list6.add(temp_obj);
				}
			}
			
		}

//		Large Scale

		obj.setState_policy_array(list1);
		obj.setDistrict_policy_array(list2);
		obj.setHealth_dept_array(list3);
		obj.setNon_health_dept_array(list4);
		obj.setPrivate_sec_array(list5);
		obj.setLarge_scale_district_array(list6);

		// select `doc_db_check_id` from `doc_db_documents` where district_id=1 and
		// cycle_id=1 and year='2019';

		// Select Query to perform insert on Primary Key
		String sql_select = "SELECT `doc_db_check_id` FROM  `doc_db_documents` where  `district_id`=? and `cycle_id`=? and `year`=?  ";

		Object[] params_select = new Object[] { model.getDistrict(), model.getCycle(), model.getYear() };

		String temp_list_insert_p_key = jdbcTemplate.query(sql_select, params_select, rs -> {

			String list_insert_p_key = "";

			while (rs.next()) {
				list_insert_p_key = rs.getString("doc_db_check_id");
			}
			return list_insert_p_key;
		});
		// End of Select Query to perform insert on Primary Key

		// If Insertion is Performed method
		if (list1_insert.size() != 0) {

			// Batch Insert performed from here

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + temp_list_insert_p_key);
							ps.setString(2, "State level");
							ps.setString(3, list1_insert.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list1_insert.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list1_insert.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list1_insert.size();
						}

					});
			// Batch Insert
		}
		// End of If Insertion is Performed method

		jdbcTemplate
				.batchUpdate(
						"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
								+ "`doc_source`=?,`user_id`=?," + "`record_created`=? WHERE `doc_db_doc_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list1.get(i).getDocument_val());

								int result = 2;
								if ("1".equals(list1.get(i).getDocument_availability())) {
									result = 1;
								}

								ps.setString(2, result + "");
								ps.setString(3, list1.get(i).getDocument_source());
								ps.setString(4, model.getUserid());
								ps.setString(5, formatedDateTime);
								ps.setString(6, list1.get(i).getDoc_db_doc_id());

							}

							public int getBatchSize() {
								return list1.size();
							}

						});

		// If Insertion is Performed method
		if (list2_insert.size() != 0) {

			// Batch Insert performed from here

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + temp_list_insert_p_key);
							ps.setString(2, "District level");
							ps.setString(3, list2_insert.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list2_insert.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list2_insert.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list2_insert.size();
						}

					});
			// Batch Insert
		}
		// End of If Insertion is Performed method

		jdbcTemplate
				.batchUpdate(
						"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
								+ "`doc_source`=?,`user_id`=?," + "`record_created`=? WHERE `doc_db_doc_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list2.get(i).getDocument_val());

								int result = 2;
								if ("1".equals(list2.get(i).getDocument_availability())) {
									result = 1;
								}

								ps.setString(2, result + "");
								ps.setString(3, list2.get(i).getDocument_source());
								ps.setString(4, model.getUserid());
								ps.setString(5, formatedDateTime);
								ps.setString(6, list2.get(i).getDoc_db_doc_id());

							}

							public int getBatchSize() {
								return list2.size();
							}

						});

		// If Insertion is Performed method
		if (list3_insert.size() != 0) {

			// Batch Insert performed from here

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + temp_list_insert_p_key);
							ps.setString(2, "Health Department");
							ps.setString(3, list3_insert.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list3_insert.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list3_insert.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list3_insert.size();
						}

					});
			// Batch Insert
		}

		// End of If Insertion is Performed method

		jdbcTemplate
				.batchUpdate(
						"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
								+ "`doc_source`=?,`user_id`=?," + "`record_created`=? WHERE `doc_db_doc_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list3.get(i).getDocument_val());

								int result = 2;
								if ("1".equals(list3.get(i).getDocument_availability())) {
									result = 1;
								}

								ps.setString(2, result + "");
								ps.setString(3, list3.get(i).getDocument_source());
								ps.setString(4, model.getUserid());
								ps.setString(5, formatedDateTime);
								ps.setString(6, list3.get(i).getDoc_db_doc_id());

							}

							public int getBatchSize() {
								return list3.size();
							}

						});

		// If Insertion is Performed method

		if (list4_insert.size() != 0) {

			// Batch Insert performed from here

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + temp_list_insert_p_key);
							ps.setString(2, "Non-Health Department");
							ps.setString(3, list4_insert.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list4_insert.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list4_insert.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list4_insert.size();
						}

					});
			// Batch Insert
		}

		// End of If Insertion is Performed method

		jdbcTemplate
				.batchUpdate(
						"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
								+ "`doc_source`=?,`user_id`=?," + "`record_created`=? WHERE `doc_db_doc_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list4.get(i).getDocument_val());

								int result = 2;
								if ("1".equals(list4.get(i).getDocument_availability())) {
									result = 1;
								}

								ps.setString(2, result + "");
								ps.setString(3, list4.get(i).getDocument_source());
								ps.setString(4, model.getUserid());
								ps.setString(5, formatedDateTime);
								ps.setString(6, list4.get(i).getDoc_db_doc_id());

							}

							public int getBatchSize() {
								return list4.size();
							}

						});

		// If Insertion is Performed method

		if (list5_insert.size() != 0) {

			// Batch Insert performed from here

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + temp_list_insert_p_key);
							ps.setString(2, "Private Sector");
							ps.setString(3, list5_insert.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list5_insert.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list5_insert.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list5_insert.size();
						}

					});
			// Batch Insert
		}

		// End of If Insertion is Performed method

		jdbcTemplate
				.batchUpdate(
						"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
								+ "`doc_source`=?,`user_id`=?," + "`record_created`=? WHERE `doc_db_doc_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list5.get(i).getDocument_val());

								int result = 2;
								if ("1".equals(list5.get(i).getDocument_availability())) {
									result = 1;
								}

								ps.setString(2, result + "");
								ps.setString(3, list5.get(i).getDocument_source());
								ps.setString(4, model.getUserid());
								ps.setString(5, formatedDateTime);
								ps.setString(6, list5.get(i).getDoc_db_doc_id());

							}

							public int getBatchSize() {
								return list5.size();
							}

						});

		// If Insertion is Performed method

		if (list6_insert.size() != 0) {

			// Batch Insert performed from here

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setString(1, "" + temp_list_insert_p_key);
							ps.setString(2, "Large scale district level surveys");
							ps.setString(3, list6_insert.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list6_insert.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list6_insert.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list6_insert.size();
						}

					});
			// Batch Insert

		}

		// End of If Insertion is Performed method

		jdbcTemplate
				.batchUpdate(
						"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
								+ "`doc_source`=?,`user_id`=?," + "`record_created`=? WHERE `doc_db_doc_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list6.get(i).getDocument_val());

								int result = 2;
								if ("1".equals(list6.get(i).getDocument_availability())) {
									result = 1;
								}

								ps.setString(2, result + "");
								ps.setString(3, list6.get(i).getDocument_source());
								ps.setString(4, model.getUserid());
								ps.setString(5, formatedDateTime);
								ps.setString(6, list6.get(i).getDoc_db_doc_id());

							}

							public int getBatchSize() {
								return list6.size();
							}

						});

		return obj;

	}

	public Form1ASendAllDataToAndroidBean retrieveForm1A_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(
			String country_id, String region_id, String state_id, String district_id, String user_id,
			String LoggedinUserId) { 

		Form1ASendAllDataToAndroidBean result = new Form1ASendAllDataToAndroidBean();

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
			List<Form1ADocumentsTableDataBean> list_docs = new ArrayList<>();

			list_docs.addAll(new ArrayList<>());
			
			result.setFormA(new ArrayList<>());

			result.setFormAdocument(list_docs);

			result.setError_code("200");
			result.setMessage("Sending formA data");
			
		

			return result;
		}
		else {
			System.out.println("Not Returning in half!!! 0 or different value rather than -1");
		}
		
		
		

		String sql = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql = "SELECT form1a.`doc_db_check_id`,  form1a.`date_of_verification`, form1a.`filled_by`, "
					+ " form1a.`verified_by`, form1a.`verified_by_other`, form1a.`district_id` as `district_id`, d.`district_id` as `district_id2`, d.`country_id`,  d.`district_name`,  d.`state_id`, "
					+ " form1a.`cycle_id`,  form1a.`year`, form1a.`user_id`,  form1a.`record_created`, form1a.`completed`,  form1a.`time_stamp`,  cs.`cs_id`,  cs.`state_name`, "
					+ " cs.`region_id`  FROM `doc_db_verify` form1a left join  `district` d on form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=? and  d.`country_id`=?   and  form1a.`year` >= 2019";
				
		}
		else {
			sql = "SELECT form1a.`doc_db_check_id`,  form1a.`date_of_verification`, form1a.`filled_by`, "
					+ " form1a.`verified_by`, form1a.`verified_by_other`, form1a.`district_id` as `district_id`, d.`district_id` as `district_id2`, d.`country_id`,  d.`district_name`,  d.`state_id`, "
					+ " form1a.`cycle_id`,  form1a.`year`, form1a.`user_id`,  form1a.`record_created`, form1a.`completed`,  form1a.`time_stamp`,  cs.`cs_id`,  cs.`state_name`, "
					+ " cs.`region_id`  FROM `doc_db_verify` form1a left join  `district` d on form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=? and  d.`country_id`=?   and  form1a.`year` >= 2019  and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+");";

		}
		
		
		Object[] params = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1APrimaryTableDataBean> list_pri = jdbcTemplate.query(sql, params, rs -> {

			List<Form1APrimaryTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1APrimaryTableDataBean tempobj = new Form1APrimaryTableDataBean();
				tempobj.setAuto_id(rs.getString("doc_db_check_id"));
				tempobj.setMeetingdate("" + rs.getString("date_of_verification"));
				tempobj.setMeetingvenue("" + rs.getString("filled_by"));
				tempobj.setChairpersonid("" + rs.getString("verified_by"));
				tempobj.setOtherchairperson("" + rs.getString("verified_by_other"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrict_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setCompleted("" + rs.getString("completed"));
				temp_list.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		result.setFormA(list_pri);
		
		String sql2 = "";
		
		
		if("0".equals(user_priv.getAll_districts())) {
			
			 sql2 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
						+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
						+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
						+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
						+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
						+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
						+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
						+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019 ";
		}
		else {

			 sql2 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
						+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
						+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
						+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
						+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
						+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
						+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
						+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019    and form1a.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+")";
		}
		 

		Object[] params2 = new Object[] { district_id, state_id, region_id, country_id, "State level" };

		List<Form1ADocumentsTableDataBean> list_docs_stl = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("STL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});
		
		
		String sql3 = ""; //  and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+")

		if("0".equals(user_priv.getAll_districts())) {
			sql3 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?    and  form1a.`year` >= 2019";
		}
		else {
			sql3 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?    and  form1a.`year` >= 2019  and form1a.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+")";
		}
		
		 

		Object[] params3 = new Object[] { district_id, state_id, region_id, country_id, "District level" };

		List<Form1ADocumentsTableDataBean> list_docs_dtl = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("DTL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});
		
		
		String sql4 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql4 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?    and  form1a.`year` >= 2019";

		}
		else {
			sql4 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?    and  form1a.`year` >= 2019   and form1a.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+")";

		}

		
		Object[] params4 = new Object[] { district_id, state_id, region_id, country_id, "Health Department" };

		List<Form1ADocumentsTableDataBean> list_docs_healthl = jdbcTemplate.query(sql4, params4, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("HEALTHL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});
		
		
		String sql5 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql5 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019";
		}
		else {
			sql5 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019  and form1a.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+")";
		}

		

		Object[] params5 = new Object[] { district_id, state_id, region_id, country_id, "Non-Health Department" };

		List<Form1ADocumentsTableDataBean> list_docs_nonhealthl = jdbcTemplate.query(sql5, params5, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("NHEALTHL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});
		
		
		String sql6 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql6 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019";
		}
		else {
			sql6 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019   and form1a.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+")";
		}

		

		Object[] params6 = new Object[] { district_id, state_id, region_id, country_id, "Private Sector" };

		List<Form1ADocumentsTableDataBean> list_docs_pvtl = jdbcTemplate.query(sql6, params6, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("PVTL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});
		
		String sql7 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql7 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019";
		}
		else {
			sql7 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
					+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
					+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
					+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
					+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
					+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
					+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019    and form1a.`district_id` IN ("+user_priv.getAll_districts()+")  and form1a.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form1a.`year` IN ("+user_priv.getAll_years()+")";
		}

		

		Object[] params7 = new Object[] { district_id, state_id, region_id, country_id,
				"Large scale district level surveys" };

		List<Form1ADocumentsTableDataBean> list_docs_lsdl = jdbcTemplate.query(sql7, params7, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("LSDL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		List<Form1ADocumentsTableDataBean> list_docs = new ArrayList<>();

		list_docs.addAll(list_docs_stl);

		list_docs.addAll(list_docs_dtl);

		list_docs.addAll(list_docs_healthl);

		list_docs.addAll(list_docs_nonhealthl);

		list_docs.addAll(list_docs_pvtl);

		list_docs.addAll(list_docs_lsdl);

		result.setFormAdocument(list_docs);

		result.setError_code("200");
		result.setMessage("Sending formA data");

		return result;
	}

	public Form1ASendAllDataToAndroidBean retrieveForm1A_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(
			String country_id, String region_id, String state_id, String district_id, String user_id) {

		Form1ASendAllDataToAndroidBean result = new Form1ASendAllDataToAndroidBean();


		String sql = "SELECT form1a.`doc_db_check_id`,  form1a.`date_of_verification`, form1a.`filled_by`, "
				+ " form1a.`verified_by`, form1a.`verified_by_other`, form1a.`district_id` as `district_id`, d.`district_id` as `district_id2`, d.`country_id`,  d.`district_name`,  d.`state_id`, "
				+ " form1a.`cycle_id`,  form1a.`year`, form1a.`user_id`,  form1a.`record_created`, form1a.`completed`,  form1a.`time_stamp`,  cs.`cs_id`,  cs.`state_name`, "
				+ " cs.`region_id`  FROM `doc_db_verify` form1a left join  `district` d on form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=? and  d.`country_id`=?   and  form1a.`year` >= 2019";

		Object[] params = new Object[] { district_id, state_id, region_id, country_id };

		List<Form1APrimaryTableDataBean> list_pri = jdbcTemplate.query(sql, params, rs -> {

			List<Form1APrimaryTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1APrimaryTableDataBean tempobj = new Form1APrimaryTableDataBean();
				tempobj.setAuto_id(rs.getString("doc_db_check_id"));
				tempobj.setMeetingdate("" + rs.getString("date_of_verification"));
				tempobj.setMeetingvenue("" + rs.getString("filled_by"));
				tempobj.setChairpersonid("" + rs.getString("verified_by"));
				tempobj.setOtherchairperson("" + rs.getString("verified_by_other"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrict_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setCompleted(rs.getString("completed"));
				tempobj.setDatafrom("WEB");
				tempobj.setUser_id("" + rs.getString("user_id"));

				temp_list.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		result.setFormA(list_pri);

		String sql2 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
				+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
				+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
				+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
				+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
				+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
				+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019";

		Object[] params2 = new Object[] { district_id, state_id, region_id, country_id, "State level" };

		List<Form1ADocumentsTableDataBean> list_docs_stl = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("STL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql3 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
				+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
				+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
				+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
				+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
				+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
				+ "  form1a.`doc_level`=?    and  form1a.`year` >= 2019";

		Object[] params3 = new Object[] { district_id, state_id, region_id, country_id, "District level" };

		List<Form1ADocumentsTableDataBean> list_docs_dtl = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("DTL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql4 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
				+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
				+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
				+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
				+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
				+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
				+ "  form1a.`doc_level`=?    and  form1a.`year` >= 2019";

		Object[] params4 = new Object[] { district_id, state_id, region_id, country_id, "Health Department" };

		List<Form1ADocumentsTableDataBean> list_docs_healthl = jdbcTemplate.query(sql4, params4, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("HEALTHL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql5 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
				+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
				+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
				+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
				+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
				+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
				+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019";

		Object[] params5 = new Object[] { district_id, state_id, region_id, country_id, "Non-Health Department" };

		List<Form1ADocumentsTableDataBean> list_docs_nonhealthl = jdbcTemplate.query(sql5, params5, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("NHEALTHL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql6 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
				+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
				+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
				+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
				+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
				+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
				+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019";

		Object[] params6 = new Object[] { district_id, state_id, region_id, country_id, "Private Sector" };

		List<Form1ADocumentsTableDataBean> list_docs_pvtl = jdbcTemplate.query(sql6, params6, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("PVTL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql7 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
				+ " form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  "
				+ " form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  "
				+ " form1a.`record_created`,  form1a.`time_stamp`, d.`country_id`, d.`state_id` , "
				+ " cs.`region_id` FROM  `doc_db_documents` form1a left join  `district` d on "
				+ " form1a.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ " where form1a.`district_id`=?    and d.state_id=?  and  cs.`region_id`=? and   d.country_id=? and "
				+ "  form1a.`doc_level`=?   and  form1a.`year` >= 2019";

		Object[] params7 = new Object[] { district_id, state_id, region_id, country_id,
				"Large scale district level surveys" };

		List<Form1ADocumentsTableDataBean> list_docs_lsdl = jdbcTemplate.query(sql7, params7, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("LSDL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				tempobj.setCountry_id("" + rs.getString("country_id"));
				tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		List<Form1ADocumentsTableDataBean> list_docs = new ArrayList<>();

		list_docs.addAll(list_docs_stl);

		list_docs.addAll(list_docs_dtl);

		list_docs.addAll(list_docs_healthl);

		list_docs.addAll(list_docs_nonhealthl);

		list_docs.addAll(list_docs_pvtl);

		list_docs.addAll(list_docs_lsdl);

		result.setFormAdocument(list_docs);

		result.setError_code("200");
		result.setMessage("Sending formA data");

		return result;
	}

	public void sendFor1ASaveForExistingDistrictCycleYear(String district_id, String cycle_id, String year,
			String country, String province, Form1ASendAllDataToAndroidBean result) {
		

		String sql = "SELECT form1a.`doc_db_check_id`,  form1a.`date_of_verification`, form1a.`filled_by`, "
				+ "  form1a.`verified_by`, form1a.`verified_by_other`, form1a.`district_id` as `district_id`, 	"
				+ "  form1a.`cycle_id`,  form1a.`year`, form1a.`user_id`,  form1a.`record_created`, form1a.`completed`, "
				+ "   form1a.`time_stamp`  FROM `doc_db_verify` form1a  where form1a.`district_id`=? and form1a.`cycle_id`=?  and  form1a.`year`=?";

		Object[] params = new Object[] { district_id, cycle_id, year };

		List<Form1APrimaryTableDataBean> list_pri = jdbcTemplate.query(sql, params, rs -> {

			List<Form1APrimaryTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1APrimaryTableDataBean tempobj = new Form1APrimaryTableDataBean();
				tempobj.setAuto_id(rs.getString("doc_db_check_id"));
				tempobj.setMeetingdate("" + rs.getString("date_of_verification"));
				tempobj.setMeetingvenue("" + rs.getString("filled_by"));
				tempobj.setChairpersonid("" + rs.getString("verified_by"));
				tempobj.setOtherchairperson("" + rs.getString("verified_by_other"));
				// tempobj.setCountry_id("" + rs.getString("country_id"));
				// tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrict_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setCompleted(rs.getString("completed"));
				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				temp_list.add(tempobj);
			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		result.setFormA(list_pri);

		String sql2 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  "
				+ " form1a.`doc_document`, form1a.`doc_availability`,  "
				+ "  form1a.`doc_source`,  form1a.`district_id`, form1a.`cycle_id`, "
				+ "  form1a.`year`,  form1a.`user_id`,  "
				+ "	 form1a.`record_created`,  form1a.`time_stamp` FROM  `doc_db_documents` form1a  where form1a.`district_id`=? and  form1a.`year`=? and form1a.cycle_id=?   and   form1a.`doc_level`=? ";

		Object[] params2 = new Object[] { district_id, year, cycle_id, "State level" };

		List<Form1ADocumentsTableDataBean> list_docs_stl = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("STL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				// tempobj.setCountry_id("" + rs.getString("country_id"));
				// tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql3 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,   form1a.`doc_document`, form1a.`doc_availability`,  "
				+ "   form1a.`doc_source`,   form1a.`district_id`, form1a.`cycle_id`,"
				+ "   form1a.`year`,  form1a.`user_id`,   form1a.`record_created`,  form1a.`time_stamp` FROM  `doc_db_documents` form1a  "
				+ "   where form1a.`district_id`=? and form1a.`cycle_id`=? and  form1a.`year` =?  and form1a.`doc_level`=?    ";

		Object[] params3 = new Object[] { district_id, cycle_id, year, "District level" };

		List<Form1ADocumentsTableDataBean> list_docs_dtl = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("DTL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				/// tempobj.setCountry_id("" + rs.getString("country_id"));
				/// tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql4 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  form1a.`doc_document`, form1a.`doc_availability`, "
				+ " form1a.`doc_source`,  form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`, "
				+ " form1a.`user_id`,  form1a.`record_created`,  form1a.`time_stamp` FROM  `doc_db_documents` form1a  where form1a.`district_id`=?  "
				+ "  and form1a.`cycle_id`=? and  form1a.`year`=? and   form1a.`doc_level`=?    ";

		Object[] params4 = new Object[] { district_id, cycle_id, year, "Health Department" };

		List<Form1ADocumentsTableDataBean> list_docs_healthl = jdbcTemplate.query(sql4, params4, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("HEALTHL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				// tempobj.setCountry_id("" + rs.getString("country_id"));
				// tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql5 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  form1a.`doc_document`,  "
				+ "    form1a.`doc_availability`,  form1a.`doc_source`,  form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  "
				+ "    form1a.`user_id`,   form1a.`record_created`,  form1a.`time_stamp` FROM  `doc_db_documents` form1a    where form1a.`district_id`=?  "
				+ "   and form1a.`cycle_id`=? and  form1a.`year`=? and   form1a.`doc_level`=? ";

		Object[] params5 = new Object[] { district_id, cycle_id, year, "Non-Health Department" };

		List<Form1ADocumentsTableDataBean> list_docs_nonhealthl = jdbcTemplate.query(sql5, params5, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("NHEALTHL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				// tempobj.setCountry_id("" + rs.getString("country_id"));
				// tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql6 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  form1a.`doc_document`, form1a.`doc_availability`, "
				+ "  form1a.`doc_source`,   form1a.`district_id`, form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`, "
				+ "  form1a.`record_created`,  form1a.`time_stamp` FROM  `doc_db_documents` form1a   where form1a.`district_id`=? "
				+ "   and form1a.`cycle_id`=?  and form1a.`year`=? and  form1a.`doc_level`=?";

		Object[] params6 = new Object[] { district_id, cycle_id, year, "Private Sector" };

		List<Form1ADocumentsTableDataBean> list_docs_pvtl = jdbcTemplate.query(sql6, params6, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("PVTL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				// tempobj.setCountry_id("" + rs.getString("country_id"));
				// tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		String sql7 = "SELECT form1a.`doc_db_doc_id`,  form1a.`doc_db_check_id`, form1a.`doc_level`,  form1a.`doc_document`, form1a.`doc_availability`,  form1a.`doc_source`,  form1a.`district_id`,"
				+ "  form1a.`cycle_id`,   form1a.`year`,  form1a.`user_id`,  form1a.`record_created`, "
				+ "  form1a.`time_stamp` FROM  `doc_db_documents` form1a   where form1a.`district_id`=?  and form1a.`cycle_id`=? "
				+ "  and  form1a.`year`=?   and   form1a.`doc_level`=? ";

		Object[] params7 = new Object[] { district_id, cycle_id, year, "Large scale district level surveys" };

		List<Form1ADocumentsTableDataBean> list_docs_lsdl = jdbcTemplate.query(sql7, params7, rs -> {

			List<Form1ADocumentsTableDataBean> temp_list = new ArrayList<>();

			while (rs.next()) {
				Form1ADocumentsTableDataBean tempobj = new Form1ADocumentsTableDataBean();

				tempobj.setAuto_id("" + rs.getString("doc_db_doc_id"));
				tempobj.setMeetingwith_id("" + rs.getString("doc_db_check_id"));
				tempobj.setDocumentlevel("LSDL");
				tempobj.setDocument("" + rs.getString("doc_document"));
				tempobj.setSourceavailibility("" + rs.getString("doc_availability"));
				tempobj.setDocumentsource("" + rs.getString("doc_source"));
				// tempobj.setCountry_id("" + rs.getString("country_id"));
				// tempobj.setProvince_id("" + rs.getString("state_id"));
				tempobj.setDistrct_id("" + rs.getString("district_id"));
				tempobj.setCycle_id("" + rs.getString("cycle_id"));
				tempobj.setYear("" + rs.getString("year"));
				tempobj.setUser_id("" + rs.getString("user_id"));
				tempobj.setRecordcreated("" + rs.getString("record_created"));
				tempobj.setTimestamp("" + rs.getString("time_stamp"));
				tempobj.setDatafrom("WEB");

				tempobj.setCountry_id(country);
				tempobj.setProvince_id(province);

				temp_list.add(tempobj);

			}
			/* We can also return any variable-data from here but not used currently */
			return temp_list;
		});

		List<Form1ADocumentsTableDataBean> list_docs = new ArrayList<>();

		list_docs.addAll(list_docs_stl);

		list_docs.addAll(list_docs_dtl);

		list_docs.addAll(list_docs_healthl);

		list_docs.addAll(list_docs_nonhealthl);

		list_docs.addAll(list_docs_pvtl);

		list_docs.addAll(list_docs_lsdl);

		result.setFormAdocument(list_docs);

		result.setError_code("200");
		result.setMessage("Sending formA data");

		// return result;

	}

	public Form1ASendAllDataToAndroidBean consumeForm1ASaveAndUpdateData(AllFormsDataFetchFromAndroidBean bean) {

		Form1ASendAllDataToAndroidBean result = new Form1ASendAllDataToAndroidBean();

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);
		

		Form1AConsumeDataFromAndroidBean model = bean.getForm1a();

		List<Form1APrimaryTableDataBean> list_pri = model.getFormA();

		List<Form1APrimaryTableDataBean> list_pri_app = new ArrayList<>();
		List<Form1APrimaryTableDataBean> list_pri_web = new ArrayList<>();

		for (int i = 0; i < list_pri.size(); i++) {

			Form1APrimaryTableDataBean obj = list_pri.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_pri_app.add(obj);
			} else {
				list_pri_web.add(obj);
			}

		}		

		List<Form1ADocumentsTableDataBean> list_docs = model.getFormAdocument();

		List<Form1ADocumentsTableDataBean> list_docs_app = new ArrayList<>();
		List<Form1ADocumentsTableDataBean> list_docs_web = new ArrayList<>();

		for (int i = 0; i < list_docs.size(); i++) {

			Form1ADocumentsTableDataBean obj = list_docs.get(i);

			if ("APP".equals(obj.getDatafrom())) {
				list_docs_app.add(obj);
			} else {
				list_docs_web.add(obj);
			}

		}
		
		int row = 0;
	
		List<Form1APrimaryTableDataBean> formA = new ArrayList<>();
		List<Form1ADocumentsTableDataBean> formAdocument = new ArrayList<>();

		HashMap<String, String> mymap = new HashMap<>();
		List<Form1ASourceIDDetailsBean> mapping = new ArrayList<>();

		if (list_pri_app.size() != 0) {

			for (int x = 0; x < list_pri_app.size(); x++) {

				Form1APrimaryTableDataBean model_obj = list_pri_app.get(x);

				Form1APrimaryTableDataBean obj_app_to_add = new Form1APrimaryTableDataBean();

				List<Form1ADocumentsTableDataBean> list_docs_match_app = new ArrayList<>();

				/*-----------------------------------------------*/

				String check_sql = "SELECt * FROM `doc_db_verify` where `district_id`=? and `cycle_id`=? and `year`=?";
				Object[] check_params = new Object[] { model_obj.getDistrict_id(), model_obj.getCycle_id(),
						model_obj.getYear() };

				String myflag = jdbcTemplate.query(check_sql, check_params, rs -> {

					String check = "0";

					while (rs.next()) {
						check = rs.getString("date_of_verification");
					}
					/* We can also return any variable-data from here but not used currently */
					return "" + check;
				});

				if ("0".equals(myflag)) {

				} else {

					/*********************** New Codes to be Added ********************************/

					

					sendFor1ASaveForExistingDistrictCycleYear(model_obj.getDistrict_id(), model_obj.getCycle_id(),
							model_obj.getYear(), model_obj.getCountry_id(), model_obj.getProvince_id(), result);

					result.setMapping(mapping);
					return result;

					/********************************************************/

					// continue;
				}

				

				/*-----------------------------------------------*/

				String sql1 = "INSERT INTO `doc_db_verify`(`date_of_verification`, `filled_by`, `verified_by`, "
						+ "		`verified_by_other`, `district_id`, `cycle_id`, `year`, `user_id`, "
						+ "		`record_created`,`completed`) VALUES(?,?,?,?,?,?,?,?,?,?) ";

				KeyHolder keyHolder = new GeneratedKeyHolder();

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, model_obj.getMeetingdate());
					obj_app_to_add.setMeetingdate(model_obj.getMeetingdate());

					ps.setString(2, model_obj.getMeetingvenue());
					obj_app_to_add.setMeetingvenue(model_obj.getMeetingvenue());

					ps.setString(3, model_obj.getChairpersonid());
					obj_app_to_add.setChairpersonid(model_obj.getChairpersonid());

					ps.setString(4, model_obj.getOtherchairperson());
					obj_app_to_add.setOtherchairperson(model_obj.getOtherchairperson());

					ps.setString(5, model_obj.getDistrict_id());
					obj_app_to_add.setDistrict_id(model_obj.getDistrict_id());

					ps.setString(6, model_obj.getCycle_id());
					obj_app_to_add.setCycle_id(model_obj.getCycle_id());

					ps.setString(7, model_obj.getYear());
					obj_app_to_add.setYear(model_obj.getYear());

					ps.setString(8, model_obj.getUser_id());
					obj_app_to_add.setUser_id(model_obj.getUser_id());

					ps.setString(9, formatedDateTime);
					obj_app_to_add.setRecordcreated(formatedDateTime);
					
					ps.setString(10, model_obj.getCompleted());
					obj_app_to_add.setRecordcreated(formatedDateTime);
					
					return ps;
				}, keyHolder);

				// ResultSet rs = ps.getGeneratedKeys();

				long p_key = keyHolder.getKey().longValue();

				obj_app_to_add.setAuto_id("" + p_key);
				obj_app_to_add.setDatafrom("WEB");

				obj_app_to_add.setCountry_id(model_obj.getCountry_id());

				obj_app_to_add.setProvince_id(model_obj.getProvince_id());

				DateTimeFormatter format2 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

				String formatedDateTime2 = current.format(format2);

				obj_app_to_add.setTimestamp(formatedDateTime2);

				formA.add(obj_app_to_add);

				
				if (row > 0) {

				
					for (int y = 0; y < list_docs_app.size(); y++) {

						Form1ADocumentsTableDataBean tempobj = list_docs_app.get(y);

						Form1ADocumentsTableDataBean tempobj_2 = list_docs_app.get(y);

						

						if (model_obj.getAuto_id().equals(tempobj.getMeetingwith_id())) {

							Form1BConsumeDataFromAndroidBean form1b_all_obj = bean.getForm1b();

							if (form1b_all_obj.getFormB() == null) {
								form1b_all_obj = new Form1BConsumeDataFromAndroidBean();
							}
							List<Form1BPrimaryTableDataBean> form1b_lists = form1b_all_obj.getFormB();

							if (form1b_lists == null) {
								form1b_lists = new ArrayList<>();
							}
							Form1ASourceIDDetailsBean sourceidmap = new Form1ASourceIDDetailsBean();

							
							if ("STL".equals(tempobj.getDocumentlevel())) {

								

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setLong(1, p_key);
									ps.setString(2, "State level");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj_2.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());
								sourceidmap.setForm1bfillcontinously("1");

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + p_key);
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							} else if ("DTL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setLong(1, p_key);
									ps.setString(2, "District level");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + p_key);
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							} else if ("HEALTHL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setLong(1, p_key);
									ps.setString(2, "Health Department");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + p_key);
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							} else if ("NHEALTHL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setLong(1, p_key);
									ps.setString(2, "Non-Health Department");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + p_key);
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							} else if ("PVTL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setLong(1, p_key);
									ps.setString(2, "Private Sector");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + p_key);
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							} else if ("LSDL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setLong(1, p_key);
									ps.setString(2, "Large scale district level surveys");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + p_key);
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							}

						} else {
							System.out.println("No Auto-Id matches ... ");
						}
						// End of if both App ids are equal
					} // End of list_docs_app for loop

				} // End of row>0

			} // End of Loop of APP List

		} // End of list_pri_web.size() != 0 means New Save data from App

		
		if (list_pri_web.size() != 0) {

			for (int x = 0; x < list_pri_web.size(); x++) {

				Form1APrimaryTableDataBean model_obj = list_pri_web.get(x);

				String sql2 = "UPDATE `doc_db_verify` SET `date_of_verification`=?,`filled_by`=?,"
						+ "`verified_by`=?,`verified_by_other`=?,`user_id`=?,"
						+ "`record_created`=?,"+ "`completed`=?  WHERE `doc_db_check_id`=?";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql2);
					ps.setString(1, model_obj.getMeetingdate());
					ps.setString(2, model_obj.getMeetingvenue());
					ps.setString(3, model_obj.getChairpersonid());
					ps.setString(4, model_obj.getOtherchairperson());
					ps.setString(5, model_obj.getUser_id());
					ps.setString(6, formatedDateTime);
					ps.setString(7, model_obj.getCompleted());
					ps.setString(8, model_obj.getAuto_id());
					return ps;
				});

				formA.add(model_obj);

				
				if (row > 0) {

					for (int pos = 0; pos < list_docs_app.size(); pos++) {

						Form1ADocumentsTableDataBean tempobj = list_docs_app.get(pos);

						if (model_obj.getAuto_id().equals(tempobj.getMeetingwith_id())
								&& "WEB".equals(model_obj.getDatafrom()) && "APP".equals(tempobj.getDatafrom())) {

							Form1BConsumeDataFromAndroidBean form1b_all_obj = bean.getForm1b();

							if (form1b_all_obj.getFormB() == null) {
								form1b_all_obj = new Form1BConsumeDataFromAndroidBean();
							}
							List<Form1BPrimaryTableDataBean> form1b_lists = form1b_all_obj.getFormB();

							if (form1b_lists == null) {
								form1b_lists = new ArrayList<>();
							}
							Form1ASourceIDDetailsBean sourceidmap = new Form1ASourceIDDetailsBean();

							/*******************************************/

							if ("STL".equals(tempobj.getDocumentlevel())) {

								

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, model_obj.getAuto_id());
									ps.setString(2, "State level");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + model_obj.getAuto_id());
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							} // If docs is of type STL

							/*******************************************/
							else if ("DTL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, model_obj.getAuto_id());
									ps.setString(2, "District level");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + model_obj.getAuto_id());
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							} // End of type
							/*********************************************************/

							else if ("HEALTHL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, model_obj.getAuto_id());
									ps.setString(2, "Health Department");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + model_obj.getAuto_id());
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							}

							/*********************************************************/

							else if ("NHEALTHL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, model_obj.getAuto_id());
									ps.setString(2, "Non-Health Department");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + model_obj.getAuto_id());
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							}

							/*********************************************************/

							else if ("PVTL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, model_obj.getAuto_id());
									ps.setString(2, "Private Sector");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + model_obj.getAuto_id());
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							}

							/********************************************************/

							else if ("LSDL".equals(tempobj.getDocumentlevel())) {

								String sqltemp = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
										+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
										+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

								KeyHolder keyHolder_new = new GeneratedKeyHolder();

								row = jdbcTemplate.update(connection -> {
									PreparedStatement ps = connection.prepareStatement(sqltemp,
											Statement.RETURN_GENERATED_KEYS);
									ps.setString(1, model_obj.getAuto_id());
									ps.setString(2, "Large scale district level surveys");
									ps.setString(3, tempobj.getDocument());

									ps.setString(4, tempobj.getSourceavailibility());
									ps.setString(5, tempobj.getDocumentsource());
									ps.setString(6, tempobj.getDistrct_id());
									ps.setString(7, tempobj.getCycle_id());
									ps.setString(8, tempobj.getYear());
									ps.setString(9, tempobj.getUser_id());
									ps.setString(10, formatedDateTime);
									return ps;
								}, keyHolder_new);

								// ResultSet rs = ps.getGeneratedKeys();

								long p_key_new = keyHolder_new.getKey().longValue();

								mymap.put(tempobj.getAuto_id(), "" + p_key_new);

								sourceidmap.setAppsend_sourceid(tempobj.getAuto_id());
								sourceidmap.setWebgen_sourceid("" + p_key_new);
								sourceidmap.setDistrict(tempobj.getDistrct_id());
								sourceidmap.setCycle(tempobj.getCycle_id());
								sourceidmap.setYear(tempobj.getYear());

								if (form1b_lists.size() == 0) {
									sourceidmap.setForm1bfillcontinously("0");
								}

								for (int mycount = 0; mycount < form1b_lists.size(); mycount++) {
									Form1BPrimaryTableDataBean tempform1bdata = form1b_lists.get(mycount);

									if (tempform1bdata.getDistrict_id().equals(tempobj.getDistrct_id())
											&& tempform1bdata.getCycle_id().equals(tempobj.getCycle_id())
											&& tempform1bdata.getYear().equals(tempobj.getYear())
											&& tempform1bdata.getDatafrom().equals("APP")) {
										sourceidmap.setForm1bfillcontinously("1");
									}
								}

								mapping.add(sourceidmap);

								tempobj.setAuto_id("" + p_key_new);
								tempobj.setMeetingwith_id("" + model_obj.getAuto_id());
								tempobj.setDatafrom("WEB");

								formAdocument.add(tempobj);

							}

							/********************************************************/

						} // End of If condition WEB of formA and APP of docs

					} // End of Edit insert for loop for docs

					for (int y = 0; y < list_docs_web.size(); y++) {

						Form1ADocumentsTableDataBean tempobj = list_docs_web.get(y);

						if (model_obj.getAuto_id().equals(tempobj.getMeetingwith_id())) {

							if ("STL".equals(tempobj.getDocumentlevel())) {

								List<Form1ADocumentsTableDataBean> l = new ArrayList<>();

								l.add(tempobj);

								formAdocument.add(tempobj);

								jdbcTemplate.batchUpdate(
										"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
												+ "`doc_source`=?,`user_id`=?,"
												+ "`record_created`=? WHERE `doc_db_doc_id`=?",
										new BatchPreparedStatementSetter() {

											public void setValues(PreparedStatement ps, int i) throws SQLException {
												ps.setString(1, tempobj.getDocument());

												ps.setString(2, tempobj.getSourceavailibility());
												ps.setString(3, tempobj.getDocumentsource());
												ps.setString(4, tempobj.getUser_id());
												ps.setString(5, tempobj.getRecordcreated());
												ps.setString(6, tempobj.getAuto_id());

											}

											public int getBatchSize() {
												return l.size();
											}

										});

							} else if ("DTL".equals(tempobj.getDocumentlevel())) {

								List<Form1ADocumentsTableDataBean> l = new ArrayList<>();

								l.add(tempobj);

								formAdocument.add(tempobj);

								jdbcTemplate.batchUpdate(
										"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
												+ "`doc_source`=?,`user_id`=?,"
												+ "`record_created`=? WHERE `doc_db_doc_id`=?",
										new BatchPreparedStatementSetter() {

											public void setValues(PreparedStatement ps, int i) throws SQLException {
												ps.setString(1, tempobj.getDocument());

												ps.setString(2, tempobj.getSourceavailibility());
												ps.setString(3, tempobj.getDocumentsource());
												ps.setString(4, tempobj.getUser_id());
												ps.setString(5, tempobj.getRecordcreated());
												ps.setString(6, tempobj.getAuto_id());

											}

											public int getBatchSize() {
												return l.size();
											}

										});

							} else if ("HEALTHL".equals(tempobj.getDocumentlevel())) {

								List<Form1ADocumentsTableDataBean> l = new ArrayList<>();

								l.add(tempobj);

								formAdocument.add(tempobj);

								jdbcTemplate.batchUpdate(
										"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
												+ "`doc_source`=?,`user_id`=?,"
												+ "`record_created`=? WHERE `doc_db_doc_id`=?",
										new BatchPreparedStatementSetter() {

											public void setValues(PreparedStatement ps, int i) throws SQLException {
												ps.setString(1, tempobj.getDocument());

												ps.setString(2, tempobj.getSourceavailibility());
												ps.setString(3, tempobj.getDocumentsource());
												ps.setString(4, tempobj.getUser_id());
												ps.setString(5, tempobj.getRecordcreated());
												ps.setString(6, tempobj.getAuto_id());

											}

											public int getBatchSize() {
												return l.size();
											}

										});

							} else if ("NHEALTHL".equals(tempobj.getDocumentlevel())) {

								List<Form1ADocumentsTableDataBean> l = new ArrayList<>();

								l.add(tempobj);

								formAdocument.add(tempobj);

								jdbcTemplate.batchUpdate(
										"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
												+ "`doc_source`=?,`user_id`=?,"
												+ "`record_created`=? WHERE `doc_db_doc_id`=?",
										new BatchPreparedStatementSetter() {

											public void setValues(PreparedStatement ps, int i) throws SQLException {
												ps.setString(1, tempobj.getDocument());

												ps.setString(2, tempobj.getSourceavailibility());
												ps.setString(3, tempobj.getDocumentsource());
												ps.setString(4, tempobj.getUser_id());
												ps.setString(5, tempobj.getRecordcreated());
												ps.setString(6, tempobj.getAuto_id());

											}

											public int getBatchSize() {
												return l.size();
											}

										});

							} else if ("PVTL".equals(tempobj.getDocumentlevel())) {

								List<Form1ADocumentsTableDataBean> l = new ArrayList<>();

								l.add(tempobj);

								formAdocument.add(tempobj);

								jdbcTemplate.batchUpdate(
										"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
												+ "`doc_source`=?,`user_id`=?,"
												+ "`record_created`=? WHERE `doc_db_doc_id`=?",
										new BatchPreparedStatementSetter() {

											public void setValues(PreparedStatement ps, int i) throws SQLException {
												ps.setString(1, tempobj.getDocument());

												ps.setString(2, tempobj.getSourceavailibility());
												ps.setString(3, tempobj.getDocumentsource());
												ps.setString(4, tempobj.getUser_id());
												ps.setString(5, tempobj.getRecordcreated());
												ps.setString(6, tempobj.getAuto_id());

											}

											public int getBatchSize() {
												return l.size();
											}

										});

							} else if ("LSDL".equals(tempobj.getDocumentlevel())) {

								List<Form1ADocumentsTableDataBean> l = new ArrayList<>();

								l.add(tempobj);

								formAdocument.add(tempobj);

								jdbcTemplate.batchUpdate(
										"UPDATE `doc_db_documents` SET `doc_document`=?,`doc_availability`=?,"
												+ "`doc_source`=?,`user_id`=?,"
												+ "`record_created`=? WHERE `doc_db_doc_id`=?",
										new BatchPreparedStatementSetter() {

											public void setValues(PreparedStatement ps, int i) throws SQLException {
												ps.setString(1, tempobj.getDocument());

												ps.setString(2, tempobj.getSourceavailibility());
												ps.setString(3, tempobj.getDocumentsource());
												ps.setString(4, tempobj.getUser_id());
												ps.setString(5, tempobj.getRecordcreated());
												ps.setString(6, tempobj.getAuto_id());

											}

											public int getBatchSize() {
												return l.size();
											}

										});

							}

						} // End of if for Pri and foreign equality of web docs

					} // End of Web list_docs_web for loop

				}

			} // End of for loop for list_pri_web

		} // End of Else if for list_pri_web.size() != 0
		

		result.setFormA(formA);
		result.setFormAdocument(formAdocument);
		result.setMapping(mapping);

		return result;
	}

	// Create if else around batch update if no data is inserted at creation time

	public Form1AView saveForm1AToDb(Form1ASave model) {

		Form1AView obj = new Form1AView();
		String sql;
		/*String sql = "INSERT INTO `doc_db_verify`(`date_of_verification`, `filled_by`, `verified_by`,\r\n"
				+ "		`verified_by_other`, `district_id`, `cycle_id`, `year`, `user_id`,\r\n"
				+ "		`record_created`) VALUES(?,?,?,?,?,?,?,?,?); ";*/

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);		

		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		int row = 0;
//		row = jdbcTemplate.update(sql,
//				new Object[] { model.getDate_of_verification(), model.getFilled_by(), model.getVerified_by_name(),
//						model.getVerified_by_name(), model.getDistrict(), model.getCycle(), model.getYear(),
//						model.getUserid(), formatedDateTime });

		String sql2 = "INSERT INTO `doc_db_verify`(`date_of_verification`, `filled_by`, `verified_by`,\r\n"
				+ "		`verified_by_other`, `district_id`, `cycle_id`, `year`, `user_id`,\r\n"
				+ "		`record_created`, `completed`) VALUES(?,?,?,?,?,?,?,?,?,?); ";

		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getDate_of_verification());
			ps.setString(2, model.getFilled_by());
			ps.setString(3, model.getVerified_by_name());

			String other = model.getVerified_by_other_actual_name();

			if (other == null || "null".equals(other)) {
				other = "";
			}
			ps.setString(4, other);
			ps.setString(5, model.getDistrict());
			ps.setString(6, model.getCycle());
			ps.setString(7, model.getYear());
			ps.setString(8, model.getUserid());
			ps.setString(9, formatedDateTime);
			ps.setString(10, model.getCompleted());
			return ps;
		}, keyHolder);

		// ResultSet rs = ps.getGeneratedKeys();
		
		//System.out.println("\n\n\n "+model.getCompleted()+" \n\n\n");

		long p_key = keyHolder.getKey().longValue();

		if (row > 0) {
			obj.setDate_of_verification(model.getDate_of_verification());
			obj.setFilled_by(model.getFilled_by());
			obj.setVerified_by_name(model.getVerified_by_name());
			obj.setVerified_by_name_actual_name(model.getVerified_by_name());

			List<Form1ASaveDocumentsArray> list1 = new ArrayList<>();
			List<Form1ASaveDocumentsArray> temp1 = new ArrayList<>();

			if (model.getState_policy_first_doc_val() == null || "null".equals(model.getState_policy_first_doc_val())) {

			} else {
				temp1.add(new Form1ASaveDocumentsArray(model.getState_policy_first_doc_val(),
						model.getState_policy_first_availability(), model.getState_policy_first_d_source(), ""));
				list1.addAll(temp1);
			}

			//System.out.println("Size of State policy Array = " + model.getState_policy_array().size());

			if (model.getState_policy_array().size() != 0) {
				list1.addAll(model.getState_policy_array());
			}

			List<Form1ASaveDocumentsArray> list2 = new ArrayList<>();
			List<Form1ASaveDocumentsArray> temp2 = new ArrayList<>();

			if (model.getDistrict_policy_first_doc_val() == null
					|| "null".equals(model.getDistrict_policy_first_doc_val())) {

			} else {
				temp2.add(new Form1ASaveDocumentsArray(model.getDistrict_policy_first_doc_val(),
						model.getDistrict_policy_first_availability(), model.getDistrict_policy_first_d_source(), ""));
				list2.addAll(temp2);
			}

			

			if (model.getDistrict_policy_array().size() != 0) {
				list2.addAll(model.getDistrict_policy_array());
			}

			List<Form1ASaveDocumentsArray> list3 = new ArrayList<>();
			List<Form1ASaveDocumentsArray> temp3 = new ArrayList<>();

			if (model.getHealth_dept_first_doc_val() == null || "null".equals(model.getHealth_dept_first_doc_val())) {

			} else {
				temp3.add(new Form1ASaveDocumentsArray(model.getHealth_dept_first_doc_val(),
						model.getHealth_dept_first_availability(), model.getHealth_dept_first_d_source(), ""));
				list3.addAll(temp3);
			}

			//System.out.println("Size of getHealth_dept_array = " + model.getHealth_dept_array().size());

			if (model.getHealth_dept_array().size() != 0) {
				list3.addAll(model.getHealth_dept_array());
			}

			List<Form1ASaveDocumentsArray> list4 = new ArrayList<>();
			List<Form1ASaveDocumentsArray> temp4 = new ArrayList<>();

			if (model.getNon_health_dept_first_doc_val() == null
					|| "null".equals(model.getNon_health_dept_first_doc_val())) {

			} else {
				temp4.add(new Form1ASaveDocumentsArray(model.getNon_health_dept_first_doc_val(),
						model.getNon_health_dept_first_availability(), model.getNon_health_dept_first_d_source(), ""));
				list4.addAll(temp4);
			}

			//System.out.println("Size of getNon_health_dept_array = " + model.getNon_health_dept_array().size());

			if (model.getNon_health_dept_array().size() != 0) {
				list4.addAll(model.getNon_health_dept_array());
			}

			List<Form1ASaveDocumentsArray> list5 = new ArrayList<>();
			List<Form1ASaveDocumentsArray> temp5 = new ArrayList<>();

			if (model.getPrivate_sec_first_doc_val() == null || "null".equals(model.getPrivate_sec_first_doc_val())) {

			} else {
				temp5.add(new Form1ASaveDocumentsArray(model.getPrivate_sec_first_doc_val(),
						model.getPrivate_sec_first_availability(), model.getPrivate_sec_first_d_source(), ""));
				list5.addAll(temp5);
			}

			//System.out.println("Size of getPrivate_sec_array = " + model.getPrivate_sec_array().size());

			if (model.getPrivate_sec_array().size() != 0) {
				list5.addAll(model.getPrivate_sec_array());
			}

			List<Form1ASaveDocumentsArray> list6 = new ArrayList<>();
			List<Form1ASaveDocumentsArray> temp6 = new ArrayList<>();
			temp6.add(new Form1ASaveDocumentsArray(model.getLarge_scale_district_first_doc_val(),
					model.getLarge_scale_district_first_availability(), model.getLarge_scale_district_first_d_source(),
					""));
			list6.addAll(temp6);

			

			if (model.getLarge_scale_district_array().size() != 0) {
				list6.addAll(model.getLarge_scale_district_array());
			}

			obj.setState_policy_array(list1);
			obj.setDistrict_policy_array(list2);
			obj.setHealth_dept_array(list3);
			obj.setNon_health_dept_array(list4);
			obj.setPrivate_sec_array(list5);
			obj.setLarge_scale_district_array(list6);

			

			sql = "INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
					+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
					+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)";

			// model.getState_policy_first_doc_val(),
			// model.getState_policy_first_availability(),
			// model.getState_policy_first_d_source()));

//			row = jdbcTemplate.update(sql,
//					new Object[] { p_key, "State level", model.getState_policy_first_doc_val(),
//							model.getState_policy_first_availability(), model.getState_policy_first_d_source(),model.getDistrict(), model.getCycle(), model.getYear(),
//							model.getUserid(), formatedDateTime });

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setLong(1, p_key);
							ps.setString(2, "State level");
							ps.setString(3, list1.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list1.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list1.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list1.size();
						}

					});

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setLong(1, p_key);
							ps.setString(2, "District level");
							ps.setString(3, list2.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list2.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list2.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list2.size();
						}

					});

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setLong(1, p_key);
							ps.setString(2, "Health Department");
							ps.setString(3, list3.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list3.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list3.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list3.size();
						}

					});

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setLong(1, p_key);
							ps.setString(2, "Non-Health Department");
							ps.setString(3, list4.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list4.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list4.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list4.size();
						}

					});

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setLong(1, p_key);
							ps.setString(2, "Private Sector");
							ps.setString(3, list5.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list5.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list5.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list5.size();
						}

					});

			jdbcTemplate.batchUpdate(
					"INSERT INTO `doc_db_documents`(`doc_db_check_id`, `doc_level`, `doc_document`, "
							+ "`doc_availability`, `doc_source`, `district_id`," + " `cycle_id`, `year`, "
							+ "`user_id`, `record_created`) VALUES(?,?,?, ?,?,?,  ?,?,?, ?)",
					new BatchPreparedStatementSetter() {

						public void setValues(PreparedStatement ps, int i) throws SQLException {
							ps.setLong(1, p_key);
							ps.setString(2, "Large scale district level surveys");
							ps.setString(3, list6.get(i).getDocument_val());

							int result = 2;
							if ("1".equals(list6.get(i).getDocument_availability())) {
								result = 1;
							}

							ps.setString(4, result + "");
							ps.setString(5, list6.get(i).getDocument_source());
							ps.setString(6, model.getDistrict());
							ps.setString(7, model.getCycle());
							ps.setString(8, model.getYear());
							ps.setString(9, model.getUserid());
							ps.setString(10, formatedDateTime);
						}

						public int getBatchSize() {
							return list6.size();
						}

					});
		}

		/*
		 * 
		 * JdbcTemplate template = new JdbcTemplate(dataSource);
		 * 
		 * // define query arguments Object[] params = new Object[] { name, surname,
		 * title, new Date() };
		 * 
		 * // define SQL types of the arguments int[] types = new int[] { Types.VARCHAR,
		 * Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
		 * 
		 * // execute insert query to insert the data // return number of row / rows
		 * processed by the executed query int row = template.update(insertSql, params,
		 * types); System.out.println(row + " row inserted.");
		 * 
		 */

		/*
		 * 
		 * String sql = "INSERT INTO CUSTOMER " +
		 * "(CUST_ID, NAME, AGE) VALUES (?, ?, ?)";
		 * 
		 * jdbcTemplate = new JdbcTemplate(dataSource);
		 * 
		 * jdbcTemplate.update(sql, new Object[] { customer.getCustId(),
		 * customer.getName(),customer.getAge() });
		 * 
		 */

		return obj;
	}

}
