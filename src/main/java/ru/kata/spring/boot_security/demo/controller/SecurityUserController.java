package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;

@Controller
public class SecurityUserController {
    private final UserDetailsService userDetailsService;

    public SecurityUserController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;

    }

    //логика отображения персональной страницы пользователя
    @GetMapping("/user")
    public String showUserInfo(Principal principal, ModelMap model) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("helloMessage", "Hello, " + user.getName() + " " + user.getLastName() + "!");
        return "/user";
    }
}
