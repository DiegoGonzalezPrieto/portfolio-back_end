package com.argentinaprograma.portfolio.security.service;

import com.argentinaprograma.portfolio.security.model.User;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


// converts User into UserSecurity (Principal)

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.getByUserName(userName).get();
        return UserSecurity.build(user);
    }

}
