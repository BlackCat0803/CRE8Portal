package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionItemDEAOverride;

@Service
public interface PrescriptionItemDEAOverrideService {
	
	List<PrescriptionItemDEAOverride> getPrescriptionItemDEAOverrideList(String gcn,String statecode);
	
	List<PrescriptionItemDEAOverride> getPrescriptionItemDEAOverrideList(String gcn);
}
