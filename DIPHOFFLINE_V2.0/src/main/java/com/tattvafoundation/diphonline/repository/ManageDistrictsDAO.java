package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.model.CreateDistrictBean;
import com.tattvafoundation.diphonline.model.DistrictResponseBean;

@Repository
public class ManageDistrictsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public DistrictResponseBean deletedistrict(CreateDistrictBean model) {
		DistrictResponseBean response = new DistrictResponseBean();
		try {
			
			Object[] params1 = { model.getDistrict_name() };
			int rows = jdbcTemplate.update(
					"DELETE FROM `district` WHERE (`district_id` = ?)",
					params1);
			

			if(rows > 0) {
				response.setStatus("deleted");
				response.setMessage("Selected District deleted.");
			}
			else {
				response.setStatus("not_allowed");
				response.setMessage("Server Error.");
			}
				
			
 
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("not_allowed");
			response.setMessage("Server Error.");
			// TODO: handle exception
		}
		return response;
	}

	public DistrictResponseBean createnewdistrict(CreateDistrictBean model) {

		DistrictResponseBean response = new DistrictResponseBean();

		try {
			/*---------------------------------------------------------*/
			
			String sql_check_permit = "SELECT `district_id`,   `country_id`,    TRIM(`district_name`) as `district_name`,  `state_id` FROM `district` where `district_name` = ?";
			Object[] params_check_permit = new Object[] { model.getDistrict_name().trim() };  

			String permission = jdbcTemplate.query(sql_check_permit, params_check_permit, rs -> {

				String permit = "";
				while (rs.next()) {

					String district_id = rs.getString("district_id");
					permit = district_id;

				}
				/* We can also return any variable-data from here but not used currently */
				return permit;
			});
			
			/*---------------------------------------------------------*/
			
			if ("".equals(permission.trim())) {
				//No district with above name exists so create new 
				
				//String sql = "INSERT INTO `district`(`country_id`,`district_name`)VALUES(?,?);";
				
				String sql = "INSERT INTO `district` (`country_id`,`district_name`,`state_id`) VALUES (?,?,?)" ;
				KeyHolder keyHolder = new GeneratedKeyHolder();

				int row = 0;

				row = jdbcTemplate.update(connection -> {
					PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, ""+model.getCountry_id());
					ps.setString(2, "" + model.getDistrict_name());
					ps.setString(3, ""+model.getState_id());  

					return ps;
				}, keyHolder);

				// new user successfully created

				response.setStatus("created");
				response.setMessage("New District Created.");
				
			}
			else 
			{
				//District name already exists.
				
				response.setStatus("exists");
				response.setMessage("District Already Exists");
			}
			
			
			

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus("not_allowed");
			response.setMessage("Server Error.");
			// TODO: handle exception
		}

		return response;
	}
}
