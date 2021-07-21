package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PointofsaleShipperType;

@Service
public interface PointofsaleShipperTypeService {
	

	List<PointofsaleShipperType> getAllActiveStatus();
	
	PointofsaleShipperType getPointofsaleShipperTypeData(int id);
	
	List<PointofsaleShipperType> getPointofsaleShipperTypeByShippertypetext(String shippertypetext);
}
