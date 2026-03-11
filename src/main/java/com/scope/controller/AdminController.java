package com.scope.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Authentication auth, Model model) {

        model.addAttribute("adminEmail", auth.getName());

        return "admindashboard";
    }
}
