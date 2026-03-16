package com.example.courseregistration.service.impl;

import com.example.courseregistration.entity.Course;
import com.example.courseregistration.repository.CourseRepository;
import com.example.courseregistration.repository.EnrollmentRepository;
import com.example.courseregistration.service.CourseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public CourseServiceImpl(CourseRepository courseRepository,
                             EnrollmentRepository enrollmentRepository) {
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public Page<Course> listCourses(String keyword, Pageable pageable) {
        if (pageable == null) {
            // admin list view không phân trang, trả tất cả
            var list = (keyword == null || keyword.isBlank())
                    ? courseRepository.findAll()
                    : courseRepository.findByNameContainingIgnoreCase(keyword, Pageable.unpaged()).getContent();
            return new PageImpl<>(list);
        }
        if (keyword == null || keyword.isBlank()) {
            return courseRepository.findAll(pageable);
        }
        return courseRepository.findByNameContainingIgnoreCase(keyword, pageable);
    }

    @Override
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found"));
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = getCourse(id);
        enrollmentRepository.deleteByCourse(course);
        courseRepository.delete(course);
    }
}
