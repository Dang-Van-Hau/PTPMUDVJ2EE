package com.example.courseregistration.security;

import com.example.courseregistration.entity.Role;
import com.example.courseregistration.entity.Student;
import com.example.courseregistration.repository.StudentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Student student = studentRepository.findByUsername(username)
                .orElseGet(() -> studentRepository.findByEmail(username)
                        .orElseThrow(() -> new UsernameNotFoundException("User not found")));

        Set<GrantedAuthority> authorities = student.getRoles()
                .stream()
                .map(Role::getName)
                .map(roleName -> roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new User(student.getUsername(), student.getPassword(), authorities);
    }
}

