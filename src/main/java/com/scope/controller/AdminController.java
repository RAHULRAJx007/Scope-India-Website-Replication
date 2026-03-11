package com.scope.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
import org.springframework.security.core.Authentication;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

import jakarta.servlet.http.HttpSession;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private StudentRepository studentRepository;

    @GetMapping("/admindashboard")
    public String dashboard(HttpSession session, Model model){

    Admin admin = (Admin) session.getAttribute("admin");

    if(admin == null){

    return "redirect:/login";
    }

    model.addAttribute("adminName", ((Principal) admin).getName());

    return "admindashboard";
    }

}