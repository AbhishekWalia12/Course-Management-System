package com.cms.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;



@FeignClient(name="admissionapp",url="http://localhost:9093")
public interface AdmissionProxy {
	@DeleteMapping("/admission/deactivate/{courseId}")
	boolean deactiveAdmission(@PathVariable String courseId,@RequestHeader HttpHeaders header);
}
