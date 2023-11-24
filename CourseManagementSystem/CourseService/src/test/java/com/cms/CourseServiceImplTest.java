package com.cms;





import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.repository.CourseRepository;
import com.cms.service.CourseServiceImpl;
import com.cms.service.SequenceGeneratorService;




//Write Unit Tests for the methods in the CourseServiceImpl
@SpringBootTest(classes= {CourseServiceImplTest.class})
public class CourseServiceImplTest {
	@Mock
	CourseRepository courseRepo;
	@InjectMocks
	CourseServiceImpl courseService;
	@Mock
	SequenceGeneratorService cs;
	
	@Test
	public void test127AddCourse() throws CourseInvalidException  {
		Course course=new Course();
		course.setCourseId(null);
		List<Course> l1=new ArrayList<>();
		 l1.add(course);
	        when(courseRepo.findAll()).thenReturn(l1);
			 when(cs.generateSequence(course.SEQUENCE_NAME)).thenReturn("201");
			 assertEquals("201", courseService.addCourse(course).getCourseId());		
	}
	
//check whether viewByCourseId returns the course for the given courseId	
	@Test
	public void test128ViewByCourseId() throws CourseInvalidException {
		Course c=new Course();
		c.setCourseId("200");
		c.setCourseName("English");
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		when(courseRepo.findAll()).thenReturn(l1);
		assertEquals("English", courseService.viewByCourseId("200").getCourseName());
		
	}
	
//check whether the findFeedbackRatingForCourseId	returns the feedback rating for the given courseId
	public void test129FindFeedbackRatingForCourseId() {
		
	}
	
	//check whether updateCourse updates the fees for the given courseId in the database
	@Test
	public void test130UpdateCourse() throws CourseInvalidException {
		Course c=new Course();
		c.setCourseId("200");
		c.setFees(2000);
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		String courseId="200";
		int fees=2000;
		when(courseRepo.findAll()).thenReturn(l1);
		assertEquals(2000, courseService.updateCourse(courseId, fees).getFees());	
	}
	
//check whether the calculateAverageFeedbackAndUpdate calculates the average feedback rating with the given rating and existing rating in the database for the given courseId and updates in the database	
	@Test
	public void test131CalculateAverageFeedbackAndUpdate() throws CourseInvalidException {
		Course c=new Course();
		c.setCourseId("200");
		c.setRating(2);
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		int rating=4;
		when(courseRepo.findAll()).thenReturn(l1);
		assertEquals(3, courseService.calculateAverageFeedbackAndUpdate("200", rating).getRating());
	}
	
//check whether the deactivateCourse removes the course of the given courseId in the database
	@Test
	public void test132DeactivateCourse() throws CourseInvalidException {
		Course c=new Course();
		c.setCourseId("200");
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		when(courseRepo.findAll()).thenReturn(l1);
		assertEquals("200", courseService.deactivateCourse("200").getCourseId());
	}
	
//check whether viewByCourseId throws CourseInvalidException when an invalid courseid is passed	
	@Test
	public void test133ViewByCourseIdForInvalidId() {
		Course c=new Course();
		c.setCourseId("200");
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		when(courseRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(CourseInvalidException.class, ()->courseService.viewByCourseId("201"));
		assertEquals("Invalid Course Id", exception.getMessage());
		
	}
	
	
	//check whether updateCourse throws CourseInvalidException for invalid course id
	@Test
	public void test135updateCourseInvalidId() {
		Course c=new Course();
		c.setCourseId("200");
		c.setFees(2000);
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		when(courseRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(CourseInvalidException.class, ()->courseService.updateCourse("201", 4000));
		assertEquals("CourseId does not exists", exception.getMessage());
		
	}
	
	//check whether calculateAverageFeedbackAndUpdate throws CourseInvalidExcception for invalid course id
	@Test
	public void test136calculateAverageFeedbackAndUpdateForInvalidId() {
		Course c=new Course();
		c.setCourseId("200");
		c.setRating(4);
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		when(courseRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(CourseInvalidException.class, ()->courseService.calculateAverageFeedbackAndUpdate("201", 8));
		assertEquals("CourseId does not exists", exception.getMessage());
		
		
	}
	//check whether findFeedbackRating throws CourseInvalidExcception for invalid course id
	@Test
	public void test137findFeedbackRatingForCourseIdForInvalidId() {
		Course c=new Course();
		c.setCourseId("200");
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		when(courseRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(CourseInvalidException.class, ()->courseService.findFeedbackRatingForCourseId("201"));
		assertEquals("CourseId does not exists", exception.getMessage());
		
	}
	
	//check whether deactivateCourse throws CourseInvalidExcception for invalid course id
	@Test
	public void test138deactivateCourseForInvalidId() {
		Course c=new Course();
		c.setCourseId("200");
		List<Course> l1=new ArrayList<>();
		l1.add(c);
		when(courseRepo.findAll()).thenReturn(l1);
		Exception exception=assertThrows(CourseInvalidException.class, ()->courseService.deactivateCourse("201"));
		assertEquals("CourseId does not exists", exception.getMessage());
		
	}
	
	

}
