package com.tattvafoundation.diphonline.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tattvafoundation.diphonline.Constants;
import com.tattvafoundation.diphonline.model.Form1ASave;
import com.tattvafoundation.diphonline.model.Form1ASaveDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1AView;
import com.tattvafoundation.diphonline.model.Form1BEditUpdate;
import com.tattvafoundation.diphonline.model.Form1BSave;
import com.tattvafoundation.diphonline.model.Form1BSaveDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BSaveIndicatorsModel;
import com.tattvafoundation.diphonline.model.Form1BSaveKeyNgoDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BSaveKeyStakeholderDocumentsArray;
import com.tattvafoundation.diphonline.model.Form1BView;
import com.tattvafoundation.diphonline.model.Form2Engage;
import com.tattvafoundation.diphonline.model.Form2EngageEdit;
import com.tattvafoundation.diphonline.model.Form2EngagePartACommonStakeHoldersArray;
import com.tattvafoundation.diphonline.model.Form2EngagePartBCommonArray;
import com.tattvafoundation.diphonline.model.Form3Define;
import com.tattvafoundation.diphonline.model.Form3DefineCommonArray;
import com.tattvafoundation.diphonline.model.Form3DefineEdit;
import com.tattvafoundation.diphonline.model.Form4Plan;
import com.tattvafoundation.diphonline.model.Form4PlanCommonObject;
import com.tattvafoundation.diphonline.model.Form5FollowUpActionCommonArray;
import com.tattvafoundation.diphonline.model.Form5Followup;
import com.tattvafoundation.diphonline.model.Supplementaryform1A;
import com.tattvafoundation.diphonline.repository.Form1ADAO;
import com.tattvafoundation.diphonline.repository.Form1BDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceUtil.class);

	public static Form1ASave Form1AViewToSaveConverter(Form1AView form1AView, String district_id, String cycle_id,
			String financial_year, String user_id, String source) {

		int i;
		Form1ASave form1ASave = new Form1ASave();

		form1ASave.setDate_of_verification(form1AView.getDate_of_verification());
		form1ASave.setFilled_by(form1AView.getFilled_by());
		form1ASave.setVerified_by_name(form1AView.getVerified_by_name());
		form1ASave.setVerified_by_other_actual_name(form1AView.getVerified_by_name_actual_name());
		form1ASave.setDistrict(district_id);
		form1ASave.setCycle(cycle_id);
		form1ASave.setYear(financial_year);
		form1ASave.setUserid(user_id);
		form1ASave.setCompleted(form1AView.getCompleted());
		form1ASave.setSource(source);

		form1ASave.setState_policy_first_doc_val(form1AView.getState_policy_array().get(0).getDocument_val());
		form1ASave.setState_policy_first_availability(
				form1AView.getState_policy_array().get(0).getDocument_availability());
		form1ASave.setState_policy_first_d_source(form1AView.getState_policy_array().get(0).getDocument_source());
		form1ASave.setNumberOfTickets(String.valueOf(form1AView.getState_policy_array().size()));
		List<Form1ASaveDocumentsArray> state_policy_array = new ArrayList<Form1ASaveDocumentsArray>();
		for (i = 1; i < form1AView.getState_policy_array().size(); i++) {
			state_policy_array.add(form1AView.getState_policy_array().get(i));
		}
		form1ASave.setState_policy_array(state_policy_array);

		form1ASave.setDistrict_policy_first_doc_val(form1AView.getDistrict_policy_array().get(0).getDocument_val());
		form1ASave.setDistrict_policy_first_availability(
				form1AView.getDistrict_policy_array().get(0).getDocument_availability());
		form1ASave.setDistrict_policy_first_d_source(form1AView.getDistrict_policy_array().get(0).getDocument_source());
		form1ASave.setNumberOfdistrictrow(String.valueOf(form1AView.getDistrict_policy_array().size()));
		List<Form1ASaveDocumentsArray> district_policy_array = new ArrayList<Form1ASaveDocumentsArray>();
		for (i = 1; i < form1AView.getDistrict_policy_array().size(); i++) {
			district_policy_array.add(form1AView.getDistrict_policy_array().get(i));
		}
		form1ASave.setDistrict_policy_array(district_policy_array);

		form1ASave.setHealth_dept_first_doc_val(form1AView.getHealth_dept_array().get(0).getDocument_val());
		form1ASave
				.setHealth_dept_first_availability(form1AView.getHealth_dept_array().get(0).getDocument_availability());
		form1ASave.setHealth_dept_first_d_source(form1AView.getHealth_dept_array().get(0).getDocument_source());
		form1ASave.setNumberOfhealthdeptrow(String.valueOf(form1AView.getHealth_dept_array().size()));
		List<Form1ASaveDocumentsArray> health_dept_array = new ArrayList<Form1ASaveDocumentsArray>();
		for (i = 1; i < form1AView.getHealth_dept_array().size(); i++) {
			health_dept_array.add(form1AView.getHealth_dept_array().get(i));
		}
		form1ASave.setHealth_dept_array(health_dept_array);

		form1ASave.setNon_health_dept_first_doc_val(form1AView.getNon_health_dept_array().get(0).getDocument_val());
		form1ASave.setNon_health_dept_first_availability(
				form1AView.getNon_health_dept_array().get(0).getDocument_availability());
		form1ASave.setNon_health_dept_first_d_source(form1AView.getNon_health_dept_array().get(0).getDocument_source());
		form1ASave.setNumberOfnonhealthdeptrow(String.valueOf(form1AView.getNon_health_dept_array().size()));
		List<Form1ASaveDocumentsArray> non_health_dept_array = new ArrayList<Form1ASaveDocumentsArray>();
		for (i = 1; i < form1AView.getNon_health_dept_array().size(); i++) {
			non_health_dept_array.add(form1AView.getNon_health_dept_array().get(i));
		}
		form1ASave.setNon_health_dept_array(non_health_dept_array);

		form1ASave.setPrivate_sec_first_doc_val(form1AView.getPrivate_sec_array().get(0).getDocument_val());
		form1ASave
				.setPrivate_sec_first_availability(form1AView.getPrivate_sec_array().get(0).getDocument_availability());
		form1ASave.setPrivate_sec_first_d_source(form1AView.getPrivate_sec_array().get(0).getDocument_source());
		form1ASave.setNumberOfprivsecrow(String.valueOf(form1AView.getPrivate_sec_array().size()));
		List<Form1ASaveDocumentsArray> private_sec_array = new ArrayList<Form1ASaveDocumentsArray>();
		for (i = 1; i < form1AView.getPrivate_sec_array().size(); i++) {
			private_sec_array.add(form1AView.getPrivate_sec_array().get(i));
		}
		form1ASave.setPrivate_sec_array(private_sec_array);

		form1ASave.setLarge_scale_district_first_doc_val(
				form1AView.getLarge_scale_district_array().get(0).getDocument_val());
		form1ASave.setLarge_scale_district_first_availability(
				form1AView.getLarge_scale_district_array().get(0).getDocument_availability());
		form1ASave.setLarge_scale_district_first_d_source(
				form1AView.getLarge_scale_district_array().get(0).getDocument_source());
		form1ASave.setNumberOflargescaledistrictrow(String.valueOf(form1AView.getLarge_scale_district_array().size()));
		List<Form1ASaveDocumentsArray> large_scale_district_array = new ArrayList<Form1ASaveDocumentsArray>();
		for (i = 1; i < form1AView.getLarge_scale_district_array().size(); i++) {
			large_scale_district_array.add(form1AView.getLarge_scale_district_array().get(i));
		}
		form1ASave.setLarge_scale_district_array(large_scale_district_array);

		return form1ASave;

	}

	public static Form1BSave form1BViewToForm1BSaveConverter(Form1AView form1AView, Form1BView form1BView,
			String district_id, String cycle_id, String financial_year, String user_id, String source) {

		Form1BSave form1BSave = new Form1BSave();

		form1BView = form1BViewDemographicalIdsGenerator(form1AView, form1BView);

		form1BSave.setDate_of_verification(form1BView.getDate_of_verification());
		form1BSave.setFilled_by(form1BView.getFilled_by());
		form1BSave.setVerified_by_name(form1BView.getVerified_by_name());
		form1BSave.setVerified_by_other_actual_name(form1BView.getVerified_by_other_actual_name());
		
		form1BSave.setTotal_area(form1BView.getTotal_area());
		form1BSave.setTotal_area_demogra_id(form1BView.getTotal_area_demogra_id());
		form1BSave.setTotal_area_others(form1BView.getTotal_area_others());
		
		form1BSave.setTotal_pop(form1BView.getTotal_pop());
		form1BSave.setTotal_pop_demogra_id(form1BView.getTotal_pop_demogra_id());
		form1BSave.setTotal_pop_others(form1BView.getTotal_pop_others());
		
		form1BSave.setNum_women_in_reproductive_age_15_49_y(form1BView.getNum_women_in_reproductive_age_15_49_y());
		form1BSave.setNum_women_in_reproductive_age_15_49_y_source(
				form1BView.getNum_women_in_reproductive_age_15_49_y_source());
		form1BSave.setNum_women_in_reproductive_others(form1BView.getNum_women_in_reproductive_others());
		
		form1BSave.setNum_child_under_5_y(form1BView.getNum_child_under_5_y());
		form1BSave.setNum_child_under_5_y_demogra_id(form1BView.getNum_child_under_5_y_demogra_id());
		form1BSave.setNo_of_child_under5_others(form1BView.getNo_of_child_under5_others());
		
		form1BSave.setRural_pop(form1BView.getRural_pop());
		form1BSave.setRural_pop_demogra_id(form1BView.getRural_pop_demogra_id());
		form1BSave.setRural_pop_others(form1BView.getRural_pop_others());
		
		form1BSave.setSc_pop(form1BView.getSc_pop());
		form1BSave.setSc_pop_demogra_id(form1BView.getSc_pop_demogra_id());
		form1BSave.setSc_pop_others(form1BView.getSc_pop_others());
		
		form1BSave.setSt_pop(form1BView.getSt_pop());
		form1BSave.setSt_pop_demogra_id(form1BView.getSt_pop_demogra_id());
		form1BSave.setSt_pop_others(form1BView.getSt_pop_others());
		
		form1BSave.setPop_density(form1BView.getPop_density());
		form1BSave.setPop_density_demogra_id(form1BView.getPop_density_demogra_id());
		form1BSave.setPop_dens_others(form1BView.getPop_dens_others());
		
		form1BSave.setTotal_literacy(form1BView.getTotal_literacy());
		form1BSave.setTotal_literacy_demogra_id(form1BView.getTotal_literacy_demogra_id());
		form1BSave.setTot_lit_others(form1BView.getTot_lit_others());
		
		form1BSave.setFem_literacy(form1BView.getFem_literacy());
		form1BSave.setFem_literacy_demogra_id(form1BView.getFem_literacy_demogra_id());
		form1BSave.setFemale_lit_others(form1BView.getFemale_lit_others());
		
		form1BSave.setIphs_theme_name(form1BView.getIphs_theme_name());
		form1BSave.setIphs_coverage_indicators(form1BView.getIphs_coverage_indicators());
		form1BSave.setIphs_source(form1BView.getIphs_source());
		form1BSave.setIphs_data(form1BView.getIphs_data());
		form1BSave.setIphs_expected_status(form1BView.getIphs_expected_status());
		form1BSave.setIphs_gap_hmis(form1BView.getIphs_gap_hmis());
		/*
		 * form1BSave.setDetails_infra(form1BView.getDetails_infra());
		 * form1BSave.setSanctioned_infra(form1BView.getSanctioned_infra());
		 * form1BSave.setAvailable_functional_infra(form1BView.
		 * getAvailable_functional_infra());
		 * form1BSave.setGap_infra(form1BView.getGap_infra());
		 * form1BSave.setDetails_fina(form1BView.getDetails_fina());
		 * form1BSave.setSanctioned_fina(form1BView.getSanctioned_fina());
		 * form1BSave.setAvailable_functional_fina(form1BView.
		 * getAvailable_functional_fina());
		 * form1BSave.setGap_fina(form1BView.getGap_fina());
		 * form1BSave.setDetails_supp(form1BView.getDetails_supp());
		 * form1BSave.setSanctioned_supp(form1BView.getSanctioned_supp());
		 * form1BSave.setAvailable_functional_supp(form1BView.
		 * getAvailable_functional_supp());
		 * form1BSave.setGap_supp(form1BView.getGap_supp());
		 * form1BSave.setDetails_tech(form1BView.getDetails_tech());
		 * form1BSave.setSanctioned_tech(form1BView.getSanctioned_tech());
		 * form1BSave.setAvailable_functional_tech(form1BView.
		 * getAvailable_functional_tech());
		 * form1BSave.setGap_tech(form1BView.getGap_tech());
		 * form1BSave.setDetails_hr(form1BView.getDetails_hr());
		 * form1BSave.setSanctioned_hr(form1BView.getSanctioned_hr());
		 * form1BSave.setAvailable_functional_hr(form1BView.getAvailable_functional_hr()
		 * ); form1BSave.setGap_hr(form1BView.getGap_hr());
		 */
		if (form1BView.getInfra_array().size() > 0) {
			form1BSave.setDetails_infra(form1BView.getInfra_array().get(0).getDocument_details());
			form1BSave.setSanctioned_infra(form1BView.getInfra_array().get(0).getDocument_sanctioned());
			form1BSave.setAvailable_functional_infra(
					form1BView.getInfra_array().get(0).getDocument_available_functional());
			form1BSave.setGap_infra(form1BView.getInfra_array().get(0).getDocument_gap());
		} else {
			form1BSave.setDetails_infra("");
			form1BSave.setSanctioned_infra("");
			form1BSave.setAvailable_functional_infra("");
			form1BSave.setGap_infra("");
		}

		if (form1BView.getFina_array().size() > 0) {
			form1BSave.setDetails_fina(form1BView.getFina_array().get(0).getDocument_details());
			form1BSave.setSanctioned_fina(form1BView.getFina_array().get(0).getDocument_sanctioned());
			form1BSave
					.setAvailable_functional_fina(form1BView.getFina_array().get(0).getDocument_available_functional());
			form1BSave.setGap_fina(form1BView.getFina_array().get(0).getDocument_gap());
		} else {
			form1BSave.setDetails_fina("");
			form1BSave.setSanctioned_fina("");
			form1BSave.setAvailable_functional_fina("");
			form1BSave.setGap_fina("");
		}

		if (form1BView.getSupp_array().size() > 0) {
			form1BSave.setDetails_supp(form1BView.getSupp_array().get(0).getDocument_details());
			form1BSave.setSanctioned_supp(form1BView.getSupp_array().get(0).getDocument_sanctioned());
			form1BSave
					.setAvailable_functional_supp(form1BView.getSupp_array().get(0).getDocument_available_functional());
			form1BSave.setGap_supp(form1BView.getSupp_array().get(0).getDocument_gap());
		} else {
			form1BSave.setDetails_supp("");
			form1BSave.setSanctioned_supp("");
			form1BSave.setAvailable_functional_supp("");
			form1BSave.setGap_supp("");
		}

		if (form1BView.getTech_array().size() > 0) {
			form1BSave.setDetails_tech(form1BView.getTech_array().get(0).getDocument_details());
			form1BSave.setSanctioned_tech(form1BView.getTech_array().get(0).getDocument_sanctioned());
			form1BSave
					.setAvailable_functional_tech(form1BView.getTech_array().get(0).getDocument_available_functional());
			form1BSave.setGap_tech(form1BView.getTech_array().get(0).getDocument_gap());
		} else {
			form1BSave.setDetails_tech("");
			form1BSave.setSanctioned_tech("");
			form1BSave.setAvailable_functional_tech("");
			form1BSave.setGap_tech("");
		}

		if (form1BView.getHr_array().size() > 0) {
			form1BSave.setDetails_hr(form1BView.getHr_array().get(0).getDocument_details());
			form1BSave.setSanctioned_hr(form1BView.getHr_array().get(0).getDocument_sanctioned());
			form1BSave.setAvailable_functional_hr(form1BView.getHr_array().get(0).getDocument_available_functional());
			form1BSave.setGap_hr(form1BView.getHr_array().get(0).getDocument_gap());
		} else {
			form1BSave.setDetails_hr("");
			form1BSave.setSanctioned_hr("");
			form1BSave.setAvailable_functional_hr("");
			form1BSave.setGap_hr("");
		}

		form1BSave.setDistrict(district_id);
		form1BSave.setCycle(cycle_id);
		form1BSave.setYear(financial_year);
		form1BSave.setUserid(user_id);
		form1BSave.setCompleted(form1BView.getCompleted());
		form1BSave.setSource(source);

		form1BSave.setDist_demogra_dtl_id(form1BView.getDist_demogra_dtl_id());
		form1BSave.setTheme_id(form1BView.getTheme_id());
		form1BSave.setAreas_Id_Indicators_map_list(form1BView.getAreas_Id_Indicators_map_list());

		if (form1BView.getInfra_array() != null && form1BView.getInfra_array().size() > 0)
			form1BView.getInfra_array().remove(0);
		if (form1BView.getFina_array() != null && form1BView.getFina_array().size() > 0)
			form1BView.getFina_array().remove(0);
		if (form1BView.getSupp_array() != null && form1BView.getSupp_array().size() > 0)
			form1BView.getSupp_array().remove(0);
		if (form1BView.getTech_array() != null && form1BView.getTech_array().size() > 0)
			form1BView.getTech_array().remove(0);
		if (form1BView.getHr_array() != null && form1BView.getHr_array().size() > 0)
			form1BView.getHr_array().remove(0);

		form1BSave.setInfra_array(form1BView.getInfra_array());
		form1BSave.setFina_array(form1BView.getFina_array());
		form1BSave.setSupp_array(form1BView.getSupp_array());
		form1BSave.setTech_array(form1BView.getTech_array());
		form1BSave.setHr_array(form1BView.getHr_array());

		int i;
		List<Form1BSaveKeyNgoDocumentsArray> key_ngo_info_array = new ArrayList<Form1BSaveKeyNgoDocumentsArray>();

		for (i = 0; i < form1BView.getKey_ngo_info_array().size(); i++) {
			String ngo_name = form1BView.getKey_ngo_info_array().get(i).getNgo_name();
			String ngo_details = form1BView.getKey_ngo_info_array().get(i).getNgo_details();
			Form1BSaveKeyNgoDocumentsArray obj = new Form1BSaveKeyNgoDocumentsArray();
			obj.setNgo_name(ngo_name);
			obj.setNgo_details(ngo_details);
			key_ngo_info_array.add(obj);
		}
		form1BSave.setKey_ngo_info_array(key_ngo_info_array);

		List<Form1BSaveKeyStakeholderDocumentsArray> key_ngo_source_array = new ArrayList<Form1BSaveKeyStakeholderDocumentsArray>();

		for (i = 0; i < form1BView.getKey_ngo_source_array().size(); i++) {
			String stakeholder_name = form1BView.getKey_ngo_source_array().get(i).getStakeholder_name();
			String contact_details = form1BView.getKey_ngo_source_array().get(i).getContact_details();
			Form1BSaveKeyStakeholderDocumentsArray obj = new Form1BSaveKeyStakeholderDocumentsArray();
			obj.setStakeholder_name(stakeholder_name);
			obj.setContact_details(contact_details);
			key_ngo_source_array.add(obj);
		}
		form1BSave.setKey_ngo_source_array(key_ngo_source_array);

		form1BSave.setTotal_coverage_indi(form1BView.getTotal_coverage_indi());

		return form1BSave;
	}

	private static Form1BView form1BViewDemographicalIdsGenerator(Form1AView form1AView, Form1BView form1BView) {

		ArrayList<Integer> demograpicalIdsList = new ArrayList<Integer>();

		int i;
		for (i = 0; i < form1AView.getState_policy_array().size(); i++) {

			demograpicalIdsList.add(Integer.parseInt(form1AView.getState_policy_array().get(i).getDoc_db_doc_id()));
		}
		for (i = 0; i < form1AView.getDistrict_policy_array().size(); i++) {

			demograpicalIdsList.add(Integer.parseInt(form1AView.getDistrict_policy_array().get(i).getDoc_db_doc_id()));
		}
		for (i = 0; i < form1AView.getHealth_dept_array().size(); i++) {

			demograpicalIdsList.add(Integer.parseInt(form1AView.getHealth_dept_array().get(i).getDoc_db_doc_id()));
		}
		for (i = 0; i < form1AView.getNon_health_dept_array().size(); i++) {

			demograpicalIdsList.add(Integer.parseInt(form1AView.getNon_health_dept_array().get(i).getDoc_db_doc_id()));
		}
		for (i = 0; i < form1AView.getPrivate_sec_array().size(); i++) {

			demograpicalIdsList.add(Integer.parseInt(form1AView.getPrivate_sec_array().get(i).getDoc_db_doc_id()));
		}
		for (i = 0; i < form1AView.getLarge_scale_district_array().size(); i++) {

			demograpicalIdsList
					.add(Integer.parseInt(form1AView.getLarge_scale_district_array().get(i).getDoc_db_doc_id()));
		}

		Collections.sort(demograpicalIdsList);

		form1BView.setTotal_area_demogra_id(
				String.valueOf(demograpicalIdsList.indexOf(Integer.parseInt(form1BView.getTotal_area_demogra_id()))));
		form1BView.setTotal_pop_demogra_id(
				String.valueOf(demograpicalIdsList.indexOf(Integer.parseInt(form1BView.getTotal_pop_demogra_id()))));
		form1BView.setNum_women_in_reproductive_age_15_49_y_source(String.valueOf(demograpicalIdsList
				.indexOf(Integer.parseInt(form1BView.getNum_women_in_reproductive_age_15_49_y_source()))));
		form1BView.setNum_child_under_5_y_demogra_id(String.valueOf(
				demograpicalIdsList.indexOf(Integer.parseInt(form1BView.getNum_child_under_5_y_demogra_id()))));
		form1BView.setRural_pop_demogra_id(
				String.valueOf(demograpicalIdsList.indexOf(Integer.parseInt(form1BView.getRural_pop_demogra_id()))));
		form1BView.setPop_density_demogra_id(
				String.valueOf(demograpicalIdsList.indexOf(Integer.parseInt(form1BView.getPop_density_demogra_id()))));
		form1BView.setTotal_literacy_demogra_id(String
				.valueOf(demograpicalIdsList.indexOf(Integer.parseInt(form1BView.getTotal_literacy_demogra_id()))));
		form1BView.setFem_literacy_demogra_id(
				String.valueOf(demograpicalIdsList.indexOf(Integer.parseInt(form1BView.getFem_literacy_demogra_id()))));

		for (int i2 = 0; i2 < form1BView.getTotal_coverage_indi().size(); i2++) {
			String indicatorSourceId = form1BView.getTotal_coverage_indi().get(i2).getSource();
			form1BView.getTotal_coverage_indi().get(i2)
					.setSource(String.valueOf(demograpicalIdsList.indexOf(Integer.parseInt(indicatorSourceId))));
		}

		return form1BView;
	}

	public static Form2Engage Form2EngageViewToForm2EngageSaveConverter(Form2Engage form2Engage, String district_id,
			String cycle_id, String financial_year, String user_id, String source) {

		Form2Engage form2EngageSave = form2Engage;

		int i;

		form2EngageSave.setPrimary_stakeholder_text(form2Engage.getPrimary_stake_array().get(0).getDocument_name());
		form2EngageSave.setPrimary_stakeholder_id(null);

		form2EngageSave.setSecondary_stakeholder_text(form2Engage.getSecondary_stake_array().get(0).getDocument_name());
		form2EngageSave.setSecondary_stakeholder_id(null);

		Map<String, String> idsMap = new HashMap<String, String>();

		for (i = 0; i < form2Engage.getPrimary_stake_array().size(); i++) {

			String key = form2Engage.getPrimary_stake_array().get(i).getDocument_id();
			String value = form2Engage.getPrimary_stake_array().get(i).getDocument_name();
			idsMap.put(key, value);
		}

		for (i = 0; i < form2Engage.getSecondary_stake_array().size(); i++) {

			String key = form2Engage.getSecondary_stake_array().get(i).getDocument_id();
			String value = form2Engage.getSecondary_stake_array().get(i).getDocument_name();
			idsMap.put(key, value);
		}

		for (i = 0; i < form2EngageSave.getDefiningprimaryrole_array().size(); i++) {

			form2EngageSave.getDefiningprimaryrole_array().get(i).setDocument_select_stakeholder(
					idsMap.get(form2EngageSave.getDefiningprimaryrole_array().get(i).getDocument_select_stakeholder()));
		}

		for (i = 0; i < form2EngageSave.getEfforttoaddressissue_array().size(); i++) {

			form2EngageSave.getEfforttoaddressissue_array().get(i).setDocument_select_stakeholder(idsMap
					.get(form2EngageSave.getEfforttoaddressissue_array().get(i).getDocument_select_stakeholder()));
		}

		for (i = 0; i < form2EngageSave.getEnhanceefficiency_array().size(); i++) {

			form2EngageSave.getEnhanceefficiency_array().get(i).setDocument_select_stakeholder(
					idsMap.get(form2EngageSave.getEnhanceefficiency_array().get(i).getDocument_select_stakeholder()));
		}

		for (i = 0; i < form2EngageSave.getThemeleadbydpt_array().size(); i++) {

			form2EngageSave.getThemeleadbydpt_array().get(i).setDocument_select_stakeholder(
					idsMap.get(form2EngageSave.getThemeleadbydpt_array().get(i).getDocument_select_stakeholder()));
		}

		form2EngageSave.setDefining_primary_role_section_select(
				form2Engage.getDefiningprimaryrole_array().get(0).getDocument_select_stakeholder());
		form2EngageSave.setDefining_primary_role_section_text(
				form2Engage.getDefiningprimaryrole_array().get(0).getDocument_desc());

		form2EngageSave.setCurrent_effort_to_address_the_issue_section_select(
				form2Engage.getEfforttoaddressissue_array().get(0).getDocument_select_stakeholder());
		form2EngageSave.setCurrent_effort_to_address_the_issue_section_text(
				form2Engage.getEfforttoaddressissue_array().get(0).getDocument_desc());

		form2EngageSave.setHow_to_enhance_engagement_and_efficiency_section_select(
				form2Engage.getEnhanceefficiency_array().get(0).getDocument_select_stakeholder());
		form2EngageSave.setHow_to_enhance_engagement_and_efficiency_section_text(
				form2Engage.getEnhanceefficiency_array().get(0).getDocument_desc());

		form2EngageSave.setTheme_lead_by_department_section_select(
				form2Engage.getThemeleadbydpt_array().get(0).getDocument_select_stakeholder());
		form2EngageSave.setTheme_lead_by_department_section_text(
				form2Engage.getThemeleadbydpt_array().get(0).getDocument_desc());

		for (i = 0; i < form2EngageSave.getPrimary_stake_array().size(); i++) {

			if ((form2EngageSave.getPrimary_stake_array().get(i).getDocument_name())
					.equals(form2EngageSave.getPrimary_stakeholder_text())) {
				form2EngageSave.getPrimary_stake_array().remove(i);
				break;
			}
		}
		for (i = 0; i < form2EngageSave.getSecondary_stake_array().size(); i++) {

			if ((form2EngageSave.getSecondary_stake_array().get(i).getDocument_name())
					.equals(form2EngageSave.getSecondary_stakeholder_text())) {
				form2EngageSave.getSecondary_stake_array().remove(i);
				break;
			}
		}

		form2EngageSave.getDefiningprimaryrole_array().remove(0);
		form2EngageSave.getEfforttoaddressissue_array().remove(0);
		form2EngageSave.getEnhanceefficiency_array().remove(0);
		form2EngageSave.getThemeleadbydpt_array().remove(0);

		form2EngageSave.setDistrict(district_id);
		form2EngageSave.setCycle(cycle_id);
		form2EngageSave.setYear(financial_year);
		form2EngageSave.setUserid(user_id);
		form2EngageSave.setSource(source);

		return form2EngageSave;
	}

	public static Form3Define Form3DefineViewToForm3DefineSaveConverter(Form3Define form3Define, String district_id,
			String cycle_id, String financial_year, String user_id, String source) {

		Form3Define form3DefineSave = form3Define;
		form3DefineSave.setTheme_id("1");

		form3DefineSave.setService_action_part_of_engagement("Service delivery");
		form3DefineSave.setService_description_p_f_h_s_p(
				form3Define.getService_array().get(0).getDocument_description_p_f_h_s_p());
		form3DefineSave.setService_possible_s_t_i(form3Define.getService_array().get(0).getDocument_possible_s_t_i());
		form3DefineSave.setService_action_part("Service delivery");
		form3DefineSave.setService_action_required(null);

		form3DefineSave.setWorkforce_service_action_part_of_engagement("Workforce");
		form3DefineSave.setWorkforce_service_description_p_f_h_s_p(
				form3Define.getWorkforce_array().get(0).getDocument_description_p_f_h_s_p());
		form3DefineSave.setWorkforce_service_possible_s_t_i(
				form3Define.getWorkforce_array().get(0).getDocument_possible_s_t_i());
		form3DefineSave.setWorkforce_action_part("Workforce");
		form3DefineSave.setWorkforce_action_required(null);

		form3DefineSave.setSupplies_service_action_part_of_engagement("Supplies & technology");
		form3DefineSave.setSupplies_service_description_p_f_h_s_p(
				form3Define.getSupplies_array().get(0).getDocument_description_p_f_h_s_p());
		form3DefineSave.setSupplies_service_possible_s_t_i(
				form3Define.getSupplies_array().get(0).getDocument_possible_s_t_i());
		form3DefineSave.setSupplies_action_part("Supplies & technology");
		form3DefineSave.setSupplies_action_required(null);

		form3DefineSave.setHealth_service_action_part_of_engagement("Health information");
		form3DefineSave.setHealth_service_description_p_f_h_s_p(
				form3Define.getHealth_array().get(0).getDocument_description_p_f_h_s_p());
		form3DefineSave
				.setHealth_service_possible_s_t_i(form3Define.getHealth_array().get(0).getDocument_possible_s_t_i());
		form3DefineSave.setHealth_action_part("Health information");
		form3DefineSave.setHealth_action_required(null);

		form3DefineSave.setFinance_service_action_part_of_engagement("Finance");
		form3DefineSave.setFinance_service_description_p_f_h_s_p(
				form3Define.getFinance_array().get(0).getDocument_description_p_f_h_s_p());
		form3DefineSave
				.setFinance_service_possible_s_t_i(form3Define.getFinance_array().get(0).getDocument_possible_s_t_i());
		form3DefineSave.setFinance_action_part("Finance");
		form3DefineSave.setFinance_action_required(null);

		form3DefineSave.setPolicy_service_action_part_of_engagement("Policy/governance");
		form3DefineSave.setPolicy_service_description_p_f_h_s_p(
				form3Define.getPolicy_array().get(0).getDocument_description_p_f_h_s_p());
		form3DefineSave
				.setPolicy_service_possible_s_t_i(form3Define.getPolicy_array().get(0).getDocument_possible_s_t_i());
		form3DefineSave.setPolicy_action_part("Policy/governance");
		form3DefineSave.setPolicy_action_required(null);

		form3DefineSave.setFirst_service_document_action_required(
				form3Define.getService_array().get(0).getDocument_action_required());
		form3DefineSave.setFirst_workforce_document_action_required(
				form3Define.getWorkforce_array().get(0).getDocument_action_required());
		form3DefineSave.setFirst_supplies_document_action_required(
				form3Define.getSupplies_array().get(0).getDocument_action_required());
		form3DefineSave.setFirst_health_document_action_required(
				form3Define.getHealth_array().get(0).getDocument_action_required());
		form3DefineSave.setFirst_finance_document_action_required(
				form3Define.getFinance_array().get(0).getDocument_action_required());
		form3DefineSave.setFirst_policy_document_action_required(
				form3Define.getPolicy_array().get(0).getDocument_action_required());

		form3DefineSave.getService_array().remove(0);
		form3DefineSave.getWorkforce_array().remove(0);
		form3DefineSave.getSupplies_array().remove(0);
		form3DefineSave.getHealth_array().remove(0);
		form3DefineSave.getFinance_array().remove(0);
		form3DefineSave.getPolicy_array().remove(0);

		form3DefineSave.setDistrict(district_id);
		form3DefineSave.setCycle(cycle_id);
		form3DefineSave.setYear(financial_year);
		form3DefineSave.setUserid(user_id);
		form3DefineSave.setSource(source);
		return form3DefineSave;
	}

	public static Form4Plan Form4PlanViewToForm4PlanSaveConverter(Map<String, String> idsMap, Form3Define form3Define,
			Form4Plan form4Plan, String district_id, String cycle_id, String financial_year, String user_id,
			String source) {

		Form4Plan form4PlanSave = form4Plan;

		int i, j;

		// Sorting and id insertion for service action array
		if (form4Plan != null && form4Plan.getService_action_arr() != null
				&& form4Plan.getService_action_arr().size() > 0) {

			List<Form4PlanCommonObject> serviceActionArray = form4PlanSave.getService_action_arr();
			Collections.sort(serviceActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4PlanSave.setService_action_arr(serviceActionArray);
		}

		for (i = 0; i < form4PlanSave.getService_action_arr().size(); i++) {

			form4PlanSave.getService_action_arr().get(i).setAction_req_id(String.valueOf(i));
			form4PlanSave.getService_action_arr().get(i)
					.setSel_stakeholder(idsMap.get(form4Plan.getService_action_arr().get(i).getSel_stakeholder()));
			form4PlanSave.getService_action_arr().get(i)
					.setSel_indicators(form4Plan.getService_action_arr().get(i).getSel_indicators());
		}
		for (j = 0; j <= form3Define.getService_array().size(); j++) {
			try {
				form4PlanSave.getService_action_arr().get(j)
						.setForm_3_1_action_part_engagement_nm_details_pkey(String.valueOf(j));
			} catch (Exception e) {
				LOGGER.error("Error in Form4PlanViewToForm4PlanSaveConverter (service action array) method : " + e);
			}
		}

		// Sorting and id insertion for work force array
		if (form4Plan != null && form4Plan.getWorkforce_action_arr() != null
				&& form4Plan.getWorkforce_action_arr().size() > 0) {

			List<Form4PlanCommonObject> workforceArray = form4PlanSave.getWorkforce_action_arr();
			Collections.sort(workforceArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4PlanSave.setWorkforce_action_arr(workforceArray);
		}

		for (i = 0; i < form4PlanSave.getWorkforce_action_arr().size(); i++) {

			form4PlanSave.getWorkforce_action_arr().get(i).setAction_req_id(String.valueOf(i));
			form4PlanSave.getWorkforce_action_arr().get(i)
					.setSel_stakeholder(idsMap.get(form4Plan.getWorkforce_action_arr().get(i).getSel_stakeholder()));
		}
		for (j = 0; j <= form3Define.getWorkforce_array().size(); j++) {
			try {
				form4PlanSave.getWorkforce_action_arr().get(j)
						.setForm_3_1_action_part_engagement_nm_details_pkey(String.valueOf(j));
			} catch (Exception e) {
				LOGGER.error("Error in Form4PlanViewToForm4PlanSaveConverter (work action array) method : " + e);
			}
		}

		// Sorting and id insertion for supply action array
		if (form4Plan != null && form4Plan.getSupplies_action_arr() != null
				&& form4Plan.getSupplies_action_arr().size() > 0) {

			List<Form4PlanCommonObject> supplyActionArray = form4PlanSave.getSupplies_action_arr();
			Collections.sort(supplyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4PlanSave.setSupplies_action_arr(supplyActionArray);
		}

		for (i = 0; i < form4PlanSave.getSupplies_action_arr().size(); i++) {

			form4PlanSave.getSupplies_action_arr().get(i).setAction_req_id(String.valueOf(i));
			form4PlanSave.getSupplies_action_arr().get(i)
					.setSel_stakeholder(idsMap.get(form4Plan.getSupplies_action_arr().get(i).getSel_stakeholder()));
		}
		for (j = 0; j <= form3Define.getSupplies_array().size(); j++) {
			try {
				form4PlanSave.getSupplies_action_arr().get(j)
						.setForm_3_1_action_part_engagement_nm_details_pkey(String.valueOf(j));
			} catch (Exception e) {
				LOGGER.error("Error in Form4PlanViewToForm4PlanSaveConverter (supply action array) method : " + e);
			}
		}

		// Sorting and id insertion for health action array
		if (form4Plan != null && form4Plan.getHealth_action_arr() != null
				&& form4Plan.getHealth_action_arr().size() > 0) {

			List<Form4PlanCommonObject> healthActionArray = form4PlanSave.getHealth_action_arr();
			Collections.sort(healthActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4PlanSave.setHealth_action_arr(healthActionArray);
		}

		for (i = 0; i < form4PlanSave.getHealth_action_arr().size(); i++) {

			form4PlanSave.getHealth_action_arr().get(i).setAction_req_id(String.valueOf(i));
			form4PlanSave.getHealth_action_arr().get(i)
					.setSel_stakeholder(idsMap.get(form4Plan.getHealth_action_arr().get(i).getSel_stakeholder()));
		}
		for (j = 0; j <= form3Define.getHealth_array().size(); j++) {
			try {
				form4PlanSave.getHealth_action_arr().get(j)
						.setForm_3_1_action_part_engagement_nm_details_pkey(String.valueOf(j));
			} catch (Exception e) {
				LOGGER.error("Error in Form4PlanViewToForm4PlanSaveConverter (health action array) method : " + e);
			}
		}

		// Sorting and id insertion for finance action array
		if (form4Plan != null && form4Plan.getFinance_action_arr() != null
				&& form4Plan.getFinance_action_arr().size() > 0) {

			List<Form4PlanCommonObject> financeActionArray = form4PlanSave.getFinance_action_arr();
			Collections.sort(financeActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4PlanSave.setFinance_action_arr(financeActionArray);
		}

		for (i = 0; i < form4PlanSave.getFinance_action_arr().size(); i++) {

			form4PlanSave.getFinance_action_arr().get(i).setAction_req_id(String.valueOf(i));
			form4PlanSave.getFinance_action_arr().get(i)
					.setSel_stakeholder(idsMap.get(form4Plan.getFinance_action_arr().get(i).getSel_stakeholder()));
		}
		for (j = 0; j <= form3Define.getFinance_array().size(); j++) {
			try {
				form4PlanSave.getFinance_action_arr().get(j)
						.setForm_3_1_action_part_engagement_nm_details_pkey(String.valueOf(j));
			} catch (Exception e) {
				LOGGER.error("Error in Form4PlanViewToForm4PlanSaveConverter (finance action array) method : " + e);
			}
		}

		// Sorting and id insertion for policy action array
		if (form4Plan != null && form4Plan.getPolicy_action_arr() != null
				&& form4Plan.getPolicy_action_arr().size() > 0) {

			List<Form4PlanCommonObject> policyActionArray = form4PlanSave.getPolicy_action_arr();
			Collections.sort(policyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4PlanSave.setPolicy_action_arr(policyActionArray);
		}

		for (i = 0; i < form4PlanSave.getPolicy_action_arr().size(); i++) {

			form4PlanSave.getPolicy_action_arr().get(i).setAction_req_id(String.valueOf(i));
			form4PlanSave.getPolicy_action_arr().get(i)
					.setSel_stakeholder(idsMap.get(form4Plan.getPolicy_action_arr().get(i).getSel_stakeholder()));
		}
		for (j = 0; j <= form3Define.getPolicy_array().size(); j++) {
			try {
				form4PlanSave.getPolicy_action_arr().get(j)
						.setForm_3_1_action_part_engagement_nm_details_pkey(String.valueOf(j));
			} catch (Exception e) {
				LOGGER.error("Error in Form4PlanViewToForm4PlanSaveConverter (policy action array) method : " + e);
			}
		}

		form4PlanSave.setDistrict(district_id);
		form4PlanSave.setCycle(cycle_id);
		form4PlanSave.setYear(financial_year);
		form4PlanSave.setUserid(user_id);
		form4PlanSave.setSource(source);

		return form4PlanSave;
	}

	public static Form5Followup Form5FollowupViewToForm5FollowupSaveConverter(Form5Followup form5Followup,
			String district_id, String cycle_id, String financial_year, String user_id, String source) {

		Form5Followup form5FollowupSave = form5Followup;

		int i, j;

		// Sorting and id insertion for service action array
		if (form5Followup != null && form5Followup.getService_action_arr() != null
				&& form5Followup.getService_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> serviceActionArray = form5Followup.getService_action_arr();
			Collections.sort(serviceActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setService_action_arr(serviceActionArray);
		}

		for (i = 0; i < form5Followup.getService_action_arr().size(); i++) {

			form5FollowupSave.getService_action_arr().get(i).setDev_action_point_id(String.valueOf(i));
			form5FollowupSave.getService_action_arr().get(i).setAction_req_id(String.valueOf(i));

			for (j = 0; j < form5Followup.getService_action_arr().get(i).getSel_indicators().size(); j++) {

				form5FollowupSave.getService_action_arr().get(i).getSel_indicators().get(j)
						.setDev_indicator_id(String.valueOf(j));
			}

		}

		// Sorting and id insertion for work force array
		if (form5Followup != null && form5Followup.getWorkforce_action_arr() != null
				&& form5Followup.getWorkforce_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> workforceArray = form5Followup.getWorkforce_action_arr();
			Collections.sort(workforceArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setWorkforce_action_arr(workforceArray);
		}

		for (i = 0; i < form5Followup.getWorkforce_action_arr().size(); i++) {

			form5FollowupSave.getWorkforce_action_arr().get(i).setDev_action_point_id(String.valueOf(i));
			form5FollowupSave.getWorkforce_action_arr().get(i).setAction_req_id(String.valueOf(i));

			for (j = 0; j < form5Followup.getWorkforce_action_arr().get(i).getSel_indicators().size(); j++) {

				form5FollowupSave.getWorkforce_action_arr().get(i).getSel_indicators().get(j)
						.setDev_indicator_id(String.valueOf(j));
			}

		}

		// Sorting and id insertion for supply action array
		if (form5Followup != null && form5Followup.getSupplies_action_arr() != null
				&& form5Followup.getSupplies_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> supplyActionArray = form5Followup.getSupplies_action_arr();
			Collections.sort(supplyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setSupplies_action_arr(supplyActionArray);
		}

		for (i = 0; i < form5Followup.getSupplies_action_arr().size(); i++) {

			form5FollowupSave.getSupplies_action_arr().get(i).setDev_action_point_id(String.valueOf(i));
			form5FollowupSave.getSupplies_action_arr().get(i).setAction_req_id(String.valueOf(i));

			for (j = 0; j < form5Followup.getSupplies_action_arr().get(i).getSel_indicators().size(); j++) {

				form5FollowupSave.getSupplies_action_arr().get(i).getSel_indicators().get(j)
						.setDev_indicator_id(String.valueOf(j));
			}

		}

		// Sorting and id insertion for health action array
		if (form5Followup != null && form5Followup.getHealth_action_arr() != null
				&& form5Followup.getHealth_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> healthActionArray = form5Followup.getHealth_action_arr();
			Collections.sort(healthActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setHealth_action_arr(healthActionArray);
		}

		for (i = 0; i < form5Followup.getHealth_action_arr().size(); i++) {

			form5FollowupSave.getHealth_action_arr().get(i).setDev_action_point_id(String.valueOf(i));
			form5FollowupSave.getHealth_action_arr().get(i).setAction_req_id(String.valueOf(i));

			for (j = 0; j < form5Followup.getHealth_action_arr().get(i).getSel_indicators().size(); j++) {

				form5FollowupSave.getHealth_action_arr().get(i).getSel_indicators().get(j)
						.setDev_indicator_id(String.valueOf(j));
			}

		}

		// Sorting and id insertion for finance action array
		if (form5Followup != null && form5Followup.getFinance_action_arr() != null
				&& form5Followup.getFinance_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> financeActionArray = form5Followup.getFinance_action_arr();
			Collections.sort(financeActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setFinance_action_arr(financeActionArray);
		}

		for (i = 0; i < form5Followup.getFinance_action_arr().size(); i++) {

			form5FollowupSave.getFinance_action_arr().get(i).setDev_action_point_id(String.valueOf(i));
			form5FollowupSave.getFinance_action_arr().get(i).setAction_req_id(String.valueOf(i));

			for (j = 0; j < form5Followup.getFinance_action_arr().get(i).getSel_indicators().size(); j++) {

				form5FollowupSave.getFinance_action_arr().get(i).getSel_indicators().get(j)
						.setDev_indicator_id(String.valueOf(j));
			}

		}

		// Sorting and id insertion for policy action array
		if (form5Followup != null && form5Followup.getPolicy_action_arr() != null
				&& form5Followup.getPolicy_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> policyActionArray = form5Followup.getPolicy_action_arr();
			Collections.sort(policyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setPolicy_action_arr(policyActionArray);
		}

		for (i = 0; i < form5Followup.getPolicy_action_arr().size(); i++) {

			form5FollowupSave.getPolicy_action_arr().get(i).setDev_action_point_id(String.valueOf(i));
			form5FollowupSave.getPolicy_action_arr().get(i).setAction_req_id(String.valueOf(i));

			for (j = 0; j < form5Followup.getPolicy_action_arr().get(i).getSel_indicators().size(); j++) {

				form5FollowupSave.getPolicy_action_arr().get(i).getSel_indicators().get(j)
						.setDev_indicator_id(String.valueOf(j));
			}

		}

		form5FollowupSave.setDistrict(district_id);
		form5FollowupSave.setCycle(cycle_id);
		form5FollowupSave.setYear(financial_year);
		form5FollowupSave.setUserid(user_id);
		form5FollowupSave.setSource(source);
		return form5FollowupSave;
	}

	public static Supplementaryform1A Supplementaryform1AViewToSupplementaryform1ASaveConverter(
			Supplementaryform1A supplementaryform1A, String district_id, String cycle_id, String financial_year,
			String user_id, String source) {

		Supplementaryform1A supplementaryform1ASave = supplementaryform1A;
		supplementaryform1ASave.setParta_part("PART-A");
		supplementaryform1ASave.setPartb_part("PART-B");
		supplementaryform1ASave.setDistrict(district_id);
		supplementaryform1ASave.setCycle(cycle_id);
		supplementaryform1ASave.setYear(financial_year);
		supplementaryform1ASave.setUserid(user_id);
		supplementaryform1ASave.setSource(source);
		return supplementaryform1ASave;
	}

	public static Form4Plan getSortedActionArraysOfForm4Plan(Form4Plan form4Plan) {

		// Sorting service action array

		if (form4Plan != null && form4Plan.getService_action_arr() != null
				&& form4Plan.getService_action_arr().size() > 0) {

			List<Form4PlanCommonObject> serviceActionArray = form4Plan.getService_action_arr();
			Collections.sort(serviceActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4Plan.setService_action_arr(serviceActionArray);
		}

		// Sorting work force array
		if (form4Plan != null && form4Plan.getWorkforce_action_arr() != null
				&& form4Plan.getWorkforce_action_arr().size() > 0) {

			List<Form4PlanCommonObject> workforceArray = form4Plan.getWorkforce_action_arr();
			Collections.sort(workforceArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4Plan.setWorkforce_action_arr(workforceArray);
		}

		// Sorting supply action array
		if (form4Plan != null && form4Plan.getSupplies_action_arr() != null
				&& form4Plan.getSupplies_action_arr().size() > 0) {

			List<Form4PlanCommonObject> supplyActionArray = form4Plan.getSupplies_action_arr();
			Collections.sort(supplyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4Plan.setSupplies_action_arr(supplyActionArray);
		}

		// Sorting health action array
		if (form4Plan != null && form4Plan.getHealth_action_arr() != null
				&& form4Plan.getHealth_action_arr().size() > 0) {

			List<Form4PlanCommonObject> healthActionArray = form4Plan.getHealth_action_arr();
			Collections.sort(healthActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4Plan.setHealth_action_arr(healthActionArray);
		}

		// Sorting finance action array
		if (form4Plan != null && form4Plan.getFinance_action_arr() != null
				&& form4Plan.getFinance_action_arr().size() > 0) {

			List<Form4PlanCommonObject> financeActionArray = form4Plan.getFinance_action_arr();
			Collections.sort(financeActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4Plan.setFinance_action_arr(financeActionArray);
		}

		// Sorting policy action array
		if (form4Plan != null && form4Plan.getPolicy_action_arr() != null
				&& form4Plan.getPolicy_action_arr().size() > 0) {

			List<Form4PlanCommonObject> policyActionArray = form4Plan.getPolicy_action_arr();
			Collections.sort(policyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form4Plan.setPolicy_action_arr(policyActionArray);
		}
		return form4Plan;
	}

	public static Form5Followup getSortedActionArraysOfForm5FollowUp(Form5Followup form5Followup) {

		// Sorting service action array
		if (form5Followup != null && form5Followup.getService_action_arr() != null
				&& form5Followup.getService_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> serviceActionArray = form5Followup.getService_action_arr();
			Collections.sort(serviceActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setService_action_arr(serviceActionArray);
		}

		// Sorting work force array
		if (form5Followup != null && form5Followup.getWorkforce_action_arr() != null
				&& form5Followup.getWorkforce_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> workforceArray = form5Followup.getWorkforce_action_arr();
			Collections.sort(workforceArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setWorkforce_action_arr(workforceArray);
		}

		// Sorting supply action array
		if (form5Followup != null && form5Followup.getSupplies_action_arr() != null
				&& form5Followup.getSupplies_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> supplyActionArray = form5Followup.getSupplies_action_arr();
			Collections.sort(supplyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setSupplies_action_arr(supplyActionArray);
		}

		// Sorting health action array
		if (form5Followup != null && form5Followup.getHealth_action_arr() != null
				&& form5Followup.getHealth_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> healthActionArray = form5Followup.getHealth_action_arr();
			Collections.sort(healthActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setHealth_action_arr(healthActionArray);
		}

		// Sorting finance action array
		if (form5Followup != null && form5Followup.getFinance_action_arr() != null
				&& form5Followup.getFinance_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> financeActionArray = form5Followup.getFinance_action_arr();
			Collections.sort(financeActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setFinance_action_arr(financeActionArray);
		}

		// Sorting policy action array
		if (form5Followup != null && form5Followup.getPolicy_action_arr() != null
				&& form5Followup.getPolicy_action_arr().size() > 0) {

			List<Form5FollowUpActionCommonArray> policyActionArray = form5Followup.getPolicy_action_arr();
			Collections.sort(policyActionArray,
					(o1, o2) -> (Integer.parseInt(o1.getAction_req_id())) - (Integer.parseInt(o2.getAction_req_id())));
			form5Followup.setPolicy_action_arr(policyActionArray);
		}

		return form5Followup;

	}

	public static Form2Engage form2EngageToForm2EngageEditConverter(final Form2EngageEdit form2EngageEdit) {

		Form2Engage form2Engage = new Form2Engage();

		form2Engage.setForm_2_date_of_meeting(form2EngageEdit.getForm_2_date_of_meeting());
		form2Engage.setForm_2_venue(form2EngageEdit.getForm_2_venue());
		form2Engage.setForm_2_filled(form2EngageEdit.getForm_2_filled());
		form2Engage.setForm_2_filled_others(form2EngageEdit.getForm_2_filled_others());
		
		List<Form2EngagePartACommonStakeHoldersArray> primaryArray = form2EngageEdit.getPrimary_stake_array();
		List<Form2EngagePartACommonStakeHoldersArray> secondaryArray = form2EngageEdit.getSecondary_stake_array();
		
		// Combined Stakeholders Array
		List<Form2EngagePartACommonStakeHoldersArray> allStakeholdersArray = new ArrayList<>();
		allStakeholdersArray.addAll(primaryArray);
		allStakeholdersArray.addAll(secondaryArray);
		
		// Primary Stakeholder setup
		form2Engage.setPrimary_stake_label("Primary Stakeholder");		
		form2Engage.setPrimary_stakeholder_text(primaryArray.get(0).getDocument_name());		
		primaryArray.remove(0);
		form2Engage.setPrimary_stake_array(primaryArray);
		
		// Secondary Stakeholder setup
		form2Engage.setSecondary_stake_label("Secondary Stakeholder");		
		form2Engage.setSecondary_stakeholder_text(secondaryArray.get(0).getDocument_name());		
		secondaryArray.remove(0);
		form2Engage.setSecondary_stake_array(secondaryArray);		
		
		
		List<Form2EngagePartBCommonArray> definingPrimaryRoleArray = form2EngageEdit.getDefiningprimaryrole_array();
		form2Engage.setDefining_primary_role_section_select(getTextFromdId(allStakeholdersArray, definingPrimaryRoleArray.get(0).getDocument_select_stakeholder()));
		form2Engage.setDefining_primary_role_section_text(definingPrimaryRoleArray.get(0).getDocument_desc());
		definingPrimaryRoleArray.remove(0);
		form2Engage.setDefiningprimaryrole_array(changeIdToTextOfArray(allStakeholdersArray, definingPrimaryRoleArray));
		
		List<Form2EngagePartBCommonArray> effortToAddressIssue = form2EngageEdit.getEfforttoaddressissue_array();
		form2Engage.setCurrent_effort_to_address_the_issue_section_select(getTextFromdId(allStakeholdersArray, effortToAddressIssue.get(0).getDocument_select_stakeholder()));
		form2Engage.setCurrent_effort_to_address_the_issue_section_text(effortToAddressIssue.get(0).getDocument_desc());
		effortToAddressIssue.remove(0);
		form2Engage.setEfforttoaddressissue_array(changeIdToTextOfArray(allStakeholdersArray, effortToAddressIssue));
		
		List<Form2EngagePartBCommonArray> enhanceEfficiency = form2EngageEdit.getEnhanceefficiency_array();
		form2Engage.setHow_to_enhance_engagement_and_efficiency_section_select(getTextFromdId(allStakeholdersArray, enhanceEfficiency.get(0).getDocument_select_stakeholder()));
		form2Engage.setHow_to_enhance_engagement_and_efficiency_section_text(enhanceEfficiency.get(0).getDocument_desc());
		enhanceEfficiency.remove(0);
		form2Engage.setEnhanceefficiency_array(changeIdToTextOfArray(allStakeholdersArray, enhanceEfficiency));
		
		List<Form2EngagePartBCommonArray> themeLeadDepartment = form2EngageEdit.getThemeleadbydpt_array();
		form2Engage.setTheme_lead_by_department_section_select(getTextFromdId(allStakeholdersArray, themeLeadDepartment.get(0).getDocument_select_stakeholder()));
		form2Engage.setTheme_lead_by_department_section_text(themeLeadDepartment.get(0).getDocument_desc());
		themeLeadDepartment.remove(0);
		form2Engage.setThemeleadbydpt_array(changeIdToTextOfArray(allStakeholdersArray, themeLeadDepartment));
		
		form2Engage.setDistrict(form2EngageEdit.getDistrict());
		form2Engage.setCycle(form2EngageEdit.getCycle());
		form2Engage.setYear(form2EngageEdit.getYear());
		form2Engage.setUserid(form2EngageEdit.getUserid());
		form2Engage.setCompleted(form2EngageEdit.getCompleted());

		return form2Engage;
	}

	private static List<Form2EngagePartBCommonArray> changeIdToTextOfArray(final List<Form2EngagePartACommonStakeHoldersArray> allStakeholderArray, final List<Form2EngagePartBCommonArray> partB_array){
		
		for(int i=0; i< partB_array.size(); i++) {
			
			String id = partB_array.get(i).getDocument_select_stakeholder();
			partB_array.get(i).setDocument_select_stakeholder(getTextFromdId(allStakeholderArray, id));
		}
		return partB_array;
	}
	
	private static String getTextFromdId(final List<Form2EngagePartACommonStakeHoldersArray> allStakeholderArray, final String selectedId) {
		
		String selectedText = null, stakeholderId;
		
		for(int i = 0; i < allStakeholderArray.size(); i++) {

			stakeholderId = allStakeholderArray.get(i).getDocument_id();
			
			if(stakeholderId.equals(selectedId)) {
				selectedText = allStakeholderArray.get(i).getDocument_name();
				break;
			}
		}
		
		return selectedText;
	}
	
	public static Form3Define Form3DefineToForm3DefineEditConverter(final Form3DefineEdit form3DefineEdit) {
		
		Form3Define form3Define = new Form3Define();
		
		form3Define.setForm_3_checkdate(form3DefineEdit.getForm_3_checkdate());
		form3Define.setForm_3_meeting_venue(form3DefineEdit.getForm_3_meeting_venue());
		form3Define.setForm_3_filled_by(form3DefineEdit.getForm_3_filled_by());
		form3Define.setForm3_chair_person(form3DefineEdit.getForm3_chair_person());
		form3Define.setForm3_chair_person_others(form3DefineEdit.getForm3_chair_person_others());
		form3Define.setTheme_id("143");
		
		
		List<Form3DefineCommonArray> serviceArray = form3DefineEdit.getService_array();
		serviceArray = copyInsertArrayToCommonArray(serviceArray, form3DefineEdit.getService_array_insert());
		form3Define.setService_action_part_of_engagement("Service delivery");
		form3Define.setService_description_p_f_h_s_p(serviceArray.get(0).getDocument_description_p_f_h_s_p());
		form3Define.setService_possible_s_t_i(serviceArray.get(0).getDocument_possible_s_t_i());		
		form3Define.setService_action_part("Service delivery");
		form3Define.setFirst_service_document_action_required(serviceArray.get(0).getDocument_action_required());
		serviceArray.remove(0);
		form3Define.setService_array(serviceArray);
		
		
		List<Form3DefineCommonArray> workforceArray = form3DefineEdit.getWorkforce_array();
		workforceArray = copyInsertArrayToCommonArray(workforceArray, form3DefineEdit.getWorkforce_array_insert());
		form3Define.setWorkforce_service_action_part_of_engagement("Workforce");
		form3Define.setWorkforce_service_description_p_f_h_s_p(workforceArray.get(0).getDocument_description_p_f_h_s_p());
		form3Define.setWorkforce_service_possible_s_t_i(workforceArray.get(0).getDocument_possible_s_t_i());		
		form3Define.setWorkforce_action_part("Workforce");
		form3Define.setFirst_workforce_document_action_required(workforceArray.get(0).getDocument_action_required());
		workforceArray.remove(0);
		form3Define.setWorkforce_array(workforceArray);
		
		
		List<Form3DefineCommonArray> supplyArray = form3DefineEdit.getSupplies_array();
		supplyArray = copyInsertArrayToCommonArray(supplyArray, form3DefineEdit.getSupplies_array_insert());
		form3Define.setSupplies_service_action_part_of_engagement("Supplies & technology");
		form3Define.setSupplies_service_description_p_f_h_s_p(supplyArray.get(0).getDocument_description_p_f_h_s_p());
		form3Define.setSupplies_service_possible_s_t_i(supplyArray.get(0).getDocument_possible_s_t_i());		
		form3Define.setSupplies_action_part("Supplies & technology");
		form3Define.setFirst_supplies_document_action_required(supplyArray.get(0).getDocument_action_required());
		supplyArray.remove(0);
		form3Define.setSupplies_array(supplyArray);
		
		
		List<Form3DefineCommonArray> healthArray = form3DefineEdit.getHealth_array();
		healthArray = copyInsertArrayToCommonArray(healthArray, form3DefineEdit.getHealth_array_insert());
		form3Define.setHealth_service_action_part_of_engagement("Health information");
		form3Define.setHealth_service_description_p_f_h_s_p(healthArray.get(0).getDocument_description_p_f_h_s_p());
		form3Define.setHealth_service_possible_s_t_i(healthArray.get(0).getDocument_possible_s_t_i());		
		form3Define.setHealth_action_part("Health information");
		form3Define.setFirst_health_document_action_required(healthArray.get(0).getDocument_action_required());
		healthArray.remove(0);
		form3Define.setHealth_array(healthArray);
		
		
		List<Form3DefineCommonArray> financeArray = form3DefineEdit.getFinance_array();
		financeArray = copyInsertArrayToCommonArray(financeArray, form3DefineEdit.getFinance_array_insert());
		form3Define.setFinance_service_action_part_of_engagement("Finance");
		form3Define.setFinance_service_description_p_f_h_s_p(financeArray.get(0).getDocument_description_p_f_h_s_p());
		form3Define.setFinance_service_possible_s_t_i(financeArray.get(0).getDocument_possible_s_t_i());		
		form3Define.setFinance_action_part("Finance");
		form3Define.setFirst_finance_document_action_required(financeArray.get(0).getDocument_action_required());
		financeArray.remove(0);
		form3Define.setFinance_array(financeArray);
		
		
		List<Form3DefineCommonArray> policyArray = form3DefineEdit.getPolicy_array();
		policyArray = copyInsertArrayToCommonArray(policyArray, form3DefineEdit.getPolicy_array_insert());
		form3Define.setPolicy_service_action_part_of_engagement("Policy/governance");
		form3Define.setPolicy_service_description_p_f_h_s_p(policyArray.get(0).getDocument_description_p_f_h_s_p());
		form3Define.setPolicy_service_possible_s_t_i(policyArray.get(0).getDocument_possible_s_t_i());		
		form3Define.setPolicy_action_part("Policy/governance");
		form3Define.setFirst_policy_document_action_required(policyArray.get(0).getDocument_action_required());
		policyArray.remove(0);
		form3Define.setPolicy_array(policyArray);
		
		
		form3Define.setDistrict(form3DefineEdit.getDistrict());
		form3Define.setCycle(form3DefineEdit.getCycle());
		form3Define.setYear(form3DefineEdit.getYear());
		form3Define.setUserid(form3DefineEdit.getUserid());
		form3Define.setCompleted(form3DefineEdit.getCompleted());
		form3Define.setSource(form3DefineEdit.getSource());
		
		return form3Define;
	}
	
	private static List<Form3DefineCommonArray> copyInsertArrayToCommonArray(List<Form3DefineCommonArray> commonArray, List<Form3DefineCommonArray> insertArray) {
		
		if(insertArray.size() == 0) return commonArray;
		
		for(int i=0; i<insertArray.size(); i++) {
			
			Form3DefineCommonArray obj = new Form3DefineCommonArray();
			obj.setDocument_description_p_f_h_s_p(insertArray.get(i).getDocument_description_p_f_h_s_p());
			obj.setDocument_possible_s_t_i(insertArray.get(i).getDocument_possible_s_t_i());
			obj.setDocument_action_required(insertArray.get(i).getDocument_action_required());
			
			commonArray.add(obj);
			obj = null;
		}
		
		return commonArray;
	}
	/*
	public static Form1ASave Form1ASaveToForm1AEditUpdateConverter(final Form1AEditUpdate form1AEdit, final String source) {
		
		Form1ASave form1ASave =new Form1ASave();
		
		form1ASave.setDate_of_verification(form1AEdit.getDate_of_verification());
		form1ASave.setFilled_by(form1AEdit.getFilled_by());
		form1ASave.setVerified_by_name(form1AEdit.getVerified_by_name());
		form1ASave.setVerified_by_other_actual_name(form1AEdit.getVerified_by_name_actual_name());
		
		// State Policy
		List<Form1ASaveDocumentsArray> statePolicyArray = form1AEdit.getState_policy_array();
		form1ASave.setState_policy_first_doc_val(statePolicyArray.get(0).getDocument_val());
		form1ASave.setState_policy_first_availability(statePolicyArray.get(0).getDocument_availability());
		form1ASave.setState_policy_first_d_source(statePolicyArray.get(0).getDocument_source());
		form1ASave.setNumberOfTickets(String.valueOf(statePolicyArray.size()));
		statePolicyArray.remove(0);
		form1ASave.setState_policy_array(statePolicyArray);
		
		// District Policy
		List<Form1ASaveDocumentsArray> districtPolicyArray = form1AEdit.getDistrict_policy_array();
		form1ASave.setDistrict_policy_first_doc_val(districtPolicyArray.get(0).getDocument_val());
		form1ASave.setDistrict_policy_first_availability(districtPolicyArray.get(0).getDocument_availability());
		form1ASave.setDistrict_policy_first_d_source(districtPolicyArray.get(0).getDocument_source());
		form1ASave.setNumberOfdistrictrow(String.valueOf(districtPolicyArray.size()));
		districtPolicyArray.remove(0);
		form1ASave.setDistrict_policy_array(districtPolicyArray);
		
		
		// Health Department
		List<Form1ASaveDocumentsArray> healthPolicyArray = form1AEdit.getHealth_dept_array();
		form1ASave.setHealth_dept_first_doc_val(healthPolicyArray.get(0).getDocument_val());
		form1ASave.setHealth_dept_first_availability(healthPolicyArray.get(0).getDocument_availability());
		form1ASave.setHealth_dept_first_d_source(healthPolicyArray.get(0).getDocument_source());
		form1ASave.setNumberOfhealthdeptrow(String.valueOf(healthPolicyArray.size()));
		healthPolicyArray.remove(0);
		form1ASave.setHealth_dept_array(healthPolicyArray);
		
		// Non-Health Department
		List<Form1ASaveDocumentsArray> nonHeathPolicyArray = form1AEdit.getNon_health_dept_array();
		form1ASave.setNon_health_dept_first_doc_val(nonHeathPolicyArray.get(0).getDocument_val());
		form1ASave.setNon_health_dept_first_availability(nonHeathPolicyArray.get(0).getDocument_availability());
		form1ASave.setNon_health_dept_first_d_source(nonHeathPolicyArray.get(0).getDocument_source());
		form1ASave.setNumberOfnonhealthdeptrow(String.valueOf(nonHeathPolicyArray.size()));
		nonHeathPolicyArray.remove(0);
		form1ASave.setNon_health_dept_array(nonHeathPolicyArray);
		
		// Private Sector
		List<Form1ASaveDocumentsArray> privatePolicyArray = form1AEdit.getPrivate_sec_array();
		form1ASave.setPrivate_sec_first_doc_val(privatePolicyArray.get(0).getDocument_val());
		form1ASave.setPrivate_sec_first_availability(privatePolicyArray.get(0).getDocument_availability());
		form1ASave.setPrivate_sec_first_d_source(privatePolicyArray.get(0).getDocument_source());
		form1ASave.setNumberOfprivsecrow(String.valueOf(privatePolicyArray.size()));
		privatePolicyArray.remove(0);
		form1ASave.setPrivate_sec_array(privatePolicyArray);
		
		// Large Scale District Level Surveys
		List<Form1ASaveDocumentsArray> largeScaleArray = form1AEdit.getLarge_scale_district_array();
		form1ASave.setLarge_scale_district_first_doc_val(largeScaleArray.get(0).getDocument_val());
		form1ASave.setLarge_scale_district_first_availability(largeScaleArray.get(0).getDocument_availability());
		form1ASave.setLarge_scale_district_first_d_source(largeScaleArray.get(0).getDocument_source());
		form1ASave.setNumberOflargescaledistrictrow(String.valueOf(largeScaleArray.size()));
		largeScaleArray.remove(0);
		form1ASave.setLarge_scale_district_array(largeScaleArray);
		
		form1ASave.setDistrict(form1AEdit.getDistrict());
		form1ASave.setCycle(form1AEdit.getCycle());
		form1ASave.setYear(form1AEdit.getYear());
		form1ASave.setCompleted(form1AEdit.getCompleted());
		form1ASave.setSource(source);
		
		return form1ASave;
		
	}
	*/
	
	// Autofill form 1A and form 1B start
	
	public static Boolean autoFillForm1A_Form1B(Form1ADAO form1ADao, Form1BDAO form1BDao, String district, String cycle, String year, String userId) {
		
		String copyFromCycle = "1";
		
		//Note :- Source is intentionally made offline to process the ids of form1a into form 1b
		String source = Constants.OFFLINE_SOURCE;
		//Note :- All id processing logic is written in offline controller, offline service etc.

		try {			
			
			// Save form 1A
			Form1AView form1AView = form1ADao.retrieveForm1ADetails(district, copyFromCycle, year, userId);
			Form1ASave form1ASave = ServiceUtil.Form1AViewToSaveConverter(form1AView, district, cycle, year, userId, source);
			form1ADao.saveForm1AToDb(form1ASave);
			
			// Save form 1B
			Form1BView form1BView = form1BDao.retrieveForm1BDetails(district, copyFromCycle, year, userId);
			Form1BSave form1BSave = ServiceUtil.form1BViewToForm1BSaveConverter(form1AView, form1BView,
					district, cycle, year, userId, source);
			form1BDao.saveForm1BToDb(form1BSave);
			
			return true;
		}
		catch(Exception e) {
			form1ADao.deletedForm1ADetails(district, cycle, year, userId);
			form1BDao.deleteForm1BDetails(district, cycle, year, userId);
		}
		
		return false;
	}
	
	// Autofill form 1A and form 1B end
	
	
	// Convert Form1BEdit to Form1BSave start
	public static Form1BSave Form1BEditUpdateToForm1BSaveConverter(final Form1BEditUpdate form1BEditUpdate, final String source) {
		
		Form1BSave form1BSave =new Form1BSave();
		int i;
		
		form1BSave.setDate_of_verification(form1BEditUpdate.getDate_of_verification());
		form1BSave.setFilled_by(form1BEditUpdate.getFilled_by());
		form1BSave.setVerified_by_name(form1BEditUpdate.getVerified_by_name());
		form1BSave.setVerified_by_other_actual_name(form1BEditUpdate.getVerified_by_other_actual_name());
		
		form1BSave.setTotal_area(form1BEditUpdate.getTotal_area());
		form1BSave.setTotal_area_demogra_id(form1BEditUpdate.getTotal_area_demogra_id());
		form1BSave.setTotal_area_others(form1BEditUpdate.getTotal_area_others());
		
		form1BSave.setTotal_pop(form1BEditUpdate.getTotal_pop());
		form1BSave.setTotal_pop_demogra_id(form1BEditUpdate.getTotal_pop_demogra_id());
		form1BSave.setTotal_pop_others(form1BEditUpdate.getTotal_pop_others());
		
		form1BSave.setNum_child_under_5_y(form1BEditUpdate.getNum_child_under_5_y());
		form1BSave.setNum_child_under_5_y_demogra_id(form1BEditUpdate.getNum_child_under_5_y_demogra_id());
		form1BSave.setNo_of_child_under5_others(form1BEditUpdate.getNo_of_child_under5_others());
		
		form1BSave.setNum_women_in_reproductive_age_15_49_y(form1BEditUpdate.getNum_women_in_reproductive_age_15_49_y());
		form1BSave.setNum_women_in_reproductive_age_15_49_y_source(form1BEditUpdate.getNum_women_in_reproductive_age_15_49_y_source());
		form1BSave.setNum_women_in_reproductive_others(form1BEditUpdate.getNum_women_in_reproductive_others());
		
		form1BSave.setRural_pop(form1BEditUpdate.getRural_pop());
		form1BSave.setRural_pop_demogra_id(form1BEditUpdate.getRural_pop_demogra_id());
		form1BSave.setRural_pop_others(form1BEditUpdate.getRural_pop_others());
		
		form1BSave.setSc_pop(form1BEditUpdate.getSc_pop());
		form1BSave.setSc_pop_demogra_id(form1BEditUpdate.getSc_pop_demogra_id());
		form1BSave.setSc_pop_others(form1BEditUpdate.getSc_pop_others());
		
		form1BSave.setSt_pop(form1BEditUpdate.getSt_pop());
		form1BSave.setSt_pop_demogra_id(form1BEditUpdate.getSt_pop_demogra_id());
		form1BSave.setSt_pop_others(form1BEditUpdate.getSt_pop_others());
		
		form1BSave.setPop_density(form1BEditUpdate.getPop_density());
		form1BSave.setPop_density_demogra_id(form1BEditUpdate.getPop_density_demogra_id());
		form1BSave.setPop_dens_others(form1BEditUpdate.getPop_dens_others());
		
		form1BSave.setTotal_literacy(form1BEditUpdate.getTotal_literacy());
		form1BSave.setTotal_literacy_demogra_id(form1BEditUpdate.getTotal_literacy_demogra_id());
		form1BSave.setTot_lit_others(form1BEditUpdate.getTot_lit_others());
		
		form1BSave.setFem_literacy(form1BEditUpdate.getFem_literacy());
		form1BSave.setFem_literacy_demogra_id(form1BEditUpdate.getFem_literacy_demogra_id());
		form1BSave.setFemale_lit_others(form1BEditUpdate.getFemale_lit_others());
		
		form1BSave.setIphs_theme_name(form1BEditUpdate.getIphs_theme_name());
		form1BSave.setIphs_coverage_indicators(form1BEditUpdate.getIphs_coverage_indicators());
		form1BSave.setIphs_source(form1BEditUpdate.getIphs_source());
		form1BSave.setIphs_data(form1BEditUpdate.getIphs_data());
		form1BSave.setIphs_expected_status(form1BEditUpdate.getIphs_expected_status());
		form1BSave.setIphs_gap_hmis(form1BEditUpdate.getIphs_gap_hmis());
		
		
		List<Form1BSaveDocumentsArray> infraArray = form1BEditUpdate.getInfra_array();
		form1BSave.setDetails_infra(infraArray.get(0).getDocument_details());
		form1BSave.setSanctioned_infra(infraArray.get(0).getDocument_sanctioned());
		form1BSave.setAvailable_functional_infra(infraArray.get(0).getDocument_available_functional());
		form1BSave.setGap_infra(infraArray.get(0).getDocument_gap());
		infraArray.remove(0);		
		form1BSave.setInfra_array(infraArray);
		
		
		
		List<Form1BSaveDocumentsArray> financeArray = form1BEditUpdate.getFina_array();
		form1BSave.setDetails_fina(financeArray.get(0).getDocument_details());
		form1BSave.setSanctioned_fina(financeArray.get(0).getDocument_sanctioned());
		form1BSave.setAvailable_functional_fina(financeArray.get(0).getDocument_available_functional());
		form1BSave.setGap_fina(financeArray.get(0).getDocument_gap());
		financeArray.remove(0);		
		form1BSave.setFina_array(financeArray);
		
		
		List<Form1BSaveDocumentsArray> suppliesArray = form1BEditUpdate.getSupp_array();
		form1BSave.setDetails_supp(suppliesArray.get(0).getDocument_details());
		form1BSave.setSanctioned_supp(suppliesArray.get(0).getDocument_sanctioned());
		form1BSave.setAvailable_functional_supp(suppliesArray.get(0).getDocument_available_functional());
		form1BSave.setGap_supp(suppliesArray.get(0).getDocument_gap());
		suppliesArray.remove(0);		
		form1BSave.setSupp_array(suppliesArray);
		
		
		List<Form1BSaveDocumentsArray> technologyArray = form1BEditUpdate.getTech_array();
		form1BSave.setDetails_tech(technologyArray.get(0).getDocument_details());
		form1BSave.setSanctioned_tech(technologyArray.get(0).getDocument_sanctioned());
		form1BSave.setAvailable_functional_tech(technologyArray.get(0).getDocument_available_functional());
		form1BSave.setGap_tech(technologyArray.get(0).getDocument_gap());
		technologyArray.remove(0);		
		form1BSave.setTech_array(technologyArray);
		
		
		List<Form1BSaveDocumentsArray> humanResourceArray = form1BEditUpdate.getHr_array();
		form1BSave.setDetails_hr(humanResourceArray.get(0).getDocument_details());
		form1BSave.setSanctioned_hr(humanResourceArray.get(0).getDocument_sanctioned());
		form1BSave.setAvailable_functional_hr(humanResourceArray.get(0).getDocument_available_functional());
		form1BSave.setGap_hr(humanResourceArray.get(0).getDocument_gap());
		humanResourceArray.remove(0);		
		form1BSave.setHr_array(humanResourceArray);		
		
		
		
		form1BSave.setDistrict(form1BEditUpdate.getDistrict());
		form1BSave.setCycle(form1BEditUpdate.getCycle());
		form1BSave.setYear(form1BEditUpdate.getYear());
		form1BSave.setCompleted(form1BEditUpdate.getCompleted());
		form1BSave.setUserid(form1BEditUpdate.getUserid());
		
		form1BSave.setDist_demogra_dtl_id(form1BEditUpdate.getDist_demogra_dtl_id());		
		
		// Key NGO info
		List<Form1BSaveKeyNgoDocumentsArray> keyNgoInfoArray = new ArrayList<Form1BSaveKeyNgoDocumentsArray>();
		for(i=0; i<form1BEditUpdate.getKey_ngo_info_array().size(); i++) {
			Form1BSaveKeyNgoDocumentsArray arrInfo = new Form1BSaveKeyNgoDocumentsArray();
			arrInfo.setNgo_name(form1BEditUpdate.getKey_ngo_info_array().get(i).getNgo_name());
			arrInfo.setNgo_details(form1BEditUpdate.getKey_ngo_info_array().get(i).getNgo_details());
			keyNgoInfoArray.add(arrInfo);
		}
		form1BSave.setKey_ngo_info_array(keyNgoInfoArray);
		
		// Key NGO source
		List<Form1BSaveKeyStakeholderDocumentsArray> keyNgoSorceArray = new ArrayList<Form1BSaveKeyStakeholderDocumentsArray>();
		for(i=0; i<form1BEditUpdate.getKey_ngo_source_array().size(); i++) {
			Form1BSaveKeyStakeholderDocumentsArray arrSource = new Form1BSaveKeyStakeholderDocumentsArray();
			arrSource.setStakeholder_name(form1BEditUpdate.getKey_ngo_source_array().get(i).getStakeholder_name());
			arrSource.setContact_details(form1BEditUpdate.getKey_ngo_source_array().get(i).getContact_details());
			keyNgoSorceArray.add(arrSource);
		}
		form1BSave.setKey_ngo_source_array(keyNgoSorceArray);
		
		List<Form1BSaveIndicatorsModel> coverageIndicatorArray = new ArrayList<Form1BSaveIndicatorsModel>();
		for(i=0; i<form1BEditUpdate.getTotal_coverage_indi().size(); i++) {
			Form1BSaveIndicatorsModel arrCoverage = new Form1BSaveIndicatorsModel();
			arrCoverage.setArea_id(form1BEditUpdate.getTotal_coverage_indi().get(i).getArea_id());
			arrCoverage.setArea_name(form1BEditUpdate.getTotal_coverage_indi().get(i).getArea_name());
			arrCoverage.setIndicator_id(form1BEditUpdate.getTotal_coverage_indi().get(i).getIndicator_id());
			arrCoverage.setIndicator_val(form1BEditUpdate.getTotal_coverage_indi().get(i).getIndicator_val());
			arrCoverage.setData(form1BEditUpdate.getTotal_coverage_indi().get(i).getData());
			arrCoverage.setExpected(form1BEditUpdate.getTotal_coverage_indi().get(i).getExpected());
			arrCoverage.setGap(form1BEditUpdate.getTotal_coverage_indi().get(i).getGap());
			arrCoverage.setSource(form1BEditUpdate.getTotal_coverage_indi().get(i).getSource());
			arrCoverage.setIndicator_desc(form1BEditUpdate.getTotal_coverage_indi().get(i).getIndicator_desc());
			//System.out.println(form1BEditUpdate.getTotal_coverage_indi().get(i));
			coverageIndicatorArray.add(arrCoverage);
		}
		form1BSave.setTotal_coverage_indi(coverageIndicatorArray);
		form1BSave.setSource(source);
		
		return form1BSave;
	}
	
}







