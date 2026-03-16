package com.example.courseregistration.service;

import com.example.courseregistration.entity.Student;

public interface StudentService {

    Student registerStudent(String username, String rawPassword, String email);

    Student findByUsername(String username);

    Student findOrCreateGoogleStudent(String email, String name);
}

