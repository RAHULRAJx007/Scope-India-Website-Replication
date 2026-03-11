package com.scope.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scope.model.Student;
import com.scope.repository.AnnouncementRepository;
import com.scope.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/dashboard")
    public String studentDashboard(Authentication auth, Model model) {

        String email = auth.getName();

        Student student = studentRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        model.addAttribute("student", student);

        return "studentdashboard";
    }
}


