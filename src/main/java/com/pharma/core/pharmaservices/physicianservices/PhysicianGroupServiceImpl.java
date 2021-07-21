package com.pharma.core.pharmaservices.physicianservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.physician.PhysicianGroup;
import com.pharma.core.repository.physician.PhysicianGroupRepository;
import com.pharma.core.util.PharmacyUtil;
/**
 * This is an implementation class that loads and stores the physician group data
 *
 */
@Service("physicianGroup")
public class PhysicianGroupServiceImpl implements PhysicianGroupService {

	@Autowired
	PhysicianGroupRepository phyGroupRepo;
	
	public boolean deleteByPhysician(int physicianId) {
		boolean isDel = true;
		try {
			phyGroupRepo.deleteByPhysicianId(physicianId);
		} catch(Exception e) {
			e.printStackTrace();
			isDel = false;
		}
		return isDel;
	}

	public boolean deleteByGroup(int groupId) {
		boolean isDel = true;
		try {
			phyGroupRepo.deleteByGroupId(groupId);
		} catch(Exception e) {
			e.printStackTrace();
			isDel = false;
		}
		return isDel;
	}

	public List<PhysicianGroup> getByPhysician(int physicianId) {
		return phyGroupRepo.findByPhysicianId(physicianId);
	}

	public List<PhysicianGroup> getByGroup(int groupId) {
		return phyGroupRepo.findByGroupId(groupId);
	}

	public PhysicianGroup getRecord(int id) {
		return phyGroupRepo.findOne(id);
	}

	public List<PhysicianGroup> getActiveRecordsByPhysician(int physicianId) {
		return phyGroupRepo.findByPhysicianIdAndStatus(physicianId, PharmacyUtil.statusActive);
	}
	
}
