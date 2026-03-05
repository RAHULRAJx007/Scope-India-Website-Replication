package com.scope.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(StudentRepository repo, PasswordEncoder encoder) {

        return args -> {

            String adminEmail = "admin@scope.com";

            if (repo.findByEmail(adminEmail).isEmpty()) {

                Student admin = new Student();

                admin.setFirstName("Admin");
                admin.setLastName("Scope");
                admin.setEmail(adminEmail);
                admin.setPhone("9999999999");
                admin.setCourse("Administrator");

                admin.setPassword(encoder.encode("admin123"));

                admin.setRole("ROLE_ADMIN");

                repo.save(admin);

                System.out.println("Admin account created!");
            }
        };
    }
}