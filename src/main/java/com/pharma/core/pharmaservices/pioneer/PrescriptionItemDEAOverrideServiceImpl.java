package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionItemDEAOverride;
import com.pharma.core.repository.pioneer.PrescriptionItemDEAOverrideRepository;

/**
 * This is an implementation class that loads the pioneer related data
 *
 */

@Service("prescriptionItemDEAOverrideService")
public class PrescriptionItemDEAOverrideServiceImpl implements PrescriptionItemDEAOverrideService {

	@Autowired
	PrescriptionItemDEAOverrideRepository prescriptionItemDEAOverrideRepo;
	
	@Autowired
	private Environment env;
	
	public List<PrescriptionItemDEAOverride> getPrescriptionItemDEAOverrideList(String gcn,String statecode) {
		
		List<PrescriptionItemDEAOverride> presObj = prescriptionItemDEAOverrideRepo.getPrescriptionItemDEAOverrideList(gcn,statecode);
		return presObj;
		
	
	}

	public List<PrescriptionItemDEAOverride> getPrescriptionItemDEAOverrideList(String gcn) {

		List<PrescriptionItemDEAOverride> presObj = prescriptionItemDEAOverrideRepo.getPrescriptionItemDEAOverrideList(gcn);
		return presObj;
	}

	
		
}
