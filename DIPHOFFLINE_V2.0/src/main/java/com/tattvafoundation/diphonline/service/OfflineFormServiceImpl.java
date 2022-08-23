package com.tattvafoundation.diphonline.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tattvafoundation.diphonline.Constants;
import com.tattvafoundation.diphonline.indicator.entity.Area;
import com.tattvafoundation.diphonline.indicator.entity.AreaIndicatorMapping;
import com.tattvafoundation.diphonline.indicator.entity.Indicator;
import com.tattvafoundation.diphonline.indicator.entity.OptionalIndicator;
import com.tattvafoundation.diphonline.model.Form1ASave;
import com.tattvafoundation.diphonline.model.Form1AView;
import com.tattvafoundation.diphonline.model.Form1BSave;
import com.tattvafoundation.diphonline.model.Form1BView;
import com.tattvafoundation.diphonline.model.Form2Engage;
import com.tattvafoundation.diphonline.model.Form3Define;
import com.tattvafoundation.diphonline.model.Form4Plan;
import com.tattvafoundation.diphonline.model.Form5Followup;
import com.tattvafoundation.diphonline.model.OfflineFormBean;
import com.tattvafoundation.diphonline.model.OfflineFormBeanVersion2;
import com.tattvafoundation.diphonline.model.Supplementaryform1A;
import com.tattvafoundation.diphonline.repository.Form1ADAO;
import com.tattvafoundation.diphonline.repository.Form1BDAO;
import com.tattvafoundation.diphonline.repository.Form2EngageDAO;
import com.tattvafoundation.diphonline.repository.Form3DefineDAO;
import com.tattvafoundation.diphonline.repository.Form4PlanDAO;
import com.tattvafoundation.diphonline.repository.Form5FollowupDAO;
import com.tattvafoundation.diphonline.repository.IndicatorDAO;
import com.tattvafoundation.diphonline.repository.SupplementaryForm1ADAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h1>Offline form service!</h1> Service for handling the requests with JSON
 * data from offline HTML forms.
 * <p>
 *
 * @author Mohd. Mohsin Ansari
 * @version 1.0
 * @since 2020-11-23
 */

