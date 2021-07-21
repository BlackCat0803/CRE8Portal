package com.pharma.core.pharmaservices;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pharma.core.model.States;

@Service
public interface StatesService {
	
	List<States> getAllStates();
	
	//Auto complete method
	List<States> getStateNameList(String term);
}
