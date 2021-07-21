package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescribedItemType;
import com.pharma.core.repository.pioneer.PrescribedItemTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
 *
 */
@Service("prescribedItemTypeService")
public class PrescribedItemTypeServiceImpl implements PrescribedItemTypeService {

	@Autowired
	PrescribedItemTypeRepository prescribedItemTypeRepo;
	
	public List<PrescribedItemType> getAllPrescribedItemType() {
		try {
			return prescribedItemTypeRepo.findAll();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return null;
	}

	
		
}
