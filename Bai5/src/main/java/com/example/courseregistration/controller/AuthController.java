package com.example.courseregistration.controller;

import com.example.courseregistration.dto.StudentRegistrationDto;
import com.example.courseregistration.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private final StudentService studentService;

    public AuthController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/login")
    public String login(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/home";
        }
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("student", new StudentRegistrationDto());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("student") @Valid StudentRegistrationDto form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        studentService.registerStudent(form.getUsername(), form.getPassword(), form.getEmail());
        return "redirect:/login?registered";
    }
}

