package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.Pharmacy;

@Service
public interface PharmacyService {

	List<Pharmacy> getPharamcyList();
	
}
