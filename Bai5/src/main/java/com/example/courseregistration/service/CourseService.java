package com.example.courseregistration.service;

import com.example.courseregistration.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {

    Page<Course> listCourses(String keyword, Pageable pageable);

    Course getCourse(Long id);

    Course saveCourse(Course course);

    void deleteCourse(Long id);
}

