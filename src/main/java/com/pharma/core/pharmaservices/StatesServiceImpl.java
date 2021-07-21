package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pharma.core.model.States;
import com.pharma.core.repository.StatesRepository;

/**
 * This is an implementation class that loads the states list
 *
 */
@Service("statesService")
public class StatesServiceImpl implements StatesService {

	@Autowired
	StatesRepository stateRepo;
	
	public List<States> getAllStates() {
		return stateRepo.findAll();
	}

	
	public List<States> getStateNameList(String term) {
		
		return stateRepo.findByStateNameIgnoreCaseContaining(term);
	}

		
}
