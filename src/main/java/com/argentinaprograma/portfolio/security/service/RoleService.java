package com.argentinaprograma.portfolio.security.service;

import com.argentinaprograma.portfolio.security.enums.RoleName;
import com.argentinaprograma.portfolio.security.model.Role;
import com.argentinaprograma.portfolio.security.repository.RoleRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class RoleService {

    @Autowired
    RoleRepository roleRepository;
    
    public Optional<Role> getByRoleName(RoleName roleName){
        return roleRepository.findByRoleName(roleName);
    }
    
    public void save(Role role){
        roleRepository.save(role);
    }
    
}
