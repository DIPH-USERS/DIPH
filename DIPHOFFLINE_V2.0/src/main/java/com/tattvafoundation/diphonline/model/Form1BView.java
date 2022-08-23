package com.tattvafoundation.diphonline.model;

import java.util.List;

public class Form1BView {

	private String date_of_verification;
	private String filled_by;
	private String verified_by_name;
	private String verified_by_other_actual_name;
	private String total_area;
	private String total_area_demogra_id;
	private String total_area_others;
	private String total_pop;
	private String total_pop_demogra_id;
	private String total_pop_others;
	private String num_women_in_reproductive_age_15_49_y;
	private String num_women_in_reproductive_age_15_49_y_source;
	private String num_women_in_reproductive_others;
	private String num_child_under_5_y;
	private String num_child_under_5_y_demogra_id;
	private String no_of_child_under5_others;
	private String rural_pop;
	private String rural_pop_demogra_id;
	private String rural_pop_others;
	private String sc_pop;
	private String sc_pop_demogra_id;
	private String sc_pop_others;
	private String st_pop;
	private String st_pop_demogra_id;
	private String st_pop_others;
	private String pop_density;
	private String pop_density_demogra_id;
	private String pop_dens_others;
	private String total_literacy;
	private String total_literacy_demogra_id;
	private String tot_lit_others;
	private String fem_literacy;
	private String fem_literacy_demogra_id;
	private String female_lit_others;
	private String iphs_theme_name;
	private String iphs_coverage_indicators;
	private String iphs_source;
	private String iphs_data;
	private String iphs_expected_status;
	private String iphs_gap_hmis;
	private String details_infra;
	private String sanctioned_infra;
	private String available_functional_infra;
	private String gap_infra;
	private String details_fina;
	private String sanctioned_fina;
	private String available_functional_fina;
	private String gap_fina;
	private String details_supp;
	private String sanctioned_supp;
	private String available_functional_supp;
	private String gap_supp;
	private String details_tech;
	private String sanctioned_tech;
	private String available_functional_tech;
	private String gap_tech;
	private String details_hr;
	private String sanctioned_hr;
	private String available_functional_hr;
	private String gap_hr;
	private String district;
	private String cycle;
	private String year;
	private String userid;
	private String dist_demogra_dtl_id;
	private String theme_id;
	private String completed;
	private List<Area_Indicator_Object> areas_Id_Indicators_map_list;
	
	private List<Form1BSaveDocumentsArray> infra_array;
	private List<Form1BSaveDocumentsArray> fina_array;
	private List<Form1BSaveDocumentsArray> supp_array;
	private List<Form1BSaveDocumentsArray> tech_array;
	private List<Form1BSaveDocumentsArray> hr_array;
	
	private List<Form1BEditKeyNgoDocumentsArray> key_ngo_info_array;
	private List<Form1BEditKeyStakeholderDocumentsArray> key_ngo_source_array;
	
	private List<Form1BSaveIndicatorsModel> total_coverage_indi;
	
	public Form1BView() {
		
	}

