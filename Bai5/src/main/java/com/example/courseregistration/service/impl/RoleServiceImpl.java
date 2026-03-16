package com.example.courseregistration.service.impl;

import com.example.courseregistration.entity.Role;
import com.example.courseregistration.repository.RoleRepository;
import com.example.courseregistration.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role getOrCreateStudentRole() {
        return roleRepository.findByName("STUDENT")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("STUDENT");
                    return roleRepository.save(role);
                });
    }

    @Override
    public Role getOrCreateAdminRole() {
        return roleRepository.findByName("ADMIN")
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName("ADMIN");
                    return roleRepository.save(role);
                });
    }
}
