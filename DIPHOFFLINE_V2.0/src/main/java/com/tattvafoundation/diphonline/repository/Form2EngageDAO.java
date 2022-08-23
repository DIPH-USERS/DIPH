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
import com.tattvafoundation.diphonline.model.DeleteForm2EngageResponse;
import com.tattvafoundation.diphonline.model.Form2EnagagePrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form2EnagageSectiondetails;
import com.tattvafoundation.diphonline.model.Form2EnagageSendAllDataBean;
import com.tattvafoundation.diphonline.model.Form2EnagageStakeholdersTableDataBean;
import com.tattvafoundation.diphonline.model.Form2Engage;
import com.tattvafoundation.diphonline.model.Form2EngageEdit;
import com.tattvafoundation.diphonline.model.Form2EngagePartACommonStakeHoldersArray;
import com.tattvafoundation.diphonline.model.Form2EngagePartBCommonArray;
import com.tattvafoundation.diphonline.model.Form2EngagestakeIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form3DefineActionIDDetailsBean;
import com.tattvafoundation.diphonline.model.Form4PlanPrimaryTableDataBean;
import com.tattvafoundation.diphonline.model.Form4PlanSendAllDataBean;
import com.tattvafoundation.diphonline.model.SavedForm2EngageResponse;
import com.tattvafoundation.diphonline.model.SendAndroidForm1BSynchedDataBean;
import com.tattvafoundation.diphonline.model.UserCredentialsFromAndroidBean;
import com.tattvafoundation.diphonline.model.User_Districts_Privileges;

@Repository
public class Form2EngageDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public SavedForm2EngageResponse updateEditedForm2EngageToDb(Form2EngageEdit model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		
		int row = 0;

		String sql2 = "UPDATE `form_2_filled_by` SET `form_2_date_of_meeting`=?,`form_2_venue`=?,"
				+ "`form_2_filled`=?,`form_2_filled_others`=?,`user_id`=?," + "`record_created`=?, `completed`=? WHERE `form_2_id`=?";

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql2);
			ps.setString(1, model.getForm_2_date_of_meeting());
			ps.setString(2, model.getForm_2_venue());
			ps.setString(3, model.getForm_2_filled());
			ps.setString(4, model.getForm_2_filled_others());
			ps.setString(5, model.getUserid());
			ps.setString(6, formatedDateTime);			
			ps.setString(7, model.getCompleted());
			ps.setString(8, model.getForm_2_id());
			return ps;
		});

		

		List<Form2EngagePartBCommonArray> list1_insert = model.getDefiningprimaryrole_array_insert();

		if (list1_insert.size() != 0) {
			jdbcTemplate
					.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
							+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
							+ "				`user_id`, `record_created`) VALUES (?,?,?,"
							+ "						?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_2_id());
									ps.setString(2, "section_2");
									ps.setString(3, list1_insert.get(i).getDocument_select_stakeholder());
									ps.setString(4, list1_insert.get(i).getDocument_desc());
									ps.setString(5, model.getDistrict());
									ps.setString(6, model.getCycle());
									ps.setString(7, model.getYear());
									ps.setString(8, model.getUserid());
									ps.setString(9, formatedDateTime);
								}

								public int getBatchSize() {
									return list1_insert.size();
								}

							});
		}

		List<Form2EngagePartBCommonArray> list2_insert = model.getEfforttoaddressissue_array_insert();

		if (list2_insert.size() != 0) {
			jdbcTemplate
					.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
							+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
							+ "				`user_id`, `record_created`) VALUES (?,?,?,"
							+ "						?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_2_id());
									ps.setString(2, "section_3");
									ps.setString(3, list2_insert.get(i).getDocument_select_stakeholder());
									ps.setString(4, list2_insert.get(i).getDocument_desc());
									ps.setString(5, model.getDistrict());
									ps.setString(6, model.getCycle());
									ps.setString(7, model.getYear());
									ps.setString(8, model.getUserid());
									ps.setString(9, formatedDateTime);
								}

								public int getBatchSize() {
									return list2_insert.size();
								}

							});
		}

		List<Form2EngagePartBCommonArray> list3_insert = model.getEnhanceefficiency_array_insert();

		if (list3_insert.size() != 0) {
			jdbcTemplate
					.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
							+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
							+ "				`user_id`, `record_created`) VALUES (?,?,?,"
							+ "						?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_2_id());
									ps.setString(2, "section_4");
									ps.setString(3, list3_insert.get(i).getDocument_select_stakeholder());
									ps.setString(4, list3_insert.get(i).getDocument_desc());
									ps.setString(5, model.getDistrict());
									ps.setString(6, model.getCycle());
									ps.setString(7, model.getYear());
									ps.setString(8, model.getUserid());
									ps.setString(9, formatedDateTime);
								}

								public int getBatchSize() {
									return list3_insert.size();
								}

							});
		}

		List<Form2EngagePartBCommonArray> list4_insert = model.getThemeleadbydpt_array_insert();

		if (list4_insert.size() != 0) {
			jdbcTemplate
					.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
							+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
							+ "				`user_id`, `record_created`) VALUES (?,?,?,"
							+ "						?,?,?,?,?,?)", new BatchPreparedStatementSetter() {

								public void setValues(PreparedStatement ps, int i) throws SQLException {
									ps.setString(1, "" + model.getForm_2_id());
									ps.setString(2, "section_5");
									ps.setString(3, list4_insert.get(i).getDocument_select_stakeholder());
									ps.setString(4, list4_insert.get(i).getDocument_desc());
									ps.setString(5, model.getDistrict());
									ps.setString(6, model.getCycle());
									ps.setString(7, model.getYear());
									ps.setString(8, model.getUserid());
									ps.setString(9, formatedDateTime);
								}

								public int getBatchSize() {
									return list4_insert.size();
								}

							});
		}

		List<Form2EngagePartBCommonArray> list1_update = model.getDefiningprimaryrole_array();

		jdbcTemplate
				.batchUpdate(
						"UPDATE `form_2_section_details` SET `section_select`=?,`section_text`=?," + "`user_id`=?,"
								+ "`record_created`=? WHERE  `form_2_section_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list1_update.get(i).getDocument_select_stakeholder());
								ps.setString(2, list1_update.get(i).getDocument_desc());
								ps.setString(3, model.getUserid());
								ps.setString(4, formatedDateTime);
//						ps.setString(5, model.getForm_2_id());
//						ps.setString(6, "section_2");
								ps.setString(5, list1_update.get(i).getForm_2_section_id());
							}

							public int getBatchSize() {
								return list1_update.size();
							}

						});

		List<Form2EngagePartBCommonArray> list2_update = model.getEfforttoaddressissue_array();

		jdbcTemplate
				.batchUpdate(
						"UPDATE `form_2_section_details` SET `section_select`=?,`section_text`=?," + "`user_id`=?,"
								+ "`record_created`=? WHERE  `form_2_section_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list2_update.get(i).getDocument_select_stakeholder());
								ps.setString(2, list2_update.get(i).getDocument_desc());
								ps.setString(3, model.getUserid());
								ps.setString(4, formatedDateTime);
//						ps.setString(5, model.getForm_2_id());
//						ps.setString(6, "section_3");
								ps.setString(5, list2_update.get(i).getForm_2_section_id());

							}

							public int getBatchSize() {
								return list2_update.size();
							}

						});

		List<Form2EngagePartBCommonArray> list3_update = model.getEnhanceefficiency_array();

		jdbcTemplate
				.batchUpdate(
						"UPDATE `form_2_section_details` SET `section_select`=?,`section_text`=?," + "`user_id`=?,"
								+ "`record_created`=? WHERE  `form_2_section_id`=?",
						new BatchPreparedStatementSetter() {

							public void setValues(PreparedStatement ps, int i) throws SQLException {
								ps.setString(1, list3_update.get(i).getDocument_select_stakeholder());
								ps.setString(2, list3_update.get(i).getDocument_desc());
								ps.setString(3, model.getUserid());
								ps.setString(4, formatedDateTime);
//						ps.setString(5, model.getForm_2_id());
//						ps.setString(6, "section_4");
								ps.setString(5, list3_update.get(i).getForm_2_section_id());

							}

							public int getBatchSize() {
								return list3_update.size();
							}

						});

		List<Form2EngagePartBCommonArray> list4_update = model.getThemeleadbydpt_array();

		jdbcTemplate.batchUpdate(
//				"UPDATE `form_2_section_details` SET `section_select`=?,`section_text`=?," + "`user_id`=?,"
//						+ "`record_created`=? WHERE `form_2_id`=?  and `section_name`=? and `form_2_section_id`=?",
				"UPDATE `form_2_section_details` SET `section_select`=?,`section_text`=?," + "`user_id`=?,"
						+ "`record_created`=? WHERE  `form_2_section_id`=?",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, list4_update.get(i).getDocument_select_stakeholder());
						ps.setString(2, list4_update.get(i).getDocument_desc());
						ps.setString(3, model.getUserid());
						ps.setString(4, formatedDateTime);
