package com.example.courseregistration.service;

import com.example.courseregistration.entity.Enrollment;
import com.example.courseregistration.entity.Student;

import java.util.List;

public interface EnrollmentService {

    Enrollment enroll(Student student, Long courseId);

    List<Enrollment> findByStudent(Student student);
}

