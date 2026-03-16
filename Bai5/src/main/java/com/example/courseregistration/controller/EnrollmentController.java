package com.example.courseregistration.controller;

import com.example.courseregistration.entity.Enrollment;
import com.example.courseregistration.entity.Student;
import com.example.courseregistration.service.EnrollmentService;
import com.example.courseregistration.service.StudentService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;

    public EnrollmentController(EnrollmentService enrollmentService, StudentService studentService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
    }

    @PostMapping("/enroll/{courseId}")
    public String enroll(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User user
    ) {
        if (user == null) {
            return "redirect:/login";
        }
        Student student = studentService.findByUsername(user.getUsername());
        enrollmentService.enroll(student, courseId);
        return "redirect:/home?enrolled";
    }

    @GetMapping("/my-courses")
    public String myCourses(
            @AuthenticationPrincipal User user,
            Model model
    ) {
        Student student = studentService.findByUsername(user.getUsername());
        List<Enrollment> enrollments = enrollmentService.findByStudent(student);
        model.addAttribute("enrollments", enrollments);
        return "my-courses";
    }
}