	public Form1BView(String date_of_verification, String filled_by, String verified_by_name,
			String verified_by_other_actual_name, String total_area, String total_area_demogra_id,
			String total_area_others, String total_pop, String total_pop_demogra_id, String total_pop_others,
			String num_women_in_reproductive_age_15_49_y, String num_women_in_reproductive_age_15_49_y_source,
			String num_women_in_reproductive_others, String num_child_under_5_y, String num_child_under_5_y_demogra_id,
			String no_of_child_under5_others, String rural_pop, String rural_pop_demogra_id, String rural_pop_others,
			String sc_pop, String sc_pop_demogra_id, String sc_pop_others, String st_pop, String st_pop_demogra_id,
			String st_pop_others, String pop_density, String pop_density_demogra_id, String pop_dens_others,
			String total_literacy, String total_literacy_demogra_id, String tot_lit_others, String fem_literacy,
			String fem_literacy_demogra_id, String female_lit_others, String iphs_theme_name,
			String iphs_coverage_indicators, String iphs_source, String iphs_data, String iphs_expected_status,
			String iphs_gap_hmis, String details_infra, String sanctioned_infra, String available_functional_infra,
			String gap_infra, String details_fina, String sanctioned_fina, String available_functional_fina,
			String gap_fina, String details_supp, String sanctioned_supp, String available_functional_supp,
			String gap_supp, String details_tech, String sanctioned_tech, String available_functional_tech,
			String gap_tech, String details_hr, String sanctioned_hr, String available_functional_hr, String gap_hr,
			String district, String cycle, String year, String userid, String dist_demogra_dtl_id, String theme_id, String completed, 
			List<Area_Indicator_Object> areas_Id_Indicators_map_list, List<Form1BSaveDocumentsArray> infra_array,
			List<Form1BSaveDocumentsArray> fina_array, List<Form1BSaveDocumentsArray> supp_array,
			List<Form1BSaveDocumentsArray> tech_array, List<Form1BSaveDocumentsArray> hr_array,
			List<Form1BEditKeyNgoDocumentsArray> key_ngo_info_array,
			List<Form1BEditKeyStakeholderDocumentsArray> key_ngo_source_array,
			List<Form1BSaveIndicatorsModel> total_coverage_indi) {
		this.date_of_verification = date_of_verification;
		this.filled_by = filled_by;
		this.verified_by_name = verified_by_name;
		this.verified_by_other_actual_name = verified_by_other_actual_name;
		this.total_area = total_area;
		this.total_area_demogra_id = total_area_demogra_id;
		this.total_area_others = total_area_others;
		this.total_pop = total_pop;
		this.total_pop_demogra_id = total_pop_demogra_id;
		this.total_pop_others = total_pop_others;
		this.num_women_in_reproductive_age_15_49_y = num_women_in_reproductive_age_15_49_y;
		this.num_women_in_reproductive_age_15_49_y_source = num_women_in_reproductive_age_15_49_y_source;
		this.num_women_in_reproductive_others = num_women_in_reproductive_others;
		this.num_child_under_5_y = num_child_under_5_y;
		this.num_child_under_5_y_demogra_id = num_child_under_5_y_demogra_id;
		this.no_of_child_under5_others = no_of_child_under5_others;
		this.rural_pop = rural_pop;
		this.rural_pop_demogra_id = rural_pop_demogra_id;
		this.rural_pop_others = rural_pop_others;
		this.sc_pop = sc_pop;
		this.sc_pop_demogra_id = sc_pop_demogra_id;
		this.sc_pop_others = sc_pop_others;
		this.st_pop = st_pop;
		this.st_pop_demogra_id = st_pop_demogra_id;
		this.st_pop_others = st_pop_others;
		this.pop_density = pop_density;
		this.pop_density_demogra_id = pop_density_demogra_id;
		this.pop_dens_others = pop_dens_others;
		this.total_literacy = total_literacy;
		this.total_literacy_demogra_id = total_literacy_demogra_id;
		this.tot_lit_others = tot_lit_others;
		this.fem_literacy = fem_literacy;
		this.fem_literacy_demogra_id = fem_literacy_demogra_id;
		this.female_lit_others = female_lit_others;
		this.iphs_theme_name = iphs_theme_name;
		this.iphs_coverage_indicators = iphs_coverage_indicators;
		this.iphs_source = iphs_source;
		this.iphs_data = iphs_data;
		this.iphs_expected_status = iphs_expected_status;
		this.iphs_gap_hmis = iphs_gap_hmis;
		this.details_infra = details_infra;
		this.sanctioned_infra = sanctioned_infra;
		this.available_functional_infra = available_functional_infra;
		this.gap_infra = gap_infra;
		this.details_fina = details_fina;
		this.sanctioned_fina = sanctioned_fina;
		this.available_functional_fina = available_functional_fina;
		this.gap_fina = gap_fina;
		this.details_supp = details_supp;
		this.sanctioned_supp = sanctioned_supp;
		this.available_functional_supp = available_functional_supp;
		this.gap_supp = gap_supp;
		this.details_tech = details_tech;
		this.sanctioned_tech = sanctioned_tech;
		this.available_functional_tech = available_functional_tech;
		this.gap_tech = gap_tech;
		this.details_hr = details_hr;
		this.sanctioned_hr = sanctioned_hr;
		this.available_functional_hr = available_functional_hr;
		this.gap_hr = gap_hr;
		this.district = district;
		this.cycle = cycle;
		this.year = year;
		this.userid = userid;
		this.dist_demogra_dtl_id = dist_demogra_dtl_id;
		this.theme_id = theme_id;
		this.completed = completed;
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
		this.infra_array = infra_array;
		this.fina_array = fina_array;
		this.supp_array = supp_array;
		this.tech_array = tech_array;
		this.hr_array = hr_array;
		this.key_ngo_info_array = key_ngo_info_array;
		this.key_ngo_source_array = key_ngo_source_array;
		this.total_coverage_indi = total_coverage_indi;
	}

