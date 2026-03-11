package com.scope.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.scope.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Student findByEmail(String email);

}
