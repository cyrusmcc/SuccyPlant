package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.EnumRole;
import com.cm.contentmanagementapp.models.Role;
import com.cm.contentmanagementapp.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{

    @Autowired
    RoleRepository roleRepository;

    @Override
    public Role findRoleByName(EnumRole name) {
        return roleRepository.findByName(name);
    }
}
