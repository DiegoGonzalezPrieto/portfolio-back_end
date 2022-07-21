/*

package com.argentinaprograma.portfolio.config;

import com.argentinaprograma.portfolio.security.enums.RoleName;
import com.argentinaprograma.portfolio.security.model.Role;
import com.argentinaprograma.portfolio.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleCreate implements CommandLineRunner {

    @Autowired
    RoleService roleService;
    
    @Override
    public void run(String... args) throws Exception {
        Role adminRole = new Role(RoleName.ROLE_ADMIN);
        Role userRole = new Role(RoleName.ROLE_USER);
        
        roleService.save(adminRole);
        roleService.save(userRole);
    }

}
*/