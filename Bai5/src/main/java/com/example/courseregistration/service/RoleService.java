package com.example.courseregistration.service;

import com.example.courseregistration.entity.Role;

public interface RoleService {

    Role getOrCreateStudentRole();

    Role getOrCreateAdminRole();
}

