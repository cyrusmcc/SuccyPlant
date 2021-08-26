package com.cm.contentmanagementapp.repositories;

import com.cm.contentmanagementapp.models.Role;
import com.cm.contentmanagementapp.models.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(EnumRole name);

}