//						ps.setString(5, model.getForm_2_id());
//						ps.setString(6, "section_5");
						ps.setString(5, list4_update.get(i).getForm_2_section_id());

					}

					public int getBatchSize() {
						return list4_update.size();
					}

				});

		SavedForm2EngageResponse responseobj = new SavedForm2EngageResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;

	}

	public DeleteForm2EngageResponse deleteForm2EngageDetails(String district_id, String cycle_id, String year,
			String user_id) {

		DeleteForm2EngageResponse responseobj = new DeleteForm2EngageResponse();

		

		Object[] params1 = { district_id, cycle_id, year };
		int rows = jdbcTemplate.update(
				"DELETE FROM `form_2_filled_by` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params1);
		

		Object[] params2 = { district_id, cycle_id, year };
		int rows2 = jdbcTemplate.update(
				"DELETE FROM `form_2_stakeholders` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params2);
		

		Object[] params3 = { district_id, cycle_id, year };
		int rows3 = jdbcTemplate.update(
				"DELETE FROM `form_2_section_details` where `district_id`=? and `cycle_id`=? and `financial_year`=?",
				params3);
		

		responseobj.setProcessname("deleted");
		if (rows > 1 && rows2 > 1 && rows3 > 1) {
			responseobj.setResponse_val("1");
		} else {
			responseobj.setResponse_val("0");
		}

		return responseobj;
	}
	
	public void sendForm2EngageSaveForExistingDistrictCycleYear(String district_id,String cycle_id,
			String year,String country,String province,Form2EnagageSendAllDataBean response ) {

		List<Form2EnagagePrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form2EnagageStakeholdersTableDataBean> list2 = new ArrayList<>();
		List<Form2EnagageSectiondetails> list3 = new ArrayList<>();

		String sql1 = "SELECT form2.`form_2_id`,form2.`form_2_date_of_meeting`,form2.`form_2_venue`,\r\n" + 
				"				 form2.`form_2_filled`,form2.`form_2_filled_others`,form2.`district_id`,\r\n" + 
				"				 form2.`cycle_id`,form2.`financial_year`,form2.`user_id`,form2.`record_created`,form2.`completed` FROM `form_2_filled_by` form2    \r\n" + 
				"				 where form2.`district_id`=? and  form2.`cycle_id`=? and  form2.`financial_year`=?";

		Object[] params1 = new Object[] { district_id, cycle_id, year };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form2EnagagePrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagagePrimaryTableDataBean obj = new Form2EnagagePrimaryTableDataBean();

				obj.setAuto_id(rs.getString("form_2_id"));
				obj.setMeetingdate(rs.getString("form_2_date_of_meeting"));
				obj.setMeetingvenue(rs.getString("form_2_venue"));
				obj.setChairpersonid(rs.getString("form_2_filled"));

				String temps1 = "";

				if (rs.getString("form_2_filled_others") == null
						|| "null".equals(rs.getString("form_2_filled_others"))) {
					temps1 = "";
				} else {
					temps1 = rs.getString("form_2_filled_others");
				}

				obj.setOtherchairperson(temps1);
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
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql2 = "SELECT form2stake.`form_2_stk_id`,    form2stake.`form_2_id`,    form2stake.`stake_label`,  "
				+ "  form2stake.`stakeholder_text`,    form2stake.`district_id`,  "
				+ "  form2stake.`cycle_id`,  form2stake.`financial_year`,    form2stake.`user_id`,  "
				+ "  form2stake.`record_created` FROM `form_2_stakeholders` form2stake  "
				+ "    where form2stake.`district_id`=?  and    form2stake.`cycle_id`=?  and  form2stake.`financial_year`=?";

		Object[] params2 = new Object[] { district_id, cycle_id, year };

		list2 = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form2EnagageStakeholdersTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagageStakeholdersTableDataBean obj = new Form2EnagageStakeholdersTableDataBean();

				obj.setAuto_id(rs.getString("form_2_stk_id"));
				obj.setForm_2engage_id(rs.getString("form_2_id"));
				
				String stake_lbl = "PRIMARY";
				
				if("Primary Stakeholder".equals(rs.getString("stake_label"))) {  
					stake_lbl = "PRIMARY";
				}
				else 
				{
					stake_lbl = "SECONDARY";
				}
				obj.setStake_label(stake_lbl);
				obj.setStakeholder_text(rs.getString("stakeholder_text"));
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

		String sql3 = "SELECT form2detail.`form_2_section_id`,form2detail.`form_2_id`, form2detail.`section_name`, "
				+ " form2detail.`section_select`,form2detail.`section_text`, form2detail.`district_id`,  "
				+ "   form2detail.`cycle_id`,form2detail.`financial_year`,  form2detail.`user_id`, "
				+ "    form2detail.`record_created`  FROM `form_2_section_details`  form2detail   "
				+ "   where form2detail.`district_id`=? and form2detail.`cycle_id`=?  and form2detail.`financial_year`=?";

		Object[] params3 = new Object[] { district_id, cycle_id, year };

		list3 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form2EnagageSectiondetails> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagageSectiondetails obj = new Form2EnagageSectiondetails();

				obj.setAuto_id(rs.getString("form_2_section_id"));
				obj.setForm_2engage_id(rs.getString("form_2_id"));
				
				String section = "section_2";
				
				if("section_2".equals(rs.getString("section_name"))) {
					section = "PROLE";
				}
				else if("section_3".equals(rs.getString("section_name"))) 
				{
					section = "CEAI";
				}
				else if("section_4".equals(rs.getString("section_name"))) 
				{
					section = "EEE";
				}
				else if("section_5".equals(rs.getString("section_name"))) 
				{
					section = "THLD";
				}
				
				obj.setSection_name(section);
				obj.setSection_select(rs.getString("section_select"));
				obj.setSection_text(rs.getString("section_text"));
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

		response.setForm2Engage(list1);
		response.setForm2stakeholders(list2);
		response.setForm2docs(list3);

		response.setError_code("200");
		response.setMessage("Sending Form2 Engage Data");

		if (list1.size() == 0 && list2.size() == 0 && list3.size() == 0) {

			response.setForm2Engage(new ArrayList<>());
			response.setForm2stakeholders(list2);
			response.setForm2docs(list3);

			response.setError_code("200");
			response.setMessage("Sending Form2 Engage Data");

		}

		//return response;
	
	}

	public Form2EnagageSendAllDataBean consumeAllForm2EnagageSaveandUpdate(AllFormsDataFetchFromAndroidBean model) {

		Form2EnagageSendAllDataBean response = new Form2EnagageSendAllDataBean();

		
		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		
		Form2EnagageSendAllDataBean form2obj = model.getForm2();

		List<Form2EnagagePrimaryTableDataBean> list1 = form2obj.getForm2Engage();

		List<Form2EnagagePrimaryTableDataBean> list_primary_send = new ArrayList<>();
		List<Form2EnagageStakeholdersTableDataBean> list_stakes_send = new ArrayList<>();
		List<Form2EnagageSectiondetails> list_sections_send = new ArrayList<>();
		
		List<Form2EngagestakeIDDetailsBean> mapping= new ArrayList<>();

		// Save for APP data
		for (int index = 0; index < list1.size(); index++) {

			Form2EnagagePrimaryTableDataBean tempobj = list1.get(index);

			Form2EnagagePrimaryTableDataBean sendobj = new Form2EnagagePrimaryTableDataBean();

			if ("APP".equals(tempobj.getDatafrom())) {

				
				
				/*------------------------------------------------*/
				
				
				String sql_check = "SELECT * FROM form_2_filled_by  where  `district_id`=? and `cycle_id`=? and `financial_year`=?";
				Object[] params_check = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(), tempobj.getYear() }; 

				String checkid = jdbcTemplate.query(sql_check, params_check, rs -> {

					
					String keyval = "";
					while (rs.next()) {

						keyval = rs.getString("form_2_id");
						
						
						
					}
					/* We can also return any variable-data from here but not used currently */
					return keyval;
				});
				
				
				
				if("".equals(checkid)) {
					
				}
				else {
					
					
					sendForm2EngageSaveForExistingDistrictCycleYear(tempobj.getDistrict_id(),tempobj.getCycle_id(),
							tempobj.getYear(),tempobj.getCountry_id(),tempobj.getProvince_id(),response );
					return response;
				}
				
				
				
				/*------------------------------------------------*/
				
				int row = 0;

				String sql1 = "INSERT INTO form_2_filled_by(form_2_date_of_meeting,form_2_venue,form_2_filled,form_2_filled_others,district_id,cycle_id,financial_year,user_id,record_created,completed)\r\n"
						+ "		 VALUES (?,?,?,?,?,?,?,?,?,?)";

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

					ps.setString(5, tempobj.getDistrict_id());
					sendobj.setDistrict_id(tempobj.getDistrict_id());

					ps.setString(6, tempobj.getCycle_id());
					sendobj.setCycle_id(tempobj.getCycle_id());

					ps.setString(7, tempobj.getYear());
					sendobj.setYear(tempobj.getYear());

					ps.setString(8, tempobj.getUser_id());
					sendobj.setUser_id(tempobj.getUser_id());

					ps.setString(9, formatedDateTime);
					sendobj.setMeetingdate(formatedDateTime);
					
					ps.setString(10, tempobj.getCompleted());
					sendobj.setUser_id(tempobj.getCompleted());

					return ps;
				}, keyHolder);

				// ResultSet rs = ps.getGeneratedKeys();

				long p_key = keyHolder.getKey().longValue();

				sendobj.setAuto_id("" + keyHolder.getKey().longValue());
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(formatedDateTime);
				sendobj.setRecordcreated(formatedDateTime); 
				sendobj.setDatafrom("WEB");

				list_primary_send.add(sendobj);

				

				List<Form2EnagageStakeholdersTableDataBean> list_stakes = form2obj.getForm2stakeholders();
				
				
				HashMap<String,String> mymap = new HashMap<>();
				

				for (int x = 0; x < list_stakes.size(); x++) {

					

					KeyHolder keyHolder2 = new GeneratedKeyHolder();

					Form2EnagageStakeholdersTableDataBean stakes_obj = list_stakes.get(x);

					Form2EnagageStakeholdersTableDataBean sendobj2 = new Form2EnagageStakeholdersTableDataBean();

					if (tempobj.getDistrict_id().equals(stakes_obj.getDistrict_id())
							&& tempobj.getCycle_id().equals(stakes_obj.getCycle_id())
							&& tempobj.getYear().equals(stakes_obj.getYear())) {
						
						Form4PlanSendAllDataBean form4plan_all_obj = model.getForm4();
						
						if(form4plan_all_obj == null) {
							form4plan_all_obj = new Form4PlanSendAllDataBean();
						}
						
						List<Form4PlanPrimaryTableDataBean> form4_lists = form4plan_all_obj.getForm4Plan();
						
						if(form4_lists == null) {
							
							form4_lists = new ArrayList<>();
							
						}
						
						
						Form2EngagestakeIDDetailsBean stake_holderidmap = new Form2EngagestakeIDDetailsBean();
						
						
						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(
									"INSERT INTO form_2_stakeholders(form_2_id,stake_label,stakeholder_text,"
											+ "district_id,cycle_id,financial_year,user_id,"
											+ "record_created) VALUES (?,?,?,?,?,?,?,?)",
									Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							sendobj2.setForm_2engage_id("" + p_key);
							
							String stakesval = "";
							
							if("PRIMARY".equals(stakes_obj.getStake_label())) { 
								stakesval = "Primary Stakeholder";
							}
							else 
							{
								stakesval = "Secondary Stakeholder";
							}

							ps.setString(2, stakesval);
							sendobj2.setStake_label(stakes_obj.getStake_label());

							ps.setString(3, stakes_obj.getStakeholder_text());
							sendobj2.setStakeholder_text(stakes_obj.getStakeholder_text());

							ps.setString(4, stakes_obj.getDistrict_id());
							sendobj2.setDistrict_id(stakes_obj.getDistrict_id());

							ps.setString(5, stakes_obj.getCycle_id());
							sendobj2.setCycle_id(stakes_obj.getCycle_id());

							ps.setString(6, stakes_obj.getYear());
							sendobj2.setYear(stakes_obj.getYear());

							ps.setString(7, stakes_obj.getUser_id());
							sendobj2.setUser_id(stakes_obj.getUser_id());

							ps.setString(8, formatedDateTime);
							sendobj2.setRecordcreated(formatedDateTime);

							return ps;
						}, keyHolder2);
						
						
						mymap.put(stakes_obj.getAuto_id(), "" + keyHolder2.getKey().longValue());
						
						
						
						

						sendobj2.setAuto_id("" + keyHolder2.getKey().longValue());
						sendobj2.setCountry_id(stakes_obj.getCountry_id());
						sendobj2.setProvince_id(stakes_obj.getProvince_id());
						sendobj2.setTimestamp(formatedDateTime);
						sendobj2.setDatafrom("WEB");

						list_stakes_send.add(sendobj2);
						

						stake_holderidmap.setDistrict(""+stakes_obj.getDistrict_id());
						stake_holderidmap.setCycle(""+stakes_obj.getCycle_id());
						stake_holderidmap.setYear(""+stakes_obj.getYear());
						stake_holderidmap.setAppsend_stakesid(""+stakes_obj.getAuto_id());
						stake_holderidmap.setWebgen_stakesid(""+keyHolder2.getKey().longValue());
						
						if(form4_lists.size() == 0) {
							stake_holderidmap.setForm4planfillcontinously("0");
						}
						
						
						for(int mycount=0;mycount<form4_lists.size();mycount++) {
							Form4PlanPrimaryTableDataBean tempform4plandata = form4_lists.get(mycount);
							
							if(tempform4plandata.getDistrict_id().equals(tempobj.getDistrict_id()) 
									&& tempform4plandata.getCycle_id().equals(tempobj.getCycle_id())
									&& tempform4plandata.getYear().equals(tempobj.getYear()) 
									&& "APP".equals(tempform4plandata.getDatafrom()) 
									&& "APP".equals(tempobj.getDatafrom()) ) {
								stake_holderidmap.setForm4planfillcontinously("1");
							}
						}
						
						
						mapping.add(stake_holderidmap); 
					}

				}

				List<Form2EnagageSectiondetails> list_sections = form2obj.getForm2docs();

				for (int y = 0; y < list_sections.size(); y++) {

					KeyHolder keyHolder2 = new GeneratedKeyHolder();

					Form2EnagageSectiondetails sections_obj = list_sections.get(y);

					Form2EnagageSectiondetails sendobj3 = new Form2EnagageSectiondetails();

					if (tempobj.getDistrict_id().equals(sections_obj.getDistrict_id())
							&& tempobj.getCycle_id().equals(sections_obj.getCycle_id())
							&& tempobj.getYear().equals(sections_obj.getYear())) {
						
						String section = "PROLE";
						
						if("PROLE".equals(sections_obj.getSection_name())) {
							section = "section_2";
						}
						else if("CEAI".equals(sections_obj.getSection_name())) {
							section = "section_3";
						}
						else if("EEE".equals(sections_obj.getSection_name())) {
							section = "section_4";							
													}
						else if("THLD".equals(sections_obj.getSection_name())) {
							section = "section_5";
						}
						
						String final_section = section;

						jdbcTemplate.update(connection -> {
							PreparedStatement ps = connection.prepareStatement(
									"INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
											+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
											+ "				`user_id`, `record_created`) VALUES (?,?,?,?,?,?,?,?,?)",
									Statement.RETURN_GENERATED_KEYS);
							ps.setString(1, "" + p_key);
							sendobj3.setForm_2engage_id("" + p_key);
							

							ps.setString(2, final_section);
							sendobj3.setSection_name(sections_obj.getSection_name());
							
							
							
							/*--------------------------------------------------------------*/
							
//							for(int temp_y=0;temp_y<list_stakes.size();temp_y++) {
//								
//								Form2EnagageStakeholdersTableDataBean temp_stakes_obj = list_stakes.get(temp_y);
//								
//								if(temp_stakes_obj.getAuto_id().equals(sections_obj.getSection_select())) { 
//									
//								}
//								
//							}
							
							
							String section_actual_value="";
							
							for (Map.Entry<String, String> entry : mymap.entrySet()) {
							    							    
							    
							    if(entry.getKey().equals(sections_obj.getSection_select())) {
							    	section_actual_value = entry.getValue();
							    }
							}
							
							
							
							/*--------------------------------------------------------------*/

							//ps.setString(3, sections_obj.getSection_select());
							//sendobj3.setSection_select(sections_obj.getSection_select());
							
							ps.setString(3, section_actual_value);
							sendobj3.setSection_select(section_actual_value);

							ps.setString(4, sections_obj.getSection_text());
							sendobj3.setSection_text(sections_obj.getSection_text());

							ps.setString(5, sections_obj.getDistrict_id());
							sendobj3.setDistrict_id(sections_obj.getDistrict_id());

							ps.setString(6, sections_obj.getCycle_id());
							sendobj3.setCycle_id(sections_obj.getCycle_id());

							ps.setString(7, sections_obj.getYear());
							sendobj3.setYear(sections_obj.getYear());

							ps.setString(8, sections_obj.getUser_id());
							sendobj3.setUser_id(sections_obj.getUser_id());

							ps.setString(9, formatedDateTime);
							sendobj3.setRecordcreated(formatedDateTime);

							return ps;
						}, keyHolder2);
						
						

						
//						String form2section_sql = "SELECT form2detail.`form_2_section_id`,form2detail.`form_2_id`,"
//								+ " form2detail.`section_name`,form2detail.`section_select`,form2detail.`section_text`,"
//								+ " form2detail.`district_id`,form2detail.`cycle_id`,form2detail.`financial_year`, "
//								+ " form2detail.`user_id`,form2detail.`record_created`,d.`district_id` as `dst2`, d.`country_id`, "
//								+ " d.`state_id`, cs.`region_id`  FROM `form_2_section_details`  form2detail  left join  `district` d "
//								+ " on form2detail.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
//								+ "  where d.`district_id`=?  and form2detail.`cycle_id`=? and form2detail.`financial_year`=? and form2detail.`section_name`=? and form2detail.`form_2_id`=? ";
//
//						Object[] form2section_param = new Object[] { sections_obj.getDistrict_id(), sections_obj.getCycle_id(), sections_obj.getYear(),
//								section,"" + p_key };
//						
//						
//
//						jdbcTemplate.query(form2section_sql, form2section_param, rs -> {
//
//							while (rs.next()) {
//								
//								sendobj3.setForm_2engage_id(rs.getString("form_2_id"));
//								sendobj3.setSection_name(rs.getString("section_name")); 
//								sendobj3.setSection_select(rs.getString("section_name"));
//							}
//							/* We can also return any variable-data from here but not used currently */
//							return "";
//						});
						
						

						sendobj3.setAuto_id("" + keyHolder2.getKey().longValue());
						sendobj3.setTimestamp(formatedDateTime);
						sendobj3.setCountry_id(sections_obj.getCountry_id());
						sendobj3.setProvince_id(sections_obj.getProvince_id());
						sendobj3.setDatafrom("WEB");

						list_sections_send.add(sendobj3);

					}

				}

			} else {
				
				continue;
			}

		}

		// Updation for WEB data
		for (int index = 0; index < list1.size(); index++) {

			Form2EnagagePrimaryTableDataBean tempobj = list1.get(index);

			if ("APP".equals(tempobj.getDatafrom())) {

				
				continue;
			} 
			else 
			{
				

				int row = 0;

				Form2EnagagePrimaryTableDataBean sendobj = new Form2EnagagePrimaryTableDataBean();

				String sql2 = "UPDATE `form_2_filled_by` SET `form_2_date_of_meeting`=?,`form_2_venue`=?,"
						+ "`form_2_filled`=?,`form_2_filled_others`=?,`user_id`=?,"
						+ "`record_created`=?,"+ "`completed`=? WHERE `form_2_id`=?";

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql2);
					ps.setString(1, tempobj.getMeetingdate());
					ps.setString(2, tempobj.getMeetingvenue());
					ps.setString(3, tempobj.getChairpersonid());
					ps.setString(4, tempobj.getOtherchairperson());
					ps.setString(5, tempobj.getUser_id());
					ps.setString(6, formatedDateTime);
					ps.setString(7, tempobj.getCompleted());
					ps.setString(8, tempobj.getAuto_id());
					return ps;
				});

				sendobj.setMeetingdate(tempobj.getMeetingdate());
				sendobj.setMeetingvenue(tempobj.getMeetingvenue());
				sendobj.setChairpersonid(tempobj.getChairpersonid());
				sendobj.setOtherchairperson(tempobj.getOtherchairperson());
				sendobj.setDistrict_id(tempobj.getDistrict_id());
				sendobj.setCycle_id(tempobj.getCycle_id());
				sendobj.setYear(tempobj.getYear());
				sendobj.setUser_id(tempobj.getUser_id());
				sendobj.setMeetingdate(formatedDateTime);
				sendobj.setAuto_id(tempobj.getAuto_id());
				sendobj.setCountry_id(tempobj.getCountry_id());
				sendobj.setProvince_id(tempobj.getProvince_id());
				sendobj.setTimestamp(formatedDateTime);
				sendobj.setDatafrom("WEB");

				list_primary_send.add(sendobj);

				List<Form2EnagageSectiondetails> list2 = form2obj.getForm2docs();

				/*--------------*/

				String sql_new = "SELECT * FROM form_2_stakeholders where district_id=? and cycle_id=? and financial_year=? and stakeholder_text!=''  order by form_2_stk_id asc";
				Object[] params_new = new Object[] { tempobj.getDistrict_id(), tempobj.getCycle_id(),
						tempobj.getYear() };

				list_stakes_send  = jdbcTemplate.query(sql_new, params_new, rs -> {
					
					List<Form2EnagageStakeholdersTableDataBean> templist = new ArrayList<>();
					
					while (rs.next()) {

						Form2EnagageStakeholdersTableDataBean obj_stake_temp = new Form2EnagageStakeholdersTableDataBean();

						obj_stake_temp.setForm_2engage_id(rs.getString("form_2_id"));
						obj_stake_temp.setStakeholder_text(rs.getString("stakeholder_text"));
						obj_stake_temp.setAuto_id(rs.getString("form_2_stk_id"));
						
						String stakes = "";
						
						if("Primary Stakeholder".equals(rs.getString("stake_label"))) { 
							stakes = "PRIMARY";
						}
						else {
							stakes = "SECONDARY";
						}
						
						obj_stake_temp.setStake_label(stakes);

						obj_stake_temp.setDistrict_id(rs.getString("district_id"));
						obj_stake_temp.setCycle_id(rs.getString("cycle_id"));
						obj_stake_temp.setYear(rs.getString("financial_year"));
						obj_stake_temp.setUser_id(rs.getString("user_id"));
						obj_stake_temp.setRecordcreated(rs.getString("record_created"));

						obj_stake_temp.setCountry_id(tempobj.getCountry_id());
						obj_stake_temp.setProvince_id(tempobj.getProvince_id());
						obj_stake_temp.setTimestamp(rs.getString("record_created"));
						obj_stake_temp.setDatafrom("WEB");

						templist.add(obj_stake_temp);
					}
					/* We can also return any variable-data from here but not used currently */
					return templist;
				});

				/*--------------*/

				for (int x = 0; x < list2.size(); x++) {

					Form2EnagageSectiondetails sections_obj = list2.get(x);

					Form2EnagageSectiondetails sendobj3 = new Form2EnagageSectiondetails();

					if (sections_obj.getDistrict_id().equals(tempobj.getDistrict_id())
							&& sections_obj.getCycle_id().equals(tempobj.getCycle_id())
							&& sections_obj.getYear().equals(tempobj.getYear())
							&& sections_obj.getForm_2engage_id().equals(tempobj.getAuto_id())) {

						if ("WEB".equals(sections_obj.getDatafrom())) {

							String sql_update = "UPDATE `form_2_section_details` SET `section_select`=?,`section_text`=?,"
									+ "`user_id`=?," + "`record_created`=? WHERE  `form_2_section_id`=?";

							row = jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(sql_update);
								ps.setString(1, sections_obj.getSection_select());
								ps.setString(2, sections_obj.getSection_text());
								ps.setString(3, sections_obj.getUser_id());
								ps.setString(4, formatedDateTime);
								ps.setString(5, sections_obj.getAuto_id());
								return ps;
							});

							sendobj3.setForm_2engage_id(sections_obj.getForm_2engage_id());
							sendobj3.setSection_name(sections_obj.getSection_name());
							sendobj3.setSection_select(sections_obj.getSection_select());
							sendobj3.setSection_text(sections_obj.getSection_text());
							sendobj3.setDistrict_id(sections_obj.getDistrict_id());
							sendobj3.setCycle_id(sections_obj.getCycle_id());
							sendobj3.setYear(sections_obj.getYear());
							sendobj3.setUser_id(sections_obj.getUser_id());
							sendobj3.setRecordcreated(formatedDateTime);

							sendobj3.setAuto_id(sections_obj.getAuto_id());
							sendobj3.setTimestamp(formatedDateTime);
							sendobj3.setCountry_id(sections_obj.getCountry_id());
							sendobj3.setProvince_id(sections_obj.getProvince_id());
							sendobj3.setDatafrom("WEB");

							list_sections_send.add(sendobj3);

						} else {
							KeyHolder keyHolder2 = new GeneratedKeyHolder();
							jdbcTemplate.update(connection -> {
								PreparedStatement ps = connection.prepareStatement(
										"INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
												+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
												+ "				`user_id`, `record_created`) VALUES (?,?,?,?,?,?,?,?,?)",
										Statement.RETURN_GENERATED_KEYS);
								ps.setString(1, "" + sections_obj.getForm_2engage_id());
								
								String section = "PROLE";
								
								if("PROLE".equals(sections_obj.getSection_name())) {
									section = "section_2";
								}
								else if("CEAI".equals(sections_obj.getSection_name())) {
									section = "section_3";
								}
								else if("EEE".equals(sections_obj.getSection_name())) {
									section = "section_4";							
															}
								else if("THLD".equals(sections_obj.getSection_name())) {
									section = "section_5";
								}
								
								ps.setString(2, section);
								ps.setString(3, sections_obj.getSection_select());
								ps.setString(4, sections_obj.getSection_text());
								ps.setString(5, sections_obj.getDistrict_id());
								ps.setString(6, sections_obj.getCycle_id());
								ps.setString(7, sections_obj.getYear());
								ps.setString(8, sections_obj.getUser_id());
								ps.setString(9, formatedDateTime);

								return ps;
							}, keyHolder2);

							sendobj3.setForm_2engage_id(sections_obj.getForm_2engage_id());
							sendobj3.setSection_name(sections_obj.getSection_name());
							sendobj3.setSection_select(sections_obj.getSection_select());
							sendobj3.setSection_text(sections_obj.getSection_text());
							sendobj3.setDistrict_id(sections_obj.getDistrict_id());
							sendobj3.setCycle_id(sections_obj.getCycle_id());
							sendobj3.setYear(sections_obj.getYear());
							sendobj3.setUser_id(sections_obj.getUser_id());
							sendobj3.setRecordcreated(formatedDateTime);
							sendobj3.setAuto_id("" + keyHolder2.getKey().longValue());
							sendobj3.setTimestamp(formatedDateTime);
							sendobj3.setCountry_id(sections_obj.getCountry_id());
							sendobj3.setProvince_id(sections_obj.getProvince_id());
							sendobj3.setDatafrom("WEB");
							list_sections_send.add(sendobj3);
						}

					}

				}
			}

		}

		response.setForm2Engage(list_primary_send);
		response.setForm2stakeholders(list_stakes_send);
		response.setForm2docs(list_sections_send);
		
		response.setMapping(mapping);		
		
		return response;

	}
	
	

	public Form2EnagageSendAllDataBean retrieveForm2Engage_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin_Authenticated(
			UserCredentialsFromAndroidBean model,String LoggedinUserId) {

		Form2EnagageSendAllDataBean response = new Form2EnagageSendAllDataBean();
		
		

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
			
						
			response.setForm2Engage(new ArrayList<>());
			response.setForm2stakeholders(new ArrayList<>());
			response.setForm2docs(new ArrayList<>());

			response.setError_code("200");
			response.setMessage("Sending Form2 Engage Data");
		}
		else {
			System.out.println("Not Returning in half!!! 0 or different value rather than -1");
		}
		

		List<Form2EnagagePrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form2EnagageStakeholdersTableDataBean> list2 = new ArrayList<>();
		List<Form2EnagageSectiondetails> list3 = new ArrayList<>();
		
		String sql1 = "";
		
		
		if("0".equals(user_priv.getAll_districts())) {
			sql1 = "SELECT form2.`completed`, form2.`form_2_id`,form2.`form_2_date_of_meeting`,form2.`form_2_venue`,"
					+ " form2.`form_2_filled`,form2.`form_2_filled_others`,form2.`district_id`,"
					+ " form2.`cycle_id`,form2.`financial_year`,form2.`user_id`,form2.`record_created`,"
					+ " d.`country_id`,d.`state_id`,cs.`region_id` FROM `form_2_filled_by` form2  left join  `district` d  "
					+ " on form2.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2.`financial_year` >= 2019";

		}
		else {
			sql1 = "SELECT form2.`completed`, form2.`form_2_id`,form2.`form_2_date_of_meeting`,form2.`form_2_venue`,"
					+ " form2.`form_2_filled`,form2.`form_2_filled_others`,form2.`district_id`,"
					+ " form2.`cycle_id`,form2.`financial_year`,form2.`user_id`,form2.`record_created`,"
					+ " d.`country_id`,d.`state_id`,cs.`region_id` FROM `form_2_filled_by` form2  left join  `district` d  "
					+ " on form2.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form2.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form2.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form2EnagagePrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagagePrimaryTableDataBean obj = new Form2EnagagePrimaryTableDataBean();

				obj.setAuto_id(rs.getString("form_2_id"));
				obj.setMeetingdate(rs.getString("form_2_date_of_meeting"));
				obj.setMeetingvenue(rs.getString("form_2_venue"));
				obj.setChairpersonid(rs.getString("form_2_filled"));

				String temps1 = "";

				if (rs.getString("form_2_filled_others") == null
						|| "null".equals(rs.getString("form_2_filled_others"))) {
					temps1 = "";
				} else {
					temps1 = rs.getString("form_2_filled_others");
				}

				obj.setOtherchairperson(temps1);
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
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});
		
		String sql2 = "";
		
		if("0".equals(user_priv.getAll_districts())) {
			sql2 = "SELECT form2stake.`form_2_stk_id`,    form2stake.`form_2_id`,    form2stake.`stake_label`,  "
					+ "  form2stake.`stakeholder_text`,    form2stake.`district_id`,    form2stake.`cycle_id`,  "
					+ "  form2stake.`financial_year`,    form2stake.`user_id`,    form2stake.`record_created`,d.`district_id` as `dst2`, "
					+ "  d.`country_id`, d.`state_id`, cs.`region_id` FROM `form_2_stakeholders` form2stake  "
					+ "  left join  `district` d on form2stake.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ "  where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2stake.`financial_year` >= 2019";

		}
		else {
			sql2 = "SELECT form2stake.`form_2_stk_id`,    form2stake.`form_2_id`,    form2stake.`stake_label`,  "
					+ "  form2stake.`stakeholder_text`,    form2stake.`district_id`,    form2stake.`cycle_id`,  "
					+ "  form2stake.`financial_year`,    form2stake.`user_id`,    form2stake.`record_created`,d.`district_id` as `dst2`, "
					+ "  d.`country_id`, d.`state_id`, cs.`region_id` FROM `form_2_stakeholders` form2stake  "
					+ "  left join  `district` d on form2stake.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
					+ "  where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2stake.`financial_year` >= 2019   and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form2stake.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form2stake.`financial_year` IN ("+user_priv.getAll_years()+");";

		}

		
		Object[] params2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list2 = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form2EnagageStakeholdersTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagageStakeholdersTableDataBean obj = new Form2EnagageStakeholdersTableDataBean();

				obj.setAuto_id(rs.getString("form_2_stk_id"));
				obj.setForm_2engage_id(rs.getString("form_2_id"));
				
				String stake_lbl = "PRIMARY";
				
				if("Primary Stakeholder".equals(rs.getString("stake_label"))) {  
					stake_lbl = "PRIMARY";
				}
				else 
				{
					stake_lbl = "SECONDARY";
				}
				obj.setStake_label(stake_lbl);
				obj.setStakeholder_text(rs.getString("stakeholder_text"));
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
			sql3 = "SELECT form2detail.`form_2_section_id`,form2detail.`form_2_id`,"
					+ " form2detail.`section_name`,form2detail.`section_select`,form2detail.`section_text`,"
					+ " form2detail.`district_id`,form2detail.`cycle_id`,form2detail.`financial_year`, "
					+ " form2detail.`user_id`,form2detail.`record_created`,d.`district_id` as `dst2`, d.`country_id`, "
					+ " d.`state_id`, cs.`region_id`  FROM `form_2_section_details`  form2detail  left join  `district` d "
					+ " on form2detail.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "  where d.`district_id`=?   and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2detail.`financial_year` >= 2019";

		}
		else {
			sql3 = "SELECT form2detail.`form_2_section_id`,form2detail.`form_2_id`,"
					+ " form2detail.`section_name`,form2detail.`section_select`,form2detail.`section_text`,"
					+ " form2detail.`district_id`,form2detail.`cycle_id`,form2detail.`financial_year`, "
					+ " form2detail.`user_id`,form2detail.`record_created`,d.`district_id` as `dst2`, d.`country_id`, "
					+ " d.`state_id`, cs.`region_id`  FROM `form_2_section_details`  form2detail  left join  `district` d "
					+ " on form2detail.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
					+ "  where d.`district_id`=?   and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2detail.`financial_year` >= 2019    and d.`district_id` IN ("+user_priv.getAll_districts()+")  and form2detail.`cycle_id` IN ("+user_priv.getAll_cycles()+")   and form2detail.`financial_year` IN ("+user_priv.getAll_years()+");"; 

		}

		
		Object[] params3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list3 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form2EnagageSectiondetails> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagageSectiondetails obj = new Form2EnagageSectiondetails();

				obj.setAuto_id(rs.getString("form_2_section_id"));
				obj.setForm_2engage_id(rs.getString("form_2_id"));
				
				String section = "section_2";
				
				if("section_2".equals(rs.getString("section_name"))) {
					section = "PROLE";
				}
				else if("section_3".equals(rs.getString("section_name"))) 
				{
					section = "CEAI";
				}
				else if("section_4".equals(rs.getString("section_name"))) 
				{
					section = "EEE";
				}
				else if("section_5".equals(rs.getString("section_name"))) 
				{
					section = "THLD";
				}
				
				obj.setSection_name(section);
				obj.setSection_select(rs.getString("section_select"));
				obj.setSection_text(rs.getString("section_text"));
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

		response.setForm2Engage(list1);
		response.setForm2stakeholders(list2);
		response.setForm2docs(list3);

		response.setError_code("200");
		response.setMessage("Sending Form2 Engage Data");

		if (list1.size() == 0 && list2.size() == 0 && list3.size() == 0) {

			response.setForm2Engage(new ArrayList<>());
			response.setForm2stakeholders(list2);
			response.setForm2docs(list3);

			response.setError_code("200");
			response.setMessage("Sending Form2 Engage Data");

		}

		return response;
	}


	public Form2EnagageSendAllDataBean retrieveForm2Engage_AllCycles_AllYearDataForSingleDistrictAtAndroidLogin(
			UserCredentialsFromAndroidBean model) {

		Form2EnagageSendAllDataBean response = new Form2EnagageSendAllDataBean();

		List<Form2EnagagePrimaryTableDataBean> list1 = new ArrayList<>();
		List<Form2EnagageStakeholdersTableDataBean> list2 = new ArrayList<>();
		List<Form2EnagageSectiondetails> list3 = new ArrayList<>();

		String sql1 = "SELECT form2.`completed`, form2.`form_2_id`,form2.`form_2_date_of_meeting`,form2.`form_2_venue`,"
				+ " form2.`form_2_filled`,form2.`form_2_filled_others`,form2.`district_id`,"
				+ " form2.`cycle_id`,form2.`financial_year`,form2.`user_id`,form2.`record_created`,"
				+ " d.`country_id`,d.`state_id`,cs.`region_id` FROM `form_2_filled_by` form2  left join  `district` d  "
				+ " on form2.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ " where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2.`financial_year` >= 2019";

		Object[] params1 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list1 = jdbcTemplate.query(sql1, params1, rs -> {

			List<Form2EnagagePrimaryTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagagePrimaryTableDataBean obj = new Form2EnagagePrimaryTableDataBean();

				obj.setAuto_id(rs.getString("form_2_id"));
				obj.setMeetingdate(rs.getString("form_2_date_of_meeting"));
				obj.setMeetingvenue(rs.getString("form_2_venue"));
				obj.setChairpersonid(rs.getString("form_2_filled"));

				String temps1 = "";

				if (rs.getString("form_2_filled_others") == null
						|| "null".equals(rs.getString("form_2_filled_others"))) {
					temps1 = "";
				} else {
					temps1 = rs.getString("form_2_filled_others");
				}

				obj.setOtherchairperson(temps1);
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
			}
			/* We can also return any variable-data from here but not used currently */
			return templist;
		});

		String sql2 = "SELECT form2stake.`form_2_stk_id`,    form2stake.`form_2_id`,    form2stake.`stake_label`,  "
				+ "  form2stake.`stakeholder_text`,    form2stake.`district_id`,    form2stake.`cycle_id`,  "
				+ "  form2stake.`financial_year`,    form2stake.`user_id`,    form2stake.`record_created`,d.`district_id` as `dst2`, "
				+ "  d.`country_id`, d.`state_id`, cs.`region_id` FROM `form_2_stakeholders` form2stake  "
				+ "  left join  `district` d on form2stake.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id  "
				+ "  where d.`district_id`=? and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2stake.`financial_year` >= 2019";

		Object[] params2 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list2 = jdbcTemplate.query(sql2, params2, rs -> {

			List<Form2EnagageStakeholdersTableDataBean> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagageStakeholdersTableDataBean obj = new Form2EnagageStakeholdersTableDataBean();

				obj.setAuto_id(rs.getString("form_2_stk_id"));
				obj.setForm_2engage_id(rs.getString("form_2_id"));
				
				String stake_lbl = "PRIMARY";
				
				if("Primary Stakeholder".equals(rs.getString("stake_label"))) {  
					stake_lbl = "PRIMARY";
				}
				else 
				{
					stake_lbl = "SECONDARY";
				}
				obj.setStake_label(stake_lbl);
				obj.setStakeholder_text(rs.getString("stakeholder_text"));
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

		String sql3 = "SELECT form2detail.`form_2_section_id`,form2detail.`form_2_id`,"
				+ " form2detail.`section_name`,form2detail.`section_select`,form2detail.`section_text`,"
				+ " form2detail.`district_id`,form2detail.`cycle_id`,form2detail.`financial_year`, "
				+ " form2detail.`user_id`,form2detail.`record_created`,d.`district_id` as `dst2`, d.`country_id`, "
				+ " d.`state_id`, cs.`region_id`  FROM `form_2_section_details`  form2detail  left join  `district` d "
				+ " on form2detail.district_id=d.district_id  left join  `country_states` cs on d.state_id=cs.cs_id   "
				+ "  where d.`district_id`=?   and  d.`state_id`=? and  cs.`region_id`=?  and  d.`country_id`=?   and  form2detail.`financial_year` >= 2019";

		Object[] params3 = new Object[] { model.getDistrict_id(), model.getProvince_id(), model.getRegion_id(),
				model.getCountry_id() };

		list3 = jdbcTemplate.query(sql3, params3, rs -> {

			List<Form2EnagageSectiondetails> templist = new ArrayList<>();

			while (rs.next()) {

				Form2EnagageSectiondetails obj = new Form2EnagageSectiondetails();

				obj.setAuto_id(rs.getString("form_2_section_id"));
				obj.setForm_2engage_id(rs.getString("form_2_id"));
				
				String section = "section_2";
				
				if("section_2".equals(rs.getString("section_name"))) {
					section = "PROLE";
				}
				else if("section_3".equals(rs.getString("section_name"))) 
				{
					section = "CEAI";
				}
				else if("section_4".equals(rs.getString("section_name"))) 
				{
					section = "EEE";
				}
				else if("section_5".equals(rs.getString("section_name"))) 
				{
					section = "THLD";
				}
				
				obj.setSection_name(section);
				obj.setSection_select(rs.getString("section_select"));
				obj.setSection_text(rs.getString("section_text"));
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

		response.setForm2Engage(list1);
		response.setForm2stakeholders(list2);
		response.setForm2docs(list3);

		response.setError_code("200");
		response.setMessage("Sending Form2 Engage Data");

		if (list1.size() == 0 && list2.size() == 0 && list3.size() == 0) {

			response.setForm2Engage(new ArrayList<>());
			response.setForm2stakeholders(list2);
			response.setForm2docs(list3);

			response.setError_code("200");
			response.setMessage("Sending Form2 Engage Data");

		}

		return response;
	}

	public Form2Engage retrieveForm2EngageDetails(String district_id, String cycle_id, String year, String user_id) {

		Form2Engage obj = new Form2Engage();

//		"INSERT INTO form_2_filled_by(form_2_date_of_meeting,form_2_venue,form_2_filled,district_id,cycle_id,financial_year,user_id,record_created)\r\n" + 
//		"		 VALUES (?,?,?,?,?,?,?,?)";

		String sql1 = "SELECt * FROM `form_2_filled_by` where `district_id`=? and `cycle_id`=? and `financial_year`=?";
		Object[] params1 = new Object[] { district_id, cycle_id, year };

		jdbcTemplate.query(sql1, params1, rs -> {

			while (rs.next()) {

				obj.setForm_2_date_of_meeting(rs.getString("form_2_date_of_meeting"));
				obj.setForm_2_venue(rs.getString("form_2_venue"));
				obj.setForm_2_filled(rs.getString("form_2_filled"));
				obj.setForm_2_filled_others(rs.getString("form_2_filled_others"));
				obj.setDistrict(rs.getString("district_id"));
				obj.setCycle(rs.getString("cycle_id"));
				obj.setYear(rs.getString("financial_year"));
				obj.setUserid(rs.getString("user_id"));
				obj.setForm_2_id(rs.getString("form_2_id"));
				obj.setCompleted(rs.getString("completed"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

//		"INSERT INTO form_2_stakeholders(form_2_id,stake_label,stakeholder_text,"
//		+ "district_id,cycle_id,financial_year,user_id,"
//		+ "record_created) VALUES (?,?,?,?,?,?,?,?)";

		String sql2 = "SELECT * FROM form_2_stakeholders where district_id=? and cycle_id=? and financial_year=?  and stake_label='Primary Stakeholder' and stakeholder_text!=''  order by form_2_stk_id asc";
		Object[] params2 = new Object[] { district_id, cycle_id, year };

		List<Form2EngagePartACommonStakeHoldersArray> p_stakeholders_list = new ArrayList<>();

		jdbcTemplate.query(sql2, params2, rs -> {

			while (rs.next()) {

				Form2EngagePartACommonStakeHoldersArray temp_obj = new Form2EngagePartACommonStakeHoldersArray();
				temp_obj.setDocument_name("" + rs.getString("stakeholder_text"));
				temp_obj.setDocument_id("" + rs.getString("form_2_stk_id"));
				temp_obj.setDocument_label("" + rs.getString("stake_label"));

				p_stakeholders_list.add(temp_obj);
				obj.setPrimary_stake_label(rs.getString("stake_label"));
				obj.setPrimary_stakeholder_text(rs.getString("stakeholder_text"));
				obj.setPrimary_stakeholder_id(rs.getString("form_2_stk_id"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		obj.setPrimary_stake_array(p_stakeholders_list);

		String sql3 = "SELECT * FROM form_2_stakeholders where district_id=? and cycle_id=? and financial_year=? and stake_label='Secondary Stakeholder' and stakeholder_text!=''  order by form_2_stk_id asc";
		Object[] params3 = new Object[] { district_id, cycle_id, year };

		List<Form2EngagePartACommonStakeHoldersArray> s_stakeholders_list = new ArrayList<>();

		jdbcTemplate.query(sql3, params3, rs -> {

			while (rs.next()) {

				Form2EngagePartACommonStakeHoldersArray temp_obj = new Form2EngagePartACommonStakeHoldersArray();
				temp_obj.setDocument_name("" + rs.getString("stakeholder_text"));
				temp_obj.setDocument_id("" + rs.getString("form_2_stk_id"));
				temp_obj.setDocument_label("" + rs.getString("stake_label"));

				s_stakeholders_list.add(temp_obj);

				obj.setSecondary_stake_label(rs.getString("stake_label"));
				obj.setSecondary_stakeholder_text(rs.getString("stakeholder_text"));
				obj.setSecondary_stakeholder_id(rs.getString("form_2_stk_id"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		obj.setSecondary_stake_array(s_stakeholders_list);

//"INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`," + 
//				"				`section_text`, `district_id`, `cycle_id`, `financial_year`, " + 
//				"				`user_id`, `record_created`) VALUES (?,?,?," + 
//				"						?,?,?,?,?,?)";

		String sql4 = "SELECT * FROM `form_2_section_details` where district_id=? and cycle_id=? and financial_year=? and section_name='section_2' order by form_2_section_id asc";
		Object[] params4 = new Object[] { district_id, cycle_id, year };
		List<Form2EngagePartBCommonArray> definingprimaryrole_list = new ArrayList<>();

		jdbcTemplate.query(sql4, params4, rs -> {

			while (rs.next()) {
				Form2EngagePartBCommonArray temp_obj = new Form2EngagePartBCommonArray();
				temp_obj.setDocument_select_stakeholder(rs.getString("section_select"));
				temp_obj.setDocument_desc(rs.getString("section_text"));
				temp_obj.setForm_2_section_id(rs.getString("form_2_section_id"));

				definingprimaryrole_list.add(temp_obj);

				obj.setDefining_primary_role_section_select(rs.getString("section_select"));
				obj.setDefining_primary_role_section_text(rs.getString("section_text"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		obj.setDefiningprimaryrole_array(definingprimaryrole_list);

		String sql5 = "SELECT * FROM `form_2_section_details` where district_id=? and cycle_id=? and financial_year=? and section_name='section_3' order by form_2_section_id asc";
		Object[] params5 = new Object[] { district_id, cycle_id, year };

		List<Form2EngagePartBCommonArray> effort_to_address_issue_list = new ArrayList<>();

		jdbcTemplate.query(sql5, params5, rs -> {

			while (rs.next()) {
				Form2EngagePartBCommonArray temp_obj = new Form2EngagePartBCommonArray();
				temp_obj.setDocument_select_stakeholder(rs.getString("section_select"));
				temp_obj.setDocument_desc(rs.getString("section_text"));
				temp_obj.setForm_2_section_id(rs.getString("form_2_section_id"));
				effort_to_address_issue_list.add(temp_obj);

				obj.setCurrent_effort_to_address_the_issue_section_select(rs.getString("section_select"));
				obj.setCurrent_effort_to_address_the_issue_section_text(rs.getString("section_text"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		obj.setEfforttoaddressissue_array(effort_to_address_issue_list);

		String sql6 = "SELECT * FROM `form_2_section_details` where district_id=? and cycle_id=? and financial_year=? and section_name='section_4' order by form_2_section_id asc";
		Object[] params6 = new Object[] { district_id, cycle_id, year };

		List<Form2EngagePartBCommonArray> enhance_efficiency_list = new ArrayList<>();

		jdbcTemplate.query(sql6, params6, rs -> {

			while (rs.next()) {
				Form2EngagePartBCommonArray temp_obj = new Form2EngagePartBCommonArray();
				temp_obj.setDocument_select_stakeholder(rs.getString("section_select"));
				temp_obj.setDocument_desc(rs.getString("section_text"));
				temp_obj.setForm_2_section_id(rs.getString("form_2_section_id"));
				enhance_efficiency_list.add(temp_obj);

				obj.setHow_to_enhance_engagement_and_efficiency_section_select(rs.getString("section_select"));
				obj.setHow_to_enhance_engagement_and_efficiency_section_text(rs.getString("section_text"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		obj.setEnhanceefficiency_array(enhance_efficiency_list);

		String sql7 = "SELECT * FROM `form_2_section_details` where district_id=? and cycle_id=? and financial_year=? and section_name='section_5' order by form_2_section_id asc";
		Object[] params7 = new Object[] { district_id, cycle_id, year };

		List<Form2EngagePartBCommonArray> theme_lead_list = new ArrayList<>();

		jdbcTemplate.query(sql7, params7, rs -> {

			while (rs.next()) {
				Form2EngagePartBCommonArray temp_obj = new Form2EngagePartBCommonArray();
				temp_obj.setDocument_select_stakeholder(rs.getString("section_select"));
				temp_obj.setDocument_desc(rs.getString("section_text"));
				temp_obj.setForm_2_section_id(rs.getString("form_2_section_id"));

				theme_lead_list.add(temp_obj);

				obj.setTheme_lead_by_department_section_select(rs.getString("section_select"));
				obj.setTheme_lead_by_department_section_text(rs.getString("section_text"));
			}
			/* We can also return any variable-data from here but not used currently */
			return "";
		});

		obj.setThemeleadbydpt_array(theme_lead_list);

		return obj;
	}

	public SavedForm2EngageResponse saveForm2EngageToDb(Form2Engage model) {

		// will give us the current time and date
		LocalDateTime current = LocalDateTime.now();

		// to print in a particular format
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		String formatedDateTime = current.format(format);

		
//		INSERT INTO form_2_filled_by(form_2_date_of_meeting,form_2_venue,form_2_filled,district_id,cycle_id,financial_year,user_id,record_created)
//		 VALUES (:chk_date,:filled_by,:verified_by,:district,:cycle,:financial_year,:user_id,:record_created)

		// execute insert query to insert the data
		// return number of row / rows processed by the executed query
		int row = 0;

		String sql1 = "INSERT INTO form_2_filled_by(form_2_date_of_meeting,form_2_venue,form_2_filled,form_2_filled_others,district_id,cycle_id,financial_year,user_id,record_created,completed)\r\n"
				+ "		 VALUES (?,?,?,?,?,?,?,?,?,?)";

		KeyHolder keyHolder = new GeneratedKeyHolder();

		row = jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, model.getForm_2_date_of_meeting());
			ps.setString(2, model.getForm_2_venue());
			ps.setString(3, model.getForm_2_filled());
			ps.setString(4, model.getForm_2_filled_others());
			ps.setString(5, model.getDistrict());
			ps.setString(6, model.getCycle());
			ps.setString(7, model.getYear());
			ps.setString(8, model.getUserid());
			ps.setString(9, formatedDateTime);
			ps.setString(10, model.getCompleted());
			return ps;
		}, keyHolder);

		// ResultSet rs = ps.getGeneratedKeys();

		long p_key = keyHolder.getKey().longValue();

		

		/* Inserting Form2Engage Stakeholders in Db */

		List<Form2EngagePartACommonStakeHoldersArray> list1 = new ArrayList<>();
		List<Form2EngagePartACommonStakeHoldersArray> temp1 = new ArrayList<>();

		if (model.getPrimary_stakeholder_text() == null || "null".equals(model.getPrimary_stakeholder_text())) {

		} else {
			temp1.add(new Form2EngagePartACommonStakeHoldersArray(model.getPrimary_stakeholder_text()));
			list1.addAll(temp1);
		}

		

		if (model.getPrimary_stake_array().size() != 0) {
			list1.addAll(model.getPrimary_stake_array());
		}

		List<Form2EngagePartACommonStakeHoldersArray> list2 = new ArrayList<>();
		List<Form2EngagePartACommonStakeHoldersArray> temp2 = new ArrayList<>();

		if (model.getSecondary_stakeholder_text() == null || "null".equals(model.getSecondary_stakeholder_text())) {

		} else {
			temp2.add(new Form2EngagePartACommonStakeHoldersArray(model.getSecondary_stakeholder_text()));
			list2.addAll(temp2);
		}

		

		if (model.getSecondary_stake_array().size() != 0) {
			list2.addAll(model.getSecondary_stake_array());
		}

		

		// Inserting primary Stakeholders in Db

//	jdbcTemplate.batchUpdate(
//				"INSERT INTO form_2_stakeholders(form_2_id,stake_label,stakeholder_text,"
//						+ "district_id,cycle_id,financial_year,user_id," + "record_created) VALUES (?,?,?,?,?,?,?,?)",
//				new BatchPreparedStatementSetter() {
//
//					public void setValues(PreparedStatement ps, int i) throws SQLException {
//		ps.setString(1, "" + p_key);
//		ps.setString(2, "Primary Stakeholder");
//		ps.setString(3, list1.get(i).getDocument_name());
//		ps.setString(4, model.getDistrict());
//		ps.setString(5, model.getCycle());
//		ps.setString(6, model.getYear());
//		ps.setString(7, model.getUserid());
//		ps.setString(8, formatedDateTime);
//					}
//
//					public int getBatchSize() {
//						return list1.size();
//					}
//
//				});

		KeyHolder keyHolder2 = new GeneratedKeyHolder();

		List<Map<String, String>> p_key_of_p_stakeholder_with_text = new ArrayList<>();

		for (int index1 = 0; index1 < list1.size(); index1++) {

			int temp_pos = index1;

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement("INSERT INTO form_2_stakeholders(form_2_id,stake_label,stakeholder_text,"
								+ "district_id,cycle_id,financial_year,user_id,"
								+ "record_created) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Primary Stakeholder");
				ps.setString(3, list1.get(temp_pos).getDocument_name());
				ps.setString(4, model.getDistrict());
				ps.setString(5, model.getCycle());
				ps.setString(6, model.getYear());
				ps.setString(7, model.getUserid());
				ps.setString(8, formatedDateTime);

				return ps;
			}, keyHolder2);

			
			Map<String, String> m = new HashMap<>();
			m.put("" + list1.get(temp_pos).getDocument_name(), "" + keyHolder2.getKey().longValue());
			p_key_of_p_stakeholder_with_text.add(m);

		}

	
		KeyHolder keyHolder3 = new GeneratedKeyHolder();

		List<Map<String, String>> p_key_of_sec_stakeholder_with_text = new ArrayList<>();

		for (int index2 = 0; index2 < list2.size(); index2++) {
			int temp_pos = index2;

			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection
						.prepareStatement("INSERT INTO form_2_stakeholders(form_2_id,stake_label,stakeholder_text,"
								+ "district_id,cycle_id,financial_year,user_id,"
								+ "record_created) VALUES (?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, "" + p_key);
				ps.setString(2, "Secondary Stakeholder");
				ps.setString(3, list2.get(temp_pos).getDocument_name());
				ps.setString(4, model.getDistrict());
				ps.setString(5, model.getCycle());
				ps.setString(6, model.getYear());
				ps.setString(7, model.getUserid());
				ps.setString(8, formatedDateTime);

				return ps;
			}, keyHolder3);

			

			Map<String, String> m = new HashMap<>();
			m.put("" + list2.get(temp_pos).getDocument_name(), "" + keyHolder3.getKey().longValue());
			p_key_of_sec_stakeholder_with_text.add(m);
		}

		/* End of Inserting Form2Engage Stakeholders in Db */



		/* Inserting Form2Engage PartB Data */

		List<Form2EngagePartBCommonArray> list3 = new ArrayList<>();
		List<Form2EngagePartBCommonArray> temp3 = new ArrayList<>();

		if (model.getDefining_primary_role_section_text() == null
				|| "null".equals(model.getDefining_primary_role_section_text())
				|| "".equals(model.getDefining_primary_role_section_text().trim())) {

		} else {
			temp3.add(new Form2EngagePartBCommonArray(model.getDefining_primary_role_section_select(),
					model.getDefining_primary_role_section_text()));
			list3.addAll(temp3);
		}

		

		if (model.getDefiningprimaryrole_array().size() != 0) {
			list3.addAll(model.getDefiningprimaryrole_array());
		}

		List<Form2EngagePartBCommonArray> list4 = new ArrayList<>();
		List<Form2EngagePartBCommonArray> temp4 = new ArrayList<>();

		if (model.getCurrent_effort_to_address_the_issue_section_text() == null
				|| "null".equals(model.getCurrent_effort_to_address_the_issue_section_text())
				|| "".equals(model.getCurrent_effort_to_address_the_issue_section_text().trim())) {

		} else {
			temp4.add(new Form2EngagePartBCommonArray(model.getCurrent_effort_to_address_the_issue_section_select(),
					model.getCurrent_effort_to_address_the_issue_section_text()));
			list4.addAll(temp4);
		}

		
		if (model.getEfforttoaddressissue_array().size() != 0) {
			list4.addAll(model.getEfforttoaddressissue_array());
		}

		List<Form2EngagePartBCommonArray> list5 = new ArrayList<>();
		List<Form2EngagePartBCommonArray> temp5 = new ArrayList<>();

		if (model.getHow_to_enhance_engagement_and_efficiency_section_text() == null
				|| "null".equals(model.getHow_to_enhance_engagement_and_efficiency_section_text())
				|| "".equals(model.getHow_to_enhance_engagement_and_efficiency_section_text().trim())) {

		} else {
			temp5.add(
					new Form2EngagePartBCommonArray(model.getHow_to_enhance_engagement_and_efficiency_section_select(),
							model.getHow_to_enhance_engagement_and_efficiency_section_text()));
			list5.addAll(temp5);
		}

		
		if (model.getEnhanceefficiency_array().size() != 0) {
			list5.addAll(model.getEnhanceefficiency_array());
		}

		List<Form2EngagePartBCommonArray> list6 = new ArrayList<>();
		List<Form2EngagePartBCommonArray> temp6 = new ArrayList<>();

		if (model.getTheme_lead_by_department_section_text() == null
				|| "null".equals(model.getTheme_lead_by_department_section_text())
				|| "".equals(model.getTheme_lead_by_department_section_text().trim())) {

		} else {
			temp6.add(new Form2EngagePartBCommonArray(model.getTheme_lead_by_department_section_select(),
					model.getTheme_lead_by_department_section_text()));
			list6.addAll(temp6);
		}

		

		if (model.getThemeleadbydpt_array().size() != 0) {
			list6.addAll(model.getThemeleadbydpt_array());
		}

		/* End of Inserting Form2Engage PartB Data */


		for (int i = 0; i < list3.size(); i++) {

			Form2EngagePartBCommonArray obj = list3.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_p_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		// List3 searching for primary key in Loop2
		for (int i = 0; i < list3.size(); i++) {

			Form2EngagePartBCommonArray obj = list3.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_sec_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		jdbcTemplate.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
				+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
				+ "				`user_id`, `record_created`) VALUES (?,?,?," + "						?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "section_2");
						ps.setString(3, list3.get(i).getDocument_select_stakeholder());
						ps.setString(4, list3.get(i).getDocument_desc());
						ps.setString(5, model.getDistrict());
						ps.setString(6, model.getCycle());
						ps.setString(7, model.getYear());
						ps.setString(8, model.getUserid());
						ps.setString(9, formatedDateTime);
					}

					public int getBatchSize() {
						return list3.size();
					}

				});


		for (int i = 0; i < list4.size(); i++) {

			Form2EngagePartBCommonArray obj = list4.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_p_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		// list4 searching for primary key in Loop2
		for (int i = 0; i < list4.size(); i++) {

			Form2EngagePartBCommonArray obj = list4.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_sec_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		jdbcTemplate.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
				+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
				+ "				`user_id`, `record_created`) VALUES (?,?,?," + "						?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "section_3");
						ps.setString(3, list4.get(i).getDocument_select_stakeholder());
						ps.setString(4, list4.get(i).getDocument_desc());
						ps.setString(5, model.getDistrict());
						ps.setString(6, model.getCycle());
						ps.setString(7, model.getYear());
						ps.setString(8, model.getUserid());
						ps.setString(9, formatedDateTime);
					}

					public int getBatchSize() {
						return list4.size();
					}

				});


		for (int i = 0; i < list5.size(); i++) {

			Form2EngagePartBCommonArray obj = list5.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_p_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		// list5 searching for primary key in Loop2
		for (int i = 0; i < list5.size(); i++) {

			Form2EngagePartBCommonArray obj = list5.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_sec_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		jdbcTemplate.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
				+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
				+ "				`user_id`, `record_created`) VALUES (?,?,?," + "						?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "section_4");
						ps.setString(3, list5.get(i).getDocument_select_stakeholder());
						ps.setString(4, list5.get(i).getDocument_desc());
						ps.setString(5, model.getDistrict());
						ps.setString(6, model.getCycle());
						ps.setString(7, model.getYear());
						ps.setString(8, model.getUserid());
						ps.setString(9, formatedDateTime);
					}

					public int getBatchSize() {
						return list5.size();
					}

				});


		for (int i = 0; i < list6.size(); i++) {

			Form2EngagePartBCommonArray obj = list6.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_p_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		// list6 searching for primary key in Loop2
		for (int i = 0; i < list6.size(); i++) {

			Form2EngagePartBCommonArray obj = list6.get(i);

			String stake_name = obj.getDocument_select_stakeholder();

			Map<String, String> m = p_key_of_sec_stakeholder_with_text.stream() // Convert to steam
					.filter(x -> x.get(stake_name) instanceof String) // we want "jack" only
					.findAny() // If 'findAny' then return found
					.orElse(null);

			if (m != null) {
				obj.setDocument_select_stakeholder("" + m.get(stake_name));
			}

		}

		jdbcTemplate.batchUpdate("INSERT INTO `form_2_section_details`(`form_2_id`, `section_name`, `section_select`,"
				+ "				`section_text`, `district_id`, `cycle_id`, `financial_year`, "
				+ "				`user_id`, `record_created`) VALUES (?,?,?," + "						?,?,?,?,?,?)",
				new BatchPreparedStatementSetter() {

					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setString(1, "" + p_key);
						ps.setString(2, "section_5");
						ps.setString(3, list6.get(i).getDocument_select_stakeholder());
						ps.setString(4, list6.get(i).getDocument_desc());
						ps.setString(5, model.getDistrict());
						ps.setString(6, model.getCycle());
						ps.setString(7, model.getYear());
						ps.setString(8, model.getUserid());
						ps.setString(9, formatedDateTime);
					}

					public int getBatchSize() {
						return list6.size();
					}

				});



		SavedForm2EngageResponse responseobj = new SavedForm2EngageResponse();
		responseobj.setProcessname("saved");

		responseobj.setResponse_val("1");

		return responseobj;
	}
}
