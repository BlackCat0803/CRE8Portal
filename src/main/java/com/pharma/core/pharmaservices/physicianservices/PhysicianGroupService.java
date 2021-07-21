package com.pharma.core.pharmaservices.physicianservices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.physician.PhysicianGroup;

@Service
public interface PhysicianGroupService {

	boolean deleteByPhysician(int physicianId);
	
	boolean deleteByGroup(int groupId);
	
	List<PhysicianGroup> getActiveRecordsByPhysician(int physicianId);
	
	List<PhysicianGroup> getByPhysician(int physicianId);
	
	List<PhysicianGroup> getByGroup(int groupId);
	
	PhysicianGroup getRecord(int id);
	
}
