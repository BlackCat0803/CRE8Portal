package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PatientSyncStatusType;

@Service
public interface PatientSyncStatusTypeService {
	
	List<PatientSyncStatusType> getAllPatientSyncStatusType();
}
