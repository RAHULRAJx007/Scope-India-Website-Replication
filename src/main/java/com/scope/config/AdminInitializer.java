package com.scope.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner initAdmin(StudentRepository studentRepo, PasswordEncoder encoder) {
        return args -> {

            Student admin = studentRepo.findByEmail("admin@scope.com");

            if (admin == null) {

                Student newAdmin = new Student();
                newAdmin.setFirstName("Admin");
                newAdmin.setLastName("Scope");
                newAdmin.setEmail("admin@scope.com");
                newAdmin.setPassword(encoder.encode("admin123"));
                newAdmin.setRole("ROLE_ADMIN");

                studentRepo.save(newAdmin);

                System.out.println("Admin created successfully");
            }
        };
    }
}