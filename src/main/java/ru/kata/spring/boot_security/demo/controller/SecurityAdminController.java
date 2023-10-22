package ru.kata.spring.boot_security.demo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.util.Set;


@Controller
@RequestMapping(value = "/admin")
public class SecurityAdminController {

    private static final String REDIRECT = "redirect:/admin";
    private final UserService userService;
    private final RoleService roleService;


    public SecurityAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //выводим всех пользователей на view "admin"
    @GetMapping()
    public String showUsers(Model model, @AuthenticationPrincipal User principalUser) {
        model.addAttribute("principalUser", principalUser);
        model.addAttribute("usersSet", userService.findAll());// Добавили всех юзеров из БД
        model.addAttribute("roles", roleService.findAll()); //Добавили все роли из БД
        return "admin";
    }

    //получаем пользователя по id
    @GetMapping("/user/{id}")
    public String findUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", user.getRoles());
        return "user";
    }

    //получаем форму для добавления нового пользователя
    @GetMapping("/registration")
    public String createForm(ModelMap model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return REDIRECT;
    }

    //получаем форму на изменения
    @GetMapping("/edit/{id}")
    public String updateForm(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.findAll());
        return REDIRECT;
    }

    //удаляем пользователя
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return REDIRECT;
    }

    //создаем нового
    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
                             @RequestParam("roles") Set<Long> roleIds) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        Set<Role> roles = roleService.findSetRoleBySetId(roleIds);
        user.setRoles(roles);
        userService.saveUser(user);
        return REDIRECT;
    }


    // меняем данные пользователя
    @PutMapping("/edit/{id}")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindResult, @PathVariable("id") Long id,
                         @RequestParam("roles") Set<Long> roleIds) {
        if (bindResult.hasErrors()) {
            return "edit";
        } else {
            Set<Role> roles = roleService.findSetRoleBySetId(roleIds);
            user.setRoles(roles);
            userService.update(user, id);
        }
        return REDIRECT;
    }

}
