package com.scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scope.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
	
}