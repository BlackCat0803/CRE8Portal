package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionSigCodes;
import com.pharma.core.repository.pioneer.PrescriptionSigCodesRepository;

/**
 * This is an implementation class that loads the pioneer related data
 */
@Service("prescriptionSigCodesService")
@PropertySource("classpath:webService.properties")
public class PrescriptionSigCodesServiceImpl implements PrescriptionSigCodesService {

	@Autowired
	PrescriptionSigCodesRepository prescriptionSigCodesRepo;
	
	@Autowired
	private Environment env;
	
	public List<PrescriptionSigCodes> getAutoCompletePrescriptionSigList(String term, String sigid) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionSigCodes> sigObj = prescriptionSigCodesRepo.getAutoCompletePrescriptionSigList(term,sigid, new PageRequest(0, MaxResults));
		return sigObj;
		
	
	}

	public List<PrescriptionSigCodes> getAllAutoCompletePrescriptionSigList(String term) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionSigCodes> sigObj = prescriptionSigCodesRepo.getAllAutoCompletePrescriptionSigList(term, new PageRequest(0, MaxResults));
		return sigObj;
	}

	
		
}
