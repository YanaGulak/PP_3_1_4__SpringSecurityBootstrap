package ru.kata.spring.rest.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.rest.demo.model.User;
import ru.kata.spring.rest.demo.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping(value = "/api/users/user_page")
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    //логика отображения персональной страницы пользователя
    @GetMapping()
    public ResponseEntity<User> getAuthUserInfo(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        if (user == null) {
            throw new UsernameNotFoundException("User with username = " + principal.getName() + " not found");
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
