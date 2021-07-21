package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.PrescriberTypeMaster;
import com.pharma.core.repository.PrescriberTypeMasterRepository;
/**
 * This is an implementation class that loads and stores the prescription type master data
 *
 */
@Service("prescriberTypeMasterService")
public class PrescriberTypeMasterServiceImpl implements PrescriberTypeMasterService {

	@Autowired
	PrescriberTypeMasterRepository typeMasterRepo;
	
	public List<PrescriberTypeMaster> getMasterList() {
		return typeMasterRepo.findAll();
	}

	
	public PrescriberTypeMaster getPrescriberTypeMasterDetails(int id) {
		return typeMasterRepo.findOne(id);
	}

	
	
}
