package com.scope.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

@Controller
public class AuthController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(StudentRepository studentRepository,
                          PasswordEncoder passwordEncoder) {

        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ===============================
    // REGISTER / SIGNUP
    // ===============================
    @PostMapping("/signup")
    public String registerStudent(@ModelAttribute Student student) {

        // Encrypt password
        student.setPassword(passwordEncoder.encode(student.getPassword()));

        // Set role
        student.setRole("ROLE_STUDENT");

        // Email not verified initially
        student.setEmailVerified(false);

        // Save student
        studentRepository.save(student);

        return "redirect:/login";
    }

}