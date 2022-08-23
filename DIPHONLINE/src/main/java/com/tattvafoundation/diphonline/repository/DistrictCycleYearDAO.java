package com.tattvafoundation.diphonline.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.model.Cycle;
import com.tattvafoundation.diphonline.model.District;

@Repository
public class DistrictCycleYearDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_districts = "SELECT `district_id`, `country_id`,  `district_name`,  `state_id` FROM `district` where `state_id`=?  order by `district_name`";

	private static final String SQL_country_states = "SELECT * FROM `cycle`";

	public List<District> getAllDistrictsSpecificStatesForCertainRegionCountry(String country_id, String region_id) {

		// SELECT d.`district_id`, d.`country_id`, d.`district_name`, d.`state_id` FROM
		// `district` d left join `country_states` s on d.state_id=s.cs_id where
		// s.`region_id`=3 and s.`country_id`=65 order by `district_name`

		// String sql = "SELECT `district_id`, `country_id`, `district_name`, `state_id`
		// FROM `district` where `state_id`=? order by `district_name`";
		String sql = "SELECT d.`district_id`, d.`country_id`,  d.`district_name`,  d.`state_id`,  s.`region_id` FROM `district` d  left join `country_states` s on d.state_id=s.cs_id   where s.`region_id`=? and s.`country_id`=? order by `district_name`";
		Object[] params = new Object[] { region_id, country_id };

		List<District> dlist = jdbcTemplate.query(sql, params, rs -> {

			List<District> list = new ArrayList<>();
			while (rs.next()) {
				District district = new District();
				district.setDistrict_id(rs.getInt("district_id"));
				district.setCountry_id(rs.getInt("country_id"));
				district.setDistrict_name(rs.getString("district_name"));
				district.setState_id(rs.getString("state_id"));
				district.setRegion_id(rs.getString("region_id"));
				list.add(district);
			}
			/* We can also return any variable-data from here but not used currently */
			return list;
		});

		//System.out.println("District list = " + dlist);
		return dlist;
	}

	public List<District> getAllDistrictsforSpecificStateCountry(String state_id) {

		 String SQL_districts = "SELECT `district_id`, `country_id`,  `district_name`,  `state_id` FROM `district` where `state_id`=?  order by `district_name`";
		String sql = SQL_districts;
		Object[] params = new Object[] { state_id };

		List<District> dlist = jdbcTemplate.query(sql, params, rs -> {

			List<District> list = new ArrayList<>();
			while (rs.next()) {
				District district = new District();
				district.setDistrict_id(rs.getInt("district_id"));
				district.setCountry_id(rs.getInt("country_id"));
				district.setDistrict_name(rs.getString("district_name"));
				district.setState_id(state_id);
				list.add(district);
			}
			/* We can also return any variable-data from here but not used currently */
			return list;
		});

		//System.out.println("District list = " + dlist);
		return dlist;
	}

	public List<Cycle> getAllCyclesforDistrict(String country_id) {

		// use country_id in where clause
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_country_states);

		List<Cycle> clist = new ArrayList<>();

		for (Map<String, Object> row : rows) {// cycle_id,cycle_name
			Cycle cycle = new Cycle();

			cycle.setCycle_id((int) row.get("cycle_id"));
			cycle.setCycle_name((String) row.get("cycle_name"));

			clist.add(cycle);

		}
		return clist;
	}

}