@Service
public class OfflineFormServiceImpl implements OfflineFormService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OfflineFormServiceImpl.class);

	@Autowired
	private Form1ADAO form1ADao;
	@Autowired
	private Form1BDAO form1BDao;
	@Autowired
	private Form2EngageDAO form2EngageDao;
	@Autowired
	private Form3DefineDAO form3DefineDao;
	@Autowired
	private Form4PlanDAO form4PlanDao;
	@Autowired
	private Form5FollowupDAO form5FollowupDao;
	@Autowired
	private SupplementaryForm1ADAO supplementaryFormDao;
	@Autowired
	private IndicatorDAO indicatorDao;
	@Value("${offline.file.location}")
	private String offlineFilePath;

	@Transactional
	@Override
	public OfflineFormBeanVersion2 jsonDataExportVersion2(String district_id, String cycle_id, String financial_year,
			String user_id) {

		OfflineFormBeanVersion2 offlineBean = null;
		try {
			Form1AView obj1A = form1ADao.retrieveForm1ADetails(district_id, cycle_id, financial_year, user_id);
			Form1BView obj1B = form1BDao.retrieveForm1BDetails(district_id, cycle_id, financial_year, user_id);
			Form2Engage objEngage = form2EngageDao.retrieveForm2EngageDetails(district_id, cycle_id, financial_year, user_id);
			Form3Define objDefine = form3DefineDao.retrieveForm3DefineDetails(district_id, cycle_id, financial_year, user_id);
			
			Form4Plan objPlan = form4PlanDao.retrieveForm4PlanDetails(district_id, cycle_id, financial_year, user_id);
			objPlan = ServiceUtil.getSortedActionArraysOfForm4Plan(objPlan);
			
			Form5Followup objFollowup = form5FollowupDao.retrieveForm5FollowupDetails(district_id, cycle_id, financial_year, user_id);
			objFollowup = ServiceUtil.getSortedActionArraysOfForm5FollowUp(objFollowup);
			
			Supplementaryform1A objSupplementry = supplementaryFormDao.retrieveSupplementaryForm1ADetails(district_id, cycle_id, financial_year, user_id);
			
			offlineBean = new OfflineFormBeanVersion2();
			offlineBean.setForm1A(obj1A);
			offlineBean.setForm1B(obj1B);
			offlineBean.setForm2Engage(objEngage);
			offlineBean.setForm3Define(objDefine);
			offlineBean.setForm4Plan(objPlan);
			offlineBean.setForm5Followup(objFollowup);
			offlineBean.setSupplementaryform(objSupplementry);
			
			offlineBean.setDistrict(district_id);
			offlineBean.setCycle(cycle_id);
			offlineBean.setYear(financial_year);
			offlineBean.setUserid(user_id);
		} catch (Exception e) {
			LOGGER.info("Error in jsonDataExportVersion2 method: " + e);
		}
		return offlineBean;
	}

	@Transactional
	@Override
	public String jsonDataImportVersion2(OfflineFormBeanVersion2 model, String district_id, String cycle_id,
			String financial_year, String user_id) {

		String status = "";
		
			String source = Constants.OFFLINE_SOURCE;
			Map<String, String> form2EngageIdsMap = new HashMap<String, String>();

			if (model != null && model.getAreaList() != null && model.getAreaList().size() > 0) {

				List<Area> areaList = model.getAreaList();
				indicatorDao.saveAreaListIntoDatabase(areaList);

				if (model.getAreaIndicatorMappingList() != null && model.getAreaIndicatorMappingList().size() > 0) {
					List<AreaIndicatorMapping> areaIndicatorMappingList = model.getAreaIndicatorMappingList();
					indicatorDao.saveAreaIndicatorMappingListInDatabase(areaIndicatorMappingList);
				}
				if (model.getIndicatorList() != null && model.getIndicatorList().size() > 0) {
					List<Indicator> indicatorList = model.getIndicatorList();
					indicatorDao.saveIndicatorListInDatabase(indicatorList);
				}
				if (model.getOptionalIndicatorList() != null && model.getOptionalIndicatorList().size() > 0) {
					List<OptionalIndicator> optionalIndicatorList = model.getOptionalIndicatorList();
					indicatorDao.saveOptionalIndicatorListInDatabase(optionalIndicatorList);
				}
			}

			if (model != null && model.getForm1A() != null && model.getForm1A().getDate_of_verification() != null) {

				Form1ASave form1A = ServiceUtil.Form1AViewToSaveConverter(model.getForm1A(), district_id, cycle_id,
						financial_year, user_id, source);
				form1ADao.deletedForm1ADetails(district_id, cycle_id, financial_year, user_id);
				form1ADao.saveForm1AToDb(form1A);
				status = status + "Form 1A data saved. \n";
			} else {
				status = "No form data saved.";
				return status;
			}

			if (model.getForm1B() != null && model.getForm1B().getDate_of_verification() != null) {

				Form1BSave form1B = ServiceUtil.form1BViewToForm1BSaveConverter(model.getForm1A(), model.getForm1B(),
						district_id, cycle_id, financial_year, user_id, source);
				form1BDao.deleteForm1BDetails(district_id, cycle_id, financial_year, user_id);
				form1BDao.saveForm1BToDb(form1B);
				status = status + "Form 1B data saved. \n";
			} else
				return status;

			if (model.getForm2Engage() != null && model.getForm2Engage().getForm_2_date_of_meeting() != null) {

				form2EngageIdsMap = getPrimarySecondaryIdsOfForm2Engage(model.getForm2Engage());
				Form2Engage formEnggage = ServiceUtil.Form2EngageViewToForm2EngageSaveConverter(model.getForm2Engage(),
						district_id, cycle_id, financial_year, user_id, source);
				form2EngageDao.deleteForm2EngageDetails(district_id, cycle_id, financial_year, user_id);
				form2EngageDao.saveForm2EngageToDb(formEnggage);
				status = status + "Form 2 - Enggage data saved. \n";
			} else
				return status;

			if (model.getForm3Define() != null && model.getForm3Define().getForm_3_checkdate() != null) {

				Form3Define formDefine = ServiceUtil.Form3DefineViewToForm3DefineSaveConverter(model.getForm3Define(),
						district_id, cycle_id, financial_year, user_id, source);
				form3DefineDao.deleteForm3DefineDetails(district_id, cycle_id, financial_year, user_id);
				form3DefineDao.saveForm3DefineToDb(formDefine);
				status = status + "Form 3 - Define data saved. \n";
			} else
				return status;

			if (model.getForm4Plan() != null && model.getForm4Plan().getDate_of_meeting() != null) {

				Form4Plan formPlan = ServiceUtil.Form4PlanViewToForm4PlanSaveConverter(form2EngageIdsMap,
						model.getForm3Define(), model.getForm4Plan(), district_id, cycle_id, financial_year, user_id,
						source);
				form4PlanDao.deleteForm4PlanDetails(district_id, cycle_id, financial_year, user_id);
				form4PlanDao.saveForm4PlanToDb(formPlan);
				status = status + "Form 4 - Plan data saved. \n";
			} else
				return status;

			if (model.getForm5Followup() != null && model.getForm5Followup().getDate_of_meeting() != null) {

				Form5Followup formFollowup = ServiceUtil.Form5FollowupViewToForm5FollowupSaveConverter(
						model.getForm5Followup(), district_id, cycle_id, financial_year, user_id, source);
				form5FollowupDao.deleteForm5FollowupDetails(district_id, cycle_id, financial_year, user_id);
				form5FollowupDao.saveForm5FollowupToDb(formFollowup);
				status = status + "Form 5 - FollowUp data saved. \n";
			} else
				return status;

			if (model.getSupplementaryform() != null
					&& model.getSupplementaryform().getParta_date_of_release() != null) {

				Supplementaryform1A formSupplementry = ServiceUtil
						.Supplementaryform1AViewToSupplementaryform1ASaveConverter(model.getSupplementaryform(),
								district_id, cycle_id, financial_year, user_id, source);
				supplementaryFormDao.deleteSupplementaryForm1ADetails(district_id, cycle_id, financial_year, user_id);
				supplementaryFormDao.savsupplemmentaryform1aToDb(formSupplementry);
				status = status + "Form 6 - Supplementry data saved.";
			} else
				return status;			
		
		return status;

	}

	private Map<String, String> getPrimarySecondaryIdsOfForm2Engage(Form2Engage form2Engage) {

		Map<String, String> idsMap = null;
		try {
			int i, count = 0;
			idsMap = new HashMap<String, String>();

			for (i = 0; i < form2Engage.getPrimary_stake_array().size(); i++) {

				String key = form2Engage.getPrimary_stake_array().get(i).getDocument_id();
				idsMap.put(key, String.valueOf(count));
				count++;
			}

			for (i = 0; i < form2Engage.getSecondary_stake_array().size(); i++) {

				String key = form2Engage.getSecondary_stake_array().get(i).getDocument_id();
				idsMap.put(key, String.valueOf(count));
				count++;
			}
		} catch (Exception e) {
			LOGGER.info("Error in getPrimarySecondaryIdsOfForm2Engage method: " + e);
		}

		return idsMap;
	}

	@Transactional
	@Override
	public String jsonDataImport(OfflineFormBean model, String district_id, String cycle_id, String financial_year,
			String user_id) {

		String status = "";

		if (model != null && model.getForm1A() != null) {

			Form1ASave form1A = model.getForm1A();
			model.getForm1A().setDistrict(district_id);
			model.getForm1A().setCycle(cycle_id);
			model.getForm1A().setYear(financial_year);
			model.getForm1A().setUserid(user_id);
			form1ADao.deletedForm1ADetails(district_id, cycle_id, financial_year, user_id);
			form1ADao.saveForm1AToDb(form1A);
			status = status + "Form 1A data saved. \n";
		} else {
			status = "No form data saved.";
			return status;
		}

		if (model.getForm1B() != null) {

			Form1BSave form1B = model.getForm1B();
			model.getForm1B().setDistrict(district_id);
			model.getForm1B().setCycle(cycle_id);
			model.getForm1B().setYear(financial_year);
			model.getForm1B().setUserid(user_id);
			form1BDao.deleteForm1BDetails(district_id, cycle_id, financial_year, user_id);
			form1BDao.saveForm1BToDb(form1B);
			status = status + "Form 1B data saved. \n";
		} else
			return status;

		if (model.getForm2Engage() != null) {

			Form2Engage formEnggage = model.getForm2Engage();
			model.getForm2Engage().setDistrict(district_id);
			model.getForm2Engage().setCycle(cycle_id);
			model.getForm2Engage().setYear(financial_year);
			model.getForm2Engage().setUserid(user_id);
			form2EngageDao.deleteForm2EngageDetails(district_id, cycle_id, financial_year, user_id);
			form2EngageDao.saveForm2EngageToDb(formEnggage);
			status = status + "Form 2 - Enggage data saved. \n";
		} else
			return status;

		if (model.getForm3Define() != null) {

			Form3Define formDefine = model.getForm3Define();
			model.getForm3Define().setDistrict(district_id);
			model.getForm3Define().setCycle(cycle_id);
			model.getForm3Define().setYear(financial_year);
			model.getForm3Define().setUserid(user_id);
			form3DefineDao.deleteForm3DefineDetails(district_id, cycle_id, financial_year, user_id);
			form3DefineDao.saveForm3DefineToDb(formDefine);
			status = status + "Form 3 - Define data saved. \n";
		} else
			return status;

		if (model.getForm4Plan() != null) {

			Form4Plan formPlan = model.getForm4Plan();
			model.getForm4Plan().setDistrict(district_id);
			model.getForm4Plan().setCycle(cycle_id);
			model.getForm4Plan().setYear(financial_year);
			model.getForm4Plan().setUserid(user_id);
			form4PlanDao.deleteForm4PlanDetails(district_id, cycle_id, financial_year, user_id);
			form4PlanDao.saveForm4PlanToDb(formPlan);
			status = status + "Form 4 - Plan data saved. \n";
		} else
			return status;

		if (model.getForm5Followup() != null) {

			Form5Followup formFollowup = model.getForm5Followup();
			model.getForm5Followup().setDistrict(district_id);
			model.getForm5Followup().setCycle(cycle_id);
			model.getForm5Followup().setYear(financial_year);
			model.getForm5Followup().setUserid(user_id);
			form5FollowupDao.deleteForm5FollowupDetails(district_id, cycle_id, financial_year, user_id);
			form5FollowupDao.saveForm5FollowupToDb(formFollowup);
			status = status + "Form 5 - FollowUp data saved. \n";
		} else
			return status;

		if (model.getSupplementaryform() != null) {

			Supplementaryform1A formSupplementry = model.getSupplementaryform();
			model.getSupplementaryform().setDistrict(district_id);
			model.getSupplementaryform().setCycle(cycle_id);
			model.getSupplementaryform().setYear(financial_year);
			model.getSupplementaryform().setUserid(user_id);
			supplementaryFormDao.deleteSupplementaryForm1ADetails(district_id, cycle_id, financial_year, user_id);
			supplementaryFormDao.savsupplemmentaryform1aToDb(formSupplementry);
			status = status + "Form 6 - Supplementry data saved.";
		} else
			return status;

		return status;

	}

	@Transactional
	@Override
	public InputStreamResource FileSystemResource(HttpServletResponse response) throws IOException {

		response.setContentType("application/zip");
		response.setHeader("Content-Disposition", "attachment; filename=\"Tomcat.zip\"");
		InputStreamResource resource = new InputStreamResource(new FileInputStream(offlineFilePath));
		return resource;
	}
}
