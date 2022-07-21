package com.argentinaprograma.portfolio.security.repository;

import com.argentinaprograma.portfolio.security.enums.RoleName;
import com.argentinaprograma.portfolio.security.model.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    
    Optional<Role> findByRoleName(RoleName roleName);
}
