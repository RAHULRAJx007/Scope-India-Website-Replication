package com.scope.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.scope.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    // Find student by login email
    Student findByEmail(String email);

    // Find student by password reset token
    Student findByResetToken(String token);

    // Get all students (not admins)
    List<Student> findByRole(String role);

}