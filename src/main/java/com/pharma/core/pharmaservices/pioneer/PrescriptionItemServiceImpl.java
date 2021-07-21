package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionItem;
import com.pharma.core.repository.pioneer.PrescriptionItemRepository;

/**
 * This is an implementation class that loads the pioneer related data
  */
@Service("prescriptionItemService")
public class PrescriptionItemServiceImpl implements PrescriptionItemService {
	
	@Autowired
	private Environment env;
	
	@Autowired
	PrescriptionItemRepository prescriptionItemRepo;
	
	public List<PrescriptionItem> getAllPrescriptionItem() {
		try {
			return prescriptionItemRepo.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}

	public List<PrescriptionItem> getAutoCompletePrescriptionItemsList(String term,String id) {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionItem> prescriptionItemObj = prescriptionItemRepo.getAutoCompletegetAutoCompletePrescriptionItemsList(term,id,new PageRequest(0, MaxResults));
		return prescriptionItemObj;
	}

	public List<PrescriptionItem> getAllAutoCompletePrescriptionItemsList() {
		int MaxResults=Integer.parseInt(env.getProperty("MaxResults"));
		List<PrescriptionItem> prescriptionItemObj = prescriptionItemRepo.getAllAutoCompletePrescriptionItemsList();
		return prescriptionItemObj;
	}

	
		
}
