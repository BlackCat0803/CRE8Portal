package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PrescribedItemType;

@Service
public interface PrescribedItemTypeService {
	
	List<PrescribedItemType> getAllPrescribedItemType();
}
