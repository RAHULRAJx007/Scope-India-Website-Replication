package com.scope.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.scope.model.Student;
import com.scope.repository.StudentRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        // ADMIN LOGIN
        if ("admin@scope.com".equals(email)) {
            return new User(
                    "admin@scope.com",
                    "$2a$10$3vUPRpdlDymlPrr0uSCCCOhSnSfd246tCiR2EYN/0J2Uqb0vGdzvi",
                    Collections.singletonList(
                            new SimpleGrantedAuthority("ROLE_ADMIN")
                    )
            );
        }

        // STUDENT LOGIN
        Student student = studentRepository.findByEmail(email);

        if (student == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new User(
                student.getEmail(),
                student.getPassword(),
                Collections.singletonList(
                        new SimpleGrantedAuthority(student.getRole())
                )
        );
    }
}