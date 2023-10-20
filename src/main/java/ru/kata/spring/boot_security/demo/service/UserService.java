package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    User findUserById(Long id);

    @Transactional
    void saveUser(User user);

    void update(User updateUser, Long id);

    // void saveUser(User user);

    void deleteUserById(long id);

//public List <Role> addRoleToUser(User user,String roleName);

}


