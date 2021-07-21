package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionItemDispensingUnit;

@Service
public interface PrescriptionItemDispensingUnitService {
	
	List<PrescriptionItemDispensingUnit> getAllPrescriptionItemDispensingUnit();
}
