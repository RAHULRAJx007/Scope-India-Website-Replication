package com.scope.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.scope.model.Student;

public interface AdminRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

}