package com.cms.model;

import java.io.Serializable;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Associate implements Serializable{
	
	private String associateId;	
	private String associateName;	
	private String associateAddress;	
	private String associateEmailId;


}
