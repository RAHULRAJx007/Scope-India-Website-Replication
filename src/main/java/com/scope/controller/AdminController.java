package com.scope.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/dashboard")
    public String adminDashboard(Model model){

        model.addAttribute("students", studentRepository.findAll());
        model.addAttribute("totalStudents", studentRepository.count());

        return "admindashboard";
    }

}