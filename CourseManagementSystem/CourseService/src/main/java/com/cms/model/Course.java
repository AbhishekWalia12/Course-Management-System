
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
public class Course implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Transient
	public static final String SEQUENCE_NAME = "course_sequence";
    @Id
	private String courseId;
	private String courseName;
	private Integer fees;
	private Integer duration;
	private String courseType;
	private float rating;
}
