package com.cms.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Document
public class Associate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String SEQUENCE_NAME = "associate_sequence";
	@Id
	private String associateId;	
	private String associateName;	
	private String associateAddress;	
	private String associateEmailId;

}
