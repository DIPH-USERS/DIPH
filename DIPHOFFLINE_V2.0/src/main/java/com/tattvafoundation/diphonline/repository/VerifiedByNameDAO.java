package com.tattvafoundation.diphonline.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.model.VerifiedByNameBean;
import com.tattvafoundation.diphonline.model.VerifiedByNamesResponse;

@Repository
public class VerifiedByNameDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	public VerifiedByNamesResponse getVerifiedByNames() {
		
		VerifiedByNamesResponse response = new VerifiedByNamesResponse();
		
		String sql = "SELECT `verified_id`,  `verified_by_name` FROM `verified_by`";
		Object[] params = new Object[] {  };

		List<VerifiedByNameBean> nameslist =jdbcTemplate.query(sql, params, rs -> {
			
			List<VerifiedByNameBean> names= new ArrayList<>();
			while (rs.next()) {
				
				String id = rs.getString("verified_id");
				String name = rs.getString("verified_by_name");	
				
				VerifiedByNameBean obj = new VerifiedByNameBean();
				obj.setId(id);
				obj.setName(name);
				
				names.add(obj);
			}
			/* We can also return any variable-data from here but not used currently */
			return names;
		});
		
		response.setVerified_by_name(nameslist); 
		response.setStatus("1");
		response.setMessage("Names Retrieved from Db successfully!"); 
		
		
		return response;
	}
}
