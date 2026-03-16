package com.scope.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String email, String otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(email);
        helper.setSubject("Email Verification — Scope India");
        helper.setText(buildOtpEmail(otp), true); // true = isHtml

        mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send OTP email to: " + email, e);
        }
    }

    public void sendPasswordResetEmail(String email, String resetLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(email);
            helper.setSubject("Password Reset — Scope India");
            helper.setText(buildResetEmail(resetLink), true);

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send reset email to: " + email, e);
        }
    }

    private String buildResetEmail(String resetLink) {
        return "<!DOCTYPE html>" +
        "<html lang='en'><head><meta charset='UTF-8'>" +
        "<meta name='viewport' content='width=device-width,initial-scale=1.0'>" +
        "<title>Password Reset</title></head>" +
        "<body style='margin:0;padding:0;background:#060e30;font-family:Arial,sans-serif;'>" +

        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='background:#060e30;padding:40px 16px;'>" +
        "<tr><td align='center'>" +

        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='max-width:520px;background:#030a28;border-radius:16px;border:1px solid rgba(245,196,0,0.2);overflow:hidden;'>" +

        // Header
        "<tr><td style='background:linear-gradient(135deg,#0d1f5c,#030a28);padding:28px 36px;border-bottom:1px solid rgba(245,196,0,0.12);'>" +
        "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr>" +
        "<td><span style='font-family:Arial,sans-serif;font-weight:900;font-size:20px;color:#ffffff;letter-spacing:1px;'>SCOPE</span>" +
        "<span style='font-family:Arial,sans-serif;font-weight:900;font-size:20px;color:#f5c400;letter-spacing:1px;'>INDIA</span></td>" +
        "<td align='right'><span style='font-size:11px;color:rgba(255,255,255,0.35);letter-spacing:0.5px;'>IT TRAINING INSTITUTE</span></td>" +
        "</tr></table>" +
        "</td></tr>" +

        // Body
        "<tr><td style='padding:36px 36px 28px;'>" +

        // Icon
        "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td align='center' style='padding-bottom:24px;'>" +
        "<div style='width:64px;height:64px;background:rgba(245,196,0,0.1);border:1px solid rgba(245,196,0,0.25);border-radius:14px;display:inline-block;text-align:center;line-height:64px;font-size:28px;'>&#x1F510;</div>" +
        "</td></tr></table>" +

        // Title + subtitle
        "<h1 style='margin:0 0 10px;font-size:22px;font-weight:900;color:#ffffff;text-align:center;font-family:Arial,sans-serif;'>Reset Your Password</h1>" +
        "<p style='margin:0 0 28px;font-size:14px;color:rgba(255,255,255,0.45);text-align:center;line-height:1.6;font-family:Arial,sans-serif;'>We received a request to reset your password. Click the button below to choose a new one.</p>" +

        // Reset button
        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='margin-bottom:24px;'><tr><td align='center'>" +
        "<a href='" + resetLink + "' style='display:inline-block;background:linear-gradient(135deg,#f5c400,#d4a800);color:#030a28;font-family:Arial,sans-serif;font-weight:900;font-size:15px;text-decoration:none;padding:14px 36px;border-radius:10px;letter-spacing:0.3px;'>Reset Password</a>" +
        "</td></tr></table>" +

        // Link fallback
        "<p style='margin:0 0 8px;font-size:12px;color:rgba(255,255,255,0.3);text-align:center;font-family:Arial,sans-serif;'>Or copy this link into your browser:</p>" +
        "<p style='margin:0 0 24px;font-size:11px;color:rgba(245,196,0,0.6);text-align:center;word-break:break-all;font-family:Arial,sans-serif;'>" + resetLink + "</p>" +

        // Expiry note
        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='margin-bottom:24px;'><tr><td align='center'>" +
        "<div style='display:inline-block;background:rgba(245,98,90,0.08);border:1px solid rgba(245,98,90,0.2);border-radius:8px;padding:10px 18px;'>" +
        "<p style='margin:0;font-size:12px;color:rgba(245,98,90,0.85);font-family:Arial,sans-serif;'>&#x23F0; This link expires in <strong>30 minutes</strong></p>" +
        "</div>" +
        "</td></tr></table>" +

        // Warning
        "<p style='margin:0;font-size:12px;color:rgba(255,255,255,0.25);text-align:center;line-height:1.6;font-family:Arial,sans-serif;'>" +
        "If you didn't request a password reset, you can safely ignore this email.<br>Your password will not be changed." +
        "</p>" +

        "</td></tr>" +

        // Footer
        "<tr><td style='padding:18px 36px;border-top:1px solid rgba(245,196,0,0.08);background:rgba(0,0,0,0.2);'>" +
        "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr>" +
        "<td><p style='margin:0;font-size:11px;color:rgba(255,255,255,0.2);font-family:Arial,sans-serif;'>&#169; 2025 Scope India IT Training Institute</p></td>" +
        "<td align='right'><p style='margin:0;font-size:11px;color:rgba(255,255,255,0.2);font-family:Arial,sans-serif;'>Do not reply to this email</p></td>" +
        "</tr></table>" +
        "</td></tr>" +

        "</table>" +
        "</td></tr></table>" +
        "</body></html>";
    }

    private String buildOtpEmail(String otp) {
        return "<!DOCTYPE html>" +
        "<html lang='en'><head><meta charset='UTF-8'>" +
        "<meta name='viewport' content='width=device-width,initial-scale=1.0'>" +
        "<title>Email Verification</title></head>" +
        "<body style='margin:0;padding:0;background:#060e30;font-family:Arial,sans-serif;'>" +

        // Outer wrapper
        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='background:#060e30;padding:40px 16px;'>" +
        "<tr><td align='center'>" +

        // Card
        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='max-width:520px;background:#030a28;border-radius:16px;border:1px solid rgba(245,196,0,0.2);overflow:hidden;'>" +

        // Header bar
        "<tr><td style='background:linear-gradient(135deg,#0d1f5c,#030a28);padding:28px 36px;border-bottom:1px solid rgba(245,196,0,0.12);'>" +
        "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr>" +
        "<td><span style='font-family:Arial,sans-serif;font-weight:900;font-size:20px;color:#ffffff;letter-spacing:1px;'>SCOPE</span>" +
        "<span style='font-family:Arial,sans-serif;font-weight:900;font-size:20px;color:#f5c400;letter-spacing:1px;'>INDIA</span></td>" +
        "<td align='right'><span style='font-size:11px;color:rgba(255,255,255,0.35);letter-spacing:0.5px;'>IT TRAINING INSTITUTE</span></td>" +
        "</tr></table>" +
        "</td></tr>" +

        // Body
        "<tr><td style='padding:36px 36px 28px;'>" +

        // Icon
        "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr><td align='center' style='padding-bottom:24px;'>" +
        "<div style='width:64px;height:64px;background:rgba(245,196,0,0.1);border:1px solid rgba(245,196,0,0.25);border-radius:14px;display:inline-block;text-align:center;line-height:64px;font-size:28px;'>&#x2709;</div>" +
        "</td></tr></table>" +

        // Title
        "<h1 style='margin:0 0 10px;font-size:22px;font-weight:900;color:#ffffff;text-align:center;font-family:Arial,sans-serif;'>Verify Your Email</h1>" +
        "<p style='margin:0 0 28px;font-size:14px;color:rgba(255,255,255,0.45);text-align:center;line-height:1.6;font-family:Arial,sans-serif;'>Use the OTP below to verify your email address and activate your Scope India account.</p>" +

        // OTP box
        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='margin-bottom:28px;'><tr><td align='center'>" +
        "<div style='display:inline-block;background:rgba(245,196,0,0.08);border:2px solid rgba(245,196,0,0.3);border-radius:14px;padding:20px 40px;'>" +
        "<p style='margin:0 0 6px;font-size:11px;font-weight:700;color:rgba(255,255,255,0.35);letter-spacing:1.5px;text-transform:uppercase;font-family:Arial,sans-serif;'>Your OTP</p>" +
        "<p style='margin:0;font-size:36px;font-weight:900;color:#f5c400;letter-spacing:10px;font-family:Arial,sans-serif;'>" + otp + "</p>" +
        "</div>" +
        "</td></tr></table>" +

        // Expiry note
        "<table width='100%' cellpadding='0' cellspacing='0' border='0' style='margin-bottom:24px;'><tr><td align='center'>" +
        "<div style='display:inline-block;background:rgba(245,98,90,0.08);border:1px solid rgba(245,98,90,0.2);border-radius:8px;padding:10px 18px;'>" +
        "<p style='margin:0;font-size:12px;color:rgba(245,98,90,0.85);font-family:Arial,sans-serif;'>&#x23F0; This OTP expires in <strong>10 minutes</strong></p>" +
        "</div>" +
        "</td></tr></table>" +

        // Warning
        "<p style='margin:0;font-size:12px;color:rgba(255,255,255,0.25);text-align:center;line-height:1.6;font-family:Arial,sans-serif;'>" +
        "If you didn't request this, please ignore this email.<br>Do not share this OTP with anyone." +
        "</p>" +

        "</td></tr>" +

        // Footer
        "<tr><td style='padding:18px 36px;border-top:1px solid rgba(245,196,0,0.08);background:rgba(0,0,0,0.2);'>" +
        "<table width='100%' cellpadding='0' cellspacing='0' border='0'><tr>" +
        "<td><p style='margin:0;font-size:11px;color:rgba(255,255,255,0.2);font-family:Arial,sans-serif;'>&#169; 2025 Scope India IT Training Institute</p></td>" +
        "<td align='right'><p style='margin:0;font-size:11px;color:rgba(255,255,255,0.2);font-family:Arial,sans-serif;'>Do not reply to this email</p></td>" +
        "</tr></table>" +
        "</td></tr>" +

        "</table>" + // end card
        "</td></tr></table>" + // end outer
        "</body></html>";
    }
}