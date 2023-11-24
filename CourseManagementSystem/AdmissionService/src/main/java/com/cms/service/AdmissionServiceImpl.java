package com.cms.service;


import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.repository.AdmissionRepository;

@Service
public class AdmissionServiceImpl implements IAdmissionService {
	@Autowired
	AdmissionRepository admissionRepo;
	@Autowired
	SequenceGeneratorService sequenceGen;

	
	public Admission registerAssociateForCourse(Admission admission)throws AdmissionInvalidException {
		if(admission.getRegistrationId()!=0) {
			if (admissionRepo.existsById(admission.getRegistrationId() )) {
	            throw new AdmissionInvalidException("AdmissionId already exists");    
	        }
			}
		    admission.setRegistrationId(sequenceGen.getNextSequence(admission.SEQUENCE_NAME));
		    admissionRepo.save(admission);
		return admission;
	}

	public int calculateFees(String associateId)throws AdmissionInvalidException {
		List<Admission> allAdmissions=admissionRepo.findAll();
		int totalFees=0;
		int count=0;
		for(Admission admission:allAdmissions) {
			if(admission.getAssociateId().equals(associateId)) {
				count++;
				totalFees+=admission.getFees();
			}
		}
		if(count==0) {
			throw new AdmissionInvalidException("AssociateId does not exists");
		}
		return totalFees;
	}

	
	public Admission addFeedback(Long regNo, String feedback, float feedbackRating) throws AdmissionInvalidException {
		List<Admission> allAdmissions=admissionRepo.findAll();
		Admission result=null;
		for(Admission admission:allAdmissions) {
			if(admission.getRegistrationId()==regNo) {
				admission.setFeedback(feedback);
				admissionRepo.save(admission);
				result=admission;
			}
		}
		if(result==null) {
			throw new AdmissionInvalidException("Invalid Registration Id");
		}
		return result;
	}

	public List<String> highestFeeForTheRegisteredCourse(String associateId)throws AdmissionInvalidException {
		int max=0;
		int maxIndex=0;
		int count=0;
		List<Integer> maxIndexes= new ArrayList<>();
		List<Admission> allAdmissions=admissionRepo.findAll();
		List<String> courses=new ArrayList<>();
		for(int i=0;i<allAdmissions.size();i++) {
			if(allAdmissions.get(i).getAssociateId().equals(associateId)) {
				count++;
				if(allAdmissions.get(i).getFees()>max) {
					maxIndex=i;
					max=allAdmissions.get(i).getFees();
				}
			} 
		}
		if(count!=0) {
		for(int i=0;i<allAdmissions.size();i++) {
			if(allAdmissions.get(i).getAssociateId().equals(associateId)) {
				if(i!=maxIndex) {
				if(allAdmissions.get(i).getFees()==max) {
					maxIndexes.add(i);
				}
			}
			}
		}
		}
	   if(count!=0) {
	   courses.add(allAdmissions.get(maxIndex).getCourseId());
	   }
	   if(!maxIndexes.isEmpty()) {
	  for(int i:maxIndexes) {
		 
		  courses.add(allAdmissions.get(i).getCourseId());
		  
	  }
	   }
	  if(count==0) {
		  throw new AdmissionInvalidException("AssociateId does not exists");
	  }
	  return courses;
		
	}

	public List<String> viewFeedbackByCourseId(String courseId) throws AdmissionInvalidException {
		List<Admission> allAdmissions=admissionRepo.findAll();
		int count=0;
		List<String> feedback=new ArrayList<>();
		for(Admission admission:allAdmissions) {
			if(admission.getCourseId().equals(courseId)) {
				count++;
				feedback.add(admission.getFeedback());
			}
		}
		if(count==0) {
			throw new AdmissionInvalidException("Invalid Course Id");
		}
		return feedback;
		
	}

	public boolean deactivateAdmission(String courseId)throws AdmissionInvalidException {
		List<Admission> allAdmissions=admissionRepo.findAll();
		int count=0;
		for(Admission admission:allAdmissions) {
			if(admission.getCourseId().equals(courseId)) {
				count++;
				admissionRepo.delete(admission);
			}
		}
		if(count==0) {
			throw new AdmissionInvalidException("CourseId does not exists");
		}
		return true;
	}

	 public boolean makePayment(int registartionId) throws AdmissionInvalidException{
		 List<Admission> allAdmissions=admissionRepo.findAll();
		 boolean result=false;
		 int count=0;
		 for(Admission admission:allAdmissions) {
				if(admission.getRegistrationId()==registartionId) {
					count++;
					
					result=true;
				}
			}
		 if(count==0) {
				throw new AdmissionInvalidException("RegNo does not exists");
			}
		 return result;
	}

	@Override
	public List<Admission> viewAll() {
		
		return admissionRepo.findAll();
	}

}
