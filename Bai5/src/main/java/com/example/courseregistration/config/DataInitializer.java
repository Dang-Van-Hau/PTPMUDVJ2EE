package com.example.courseregistration.config;

import com.example.courseregistration.entity.Student;
import com.example.courseregistration.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDefaultUsers(StudentRepository studentRepository,
                                              PasswordEncoder passwordEncoder) {
        return args -> {
            studentRepository.findByUsername("admin").ifPresent(admin -> {
                if (admin.getPassword() == null || admin.getPassword().isBlank()) {
                    admin.setPassword(passwordEncoder.encode("admin123"));
                    studentRepository.save(admin);
                }
            });

            studentRepository.findByUsername("student").ifPresent(student -> {
                if (student.getPassword() == null || student.getPassword().isBlank()) {
                    student.setPassword(passwordEncoder.encode("student123"));
                    studentRepository.save(student);
                }
            });
        };
    }
}

