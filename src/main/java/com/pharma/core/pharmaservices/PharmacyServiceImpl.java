package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.Pharmacy;
import com.pharma.core.repository.PharmacyRepository;
/**
 * This is an implementation class that loads the pharmacy data
 *
 */
@Service("pharmacyService")
public class PharmacyServiceImpl implements PharmacyService {

	@Autowired
	PharmacyRepository pharmacyRepo;
	
	public List<Pharmacy> getPharamcyList() {
		return pharmacyRepo.findAll();
	}
	
	
}
