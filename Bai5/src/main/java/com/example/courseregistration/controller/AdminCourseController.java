package com.example.courseregistration.controller;

import com.example.courseregistration.entity.Category;
import com.example.courseregistration.entity.Course;
import com.example.courseregistration.service.CategoryService;
import com.example.courseregistration.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin/courses")
public class AdminCourseController {

    private final CourseService courseService;
    private final CategoryService categoryService;

    public AdminCourseController(CourseService courseService, CategoryService categoryService) {
        this.courseService = courseService;
        this.categoryService = categoryService;
    }

    @ModelAttribute("categories")
    public List<Category> categories() {
        return categoryService.findAll();
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("courses", courseService.listCourses(null, null).getContent());
        return "admin/admin-course-list";
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("course", new Course());
        return "admin/admin-course-form";
    }

    @PostMapping("/create")
    public String create(
            @ModelAttribute("course") @Valid Course course,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/admin-course-form";
        }
        courseService.saveCourse(course);
        return "redirect:/admin/courses?success";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "admin/admin-course-form";
    }

    @PostMapping("/edit/{id}")
    public String edit(
            @PathVariable Long id,
            @ModelAttribute("course") @Valid Course form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "admin/admin-course-form";
        }
        Course course = courseService.getCourse(id);
        course.setName(form.getName());
        course.setCredits(form.getCredits());
        course.setLecturer(form.getLecturer());
        course.setImage(form.getImage());
        course.setCategory(form.getCategory());
        courseService.saveCourse(course);
        return "redirect:/admin/courses?updated";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return "redirect:/admin/courses?deleted";
    }
}

