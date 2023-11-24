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

import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.repository.AssociateRepository;
import com.cms.service.AssociateServiceImpl;
import com.cms.service.SequenceGeneratorService;

//Write Unit Tests for the methods in the AssociateServiceImpl
@SpringBootTest(classes= {AssociateServiceImplTest.class})
public class AssociateServiceImplTest {
	@Mock
	AssociateRepository associateRepo;
	@InjectMocks
	AssociateServiceImpl associateService;
	@Mock
    SequenceGeneratorService sequenceGeneratorService;
	
	
	@Test
	public void test118AddAssociate() throws AssociateInvalidException{	
		Associate associate = new Associate();
        associate.setAssociateId(null);
        List<Associate> l1=new ArrayList<>();
        l1.add(associate);
        when(associateRepo.findAll()).thenReturn(l1);
		 when(sequenceGeneratorService.generateSequence(Associate.SEQUENCE_NAME)).thenReturn("101");
		 assertEquals("101", associateService.addAssociate(associate).getAssociateId());
		
	}
	
	//check whether the viewByAssociateId returns the associate for the given associate Id
	@Test
	public void test119ViewByAssociateId() throws AssociateInvalidException {
		Associate as=new Associate();
		as.setAssociateId("101");
		as.setAssociateEmailId("sannusingh22@gmail.com");
		List<Associate> l1=new ArrayList<>();
	  l1.add(as);			
		when(associateRepo.findAll()).thenReturn(l1);
		assertEquals("sannusingh22@gmail.com", associateService.viewByAssociateId("101").getAssociateEmailId());
	}
	
	//check whether updateAssociate updates the address of the given assciateId in the database
	@Test
	public void test120updateAssociate() throws AssociateInvalidException {
		Associate as=new Associate();
		as.setAssociateId("101");
		as.setAssociateEmailId("sannusingh22@gmail.com");
		List<Associate> l1=new ArrayList<>();
	     l1.add(as);	
	     when(associateRepo.findAll()).thenReturn(l1);
		  assertEquals("sannusingh22@gmail.com", associateService.updateAssociate("101","sannusingh1509@gmail.com").getAssociateEmailId());
		
	}
	
	//check whether viewByAssociateId throws AssociateInvalidException for invalid associateId
	@Test
	public void test121ViewByAssociateIdForInvalidId() {
		Associate as=new Associate();
		as.setAssociateId("101");
		as.setAssociateEmailId("sannusingh22@gmail.com");
		List<Associate> l1=new ArrayList<>();
	     l1.add(as);	
	     when(associateRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(AssociateInvalidException.class, ()->associateService.viewByAssociateId("102"));
		assertEquals("Invalid Associate Id", exception.getMessage());
	}
	//check whether updateAssociate updates the address of the given assciateId in the database for invalid id
	@Test
	public void test120UpdateassociateForInvalidId() {
		Associate as=new Associate();
		as.setAssociateId("101");
		as.setAssociateEmailId("sannusingh22@gmail.com");
		List<Associate> l1=new ArrayList<>();
	     l1.add(as);	
	     when(associateRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(AssociateInvalidException.class, ()->associateService.updateAssociate("201", "ssanu@"));
		assertEquals("AssociateId does not exists", exception.getMessage());

	}
}