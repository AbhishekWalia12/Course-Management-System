package com.cms.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class Admission {
	
	private long registrationId;	
	private String courseId	;
	private String associateId	;
	private int fees;	
	private String feedback	;
	
}




