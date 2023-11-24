
package com.cms.model;

import java.io.Serializable;



import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Course implements Serializable{
	
	private String courseId;
	
	private String courseName;
	
	private Integer fees;
	
	private Integer duration;
	
	
	private String courseType;
	private float rating;

}
