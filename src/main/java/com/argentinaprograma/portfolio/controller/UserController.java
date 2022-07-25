package com.argentinaprograma.portfolio.controller;

import java.util.List;
import java.util.Optional;

import com.argentinaprograma.portfolio.security.dto.Message;
import com.argentinaprograma.portfolio.security.model.User;
import com.argentinaprograma.portfolio.security.model.UserSecurity;
import com.argentinaprograma.portfolio.security.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @DeleteMapping ("/delete")
    public ResponseEntity<Message>  deleteUser(@RequestParam(value = "id") int id,
			@AuthenticationPrincipal UserSecurity user) {

        User userByAuth = userService.getByUserName(user.getUsername()).get();
		//Optional<User> userById = userService.getById(id);

		if (id == userByAuth.getId() || userByAuth.getPerson().getId() == 1){ 
			userService.deleteUser(id);
			return new ResponseEntity<>(new Message("Usuario eliminado."), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(new Message("No se pudo eliminar el usuario."), HttpStatus.UNAUTHORIZED);
		}


    }

}
