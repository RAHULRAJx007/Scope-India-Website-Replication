package com.scope.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scope.model.Student;
import com.scope.repository.AdminRepository;
import com.scope.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private AdminRepository adminRepo;

    @PostMapping("/login")
    public String login(@RequestParam String email, 
                        @RequestParam String password, 
                        HttpSession session) {

        // Check Admin
        Admin admin = adminRepo.findByEmailAndPassword(email, password);
        if (admin != null) {
            session.setAttribute("admin", admin);
            return "redirect:/admin/dashboard";
        }

        // Check Student
        Student student = studentRepo.findByEmailAndPassword(email, password);
        if (student != null) {
            session.setAttribute("student", student);
            return "redirect:/student/dashboard";
        }

        // If both fail
        return "redirect:/login?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}