package com.tattvafoundation.diphonline.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;

import com.tattvafoundation.diphonline.model.OfflineFormBean;
import com.tattvafoundation.diphonline.model.OfflineFormBeanVersion2;


public interface OfflineFormService {
	
	
	public String jsonDataImport(OfflineFormBean model, String district_id, String cycle_id, String financial_year, String user_id);
	
	public String jsonDataImportVersion2(OfflineFormBeanVersion2 model, String district_id, String cycle_id, String financial_year, String user_id);
	
	public OfflineFormBeanVersion2 jsonDataExportVersion2(String district_id, String cycle_id, String financial_year, String user_id);
	
	public InputStreamResource FileSystemResource (HttpServletResponse response) throws IOException;
	
}
