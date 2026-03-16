package com.example.courseregistration.service.impl;

import com.example.courseregistration.entity.Role;
import com.example.courseregistration.entity.Student;
import com.example.courseregistration.repository.RoleRepository;
import com.example.courseregistration.repository.StudentRepository;
import com.example.courseregistration.service.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public StudentServiceImpl(StudentRepository studentRepository,
                              RoleRepository roleRepository,
                              PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Student registerStudent(String username, String rawPassword, String email) {
        Student student = new Student();
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode(rawPassword));
        student.setEmail(email);

        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("STUDENT");
                    return roleRepository.save(role);
                });

        student.setRoles(new HashSet<>());
        student.getRoles().add(studentRole);

        return studentRepository.save(student);
    }

    @Override
    public Student findByUsername(String username) {
        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Student not found"));
    }

    @Override
    public Student findOrCreateGoogleStudent(String email, String name) {
        Optional<Student> existing = studentRepository.findByEmail(email);
        if (existing.isPresent()) {
            return existing.get();
        }
        Student student = new Student();
        student.setEmail(email);
        String baseUsername = email != null ? email.split("@")[0] : name.replaceAll("\\s+", "").toLowerCase();
        String username = baseUsername;
        int counter = 1;
        while (studentRepository.findByUsername(username).isPresent()) {
            username = baseUsername + counter;
            counter++;
        }
        student.setUsername(username);
        student.setPassword(passwordEncoder.encode("oauth2-login"));

        Role studentRole = roleRepository.findByName("STUDENT")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("STUDENT");
                    return roleRepository.save(role);
                });
        student.setRoles(new HashSet<>());
        student.getRoles().add(studentRole);

        return studentRepository.save(student);
    }
}
