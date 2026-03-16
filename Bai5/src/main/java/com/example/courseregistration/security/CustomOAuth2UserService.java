package com.example.courseregistration.security;

import com.example.courseregistration.entity.Role;
import com.example.courseregistration.entity.Student;
import com.example.courseregistration.repository.RoleRepository;
import com.example.courseregistration.repository.StudentRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final StudentRepository studentRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomOAuth2UserService(StudentRepository studentRepository,
                                   RoleRepository roleRepository,
                                   PasswordEncoder passwordEncoder) {
        this.studentRepository = studentRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.getOrDefault("name", email);

        Student student = studentRepository.findByEmail(email)
                .orElseGet(() -> createStudentFromGoogle(email, name));

        Set<GrantedAuthority> authorities = student.getRoles()
                .stream()
                .map(Role::getName)
                .map(roleName -> roleName.startsWith("ROLE_") ? roleName : "ROLE_" + roleName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new DefaultOAuth2User(authorities, attributes, "sub");
    }

    private Student createStudentFromGoogle(String email, String name) {
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

