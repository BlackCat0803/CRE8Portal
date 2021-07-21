package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescriptionOriginType;

@Service
public interface PrescriptionOriginTypeService {
	
	List<PrescriptionOriginType> getAllPrescriptionOriginType();
}