	public String getDate_of_verification() {
		return date_of_verification;
	}

	public void setDate_of_verification(String date_of_verification) {
		this.date_of_verification = date_of_verification;
	}

	public String getFilled_by() {
		return filled_by;
	}

	public void setFilled_by(String filled_by) {
		this.filled_by = filled_by;
	}

	public String getVerified_by_name() {
		return verified_by_name;
	}

	public void setVerified_by_name(String verified_by_name) {
		this.verified_by_name = verified_by_name;
	}

	public String getVerified_by_other_actual_name() {
		return verified_by_other_actual_name;
	}

	public void setVerified_by_other_actual_name(String verified_by_other_actual_name) {
		this.verified_by_other_actual_name = verified_by_other_actual_name;
	}

	public String getTotal_area() {
		return total_area;
	}

	public void setTotal_area(String total_area) {
		this.total_area = total_area;
	}

	public String getTotal_area_demogra_id() {
		return total_area_demogra_id;
	}

	public void setTotal_area_demogra_id(String total_area_demogra_id) {
		this.total_area_demogra_id = total_area_demogra_id;
	}

	public String getTotal_area_others() {
		return total_area_others;
	}

	public void setTotal_area_others(String total_area_others) {
		this.total_area_others = total_area_others;
	}

	public String getTotal_pop() {
		return total_pop;
	}

	public void setTotal_pop(String total_pop) {
		this.total_pop = total_pop;
	}

	public String getTotal_pop_demogra_id() {
		return total_pop_demogra_id;
	}

	public void setTotal_pop_demogra_id(String total_pop_demogra_id) {
		this.total_pop_demogra_id = total_pop_demogra_id;
	}

	public String getTotal_pop_others() {
		return total_pop_others;
	}

	public void setTotal_pop_others(String total_pop_others) {
		this.total_pop_others = total_pop_others;
	}

	public String getNum_women_in_reproductive_age_15_49_y() {
		return num_women_in_reproductive_age_15_49_y;
	}

	public void setNum_women_in_reproductive_age_15_49_y(String num_women_in_reproductive_age_15_49_y) {
		this.num_women_in_reproductive_age_15_49_y = num_women_in_reproductive_age_15_49_y;
	}

	public String getNum_women_in_reproductive_age_15_49_y_source() {
		return num_women_in_reproductive_age_15_49_y_source;
	}

	public void setNum_women_in_reproductive_age_15_49_y_source(String num_women_in_reproductive_age_15_49_y_source) {
		this.num_women_in_reproductive_age_15_49_y_source = num_women_in_reproductive_age_15_49_y_source;
	}

	public String getNum_women_in_reproductive_others() {
		return num_women_in_reproductive_others;
	}

	public void setNum_women_in_reproductive_others(String num_women_in_reproductive_others) {
		this.num_women_in_reproductive_others = num_women_in_reproductive_others;
	}

	public String getNum_child_under_5_y() {
		return num_child_under_5_y;
	}

	public void setNum_child_under_5_y(String num_child_under_5_y) {
		this.num_child_under_5_y = num_child_under_5_y;
	}

