package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;

@Controller
public class SecurityUserController {

    //логика отображения персональной страницы пользователя
    @GetMapping("/user")
    public String showUserInfo(@AuthenticationPrincipal User principaluser, ModelMap model) {
        model.addAttribute("principalUser", principaluser);
        model.addAttribute("roles", principaluser.getAuthorities());
        return "/user";
    }
}
