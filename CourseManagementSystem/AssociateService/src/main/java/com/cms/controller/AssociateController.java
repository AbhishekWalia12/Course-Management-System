package com.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import com.cms.exception.AssociateInvalidException;
import com.cms.model.Associate;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.service.AssociateServiceImpl;



@RestController
public class AssociateController {
	
	@Autowired
	AssociateServiceImpl associateService;
	@Autowired
	AuthenticationAuthorizationProxy authProxy;
	
	
	@PostMapping(value="/associate/addAssociate")
	public ResponseEntity<Object> addAssociate(@RequestBody Associate cObj,@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
		try {
			
            Associate addedAssociate = associateService.addAssociate(cObj);
            result=ResponseEntity.ok(addedAssociate);
        }  catch (AssociateInvalidException e) {
            result=ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
		}else {
            result=ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		return result;
	}
	
	@PutMapping(value="/associate/updateAssociate/{associateId}/{associateAddr}")
	public ResponseEntity<Object> updateAssociate(@PathVariable String associateId,@PathVariable("associateAddr") String associateAddress,@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
		try {
            Associate updateAssociate = associateService.updateAssociate(associateId, associateAddress);
            result=ResponseEntity.ok(updateAssociate);
        } catch (AssociateInvalidException e) {
            result=ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }}else {
            result=ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		return result;
	}
	
	@GetMapping(value="/associate/viewByAssociateId/{associateId}")
	public ResponseEntity<Object> viewByAssociateId(@PathVariable String associateId,@RequestHeader("Authorization") String token){
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
		try {
            Associate associate = associateService.viewByAssociateId(associateId);
            result=ResponseEntity.ok(associate);
        } catch (AssociateInvalidException e) {
            result=ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }}else {
            result=ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		return result;
	}
	
	@GetMapping(value="/associate/viewAll")
	 public ResponseEntity<Object> viewAll(@RequestHeader("Authorization") String token) {
		ResponseEntity<Object> result;
		if(authProxy.isValidToken(token)) {
            List<Associate> associateList = associateService.viewAll();
           result=ResponseEntity.status(HttpStatus.OK).body(associateList);
        } else {
        	result=ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
		return result;
	}
	
	

	
	
}