	public String getNum_child_under_5_y_demogra_id() {
		return num_child_under_5_y_demogra_id;
	}

	public void setNum_child_under_5_y_demogra_id(String num_child_under_5_y_demogra_id) {
		this.num_child_under_5_y_demogra_id = num_child_under_5_y_demogra_id;
	}

	public String getNo_of_child_under5_others() {
		return no_of_child_under5_others;
	}

	public void setNo_of_child_under5_others(String no_of_child_under5_others) {
		this.no_of_child_under5_others = no_of_child_under5_others;
	}

	public String getRural_pop() {
		return rural_pop;
	}

	public void setRural_pop(String rural_pop) {
		this.rural_pop = rural_pop;
	}

	public String getRural_pop_demogra_id() {
		return rural_pop_demogra_id;
	}

	public void setRural_pop_demogra_id(String rural_pop_demogra_id) {
		this.rural_pop_demogra_id = rural_pop_demogra_id;
	}

	public String getRural_pop_others() {
		return rural_pop_others;
	}

	public void setRural_pop_others(String rural_pop_others) {
		this.rural_pop_others = rural_pop_others;
	}

	public String getSc_pop() {
		return sc_pop;
	}

	public void setSc_pop(String sc_pop) {
		this.sc_pop = sc_pop;
	}

	public String getSc_pop_demogra_id() {
		return sc_pop_demogra_id;
	}

	public void setSc_pop_demogra_id(String sc_pop_demogra_id) {
		this.sc_pop_demogra_id = sc_pop_demogra_id;
	}

	public String getSc_pop_others() {
		return sc_pop_others;
	}

	public void setSc_pop_others(String sc_pop_others) {
		this.sc_pop_others = sc_pop_others;
	}

	public String getSt_pop() {
		return st_pop;
	}

	public void setSt_pop(String st_pop) {
		this.st_pop = st_pop;
	}

	public String getSt_pop_demogra_id() {
		return st_pop_demogra_id;
	}

	public void setSt_pop_demogra_id(String st_pop_demogra_id) {
		this.st_pop_demogra_id = st_pop_demogra_id;
	}

	public String getSt_pop_others() {
		return st_pop_others;
	}

	public void setSt_pop_others(String st_pop_others) {
		this.st_pop_others = st_pop_others;
	}

	public String getPop_density() {
		return pop_density;
	}

	public void setPop_density(String pop_density) {
		this.pop_density = pop_density;
	}

	public String getPop_density_demogra_id() {
		return pop_density_demogra_id;
	}

	public void setPop_density_demogra_id(String pop_density_demogra_id) {
		this.pop_density_demogra_id = pop_density_demogra_id;
	}

	public String getPop_dens_others() {
		return pop_dens_others;
	}

	public void setPop_dens_others(String pop_dens_others) {
		this.pop_dens_others = pop_dens_others;
	}

	public String getTotal_literacy() {
		return total_literacy;
	}

	public void setTotal_literacy(String total_literacy) {
		this.total_literacy = total_literacy;
	}

	public String getTotal_literacy_demogra_id() {
		return total_literacy_demogra_id;
	}

	public void setTotal_literacy_demogra_id(String total_literacy_demogra_id) {
		this.total_literacy_demogra_id = total_literacy_demogra_id;
	}

	public String getTot_lit_others() {
		return tot_lit_others;
	}

	public void setTot_lit_others(String tot_lit_others) {
		this.tot_lit_others = tot_lit_others;
	}

	public String getFem_literacy() {
		return fem_literacy;
	}

	public void setFem_literacy(String fem_literacy) {
		this.fem_literacy = fem_literacy;
	}

	public String getFem_literacy_demogra_id() {
		return fem_literacy_demogra_id;
	}

	public void setFem_literacy_demogra_id(String fem_literacy_demogra_id) {
		this.fem_literacy_demogra_id = fem_literacy_demogra_id;
	}

	public String getFemale_lit_others() {
		return female_lit_others;
	}

