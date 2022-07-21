package com.argentinaprograma.portfolio.controller;

import com.argentinaprograma.portfolio.model.AppUser;
import com.argentinaprograma.portfolio.service.IAppUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class AppUserController {

    @Autowired
    private IAppUserService uService;
    
    @GetMapping ("/get/all")
    public ResponseEntity<List<AppUser>> getUsers() {
        List<AppUser> users = uService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    
    @GetMapping ("/get")
    public AppUser getUser(@RequestParam(value = "id") Long id) {
        return uService.findUser(id);
    }

    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping ("/create")
    public String saveUser(@RequestBody AppUser user) {
        uService.saveUser(user) ;
        return "Saved: " + user.toString();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping ("/delete")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam(value = "id") Long id) {
        uService.deleteUser(id) ;
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping ("/edit")
    public String editUser(@RequestBody AppUser user){
        Long uId = user.getId();
        if (uService.findUser(uId) != null){
            uService.saveUser(user);
            return "Edited: " + user.toString();
            
            // TODO : check if associated Person is ok.
            
        } else { // TODO : return if register doesn't exist
            return "Not found";
        }
    }
}
