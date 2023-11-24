package com.cms.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cms.exception.CourseInvalidException;

import com.cms.model.Course;

import com.cms.repository.CourseRepository;




@Service
public class CourseServiceImpl implements ICourseService{
	
	@Autowired
    private CourseRepository courseRepository;
	@Autowired
	SequenceGeneratorService sequenceGen;
	
	@Override
	public Course addCourse(Course cObj) throws CourseInvalidException  {
       List<Course> allAssociate=courseRepository.findAll();
		
		if(cObj.getCourseId()!=null) {
			for(Course c:allAssociate) {
				if(cObj.getCourseId().equals(c.getCourseId())) {
					throw new CourseInvalidException("CourseId already exists");
				}
			}
		}
		cObj.setCourseId(sequenceGen.generateSequence(cObj.SEQUENCE_NAME));
         courseRepository.save(cObj);
        return cObj;
	}

	@Override
	public Course updateCourse(String courseId, Integer fees) throws CourseInvalidException {
		List<Course> allCourse=courseRepository.findAll();
		Course course=null;
		for(Course c:allCourse) {
			if(c.getCourseId().equals(courseId)) {
				
				c.setFees(fees);
				courseRepository.save(c);
				course=c;
			}			
		}
		if(course==null) {
			throw new CourseInvalidException("CourseId does not exists");
		}
		return course;
		
	}

	@Override
	public Course viewByCourseId(String courseId) throws CourseInvalidException {
		List<Course> allCourse=courseRepository.findAll();
		Course course=null;
		for(Course c:allCourse) {
			if(c.getCourseId().equals(courseId)) {
				course=c;
			}
		}
		if(course==null){
			throw new CourseInvalidException("Invalid Course Id");
		}
		return course;
	}

	@Override
	public Course calculateAverageFeedbackAndUpdate(String courseId, float rating) throws CourseInvalidException {
		List<Course> allCourse=courseRepository.findAll();
		Course course=null;
		for(Course c:allCourse) {
			if(c.getCourseId().equals(courseId)) {
				float existingRating=c.getRating();
				float updateValue=(existingRating+rating)/2;
				c.setRating(updateValue);
				courseRepository.save(c);
				course=c;
			}
		}
		if(course==null) {
			throw new CourseInvalidException("CourseId does not exists");
		}
		return course;
	}

		
	public float findFeedbackRatingForCourseId(String courseId) throws CourseInvalidException {
		List<Course> allCourse=courseRepository.findAll();
		float feedback=0;
		System.out.println(feedback);
		int count=0;
		for(Course c:allCourse) {

			if(c.getCourseId().equals(courseId)) {
				count++;
				System.out.println(count);
				feedback=c.getRating();
			}
		}
		if(count==0) {
			throw new CourseInvalidException("CourseId does not exists");
		}
		return feedback;
	}

	@Override
	public Course deactivateCourse(String courseId) throws CourseInvalidException {
		List<Course> allCourse=courseRepository.findAll();
		Course course=null;
		for(Course c:allCourse) {
			if(c.getCourseId().equals(courseId)) {
				courseRepository.delete(c);
				course=c;
			}
		}
		if(course==null) {
			throw new CourseInvalidException("CourseId does not exists");
		}
		return course;
	}

	@Override
	public List<Course> viewAll() {
		return courseRepository.findAll();
		
	}
	

}