	public void setFemale_lit_others(String female_lit_others) {
		this.female_lit_others = female_lit_others;
	}

	public String getIphs_theme_name() {
		return iphs_theme_name;
	}

	public void setIphs_theme_name(String iphs_theme_name) {
		this.iphs_theme_name = iphs_theme_name;
	}

	public String getIphs_coverage_indicators() {
		return iphs_coverage_indicators;
	}

	public void setIphs_coverage_indicators(String iphs_coverage_indicators) {
		this.iphs_coverage_indicators = iphs_coverage_indicators;
	}

	public String getIphs_source() {
		return iphs_source;
	}

	public void setIphs_source(String iphs_source) {
		this.iphs_source = iphs_source;
	}

	public String getIphs_data() {
		return iphs_data;
	}

	public void setIphs_data(String iphs_data) {
		this.iphs_data = iphs_data;
	}

	public String getIphs_expected_status() {
		return iphs_expected_status;
	}

	public void setIphs_expected_status(String iphs_expected_status) {
		this.iphs_expected_status = iphs_expected_status;
	}

	public String getIphs_gap_hmis() {
		return iphs_gap_hmis;
	}

	public void setIphs_gap_hmis(String iphs_gap_hmis) {
		this.iphs_gap_hmis = iphs_gap_hmis;
	}

	public String getDetails_infra() {
		return details_infra;
	}

	public void setDetails_infra(String details_infra) {
		this.details_infra = details_infra;
	}

	public String getSanctioned_infra() {
		return sanctioned_infra;
	}

	public void setSanctioned_infra(String sanctioned_infra) {
		this.sanctioned_infra = sanctioned_infra;
	}

	public String getAvailable_functional_infra() {
		return available_functional_infra;
	}

	public void setAvailable_functional_infra(String available_functional_infra) {
		this.available_functional_infra = available_functional_infra;
	}

	public String getGap_infra() {
		return gap_infra;
	}

	public void setGap_infra(String gap_infra) {
		this.gap_infra = gap_infra;
	}

	public String getDetails_fina() {
		return details_fina;
	}

	public void setDetails_fina(String details_fina) {
		this.details_fina = details_fina;
	}

	public String getSanctioned_fina() {
		return sanctioned_fina;
	}

	public void setSanctioned_fina(String sanctioned_fina) {
		this.sanctioned_fina = sanctioned_fina;
	}

	public String getAvailable_functional_fina() {
		return available_functional_fina;
	}

	public void setAvailable_functional_fina(String available_functional_fina) {
		this.available_functional_fina = available_functional_fina;
	}

	public String getGap_fina() {
		return gap_fina;
	}

	public void setGap_fina(String gap_fina) {
		this.gap_fina = gap_fina;
	}

	public String getDetails_supp() {
		return details_supp;
	}

	public void setDetails_supp(String details_supp) {
		this.details_supp = details_supp;
	}

	public String getSanctioned_supp() {
		return sanctioned_supp;
	}

	public void setSanctioned_supp(String sanctioned_supp) {
		this.sanctioned_supp = sanctioned_supp;
	}

	public String getAvailable_functional_supp() {
		return available_functional_supp;
	}

	public void setAvailable_functional_supp(String available_functional_supp) {
		this.available_functional_supp = available_functional_supp;
	}

	public String getGap_supp() {
		return gap_supp;
	}

	public void setGap_supp(String gap_supp) {
		this.gap_supp = gap_supp;
	}

	public String getDetails_tech() {
		return details_tech;
	}

	public void setDetails_tech(String details_tech) {
		this.details_tech = details_tech;
	}

	public String getSanctioned_tech() {
		return sanctioned_tech;
	}

	public void setSanctioned_tech(String sanctioned_tech) {
		this.sanctioned_tech = sanctioned_tech;
	}

	public String getAvailable_functional_tech() {
		return available_functional_tech;
	}

	public void setAvailable_functional_tech(String available_functional_tech) {
		this.available_functional_tech = available_functional_tech;
	}

	public String getGap_tech() {
		return gap_tech;
	}

