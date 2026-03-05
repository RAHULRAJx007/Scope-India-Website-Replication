package com.scope.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StudentController {

    @GetMapping("/studentdashboard")
    public String studentDashboard() {
        return "student-dashboard";
    }

}