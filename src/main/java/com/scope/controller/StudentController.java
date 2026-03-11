package com.scope.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scope.model.Announcement;
import com.scope.model.Student;
import com.scope.repository.AnnouncementRepository;
import com.scope.repository.StudentRepository;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;
    
    @Autowired
    private AnnouncementRepository announcementRepo;


    @GetMapping("/dashboard")
    public String studentDashboard(Authentication auth, Model model) {

        String email = auth.getName();

        Student student = studentRepo.findByEmail(email);

        if (student == null) {
            return "redirect:/login";
        }

        model.addAttribute("student", student);
        
        List<Announcement> announcements =
                announcementRepo.findAllByOrderByPublishedAtDesc();

        model.addAttribute("announcements", announcements);


        return "studentdashboard";
    }
}
