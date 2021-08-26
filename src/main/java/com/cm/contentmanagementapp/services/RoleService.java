package com.cm.contentmanagementapp.services;

import com.cm.contentmanagementapp.models.EnumRole;
import com.cm.contentmanagementapp.models.Role;

public interface RoleService {

    Role findRoleByName(EnumRole role);

}
