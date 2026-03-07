package com.scope.repository;

import java.util.List;

import com.scope.model.Enrollment;

public interface EnrollmentRepository {

	Object findTop5ByOrderByEnrolledAtDesc();

	List<Enrollment> findByStatus(String string);

	Object countByStatus(String string);

}
