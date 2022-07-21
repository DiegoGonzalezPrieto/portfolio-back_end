package com.argentinaprograma.portfolio.security.controller;

import com.argentinaprograma.portfolio.security.dto.JwtDto;
import com.argentinaprograma.portfolio.security.dto.LoginUser;
import com.argentinaprograma.portfolio.security.dto.Message;
import com.argentinaprograma.portfolio.security.dto.NewUser;
import com.argentinaprograma.portfolio.security.enums.RoleName;
import com.argentinaprograma.portfolio.security.jwt.JwtProvider;
import com.argentinaprograma.portfolio.security.model.Role;
import com.argentinaprograma.portfolio.security.model.User;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.security.service.RoleService;
import com.argentinaprograma.portfolio.security.service.UserService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
    AuthenticationManager authManager;
    
    @Autowired
    UserService userService;
    
    @Autowired
    RoleService roleService;
    
    @Autowired
    JwtProvider jwtProvider;
    
	@CrossOrigin
    @PostMapping("/new")
    public ResponseEntity<?> newUser(@Valid @RequestBody NewUser newUser, BindingResult bindingResult){
        
        if (bindingResult.hasErrors()){
            return new ResponseEntity("Invalid email, username or other information.", HttpStatus.BAD_REQUEST);
        }
            // TODO : change response entity with a custom message DTO?
        
            if (userService.existsByUserName(newUser.getUserName())){
            return new ResponseEntity("Username is already registered.", HttpStatus.BAD_REQUEST);
        }
        if (userService.existsByEmail(newUser.getEmail())){
            return new ResponseEntity("Email is already registered.", HttpStatus.BAD_REQUEST);
        }
        
        User user =
            new User(newUser.getName(), newUser.getUserName(), newUser.getEmail(), 
                    passwordEncoder.encode(newUser.getPassword()));
        Set<Role> roles = new HashSet<>();
            roles.add(roleService.getByRoleName(RoleName.ROLE_USER).get());
        if (newUser.getRoles().contains("admin"))
            roles.add(roleService.getByRoleName(RoleName.ROLE_ADMIN).get());
        
        user.setRoles(roles);
        userService.save(user);
        return new ResponseEntity(new Message("Sign up completed."), HttpStatus.CREATED);   
       
    }
    
	
    @PostMapping("/login")
    public ResponseEntity<JwtDto> loginUser(@Valid @RequestBody LoginUser loginUser, BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return new ResponseEntity("Login failed.", HttpStatus.BAD_REQUEST);
        Authentication auth =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(
                        loginUser.getUserName(), loginUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtProvider.generateToken(auth);
        // get personId for JwtDto
        UserSecurity user = (UserSecurity) auth.getPrincipal();
        Long personId = user.getPerson().getId();
        
        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities(), personId.toString());
        return new ResponseEntity(jwtDto, HttpStatus.OK);
        
        
    }
}
