package com.cm.contentmanagementapp.user;

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
