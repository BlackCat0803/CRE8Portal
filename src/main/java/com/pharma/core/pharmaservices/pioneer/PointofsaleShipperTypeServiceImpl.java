package com.pharma.core.pharmaservices.pioneer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.pioneer.PointofsaleShipperType;
import com.pharma.core.repository.pioneer.PointofsaleShipperTypeRepository;

/**
 * This is an implementation class that loads the pioneer related data
 */
@Service("pointofsaleShipperTypeService")
public class PointofsaleShipperTypeServiceImpl implements PointofsaleShipperTypeService {

	@Autowired
	PointofsaleShipperTypeRepository  pointofsaleShipperTypeRepo;

	public List<PointofsaleShipperType> getAllActiveStatus() {
		try {
			return pointofsaleShipperTypeRepo.findByIsdeleted(0);  // 0 -> Not Deleted, 1 -> Deleted
		} catch (Exception e) {
			e.printStackTrace();
		}
		 return null;
	}
	public PointofsaleShipperType getPointofsaleShipperTypeData(int id) {
		return pointofsaleShipperTypeRepo.findOne(id);
	}
	

	public List<PointofsaleShipperType> getPointofsaleShipperTypeByShippertypetext(String shippertypetext) {
		return pointofsaleShipperTypeRepo.findByShippertypetext(shippertypetext);
	}
}
