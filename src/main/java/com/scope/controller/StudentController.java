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

@Controller
@RequestMapping("/student")
public class StudentController {

			@Autowired
			StudentRepository studentRepo;
			
			@GetMapping("/dashboard")
			public String dashboard(Model model, Principal principal){
			
			Student student = studentRepo
			        .findByEmail(principal.getName())
			        .orElseThrow(() -> new RuntimeException("Student not found"));
			
			model.addAttribute("student", student);
			
			return "studentdashboard";
		}
}

