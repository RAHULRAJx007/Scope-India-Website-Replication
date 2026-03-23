package com.scope.controller;

import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;
import com.scope.service.EmailService;

@Controller
public class AuthController {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthController(StudentRepository studentRepository,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService) {

        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    // ===============================
    // REGISTER / SIGNUP
    // ===============================
    @PostMapping("/signup")
    public String registerStudent(@ModelAttribute Student student) {

        student.setPassword(passwordEncoder.encode(student.getPassword()));

        student.setRole("ROLE_STUDENT");

        student.setEmailVerified(false);

        studentRepository.save(student);

        return "redirect:/login";
    }


    // ===============================
    // SHOW FORGOT PASSWORD PAGE
    // ===============================
    @GetMapping("/forgot-password")
    public String showForgotPasswordPage() {
        return "forgot-password";
    }


    // ===============================
    // SEND RESET EMAIL
    // ===============================
    @PostMapping("/forgot-password")
    public String processForgotPassword(@RequestParam String email) {

        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            return "redirect:/login?error";
        }

        String token = UUID.randomUUID().toString();

        student.setResetToken(token);

        studentRepository.save(student);

        String resetLink = "http://localhost:8080/reset-password?token=" + token;

        emailService.sendPasswordResetEmail(email, resetLink);

        return "redirect:/login?resetSent";
    }


    // ===============================
    // OPEN RESET PASSWORD PAGE
    // ===============================
    @GetMapping("/reset-password")
    public String showResetPasswordPage(@RequestParam String token, Model model) {

        Student student = studentRepository.findByResetToken(token);

        if (student == null) {
            return "redirect:/login?invalidToken";
        }

        model.addAttribute("token", token);

        return "reset-password";
    }


    // ===============================
    // SAVE NEW PASSWORD
    // ===============================
    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String token,
                                       @RequestParam String password) {

        Student student = studentRepository.findByResetToken(token);

        if (student == null) {
            return "redirect:/login?invalidToken";
        }

        student.setPassword(passwordEncoder.encode(password));

        student.setResetToken(null);

        studentRepository.save(student);

        return "redirect:/login?resetSuccess";
    }

}