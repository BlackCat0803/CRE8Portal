package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionItem;

@Service
public interface PrescriptionItemService {
	
	List<PrescriptionItem> getAllPrescriptionItem();

	List<PrescriptionItem> getAutoCompletePrescriptionItemsList(String term,String id);

	List<PrescriptionItem> getAllAutoCompletePrescriptionItemsList();

	List saveItem(int typeId, String drugName);
}
