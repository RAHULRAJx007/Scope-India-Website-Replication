package com.scope.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

@Controller
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/studentdashboard")
    public String studentDashboard(Authentication authentication, Model model) {

        String email = authentication.getName();

        Student student = studentRepository.findByEmail(email).orElse(null);

        model.addAttribute("student", student);

        return "studentdashboard";
    }
}