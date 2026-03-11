package com.scope.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

@Controller
public class AdminController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

    	List<Student> students = studentRepo.findByRole("ROLE_STUDENT");

        model.addAttribute("students", students);

        return "admindashboard";
    }
}
