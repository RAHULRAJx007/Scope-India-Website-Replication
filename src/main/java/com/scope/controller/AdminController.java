package com.scope.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private PasswordEncoder passwordEncoder;


    // ===============================
    // ADMIN DASHBOARD
    // ===============================
    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {

        List<Student> students = studentRepo.findByRole("ROLE_STUDENT");

        model.addAttribute("students", students);

        List<Announcement> announcements =
                announcementRepo.findAllByOrderByPublishedAtDesc();

        model.addAttribute("announcements", announcements);

        return "admindashboard";
    }


    // ===============================
    // POST ANNOUNCEMENT
    // ===============================
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


    // ===============================
    // DELETE ANNOUNCEMENT
    // ===============================
    @GetMapping("/admin/announcements/delete/{id}")
    public String deleteAnnouncement(@PathVariable Long id) {

        announcementRepo.deleteById(id);

        return "redirect:/admin/dashboard";
    }


    // ===============================
    // OPEN EDIT STUDENT PAGE
    // ===============================
    @GetMapping("/admin/students/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {

        Student student = studentRepo.findById(id).orElse(null);

        if (student == null) {
            return "redirect:/admin/dashboard";
        }

        model.addAttribute("student", student);

        return "edit-student";
    }


    // ===============================
    // UPDATE STUDENT DETAILS
    // ===============================
    @PostMapping("/admin/students/edit/{id}")
    public String updateStudent(@PathVariable Long id,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String phone,
                                @RequestParam String course,
                                @RequestParam String branch) {

        Student student = studentRepo.findById(id).orElse(null);

        if (student == null) {
            return "redirect:/admin/dashboard";
        }

        student.setFirstName(firstName);
        student.setLastName(lastName);
        student.setPhone(phone);
        student.setCourse(course);
        student.setBranch(branch);

        studentRepo.save(student);

        return "redirect:/admin/dashboard";
    }


    // ===============================
    // RESET PASSWORD
    // ===============================
    @PostMapping("/admin/students/reset-password/{id}")
    public String resetStudentPassword(
            @PathVariable Long id,
            @RequestParam String password) {

        Student student = studentRepo.findById(id).orElse(null);

        if (student == null) {
            return "redirect:/admin/dashboard";
        }

        student.setPassword(passwordEncoder.encode(password));

        studentRepo.save(student);

        return "redirect:/admin/dashboard";
    }

}