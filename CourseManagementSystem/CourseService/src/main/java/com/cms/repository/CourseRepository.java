package com.cms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.cms.model.Course;
import java.util.List;


@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
       List<Course> findAll();     
}
