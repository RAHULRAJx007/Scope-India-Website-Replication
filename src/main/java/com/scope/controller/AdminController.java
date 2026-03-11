package com.scope.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.scope.model.Announcement;
import com.scope.model.Student;
import com.scope.repository.AnnouncementRepository;
import com.scope.repository.StudentRepository;

@Controller
public class AdminController {

    @Autowired
    private StudentRepository studentRepo;
    
    @Autowired
    private AnnouncementRepository announcementRepo;


    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

    	List<Student> students = studentRepo.findByRole("ROLE_STUDENT");

        model.addAttribute("students", students);
        
        List<Announcement> announcements =
                announcementRepo.findAllByOrderByPublishedAtDesc();

        model.addAttribute("announcements", announcements);

        return "admindashboard";
    }
    
    @PostMapping("/admin/announcements/post")
    public String postAnnouncement(
            @RequestParam String title,
            @RequestParam String body,
            @RequestParam String category,
            @RequestParam String target) {

        Announcement a = new Announcement();

        a.setTitle(title);
        a.setBody(body);
        a.setCategory(category);
        a.setTarget(target);
        a.setPublishedAt(LocalDateTime.now());

        announcementRepo.save(a);

        return "redirect:/admin/dashboard";
    }
    
    @GetMapping("/admin/announcements/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id) {

        announcementRepo.deleteById(id);

        return "redirect:/admin/dashboard";
    }


}
