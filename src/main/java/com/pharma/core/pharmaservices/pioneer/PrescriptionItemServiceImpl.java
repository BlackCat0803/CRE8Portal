package com.pharma.core.pharmaservices.pioneer;

import com.pharma.core.model.pioneer.PrescriptionItem;
import com.pharma.core.repository.pioneer.PrescriptionItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

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

	@Override
	public List saveItem(int typeId, String drugName) {

		PrescriptionItem prescriptionItem = new PrescriptionItem();
		prescriptionItem.setItemid(UUID.randomUUID().toString().toUpperCase());
		prescriptionItem.setItemtypeid(typeId);
		prescriptionItem.setPrintname(drugName);
		prescriptionItem.setItemname(drugName);
		prescriptionItem.setLocationid("2DBD3435-FE93-4D79-8AAF-6D258A98FED7");
		prescriptionItem.setItemgroup("");
		prescriptionItem.setUpc("");
		prescriptionItem.setNdc("");
		prescriptionItem.setManufacturer("");
		prescriptionItem.setDepartmentid("BC577215-6841-4101-B7F5-6A89A5DDA041");
		prescriptionItem.setLabeltypeid(1);
		prescriptionItem.setDrugclassid(2);
		prescriptionItem.setDeaschedule(0);
		prescriptionItem.setCpt("");
		prescriptionItem.setHcpc("");
		prescriptionItem.setBrandequivalentndc("");
		prescriptionItem.setGcn("");
		prescriptionItem.setHicl("");
		prescriptionItem.setItemadministrationid(0);
		prescriptionItem.setDosageformid(0);
		prescriptionItem.setDispensingunitid(1);
		prescriptionItem.setUnitsperlabel(0);
		prescriptionItem.setStocksize(1.00);
		prescriptionItem.setPackagetypeid(10);
		prescriptionItem.setStrength("");
		prescriptionItem.setDefaultdayssupply(0);
		return Collections.singletonList(prescriptionItemRepo.save(prescriptionItem));
	}


}
