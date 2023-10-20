package ru.kata.spring.boot_security.demo.controller;


import org.springframework.stereotype.Controller;
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
    @GetMapping
    public String showUsers(ModelMap model) {
        model.addAttribute("users", userService.findAll());
        return "admin";
    }

    //получаем форму для добавления нового пользователя
    @GetMapping("/registration")
    public String createForm(ModelMap model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll()); //чтобы в представлении поставить галочку у нужной роли
        return "/registration";
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

    //получаем форму на изменения
    @GetMapping("/edit/{id}")
    public String updateForm(ModelMap model, @PathVariable("id") Long id) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.findAll());
        return "edit";
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

    //удаляем пользователя
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return REDIRECT;
    }
}
