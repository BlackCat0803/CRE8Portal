package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.PrescriberTypeMaster;

@Service
public interface PrescriberTypeMasterService {

	List<PrescriberTypeMaster> getMasterList();
	
	PrescriberTypeMaster getPrescriberTypeMasterDetails(int id);
}
