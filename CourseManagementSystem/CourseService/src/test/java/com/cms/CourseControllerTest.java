package com.cms;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.cms.controller.CourseController;
import com.cms.exception.CourseInvalidException;
import com.cms.model.Course;
import com.cms.proxy.AuthenticationAuthorizationProxy;
import com.cms.service.CourseServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan(basePackages = "com.cms.*")
@AutoConfigureMockMvc
@ContextConfiguration(classes = CourseServiceApplication.class)
@SpringBootTest
public class CourseControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CourseServiceImpl courseService;

	@Mock
	private AuthenticationAuthorizationProxy authProxy;

	@InjectMocks
	private CourseController courseController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(courseController).build();
	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successful
	@Test
	public void test122RestApiCallForViewByCourseId() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.viewByCourseId(courseId)).thenReturn(course);

		mockMvc.perform(get("/course/viewByCourseId/{courseId}", courseId).header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.courseId").value(courseId))
				.andExpect(jsonPath("$.courseName").value("English"));
	}

	// Test whether the end point /update/{courseId}/{fee} is successfull
	@Test
	public void test123RestApiCallForUpdateCourse() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.updateCourse(courseId, 2000)).thenReturn(course);

		mockMvc.perform(put("/course/update/{courseId}/{courseFees}", courseId, 2000).header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$.courseId").value(courseId))
				.andExpect(jsonPath("$.courseName").value("English"));

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull
	@Test
	public void test124RestApiCallForViewFeedbackRating() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.findFeedbackRatingForCourseId(courseId)).thenReturn(rating);

		mockMvc.perform(get("/course/viewFeedbackRating/{courseId}", courseId).header("Authorization", token))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0]").value(rating));

	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull
	@Test
	public void test125RestApiCallForCalculateAverageFeedback() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.calculateAverageFeedbackAndUpdate(courseId, rating)).thenReturn(course);

		mockMvc.perform(put("/course/calculateAverageFeedback/{courseId}/{rating}", courseId, rating)
				.header("Authorization", token)).andExpect(status().isOk())
				.andExpect(jsonPath("$.courseId").value(courseId)).andExpect(jsonPath("$.courseName").value("English"))
				.andExpect(jsonPath("$.fees").value(2000));
	}

	// Test whether the endpoint /addCourse is successfull
	@Test
	public void test126RestApiCallForAddCourse() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.addCourse(course)).thenReturn(course);
		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(course);
		mockMvc.perform(post("/course/addCourse")
		        .header("Authorization", token)
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(jsonBody))
		        .andExpect(status().isOk()).andDo(print());

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successful for
	// invalid id
	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidId() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.viewByCourseId(courseId)).thenThrow(new CourseInvalidException("Invalid Course Id"));

		mockMvc.perform(get("/course/viewByCourseId/{courseId}", courseId).header("Authorization", token))
				.andExpect(status().isNotFound()).andExpect(content().string("Invalid Course Id"));

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successful for
	// invalid token
	@Test
	public void test122RestApiCallForViewByCourseIdForInvalidToken() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");

		when(authProxy.isValidToken(token)).thenReturn(false);

		mockMvc.perform(get("/course/viewByCourseId/{courseId}", courseId).header("Authorization", token))
				.andExpect(status().isUnauthorized());
	}

	// Test whether the end point /update/{courseId}/{fee} is successfull for
	// invalid id
	@Test
	public void test123RestApiCallForUpdateCourseForInvalidId() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.updateCourse(courseId, 2000))
				.thenThrow(new CourseInvalidException("CourseId does not exists"));

		mockMvc.perform(put("/course/update/{courseId}/{courseFees}", courseId, 2000).header("Authorization", token))
				.andExpect(status().isNotFound()).andExpect(content().string("CourseId does not exists"));

	}

	// Test whether the end point /update/{courseId}/{fee} is successfull for
	// invalid token
	@Test
	public void test123RestApiCallForUpdateCourseForInvalidToken() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);

		when(authProxy.isValidToken(token)).thenReturn(false);

		mockMvc.perform(put("/course/update/{courseId}/{courseFees}", courseId, 2000).header("Authorization", token))
				.andExpect(status().isUnauthorized());

	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull for
	// invalid id
	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidId() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.findFeedbackRatingForCourseId(courseId))
				.thenThrow(new CourseInvalidException("CourseId does not exists"));

		mockMvc.perform(get("/course/viewFeedbackRating/{courseId}", courseId).header("Authorization", token))
				.andExpect(status().isNotFound()).andExpect(content().string("CourseId does not exists"));
	}

	// Test whether the endpoint /viewByCourseId/{courseId} is successfull for
	// invalid token
	@Test
	public void test124RestApiCallForViewFeedbackRatingForInvalidToken() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);

		when(authProxy.isValidToken(token)).thenReturn(false);

		mockMvc.perform(get("/course/viewFeedbackRating/{courseId}", courseId).header("Authorization", token))
				.andExpect(status().isUnauthorized());

	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull for invalid id
	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidId() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);

		when(authProxy.isValidToken(token)).thenReturn(true);
		when(courseService.calculateAverageFeedbackAndUpdate(courseId, rating))
				.thenThrow(new CourseInvalidException("CourseId does not exists"));

		mockMvc.perform(put("/course/calculateAverageFeedback/{courseId}/{rating}", courseId, rating)
				.header("Authorization", token)).andExpect(status().isNotFound())
				.andExpect(content().string("CourseId does not exists"));
	}

	// Test whether the endpoint /calculateAverageFeedback/{courseId}/{rating} is
	// successfull for invalid token
	@Test
	public void test125RestApiCallForCalculateAverageFeedbackForInvalidToken() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);
		when(authProxy.isValidToken(token)).thenReturn(false);
		mockMvc.perform(put("/course/calculateAverageFeedback/{courseId}/{rating}", courseId, rating)
				.header("Authorization", token)).andExpect(status().isUnauthorized());

	}

	// Test whether the endpoint /addCourse is successfull for invalid token
	@Test
	public void test126RestApiCallForAddCourseForInvalidToken() throws Exception {
		String token = "sample_token";
		String courseId = "140";
		float rating = 2;
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("English");
		course.setFees(2000);
		course.setRating(rating);

		when(authProxy.isValidToken(token)).thenReturn(false);

		ObjectMapper mapper = new ObjectMapper();
		String jsonBody = mapper.writeValueAsString(course);
		mockMvc.perform(post("/course/addCourse").header("Authorization", token).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());

	}

}
