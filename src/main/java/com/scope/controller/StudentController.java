package com.scope.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.scope.model.Announcement;
import com.scope.model.Student;
import com.scope.repository.AnnouncementRepository;
import com.scope.repository.StudentRepository;
import com.scope.service.EmailService;
import com.scope.util.OtpGenerator;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentRepository studentRepo;

    @Autowired
    private AnnouncementRepository announcementRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Map<String, String> otpStorage = new HashMap<>();


    // ===============================
    // STUDENT DASHBOARD
    // ===============================
    @GetMapping("/dashboard")
    public String studentDashboard(Authentication auth, Model model) {

        String email = auth.getName();

        Student student = studentRepo.findByEmail(email);

        if (student == null) {
            return "redirect:/login";
        }

        if (student.getEmailVerified() == null || !student.getEmailVerified()) {
            model.addAttribute("email", email);
            return "verify-email";
        }

        model.addAttribute("student", student);

        List<Announcement> announcements =
                announcementRepo.findAllByOrderByPublishedAtDesc();

        model.addAttribute("announcements", announcements);

        return "studentdashboard";
    }


    // ===============================
    // SEND OTP
    // ===============================
    @PostMapping("/send-otp")
    @ResponseBody
    public String sendOtp(Authentication auth) {

        String email = auth.getName();

        String otp = OtpGenerator.generateOtp();

        otpStorage.put(email, otp);

        try {
            emailService.sendOtpEmail(email, otp);
            return "OTP sent successfully to " + email;
        } catch (Exception e) {
            otpStorage.remove(email);
            return "Failed to send OTP. Please try again.";
        }
    }


    // ===============================
    // VERIFY OTP
    // ===============================
    @PostMapping("/verify-otp")
    public String verifyOtp(@RequestParam String otp,
                            Authentication auth,
                            Model model) {

        String email = auth.getName();

        String storedOtp = otpStorage.get(email);

        if (storedOtp != null && storedOtp.equals(otp)) {

            Student student = studentRepo.findByEmail(email);

            student.setEmailVerified(true);

            studentRepo.save(student);

            otpStorage.remove(email);

            return "redirect:/student/dashboard";
        }

        model.addAttribute("error", "Invalid OTP. Please try again.");

        return "verify-email";
    }


    // ===============================
    // CHANGE PASSWORD
    // ===============================
    @PostMapping("/profile/change-password")
    public String changePassword(Authentication auth,
                                 @RequestParam String currentPassword,
                                 @RequestParam String newPassword,
                                 Model model) {

        String email = auth.getName();

        Student student = studentRepo.findByEmail(email);

        if (student == null) {
            return "redirect:/login";
        }

        // Check current password
        if (!passwordEncoder.matches(currentPassword, student.getPassword())) {

            model.addAttribute("error", "Current password is incorrect");

            return "redirect:/student/dashboard?passwordError";
        }

        // Encode and save new password
        student.setPassword(passwordEncoder.encode(newPassword));

        studentRepo.save(student);

        return "redirect:/student/dashboard?passwordChanged";
    }

}