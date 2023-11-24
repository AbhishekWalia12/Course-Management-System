package com.cms;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cms.exception.AdmissionInvalidException;
import com.cms.model.Admission;
import com.cms.repository.AdmissionRepository;
import com.cms.service.AdmissionServiceImpl;




//Write Unit Tests for the methods in the AdmissionServiceImpl
@SpringBootTest(classes= {AdmissionServiceImplTest.class})
public class AdmissionServiceImplTest {
	
	@Mock
	AdmissionRepository admissionRepo;
	@InjectMocks
	AdmissionServiceImpl admissionService;
	
	//check whether the calculateFees calculates fees for the given associate Id
	@Test
	public void test108CalculateFees() throws AdmissionInvalidException {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setAssociateId("201");
		admission.setFees(2000);
		Admission admission2=new Admission();
		admission2.setRegistrationId(302);
		admission2.setAssociateId("201");
		admission2.setFees(2000);
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		l1.add(admission2);
		when(admissionRepo.findAll()).thenReturn(l1);
		assertEquals(4000, admissionService.calculateFees("201"));
	}
	
	//check whether addFeedback adds the feedback entered by the assocaite for the given registration Id
	@Test
	public void test109AddFeedback() throws AdmissionInvalidException {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		when(admissionRepo.findAll()).thenReturn(l1);
		assertEquals("Good", admissionService.addFeedback(301L, "Good",2).getFeedback());
		
	}
	
	
	//check whether highestFeeForTheRegisteredCourse returns the highest fee among all the courses registered by the associate
	@Test
	public void test110highestFeeForTheRegisteredCourse() throws AdmissionInvalidException {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setAssociateId("101");
		admission.setCourseId("202");
		admission.setFees(2000);
		Admission admission1=new Admission();
		admission1.setRegistrationId(302);
		admission1.setAssociateId("101");
		admission1.setCourseId("203");
		admission1.setFees(4000);
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		l1.add(admission1);
		when(admissionRepo.findAll()).thenReturn(l1);
		List<String> course=new ArrayList<>();
		course.add("203");
		assertEquals(course, admissionService.highestFeeForTheRegisteredCourse("101"));
		
		
		
	}
	
	//check whether the viewFeedbackByCourseId returns the list of feedbacks for the given courseId
	@Test
	public void test111ViewFeedbackByCourseId() throws AdmissionInvalidException {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setCourseId("201");
		admission.setFeedback("good");
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		when(admissionRepo.findAll()).thenReturn(l1);
		List<String> courseFeedback=new ArrayList<>();
		courseFeedback.add("good");
		assertEquals(courseFeedback, admissionService.viewFeedbackByCourseId("201"));
		
		
	}
	
	//check whether deactivateAdmission removes the admission for the given courseId in the database
	@Test
	public void test112DeactivateAdmission() throws AdmissionInvalidException {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setCourseId("201");
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		when(admissionRepo.findAll()).thenReturn(l1);
		assertEquals(true, admissionService.deactivateAdmission("201"));
	}
	
	//check whether addFeedback throws AdmissionInvalidException when the registration id is invalid
	@Test
	public void test113AddFeedbackForInvalidId() {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		when(admissionRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(AdmissionInvalidException.class, ()->admissionService.addFeedback(302L, "good", 2));
		assertEquals("Invalid Registration Id", exception.getMessage());
		
	}
	
	//check whether viewFeedbackByCourseId throws AdmissionInvalidException when the course id is invalid 
	@Test
	public void test114ViewFeedbackByCourseIdForInvalidCourseId() {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setCourseId("201");
		admission.setFeedback("good");
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		when(admissionRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(AdmissionInvalidException.class, ()->admissionService.viewFeedbackByCourseId("202"));
		assertEquals("Invalid Course Id", exception.getMessage());
		
		
	}
	
	

	//check whether the calculateFees throws AdmissionInvalidException for invalid associate Id
	@Test
	public void test115calculateFeesForInvalidAssociateId() {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setAssociateId("201");
		admission.setFees(2000);
		Admission admission2=new Admission();
		admission2.setRegistrationId(302);
		admission2.setAssociateId("201");
		admission2.setFees(2000);
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		l1.add(admission2);
		when(admissionRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(AdmissionInvalidException.class, ()->admissionService.calculateFees("202"));
		assertEquals("AssociateId does not exists", exception.getMessage());
		
		
	}
	
	//check whether the highestFeeForTheRegisteredCourse throws AdmissionInvalidException for invalid associate Id
	@Test
	public void test116highestFeeForTheRegisteredCourseForInvalidAssociateId() {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setAssociateId("101");
		admission.setCourseId("202");
		admission.setFees(2000);
		Admission admission1=new Admission();
		admission1.setRegistrationId(302);
		admission1.setAssociateId("101");
		admission1.setCourseId("203");
		admission1.setFees(4000);
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		l1.add(admission1);
		when(admissionRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(AdmissionInvalidException.class, ()->admissionService.highestFeeForTheRegisteredCourse("102"));
		assertEquals("AssociateId does not exists", exception.getMessage());
		
	}
	
	//check whether the deactivateAdmission throws AdmissionInvalidException for invalid course Id
	@Test
	public void test117deactivateAdmissionForInvalidCourseId() {
		Admission admission=new Admission();
		admission.setRegistrationId(301);
		admission.setCourseId("201");
		List<Admission> l1=new ArrayList<>();
		l1.add(admission);
		when(admissionRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(AdmissionInvalidException.class, ()->admissionService.deactivateAdmission("202"));
		assertEquals("CourseId does not exists", exception.getMessage());
		
	}
	

}
