package com.cms.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class Admission implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Transient
    public static final String SEQUENCE_NAME = "admission_sequence";
    
	@Id
	private long registrationId;	
	private String courseId;
	private String associateId	;
	private int fees;	
	private String feedback	;
	
}




