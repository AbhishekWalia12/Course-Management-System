package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.cms.model.Associate;

@FeignClient(name="associateapp",url="http://localhost:9092")
public interface AssociateProxy {
	
	@GetMapping(value="/associate/viewAll")
	public ResponseEntity<Object> viewAll(@RequestHeader HttpHeaders header);
	@PostMapping(value="/associate/addAssociate")
	public ResponseEntity<Object> addAssociate(@RequestBody Associate cObj,@RequestHeader HttpHeaders header);
	@PutMapping(value="/associate/updateAssociate/{associateId}/{associateAddr}")
	public ResponseEntity<Object> updateAssociate(@PathVariable String associateId,@PathVariable("associateAddr") String associateAddress,@RequestHeader HttpHeaders header);
	@GetMapping(value="/associate/viewByAssociateId/{associateId}")
	public ResponseEntity<Object> viewByAssociateId(@PathVariable String associateId,@RequestHeader HttpHeaders header);

}
