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

    @PostMapping("/signup")
    public String registerStudent(@ModelAttribute Student student) {

        student.setPassword(passwordEncoder.encode(student.getPassword()));
        student.setRole("ROLE_STUDENT");

        studentRepository.save(student);

        return "redirect:/login";
    }
}