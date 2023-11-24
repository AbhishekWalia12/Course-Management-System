package com.cms.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;


@Service
public class AssociateServiceImpl implements IAssociateService{
	
	@Autowired
     AssociateRepository associateRepo;
	
	@Autowired
	SequenceGeneratorService sequenceGen;
	

	
	
	public Associate addAssociate(Associate cObj) throws AssociateInvalidException {
		List<Associate> allAssociate=associateRepo.findAll();
		
		if(cObj.getAssociateId()!=null) {
			for(Associate a:allAssociate) {
				if(cObj.getAssociateId().equals(a.getAssociateId())) {
					throw new AssociateInvalidException("AssociateId already exists");
				}
			}
		}
		cObj.setAssociateId(sequenceGen.generateSequence(cObj.SEQUENCE_NAME));
	
			
         associateRepo.save(cObj);
		
		
        return  cObj;
	}
		
	public Associate viewByAssociateId(String associateId) throws AssociateInvalidException {
		List<Associate> allAssociate=associateRepo.findAll();
		Associate associate=null;
		for(Associate a:allAssociate) {
			if(a.getAssociateId().equals(associateId)) {
				associate=a;
			}
		}
		if(associate==null) {
			throw new AssociateInvalidException("Invalid Associate Id");
		}
		return associate;
	}

	public Associate updateAssociate(String associateId, String associateAddress)throws AssociateInvalidException {
		List<Associate> allAssociate=associateRepo.findAll();
		Associate associate=null;
		for(Associate a:allAssociate) {
			if(a.getAssociateId().equals(associateId)) {
				a.setAssociateAddress(associateAddress);
				associateRepo.save(a);
				associate=a;
			}
		}
		if(associate==null) {
			throw new AssociateInvalidException("AssociateId does not exists"); 
		}
		return associate;
	}

	
	public List<Associate> viewAll() {
		// TODO Auto-generated method stub
		return associateRepo.findAll();
	}

}

