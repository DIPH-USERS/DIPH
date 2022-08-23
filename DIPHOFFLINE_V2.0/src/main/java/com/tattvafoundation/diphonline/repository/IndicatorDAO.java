package com.tattvafoundation.diphonline.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

	public void saveAreaListIntoDatabase(List<Area> areaList) {

		try {
			String truncateSQL = "truncate area";
			String insertSQL = "insert into area(id,area_name) values(?,?)";

			jdbcTemplate.update(truncateSQL);

			jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, areaList.get(i).getId());
					ps.setString(2, areaList.get(i).getAreaName());
				}

				public int getBatchSize() {
					int listSize = areaList.size();
					LOGGER.info("Rows saved into area table of database: " + listSize);
					return listSize;
				}

			});
		} catch (Exception e) {
			LOGGER.info("Error in insert area table: " + e);
		}

	}

	public void saveAreaIndicatorMappingListInDatabase(List<AreaIndicatorMapping> areaIndicatorMappingList) {

		try {
			String truncateSQL = "truncate area_indicator_mapping";
			String insertSQL = "insert into area_indicator_mapping(id,area_id,indicator_id) values(?,?,?)";

			jdbcTemplate.update(truncateSQL);

			jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, areaIndicatorMappingList.get(i).getId());
					ps.setInt(2, areaIndicatorMappingList.get(i).getAreaId());
					ps.setInt(3, areaIndicatorMappingList.get(i).getIndicatorId());
				}

				public int getBatchSize() {
					int listSize = areaIndicatorMappingList.size();
					LOGGER.info("Rows saved into area_indicator_mapping table of database: " + listSize);
					return listSize;
				}

			});
		} catch (Exception e) {
			LOGGER.info("Error in insert area_indicator_mapping table: " + e);
		}

	}

	public void saveIndicatorListInDatabase(List<Indicator> indicatorList) {
		try {
			String truncateSQL = "truncate indicators";
			String insertSQL = "insert into indicators(id,indicator_name,definition,numerator,denominator,source,frequency,area_id,category) values(?,?,?,?,?,?,?,?,?)";

			jdbcTemplate.update(truncateSQL);

			jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, indicatorList.get(i).getId());
					ps.setString(2, indicatorList.get(i).getIndicatorName());
					ps.setString(3, indicatorList.get(i).getDefinition());
					ps.setString(4, indicatorList.get(i).getNumerator());
					ps.setString(5, indicatorList.get(i).getDenominator());
					ps.setString(6, indicatorList.get(i).getSource());
					ps.setString(7, indicatorList.get(i).getFrequency());
					ps.setInt(8, indicatorList.get(i).getAreaId());
					ps.setString(9, indicatorList.get(i).getCategory());
				}

				public int getBatchSize() {
					int listSize = indicatorList.size();
					LOGGER.info("Rows saved into indicators table of database: " + listSize);
					return listSize;
				}

			});
		} catch (Exception e) {
			LOGGER.info("Error in insert indicators table: " + e);
		}

	}

	public void saveOptionalIndicatorListInDatabase(List<OptionalIndicator> indicatorList) {

		try {
			String truncateSQL = "truncate indicators_optional";
			String insertSQL = "insert into indicators_optional(id,indicator_name,definition,numerator,denominator,source,Theme,Current_Available,frequency,area_id,category) values(?,?,?,?,?,?,?,?,?,?,?)";

			jdbcTemplate.update(truncateSQL);

			jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {

				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, indicatorList.get(i).getId());
					ps.setString(2, indicatorList.get(i).getIndicatorName());
					ps.setString(3, indicatorList.get(i).getDefinition());
					ps.setString(4, indicatorList.get(i).getNumerator());
					ps.setString(5, indicatorList.get(i).getDenominator());
					ps.setString(6, indicatorList.get(i).getSource());
					ps.setString(7, indicatorList.get(i).getTheme());
					ps.setString(8, indicatorList.get(i).getCurrentAvailable());
					ps.setString(9, indicatorList.get(i).getFrequency());
					ps.setInt(10, indicatorList.get(i).getAreaId());
					ps.setString(11, indicatorList.get(i).getCategory());
				}

				public int getBatchSize() {
					int listSize = indicatorList.size();
					LOGGER.info("Rows saved into indicators_optional table of database: " + listSize);
					return listSize;
				}

			});
		} catch (Exception e) {
			LOGGER.info("Error in insert indicators_optional table: " + e);
		}
	}

}
