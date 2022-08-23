package com.tattvafoundation.diphonline.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.model.C_States;
import com.tattvafoundation.diphonline.model.Countries;
import com.tattvafoundation.diphonline.model.Countries_States;
import com.tattvafoundation.diphonline.model.Country;
import com.tattvafoundation.diphonline.model.Cycle;
import com.tattvafoundation.diphonline.model.District;
import com.tattvafoundation.diphonline.model.Regions;
import com.tattvafoundation.diphonline.model.States;
import com.tattvafoundation.diphonline.model.StatesandDistrictsBean;

@Repository
public class CountryStateDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String SQL_countries = "SELECT * FROM countries";

	// private static final String SQL_country_states = "SELECT * FROM
	// country_states";

	public Cycle getCycle(String id) {

		Object[] params = new Object[] { id };

		Cycle obj = jdbcTemplate.query("SELECT * FROM cycle where cycle_id=?", params, rs -> {

			Cycle c = new Cycle();
			while (rs.next()) {
				c.setCycle_id(Integer.parseInt(rs.getString("cycle_id")));
				c.setCycle_name(rs.getString("cycle_name"));
			}
			return c;
		});

		return obj;
	}

	public District getDistrict(String id) {

		Object[] params = new Object[] { id };

		District obj = jdbcTemplate.query("SELECT * FROM district where district_id=?", params, rs -> {

			District d = new District();
			while (rs.next()) {
				d.setDistrict_id(Integer.parseInt(rs.getString("district_id")));
				d.setCountry_id(Integer.parseInt(rs.getString("country_id")));
				d.setDistrict_name(rs.getString("district_name"));
			}
			return d;
		});

		return obj;
	}

	public List<Country> getAllCountries() {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_countries);

//		Countries list = new Countries();
		List<Country> countryList = new ArrayList<>();

		for (Map<String, Object> row : rows) {// country_id,country_name,status
			Country country = new Country();

			country.setId((int) row.get("country_id"));
			country.setName((String) row.get("country_name"));
			country.setStatus((String) row.get("status"));
			countryList.add(country);
//			if (!list.getCountryList().contains(country)) {
//				list.getCountryList().add(country);
//			}
		}
//		System.out.println("countryList = ");
//		System.out.println(countryList);

