package ru.kata.spring.rest.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.rest.demo.util.UserNotCreatedException;
import ru.kata.spring.rest.demo.util.UserNotFoundException;
import ru.kata.spring.rest.demo.model.Role;
import ru.kata.spring.rest.demo.model.User;
import ru.kata.spring.rest.demo.service.RoleService;
import ru.kata.spring.rest.demo.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

import static java.lang.String.format;


@RestController
@RequestMapping(value = "/api")
public class RestAdminController {

    private static final String REDIRECT = "redirect:/admin";
    private final UserService userService;
    private final RoleService roleService;


    public RestAdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    //выводим всех пользователей на view "admin"
    @GetMapping("/users")
    public ResponseEntity<List<User>> showUsers() {
        System.out.println(userService.findAll());

        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "/roles")
    public ResponseEntity<List<Role>> showRoles() {
        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }


    //получаем пользователя по id
    @GetMapping("/users/{id}")
    public ResponseEntity<User> findUser(@PathVariable("id") Long id) throws UserNotFoundException {
        if (userService.findUserById(id)==null)
        { throw new UserNotFoundException("User with id = " + id + " not found");}
         return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }

//        @PostMapping("/users")
//        public ResponseEntity<HttpStatus> saveUser (@Valid @RequestBody User user,
//                @RequestParam("roles") Set < Long > roleIds) {
//
//                Set<Role> roles = roleService.findSetRoleBySetId(roleIds);
//                user.setRoles(roles);
//                userService.saveUser(user);
//                return new ResponseEntity<>(HttpStatus.OK);
//            }
    @PostMapping("/users")
    public ResponseEntity<HttpStatus> saveUser (@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error:errors
                 ) {
                //Создадим строку с указанием полей и сообщений об ошибках
                errorMsg.append(error.getField()).append("-").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errorMsg.toString());
        }
        userService.saveUser(user);
        //если все ОК, будет отправлен ответ с пустым телом и http статусом 200
        return new ResponseEntity<>(HttpStatus.OK);
    }

        @PutMapping("/users/{id}")
        public ResponseEntity<HttpStatus> updateUser (@RequestBody @Valid User user,
                @PathVariable("id") Long id){
                userService.update(user, id);
                return new ResponseEntity<>(HttpStatus.OK);

            }

        @DeleteMapping("/users/{id}")
        public ResponseEntity<HttpStatus> deleteUser (@PathVariable("id") Long id){
            userService.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }



    }
