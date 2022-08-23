package com.tattvafoundation.diphonline.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.tattvafoundation.diphonline.indicator.entity.Area;
import com.tattvafoundation.diphonline.indicator.entity.AreaIndicatorMapping;
import com.tattvafoundation.diphonline.indicator.entity.Indicator;
import com.tattvafoundation.diphonline.indicator.entity.OptionalIndicator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Repository
public class IndicatorDAO {

	private static final Logger LOGGER = LoggerFactory.getLogger(IndicatorDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<Area> getAllAreaListInDatabase() {

		String sql = "SELECT * FROM area";

		List<Area> areaList = new ArrayList<>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			Area area = new Area();

			area.setId(((Integer) row.get("id")).intValue());
			area.setAreaName((String) row.get("area_name"));
			areaList.add(area);
		}

		return areaList;
	}

	public List<AreaIndicatorMapping> getAllAreaIndicatorMappingListInDatabase() {

		String sql = "SELECT * FROM area_indicator_mapping";

		List<AreaIndicatorMapping> areaIndicatorMappingList = new ArrayList<>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			AreaIndicatorMapping areaIndicatorMapping = new AreaIndicatorMapping();

			areaIndicatorMapping.setId(((Integer) row.get("id")).intValue());
			areaIndicatorMapping.setAreaId(((Integer) row.get("area_id")).intValue());
			areaIndicatorMapping.setIndicatorId(((Integer) row.get("indicator_id")).intValue());
			areaIndicatorMappingList.add(areaIndicatorMapping);
		}

		return areaIndicatorMappingList;
	}

	public List<Indicator> getAllIndicatorListInDatabase() {

		String sql = "SELECT * FROM indicators";

		List<Indicator> indicatorList = new ArrayList<>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			Indicator indicator = new Indicator();

			indicator.setId(((Integer) row.get("id")).intValue());
			indicator.setIndicatorName((String) row.get("indicator_name"));
			indicator.setDefinition((String) row.get("definition"));
			indicator.setNumerator((String) row.get("numerator"));
			indicator.setDenominator((String) row.get("denominator"));
			indicator.setSource((String) row.get("source"));
			indicator.setFrequency((String) row.get("frequency"));
			indicator.setAreaId(((Integer) row.get("area_id")).intValue());
			indicator.setCategory((String) row.get("category"));
			indicatorList.add(indicator);
		}

		return indicatorList;
	}

	public List<OptionalIndicator> getAllOptionalIndicatorListInDatabase() {

		String sql = "SELECT * FROM indicators_optional";

		List<OptionalIndicator> indicatorList = new ArrayList<>();

		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

		for (Map<String, Object> row : rows) {
			OptionalIndicator indicator = new OptionalIndicator();

			indicator.setId(((Integer) row.get("id")).intValue());
			indicator.setIndicatorName((String) row.get("indicator_name"));
			indicator.setDefinition((String) row.get("definition"));
			indicator.setNumerator((String) row.get("numerator"));
			indicator.setDenominator((String) row.get("denominator"));
			indicator.setSource((String) row.get("source"));
			indicator.setSource((String) row.get("Theme"));
			indicator.setSource((String) row.get("Current_Available"));
			indicator.setFrequency((String) row.get("Frequency"));
			indicator.setAreaId(((Integer) row.get("area_id")).intValue());
			indicator.setCategory((String) row.get("category"));
			indicatorList.add(indicator);
		}

		return indicatorList;
	}
	
}