//		if (true) {
//
//			System.out.println(rows);
//			return null;
//		}

		return countryList;
	}

	public void addCountry(Country country) {
		Countries list = new Countries();
		list.getResult().add(country);
	}

	public List<Regions> getAllRegionsForSpecificCountry(String country_id) {

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT `region_id`, `region_name`, `country_id`, `status`, `reproductive_age_women_cfactor`, `under_five_children_cfactor` FROM `region` where `country_id`="
						+ country_id);

		List<Regions> regionList = new ArrayList<Regions>();

		for (Map<String, Object> row : rows) {// cs_id,country_id,state_name,status
			Regions region = new Regions();

			region.setRegion_id((long) row.get("region_id"));
			region.setCountry_id((int) row.get("country_id"));
			region.setRegion_name((String) row.get("region_name"));
			region.setStatus((String) row.get("status"));
			region.setReproductive_age_women_cfactor((String) row.get("reproductive_age_women_cfactor"));
			region.setUnder_five_children_cfactor((String) row.get("under_five_children_cfactor"));

			regionList.add(region);
		}

		return regionList;
	}
	
	public Regions getRegionsForid(String region_id) {

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT `region_name`, `country_id`, `status`, `reproductive_age_women_cfactor`, `under_five_children_cfactor` FROM `region` where `region_id`="
						+ region_id);

		Regions region = new Regions();

		for (Map<String, Object> row : rows) {// cs_id,country_id,state_name,status			
			
			region.setCountry_id((int) row.get("country_id"));
			region.setRegion_name((String) row.get("region_name"));
			region.setStatus((String) row.get("status"));
			region.setReproductive_age_women_cfactor((String) row.get("reproductive_age_women_cfactor"));
			region.setUnder_five_children_cfactor((String) row.get("under_five_children_cfactor"));
			
		}

		return region;
	}

	// getstatesfordistrictsinfo
	public StatesandDistrictsBean states_for_districts(String district_id) {

		
		StatesandDistrictsBean response = new StatesandDistrictsBean();
		
		List<States> stslist = new ArrayList<>();
		stslist = jdbcTemplate.query(
				"SELECT `cs_id`,  `country_id`,  `state_name`,  `status`,  `region_id`  FROM `country_states` ",
				new Object[] {}, rs -> {

					List<States> templist = new ArrayList<>();
					while (rs.next()) {
						States state = new States();
						state.setCs_id(Integer.parseInt(rs.getString("cs_id")));
						state.setCountry_id(Integer.parseInt(rs.getString("country_id")));
						state.setState_name(rs.getString("state_name"));
						state.setStatus(rs.getString("status"));
						state.setRegion_id(rs.getString("region_id"));
						templist.add(state);
					}
					/* We can also return any variable-data from here but not used currently */
					return templist;
				});

		List<District> distslist = new ArrayList<>();
		
		distslist = jdbcTemplate.query(
				"SELECT `district_id`,    `country_id`,    `district_name`,    `state_id`  " 
				+ "  FROM `district` where  `state_id`= (SELECT `state_id`  FROM `district` where `district_id`=?)",
				new Object[] {district_id  }, rs -> { 

					List<District> templist = new ArrayList<>();

					while (rs.next()) {
						District obj = new District();
						obj.setState_id(rs.getString("state_id"));
						obj.setDistrict_id(Integer.parseInt(rs.getString("district_id")));
						obj.setDistrict_name(rs.getString("district_name"));
						obj.setCountry_id(Integer.parseInt(rs.getString("country_id")));
						
						templist.add(obj);
					}
					/* We can also return any variable-data from here but not used currently */
					return templist;
				});
		
		
		response.setStatesList(stslist);
		response.setDistrictsList(distslist); 
		
		return response;
	}

	public List<States> getAllStatesForSpecificRegion(String region_id) {
		// use country_id in where clause
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT `cs_id`,  `country_id`,  `state_name`,  `status`,  `region_id`  FROM `country_states` where `region_id`="
						+ region_id + " ORDER BY `state_name`");

		List<States> statesList = new ArrayList<States>();

		for (Map<String, Object> row : rows) {// cs_id,country_id,state_name,status
			States state = new States();

			state.setCs_id((long) row.get("cs_id"));
			state.setCountry_id((int) row.get("country_id"));
			state.setState_name((String) row.get("state_name"));
			state.setStatus((String) row.get("status"));
			state.setRegion_id(region_id);

			statesList.add(state);
		}
		return statesList;
	}

	public List<States> getAllStatesForSpecificRegion(String country_id, String region_id) {
		// use country_id in where clause
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(
				"SELECT `cs_id`,  `country_id`,  `state_name`,  `status`,  `region_id`  FROM `country_states` where `region_id`="
						+ region_id + " and `country_id`=" + country_id + " ORDER BY `state_name`");

		List<States> statesList = new ArrayList<States>();

		for (Map<String, Object> row : rows) {// cs_id,country_id,state_name,status
			States state = new States();

			state.setCs_id((long) row.get("cs_id"));
			state.setCountry_id((int) row.get("country_id"));
			state.setState_name((String) row.get("state_name"));
			state.setStatus((String) row.get("status"));
			state.setRegion_id(region_id);

			statesList.add(state);

		}
		return statesList;
	}

	public void addState(States states) {
		C_States slist = new C_States();
		slist.getResult().add(states);
	}

	public Countries_States getCountriesAndStates(String country_id) {
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_countries);

		Countries_States cslist = new Countries_States();

		for (Map<String, Object> row : rows) {// country_id,country_name,status
			Country country = new Country();

			country.setId((int) row.get("country_id"));
			country.setName((String) row.get("country_name"));
			country.setStatus((String) row.get("status"));

			if (!cslist.getCountryList().contains(country)) {
				cslist.getCountryList().add(country);
			}

		}

		// System.out.println("List of countries");
		// System.out.println(cslist.getCountryList());

		// use country_id in where clause
		List<Map<String, Object>> rows2 = jdbcTemplate
				.queryForList("SELECT * FROM country_states where country_id=" + country_id);

		// int country_id = slist.getCountry_id();

		for (Map<String, Object> row : rows2) {// cs_id,country_id,state_name,status
			States state = new States();

			state.setCs_id((long) row.get("cs_id"));
			state.setCountry_id((int) row.get("country_id"));
			state.setState_name((String) row.get("state_name"));
			state.setStatus((String) row.get("status"));

			if (!cslist.getStatesList().contains(state)) {
				cslist.getStatesList().add(state);
			}
		}

		// System.out.println("List of country States");
		// System.out.println(cslist.getStatesList());

		return cslist;
	}

	public void addCountriesAndStates(Country country, States state) {
		Countries_States cslist = new Countries_States();
		if (!cslist.getCountryList().contains(country)) {
			cslist.getCountryList().add(country);
		}
		if (!cslist.getStatesList().contains(state)) {
			cslist.getStatesList().add(state);
		}
	}

}
