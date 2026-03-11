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

	    @GetMapping("/login")
	    public String loginPage() {
	        return "login";
	    }

	}