	public void setGap_tech(String gap_tech) {
		this.gap_tech = gap_tech;
	}

	public String getDetails_hr() {
		return details_hr;
	}

	public void setDetails_hr(String details_hr) {
		this.details_hr = details_hr;
	}

	public String getSanctioned_hr() {
		return sanctioned_hr;
	}

	public void setSanctioned_hr(String sanctioned_hr) {
		this.sanctioned_hr = sanctioned_hr;
	}

	public String getAvailable_functional_hr() {
		return available_functional_hr;
	}

	public void setAvailable_functional_hr(String available_functional_hr) {
		this.available_functional_hr = available_functional_hr;
	}

	public String getGap_hr() {
		return gap_hr;
	}

	public void setGap_hr(String gap_hr) {
		this.gap_hr = gap_hr;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getCycle() {
		return cycle;
	}

	public void setCycle(String cycle) {
		this.cycle = cycle;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDist_demogra_dtl_id() {
		return dist_demogra_dtl_id;
	}

	public void setDist_demogra_dtl_id(String dist_demogra_dtl_id) {
		this.dist_demogra_dtl_id = dist_demogra_dtl_id;
	}

	public String getTheme_id() {
		return theme_id;
	}

	public void setTheme_id(String theme_id) {
		this.theme_id = theme_id;
	}	

	public String getCompleted() {
		return completed;
	}

	public void setCompleted(String completed) {
		this.completed = completed;
	}

	public List<Area_Indicator_Object> getAreas_Id_Indicators_map_list() {
		return areas_Id_Indicators_map_list;
	}

	public void setAreas_Id_Indicators_map_list(List<Area_Indicator_Object> areas_Id_Indicators_map_list) {
		this.areas_Id_Indicators_map_list = areas_Id_Indicators_map_list;
	}

	public List<Form1BSaveDocumentsArray> getInfra_array() {
		return infra_array;
	}

	public void setInfra_array(List<Form1BSaveDocumentsArray> infra_array) {
		this.infra_array = infra_array;
	}

	public List<Form1BSaveDocumentsArray> getFina_array() {
		return fina_array;
	}

	public void setFina_array(List<Form1BSaveDocumentsArray> fina_array) {
		this.fina_array = fina_array;
	}

	public List<Form1BSaveDocumentsArray> getSupp_array() {
		return supp_array;
	}

	public void setSupp_array(List<Form1BSaveDocumentsArray> supp_array) {
		this.supp_array = supp_array;
	}

	public List<Form1BSaveDocumentsArray> getTech_array() {
		return tech_array;
	}

	public void setTech_array(List<Form1BSaveDocumentsArray> tech_array) {
		this.tech_array = tech_array;
	}

	public List<Form1BSaveDocumentsArray> getHr_array() {
		return hr_array;
	}

	public void setHr_array(List<Form1BSaveDocumentsArray> hr_array) {
		this.hr_array = hr_array;
	}

	public List<Form1BEditKeyNgoDocumentsArray> getKey_ngo_info_array() {
		return key_ngo_info_array;
	}

	public void setKey_ngo_info_array(List<Form1BEditKeyNgoDocumentsArray> key_ngo_info_array) {
		this.key_ngo_info_array = key_ngo_info_array;
	}

	public List<Form1BEditKeyStakeholderDocumentsArray> getKey_ngo_source_array() {
		return key_ngo_source_array;
	}

	public void setKey_ngo_source_array(List<Form1BEditKeyStakeholderDocumentsArray> key_ngo_source_array) {
		this.key_ngo_source_array = key_ngo_source_array;
	}

	public List<Form1BSaveIndicatorsModel> getTotal_coverage_indi() {
		return total_coverage_indi;
	}

	public void setTotal_coverage_indi(List<Form1BSaveIndicatorsModel> total_coverage_indi) {
		this.total_coverage_indi = total_coverage_indi;
	}

	@Override
	public String toString() {
		return "Form1BView [date_of_verification=" + date_of_verification + ", filled_by=" + filled_by
				+ ", verified_by_name=" + verified_by_name + ", verified_by_other_actual_name="
				+ verified_by_other_actual_name + ", total_area=" + total_area + ", total_area_demogra_id="
				+ total_area_demogra_id + ", total_area_others=" + total_area_others + ", total_pop=" + total_pop
				+ ", total_pop_demogra_id=" + total_pop_demogra_id + ", total_pop_others=" + total_pop_others
				+ ", num_women_in_reproductive_age_15_49_y=" + num_women_in_reproductive_age_15_49_y
				+ ", num_women_in_reproductive_age_15_49_y_source=" + num_women_in_reproductive_age_15_49_y_source
				+ ", num_women_in_reproductive_others=" + num_women_in_reproductive_others + ", num_child_under_5_y="
				+ num_child_under_5_y + ", num_child_under_5_y_demogra_id=" + num_child_under_5_y_demogra_id
				+ ", no_of_child_under5_others=" + no_of_child_under5_others + ", rural_pop=" + rural_pop
				+ ", rural_pop_demogra_id=" + rural_pop_demogra_id + ", rural_pop_others=" + rural_pop_others
				+ ", sc_pop=" + sc_pop + ", sc_pop_demogra_id=" + sc_pop_demogra_id + ", sc_pop_others=" + sc_pop_others
				+ ", st_pop=" + st_pop + ", st_pop_demogra_id=" + st_pop_demogra_id + ", st_pop_others=" + st_pop_others
				+ ", pop_density=" + pop_density + ", pop_density_demogra_id=" + pop_density_demogra_id
				+ ", pop_dens_others=" + pop_dens_others + ", total_literacy=" + total_literacy
				+ ", total_literacy_demogra_id=" + total_literacy_demogra_id + ", tot_lit_others=" + tot_lit_others
				+ ", fem_literacy=" + fem_literacy + ", fem_literacy_demogra_id=" + fem_literacy_demogra_id
				+ ", female_lit_others=" + female_lit_others + ", iphs_theme_name=" + iphs_theme_name
				+ ", iphs_coverage_indicators=" + iphs_coverage_indicators + ", iphs_source=" + iphs_source
				+ ", iphs_data=" + iphs_data + ", iphs_expected_status=" + iphs_expected_status + ", iphs_gap_hmis="
				+ iphs_gap_hmis + ", details_infra=" + details_infra + ", sanctioned_infra=" + sanctioned_infra
				+ ", available_functional_infra=" + available_functional_infra + ", gap_infra=" + gap_infra
				+ ", details_fina=" + details_fina + ", sanctioned_fina=" + sanctioned_fina
				+ ", available_functional_fina=" + available_functional_fina + ", gap_fina=" + gap_fina
				+ ", details_supp=" + details_supp + ", sanctioned_supp=" + sanctioned_supp
				+ ", available_functional_supp=" + available_functional_supp + ", gap_supp=" + gap_supp
				+ ", details_tech=" + details_tech + ", sanctioned_tech=" + sanctioned_tech
				+ ", available_functional_tech=" + available_functional_tech + ", gap_tech=" + gap_tech
				+ ", details_hr=" + details_hr + ", sanctioned_hr=" + sanctioned_hr + ", available_functional_hr="
				+ available_functional_hr + ", gap_hr=" + gap_hr + ", district=" + district + ", cycle=" + cycle
				+ ", year=" + year + ", userid=" + userid + ", dist_demogra_dtl_id=" + dist_demogra_dtl_id
				+ ", theme_id=" + theme_id + ", completed=" + completed + ", areas_Id_Indicators_map_list="
				+ areas_Id_Indicators_map_list + ", infra_array=" + infra_array + ", fina_array=" + fina_array
				+ ", supp_array=" + supp_array + ", tech_array=" + tech_array + ", hr_array=" + hr_array
				+ ", key_ngo_info_array=" + key_ngo_info_array + ", key_ngo_source_array=" + key_ngo_source_array
				+ ", total_coverage_indi=" + total_coverage_indi + "]";
	}

	
	
}
